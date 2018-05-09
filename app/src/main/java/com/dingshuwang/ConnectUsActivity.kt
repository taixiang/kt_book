package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.ConnectUsFragment

/**
 * Created by tx on 2017/6/28.
 */

class ConnectUsActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "联系我们"

    override fun fragmentAsView(): BaseFragment {
        return ConnectUsFragment.newInstance()
    }

    companion object {

        fun actConnectUs(activity: BaseActivity) {
            val intent = Intent(activity, ConnectUsActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
