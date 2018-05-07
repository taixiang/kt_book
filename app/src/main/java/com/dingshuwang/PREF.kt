package com.dingshuwang


class PREF private constructor() {
    init {
        throw RuntimeException("PREF类不可以实例化")
    }

    companion object {

        val PREF_USER_ID = "user_id"

        val CART_NUM = "shop_num"
    }


}
