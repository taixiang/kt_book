package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.AddressListFragment

/**
 * Created by tx on 2017/6/19.
 */

class AddressListActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "收货地址"

    override fun fragmentAsView(): BaseFragment {
        return AddressListFragment.newInstance(intent.getIntExtra("tag", 0))
    }

    companion object {

        fun actionAddresList(activity: BaseActivity, tag: Int) {
            val intent = Intent(activity, AddressListActivity::class.java)
            intent.putExtra("tag", tag)
            activity.startActivity(intent)
        }
    }
}
