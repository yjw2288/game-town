package com.gametown.web.account;

import com.gametown.account.service.AccountDto;
import com.gametown.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/accounts")
@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ResponseBody
    @GetMapping
    public List<AccountDto> acccounts(){
        return accountService.accounts();
    }
}
