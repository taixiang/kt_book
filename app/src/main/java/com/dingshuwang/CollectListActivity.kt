package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.CollectListFragment

/**
 * Created by tx on 2017/6/24.
 */

class CollectListActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "我的收藏"

    override fun fragmentAsView(): BaseFragment {
        return CollectListFragment.newInstance()
    }

    companion object {

        fun actCollectList(activity: BaseActivity) {
            val intent = Intent(activity, CollectListActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
