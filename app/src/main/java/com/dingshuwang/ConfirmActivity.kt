package com.dingshuwang

import android.content.Intent
import android.util.Log

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.ConfirmFragment

/**
 * Created by tx on 2017/6/19.
 */

class ConfirmActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "订单确认"

    override fun fragmentAsView(): BaseFragment {
        val item = intent.getStringExtra("goods")
        val all = intent.getStringExtra("money")
        Log.i("》》》》 ", "money 444$all")
        return ConfirmFragment.newInstance(item, all)
    }

    companion object {

        fun actConfirm(activity: BaseActivity, item: String, allMoney: String) {
            val intent = Intent(activity, ConfirmActivity::class.java)
            intent.putExtra("goods", item)
            intent.putExtra("money", allMoney)
            Log.i("》》》》 ", "money 33$allMoney")
            activity.startActivity(intent)
        }
    }
}
