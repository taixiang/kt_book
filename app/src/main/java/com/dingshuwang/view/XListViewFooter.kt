/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.dingshuwang.view


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.dingshuwang.R


class XListViewFooter : LinearLayout {

    private var mContext: Context? = null

    private var mContentView: View? = null
    private var mProgressBar: View? = null
    private var mHintView: TextView? = null
    internal var txt = ""

    var bottomMargin: Int
        get() {
            val lp = mContentView!!
                    .layoutParams as LinearLayout.LayoutParams
            return lp.bottomMargin
        }
        set(height) {
            if (height < 0) {
                return
            }

            val lp = mContentView!!
                    .layoutParams as LinearLayout.LayoutParams
            lp.bottomMargin = height
            mContentView!!.layoutParams = lp
        }

    constructor(context: Context, loadMore: Boolean) : super(context) {
        initView(context, loadMore)
    }

    constructor(context: Context, attrs: AttributeSet, loadMore: Boolean) : super(context, attrs) {
        initView(context, loadMore)
    }

    fun setState(state: Int) {
        mHintView!!.visibility = View.INVISIBLE
        mProgressBar!!.visibility = View.INVISIBLE
        if (state == STATE_READY) {
            mHintView!!.visibility = View.VISIBLE
            mHintView!!.text = txt
        } else if (state == STATE_LOADING) {
            mProgressBar!!.visibility = View.VISIBLE
        } else {
            mHintView!!.visibility = View.VISIBLE
            mHintView!!.text = txt
        }
    }

    /**
     * normal status
     */
    fun normal() {
        mHintView!!.visibility = View.VISIBLE
        mProgressBar!!.visibility = View.GONE
    }

    /**
     * loading status
     */
    fun loading() {
        mHintView!!.visibility = View.GONE
        mProgressBar!!.visibility = View.VISIBLE
    }

    /**
     * hide footer when disable pull load more
     */
    fun hide() {
        val lp = mContentView!!
                .layoutParams as LinearLayout.LayoutParams
        lp.height = 0
        mContentView!!.layoutParams = lp
    }

    /**
     * show footer
     */
    fun show() {
        val lp = mContentView!!
                .layoutParams as LinearLayout.LayoutParams
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT
        mContentView!!.layoutParams = lp
    }

    private fun initView(context: Context, loadMore: Boolean) {
        mContext = context
        val moreView = LayoutInflater.from(mContext)
                .inflate(R.layout.xlistview_footer, null) as LinearLayout
        addView(moreView)
        moreView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mContentView = moreView.findViewById(R.id.xlistview_footer_content)
        mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar)
        mHintView = moreView
                .findViewById<View>(R.id.xlistview_footer_hint_textview) as TextView
        if (loadMore) {
            txt = resources.getString(
                    R.string.xlistview_footer_hint_onclick)
        } else {
            txt = resources.getString(
                    R.string.xlistview_footer_hint_normal)
        }
        mHintView!!.text = txt
    }

    companion object {
        val STATE_NORMAL = 0
        val STATE_READY = 1
        val STATE_LOADING = 2
    }
}
