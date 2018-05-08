package com.dingshuwang

import android.content.Intent
import android.util.Log

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.LoginFragment

/**
 * 登录
 */
class LoginActivity : BaseTitleActivity() {


    override val activityTitle: CharSequence
        get() = "登录"


    override fun fragmentAsView(): BaseFragment {
        val tag = intent.getIntExtra("tag", 0)
        return if (tag != 0) {
            LoginFragment.newInstance(tag)
        } else LoginFragment.newInstance()
    }

    companion object {


        fun actionLogin(activity: BaseActivity, tag: Int) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.putExtra("tag", tag)
            activity.startActivity(intent)
        }
    }
}
