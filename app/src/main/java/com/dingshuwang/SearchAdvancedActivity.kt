package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.SearchAdvancedFragment

/**
 * Created by tx on 2017/6/19.
 */

class SearchAdvancedActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "高级搜索"

    override fun fragmentAsView(): BaseFragment {
        return SearchAdvancedFragment.newInstance()
    }

    companion object {

        fun actionSearchAdvaced(activity: BaseActivity) {
            val intent = Intent(activity, SearchAdvancedActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
