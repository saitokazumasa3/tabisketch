package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.GooglePlace;
import com.tabisketch.bean.form.CreateGooglePlaceForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.mapper.IGooglePlaceMapper;
import com.tabisketch.service.ICreateGooglePlaceService;
import org.springframework.stereotype.Service;

@Service
public class CreateGooglePlaceService implements ICreateGooglePlaceService {
    private final IGooglePlaceMapper googlePlaceMapper;

    public CreateGooglePlaceService(final IGooglePlaceMapper googlePlaceMapper) {
        this.googlePlaceMapper = googlePlaceMapper;
    }

    @Override
    public int execute(final CreateGooglePlaceForm createGooglePlaceForm) throws InsertFailedException {
        final GooglePlace googlePlace = GooglePlace.generate(
                createGooglePlaceForm.getPlaceId(),
                createGooglePlaceForm.getName(),
                createGooglePlaceForm.getLatitude(),
                createGooglePlaceForm.getLongitude()
        );

        final int result = this.googlePlaceMapper.insert(googlePlace);
        if (result != 1) throw new InsertFailedException("GooglePlaceの追加に失敗しました。");

        return googlePlace.getId();
    }
}
