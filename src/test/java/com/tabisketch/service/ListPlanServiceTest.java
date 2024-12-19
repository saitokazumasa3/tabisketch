package com.tabisketch.service;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.mapper.IPlansMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ListPlanServiceTest {
    @MockitoBean
    private IPlansMapper plansMapper;
    @Autowired
    private IListPlanService listPlanService;

    @Test
    public void testExecute() {
        when(this.plansMapper.selectByMailAddress(anyString())).thenReturn(new ArrayList<>());

        final String mailAddress = "sample@example.com";
        final List<Plan> planList = this.listPlanService.execute(mailAddress);

        verify(this.plansMapper).selectByMailAddress(anyString());
        assert planList != null;
    }
}
