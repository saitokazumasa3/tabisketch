package com.tabisketch.controller;

import com.tabisketch.bean.form.ResetPasswordForm;
import com.tabisketch.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@WebMvcTest(ResetPasswordController.class)
public class ResetPasswordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResetPasswordService resetPasswordService;

    @Test
    public void testGetResetPasswordRequestForm() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/reset-password"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("resetPasswordForm"))
                .andExpect(MockMvcResultMatchers.view().name("user/reset-password/request"));
    }

    @Test
    public void testPostResetPasswordRequest() throws Exception {
        final String email = "sample@example.com";
        final ResetPasswordForm form = new ResetPasswordForm();
        form.setEmail(email);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/reset-password")
                        .flashAttr("resetPasswordForm", form)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("message", "リセットリンクがメールで送信されました。"))
                .andExpect(MockMvcResultMatchers.view().name("user/reset-password/send"));
    }

    @Test
    public void testGetResetPasswordForm() throws Exception {
        final UUID token = UUID.randomUUID();

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/reset-password/" + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("resetPasswordForm"))
                .andExpect(MockMvcResultMatchers.model().attribute("token", token.toString()))
                .andExpect(MockMvcResultMatchers.view().name("user/reset-password/form"));
    }

    @Test
    public void testPostResetPassword() throws Exception {
        final UUID token = UUID.randomUUID();
        final ResetPasswordForm form = new ResetPasswordForm();
        form.setEmail("sample@example.com");
        form.setNewPassword("password");
        form.setConfirmPassword("password");

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/reset-password/" + token)
                        .flashAttr("resetPasswordForm", form)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }
}
