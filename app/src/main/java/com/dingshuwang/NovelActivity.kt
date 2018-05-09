package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.NovelFragment

/**
 * Created by tx on 2017/6/15.
 */

class NovelActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "小说专区"

    override fun fragmentAsView(): BaseFragment {
        return NovelFragment.newInstance()
    }

    companion object {

        fun actionNovel(activity: BaseActivity) {
            val intent = Intent(activity, NovelActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
