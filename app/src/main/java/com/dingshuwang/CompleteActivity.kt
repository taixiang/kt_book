package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.CompleteFragment

/**
 * Created by Administrator on 2016/7/29.
 */
class CompleteActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "已完成"

    override fun fragmentAsView(): BaseFragment {
        return CompleteFragment.newInstance()
    }

    companion object {

        fun actComplete(activity: BaseActivity) {
            val intent = Intent(activity, CompleteActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
