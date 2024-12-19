package com.tabisketch.mapper;

import com.tabisketch.bean.entity.MAAToken;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MAATokensMapperTest {
    @Autowired
    private IMAATokensMapper maaTokensMapper;

    @Test
    @Sql("classpath:/sql/CreateUser.sql")
    public void testInsert() {
        final var maaToken = MAAToken.generate(1);
        assert this.maaTokensMapper.insert(maaToken) == 1;
        assert maaToken.getId() != -1;
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreateMAAToken.sql"
    })
    public void testSelect() {
        final var token = UUID.fromString("a2e69add-9d95-4cf1-a59b-cedbb95dcd6b");
        assert this.maaTokensMapper.selectByToken(token) != null;
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreateMAAToken.sql"
    })
    public void testDelete() {
        final int id = 1;
        assert this.maaTokensMapper.delete(id) == 1;
    }
}
