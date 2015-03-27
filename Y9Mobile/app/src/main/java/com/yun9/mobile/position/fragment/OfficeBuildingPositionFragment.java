package com.yun9.mobile.position.fragment;

import android.os.Bundle;
import android.util.Log;

public class OfficeBuildingPositionFragment extends PositionFragment {
	protected static final String TAG = OfficeBuildingPositionFragment.class.getSimpleName();
	private String SEARCH_KEY_DEFAULT = "写字楼";
	@Override
	public String getSearKey() {
		return SEARCH_KEY_DEFAULT;
	}
}
