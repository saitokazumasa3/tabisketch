package com.tabisketch.bean.entity;

import java.time.LocalTime;

public class ExamplePlace {
    public static Place generate() {
        return new Place(
                1,
                1,
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
