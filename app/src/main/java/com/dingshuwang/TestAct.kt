package com.dingshuwang

import android.os.Bundle
import android.view.View
import android.webkit.WebView

import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseNoTitleActivity


/**
 * Created by tx on 2017/6/20.
 */

class TestAct : BaseNoTitleActivity() {

    internal var webView: WebView ?= null

    override val activityTitle: CharSequence
        get() = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test)
        webView = findViewById<View>(R.id.webview) as WebView?
        this.webView?.loadUrl("http://m.iisbn.com/rec.html")
    }

    override fun fragmentAsView(): BaseFragment? {
        return null
    }
}
