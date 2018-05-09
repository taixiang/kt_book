package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by Administrator on 2016/7/27.
 */
class PayItem : Serializable {

    var order: Order? = null

    inner class Order {
        var order_no: String? = null
        var express_fee: String? = null
        var real_amount: String? = null
        var order_amount: String? = null
        var area: String? = null
        var address: String? = null
        var accept_name: String? = null
        var telphone: String? = null
        var order_status: String? = null
        var express_title: String? = null
        var point: String? = null
        var favourable_amount: String? = null
        var groups_favourable: String? = null
        var order_goods: List<OrderGood>? = null

        inner class OrderGood {
            var goods_title: String? = null
            var img_url: String? = null
            var real_price: String? = null
            var quantity: String? = null
        }


    }


}
