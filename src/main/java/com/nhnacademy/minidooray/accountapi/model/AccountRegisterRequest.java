package com.nhnacademy.minidooray.accountapi.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountRegisterRequest {
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDate latestLoginDate;
    private LocalDate createdDate;
    private String accountState;
}
