package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by tx on 2017/7/7.
 */

class CollectListItem : Serializable {
    var result: Boolean = false
    var favorites: List<CollectList>? = null

    inner class CollectList : Serializable {
        var pro_id: String? = null
        var name: String? = null
        var ISBN: String? = null
        var img_url: String? = null
        var create_time: String? = null

    }
}
