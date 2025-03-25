package com.microcompany.accountsservice.servicios;

import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.exception.OwnerNotFoundException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.persistence.AccountRepository;
import com.microcompany.accountsservice.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceMockTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account();
        account.setId(1L);
        account.setType("Savings");
        account.setBalance(5000);
        account.setOwnerId(1L);
    }

    @Test
    public void testCreateAccount() {

        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        Account createdAccount = accountService.create(account);

        assertNotNull(createdAccount);
        assertEquals("Savings", createdAccount.getType());
        assertEquals(5000, createdAccount.getBalance());
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any(Account.class));
    }

    @Test
    public void testGetAccount() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account foundAccount = accountService.getAccount(1L);

        assertNotNull(foundAccount);
        assertEquals(1L, foundAccount.getId());
        assertEquals("Savings", foundAccount.getType());
        assertEquals(5000, foundAccount.getBalance());
        Mockito.verify(accountRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
    }

    @Test
    public void testGetAccountNotFound() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        AccountNotfoundException exception = assertThrows(AccountNotfoundException.class, () -> accountService.getAccount(1L));
        assertEquals("Account with id: 1 not found", exception.getMessage());

    }

    @Test
    public void testGetAccountsByOwnerId() {
        Mockito.when(accountRepository.findByOwnerId(1L)).thenReturn(Arrays.asList(account));

        List<Account> accounts = accountService.getAccountByOwnerId(1L);

        assertNotNull(accounts);
        assertFalse(accounts.isEmpty());
        assertEquals(1, accounts.size());
        assertEquals(1L, accounts.get(0).getOwnerId());
    }

    @Test
    public void testGetAccountsByOwnerIdNotFound() {
        Mockito.when(accountRepository.findByOwnerId(999L)).thenReturn(Arrays.asList());

        OwnerNotFoundException exception = assertThrows(OwnerNotFoundException.class, () -> accountService.getAccountByOwnerId(999L));
        assertEquals("Owner with id: 999 not found", exception.getMessage());
    }

    @Test
    public void testUpdateAccount() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        account.setType("Current");
        account.setBalance(10000);

        Account updatedAccount = accountService.updateAccount(1L, account);

        assertNotNull(updatedAccount);
        assertEquals("Current", updatedAccount.getType());
        assertEquals(10000, updatedAccount.getBalance());
    }

    @Test
    public void testDeleteAccount() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.delete(1L);

        Mockito.verify(accountRepository, Mockito.times(1)).delete(Mockito.any(Account.class));
    }

    @Test
    public void testDeleteAccountsUsingOwnerId() {
        Mockito.when(accountRepository.findByOwnerId(1L)).thenReturn(Arrays.asList(account));
        accountService.deleteAccountsUsingOwnerId(1L);

        Mockito.verify(accountRepository, Mockito.times(1)).delete(Mockito.any(Account.class));
    }

    @Test
    public void testEsPosiblePrestamo() {
        Account account2 = new Account();
        account2.setId(1L);
        account2.setBalance(2000);
        Mockito.when(accountRepository.findByOwnerId(1L)).thenReturn(Arrays.asList(account, account2));

        boolean isPossible = accountService.esPosiblePrestamo(1L, 4000.0);

        assertTrue(isPossible);
    }

    @Test
    public void testNoEsPosiblePrestamo() {
        Account account2 = new Account();
        account2.setId(1L);
        account2.setBalance(2000);
        Mockito.when(accountRepository.findByOwnerId(1L)).thenReturn(Arrays.asList(account, account2));

        boolean isPossible = accountService.esPosiblePrestamo(1L, 12000.0);

        assertFalse(isPossible);
    }

    @Test
    public void testWithdrawBalance() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        int withdrawAmount = 1000;
        Account updatedAccount = accountService.withdrawBalance(1L, withdrawAmount, 1L);

        assertNotNull(updatedAccount);
        assertEquals(4000, updatedAccount.getBalance());
    }

    @Test
    public void testWithdrawBalanceAccountNotFound() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        AccountNotfoundException exception = assertThrows(AccountNotfoundException.class, () -> { accountService.withdrawBalance(1L, 1000, 1L);});
        assertEquals("Account with id: 1 not found", exception.getMessage());
    }
    @Test
    public void testAddBalance() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        int addAmount = 2000;
        Account updatedAccount = accountService.addBalance(1L, addAmount, 1L);

        assertNotNull(updatedAccount);
        assertEquals(7000, updatedAccount.getBalance());
        Mockito.verify(accountRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(accountRepository, Mockito.times(1)).save(updatedAccount);
    }

    @Test
    public void testAddBalanceAccountNotFound() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        AccountNotfoundException exception = assertThrows(AccountNotfoundException.class, () -> {
            accountService.addBalance(1L, 1000, 1L);
        });
        assertEquals("Account with id: 1 not found", exception.getMessage());
    }
}
