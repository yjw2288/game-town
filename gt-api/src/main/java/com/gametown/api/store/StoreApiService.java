package com.gametown.api.store;

import com.gametown.account.domain.AccountDto;
import com.gametown.api.account.AccountView;
import com.gametown.store.domain.StoreDto;
import com.gametown.store.domain.StoreFormDto;
import com.gametown.store.domain.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StoreApiService {

    private final StoreService storeService;

    public StoreCreateView create(AccountDto loginAccount, StoreFormDto storeForm) {
        StoreDto storeDto =
                storeService.create(loginAccount.getAccountId(), storeForm);

        return StoreCreateView.builder()
                .hostAccount(AccountView.from(loginAccount))
                .store(StoreView.from(storeDto))
                .build();
    }
}
