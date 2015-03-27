package com.yun9.mobile.msg.interfaces;

import java.util.List;

import android.text.SpannableString;

public interface NewMsgCardIView {

	public void showToast(String msg);

	public void locationState(String text, int id);

	public void scopeMode(String text, int id);

	public void setOrgORuser(List<String> userNames, List<String> orgNames);

	public void showEditText(SpannableString msgShow);

	public void showEditText(String msgShow);
}
