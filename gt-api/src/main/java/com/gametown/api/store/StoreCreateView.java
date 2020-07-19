package com.gametown.api.store;

import com.gametown.api.account.AccountView;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StoreCreateView {
    private final AccountView hostAccount;
    private final StoreView store;
}
