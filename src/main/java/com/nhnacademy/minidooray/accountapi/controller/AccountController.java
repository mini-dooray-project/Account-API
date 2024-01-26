package com.nhnacademy.minidooray.accountapi.controller;

import com.nhnacademy.minidooray.accountapi.domain.AccountDto;
import com.nhnacademy.minidooray.accountapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.accountapi.model.AccountLoginRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountModifyRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountRegisterRequest;
import com.nhnacademy.minidooray.accountapi.model.DeleteResponse;
import com.nhnacademy.minidooray.accountapi.model.MatchResponse;
import com.nhnacademy.minidooray.accountapi.service.AccountService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/api/accounts/login")
    public MatchResponse matchResponse(@Valid @RequestBody AccountLoginRequest accountRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        return new MatchResponse(accountService.match(accountRequest));
    }

    @GetMapping("/api/accounts")
    public List<AccountDto> getAccounts(){
        return accountService.getAccounts();
    }

    @GetMapping("/api/accounts/{id}")
    public AccountDto getAccount(@PathVariable("id") String id){
        return accountService.getAccount(id);
    }

    @PostMapping("/api/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@Valid @RequestBody AccountRegisterRequest accountRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        accountService.createAccount(accountRequest);
    }

    @PutMapping("/api/accounts")
    @ResponseStatus(HttpStatus.OK)
    public void modifyAccount(@Valid @RequestBody AccountModifyRequest accountRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        accountService.modifyAccount(accountRequest);
    }

    @DeleteMapping("/api/accounts/{id}")
    public DeleteResponse deleteResponse(@PathVariable("id") String id){
        accountService.deleteAccount(id);
        return new DeleteResponse("OK");
    }

}
