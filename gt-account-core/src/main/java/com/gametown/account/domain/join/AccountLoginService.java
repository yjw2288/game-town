package com.gametown.account.domain.join;

import com.gametown.account.domain.AccountRepository;
import com.gametown.account.enc.AES256Machine;
import com.gametown.account.enc.SHA2Machine;
import com.gametown.exception.ErrorCode;
import com.gametown.exception.GameTownException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(value = "accountTransactionManager", readOnly = true)
public class AccountLoginService {
    private final AccountRepository accountRepository;
    private final SHA2Machine sha2Machine;
    private final AES256Machine aes256Machine;

    @Transactional(value = "accountTransactionManager", readOnly = true)
    public String login(String email, String password) {
        return accountRepository.findByEmail(email)
                .filter(account -> account.getPassword().equals(sha2Machine.getSHA256(password)))
                .map(account -> aes256Machine.aesEncode(account.getAccountId()))
                .orElseThrow(() -> new GameTownException(ErrorCode.ACCOUNT_NOT_FOUND));
    }
}
