package com.yun9.mobile.imageloader;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.yun9.mobile.R;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.cache.ACache;
import com.yun9.mobile.framework.file.FileFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.FileUrlById;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.Logger;

/**
 * 该工具类用来替代“com.nostra13.universalimageloader.core.ImageLoader”
 * 的使用，使用方法与ImageLoader基本一致；
 * 显示图片时，传递的第一个参数不是uri而是图片文件的ID，即文件对应表(sys_inst_file)的ID
 * 
 * @author yun9
 * 
 */
public class MyImageLoader {

	public static final String CACHE_TAG = "MyImageLoader_cache";

	private static final String FILE_ID_URL_CACHE_KEY = "fileIdAndUrl";

	private volatile static MyImageLoader instance;

	private static final Logger logger = Logger.getLogger(MyImageLoader.class);

	private ACache fileIdAndUrlCache;

	private FileFactory fileFactory;

	public static MyImageLoader getInstance() {
		if (instance == null) {
			synchronized (MyImageLoader.class) {
				if (instance == null) {
					instance = new MyImageLoader();
				}
			}
		}
		return instance;
	}

	protected MyImageLoader() {
		fileIdAndUrlCache = ACache.get(BeanConfig.getInstance()
				.getBeanContext().getApplicationContext(),
				FILE_ID_URL_CACHE_KEY);
		fileFactory = BeanConfig.getInstance().getBeanContext()
				.get(FileFactory.class);
	}

	/**
	 * Adds display image task to execution pool. Image will be set to
	 * ImageAware when it's turn. <br/>
	 * Default {@linkplain DisplayImageOptions display image options} from
	 * {@linkplain ImageLoaderConfiguration configuration} will be used.<br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param imageAware
	 *            {@linkplain com.nostra13.universalimageloader.core.imageaware.ImageAware
	 *            Image aware view} which should display image
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 * @throws IllegalArgumentException
	 *             if passed <b>imageAware</b> is null
	 */
	public void displayImage(String fileId, ImageAware imageAware) {
		displayImage(fileId, imageAware, null, null, null);
	}

	/**
	 * Adds display image task to execution pool. Image will be set to
	 * ImageAware when it's turn.<br />
	 * Default {@linkplain DisplayImageOptions display image options} from
	 * {@linkplain ImageLoaderConfiguration configuration} will be used.<br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param imageAware
	 *            {@linkplain com.nostra13.universalimageloader.core.imageaware.ImageAware
	 *            Image aware view} which should display image
	 * @param listener
	 *            {@linkplain ImageLoadingListener Listener} for image loading
	 *            process. Listener fires events on UI thread if this method is
	 *            called on UI thread.
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 * @throws IllegalArgumentException
	 *             if passed <b>imageAware</b> is null
	 */
	public void displayImage(String fileId, ImageAware imageAware,
			ImageLoadingListener listener) {
		displayImage(fileId, imageAware, null, listener, null);
	}

	/**
	 * Adds display image task to execution pool. Image will be set to
	 * ImageAware when it's turn.<br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param imageAware
	 *            {@linkplain com.nostra13.universalimageloader.core.imageaware.ImageAware
	 *            Image aware view} which should display image
	 * @param options
	 *            {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
	 *            Options} for image decoding and displaying. If <b>null</b> -
	 *            default display image options
	 *            {@linkplain ImageLoaderConfiguration.Builder#defaultDisplayImageOptions(DisplayImageOptions)
	 *            from configuration} will be used.
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 * @throws IllegalArgumentException
	 *             if passed <b>imageAware</b> is null
	 */
	public void displayImage(String fileId, ImageAware imageAware,
			DisplayImageOptions options) {
		displayImage(fileId, imageAware, options, null, null);
	}

	/**
	 * Adds display image task to execution pool. Image will be set to
	 * ImageAware when it's turn.<br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param imageAware
	 *            {@linkplain com.nostra13.universalimageloader.core.imageaware.ImageAware
	 *            Image aware view} which should display image
	 * @param options
	 *            {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
	 *            Options} for image decoding and displaying. If <b>null</b> -
	 *            default display image options
	 *            {@linkplain ImageLoaderConfiguration.Builder#defaultDisplayImageOptions(DisplayImageOptions)
	 *            from configuration} will be used.
	 * @param listener
	 *            {@linkplain ImageLoadingListener Listener} for image loading
	 *            process. Listener fires events on UI thread if this method is
	 *            called on UI thread.
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 * @throws IllegalArgumentException
	 *             if passed <b>imageAware</b> is null
	 */
	public void displayImage(String fileId, ImageAware imageAware,
			DisplayImageOptions options, ImageLoadingListener listener) {
		displayImage(fileId, imageAware, options, listener, null);
	}

