package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.ExpOrderFragment

/**
 * Created by Administrator on 2016/7/28.
 */
class ExpOrderActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "待发货"

    override fun fragmentAsView(): BaseFragment {
        return ExpOrderFragment.newInstance()
    }

    companion object {

        fun actExpOrder(activity: BaseActivity) {
            val intent = Intent(activity, ExpOrderActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
