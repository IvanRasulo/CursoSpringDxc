package com.microcompany.accountsservice.persistencia;

import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.persistence.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestEntityManager
@ComponentScan(basePackages = {"com.microcompany.productsservice"})
@Sql(value = "classpath:testing.sql")
public class JPAAccountsRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(JPAAccountsRepositoryTest.class);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository jpaAccountRepo;

    @Test
    public void testFindByOwnerId() {
        List<Account> accounts = jpaAccountRepo.findByOwnerId(1L);
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
        assertEquals(1L, accounts.get(0).getOwnerId());
        assertEquals("Savings", accounts.get(0).getType());
        assertEquals(5000, accounts.get(0).getBalance());
    }

    @Test
    public void testFindByOwnerId_MultipleAccounts() {
        List<Account> accounts = jpaAccountRepo.findByOwnerId(2L);
        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        assertEquals(2L, accounts.get(0).getOwnerId());
        assertEquals("Current", accounts.get(0).getType());
        assertEquals(12000, accounts.get(0).getBalance());
    }

    @Test
    public void testFindByOwnerId_NoAccounts() {
        List<Account> accounts = jpaAccountRepo.findByOwnerId(999L);
        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
    }

    @Test
    public void testFindById() {
        Account account = jpaAccountRepo.findById(1L).orElse(null);
        assertNotNull(account);
        assertEquals(1L, account.getId());
        assertEquals("Savings", account.getType());
        assertEquals(5000, account.getBalance());
        assertEquals(1L, account.getOwnerId());
    }

    @Test
    public void testFindById_NotFound() {
        Account account = jpaAccountRepo.findById(999L).orElse(null);
        assertNull(account);
    }

    @Test
    public void testAccountCount() {
        long count = jpaAccountRepo.count();
        assertEquals(5, count);
    }
}
