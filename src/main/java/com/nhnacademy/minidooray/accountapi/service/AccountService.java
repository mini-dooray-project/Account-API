package com.nhnacademy.minidooray.accountapi.service;

import com.nhnacademy.minidooray.accountapi.entity.Account;
import java.util.List;

public interface AccountService {
    List<Account> getAccounts();

    Account getAccount(String id);

    Account createAccount(Account account);

    void deleteAccount(String id);
}
