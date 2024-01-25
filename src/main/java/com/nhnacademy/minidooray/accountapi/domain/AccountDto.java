package com.nhnacademy.minidooray.accountapi.domain;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String id;

    private String password;

    private String name;

    private String email;

    private LocalDate latestLoginDate;

    private LocalDate createdDate;

    private String accountState;

}
