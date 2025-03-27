package com.microcompany.accountsservice.controladores;

import com.microcompany.accountsservice.controller.AccountController;
import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(value = "classpath:testing.sql")
public class AccountControllerTest {

    @Autowired
    private AccountController controller;

    @Test
    void givenProducts_whengetAccount_thenIsNotNull() {
        ResponseEntity<Account> response = controller.getAccount(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void givenProducts_whengetAccount_thenAccountNotFound() {
        Long accountId = 999L;

        assertThrows(AccountNotfoundException.class, () -> {
            controller.getAccount(accountId);
        });
    }


    @Test
    void givenValidAccount_whenCreateAccount_thenAccountCreated() {
        Account newAccount = new Account();
        newAccount.setType("Ahorros");
        newAccount.setOpeningDate(new Date());
        newAccount.setBalance(1000);
        newAccount.setOwnerId(1L);

        ResponseEntity<Account> response = controller.create(newAccount);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getType()).isEqualTo("Ahorros");
    }

    @Test
    void givenInvalidAccount_whenCreateAccount_thenConstraintViolation() {
        Account invalidAccount = new Account();
        invalidAccount.setType(""); // Invalid type
        invalidAccount.setOpeningDate(new Date());
        invalidAccount.setBalance(-100); // Invalid balance
        invalidAccount.setOwnerId(1L);


        assertThrows(ConstraintViolationException.class, () -> {
            controller.create(invalidAccount);
        });    }

    @Test
    void givenValidAccount_whenUpdateAccount_thenAccountUpdated() {
        Long existingAccountId = 1L;
        Account updatedAccount = new Account();
        updatedAccount.setType("Corriente");
        updatedAccount.setOpeningDate(new Date());
        updatedAccount.setBalance(1500);
        updatedAccount.setOwnerId(2L);

        ResponseEntity<Account> response = controller.updateAccount(existingAccountId, updatedAccount);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getType()).isEqualTo("Corriente");
    }

    @Test
    void givenNonExistingAccount_whenUpdateAccount_thenNotFound() {
        Long nonExistingAccountId = 999L;
        Account updatedAccount = new Account();
        updatedAccount.setType("Corriente");
        updatedAccount.setOpeningDate(new Date());
        updatedAccount.setBalance(1500);
        updatedAccount.setOwnerId(2L);

        assertThrows(AccountNotfoundException.class, () -> {
            controller.updateAccount(nonExistingAccountId, updatedAccount);
        });    }

    @Test
    void givenValidAccount_whenDeleteAccount_thenAccountDeleted() {
        Long accountId = 3L;

        ResponseEntity<Void> response = controller.delete(accountId);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void givenNonExistingAccount_whenDeleteAccount_thenNotFound() {
        Long nonExistingAccountId = 999L;

        assertThrows(AccountNotfoundException.class, () -> {
            controller.delete(nonExistingAccountId);
        });
    }

}
