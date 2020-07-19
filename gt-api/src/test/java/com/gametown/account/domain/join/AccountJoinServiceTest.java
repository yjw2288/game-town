package com.gametown.account.domain.join;

import com.gametown.account.domain.Account;
import com.gametown.account.domain.AccountRepository;
import com.gametown.account.enc.SHA2Machine;
import com.gametown.exception.GameTownException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("회원 가입 테스트")
public class AccountJoinServiceTest {
    @InjectMocks
    private AccountJoinService accountJoinService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private SHA2Machine sha2Machine;
    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

    private void initSha() {
        when(sha2Machine.getSHA256("password"))
                .thenReturn("password");
    }

    private JoinFormDto joinForm() {
        JoinFormDto joinForm = new JoinFormDto();
        joinForm.setEmail("email");
        joinForm.setName("name");
        joinForm.setPassword(sha2Machine.getSHA256("password"));
        return joinForm;
    }

    @Test
    @DisplayName("가입 신청을 승인하고, 회원 객체를 리턴한다. 비밀번호는 암호회된다.")
    public void join() {
        initSha();
        JoinFormDto joinForm = joinForm();

        Account savedAccount = new Account();
        savedAccount.setEmail("userId");
        when(accountRepository.save(accountArgumentCaptor.capture()))
                .thenReturn(savedAccount);

        String savedUserId = accountJoinService.join(joinForm);
        assertEquals(savedUserId, "userId");

        Account accountCaptor = accountArgumentCaptor.getValue();

        assertAll("accounts",
                () -> assertEquals(accountCaptor.getEmail(), joinForm.getEmail()),
                () -> assertEquals(accountCaptor.getName(), joinForm.getName()),
                () -> assertEquals(accountCaptor.getPassword(), sha2Machine.getSHA256("password"))
        );
    }

    @Test
    @DisplayName("비슷한 아이디로 가입신청이 들어온다면, 실패처리를 한다.")
    public void joinAlreadyJoined() {
        initSha();
        JoinFormDto joinForm = joinForm();
        when(accountRepository.findByEmail("userId"))
                .thenReturn(Optional.of(new Account()));

        joinForm.setEmail("userId");
        Assertions.assertThrows(GameTownException.class, () -> {
            accountJoinService.join(joinForm);
        });
    }
}