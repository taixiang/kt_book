package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.AddressEditFragment

/**
 * Created by tx on 2017/6/19.
 */

class AddressEditActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "地址编辑"

    override fun fragmentAsView(): BaseFragment {
        return AddressEditFragment.newInstance(intent.getStringExtra("address_id"))
    }

    companion object {

        fun actionAddressEdit(activity: BaseActivity, address_id: String) {
            val intent = Intent(activity, AddressEditActivity::class.java)
            intent.putExtra("address_id", address_id)
            activity.startActivity(intent)
        }
    }
}
