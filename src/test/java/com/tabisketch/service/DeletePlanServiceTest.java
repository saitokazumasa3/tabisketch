package com.tabisketch.service;

import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.mapper.IDaysMapper;
import com.tabisketch.mapper.IPlacesMapper;
import com.tabisketch.mapper.IPlansMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeletePlanServiceTest {
    @MockitoBean
    private IPlansMapper plansMapper;
    @MockitoBean
    private IDaysMapper daysMapper;
    @MockitoBean
    private IPlacesMapper placesMapper;
    @Autowired
    private IDeletePlanService deletePlanService;

    @Test
    public void testExecute() throws DeleteFailedException {
        when(this.plansMapper.deleteByUUID(any())).thenReturn(1);

        final var uuid = UUID.randomUUID().toString();

        this.deletePlanService.execute(uuid);

        verify(this.placesMapper).deleteByPlanUUID(any());
        verify(this.daysMapper).deleteByPlanUUID(any());
        verify(this.plansMapper).deleteByUUID(any());
    }
}
