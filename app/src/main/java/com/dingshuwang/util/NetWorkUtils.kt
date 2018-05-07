package com.dingshuwang.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.telephony.TelephonyManager

import java.util.regex.Matcher
import java.util.regex.Pattern

/***
 * 检查当前的网络是否可用
 *
 * @author 李波
 * @version 1.0
 */
object NetWorkUtils {
    val IP_REGEX = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b"
    /**
     * 判断当前网络是否可用
     *
     * @param context context
     * @return boolean
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }

    /****
     * 是否GPS打开
     *
     * @param context 上下文
     * @return boolean
     */
    fun isGpsEnable(context: Context): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    /***
     * 当前网络是否是WIFI
     *
     * @param context 上下文
     * @return boolean
     */
    fun isWIFI(context: Context): Boolean {
        var isWifi = false
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = manager.activeNetworkInfo
        isWifi = activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
        return isWifi

    }

    /***
     * 当前网络是否是手机网络(3g)
     *
     * @param context 上下文
     * @return boolean
     */
    fun is3G(context: Context): Boolean {
        var is3G = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        is3G = activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE
        return is3G
    }

    /**
     * 当前网络是否是手机网络(4g)
     *
     * @param context 上下文
     * @return boolean
     */
    fun is4G(context: Context): Boolean {
        var is4G = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        is4G = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting && activeNetInfo.type == TelephonyManager.NETWORK_TYPE_LTE

        return is4G
    }

    /***
     * 判断给定的字符串是否是ip字符串
     * @param ipString     ip字符串
     * @return   boolean
     */
    fun isIpString(ipString: String): Boolean {
        val pattern = Pattern
                .compile(IP_REGEX)
        val matcher = pattern.matcher(ipString)
        return matcher.matches()
    }

    /**
     * 弹出一个网络不可以使用的对话框
     *
     * @param context context
     * @return alertdialog
     */
    fun showNetworkDisableDialog(context: Context): AlertDialog {
        val dialog: AlertDialog
        dialog = AlertDialog.Builder(context).create()
        dialog.setTitle("网络状况")
        dialog.setIcon(context.applicationInfo.icon)
        dialog.setMessage("当前网络不可用,请设置网络")
        dialog.setCanceledOnTouchOutside(false)
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "设置") { dialog, which ->
            val intent = Intent(Settings.ACTION_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        return dialog
    }

}
