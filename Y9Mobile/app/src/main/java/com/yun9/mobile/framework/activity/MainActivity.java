package com.yun9.mobile.framework.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.department.callback.AsyncHttpUserNaviCallback;
import com.yun9.mobile.department.fragment.DepartmentTreeTabFragment;
import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.framework.auth.AuthManager;
import com.yun9.mobile.framework.base.activity.BaseFragmentActivity;
import com.yun9.mobile.framework.base.activity.EnterActivity;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.fragment.FoundFragment;
import com.yun9.mobile.framework.fragment.MineFragment;
import com.yun9.mobile.framework.impls.presenter.ImplPre4UiMainActivity;
import com.yun9.mobile.framework.interfaces.ui4presenter.UI4PreMainActivity;
import com.yun9.mobile.framework.model.SysMdNavi;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.JsonUtil;
import com.yun9.mobile.framework.view.FunNavView;
import com.yun9.mobile.framework.view.FunNavView.Holder;
import com.yun9.mobile.msg.fragment.MsgCardFragment;
import com.yun9.mobile.push.PushSynchronization;

public class MainActivity extends BaseFragmentActivity implements
		UI4PreMainActivity {

	private View mPopView;
	private PopupWindow mPopupWindow;
	private LinearLayout buttonBarGroup;

	private View mPopNavView;
	private PopupWindow mPopupNavWindow;

	private FunNavView funNavView;

	private TextView appCancle;
	private TextView appLogout;
	private ImageButton buttonMsg;
	private ImageButton buttonConstact;
	private ImageButton buttonJM;
	private ImageButton buttonSetting;
	private ImageButton buttonFunNav;

	private final int REQ_POSITION_DEFAULT = 0x1001;
	private final int REQ_POSITION_DAKA = 0x1002;

	private View currentButton;
	private boolean isExit;

	private ImplPre4UiMainActivity presenter;
	private ProgressDialog proDlg;
	private AlertDialog updateDlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
		buttonMsg.performClick();
		// 注册推送
		PushSynchronization.registerPush(this);

		initProDlg();
		presenter = new ImplPre4UiMainActivity(this, this);

	}

	private void initProDlg() {
		proDlg = new ProgressDialog(this);
		proDlg.setCancelable(false);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (AssertValue.isNotNull(mPopupWindow)) {
			this.mPopupWindow.dismiss();
		}
	}

	@Override
	protected void initWidget() {
		mPopView = LayoutInflater.from(this.context).inflate(
				R.layout.main_popupmenu, null);

		mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);

		buttonBarGroup = (LinearLayout) findViewById(R.id.button_bar_group);

		appCancle = (TextView) mPopView.findViewById(R.id.app_cancle);
		appLogout = (TextView) mPopView.findViewById(R.id.app_logout);

		buttonMsg = (ImageButton) findViewById(R.id.button_msg);
		buttonConstact = (ImageButton) findViewById(R.id.button_constact);
		buttonJM = (ImageButton) findViewById(R.id.button_jm);
		buttonSetting = (ImageButton) findViewById(R.id.button_setting);
		buttonFunNav = (ImageButton) findViewById(R.id.funnav);

		mPopNavView = LayoutInflater.from(this.context).inflate(
				R.layout.main_popupnav, null);
		mPopupNavWindow = new PopupWindow(mPopNavView,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);

		funNavView = (FunNavView) mPopNavView.findViewById(R.id.funnav_view);

		funNavView.load(this.builderNavItems());

	}

	@Override
	protected void bindEvent() {

		appLogout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				AuthManager authManager = BeanConfig.getInstance()
						.getBeanContext().get(AuthManager.class);
				authManager.logout();

				Intent intent = new Intent(context, LoginActivity.class);
				startActivity(intent);
				((Activity) context).overridePendingTransition(
						R.anim.activity_up, R.anim.fade_out);
				finish();
			}
		});

		appCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});

		funNavView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FunNavView.Holder holder = (Holder) view.getTag();
				Map<String,Object> json=JsonUtil.jsonToBean(holder.navItem.getActionparams(), Map.class);
				try {
					EnterActivity entity = (EnterActivity) Class.forName(json.get("class").toString()).newInstance();
					entity.enter(MainActivity.this, null);
					mPopupNavWindow.dismiss();
				} catch (ClassNotFoundException e) {
					showToast("系统配置参数错误。请稍后重新尝试。");
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		});

		buttonMsg.setOnClickListener(msgOnClickListener);
		buttonConstact.setOnClickListener(constactOnClickListener);
		buttonJM.setOnClickListener(jmOnClickListener);
		buttonFunNav.setOnClickListener(funcNavOnClickListener);
		buttonSetting.setOnClickListener(settingOnClickListener);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_MENU) {
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#b0000000")));
			mPopupWindow.showAtLocation(buttonBarGroup, Gravity.BOTTOM, 0, 0);
			mPopupWindow.setAnimationStyle(R.style.main_pop);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setFocusable(true);
			mPopupWindow.update();
		}

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private OnClickListener msgOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			MsgCardFragment mainMsgFragment = new MsgCardFragment();
			ft.replace(R.id.fl_content, mainMsgFragment,
					MainActivity.class.getName());
			ft.commit();
			setButton(v);
		}
	};

	private OnClickListener constactOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			DataInfoService data=new DataInfoService();
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			DepartmentTreeTabFragment dpartmentGroupTabFragment = new DepartmentTreeTabFragment(
					context, data.getUserInst().getName());
			dpartmentGroupTabFragment.setIsselect(true);
			dpartmentGroupTabFragment.setState(0);
			ft.replace(R.id.fl_content, dpartmentGroupTabFragment,
					MainActivity.class.getName());
			ft.commit();
			setButton(v);

		}
	};

	private OnClickListener jmOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			FoundFragment foundFragment = new FoundFragment(context);
			FragmentManager fm = getSupportFragmentManager();

			FragmentTransaction ft = fm.beginTransaction();

			ft.replace(R.id.fl_content, foundFragment,
					MainActivity.class.getName());
			ft.commit();
			setButton(v);
		}
	};

	private OnClickListener settingOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			MineFragment mineFragment = new MineFragment();

			FragmentManager fm = getSupportFragmentManager();

			FragmentTransaction ft = fm.beginTransaction();

			ft.replace(R.id.fl_content, mineFragment,
					MainActivity.class.getName());
			ft.commit();
			setButton(v);
		}
	};

	private OnClickListener funcNavOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			mPopupNavWindow.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#b0000000")));

			mPopupNavWindow.showAtLocation(buttonFunNav, Gravity.CENTER, 100,
					100);
			mPopupNavWindow.setAnimationStyle(R.style.main_pop);
			mPopupNavWindow.setOutsideTouchable(true);
			mPopupNavWindow.setFocusable(true);
			mPopupNavWindow.update();
		}
	};

	private void setButton(View v) {
		if (currentButton != null && currentButton.getId() != v.getId()) {
			currentButton.setEnabled(true);
		}
		v.setEnabled(false);
		currentButton = v;
	}
	
	
	private List<SysMdNavi> builderNavItems() {
		final List<SysMdNavi> navItems = new ArrayList<SysMdNavi>();
		DataInfoService data=new DataInfoService();
		data.getUserNaviCallback(new AsyncHttpUserNaviCallback() {
			
			@Override
			public void handler(List<SysMdNavi> navis) {
				navItems.addAll(navis);
			}
		});		
		return navItems;

	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}

	};

	public void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(),
					R.string.app_click_again_exit, Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			System.exit(0);
		}
	}

	/*
	 * 更新对话框
	 */
	@Override
	public void showUpdateDlg(String string, String log) {

		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(string);
		builder.setMessage(log);
		builder.setPositiveButton("立刻升级",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						presenter.updateEnsureOnclickListener();
					}
				});
		builder.setNegativeButton("下次再说",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						presenter.updateCancelOnClickListener();
					}
				});
		updateDlg = builder.create();
		updateDlg.show();
	}

	/*
	 * （非 Javadoc） 强制更新对话框
	 * 
	 * @see
	 * com.yun9.mobile.framework.interfaces.ui4presenter.UI4PreMainActivity#
	 * showFocusUpdateDlg(java.lang.String, java.lang.String)
	 */
	@Override
	public void showFocusUpdateDlg(String string, String log) {

		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(string);
		builder.setMessage(log);
		builder.setPositiveButton("立刻升级",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						presenter.updateEnsureOnclickListener();
					}
				});
		updateDlg = builder.create();
		updateDlg.setCancelable(false);
		updateDlg.show();
	}

	@Override
	public void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	/*
	 * （非 Javadoc） 关闭更新进度对话框
	 * 
	 * @see
	 * com.yun9.mobile.framework.interfaces.ui4presenter.UI4PreMainActivity#
	 * closeUpdateProDlg()
	 */
	@Override
	public void closeUpdateProDlg() {
		proDlg.dismiss();
	}

	/*
	 * （非 Javadoc） 显示更新进度对话框
	 * 
	 * @see
	 * com.yun9.mobile.framework.interfaces.ui4presenter.UI4PreMainActivity#
	 * showUpdateProDlg(java.lang.String, java.lang.String)
	 */
	@Override
	public void showUpdateProDlg(String title, String message) {
		proDlg.setTitle(title);
		proDlg.setMessage(message);
		proDlg.show();
	}

}