package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.AdvanceListFragment

/**
 * Created by tx on 2017/6/20.
 */

class AdvanceListActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "搜索结果"

    override fun fragmentAsView(): BaseFragment? {
        return AdvanceListFragment.newInstance(intent.getStringExtra("advance"))
    }

    companion object {

        fun actionAdvance(activity: BaseActivity, advance: String) {
            val intent = Intent(activity, AdvanceListActivity::class.java)
            intent.putExtra("advance", advance)
            activity.startActivity(intent)
        }
    }
}
