package com.dingshuwang.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff.Mode
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.hardware.Camera
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Surface
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup.LayoutParams
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.dingshuwang.LoginActivity
import com.dingshuwang.R
import com.dingshuwang.view.AlertDialogUI

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Field
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList
import java.util.Date
import java.util.Hashtable


object UIUtil {

    private var toast: Toast? = null

    private var lastclickTime: Long = 0
    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    fun subZeroAndDot(s: String): String {
        var s = s
        if (s.indexOf(".") > 0) {
            s = s.replace("0+?$".toRegex(), "")// 去掉多余的0
            s = s.replace("[.]$".toRegex(), "")// 如最后一位是.则去掉
        }
        return s
    }

    /***
     * 查看大图
     * @param position
     * @param imgList
     * @param mContext
     */

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName
     * 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    fun isServiceWork(mContext: Context, serviceName: String): Boolean {
        var isWork = false
        val myAM = mContext
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val myList = myAM.getRunningServices(40)
        if (myList.size <= 0) {
            return false
        }
        for (i in myList.indices) {
            val mName = myList[i].service.className.toString()
            if (mName == serviceName) {
                isWork = true
                break
            }
        }
        return isWork
    }

    /** 求差  */
    fun getSubtraction(float1: Float, float2: Float): Float {
        val b1 = BigDecimal(java.lang.Float.toString(float1))
        val b2 = BigDecimal(java.lang.Float.toString(float2))
        return if (b1.subtract(b2).toFloat() <= 0) {
            0f
        } else b1.subtract(b2).toFloat()
    }

