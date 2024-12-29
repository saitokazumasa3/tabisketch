package com.tabisketch.service;

import com.tabisketch.bean.form.CreateGooglePlaceForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.mapper.IGooglePlaceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreateGooglePlaceServiceTest {
    @Autowired
    private ICreateGooglePlaceService createGooglePlaceService;
    @MockitoBean
    private IGooglePlaceMapper googlePlaceMapper;

    @Test
    public void testExecute() throws InsertFailedException {
        when(this.googlePlaceMapper.insert(any())).thenReturn(1);

        final var createGooglePlaceForm = CreateGooglePlaceForm.generate("", "", 0, 0);
        this.createGooglePlaceService.execute(createGooglePlaceForm);

        verify(this.googlePlaceMapper).insert(any());
    }
}
