package com.gametown.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gametown.account.domain.AccountService;
import com.gametown.account.enc.AES256Machine;
import com.gametown.api.exception.ErrorController;
import com.gametown.api.login.LoginArgumentResolver;
import com.gametown.api.store.StoreApiController;
import com.gametown.exception.ErrorCode;
import com.gametown.store.domain.StoreFormDto;
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
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class LoginArgumentTest {
    private MockMvc mockMvc;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @InjectMocks
    private LoginArgumentResolver loginArgumentResolver;

    @InjectMocks
    private StoreApiController storeApiController;

    @Mock
    private AES256Machine aes256Machine;
    @Mock
    private AccountService accountService;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(storeApiController)
                .apply(documentationConfiguration(restDocumentation))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .setControllerAdvice(new ErrorController())
                .setCustomArgumentResolvers(loginArgumentResolver)
                .alwaysDo(print())
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testNotLogin() throws Exception {
        StoreFormDto storeForm = new StoreFormDto();
        storeForm.setName("store name");
        storeForm.setStoreCode("store code");
        storeForm.setAddress("address");

        mockMvc.perform(
                post("/stores")
                        .content(objectMapper.writeValueAsString(storeForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", is(equalTo(ErrorCode.LOGIN_REQUIRED.name()))))
                .andExpect(jsonPath("$.message", is(equalTo(ErrorCode.LOGIN_REQUIRED.message))))
                .andDo(document("login-error-not-found-token", getDocumentRequest(), getDocumentResponse(),
                        requestFields(
                                fieldWithPath("storeCode").type(JsonFieldType.STRING).description("상점 코드"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("상점 이름"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("주소")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("오류 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("오류 메시지")
                        )));
    }

    @Test
    public void testNotFoundAccount() throws Exception {
        StoreFormDto storeForm = new StoreFormDto();
        storeForm.setName("store name");
        storeForm.setStoreCode("store code");
        storeForm.setAddress("address");

        when(aes256Machine.aesDecode("abcd")).thenReturn("1");

        mockMvc.perform(
                post("/stores")
                        .content(objectMapper.writeValueAsString(storeForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("login", "abcd")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", is(equalTo(ErrorCode.LOGIN_NOT_FOUND_ACCOUNT.name()))))
                .andExpect(jsonPath("$.message", is(equalTo(ErrorCode.LOGIN_NOT_FOUND_ACCOUNT.message))))
                .andDo(document("login-error-not-found-user", getDocumentRequest(), getDocumentResponse(),
                        requestFields(
                                fieldWithPath("storeCode").type(JsonFieldType.STRING).description("상점 코드"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("상점 이름"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("주소")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("오류 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("오류 메시지")
                        )));
    }
}
