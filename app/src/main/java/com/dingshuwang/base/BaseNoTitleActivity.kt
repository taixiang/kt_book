package com.dingshuwang.base

import android.os.Bundle

/**
 * 将中间显示标题的ToolBar隐藏
 */
abstract class BaseNoTitleActivity : BaseTitleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        hideTitleBar()
    }



     override fun afterOnCreate(savedInstanceState: Bundle?) {
        super.afterOnCreate(savedInstanceState)
        hideTitleBar()
    }
}
