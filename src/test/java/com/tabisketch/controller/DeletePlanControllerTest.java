package com.tabisketch.controller;

import com.tabisketch.service.IDeletePlanService;
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

@WebMvcTest(DeletePlanController.class)
public class DeletePlanControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IDeletePlanService __; // DIで使用

    @Test
    @WithMockUser
    public void testDelete() throws Exception {
        final var uuid = UUID.randomUUID().toString();
        mockMvc.perform(MockMvcRequestBuilders
                    .delete("/share/" + uuid)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/plan/list"));
    }
}
