package com.yun9.mobile.position.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.view.TitleBarView;
import com.yun9.mobile.position.adapter.PositionFragmentPagerAdapter;
import com.yun9.mobile.position.fragment.BusinessPositionFragment;
import com.yun9.mobile.position.fragment.CommunityPositionFragment;
import com.yun9.mobile.position.fragment.DefineKeyPositionFragment;
import com.yun9.mobile.position.fragment.OfficeBuildingPositionFragment;
import com.yun9.mobile.position.iface.IAcquirePositionCallBack;
import com.yun9.mobile.position.iface.IPoiSearch;
import com.yun9.mobile.position.iview.PositionIView;
import com.yun9.mobile.position.presenter.PositionPresenter;

public class PositionActivity extends FragmentActivity implements IPoiSearch, PositionIView {

	protected static final String TAG = PositionActivity.class.getSimpleName();

	private final String TITLE = "位置";
	
	private ImageView ivPicPosition;
	private MapView mMapView;
	private ArrayList<Fragment> fragmentsList;
	private ViewPager mPager;
	
	private PositionPresenter presenter;
    private int position_one;
    private int position_two;
    private int position_three;
    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private ImageView ivBottomLine;
	
    private View vgBottomLoad;
    private View vgBottomView;
    private View vLeft;
    private View vRight;
    private TitleBarView tbvTitleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_position);
		
		findView();
		init();
		setEvent();
	}
	private void findView() {
		ivPicPosition = (ImageView) findViewById(R.id.ivPicPosition);
		mMapView = (MapView) findViewById(R.id.bmapView);
		tbvTitleBar = (TitleBarView) findViewById(R.id.tbvTitleBar);
		
	
		vgBottomLoad = findViewById(R.id.vgBottomLoad);
		vgBottomView = findViewById(R.id.vgBottomView);
		
		
		vLeft = tbvTitleBar.getBtnLeft();
		vLeft.setVisibility(View.VISIBLE);
		vRight = tbvTitleBar.getBtnFuncNav();
		vRight.setVisibility(View.VISIBLE);
		TextView tvTitle = tbvTitleBar.getTvTitle();
		tvTitle.setText(TITLE);
		tvTitle.setVisibility(View.VISIBLE);
	}
	private void setEvent() {
		// 测试
		findViewById(R.id.btnTest).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.refresh();
			}
		});
		
		
		vLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                presenter.canelOnClick();
			}
		});
		
		vRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.vRightOnClick();
			}
		});
	}

	private void init() {
		presenter = new PositionPresenter(this, this, mMapView,ivPicPosition);
		initWidth();
		initTextView();
		
	}
	

    
    private void initWidth() {
        ivBottomLine = (ImageView) findViewById(R.id.ivBottomLine);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        Log.d(TAG, "cursor imageview width=" + bottomLineWidth);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / 4.0 - bottomLineWidth) / 2);
        Log.i("MainActivity", "offset=" + offset);

        position_one = (int) (screenW / 4.0);
        position_two = position_one * 2;
        position_three = position_one * 3;
    }
	
    private TextView tvTabAll, tvTabOfficeBuilding, tvTabBusiness, tvTabCommunity;
    private void initTextView() {
    	tvTabAll = (TextView) findViewById(R.id.tvTabAll);
    	tvTabOfficeBuilding = (TextView) findViewById(R.id.tvTabOfficeBuilding);
    	tvTabBusiness = (TextView) findViewById(R.id.tvTabBusiness);
    	tvTabCommunity = (TextView) findViewById(R.id.tvTabCommunity);

    	tvTabAll.setOnClickListener(new MyOnClickListener(0));
        tvTabOfficeBuilding.setOnClickListener(new MyOnClickListener(1));
        tvTabBusiness.setOnClickListener(new MyOnClickListener(2));
        tvTabCommunity.setOnClickListener(new MyOnClickListener(3));
    }
    
    private void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        fragmentsList = new ArrayList<Fragment>();
        Fragment defineKeyFragment = new DefineKeyPositionFragment();
        Fragment officeBuildingFragment = new OfficeBuildingPositionFragment();
        Fragment businessFragment = new BusinessPositionFragment();
        Fragment communityFragment = new CommunityPositionFragment();

        fragmentsList.add(defineKeyFragment);
        fragmentsList.add(officeBuildingFragment);
        fragmentsList.add(businessFragment);
        fragmentsList.add(communityFragment);
        
        mPager.setAdapter(new PositionFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    

	
   @Override
	protected void onResume() {
	   super.onResume();
	   presenter.onResume();
	}
   
   @Override
	protected void onPause() {
       Log.i(TAG, "onPause");
	   presenter.onPause();
	   super.onPause();
	}

    @Override
    protected void onStop() {
        Log.i(TAG, "onPause");
        super.onStop();

    }

    @Override
	protected void onDestroy() {
       Log.i(TAG, "onDestroy");
		presenter.onDestroy();
		super.onDestroy();
		
	}
   
   

	public class MyOnClickListener implements OnClickListener {
	        private int index = 0;

	        public MyOnClickListener(int i) {
	            index = i;
	        }

	        @Override
	        public void onClick(View v) {
	            mPager.setCurrentItem(index);
	        }
	    };
	    
	public class MyOnPageChangeListener implements OnPageChangeListener {

	        @Override
	        public void onPageSelected(int index) {
	            Animation animation = null;
	            switch (index) {
	            case 0:
	                if (currIndex == 1) {
	                    animation = new TranslateAnimation(position_one, 0, 0, 0);
	                } else if (currIndex == 2) {
	                    animation = new TranslateAnimation(position_two, 0, 0, 0);
	                } else if (currIndex == 3) {
	                    animation = new TranslateAnimation(position_three, 0, 0, 0);
	                }
	                break;
	            case 1:
	                if (currIndex == 0) {
	                    animation = new TranslateAnimation(0, position_one, 0, 0);
	                } else if (currIndex == 2) {
	                    animation = new TranslateAnimation(position_two, position_one, 0, 0);
	                } else if (currIndex == 3) {
	                    animation = new TranslateAnimation(position_three, position_one, 0, 0);
	                }
	                break;
	            case 2:
	                if (currIndex == 0) {
	                    animation = new TranslateAnimation(0, position_two, 0, 0);
	                } else if (currIndex == 1) {
	                    animation = new TranslateAnimation(position_one, position_two, 0, 0);
	                } else if (currIndex == 3) {
	                    animation = new TranslateAnimation(position_three, position_two, 0, 0);
	                }
	                break;
	            case 3:
	                if (currIndex == 0) {
	                    animation = new TranslateAnimation(0, position_three, 0, 0);
	                } else if (currIndex == 1) {
	                    animation = new TranslateAnimation(position_one, position_three, 0, 0);
	                } else if (currIndex == 2) {
	                    animation = new TranslateAnimation(position_two, position_three, 0, 0);
	                }
	                break;
	            }
	            currIndex = index;
	            animation.setFillAfter(true);
	            animation.setDuration(300);
	            ivBottomLine.startAnimation(animation);
	        }

	        @Override
	        public void onPageScrolled(int arg0, float arg1, int arg2) {
	        }

	        @Override
	        public void onPageScrollStateChanged(int arg0) {
	        }
	    }


	@Override
	public double getLat() {
		return presenter.getLat();
	}

	@Override
	public double getLng() {
		return presenter.getLng();
	}

	@Override
	public int radius() {
		return presenter.getPoiSearchRadius();
	}

	@Override
	public void setPoi(PoiInfo poi) {
		presenter.setPoiChoose(poi);
	}

	@Override
	public void showChoosePoiName(String name) {
		TextView tvPoiName = (TextView)findViewById(R.id.tvPoiName);	
		tvPoiName.setText(name);
	}

	@Override
	public void showChoosePoiAddr(String addr) {
		TextView tvPoiAddr= (TextView)findViewById(R.id.tvPoiAddr);
		tvPoiAddr.setText(addr);
	}
	@Override
	public void freshMapCenter(double latitude, double longitude) {
		presenter.freshMapCenter(latitude, longitude);
	}
	@Override
	public void showBottomView() {
		vgBottomView.setVisibility(View.VISIBLE);
	}
	@Override
	public void closeBottomLoad() {
		vgBottomLoad.setVisibility(View.GONE);
	}
	@Override
	public void ShowToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void initBottomView() {
		initViewPager();		
	}
}
