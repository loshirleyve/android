package com.yun9.mobile.framework.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yun9.mobile.R;
import com.yun9.mobile.department.fragment.DepartmentTreeTabFragment;
import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.framework.activity.MainActivity;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   AddressBookFragment
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-16下午2:37:46
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-16下午2:37:46  
 * 修改备注：    
 * @version     
 *     
 */
public class FoundFragment  extends Fragment{
	private Context mContext;
	private View mBaseView;
	private RelativeLayout addressbook;
	private RelativeLayout beautiful;
	public FoundFragment(Context context)
	{
		this.mContext=context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mBaseView = inflater.inflate(R.layout.fragment_addressbook, null);
		init();
		return mBaseView;
	}
	
	public void init()
	{
		addressbook=(RelativeLayout)mBaseView.findViewById(R.id.addressbook_layout);
		beautiful=(RelativeLayout)mBaseView.findViewById(R.id.beautiful_layout);
		
		addressbook.setOnClickListener(addressonclick);
		beautiful.setOnClickListener(beautifulonclick);
	}
	
	OnClickListener addressonclick=new OnClickListener() {
		@Override
		public void onClick(View v) {
			DataInfoService data=new DataInfoService();
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			DepartmentTreeTabFragment dpartmentGroupTabFragment = new DepartmentTreeTabFragment(
					mContext, data.getUserInst().getName());
			dpartmentGroupTabFragment.setIsselect(true);
			dpartmentGroupTabFragment.setState(0);
			ft.replace(R.id.fl_content, dpartmentGroupTabFragment,
					MainActivity.class.getName());
			ft.commit();
		}
	};
	
	
	OnClickListener beautifulonclick=new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			WebViewFragment webViewFragment = new WebViewFragment(mContext);
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.fl_content, webViewFragment,
					MainActivity.class.getName());
			ft.commit();
		}
	};
	
	
	
		
}
