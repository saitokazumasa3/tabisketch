package com.tabisketch.bean.response;

import com.tabisketch.bean.entity.Place;
import lombok.Getter;

@Getter
public class CreatePlaceResponse implements IResponse {
    private final String status;
    private final int placeId;

    private CreatePlaceResponse(
            final String status,
            final int placeId
    ) {
        this.status = status;
        this.placeId = placeId;
    }

    public static CreatePlaceResponse success(final int placeId) {
        return new CreatePlaceResponse(Status.Success.toString(), placeId);
    }

    public static CreatePlaceResponse failed() {
        return new CreatePlaceResponse(Status.Failed.toString(), -1);
    }
}
