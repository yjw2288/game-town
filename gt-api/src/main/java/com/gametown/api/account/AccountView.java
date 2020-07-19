package com.gametown.api.account;

import com.gametown.account.domain.AccountDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountView {
    private String name;
    private String email;

    public static AccountView from(AccountDto account) {
        AccountView view = new AccountView();
        view.name = account.getName();
        view.email = account.getEmail();
        return view;
    }
}
