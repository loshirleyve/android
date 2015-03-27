package com.yun9.mobile.form.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.maoye.form.interfaces.AdapterShowImage;
import com.maoye.form.model.ModelFile.FileType;
import com.maoye.form.model.ModelPic;
import com.maoye.form.utils.UtilDebugLog;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yun9.mobile.camera.activity.ImageBrowseActivity;
import com.yun9.mobile.camera.util.SingletonImageLoader;
import com.yun9.mobile.imageloader.MyImageLoader;

public class ImplAdapterShowImage implements AdapterShowImage{

	private SingletonImageLoader imageLoader;
	
	private Context context;
	
	/**
	 * @param context
	 */
	public ImplAdapterShowImage(Context context) {
		super();
		this.context = context;
		
		init();
	}

	private void init(){
		imageLoader = SingletonImageLoader.getInstance();
	}
	
	@Override
	public void showImage(ImageView view, ModelPic pic) {
		
		String showPath;
		
		if(pic.getEmumType() == FileType.PicNetWork){
			showPath = pic.getShowPath();
			MyImageLoader.getInstance().displayImage(showPath, view, imageLoader.getOptions());
		}else if(pic.getEmumType() == FileType.PicLocal){
			showPath = "file://" + pic.getShowPath();
			imageLoader.getImageLoader().displayImage(showPath, view, imageLoader.getOptions()); 
		}
		
	}

}
