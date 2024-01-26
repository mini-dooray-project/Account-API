package com.nhnacademy.minidooray.accountapi.model;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountModifyRequest {
    @NotBlank
    @Size(max = 20)
    private String id;

    @NotBlank
    @Size(max = 20)
    private String password;

    @NotBlank
    @Size(max = 10)
    private String name;

    @NotBlank
    @Size(max = 30)
    private String email;

    private LocalDate latestLoginDate;

    @Size(max = 10)
    private String accountState;
}
