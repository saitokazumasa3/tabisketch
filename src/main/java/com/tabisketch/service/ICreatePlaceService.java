package com.tabisketch.service;

import com.tabisketch.bean.form.CreatePlaceForm;
import com.tabisketch.exception.InsertFailedException;

public interface ICreatePlaceService {
    /** @return 作成したPlaceのID */
    int execute(final CreatePlaceForm createPlaceForm) throws InsertFailedException;
}
