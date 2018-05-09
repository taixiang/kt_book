package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseNoTitleActivity
import com.dingshuwang.fragment.SearchFindFragment

/**
 * Created by tx on 2017/6/14.
 */

class SearchFindActivity : BaseNoTitleActivity() {

    override val activityTitle: CharSequence
        get() = ""

    override fun fragmentAsView(): BaseFragment {
        return SearchFindFragment.newInstance()
    }

    companion object {

        fun actionSearchFind(activity: BaseActivity) {
            val intent = Intent(activity, SearchFindActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
