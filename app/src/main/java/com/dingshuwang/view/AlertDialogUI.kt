package com.dingshuwang.view

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

import com.dingshuwang.R


/**
 * 自定义 dialog
 *
 * @author Davis
 */
class AlertDialogUI(context: Context) {
    internal var context: Context ?= null
    internal var ad: android.app.AlertDialog? = null
    internal lateinit var titleView: TextView
    internal lateinit var messageView: TextView
    internal lateinit var btn_sure: TextView
    internal lateinit var btn_cancle: TextView

    init {

        // TODO Auto-generated constructor stub
        try {
            this.context = context
            val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            if (ad == null) {
                ad = android.app.AlertDialog.Builder(context).create()
            }
            if (!ad!!.isShowing) {
                ad!!.show()
            }
            // 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
            // Window window = ad.getWindow();
            // window.setContentView(R.layout.alertdialog);
            val layout = inflater.inflate(R.layout.alertdialog, null)

            val dialogWindow = ad!!.window
            val metrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay
                    .getMetrics(metrics)
            val width = (metrics.widthPixels * 0.8).toInt()
            val layoutParams = ad!!.window!!
                    .attributes
            layoutParams.width = width
            dialogWindow!!.attributes = layoutParams

            ad!!.setContentView(layout, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT))

            titleView = layout.findViewById<View>(R.id.title) as TextView
            messageView = layout.findViewById<View>(R.id.message) as TextView
            btn_sure = layout.findViewById<View>(R.id.btn_sure) as TextView
            btn_cancle = layout.findViewById<View>(R.id.btn_cancle) as TextView
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    fun showDialog() {
        if (null != ad && !ad!!.isShowing) {
            ad!!.show()
        }
    }

    fun setDisMissListener() {

    }

    fun setTitle(resId: Int) {
        titleView.visibility = View.VISIBLE
        titleView.setText(resId)
    }

    fun setTitle(title: String) {
        try {
            titleView.visibility = View.VISIBLE
            titleView.text = title
        } catch (e: Exception) {

        }

    }

    fun setMessage(resId: Int) {
        messageView.setText(resId)
    }

    fun setMessage(message: String) {
        try {
            messageView.text = message
        } catch (e: Exception) {

        }

    }

    fun setCanceledOnTouchOutside(flag: Boolean) {
        ad!!.setCanceledOnTouchOutside(flag)
    }

    fun setCancleAble(flag: Boolean) {
        ad!!.setCancelable(flag)
    }

    /**
     * 设置按钮
     *
     * @param text
     * @param listener
     */
    fun setPositiveButton(text: String,
                          listener: View.OnClickListener) {
        btn_sure.text = text
        btn_sure.setOnClickListener(listener)
    }

    /**
     * 设置按钮
     *
     * @param text
     * @param listener
     */
    fun setNegativeButton(text: String,
                          listener: View.OnClickListener) {
        btn_cancle.text = text
        btn_cancle.visibility = View.VISIBLE
        btn_cancle.setOnClickListener(listener)
    }

    /**
     * 关闭对话框
     */
    fun dismiss() {
        try {
            ad!!.dismiss()
        } catch (e: Exception) {
            // TODO: handle exception
        }

    }

}