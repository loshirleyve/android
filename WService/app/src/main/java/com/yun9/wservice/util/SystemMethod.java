package com.yun9.wservice.util;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SystemMethod {
	private static final int ww = 480;
	private static final int hh = 800;

	/**
	 * 对网络连接状态进行判断
	 * 
	 * @return true, 可用； false， 不可用
	 */
	public static boolean isOpenNetwork(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}

		return false;
	}

	public static int dip2px(Context context, int value) {
		float scaleing = context.getResources().getDisplayMetrics().density;
		return (int) (value * scaleing + 0.5f);
	}

	public static int px2dip(Context context, int value) {
		float scaling = context.getResources().getDisplayMetrics().density;
		return (int) (value / scaling + 0.5f);
	}

	/**
	 * 图片变圆角
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
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

	/**
	 * save
	 * 
	 * @param path
	 * @param buffer
	 * @return
	 */
	public static int saveBitmap(String path, byte[] buffer) {
		int result = -1;
		try {
			FileOutputStream out = new FileOutputStream(new File(path));
			out.write(buffer);
			out.flush();
			out.close();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getdecodeBitmap(String filePath) {
		if (filePath == null) {
			return null;
		}
		Options options = new Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

		int width = options.outWidth;
		int height = options.outHeight;
		float scale = 1f;
		if (width > ww && width > height) {
			scale = width / ww;
		} else if (height > hh && height > width) {
			scale = height / hh;
		} else {
			scale = 1;
		}

		options.inSampleSize = (int) scale;
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(filePath, options);
		return bitmap;
	}

	public static int saveBitmap(String path, Bitmap bitmap) {
		int result = -1;
		try {
			FileOutputStream fos = new FileOutputStream(new File(path));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public static Map<String, String> collectDeviceInfo(Context ctx) {
		Map<String, String> infos = new HashMap<String, String>();

		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(SystemMethod.class.getName(),
					"an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(SystemMethod.class.getName(), field.getName() + " : "
						+ field.get(null));
			} catch (Exception e) {
				Log.e(SystemMethod.class.getName(),
						"an error occured when collect crash info", e);
			}
		}

		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);

		infos.put("TMDeviceid", tm.getDeviceId());
		infos.put("TMDeviceSoftwareVersion", tm.getDeviceSoftwareVersion());
		infos.put("TMLine1Number", tm.getLine1Number());
		infos.put("TMNetworkCountryIso", tm.getNetworkCountryIso());
		infos.put("TMNetworkOperator", tm.getNetworkOperator());
		infos.put("TMNetworkOperatorName", tm.getNetworkOperatorName());
		infos.put("TMNetworkType", tm.getNetworkType() + "");
		infos.put("TMPhoneType", tm.getPhoneType() + "");
		infos.put("TMSimCountryIso", tm.getSimCountryIso());
		infos.put("TMSimOperator", tm.getSimOperator());
		infos.put("TMSimOperatorName", tm.getSimOperatorName());
		infos.put("TMSimSerialNumber", tm.getSimSerialNumber());
		infos.put("TMSimState", tm.getSimState() + "");
		infos.put("TMSubscriberId(IMSI)", tm.getSubscriberId());
		infos.put("TMVoiceMailNumber", tm.getVoiceMailNumber());

		return infos;
	}

}
