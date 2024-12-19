package com.tabisketch.service;

import com.tabisketch.bean.form.EditMailAddressForm;
import com.tabisketch.exception.InsertFailedException;
import jakarta.mail.MessagingException;

public interface IEditMailAddressService {
    void execute(final EditMailAddressForm editMailAddressForm) throws InsertFailedException, MessagingException;
}
