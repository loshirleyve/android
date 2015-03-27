package com.yun9.mobile.framework.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.base.view.BaseRelativeLayout;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.Topic;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.msg.adapter.GroupAdapter;
import com.yun9.mobile.msg.adapter.MsgCardQueryGroup;
import com.yun9.mobile.msg.fragment.MsgCardListFragment;

public class MsgCardTitleBarView extends BaseRelativeLayout{
	
	private static final String LIMITROW = "5";

	private Context mContext;
	private ImageButton btnLeft;
	private ImageButton btnFuncNav;
	private ImageButton btnReturn;
	private TextView tvTitle;
	private PopupWindow mPopupWindow;
	private View popupContentView;
	private ListView groupListView;
	private ListView topicListView;
	private GroupAdapter groupAdapter;
	private GroupAdapter topicGroupAdapter;
	private ArrayList<MsgCardQueryGroup> groups;
	private ArrayList<MsgCardQueryGroup> topicGroups;

	public MsgCardTitleBarView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public MsgCardTitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.title_bar, this);
		btnLeft = (ImageButton) findViewById(R.id.title_btn_left);
		btnReturn = (ImageButton) findViewById(R.id.title_btn_return);
        btnFuncNav = (ImageButton) findViewById(R.id.title_btn_funcnav);
		tvTitle = (TextView) findViewById(R.id.title_txt);
		tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
		bindEvn();

	}
	
	private void bindEvn() {
		tvTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPopupWindow == null) {
					LayoutInflater mLayoutInflater = LayoutInflater.from(MsgCardTitleBarView.this.mContext.getApplicationContext());
					popupContentView = mLayoutInflater.inflate(R.layout.msg_card_group_list, null);

					groupListView = (ListView) popupContentView.findViewById(R.id.lv_group);
					topicListView = (ListView) popupContentView.findViewById(R.id.topic_group);

					groups = new ArrayList<MsgCardQueryGroup>();
					
					// 首页
					groups.add(new MsgCardQueryGroup(mContext.getResources().getString(R.string.index_page), null));
					// 待处理
					groups.add(new MsgCardQueryGroup(mContext.getResources().getString(R.string.pending), MsgCardQueryGroup.Group.PENDING));
					// 我的动态
					groups.add(new MsgCardQueryGroup(mContext.getResources().getString(R.string.frome_me), MsgCardQueryGroup.Group.FROMME));
					// 发送给我的
					groups.add(new MsgCardQueryGroup(mContext.getResources().getString(R.string.sendtome), MsgCardQueryGroup.Group.SENDTOME));
					// 我评论的
					groups.add(new MsgCardQueryGroup(mContext.getResources().getString(R.string.commented), MsgCardQueryGroup.Group.COMMENT));

					groupAdapter = new GroupAdapter(MsgCardTitleBarView.this.mContext, groups);
					groupListView.setAdapter(groupAdapter);
					loadTopic();

					mPopupWindow = new PopupWindow(popupContentView,MsgCardTitleBarView.this.getRight()/2 ,LayoutParams.WRAP_CONTENT, true);
				}
				mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
				mPopupWindow.setOutsideTouchable(true);
				int xPos = mPopupWindow.getWidth() / 3;

				mPopupWindow.showAsDropDown(v, -xPos, 0);

				groupListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						tvTitle.setText(groups.get(position).getLabel());
						if (MsgCardListFragment.presenter != null) {
							MsgCardListFragment.presenter.reload(groups.get(position).getQueryGroup(), null);
						}
						if (mPopupWindow != null) {
							mPopupWindow.dismiss();
						}
					}
				});
			}

		});
		
	}
	
	private void loadTopic() {
		Resource resource = ResourceUtil.get("SysMsgTopicQueryService");
		resource.header(Resource.HEADER.LIMITROW, LIMITROW);
		resource.param(Resource.HEADER.LIMITROW, LIMITROW);// TODO 无意义的参数
		resource.invok(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<Topic> topics = (List<Topic>) response.getPayload();
				topicGroups = new ArrayList<MsgCardQueryGroup>();
				if (AssertValue.isNotNull(topics)) {
					for (Topic top : topics) {
						topicGroups.add(new MsgCardQueryGroup(top.getName(), null, top.getName()));
					}
				}
				topicGroupAdapter = new GroupAdapter(MsgCardTitleBarView.this.mContext, topicGroups);
				topicListView.setAdapter(topicGroupAdapter);
				topicListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						tvTitle.setText(topicGroups.get(position).getLabel());
						if (MsgCardListFragment.presenter != null) {
							MsgCardListFragment.presenter.reload(topicGroups.get(position).getContent(), "#"+topicGroups.get(position).getContent()+"#");
						}
						if (mPopupWindow != null) {
							mPopupWindow.dismiss();
						}
					}
				});
				
			}
			
			@Override
			public void onFailure(Response response) {
				TipsUtil.showToast(response.getCause(), mContext);
			}
		});
	}

	public ImageButton getBtnLeft() {
		return btnLeft;
	}

	public TextView getTvTitle() {
		return tvTitle;
	}

	public ImageButton getBtnFuncNav() {
		return btnFuncNav;
	}

	public ImageButton getBtnReturn() {
		return btnReturn;
	}

	public void dismiss(View v) {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
	}
	
}
