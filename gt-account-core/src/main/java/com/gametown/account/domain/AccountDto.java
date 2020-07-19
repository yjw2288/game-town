package com.gametown.account.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private long accountId;
    private String name;
    private String email;

    public static AccountDto from(Account account) {
        AccountDto dto = new AccountDto();
        dto.accountId = account.getAccountId();
        dto.name = account.getName();
        dto.email = account.getEmail();
        return dto;
    }
}
