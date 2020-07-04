package com.gametown.account.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(value = "accountTransactionManager")
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Transactional(value = "accountTransactionManager", readOnly = true)
    public List<AccountDto> accounts() {
        return accountRepository.findAll()
                .stream().map(AccountDto::from)
                .collect(Collectors.toList());
    }
}
