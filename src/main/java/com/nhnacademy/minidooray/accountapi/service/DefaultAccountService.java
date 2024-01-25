package com.nhnacademy.minidooray.accountapi.service;

import com.nhnacademy.minidooray.accountapi.entity.Account;
import com.nhnacademy.minidooray.accountapi.exception.AccountNotFoundException;
import com.nhnacademy.minidooray.accountapi.model.AccountModifyRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountRegisterRequest;
import com.nhnacademy.minidooray.accountapi.repository.AccountRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    public Account createAccount(AccountRegisterRequest accountRequest) {
        boolean present = accountRepository.findById(accountRequest.getId()).isPresent();
        if(present){
            throw new IllegalStateException("already exist " + accountRequest.getId());
        }
        Account account = new Account(accountRequest.getId()
                                    , accountRequest.getPassword()
                                    , accountRequest.getName()
                                    , accountRequest.getEmail()
                                    , null
                                    , LocalDate.now()
                                    , "회원");
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account modifyAccount(AccountModifyRequest accountRequest) {
        Optional<Account> result = accountRepository.findById(accountRequest.getId());
        if(result.isEmpty()){
            throw new IllegalStateException("not found " + accountRequest.getId());
        }
        Account account = result.get();
        account.setPassword(accountRequest.getPassword());
        account.setName(accountRequest.getName());
        account.setEmail(accountRequest.getEmail());
        account.setLatestLoginDate(accountRequest.getLatestLoginDate());
        account.setAccountState(accountRequest.getAccountState());
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccount(String id) {
        boolean present = accountRepository.findById(id).isPresent();
        if(!present){
            throw new IllegalStateException("already exist " + id);
        }
        accountRepository.deleteById(id);
    }
}
