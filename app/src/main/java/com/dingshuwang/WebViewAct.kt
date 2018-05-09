package com.dingshuwang

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseNoTitleActivity

class WebViewAct : BaseNoTitleActivity() {

    private var contentWebView: WebView? = null
    private var imgShare: ImageView? = null
    private var rlyBack: RelativeLayout? = null

    internal var url = ""
    private var bar_title_txt: TextView? = null

    internal var btnClickListener: OnClickListener = OnClickListener { v ->
        when (v.id) {
            R.id.rlyBack -> jumpBack()
            R.id.img_right -> contentWebView!!.loadUrl("javascript:getInfo()")// android调用当前页面的androidGetInfo()方法。
        }
    }

    override val activityTitle: CharSequence
        get() = ""

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_help)
        try {
            url = this.intent.getStringExtra("h_url")
            val title = this.intent.getStringExtra("h_title")
            contentWebView = findViewById<View>(R.id.webview) as WebView
            bar_title_txt = findViewById<View>(R.id.bar_title_txt) as TextView
            rlyBack = findViewById<View>(R.id.rlyBack) as RelativeLayout
            imgShare = findViewById<View>(R.id.img_right) as ImageView
            imgShare!!.setOnClickListener(btnClickListener)
            imgShare!!.visibility = View.GONE
            rlyBack!!.setOnClickListener(btnClickListener)
            setTitleBarText(title)
            // 启用javascript
            val settings = contentWebView!!.settings
            settings.javaScriptEnabled = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true

            contentWebView!!.webViewClient = object : WebViewClient() {

                override fun onPageStarted(
                        view: WebView, url: String, favicon: Bitmap
                ) {

                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView, url: String) {

                    super.onPageFinished(view, url)
                }
            }

            contentWebView!!.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    // activity的进度是0 to 10000 (both inclusive),所以要*100
                    this@WebViewAct.setProgress(newProgress * 100)
                }
            }
            contentWebView!!.loadUrl(url)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    internal fun setTitleBarText(title: String) {
        bar_title_txt!!.text = title
    }

    override fun fragmentAsView(): BaseFragment? {
        return null
    }

    public override fun onResume() {
        super.onResume()
    }

    companion object {

        fun actionWebView(activity: BaseActivity, title: String, url: String) {
            val intent = Intent(activity, WebViewAct::class.java)
            intent.putExtra("h_url", url)
            intent.putExtra("h_title", title)
            activity.startActivity(intent)
        }
    }
}