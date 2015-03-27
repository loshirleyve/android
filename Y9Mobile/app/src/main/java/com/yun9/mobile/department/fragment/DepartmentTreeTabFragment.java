package com.yun9.mobile.department.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.department.activity.DepartmentDetailActivity;
import com.yun9.mobile.department.adapter.DepartmentTreeViewPageAdapter;
import com.yun9.mobile.department.adapter.DepartmentTreeViewPageAdapter.DListItemView;
import com.yun9.mobile.department.adapter.UserViewPageAdapter;
import com.yun9.mobile.department.adapter.UserViewPageAdapter.UListItemView;
import com.yun9.mobile.department.callback.AsyncHttpOrgCallback;
import com.yun9.mobile.department.callback.AsyncHttpUserBeanCallback;
import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.department.support.UserConstant;
import com.yun9.mobile.framework.activity.MainActivity;
import com.yun9.mobile.framework.fragment.FoundFragment;
import com.yun9.mobile.framework.interfaces.scope.MsgScopeCallBack;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.OrgModel;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.model.UserBean;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.TipsUtil;

public class DepartmentTreeTabFragment extends Fragment {
	// ViewPager是google SDk中自带的一个附加包的一个类，可以用来实现屏幕间的切换。
	// android-support-v4.jar
	private Context mContext;
	private View baseView;
	private ViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private ImageView cursor;// 动画图片
	private LinearLayout linearLayout1;
	private TextView t1, t2;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 1;// 当前页卡编号
	private View tab1view;
	private View tab2view;
	private ListView listview;
	private ListView listview2;
	private LayoutInflater mInflater;
	private boolean isselect;
	private DepartmentTreeViewPageAdapter<OrgModel> departmentTreeViewPageAdapter;
	private UserViewPageAdapter userViewPageAdapter;
	private String title;
	private TextView titleTop;
	private EditText searchText;
	private ImageView delimage;
	private Button selectBtn;
	private Button cancelBtn;
	private ProgressDialog progressDialog;
	private DataInfoService data;
	private ImageButton returnButton;
	private ImageButton returnButton2;
	private int state;
	private TextView contactCommit;
	private LinearLayout contactLayout;

	private Map<String, User> selectedUsers;
	private Map<String, Org> selectedOrgs;
	private MsgScopeCallBack callback;
	private int selectUserOrOrg;// 显示用户或者显示组织 0 都显示，1只显示用户，2只显示部门

	public DepartmentTreeTabFragment(Context context, String title) {
		this.mContext = context;
		this.title = title;
	}

