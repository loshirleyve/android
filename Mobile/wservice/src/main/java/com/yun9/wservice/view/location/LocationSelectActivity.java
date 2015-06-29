package com.yun9.wservice.view.location;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.location.LocationBean;
import com.yun9.jupiter.location.LocationFactory;
import com.yun9.jupiter.location.OnGetPoiInfoListener;
import com.yun9.jupiter.location.OnLocationListener;
import com.yun9.jupiter.location.PoiInfoBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.login.LoginCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/12.
 */
public class LocationSelectActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.office_rb)
    private RadioButton officeRB;
    @ViewInject(id = R.id.business_rb)
    private RadioButton businessRB;
    @ViewInject(id = R.id.community_rb)
    private RadioButton communityRB;
    @ViewInject(id = R.id.location_lv)
    private ListView locationLV;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    private LocationBean lastLocationBean;

    private LocationSelectAdapter locationSelectAdapter;

    private List<PoiInfoBean> mPoiInfoBeans = new ArrayList<>();

    private ProgressDialog locationDialog = null;


    private String currKey;


    public static void start(Activity activity, LocationSelectCommand command) {
        Intent intent = new Intent(activity, LocationSelectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_location_select;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        officeRB.setTag("写字楼");
        businessRB.setTag("商家");
        communityRB.setTag("小区");

        officeRB.setOnClickListener(onClickListener);
        businessRB.setOnClickListener(onClickListener);
        communityRB.setOnClickListener(onClickListener);
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListenre);

        officeRB.performClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
        locationFactory.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
        locationFactory.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
        locationFactory.setOnLocationListener(onLocationListener);
        locationFactory.setOnGetPoiInfoListener(onGetPoiInfoListener);
        locationFactory.start();
        if (!AssertValue.isNotNullAndNotEmpty(currKey)) {
            officeRB.performClick();
        }
    }

    private View.OnClickListener onCancelClickListenre = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setResult(LoginCommand.RESULT_CODE_CANCEL);
            LocationSelectActivity.this.finish();
        }
    };

    private OnSelectListener onSelectListener = new OnSelectListener() {
        @Override
        public void onSelect(View view, boolean mode) {
            if (AssertValue.isNotNull(view.getTag())) {
                PoiInfoBean poiInfoBean = (PoiInfoBean) view.getTag();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(LocationSelectCommand.PARAM_POIINFO, poiInfoBean);
                intent.putExtras(bundle);

                setResult(LocationSelectCommand.RESULT_CODE_OK, intent);
                finish();
            }
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currKey = (String) v.getTag();
            if (AssertValue.isNotNullAndNotEmpty(currKey)) {
                locationDialog = ProgressDialog.show(LocationSelectActivity.this, null, getResources().getString(R.string.app_wating), true);
                LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
                locationFactory.requestLocation();
            }
        }
    };

    private OnLocationListener onLocationListener = new OnLocationListener() {
        @Override
        public void onReceiveLocation(LocationBean locationBean) {
            if (AssertValue.isNotNullAndNotEmpty(currKey)) {
                if (AssertValue.isNotNull(locationDialog)){
                    locationDialog.dismiss();
                }

                locationDialog = ProgressDialog.show(LocationSelectActivity.this, null, getResources().getString(R.string.app_wating), true);
                LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
                locationFactory.poiSearch(locationBean, currKey, 0, 2000);
            }
        }
    };


    private OnGetPoiInfoListener onGetPoiInfoListener = new OnGetPoiInfoListener() {
        @Override
        public void onGetPoiInfo(List<PoiInfoBean> poiInfoBeans) {
            if (AssertValue.isNotNullAndNotEmpty(poiInfoBeans)) {
                mPoiInfoBeans.clear();
                for (PoiInfoBean poiInfoBean : poiInfoBeans) {
                    mPoiInfoBeans.add(poiInfoBean);
                }

                if (!AssertValue.isNotNull(locationSelectAdapter)) {
                    locationSelectAdapter = new LocationSelectAdapter(LocationSelectActivity.this, mPoiInfoBeans);
                    locationSelectAdapter.setOnSelectListener(onSelectListener);
                    locationLV.setAdapter(locationSelectAdapter);
                } else {
                    locationSelectAdapter.notifyDataSetChanged();
                }

            } else {
                //没有结果
            }

            if (AssertValue.isNotNull(locationDialog)) {
                locationDialog.dismiss();
            }
        }
    };
}
