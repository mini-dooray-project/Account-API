package com.nhnacademy.minidooray.accountapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.nhnacademy.minidooray.accountapi.domain.AccountDto;
import com.nhnacademy.minidooray.accountapi.entity.Account;
import com.nhnacademy.minidooray.accountapi.exception.AccountNotFoundException;
import com.nhnacademy.minidooray.accountapi.model.AccountLoginRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountModifyRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountRegisterRequest;
import com.nhnacademy.minidooray.accountapi.repository.AccountRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {

    @Autowired
    AccountService accountService;

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
    @Transactional
    void testAccountService() {
        accountService.createAccount(new AccountRegisterRequest(
                "user1", "1234", "유저1", "user1@gmail.com"));
        List<AccountDto> accounts = accountService.getAccounts();
        assertThat(accounts).isNotEmpty();
        assertThat(accounts.get(1).getName()).isEqualTo(accountService.getAccount("user1").getName());

        accountService.modifyAccount(new AccountModifyRequest(
                "user1", "12345", "유저", "user1@gmail.com", LocalDate.of(2024,1,27),"회원"));
        assertThat(accountService.getAccount("user1").getPassword())
                .isEqualTo("12345");

        accountService.deleteAccount("user1");
        assertThat(accountRepository.findAccountById("user1")).isEmpty();
    }

    @Test
    @Transactional
    void testMatch() {
        String accountState = accountService.match(new AccountLoginRequest("user", "1234"));
        assertThat(accountState).isNotEmpty();
        assertThat(accountState).isEqualTo("회원");
    }

    @Test
    @Transactional
    void testMatchException() {
        assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> accountService.match(new AccountLoginRequest("user", "1111")))
                .withMessage("account not found");
    }

    @Test
    @Transactional
    void testGetAccounts() {
        List<AccountDto> accounts = accountService.getAccounts();
        assertThat(accounts).isNotEmpty();
        assertThat(accounts).hasSize(1);
    }

    @Test
    @Transactional
    void testGetAccountsException() {
        accountRepository.deleteById("user");
        assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> accountService.getAccounts())
                .withMessage("accounts not found");
    }

    @Test
    @Transactional
    void testGetAccount() {
        assertThat(accountService.getAccount("user").getName())
                .isNotNull()
                .isEqualTo("유저");
    }

    @Test
    @Transactional
    void testGetAccountException() {
        assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> accountService.getAccount("user2"))
                .withMessage("account not found");
    }

    @Test
    @Transactional
    void testCreateAccount() {
        accountService.createAccount(new AccountRegisterRequest(
                "user1", "1234", "유저1", "user1@gmail.com"));
        assertThat(accountRepository.findAccountById("user1"))
                .isPresent()
                .map(AccountDto::getName)
                .hasValue("유저1");
    }

    @Test
    @Transactional
    void testCreateAccountException() {
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> accountService.createAccount(new AccountRegisterRequest(
                                "user", "1234", "유저", "user@gmail.com")))
                .withMessage("account already exists: user");
    }

    @Test
    @Transactional
    void testModifyAccount() {
        accountService.modifyAccount(new AccountModifyRequest(
                "user", "12345", "유저", "user@gmail.com", LocalDate.of(2024,1,27),"회원"));
        assertThat(accountRepository.findAccountById("user"))
                .isPresent()
                .map(AccountDto::getPassword)
                .hasValue("12345");
    }

    @Test
    @Transactional
    void testModifyAccountException() {
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> accountService.modifyAccount(new AccountModifyRequest(
                        "user1", "12345", "유저", "user@gmail.com", LocalDate.of(2024,1,27),"회원")))
                .withMessage("account not found: user1");
    }

    @Test
    @Transactional
    void testDeleteAccount() {
        accountService.deleteAccount("user");
        assertThat(accountRepository.findAccountById("user")).isEmpty();
    }

    @Test
    @Transactional
    void testDeleteAccountException() {
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> accountService.deleteAccount("user1"))
                .withMessage("account not found: user1");
    }
}