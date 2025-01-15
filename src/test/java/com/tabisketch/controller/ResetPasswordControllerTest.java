package com.tabisketch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ResetPasswordController.class)
public class ResetPasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResetPasswordService resetPasswordService;

    @Test
    void パスワードリセットメール送信に成功するとリダイレクトされる() throws Exception {
        String email = "user@example.com";

        mockMvc.perform(post("/password-reset/send")
                        .param("email", email))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/password-reset/send"));

        verify(resetPasswordService).sendResetPasswordEmail(email);
    }

    @Test
    @WithMockUser(username = "sample@example.com", password = "$2a$10$G7Emd1ALL6ibttkgRZtBZeX6Qps6lgEGKq.njouwtiuE4uvjD2YMO")
    void パスワードフォーム送信時にリダイレクトされる() throws Exception {
        final var resetPasswordForm = new ResetPasswordForm(
                currentUserMailAddress(),
                "password",
                "password2"
        );

        mockMvc.perform(post("/user/edit/password")
                        .flashAttr("resetPasswordForm", resetPasswordForm))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/edit/password/confirm"));
    }

    private static String currentUserMailAddress() {
        return "sample@example.com";
    }
}
