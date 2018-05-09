package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.CouponsFragment

/**
 * Created by tx on 2017/6/21.
 */

class CouponsActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "我的优惠券"

    override fun fragmentAsView(): BaseFragment {
        return CouponsFragment.newInstance()
    }

    companion object {

        fun actionCoupons(activity: BaseActivity) {
            val intent = Intent(activity, CouponsActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
