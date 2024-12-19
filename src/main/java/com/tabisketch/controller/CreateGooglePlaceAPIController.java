package com.tabisketch.controller;

import com.tabisketch.bean.form.CreateGooglePlaceForm;
import com.tabisketch.bean.response.CreateGooglePlaceResponse;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.service.ICreateGooglePlaceService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/create-google-place")
public class CreateGooglePlaceAPIController {
    final ICreateGooglePlaceService createGooglePlaceService;

    public CreateGooglePlaceAPIController(final ICreateGooglePlaceService createGooglePlaceService) {
        this.createGooglePlaceService = createGooglePlaceService;
    }

    @PostMapping
    public CreateGooglePlaceResponse post(
            final @Validated CreateGooglePlaceForm createGooglePlaceForm,
            final BindingResult bindingResult
    ) throws InsertFailedException {
        if (bindingResult.hasErrors()) return CreateGooglePlaceResponse.failed();

        final int googlePlaceId = this.createGooglePlaceService.execute(createGooglePlaceForm);
        return CreateGooglePlaceResponse.success(googlePlaceId);
    }
}