	/**
	 * Adds display image task to execution pool. Image will be set to ImageView
	 * when it's turn. <br/>
	 * Default {@linkplain DisplayImageOptions display image options} from
	 * {@linkplain ImageLoaderConfiguration configuration} will be used.<br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param imageView
	 *            {@link ImageView} which should display image
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 * @throws IllegalArgumentException
	 *             if passed <b>imageView</b> is null
	 */
	public void displayImage(String fileId, ImageView imageView) {
		displayImage(fileId, new ImageViewAware(imageView), null, null, null);
	}

	/**
	 * Adds display image task to execution pool. Image will be set to ImageView
	 * when it's turn.<br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param imageView
	 *            {@link ImageView} which should display image
	 * @param options
	 *            {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
	 *            Options} for image decoding and displaying. If <b>null</b> -
	 *            default display image options
	 *            {@linkplain ImageLoaderConfiguration.Builder#defaultDisplayImageOptions(DisplayImageOptions)
	 *            from configuration} will be used.
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 * @throws IllegalArgumentException
	 *             if passed <b>imageView</b> is null
	 */
	public void displayImage(String fileId, ImageView imageView,
			DisplayImageOptions options) {
		displayImage(fileId, new ImageViewAware(imageView), options, null, null);
	}

	/**
	 * Adds display image task to execution pool. Image will be set to ImageView
	 * when it's turn.<br />
	 * Default {@linkplain DisplayImageOptions display image options} from
	 * {@linkplain ImageLoaderConfiguration configuration} will be used.<br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param imageView
	 *            {@link ImageView} which should display image
	 * @param listener
	 *            {@linkplain ImageLoadingListener Listener} for image loading
	 *            process. Listener fires events on UI thread if this method is
	 *            called on UI thread.
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 * @throws IllegalArgumentException
	 *             if passed <b>imageView</b> is null
	 */
	public void displayImage(String fileId, ImageView imageView,
			ImageLoadingListener listener) {
		displayImage(fileId, new ImageViewAware(imageView), null, listener,
				null);
	}

	/**
	 * Adds display image task to execution pool. Image will be set to ImageView
	 * when it's turn.<br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param imageView
	 *            {@link ImageView} which should display image
	 * @param options
	 *            {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
	 *            Options} for image decoding and displaying. If <b>null</b> -
	 *            default display image options
	 *            {@linkplain ImageLoaderConfiguration.Builder#defaultDisplayImageOptions(DisplayImageOptions)
	 *            from configuration} will be used.
	 * @param listener
	 *            {@linkplain ImageLoadingListener Listener} for image loading
	 *            process. Listener fires events on UI thread if this method is
	 *            called on UI thread.
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 * @throws IllegalArgumentException
	 *             if passed <b>imageView</b> is null
	 */
	public void displayImage(String fileId, ImageView imageView,
			DisplayImageOptions options, ImageLoadingListener listener) {
		displayImage(fileId, imageView, options, listener, null);
	}

	/**
	 * Adds display image task to execution pool. Image will be set to ImageView
	 * when it's turn.<br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param imageView
	 *            {@link ImageView} which should display image
	 * @param options
	 *            {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
	 *            Options} for image decoding and displaying. If <b>null</b> -
	 *            default display image options
	 *            {@linkplain ImageLoaderConfiguration.Builder#defaultDisplayImageOptions(DisplayImageOptions)
	 *            from configuration} will be used.
	 * @param listener
	 *            {@linkplain ImageLoadingListener Listener} for image loading
	 *            process. Listener fires events on UI thread if this method is
	 *            called on UI thread.
	 * @param progressListener
	 *            {@linkplain com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener
	 *            Listener} for image loading progress. Listener fires events on
	 *            UI thread if this method is called on UI thread. Caching on
	 *            disk should be enabled in
	 *            {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
	 *            options} to make this listener work.
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 * @throws IllegalArgumentException
	 *             if passed <b>imageView</b> is null
	 */
	public void displayImage(String fileId, ImageView imageView,
			DisplayImageOptions options, ImageLoadingListener listener,
			ImageLoadingProgressListener progressListener) {
		displayImage(fileId, new ImageViewAware(imageView), options, listener,
				progressListener);
	}

