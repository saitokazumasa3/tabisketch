package com.tabisketch.mapper;

import com.tabisketch.bean.entity.GooglePlace;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GooglePlaceMapperTest {
    @Autowired
    private IGooglePlaceMapper googlePlaceMapper;

    @Test
    public void testInsert() {
        final var googlePlace = GooglePlace.generate(
                "",
                "",
                0,
                0
        );
        assert this.googlePlaceMapper.insert(googlePlace) == 1;
        assert googlePlace.getId() != -1;
    }
}
