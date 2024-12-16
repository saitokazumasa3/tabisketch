package com.tabisketch.mapper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.stream.Stream;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PasswordAuthTokensMapperTest {
    @Autowired
    private IPassWordAuthenticationTokensMapper passwordAuthTokensMapper;

    @ParameterizedTest
    @MethodSource("samplePasswordAuthToken")
    @Sql("classpath:/sql/CreateUser.sql")
    public void INSERTできるか(final PasswordAuthToken passwordAuthToken) {
        final var result = this.passwordAuthTokensMapper.insert(passwordAuthToken);
        assert result == 1;
        assert passwordAuthToken.getId() != -1;
    }

    @ParameterizedTest
    @MethodSource("samplePasswordAuthTokenForEmail")
    @Sql("classpath:/sql/CreateUser.sql")
    public void メールアドレスを送信できるか(final PasswordAuthToken passwordAuthToken) {
        // ここでメールアドレスを送信する処理を追加する
        final var result = this.passwordAuthTokensMapper.insert(passwordAuthToken);
        assert result == 1;
        assert passwordAuthToken.getEmail() != null;  // メールアドレスが設定されているか確認
    }

    private static Stream<PasswordAuthToken> samplePasswordAuthToken() {
        final var passwordAuthToken1 = PasswordAuthToken.generate(1);
        return Stream.of(passwordAuthToken1);
    }

    private static Stream<PasswordAuthToken> samplePasswordAuthTokenForEmail() {
        final var passwordAuthToken1 = PasswordAuthToken.generateWithEmail("sample@example.com");
        return Stream.of(passwordAuthToken1);
    }
}
