package com.nhnacademy.minidooray.accountapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.nhnacademy.minidooray.accountapi.domain.AccountDto;
import com.nhnacademy.minidooray.accountapi.entity.Account;
import com.nhnacademy.minidooray.accountapi.repository.AccountRepository;
import com.nhnacademy.minidooray.accountapi.service.AccountService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    AccountService accountService;

    @Test
    void matchResponse() {

    }

//    @Test
//    void testGetAccounts() throws Exception{
//
//        given(accountService.getAccounts()).willReturn(List.of(new Account(
//                "user",
//                "12345",
//                "유저",
//                "user@gmail.com",
//                LocalDate.now(),
//                LocalDate.now(),
//                "회원") {
//        }));
//        mockMvc.perform(get("/api/accounts"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id", equalTo("user")));
//    }
//    @Test
//    void testGetAccounts() throws Exception {
//        given(accountRepository.findAll()).willReturn(List.of(
//                new AccountDto("testId", "1234", "테스트", "test@gmail.com", LocalDate.now(),
//                        LocalDate.now(), "회원")));
//
//        mockMvc.perform(get("/api/accounts"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].getName()", equalTo("테스트")));
//    }


    @Test
    void getAcount() {
    }

    @Test
    void createAccount() {
    }

    @Test
    void modifyAccount() {
    }

    @Test
    void deleteResponse() {
    }
}