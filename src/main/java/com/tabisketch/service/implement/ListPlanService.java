package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.service.IListPlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListPlanService implements IListPlanService {
    private final IPlansMapper plansMapper;

    public ListPlanService(final IPlansMapper plansMapper) {
        this.plansMapper = plansMapper;
    }

    @Override
    public List<Plan> execute(final String mailAddress) {
        return plansMapper.selectByMailAddress(mailAddress);
    }
}
