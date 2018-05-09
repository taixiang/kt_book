package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by Administrator on 2016/7/24.
 */
class AddressDetailItem : Serializable {
    var address: Item? = null

    inner class Item {
        var id: String? = null
        var area_id: String? = null
        var area: String? = null
        var address: String? = null
        var accept_name: String? = null
        var phone: String? = null
    }
}
