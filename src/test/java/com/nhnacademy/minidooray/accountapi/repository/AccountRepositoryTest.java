package com.nhnacademy.minidooray.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidooray.accountapi.domain.AccountDto;
import com.nhnacademy.minidooray.accountapi.entity.Account;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

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
    void testFindByLoginId() {
        Optional<AccountDto> findAccount = accountRepository.findByLoginId("user", "1234");
        assertThat(findAccount).isPresent();
        assertThat(findAccount.get().getAccountState()).isEqualTo("회원");
    }

    @Test
    void testFindAllBy() {
        List<AccountDto> accounts = accountRepository.findAllBy();
        assertThat(accounts).isNotNull();
        assertThat(accounts.get(0).getId()).isEqualTo("user");
    }

    @Test
    void testFindAccountById() {
        Optional<AccountDto> findAccount = accountRepository.findAccountById("user");
        assertThat(findAccount).isPresent();
        assertThat(findAccount.get().getPassword()).isEqualTo("1234");
    }
}