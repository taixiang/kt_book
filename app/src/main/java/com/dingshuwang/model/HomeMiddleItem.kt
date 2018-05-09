package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by tx on 2017/6/8.
 */

class HomeMiddleItem : Serializable {

    var result: Boolean = false
    var data: List<HomeMiddle>? = null

    inner class HomeMiddle : Serializable {
        var keywords: String? = null
        var image_url: String? = null
        var url: String? = null

    }


}
