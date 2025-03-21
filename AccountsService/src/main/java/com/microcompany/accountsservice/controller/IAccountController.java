package com.microcompany.accountsservice.controller;


import com.microcompany.accountsservice.model.Account;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/default")
@Validated
public interface IAccountController {

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity getAccount(@PathVariable(value = "id") Long id);


    @GetMapping(value = "/getAll/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity getAccountsByOwnerId(@PathVariable(value = "ownerId") Long ownerId);

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity create (@Valid @RequestBody Account account);

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity updateAccount(@PathVariable(value = "id") Long id, @RequestBody Account account);

    @DeleteMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity delete(@PathVariable(value = "id") Long id);


    @PutMapping(value = "/{ownerId}/{accountId}/addBalance", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Account> addBalance(@PathVariable(value = "ownerId") Long ownerId, @PathVariable(value = "accountId") Long id, @RequestParam(value = "amount") int amount);


    /*
    @PutMapping(value = "/{ownerId}/{accountId}/addBalance",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Account> addBalance(
            @PathVariable(value = "ownerId") Long ownerId,
            @PathVariable(value = "accountId") Long accountId,
            @RequestParam(value = "amount") int amount);

     */

    @DeleteMapping(value = "/deleteAll/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity deleteAccountsUsingOwnerId(@PathVariable(value = "ownerId") Long ownerId);

    @PutMapping(value = "/{ownerId}/{accountId}/withdrawBalance", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity withdrawBalance(@PathVariable(value = "ownerId") Long ownerId, @PathVariable(value = "accountId") Long id, @RequestParam(value = "amount") int amount);

    @GetMapping(value = "/{ownerId}/prestamo", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Boolean> comprobarPrestamo(@PathVariable(value = "ownerId") Long ownerId, @RequestParam(value = "amount") Double amount);





}
