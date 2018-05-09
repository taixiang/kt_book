package com.dingshuwang.base

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import com.dingshuwang.view.AlertDialogUI


/**
 * @author tx
 * @date 2018/5/7
 */
class MMApplication : Application() {
    companion object {
        var mIsLogin = false
        var alertbBuilder: AlertDialogUI? = null

        fun setNetWork(context: Context) {
            try {
                if (null == alertbBuilder) {
                    alertbBuilder = AlertDialogUI(context)
                } else {
                    alertbBuilder!!.dismiss()
                    alertbBuilder = null
                    alertbBuilder = AlertDialogUI(context)
                }
                if (null != alertbBuilder) {
                    alertbBuilder!!.setTitle("未打开网络")
                    alertbBuilder!!.setMessage("请打开您的网络连接，稍后再试!")
                    alertbBuilder!!.setPositiveButton("网络设置", object : View.OnClickListener {
                        override fun onClick(v: View) {
                            context.startActivity(Intent(
                                    android.provider.Settings.ACTION_SETTINGS))
                            alertbBuilder!!.dismiss()
                        }
                    })
                    alertbBuilder!!.setNegativeButton("取消", object : View.OnClickListener {
                        override fun onClick(v: View) {

                            // ((Activity)context).finish();
                            alertbBuilder!!.dismiss()
                        }
                    })
                }
            } catch (e: Exception) {

                e.printStackTrace()
            }

        }
    }


    override fun onCreate() {
        super.onCreate()

    }



}
