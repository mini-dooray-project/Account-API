package com.nhnacademy.minidooray.accountapi.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidooray.accountapi.repository.AccountRepository;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
public class AccountEntityTest {

    @Autowired
    AccountRepository accountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp(){
        Account account = new Account("user"
                , "1234"
                , "유저"
                , "user@gmail.com"
                , null
                , LocalDate.of(2024,1,26)
                , "회원");
        accountRepository.save(account);
    }

    @Test
    void testEqualsAndHashCode(){
        Account account = new Account("user"
                , "1234"
                , "유저"
                , "user@gmail.com"
                , null
                , LocalDate.of(2024,1,26)
                , "회원");
        Account account2 = entityManager.find(Account.class, "user");
        assertThat(account.hashCode()).isEqualTo(account2.hashCode());
    }

    @Test
    void testGetterSetter() {
        Account account = new Account();
        account.setId("user1");
        account.setPassword("1234");
        account.setName("유저1");
        account.setEmail("user1@gmail.com");
        account.setLatestLoginDate(LocalDate.of(2024,1,26));
        account.setCreatedDate(LocalDate.of(2024,1,26));
        account.setAccountState("회원");

        entityManager.persist(account);

        Account foundAccount = entityManager.find(Account.class, account.getId());

        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getId()).isEqualTo("user1");
        assertThat(foundAccount.getPassword()).isEqualTo("1234");
        assertThat(foundAccount.getName()).isEqualTo("유저1");
        assertThat(foundAccount.getEmail()).isEqualTo("user1@gmail.com");
        assertThat(foundAccount.getLatestLoginDate()).isEqualTo("2024-01-26");
        assertThat(foundAccount.getCreatedDate()).isEqualTo("2024-01-26");
        assertThat(foundAccount.getAccountState()).isEqualTo("회원");
    }

}