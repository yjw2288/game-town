package com.gametown.api.store;

import com.gametown.account.domain.AccountDto;
import com.gametown.account.domain.AccountService;
import com.gametown.api.login.LoginAccount;
import com.gametown.store.domain.StoreDto;
import com.gametown.store.domain.StoreForm;
import com.gametown.store.domain.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StoreApiService {

    private final StoreService storeService;
    private final AccountService accountService;

    public StoreCreateView create(LoginAccount loginAccount, StoreForm storeForm) {
        AccountDto masterAccountDto = accountService.findById(loginAccount.getAccountId());
        StoreDto storeDto = storeService.create(loginAccount.getAccountId(), storeForm);

        return StoreCreateView.builder()
                .masterAccountDto(masterAccountDto)
                .storeDto(storeDto)
                .build();
    }
}
