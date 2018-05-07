package com.dingshuwang.util

import android.text.TextUtils

import com.dingshuwang.APIURL
import com.dingshuwang.DataView
import com.dingshuwang.PREF
import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.APIResult


/**
 * 登录的工具类
 */
object LoginUtils {

    fun doLogin(activity: BaseActivity, userName: CharSequence, pwd: CharSequence, onLoginListener: OnLoginListener) {
        //点击登录时，清除之前的access token 信息，
        //        activity.getPreferences().edit().remove(PREF.PREF_ACCESS_TOKEN).commit();
        val onAccessToken = object : AccessTokenUtils.OnAccessTokenListener {
            override fun onAccessTokenSuccess() {
                doGetUserInfo(activity, onLoginListener)
            }

            override fun onAccessTokenFailured() {
                activity.dismissProgressDialog()
                onLoginListener.onLoginFailured(activity.getString(R.string.tv_login_failured_msg))
            }
        }
    }

    interface OnLoginListener {
        //        public void onLoginSuccess(UserInfoItem userInfoItem);

        fun onLoginFailured(failuredMsg: String)
    }

    fun doGetUserInfo(activity: BaseActivity, onLoginListener: OnLoginListener) {
        //        String useInfoUrl = APIURL.GET_USER_INFO;
        val useInfoUrl = ""
        val dataView = object : DataView {
            override fun onGetDataFailured(msg: String, requestTag: String) {
                onLoginListener.onLoginFailured(activity.getString(R.string.tv_login_failured_msg))
            }

            override fun onGetDataSuccess(result: String, requestTag: String) {
                activity.dismissProgressDialog()
                val apiResult = GsonUtils.jsonToClass(result, APIResult::class.java)

                onLoginListener.onLoginFailured(activity.getString(R.string.tv_login_failured_msg))
            }
        }
        RequestUtils.getDataFromUrl(activity, useInfoUrl, dataView, null, true, true)
    }


    fun doGetUserInfo(activity: BaseActivity, dataView: DataView) {
        //        String useInfoUrl = APIURL.GET_USER_INFO;
        //        RequestUtils.getDataFromUrl(activity, useInfoUrl, dataView, null, true, true);
    }

    fun doGetUserInfo(activity: BaseActivity, dataView: DataView, requestTag: String) {
        //        String useInfoUrl = APIURL.getUserInfo;
        //        RequestUtils.getDataFromUrl(activity, useInfoUrl, dataView, requestTag, true, false);
    }

}
