package com.nhnacademy.minidooray.accountapi.service;

import com.nhnacademy.minidooray.accountapi.entity.Account;
import com.nhnacademy.minidooray.accountapi.exception.AccountNotFoundException;
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
        List<Account> accounts = accountRepository.findAll();
        if(accounts.isEmpty()){
            throw new AccountNotFoundException("accounts not found");
        }
        return accounts;
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(String id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("account not found"));
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
        boolean present = accountRepository.findById(id).isPresent();
        if(present){
            throw new IllegalStateException("already exist " + id);
        }
        accountRepository.deleteById(id);
    }
}
