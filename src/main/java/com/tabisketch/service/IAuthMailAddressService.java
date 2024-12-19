package com.tabisketch.service;

import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.exception.UpdateFailedException;

public interface IAuthMailAddressService {
    void execute(final String maaTokenStr) throws UpdateFailedException, DeleteFailedException;
}
