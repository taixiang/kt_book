package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.ShoppingCartFragment

class ShopCartActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "购物车"

    override fun fragmentAsView(): BaseFragment {
        return ShoppingCartFragment.newInstance()
    }

    companion object {

        fun actShop(activity: BaseActivity) {
            val intent = Intent(activity, ShopCartActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
