package com.yun9.wservice.view.inst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by li on 2015/7/9.
 */
public class StaffNumActivity extends JupiterFragmentActivity {
    @ViewInject(id = R.id.finishTv)
    private TextView finishTv;
    @ViewInject(id = R.id.staffNumTitle)
    private JupiterTitleBarLayout staffNumTitle;
    @ViewInject(id = R.id.staff_num_category1)
    private JupiterRowStyleSutitleLayout staffNumCatOneLayout;
    @ViewInject(id = R.id.staff_num_category2)
    private JupiterRowStyleSutitleLayout staffNumCatTwoLayout;
    @ViewInject(id = R.id.staff_num_category3)
    private JupiterRowStyleSutitleLayout staffNumCatThrLayout;
    @ViewInject(id = R.id.staff_num_category4)
    private JupiterRowStyleSutitleLayout staffNumCatFouLayout;

    public static void start(Activity activity, StaffNumCommand command){
        Intent intent = new Intent(activity, StaffNumActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StaffNumCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String value = getResources().getString(R.string.select_num_max,1);
        staffNumTitle.getTitleRightTv().setText(value);
        staffNumTitle.getTitleLeftIV().setOnClickListener(onBackClickListener);
        staffNumCatOneLayout.getSelectModeIV().setImageResource(R.drawable.selector_empty);
        staffNumCatOneLayout.getSelectModeIV().setVisibility(View.VISIBLE);
        staffNumCatTwoLayout.getSelectModeIV().setImageResource(R.drawable.selector_empty);
        staffNumCatTwoLayout.getSelectModeIV().setVisibility(View.VISIBLE);
        staffNumCatThrLayout.getSelectModeIV().setImageResource(R.drawable.selector_empty);
        staffNumCatThrLayout.getSelectModeIV().setVisibility(View.VISIBLE);
        staffNumCatFouLayout.getSelectModeIV().setImageResource(R.drawable.selector_empty);
        staffNumCatFouLayout.getSelectModeIV().setVisibility(View.VISIBLE);

        staffNumCatOneLayout.setOnClickListener(new OnStaffNumCatItemClickListener());
        staffNumCatTwoLayout.setOnClickListener(new OnStaffNumCatItemClickListener());
        staffNumCatThrLayout.setOnClickListener(new OnStaffNumCatItemClickListener());
        staffNumCatFouLayout.setOnClickListener(new OnStaffNumCatItemClickListener());

        //finishTv.setOnClickListener(onFinishClickListener);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_staff_num;
    }

    private class OnStaffNumCatItemClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()){
                case R.id.staff_num_category1:
                    staffNumCatOneLayout.getSelectModeIV().setImageResource(R.drawable.selector);
                    intent.putExtra(StaffNumCommand.STAFF_NUM_CATEGORY, "miniature");
                    setResult(StaffNumCommand.RESULT_CODE_OK, intent);
                    finish();
                    break;
                case R.id.staff_num_category2:
                    staffNumCatTwoLayout.getSelectModeIV().setImageResource(R.drawable.selector);
                    intent.putExtra(StaffNumCommand.STAFF_NUM_CATEGORY, "small");
                    setResult(StaffNumCommand.RESULT_CODE_OK, intent);
                    finish();
                    break;
                case R.id.staff_num_category3:
                    staffNumCatThrLayout.getSelectModeIV().setImageResource(R.drawable.selector);
                    intent.putExtra(StaffNumCommand.STAFF_NUM_CATEGORY, "medium");
                    setResult(StaffNumCommand.RESULT_CODE_OK, intent);
                    finish();
                    break;
                case R.id.staff_num_category4:
                    staffNumCatFouLayout.getSelectModeIV().setImageResource(R.drawable.selector);
                    intent.putExtra(StaffNumCommand.STAFF_NUM_CATEGORY, "large");
                    setResult(StaffNumCommand.RESULT_CODE_OK, intent);
                    finish();
                    break;
                //default:
                    //finishTv.setVisibility(View.VISIBLE);
            }
        }
    };

    private View.OnClickListener onFinishClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
