package com.yun9.mobile.position.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommunityPositionFragment extends PositionFragment {
	protected static final String TAG = CommunityPositionFragment.class.getSimpleName();

	private String SEARCH_KEY_DEFAULT = "小区";

	@Override
	public String getSearKey() {

		return SEARCH_KEY_DEFAULT;
	}
}
