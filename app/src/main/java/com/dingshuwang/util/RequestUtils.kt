package com.dingshuwang.util

import android.text.TextUtils

import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.dingshuwang.DataView
import com.dingshuwang.base.BaseActivity

import org.apache.http.protocol.HTTP

import java.io.UnsupportedEncodingException
import java.util.HashMap

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 15:28
 * 说明：网络请求统一管理类
 */
object RequestUtils {

    fun getDataFromUrl(activity: BaseActivity, url: String, dataView: DataView, requestTag: String, isShowProgressDialog: Boolean) {
        getDataFromUrl(activity, url, dataView, requestTag, true, true)
    }

    /***
     * 请求url中的数据，默认用get请求
     *
     * @param activity                       :请求时所在的Activity
     * @param url                            :请求的URL
     * @param dataView                       :请求时UI界面处理
     * @param requestTag                     ：请求TAG
     * @param isNeedAccessToken              :是否需要AccessToken
     * @param isShowProgressDialog:是否显示进度对话框 ,请求出现问题时直接消失，否则根据 isShowProgressDialog判断是否消失
     */
    fun getDataFromUrl(activity: BaseActivity, url: String, dataView: DataView, requestTag: String? = null, isNeedAccessToken: Boolean = false, isShowProgressDialog: Boolean = true) {
        println("GET 请求URL = " + url)
        val responseListener = object : Response.Listener<String> {
            override fun onResponse(response: String?) {
                val result = response?.toString()
                if (isShowProgressDialog) {
                    activity.dismissProgressDialog()
                }
                if (DebugUtils.isShowDebug(activity)) {
                    println("结果数据 = 【 $result 】")
                }
                dataView.onGetDataSuccess(result!!, requestTag!!)
            }
        }
        val errorListener = Response.ErrorListener { error ->
            activity.dismissProgressDialog()
            println("错误信息 = " + error.message)
            dataView.onGetDataFailured(error.message!!, requestTag!!)
        }
        val request = object : StringRequest(Request.Method.GET, url, responseListener, errorListener) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
//                if (isNeedAccessToken)
                //                {
                //                    String accessTokenItem = activity.getAccessTokenItem();
                //                    if (null != accessTokenItem && !StringUtils.isEmpty(accessTokenItem))
                //                    {
                //                        headerMap.put("Authorization", "Bearer " + accessTokenItem);
                //                        headerMap.put("Content-Type", "application/json");
                //                    }
                //                }
                return HashMap()
            }
        }
        VolleyUtils.addRequest(activity, request)
        if (isShowProgressDialog) {
            activity.showProgressDialog()
        }
    }

    @JvmOverloads
    fun getDataFromUrlByPost(activity: BaseActivity, url: String, postBody: String, dataView: DataView, requestTag: String, isShowProgressDialog: Boolean = true) {
        getDataFromUrlByPost(activity, url, postBody, dataView, requestTag, true, isShowProgressDialog)
    }

    /***
     * @param activity             :请求时所在的Activity
     * @param url                  :请求的URL
     * @param postBody             :post请求的内容
     * @param dataView             :请求时UI界面处理
     * @param requestTag           ：请求TAG
     * @param needToken            :是否需要AccessToken
     * @param isShowProgressDialog :是否显示进度对话框 ,请求出现问题时直接消失，否则根据 isShowProgressDialog判断是否消失
     */
    fun getDataFromUrlByPost(activity: BaseActivity, url: String, postBody: String, dataView: DataView, requestTag: String, needToken: Boolean, isShowProgressDialog: Boolean) {
        println("POST 请求URL = " + url + "\n")
        println("POST 请求体 = " + postBody)
        val responseListener = object : Response.Listener<String> {
            override fun onResponse(response: String?) {
                val result = response?.toString()
                if (isShowProgressDialog) {
                    activity.dismissProgressDialog()
                }
                if (DebugUtils.isShowDebug(activity)) {
                    println("结果数据 = 【 $result 】")
                }
                dataView.onGetDataSuccess(result!!, requestTag)
            }
        }
        val errorListener = Response.ErrorListener { error ->
            activity.dismissProgressDialog()
            println("错误信息 = " + error.message)
            dataView.onGetDataFailured(error.message!!, requestTag)
        }
        val request = object : StringRequest(Request.Method.POST, url, responseListener, errorListener) {

        }
        if (isShowProgressDialog) {
            activity.showProgressDialog()
        }
        VolleyUtils.addRequest(activity, request)
    }


    /***
     * @param activity             :请求时所在的Activity
     * @param url                  :请求的URL
     * @param postBody             :post请求的内容
     * @param dataView             :请求时UI界面处理
     * @param requestTag           ：请求TAG
     * @param needToken            :是否需要AccessToken
     * @param isShowProgressDialog :是否显示进度对话框 ,请求出现问题时直接消失，否则根据 isShowProgressDialog判断是否消失
     */
    fun getDataFromUrlByCustomMethod(method: Int, activity: BaseActivity, url: String, postBody: String, dataView: DataView, requestTag: String, needToken: Boolean, isShowProgressDialog: Boolean) {
        println("POST 请求URL = " + url + "\n")
        println("POST 请求体 = " + postBody)
        println("activity = " + activity)
        val responseListener = object : Response.Listener<String> {
             override fun onResponse(response: String?) {
                val result = response
                if (isShowProgressDialog) {
                    activity.dismissProgressDialog()
                }
                if (DebugUtils.isShowDebug(activity)) {
                    println("结果数据 = 【 $result 】")
                }
                dataView.onGetDataSuccess(result!!, requestTag)
            }
        }
        val errorListener = Response.ErrorListener { error ->
            activity.dismissProgressDialog()
            println("错误信息 = " + error.message)
            dataView.onGetDataFailured(error.message!!, requestTag)
        }
        val request = object : StringRequest(method, url, responseListener, errorListener) {
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                var bytes: ByteArray? = null
                if (!TextUtils.isEmpty(postBody)) {
                    try {
                        bytes = postBody.toByteArray(charset(HTTP.UTF_8))
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }

                }
                return bytes
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                //POST请求添加的头信息

                val headerMap = HashMap<String, String>()
                if (needToken) {
                    //                    String accessTokenItem = activity.getAccessTokenItem();
                    //                    if (null != accessTokenItem && !StringUtils.isEmpty(accessTokenItem))
                    //                    {
                    //                        headerMap.put("Authorization", "Bearer " + accessTokenItem);
                    //                        headerMap.put("Content-Type", "application/json");
                    //                        System.out.println("Bearer = " + accessTokenItem);
                    //                    }
                }
                return headerMap
            }
        }
        VolleyUtils.addRequest(activity, request)
        if (isShowProgressDialog) {
            activity.showProgressDialog()
        }
    }
}
/**
 * 请求url中的数据，默认用et请求
 *
 * @param activity
 * @param url:请求的URL
 * @param dataView:请求结果的回调
 */
