package com.tabisketch.mapper;

import com.tabisketch.bean.entity.GooglePlace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface IGooglePlaceMapper {
    @Insert("INSERT INTO google_places (place_id, name, latitude, longitude) " +
            "VALUES (#{placeId}, #{name}, #{latitude}, #{longitude})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final GooglePlace googlePlace);
}
