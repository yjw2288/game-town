package com.gametown.account.domain.join;

import com.gametown.account.domain.Account;
import com.gametown.account.domain.AccountRepository;
import com.gametown.account.enc.AES256Machine;
import com.gametown.account.enc.SHA2Machine;
import com.gametown.exception.GameTownException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("회원 로그인")
public class AccountLoginServiceTest {
    @InjectMocks
    private AccountLoginService accountLoginService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private SHA2Machine sha2Machine;
    @Mock
    private AES256Machine aes256Machine;

    @Test
    @DisplayName("로그인 성공")
    public void testLogin() {
        Account account = new Account();
        account.setAccountId(1L);
        account.setPassword("password");

        when(sha2Machine.getSHA256("password"))
                .thenReturn("password");
        when(aes256Machine.aesEncode(1L))
                .thenReturn("1");
        when(accountRepository.findByEmail("userId"))
                .thenReturn(Optional.of(account));

        String sessionKey = accountLoginService.login("userId", "password");
        assertEquals(sessionKey, "1");
    }

    @Nested
    @DisplayName("로그인 실패")
    class LoginFail {
        @Test
        @DisplayName("잘못된 비밀번호")
        public void testLoginWrongPassword() {
            Account account = new Account();
            account.setAccountId(1L);
            account.setPassword("password");

            when(sha2Machine.getSHA256("password"))
                    .thenReturn("password2");
            when(accountRepository.findByEmail("userId"))
                    .thenReturn(Optional.of(account));

            assertThrows(GameTownException.class, () -> {
                accountLoginService.login("userId", "password");
            });
        }

        @Test
        @DisplayName("존재하지 않는 사용자")
        public void testLoginNotExistUser() {
            Account account = new Account();
            account.setAccountId(1L);
            account.setPassword("password");

            when(accountRepository.findByEmail("userId"))
                    .thenReturn(Optional.empty());

            assertThrows(GameTownException.class, () -> {
                accountLoginService.login("userId", "password");
            });
        }
    }
}