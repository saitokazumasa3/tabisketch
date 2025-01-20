package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleGooglePlace;
import com.tabisketch.bean.form.ExampleCreatePlaceForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.mapper.IGooglePlaceMapper;
import com.tabisketch.mapper.IPlacesMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreatePlaceServiceTest {
    @Autowired
    private ICreatePlaceService createPlaceService;
    @MockitoBean
    private IPlacesMapper placesMapper;
    @MockitoBean
    private IGooglePlaceMapper googlePlaceMapper;

    @Test
    public void testExecuteWhenExistGooglePlace() throws InsertFailedException {
        final var googlePlace = ExampleGooglePlace.generate();
        when(this.googlePlaceMapper.selectByPlaceId(anyString())).thenReturn(googlePlace);
        when(this.placesMapper.insert(any())).thenReturn(1);

        final var createPlaceForm = ExampleCreatePlaceForm.generate();
        this.createPlaceService.execute(createPlaceForm);

        verify(this.googlePlaceMapper).selectByPlaceId(anyString());
        verify(this.placesMapper).insert(any());
    }

    @Test
    public void testExecute() throws InsertFailedException {
        when(this.googlePlaceMapper.selectByPlaceId(anyString())).thenReturn(null);
        when(this.googlePlaceMapper.insert(any())).thenReturn(1);
        when(this.placesMapper.insert(any())).thenReturn(1);

        final var createPlaceForm = ExampleCreatePlaceForm.generate();
        this.createPlaceService.execute(createPlaceForm);

        verify(this.googlePlaceMapper).selectByPlaceId(anyString());
        verify(this.googlePlaceMapper).insert(any());
        verify(this.placesMapper).insert(any());
    }
}
