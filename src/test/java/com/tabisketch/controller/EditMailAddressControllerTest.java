package com.tabisketch.controller;

import com.tabisketch.bean.form.EditMailAddressForm;
import com.tabisketch.service.IEditMailAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(EditMailAddressController.class)
public class EditMailAddressControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IEditMailAddressService __; // DIで使用

    @Test
    @WithMockUser(username = "sample@example.com")
    public void testGet() throws Exception {
        final var editMailAddressForm = EditMailAddressForm.empty();
        editMailAddressForm.setCurrentMailAddress(currentUserMailAddress());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/edit/mail"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("editMailAddressForm"))
                .andExpect(MockMvcResultMatchers.model().attribute("editMailAddressForm", editMailAddressForm))
                .andExpect(MockMvcResultMatchers.view().name("user/edit/mail/index"));
    }

    @Test
    @WithMockUser(username = "sample@example.com", password = "$2a$10$G7Emd1ALL6ibttkgRZtBZeX6Qps6lgEGKq.njouwtiuE4uvjD2YMO")
    public void testPost() throws Exception {
        final var editMailAddressForm = new EditMailAddressForm(
                currentUserMailAddress(),
                "sample2@example.com",
                "password"
        );

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/edit/mail")
                        .flashAttr("editMailAddressForm", editMailAddressForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/edit/mail/send"));
    }

    @Test
    @WithMockUser
    public void testSend() throws Exception {
        final String mailAddress = "sample@example.com";
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/edit/mail/send")
                        .flashAttr("mailAddress", mailAddress)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/edit/mail/send"));
    }

    private static String currentUserMailAddress() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
