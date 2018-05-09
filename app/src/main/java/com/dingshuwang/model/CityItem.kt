package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by Administrator on 2016/7/20.
 * "id": "5",
 * "province_name": "上海市",
 * "City": [
 * {
 * "id": "51",
 * "city_name": "上海市"
 * }
 */
class CityItem : Serializable {

    var id: String? = null
    var province_name: String? = null
    var City: List<CityName>? = null

    inner class CityName : Serializable {
        var id: String? = null
        var city_name: String? = null

        override fun toString(): String {
            return "CityName{" +
                    "id='" + id + '\''.toString() +
                    ", city_name='" + city_name + '\''.toString() +
                    '}'.toString()
        }
    }

    override fun toString(): String {
        return "CityItem{" +
                "id='" + id + '\''.toString() +
                ", province_name='" + province_name + '\''.toString() +
                ", City=" + City +
                '}'.toString()
    }
}
