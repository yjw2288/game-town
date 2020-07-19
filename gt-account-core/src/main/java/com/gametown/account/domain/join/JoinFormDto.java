package com.gametown.account.domain.join;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinFormDto {
    private String password;
    private String name;
    private String email;
}
