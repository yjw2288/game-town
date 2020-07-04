package com.gametown.api.account;

import com.gametown.account.domain.join.AccountJoinService;
import com.gametown.account.domain.join.JoinFormDto;
import com.gametown.api.account.enc.AES256Machine;
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
    private final AES256Machine aes256Machine;

    @PostMapping("/join")
    public JoinView join(@RequestBody JoinFormDto joinForm) {
        String userId = accountJoinService.join(joinForm);
        return new JoinView(userId);
    }

    @PostMapping("/login")
    public LoginView login(@RequestBody LoginFormDto loginFormDto) {
        long loginId =
                accountJoinService.login(loginFormDto.getUserId(), loginFormDto.getPassword());
        LoginView loginView = new LoginView();
        loginView.setSessionKey(aes256Machine.aesEncode(loginId));
        return loginView;
    }
}
