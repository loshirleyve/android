package com.yun9.mobile.usermanual.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.UserManual;
import com.yun9.mobile.usermanual.activity.DataService;
import com.yun9.mobile.usermanual.activity.UserManualActivity;
import com.yun9.mobile.usermanual.callback.AsyncHttpUserManualCallback;
import com.yun9.mobile.usermanual.fragment.UserManualAdapter.UserManualListItemView;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   UserManualFragment
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-5下午5:59:05
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-5下午5:59:05  
 * 修改备注：    
 * @version     
 *     
 */
public class UserManualListFragment extends Fragment {
	private View baseView;
	private Context mContext;
	private ListView listview;
	private ImageButton returnBtn; 
	public UserManualListFragment(Context context)
	{
		this.mContext=context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.usermanual_list, null);
		init();
		return baseView;
	}
	
	public void init()
	{
		returnBtn=(ImageButton)baseView.findViewById(R.id.return_btn);
		returnBtn.setOnClickListener(returnonclick);
		listview = (ListView) baseView.findViewById(R.id.userManualList);
		DataService data=new DataService();
		data.UserManual(new  AsyncHttpUserManualCallback() {
			
			@Override
			public void handler(List<UserManual> userManuals) {
				UserManualAdapter adapter=new UserManualAdapter(mContext,userManuals);
				adapter.setOnclick(replace);
				listview.setAdapter(adapter);
			}
		});
	}
	OnClickListener returnonclick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			getActivity().finish();	
		}
	};

	OnClickListener replace=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			UserManualListItemView item=(UserManualListItemView)v.getTag();
			UserManualFragment webViewFragment = new UserManualFragment(mContext,item.usermanual);
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.fl_content, webViewFragment,
					UserManualActivity.class.getName());
			ft.commit();
		}
	};
}
