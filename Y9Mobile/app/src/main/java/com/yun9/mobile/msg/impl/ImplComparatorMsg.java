package com.yun9.mobile.msg.impl;

import java.util.Comparator;

import com.yun9.mobile.msg.model.MyMsgCard;
import com.yun9.mobile.msg.model.MyMsgCardMain;

public class ImplComparatorMsg implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		MyMsgCard msg0 = (MyMsgCard)arg0;
		MyMsgCard msg1 = (MyMsgCard)arg1;
		
		MyMsgCardMain msgMain0 = msg0.getMain();
		MyMsgCardMain msgMain1 = msg1.getMain();
		
		return msgMain1.getCreatedate().compareTo(msgMain0.getCreatedate());
	}
}
