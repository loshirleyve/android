package com.yun9.mobile.framework.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.department.callback.AsyncHttpPasswordCallback;
import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.framework.activity.EditUserSignatureActivity;
import com.yun9.mobile.framework.activity.LoginActivity;
import com.yun9.mobile.framework.adapter.InstListAdatper;
import com.yun9.mobile.framework.auth.AuthManager;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.cache.MsgCardCache;
import com.yun9.mobile.framework.cache.UserInfoCache;
import com.yun9.mobile.framework.interfaces.ui4presenter.UI4preMineFragment;
import com.yun9.mobile.framework.model.Inst;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.model.UserPassword;
import com.yun9.mobile.framework.presenters.PresenterMineFragment;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.support.GetPersonalData;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.push.PushSynchronization;
import com.yun9.mobile.roundimage.RoundImageView;

public class MineFragment extends Fragment implements UI4preMineFragment{
	
	private static final int EDIT_SIGNATURE_ACTIVITY_REQUESE = 100;

	private View mBaseView;

	private RelativeLayout logoutRL;
	private RelativeLayout cleanRL;
	private RelativeLayout updatepassRL;
	private RelativeLayout changeInstRL;
	private RelativeLayout photoRL;

    private LinearLayout ll_dynamic;
	private FragmentActivity mContext;
    private ListView lv_inst_list;
    private InstListAdatper mAdapter;
    private Inst oldInst;
    private List<Inst> instList;
    private SessionManager sessionManager;

    private TextView tv_signature;
    private View rl_signature;
    
    private RoundImageView iv_headPic;
    
    private TextView tvUserName;

    private RelativeLayout rlUpdate;
    
    private ProgressDialog proDlg;
    private PresenterMineFragment presenter;
    private ProgressDialog downApkProDlg;
    private ProgressDialog uppassDia;
    
	/**
	 * 修改密码的
	 */
    private View mPopNavView;
	private PopupWindow mPopupNavWindow;
	private ImageButton returnBtn;
	private Button      subBtn;
	private RoundImageView upwImage;
	private EditText upwPwdjiu;
	private EditText upwPwdxin1;
	private EditText upwPwdxin2;
	
    /**
     * 升级对话框
     */
    private AlertDialog.Builder builder;
    private AlertDialog updateDlg;
    
    private TextView tvNewVersionTip;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mContext = this.getActivity();

		mBaseView = inflater.inflate(R.layout.fragment_mine, null);

		return mBaseView;
	}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        tvNewVersionTip = (TextView) mBaseView.findViewById(R.id.tvNewVersionTip);
        
        rlUpdate = (RelativeLayout)mBaseView.findViewById(R.id.rlUpdate);
        tvUserName = (TextView) mBaseView.findViewById(R.id.tvUserName);
        logoutRL = (RelativeLayout) mBaseView.findViewById(R.id.logout);
        cleanRL = (RelativeLayout) mBaseView.findViewById(R.id.clean);
        updatepassRL = (RelativeLayout) mBaseView.findViewById(R.id.updatepass);
        initUPwd();
        changeInstRL = (RelativeLayout) mBaseView.findViewById(R.id.change_inst);
        photoRL = (RelativeLayout) mBaseView.findViewById(R.id.photo);
        ll_dynamic = (LinearLayout) mBaseView.findViewById(R.id.ll_dynamic);
        lv_inst_list = (ListView) mBaseView.findViewById(R.id.lv_inst_list);
        tv_signature = (TextView) mBaseView.findViewById(R.id.tv_n_signature);
        rl_signature =  mBaseView.findViewById(R.id.rl_signature);
        iv_headPic =  (RoundImageView) mBaseView.findViewById(R.id.pic);
        lv_inst_list.setVisibility(View.GONE);

        logoutRL.setOnClickListener(logoutOnClickListener);
        cleanRL.setOnClickListener(cleanOnClickListener);
        updatepassRL.setOnClickListener(updatePasswordOnClickListener);
        changeInstRL.setOnClickListener(changeInstOnClickListener);
        photoRL.setOnClickListener(photoOnClickListener);
        ll_dynamic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 SessionManager manager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
	                User user = manager.getAuthInfo().getUserinfo();
	                GetPersonalData.goToPersonalData(getActivity(),user,true);
			}
		});
        rl_signature.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MineFragment.this.getActivity(), EditUserSignatureActivity.class);
				startActivityForResult(intent,EDIT_SIGNATURE_ACTIVITY_REQUESE);
			}
		});

        rlUpdate.setOnClickListener(UpdateOnClickListenr);

        sessionManager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
        
        tvUserName.setText(sessionManager.getAuthInfo().getUserinfo().getName());
        
        tv_signature.setText(sessionManager.getAuthInfo().getUserinfo().getSignature());
        
        instList = sessionManager.getAuthInfo().getAllinsts();

        SessionManager manager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
        oldInst = manager.getAuthInfo().getInstinfo();
        String instId = oldInst.getId();
        int position = 0;
        for (int i = 0;i < instList.size();i++)
        {
            if (instList.get(i).getId().equals(instId))
            {
                position = i;
                break;
            }
        }
        MyImageLoader.getInstance().displayImage(manager.getAuthInfo().getUserinfo().getHeaderfileid(),iv_headPic);
        mAdapter = new InstListAdatper(getActivity(),instList,position);
        lv_inst_list.setAdapter(mAdapter);
        lv_inst_list.setOnItemClickListener(instOnItemListener);
        
        presenter = new PresenterMineFragment(getActivity(), MineFragment.this);
        presenter.work();
        init();
    }

    
    private void init() {
//		initProDlg();
	}


