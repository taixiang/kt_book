package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.ForExpFragment

/**
 * Created by Administrator on 2016/7/28.
 */
class ForExpActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "待收货"

    override fun fragmentAsView(): BaseFragment {
        return ForExpFragment.newInstance()
    }

    companion object {

        fun actForExp(activity: BaseActivity) {
            val intent = Intent(activity, ForExpActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
