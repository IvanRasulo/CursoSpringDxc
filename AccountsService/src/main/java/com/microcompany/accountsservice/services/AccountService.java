package com.microcompany.accountsservice.services;

import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.exception.OwnerNotFoundException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.model.Customer;
import com.microcompany.accountsservice.persistence.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountService implements IAccountService {
    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account create(Account account) {
        Date current_Date = new Date();
        account.setOpeningDate(current_Date);
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        Customer owner = null; // Will be gotten from user service
        account.setOwner(owner);
        return account;
    }

    @Override
    public List<Account> getAccountByOwnerId(Long ownerId) {
        List<Account> accs = accountRepository.findByOwnerId(ownerId);
        if(accs.size()>0){
            return accs;
        } else {
            throw new OwnerNotFoundException(ownerId);
        }
    }

    @Override
    public Account updateAccount(Long id, Account account) {
        Account newAccount = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        newAccount.setType(account.getType());
        newAccount.setBalance(account.getBalance());
        return accountRepository.save(newAccount);
    }

    @Override
    public Account addBalance(Long id, int amount, Long ownerId) {
        Account newAccount = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        Customer owner = null;// Will be gotten from user service
        int newBalance = newAccount.getBalance() + amount;
        newAccount.setBalance(newBalance);
        return accountRepository.save(newAccount);
    }

    @Override
    public Account withdrawBalance(Long id, int amount, Long ownerId) {
        Account newAccount = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        Customer owner = null; // Will be gotten from user service
        int newBalance = newAccount.getBalance() - amount;
        newAccount.setBalance(newBalance);
        return accountRepository.save(newAccount);
    }

    @Override
    public void delete(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        this.accountRepository.delete(account);
    }

    @Override
    public void deleteAccountsUsingOwnerId(Long ownerId) {
        List<Account> accounts = accountRepository.findByOwnerId(ownerId);
        if(accounts.size()>0) {
            for (Account account : accounts) {
                this.accountRepository.delete(account);
            }
        } else throw new OwnerNotFoundException(ownerId);
    }

    @Override
    public boolean esPosiblePrestamo(Long customerId, Double loanAmount) {
        List<Account> accounts = getAccountByOwnerId(customerId);
        if(accounts.size()>0) {
            double totalBalance = accounts.stream()
                    .mapToDouble(Account::getBalance)
                    .sum();
            return loanAmount <= (totalBalance * 0.8);
        } else throw new OwnerNotFoundException(customerId);
    }


}
