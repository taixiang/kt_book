package com.dingshuwang.util

import android.util.Log

import java.security.MessageDigest
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import java.util.regex.Pattern
import kotlin.experimental.and

object StringUtils {
    val sdf_yy_MM_dd = SimpleDateFormat(
            "yyyy-MM-dd")
    val sdf_yy_MM_dd_HH_mm = SimpleDateFormat(
            "yyyy-MM-dd HH:mm")
    val sdf_yy_MM_dd_HH_mm_ss = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss")
    val sdf_HH_mm_ss = SimpleDateFormat(
            "HH:mm")

    /**
     * String helper to ensure the object safely translates to a string, will
     * not return null
     */
    fun toString(o: Any?): String {
        return o?.toString() ?: ""
    }

    fun isEmpty(str: String?): Boolean {
        return if (str == null) {
            true
        } else str.trim { it <= ' ' }.length == 0

    }

    /**
     * segs equalsIgnoreCase
     */
    fun isEmptyOrLikeSegs(str: String?, vararg segs: String): Boolean {
        if (str == null) {
            return true
        }

        if (str.trim { it <= ' ' }.length == 0) {
            return true
        }

        for (seg in segs) {
            if (str.equals(seg, ignoreCase = true)) {
                return true
            }
        }

        return false
    }

    fun capitalize(str: String): String {
        return Character.toUpperCase(str[0]) + str.substring(1)
    }

    fun floatToPercent(f: Float): Float {
        return f * 100
    }

    fun isNumeric(str: String): Boolean {
        var i = str.length
        while (--i >= 0) {
            if (!Character.isDigit(str[i])) {
                return false
            }
        }
        return true
    }

    fun isNumericFast(str: String): Boolean {
        var i = str.length
        while (--i >= 0) {
            val chr = str[i].toInt()
            if (chr < 48 || chr > 57)
                return false
        }
        return true
    }

    fun isNumericPattern(str: String): Boolean {
        val pattern = Pattern.compile("[0-9]*")
        return pattern.matcher(str).matches()
    }

    fun checkSpace(str: String): Boolean {
        var flag = false
        if (str.contains(" ")) {
            flag = true
            return flag
        }
        return flag
    }

    fun changeData(dateStr: String): String {
        val date = Date(java.lang.Long.parseLong(dateStr))
        return sdf_yy_MM_dd_HH_mm.format(date)
    }

    /***
     * 变换为时分秒格式
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    fun changeHMSData(dateStr: String): String {
        var date: Date? = null
        try {
            date = sdf_yy_MM_dd_HH_mm_ss.parse(dateStr)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return sdf_HH_mm_ss.format(date)
    }

    /***
     * 变换为时分秒格式
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    fun changeYMDData(dateStr: String): String {
        var date: Date? = null
        try {
            date = sdf_yy_MM_dd_HH_mm_ss.parse(dateStr)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return sdf_yy_MM_dd_HH_mm.format(date)
    }

    fun getObjectKeyName(key: String): String {
        if (key.length == 0) {
            return key
        }
        val index = key.substring(0, key.length - 1).lastIndexOf('/')
        return if (index == -1) {
            key
        } else key.substring(index + 1)

    }

    fun checkBlankString(param: String?): String {
        return param ?: ""
    }

//    @Throws(Exception::class)
//    fun md5(encryptStr: String): String {
//        var encryptStr = encryptStr
//        val md = MessageDigest.getInstance("MD5")
//        md.update(encryptStr.toByteArray(charset("UTF-8")))
//        val digest = md.digest()
//        val md5 = StringBuffer()
//        for (i in digest.indices) {
//            md5.append(Character.forDigit((digest[i] and 0xF0) >> 4, 16))
//            md5.append(Character.forDigit(digest[i] and 0xF, 16))
//        }
//
//        encryptStr = md5.toString()
//        return encryptStr
//    }
//
//    @Throws(Exception::class)
//    fun sha1(encryptStr: String): String {
//        var encryptStr = encryptStr
//        val md = MessageDigest.getInstance("SHA1")
//        md.update(encryptStr.toByteArray(charset("UTF-8")))
//        val digest = md.digest()
//        val sha1 = StringBuffer()
//        for (i in digest.indices) {
//            sha1.append(Character.forDigit(digest[i] and 0xF0 shr 4, 16))
//            sha1.append(Character.forDigit(digest[i] and 0xF, 16))
//        }
//
//        encryptStr = sha1.toString()
//        return encryptStr
//    }

    @Throws(Exception::class)
    fun md5Byte(encryptStr: String): ByteArray {
        val md = MessageDigest.getInstance("MD5")
        md.update(encryptStr.toByteArray(charset("UTF-8")))
        return md.digest()
    }

    @Throws(Exception::class)
    fun sha1Byte(encryptStr: String): ByteArray {
        val md = MessageDigest.getInstance("SHA1")
        md.update(encryptStr.toByteArray(charset("UTF-8")))
        return md.digest()
    }

    fun genUUIDHexString(): String {
        return UUID.randomUUID().toString().replace("-".toRegex(), "")
    }

//    @Throws(Exception::class)
//    fun parseUUIDFromHexString(hexUUID: String): UUID {
//        val data = hexStringToByteArray(hexUUID)
//        var msb: Long = 0
//        var lsb: Long = 0
//
//        for (i in 0..7)
//            msb = msb shl 8 or (data[i] and 0xff)
//        for (i in 8..15)
//            lsb = lsb shl 8 or (data[i] and 0xff)
//
//        return UUID(msb, lsb)
//    }

//    private fun convertDigit(value: Int): Char {
//        var value = value
//
//        value = value and 0x0f
//        return if (value >= 10)
//            (value - 10 + 'a').toChar()
//        else
//            (value + '0').toChar()
//
//    }

    fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character
                    .digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

//    fun convert(bytes: ByteArray): String {
//
//        val sb = StringBuffer(bytes.size * 2)
//        for (i in bytes.indices) {
//            sb.append(convertDigit((bytes[i] shr 4).toInt()))
//            sb.append(convertDigit((bytes[i] and 0x0f).toInt()))
//        }
//        return sb.toString()
//
//    }

//    fun convert(bytes: ByteArray, pos: Int, len: Int): String {
//        val sb = StringBuffer(len * 2)
//        for (i in pos until pos + len) {
//            sb.append(convertDigit((bytes[i] shr 4).toInt()))
//            sb.append(convertDigit((bytes[i] and 0x0f).toInt()))
//        }
//        return sb.toString()
//
//    }


    fun doubleFormat(d: Double): String {
        val df = DecimalFormat("######0.00")

        return df.format(d)
    }

}
