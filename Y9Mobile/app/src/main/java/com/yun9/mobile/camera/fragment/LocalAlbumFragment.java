package com.yun9.mobile.camera.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.yun9.mobile.R;
import com.yun9.mobile.camera.activity.CameraActivity;
import com.yun9.mobile.camera.activity.ImageBrowseActivity;
import com.yun9.mobile.camera.adapter.AdapterLocalAlbum;
import com.yun9.mobile.camera.constant.ConstantAlbum;
import com.yun9.mobile.camera.domain.DmLocalAlbum;
import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.domain.DmImageItem;
import com.yun9.mobile.camera.enums.ModeAlbum;
import com.yun9.mobile.camera.enums.ModeAlbum.ModelAlbum2Level;
import com.yun9.mobile.camera.imageInterface.CameraCallBack;
import com.yun9.mobile.camera.interfaces.A4FragmentAlbum;
import com.yun9.mobile.camera.interfaces.F4ActivityAlbum;
import com.yun9.mobile.camera.util.ThumbnailsUtil;
import com.yun9.mobile.camera.util.Utils;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.dlg.nifty.DlgCommon;
import com.yun9.mobile.framework.dlg.prodlg.ProDlgForce;
import com.yun9.mobile.framework.dlg.sweetAlert.DlgAlertCommon;
import com.yun9.mobile.framework.dlg.sweetAlert.DlgProTip;
import com.yun9.mobile.framework.file.FileFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.UtilImageCompress;

import java.io.File;
import java.util.*;

@SuppressLint("ValidFragment")
public class LocalAlbumFragment extends BaseFragment implements F4ActivityAlbum
{
    public static final String tag = LocalAlbumFragment.class.getSimpleName();
    private View view;
    private AdapterLocalAlbum mAdapter;
    //选择的图片地址集合
    private ModeAlbum mModeAlbum;
    private ModelAlbum2Level mMode2Level;
    private boolean isShowChk = false;
    private GridView gv;
    private View loading;
    /**
     * 相册
     */
    
    /**
     * 当前展示的相册，初始化展示的是系统返回的第一个相册（估计是最近更新的，没测试过）
     */
    private DmLocalAlbum currentAlbum;
    
    private List<DmLocalAlbum> albumList = new ArrayList<DmLocalAlbum>();
    private ContentResolver cr; 
    private A4FragmentAlbum A4Fragment;
    
