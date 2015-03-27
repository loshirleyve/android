package com.yun9.mobile.msg.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.imageloader.MyImageLoader;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   ImageAdapter
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-29下午3:25:15
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-29下午3:25:15  
 * 修改备注：    
 * @version     
 *     
 */
public class MsgCardImageAdapter extends BaseAdapter {   
    private Context mContext;   
    private int imageWidth;
    private List<String> imageList;
    public MsgCardImageAdapter(Context c,int width,List<String> imageList)   
    {   
       mContext=c; 
       this.imageList=imageList;
       imageWidth=(width-140)/3;
    }   
    @Override   
    public int getCount() {   
    	if(AssertValue.isNotNullAndNotEmpty(imageList))
    	{
    		return imageList.size();
    	}
      return 0;
    }   
    @Override   
    public Object getItem(int position) {   
    	if(AssertValue.isNotNullAndNotEmpty(imageList))
    	{
    		return imageList.get(position);
    	}
      return null;   
    }   
    @Override   
    public long getItemId(int position) {   
      return 0;   
    }   
    @Override   
    public View getView(int position, View convertView, ViewGroup parent) {   
      ImageView imageview;   
      if(convertView==null)   
      {   
        imageview=new ImageView(mContext);   
        imageview.setLayoutParams(new GridView.LayoutParams(imageWidth,imageWidth));   
        imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
      }   
      else   
      {   
        imageview=(ImageView) convertView;   
      }   
      MyImageLoader.getInstance().displayImage(imageList.get(position), imageview);
      return imageview;   
   }   
}  