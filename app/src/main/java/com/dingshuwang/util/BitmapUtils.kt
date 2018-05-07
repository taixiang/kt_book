package com.dingshuwang.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object BitmapUtils {
    /***
     * 将一个图片文件压缩到指定的大小(不大于指定的宽度和高度)并保存，
     *
     * @param context
     * @param originalPath
     * @return 压缩后的图片路径
     */
    fun getCompressBitmapFilePath(context: Context, originalPath: String?): String? {
        var compressBitmapFielPath: String? = null
        var bitmap: Bitmap? = null
        if (null != originalPath) {
            bitmap = scaleBitmap(originalPath, PhotoUtils.W_H, PhotoUtils.W_H)
            compressBitmapFielPath = genCompressBitmapFilePath(context, bitmap, originalPath)
        }
        return compressBitmapFielPath
    }

    /***
     * 获取压缩后的bitmap保存的文件路径
     *
     * @param context
     * @param bitmap
     * @param originalPath
     * @return
     */
    fun genCompressBitmapFilePath(context: Context, bitmap: Bitmap?, originalPath: String): String? {
        var compressBitmapFielPath: String? = null
        if (null != bitmap) {
            try {
                val file = File(StorageUtils.getCacheDir(context), MD5Utils.getMD5(originalPath))
                val fileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                compressBitmapFielPath = file.absolutePath
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        println("新的处理之后的图片路径 = " + compressBitmapFielPath!!)
        return compressBitmapFielPath
    }


    /**
     * 按原比例缩放图片
     *
     * @param path      图片的URI地址
     * @param maxWidth  缩放后的宽度
     * @param maxHeight 缩放后的高度
     * @return
     */
    fun scaleBitmap(path: String, maxWidth: Int, maxHeight: Int): Bitmap? {
        var resizedBitmap: Bitmap? = null
        try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            var input: InputStream = FileInputStream(path)
            BitmapFactory.decodeStream(input, null, options)
            val sourceWidth = options.outWidth
            val sourceHeight = options.outHeight
            input.close()
            val rate = Math.max(sourceWidth / maxWidth.toFloat(), sourceHeight / maxHeight.toFloat())
            options.inJustDecodeBounds = false
            options.inSampleSize = rate.toInt()
            input = FileInputStream(path)
            val bitmap = BitmapFactory.decodeStream(input, null, options)
            val w0 = bitmap.width
            val h0 = bitmap.height
            val scaleWidth = maxWidth / w0.toFloat()
            val scaleHeight = maxHeight / h0.toFloat()
            val maxScale = Math.min(scaleWidth, scaleHeight)
            val matrix = Matrix()
            matrix.reset()
            if (maxScale < 1) {
                matrix.postScale(maxScale, maxScale)
            }
            resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, w0, h0, matrix, true)
            input.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return resizedBitmap
    }

    fun scaleBitmap(path: String): Bitmap? {
        var resizedBitmap: Bitmap? = null
        try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            var input: InputStream = FileInputStream(path)
            BitmapFactory.decodeStream(input, null, options)
            val sourceWidth = options.outWidth
            val sourceHeight = options.outHeight
            input.close()
            val rate = Math.max(sourceWidth / PhotoUtils.W_H.toFloat(), sourceHeight / PhotoUtils.W_H.toFloat())
            options.inJustDecodeBounds = false
            options.inSampleSize = rate.toInt()
            input = FileInputStream(path)
            val bitmap = BitmapFactory.decodeStream(input, null, options)
            val w0 = bitmap.width
            val h0 = bitmap.height
            val scaleWidth = PhotoUtils.W_H / w0.toFloat()
            val scaleHeight = PhotoUtils.W_H / h0.toFloat()
            val maxScale = Math.min(scaleWidth, scaleHeight)
            val matrix = Matrix()
            matrix.reset()
            if (maxScale < 1) {
                matrix.postScale(maxScale, maxScale)
            }
            resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, w0, h0, matrix, true)
            input.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return resizedBitmap
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    private fun readPictureDegree(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return degree
    }

    /***
     * 将图片旋转后返回bitmap ,用于选择图片时图片旋转90度的问题
     *
     * @return
     */
    fun rotatePictureBitmap(imagePath: String, bitmap: Bitmap): Bitmap {
        //        //旋转图片 动作
        //        Matrix matrix = new Matrix();
        //        matrix.postRotate(readPictureDegree(imagePath));
        //        // 创建新的图片
        //        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        //        return resizedBitmap;
        val angle = readPictureDegree(imagePath)
        return rotateBitmap(bitmap, angle.toFloat())
    }

    fun rotateBitmap(bitmap: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

}
