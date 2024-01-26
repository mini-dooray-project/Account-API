package com.nhnacademy.minidooray.accountapi.domain;

import java.time.LocalDate;

public interface AccountDto {
    String getId();

    String getPassword();

    String getName();

    String getEmail();

    LocalDate getLatestLoginDate();

    LocalDate getCreatedDate();

    String getAccountState();
}
