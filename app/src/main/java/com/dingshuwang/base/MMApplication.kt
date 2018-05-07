package com.dingshuwang.base

import android.app.Application
import android.content.Context

/**
 * @author tx
 * @date 2018/5/7
 */
class MMApplication : Application() {
    companion object {
        var mIsLogin = false
    }


    override fun onCreate() {
        super.onCreate()

    }
}
