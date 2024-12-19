package com.tabisketch.bean.response;

import lombok.Getter;

@Getter
public class CreateGooglePlaceResponse {
    private enum Status {
        SUCCESS,
        FAILED
    }

    private final String status;
    private final int googlePlaceId;

    private CreateGooglePlaceResponse(final String status, final int googlePlaceId) {
        this.status = status;
        this.googlePlaceId = googlePlaceId;
    }

    public static CreateGooglePlaceResponse success(final int googlePlaceId) {
        return new CreateGooglePlaceResponse(Status.SUCCESS.toString(), googlePlaceId);
    }

    public static CreateGooglePlaceResponse failed() {
        return new CreateGooglePlaceResponse(Status.FAILED.toString(), -1);
    }
}
