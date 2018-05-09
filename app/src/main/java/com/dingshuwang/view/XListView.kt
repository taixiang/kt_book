/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * Implement IXListViewListener, and see stopRefresh() / stopLoadMore().
 */
package com.dingshuwang.view


import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.DecelerateInterpolator
import android.widget.AbsListView
import android.widget.AbsListView.OnScrollListener
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.Scroller
import android.widget.TextView

import com.dingshuwang.R
import com.dingshuwang.base.MMApplication
import com.dingshuwang.util.UIUtil


class XListView : ListView, OnScrollListener {

    private var mLastY = -1f // save event y
    private var mScroller: Scroller? = null // used for scroll back
    private var mScrollListener: OnScrollListener? = null // user's scroll listener

    // the interface to trigger refresh and load more.
    private var mListViewListener: IXListViewListener? = null

    // -- header view
    private var mHeaderView: XListViewHeader? = null
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private var mHeaderViewContent: RelativeLayout? = null
    private var mHeaderTimeView: TextView? = null
    private var mHeaderViewHeight: Int = 0 // header view's height
    private var mEnablePullRefresh = true
    private var mPullRefreshing = false // is refreashing.

    // -- footer view
    private var mFooterView: XListViewFooter? = null
    private var mEnablePullLoad: Boolean = false
    private var mPullLoading: Boolean = false
    private var mIsFooterReady = false

    // total list items, used to detect is at the bottom of listview.
    private var mTotalItemCount: Int = 0

    // for mScroller, scroll back from header or footer.
    private var mScrollBack: Int = 0
    // feature.

    private var mContext: Context? = null
    var isLoadMore = false

    // 滑动距离及坐标
    private var xDistance: Float = 0.toFloat()
    private var yDistance: Float = 0.toFloat()
    private var xLast: Float = 0.toFloat()
    private var yLast: Float = 0.toFloat()

    fun ismPullLoading(): Boolean {
        return mPullRefreshing
    }


    fun getmHeaderView(): XListViewHeader? {
        return mHeaderView
    }

    /**
     * @param context
     */
    constructor(context: Context) : super(context) {
        mContext = context
        initWithContext(context, isLoadMore)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // mGestureDetector = new GestureDetector(new YScrollDetector());
        // setFadingEdgeLength(0);
        mContext = context
        initWithContext(context, isLoadMore)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        mContext = context
        initWithContext(context, isLoadMore)
    }

