package com.dingshuwang

/**
 * Created by Steven on 2015/9/10 0010.
 */
interface DataView {
    fun onGetDataFailured(msg: String, requestTag: String)
    fun onGetDataSuccess(result: String, requestTag: String)
}
