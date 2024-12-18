package com.tabisketch.service.implement;

import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IEditPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EditPasswordService implements IEditPasswordService {

    private final IUsersMapper usersMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public EditPasswordService(IUsersMapper usersMapper, BCryptPasswordEncoder passwordEncoder) {
        this.usersMapper = usersMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean editPassword(final String mailAddress, final String newPassword) {
        String hashedPassword = passwordEncoder.encode(newPassword);
        int updatedRows = usersMapper.updatePassword(mailAddress, hashedPassword);
        return updatedRows > 0;
    }
}
