package com.dingshuwang.base

import android.annotation.TargetApi
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import com.dingshuwang.R

import butterknife.ButterKnife

/**
 * 使用到的基类Activity
 */
abstract class BaseActivity : AppCompatActivity(), IActivityTitle {
    companion object {
        lateinit var mActivity: BaseActivity
    }

    private var mProgressDialog: ProgressDialog? = null
    private var mFrameContainer: FrameLayout? = null
    private var mTvCenterTitle: TextView? = null
    private var mTvRightTitle: TextView? = null
    private var mRelativeTitleContainer: RelativeLayout? = null
    private var mIvRight: ImageView? = null
    private var mIvBack: ImageView? = null
    var preferences: SharedPreferences? = null
    private var mCurrentToast: Toast? = null

    val mmApplication: MMApplication
        get() = application as MMApplication

    fun getMMApplication(): MMApplication {
        return application as MMApplication
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        preferences = mActivity.getSharedPreferences(packageName, Context.MODE_PRIVATE)
        ButterKnife.bind(mActivity)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    }

    override fun setContentView(layoutResID: Int) {
        val contentView = LayoutInflater.from(mActivity).inflate(layoutResID, null)
        setContentView(contentView)
    }

    override fun setContentView(view: View) {
        super.setContentView(R.layout.activity_base)
        mFrameContainer = findViewById<View>(R.id.frame_container) as FrameLayout
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        mFrameContainer!!.addView(view, layoutParams)
        initTitleBar()
    }

    /***
     * 初始化title bar
     */
    private fun initTitleBar() {
        mTvCenterTitle = findViewById<View>(R.id.tv_center_title) as TextView
        mTvRightTitle = findViewById<View>(R.id.tv_right_title) as TextView
        mIvRight = findViewById<View>(R.id.iv_right) as ImageView
        mRelativeTitleContainer = findViewById<View>(R.id.relative_title_container) as RelativeLayout
        mIvBack = findViewById<View>(R.id.iv_back) as ImageView
    }

    fun hideTitleBar() {
        if (null != mRelativeTitleContainer) {
            mRelativeTitleContainer!!.visibility = View.GONE
        }
        val viewViewLine = findViewById<View>(R.id.view_view_line)
        if (null != viewViewLine) {
            viewViewLine.visibility = View.GONE
        }
    }

    fun showTitleBar() {
        if (null != mRelativeTitleContainer) {
            mRelativeTitleContainer!!.visibility = View.VISIBLE
        }
        val viewViewLine = findViewById<View>(R.id.view_view_line)
        if (null != viewViewLine) {
            viewViewLine.visibility = View.VISIBLE
        }

    }

    protected fun showTitleContainer() {
        if (null != mRelativeTitleContainer) {
            mRelativeTitleContainer!!.visibility = View.VISIBLE
            mIvBack!!.visibility = View.VISIBLE
            val homeIndicatorResId = R.mipmap.ic_back
            mIvBack!!.setImageResource(homeIndicatorResId)
            mIvBack!!.setOnClickListener { onBackPressed() }
        }
    }

    fun setOnBackClickListener(backResId: Int, onClickListener: View.OnClickListener) {
        if (null != mIvBack) {
            if (mIvBack!!.visibility == View.GONE) {
                mIvBack!!.visibility = View.VISIBLE
            }
            if (backResId != 0) {
                mIvBack!!.setImageResource(backResId)
            }
            mIvBack!!.setOnClickListener(onClickListener)
        }
    }

    /***
     * 隐藏标题栏左侧的返回imageview
     */
    fun hideBackImageView() {
        if (mIvBack != null && mIvBack!!.visibility == View.VISIBLE) {
            mIvBack!!.visibility = View.GONE
        }
    }

