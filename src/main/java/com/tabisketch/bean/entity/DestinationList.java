package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 目的地リスト */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DestinationList {
    /** 識別子 */
    private int id;
    /** 日にち */
    private int day;
    /** 有効な移動手段の2進数リスト */
    private String availableTransportationListBinary;
    /** 関連する「旅行プラン」の識別子 */
    private int travelPlanId;
}
