package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.model.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/default")
@Validated
@Tag(name = "Endpoints Cuentas", description = "Endpoints para la gestión de cuentas bancarias")
public interface IAccountController {

    @Operation(summary = "Obtiene una cuenta", description = "Obtiene una cuenta según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta obtenida exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró la cuenta con el ID proporcionado.")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity getAccount(
            @Parameter(description = "ID de la cuenta, debe ser mayor que 0.", required = true, example = "1")
            @Min(value = 1, message = "El ID debe ser mayor que 0") @PathVariable(value = "id") Long id
    );

    @Operation(summary = "Obtiene todas las cuentas del propietario", description = "Obtiene todas las cuentas asociadas al ID de un propietario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuentas obtenidas exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontraron cuentas asociadas al propietario.")
    })
    @GetMapping(value = "/getAll/{ownerId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity getAccountsByOwnerId(
            @Parameter(description = "ID del propietario, debe ser mayor que 0.", required = true, example = "1")
            @Min(value = 1, message = "El ID del propietario debe ser mayor que 0") @PathVariable(value = "ownerId") Long ownerId
    );

    @Operation(summary = "Crea una cuenta", description = "Crea una nueva cuenta bancaria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente."),
            @ApiResponse(responseCode = "400", description = "Datos de la cuenta no válidos.")
    })
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la cuenta a crear.", required = true)
            @Valid @RequestBody Account account
    );

    @Operation(summary = "Actualiza una cuenta", description = "Actualiza los datos de una cuenta bancaria según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Cuenta actualizada exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró la cuenta para actualizar."),
            @ApiResponse(responseCode = "400", description = "Datos de la cuenta no válidos.")
    })
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity updateAccount(
            @Parameter(description = "ID de la cuenta, debe ser mayor que 0.", required = true, example = "1")
            @Min(value = 1, message = "El ID de la cuenta debe ser mayor que 0") @PathVariable(value = "id") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados de la cuenta.", required = true)
            @Valid @RequestBody Account account
    );

    @Operation(summary = "Elimina una cuenta", description = "Elimina una cuenta según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta eliminada exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró la cuenta para eliminar.")
    })
    @DeleteMapping(value = "/delete/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity delete(
            @Parameter(description = "ID de la cuenta, debe ser mayor que 0.", required = true, example = "1")
            @Min(value = 1, message = "El ID de la cuenta debe ser mayor que 0") @PathVariable(value = "id") Long id
    );

    @Operation(summary = "Añade balance a una cuenta", description = "Añade una cantidad específica al balance de una cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Balance añadido exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró la cuenta para añadir balance."),
            @ApiResponse(responseCode = "400", description = "Monto inválido.")
    })
    @PutMapping(value = "/{ownerId}/{accountId}/addBalance", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Account> addBalance(
            @Parameter(description = "ID del propietario, debe ser mayor que 0.", required = true, example = "1")
            @Min(value = 1, message = "El ID del propietario debe ser mayor que 0") @PathVariable(value = "ownerId") Long ownerId,
            @Parameter(description = "ID de la cuenta, debe ser mayor que 0.", required = true, example = "10")
            @Min(value = 1, message = "El ID de la cuenta debe ser mayor que 0") @PathVariable(value = "accountId") Long accountId,
            @Parameter(description = "Monto a añadir, debe ser mayor que 0.", required = true, example = "100")
            @Min(value = 1, message = "El monto debe ser mayor que 0") @RequestParam(value = "amount") int amount
    );

    @Operation(summary = "Elimina todas las cuentas del propietario", description = "Elimina todas las cuentas asociadas al propietario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuentas eliminadas exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontraron cuentas asociadas al propietario.")
    })
    @DeleteMapping(value = "/deleteAll/{ownerId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity deleteAccountsUsingOwnerId(
            @Parameter(description = "ID del propietario, debe ser mayor que 0.", required = true, example = "1")
            @Min(value = 1, message = "El ID del propietario debe ser mayor que 0") @PathVariable(value = "ownerId") Long ownerId
    );

    @Operation(summary = "Retira balance de una cuenta", description = "Retira una cantidad específica del balance de una cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Balance retirado exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontró la cuenta para retirar balance."),
            @ApiResponse(responseCode = "400", description = "Monto inválido.")
    })
    @PutMapping(value = "/{ownerId}/{accountId}/withdrawBalance", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity withdrawBalance(
            @Parameter(description = "ID del propietario, debe ser mayor que 0.", required = true, example = "1")
            @Min(value = 1, message = "El ID del propietario debe ser mayor que 0") @PathVariable(value = "ownerId") Long ownerId,
            @Parameter(description = "ID de la cuenta, debe ser mayor que 0.", required = true, example = "10")
            @Min(value = 1, message = "El ID de la cuenta debe ser mayor que 0") @PathVariable(value = "accountId") Long accountId,
            @Parameter(description = "Monto a retirar, debe ser mayor que 0.", required = true, example = "100")
            @Min(value = 1, message = "El monto debe ser mayor que 0") @RequestParam(value = "amount") int amount
    );

    @Operation(summary = "Comprueba elegibilidad para préstamo", description = "Verifica si el propietario es elegible para un préstamo según el monto solicitado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluación completada exitosamente."),
            @ApiResponse(responseCode = "400", description = "Monto solicitado inválido.")
    })
    @GetMapping(value = "/{ownerId}/prestamo", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Boolean> comprobarPrestamo(
            @Parameter(description = "ID del propietario, debe ser mayor que 0.", required = true, example = "1")
            @Min(value = 1, message = "El ID del propietario debe ser mayor que 0") @PathVariable(value = "ownerId") Long ownerId,
            @Parameter(description = "Monto del préstamo solicitado, debe ser mayor que 0.", required = true, example = "1000.0")
            @Min(value = 1, message = "El monto debe ser mayor que 0") @RequestParam(value = "amount") Double amount
    );
}