    private boolean isNeedSleep;
    private DlgAlertCommon dlgAlert;
    private DlgProTip dlgProTip;
    @Override
    public void onAttach(Activity activity) {
    	// TODO 自动生成的方法存根
    	super.onAttach(activity);
    	A4Fragment = (A4FragmentAlbum) activity;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_localalbum,null);
        findId();
        init();
        setEvent();
        return view;
    }



    private void setEvent() {
    	// 图片点击
		gridViewP2refresh.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					go2Camera();
				} else if (ModeAlbum.BROWSE == mModeAlbum) {
					go2Browse(position);
				} else if (ModeAlbum.CHOICE == mModeAlbum) {
//					if(ModelAlbum2Level.CHOICE_ONLY_NETWORK_PIC == mMode2Level){
//						showToast("只能选择云相册图片");
//					}else{
//						go2Choice(position);
//					}
					go2Choice(position, view);
				}
			}
		});
		
		// 图片长按:将图片传到云相册
		gv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
				if(position != 0){
					showUploadDlg(position);
				}
				return true;
			}
		});
	}

    /**
     * 上传图片对话框
     * @param position
     */
    private void showUploadDlg(int position){
    	final DmLocalPhoto photo = currentAlbum.getList().get(position);
    	dlgAlert.setTitle("图片上传到云相册");
    	dlgAlert.setContent(photo.getPath_absolute());
    	dlgAlert.setConfirmOnClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlgAlert.dismiss();
				uploadPhoto(photo.getPath_absolute());				
			}
		});
    	dlgAlert.show();
    }
    
    /**
     * 图片上传
     */
    private void uploadPhoto(String path){
    	dlgProTip.show();
		FileFactory fileFactory = BeanConfig.getInstance().getBeanContext().get(FileFactory.class);
		boolean whetherCompress = A4Fragment.isOrigin();
		File uploadFile;
		if(whetherCompress){
			uploadFile = new File(path);
		}else{
			uploadFile = UtilImageCompress.compressPicFile(path, getActivity());
		}
		
		if (uploadFile == null) {
			showToast("文件压缩或者获取失败");
			dlgProTip.dismiss();
			return;
		}
		fileFactory.uploadImgFileUserLevel(uploadFile, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				dlgProTip.dismiss();
				showToast("图片上传成功");
			}

			@Override
			public void onFailure(Response response) {
				dlgProTip.dismiss();
				Toast.makeText(getActivity(), "上传图片失败,请稍后再试",Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    private DlgCommon dlg;
	private void init()
    {
		dlgAlert = new DlgAlertCommon(getActivity());
		dlgProTip = new DlgProTip(getActivity());
		
       	mModeAlbum = A4Fragment.getAlbumMode();
       	mMode2Level = A4Fragment.getAlbumModeLevel();
        isShowChk = true; 
        initLocalAlbums();
    }
	
	private void initLocalAlbums(){
    	cr = getActivity().getContentResolver(); 
    	new ImageAsyncTask().execute();
	}
	
	// 相机拍照
    protected void go2Camera() {
      	CameraActivity.newInsatnce(getActivity(), new CameraCallBack() {
    			@Override
    			public void ImageUrlCall(DmImageItem imageItem) {
					try {
						if(imageItem != null){
							boolean hasAlbum = false;
							String path = imageItem.getImageUrl();
							File file = new File(path);
							DmLocalPhoto photo = new DmLocalPhoto();
							
							photo.setPath_absolute(path);
							photo.setPath_file("file://" + path);
							photo.setThumbnailPath(path);
							photo.setDateAdded(String.valueOf(file.lastModified()));
							photo.setImage_id(0);
							
							for(DmLocalAlbum album : albumList){
								if(album.getName_album().equals(ConstantAlbum.albumDir)){
									hasAlbum = true;
									photo.setAblumId(album.getImage_id());
									album.getList().add(1, photo);
									if(album == currentAlbum){
										mAdapter.notifyDataSetChanged();
									}else{
										showToast("图片存放在移店通相册");
									}
								}
							}
							if(!hasAlbum){
								// 重新寻找图片（为了得到相册）
								isNeedSleep = true;
								new ImageAsyncTask().execute();
							}
								
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
    			}
    		});
	}
    
	protected void go2Choice(int position, View view) {
		boolean stat = mAdapter.getCheckedStat(position);
		boolean isWillChecked = !stat;
		DmLocalPhoto photo = (DmLocalPhoto)mAdapter.getItem(position);
		if(isWillChecked){
			if(A4Fragment.getSelectablePicNum() <= 0){
				showToast("最多可以选择" + A4Fragment.getMaxChoosePicNum() + "张");
    			return ;
			}else{
				A4Fragment.addChosenLocalPhoto(photo);
				mAdapter.setChecked(position, true, view);
			}
		}else{
			A4Fragment.removeChosenLocalPhoto(photo);
			mAdapter.setChecked(position, false, view);
		}
	}
    
	/**
	 * 浏览模式下，浏览图片
	 * @param position
	 */
	protected void go2Browse(int position) {
		ArrayList<String> imageUrl = getImageUrls();
        Intent intent = new Intent(getActivity(), ImageBrowseActivity.class);
        intent.putExtra(ImageBrowseActivity.EXTRA_IMAGE_URLS, imageUrl);
        intent.putExtra(ImageBrowseActivity.EXTRA_IMAGE_INDEX, position-1);
        intent.putExtra(ImageBrowseActivity.EXTRA_IMAGE_FROM, ImageBrowseActivity.EXTRA_IMAGE_FROM_LOCALALBUM);
        getActivity().startActivity(intent);
	}
    
	public ArrayList<String> getImageUrls() {
		 ArrayList<String> imageUrls = new ArrayList<String>();
		if(AssertValue.isNotNullAndNotEmpty(currentAlbum.getList())){
			for(int i = 1; i < currentAlbum.getList().size(); i++){
				imageUrls.add(currentAlbum.getList().get(i).getPath_absolute());
			}
		}
		return imageUrls;
	}

	protected void showToast(String string) {
		Toast.makeText(getActivity(), string, 0).show();		
	}

	private void findId()
    {
        gridViewP2refresh = (PullToRefreshGridView) view.findViewById(R.id.gvPicShow);
        gv = gridViewP2refresh.getRefreshableView();
        loading = view.findViewById(R.id.loading);
    }
	
	/**
	 * 获取手机图片
	 * @author lhk
	 *
	 */
	private class ImageAsyncTask extends AsyncTask<Void, Void, Object>{
		@Override
		protected void onPreExecute() {
			// TODO 自动生成的方法存根
			super.onPreExecute();
			
			albumList.clear();
			loading.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected Object doInBackground(Void... params) {
			//获取缩略图
			ThumbnailsUtil.clear();
			String[] projection = { Thumbnails._ID, Thumbnails.IMAGE_ID, Thumbnails.DATA };
			Cursor cur = cr.query(Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
			if (cur!=null&&cur.moveToFirst()) {
				int image_id;
				String image_path;
				int image_idColumn = cur.getColumnIndex(Thumbnails.IMAGE_ID);
				int dataColumn = cur.getColumnIndex(Thumbnails.DATA);
				do {
					image_id = cur.getInt(image_idColumn);
					image_path = cur.getString(dataColumn);
					ThumbnailsUtil.put(image_id, image_path);
				} while (cur.moveToNext());
			}

			//获取原图
			Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, "date_modified DESC");  
			HashMap<String,DmLocalAlbum> myhash = new HashMap<String, DmLocalAlbum>();
			DmLocalAlbum albumInfo = null;
			DmLocalPhoto photoInfo = null;
			if (cursor!=null&&cursor.moveToFirst())
			{
				do{  
					int index = 0;
					int _id = cursor.getInt(cursor.getColumnIndex(Images.Media._ID)); 
					String path = cursor.getString(cursor.getColumnIndex(Images.Media.DATA));
					String album = cursor.getString(cursor.getColumnIndex(Images.Media.BUCKET_DISPLAY_NAME));  
					String dateAdded = cursor.getString(cursor.getColumnIndex(Images.Media.DATE_ADDED));
					
					List<DmLocalPhoto> stringList = new ArrayList<DmLocalPhoto>();
					photoInfo = new DmLocalPhoto();
					if(myhash.containsKey(album)){
						albumInfo = myhash.get(album);
						photoInfo.setAblumId(albumInfo.getImage_id());
						photoInfo.setImage_id(_id);
						photoInfo.setPath_file("file://"+path);
						photoInfo.setThumbnailPath(ThumbnailsUtil.MapgetHashValue(_id, path));
						photoInfo.setPath_absolute(path);
						photoInfo.setDateAdded(dateAdded);
						albumInfo.getList().add(photoInfo);
					}else{
						albumInfo = new DmLocalAlbum();
						stringList.clear();
						photoInfo.setImage_id(_id);
						photoInfo.setPath_file("file://"+path);
						photoInfo.setPath_absolute(path);
						photoInfo.setThumbnailPath(ThumbnailsUtil.MapgetHashValue(_id, path));
						photoInfo.setDateAdded(dateAdded);
						stringList.add(photoInfo);
						
						photoInfo.setAblumId(_id);
						albumInfo.setImage_id(_id);
						albumInfo.setPath_file("file://"+path);
						albumInfo.setPath_absolute(path);
						albumInfo.setName_album(album);
						albumInfo.setDateAdded(dateAdded);
//						Camera
						albumInfo.setList(stringList);
						albumList.add(albumInfo);
						myhash.put(album, albumInfo);
					}
				}while (cursor.moveToNext());
			}
			return null;
		}
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if(getActivity()!=null){
				if(AssertValue.isNotNullAndNotEmpty(albumList)){
					List<DmLocalPhoto> chosenLocalPhotos = A4Fragment.getChosenLocalPhotos();
					// 将相机加入每个相册第0个位置
					DmLocalPhoto camera = new DmLocalPhoto();
					camera.isCamera(true);
					for(DmLocalAlbum album : albumList){
						album.getList().add(0, camera);
					}
					
					if(AssertValue.isNotNullAndNotEmpty(chosenLocalPhotos)){
						for(DmLocalAlbum album : albumList){
							for(DmLocalPhoto photo : album.getList()){
								if(chosenLocalPhotos.contains(photo)){
									photo.setChecked(true);
								}
							}
							
						}
					}
					loading.setVisibility(View.GONE);
					currentAlbum = albumList.get(0);
					mAdapter = new AdapterLocalAlbum(getActivity(), currentAlbum, isShowChk);
					gridViewP2refresh.setAdapter(mAdapter);
					A4Fragment.updateCurrentAlbum();
				}
			}
		}
	}

	@Override
	public void notifyModeChanged(ModeAlbum mode) {
		mModeAlbum = mode;
		if(mModeAlbum == ModeAlbum.BROWSE){
			isShowChk = false;
			mAdapter.setShowChk(isShowChk);
		}else if(mModeAlbum == ModeAlbum.CHOICE){
			isShowChk = true;
			mAdapter.setShowChk(isShowChk);
		}
		mAdapter.notifyDataSetChanged();
	}

	public DmLocalAlbum getCurrentAlbum() {
		return currentAlbum;
	}

	public void setCurrentAlbum(DmLocalAlbum currentAlbum) {
		this.currentAlbum = currentAlbum;
	}

	/**
	 * 当前相册有变化
	 */
	public void notifyAlbumChanged(){
		mAdapter.setPhotoList(currentAlbum.getList());
		mAdapter.notifyDataSetChanged();
	}
	
	
	public List<DmLocalAlbum> getAlbumList() {
		return albumList;
	}

	
	public void resetSelectablePhoto() {
		for(DmLocalAlbum album : this.albumList){
			for(DmLocalPhoto photo : album.getList()){
				photo.setChecked(false);
			}
		}
		
		this.mAdapter.notifyDataSetChanged();
	}
}
