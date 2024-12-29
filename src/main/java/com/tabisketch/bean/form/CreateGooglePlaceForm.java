package com.tabisketch.bean.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateGooglePlaceForm {
    @NotBlank
    private String placeId;

    @NotBlank
    private String name;

    private double latitude;
    private double longitude;

    public static CreateGooglePlaceForm generate(
            final String placeId,
            final String name,
            final double latitude,
            final double longitude
    ) {
        return new CreateGooglePlaceForm(
                placeId,
                name,
                latitude,
                longitude
        );
    }
}
