package com.yun9.mobile.msg.action;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.Logger;
import com.yun9.mobile.msg.action.entity.ActionContext;
import com.yun9.mobile.msg.action.entity.ActionParams;
import com.yun9.mobile.msg.action.invoke.ActionInvokeFactory;
import com.yun9.mobile.msg.activity.MsgCardActivity;
import com.yun9.mobile.msg.model.MyMsgCard;
import com.yun9.mobile.msg.model.MyMsgCardAction;

/**
 * BPMX5额外功能处理
 * 
 * @author yun9
 * 
 */
public class BpmExtraMenuHandler implements ExtraMenuHandler {

	private static final Logger logger = Logger
			.getLogger(BpmExtraMenuHandler.class);

	private MyMsgCard msgCard;
	private Activity context;
	private PopupWindow mPopupWindow;
	private TextView aduditTv;
	private ActionInvokeFactory actionInvokeFactory;

	@Override
	public void handle(MyMsgCard cardInfo, Activity activity) {
		this.msgCard = cardInfo;
		this.context = activity;
		actionInvokeFactory = BeanConfig.getInstance().getBeanContext()
				.get(ActionInvokeFactory.class);

		// 添加“处理”按钮
		if (hasAuth()) {
			addDealButton();
		} else if (aduditTv != null) {
			((MsgCardActivity)context).getMenuViewGroup().removeView(aduditTv);
		}
	}

	/**
	 * 验证当前用户是否有权限看到这些按钮
	 * @return
	 */
	private boolean hasAuth() {
		SessionManager session = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
		String userid = session.getAuthInfo().getUserinfo().getId();
		if (AssertValue.isNotNullAndNotEmpty(getMsgCardAction())) {
			for (MyMsgCardAction action : getMsgCardAction()) {
				if (AssertValue.isNotNullAndNotEmpty(action.getActors())
						&& action.getActors().indexOf(userid+",") != -1
						&& MyMsgCardAction.State.PENDING.equals(action.getState())) {
					return true;
				}
			}
		}
		return false;
	}

	private void addDealButton() {
		if (AssertValue.isNotNull(aduditTv)) {
			((MsgCardActivity)context).getMenuViewGroup().removeView(aduditTv);
		}
		
		addAduditBtn();
		ViewGroup mPopView = (ViewGroup) LayoutInflater.from(this.context)
				.inflate(R.layout.empty_popupmenu, null);
		appendActionButton(mPopView);
		mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
	}

	private void addAduditBtn() {
		final LinearLayout buttonBarGroup = (LinearLayout) findViewById(R.id.button_bar_group);
		aduditTv = new TextView(context);
		LayoutParams txtAgreeParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, 80);
		txtAgreeParams.setMargins(25, 10, 25, 10);
		aduditTv.setLayoutParams(txtAgreeParams);
		aduditTv.setGravity(Gravity.CENTER);
		aduditTv.setBackgroundResource(R.drawable.pop_bg);
		aduditTv.setTextColor(context.getResources().getColor(R.color.blue));
		aduditTv.setTextSize(16);
		aduditTv.setText("审核");
		aduditTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MsgCardActivity)context).getPopupWindow().dismiss();
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
		((MsgCardActivity)context).getMenuViewGroup().addView(aduditTv);
		
	}

	private View findViewById(int viewId) {
		return this.context.findViewById(viewId);
	}

	/**
	 * 添加弹出框按钮 根据sys_msg_car_action而来
	 * 
	 * @param popView
	 */
	private void appendActionButton(ViewGroup popView) {
		List<MyMsgCardAction> actions = this.getMsgCardAction();
		SessionManager session = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
		String userid = session.getAuthInfo().getUserinfo().getId();
		if (!AssertValue.isNotNullAndNotEmpty(actions)) {
			return;
		}
		TextView nodeName = new TextView(context);
		nodeName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		nodeName.setGravity(Gravity.CENTER);
		nodeName.setTextSize(14);
		nodeName.setTextColor(context.getResources().getColor(R.color.black));
		nodeName.setText("节点名称：");
		popView.addView(nodeName);
		for (final MyMsgCardAction action : actions) {
			if (!AssertValue.isNotNullAndNotEmpty(action.getActors())
					|| action.getActors().indexOf(userid+",") == -1
					|| !MyMsgCardAction.State.PENDING.equals(action.getState())) {	// 当前用户没有该按钮的操作权限
				continue;
			}
			nodeName.setText(new ActionParams(action.getParams()).getHeader().getNodeName());
			TextView txtView = new TextView(context);
			LayoutParams txtAgreeParams = new LayoutParams(
					LayoutParams.MATCH_PARENT, 80);
			txtAgreeParams.setMargins(25, 10, 25, 10);
			txtView.setLayoutParams(txtAgreeParams);
			txtView.setGravity(Gravity.CENTER);
			txtView.setBackgroundResource(R.drawable.pop_bg);
			txtView.setTextColor(context.getResources().getColor(R.color.blue));
			txtView.setTextSize(16);
			txtView.setText(action.getLabel());
			txtView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (AssertValue.isNotNullAndNotEmpty(action.getParams())) {
						try {
							ActionContext actionContext = new ActionContext(
									new ActionParams(action.getParams()),
									BpmExtraMenuHandler.this, context);
							actionContext.setLabel(action.getLabel());
							actionContext.put(MyMsgCard.PARAMS_MSG_CARD, msgCard);
							actionInvokeFactory.invoke(actionContext);
							mPopupWindow.dismiss();
						} catch (Exception e) {
							Toast.makeText(context, "执行动作失败，请稍候重试！",
									Toast.LENGTH_LONG).show();
							;
							logger.exception(e);
						}
					}
					mPopupWindow.dismiss();
				}
			});
			popView.addView(txtView);
		}
	}

	private List<MyMsgCardAction> getMsgCardAction() {
		return this.msgCard.getActions();
	}

	@Override
	public MenuHandlerType getMenuType() {
		return MenuHandlerType.BPMX5;
	}

	@Override
	public void callback(ActionContext actionContext) {
		((MsgCardActivity)context).refresh();
	}

}
