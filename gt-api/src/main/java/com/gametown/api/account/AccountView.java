package com.gametown.api.account;

import com.gametown.account.domain.AccountDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountView {
    private String email;

    public static AccountView from(AccountDto account) {
        AccountView view = new AccountView();
        view.email = account.getEmail();
        return view;
    }
}
