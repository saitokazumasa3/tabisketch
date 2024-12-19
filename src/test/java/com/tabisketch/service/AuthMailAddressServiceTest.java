package com.tabisketch.service;

import com.tabisketch.bean.entity.MAAToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.exception.UpdateFailedException;
import com.tabisketch.mapper.IMAATokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthMailAddressServiceTest {
    @MockitoBean
    private IMAATokensMapper maaTokensMapper;
    @MockitoBean
    private IUsersMapper usersMapper;
    @Autowired
    private IAuthMailAddressService authMailAddressService;

    @Test
    public void testExecute() throws DeleteFailedException, UpdateFailedException {
        final var maaToken = MAAToken.generate(1, "sample2@example.com");
        final var user = new User(1, "", "", false);

        when(this.maaTokensMapper.selectByToken(any())).thenReturn(maaToken);
        when(this.usersMapper.selectById(anyInt())).thenReturn(user);
        when(this.usersMapper.update(any())).thenReturn(1);
        when(this.maaTokensMapper.delete(anyInt())).thenReturn(1);

        this.authMailAddressService.execute(maaToken.getToken().toString());

        verify(this.maaTokensMapper).selectByToken(any());
        verify(this.usersMapper).selectById(anyInt());
        verify(this.usersMapper).update(any());
        verify(this.maaTokensMapper).delete(anyInt());
    }
}
