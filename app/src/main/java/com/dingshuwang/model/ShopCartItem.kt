package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by Administrator on 2016/7/9.
 */
class ShopCartItem : Serializable {
    var shops: List<ShopsItem>? = null

    inner class ShopsItem : Serializable {
        var supplier_id: String? = null
        var supplier_name: String? = null
        var Shop: List<ShopItem>? = null


    }

    inner class ShopItem {
        var innerid: String? = null
        var user_id: String? = null
        var pro_id: String? = null
        var pro_name: String? = null
        var price_sell: String? = null
        var available_nums: Int = 0
        var img_url: String? = null
        var order_number: String? = null
        var remark: String? = null
        var point: String? = null
        var supplier_id: String? = null

    }

}
