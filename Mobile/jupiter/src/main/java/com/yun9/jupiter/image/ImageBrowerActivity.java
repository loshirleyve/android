package com.yun9.jupiter.image;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yun9.jupiter.R;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;

/**
 * Created by Leon on 15/6/15.
 */
public class ImageBrowerActivity extends JupiterFragmentActivity {

    private HackyViewPager mViewPager;

    private ImageBrowerCommand command;

    public static void start(Activity activity, ImageBrowerCommand command) {
        Intent intent = new Intent(activity, ImageBrowerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        command = (ImageBrowerCommand) this.getIntent().getSerializableExtra("command");

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getImageBeans())) {
            ImageBrowerPagerAdapter imageBrowerPagerAdapter = new ImageBrowerPagerAdapter(getApplicationContext(), command.getImageBeans());
            mViewPager.setAdapter(imageBrowerPagerAdapter);
        }

        if (AssertValue.isNotNull(command) && command.getPosition()>0){
            mViewPager.setCurrentItem(command.getPosition());
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_view_pager;
    }
}
