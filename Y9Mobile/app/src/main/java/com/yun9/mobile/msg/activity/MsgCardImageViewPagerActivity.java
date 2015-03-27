package com.yun9.mobile.msg.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yun9.mobile.R;
import com.yun9.mobile.camera.domain.DmImageItem;
import com.yun9.mobile.framework.activity.MainActivity;
import com.yun9.mobile.framework.base.activity.BaseFragmentActivity;
import com.yun9.mobile.framework.image.YnViewPagerFragment;
import com.yun9.mobile.framework.model.ImageItems;

public class MsgCardImageViewPagerActivity extends BaseFragmentActivity {
	private List<DmImageItem> imageItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_msg_card_image_viewpager);
		ImageItems imageItems = (ImageItems) this.getIntent()
				.getSerializableExtra("imageItems");
		this.imageItems = imageItems.getImageItems();
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void initWidget() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		YnViewPagerFragment ynViewPagerFragment = new YnViewPagerFragment(
				imageItems);
		ft.replace(R.id.fl_content, ynViewPagerFragment,
				MainActivity.class.getName());
		ft.commit();
	}

	@Override
	protected void bindEvent() {

	}

}
