package com.microcompany.accountsservice.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microcompany.accountsservice.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("classpath:testing.sql")
public class AccountControllerTestRestTemplate {
    @Value(value = "${local.server.port}")
//    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenUrl_whenGetAccountById_thenAnAccountExists() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("ACCEPT", MediaType.APPLICATION_JSON_VALUE + "," + MediaType.APPLICATION_XML_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Account> response = restTemplate.exchange(
                "http://localhost:" + port + "/accounts/1",
                HttpMethod.GET,
                entity,
                Account.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(response.getBody());
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }
    @Test
    public void givenNonExistingAccountId_whenGetAccount_thenNotFoundShouldBeReturned() throws Exception {
        // Asumimos que la cuenta con ID 999 no existe
        Long nonExistingAccountId = 999L;

        HttpHeaders headers = new HttpHeaders();
        headers.set("ACCEPT", MediaType.APPLICATION_JSON_VALUE + "," + MediaType.APPLICATION_XML_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Account> response = restTemplate.exchange(
                "http://localhost:" + port + "/accounts/" + nonExistingAccountId,
                HttpMethod.GET,
                entity,
                Account.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenExistingAccountId_whenDeleteAccount_thenNoContentShouldBeReturned() throws Exception {
        // Asumimos que la cuenta con ID 1 existe
        Long existingAccountId = 1L;

        HttpHeaders headers = new HttpHeaders();
        headers.set("ACCEPT", MediaType.APPLICATION_JSON_VALUE + "," + MediaType.APPLICATION_XML_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/accounts/delete/" + existingAccountId,
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void givenNonExistingAccountId_whenDeleteAccount_thenNotFoundShouldBeReturned() throws Exception {
        // Asumimos que la cuenta con ID 999 no existe
        Long nonExistingAccountId = 999L;

        HttpHeaders headers = new HttpHeaders();
        headers.set("ACCEPT", MediaType.APPLICATION_JSON_VALUE + "," + MediaType.APPLICATION_XML_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/accounts/delete/" + nonExistingAccountId,
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    public void givenValidAccount_whenCreateAccount_thenAccountShouldBeCreated() throws Exception {
        // Crear un objeto Account con datos válidos
        Account newAccount = new Account();
        newAccount.setType("Ahorro");
        newAccount.setOpeningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        newAccount.setBalance(1000);  // Balance positivo
        newAccount.setOwnerId(1L);  // ID del propietario válido

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        // Convertir el objeto Account a JSON
        String accountJson = new ObjectMapper().writeValueAsString(newAccount);

        // Crear el HttpEntity con el objeto Account
        HttpEntity<String> entity = new HttpEntity<>(accountJson, headers);

        ResponseEntity<Account> response = restTemplate.exchange(
                "http://localhost:" + port + "/accounts/create",
                HttpMethod.POST,
                entity,
                Account.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getType()).isEqualTo("Ahorro");
        assertThat(response.getBody().getBalance()).isEqualTo(1000);
        assertThat(response.getBody().getOwnerId()).isEqualTo(1L);
    }

    @Test
    public void givenInvalidAccount_whenCreateAccount_thenPreconditionFailedShouldBeReturned() throws Exception {
        // Crear un objeto Account con datos inválidos
        Account newAccount = new Account();
        newAccount.setType(null);  // Tipo nulo
        newAccount.setOpeningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        newAccount.setBalance(-100);  // Balance negativo (no permitido)
        newAccount.setOwnerId(1L);  // ID del propietario válido

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        // Convertir el objeto Account a JSON
        String accountJson = new ObjectMapper().writeValueAsString(newAccount);

        // Crear el HttpEntity con el objeto Account
        HttpEntity<String> entity = new HttpEntity<>(accountJson, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/accounts/create",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    public void givenExistingAccountId_whenUpdateAccount_thenAccountShouldBeUpdated() throws Exception {
        // Asumimos que la cuenta con ID 1 existe
        Long existingAccountId = 5L;

        // Crear un objeto Account con los nuevos datos válidos
        Account updatedAccount = new Account();
        updatedAccount.setType("Corriente");
        updatedAccount.setOpeningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-01"));
        updatedAccount.setBalance(2000);  // Balance válido
        updatedAccount.setOwnerId(2L);  // ID del propietario válido

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        // Convertir el objeto Account a JSON
        String accountJson = new ObjectMapper().writeValueAsString(updatedAccount);

        // Crear el HttpEntity con el objeto Account
        HttpEntity<String> entity = new HttpEntity<>(accountJson, headers);

        ResponseEntity<Account> response = restTemplate.exchange(
                "http://localhost:" + port + "/accounts/update/" + existingAccountId,
                HttpMethod.PUT,
                entity,
                Account.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getType()).isEqualTo("Corriente");
        assertThat(response.getBody().getBalance()).isEqualTo(2000);
        assertThat(response.getBody().getOwnerId()).isEqualTo(5L);
    }

    @Test
    public void givenInvalidAccount_whenUpdateAccount_thenBadRequestShouldBeReturned() throws Exception {
        // Asumimos que la cuenta con ID 999 no existe
        Long nonExistingAccountId = 999L;

        // Crear un objeto Account con datos inválidos
        Account updatedAccount = new Account();
        updatedAccount.setType("dsadasd");  // Tipo nulo (no permitido)
        updatedAccount.setOpeningDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-01"));
        updatedAccount.setBalance(2000);  // Balance negativo (no permitido)
        updatedAccount.setOwnerId(2L);  // ID del propietario válido

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        // Convertir el objeto Account a JSON
        String accountJson = new ObjectMapper().writeValueAsString(updatedAccount);

        // Crear el HttpEntity con el objeto Account
        HttpEntity<String> entity = new HttpEntity<>(accountJson, headers);

        // Intentar actualizar con un ID no existente
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/accounts/update/" + nonExistingAccountId,
                HttpMethod.PUT,
                entity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }



}