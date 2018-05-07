package com.dingshuwang.util


import com.dingshuwang.DataView
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.APIResult


object AccessTokenUtils {
    /***
     * 获取Access Code请求的TAG
     */
    val REQUEST_ACCESS_CODE_TAG = "access_code"
    /***
     * 获取Access Token请求的TAG
     */
    val REQUEST_ACCESS_TOKEN_TAG = "access_token"

    /***
     * 获取Access Code
     *
     * @param userName
     * @param pwd
     */
    fun doGetAccessToken(activity: BaseActivity, userName: CharSequence, pwd: CharSequence, onAccessToken: OnAccessTokenListener) {
        val accessCodeUrl = ""
        activity.showProgressDialog()
        val dataView = object : DataView {
            override fun onGetDataFailured(msg: String, requestTag: String) {
                onAccessToken.onAccessTokenFailured()
            }

            override fun onGetDataSuccess(result: String, requestTag: String) {
                val apiResult = GsonUtils.jsonToClass(result, APIResult::class.java)
                if (null != apiResult) {
                    onAccessToken.onAccessTokenSuccess()
                }
            }
        }
        RequestUtils.getDataFromUrlByPost(activity, accessCodeUrl, "", dataView, REQUEST_ACCESS_CODE_TAG, false)
    }

    interface OnAccessTokenListener {
        fun onAccessTokenSuccess()

        fun onAccessTokenFailured()

    }
}
