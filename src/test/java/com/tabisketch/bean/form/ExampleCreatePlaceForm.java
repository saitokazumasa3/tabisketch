package com.tabisketch.bean.form;

import java.time.LocalTime;

public class ExampleCreatePlaceForm {
    public static CreatePlaceForm generate() {
        return new CreatePlaceForm(
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
