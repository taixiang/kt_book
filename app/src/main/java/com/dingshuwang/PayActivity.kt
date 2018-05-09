package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.PayFragment

/**
 * Created by tx on 2017/7/5.
 */

class PayActivity : BaseTitleActivity() {
    override val activityTitle: CharSequence
        get() = "订单详情"

    override fun fragmentAsView(): BaseFragment {
        val order_id = intent.getStringExtra("order_id")
        return PayFragment.newInstance(order_id)
    }

    companion object {

        fun actConfirm(activity: BaseActivity, order_id: String) {
            val intent = Intent(activity, PayActivity::class.java)
            intent.putExtra("order_id", order_id)
            activity.startActivity(intent)
        }
    }
}
