package com.gametown.api.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginFormDto {
    private String userId;
    private String password;
}
