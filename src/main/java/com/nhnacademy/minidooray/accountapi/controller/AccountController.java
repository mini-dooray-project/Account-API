package com.nhnacademy.minidooray.accountapi.controller;

import com.nhnacademy.minidooray.accountapi.entity.Account;
import com.nhnacademy.minidooray.accountapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.accountapi.model.AccountRegisterRequest;
import com.nhnacademy.minidooray.accountapi.model.DeleteResponse;
import com.nhnacademy.minidooray.accountapi.service.AccountService;
import java.util.List;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/api/accounts")
    public List<Account> getAccounts(){
        return accountService.getAccounts();
    }

    @GetMapping("/api/accounts/{id}")
    public Account getAcount(@PathVariable("id") String id){
        return accountService.getAccount(id);
    }

    @PostMapping("/api/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@Valid @RequestBody AccountRegisterRequest accountRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        Account account = accountService.createAccount(accountRequest);
        return account;
    }

    @DeleteMapping("/api/accounts/{id}")
    public DeleteResponse deleteResponse(@PathVariable("id") String id){
        accountService.deleteAccount(id);
        return new DeleteResponse("OK");
    }

}
