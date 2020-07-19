package com.gametown.account.domain.join;

import com.gametown.account.domain.Account;
import com.gametown.account.domain.AccountRepository;
import com.gametown.account.enc.SHA2Machine;
import com.gametown.exception.ErrorCode;
import com.gametown.exception.GameTownException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "accountTransactionManager")
@AllArgsConstructor
public class AccountJoinService {

    private final AccountRepository accountRepository;
    private final SHA2Machine sha2Machine;

    @Transactional(value = "accountTransactionManager")
    public String join(JoinFormDto joinForm) {
        boolean existsAccount = accountRepository.findByEmail(joinForm.getEmail())
                .isPresent();
        if(existsAccount) {
            throw new GameTownException(ErrorCode.ACCOUNT_ALREADY_EXISTS);
        }

        Account account = new Account();
        account.setEmail(joinForm.getEmail());
        account.setPassword(sha2Machine.getSHA256(joinForm.getPassword()));
        return accountRepository.save(account).getEmail();
    }
}
