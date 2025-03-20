package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.model.StatusMessage;
import com.microcompany.accountsservice.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.beans.Statement;
import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController implements IAccountController{
    @Override
    public ResponseEntity withdrawBalance(Long ownerId, Long id, int amount) {
       Account account = servicioAccount.getAccount(id);
        if (account != null){
            Account updateAccount = servicioAccount.withdrawBalance(ownerId, amount, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(updateAccount);
        }else{
            return new ResponseEntity<>(new StatusMessage(HttpStatus.NOT_FOUND.value(),"No existe la cuenta"), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity deleteAccountsUsingOwnerId(Long ownerId) {
        List<Account> accounts = servicioAccount.getAccountByOwnerId(ownerId);
        if (accounts.size() > 0 && accounts != null){
            servicioAccount.deleteAccountsUsingOwnerId(ownerId);
            return ResponseEntity.noContent().build();
        }else{
            return new ResponseEntity<>(new StatusMessage(HttpStatus.NOT_FOUND.value(),"No se ha podido eliminar la totalidad de las cuentas"), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity create(Account account) {
        servicioAccount.create(account);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(account);
    }

    @Override
    public ResponseEntity updateAccount(Long id, Account account) {
        servicioAccount.updateAccount(id, account);
        if (account != null && id > 0){
            return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(account);
        }else {
           return new ResponseEntity<>(new StatusMessage(HttpStatus.NOT_MODIFIED.value(),"No se ha podido crear la cuenta"), HttpStatus.NOT_MODIFIED);
        }
    }

    @Override
    public ResponseEntity delete(Long id) {
        Account account = servicioAccount.getAccount(id);
        if (account != null){
            servicioAccount.delete(id);
            return ResponseEntity.noContent().build();
        }else{
            return new ResponseEntity<>(new StatusMessage(HttpStatus.NOT_FOUND.value(),"No se ha podido eliminar la cuenta"), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity addBalance(Long ownerId, Long id, int amount) {
        Account account = servicioAccount.getAccount(id);
        if (account != null){
            Account updateAccount = servicioAccount.addBalance(ownerId, amount, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(updateAccount);
        }else{
            return new ResponseEntity<>(new StatusMessage(HttpStatus.NOT_FOUND.value(),"No existe la cuenta"), HttpStatus.NOT_FOUND);
        }
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
