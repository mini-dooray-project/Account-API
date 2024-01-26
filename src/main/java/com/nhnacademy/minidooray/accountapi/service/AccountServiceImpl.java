package com.nhnacademy.minidooray.accountapi.service;

import com.nhnacademy.minidooray.accountapi.domain.AccountDto;
import com.nhnacademy.minidooray.accountapi.entity.Account;
import com.nhnacademy.minidooray.accountapi.exception.AccountNotFoundException;
import com.nhnacademy.minidooray.accountapi.model.AccountLoginRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountModifyRequest;
import com.nhnacademy.minidooray.accountapi.model.AccountRegisterRequest;
import com.nhnacademy.minidooray.accountapi.repository.AccountRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public String match(AccountLoginRequest accountRequest){
        Optional<AccountDto> account = accountRepository.findByLoginId(accountRequest.getId(),accountRequest.getPassword());
        if(account.isEmpty()){
            throw new AccountNotFoundException("account not found");
        }
        return account.get().getAccountState();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAccounts() {
        List<AccountDto> accounts = accountRepository.findAllBy();
        if (accounts.isEmpty()) {
            throw new AccountNotFoundException("Accounts not found");
        }
        return accounts;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccount(String id) {
        return accountRepository.findAccountById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Override
    @Transactional
    public void createAccount(AccountRegisterRequest accountRequest) {
        boolean present = accountRepository.findById(accountRequest.getId()).isPresent();
        if (present) {
            throw new IllegalStateException("Account already exists: " + accountRequest.getId());
        }
        Account account = new Account(accountRequest.getId()
                , accountRequest.getPassword()
                , accountRequest.getName()
                , accountRequest.getEmail()
                , null
                , LocalDate.now()
                , "회원");
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void modifyAccount(AccountModifyRequest accountRequest) {
        Optional<AccountDto> resultOptional = accountRepository.findAccountById(accountRequest.getId());
        if (resultOptional.isPresent()) {
            AccountDto result = resultOptional.get();
            Account account = new Account();
            account.setId(result.getId());
            account.setPassword(accountRequest.getPassword());
            account.setName(accountRequest.getName());
            account.setEmail(accountRequest.getEmail());
            account.setLatestLoginDate(accountRequest.getLatestLoginDate());
            account.setCreatedDate(result.getCreatedDate());
            account.setAccountState(accountRequest.getAccountState());
            accountRepository.save(account);
        } else {
            throw new IllegalStateException("Account not found: " + accountRequest.getId());
        }
    }

    @Override
    @Transactional
    public void deleteAccount(String id) {
        boolean present = accountRepository.findById(id).isPresent();
        if (!present) {
            throw new IllegalStateException("Account not found: " + id);
        }
        accountRepository.deleteById(id);
    }

}
