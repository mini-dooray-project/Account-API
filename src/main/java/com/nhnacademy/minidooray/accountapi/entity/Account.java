package com.nhnacademy.minidooray.accountapi.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "Account")
public class Account {

    @Id
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

    @NonNull
    @Column(name = "latest_login_date")
    private LocalDate latestLoginDate;

    @NonNull
    @Column(name = "created_date")
    private LocalDate createdDate;

    @NotBlank
    @Size(max = 10)
    @Column(name = "account_state")
    private String accountState;
}
