package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.PasswordResetToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.mapper.IPasswordResetTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.ISendMailService;
import com.tabisketch.service.IResetPasswordSendService;
import com.tabisketch.valueobject.Mail;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordSendService implements IResetPasswordSendService {
    private final IUsersMapper usersMapper;
    private final IPasswordResetTokensMapper passwordResetTokensMapper;
    private final ISendMailService sendMailService;

    public ResetPasswordSendService(
            final IUsersMapper usersMapper,
            final IPasswordResetTokensMapper passwordResetTokensMapper,
            final ISendMailService sendMailService
    ) {
        this.usersMapper = usersMapper;
        this.passwordResetTokensMapper = passwordResetTokensMapper;
        this.sendMailService = sendMailService;
    }

    public void execute(final String mailAddress) throws InsertFailedException, MessagingException {
        final boolean isExist = usersMapper.isExistMailAddress(mailAddress);
        if (!isExist) throw new IllegalArgumentException("メールアドレスが存在しません");

        final User user = usersMapper.selectByMailAddress(mailAddress);
        final PasswordResetToken token = PasswordResetToken.generate(user.getId());
        final int tokenInsertResult = passwordResetTokensMapper.insert(token);

        if (tokenInsertResult != 1) throw new InsertFailedException("PasswordResetTokenの追加に失敗しました。");

        // メール送信
        final var mail = Mail.passwordResetMail(user.getMailAddress(), token.getToken());
        sendMailService.execute(mail);
    }
}