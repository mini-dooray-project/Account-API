package com.nhnacademy.minidooray.accountapi.service;

import com.nhnacademy.minidooray.accountapi.domain.AccountDto;
import com.nhnacademy.minidooray.accountapi.model.AccountLoginRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountModifyRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountRegisterRequest;
import java.util.List;

public interface AccountService {
    Boolean match(AccountLoginRequest accountRequest);

    List<AccountDto> getAccounts();

    AccountDto getAccount(String id);

    void createAccount(AccountRegisterRequest accountRequest);

    void modifyAccount(AccountModifyRequest accountRequest);

    void deleteAccount(String id);
}
