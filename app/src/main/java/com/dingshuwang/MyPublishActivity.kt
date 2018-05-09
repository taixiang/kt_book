package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.MyPublishFragment

/**
 * Created by tx on 2017/7/7.
 */

class MyPublishActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "我的发布"

    override fun fragmentAsView(): BaseFragment {
        return MyPublishFragment.newInstance()
    }

    companion object {

        fun actMyPublish(activity: BaseActivity) {
            val intent = Intent(activity, MyPublishActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
