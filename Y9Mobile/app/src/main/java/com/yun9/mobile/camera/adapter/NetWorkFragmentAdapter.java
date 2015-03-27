package com.yun9.mobile.camera.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yun9.mobile.R;
import com.yun9.mobile.camera.adapter.AdapterLocalAlbum.HolderView;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.enums.ModeAlbum;
import com.yun9.mobile.camera.interfaces.F4ActivityAlbum;
import com.yun9.mobile.camera.util.SingletonImageLoader;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.imageloader.MyImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class NetWorkFragmentAdapter extends BaseAdapter
{
    private Context context;
//    private ArrayList<String> imageUrls;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Map<Integer,Integer> map;
    private Map<Integer,String> imageMaps;

    /**
     * 被选中的数量
     */
    private int checkedNum;
    /**
     * 控制是否展示复选框
     */
    private boolean isShowChk;

    private List<DmNetPhoto> netPics;
    public NetWorkFragmentAdapter(Context context, List<DmNetPhoto> domainNewWorkFileList, Map<Integer, String> imageMaps, boolean isShowChk)
    {
        this.netPics = domainNewWorkFileList;
        this.context = context;
        this.imageMaps = imageMaps;
        this.isShowChk = isShowChk;
        
        imageLoader = SingletonImageLoader.getInstance().getImageLoader();
        options = SingletonImageLoader.getInstance().getOptions();
    }
    
    
    private void init(){
    	checkedNum = 0;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public int getCount()
    {
    	return netPics.size();
    }

    @Override
    public Object getItem(int position)
    {
    
        return netPics.get(position);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup)
    {

        ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_netablum, null);
            holder.checkBox = (CheckBox) view.findViewById(R.id.chkPhoto);
            holder.imageView = (ImageView) view.findViewById(R.id.gridViewImages);
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }
       
        // 显示相机
        if(position == 0){
    		String path = "drawable://" + R.drawable.compose_photo_photograph;
    		holder.checkBox.setVisibility(View.GONE);
    		imageLoader.displayImage(path, holder.imageView, options);
        }
        // 显示云图片
        else{
        	holder.checkBox.setVisibility(View.VISIBLE);
			String fileId = netPics.get(position).getFileInfo().getId();
			boolean isCheck = netPics.get(position).isChecked();
			holder.checkBox.setChecked(isCheck);
			
			if (!isShowChk) {
				holder.checkBox.setVisibility(View.GONE);
			}
			
			MyImageLoader.getInstance().displayImage(fileId, holder.imageView,options);
        }
        return view;
    }

    class ViewHolder
    {
        ImageView imageView;
        CheckBox checkBox;
    }

	public int getCheckedNum() {
		return checkedNum;
	}

	public boolean getCheckedStat(int position) {
		return netPics.get(position).isChecked();
	}

	
	public void setChecked(int position, boolean isChecked, View view){
	 	updateCheckedNum(position, isChecked, view);
	}
    
	  private void updateCheckedNum(int position, boolean isChecked, View view) {
	      	
			if (isChecked) {
				checkedNum++;
			} else {
				checkedNum--;
			}
			netPics.get(position).setChecked(isChecked);
			ViewHolder holerview = (ViewHolder) view.getTag();
			holerview.checkBox.setChecked(isChecked);
		}
	
    public void reduceCheckedNum(){
    	checkedNum--;
    }
    
    
    public void setAllChecked(boolean checked){
    	for(DmNetPhoto domain :netPics){
    		domain.setChecked(checked);
    	}
    }

	public boolean isShowChk() {
		return isShowChk;
	}

	public void setShowChk(boolean isShowChk) {
		this.isShowChk = isShowChk;
	}
	
}
