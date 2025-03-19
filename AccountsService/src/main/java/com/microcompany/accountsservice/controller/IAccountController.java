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


    @GetMapping(value = "/{ownerId}")
    ResponseEntity getAccountsByOwnerId(@PathParam(value = "ownerId") Long ownerId);

    @PostMapping(value = "")
    ResponseEntity create (@Valid @RequestBody Account account);

    @PutMapping(value = "/{id}")
    ResponseEntity updateAccount(@PathParam(value = "id") Long id);


}
