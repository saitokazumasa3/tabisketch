package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/** 目的地 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Destination {
    /** 識別子 */
    private int id;
    /** GoogleMapのPlaceId */
    private String googleMapPlaceId;
    /** 順番 */
    private int order;
    /** 訪れる時間 */
    private LocalTime startTime;
    /** ユーザーが指定した訪れる時間 */
    private LocalTime specifiedStartTime;
    /** ここへ向かうまでの交通手段 */
    private String transportationMethod;
    /** 滞在時間 */
    private int duration;
    /** 予算 */
    private int budget;
    /** 関連する「目的地リスト」の識別子 */
    private int destinationListId;
}
