package com.gametown.api.account;

import com.gametown.account.domain.join.AccountJoinService;
import com.gametown.account.domain.join.AccountLoginService;
import com.gametown.account.domain.join.JoinFormDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountApiController {

    private final AccountJoinService accountJoinService;
    private final AccountLoginService accountLoginService;

    @PostMapping("/join")
    public JoinView join(@RequestBody JoinFormDto joinForm, HttpServletResponse response) {
        String sessionKey = accountJoinService.join(joinForm);

        Cookie cookie = new Cookie("loginToken", sessionKey);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new JoinView(sessionKey);
    }

    @PostMapping("/login")
    public LoginView login(@RequestBody LoginFormDto loginFormDto, HttpServletResponse response) {
        String sessionKey =
                accountLoginService.login(loginFormDto.getEmail(), loginFormDto.getPassword());
        LoginView loginView = new LoginView();
        loginView.setLoginToken(sessionKey);

        Cookie cookie = new Cookie("loginToken", sessionKey);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);

        return loginView;
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("loginToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "success";
    }
}
