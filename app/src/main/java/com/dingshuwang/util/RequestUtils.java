package com.dingshuwang.util;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dingshuwang.DataView;
import com.dingshuwang.base.BaseActivity;

import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 15:28
 * 说明：网络请求统一管理类
 */
public class RequestUtils
{
    /**
     * 请求url中的数据，默认用et请求
     *
     * @param activity
     * @param url:请求的URL
     * @param dataView:请求结果的回调
     */
    public static void getDataFromUrl(final BaseActivity activity, String url, final DataView dataView, final String requestTag)
    {
        getDataFromUrl(activity, url, dataView, requestTag, false, true);
    }

    public static void getDataFromUrl(final BaseActivity activity, String url, final DataView dataView)
    {
        getDataFromUrl(activity, url, dataView, null, false, true);
    }

    public static void getDataFromUrl(final BaseActivity activity, String url, final DataView dataView, final String requestTag, boolean isShowProgressDialog)
    {
        getDataFromUrl(activity, url, dataView, requestTag, true, true);
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
    public static void getDataFromUrl(final BaseActivity activity, String url, final DataView dataView, final String requestTag, final boolean isNeedAccessToken, final boolean isShowProgressDialog)
    {
        System.out.println("GET 请求URL = " + url);
        Response.Listener responseListener = new Response.Listener()
        {
            @Override
            public void onResponse(Object response)
            {
                final String result = (response != null) ? response.toString() : null;
                if (isShowProgressDialog)
                {
                    activity.dismissProgressDialog();
                }
                if (DebugUtils.INSTANCE.isShowDebug(activity))
                {
                    System.out.println("结果数据 = 【 " + result + " 】");
                }
                dataView.onGetDataSuccess(result, requestTag);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                activity.dismissProgressDialog();
                System.out.println("错误信息 = " + error.getMessage());
                dataView.onGetDataFailured(error.getMessage(), requestTag);
            }
        };
        Request request = new StringRequest(Request.Method.GET, url, responseListener, errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headerMap = new HashMap<>();
//                if (isNeedAccessToken)
//                {
//                    String accessTokenItem = activity.getAccessTokenItem();
//                    if (null != accessTokenItem && !StringUtils.isEmpty(accessTokenItem))
//                    {
//                        headerMap.put("Authorization", "Bearer " + accessTokenItem);
//                        headerMap.put("Content-Type", "application/json");
//                    }
//                }
                return headerMap;
            }
        };
        VolleyUtils.addRequest(activity, request);
        if (isShowProgressDialog)
        {
            activity.showProgressDialog();
        }
    }

    public static void getDataFromUrlByPost(final BaseActivity activity, String url, final String postBody, final DataView dataView, final String requestTag)
    {
        getDataFromUrlByPost(activity, url, postBody, dataView, requestTag, true);
    }

    public static void getDataFromUrlByPost(final BaseActivity activity, String url, final String postBody, final DataView dataView, final String requestTag, boolean isShowProgressDialog)
    {
        getDataFromUrlByPost(activity, url, postBody, dataView, requestTag, true, isShowProgressDialog);
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
    public static void getDataFromUrlByPost(final BaseActivity activity, String url, final String postBody, final DataView dataView, final String requestTag, final boolean needToken, final boolean isShowProgressDialog)
    {
        System.out.println("POST 请求URL = " + url + "\n");
        System.out.println("POST 请求体 = " + postBody);
        Response.Listener responseListener = new Response.Listener()
        {
            @Override
            public void onResponse(Object response)
            {
                final String result = (response != null) ? response.toString() : null;
                if (isShowProgressDialog)
                {
                    activity.dismissProgressDialog();
                }
                if (DebugUtils.INSTANCE.isShowDebug(activity))
                {
                    System.out.println("结果数据 = 【 " + result + " 】");
                }
                dataView.onGetDataSuccess(result, requestTag);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                activity.dismissProgressDialog();
                System.out.println("错误信息 = " + error.getMessage());
                dataView.onGetDataFailured(error.getMessage(), requestTag);
            }
        };
        Request request = new StringRequest(Request.Method.POST, url, responseListener, errorListener)
        {

        };
        if (isShowProgressDialog)
        {
            activity.showProgressDialog();
        }
        VolleyUtils.addRequest(activity, request);
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
    public static void getDataFromUrlByCustomMethod(int method, final BaseActivity activity, String url, final String postBody, final DataView dataView, final String requestTag, final boolean needToken, final boolean isShowProgressDialog)
    {
        System.out.println("POST 请求URL = " + url + "\n");
        System.out.println("POST 请求体 = " + postBody);
        System.out.println("activity = " + activity );
        Response.Listener responseListener = new Response.Listener()
        {
            @Override
            public void onResponse(Object response)
            {
                final String result = (response != null) ? response.toString() : null;
                if (isShowProgressDialog)
                {
                    activity.dismissProgressDialog();
                }
                if (DebugUtils.INSTANCE.isShowDebug(activity))
                {
                    System.out.println("结果数据 = 【 " + result + " 】");
                }
                dataView.onGetDataSuccess(result, requestTag);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                activity.dismissProgressDialog();
                System.out.println("错误信息 = " + error.getMessage());
                dataView.onGetDataFailured(error.getMessage(), requestTag);
            }
        };
        Request request = new StringRequest(method, url, responseListener, errorListener)
        {
            public byte[] getBody() throws AuthFailureError
            {
                byte[] bytes = null;
                if (!TextUtils.isEmpty(postBody))
                {
                    try
                    {
                        bytes = postBody.getBytes(HTTP.UTF_8);
                    } catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                }
                return bytes;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                //POST请求添加的头信息

                HashMap<String, String> headerMap = new HashMap<>();
                if (needToken)
                {
//                    String accessTokenItem = activity.getAccessTokenItem();
//                    if (null != accessTokenItem && !StringUtils.isEmpty(accessTokenItem))
//                    {
//                        headerMap.put("Authorization", "Bearer " + accessTokenItem);
//                        headerMap.put("Content-Type", "application/json");
//                        System.out.println("Bearer = " + accessTokenItem);
//                    }
                }
                return headerMap;
            }
        };
        VolleyUtils.addRequest(activity, request);
        if (isShowProgressDialog)
        {
            activity.showProgressDialog();
        }
    }
}
