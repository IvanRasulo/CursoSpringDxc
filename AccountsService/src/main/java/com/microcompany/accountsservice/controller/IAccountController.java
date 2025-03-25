package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.model.Account;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/default")
@Validated
public interface IAccountController {

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity getAccount(@Min(value = 1, message = "El ID debe ser mayor que 0") @PathVariable(value = "id") Long id);

    @GetMapping(value = "/getAll/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity getAccountsByOwnerId(@Min(value = 1, message = "El ID del propietario debe ser mayor que 0") @PathVariable(value = "ownerId") Long ownerId);

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity create(@Valid @RequestBody Account account);

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity updateAccount(@Min(value = 1, message = "El ID de la cuenta debe ser mayor que 0") @PathVariable(value = "id") Long id,
                                 @Valid @RequestBody Account account);

    @DeleteMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity delete(@Min(value = 1, message = "El ID de la cuenta debe ser mayor que 0") @PathVariable(value = "id") Long id);

    @PutMapping(value = "/{ownerId}/{accountId}/addBalance", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Account> addBalance(@Min(value = 1, message = "El ID del propietario debe ser mayor que 0") @PathVariable(value = "ownerId") Long ownerId,
                                       @Min(value = 1, message = "El ID de la cuenta debe ser mayor que 0") @PathVariable(value = "accountId") Long accountId,
                                       @Min(value = 1, message = "El monto debe ser mayor que 0") @RequestParam(value = "amount") int amount);

    @DeleteMapping(value = "/deleteAll/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity deleteAccountsUsingOwnerId(@Min(value = 1, message = "El ID del propietario debe ser mayor que 0") @PathVariable(value = "ownerId") Long ownerId);

    @PutMapping(value = "/{ownerId}/{accountId}/withdrawBalance", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity withdrawBalance(@Min(value = 1, message = "El ID del propietario debe ser mayor que 0") @PathVariable(value = "ownerId") Long ownerId,
                                   @Min(value = 1, message = "El ID de la cuenta debe ser mayor que 0") @PathVariable(value = "accountId") Long accountId,
                                   @Min(value = 1, message = "El monto debe ser mayor que 0") @RequestParam(value = "amount") int amount);

    @GetMapping(value = "/{ownerId}/prestamo", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Boolean> comprobarPrestamo(@Min(value = 1, message = "El ID del propietario debe ser mayor que 0") @PathVariable(value = "ownerId") Long ownerId,
                                              @Min(value = 1, message = "El monto debe ser mayor que 0") @RequestParam(value = "amount") Double amount);
}
