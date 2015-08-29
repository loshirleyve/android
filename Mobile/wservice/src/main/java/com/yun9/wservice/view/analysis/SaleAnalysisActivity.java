package com.yun9.wservice.view.analysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterListView;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/8/29.
 */
public class SaleAnalysisActivity extends JupiterFragmentActivity {

    @ViewInject(id= R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.category_lv)
    private JupiterListView categoryListView;

    public static void start(Context context) {
        Intent intent = new Intent(context, SaleAnalysisActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildView();
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleAnalysisActivity.this.finish();
            }
        });
        categoryListView.setAdapter(adapter);
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            JupiterRowStyleTitleLayout titleLayout;
            if (convertView == null){
                titleLayout = new JupiterRowStyleTitleLayout(SaleAnalysisActivity.this);
                titleLayout.getHotNitoceTV().setVisibility(View.GONE);
                titleLayout.getMainIV().setVisibility(View.GONE);
                titleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = (String) v.getTag();
                        SignBillAnalysisActivity.start(SaleAnalysisActivity.this);
                    }
                });
                convertView = titleLayout;
            } else {
                titleLayout = (JupiterRowStyleTitleLayout) convertView;
            }
            switch (position){
                case 0:
                    titleLayout.getTitleTV().setText(R.string.signbill_analysis);
                    titleLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SignBillAnalysisActivity.start(SaleAnalysisActivity.this);
                        }
                    });
                    break;
                case 1:
                    titleLayout.getTitleTV().setText(R.string.gathering_analysis);
                    titleLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GatheringAnalysisActivity.start(SaleAnalysisActivity.this);
                        }
                    });
                    break;
                case 2:
                    titleLayout.getTitleTV().setText(R.string.workorder_analysis);
                    titleLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WorkorderAnalysisActivity.start(SaleAnalysisActivity.this);
                        }
                    });
            }
            return convertView;
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_sale_analysis;
    }
}
