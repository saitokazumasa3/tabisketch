package com.tabisketch.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResetPasswordServiceTest {

    @Mock
    private PasswordResetTokenRepository tokenRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ResetPasswordService resetPasswordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendResetPasswordEmail_正常にトークンが生成されメールが送信される() {
        String email = "user@example.com";
        User user = new User(email, "password123");
        when(userService.findUserByEmail(email)).thenReturn(user);

        resetPasswordService.sendResetPasswordEmail(email);

        verify(userService).findUserByEmail(email);
        verify(tokenRepository).save(any());
    }

    @Test
    void resetPassword_有効なトークンでパスワードがリセットされる() {
        String token = "valid-token";
        String newPassword = "newPassword123";
        User user = new User("user@example.com", "password123");

        when(tokenRepository.findByToken(token)).thenReturn(user);

        resetPasswordService.resetPassword(token, newPassword);

        verify(tokenRepository).findByToken(token);
        verify(userService).updatePassword(user, newPassword);
    }

    @Test
    void resetPassword_無効なトークンで例外がスローされる() {
        String token = "invalid-token";

        when(tokenRepository.findByToken(token)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                resetPasswordService.resetPassword(token, "newPassword123")
        );
    }
}
