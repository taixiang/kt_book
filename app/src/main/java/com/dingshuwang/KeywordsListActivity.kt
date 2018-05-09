package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseNoTitleActivity
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.KeywordsListFragment

/**
 * Created by tx on 2017/6/9.
 */

class KeywordsListActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = ""

    override fun fragmentAsView(): BaseFragment {

        return KeywordsListFragment.newInstance(intent.getStringExtra("keyword"))
    }

    companion object {

        fun actionKeywordsList(activity: BaseActivity, keyword: String) {
            val intent = Intent(activity, KeywordsListActivity::class.java)
            intent.putExtra("keyword", keyword)
            activity.startActivity(intent)
        }
    }
}
