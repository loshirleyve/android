package com.yun9.mobile.framework.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.LoadMoreable;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.msg.bean.MsgCardCommentBean;
import com.yun9.mobile.msg.model.MsgCardComment;
import com.yun9.mobile.msg.view.MsgCardCommentItemView;

public class CommentTableLayout extends TableLayout implements
		LoadMoreable<List<MsgCardComment>> {

	private static final String LIMITROW = "20";
	private static final String PARAM_MSG_CARD_ID = "msgcardid";
	private  float PER_ROW_H = 73;
	private String lastupid = "0";
	private String lastdownid;
	private View progressing;
	private String msgCardId;
	private float h;

	public CommentTableLayout(Context context, String msgCardId) {
		super(context);
		progressing = ((Activity) context).findViewById(R.id.progressing);
		this.msgCardId = msgCardId;
	}

	public CommentTableLayout(Context context, AttributeSet attrs,
			String msgCardId) {
		super(context, attrs);
		progressing = ((Activity) context).findViewById(R.id.progressing);
		this.msgCardId = msgCardId;
	}

	@Override
	public void loadMore(final AsyncHttpResponseCallback callback) {
		this.loadMoreData(new AsyncHttpResponseCallback() {

			@Override
			public void onSuccess(Response response) {
				@SuppressWarnings("unchecked")
				List<MsgCardCommentBean> commentBeans = (List<MsgCardCommentBean>) response
						.getPayload();
				MsgCardCommentItemView comment = null;
				if (AssertValue.isNotNullAndNotEmpty(commentBeans)) {
					String tempLastdownid = Long.MAX_VALUE + "";
					for (MsgCardCommentBean c : commentBeans) {
						if (Long.valueOf(tempLastdownid) > Long.valueOf(c
								.getComment().getId())) {
							tempLastdownid = c.getComment().getId();
						}
						comment = new MsgCardCommentItemView(
								CommentTableLayout.this.getContext());
						comment.load(c);
						CommentTableLayout.this.addView(comment);
						h +=PER_ROW_H;
					}
					lastdownid = tempLastdownid;
				}
				callback.onSuccess(response);
			}

			@Override
			public void onFailure(Response response) {
				Toast.makeText(CommentTableLayout.this.getContext(),
						"加载评论失败，请稍候重试！", Toast.LENGTH_LONG).show();
				callback.onFailure(response);
			}
		});
	}

	private void loadMoreData(AsyncHttpResponseCallback callback) {

		Resource sysMsgCardCommQueryRes = ResourceUtil
				.get("SysMsgCardCommentQueryService");

		SessionManager sessionManager = BeanConfig.getInstance()
				.getBeanContext().get(SessionManager.class);

		String userid = sessionManager.getAuthInfo().getUserinfo().getId();

		sysMsgCardCommQueryRes.header(Resource.HEADER.USERID, userid);

		sysMsgCardCommQueryRes.header(Resource.HEADER.LIMITROW, LIMITROW);

		sysMsgCardCommQueryRes.header(Resource.HEADER.LASTDOWNID, lastdownid);

		sysMsgCardCommQueryRes.param(PARAM_MSG_CARD_ID, msgCardId);

		sysMsgCardCommQueryRes.invok(callback);
	}

	public void refresh(final AsyncHttpResponseCallback callback) {
		this.refreshData(new AsyncHttpResponseCallback() {

			@Override
			public void onSuccess(Response response) {
				progressing.setVisibility(View.GONE);
				@SuppressWarnings("unchecked")
				List<MsgCardCommentBean> commentBeans = (List<MsgCardCommentBean>) response
						.getPayload();
				if (AssertValue.isNotNullAndNotEmpty(commentBeans)) {
					MsgCardCommentBean c = null;
					String tempLastdownid = Long.MAX_VALUE + "";
					for (int i = commentBeans.size() - 1; i > -1; i--) {
						c = commentBeans.get(i);
						if (Long.valueOf(lastupid) < Long.valueOf(c
								.getComment().getId())) {
							lastupid = c.getComment().getId();
						}
						if (Long.valueOf(tempLastdownid) > Long.valueOf(c
								.getComment().getId())) {
							tempLastdownid = c.getComment().getId();
						}
						final MsgCardCommentItemView comment = new MsgCardCommentItemView(
								CommentTableLayout.this.getContext());
						comment.load(c);
						CommentTableLayout.this.addView(comment, 0);
						comment.post(new Runnable() {
							
							@Override
							public void run() {
								h +=PER_ROW_H + (comment.getTvComment().getLineCount() -1 )*20;	// 20为每行的高度，TODO 临时方案
							}
						});
					}
					if (lastdownid == null) {
						lastdownid = tempLastdownid;
					}
				}
				if (AssertValue.isNotNull(callback)) {
					callback.onSuccess(response);
				}
			}

			@Override
			public void onFailure(Response response) {
				progressing.setVisibility(View.GONE);
				Toast.makeText(CommentTableLayout.this.getContext(),
						"加载评论失败，请稍候重试！", Toast.LENGTH_LONG).show();
				if (AssertValue.isNotNull(callback)) {
					callback.onFailure(response);
				}
			}
		});
	}

	private void refreshData(AsyncHttpResponseCallback callback) {

		Resource sysMsgCardCommQueryRes = ResourceUtil
				.get("SysMsgCardCommentQueryService");

		SessionManager sessionManager = BeanConfig.getInstance()
				.getBeanContext().get(SessionManager.class);

		String userid = sessionManager.getAuthInfo().getUserinfo().getId();

		sysMsgCardCommQueryRes.header(Resource.HEADER.USERID, userid);

		sysMsgCardCommQueryRes.header(Resource.HEADER.LIMITROW, LIMITROW);

		sysMsgCardCommQueryRes.header(Resource.HEADER.LASTUPID, lastupid);

		sysMsgCardCommQueryRes.param(PARAM_MSG_CARD_ID, msgCardId);

		progressing.setVisibility(View.VISIBLE);
		sysMsgCardCommQueryRes.invok(callback);
	}

	public float getH() {
		return h;
	}
}
