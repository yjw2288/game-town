package com.gametown.api.store;

import com.gametown.account.domain.AccountDto;
import com.gametown.api.login.LoginAccount;
import com.gametown.store.domain.StoreFormDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
@AllArgsConstructor
public class StoreApiController {

    private final StoreApiService storeApiService;

    @PostMapping
    public StoreCreateView createStore(@LoginAccount AccountDto loginAccount, @RequestBody StoreFormDto storeForm) {
        return storeApiService.create(loginAccount, storeForm);
    }
}
