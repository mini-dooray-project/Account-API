package com.nhnacademy.minidooray.accountapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.accountapi.model.AccountLoginRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountModifyRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountRegisterRequest;
import com.nhnacademy.minidooray.accountapi.service.AccountService;
import java.time.LocalDate;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AccountService accountService;

    @BeforeEach
    void setUp(){
        AccountRegisterRequest accountRequest = new AccountRegisterRequest(
                "user", "1234", "유저", "user@gmail.com");
        accountService.createAccount(accountRequest);
    }

    @Test
    @Order(1)
    void testMatchResponse() throws Exception {
        AccountLoginRequest accountRequest = new AccountLoginRequest("user", "1234");
        mockMvc.perform(post("/api/accounts/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("회원"));
    }

    @Test
    @Order(2)
    void testMatchResponseException() throws Exception {
        AccountLoginRequest accountRequest = new AccountLoginRequest(null, "1234567");
        mockMvc.perform(post("/api/accounts/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(
                        "ObjectName=accountLoginRequest,Message=must not be blank,code=NotBlank"));
    }

    @Test
    @Order(3)
    void testGetAccounts() throws Exception {
        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", equalTo("유저")));
    }

    @Test
    @Order(4)
    void testGetAccountsException() throws Exception {
        accountService.deleteAccount("user");
        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("accounts not found"));
    }

    @Test
    @Order(5)
    void testGetAccount() throws Exception {
        mockMvc.perform(get("/api/accounts/{id}", "user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("유저")));
    }

    @Test
    @Order(6)
    void testGetAccountException() throws Exception {
        mockMvc.perform(get("/api/accounts/{id}", "user1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("account not found"));

    }

    @Test
    @Order(7)
    void testCreateAccount() throws Exception {
        AccountRegisterRequest accountRequest = new AccountRegisterRequest(
                "user1", "1234", "유저1", "user1@gmail.com");
        mockMvc.perform(post("/api/accounts")
                        .content(objectMapper.writeValueAsString(accountRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertThat(accountService.getAccount("user1").getName())
                .isEqualTo("유저1");
    }

    @Test
    @Order(8)
    void testCreateAccountException() throws Exception {
        AccountRegisterRequest accountRequest = new AccountRegisterRequest(
                null, "1234", "유저", "user@gmail.com");
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(
                        "ObjectName=accountRegisterRequest,Message=must not be blank,code=NotBlank"));
    }

    @Test
    @Order(9)
    void testModifyAccount() throws Exception {
        AccountModifyRequest accountRequest = new AccountModifyRequest(
                "user", "12345", "유저", "user@gmail.com", LocalDate.of(2024,1,27), "회원");
        mockMvc.perform(put("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isOk());
        assertThat(accountService.getAccount("user").getPassword())
                .isEqualTo("12345");
    }

    @Test
    @Order(10)
    void testModifyAccountException() throws Exception {
        AccountModifyRequest accountRequest = new AccountModifyRequest(
                null, "12345", "유저", "user@gmail.com", LocalDate.of(2024,1,27), "회원");
        mockMvc.perform(put("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(
                        "ObjectName=accountModifyRequest,Message=must not be blank,code=NotBlank"));
    }

    @Test
    @Order(11)
    void testDeleteResponse() throws Exception {
        mockMvc.perform(delete("/api/accounts/{id}", "user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("user")))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"result\":\"OK\"}"));
    }
}