	public DepartmentTreeTabFragment(Context context, String title,
			Map<String, User> users, Map<String, Org> orgs,
			int selectUserOrOrg, MsgScopeCallBack contactUserCallback) {
		this.mContext = context;
		this.title = title;
		this.selectedUsers = users;
		this.selectedOrgs = orgs;
		this.selectUserOrOrg = selectUserOrOrg;
		this.callback = contactUserCallback;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.fragment_department_group, null);
		initTextView();
		initImageButtonView();
		initImageView();
		initViewPager();
		return baseView;
	}

	/**
	 * 初始化头标
	 */
	private void initTextView() {
		linearLayout1 = (LinearLayout) baseView
				.findViewById(R.id.linearLayout1);// 包裹t1,t2
		t1 = (TextView) baseView.findViewById(R.id.userlist);
		t2 = (TextView) baseView.findViewById(R.id.grouplist);
		linearLayout1.setVisibility(View.GONE);
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
	}

	/**
	 * 初始化头标
	 */
	private void initImageButtonView() {
		titleTop = (TextView) baseView.findViewById(R.id.title_txt);
		titleTop.setText(title);
		selectBtn = (Button) baseView.findViewById(R.id.select_btn);
		cancelBtn = (Button) baseView.findViewById(R.id.cancel_btn);
		returnButton = (ImageButton) baseView.findViewById(R.id.return_btn);
		returnButton2 = (ImageButton) baseView.findViewById(R.id.return_btn2);
		if (selectUserOrOrg == UserConstant.SELECT_USER_AND_ORG)
			returnButton.setVisibility(View.VISIBLE);
		else
			returnButton2.setVisibility(View.VISIBLE);
		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (selectUserOrOrg == UserConstant.SELECT_USER_AND_ORG) {
					FoundFragment foundFragment = new FoundFragment(mContext);
					FragmentManager fm = getFragmentManager();
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.fl_content, foundFragment,
							MainActivity.class.getName());
					ft.commit();
				}
			}
		});
		returnButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
	}

	/**
	 * 初始化ViewPager
	 */
	private void initViewPager() {
		mPager = (ViewPager) baseView.findViewById(R.id.vPager);
		mInflater = LayoutInflater.from(mContext);

		tab1view = mInflater.inflate(R.layout.userlist_tab1, null);
		listview = (ListView) tab1view.findViewById(R.id.userAll);
		tab2view = mInflater.inflate(R.layout.department_group_tab2, null);
		listview2 = (ListView) tab2view.findViewById(R.id.departmentGroup);
		searchText = (EditText) tab1view.findViewById(R.id.edit_operator_name);
		delimage = (ImageView) tab1view.findViewById(R.id.del);
		progressDialog = TipsUtil.openDialog(null, false, mContext);
		listViews = new ArrayList<View>();
		data = new DataInfoService();
		userViewPageAdapter = new UserViewPageAdapter(mContext, baseView,
				progressDialog, getActivity(), selectUserOrOrg);
		data.getUserBeanCallBack(null, new AsyncHttpUserBeanCallback() {
			@Override
			public void handler(List<UserBean> users) {
				userViewPageAdapter.setListItems(users);
				listview.setAdapter(userViewPageAdapter);
			}
		});

		data.getAllOrgCallBack(null, new AsyncHttpOrgCallback() {
			@Override
			public void handler(List<Org> orgs) {
				try {
					departmentTreeViewPageAdapter = new DepartmentTreeViewPageAdapter<OrgModel>(
							listview2, mContext, data.transfromOrgModel(orgs),
							10);
					departmentTreeViewPageAdapter.setOrgItems(data
							.transfromOrg(orgs));
					departmentTreeViewPageAdapter.setDselect(isselect);
					departmentTreeViewPageAdapter.setClickitem(DepitemOnclick);
					departmentTreeViewPageAdapter.setStateD(state);
				} catch (IllegalArgumentException e) {
					progressDialog.dismiss();
					System.out.println(e);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					progressDialog.dismiss();
					e.printStackTrace();
				}
				listview2.setAdapter(departmentTreeViewPageAdapter);
				if (isselect == false) {
					selOnclick();
				}
			}
		});
		userViewPageAdapter.setIsselect(isselect);
		userViewPageAdapter.setState(state);
		if (selectUserOrOrg == UserConstant.SELECT_USER
				|| selectUserOrOrg == UserConstant.SELECT_USER_RADIO) {
			if (!AssertValue.isNotNullAndNotEmpty(userViewPageAdapter
					.getListItems())) {
				progressDialog.dismiss();
			}
			listViews.add(tab1view);
		}
		if (selectUserOrOrg == UserConstant.SELECT_ORG
				|| selectUserOrOrg == UserConstant.SELECT_ORG_RADIO) {
			listViews.add(tab2view);
			progressDialog.dismiss();
		}
		if (selectUserOrOrg == UserConstant.SELECT_USER_AND_ORG) {
			linearLayout1.setVisibility(View.VISIBLE);
			cursor.setVisibility(View.VISIBLE);
			listViews.add(tab1view);
			listViews.add(tab2view);
		}
		mPager.setAdapter(new MyPagerAdapter(listViews));
		if (selectUserOrOrg == UserConstant.SELECT_USER_AND_ORG)
			t1.setTextColor(getResources().getColor(R.color.them_red));
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());

		searchText.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				delimage.setVisibility(View.VISIBLE);
				String searchStr = searchText.getText().toString();
				Map<String, String> params = new HashMap<String, String>();
				params.put("name", searchStr);
				data.getUserBeanCallBack(params,
						new AsyncHttpUserBeanCallback() {
							@Override
							public void handler(List<UserBean> users) {
								if (AssertValue.isNotNullAndNotEmpty(users))
									userViewPageAdapter.setListItems(users);
								listview.setAdapter(userViewPageAdapter);
							}
						});
				if (searchText.getText().length() == 0) {
					delimage.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}
		});
		delimage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchText.getText().clear();
				delimage.setVisibility(View.GONE);
			}
		});
	}

	// 企业项点击事件
	OnClickListener DepitemOnclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			DListItemView item = (DListItemView) v.getTag();
			if (departmentTreeViewPageAdapter.getStateD() == 0) {
				Intent intent = new Intent(getActivity(),
						DepartmentDetailActivity.class);
				intent.putExtra("org", (Serializable) item.org);
				mContext.startActivity(intent);
			} else {
				// 选择部门控制按钮
				// 多选
				if (selectUserOrOrg == UserConstant.SELECT_ORG) {
					if (item.isSel.getText().equals("1")) {
						item.imageRightSel2.setVisibility(View.VISIBLE);
						departmentTreeViewPageAdapter.getSelorgItems().put(
								item.id.getText().toString(), item.org);
						item.isSel.setText("0");
					} else {
						item.imageRightSel2.setVisibility(View.GONE);
						item.imageRightSel1.setVisibility(View.VISIBLE);
						departmentTreeViewPageAdapter.getSelorgItems().remove(
								item.id.getText().toString());
						item.isSel.setText("1");
					}
				} else if (selectUserOrOrg == UserConstant.SELECT_ORG_RADIO)// 单选
				{
					if (item.isSel.getText().equals("1")) {
						for (Map.Entry<Integer, View> e : departmentTreeViewPageAdapter
								.get_Dcache().entrySet()) {
							DListItemView itemview = (DListItemView) e
									.getValue().getTag();
							itemview.imageRight.setVisibility(View.GONE);
							itemview.imageRightSel1.setVisibility(View.VISIBLE);
							itemview.imageRightSel2.setVisibility(View.GONE);
							itemview.isSel.setText("1");
						}
						item.imageRightSel2.setVisibility(View.VISIBLE);
						departmentTreeViewPageAdapter.getSelorgItems().clear();
						departmentTreeViewPageAdapter.getSelorgItems().put(
								item.id.getText().toString(), item.org);
						item.isSel.setText("0");
					} else if (item.isSel.getText().equals("0")) {
						item.imageRightSel2.setVisibility(View.GONE);
						item.imageRightSel1.setVisibility(View.VISIBLE);
						departmentTreeViewPageAdapter.getSelorgItems().clear();
						item.isSel.setText("1");
					}
				}
			}

		}

	};

	private void selOnclick() {
		contactCommit = (TextView) baseView.findViewById(R.id.commit_contact);
		contactLayout = (LinearLayout) baseView
				.findViewById(R.id.commitContactLayout);
		contactLayout.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#b0000000")));
		contactCommit.setText("完成");
		if (this.state == 0) {
			selectBtn.setVisibility(View.VISIBLE);
		} else {
			cancelBtn.setVisibility(View.VISIBLE);
			contactLayout.setVisibility(View.VISIBLE);
			userViewPageAdapter.setSelItems(selectedUsers);
			if (AssertValue.isNotNull(departmentTreeViewPageAdapter))
				departmentTreeViewPageAdapter.setSelorgItems(selectedOrgs);
		}

		contactCommit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AssertValue.isNotNull(callback)
						&& AssertValue.isNotNull(departmentTreeViewPageAdapter)) {
					callback.onSuccess(0, userViewPageAdapter.getSelItems(),
							departmentTreeViewPageAdapter.getSelorgItems());
				} else if (!AssertValue
						.isNotNull(departmentTreeViewPageAdapter)) {
					callback.onSuccess(0, userViewPageAdapter.getSelItems(),
							null);
				}
				getActivity().finish();
			}

		});
		selectBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectBtn.setVisibility(View.GONE);
				cancelBtn.setVisibility(View.VISIBLE);
				for (Map.Entry<Integer, View> e : userViewPageAdapter
						.get_cache().entrySet()) {
					UListItemView item = (UListItemView) e.getValue().getTag();
					item.imageRight.setVisibility(View.GONE);
					item.imageRightSel1.setVisibility(View.VISIBLE);
				}
				userViewPageAdapter.setState(1);
				if (AssertValue.isNotNull(departmentTreeViewPageAdapter))
					departmentTreeViewPageAdapter.setStateD(1);
				state = 1;
				contactLayout.setVisibility(View.VISIBLE);
			}
		});
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectBtn.setVisibility(View.VISIBLE);
				cancelBtn.setVisibility(View.GONE);
				if (AssertValue.isNotNullAndNotEmpty(userViewPageAdapter
						.getSelItems()))
					userViewPageAdapter.getSelItems().clear();
				if (AssertValue.isNotNull(departmentTreeViewPageAdapter)
						&& AssertValue
								.isNotNullAndNotEmpty(departmentTreeViewPageAdapter
										.getSelorgItems()))
					departmentTreeViewPageAdapter.getSelorgItems().clear();
				for (Map.Entry<Integer, View> e : userViewPageAdapter
						.get_cache().entrySet()) {
					UListItemView item = (UListItemView) e.getValue().getTag();
					item.imageRight.setVisibility(View.VISIBLE);
					item.imageRightSel1.setVisibility(View.GONE);
					item.imageRightSel2.setVisibility(View.GONE);
				}

				if (AssertValue.isNotNull(departmentTreeViewPageAdapter)) {
					for (Map.Entry<Integer, View> e : departmentTreeViewPageAdapter
							.get_Dcache().entrySet()) {
						DListItemView item = (DListItemView) e.getValue()
								.getTag();
						item.imageRight.setVisibility(View.VISIBLE);
						item.imageRightSel1.setVisibility(View.GONE);
						item.imageRightSel2.setVisibility(View.GONE);
					}
					departmentTreeViewPageAdapter.setStateD(0);
				}
				userViewPageAdapter.setState(0);
				state = 0;
				contactLayout.setVisibility(View.GONE);
			}
		});
	}

	/**
	 * 初始化动画
	 */
	private void initImageView() {
		cursor = (ImageView) baseView.findViewById(R.id.cursor);
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		cursor.setMinimumWidth(screenW / 2);// 设置图标最大宽度
		offset = screenW / 2;
		if (selectUserOrOrg != 0)
			cursor.setVisibility(View.GONE);
	}

	/**
	 * ViewPager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 头标点击监听
	 */

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			initImageView();
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					t1.setTextColor(getResources().getColor(R.color.them_red));
					t2.setTextColor(getResources().getColor(R.color.black));
					animation = new TranslateAnimation(offset, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					t2.setTextColor(getResources().getColor(R.color.them_red));
					t1.setTextColor(getResources().getColor(R.color.black));
					animation = new TranslateAnimation(0, offset, 0, 0);
				} else {
					t2.setTextColor(getResources().getColor(R.color.them_red));
					t1.setTextColor(getResources().getColor(R.color.black));
					animation = new TranslateAnimation(offset, offset, 0, 0);
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	public boolean isIsselect() {
		return isselect;
	}

	public void setIsselect(boolean isselect) {
		this.isselect = isselect;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}