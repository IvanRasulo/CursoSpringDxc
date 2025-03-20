package com.microcompany.accountsservice.controller;


import com.microcompany.accountsservice.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/default")
@Validated
public interface IAccountController {

    @GetMapping(value = "/{id}")
    ResponseEntity getAccount(@PathParam(value = "id") Long id);


    @GetMapping(value = "/getAll/{ownerId}")
    ResponseEntity getAccountsByOwnerId(@PathParam(value = "ownerId") Long ownerId);

    @PostMapping(value = "")
    ResponseEntity create (@Valid @RequestBody Account account);

    @PutMapping(value = "/update/{id}")
    ResponseEntity updateAccount(@PathParam(value = "id") Long id, Account account);

    @DeleteMapping(value = "/delete/{id}")
    ResponseEntity delete(@PathParam(value = "id") Long id);

    @PutMapping(value = "/{ownerId}/{accountId}/addbalance?amount={amount}")
    ResponseEntity addBalance(@PathVariable(value = "ownerId") Long ownerId, @PathVariable(value = "id") Long id, @PathParam(value = "amount") int amount);

    @DeleteMapping(value = "/deleteAll/{ownerId}")
    ResponseEntity deleteAccountsUsingOwnerId(@PathParam(value = "ownerId") Long ownerId);

    @PutMapping(value = "/{ownerId}/{accountId}/withdrawBalance?amount={amount}")
    ResponseEntity withdrawBalance(@PathVariable(value = "ownerId") Long ownerId, @PathVariable(value = "id") Long id, @PathParam(value = "amount") int amount);





}
