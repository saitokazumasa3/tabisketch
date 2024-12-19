package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.MAAToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.valueobject.Mail;
import com.tabisketch.mapper.IMAATokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IRegisterService;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService implements IRegisterService {
    private final IUsersMapper usersMapper;
    private final IMAATokensMapper maaTokensMapper;
    private final ISendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(
            final IUsersMapper usersMapper,
            final IMAATokensMapper maaTokensMapper,
            final ISendMailService sendMailService,
            final PasswordEncoder passwordEncoder
    ) {
        this.usersMapper = usersMapper;
        this.maaTokensMapper = maaTokensMapper;
        this.sendMailService = sendMailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void execute(final RegisterForm registerForm) throws InsertFailedException, MessagingException {
        final String encryptedPassword = this.passwordEncoder.encode(registerForm.getPassword());
        final var user = User.generate(registerForm.getMailAddress(), encryptedPassword);
        final int userInsertResult = this.usersMapper.insert(user);

        final var maaToken = MAAToken.generate(user.getId());
        final int maaTokenInsertResult = this.maaTokensMapper.insert(maaToken);

        if (userInsertResult != 1) throw new InsertFailedException("Userの追加に失敗しました。");
        if (maaTokenInsertResult != 1) throw new InsertFailedException("MAATokenの追加に失敗しました。");

        final var mail = Mail.registrationMail(user.getMailAddress(), maaToken.getToken());
        this.sendMailService.execute(mail);
    }
}
