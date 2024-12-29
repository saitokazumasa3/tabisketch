package com.tabisketch.service;

import com.tabisketch.bean.form.CreateGooglePlaceForm;
import com.tabisketch.exception.InsertFailedException;

public interface ICreateGooglePlaceService {
    /** @return 作成したGooglePlaceのID */
    int execute(final CreateGooglePlaceForm createGooglePlaceForm) throws InsertFailedException;
}
