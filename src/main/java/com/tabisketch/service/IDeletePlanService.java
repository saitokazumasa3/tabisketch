package com.tabisketch.service;

import com.tabisketch.exception.DeleteFailedException;

public interface IDeletePlanService {
    void execute(final String planUUID) throws DeleteFailedException;
}
