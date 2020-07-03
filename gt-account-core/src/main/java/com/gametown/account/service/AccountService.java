package com.gametown.account.service;

import com.gametown.account.domain.Account;
import com.gametown.account.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> accounts() {
        return accountRepository.findAll();
    }
}
