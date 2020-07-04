package com.gametown.account.domain.join;

import com.gametown.account.domain.Account;
import com.gametown.account.domain.AccountRepository;
import com.gametown.account.utils.SHA2;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "accountTransactionManager")
@AllArgsConstructor
public class AccountJoinService {

    private final AccountRepository accountRepository;

    @Transactional(value = "accountTransactionManager")
    public String join(JoinFormDto joinForm) {
        Account account = new Account();
        account.setUserId(joinForm.getUserId());
        account.setEmail(joinForm.getEmail());
        account.setName(joinForm.getName());
        account.setPassword(SHA2.getSHA256(joinForm.getPassword()));

        return accountRepository.save(account).getUserId();
    }

    @Transactional(value = "accountTransactionManager", readOnly = true)
    public long login(String userId, String password) {
        return accountRepository.findByUserId(userId)
                .map(Account::getAccountId)
                .orElseThrow(AccountNotFoundException::new);
    }
}
