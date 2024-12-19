package com.tabisketch.controller;

import com.tabisketch.service.implement.AuthMailAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AuthMailAddressController.class)
public class AuthMailAddressControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AuthMailAddressService __; // DIで使用

    @Test
    @WithMockUser
    public void testGet() throws Exception {
        final String token = "a2e69add-9d95-4cf1-a59b-cedbb95dcd6b";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/mail/confirm/" + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("mail/confirm"));
    }
}
