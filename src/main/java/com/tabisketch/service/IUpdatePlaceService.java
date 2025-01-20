package com.tabisketch.service;

import com.tabisketch.bean.form.UpdatePlaceForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.UpdateFailedException;

public interface IUpdatePlaceService {
    /** @return 更新したPlaceのID */
    int execute(final UpdatePlaceForm updatePlaceForm) throws UpdateFailedException, InsertFailedException;
}
