package com.tabisketch.bean.entity;

public class ExampleGooglePlace {
    public static GooglePlace generate() {
        return new GooglePlace(
                1,
                "googlePlaceId",
                "name",
                0,
                0
        );
    }
}
