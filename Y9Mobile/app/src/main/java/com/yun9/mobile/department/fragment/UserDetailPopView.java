package com.yun9.mobile.department.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.model.UserBean;
import com.yun9.mobile.framework.model.UserContact;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.image.activity.ImageViewPagerActivity;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.roundimage.RoundImageView;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   UserDetailPopView
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-31上午10:47:59
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-31上午10:47:59  
 * 修改备注：    
 * @version     
 *     
 */
public class UserDetailPopView extends View{

	private Activity baseActivity;
	private View baseView;
	private View mPopNavView;
	private PopupWindow mPopupNavWindow;
	private RoundImageView uimage;
	private TextView uname;
	private TextView usex;
	private ImageView usexImage;
	private TextView usignature;
	private TextView udepartment;
	private TextView cweixin;
	private TextView cemail;
	private TextView cphone;
	private LinearLayout signature;
	private LinearLayout weixin;
	private LinearLayout email;
	private LinearLayout phone;
	
	private ImageButton return_btn;
	private Button callButton;
	private Button messageBtn;
	
	public UserDetailPopView(Context context,View baseView,Activity baseActivity) {
		super(context);
		this.baseView=baseView;
		this.baseActivity=baseActivity;
		initWidget();
	}
	
	public void initWidget()
	{
		mPopNavView = LayoutInflater.from(baseActivity).inflate(
				R.layout.userinfo_detail, null);
		uimage = (RoundImageView) mPopNavView.findViewById(R.id.u_image);
		uname = (TextView) mPopNavView.findViewById(R.id.u_name);
		usex = (TextView) mPopNavView.findViewById(R.id.u_sex);
		usexImage = (ImageView) mPopNavView.findViewById(R.id.u_sexImage);
		signature=(LinearLayout) mPopNavView.findViewById(R.id.signature);
		usignature=(TextView) mPopNavView.findViewById(R.id.u_signature);
		udepartment = (TextView) mPopNavView.findViewById(R.id.u_department);
		
		weixin = (LinearLayout) mPopNavView.findViewById(R.id.weixin);
		email = (LinearLayout) mPopNavView.findViewById(R.id.email);
		phone = (LinearLayout) mPopNavView.findViewById(R.id.phone);
		
		cweixin = (TextView) mPopNavView.findViewById(R.id.c_weixin);
		cemail = (TextView) mPopNavView.findViewById(R.id.c_email);
		cphone = (TextView) mPopNavView.findViewById(R.id.c_phone);
		

		callButton = (Button) mPopNavView.findViewById(R.id.callBtn);
		messageBtn = (Button) mPopNavView.findViewById(R.id.messageBtn);
		return_btn = (ImageButton) mPopNavView.findViewById(R.id.return_btn);
		return_btn.setOnClickListener(returnOnclick);
		callButton.setOnClickListener(calllistener);
		messageBtn.setOnClickListener(messagelistener);

		mPopupNavWindow = new PopupWindow(mPopNavView,
				LayoutParams.MATCH_PARENT, this.screenHeight(), true);
	}
	
	// 用户信息
	public void show(UserBean userbean)
	{
		
		User user=userbean.getUser();
		MyImageLoader.getInstance().displayImage(user.getHeaderfileid(),uimage);
		uimage.setTag(user.getHeaderfileid());
		uimage.setOnClickListener(uimageLook);
		uname.setText(user.getName());
		usex.setText(user.getSex());
		usexImage.setBackgroundResource(user.getSex().equals("女")?R.drawable.sex_women:R.drawable.sex_men);
		if(user.getSignature()==null)
		signature.setVisibility(View.GONE);
		else
		usignature.setText(user.getSignature());
		DataInfoService data=new DataInfoService();
		final String instname=data.getUserInst().getName();
		weixin.setVisibility(View.GONE);
		email.setVisibility(View.GONE);
		phone.setVisibility(View.GONE);
		if(AssertValue.isNotNullAndNotEmpty(userbean.getUsercontacts()))
		{
			for (UserContact userContact : userbean.getUsercontacts()) {
				if(userContact.getContactkey().equals("weixin"))
				{
					weixin.setVisibility(View.VISIBLE);
					cweixin.setText(userContact.getContactvalue());
				}
				else if(userContact.getContactkey().equals("email"))
				{
					email.setVisibility(View.VISIBLE);
					cemail.setText(userContact.getContactvalue());
				}
				else if(userContact.getContactkey().equals("phone"))
				{
					phone.setVisibility(View.VISIBLE);
					cphone.setText(userContact.getContactvalue());
				}
			}
		}
		udepartment.setText(instname);
		if(AssertValue.isNotNull(userbean.getOrg()))
		udepartment.setText(userbean.getOrg().getName());
	
		callButton.setTag(cphone);
		messageBtn.setTag(cphone);
		mPopupNavWindow.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#b0000000")));
		mPopupNavWindow.showAtLocation(baseView, Gravity.BOTTOM, 0, 0);
		mPopupNavWindow.setAnimationStyle(R.style.main_pop);
		mPopupNavWindow.setOutsideTouchable(true);
		mPopupNavWindow.setFocusable(true);
		mPopupNavWindow.update();
	}

	// 返回按钮点击事件
	OnClickListener returnOnclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mPopupNavWindow.dismiss();
		}
	};
	
	
	// 用戶詳細信息頁面打電話的服務事件
		OnClickListener calllistener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView cphone = (TextView) v.getTag();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ cphone.getText().toString()));
				// 通知activtity处理传入的call服务
				baseActivity.startActivity(intent);
			}
		};

		// 用戶詳細信息頁面发送短信的服務事件
		OnClickListener messagelistener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView cphone = (TextView) v.getTag();
				Uri uri = Uri.parse("smsto:"+cphone.getText().toString());
				Intent it = new Intent(Intent.ACTION_SENDTO, uri);
				baseActivity.startActivity(it);
			}
		};
		
		// 获取屏幕的高度
		private int screenHeight() {
			DisplayMetrics dm = baseActivity.getResources()
					.getDisplayMetrics();
			Rect rect = new Rect();
			baseActivity.getWindow().getDecorView()
					.getWindowVisibleDisplayFrame(rect);
			// dm.heightPixels屏幕高度
			// rect.top; //状态栏高度
			return dm.heightPixels - rect.top;
		}
		
		//查看大头像，用户头像
		OnClickListener uimageLook=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String headfileid=(String)v.getTag();
				List<String> imagelist=new ArrayList<String>();
				imagelist.add(headfileid);
				Intent intent = new Intent(baseActivity, ImageViewPagerActivity.class);
				intent.putStringArrayListExtra("imagelist", (ArrayList<String>) imagelist);
				baseActivity.startActivity(intent);
			}
		};
}
