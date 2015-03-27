package com.yun9.mobile.department.adapter;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.department.activity.DepartmentDetailActivity;
import com.yun9.mobile.department.fragment.DepartmentDetailFragment;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.roundimage.RoundImageView;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   DepartmentGridViewAdapter
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-30上午11:39:17
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-30上午11:39:17  
 * 修改备注：    
 * @version     
 *     
 */
public class DepartmentGridViewAdapter extends BaseAdapter {  
	private Context mContext;
	private TextView title_txt;//DepartmentDetailActivity的标题，因为在fragment点击部门时要替换fragment也要替换标题，便于控制写在这里
    private List<Org> orglist;
    private LayoutInflater listContainer; // 视图容器
    private ProgressDialog progressDialog;
    private FragmentManager fragmentManager;
    private final class OrgView
    {
    	private RoundImageView orgImage;
    	private TextView       orgName;
    	private Org            org;
    }
    
    private OrgView orgview;
    public DepartmentGridViewAdapter(Context context,List<Org> orglist,ProgressDialog progressDialog,FragmentManager fragmentManager,TextView title_txt)   
    {   
       this.mContext=context;
       listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
       this.orglist=orglist;
       this.progressDialog=progressDialog;
       this.fragmentManager=fragmentManager;
       this.title_txt=title_txt;
    }   
    @Override   
    public int getCount() {   
    	if(AssertValue.isNotNullAndNotEmpty(orglist))
    	{
    		return orglist.size();
    	}
    	return 0;
    }   
    @Override   
    public Object getItem(int position) {   
    	if(AssertValue.isNotNullAndNotEmpty(orglist))
    	{
    		return orglist.get(position);
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
    	  orgview=new OrgView();
    	  convertView = listContainer.inflate(R.layout.contact_gridview_items,
					null);
    	  orgview.orgImage=(RoundImageView)convertView.findViewById(R.id.image);
    	  orgview.orgName=(TextView)convertView.findViewById(R.id.name);
    	  convertView.setTag(orgview);
    	  convertView.setOnClickListener(replaceFragment);
      }   
      else   
      {   
    	  orgview=(OrgView)convertView.getTag();
      }   
      orgview.orgImage.setBackgroundResource(R.drawable.bpm_user);
      if(orglist.get(position).getName().length()>3)
      orgview.orgName.setText(orglist.get(position).getName().subSequence(0,3).toString()+"...");
      else
      {
    	  orgview.orgName.setText(orglist.get(position).getName());
      }
      orgview.org=orglist.get(position);
      progressDialog.dismiss();
      return convertView;   
   }   
    
    
    
    OnClickListener replaceFragment=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			OrgView orgview=(OrgView)v.getTag();
			Org org=orgview.org;
			FragmentTransaction ft = fragmentManager.beginTransaction();
			DepartmentDetailFragment departmentFragment = new DepartmentDetailFragment(mContext,org,title_txt);
			ft.replace(R.id.fl_content, departmentFragment,
					DepartmentDetailActivity.class.getName());
			ft.commit();
		}
	};
    	
}  