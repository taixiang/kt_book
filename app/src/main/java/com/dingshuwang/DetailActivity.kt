package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.DetailFragment

/**
 * Created by tx on 2017/6/14.
 */

class DetailActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "书本详情"

    override fun fragmentAsView(): BaseFragment {
        return DetailFragment.newInstance(intent.getStringExtra("id"))
    }

    companion object {

        fun actionDetail(activity: BaseActivity, id: String) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("id", id)
            activity.startActivity(intent)
        }
    }
}
