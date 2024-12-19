package com.tabisketch.mapper;

import com.tabisketch.bean.entity.Place;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface IPlacesMapper {
    @Insert("INSERT INTO places (" +
            "   google_place_id, day_id, budget, " +
            "   start_time, end_time, " +
            "   desired_start_time, desired_end_time, " +
            "   to_poly_line, to_travel_time, to_transportation) " +
            "VALUES (" +
            "   #{googlePlaceId}, #{dayId}, #{budget}, " +
            "   #{startTime}, #{endTime}, " +
            "   #{desiredStartTime}, #{desiredEndTime}, " +
            "   #{toPolyLine}, #{toTravelTime}, #{toTransportation})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final Place place);

    @Select("SELECT * FROM places WHERE day_id = #{dayId}")
    List<Place> selectByDayId(final int dayId);

    @Delete("DELETE FROM places " +
            "   USING days, plans " +
            "   WHERE places.day_id = days.id " +
            "   AND days.plan_id = plans.id " +
            "   AND plans.uuid = #{planUUID}")
    int deleteByPlanUUID(final UUID planUUID);
}
