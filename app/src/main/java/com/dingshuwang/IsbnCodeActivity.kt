package com.dingshuwang

import android.content.Intent

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseTitleActivity
import com.dingshuwang.fragment.IsbnCodeFragment

class IsbnCodeActivity : BaseTitleActivity() {

    override val activityTitle: CharSequence
        get() = "扫描结果"

    override fun fragmentAsView(): BaseFragment {
        val isbn = intent.getStringExtra("isbn")
        return IsbnCodeFragment.newInstance(isbn)
    }

    companion object {

        fun actIsbn(activity: BaseActivity, isbn: String) {
            val intent = Intent(activity, IsbnCodeActivity::class.java)
            intent.putExtra("isbn", isbn)
            activity.startActivity(intent)
        }
    }
}
