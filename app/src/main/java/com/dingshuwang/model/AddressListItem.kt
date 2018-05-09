package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by Administrator on 2016/7/21.
 */
class AddressListItem : Serializable {
    var address: List<AddressItem>? = null

    inner class AddressItem : Serializable {
        var id: String? = null
        var area_id: String? = null
        var area: String? = null
        var address: String? = null
        var accept_name: String? = null
        var phone: String? = null
        var is_default: String? = null
    }

}
