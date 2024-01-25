package com.nhnacademy.minidooray.accountapi.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private String id;

    private String password;

    private String name;

    private String email;

    @Column(name = "latest_login_date")
    private LocalDate latestLoginDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "account_state")
    private String accountState;
}
