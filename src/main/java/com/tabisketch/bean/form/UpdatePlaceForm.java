package com.tabisketch.bean.form;

import com.tabisketch.constant.Transportation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class UpdatePlaceForm {
    @Min(0)
    private int id;

    @Min(1)
    private int dayId;

    @Min(0)
    private int budget;

    /**
     * 開始時間
     */
    @NotNull
    private LocalTime startTime;
    /**
     * 終了時間
     */
    @NotNull
    private LocalTime endTime;

    /**
     * 希望開始時間
     */
    private LocalTime desiredStartTime;
    /**
     * 希望終了時間
     */
    private LocalTime desiredEndTime;

    private String toPolyLine;
    private Integer toTravelTime;
    private Transportation toTransportation;
}
