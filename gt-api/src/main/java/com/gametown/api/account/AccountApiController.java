package com.gametown.api.account;

import com.gametown.account.domain.join.AccountJoinService;
import com.gametown.account.domain.join.AccountLoginService;
import com.gametown.account.domain.join.JoinFormDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountApiController {

    private final AccountJoinService accountJoinService;
    private final AccountLoginService accountLoginService;

    @PostMapping("/join")
    public JoinView join(@RequestBody JoinFormDto joinForm) {
        String email = accountJoinService.join(joinForm);
        return new JoinView(email);
    }

    @PostMapping("/login")
    public LoginView login(@RequestBody LoginFormDto loginFormDto) {
        String sessionKey =
                accountLoginService.login(loginFormDto.getEmail(), loginFormDto.getPassword());
        LoginView loginView = new LoginView();
        loginView.setLoginToken(sessionKey);
        return loginView;
    }
}
