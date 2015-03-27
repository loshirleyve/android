package com.yun9.mobile.position.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BusinessPositionFragment extends PositionFragment {
	protected static final String TAG = BusinessPositionFragment.class.getSimpleName();
	private String SEARCH_KEY_DEFAULT= "商店";

	@Override
	public String getSearKey() {
		return SEARCH_KEY_DEFAULT;
	}
}
