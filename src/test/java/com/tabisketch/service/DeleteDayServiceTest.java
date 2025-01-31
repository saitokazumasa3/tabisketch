package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleDay;
import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.mapper.IDaysMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeleteDayServiceTest {
    @MockitoBean
    private IDaysMapper daysMapper;
    @Autowired
    private IDeleteDayService deleteDayService;

    @Test
    public void testExecute() throws DeleteFailedException {
        when(this.daysMapper.deleteById(anyInt())).thenReturn(1);

        final var id = ExampleDay.generate().getId();
        this.deleteDayService.execute(id);

        verify(this.daysMapper).deleteById(anyInt());
    }
}
