package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleGooglePlace;
import com.tabisketch.bean.form.ExampleUpdatePlaceForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.UpdateFailedException;
import com.tabisketch.mapper.IGooglePlaceMapper;
import com.tabisketch.mapper.IPlacesMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UpdatePlaceServiceTest {
    @Autowired
    private IUpdatePlaceService updatePlaceService;
    @MockitoBean
    private IGooglePlaceMapper googlePlaceMapper;
    @MockitoBean
    private IPlacesMapper placesMapper;

    @Test
    public void testExecuteWhenExistGooglePlace() throws UpdateFailedException, InsertFailedException {
        final var googlePlace = ExampleGooglePlace.generate();
        when(this.googlePlaceMapper.selectByPlaceId(anyString())).thenReturn(googlePlace);
        when(this.placesMapper.update(any())).thenReturn(1);

        final var updatePlaceForm = ExampleUpdatePlaceForm.generate();
        this.updatePlaceService.execute(updatePlaceForm);

        verify(this.googlePlaceMapper).selectByPlaceId(anyString());
        verify(this.placesMapper).update(any());
    }

    @Test
    public void testExecute() throws UpdateFailedException, InsertFailedException {
        when(this.googlePlaceMapper.selectByPlaceId(anyString())).thenReturn(null);
        when(this.googlePlaceMapper.insert(any())).thenReturn(1);
        when(this.placesMapper.update(any())).thenReturn(1);

        final var updatePlaceForm = ExampleUpdatePlaceForm.generate();
        this.updatePlaceService.execute(updatePlaceForm);

        verify(this.googlePlaceMapper).selectByPlaceId(anyString());
        verify(this.googlePlaceMapper).insert(any());
        verify(this.placesMapper).update(any());
    }
}
