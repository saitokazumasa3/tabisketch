package com.tabisketch.bean.form;

import java.time.LocalTime;

public class ExampleUpdatePlaceForm {
    public static UpdatePlaceForm generate() {
        return new UpdatePlaceForm(
                1,
                "name",
                "googlePlaceId",
                0,
                0,
                1,
                0,
                LocalTime.now(),
                LocalTime.now(),
                null,
                null,
                null,
                null,
                null
        );
    }
}
