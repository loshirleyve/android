package com.yun9.mobile.camera.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yun9.mobile.R;
import com.yun9.mobile.camera.adapter.NetWorkFragmentAdapter.ViewHolder;
import com.yun9.mobile.camera.domain.DmLocalAlbum;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.util.SingletonImageLoader;
import com.yun9.mobile.camera.util.ThumbnailsUtil;
import com.yun9.mobile.imageloader.MyImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterLocalAlbum extends BaseAdapter
{
    private Context context;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private List<DmLocalPhoto> photoList;
    private DmLocalAlbum album;
    
    
    /**
     * 控制是否展示复选框
     */
    private boolean isShowChk;
    
    /**
     * 被选中的数量
     */
    private int checkedNum;
    
    public AdapterLocalAlbum(Context context, DmLocalAlbum album, boolean isShowChk){
    	this.context = context;
    	this.album = album;
    	this.isShowChk = isShowChk;
    	init();
    }
    
    
    private void init() {
    	imageLoader = SingletonImageLoader.getInstance().getImageLoader();
    	options = SingletonImageLoader.getInstance().getOptions();
    	photoList = album.getList();
    	checkedNum = 0;
    	imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}

    @Override
    public int getCount()
    {
    	return photoList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return photoList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    
    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {

    	HolderView holder;
    	if(view == null){
    		holder = new HolderView();
    		view = LayoutInflater.from(context).inflate(R.layout.item_localablum, null);
    		holder.checkBox =  (CheckBox) view.findViewById(R.id.chkPhoto);
    		holder.imageView = (ImageView) view.findViewById(R.id.ivPhoto);
    		view.setTag(holder);
    	}else{
    		holder = (HolderView) view.getTag();
    	}
    	// 显示相机
    	if(0 == position){
    		String path = "drawable://" + R.drawable.compose_photo_photograph;
    		holder.checkBox.setVisibility(View.GONE);
    		imageLoader.displayImage(path, holder.imageView, options);
    	}else{
    	 	holder.checkBox.setVisibility(View.VISIBLE);
    		String path = "file://" + photoList.get(position).getThumbnailPath();
    		boolean isCheck = photoList.get(position).isChecked();
    		holder.checkBox.setChecked(isCheck);
    		if (!isShowChk) {
    			holder.checkBox.setVisibility(View.GONE);
    		}
    		imageLoader.displayImage(path, holder.imageView, options);
    	}
		return view;
    }

	public int getCheckedNum() {
		return checkedNum;
	}
    
	public boolean getCheckedStat(int position) {
		return photoList.get(position).isChecked();
	}
	
	public void setChecked(int position, boolean isChecked, View view){
	 	updateCheckedNum(position, isChecked, view);
	 	
	}
	
	private void updateCheckedNum(int position, boolean isChecked,View view) {
		if(isChecked){
		  checkedNum++;
		}else{
		  checkedNum--;
		}
		photoList.get(position).setChecked(isChecked);
		HolderView holerview = (HolderView) view.getTag();
		holerview.checkBox.setChecked(isChecked);
	}
    
    class HolderView
    {
        ImageView imageView;
        CheckBox checkBox;
    }

	public boolean isShowChk() {
		return isShowChk;
	}

	public void setShowChk(boolean isShowChk) {
		this.isShowChk = isShowChk;
	}
	
	public void setAllChecked(boolean checked) {
		for (DmLocalPhoto photo : photoList) {
			photo.setChecked(checked);
		}
	}

	public List<DmLocalPhoto> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<DmLocalPhoto> photoList) {
		this.photoList = photoList;
	}


	public void setCheckedNum(int checkedNum) {
		this.checkedNum = checkedNum;
	}
	
}
