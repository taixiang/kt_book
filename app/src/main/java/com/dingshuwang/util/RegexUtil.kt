package com.dingshuwang.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

class RegexUtil private constructor()// getHttpClient();.
{

    // 过滤特殊字符

    /**
     * @param str
     * @return
     * @note 过滤特殊字符
     */
    fun stringFilter(str: String): Boolean {
        var flag = true
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        val regEx = "[`~#$%^&*()|'',\\[\\]<>/?~#￥%……&*——+|‘”“’]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(str)
        if (m.find()) {
            flag = false
        }
        return flag
    }

    // 校验密码
    fun ckeckPass(passWord: String?): Boolean {
        return if (passWord != null && passWord.matches("[0-9A-Za-z]*".toRegex()) == true) {
            true
        } else {
            false
        }
    }

    /**
     * @param strPhysicianLicence
     * @return
     * @note
     */
    fun regexPhysicianLicence(strPhysicianLicence: String): Boolean {
        var flag = true

        val regExp = "^[0-9]{16}"

        val pattern = Pattern.compile(regExp)
        val matcher = pattern.matcher(strPhysicianLicence)

        flag = matcher.matches()

        return flag
    }

    fun generateUUID(context: Context): String {

        val tm = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val tmDevice: String
        val tmSerial: String
        val tmPhone: String
        val androidId: String
        tmDevice = "" + tm.deviceId
        tmSerial = "" + tm.simSerialNumber
        androidId = "" + android.provider.Settings.Secure.getString(
                context.contentResolver,
                android.provider.Settings.Secure.ANDROID_ID)

        val deviceUuid = UUID(androidId.hashCode().toLong(),
                tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong())
        return deviceUuid.toString()
    }

