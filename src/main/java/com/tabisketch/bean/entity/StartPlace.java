package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/** 開始地点 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartPlace {
    /** 識別子 */
    private int id;
    /** GoogleMapのPlaceId */
    private String googleMapPlaceId;
    /** 出発時間 */
    private LocalTime departureTime;
    /** 関連する「目的地リスト」の識別子 */
    private int destinationListId;
}
