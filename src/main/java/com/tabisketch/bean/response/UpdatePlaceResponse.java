package com.tabisketch.bean.response;

import com.tabisketch.bean.entity.Place;
import lombok.Getter;

@Getter
public class UpdatePlaceResponse implements IResponse {
    private final String status;
    private final int placeId;

    private UpdatePlaceResponse(
            final String status,
            final int placeId
    ) {
        this.status = status;
        this.placeId = placeId;
    }

    public static UpdatePlaceResponse success(final int placeId) {
        return new UpdatePlaceResponse(Status.Success.toString(), placeId);
    }

    public static UpdatePlaceResponse failed() {
        return new UpdatePlaceResponse(Status.Failed.toString(), -1);
    }
}
