package com.yun9.mobile.msg.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yun9.mobile.R;
import com.yun9.mobile.camera.domain.DmImageItem;
import com.yun9.mobile.framework.base.activity.BaseFragmentActivity;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.Logger;
import com.yun9.mobile.framework.util.MsgcardLikeUtil;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.framework.view.CommentTableLayout;
import com.yun9.mobile.framework.view.TitleBarView;
import com.yun9.mobile.imageloader.fragment.ImageGridFragment;
import com.yun9.mobile.msg.action.ExtraMenuFactory;
import com.yun9.mobile.msg.adapter.CommentTabAdapter;
import com.yun9.mobile.msg.bean.MsgCardCommentBean;
import com.yun9.mobile.msg.model.MsgCardComment;
import com.yun9.mobile.msg.model.MyMsgCard;
import com.yun9.mobile.msg.model.MyMsgCardAttachment;
import com.yun9.mobile.msg.model.MyMsgCardRelationship;
import com.yun9.mobile.msg.view.MsgCardItemView.MsgCardEditCallback;
import com.yun9.mobile.msg.view.MsgCardView;

public class MsgCardActivity extends BaseFragmentActivity{

	private static final Logger logger = Logger
			.getLogger(MsgCardActivity.class);
	public static int NEW_COMMENT_ACTIVITY_REQUESE = 100;
	private static int DEFAULT_RANDOM_NUM = 101; // 0到100
	private static final String PARAM_MSG_CARD_ID = "msgcardid";
	private static final String PARAM_MSG_CARD_COMMENT_CONTEXT = "content";

