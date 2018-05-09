package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.ToPayFragment

/**
 * Created by Administrator on 2016/7/25.
 */
class ToPayActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "待付款"

    override fun fragmentAsView(): BaseFragment? {
        return ToPayFragment.newInstance()
    }

    companion object {

        fun actToPay(activity: BaseActivity) {
            val intent = Intent(activity, ToPayActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
