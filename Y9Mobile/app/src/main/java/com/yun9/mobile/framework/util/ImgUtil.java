package com.yun9.mobile.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class ImgUtil {
	private static ImgUtil instance;
	private static ExecutorService executorThreadPool = Executors
			.newFixedThreadPool(1);
	static {
		instance = new ImgUtil();
	}

	public static ImgUtil getInstance() {
		if (instance != null) {
			return instance;
		}
		return null;
	}

	public void loadBitmap(final String path, final View view,
			final OnLoadBitmapListener listener) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bitmap bitmap = (Bitmap) msg.obj;
				listener.loadImage(bitmap, path, view);
			}
		};
		new Thread() {

			@Override
			public void run() {
				executorThreadPool.execute(new Runnable() {
					@Override
					public void run() {
						Bitmap bitmap = loadBitmapFromCache(path);
						if (bitmap != null) {
							Message msg = handler.obtainMessage();
							msg.obj = bitmap;
							handler.sendMessage(msg);
						}

					}
				});
			}

		}.start();
	}

	private Bitmap loadBitmapFromCache(String path) {

		Bitmap bitmap = null;

		if (bitmap == null) {
			bitmap = loadBitmapFromLocal(path);
		}
		return bitmap;
	}

	private Bitmap loadBitmapFromLocal(String path) {
		if (path == null) {
			return null;
		}

		InputStream is1 = null;
		InputStream is2 = null;

		try {
			BitmapFactory.Options options = new Options();
			options.inJustDecodeBounds = true;
			URL url = new URL(path);
			is1 = url.openStream();
			is2 = url.openStream();

			// Bitmap bitmap = BitmapFactory.decodeFile(path, options);
			Bitmap bitmap = BitmapFactory.decodeStream(is1, null, options);

			float height = 800f;
			float width = 480f;
			float scale = 1;
			if (options.outWidth > width
					&& options.outWidth > options.outHeight) {
				scale = options.outWidth / width;
			} else if (options.outHeight > height
					&& options.outHeight > options.outWidth) {
				scale = options.outHeight / height;
			} else {
				scale = 1;
			}
			options.inSampleSize = (int) scale;
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeStream(is2, null, options);
			if (AssertValue.isNotNull(bitmap)) {
				bitmap = decodeBitmap(bitmap);

				return bitmap;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (AssertValue.isNotNull(is1)) {
				try {
					is1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (AssertValue.isNotNull(is2)) {
				try {
					is2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;

	}

	private Bitmap decodeBitmap(Bitmap bitmap) {
		int scale = 100;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, scale, bos);
		while ((bos.toByteArray().length / 1024) > 30) {
			bos.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, scale, bos);
			scale -= 10;
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		bitmap = BitmapFactory.decodeStream(bis);
		return bitmap;
	}

	public interface OnLoadBitmapListener {
		void loadImage(Bitmap bitmap, String path, View view);
	}
}