//	private void initProDlg() {
//		proDlg = new ProgressDialog(getActivity());
//		proDlg.setTitle("正在检查更新...");
//		
//		
//		downApkProDlg = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
//		downApkProDlg.setTitle("正在更新...");
//		downApkProDlg.setCancelable(false);
//		
//		builder = new Builder(getActivity());
//		builder.setTitle("APP升级提示");
//		builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				presenter.updateEnsureOnclickListener();
//			}
//		});
//		builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				presenter.updateCancelOnClickListener();
//			}
//		});
//		updateDlg = builder.create();
//	}
	
	/**
	 * 初始化修改密码页面控件
	 */
    public void initUPwd()
    {
    	mPopNavView = LayoutInflater.from(mContext).inflate(
				R.layout.update_password, null);
		returnBtn=(ImageButton) mPopNavView.findViewById(R.id.return_btn);
		returnBtn.setOnClickListener(returnOnclick);
		subBtn=(Button) mPopNavView.findViewById(R.id.submitPassword);
		upwImage = (RoundImageView) mPopNavView.findViewById(R.id.upw_image);
		upwPwdjiu = (EditText) mPopNavView.findViewById(R.id.upw_mima1);
		upwPwdxin1= (EditText) mPopNavView.findViewById(R.id.upw_xinmima1);
		upwPwdxin2= (EditText) mPopNavView.findViewById(R.id.upw_xinmima2);
		subBtn.setOnClickListener(submitOnclick);
		mPopupNavWindow = new PopupWindow(mPopNavView,LayoutParams.MATCH_PARENT,screenHeight(), true);
    }
    
    private OnClickListener submitOnclick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(AssertValue.isNotEmpty(upwPwdjiu.getText().toString()) && AssertValue.isNotEmpty(upwPwdxin1.getText().toString()) && AssertValue.isNotEmpty(upwPwdxin2.getText().toString()))
			{
				if(upwPwdxin1.length()<6)
				{
					showToast("请输入长度在6到15的新密码");
					return;
				}
				uppassDia= TipsUtil.openDialog(null, false, mContext);
				DataInfoService data=new DataInfoService();
				Map<String,String> params=new HashMap<String, String>();
				params.put("password",upwPwdjiu.getText().toString());
				params.put("newpassword",upwPwdxin1.getText().toString());
				params.put("repassword",upwPwdxin2.getText().toString());
				data.updatePasswordCallback(params,new AsyncHttpPasswordCallback() {
					
					@Override
					public void handler(UserPassword password) {
						uppassDia.dismiss();
						if(AssertValue.isNotNull(password))
						{
							mPopupNavWindow.dismiss();
							showToast("修改密码成功,请重新登录！");
							logoutRL.performClick();
						}
						else
						{
							cleanPassword();
							showToast("修改密码失败,请核对信息！");
						}
					}
				});
			}
			else
			{
				showToast("请输入完整信息！");
			}
			
		}
	};
	
	public void cleanPassword()
	{
		upwPwdjiu.setText("");
		upwPwdxin1.setText("");
		upwPwdxin2.setText("");
	}
    
	/**
     *	更新app
     */
    private OnClickListener UpdateOnClickListenr = new OnClickListener() {
        @Override
        public void onClick(View view) {
//            presenter.updateOnClickLister();
            presenter.go2aboutApp();
        }
    };

	public void showToast(String text){
       Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}



	private OnClickListener changeInstOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (lv_inst_list.getVisibility() == View.VISIBLE)
            {
                lv_inst_list.setVisibility(View.GONE);
            }else
            {
                lv_inst_list.setVisibility(View.VISIBLE);
            }
		}
	};

    private AdapterView.OnItemClickListener instOnItemListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            sessionManager.changeInst(oldInst, instList.get(i));
            Toast.makeText(getActivity(),"切换成功-->"+instList.get(i).getName(),Toast.LENGTH_SHORT).show();
            mAdapter = new InstListAdatper(getActivity(),instList,i);
            lv_inst_list.setAdapter(mAdapter);
            lv_inst_list.setSelection(i);
            oldInst = instList.get(i);
        }
    };

	private OnClickListener logoutOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			MsgCardCache.getInstance().clean();
			AuthManager authManager = BeanConfig.getInstance().getBeanContext()
					.get(AuthManager.class);
			authManager.logout();
			PushSynchronization.unregisterPush(MineFragment.this.mContext);
			Intent intent = new Intent(mContext, LoginActivity.class);
			startActivity(intent);
			((Activity) mContext).overridePendingTransition(R.anim.activity_up,
					R.anim.fade_out);

			mContext.finish();

		}
	};

	private OnClickListener cleanOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			MyImageLoader.getInstance().clearDiskCache();
			MyImageLoader.getInstance().clearMemoryCache();
			MsgCardCache.getInstance().clean();
			UserInfoCache.getInstance().clean();
			Toast.makeText(mContext, "缓存已清理", Toast.LENGTH_LONG).show();
		}
	};
	
	private OnClickListener updatePasswordOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			DataInfoService data=new DataInfoService();
			MyImageLoader.getInstance().displayImage(data.getUser().getHeaderfileid(),upwImage);
			mPopupNavWindow.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#b0000000")));
			mPopupNavWindow.showAtLocation(mBaseView, Gravity.BOTTOM, 0, 0);
			mPopupNavWindow.setAnimationStyle(R.style.main_pop);
			mPopupNavWindow.setOutsideTouchable(true);
			mPopupNavWindow.setFocusable(true);
			mPopupNavWindow.update();
		}
	};

	private OnClickListener photoOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(mContext, "相册", Toast.LENGTH_LONG).show();
		}
	};

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(EditUserSignatureActivity.EDIT_SIGNATURE_ACTIVITY_RESULT==resultCode)  
        {
			tv_signature.setText(data.getStringExtra("signature"));
			  SessionManager manager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
              User user = manager.getAuthInfo().getUserinfo();
              user.setSignature(data.getStringExtra("signature"));
              manager.setLocationParams();
        }
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void showFocusUpdateDlg(String message) {
		updateDlg.setMessage(message);
		updateDlg.setCancelable(false);//强制升级
		updateDlg.show();
	}

	@Override
	public void showChoseUpdateDlg(String message) {
		updateDlg.setMessage(message);
		updateDlg.setCancelable(true);//取消强制升级
		updateDlg.show();
	}

	@Override
	public void closeUpdateDlg() {
		updateDlg.dismiss();
	}

	@Override
	public void showIntallApkDlg() {
		downApkProDlg.show();
	}

	@Override
	public void closeIntallApkDlg() {
		downApkProDlg.dismiss();
	}

	@Override
	public void setIntallApkDlgProgress(String msg) {
		downApkProDlg.setMessage(msg);
		
	}

	@Override
	public void isShowNewVersionTip(boolean isShow) {
		if(isShow){
			tvNewVersionTip.setVisibility(View.VISIBLE);
		}else{
			tvNewVersionTip.setVisibility(View.GONE);
		}
	}

	// 返回按钮点击事件
	OnClickListener returnOnclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mPopupNavWindow.dismiss();
		}
	};
	
	// 获取屏幕的高度
	private int screenHeight() {
		DisplayMetrics dm = getActivity().getResources()
				.getDisplayMetrics();
		Rect rect = new Rect();
		getActivity().getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(rect);
		// dm.heightPixels屏幕高度
		// rect.top; //状态栏高度
		return dm.heightPixels - rect.top;
	}
}
