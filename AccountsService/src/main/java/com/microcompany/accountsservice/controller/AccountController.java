package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController implements IAccountController{

    @Override
    public ResponseEntity create(Account account) {
        servicioAccount.create(account);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(account);
    }

    @Autowired
    private IAccountService servicioAccount;

    //Devuelve una cuenta segun el id
    @Override
    public ResponseEntity getAccount(Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(servicioAccount.getAccount(id));
    }

    //Listado de cuentas del usuario
    @Override
    public ResponseEntity getAccountsByOwnerId(Long ownerId){
        return ResponseEntity.status(HttpStatus.OK.value()).body(servicioAccount.getAccountByOwnerId(ownerId));
    }
}
