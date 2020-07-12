package com.gametown.api.login;

import com.gametown.account.enc.AES256Machine;
import com.gametown.exception.ErrorCode;
import com.gametown.exception.GameTownException;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@AllArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final AES256Machine aes256Machine;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return LoginAccount.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String loginCode = webRequest.getHeader("login");
        if (loginCode == null) {
            throw new GameTownException(ErrorCode.LOGIN_REQUIRED);
        }

        return new LoginAccount(Long.parseLong(aes256Machine.aesDecode(loginCode)));
    }
}