	private MsgCardView msgCardView;
	private MyMsgCard msgCard;
	private TitleBarView titleBarView;
	private List<DmImageItem> imageItems;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private ImageView btnNewComment;
	private ImageView btnLike;
	private View rlLike;
	private ViewPager vpComment;
	private PagerSlidingTabStrip commentTabs;
	private CommentTabAdapter commentAdapter;
	private ViewGroup mPopView;
	private PopupWindow mPopupWindow;
	private LinearLayout llDice;
	private TextView tvDice;
	private LinearLayout like_linearlayout;
	private TextView likerPer;
	private static MsgCardEditCallback editCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_msg_card);
		msgCard = (MyMsgCard) this.getIntent().getSerializableExtra("msgcard");
		imageItems = this.getImageItems();
		super.onCreate(savedInstanceState);
		replaceImageFragment();
		loadLiker();
		loadDiceLayout(msgCard.getCommentlist());
		
		// 决定是否定位到评论界面
		String from  =  (String) getIntent().getSerializableExtra("from");
		scrollTo(from);
	}
	
	private void loadLiker() {
		like_linearlayout.setVisibility(View.GONE);
		if(AssertValue.isNotNullAndNotEmpty(msgCard.getPraiseusername()))
		{
			like_linearlayout.setVisibility(View.VISIBLE);
			String liker="";
			for (String like : msgCard.getPraiseusername()) {
				liker+=like+"，";
			}
			
			liker=liker.substring(0,liker.length()-1);
			likerPer.setText(liker);
		}
	}

	private void loadDiceLayout(List<MsgCardCommentBean> cList) {
		llDice.setVisibility(View.GONE);
		tvDice.setText("");
		if (cList == null) {
			return;
		}
		List<MsgCardCommentBean> cBeans = new ArrayList<MsgCardCommentBean>();
		for (MsgCardCommentBean bean : cList) {
			if (MsgCardComment.Type.DICE.equals(bean.getComment().getType())) {
				cBeans.add(bean);
			}
		}
		if (cBeans.size() > 0) {
			Collections.sort(cBeans, new Comparator<MsgCardCommentBean>() {
				@Override
				public int compare(MsgCardCommentBean lhs,
						MsgCardCommentBean rhs) {
					int l = 0;
					int r = 0;
					if (AssertValue.isNotNullAndNotEmpty(lhs.getComment().getContent())) {
						l = Integer.valueOf(lhs.getComment().getContent());
					}
					if (AssertValue.isNotNullAndNotEmpty(rhs.getComment().getContent())) {
						r = Integer.valueOf(rhs.getComment().getContent());
					}
					return l -r;
				}
			});
			llDice.setVisibility(View.VISIBLE);
			StringBuffer dicer = new StringBuffer();
			for (MsgCardCommentBean bean : cBeans) {
				dicer.append(bean.getUser().getName()).append(":")
				.append(bean.getComment().getContent()).append("、");
			}
			
			tvDice.setText(dicer.substring(0, dicer.length() -1));
		}
		
	}

	private void scrollTo(String from) {
		if (!AssertValue.isNotNullAndNotEmpty(from)) {
			return ;
		}
		if (from.equals("comment")) {
			if (msgCard.getCommentcount() <= 0) {
				Intent intent = new Intent(MsgCardActivity.this, NewCommentActivity.class);
				intent.putExtra(MyMsgCard.PARAMS_MSG_CARD, msgCard);
				startActivityForResult(intent,NEW_COMMENT_ACTIVITY_REQUESE);
			} else {
				 Runnable runnable = new Runnable() {  
					    @Override  
					    public void run() {  
					    	View commentView = mPullRefreshScrollView.findViewById(R.id.comment_tabs);
							mPullRefreshScrollView.scrollTo(0, commentView.getTop());
							mPullRefreshScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,mPullRefreshScrollView.getBottom() + commentView.getTop()));
					    }  
					}; 
				Handler handler = new Handler();
				handler.postDelayed(runnable, 200);
				mPullRefreshScrollView.setOnPullEventListener(new OnPullEventListener<ScrollView>() {
					@Override
					public void onPullEvent(
							PullToRefreshBase<ScrollView> refreshView, State state,
							Mode direction) {
						mPullRefreshScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
					}
				});
			}
		}
	}

	private void extraAction() {
		if (AssertValue.isNotNull(mPopView)) {
			return;
		}
		// TODO 硬编码，增加“更多”处理按钮
		final LinearLayout buttonBarGroup = (LinearLayout) findViewById(R.id.button_bar_group);
		ViewGroup btnDealLayout = (ViewGroup) LayoutInflater.from(this.context)
				.inflate(R.layout.bpm_menu_button, null);
		buttonBarGroup.addView(btnDealLayout);
		ImageButton btnDeal = (ImageButton) btnDealLayout
				.findViewById(R.id.btn_deal);

		mPopView = (ViewGroup) LayoutInflater.from(this.context)
				.inflate(R.layout.empty_popupmenu, null);
		appendDice(mPopView);
		mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		btnDeal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
						.parseColor("#b0000000")));
				mPopupWindow.showAtLocation(buttonBarGroup, Gravity.BOTTOM, 0,
						0);
				mPopupWindow.setAnimationStyle(R.style.main_pop);
				mPopupWindow.setOutsideTouchable(true);
				mPopupWindow.setFocusable(true);
				mPopupWindow.update();
			}
		});
		
		// TODO 如果不为用户创建的则显示处理面板
		if (!MyMsgCard.Source.USER.equals(msgCard.getMain().getSource())){
			// 额外的处理
			ExtraMenuFactory extraMenuFactory = BeanConfig.getInstance()
													.getBeanContext().get(ExtraMenuFactory.class);
			extraMenuFactory.addExtraMenu(msgCard, this);
		}
	}

	private void appendDice(ViewGroup popView) {
		TextView txtView = new TextView(context);
		LayoutParams txtAgreeParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, 80);
		txtAgreeParams.setMargins(25, 10, 25, 10);
		txtView.setLayoutParams(txtAgreeParams);
		txtView.setGravity(Gravity.CENTER);
		txtView.setBackgroundResource(R.drawable.pop_bg);
		txtView.setTextColor(context.getResources().getColor(R.color.blue));
		txtView.setTextSize(16);
		txtView.setText("掷骰子");
		txtView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dice();
				mPopupWindow.dismiss();
			}

			private void dice() {
				Resource resource = ResourceUtil.get("RandomNumberService");
				resource.param("t", "t");
				resource.invok(diceCallback);
			}
		});
		popView.addView(txtView);
	}
	
	private AsyncHttpResponseCallback diceCallback = new AsyncHttpResponseCallback() {
		@Override
		public void onSuccess(Response response) {
			final int diceNum = (Integer) response.getPayload();
			Resource sysMsgCardCommSaveRes = ResourceUtil.get("SysMsgCardCommentSaveService");
			SessionManager sessionManager = BeanConfig.getInstance()
					.getBeanContext().get(SessionManager.class);
			String userid = sessionManager.getAuthInfo().getUserinfo().getId();

			sysMsgCardCommSaveRes.header(Resource.HEADER.USERID, userid);
			sysMsgCardCommSaveRes.param(PARAM_MSG_CARD_COMMENT_CONTEXT, diceNum);
			sysMsgCardCommSaveRes.param("type", "dice");
			sysMsgCardCommSaveRes.param(PARAM_MSG_CARD_ID, msgCard.getMain().getId());

			sysMsgCardCommSaveRes.invok(new AsyncHttpResponseCallback() {
				@Override
				public void onSuccess(Response response) {
					TipsUtil.showToast("成功！骰子号："+diceNum, context);
					refresh();
				}
				
				@Override
				public void onFailure(Response response) {
					TipsUtil.showToast("掷骰子失败，请稍候重试！", context);
				}
			});
		}
		
		@Override
		public void onFailure(Response response) {
			
		}
	};

	private void replaceImageFragment() {
		String tag = ImageGridFragment.class.getSimpleName();
		Fragment fr = getSupportFragmentManager().findFragmentByTag(tag);
		if (fr == null) {
			fr = new ImageGridFragment(imageItems);
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.msgcard_image, fr, tag).commit();
	}
	
	@Override
	protected void initWidget() {
		msgCardView = (MsgCardView) findViewById(R.id.msgcard_detail);
		titleBarView = (TitleBarView) findViewById(R.id.title_bar_top);
		btnNewComment = (ImageView) findViewById(R.id.btn_newComment);
		btnLike = (ImageView) findViewById(R.id.btn_like);
		rlLike = findViewById(R.id.rl_like);
		vpComment = (ViewPager) findViewById(R.id.vp_comment);
		commentTabs = (PagerSlidingTabStrip) findViewById(R.id.comment_tabs);
		llDice = (LinearLayout) findViewById(R.id.ll_dice);
		tvDice = (TextView) findViewById(R.id.tvDicer);
		like_linearlayout=(LinearLayout) findViewById(R.id.like_linearlayout);
		likerPer = (TextView) findViewById(R.id.likerPer);
		titleBarView.getBtnReturn().setVisibility(View.VISIBLE);
		titleBarView.getTvTitle().setVisibility(View.VISIBLE);
		titleBarView.getTvTitle().setText(R.string.msgcard_title);

		titleBarView.getBtnReturn().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(context, MainActivity.class);
//				startActivity(intent);
				finish();
			}
		});

		msgCardView.load(msgCard);
		
		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.refresh_root);
		mPullRefreshScrollView.setOnRefreshListener(
				new OnRefreshListener2<ScrollView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						new RefreshTask().execute();
						View c = vpComment.getChildAt(vpComment.getCurrentItem());
						if (c instanceof CommentTableLayout) {
							((CommentTableLayout)c).refresh(new AsyncHttpResponseCallback(){
								@Override
								public void onSuccess(Response response) {
									commentAdapter.notifyDataSetChanged();
								}

								@Override
								public void onFailure(Response response) {
									commentAdapter.notifyDataSetChanged();
								}
								
							});
						}
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
						View c = vpComment.getChildAt(vpComment.getCurrentItem());
						if (c instanceof CommentTableLayout) {
							((CommentTableLayout)c).loadMore(new AsyncHttpResponseCallback(){
								@Override
								public void onSuccess(Response response) {
									commentAdapter.notifyDataSetChanged();
									commentTabs.notifyDataSetChanged();
									mPullRefreshScrollView.onRefreshComplete();
								}

								@Override
								public void onFailure(Response response) {
									commentAdapter.notifyDataSetChanged();
									commentTabs.notifyDataSetChanged();
									mPullRefreshScrollView.onRefreshComplete();
								}
								
							});
						}
					}
					
		});
		
		// 点赞
		rlLike.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AssertValue.isNotNull(msgCard.getRelationship()) && msgCard.getRelationship().isLike()) {
					MsgcardLikeUtil.unLike(msgCard.getMain().getId(), new AsyncHttpResponseCallback() {
						@Override
						public void onSuccess(Response response) {
							btnLike.setImageResource(R.drawable.like);
							msgCard.getRelationship().setLike(false);
							msgCard.setPraisecount(msgCard.getPraisecount() - 1);
							Toast.makeText(getApplicationContext(), "已取消点赞", Toast.LENGTH_SHORT).show();
						}
						@Override
						public void onFailure(Response response) {
							Toast.makeText(getApplicationContext(), response.getCause(), Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					if (!AssertValue.isNotNull(msgCard.getRelationship())) {
						msgCard.setRelationship(new MyMsgCardRelationship());
					}
					MsgcardLikeUtil.like(msgCard.getMain().getId(), new AsyncHttpResponseCallback() {
						@Override
						public void onSuccess(Response response) {
							btnLike.setImageResource(R.drawable.liked);
							msgCard.getRelationship().setLike(true);
							msgCard.setPraisecount(msgCard.getPraisecount() + 1);
							Toast.makeText(getApplicationContext(), "点赞成功！", Toast.LENGTH_SHORT).show();
						}
						
						@Override
						public void onFailure(Response response) {
							Toast.makeText(getApplicationContext(), response.getCause(), Toast.LENGTH_SHORT).show();
						}
					});
				}
				
			}
		});
	}
	
	private void initIvPraise() {
		if (AssertValue.isNotNull(msgCard.getRelationship()) && msgCard.getRelationship().isLike()) {
			btnLike.setImageResource(R.drawable.liked);
		} else {
			btnLike.setImageResource(R.drawable.like);
		}
	}

	private List<DmImageItem> getImageItems() {
		List<DmImageItem> imageItems = new ArrayList<DmImageItem>();

		if (AssertValue.isNotNullAndNotEmpty(this.msgCard.getAttachments())) {

			for (MyMsgCardAttachment msgCardAttachment : this.msgCard
					.getAttachments()) {
				DmImageItem ii = new DmImageItem();
				
				ii.setId(msgCardAttachment.getFileid());
				ii.setName(msgCardAttachment.getDesc());
				imageItems.add(ii);
			}
		}

		return imageItems;

	}

	@Override
	protected void bindEvent() {
		btnNewComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MsgCardActivity.this, NewCommentActivity.class);
				intent.putExtra(MyMsgCard.PARAMS_MSG_CARD, msgCard);
				startActivityForResult(intent,NEW_COMMENT_ACTIVITY_REQUESE);
			}
		});
		
		commentAdapter = new CommentTabAdapter(this,msgCard,commentTabs, vpComment);
		vpComment.setAdapter(commentAdapter);
		commentTabs.setViewPager(vpComment);
		commentTabs.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				commentAdapter.refresh(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// do nothing
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// do nothing
			}
		});
	}
	
	private class RefreshTask extends AsyncTask<String, Void, MyMsgCard> {
		
		@Override
		protected MyMsgCard doInBackground(String... params) {
			// TODO 这里重新获取界面内容
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(MyMsgCard result) {
			msgCardView.load(msgCard);
			mPullRefreshScrollView.onRefreshComplete();
			super.onPostExecute(result);
		}

	}

	public void refresh() {
		View c = vpComment.getChildAt(vpComment.getCurrentItem());
		if (c instanceof CommentTableLayout) {
			((CommentTableLayout)c).refresh(new AsyncHttpResponseCallback(){
				@SuppressWarnings("unchecked")
				@Override
				public void onSuccess(Response response) {
					List<MsgCardCommentBean> commentBeans = (List<MsgCardCommentBean>) response
							.getPayload();
					if (AssertValue.isNotNullAndNotEmpty(commentBeans)) {
						if (AssertValue.isNotNullAndNotEmpty(msgCard.getCommentlist())) { 
							commentBeans.addAll(msgCard.getCommentlist());
							msgCard.setCommentlist(commentBeans);
						}
						loadDiceLayout(commentBeans);
					}
					commentAdapter.notifyDataSetChanged();
				}

				@Override
				public void onFailure(Response response) {
					commentAdapter.notifyDataSetChanged();
				}
				
			});
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		loadMsgCard();
	}
	
	private void loadMsgCard() {
		Resource resource = ResourceUtil.get("SysMsgCardQueryMyCardByIdService");
		resource.param("id", msgCard.getMain().getId());
		resource.invok(new AsyncHttpResponseCallback() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Response response) {
				List<MyMsgCard> cards = (List<MyMsgCard>) response.getPayload();
				if (cards != null && cards.size() > 0) {
					msgCard = cards.get(0);
				}
				// 扩展功能
				extraAction();
				
				initIvPraise();
			}
			
			@Override
			public void onFailure(Response response) {
				
			}
		});
	}
	
	public ViewGroup getMenuViewGroup() {
		return mPopView;
	}
	
	public PopupWindow getPopupWindow() {
		return mPopupWindow;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(NewCommentActivity.NEW_COMMENT_ACTIVITY_RESULT==resultCode)  
        {
			refresh();
        }
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void finish() {
		super.finish();
		if (editCallback != null) {
			editCallback.done();
			editCallback = null;
		}
	}

	public static void setEditCallback(MsgCardEditCallback editCallback) {
		MsgCardActivity.editCallback = editCallback;
	}

}
