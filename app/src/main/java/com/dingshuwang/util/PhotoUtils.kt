package com.dingshuwang.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout

import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity

import java.io.File

/**
 * 图片选择工具类
 */
object PhotoUtils {
    /**
     * 最大上传图片的宽度和高度
     */
    val W_H = 600
    /***
     * 从相册文件中选取图片时的请求码
     */
    public val REQUEST_FROM_PHOTO = 1111
    /***
     * 从拍照中选取图片时的请求码
     */
    val REQUEST_FROM_CAMERA = 2222

    /***
     * 将上一步拍照/文件进行剪取
     */
    val REQUEST_FROM_CROP = 3333

    /****
     * 要进行剪切的图片路径
     */
    val EXTRA_SELECTED_PIC_PATH = "selected_pic_path"

    val EXTRA_CROP_BITMAP_PATH = "crop_bitmap_path"


    var finalCameraImagePath: String? = null
        private set

    fun showSelectDialog(activity: BaseActivity) {
        finalCameraImagePath = null
        val view = LayoutInflater.from(activity).inflate(R.layout.layout_dialog_modify_photo, null)
        val linearCameraContainer = view.findViewById<View>(R.id.linear_camera_container) as LinearLayout
        val linearGalleryContainer = view.findViewById<View>(R.id.linear_gallery_container) as LinearLayout
        val linearCancelContainer = view.findViewById<View>(R.id.linear_cancel_container) as LinearLayout
        val alertDialog = AlertDialog.Builder(activity).setView(view).create()
        val width = DensityUtils.getScreenWidthInPx(activity) - DensityUtils.dpToPx(activity, 40f)
        alertDialog.window!!.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        alertDialog.show()
        linearCameraContainer.setOnClickListener {
            PhotoUtils.selectPicFromCamera(activity)
            alertDialog.dismiss()
        }
        linearGalleryContainer.setOnClickListener {
            PhotoUtils.selectPicFromSD(activity)
            alertDialog.dismiss()
        }
        linearCancelContainer.setOnClickListener { alertDialog.dismiss() }

    }

    /**
     * 从相册中选择图片
     *
     * @param activity
     */
    private fun selectPicFromSD(activity: BaseActivity) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = "android.intent.action.GET_CONTENT"
        val localIntent2 = Intent.createChooser(intent, "选择图片")
        activity.startActivityForResult(localIntent2, PhotoUtils.REQUEST_FROM_PHOTO)
    }

    fun cropSelectedPic(activity: BaseActivity, selectedPicPath: String) {
        //        Intent intent = new Intent(activity, CropActivity.class);
        //        intent.putExtra(EXTRA_SELECTED_PIC_PATH, selectedPicPath);
        //        activity.startActivityForResult(intent, REQUEST_FROM_CROP);
    }

    /***
     * 当剪切完成时调用，用于后续的操作
     *
     * @param activity
     * @param cropImagePath
     */
    fun cropPicFinished(activity: BaseActivity, cropImagePath: String) {

        val intent = Intent()
        intent.putExtra(EXTRA_CROP_BITMAP_PATH, cropImagePath)
        activity.setResult(Activity.RESULT_OK, intent)
        activity.finish()
    }

    /***
     * 获取剪切后的bitmap
     *
     * @param intent
     * @return
     */
    fun getCropBitmapPath(intent: Intent): String {
        return intent.getStringExtra(EXTRA_CROP_BITMAP_PATH)

    }

    fun getSelectedToCropPicPath(activity: BaseActivity): String {
        return activity.intent.getStringExtra(EXTRA_SELECTED_PIC_PATH)
    }

    /***
     * 拍照选择图片
     *
     * @param activity
     * @return 保存的拍照图片路径
     */
    private fun selectPicFromCamera(activity: BaseActivity): String? {
        var imageFilePath: String? = null
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            activity.showToast("SD卡不可用")
        }
        try {
            println("activity.getExternalCacheDir() = " + activity.externalCacheDir!!)
            val imageFile = File(StorageUtils.getCacheDir(activity), System.currentTimeMillis().toString() + ".png")  //通过路径创建保存文件
            if (imageFile.exists()) {
                imageFile.delete()
            }
            imageFilePath = imageFile.path
            val photoUri = Uri.fromFile(imageFile)                                    //获取文件的Uri
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)            //跳转到相机Activity
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)    //告诉相机拍摄完毕输出图片到指定的Uri
            activity.startActivityForResult(intent, PhotoUtils.REQUEST_FROM_CAMERA)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        finalCameraImagePath = imageFilePath
        return imageFilePath
    }

    fun getFinalPhotoImagePath(activity: BaseActivity?, uri: Uri?): String? {
        return if (null != activity && null != uri) {
            StorageUtils.getFilePathFromUri(activity, uri)
        } else null
    }

}
