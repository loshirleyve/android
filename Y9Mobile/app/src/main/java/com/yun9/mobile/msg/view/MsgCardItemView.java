package com.yun9.mobile.msg.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.base.view.BaseRelativeLayout;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.DateUtil;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.image.activity.ImageViewPagerActivity;
import com.yun9.mobile.imageloader.MyImageLoader;
import com.yun9.mobile.imageloader.fragment.MyGridView;
import com.yun9.mobile.msg.activity.MsgCardActivity;
import com.yun9.mobile.msg.adapter.MsgCardImageAdapter;
import com.yun9.mobile.msg.adapter.MsgCommentListAdapter;
import com.yun9.mobile.msg.bean.MsgCardCommentBean;
import com.yun9.mobile.msg.model.MsgCardComment;
import com.yun9.mobile.msg.model.MyMsgCard;
import com.yun9.mobile.msg.model.MyMsgCardAction;
import com.yun9.mobile.msg.model.MyMsgCardAttachment;

public class MsgCardItemView extends BaseRelativeLayout {
	private Context mContext;

	private ImageView ivUserPicture;
	private TextView tvUserName;
	private TextView tvContent;
	private TextView tvTime;
	private TextView tvDevice;
	private TextView likerPer;
	private RelativeLayout rlMsgCardItem;
	private LinearLayout llLocation;
	private MyMsgCard msgCard;
	private LinearLayout like_linearlayout;
	private LinearLayout llDice;
	private TextView tvDice;
	private MyListView commonlistview;
	private MyGridView imagegridview;
	private TextView msgcardState;
	private List<String> imageItems;
	public MsgCardItemView(Context context,int screenWidth) {
		super(context);
		this.mContext = context;
		this.initView();

	}

	public MsgCardItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.msgcard_item, this);
		ivUserPicture = (ImageView) findViewById(R.id.msgcard_user_picture);
		tvUserName = (TextView) findViewById(R.id.msgcard_user_name);
		tvContent = (TextView) findViewById(R.id.msgcard_context);
		imagegridview=(MyGridView)findViewById(R.id.imagegridview);
		tvTime = (TextView) findViewById(R.id.msgcard_time);
		tvDevice = (TextView) findViewById(R.id.msgcard_device);
		rlMsgCardItem = (RelativeLayout) findViewById(R.id.msgcard_item);
		like_linearlayout=(LinearLayout) findViewById(R.id.like_linearlayout);
		likerPer = (TextView) findViewById(R.id.likerPer);
		commonlistview=(MyListView)findViewById(R.id.commonlist);
		msgcardState = (TextView) findViewById(R.id.msgcard_state);
		llDice = (LinearLayout) findViewById(R.id.ll_dice);
		tvDice = (TextView) findViewById(R.id.tvDicer);
		rlMsgCardItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MsgCardActivity.setEditCallback(new MsgCardEditCallback() {
					@Override
					public void done() {
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
								load(msgCard);
							}
							
							@Override
							public void onFailure(Response response) {
								TipsUtil.showToast("重新获取消息卡片信息失败！", getContext());
							}
						});
					}
				});
				Intent intent = new Intent(v.getContext(),
						MsgCardActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("msgcard", msgCard);
				intent.putExtras(bundle);
				v.getContext().startActivity(intent);

			}
		});
		llLocation =  (LinearLayout) findViewById(R.id.ll_location);
	}
	

	public void load(MyMsgCard mc) {
		this.msgCard = mc;
		imageItems = this.getImageItems();
		tvUserName.setText(msgCard.getFormuser().getName());
		tvContent.setText(new SpannableStringUtil().fromMsgCardContent(msgCard.getMain().getContent(),tvContent));
		tvTime.setText(DateUtil.getDateStr(msgCard.getMain().getCreatedate()));
		tvDevice.setText(msgCard.getMain().getDevicename());
		if (AssertValue.isNotNullAndNotEmpty(msgCard.getFormuser().getHeaderfileid())) {
			ivUserPicture.setImageResource(R.drawable.ic_launcher);
			MyImageLoader.getInstance().displayImage(msgCard.getFormuser().getHeaderfileid(), ivUserPicture);
		} else {
			ivUserPicture.setImageResource(R.drawable.ic_launcher);
		}
		
		imagegridview.setAdapter(new MsgCardImageAdapter(mContext,screenWidth(),imageItems));//调用ImageAdapter.java   
		imagegridview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mContext, ImageViewPagerActivity.class);
				intent.putStringArrayListExtra("imagelist", (ArrayList<String>) getImageItems());
				intent.putExtra("currentItem",position );
				mContext.startActivity(intent);
			}
		
		});
		// 处理地理位置
		if (AssertValue.isNotNullAndNotEmpty(msgCard.getMain().getLocationlabel())) {
			TextView txtLocation = (TextView) llLocation.findViewById(R.id.txt_location);
			txtLocation.setText(msgCard.getMain().getLocationlabel());
			llLocation.setVisibility(View.VISIBLE);
		}
		like_linearlayout.setVisibility(View.GONE);
		commonlistview.setVisibility(View.GONE);
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
		if(AssertValue.isNotNullAndNotEmpty(msgCard.getCommentlist()))
		{
			commonlistview.setVisibility(View.VISIBLE);
			MsgCommentListAdapter commentadapter=new MsgCommentListAdapter(mContext,msgCard.getCommentlist());
			commonlistview.setAdapter(commentadapter);
		}
		
		if (MyMsgCardAction.State.PENDING.equals(msgCard.getRelationship().getState())) {
			msgcardState.setVisibility(View.VISIBLE);
		} else {
			msgcardState.setVisibility(View.GONE);
		}
		
		loadDiceLayout(msgCard.getCommentlist());
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


	public ImageView getIvUserPicture() {
		return ivUserPicture;
	}

	public TextView getTvUserName() {
		return tvUserName;
	}

	public TextView getTvContent() {
		return tvContent;
	}

	public TextView getTvTime() {
		return tvTime;
	}

	public TextView getTvDevice() {
		return tvDevice;
	}

	public MyMsgCard getMsgCard() {
		return msgCard;
	}

	public RelativeLayout getRlMsgCardItem() {
		return rlMsgCardItem;
	}
	private List<String> getImageItems() {
		List<String> imageItems = new ArrayList<String>();

		if (AssertValue.isNotNullAndNotEmpty(this.msgCard.getAttachments())) {

			for (MyMsgCardAttachment msgCardAttachment : this.msgCard
					.getAttachments()) {
				imageItems.add(msgCardAttachment.getFileid());
			}
		}

		return imageItems;
	}

	// 获取屏幕的高度
	private int screenWidth() {
		DisplayMetrics dm = mContext.getResources()
				.getDisplayMetrics();
		return dm.widthPixels;
	}
	
	public interface MsgCardEditCallback {
		void done();
	}
}