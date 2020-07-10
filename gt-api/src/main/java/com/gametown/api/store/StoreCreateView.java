package com.gametown.api.store;

import com.gametown.account.domain.AccountDto;
import com.gametown.store.domain.StoreDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StoreCreateView {
    private final AccountDto masterAccountDto;
    private final StoreDto storeDto;
}
