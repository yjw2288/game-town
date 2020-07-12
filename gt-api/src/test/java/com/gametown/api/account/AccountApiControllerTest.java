package com.gametown.api.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gametown.account.domain.join.AccountJoinService;
import com.gametown.account.domain.join.JoinFormDto;
import com.gametown.api.exception.ErrorController;
import com.gametown.exception.ErrorCode;
import com.gametown.exception.GameTownException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountApiControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private AccountApiController accountApiController;

    private ObjectMapper objectMapper;

    @Mock
    private AccountJoinService accountJoinService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(accountApiController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .setControllerAdvice(new ErrorController())
                .alwaysDo(print())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testJoin() throws Exception {
        when(accountJoinService.join(any()))
                .thenReturn("한글");

        JoinFormDto joinForm = new JoinFormDto();
        joinForm.setUserId("abc");
        joinForm.setName("abc");
        joinForm.setEmail("email");
        joinForm.setPassword("password");

        mockMvc.perform(
                post("/accounts/join")
                        .content(objectMapper.writeValueAsString(joinForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userId", is(equalTo("한글"))));
    }

    @Test
    public void testJoin_이미_존재_하는_회원() throws Exception {
        when(accountJoinService.join(any()))
                .thenThrow(new GameTownException(ErrorCode.ACCOUNT_ALREADY_EXISTS));

        JoinFormDto joinForm = new JoinFormDto();
        joinForm.setUserId("abc");
        joinForm.setName("abc");
        joinForm.setEmail("email");
        joinForm.setPassword("password");

        mockMvc.perform(
                post("/accounts/join")
                        .content(objectMapper.writeValueAsString(joinForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", is(equalTo(ErrorCode.ACCOUNT_ALREADY_EXISTS.name()))))
                .andExpect(jsonPath("$.message", is(equalTo(ErrorCode.ACCOUNT_ALREADY_EXISTS.message))));
    }
}