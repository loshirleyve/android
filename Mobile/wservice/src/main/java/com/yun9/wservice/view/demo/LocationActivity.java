package com.yun9.wservice.view.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.location.LocationBean;
import com.yun9.jupiter.location.LocationFactory;
import com.yun9.jupiter.location.OnGetPoiInfoListener;
import com.yun9.jupiter.location.OnLocationListener;
import com.yun9.jupiter.location.PoiInfoBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.List;

/**
 * Created by Leon on 15/5/28.
 */
public class LocationActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.locationinfo)
    private TextView locationInfo;

    @ViewInject(id = R.id.poiinfo)
    private TextView poiinfo;

    @ViewInject(id = R.id.start)
    private Button startBtn;

    @ViewInject(id = R.id.stop)
    private Button stopBtn;

    @ViewInject(id = R.id.requestlocation)
    private Button requestBtn;

    @ViewInject(id = R.id.poisearch)
    private Button poiSearch;

    @ViewInject(id = R.id.keyword)
    private EditText poiKeyword;

    @ViewInject(id = R.id.radius)
    private EditText poiRadius;

    @ViewInject(id = R.id.pageNum)
    private EditText pageNumET;

    private LocationBean lastLocationBean;

    private int requestNum = 0;

    private static final Logger logger = Logger.getLogger(LocationActivity.class);


    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, LocationActivity.class);
        if (AssertValue.isNotNull(bundle)) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

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
    protected int getContentView() {
        return R.layout.activity_demo_location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
        locationFactory.setOnLocationListener(onLocationListener);
        locationFactory.setOnGetPoiInfoListener(onGetPoiInfoListener);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
                locationFactory.start();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
                locationFactory.stop();
            }
        });

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
                locationFactory.start();
                locationFactory.requestLocation();
            }
        });

        poiSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
                int radius = Integer.valueOf(poiRadius.getText().toString());
                int pageNum = Integer.valueOf(pageNumET.getText().toString());

                locationFactory.poiSearch(lastLocationBean, poiKeyword.getText().toString(), pageNum, radius);
            }
        });
    }

    private OnLocationListener onLocationListener = new OnLocationListener() {
        @Override
        public void onReceiveLocation(LocationBean locationBean) {
            locationInfo.setText(locationBean.toString());
            requestNum++;
            titleBarLayout.getTitleSutitleTv().setText("请求次数：" + requestNum);
            lastLocationBean = locationBean;
        }
    };

    private OnGetPoiInfoListener onGetPoiInfoListener = new OnGetPoiInfoListener() {
        @Override
        public void onGetPoiInfo(List<PoiInfoBean> poiInfoBeans) {
            if (AssertValue.isNotNullAndNotEmpty(poiInfoBeans)) {
                StringBuffer sb = new StringBuffer();

                sb.append("共检索出" + poiInfoBeans.size() + "个结果！");
                sb.append("\n");
                for (PoiInfoBean poiInfoBean : poiInfoBeans) {
                    sb.append(poiInfoBean.toString());
                    sb.append("\n");
                    sb.append("*************************");
                    sb.append("\n");
                }
                poiinfo.setText(sb.toString());
            } else {
                poiinfo.setText("没有搜索到结果！");
            }
        }
    };
}