	/**
	 * Adds load image task to execution pool. Image will be returned with
	 * {@link ImageLoadingListener#onLoadingComplete(String, android.view.View, android.graphics.Bitmap)}
	 * callback}. <br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param listener
	 *            {@linkplain ImageLoadingListener Listener} for image loading
	 *            process. Listener fires events on UI thread if this method is
	 *            called on UI thread.
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 */
	public void loadImage(String fileId, ImageLoadingListener listener) {
		loadImage(fileId, null, null, listener, null);
	}

	/**
	 * Adds load image task to execution pool. Image will be returned with
	 * {@link ImageLoadingListener#onLoadingComplete(String, android.view.View, android.graphics.Bitmap)}
	 * callback}. <br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param targetImageSize
	 *            Minimal size for {@link Bitmap} which will be returned in
	 *            {@linkplain ImageLoadingListener#onLoadingComplete(String, android.view.View, android.graphics.Bitmap)}
	 *            callback}. Downloaded image will be decoded and scaled to
	 *            {@link Bitmap} of the size which is <b>equal or larger</b>
	 *            (usually a bit larger) than incoming targetImageSize.
	 * @param listener
	 *            {@linkplain ImageLoadingListener Listener} for image loading
	 *            process. Listener fires events on UI thread if this method is
	 *            called on UI thread.
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 */
	public void loadImage(String fileId, ImageSize targetImageSize,
			ImageLoadingListener listener) {
		loadImage(fileId, targetImageSize, null, listener, null);
	}

	/**
	 * Adds load image task to execution pool. Image will be returned with
	 * {@link ImageLoadingListener#onLoadingComplete(String, android.view.View, android.graphics.Bitmap)}
	 * callback}. <br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param options
	 *            {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
	 *            Options} for image decoding and displaying. If <b>null</b> -
	 *            default display image options
	 *            {@linkplain ImageLoaderConfiguration.Builder#defaultDisplayImageOptions(DisplayImageOptions)
	 *            from configuration} will be used.<br />
	 * @param listener
	 *            {@linkplain ImageLoadingListener Listener} for image loading
	 *            process. Listener fires events on UI thread if this method is
	 *            called on UI thread.
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 */
	public void loadImage(String fileId, DisplayImageOptions options,
			ImageLoadingListener listener) {
		loadImage(fileId, null, options, listener, null);
	}

	/**
	 * Adds load image task to execution pool. Image will be returned with
	 * {@link ImageLoadingListener#onLoadingComplete(String, android.view.View, android.graphics.Bitmap)}
	 * callback}. <br />
	 * <b>NOTE:</b> {@link #init(ImageLoaderConfiguration)} method must be
	 * called before this method call
	 * 
	 * @param fileId
	 *            图片文件的ID(对应sys_inst_file的主键)
	 * @param targetImageSize
	 *            Minimal size for {@link Bitmap} which will be returned in
	 *            {@linkplain ImageLoadingListener#onLoadingComplete(String, android.view.View, android.graphics.Bitmap)}
	 *            callback}. Downloaded image will be decoded and scaled to
	 *            {@link Bitmap} of the size which is <b>equal or larger</b>
	 *            (usually a bit larger) than incoming targetImageSize.
	 * @param options
	 *            {@linkplain com.nostra13.universalimageloader.core.DisplayImageOptions
	 *            Options} for image decoding and displaying. If <b>null</b> -
	 *            default display image options
	 *            {@linkplain ImageLoaderConfiguration.Builder#defaultDisplayImageOptions(DisplayImageOptions)
	 *            from configuration} will be used.<br />
	 * @param listener
	 *            {@linkplain ImageLoadingListener Listener} for image loading
	 *            process. Listener fires events on UI thread if this method is
	 *            called on UI thread.
	 * @throws IllegalStateException
	 *             if {@link #init(ImageLoaderConfiguration)} method wasn't
	 *             called before
	 */
	public void loadImage(String fileId, ImageSize targetImageSize,
			DisplayImageOptions options, ImageLoadingListener listener) {
		loadImage(fileId, targetImageSize, options, listener, null);
	}

