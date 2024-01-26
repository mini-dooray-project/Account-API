package com.nhnacademy.minidooray.accountapi.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountRequest implements AccountDto {
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDate latestLoginDate;
    private LocalDate createdDate;
    private String accountState;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public LocalDate getLatestLoginDate() {
        return latestLoginDate;
    }

    @Override
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    @Override
    public String getAccountState() {
        return accountState;
    }
}
