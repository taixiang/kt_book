package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.AllOrderFragment

/**
 * Created by tx on 2017/7/6.
 */

class AllOrderActivity : BaseTitleActivity() {


    override val activityTitle: CharSequence
        get() = "全部订单"

    override fun fragmentAsView(): BaseFragment {
        return AllOrderFragment.newInstance()
    }

    companion object {

        fun actToPay(activity: BaseActivity) {
            val intent = Intent(activity, AllOrderActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
