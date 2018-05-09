package com.dingshuwang.model

import java.io.Serializable

/**
 */
class CouponsItem : Serializable {
    var result: Boolean = false
    var coupons: List<Coupon>? = null

    inner class Coupon {
        var special_price: String? = null
        var coupons_name: String? = null
        var innerid: String? = null
        var start_date: String? = null
        var end_date: String? = null
    }
}
