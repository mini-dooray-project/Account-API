package com.nhnacademy.minidooray.accountapi.repository;

import com.nhnacademy.minidooray.accountapi.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findById(String id);
    void deleteById(String id);
}
