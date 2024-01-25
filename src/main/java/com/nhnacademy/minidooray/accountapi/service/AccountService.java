package com.nhnacademy.minidooray.accountapi.service;

import com.nhnacademy.minidooray.accountapi.entity.Account;
import com.nhnacademy.minidooray.accountapi.model.AccountModifyRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountRegisterRequest;
import java.util.List;

public interface AccountService {
    List<Account> getAccounts();

    Account getAccount(String id);

    Account createAccount(AccountRegisterRequest accountRequest);

    Account modifyAccount(AccountModifyRequest accountRequest);

    void deleteAccount(String id);
}
