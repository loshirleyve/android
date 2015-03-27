package com.yun9.mobile.camera.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yun9.mobile.R;
import com.yun9.mobile.camera.activity.AlbumActivity;
import com.yun9.mobile.camera.domain.DmLocalPhoto;
import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.camera.util.PicerMap;
import com.yun9.mobile.camera.util.SingletonImageLoader;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.imageloader.MyImageLoader;

import java.util.ArrayList;
import java.util.List;

public class AlbumPhoto4MsgAdapter extends BaseAdapter
{
    private Context context;
    private SingletonImageLoader singletonImageLoader;
    List<DmLocalPhoto> localPhotos;
    List<DmNetPhoto> netPhotos;
    
    public AlbumPhoto4MsgAdapter(Context context, List<DmLocalPhoto> localPhotos, List<DmNetPhoto> netPhotos)
    {
        this.context = context;
        this.localPhotos = localPhotos;
        this.netPhotos = netPhotos;
        
        singletonImageLoader = SingletonImageLoader.getInstance();
    }

    
    /**
     * 统计图片数量（网络+本地）
     */
    private int countPhotoNum(){
    	int photoNum = 0;
    	if(AssertValue.isNotNullAndNotEmpty(localPhotos)){
    		photoNum += localPhotos.size();
    	}
    	if(AssertValue.isNotNullAndNotEmpty(netPhotos)){
    		photoNum += netPhotos.size();
    	}
    	return photoNum;
    }
    
    @Override
    public int getCount()
    {
        return countPhotoNum();
    }

    @Override
    public Object getItem(int i)
    {
        return 1;
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        singletonImageLoader.getImageLoader().init(ImageLoaderConfiguration.createDefault(context));
        ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.fragmentimgsitem,null);
            holder.imageView = (ImageView) view.findViewById(R.id.gridViewImages);
            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }
        
        // 显示本地图片
        if(i < localPhotos.size()){
        	singletonImageLoader.getImageLoader().displayImage(localPhotos.get(i).getPath_file(), holder.imageView, singletonImageLoader.getOptions());
        }
        // 显示网络图片
        else{
        	int position = i - localPhotos.size();
        	MyImageLoader.getInstance().displayImage(netPhotos.get(position).getFileInfo().getId(), holder.imageView,singletonImageLoader.getOptions());
        }
        return view;
    }

    class ViewHolder
    {
        ImageView imageView;
    }
}
