package com.gametown.api.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gametown.account.domain.join.AccountJoinService;
import com.gametown.account.domain.join.AccountLoginService;
import com.gametown.account.domain.join.JoinFormDto;
import com.gametown.api.exception.ErrorController;
import com.gametown.exception.ErrorCode;
import com.gametown.exception.GameTownException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.gametown.ApiDocumentUtils.getDocumentRequest;
import static com.gametown.ApiDocumentUtils.getDocumentResponse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AccountApiControllerTest {
    private MockMvc mockMvc;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @InjectMocks
    private AccountApiController accountApiController;

    @Mock
    private AccountJoinService accountJoinService;

    @Mock
    private AccountLoginService accountLoginService;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountApiController)
                .apply(documentationConfiguration(restDocumentation))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .setControllerAdvice(new ErrorController())
                .alwaysDo(print())
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testJoin() throws Exception {
        when(accountJoinService.join(any()))
                .thenReturn("한글");

        JoinFormDto joinForm = new JoinFormDto();
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
                .andExpect(jsonPath("$.email", is(equalTo("한글"))))
                .andDo(document("account-join", getDocumentRequest(), getDocumentResponse(),
                        requestFields(
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        ),
                        responseFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("로그인 이메일")
                        )));
    }

    @Test
    public void testJoinAlreadyExistAccount() throws Exception {
        when(accountJoinService.join(any()))
                .thenThrow(new GameTownException(ErrorCode.ACCOUNT_ALREADY_EXISTS));

        JoinFormDto joinForm = new JoinFormDto();
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
                .andExpect(jsonPath("$.message", is(equalTo(ErrorCode.ACCOUNT_ALREADY_EXISTS.message))))
                .andDo(document("account-join-fail-duplicate-account", getDocumentRequest(), getDocumentResponse(),
                        requestFields(
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("실패 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("실패 메시지")
                        )));
    }

    @Test
    public void testLogin() throws Exception {
        LoginFormDto loginForm = new LoginFormDto();
        loginForm.setEmail("email");
        loginForm.setPassword("password");

        when(accountLoginService.login(loginForm.getEmail(), loginForm.getPassword()))
                .thenReturn("abcd");

        mockMvc.perform(
                post("/accounts/login")
                        .content(objectMapper.writeValueAsString(loginForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.loginToken", is(equalTo("abcd"))))
                .andDo(document("account-login", getDocumentRequest(), getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("loginToken").type(JsonFieldType.STRING).description("로그인 세션 키")
                        )));
    }

    @Test
    public void testLoginNotExists() throws Exception {
        LoginFormDto loginForm = new LoginFormDto();
        loginForm.setEmail("email");
        loginForm.setPassword("password");

        when(accountLoginService.login(loginForm.getEmail(), loginForm.getPassword()))
                .thenThrow(new GameTownException(ErrorCode.ACCOUNT_NOT_FOUND));

        mockMvc.perform(
                post("/accounts/login")
                        .content(objectMapper.writeValueAsString(loginForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", is(equalTo(ErrorCode.ACCOUNT_NOT_FOUND.name()))))
                .andExpect(jsonPath("$.message", is(equalTo(ErrorCode.ACCOUNT_NOT_FOUND.message))))
                .andDo(document("account-login-fail-not-exist-account", getDocumentRequest(), getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("로그인 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("실패 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("실패 메시지")
                        )));
    }
}