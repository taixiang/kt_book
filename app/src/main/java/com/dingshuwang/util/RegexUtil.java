package com.dingshuwang.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexUtil {


    public static boolean regexAwardName(String str) {
        boolean flag = true;
        Pattern pattern = Pattern.compile("^[a-zA-Z\u4e00-\u9fa5 .]+$");
        Matcher matcher = pattern.matcher(str);
        flag = matcher.matches();
        return flag;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    private static RegexUtil regexUtil = new RegexUtil();

    private RegexUtil() {
        // getHttpClient();.
    }

    public static RegexUtil getInstance() {
        return regexUtil;
    }

    public final static SimpleDateFormat daySdf = new SimpleDateFormat(
            "yyyy-MM-dd");
    public final static SimpleDateFormat daymins = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");
    public final static SimpleDateFormat dayminss = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public final static SimpleDateFormat date4WithDraw = new SimpleDateFormat(
            "yyyy.MM.dd HH:mm");

    public final static SimpleDateFormat dayChina = new SimpleDateFormat(
            "yyyy年MM月dd日");
    public final static SimpleDateFormat dayChinaMD = new SimpleDateFormat(
            "MM月dd");

    public final static SimpleDateFormat enSdf = new SimpleDateFormat(
            "yyyyMMdd");

    public final static SimpleDateFormat sdf_china = new SimpleDateFormat(
            "yyyy/MM/dd", Locale.CHINA);

    /***
     * 获取两个日期之间的天数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long getQuot(String time1, String time2) {
        long quot = 0;
        try {
            Date date1 = daySdf.parse(time1);
            Date date2 = daySdf.parse(time2);
            quot = date1.getTime() - date2.getTime();
            quot = quot / 1000 / 60 / 60 / 24;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return quot;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date11
     * @param date22
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String date11, String date22)
            throws ParseException {
        Date date1 = daySdf.parse(date11);
        Date date2 = daySdf.parse(date22);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @param strTelNum
     * @return
     * @note
     */
    public static boolean regexTelNum(String strTelNum) {
        boolean flag = true;

        String regExp = "^[1]([3][0-9]{1}|[4][0-9]{1}|[5][0-9]{1}|[6][0-9]{1}|[7][0-9]{1}|[8][0-9]{1})[0-9]{8}";

        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(strTelNum);

        flag = matcher.matches();

        return flag;
    }

    /**
     * @param strTelNum
     * @return
     * @note
     */
    public static boolean regexUrerName(String strTelNum) {
        boolean flag = true;
        String regExp = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(strTelNum);
        flag = matcher.matches();
        return flag;
    }

    // 过滤数字
    public static int getNum(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return Integer.parseInt(m.replaceAll("").trim());
    }

    /**
     * @param strEnter
     * @return
     * @note 判断用户输入的是否有特殊字符
     */
    public static boolean regexEnter(String strEnter) {
        boolean flag = true;

        String regExp = "[+-`~!@#$^&*()=|{}':;\",\\[\\].<>/?~！@#￥�?�?*（）—�?|｛｝《�?【�?·‘；：�?�?。，、？]";

        Pattern pattern = Pattern.compile(regExp);

        for (int i = 0; i < strEnter.length(); i++) {
            char[] ca = new char[1];

            ca[0] = strEnter.charAt(i);

            Matcher matcher = pattern.matcher(new String(ca));

            flag = matcher.matches();
            if (flag) {
                break;
            }
        }
        return flag;
    }


    public static boolean RegexName(String str) {
        boolean flag = true;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_\u4e00-\u9fa5]+$");
        Matcher matcher = pattern.matcher(str);
        flag = matcher.matches();
        return flag;
    }

    // 过滤特殊字符

    /**
     * @param str
     * @return
     * @note 过滤特殊字符
     */
    public boolean stringFilter(String str) {
        boolean flag = true;
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~#$%^&*()|'',\\[\\]<>/?~#￥%……&*——+|‘”“’]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            flag = false;
        }
        return flag;
    }

    // 过滤特殊字符
    public static String StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    // 校验邮箱
    public static boolean emailFormat(String email) {
        boolean tag = false;
        // final String pattern1 =
        String check =  "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final String pattern1 = check;
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = true;
        }
        return tag;
    }

    // 校验密码
    public boolean ckeckPass(String passWord) {
        if (passWord != null && passWord.matches("[0-9A-Za-z]*") == true) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * @param strPhysicianLicence
     * @return
     * @note
     */
    public boolean regexPhysicianLicence(String strPhysicianLicence) {
        boolean flag = true;

        String regExp = "^[0-9]{16}";

        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(strPhysicianLicence);

        flag = matcher.matches();

        return flag;
    }

    public String generateUUID(Context context) {

        final TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = ""
                + android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    // 格式化时间
    public static String getTime(String dateStr) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = null;
        try {
            dateString = sdf2.format(sdf1.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
            return true;
        }
        return false;
    }


    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 1) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 2);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String content = s == null ? null : s.toString();
                if (s == null || s.length() == 0) {
                    return;
                }
                int size = content.length();
                if (!content.endsWith(".")) { //最后输入的不是点，无需处理
                    return;
                }
                if (content.substring(0, size - 1).contains(".")) { //判断之前有没有输入过点
                    s.delete(size - 1, size);//之前有输入过点，删除重复输入的点
                }
            }

        });
    }

    /**
     * 获取网落图片资源
     *
     * @param
     * @return
     */
    public static Bitmap getBitmap(String path) throws IOException {

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }
}
