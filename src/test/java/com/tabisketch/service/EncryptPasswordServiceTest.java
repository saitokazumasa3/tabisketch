package com.tabisketch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class EncryptPasswordServiceTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testExecute() {
        final String password = "password";
        final String encryptedPassword = passwordEncoder.encode(password);
        assert passwordEncoder.matches(password, encryptedPassword);
    }
}
