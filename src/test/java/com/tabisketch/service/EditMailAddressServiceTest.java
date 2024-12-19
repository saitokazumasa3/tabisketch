package com.tabisketch.service;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.EditMailAddressForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.mapper.IMAATokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EditMailAddressServiceTest {
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private IMAATokensMapper maaTokensMapper;
    @MockitoBean
    private ISendMailService sendMailService;
    @Autowired
    private IEditMailAddressService editMailAddressService;

    @Test
    @WithMockUser
    public void testExecute() throws MessagingException, InsertFailedException {
        final var user =
                User.generate("", "$2a$10$if7oiFZVmP9I59AOtzbSz.dWsdPUUuPTRkcIoR8iYhFpG/0COY.TO");
        final var editMailAddressForm = new EditMailAddressForm(
                "sample@example.com",
                "sample2@example.com",
                "password"
        );

        when(this.usersMapper.selectByMailAddress(editMailAddressForm.getCurrentMailAddress())).thenReturn(user);
        when(this.usersMapper.selectByMailAddress(editMailAddressForm.getNewMailAddress())).thenReturn(null);
        when(this.maaTokensMapper.insert(any())).thenReturn(1);

        this.editMailAddressService.execute(editMailAddressForm);

        verify(this.usersMapper, times(2)).selectByMailAddress(anyString());
        verify(this.maaTokensMapper).insert(any());
        verify(this.sendMailService).execute(any());
    }
}
