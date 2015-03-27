package com.yun9.mobile.calendar.activity;

import com.yun9.mobile.R;
import com.yun9.mobile.calendar.fragment.ScheduleFragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * 我的排班
 * Created by Kass on 2014/12/9.
 */
public class ScheduleActivity extends Activity {
	
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private ScheduleFragment mScheduleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        
        Bundle bundle = getIntent().getExtras();
        mManager = getFragmentManager();
        mTransaction = mManager.beginTransaction();
        mScheduleFragment = new ScheduleFragment();
        mScheduleFragment.setArguments(bundle);
        mTransaction.replace(R.id.frame_Schedule_FL, mScheduleFragment).commit();
    }
}
