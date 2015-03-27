package com.yun9.mobile.usermanual.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.UserManual;
import com.yun9.mobile.usermanual.activity.UserManualActivity;

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
public class UserManualFragment extends Fragment {
	private Context mContext;
	private WebView webview;
	private View baseView;
	private TextView title;
	private ImageButton returnBtn;
	private UserManual manual;
	public UserManualFragment(Context context,UserManual manual)
	{
		this.mContext=context;
		this.manual=manual;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.usermanual_webview, null);
		init();
		return baseView;
	}
	
	public void init()
	{
		title=(TextView)baseView.findViewById(R.id.title_txt);
		title.setText(manual.getItemtext());
		returnBtn=(ImageButton)baseView.findViewById(R.id.return_btn);
		returnBtn.setOnClickListener(returnonclick);
		webview = (WebView) baseView.findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl(manual.getUrl().trim());
	}
	
	OnClickListener returnonclick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			UserManualListFragment fragment=new UserManualListFragment(mContext);
			ft.replace(R.id.fl_content, fragment,
					UserManualActivity.class.getName());
			ft.commit();
		}
	};
}