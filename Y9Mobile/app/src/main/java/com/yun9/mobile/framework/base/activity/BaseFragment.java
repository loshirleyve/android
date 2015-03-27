package com.yun9.mobile.framework.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	protected Context mContext;

	protected abstract View initView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	protected abstract void initWidget();

	protected abstract void bindEvent();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this.getActivity();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = this.initView(inflater, container, savedInstanceState);

		this.initWidget();

		this.bindEvent();
		return view;
	}
}
