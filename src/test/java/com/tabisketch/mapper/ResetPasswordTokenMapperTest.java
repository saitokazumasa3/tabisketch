package com.tabisketch.mapper;

import com.tabisketch.bean.entity.PasswordResetToken;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResetPasswordTokenMapperTest {
    @Autowired
    private ResetPasswordTokenMapper tokenMapper;

    @Test
    @Sql("classpath:/sql/CreateUser.sql")
    public void testInsert() {
        PasswordResetToken token = new PasswordResetToken(UUID.randomUUID(), "sample@example.com", true);
        int rowsInserted = tokenMapper.insert(token);
        assertEquals(1, rowsInserted);
        assertNotNull(token.getId());
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePasswordResetToken.sql"
    })
    public void testSelect() {
        UUID token = UUID.fromString("a2e69add-9d95-4cf1-a59b-cedbb95dcd6b");
        PasswordResetToken foundToken = tokenMapper.selectByToken(token);
        assertNotNull(foundToken);
        assertEquals(token, foundToken.getToken());
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePasswordResetToken.sql"
    })
    public void testUpdate() {
        UUID token = UUID.randomUUID();
        PasswordResetToken resetToken = new PasswordResetToken(token, "sample@example.com", true);
        tokenMapper.insert(resetToken);

        resetToken.setIsValid(false);
        int rowsUpdated = tokenMapper.update(resetToken);
        assertEquals(1, rowsUpdated);

        PasswordResetToken updatedToken = tokenMapper.selectByToken(token);
        assertNotNull(updatedToken);
        assertFalse(updatedToken.getIsValid());
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePasswordResetToken.sql"
    })
    public void testDelete() {
        UUID token = UUID.randomUUID();
        PasswordResetToken resetToken = new PasswordResetToken(token, "sample@example.com", true);
        tokenMapper.insert(resetToken);

        int rowsDeleted = tokenMapper.delete(resetToken.getId());
        assertEquals(1, rowsDeleted);

        PasswordResetToken foundToken = tokenMapper.selectByToken(token);
        assertNull(foundToken);
    }
}
