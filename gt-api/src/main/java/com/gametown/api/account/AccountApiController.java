package com.gametown.api.account;

import com.gametown.account.service.AccountDto;
import com.gametown.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountApiController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<AccountDto> accounts() {
        return accountService.accounts();
    }
}
