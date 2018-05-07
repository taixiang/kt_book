package com.dingshuwang.util;

import android.util.Log;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtils {
	public static final SimpleDateFormat sdf_yy_MM_dd = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat sdf_yy_MM_dd_HH_mm = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat sdf_yy_MM_dd_HH_mm_ss = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat sdf_HH_mm_ss = new SimpleDateFormat(
			"HH:mm");

	/**
	 * String helper to ensure the object safely translates to a string, will
	 * not return null
	 */
	public static String toString(Object o) {
		return o != null ? o.toString() : "";
	}

	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}

		return str.trim().length() == 0;
	}

	/**
	 * segs equalsIgnoreCase
	 */
	public static boolean isEmptyOrLikeSegs(String str, String... segs) {
		if (str == null) {
			return true;
		}

		if (str.trim().length() == 0) {
			return true;
		}

		for (String seg : segs) {
			if (str.equalsIgnoreCase(seg)) {
				return true;
			}
		}

		return false;
	}

	public static String capitalize(String str) {
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	public static float floatToPercent(float f) {
		return (f * 100);
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumericFast(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	public static boolean isNumericPattern(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static boolean checkSpace(String str) {
		boolean flag = false;
		if (str.contains(" ")) {
			flag = true;
			return flag;
		}
		return flag;
	}

	public static String changeData(String dateStr) {
		Date date = new Date(Long.parseLong(dateStr));
		return sdf_yy_MM_dd_HH_mm.format(date);
	}

	/***
	 * 变换为时分秒格式
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static String changeHMSData(String dateStr) {
		Date date = null;
		try {
			date = sdf_yy_MM_dd_HH_mm_ss.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf_HH_mm_ss.format(date);
	}

	/***
	 * 变换为时分秒格式
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static String changeYMDData(String dateStr) {
		Date date = null;
		try {
			date = sdf_yy_MM_dd_HH_mm_ss.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf_yy_MM_dd_HH_mm.format(date);
	}

	public static String getObjectKeyName(String key) {
		if (key.length() == 0) {
			return key;
		}
		int index = key.substring(0, key.length() - 1).lastIndexOf('/');
		if (index == -1) {
			return key;
		}

		return key.substring(index + 1);
	}

	public static String checkBlankString(String param) {
		if (param == null) {
			return "";
		}
		return param;
	}

	public static String md5(String encryptStr) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(encryptStr.getBytes("UTF-8"));
		byte[] digest = md.digest();
		StringBuffer md5 = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			md5.append(Character.forDigit((digest[i] & 0xF0) >> 4, 16));
			md5.append(Character.forDigit((digest[i] & 0xF), 16));
		}

		encryptStr = md5.toString();
		return encryptStr;
	}

	public static String sha1(String encryptStr) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(encryptStr.getBytes("UTF-8"));
		byte[] digest = md.digest();
		StringBuffer sha1 = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			sha1.append(Character.forDigit((digest[i] & 0xF0) >> 4, 16));
			sha1.append(Character.forDigit((digest[i] & 0xF), 16));
		}

		encryptStr = sha1.toString();
		return encryptStr;
	}

	public static byte[] md5Byte(String encryptStr) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(encryptStr.getBytes("UTF-8"));
		return md.digest();
	}

	public static byte[] sha1Byte(String encryptStr) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(encryptStr.getBytes("UTF-8"));
		return md.digest();
	}

	public static String genUUIDHexString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static UUID parseUUIDFromHexString(String hexUUID) throws Exception {
		byte[] data = hexStringToByteArray(hexUUID);
		long msb = 0;
		long lsb = 0;

		for (int i = 0; i < 8; i++)
			msb = (msb << 8) | (data[i] & 0xff);
		for (int i = 8; i < 16; i++)
			lsb = (lsb << 8) | (data[i] & 0xff);

		return new UUID(msb, lsb);
	}

	private static char convertDigit(int value) {

		value &= 0x0f;
		if (value >= 10)
			return ((char) (value - 10 + 'a'));
		else
			return ((char) (value + '0'));

	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static String convert(final byte bytes[]) {

		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(convertDigit((int) (bytes[i] >> 4)));
			sb.append(convertDigit((int) (bytes[i] & 0x0f)));
		}
		return (sb.toString());

	}

	public static String convert(final byte bytes[], int pos, int len) {
		StringBuffer sb = new StringBuffer(len * 2);
		for (int i = pos; i < pos + len; i++) {
			sb.append(convertDigit((int) (bytes[i] >> 4)));
			sb.append(convertDigit((int) (bytes[i] & 0x0f)));
		}
		return (sb.toString());

	}


	public static String doubleFormat(double d){
		DecimalFormat    df   = new DecimalFormat("######0.00");

		return df.format(d);
	}

}
