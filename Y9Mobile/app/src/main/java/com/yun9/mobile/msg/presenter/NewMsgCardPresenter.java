package com.yun9.mobile.msg.presenter;

import java.util.List;

import android.app.Activity;
import android.widget.EditText;

import com.yun9.mobile.framework.location.LocationCallBack.LocationOutParam;
import com.yun9.mobile.msg.interfaces.NewMsgCardIView;

public class NewMsgCardPresenter {

	private static final int REQ_TOPIC = 0x101;
	private Activity activity;

	private NewMsgCardIView iView;

	private EditText etMsg;
	/**
	 * 位置逻辑处理
	 */
	private NewMsgCardPositionPresenter positionPre;

	/**
	 * public等范围逻辑处理
	 */
	private NewMsgCardScopePresenter scopePre;

	/**
	 * 要发送的消息逻辑处理(主要是文本编辑框的处理)
	 */
	private NewMsgCardMsgPresenter msgPre;

	/**
	 * 联系人逻辑处理
	 */
	//private NewMsgCardContactsPresenter contactsPre;

	public NewMsgCardPresenter(Activity activity, EditText etMsg) {
		this.activity = activity;
		this.iView = (NewMsgCardIView) activity;
		this.etMsg = etMsg;
		init();
	}

	private void init() {
		positionPre = new NewMsgCardPositionPresenter(iView, activity);
		scopePre = new NewMsgCardScopePresenter(iView, activity);
		msgPre = new NewMsgCardMsgPresenter(iView, activity, etMsg);
//		contactsPre = new NewMsgCardContactsPresenter(activity);

		positionPre.handlePostion();
		msgPre.handleMsg();
	}

	public void topicOnClick() {
		msgPre.topicOnClick();
	}

	public void itLocateOnClickListener() {
		positionPre.locateOnClickListenr();
	}

	public void scopeOnClickListener() {
		scopePre.scopeOnClickListener();
	}

//	public void addContactUserOnClickListener() {
//		contactsPre.addContactUserOnClickListener();
//	}

	/**
	 * 获取user和org ID
	 * 
	 * @param userids
	 * @param orgids
	 */
	public void getIds(List<String> userids, List<String> orgids) {
		scopePre.getIds(userids, orgids);
	}
	
	/**
	 * 获取地理位置信息
	 * 
	 * @return
	 */
	public LocationOutParam getLocation() {
		return positionPre.getLocation();
	}

	/**
	 * 获取当前模式
	 */
	public String getCurrentMode() {
		return scopePre.getCurrentMode();
	}
}
