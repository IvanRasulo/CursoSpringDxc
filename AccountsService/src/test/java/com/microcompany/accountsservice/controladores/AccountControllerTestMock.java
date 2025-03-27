package com.microcompany.accountsservice.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.services.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.security.auth.login.AccountNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AccountControllerTestMock {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void givenExistingAccountId_whenGetAccount_thenReturnAccount() throws Exception {
        Long accountId = 1L;
        Account mockAccount = new Account();
        mockAccount.setId(accountId);
        mockAccount.setType("Ahorro");
        mockAccount.setOpeningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        mockAccount.setBalance(5000);
        mockAccount.setOwnerId(1L);

        Mockito.when(accountService.getAccount(accountId)).thenReturn(mockAccount);

        mockMvc.perform(get("/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verificar que el código de estado sea 200
                .andExpect(jsonPath("$.type").value("Ahorro"))
                .andExpect(jsonPath("$.balance").value(5000));
    }

    @Test
    public void givenNonExistingAccountId_whenGetAccount_thenReturnNotFound() throws Exception {
        Long accountId = 99L;

        Mockito.when(accountService.getAccount(accountId)).thenThrow(new AccountNotfoundException(accountId));

        mockMvc.perform(get("/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());  // Verificar que el código de estado sea 404
    }

    @Test
    public void givenExistingAccountId_whenDeleteAccount_thenReturnNoContent() throws Exception {
        Long accountId = 1L;

        Mockito.doNothing().when(accountService).delete(accountId);

        mockMvc.perform(delete("/accounts/delete/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenNonExistingAccountId_whenDeleteAccount_thenReturnNotFound() throws Exception {
        Long accountId = 99L;

        Mockito.doThrow(new AccountNotfoundException(accountId)).when(accountService).delete(accountId);

        mockMvc.perform(delete("/accounts/delete/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidAccount_whenCreateAccount_thenReturnCreated() throws Exception {
        Account newAccount = new Account();
        newAccount.setType("Ahorro");
        newAccount.setOpeningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        newAccount.setBalance(5000);
        newAccount.setOwnerId(1L);

        Account createdAccount = new Account();
        createdAccount.setId(1L);


        Mockito.when(accountService.create(Mockito.any(Account.class))).thenReturn(createdAccount);

        mockMvc.perform(post("/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newAccount)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void givenInvalidAccount_whenCreateAccount_thenReturnPreconditionFailed() throws Exception {
        Account invalidAccount = new Account();
        invalidAccount.setType(null);
        invalidAccount.setOpeningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        invalidAccount.setBalance(5000);
        invalidAccount.setOwnerId(1L);

        mockMvc.perform(post("/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidAccount)))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void givenExistingAccountId_whenUpdateAccount_thenReturnUpdatedAccount() throws Exception {
        Long accountId = 1L;

        Account updatedAccount = new Account();
        updatedAccount.setType("Corriente");
        updatedAccount.setOpeningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        updatedAccount.setBalance(10000);
        updatedAccount.setOwnerId(1L);

        Account updatedResponse = new Account();
        updatedResponse.setId(accountId);
        updatedResponse.setType("Corriente");
        updatedResponse.setOpeningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        updatedResponse.setBalance(10000);
        updatedResponse.setOwnerId(1L);

        Mockito.when(accountService.updateAccount(Mockito.anyLong(), Mockito.any(Account.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(put("/accounts/update/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedAccount)))
                .andExpect(status().isOk())  // Verificar que el código de estado sea 200
                .andExpect(jsonPath("$.type").value("Corriente"))
                .andExpect(jsonPath("$.balance").value(10000));
    }

    @Test
    public void givenNonExistingAccountId_whenUpdateAccount_thenReturnNotFound() throws Exception {
        Long accountId = 99L;

        Account updatedAccount = new Account();
        updatedAccount.setType("Corriente");
        updatedAccount.setOpeningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        updatedAccount.setBalance(10000);
        updatedAccount.setOwnerId(1L);

        Mockito.when(accountService.updateAccount(Mockito.anyLong(), Mockito.any(Account.class)))
                .thenThrow(new AccountNotfoundException(accountId));




        mockMvc.perform(put("/accounts/update/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedAccount)))
                .andExpect(status().isNotFound());
    }




}
