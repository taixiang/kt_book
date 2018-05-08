package com.dingshuwang.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dingshuwang.Constants
import com.dingshuwang.R
import com.dingshuwang.util.BitmapUtils
import com.dingshuwang.util.PhotoUtils

import butterknife.ButterKnife

/**
 * Created by raiyi-suzhou on 2015/5/11 0011.
 */
abstract class BaseFragment : Fragment(), IFragmentTitle {
    protected lateinit var mActivity: BaseActivity
    /**
     * 一个标识值， 应该在[Fragment.onCreateView]方法中将其修改为true
     */
    protected var mIsInit = false
    protected var mCanPullToRefresh = false
    protected lateinit var mFragment: BaseFragment
    private var mContext: Context? = null
    var isUseButterKnife = true

    private var mProgressDialog: ProgressDialog? = null
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        try {
            mActivity = activity as BaseActivity
            mFragment = this
            mContext = getActivity()
        } catch (e: Exception) {

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isUseButterKnife) {
            if (null != view) {
                ButterKnife.bind(mFragment, view)
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            if (mIsInit) {
                mIsInit = false
                doGetData()
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (mIsInit) {
                mIsInit = false
                doGetData()
            }
        }
    }

    /**
     * 在此方法中进行网络请求操作  ,注意懒加载只有在和ViewPager结合使用时才有效的
     * 如果只是一个单纯的Fragment,需要手动去调用此方法
     */
    protected fun doGetData() {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i("》》》》  ", " base requestCode ===== " + requestCode)
        Log.i("》》》》  ", " base resultcode ===== " + resultCode)
        Log.i("》》》》", "base --------")

        if (resultCode == Activity.RESULT_OK) {
            var imagePath: String? = null
            if (PhotoUtils.REQUEST_FROM_PHOTO == requestCode) {
                if (null != data && null != data.data) {
                    imagePath = PhotoUtils.getFinalPhotoImagePath(mActivity, data.data)
                }
            } else if (PhotoUtils.REQUEST_FROM_CAMERA == requestCode) {
                imagePath = PhotoUtils.finalCameraImagePath
            }
            if (!TextUtils.isEmpty(imagePath)) {
                val intent = Intent(Constants.PHOTO_URL)
                intent.putExtra("photo_url", imagePath)
                mActivity.sendBroadcast(intent)
            }
        }

    }

    /**
     * 结束当前activity
     */
    protected fun jumpBack() {
        activity!!.finish()
    }

    /**
     * 加载下一个activity,不结束自身
     */
    protected fun loadNext(cls: Class<*>) {
        val intent = Intent(mContext, cls)
        startActivity(intent)
        (mContext as Activity).overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out)
    }

    /**
     * 加载下一个activity,不结束自身 {key,value}, {key,value}, {key,value} ...
     *
     * @param cls
     * @param pairs
     */
    protected fun loadNext(cls: Class<*>, vararg pairs: Array<String>) {
        val intent = Intent(mContext, cls)
        for (pair in pairs) {
            intent.putExtra(pair[0], pair[1])
        }
        startActivity(intent)
        (mContext as Activity).overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out)
    }

    /**
     * 加载下一个activity,不结束自身,使用Result模式
     */
    protected fun loadNextForResult(cls: Class<*>, requestCode: Int) {
        val intent = Intent(mContext, cls)
        startActivityForResult(intent, requestCode)
        (mContext as Activity).overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out)
    }

    /**
     * 加载下一个activity,不结束自身,使用Result模式
     */
    protected fun loadNextForResult(cls: Class<*>, requestCode: Int, vararg pairs: Array<String>) {
        val intent = Intent(mContext, cls)
        for (pair in pairs) {
            intent.putExtra(pair[0], pair[1])
        }
        startActivityForResult(intent, requestCode)
        (mContext as Activity).overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out)
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
                mProgressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(0x00000000))
                mProgressDialog!!.setContentView(R.layout.layout_progress_dialog)
            } else {
                mProgressDialog = ProgressDialog(mActivity)
                mProgressDialog!!.show()
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

}
/***
 * 显示进度对话框
 */
