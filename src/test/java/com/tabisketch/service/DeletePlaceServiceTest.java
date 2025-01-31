package com.tabisketch.service;

import com.tabisketch.bean.entity.ExamplePlace;
import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.mapper.IPlacesMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeletePlaceServiceTest {
    @MockitoBean
    private IPlacesMapper placesMapper;
    @Autowired
    private IDeletePlaceService deletePlaceService;

    @Test
    public void testExecute() throws DeleteFailedException {
        when(this.placesMapper.deleteById(anyInt())).thenReturn(1);

        final int id = ExamplePlace.generate().getId();
        this.deletePlaceService.execute(id);

        verify(this.placesMapper).deleteById(anyInt());
    }
}
