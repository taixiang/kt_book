package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.RegisterFragment

/**
 * Created by tx on 2017/6/8.
 */

class RegisterActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "注册"

    override fun fragmentAsView(): BaseFragment {
        return RegisterFragment.newInstance()
    }

    companion object {


        fun actionRegister(activity: BaseActivity) {
            val intent = Intent(activity, RegisterActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