    internal inner class YScrollDetector : SimpleOnGestureListener() {
        override fun onScroll(e1: MotionEvent, e2: MotionEvent,
                              distanceX: Float, distanceY: Float): Boolean {
            if (distanceY != 0f && distanceX != 0f) {
                return false
            }
            return if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                true
            } else false
        }
    }

    private fun initWithContext(context: Context, isLoadMore: Boolean) {
        try {
            mScroller = Scroller(context, DecelerateInterpolator())
            // XListView need the scroll event, and it will dispatch the event
            // to
            // user's listener (as a proxy).
            super.setOnScrollListener(this)

            // init header view
            mHeaderView = XListViewHeader(context)
            mHeaderViewContent = mHeaderView!!
                    .findViewById<View>(R.id.xlistview_header_content) as RelativeLayout
            mHeaderTimeView = mHeaderView!!
                    .findViewById<View>(R.id.xlistview_header_time) as TextView
            addHeaderView(mHeaderView)

            // init footer view
            mFooterView = XListViewFooter(context, isLoadMore)

            // init header height
            mHeaderView!!.viewTreeObserver.addOnGlobalLayoutListener(
                    object : OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            mHeaderViewHeight = mHeaderViewContent!!.height
                            viewTreeObserver.removeGlobalOnLayoutListener(
                                    this)
                        }
                    })
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    override fun setAdapter(adapter: ListAdapter) {
        // make sure XListViewFooter is the last footer view, and only add once.
        try {
            if (mIsFooterReady == false) {
                mIsFooterReady = true
                addFooterView(mFooterView)
            }
            super.setAdapter(adapter)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    fun showFooter() {
        removeFooterView(mFooterView)
        addFooterView(mFooterView)
        mFooterView!!.show()
        setPullLoadEnable(true)
        mFooterView!!.visibility = View.VISIBLE
    }

    fun goneFooter() {
        try {
            removeFooterView(mFooterView)
            mFooterView!!.hide()
            setPullLoadEnable(false)
            mFooterView!!.visibility = View.GONE
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    @SuppressLint("NewApi")
    fun showHeader() {
        try {
            removeHeaderView(mHeaderView)
            addHeaderView(mHeaderView)
            setPullRefreshEnable(true)
            mHeaderView!!.isActivated = true
            mHeaderView!!.visibility = View.VISIBLE
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    @SuppressLint("NewApi")
    fun goneHeader() {
        removeHeaderView(mFooterView)
        setPullRefreshEnable(false)
        mHeaderView!!.isActivated = false
        mHeaderView!!.removeAllViews()
        mHeaderView!!.visibility = View.GONE
    }

    /**
     * enable or disable pull down refresh feature.
     *
     * @param enable
     */
    fun setPullRefreshEnable(enable: Boolean) {
        try {
            mEnablePullRefresh = enable
            if (!mEnablePullRefresh) { // disable, hide the content
                mHeaderViewContent!!.visibility = View.INVISIBLE
            } else {
                mHeaderViewContent!!.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    fun setPullLoadEnable(enable: Boolean) {
        try {
            mEnablePullLoad = enable
            if (!mEnablePullLoad) {
                mFooterView!!.hide()
                mFooterView!!.setOnClickListener(null)
            } else {
                mPullLoading = false
                mFooterView!!.show()
                mFooterView!!.setState(XListViewFooter.STATE_NORMAL)
                // both "pull up" and "click" will invoke load more.
                mFooterView!!.setOnClickListener { startLoadMore() }
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    /**
     * stop refresh, reset header view.
     */
    fun stopRefresh() {
        try {
            if (mPullRefreshing == true) {
                mPullRefreshing = false
                resetHeaderHeight()
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    /**
     * stop load more, reset footer view.
     */
    fun stopLoadMore() {
        try {
            if (mPullLoading == true) {
                mPullLoading = false
                mFooterView!!.setState(XListViewFooter.STATE_NORMAL)
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    /**
     * set last refresh time
     *
     * @param time
     */
    fun setRefreshTime(time: String) {
        try {
            mHeaderTimeView!!.text = time
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    private fun invokeOnScrolling() {
        try {
            if (mScrollListener is OnXScrollListener) {
                val l = mScrollListener as OnXScrollListener?
                l!!.onXScrolling(this)
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    private fun updateHeaderHeight(delta: Float) {
        try {
            mHeaderView!!.visiableHeight = delta.toInt() + mHeaderView!!.visiableHeight
            if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
                if (mHeaderView!!.visiableHeight > mHeaderViewHeight) {
                    mHeaderView!!.setState(XListViewHeader.STATE_READY)
                } else {
                    mHeaderView!!.setState(XListViewHeader.STATE_NORMAL)
                }
            }
            setSelection(0) // scroll to top each time
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    /**
     * reset header view's height.
     */
    private fun resetHeaderHeight() {
        try {
            val height = mHeaderView!!.visiableHeight
            if (height == 0)
            // not visible.
                return
            // refreshing and header isn't shown fully. do nothing.
            if (mPullRefreshing && height <= mHeaderViewHeight) {
                return
            }
            var finalHeight = 0 // default: scroll back to dismiss header.
            // is refreshing, just scroll back to show all the header.
            if (mPullRefreshing && height > mHeaderViewHeight) {
                finalHeight = mHeaderViewHeight
            }
            mScrollBack = SCROLLBACK_HEADER
            mScroller!!.startScroll(0, height, 0, finalHeight - height,
                    SCROLL_DURATION)
            // trigger computeScroll
            invalidate()
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    private fun updateFooterHeight(delta: Float) {
        try {
            val height = delta.toInt()
            // mFooterView.getBottomMargin() + (int) delta;
            if (mEnablePullLoad && !mPullLoading) {
                if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke
                    // load
                    // more.
                    mFooterView!!.setState(XListViewFooter.STATE_READY)
                } else {
                    mFooterView!!.setState(XListViewFooter.STATE_NORMAL)
                }
            }
            mFooterView!!.bottomMargin = height
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        // setSelection(mTotalItemCount - 1); // scroll to bottom
    }

    private fun resetFooterHeight() {
        try {
            val bottomMargin = mFooterView!!.bottomMargin
            if (bottomMargin > 0) {
                mScrollBack = SCROLLBACK_FOOTER
                mScroller!!.startScroll(0, bottomMargin, 0, -bottomMargin,
                        SCROLL_DURATION)
                invalidate()
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    private fun startLoadMore() {
        try {
            mPullLoading = true
            mFooterView!!.setState(XListViewFooter.STATE_LOADING)
            if (mListViewListener != null) {
                // by steven when on network dealwith
                if (UIUtil.isConnect(mContext!!)) {
                    mListViewListener!!.onLoadMore()
                } else {
                    MMApplication.setNetWork(mContext!!)
                    stopLoadMore()
                }
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        try {
            if (mLastY == -1f) {
                mLastY = ev.rawY
            }
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> mLastY = ev.rawY
                MotionEvent.ACTION_MOVE -> {
                    val deltaY = ev.rawY - mLastY
                    mLastY = ev.rawY
                    if (firstVisiblePosition == 0 && (mHeaderView!!.visiableHeight > 0 || deltaY > 0)) {
                        // the first item is showing, header has shown or pull down.
                        updateHeaderHeight(deltaY / OFFSET_RADIO)
                        invokeOnScrolling()
                    } else if (lastVisiblePosition == mTotalItemCount - 1 && (mFooterView!!.bottomMargin > 0 || deltaY < 0)) {
                        // last item, already pulled up or want to pull up.
                        updateFooterHeight(-deltaY / OFFSET_RADIO)
                    }
                }
                else -> {
                    mLastY = -1f // reset
                    if (firstVisiblePosition == 0) {
                        // invoke refresh
                        if (mEnablePullRefresh && mHeaderView!!.visiableHeight > mHeaderViewHeight) {
                            mPullRefreshing = true
                            mHeaderView!!.setState(XListViewHeader.STATE_REFRESHING)
                            if (mListViewListener != null) {
                                // by steven when on network dealwith
                                if (UIUtil.isConnect(mContext!!)) {
                                    mListViewListener!!.onRefresh()
                                } else {
                                    // set network stopRefreh
                                    MMApplication.setNetWork(mContext!!)
                                    stopRefresh()
                                }
                            }
                        }
                        resetHeaderHeight()
                    } else if (lastVisiblePosition == mTotalItemCount - 1) {
                        // invoke load more.
                        if (mEnablePullLoad && mFooterView!!.bottomMargin > PULL_LOAD_MORE_DELTA) {
                            startLoadMore()
                        }
                        resetFooterHeight()
                    }
                }
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        try {
            return super.onTouchEvent(ev)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            return false
        }

    }

    override fun computeScroll() {
        try {
            if (mScroller!!.computeScrollOffset()) {
                if (mScrollBack == SCROLLBACK_HEADER) {
                    mHeaderView!!.visiableHeight = mScroller!!.currY
                } else {
                    mFooterView!!.bottomMargin = mScroller!!.currY
                }
                postInvalidate()
                invokeOnScrolling()
            }
            super.computeScroll()
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    override fun setOnScrollListener(l: OnScrollListener) {
        mScrollListener = l
    }

    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
        if (mScrollListener != null) {
            mScrollListener!!.onScrollStateChanged(view, scrollState)
        }
    }

    override fun onScroll(view: AbsListView, firstVisibleItem: Int,
                          visibleItemCount: Int, totalItemCount: Int) {
        // send to user's listener
        try {
            mTotalItemCount = totalItemCount
            if (mScrollListener != null) {
                mScrollListener!!.onScroll(view, firstVisibleItem,
                        visibleItemCount, totalItemCount)
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    fun setXListViewListener(l: IXListViewListener) {
        mListViewListener = l
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    interface OnXScrollListener : OnScrollListener {
        fun onXScrolling(view: View)
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    interface IXListViewListener {
        fun onRefresh()

        fun onLoadMore()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        try {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    yDistance = 0f
                    xDistance = yDistance
                    xLast = ev.x
                    yLast = ev.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val curX = ev.x
                    val curY = ev.y

                    xDistance += Math.abs(curX - xLast)
                    yDistance += Math.abs(curY - yLast)
                    xLast = curX
                    yLast = curY

                    if (xDistance > yDistance) {
                        return false // 表示向下传递事件
                    }
                }
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return super.onInterceptTouchEvent(ev)
    }

    companion object {
        private val SCROLLBACK_HEADER = 0
        private val SCROLLBACK_FOOTER = 1

        private val SCROLL_DURATION = 400 // scroll back duration
        private val PULL_LOAD_MORE_DELTA = 20 // when pull up >= 50px
        // at bottom, trigger
        // load more.
        private val OFFSET_RADIO = 1.8f // support iOS like pull
    }
}
