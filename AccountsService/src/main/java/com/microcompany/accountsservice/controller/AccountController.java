package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.exception.OwnerNotFoundException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.model.StatusMessage;
import com.microcompany.accountsservice.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.beans.Statement;
import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController implements IAccountController{
    @Autowired
    private IAccountService servicioAccount;

    @Override
    public ResponseEntity getAccount(Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(servicioAccount.getAccount(id));
    }

    @Override
    public ResponseEntity getAccountsByOwnerId(Long ownerId){
        return ResponseEntity.status(HttpStatus.OK.value()).body(servicioAccount.getAccountByOwnerId(ownerId));
    }
    @Override
    public ResponseEntity withdrawBalance(Long ownerId, Long id, int amount) {
        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body( servicioAccount.withdrawBalance(ownerId, amount, id));
    }

    @Override
    public ResponseEntity deleteAccountsUsingOwnerId(Long ownerId) {
        servicioAccount.deleteAccountsUsingOwnerId(ownerId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity create(Account account) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(servicioAccount.create(account));
    }

    @Override
    public ResponseEntity updateAccount(Long id, Account account) {
        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body( servicioAccount.updateAccount(id, account));
    }

    @Override
    public ResponseEntity delete(Long id) {
        servicioAccount.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity addBalance(Long ownerId, Long id, int amount) {
        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(servicioAccount.addBalance(ownerId, amount, id));
    }

    @Override
    public ResponseEntity<Boolean> comprobarPrestamo(Long customerId, Double loanAmount) {
        return ResponseEntity.ok(servicioAccount.esPosiblePrestamo(customerId, loanAmount));
    }
}
