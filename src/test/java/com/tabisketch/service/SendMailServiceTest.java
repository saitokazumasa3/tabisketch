package com.tabisketch.service;

import com.tabisketch.valueobject.Mail;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

@SpringBootTest
public class SendMailServiceTest {
    @MockitoBean
    private JavaMailSender __; // DIで使用している
    @Autowired
    private ISendMailService sendMailService;

    // NOTE: アドレスエラーは検出されない
    @Test
    @WithMockUser
    public void testExist() throws MessagingException {
        final String mailAddress = "sample@example.com";

        final Mail registrationMail = Mail.registrationMail(mailAddress, UUID.randomUUID());
        this.sendMailService.execute(registrationMail);

        final Mail mailAddressEditMail = Mail.mailAddressEditMail(mailAddress, UUID.randomUUID());
        this.sendMailService.execute(mailAddressEditMail);
    }
}
