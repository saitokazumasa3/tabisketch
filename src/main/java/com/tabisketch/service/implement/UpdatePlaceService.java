package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.GooglePlace;
import com.tabisketch.bean.entity.Place;
import com.tabisketch.bean.form.UpdatePlaceForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.UpdateFailedException;
import com.tabisketch.mapper.IGooglePlaceMapper;
import com.tabisketch.mapper.IPlacesMapper;
import com.tabisketch.service.IUpdatePlaceService;
import org.springframework.stereotype.Service;

@Service
public class UpdatePlaceService implements IUpdatePlaceService {
    private final IGooglePlaceMapper googlePlaceMapper;
    private final IPlacesMapper placesMapper;

    public UpdatePlaceService(
            final IGooglePlaceMapper googlePlaceMapper,
            final IPlacesMapper placesMapper
    ) {
        this.googlePlaceMapper = googlePlaceMapper;
        this.placesMapper = placesMapper;
    }

    @Override
    public int execute(final UpdatePlaceForm updatePlaceForm)
            throws UpdateFailedException, InsertFailedException {
        final GooglePlace selectedGooglePlace =
                this.googlePlaceMapper.selectByPlaceId(updatePlaceForm.getGooglePlaceId());

        if (selectedGooglePlace != null) {
            final Place place = updatePlaceForm.toPlace(selectedGooglePlace.getId());
            final int result = this.placesMapper.update(place);

            if (result != 1) throw new UpdateFailedException("Placeの更新に失敗しました。");

            return place.getId();
        }

        final GooglePlace googlePlace = updatePlaceForm.toGooglePlace();
        final int googlePlaceResult = this.googlePlaceMapper.insert(googlePlace);

        final Place place = updatePlaceForm.toPlace(googlePlace.getId());
        final int result = this.placesMapper.update(place);

        if (googlePlaceResult != 1) throw new InsertFailedException("GooglePlaceの追加に失敗しました。");
        if (result != 1) throw new UpdateFailedException("Placeの更新に失敗しました。");

        return place.getId();
    }
}
