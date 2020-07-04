package com.gametown.account.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private String userId;
    private String name;
    private String email;

    public static AccountDto from(Account account) {
        AccountDto dto = new AccountDto();
        dto.userId = account.getUserId();
        dto.name = account.getName();
        dto.email = account.getEmail();
        return dto;
    }
}
