package com.nhnacademy.minidooray.accountapi.repository;

import com.nhnacademy.minidooray.accountapi.domain.AccountDto;
import com.nhnacademy.minidooray.accountapi.entity.Account;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("select a from Account a where a.id = ?1 and a.password = ?2")
    Optional<AccountDto> findByLoginId(String id, String password);

    List<AccountDto> findAllBy();

    Optional<AccountDto> findAccountById(String id);
}
