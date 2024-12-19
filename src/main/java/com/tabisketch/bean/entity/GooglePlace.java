package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GooglePlace {
    private int id;
    private String placeId;
    private String name;
    private double latitude;
    private double longitude;

    public static GooglePlace generate(
            final String placeId,
            final String name,
            final double latitude,
            final double longitude
    ) {
        return new GooglePlace(
                -1,
                placeId,
                name,
                latitude,
                longitude
        );
    }
}
