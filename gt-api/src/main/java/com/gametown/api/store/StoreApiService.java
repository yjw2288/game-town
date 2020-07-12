package com.gametown.api.store;

import com.gametown.account.domain.AccountDto;
import com.gametown.account.domain.AccountService;
import com.gametown.api.login.LoginAccount;
import com.gametown.exception.ErrorCode;
import com.gametown.exception.GameTownException;
import com.gametown.store.domain.StoreFormDto;
import com.gametown.store.domain.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreApiService {

    private final StoreService storeService;
    private final AccountService accountService;

    public StoreCreateView create(LoginAccount loginAccount, StoreFormDto storeForm) {
        Optional<AccountDto> masterAccountDtoOp = accountService.findById(loginAccount.getAccountId());
        return masterAccountDtoOp
                .map(masterAccountDto -> StoreCreateView.builder()
                        .masterAccountDto(masterAccountDto)
                        .storeDto(storeService.create(loginAccount.getAccountId(), storeForm))
                        .build()).orElseThrow(() -> {
                            throw new GameTownException(ErrorCode.ACCOUNT_NOT_FOUND);
                        }
                );
    }
}
