package com.yun9.mobile.msg.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import com.yun9.mobile.framework.constant.Constant;
import com.yun9.mobile.framework.factory.SelectTopicFactory;
import com.yun9.mobile.framework.interfaces.topic.SelectTopic;
import com.yun9.mobile.framework.interfaces.topic.SelectTopicCallback;
import com.yun9.mobile.framework.model.Topic;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.msg.interfaces.NewMsgCardIView;

public class NewMsgCardMsgPresenter {
	private NewMsgCardIView iView;
	private Activity activity;
	private EditText etMsg;
	private static final String TOPIC = "#.+?#";
	private static final String START = "start";
	private static final String END = "end";
	private static final String PHRASE = "phrase";
	protected static final String TAG = NewMsgCardMsgPresenter.class
			.getSimpleName();

	private SpannableString spanStr;

	// 光标位置
	private int cursor = 0;
	/**
	 * 要发送的消息文本
	 */
	private String msgAt;

	/**
	 * 展示用的消息文本（主题高亮、图片等）
	 */
	private String msgShow;

	/**
	 * @param iView
	 */
	public NewMsgCardMsgPresenter(NewMsgCardIView iView, Activity activity,
			EditText etMsg) {
		super();
		this.iView = iView;
		this.activity = activity;
		this.etMsg = etMsg;
	}

	public void handleMsg() {

		// FIXME 使用这个什么监听文本的的东西，如果文本包含话题#XX#，
		// 则每次输入都跳到文本末尾了
		//etMsg.addTextChangedListener(watcher);
		getIntentParm();

	}

	private void getIntentParm() {
		msgAt = activity.getIntent().getStringExtra(Constant.KEY_MSG_HEAD);
		if (!AssertValue.isNotNullAndNotEmpty(msgAt)) {
			return;
		}

		msgShow = msgAt;
		iView.showEditText(msgShow);

		// 光标初始化在字体后面
		// Editable s = etMsg.getEditableText();
		// cursor = s.length();
		// Selection.setSelection(s,cursor);
	}

	TextWatcher watcher = new TextWatcher() {

		// 记录光标的位置
		private int location = 0;

		private String temp;

		// s:之前的文字内容
		// start:添加文字的位置(从0开始)
		// count:不知道 一直是0
		// after:添加的文字总数
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			cursor = start + after;

		}

		// s:之后的文字内容
		// start:添加文字的位置(从0开始)
		// before:不知道 一直是0
		// count:添加的文字总数
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void afterTextChanged(Editable s) {

			temp = s.toString();
			if (!(temp.trim().equals("")) && !temp.equals(msgAt)) {
				msgAt = temp;
				msgShow = msgAt;
				spanStr = hightLightTopic(msgShow);
				if (spanStr != null) {
					iView.showEditText(spanStr);
				}
			}
			Selection.setSelection(s, cursor);
		}
	};

	/**
	 * 高亮话题
	 */
	private SpannableString hightLightTopic(String str) {
		return hightLight(Pattern.compile(TOPIC), str);
	}

	public SpannableString hightLight(Pattern pattern, String str) {
		List<HashMap<String, String>> lst = this.getStartAndEnd(pattern, str);
		if (!AssertValue.isNotNullAndNotEmpty(lst)) {
			return null;
		}

		SpannableString spannableString = new SpannableString(str);
		for (HashMap<String, String> map : lst) {
			ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
			spannableString.setSpan(span, Integer.parseInt(map.get(START)),
					Integer.parseInt(map.get(END)),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return spannableString;
	}

	public List<HashMap<String, String>> getStartAndEnd(Pattern pattern,
			String str) {
		List<HashMap<String, String>> lst = new ArrayList<HashMap<String, String>>();
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(PHRASE, matcher.group());
			map.put(START, matcher.start() + "");
			map.put(END, matcher.end() + "");
			lst.add(map);
		}
		return lst;
	}

	public void addTopicText(String topic) {
		String newMsg = etMsg.getText().toString().trim() + "#" + topic + "#";
		iView.showEditText(newMsg);
	}

	public void topicOnClick() {
		SelectTopic selectTopic = SelectTopicFactory.create(activity);
		selectTopic.selectTopic(new SelectTopicCallback() {
			@Override
			public void onSuccess(Topic topic) {
				addTopicText(topic.getName());
			}

			@Override
			public void onFailure() {
			}
		});
	}

}
