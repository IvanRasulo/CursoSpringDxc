package com.microcompany.accountsservice.controller;


import com.microcompany.accountsservice.model.Account;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/default")
@Validated
public interface IAccountController {

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity getAccount(@PathParam(value = "id") Long id);


    @GetMapping(value = "/getAll/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity getAccountsByOwnerId(@PathParam(value = "ownerId") Long ownerId);

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity create (@Valid @RequestBody Account account);

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity updateAccount(@PathParam(value = "id") Long id, Account account);

    @DeleteMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity delete(@PathParam(value = "id") Long id);

    @PutMapping(value = "/{ownerId}/{accountId}/addbalance?amount={amount}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity addBalance(@PathParam(value = "ownerId") Long ownerId, @PathParam(value = "id") Long id, @RequestParam(value = "amount") int amount);

    @DeleteMapping(value = "/deleteAll/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity deleteAccountsUsingOwnerId(@PathParam(value = "ownerId") Long ownerId);

    @PutMapping(value = "/{ownerId}/{accountId}/withdrawBalance?amount={amount}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity withdrawBalance(@PathParam(value = "ownerId") Long ownerId, @PathParam(value = "id") Long id, @RequestParam(value = "amount") int amount);

    @GetMapping(value = "/{ownerId}/prestamo?amount={amount}", consumes = MediaType.APPLICATION_JSON_VALUE, // Consume JSON
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Boolean> comprobarPrestamo(@PathParam(value = "ownerId") Long ownerId, @RequestParam(value = "amount") Double amount);





}