	public void loadImage(final String fileId, final ImageSize targetImageSize,
			final DisplayImageOptions options,
			final ImageLoadingListener listener,
			final ImageLoadingProgressListener progressListener) {
		if (!AssertValue.isNotNullAndNotEmpty(fileId)) {
			return;
		}
		String uri = getUriByFileId(fileId);
		if (AssertValue.isNotNullAndNotEmpty(uri)) {
			ImageLoader.getInstance().loadImage(uri, targetImageSize, options,
					listener, progressListener);
		} else {
			fileFactory.genFileUrlById(fileId, new AsyncHttpResponseCallback() {

				@Override
				public void onSuccess(Response response) {
					FileUrlById fub = (FileUrlById) response.getPayload();
					if (AssertValue.isNotNull(fub)) {
						putMapping(fileId, fub.getUrl().toString());
						ImageLoader.getInstance().loadImage(
								fub.getUrl().toString(), targetImageSize,
								options, listener, progressListener);
					}

				}

				@Override
				public void onFailure(Response response) {
					// do nothing
					logger.e("根据ID获取文件URL失败，" + response.getCause());
				}
			});
		}

	}

	public void displayImage(final String fileId, final ImageAware imageAware,
			final DisplayImageOptions options,
			final ImageLoadingListener listener,
			final ImageLoadingProgressListener progressListener) {
		if (!AssertValue.isNotNullAndNotEmpty(fileId)) {
			settleDefaultImage(imageAware.getWrappedView());
			return;
		}
		String uri = getUriByFileId(fileId);
		final ImageLoadingListener wrapLoadingListener = wrapLoadingListener(
				fileId, imageAware, options, listener, progressListener);
		if (AssertValue.isNotNullAndNotEmpty(uri)) {
			ImageLoader.getInstance().displayImage(uri, imageAware, options,
					wrapLoadingListener, progressListener);
		} else {
			fileFactory.genFileUrlById(fileId, new AsyncHttpResponseCallback() {

				@Override
				public void onSuccess(Response response) {
					FileUrlById fub = (FileUrlById) response.getPayload();
					if (AssertValue.isNotNull(fub)) {
						putMapping(fileId, fub.getUrl().toString());
						ImageLoader.getInstance().displayImage(
								fub.getUrl().toString(), imageAware, options,
								listener, progressListener);
					} else {
						settleDefaultImage(imageAware.getWrappedView());
					}

				}

				@Override
				public void onFailure(Response response) {
					// do nothing
					settleDefaultImage(imageAware.getWrappedView());
					logger.e("根据ID获取文件URL失败，" + response.getCause());
				}
			});
		}

	}

	private void settleDefaultImage(View wrappedView) {
		if (wrappedView !=null && wrappedView instanceof ImageView) {
			((ImageView)wrappedView).setImageResource(R.drawable.ic_launcher);
		}
	}

	private ImageLoadingListener wrapLoadingListener(final String fileId,
			final ImageAware imageAware, final DisplayImageOptions options,
			final ImageLoadingListener listener,
			final ImageLoadingProgressListener progressListener) {
		ImageLoadingListener defaultLoadingListener = new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				if (listener != null) {
					listener.onLoadingStarted(imageUri, view);
				}
			}

			@Override
			public void onLoadingFailed(final String imageUri, final View view,
					final FailReason failReason) {
				// 如果加载失败，尝试重新加载
				fileFactory.genFileUrlById(fileId,
						new AsyncHttpResponseCallback() {

							@Override
							public void onSuccess(Response response) {
								FileUrlById fub = (FileUrlById) response
										.getPayload();
								// 如果查出的图片url与之前的不一致，则重新获取
								if (AssertValue.isNotNull(fub)
										&& !fub.getUrl().toString()
												.equals(imageUri)) {
									putMapping(fileId, fub.getUrl().toString());
									ImageLoader.getInstance().displayImage(
											fub.getUrl().toString(),
											imageAware, options, listener,
											progressListener);
								} else if (listener != null) {
									listener.onLoadingFailed(imageUri, view,
											failReason);
								}

							}

							@Override
							public void onFailure(Response response) {
								// do nothing
								logger.e("根据ID获取文件URL失败，" + response.getCause());
							}
						});
			}

			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				if (listener != null) {
					listener.onLoadingComplete(imageUri, view, loadedImage);
				}
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				if (listener != null) {
					listener.onLoadingCancelled(imageUri, view);
				}
			}
		};
		return defaultLoadingListener;
	}

	public void clearMemoryCache() {
		ImageLoader.getInstance().clearMemoryCache();
		fileIdAndUrlCache.clear();
	}

	public void clearDiskCache() {
		ImageLoader.getInstance().clearDiskCache();
		fileIdAndUrlCache.clear();
	}

	private void putMapping(String fileId, String uri) {
		fileIdAndUrlCache.put(fileId, uri);
	}

	private String getUriByFileId(String fileId) {
		if (!AssertValue.isNotNullAndNotEmpty(fileId)) {
			return null;
		}
		return fileIdAndUrlCache.getAsString(fileId);
	}

}
