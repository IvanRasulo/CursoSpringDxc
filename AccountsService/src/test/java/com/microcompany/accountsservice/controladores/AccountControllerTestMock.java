package com.microcompany.accountsservice.controladores;

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

import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}
