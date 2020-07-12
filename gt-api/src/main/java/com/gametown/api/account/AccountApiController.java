package com.gametown.api.account;

import com.gametown.account.domain.join.AccountJoinService;
import com.gametown.account.domain.join.AccountLoginService;
import com.gametown.account.domain.join.JoinFormDto;
import com.gametown.exception.GameTownException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountApiController {

    private final AccountJoinService accountJoinService;
    private final AccountLoginService accountLoginService;

    @PostMapping("/join")
    public JoinView join(@RequestBody JoinFormDto joinForm) {
        String userId = accountJoinService.join(joinForm);
        return new JoinView(userId);
    }

    @PostMapping("/login")
    public LoginView login(@RequestBody LoginFormDto loginFormDto) {
        String sessionKey =
                accountLoginService.login(loginFormDto.getUserId(), loginFormDto.getPassword());
        LoginView loginView = new LoginView();
        loginView.setSessionKey(sessionKey);
        return loginView;
    }
}
