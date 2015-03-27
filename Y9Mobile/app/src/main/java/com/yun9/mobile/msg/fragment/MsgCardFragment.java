package com.yun9.mobile.msg.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.base.activity.BaseFragment;
import com.yun9.mobile.framework.view.MsgCardTitleBarView;

public class MsgCardFragment extends BaseFragment {

	private View mBaseView;

	private MsgCardTitleBarView mTitleBarView;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.mBaseView = inflater.inflate(R.layout.fragment_msgcard, null);
		this.mTitleBarView = (MsgCardTitleBarView) mBaseView
				.findViewById(R.id.title_bar_top);
		return mBaseView;
	}

	@Override
	protected void initWidget() {

		this.mTitleBarView.getTvTitle().setText(R.string.index_page);
		this.mTitleBarView.getTvTitle().setVisibility(View.VISIBLE);
	}

	@Override
	protected void bindEvent() {
		this.initList();
	}

	private void initList() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		MsgCardListFragment msgCardListFragment = new MsgCardListFragment();
		ft.replace(R.id.child_fragment, msgCardListFragment,
				MsgCardFragment.class.getName());
		// ft.addToBackStack(TAG);
		ft.commit();
	}

}
