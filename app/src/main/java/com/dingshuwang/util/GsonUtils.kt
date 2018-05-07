package com.dingshuwang.util

import android.util.Log

import com.google.gson.Gson

import java.lang.reflect.Type

/**
 * 使用Gson 统一处理json字符串和对象之间的转化
 */
object GsonUtils {
    /**
     * 将json字符串转化为一个对象
     *
     * @param json     ：json字符串
     * @param classOfT ：对象的Class
     * @param <T>      要转化的对象
     * @return null 或者 一个T类型的对象
    </T> */
    fun <T> jsonToClass(json: String, classOfT: Class<T>): T? {
        var t: T? = null
        try {
            t = Gson().fromJson(json, classOfT)
        } catch (e: Exception) {
            Log.i("》》》》  ", "json to class【" + classOfT + "】 解析失败  " + e.message)
        }

        Log.i("》》》》  ", "\nJSON 数据 【$json 】")
        return t
    }

    /**
     * 将json字符串转化为一个对象列表
     *
     * @param json    ：json字符串
     * @param typeOfT ：对象列表的 type
     * @param <T>     要转化的对象
     * @return null 或者 一个对象列表
    </T> */
    fun <T> jsonToList(json: String, typeOfT: Type): List<T>? {
        var items: List<T>? = null
        try {
            items = Gson().fromJson<List<T>>(json, typeOfT)
        } catch (e: Exception) {
            Log.i("》》》》  ", "json to list 解析失败  " + e.message)
        }

        Log.i("》》》》  ", "\n" + json)
        return items
    }

    private fun <T> jsonToList(obj: Any?, typeOfT: Type): List<T>? {
        var items: List<T>? = null
        if (obj != null) {
            items = jsonToList(obj.toString(), typeOfT)
        }
        return items
    }


    /**
     * 将一个对象转化成json字符串
     *
     * @param object
     * @return
     */
    fun toJson(`object`: Any): String {
        return Gson().toJson(`object`)
    }

}
