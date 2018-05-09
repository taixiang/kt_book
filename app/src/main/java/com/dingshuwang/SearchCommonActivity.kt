package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseNoTitleActivity
import com.dingshuwang.fragment.SearchCommonFragment

/**
 * Created by tx on 2017/6/14.
 */

class SearchCommonActivity : BaseNoTitleActivity() {

    override val activityTitle: CharSequence
        get() = ""

    override fun fragmentAsView(): BaseFragment {
        return SearchCommonFragment.newInstance()
    }

    companion object {

        fun actionSearchCommon(activity: BaseActivity) {
            val intent = Intent(activity, SearchCommonActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
