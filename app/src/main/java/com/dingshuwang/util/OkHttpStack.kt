package com.dingshuwang.util

import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.HttpStack
import com.squareup.okhttp.Call
import com.squareup.okhttp.Headers
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Protocol
import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.Response
import com.squareup.okhttp.ResponseBody

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.ProtocolVersion
import org.apache.http.StatusLine
import org.apache.http.entity.BasicHttpEntity
import org.apache.http.message.BasicHeader
import org.apache.http.message.BasicHttpResponse
import org.apache.http.message.BasicStatusLine

import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * OkHttp backed [HttpStack] that does not
 * use okhttp-urlconnection
 */
class OkHttpStack(private val mClient: OkHttpClient) : HttpStack {

    @Throws(IOException::class, AuthFailureError::class)
    override fun performRequest(request: Request<*>, additionalHeaders: Map<String, String>): HttpResponse {
        val client = mClient.clone()
        val timeoutMs = request.timeoutMs
        client.setConnectTimeout(timeoutMs.toLong(), TimeUnit.MILLISECONDS)
        client.setReadTimeout(timeoutMs.toLong(), TimeUnit.MILLISECONDS)
        client.setWriteTimeout(timeoutMs.toLong(), TimeUnit.MILLISECONDS)

        val okHttpRequestBuilder = com.squareup.okhttp.Request.Builder()
        okHttpRequestBuilder.url(request.url)


        val headers = request.headers

        for (name in headers.keys) {
            okHttpRequestBuilder.addHeader(name, headers[name])
        }

        for (name in additionalHeaders.keys) {
            okHttpRequestBuilder.addHeader(name, additionalHeaders[name])
        }


        setConnectionParametersForRequest(okHttpRequestBuilder, request)

        val okHttpRequest = okHttpRequestBuilder.build()
        val okHeaders = okHttpRequest.headers()

        //        for (final String name : okHeaders.names()) {
        //            System.out.println("okheaders value = " + okHeaders.values(name));
        //        }
        val okHttpCall = client.newCall(okHttpRequest)
        val okHttpResponse = okHttpCall.execute()

        val responseStatus = BasicStatusLine(
                parseProtocol(okHttpResponse.protocol()), okHttpResponse.code(), okHttpResponse.message())

        val response = BasicHttpResponse(responseStatus)
        response.entity = entityFromOkHttpResponse(okHttpResponse)

        val responseHeaders = okHttpResponse.headers()

        var i = 0
        val len = responseHeaders.size()
        while (i < len) {
            val name = responseHeaders.name(i)
            val value = responseHeaders.value(i)

            if (name != null) {
                response.addHeader(BasicHeader(name, value))
            }
            i++
        }

        return response
    }

    @Throws(IOException::class)
    private fun entityFromOkHttpResponse(r: Response): HttpEntity {
        val entity = BasicHttpEntity()
        val body = r.body()

        entity.content = body.byteStream()
        entity.contentLength = body.contentLength()
        entity.setContentEncoding(r.header("Content-Encoding"))

        if (body.contentType() != null) {
            entity.setContentType(body.contentType().type())
        }
        return entity
    }

    @Throws(IOException::class, AuthFailureError::class)
    private fun setConnectionParametersForRequest(builder: com.squareup.okhttp.Request.Builder, request: Request<*>) {
        when (request.method) {
            Request.Method.DEPRECATED_GET_OR_POST -> {
                // Ensure backwards compatibility.
                // Volley assumes a request with a null body is a GET.
                val postBody = request.postBody

                if (postBody != null) {
                    builder.post(
                            RequestBody.create(MediaType.parse(request.postBodyContentType), postBody))
                }
            }

            Request.Method.GET -> builder.get()

            Request.Method.DELETE -> builder.delete()

            Request.Method.POST -> builder.post(createRequestBody(request))

            Request.Method.PUT -> builder.put(createRequestBody(request))

            Request.Method.HEAD -> builder.head()

            Request.Method.OPTIONS -> builder.method("OPTIONS", null)

            Request.Method.TRACE -> builder.method("TRACE", null)

            Request.Method.PATCH -> builder.patch(createRequestBody(request))

            else -> throw IllegalStateException("Unknown method type.")
        }
    }

    private fun parseProtocol(p: Protocol): ProtocolVersion {
        when (p) {
            Protocol.HTTP_1_0 -> return ProtocolVersion("HTTP", 1, 0)
            Protocol.HTTP_1_1 -> return ProtocolVersion("HTTP", 1, 1)
            Protocol.SPDY_3 -> return ProtocolVersion("SPDY", 3, 1)
            Protocol.HTTP_2 -> return ProtocolVersion("HTTP", 2, 0)
        }

        throw IllegalAccessError("Unkwown protocol")
    }

    @Throws(AuthFailureError::class)
    private fun createRequestBody(r: Request<*>): RequestBody {
        //================================DEFAULT============================================
        // final byte[] body = r.getBody();
        // if (body == null) return null;
        // return RequestBody.create(MediaType.parse(r.getBodyContentType()), body);
        //================================DEFAULT============================================

        //自己重写定制：
        var requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "")
        val body = r.body
        if (null != body) {
            if (r.method == Request.Method.POST || r.method == Request.Method.PUT)
            //POST请求处理
            {
                requestBody = RequestBody.create(MediaType.parse("application/json"), body)
            } else {
                requestBody = RequestBody.create(MediaType.parse(r.bodyContentType), body)
            }
        }

        return requestBody

    }
}