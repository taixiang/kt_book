package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by Administrator on 2016/7/9.
 */
class UserItem : Serializable {

    var id: String? = null
    var group_id: String? = null
    var user_name: String? = null
    var real_name: String? = null
    var nick_name: String? = null
    var openid: String? = null
    var amount: String? = null
    var point: Double = 0.toDouble()
    var address_id: String? = null
    var user_lvl: String? = null


}



