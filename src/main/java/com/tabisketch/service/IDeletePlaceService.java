package com.tabisketch.service;

import com.tabisketch.exception.DeleteFailedException;

public interface IDeletePlaceService {
    void execute(final int id) throws DeleteFailedException;
}
