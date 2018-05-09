package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by tx on 2017/6/8.
 */


class HomeFreeItem : Serializable {
    var result: Boolean = false
    var data: List<HomeFree>? = null

    inner class HomeFree : Serializable {
        var id: String? = null
        var type: Int = 0
        var name: String? = null
        var price_market: String? = null
        var price_sell: String? = null
        var img_url: String? = null
    }
}
