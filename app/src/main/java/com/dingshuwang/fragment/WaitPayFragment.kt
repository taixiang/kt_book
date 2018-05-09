package com.dingshuwang.fragment

import com.dingshuwang.DataView
import com.dingshuwang.base.BaseFragment

/**
 * Created by tx on 2017/6/21.
 */

class WaitPayFragment : BaseFragment(), DataView {

    override val fragmentTitle: String
        get() = ""

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {

    }
}
