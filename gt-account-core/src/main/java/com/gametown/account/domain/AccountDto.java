package com.gametown.account.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private long accountId;
    private String email;

    public static AccountDto from(Account account) {
        AccountDto dto = new AccountDto();
        dto.accountId = account.getAccountId();
        dto.email = account.getEmail();
        return dto;
    }
}
