package com.tabisketch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.bean.form.CreateGooglePlaceForm;
import com.tabisketch.bean.response.CreateGooglePlaceResponse;
import com.tabisketch.service.ICreateGooglePlaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CreateGooglePlaceAPIController.class)
public class CreateGooglePlaceAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ICreateGooglePlaceService createGooglePlaceService;

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final int googlePlaceId = 1;
        when(this.createGooglePlaceService.execute(any())).thenReturn(googlePlaceId);

        final var createGooglePlaceForm = new CreateGooglePlaceForm("placeId", "name", 0,0);
        final String responseJson = this.objectMapper.writeValueAsString(CreateGooglePlaceResponse.success(googlePlaceId));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/create-google-place")
                        .flashAttr("createGooglePlaceForm", createGooglePlaceForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }
}
