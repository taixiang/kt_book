package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by tx on 2017/7/7.
 */

class PublishListItem : Serializable {
    var result: Boolean = false
    var trade_infor: List<Publish>? = null

    inner class Publish : Serializable {
        var create_date: String? = null
        var image_url: String? = null
        var pro_name: String? = null
        var pro_isbn: String? = null
        var price_sell: String? = null
    }
}
