package com.yun9.mobile.department.adapter;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.department.fragment.UserDetailPopView;
import com.yun9.mobile.framework.model.UserBean;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.roundimage.RoundImageView;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   UserGridViewAdapter
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-30上午11:39:17
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-30上午11:39:17  
 * 修改备注：    
 * @version     
 *     
 */
public class UserGridViewAdapter extends BaseAdapter {   
	private Context mContext;
	private Activity baseActivity;
    private List<UserBean> userlist;
    private LayoutInflater listContainer; // 视图容器
    private ProgressDialog progressDialog;
    private View baseView;
    private final class UserView
    {
    	private RoundImageView userImage;
    	private TextView       userName;
    	private UserBean       userbean;
    }
    
    private UserView userview;
    public UserGridViewAdapter(Context context,List<UserBean> userlist,ProgressDialog progressDialog,View baseView,Activity baseActivity)   
    {   
       this.mContext=context;
       listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
       this.userlist=userlist;
       this.progressDialog=progressDialog;
       this.baseView=baseView;
       this.baseActivity=baseActivity;
    }   
    @Override   
    public int getCount() {   
    	if(AssertValue.isNotNullAndNotEmpty(userlist))
    	{
    		return userlist.size();
    	}
    	return 0;
    }   
    @Override   
    public Object getItem(int position) {   
    	if(AssertValue.isNotNullAndNotEmpty(userlist))
    	{
    		return userlist.get(position);
    	}
      return null;   
    }   
    
    @Override   
    public long getItemId(int position) {   
      return 0;   
    }   
    
    @Override   
    public View getView(int position, View convertView, ViewGroup parent) {   
      if(convertView==null)   
      {   
    	  userview=new UserView();
    	  convertView = listContainer.inflate(R.layout.contact_gridview_items,
					null);
    	  userview.userImage=(RoundImageView)convertView.findViewById(R.id.image);
    	  userview.userName=(TextView)convertView.findViewById(R.id.name);
    	  convertView.setTag(userview);
    	  convertView.setOnClickListener(userDetail);
      }   
      else   
      {   
    	  userview=(UserView)convertView.getTag();
      }   
      MyImageLoader.getInstance().displayImage(userlist.get(position).getUser().getHeaderfileid(), userview.userImage);
      userview.userName.setText(userlist.get(position).getUser().getName());
      userview.userbean=userlist.get(position);
      progressDialog.dismiss();
      return convertView;   
   } 
    
    OnClickListener userDetail=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			UserView userview=(UserView)v.getTag();
			v.getTag();
			UserDetailPopView popview=new UserDetailPopView(mContext,baseView,baseActivity);
			popview.show(userview.userbean);
		}
	};
	
	
}  