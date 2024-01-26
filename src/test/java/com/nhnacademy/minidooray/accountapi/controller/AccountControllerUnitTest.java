package com.nhnacademy.minidooray.accountapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.accountapi.domain.AccountRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountLoginRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountRegisterRequest;
import com.nhnacademy.minidooray.accountapi.service.AccountService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    AccountService accountService;

    @BeforeEach
    void setUp(){
        AccountRegisterRequest accountRequest = new AccountRegisterRequest(
                "user", "1234", "유저", "user@gmail.com");
        accountService.createAccount(accountRequest);
    }

    @Test
    void testMatchResponse() throws Exception {
        AccountLoginRequest accountRequest = new AccountLoginRequest("user", "1234");
        given(accountService.match(accountRequest)).willReturn("회원");

        mockMvc.perform(post("/api/accounts/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("회원"));
    }

    @Test
    void testMatchResponseException() throws Exception {
        AccountLoginRequest accountRequest = new AccountLoginRequest(null, "1234567");
        given(accountService.match(accountRequest)).willReturn(
                "\"ObjectName=accountLoginRequest,Message=must not be blank,code=NotBlank\"");

    mockMvc.perform(post("/api/accounts/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(
                        "ObjectName=accountLoginRequest,Message=must not be blank,code=NotBlank"));

    }

    @Test
    void testGetAccounts() throws Exception {
        given(accountService.getAccounts()).willReturn(List.of(new AccountRequest(
                "user", "1234", "유저", "user@gmail.com", null, LocalDate.now(), "회원" )));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", equalTo("유저")));
    }

    @Test
    void testGetAccount() throws Exception {
        given(accountService.getAccount("user")).willReturn(new AccountRequest(
                "user", "1234", "유저", "user@gmail.com", null, LocalDate.now(), "회원" ));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/{id}", "user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("유저")));
    }

    @Test
    void testCreateAccount() throws Exception {
//        AccountRegisterRequest accountRequest = new AccountRegisterRequest(
//                "user1", "1234", "유저1", "user1@gmail.com");
//        doNothing().when(accountService).createAccount(accountRequest);
//
//        mockMvc.perform(post("/api/accounts")
//                        .content(objectMapper.writeValueAsString(accountRequest))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//        assertThat(accountService.getAccount("user1").getName())
//                .isEqualTo("유저1");
    }

    @Test
    void modifyAccount() {
    }

    @Test
    void deleteResponse() {
    }
}