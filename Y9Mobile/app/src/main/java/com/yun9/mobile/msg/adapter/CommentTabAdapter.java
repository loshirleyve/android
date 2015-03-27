package com.yun9.mobile.msg.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

import com.astuetz.PagerSlidingTabStrip;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.framework.view.CommentTableLayout;
import com.yun9.mobile.msg.model.BpmNodeInfo;
import com.yun9.mobile.msg.model.MyMsgCard;

public class CommentTabAdapter extends PagerAdapter {

	private Activity activity;
	private MyMsgCard msgCard;
	private PagerSlidingTabStrip commentTabs;
	private ViewPager vpComment;

	private List<CommentTableLayout> ctls;
	private List<String> titles;

	public CommentTabAdapter(Activity activity, MyMsgCard msgCard, PagerSlidingTabStrip commentTabs, ViewPager vpComment) {
		this.activity = activity;
		this.msgCard = msgCard;
		this.commentTabs = commentTabs;
		this.vpComment = vpComment;
		init();
	}

	private void init() {
		ctls = new ArrayList<CommentTableLayout>();
		ctls.add(new CommentTableLayout(this.activity, msgCard.getMain().getId()));
		titles = new ArrayList<String>();
		titles.add("评论");
		//loadMoreTabs(); 方案改变，不需要获取这个了
	}

	private void loadMoreTabs() {
		if (!AssertValue.isNotNullAndNotEmpty(msgCard.getMain().getSourceid())) {
			return;
		}
		Resource resource = ResourceUtil.get("BpmMsgCardRefService");
		resource.param("instid", msgCard.getMain().getSourceid());
		resource.invok(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				@SuppressWarnings("unchecked")
				List<BpmNodeInfo> nodes = (List<BpmNodeInfo>) response.getPayload();
				if (AssertValue.isNotNullAndNotEmpty(nodes)) {
					for (BpmNodeInfo node : nodes) {
						if (!msgCard.getMain().getId().equals(node.getMcId())) {
							ctls.add(new CommentTableLayout(activity, node.getMcId()));
							titles.add(node.getNodeName());
						}
					}
				}
				notifyDataSetChanged();
				commentTabs.notifyDataSetChanged();
			}
			
			@Override
			public void onFailure(Response response) {
				TipsUtil.showToast("获取流程实例节点列表失败！", activity);
			}
		});
	}

	@Override
	public int getCount() {
		return ctls.size();
	}

	@Override
	public boolean isViewFromObject(View v, Object o) {
		return v == ((View) o);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}

	@Override
	public Object instantiateItem(final ViewGroup container, int position) {
		final CommentTableLayout c = ctls.get(position);
		container.addView(c);
		if (vpComment.getCurrentItem() == position) {
			refresh(position);
		}
		return c;
	}
	
	public void refresh(int position) {
		CommentTableLayout c = ctls.get(position);
		c.refresh(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				notifyDataSetChanged();
			}
			
			@Override
			public void onFailure(Response response) {
				notifyDataSetChanged();
			}
		});
	
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object view) {
		container.removeView((View) view);
	}
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		final CommentTableLayout c = ctls.get(vpComment.getCurrentItem());
		c.post(new Runnable() {
			
			@Override
			public void run() {
				int h = dip2px(activity, c.getH());
				h = (h > 500)?h:500;
				vpComment.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, h));
			}
		});
	}
	
	/**
     * 将dip转换为px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
    }

}
