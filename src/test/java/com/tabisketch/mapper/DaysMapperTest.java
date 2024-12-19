package com.tabisketch.mapper;

import com.tabisketch.bean.entity.Day;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;
import java.util.stream.Stream;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaysMapperTest {
    @Autowired
    private IDaysMapper daysMapper;

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePlan.sql",
    })
    public void testInsert() {
        final var day = Day.generate(
                -1,
                1,
                1,
                "0000"
        );
        assert this.daysMapper.insert(day) == 1;
        assert day.getId() != -1;
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePlan.sql",
            "classpath:/sql/CreateDay.sql",
    })
    public void testSelect() {
        final int planId = 1;
        final var dayList = this.daysMapper.selectByPlanId(planId);
        assert dayList != null;
        assert !dayList.isEmpty();
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePlan.sql",
            "classpath:/sql/CreateDay.sql",
    })
    public void testDelete() {
        final var uuid = UUID.fromString("611d4008-4c0d-4b45-bd1b-21c97e7df3b2");
        assert this.daysMapper.deleteByPlanUUID(uuid) == 1;
    }
}
