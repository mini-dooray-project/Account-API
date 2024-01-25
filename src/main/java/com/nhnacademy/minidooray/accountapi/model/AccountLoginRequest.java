package com.nhnacademy.minidooray.accountapi.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountLoginRequest {
    @NotBlank
    @Size(max = 20)
    private String id;

    @NotBlank
    @Size(max = 20)
    private String password;
}
