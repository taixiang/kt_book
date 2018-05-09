/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.dingshuwang.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

import com.dingshuwang.R


class XListViewHeader : LinearLayout {
    private var mContainer: LinearLayout? = null
    private var mArrowImageView: ProgressBar? = null
    private val mRotateDownAnim: RotateAnimation? = null
    private var mProgressBar: ProgressBar? = null
    private var mHintTextView: TextView? = null
    private var mState = STATE_NORMAL

    private val ROTATE_ANIM_DURATION = 0

    var visiableHeight: Int
        get() = mContainer!!.height
        set(height) {
            var height = height
            if (height < 0)
                height = 0
            val lp = mContainer!!
                    .layoutParams as LinearLayout.LayoutParams
            lp.height = height
            mContainer!!.layoutParams = lp
        }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    fun getmState(): Int {
        return mState
    }

    /**
     * @param context
     * @param attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    private fun initView(context: Context) {
        try {
            // 初始情况，设置下拉刷新view高度为0
            val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, 0)
            mContainer = LayoutInflater.from(context).inflate(
                    R.layout.xlistview_header, null) as LinearLayout
            addView(mContainer, lp)
            gravity = Gravity.BOTTOM

            mArrowImageView = findViewById<View>(R.id.xlistview_header_arrow) as ProgressBar
            //			xlistview_header_img = (ImageView) findViewById(R.id.xlistview_header_img);
            mHintTextView = findViewById<View>(R.id.xlistview_header_hint_textview) as TextView
            mProgressBar = findViewById<View>(R.id.xlistview_header_progressbar) as ProgressBar

            // mRotateUpAnim = new RotateAnimation(0.0f, 0.0f,
            // Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            // 0.5f);
            // mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
            // mRotateUpAnim.setFillAfter(true);
            // mRotateUpAnim.setRepeatCount(0);
            //
            try {
                // if (mRotateDownAnim == null)
                // mRotateDownAnim = new RotateAnimation(0.0f, 0.0f,
                // Animation.RELATIVE_TO_SELF, 0.5f,
                // Animation.RELATIVE_TO_SELF, 0.5f);
                // mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
                // mRotateDownAnim.setFillAfter(true);
                // mRotateDownAnim.setInterpolator(context,
                // interpolator.accelerate_cubic);
                // mRotateDownAnim.setRepeatCount(0);

            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    fun showAninmial() {
        // TODO Auto-generated method stub
        try {
            //			xlistview_header_img.setVisibility(View.VISIBLE);
            //			AnimationDrawable animationDrawable = (AnimationDrawable) mArrowImageView
            //					.getBackground();
            //			animationDrawable.start();
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    fun setState(state: Int) {
        try {
            if (state == mState)
                return
            if (state == STATE_REFRESHING) { // 显示进度
                //				xlistview_header_img.setVisibility(View.INVISIBLE);
                mArrowImageView!!.visibility = View.VISIBLE
            } else { // 显示箭头图片
                showAninmial()
                // xlistview_header_img.setVisibility(View.VISIBLE);
                mArrowImageView!!.visibility = View.INVISIBLE
            }
            when (state) {
                STATE_NORMAL -> {
                    if (mState == STATE_READY) {
                        mArrowImageView!!.clearAnimation()
                        // mArrowImageView.startAnimation(mRotateDownAnim);
                        showAninmial()
                    }
                    if (mState == STATE_REFRESHING) {
                        mArrowImageView!!.clearAnimation()
                    }
                    mHintTextView!!.setText(R.string.xlistview_header_hint_normal)
                }
                STATE_READY -> if (mState != STATE_READY) {
                    // mArrowImageView.clearAnimation();
                    // mArrowImageView.startAnimation(mRotateDownAnim);
                    showAninmial()
                    mHintTextView!!.setText(R.string.xlistview_header_hint_ready)
                }
                STATE_REFRESHING ->
                    // mArrowImageView.startAnimation(mRotateUpAnim);
                    // mArrowImageView.startAnimation(mRotateDownAnim);
                    // showAninmial();
                    mHintTextView!!.setText(R.string.xlistview_header_hint_loading)
            }
            mState = state
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    companion object {

        val STATE_NORMAL = 0
        val STATE_READY = 1
        val STATE_REFRESHING = 2
    }

}
