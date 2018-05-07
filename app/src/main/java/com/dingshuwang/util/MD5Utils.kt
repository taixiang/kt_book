package com.dingshuwang.util

import java.security.MessageDigest

object MD5Utils {

    private val MD5_CHARSET = "UTF-8"
    // MD5加密，32位
    fun EncoderByMd5(str: String): String {
        var md5: MessageDigest? = null
        try {
            md5 = MessageDigest.getInstance("MD5")
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

        val charArray = str.toCharArray()
        val byteArray = ByteArray(charArray.size)

        for (i in charArray.indices) {
            byteArray[i] = charArray[i].toByte()
        }

        val md5Bytes = md5!!.digest(byteArray)
        val hexValue = StringBuffer()
        for (i in md5Bytes.indices) {
            val `val` = md5Bytes[i].toInt() and 0xff
            if (`val` < 16) {
                hexValue.append("0")
            }
            hexValue.append(Integer.toHexString(`val`))
        }
        return hexValue.toString()
    }

    /**
     * MD5加密
     *
     * @author 刘施洁
     * @2012-1-6上午09:02:37
     * @param sStr
     * @return
     */
    fun getMD5(sStr: String): String {
        var sReturnCode = ""
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(sStr.toByteArray(charset(MD5_CHARSET)))
            val b = md.digest()
            var i: Int
            val sb = StringBuffer("")
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0) {
                    i += 256
                }
                if (i < 16) {
                    sb.append("0")
                }
                sb.append(Integer.toHexString(i))
            }

            sReturnCode = sb.toString()
        } catch (ex: Exception) {

        }

        return sReturnCode
    }
}
