package com.yun9.mobile.framework.uiface;

import java.util.LinkedList;
import java.util.List;

import com.yun9.mobile.framework.model.Topic;

public interface UifaceTopic {
	public void showToast(String msg);

	public void notifyDataSetChanged(List<Topic> listTopic);
}
