package com.microcompany.accountsservice.persistencia;

import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.persistence.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JPAAccountRepositoryMockTest {

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void testFindByOwnerId() {
        Long ownerId = 1L;
        Account account1 = new Account(1L, "Savings", new Date(), 1000, ownerId, null);
        Account account2 = new Account(2L, "Checking", new Date(), 1500, ownerId, null);

        when(accountRepository.findByOwnerId(ownerId)).thenReturn(Arrays.asList(account1, account2));
        List<Account> accounts = accountRepository.findByOwnerId(ownerId);
        verify(accountRepository).findByOwnerId(ownerId);
        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        assertEquals(ownerId, accounts.get(0).getOwnerId());
    }

    @Test
    public void testFindByOwnerId_NoAccounts() {
        Long ownerId = 1L;

        when(accountRepository.findByOwnerId(ownerId)).thenReturn(Arrays.asList());

        List<Account> accounts = accountRepository.findByOwnerId(ownerId);

        verify(accountRepository).findByOwnerId(ownerId);

        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
    }

    @Test
    public void testFindById() {
        Long accountId = 1L;
        Account account = new Account(accountId, "Savings", new Date(), 1000, 1L, null);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Optional<Account> foundAccount = accountRepository.findById(accountId);

        verify(accountRepository).findById(accountId);

        assertTrue(foundAccount.isPresent());
        assertEquals(accountId, foundAccount.get().getId());
    }

    @Test
    public void testFindById_NotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        Optional<Account> foundAccount = accountRepository.findById(accountId);

        verify(accountRepository).findById(accountId);

        assertFalse(foundAccount.isPresent());
    }

}
