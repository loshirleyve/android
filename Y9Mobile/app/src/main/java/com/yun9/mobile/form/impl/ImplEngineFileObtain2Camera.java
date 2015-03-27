package com.yun9.mobile.form.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.maoye.form.interfaces.engine.EngineFileObtain;
import com.maoye.form.model.ModelPic;
import com.yun9.mobile.camera.activity.CameraActivity;
import com.yun9.mobile.camera.domain.DmImageItem;
import com.yun9.mobile.camera.imageInterface.CameraCallBack;

public class ImplEngineFileObtain2Camera implements EngineFileObtain{

	private Context context;
	private String name;
	
	/**
	 * @param context
	 * @param name
	 */
	public ImplEngineFileObtain2Camera(Context context, String name) {
		super();
		this.context = context;
		this.name = name;
	}

	@Override
	public String obtainFileMethodName() {
		return name;
	}

	@Override
	public void obtainFile(final List<ModelPic> files, final List<ModelPic> pics, final CallBack callBack) {
		
		CameraActivity.newInsatnce(context, new CameraCallBack() {
			@Override
			public void ImageUrlCall(DmImageItem imageItem) {
				if(imageItem == null){
					return ;
				}
				
				String path = imageItem.getImageUrl();
//				ModelPic pic = new ModelPic(path);
//				pic.setEmumType(FileType.PicLocal);
				
//				pics.add(pic);
				callBack.onSuccess(files, pics);
			}
		});
	}

}
