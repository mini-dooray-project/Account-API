package com.nhnacademy.minidooray.accountapi.service;

import com.nhnacademy.minidooray.accountapi.entity.Account;
import com.nhnacademy.minidooray.accountapi.repository.AccountRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultAccountService implements AccountService{

    private final AccountRepository accountRepository;

    public DefaultAccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(String id){
        return accountRepository.findById(id)
                .orElseThrow();
    }

    @Override
    @Transactional
    public Account createAccount(Account account) {
        boolean present = accountRepository.findById(account.getId()).isPresent();
        if(present){
            throw new IllegalStateException("already exist " + account.getId());
        }
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }
}
