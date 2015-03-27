package com.yun9.mobile.framework.image;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.domain.DmImageItem;
import com.yun9.mobile.framework.base.activity.BaseFragment;

public class YnViewPagerFragment extends BaseFragment {

	private View mBaseView;

	private YnViewPager ynViewPager;

	private List<DmImageItem> imageItems;

	public YnViewPagerFragment(List<DmImageItem> imageItems) {
		this.imageItems = imageItems;
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mBaseView = inflater.inflate(R.layout.yn_view_pager_fragment, null);
		return this.mBaseView;
	}

	@Override
	protected void initWidget() {
		ynViewPager = (YnViewPager) mBaseView.findViewById(R.id.view_pager);

		YnViewPagerAdapter adapter = new YnViewPagerAdapter(this.mContext,
				this.imageItems);
		ynViewPager.setAdapter(adapter);
	}

	@Override
	protected void bindEvent() {

	}

}
