package com.dingshuwang.util

import android.util.Log

import com.dingshuwang.DataView
import com.dingshuwang.base.BaseActivity
import com.squareup.okhttp.Callback
import com.squareup.okhttp.Headers
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.MultipartBuilder
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Protocol
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.Response

import org.apache.http.HttpStatus
import org.apache.http.protocol.HTTP

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * Created by raiyi-suzhou on 2015/5/20 0020.
 */
object OkHttpUtils {
    private val sOkHttpClient = OkHttpClient()

    init {
        sOkHttpClient.setConnectTimeout(20, TimeUnit.SECONDS)
        sOkHttpClient.setReadTimeout(20, TimeUnit.SECONDS)
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request：请求
     * @param callback:回调
     */
    fun asyncExecute(request: Request, callback: Callback) {
        sOkHttpClient.newCall(request).enqueue(callback)
    }

    fun cancleRequest(request: Request) {
        sOkHttpClient.cancel(request)
    }

    fun asynExecute(request: Request) {
        asyncExecute(request, object : Callback {
            override fun onFailure(request: Request, e: IOException) {
                //空实现
            }

            @Throws(IOException::class)
            override fun onResponse(response: Response) {

                //空实现
            }
        })
    }

    /**
     * 创建线程请求，返回的字符串结果在Callback#onResponse方法中的message中
     *
     * @param url
     * @param callback
     */
    fun asynExecuteWithThread(url: String, callback: Callback) {
        object : Thread() {
            override fun run() {
                try {
                    val stringBuffer = StringBuffer()
                    val httpURLConnection = URL(url).openConnection() as HttpURLConnection
                    val responsedCode = httpURLConnection.responseCode
                    if (responsedCode == HttpStatus.SC_OK) {
                        val intputstream = httpURLConnection.inputStream
                        var len = -1
                        val buffer = ByteArray(1024)
                        val t = HTTP.UTF_8
                        do {
                            len = intputstream.read(buffer)
                            if (len != -1) {
                                stringBuffer.append(String(buffer, 0, len, HTTP.UTF_8))
                            } else {
                                break
                            }

                        } while (true)

                    }
                    val json = stringBuffer.toString()
                    println("json = $json")
                    callback.onResponse(Response.Builder().request(Request.Builder().url(url).build()).code(HttpStatus.SC_OK).protocol(Protocol.HTTP_1_1).message(json).build())
                } catch (e: IOException) {
                    callback.onFailure(Request.Builder().build(), IOException("请求发生错误"))
                }

            }
        }.start()
    }

    fun customAsyncExecuteWithFile(activity: BaseActivity, url: String, file: File?, needToken: Boolean, dataView: DataView, mRequestTag: String) {
        println(" 请求URL = $url")
        val accessToken = ""
        if (needToken) {
            //            accessToken = activity.getAccessTokenItem();
            //            if (null != accessTokenItem && accessTokenItem.accessToken != null)
            //            {
            //                accessToken = accessTokenItem.accessToken.accessToken;
            //            }
        }
        val headers = Headers.Builder().add("Authorization", "Bearer $accessToken").build()
        val multipartBuilder = MultipartBuilder().type(MultipartBuilder.MIXED)
        //添加提交的文件列表
        if (null != file) {
            if (!file.exists()) {
                println("选中的图片文件不存在")
            }
            val fileBody = RequestBody.create(MediaType.parse("image/png"), file)
            var fileName = file.name
            if (!fileName.endsWith(".png")) {
                fileName = "$fileName.png"
            }
            multipartBuilder.addFormDataPart("files", fileName, fileBody)
        }
        val reqBody = multipartBuilder.build()
        val request = Request.Builder().url(url).headers(headers).post(reqBody).build()

        val callback = object : Callback {
            override fun onFailure(request: Request, e: IOException) {
                activity.runOnUiThread {
                    activity.dismissProgressDialog()
                    dataView.onGetDataFailured(e.message!!, mRequestTag)
                }
            }

            @Throws(IOException::class)
            override fun onResponse(response: Response?) {
                val result = response?.body()?.string()
                activity.runOnUiThread {
                    //                        activity.dismissProgressDialog();
                    dataView.onGetDataSuccess(result!!, mRequestTag)
                }
            }
        }
        asyncExecute(request, callback)
    }

    object TheadUtils {
        // 个人信息编辑
        fun ModifyPhoto(activity: BaseActivity, apiurl: String, filePath: String, needAddToken: Boolean, dataView: DataView, requestTag: String) {
            object : Thread() {
                override fun run() {
                    doUploadPhoto(activity, apiurl, filePath, needAddToken, dataView, requestTag)
                }
            }.start()
        }

        fun doUploadPhoto(activity: BaseActivity, apiurl: String, filePath: String, needAddToken: Boolean, dataView: DataView, requestTag: String) {
            try {
                val buf = ByteArray(1024)
                val file = File(filePath)
                val inputStream = FileInputStream(file)
                val url = URL(apiurl)
                val con = url.openConnection() as HttpURLConnection
                con.connectTimeout = 4000
                con.readTimeout = 4000
                con.requestMethod = "POST"
                con.doOutput = true
                con.doInput = true
                con.setRequestProperty("Charset", "utf-8")
                con.setRequestProperty("connection", "keep-alive")
                con.setRequestProperty("Content-Type", "multipart/form-data")
                val osw = DataOutputStream(con.outputStream)
                var len = 0

                do {
                    len = inputStream.read(buf)
                    if (len != -1) {
                        osw.write(buf, 0, len)
                    } else {
                        break
                    }
                } while (true)

                osw.flush()
                osw.close()
                inputStream.close()
                var result = ""
                if (con.responseCode == 200) {
                    val input = con.inputStream
                    val sb1 = StringBuffer()
                    var ss: Int

                    do {
                        ss = input.read()
                        if(ss != -1){
                            sb1.append(ss.toChar())
                        }else{
                            break
                        }
                    }while (true)

                    result = sb1.toString()
                }

                val finalResult = result
                activity.runOnUiThread {

                    dataView.onGetDataSuccess(finalResult, requestTag)
                }
                return
            } catch (e: Exception) {
                Log.i("》》》》》   ", "" + e.toString())
                e.printStackTrace()
            }

            activity.runOnUiThread {
                activity.dismissProgressDialog()
                //                    activity.showToast("发布失败，请稍后再试");
            }
        }
    }
}
