package com.microcompany.accountsservice.controladores;

import com.microcompany.accountsservice.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

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
        // Crear encabezados y especificar el tipo de contenido aceptado
        HttpHeaders headers = new HttpHeaders();
        headers.set("ACCEPT", MediaType.APPLICATION_JSON_VALUE);

        // Crear la entidad HTTP con los encabezados
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Hacer la solicitud GET con encabezados
        ResponseEntity<Account> response = restTemplate.exchange(
                "http://localhost:" + port + "/accounts/1",
                org.springframework.http.HttpMethod.GET,
                entity,
                Account.class
        );
        // Validar el estado de respuesta
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Mostrar el cuerpo de la respuesta
        System.out.println(response.getBody());

        // Validar el tipo de contenido devuelto
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }
}