    /***
     * 控制标题栏右侧的imageview是否显示
     *
     * @param isVisibility true：显示 、false 隐藏
     */
    fun setRightImageViewVisibility(isVisibility: Boolean) {
        if (mIvRight != null) {
            if (isVisibility) {
                mIvRight!!.visibility = View.VISIBLE
            } else {
                mIvRight!!.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        hideSoftKeyBorard()

        super.onBackPressed()
    }

    fun setOnRightClickListener(rightResId: Int, onClickListener: View.OnClickListener) {
        if (null != mIvRight) {
            if (mIvRight!!.visibility == View.GONE) {
                mIvRight!!.visibility = View.VISIBLE
            }
            if (rightResId != 0) {
                mIvRight!!.setImageResource(rightResId)
            }
            mIvRight!!.setOnClickListener(onClickListener)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /***
     * 显示进度对话框
     *
     * @param msg ：显示的提示文字
     */
    @JvmOverloads
    fun showProgressDialog(msg: String = "请稍等，正在加载数据……") {
        try {

            if (null != mProgressDialog) {
                mProgressDialog = null
                mProgressDialog = ProgressDialog(mActivity)
                mProgressDialog!!.show()
                mProgressDialog!!.setCanceledOnTouchOutside(false)
                mProgressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(0x00000000))
                mProgressDialog!!.setContentView(R.layout.layout_progress_dialog)
            } else {
                mProgressDialog = ProgressDialog(mActivity)
                mProgressDialog!!.show()
                mProgressDialog!!.setCanceledOnTouchOutside(false)
                mProgressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(0x00000000))
                mProgressDialog!!.setContentView(R.layout.layout_progress_dialog)
            }
        } catch (e: Exception) {

        }

    }

    fun dismissProgressDialog() {
        try {
            if (null != mProgressDialog) {
                if (mProgressDialog!!.isShowing) {
                    mProgressDialog!!.dismiss()
                }
                mProgressDialog = null
            }
        } catch (e: Exception) {

        }

    }

    fun setCenterTitle(centerTitle: CharSequence) {
        mTvCenterTitle!!.text = centerTitle
    }

    fun setCenterTitle(centerTitle: CharSequence, textColor: Int) {
        mTvCenterTitle!!.setTextColor(textColor)
        mTvCenterTitle!!.text = centerTitle
    }

    fun setRightTitleWithClickWithColor(rightTitle: CharSequence, textColor: Int, onClickListener: View.OnClickListener) {
        if (null != mTvRightTitle) {
            mTvRightTitle!!.visibility = View.VISIBLE
            mTvRightTitle!!.setTextColor(textColor)
            mTvRightTitle!!.text = rightTitle
            mTvRightTitle!!.setOnClickListener(onClickListener)

        }
    }

    /**
     * 隐藏软键盘，根据当前焦点View
     */
    fun hideSoftKeyBorard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            if (imm.isActive) {
                val focusView = mActivity.currentFocus
                if (null != focusView) {
                    imm.hideSoftInputFromWindow(focusView.windowToken, 0)

                }
            }
        }
    }

    /**
     * 隐藏软键盘，根据给定的View
     *
     * @param view
     */
    fun hideSoftKeyBorard(view: View?) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive && null != view) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showToast(text: String) {
        if (null != mCurrentToast) {
            mCurrentToast!!.cancel()
        }
        mCurrentToast = Toast.makeText(mActivity, text, Toast.LENGTH_SHORT)
        mCurrentToast!!.show()
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyBorard()
    }

    /**
     * 加载下一个activity,不结束自身
     */
    protected fun loadNext(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
    }

    /**
     * 加载下一个activity,不结束自身 {key,value}, {key,value}, {key,value} ...
     *
     * @param cls
     * @param pairs
     */
    fun loadNext(cls: Class<*>, vararg pairs: Array<String>) {
        val intent = Intent(this, cls)
        for (pair in pairs) {
            intent.putExtra(pair[0], pair[1])
        }
        startActivity(intent)
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
    }

    /***
     * 返回
     *
     * @param cls
     */
    protected fun jumpBack(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }

    /**
     * 结束当前activity
     */
    protected fun jumpBack() {
        finish()
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }



}
/***
 * 显示进度对话框
 */
