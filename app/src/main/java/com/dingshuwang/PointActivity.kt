package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.PointFragment

/**
 * Created by tx on 2017/6/22.
 */

class PointActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "积分记录"

    override fun fragmentAsView(): BaseFragment {
        return PointFragment.newInstance()
    }

    companion object {

        fun actPoint(activity: BaseActivity) {
            val intent = Intent(activity, PointActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
