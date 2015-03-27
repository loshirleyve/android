package com.yun9.mobile.form.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.maoye.form.interfaces.engine.EngineFileObtain;
import com.maoye.form.model.ModelFile.FileType;
import com.maoye.form.model.ModelPic;
import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.impl.ImplAlbumEntrance;
import com.yun9.mobile.camera.interfaces.AlbumEntrance;
import com.yun9.mobile.camera.interfaces.AlbumEntrance.AlbumCallBack;
import com.yun9.mobile.camera.util.ThumbnailsUtil;
import com.yun9.mobile.framework.model.FileByUserId;
import com.yun9.mobile.framework.util.AssertValue;

public class ImplEngineFileObtain2Album implements EngineFileObtain{

	private Context context;
	private String name;
	
	private List<DmNetPhoto> listDmNetPhoto;
	private List<DmLocalPhoto> listDmLocalPhoto;
	
	/**
	 * @param context
	 * @param name
	 */
	public ImplEngineFileObtain2Album(Context context, String name) {
		super();
		this.context = context;
		this.name = name;
		
		init();
	}

	@Override
	public String obtainFileMethodName() {
		return name;
	}

	
	private void init(){
	}
	
	@Override
	public void obtainFile(final List<ModelPic> files, final List<ModelPic> pics, final CallBack callBack) {
		
		listDmNetPhoto = AdapterPicTranslate.modelFile2DmNetPhoto(pics);
		listDmLocalPhoto = AdapterPicTranslate.modelFile2DmLocalPhoto(pics);
		
		
		AlbumEntrance album = new ImplAlbumEntrance(context);
		
		album.go2SelectAlbumPhoto(listDmLocalPhoto, listDmNetPhoto,new AlbumEntrance.AlbumCallBack() {

			@Override
			public void photoCallBack(List<DmLocalPhoto> listChosenDmLocalPhoto, List<DmNetPhoto> listChosenDmNetPhoto, boolean isOrigin) {
				List<ModelPic> netPhotos = AdapterPicTranslate.netPhoto2ModelFile(listChosenDmNetPhoto);
				List<ModelPic> localPhotos = AdapterPicTranslate.localPhoto2ModelFile(listChosenDmLocalPhoto);
				List<ModelPic> photos = new ArrayList<ModelPic>();
				photos.addAll(netPhotos);
				photos.addAll(localPhotos);
				callBack.onSuccess(files, photos);
			}
			
		});
		
//		album.go2SelectNetworkFile(listDmNetPhoto, new AlbumEntrance.AlbumCallBack() {
//			
//			@Override
//			public void photoCallBack(List<DmLocalPhoto> listChosenDmLocalPhoto, List<DmNetPhoto> listChosenDmNetPhoto, boolean isOrigin) {
//				
//				List<ModelFile> netPhotos = AdapterPicTranslate.netPhoto2ModelFile(listChosenDmNetPhoto);
//				List<ModelFile> localPhotos = AdapterPicTranslate.localPhoto2ModelFile(listChosenDmLocalPhoto);
//				List<ModelFile> photos = new ArrayList<ModelFile>();
//				photos.addAll(netPhotos);
//				photos.addAll(localPhotos);
//				callBack.onSuccess(files, photos);
//			}
//		});
	}
	
	
	private static class  AdapterPicTranslate{
		
		
		
		
		public static List<ModelPic> localPhoto2ModelFile(List<DmLocalPhoto> photos){
			
			List<ModelPic> modelFiles = new ArrayList<ModelPic>();
			if(AssertValue.isNotNullAndNotEmpty(photos)){
				ModelPic modelFile;
				for(DmLocalPhoto photo : photos){
					modelFile = new ModelPic();
					
					modelFile.setCreatedate(Long.valueOf(photo.getDateAdded()));
					modelFile.setPath(photo.getPath_absolute());
					modelFile.setEmumType(FileType.PicLocal);
					modelFiles.add(modelFile);
				}
			}
			
			return modelFiles;
		}
		
		public static List<DmLocalPhoto> modelFile2DmLocalPhoto(List<ModelPic> pics){
			
			List<DmLocalPhoto> listPhotos = new ArrayList<DmLocalPhoto>();
			
			if(!AssertValue.isNotNullAndNotEmpty(pics)){
				return listPhotos;
			}
			
			for(ModelPic modelFile : pics){
				if(modelFile.getEmumType() == FileType.PicLocal){
					DmLocalPhoto photo = new DmLocalPhoto();
//					FileByUserId fileInfo = new FileByUserId();
//					fileInfo.setCreatedate(modelFile.getCreatedate());
//					fileInfo.setId(modelFile.getValue());
//					fileInfo.setName(modelFile.getName());
//					photo.setFileInfo(fileInfo);
					
//					photo.setPath_file("file://"+modelFile.getPath());
					photo.setPath_absolute(modelFile.getPath());
					photo.setDateAdded(String.valueOf(modelFile.getCreatedate()));
					photo.setChecked(true);
					listPhotos.add(photo);
				}
			}
			
			return listPhotos;
		}
		
		
		
		public static List<ModelPic> netPhoto2ModelFile(List<DmNetPhoto> photos){
			
			List<ModelPic> modelFiles = new ArrayList<ModelPic>();
			
			if(AssertValue.isNotNullAndNotEmpty(photos));
			{
				ModelPic modelFile;
				for(DmNetPhoto photo : photos){
					modelFile = new ModelPic();
					modelFile.setValue(photo.getFileInfo().getId());
					modelFile.setCreatedate(photo.getFileInfo().getCreatedate());
					modelFile.setName(photo.getFileInfo().getName());
					modelFile.setEmumType(FileType.PicNetWork);
					modelFiles.add(modelFile);
				}
				
			}
			return modelFiles;
			
		}
		
		public static List<DmNetPhoto> modelFile2DmNetPhoto(List<ModelPic> pics){
			
			List<DmNetPhoto> listPhotos = new ArrayList<DmNetPhoto>();
			
			if(!AssertValue.isNotNullAndNotEmpty(pics)){
				return listPhotos;
			}
			
			for(ModelPic modelFile : pics){
				if(modelFile.getEmumType() == FileType.PicNetWork){
					DmNetPhoto photo = new DmNetPhoto();
					FileByUserId fileInfo = new FileByUserId();
					fileInfo.setCreatedate(modelFile.getCreatedate());
					fileInfo.setId(modelFile.getValue());
					fileInfo.setName(modelFile.getName());
					photo.setFileInfo(fileInfo);
					photo.setChecked(true);
					listPhotos.add(photo);
				}
			}
			
			return listPhotos;
		}
	}


	
	
}
