package com.yun9.mobile.calendar.activity;

import com.yun9.mobile.R;
import com.yun9.mobile.calendar.fragment.SelectShiftFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * 选择班次
 * @author Kass
 *
 */
public class SelectShiftActivity extends Activity {
	
	private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private SelectShiftFragment mSelectShiftInfoFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame);
		
		Bundle bundle = getIntent().getExtras();
        mManager = getFragmentManager();
        mTransaction = mManager.beginTransaction();
        mSelectShiftInfoFragment = new SelectShiftFragment();
        mSelectShiftInfoFragment.setArguments(bundle);
        mTransaction.replace(R.id.frame_Schedule_FL, mSelectShiftInfoFragment).commit();
	}

}