    fun getSubtraction(float1: Double, float2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(float1))
        val b2 = BigDecimal(java.lang.Double.toString(float2))
        return if (b1.subtract(b2).toDouble() <= 0) {
            0.0
        } else b1.subtract(b2).toDouble()
    }

    fun getSubtraction_(float1: Double, float2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(float1))
        val b2 = BigDecimal(java.lang.Double.toString(float2))
        return if (b1.subtract(b2).toDouble() <= 0) {
            0.0
        } else b1.subtract(b2).setScale(5, BigDecimal.ROUND_DOWN).toDouble()
    }

    /*** 求和  */
    fun getAdd(float1: Float, float2: Float): Float {
        val b1 = BigDecimal(java.lang.Float.toString(float1))
        val b2 = BigDecimal(java.lang.Float.toString(float2))
        return b1.add(b2).setScale(5, BigDecimal.ROUND_FLOOR).toFloat()
    }

    fun getAdd(float1: Double, float2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(float1))
        val b2 = BigDecimal(java.lang.Double.toString(float2))
        return b1.add(b2).setScale(5, BigDecimal.ROUND_DOWN).toDouble()
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1
     * 被乘数
     * @param v2
     * 乘数
     * @return 两个参数的积
     */
    fun mul(v1: Float, v2: Float): Float {
        val b1 = BigDecimal(java.lang.Float.toString(v1))
        val b2 = BigDecimal(java.lang.Float.toString(v2))
        return b1.multiply(b2).toFloat()
    }

    fun mul(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.multiply(b2).setScale(5, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun mul_(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.multiply(b2).setScale(5, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /***
     * 除法
     *
     * @param v1
     * @param v2
     * @return
     */
    fun divide(v1: Float, v2: Float): Float {
        if (v2 <= 0) {
            return 0f
        }
        val b1 = BigDecimal(java.lang.Float.toString(v1))
        val b2 = BigDecimal(java.lang.Float.toString(v2))
        return b1.divide(b2, 2).setScale(2, BigDecimal.ROUND_FLOOR)
                .toFloat()
    }

    fun divide(v1: Double, v2: Double): Double {
        if (v2 <= 0) {
            return 0.0
        }
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).setScale(3, BigDecimal.ROUND_FLOOR)
                .toFloat().toDouble()
    }

    fun divide_(v1: Double, v2: Double): Double {
        if (v2 <= 0) {
            return 0.0
        }
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).setScale(3, BigDecimal.ROUND_FLOOR)
                .toFloat().toDouble()
    }


    /**
     * 用来判断服务是否运行.
     *
     * @paramcontext
     * @param className
     * 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    fun isServiceRunning(mContext: Context, className: String): Boolean {
        var isRunning = false
        val activityManager = mContext
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val serviceList = activityManager
                .getRunningServices(30)
        if (serviceList.size <= 0) {
            return false
        }
        for (i in serviceList.indices) {
            if (serviceList[i].service.className == className == true) {
                isRunning = true
                break
            }
        }
        return isRunning
    }

    /**
     * 设置本地图片
     *
     * @param url
     * @return
     */
    fun setLoacalBitmap(view: ImageView, url: String) {
        try {
            val fis = FileInputStream(url)
            val bitmap = BitmapFactory.decodeStream(fis)
            if (bitmap != null) {
                view.setImageBitmap(bitmap)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }

    fun isConnect(context: Context): Boolean {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            val connectivity = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                // 获取网络连接管理的对象
                val info = connectivity.activeNetworkInfo
                if (info != null && info.isConnected) {
                    // 判断当前网络是否已经连接
                    if (info.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        } catch (e: Exception) {
            // TODO: handle exception
            Log.v("error", e.toString())
        }

        return false
    }

    /**
     *
     * @Title: hideSystemKeyBoard
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param mcontext
     * @param @param v 设定文件
     * @return void 返回类型
     * @throws
     */
    fun hideSystemKeyBoard(mcontext: Context, v: View) {
        try {
            val imm = mcontext
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        } catch (e: Exception) {
        }

    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    fun getVersion(context: Context): String? {
        try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName,
                    0)
            return info.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     *
     * 二维码的 大小
     *
     * @paramlistView
     * @author Steven
     * @return void
     */
    fun getMaxCodeSize(density: Float): Int {
        var textSize = 0
        if (density >= 3.0) {
            textSize = 220
        } else if (density > 2.0 && density < 3.0) {
            textSize = 180
        } else if (density >= 1.5 && density < 2.0) {
            textSize = 140
        } else if (density >= 1.0 && density < 1.5) {
            textSize = 100
        } else {
            textSize = 100
        }
        return textSize
    }

    /**
     * 密度计算 字体大小 文本框
     *
     * @paramistView
     * @author Steven
     * @return void
     */
    fun getTextSize(density: Float): Float {
        var textSize = 0.0f
        if (density >= 3.0) {
            textSize = 24f
        } else if (density >= 2.0 && density < 3.0) {
            textSize = 16f
        } else if (density >= 1.5 && density < 2.0) {
            textSize = 14f
        } else if (density >= 1.0 && density < 1.5) {
            textSize = 10f
        } else {
            textSize = 12f
        }
        return textSize
    }

    /**
     * 密度计算 发送短信 字体 字体大小
     *
     * @param
     * @author Steven
     * @return void
     */
    fun getSendCodeBtnTextSize(density: Float): Float {
        var textSize = 0.0f
        if (density >= 3.0) {
            textSize = 15f
        } else if (density >= 2.0 && density < 3.0) {
            textSize = 14f
        } else if (density >= 1.5 && density < 2.0) {
            textSize = 12f
        } else if (density >= 1.0 && density < 1.5) {
            textSize = 10f
        } else {
            textSize = 10f
        }
        return textSize
    }

    /**
     * 动态计算设置listview高度
     *
     * @函数名 setListViewHeightBasedOnChildren
     * @功能 TODO
     * @param listView
     * @备注 <其它说明>
    </其它说明> */
    fun changeListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: // pre-condition
                return

        var totalHeight = 0
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }

        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }

    fun setListViewHeightBasedOnChildren(listView: ListView) {

        val listAdapter = listView.adapter ?: return

        var totalHeight = 0

        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }

        val params = listView.layoutParams

        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)

        // ((MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除

        listView.layoutParams = params
    }

    fun showToastl(con: Context, str: String) {
        if (toast == null) {
            toast = Toast.makeText(con, str, Toast.LENGTH_LONG)
        } else {
            toast!!.setText(str)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        toast!!.show()
    }

    fun showToasts(con: Context, str: String) {
        if (toast == null) {
            toast = Toast.makeText(con, str, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(str)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        toast!!.show()
    }

    fun showToasts(con: Context, res: Int) {
        if (toast == null) {
            toast = Toast.makeText(con, res, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(res)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        toast!!.show()
    }

    fun Dp2Px(con: Context, dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp.toFloat(),
                con.resources.displayMetrics).toInt()
    }

    fun Px2Dp(con: Context, px: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px.toFloat(),
                con.resources.displayMetrics).toInt()
    }

    fun getPxFromDpRes(con: Context, resid: Int): Int {
        return con.resources.getDimension(resid).toInt()
    }

    fun resizeDrawable(con: Context, dr: Drawable, w: Int, h: Int): Drawable {
        // Read your drawable from somewhere
        // Drawable dr = getResources().getDrawable(R.drawable.somedrawable);
        val bitmap = (dr as BitmapDrawable).bitmap
        // Scale it to 50 x 50
        // Set your new, scaled drawable "d"
        return BitmapDrawable(con.resources,
                Bitmap.createScaledBitmap(bitmap, w, h, true))
    }

    fun Bitmap2Drawable(con: Context, bm: Bitmap): Drawable {
        return BitmapDrawable(con.resources, bm)
    }

    fun Drawable2Bitmap(con: Context, dr: Drawable): Bitmap {
        val w = dr.intrinsicWidth
        val h = dr.intrinsicHeight

        // 取 drawable 的颜色格式
        val config = if (dr.opacity != PixelFormat.OPAQUE)
            Config.ARGB_8888
        else
            Config.RGB_565
        // 建立对应 bitmap
        val bitmap = Bitmap.createBitmap(w, h, config)
        // 建立对应 bitmap 的画布
        val canvas = Canvas(bitmap)
        dr.setBounds(0, 0, w, h)
        // 把 drawable 内容画到画布中
        dr.draw(canvas)
        return bitmap
    }

    fun rotateBitmap(b: Bitmap?, degrees: Int): Bitmap? {
        var b = b
        if (degrees != 0 && b != null) {
            val m = Matrix()
            m.setRotate(degrees.toFloat(), b.width.toFloat() / 2,
                    b.height.toFloat() / 2)
            try {
                val b2 = Bitmap.createBitmap(b, 0, 0, b.width,
                        b.height, m, true)
                if (b != b2) {
                    b.recycle() // Android开发网再次提示Bitmap操作完应该显示的释放
                    b = b2
                }
            } catch (ex: OutOfMemoryError) {
                // Android123建议大家如何出现了内存不足异常，最好return 原始的bitmap对象。.
                // Log.e(TAG, "rotateBmp OutOfMemoryError");
                ex.printStackTrace()
            }

        }
        return b
    }

    // to do need to improve and do with jpg and quality
    // // image.compressToJpeg(
    // new Rect(0, 0, image.getWidth(), image.getHeight()), 90,
    // filecon);

    fun loadBitmapFromLocal(imgPath: String): Bitmap {
        val options = BitmapFactory.Options()
        //
        options.inPreferredConfig = Config.RGB_565// 表示16位位图
        // 565代表对应三原色占的位数
        // options.inInputShareable=true; //??
        options.inPurgeable = true// 设置图片可以被回收
        options.inTempStorage = ByteArray(1024 * 1024 * 10)
        //
        options.inSampleSize = 1
        return BitmapFactory.decodeFile(imgPath, options)
        // jpgView.setImageBitmap(bm);
    }

    fun loadBitmapFromLocalWithSampleSize(imgPath: String,
                                          sampleSize: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inSampleSize = sampleSize
        return BitmapFactory.decodeFile(imgPath, options)
        // jpgView.setImageBitmap(bm);
    }

    // todo calc file size with inJustDecodeBounds
    // todo get bitmap with inSampleSize

    fun saveBitmap2JPG(path: String, bitmap: Bitmap) {
        val f = File(path)// ("/sdcard/" + bitName + ".png");
        var fOut: FileOutputStream? = null
        try {
            f.createNewFile()
            fOut = FileOutputStream(f)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun saveBitmap2JPG(dir: String, filename: String, bitmap: Bitmap) {
        saveBitmap2JPG(dir + File.separator + filename, bitmap)
    }

    fun saveBitmap2PNG(path: String, bitmap: Bitmap) {
        val f = File(path)// ("/sdcard/" + bitName + ".png");
        var fOut: FileOutputStream? = null
        try {
            f.createNewFile()
            fOut = FileOutputStream(f)
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun saveBitmap2PNG(dir: String, filename: String, bitmap: Bitmap) {
        saveBitmap2PNG(dir + File.separator + filename, bitmap)

    }


    /**
     *
     * like ".../data/tmpResImgFile.jpg"
     *
     *
     */
    fun saveResImg2Path(resid: Int, con: Context, filePath: String): String {

        val drawable = con.resources.getDrawable(resid)
        val bmp = Drawable2Bitmap(con, drawable)
        saveBitmap2JPG(filePath, bmp)
        return filePath

    }

    /**
     * 安全获取edit的string
     */
    fun getEditTextSafe(editText: EditText?): String {
        return if (editText != null && editText.text != null
                && editText.text.toString().trim { it <= ' ' } != "") {
            editText.text.toString().trim { it <= ' ' }
        } else {
            ""
        }
    }

    /**
     * 判断edittext是否null,是的话返回true
     */
    fun isEditTextEmpty(editText: EditText?): Boolean {
        return if (editText != null && editText.text != null
                && editText.text.toString().trim { it <= ' ' } != "") {
            false
        } else {
            true
        }
    }

    fun alert(context: Context, message: String) {
        val alertDialog = AlertDialogUI(context)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("确定", OnClickListener {
            // TODO Auto-generated method stub
            alertDialog.dismiss()
        })
    }

    fun alert(context: Context, title: String, message: String) {
        val alertDialog = AlertDialogUI(context)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("确定", OnClickListener {
            // TODO Auto-generated method stub
            alertDialog.dismiss()
        })
    }

    fun alert(context: Context, title: String, message: String,
              ok: String) {
        val alertDialog = AlertDialogUI(context)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(ok, OnClickListener {
            // TODO Auto-generated method stub
            alertDialog.dismiss()
        })
    }

    fun loadingDialog(context: Context): ProgressDialog {

        val dlg = ProgressDialog(context,
                ProgressDialog.STYLE_SPINNER)
        dlg.setCancelable(false)
        dlg.setCanceledOnTouchOutside(false)
        dlg.show()

        val progress = dlg
                .findViewById<View>(android.R.id.progress) as ProgressBar
        val progressParams = progress.layoutParams
        progressParams.height = LayoutParams.WRAP_CONTENT
        progressParams.width = LayoutParams.MATCH_PARENT// set MATCH_PARENT to
        // center
        progress.layoutParams = progressParams

        val linear = progress.parent as LinearLayout
        val contentp = linear.parent as FrameLayout
        val contentp2 = contentp.parent as FrameLayout
        val contentp3 = contentp2.parent as FrameLayout
        contentp3.setBackgroundResource(0)// remove gray background

        return dlg
    }

    fun loadingDialog(context: Context, title: String,
                      message: String): ProgressDialog {
        val dlg = ProgressDialog.show(context, title, message, true,
                false)
        val msg = dlg.findViewById<View>(android.R.id.message) as TextView
        msg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        return dlg
    }

    private fun loadingDialogTranslucent(context: Context): ProgressDialog {

        val dlg = ProgressDialog(context,
                ProgressDialog.STYLE_SPINNER)
        dlg.setCancelable(false)
        dlg.setCanceledOnTouchOutside(false)
        dlg.show()

        val progress = dlg
                .findViewById<View>(android.R.id.progress) as ProgressBar
        val progressParams = progress.layoutParams
        progressParams.height = LayoutParams.WRAP_CONTENT
        progressParams.width = LayoutParams.MATCH_PARENT// set MATCH_PARENT to
        // center
        progress.layoutParams = progressParams

        val linear = progress.parent as LinearLayout
        val contentp = linear.parent as FrameLayout
        val contentp2 = contentp.parent as FrameLayout
        val contentp3 = contentp2.parent as FrameLayout
        val contentp4 = contentp3.parent as LinearLayout
        val contentp5 = contentp4.parent as FrameLayout
        val contentp6 = contentp5.parent as FrameLayout
        val contentp7 = contentp6.parent as FrameLayout

        contentp3.setBackgroundResource(0)// remove gray background
        // contentp7.setBackgroundResource(0);

        // contentp4.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        // contentp5.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        // contentp6.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        // contentp7.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

        return dlg
    }

    // /////////////

    /**
     * 重新定义图片高度
     *
     * @author Davis
     * @param bitmapToScale
     * @param newWidth
     * @param newHeight
     * @return
     */
    fun scaleBitmap(bitmapToScale: Bitmap?, newWidth: Float,
                    newHeight: Float): Bitmap? {
        if (bitmapToScale == null) {
            return null
        }

        // get the original width and height
        val width = bitmapToScale.width
        val height = bitmapToScale.height

        if (width.toFloat() == newWidth && height.toFloat() == newHeight) {
            return bitmapToScale
        }

        // create a matrix for the manipulation
        val matrix = Matrix()

        // resize the bit map
        matrix.postScale(newWidth / width, newHeight / height)

        // recreate the new Bitmap and set it back
        return Bitmap.createBitmap(bitmapToScale, 0, 0,
                bitmapToScale.width, bitmapToScale.height, matrix,
                true)
    }

    fun isfastdoubleClick(): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastclickTime
        if (0 < timeD && timeD < 1000) {
            return true
        }
        lastclickTime = time
        return false
    }

    /**
     * 登录
     *
     * @author Davis
     * @param mContext
     */
    fun checkLogin(mContext: Context) {
        val dialogUI = AlertDialogUI(mContext)
        dialogUI.setTitle("登录")
        dialogUI.setMessage("您还没有登录，不能收藏！是否登录？")
        dialogUI.setPositiveButton("确定", OnClickListener {
            dialogUI.dismiss()
            val intent = Intent(mContext, LoginActivity::class.java)
            mContext.startActivity(intent)
        })
        dialogUI.setNegativeButton("取消", OnClickListener { dialogUI.dismiss() })
    }

    /***
     * 拨打电话
     *
     * @author Davis
     * @param phoneno
     * @param mContext
     */
    fun cellPhone(phoneno: String, mContext: Context) {
        val dialogUI = AlertDialogUI(mContext)
        dialogUI.setTitle("拨号")
        dialogUI.setMessage("您是否要拨打:" + phoneno)
        dialogUI.setPositiveButton("确定",
                OnClickListener {
                    dialogUI.dismiss()
                    val intent = Intent(Intent.ACTION_DIAL, Uri
                            .parse("tel:" + phoneno))
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    mContext.startActivity(intent)
                    (mContext as Activity).overridePendingTransition(
                            R.anim.push_left_in, R.anim.push_left_out)
                })
        dialogUI.setNegativeButton("取消",
                OnClickListener { dialogUI.dismiss() })
    }

    /**
     * 判断是否安装目标应用
     *
     * @author Davis
     * @param packageName
     * 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private fun isInstallByread(packageName: String): Boolean {
        return File("/data/data/" + packageName).exists()
    }

    fun bitmapToString(filePath: String): String {
        val degree = readPictureDegree(filePath)
        var bm = getSmallBitmap(filePath)
        bm = rotaingImageView(degree, bm)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun getSmallBitmap(filePath: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 768, 1024)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false

        return BitmapFactory.decodeFile(filePath, options)
    }

    fun calculateInSampleSize(options: BitmapFactory.Options,
                              reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }

    @SuppressLint("NewApi")
    fun setGridViewHeightBasedOnChildren(gridView: GridView) {
        // 获取GridView对应的Adapter
        try {
            val listAdapter = gridView.adapter ?: return
            val rows: Int
            var columns = 0
            var horizontalBorderHeight = 0
            var verBorderHeight = 0
            val clazz = gridView.javaClass
            try {
                // 利用反射，取得每行显示的个数
                val column = clazz.getDeclaredField("mRequestedNumColumns")
                column.isAccessible = true
                columns = column.get(gridView) as Int
                // 利用反射，取得横向分割线高度
                val horizontalSpacing = clazz
                        .getDeclaredField("mRequestedHorizontalSpacing")
                horizontalSpacing.isAccessible = true
                horizontalBorderHeight = horizontalSpacing
                        .get(gridView) as Int
                verBorderHeight = gridView.verticalSpacing
            } catch (e: Exception) {
                // TODO: handle exception
                e.printStackTrace()
            }

            // 判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
            if (listAdapter.count % columns > 0) {
                rows = listAdapter.count / columns + 1
            } else {
                rows = listAdapter.count / columns
            }
            var totalHeight = 0
            for (i in 0 until rows) { // 只计算每项高度*行数
                val listItem = listAdapter.getView(i, null, gridView)
                listItem.measure(0, 0) // 计算子项View 的宽高
                totalHeight += listItem.measuredHeight // 统计所有子项的总高度
            }
            val params = gridView.layoutParams
            params.height = (totalHeight + horizontalBorderHeight * (rows - 1)
                    + verBorderHeight * (rows - 1))// 最后加上分割线总高度
            gridView.layoutParams = params
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    fun rotaingImageView(angle: Int, bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.width, bitmap.height, matrix, true)
    }

    fun readPictureDegree(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            // TODO: handle exception
        }

        return degree
    }


    /***
     * @author Davis
     * @param mContext
     * @return
     * @throws Exception
     */
    fun getVersionName(mContext: Context): String {
        try {
            // 获取packagemanager的实例
            val packageManager = mContext.packageManager
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            val packInfo = packageManager.getPackageInfo(
                    mContext.packageName, 0)
            return packInfo.versionName
        } catch (e: Exception) {
            // TODO: handle exception
        }

        return ""
    }


    fun returnBitMap(url: String, type: Int, height: Int): Bitmap? {
        val opts = BitmapFactory.Options()
        opts.inJustDecodeBounds = true
        opts.inPreferredConfig = Config.RGB_565
        opts.inPurgeable = true
        opts.inInputShareable = true
        if (type > 0) {
            opts.inSampleSize = 8
        } else {
            opts.inSampleSize = 8
        }
        opts.outHeight = height
        var myFileUrl: URL? = null
        var bitmap: Bitmap? = null
        try {
            myFileUrl = URL(url)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        try {
            if (null != myFileUrl) {
                val conn = myFileUrl
                        .openConnection() as HttpURLConnection
                conn.doInput = true
                conn.connect()
                val `is` = conn.inputStream
                bitmap = BitmapFactory.decodeStream(`is`)
                `is`.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {

        }
        return bitmap
    }

    private fun computeInitialSampleSize(options: BitmapFactory.Options,
                                         minSideLength: Int, maxNumOfPixels: Int): Int {
        val w = options.outWidth.toDouble()
        val h = options.outHeight.toDouble()

        val lowerBound = if (maxNumOfPixels == -1)
            1
        else
            Math.ceil(Math
                    .sqrt(w * h / maxNumOfPixels)).toInt()
        val upperBound = if (minSideLength == -1)
            128
        else
            Math.min(
                    Math.floor(w / minSideLength), Math.floor(h / minSideLength)).toInt()

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound
        }

        return if (maxNumOfPixels == -1 && minSideLength == -1) {
            1
        } else if (minSideLength == -1) {
            lowerBound
        } else {
            upperBound
        }
    }

    private fun color(colorStr: String): Int {
        var color = 0
        val colorStrs = colorStr.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        color = Color.rgb(Integer.parseInt(colorStrs[0].trim { it <= ' ' }),
                Integer.parseInt(colorStrs[1].trim { it <= ' ' }),
                Integer.parseInt(colorStrs[2].trim { it <= ' ' }))
        return color
    }

    /**
     * 从相册得到的url转换为SD卡中图片路径
     */
    @SuppressLint("NewApi")
    fun getPath(uri: Uri, mContext: Context): String? {
        var filePath = ""
        try {
            if (StringUtils.isEmpty(uri.authority)) {
                return null
            }
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = (mContext as Activity).managedQuery(uri, projection, null, null, null)
            val column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            filePath = cursor.getString(column_index)
            if (StringUtils.isEmpty(filePath)) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    val wholeID = DocumentsContract.getDocumentId(uri)
                    val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    val column = arrayOf(MediaStore.Images.Media.DATA)
                    val sel = MediaStore.Images.Media._ID + " = ?"
                    val cursor1 = mContext.getContentResolver().query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, arrayOf(id), null)
                    val columnIndex = cursor1!!.getColumnIndex(column[0])
                    if (cursor1.moveToFirst()) {
                        filePath = cursor1.getString(columnIndex)
                    }
                    cursor1.close()
                }
            }
        } catch (e: IllegalArgumentException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: Exception) {
            // TODO: handle exception
        }

        return filePath
    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context
     * the context
     * @return mDisplayMetrics
     */
    fun getDisplayMetrics(context: Context?): DisplayMetrics {
        val mResources: Resources
        if (context == null) {
            mResources = Resources.getSystem()

        } else {
            mResources = context.resources
        }
        // DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5,
        // xdpi=160.421, ydpi=159.497}
        // DisplayMetrics{density=2.0, width=720, height=1280,
        // scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
        return mResources.displayMetrics
    }

    /** 设置图片圆角  */
    fun toRoundCorner(bitmap: Bitmap, pixels: Int): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width,
                bitmap.height, Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    @SuppressLint("NewApi")
    fun setCameraDisplayOrientation(activity: Activity,
                                    cameraId: Int, camera: Camera) {
        val info = Camera.CameraInfo()
        Camera.getCameraInfo(cameraId, info)
        val rotation = activity.windowManager.defaultDisplay
                .rotation
        var degrees = 0
        when (rotation) {
            Surface.ROTATION_0 -> degrees = 0
            Surface.ROTATION_90 -> degrees = 90
            Surface.ROTATION_180 -> degrees = 180
            Surface.ROTATION_270 -> degrees = 270
        }
        var result: Int
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360
            result = (360 - result) % 360 // compensate the mirror
        } else {
            // back-facing
            result = (info.orientation - degrees + 360) % 360
        }
        camera.setDisplayOrientation(result)
    }

    fun loadAppMarketPage(con: Context) {
        try {
            val uri = Uri.parse("market://search?q=pname:" + con.packageName.trim { it <= ' ' })
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            con.startActivity(intent)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            UIUtil.showToasts(con, "您还没有安装任何应用市场！")
        }

    }
}
