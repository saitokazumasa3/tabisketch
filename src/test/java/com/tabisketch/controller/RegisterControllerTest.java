package com.tabisketch.controller;

import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.service.IRegisterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IRegisterService __; // DIで使用している

    @Test
    @WithMockUser
    public void testGet() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register/index"));
    }

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final var registerForm = new RegisterForm(
                "sample@example.com",
                "password",
                "password"
        );

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .flashAttr("registerForm", registerForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register/send"));
    }

    @ParameterizedTest
    @MethodSource("validationTestData")
    @WithMockUser
    public void testValidation(final RegisterForm registerForm) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .flashAttr("registerForm", registerForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.model().attributeExists("registerForm"))
                .andExpect(MockMvcResultMatchers.model().attribute("registerForm", registerForm))
                .andExpect(MockMvcResultMatchers.view().name("register/index"));
    }

    private static Stream<RegisterForm> validationTestData() {
        // mailAddressが未入力
        final var registerForm1 = new RegisterForm("", "password", "password");
        // passwordが未入力
        final var registerForm2 = new RegisterForm("sample@example.com", "", "password");
        // rePasswordが未入力
        final var registerForm3 = new RegisterForm("sample@example.com", "password", "");
        // passwordとrePasswordが一致しない
        final var registerForm4 = new RegisterForm("sample@example.com", "password", "pass");
        return Stream.of(registerForm1, registerForm2, registerForm3, registerForm4);
    }

    @Test
    @WithMockUser
    public void testSend() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/register/send"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register/send"));
    }
}
