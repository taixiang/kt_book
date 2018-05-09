package com.dingshuwang.base

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log

import com.dingshuwang.R


/**
 * 中间显示一个标题的Activity的基类，标题的名称为[BaseActivity.getActivityTitle]
 */
abstract class BaseTitleActivity : BaseActivity() {
    private var mBaseFragment: BaseFragment? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_as_view_common)
        mBaseFragment = fragmentAsView()
        if (null != mBaseFragment) {
            supportFragmentManager.beginTransaction().replace(R.id.frame_content_container, mBaseFragment).commit()
            setCenterTitle(activityTitle, Color.WHITE)
        }
        showTitleContainer()
        afterOnCreate(savedInstanceState)
    }

    open fun afterOnCreate(savedInstanceState: Bundle?) {
        //        setSwipeBackEnable(false);
    }

    abstract fun fragmentAsView(): BaseFragment?

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (null != mBaseFragment) {
            mBaseFragment!!.onActivityResult(requestCode, resultCode, data)
        }
    }
}