    companion object {


        fun regexAwardName(str: String): Boolean {
            var flag = true
            val pattern = Pattern.compile("^[a-zA-Z\u4e00-\u9fa5 .]+$")
            val matcher = pattern.matcher(str)
            flag = matcher.matches()
            return flag
        }

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

        val instance = RegexUtil()

        val daySdf = SimpleDateFormat(
                "yyyy-MM-dd")
        val daymins = SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss")
        val dayminss = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss")

        val date4WithDraw = SimpleDateFormat(
                "yyyy.MM.dd HH:mm")

        val dayChina = SimpleDateFormat(
                "yyyy年MM月dd日")
        val dayChinaMD = SimpleDateFormat(
                "MM月dd")

        val enSdf = SimpleDateFormat(
                "yyyyMMdd")

        val sdf_china = SimpleDateFormat(
                "yyyy/MM/dd", Locale.CHINA)

        /***
         * 获取两个日期之间的天数
         *
         * @param time1
         * @param time2
         * @return
         */
        fun getQuot(time1: String, time2: String): Long {
            var quot: Long = 0
            try {
                val date1 = daySdf.parse(time1)
                val date2 = daySdf.parse(time2)
                quot = date1.time - date2.time
                quot = quot / 1000 / 60 / 60 / 24
            } catch (e: ParseException) {
                e.printStackTrace()
            } catch (e: Exception) {
                // TODO: handle exception
            }

            return quot
        }

        /**
         * 计算两个日期之间相差的天数
         *
         * @param date11
         * @param date22
         * @return
         * @throws ParseException
         */
        @Throws(ParseException::class)
        fun daysBetween(date11: String, date22: String): Int {
            val date1 = daySdf.parse(date11)
            val date2 = daySdf.parse(date22)
            val cal = Calendar.getInstance()
            cal.time = date1
            val time1 = cal.timeInMillis
            cal.time = date2
            val time2 = cal.timeInMillis
            val between_days = (time2 - time1) / (1000 * 3600 * 24)

            return Integer.parseInt(between_days.toString())
        }

        /**
         * @param strTelNum
         * @return
         * @note
         */
        fun regexTelNum(strTelNum: String): Boolean {
            var flag = true

            val regExp = "^[1]([3][0-9]{1}|[4][0-9]{1}|[5][0-9]{1}|[6][0-9]{1}|[7][0-9]{1}|[8][0-9]{1})[0-9]{8}"

            val pattern = Pattern.compile(regExp)
            val matcher = pattern.matcher(strTelNum)

            flag = matcher.matches()

            return flag
        }

        /**
         * @param strTelNum
         * @return
         * @note
         */
        fun regexUrerName(strTelNum: String): Boolean {
            var flag = true
            val regExp = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$"
            val pattern = Pattern.compile(regExp)
            val matcher = pattern.matcher(strTelNum)
            flag = matcher.matches()
            return flag
        }

        // 过滤数字
        fun getNum(str: String): Int {
            val regEx = "[^0-9]"
            val p = Pattern.compile(regEx)
            val m = p.matcher(str)
            return Integer.parseInt(m.replaceAll("").trim { it <= ' ' })
        }

        /**
         * @param strEnter
         * @return
         * @note 判断用户输入的是否有特殊字符
         */
        fun regexEnter(strEnter: String): Boolean {
            var flag = true

            val regExp = "[+-`~!@#$^&*()=|{}':;\",\\[\\].<>/?~！@#￥�?�?*（）—�?|｛｝《�?【�?·‘；：�?�?。，、？]"

            val pattern = Pattern.compile(regExp)

            for (i in 0 until strEnter.length) {
                val ca = CharArray(1)

                ca[0] = strEnter[i]

                val matcher = pattern.matcher(String(ca))

                flag = matcher.matches()
                if (flag) {
                    break
                }
            }
            return flag
        }


        fun RegexName(str: String): Boolean {
            var flag = true
            val pattern = Pattern.compile("^[a-zA-Z0-9_\u4e00-\u9fa5]+$")
            val matcher = pattern.matcher(str)
            flag = matcher.matches()
            return flag
        }

        // 过滤特殊字符
        @Throws(PatternSyntaxException::class)
        fun StringFilter(str: String): String {
            // 只允许字母和数字
            // String regEx = "[^a-zA-Z0-9]";
            // 清除掉所有特殊字符
            val regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"
            val p = Pattern.compile(regEx)
            val m = p.matcher(str)
            return m.replaceAll("").trim { it <= ' ' }
        }

        // 校验邮箱
        fun emailFormat(email: String): Boolean {
            var tag = false
            // final String pattern1 =
            val check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
            val pattern = Pattern.compile(check)
            val mat = pattern.matcher(email)
            if (!mat.find()) {
                tag = true
            }
            return tag
        }

        /***
         * @param cardId
         * @return
         */
        fun checkBankCard(cardId: String): Boolean {
            val bit = getBankCardCheckCode(cardId
                    .substring(0, cardId.length - 1))
            return if (bit == 'N') {
                false
            } else cardId[cardId.length - 1] == bit
        }

        fun getBankCardCheckCode(nonCheckCodeCardId: String?): Char {
            if (nonCheckCodeCardId == null
                    || nonCheckCodeCardId.trim { it <= ' ' }.length == 0
                    || !nonCheckCodeCardId.matches("\\d+".toRegex())) {
                // 如果传的不是数据返回N
                return 'N'
            }
            val chs = nonCheckCodeCardId.trim { it <= ' ' }.toCharArray()
            var luhmSum = 0
            var i = chs.size - 1
            var j = 0
            while (i >= 0) {
                var k = chs[i] - '0'
                if (j % 2 == 0) {
                    k *= 2
                    k = k / 10 + k % 10
                }
                luhmSum += k
                i--
                j++
            }
            return if (luhmSum % 10 == 0) '0' else (10 - luhmSum % 10 + 0).toChar()
        }

        // 格式化时间
        fun getTime(dateStr: String): String? {
            val sdf1 = SimpleDateFormat("yyyy/MM/dd H:mm:ss")
            val sdf2 = SimpleDateFormat("yyyy-MM-dd")
            var dateString: String? = null
            try {
                dateString = sdf2.format(sdf1.parse(dateStr))
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return dateString
        }

        /**
         * 检测String是否全是中文
         *
         * @param name
         * @return
         */
        fun checkNameChese(name: String): Boolean {
            var res = true
            val cTemp = name.toCharArray()
            for (i in 0 until name.length) {
                if (!isChinese(cTemp[i])) {
                    res = false
                    break
                }
            }
            return res
        }

        /**
         * 判定输入汉字
         *
         * @param c
         * @return
         */
        fun isChinese(c: Char): Boolean {
            val ub = Character.UnicodeBlock.of(c)
            return if (ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                true
            } else false
        }


        fun setPricePoint(editText: EditText) {
            editText.addTextChangedListener(object : TextWatcher {

                override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                           count: Int) {
                    var s = s
                    if (s.toString().contains(".")) {
                        if (s.length - 1 - s.toString().indexOf(".") > 1) {
                            s = s.toString().subSequence(0,
                                    s.toString().indexOf(".") + 2)
                            editText.setText(s)
                            editText.setSelection(s.length)
                        }
                    }
                    if (s.toString().trim { it <= ' ' }.substring(0) == ".") {
                        s = "0" + s
                        editText.setText(s)
                        editText.setSelection(2)
                    }

                    if (s.toString().startsWith("0") && s.toString().trim { it <= ' ' }.length > 1) {
                        if (s.toString().substring(1, 2) != ".") {
                            editText.setText(s.subSequence(0, 1))
                            editText.setSelection(1)
                            return
                        }
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                               after: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    // TODO Auto-generated method stub
                    val content = s?.toString()
                    if (s == null || s.length == 0) {
                        return
                    }
                    val size = content!!.length
                    if (!content.endsWith(".")) { //最后输入的不是点，无需处理
                        return
                    }
                    if (content.substring(0, size - 1).contains(".")) { //判断之前有没有输入过点
                        s.delete(size - 1, size)//之前有输入过点，删除重复输入的点
                    }
                }

            })
        }

        /**
         * 获取网落图片资源
         *
         * @param
         * @return
         */
        @Throws(IOException::class)
        fun getBitmap(path: String): Bitmap? {

            val url = URL(path)
            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 5000
            conn.requestMethod = "GET"
            if (conn.responseCode == 200) {
                val inputStream = conn.inputStream
                return BitmapFactory.decodeStream(inputStream)
            }
            return null
        }
    }
}
