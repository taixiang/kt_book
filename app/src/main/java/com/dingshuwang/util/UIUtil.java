package com.dingshuwang.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dingshuwang.LoginActivity;
import com.dingshuwang.R;
import com.dingshuwang.view.AlertDialogUI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;


public class UIUtil {
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

	/***
	 * 查看大图
	 *  @param position
	 * @param imgList
	 * @param mContext
	 */

	/**
	 * 判断某个服务是否正在运行的方法
	 *
	 * @param mContext
	 * @param serviceName
	 *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
	 * @return true代表正在运行，false代表服务没有正在运行
	 */
	public static boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}

	/** 求差 */
	public static float getSubtraction(float float1, float float2) {
		BigDecimal b1 = new BigDecimal(Float.toString(float1));
		BigDecimal b2 = new BigDecimal(Float.toString(float2));
		if (b1.subtract(b2).floatValue() <= 0) {
			return 0;
		}
		return b1.subtract(b2).floatValue();
	}

	public static double getSubtraction(double float1, double float2) {
		BigDecimal b1 = new BigDecimal(Double.toString(float1));
		BigDecimal b2 = new BigDecimal(Double.toString(float2));
		if (b1.subtract(b2).doubleValue() <= 0) {
			return 0;
		}
		return b1.subtract(b2).doubleValue();
	}

	public static double getSubtraction_(double float1, double float2) {
		BigDecimal b1 = new BigDecimal(Double.toString(float1));
		BigDecimal b2 = new BigDecimal(Double.toString(float2));
		if (b1.subtract(b2).doubleValue() <= 0) {
			return 0;
		}
		return b1.subtract(b2).setScale(5, BigDecimal.ROUND_DOWN).doubleValue();
	}

	/*** 求和 */
	public static float getAdd(float float1, float float2) {
		BigDecimal b1 = new BigDecimal(Float.toString(float1));
		BigDecimal b2 = new BigDecimal(Float.toString(float2));
		return b1.add(b2).setScale(5, BigDecimal.ROUND_FLOOR).floatValue();
	}

	public static double getAdd(double float1, double float2) {
		BigDecimal b1 = new BigDecimal(Double.toString(float1));
		BigDecimal b2 = new BigDecimal(Double.toString(float2));
		return b1.add(b2).setScale(5, BigDecimal.ROUND_DOWN).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static float mul(float v1, float v2) {
		BigDecimal b1 = new BigDecimal(Float.toString(v1));
		BigDecimal b2 = new BigDecimal(Float.toString(v2));
		return b1.multiply(b2).floatValue();
	}

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double mul_(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/***
	 * 除法
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static float divide(float v1, float v2) {
		if (v2 <= 0) {
			return 0;
		}
		BigDecimal b1 = new BigDecimal(Float.toString(v1));
		BigDecimal b2 = new BigDecimal(Float.toString(v2));
		return b1.divide(b2, 2).setScale(2, BigDecimal.ROUND_FLOOR)
				.floatValue();
	}

	public static double divide(double v1, double v2) {
		if (v2 <= 0) {
			return 0;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2,3,BigDecimal.ROUND_HALF_UP).setScale(3, BigDecimal.ROUND_FLOOR)
				.floatValue();
	}

	public static double divide_(double v1, double v2) {
		if (v2 <= 0) {
			return 0;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2,3,BigDecimal.ROUND_HALF_UP).setScale(3, BigDecimal.ROUND_FLOOR)
				.floatValue();
	}


	/**
	 * 用来判断服务是否运行.
	 * 
	 * @paramcontext
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * 设置本地图片
	 * 
	 * @param url
	 * @return
	 */
	public static void setLoacalBitmap(ImageView view, String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			Bitmap bitmap = BitmapFactory.decodeStream(fis);
			if (bitmap != null) {
				view.setImageBitmap(bitmap);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("error", e.toString());
		}
		return false;
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
	public static void hideSystemKeyBoard(Context mcontext, View v) {
		try {
			InputMethodManager imm = (InputMethodManager) mcontext
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		} catch (Exception e) {
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 
	 * 二维码的 大小
	 * 
	 * @paramlistView
	 * @author Steven
	 * @return void
	 */
	public static int getMaxCodeSize(float density) {
		int textSize = 0;
		if (density >= 3.0) {
			textSize = 220;
		} else if (density > 2.0 && density < 3.0) {
			textSize = 180;
		} else if (density >= 1.5 && density < 2.0) {
			textSize = 140;
		} else if (density >= 1.0 && density < 1.5) {
			textSize = 100;
		} else {
			textSize = 100;
		}
		return textSize;
	}

	/**
	 * 密度计算 字体大小 文本框
	 * 
	 * @paramistView
	 * @author Steven
	 * @return void
	 */
	public static float getTextSize(float density) {
		float textSize = 0.0f;
		if (density >= 3.0) {
			textSize = 24;
		} else if (density >= 2.0 && density < 3.0) {
			textSize = 16;
		} else if (density >= 1.5 && density < 2.0) {
			textSize = 14;
		} else if (density >= 1.0 && density < 1.5) {
			textSize = 10;
		} else {
			textSize = 12;
		}
		return textSize;
	}

	/**
	 * 密度计算 发送短信 字体 字体大小
	 * 
	 * @param
	 * @author Steven
	 * @return void
	 */
	public static float getSendCodeBtnTextSize(float density) {
		float textSize = 0.0f;
		if (density >= 3.0) {
			textSize = 15;
		} else if (density >= 2.0 && density < 3.0) {
			textSize = 14;
		} else if (density >= 1.5 && density < 2.0) {
			textSize = 12;
		} else if (density >= 1.0 && density < 1.5) {
			textSize = 10;
		} else {
			textSize = 10;
		}
		return textSize;
	}

	/**
	 * 动态计算设置listview高度
	 * 
	 * @函数名 setListViewHeightBasedOnChildren
	 * @功能 TODO
	 * @param listView
	 * @备注 <其它说明>
	 */
	public static void changeListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		// ((MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除

		listView.setLayoutParams(params);
	}

	private static Toast toast = null;

	public static void showToastl(Context con, String str) {
		if (toast == null) {
			toast = Toast.makeText(con, str, Toast.LENGTH_LONG);
		} else {
			toast.setText(str);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	public static void showToasts(Context con, String str) {
		if (toast == null) {
			toast = Toast.makeText(con, str, Toast.LENGTH_SHORT);
		} else {
			toast.setText(str);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	public static void showToasts(Context con, int res) {
		if (toast == null) {
			toast = Toast.makeText(con, res, Toast.LENGTH_SHORT);
		} else {
			toast.setText(res);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	public static int Dp2Px(Context con, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp,
				con.getResources().getDisplayMetrics());
	}

	public static int Px2Dp(Context con, int px) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px,
				con.getResources().getDisplayMetrics());
	}

	public static int getPxFromDpRes(Context con, int resid) {
		return (int) con.getResources().getDimension(resid);
	}

	public static Drawable resizeDrawable(Context con, Drawable dr, int w, int h) {
		// Read your drawable from somewhere
		// Drawable dr = getResources().getDrawable(R.drawable.somedrawable);
		Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
		// Scale it to 50 x 50
		Drawable rdr = new BitmapDrawable(con.getResources(),
				Bitmap.createScaledBitmap(bitmap, w, h, true));
		// Set your new, scaled drawable "d"
		return rdr;
	}

	public static Drawable Bitmap2Drawable(Context con, Bitmap bm) {
		BitmapDrawable bd = new BitmapDrawable(con.getResources(), bm);
		return bd;
	}

	public static Bitmap Drawable2Bitmap(Context con, Drawable dr) {
		int w = dr.getIntrinsicWidth();
		int h = dr.getIntrinsicHeight();

		// 取 drawable 的颜色格式
		Config config = dr.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
				: Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		dr.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		dr.draw(canvas);
		return bitmap;
	}

	public static Bitmap rotateBitmap(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle(); // Android开发网再次提示Bitmap操作完应该显示的释放
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				// Android123建议大家如何出现了内存不足异常，最好return 原始的bitmap对象。.
				// Log.e(TAG, "rotateBmp OutOfMemoryError");
				ex.printStackTrace();
			}
		}
		return b;
	}

	// to do need to improve and do with jpg and quality
	// // image.compressToJpeg(
	// new Rect(0, 0, image.getWidth(), image.getHeight()), 90,
	// filecon);

	public static Bitmap loadBitmapFromLocal(String imgPath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		//
		options.inPreferredConfig = Config.RGB_565;// 表示16位位图
															// 565代表对应三原色占的位数
		// options.inInputShareable=true; //??
		options.inPurgeable = true;// 设置图片可以被回收
		options.inTempStorage = new byte[1024 * 1024 * 10];
		//
		options.inSampleSize = 1;
		Bitmap bm = BitmapFactory.decodeFile(imgPath, options);
		return bm;
		// jpgView.setImageBitmap(bm);
	}

	public static Bitmap loadBitmapFromLocalWithSampleSize(String imgPath,
			int sampleSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = sampleSize;
		Bitmap bm = BitmapFactory.decodeFile(imgPath, options);
		return bm;
		// jpgView.setImageBitmap(bm);
	}

	// todo calc file size with inJustDecodeBounds
	// todo get bitmap with inSampleSize

	public static void saveBitmap2JPG(String path, Bitmap bitmap) {
		File f = new File(path);// ("/sdcard/" + bitName + ".png");
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveBitmap2JPG(String dir, String filename, Bitmap bitmap) {
		saveBitmap2JPG(dir + File.separator + filename, bitmap);
	}

	public static void saveBitmap2PNG(String path, Bitmap bitmap) {
		File f = new File(path);// ("/sdcard/" + bitName + ".png");
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 50, fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveBitmap2PNG(String dir, String filename, Bitmap bitmap) {
		saveBitmap2PNG(dir + File.separator + filename, bitmap);

	}




	/**
	 * 
	 * like ".../data/tmpResImgFile.jpg"
	 * 
	 * 
	 */
	public static String saveResImg2Path(int resid, Context con, String filePath) {

		Drawable drawable = con.getResources().getDrawable(resid);
		Bitmap bmp = Drawable2Bitmap(con, drawable);
		String tmpResImgPath = filePath;
		saveBitmap2JPG(tmpResImgPath, bmp);
		return tmpResImgPath;

	}

	/**
	 * 安全获取edit的string
	 */
	public static String getEditTextSafe(EditText editText) {
		if (editText != null && editText.getText() != null
				&& !(editText.getText().toString().trim().equals(""))) {
			return editText.getText().toString().trim();
		} else {
			return "";
		}
	}

	/**
	 * 判断edittext是否null,是的话返回true
	 */
	public static Boolean isEditTextEmpty(EditText editText) {
		if (editText != null && editText.getText() != null
				&& !(editText.getText().toString().trim().equals(""))) {
			return false;
		} else {
			return true;
		}
	}

	public static void alert(Context context, String message) {
		final AlertDialogUI alertDialog = new AlertDialogUI(context);
		alertDialog.setMessage(message);
		alertDialog.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			}
		});
	}

	public static void alert(Context context, String title, String message) {
		final AlertDialogUI alertDialog = new AlertDialogUI(context);
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			}
		});
	}

	public static void alert(Context context, String title, String message,
			String ok) {
		final AlertDialogUI alertDialog = new AlertDialogUI(context);
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setPositiveButton(ok, new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			}
		});
	}

	public static ProgressDialog loadingDialog(Context context) {

		ProgressDialog dlg = new ProgressDialog(context,
				ProgressDialog.STYLE_SPINNER);
		dlg.setCancelable(false);
		dlg.setCanceledOnTouchOutside(false);
		dlg.show();

		ProgressBar progress = (ProgressBar) dlg
				.findViewById(android.R.id.progress);
		LayoutParams progressParams = progress.getLayoutParams();
		progressParams.height = LayoutParams.WRAP_CONTENT;
		progressParams.width = LayoutParams.MATCH_PARENT;// set MATCH_PARENT to
															// center
		progress.setLayoutParams(progressParams);

		LinearLayout linear = (LinearLayout) progress.getParent();
		FrameLayout contentp = (FrameLayout) linear.getParent();
		FrameLayout contentp2 = (FrameLayout) contentp.getParent();
		FrameLayout contentp3 = (FrameLayout) contentp2.getParent();
		contentp3.setBackgroundResource(0);// remove gray background

		return dlg;
	}

	public static ProgressDialog loadingDialog(Context context, String title,
			String message) {
		ProgressDialog dlg = ProgressDialog.show(context, title, message, true,
				false);
		TextView msg = (TextView) dlg.findViewById(android.R.id.message);
		msg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		return dlg;
	}

	private static ProgressDialog loadingDialogTranslucent(Context context) {

		ProgressDialog dlg = new ProgressDialog(context,
				ProgressDialog.STYLE_SPINNER);
		dlg.setCancelable(false);
		dlg.setCanceledOnTouchOutside(false);
		dlg.show();

		ProgressBar progress = (ProgressBar) dlg
				.findViewById(android.R.id.progress);
		LayoutParams progressParams = progress.getLayoutParams();
		progressParams.height = LayoutParams.WRAP_CONTENT;
		progressParams.width = LayoutParams.MATCH_PARENT;// set MATCH_PARENT to
															// center
		progress.setLayoutParams(progressParams);

		LinearLayout linear = (LinearLayout) progress.getParent();
		FrameLayout contentp = (FrameLayout) linear.getParent();
		FrameLayout contentp2 = (FrameLayout) contentp.getParent();
		FrameLayout contentp3 = (FrameLayout) contentp2.getParent();
		LinearLayout contentp4 = (LinearLayout) contentp3.getParent();
		FrameLayout contentp5 = (FrameLayout) contentp4.getParent();
		FrameLayout contentp6 = (FrameLayout) contentp5.getParent();
		FrameLayout contentp7 = (FrameLayout) contentp6.getParent();

		contentp3.setBackgroundResource(0);// remove gray background
		// contentp7.setBackgroundResource(0);

		// contentp4.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
		// contentp5.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
		// contentp6.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
		// contentp7.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

		return dlg;
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
	public static Bitmap scaleBitmap(Bitmap bitmapToScale, float newWidth,
			float newHeight) {
		if (bitmapToScale == null) {
			return null;
		}

		// get the original width and height
		int width = bitmapToScale.getWidth();
		int height = bitmapToScale.getHeight();

		if (width == newWidth && height == newHeight) {
			return bitmapToScale;
		}

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();

		// resize the bit map
		matrix.postScale(newWidth / width, newHeight / height);

		// recreate the new Bitmap and set it back
		return Bitmap.createBitmap(bitmapToScale, 0, 0,
				bitmapToScale.getWidth(), bitmapToScale.getHeight(), matrix,
				true);
	}

	private static long lastclickTime;

	public static boolean isfastdoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastclickTime;
		if (0 < timeD && timeD < 1000) {
			return true;
		}
		lastclickTime = time;
		return false;
	}

	/**
	 * 登录
	 * 
	 * @author Davis
	 * @param mContext
	 */
	public static void checkLogin(final Context mContext) {
		final AlertDialogUI dialogUI = new AlertDialogUI(mContext);
		dialogUI.setTitle("登录");
		dialogUI.setMessage("您还没有登录，不能收藏！是否登录？");
		dialogUI.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(View v) {

				dialogUI.dismiss();
				Intent intent = new Intent(mContext, LoginActivity.class);
				mContext.startActivity(intent);
			}
		});
		dialogUI.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialogUI.dismiss();
			}
		});
	}

	/***
	 * 拨打电话
	 * 
	 * @author Davis
	 * @param phoneno
	 * @param mContext
	 */
	public static void cellPhone(final String phoneno, final Context mContext) {
		final AlertDialogUI dialogUI = new AlertDialogUI(mContext);
		dialogUI.setTitle("拨号");
		dialogUI.setMessage("您是否要拨打:" + phoneno);
		dialogUI.setPositiveButton("确定",
				new OnClickListener() {
					@Override
					public void onClick(View v) {

						dialogUI.dismiss();
						Intent intent = new Intent(Intent.ACTION_DIAL, Uri
								.parse("tel:" + phoneno));
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mContext.startActivity(intent);
						((Activity) mContext).overridePendingTransition(
								R.anim.push_left_in, R.anim.push_left_out);
					}
				});
		dialogUI.setNegativeButton("取消",
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialogUI.dismiss();
					}
				});
	}

	/**
	 * 判断是否安装目标应用
	 * 
	 * @author Davis
	 * @param packageName
	 *            目标应用安装后的包名
	 * @return 是否已安装目标应用
	 */
	private static boolean isInstallByread(String packageName) {
		return new File("/data/data/" + packageName).exists();
	}

	public static String bitmapToString(String filePath) {
		int degree = readPictureDegree(filePath);
		Bitmap bm = getSmallBitmap(filePath);
		bm = rotaingImageView(degree, bm);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 768, 1024);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	@SuppressLint("NewApi")
	public static void setGridViewHeightBasedOnChildren(GridView gridView) {
		// 获取GridView对应的Adapter
		try {
			ListAdapter listAdapter = gridView.getAdapter();
			if (listAdapter == null) {
				return;
			}
			int rows;
			int columns = 0;
			int horizontalBorderHeight = 0;
			int verBorderHeight = 0;
			Class<?> clazz = gridView.getClass();
			try {
				// 利用反射，取得每行显示的个数
				Field column = clazz.getDeclaredField("mRequestedNumColumns");
				column.setAccessible(true);
				columns = (Integer) column.get(gridView);
				// 利用反射，取得横向分割线高度
				Field horizontalSpacing = clazz
						.getDeclaredField("mRequestedHorizontalSpacing");
				horizontalSpacing.setAccessible(true);
				horizontalBorderHeight = (Integer) horizontalSpacing
						.get(gridView);
				verBorderHeight = gridView.getVerticalSpacing();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			// 判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
			if (listAdapter.getCount() % columns > 0) {
				rows = listAdapter.getCount() / columns + 1;
			} else {
				rows = listAdapter.getCount() / columns;
			}
			int totalHeight = 0;
			for (int i = 0; i < rows; i++) { // 只计算每项高度*行数
				View listItem = listAdapter.getView(i, null, gridView);
				listItem.measure(0, 0); // 计算子项View 的宽高
				totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
			}
			LayoutParams params = gridView.getLayoutParams();
			params.height = totalHeight + horizontalBorderHeight * (rows - 1)
					+ verBorderHeight * (rows - 1);// 最后加上分割线总高度
			gridView.setLayoutParams(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return degree;
	}



	/***
	 * @author Davis
	 * @param mContext
	 * @return
	 * @throws Exception
	 */
	public static String getVersionName(Context mContext) {
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = mContext.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(
					mContext.getPackageName(), 0);
			return packInfo.versionName;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}


	public static Bitmap returnBitMap(String url, int type, int height) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Config.RGB_565;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		if (type > 0) {
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 8;
		}
		opts.outHeight = height;
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			if (null != myFileUrl) {
				HttpURLConnection conn = (HttpURLConnection) myFileUrl
						.openConnection();
				conn.setDoInput(true);
				conn.connect();
				InputStream is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return bitmap;
	}

	@SuppressWarnings("unused")
	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	private static int color(String colorStr) {
		int color = 0;
		String[] colorStrs = colorStr.split(",");
		color = Color.rgb(Integer.parseInt(colorStrs[0].trim()),
				Integer.parseInt(colorStrs[1].trim()),
				Integer.parseInt(colorStrs[2].trim()));
		return color;
	}

	/**
	 * 从相册得到的url转换为SD卡中图片路径
	 */
	@SuppressLint("NewApi")
	public static String getPath(Uri uri, Context mContext) {
		String filePath = "";
		try {
			if (StringUtils.isEmpty(uri.getAuthority())) {
				return null;
			}
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = ((Activity) mContext).managedQuery(uri, projection,
					null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			filePath = cursor.getString(column_index);
			if (StringUtils.isEmpty(filePath)) {
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
					String wholeID = DocumentsContract.getDocumentId(uri);
					String id = wholeID.split(":")[1];
					String[] column = { MediaStore.Images.Media.DATA };
					String sel = MediaStore.Images.Media._ID + " = ?";
					Cursor cursor1 = mContext.getContentResolver().query(
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
							column, sel, new String[] { id }, null);
					int columnIndex = cursor1.getColumnIndex(column[0]);
					if (cursor1.moveToFirst()) {
						filePath = cursor1.getString(columnIndex);
					}
					cursor1.close();
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filePath;
	}

	/**
	 * 获取屏幕尺寸与密度.
	 * 
	 * @param context
	 *            the context
	 * @return mDisplayMetrics
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		Resources mResources;
		if (context == null) {
			mResources = Resources.getSystem();

		} else {
			mResources = context.getResources();
		}
		// DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5,
		// xdpi=160.421, ydpi=159.497}
		// DisplayMetrics{density=2.0, width=720, height=1280,
		// scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
		DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
		return mDisplayMetrics;
	}

	/** 设置图片圆角 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void setCameraDisplayOrientation(Activity activity,
			int cameraId, Camera camera) {
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}
		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else {
			// back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}

	public final static void loadAppMarketPage(Context con) {
		try {
			Uri uri = Uri.parse("market://search?q=pname:"
					+ con.getPackageName().trim());
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			con.startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UIUtil.showToasts(con,"您还没有安装任何应用市场！");
		}
	}
}
