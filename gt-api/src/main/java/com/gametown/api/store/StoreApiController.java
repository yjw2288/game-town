package com.gametown.api.store;

import com.gametown.api.login.LoginAccount;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/stores")
@AllArgsConstructor
public class StoreApiController {

    private final StoreApiService storeApiService;

    @PostMapping
    public StoreCreateView createStore(LoginAccount loginAccount, @RequestBody @Valid StoreForm storeForm) {
        return storeApiService.create(loginAccount, storeForm);
    }
}
