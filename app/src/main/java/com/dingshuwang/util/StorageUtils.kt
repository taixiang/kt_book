package com.dingshuwang.util

import android.annotation.TargetApi
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

import java.io.File
import java.io.FileOutputStream

object StorageUtils {

    /**
     * 获取拍照目录
     */
    val dcimDir: File
        get() {
            val dcim = File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DCIM)
            if (!dcim.exists()) {
                dcim.mkdirs()
            }
            return dcim
        }

    /**
     * 获取统一的缓存目录，如果有sd卡 :/mnt/sdcard/android/data/包名/cache/
     * 如果没有: /data/data/包名/cache/
     */
    fun getCacheDir(context: Context): File? {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            context.externalCacheDir
        } else {
            context.cacheDir
        }
    }

    /**
     * 获取崩溃日志文件目录
     */
    fun getCrashDir(context: Context): File {
        val crashDirFile = File(getCacheDir(context)!!.absolutePath + File.separator + "crash")
        if (!crashDirFile.exists()) {
            crashDirFile.mkdirs()
        }
        return crashDirFile
    }

    /**
     * 根据url的MD5来获取对应的bitmap(用于网站图标)
     *
     * @param context
     * @param url
     * @return
     */
    fun getCacheBitmap(context: Context, url: String): Bitmap? {
        var bitmap: Bitmap? = null
        val cacheFile = File(getCacheFilePath(context, url)!!)
        if (cacheFile.exists()) {
            bitmap = BitmapFactory.decodeFile(cacheFile.absolutePath)
        }
        return bitmap
    }

    /**
     * 获取缓存文件路径
     */
    fun getCacheFilePath(context: Context, url: String): String? {
        return null//getCacheDir(context).getAbsolutePath() + MD5Utils.generate(url);
    }

    /**
     * 从Uri中获取包含的文件路径， 需要判断是否4.4以上，主要用于onActivityResult中的处理
     * 文件路径：可以是来自Storage Access Framework Documents（4.4),也可以是基于ContentProviders 或者   MediaStore 中的_data
     *
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun getFilePathFromUri(context: Context, uri: Uri): String? {
        val isKK = Build.VERSION.SDK_INT >= 19
        if (isKK && DocumentsContract.isDocumentUri(context, uri))
        // 4.4以上才有的
        {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if (ContentResolver.SCHEME_CONTENT.equals(uri.scheme, ignoreCase = true))
        // MediaStore (and general)
        {
            return getDataColumn(context, uri, null, null)
        } else if (ContentResolver.SCHEME_FILE.equals(uri.scheme, ignoreCase = true)) {// File
            return uri.path
        }
        return uri.path
    }

    /**
     * 从cursor中查询获取uri中包含的文件路径
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    private fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = MediaStore.MediaColumns.DATA
        val projection = arrayOf(column)
        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
        return null
    }

    /**
     * 判断是否是SDcard存储文件
     *
     * @param uri
     * @return
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * 判断Uri是否是下载文件
     *
     * @param uri
     * @return
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * 判断Uri是否是多媒体文件
     *
     * @param uri
     * @return
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    fun saveImageToDCIM(context: Context, bmp: Bitmap, imageName: String): File? {
        // 首先保存图片
        val fileName = imageName + ".png"
        var file: File? = null
        try {
            file = File(dcimDir, fileName)
            if (file.exists()) {
                file.delete()
            }
            val fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            // 其次把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, fileName, null)
            // 最后通知图库更新
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.path)))
        } catch (e: Exception) {
            e.printStackTrace()
            file = null
        }

        return file
    }
}


