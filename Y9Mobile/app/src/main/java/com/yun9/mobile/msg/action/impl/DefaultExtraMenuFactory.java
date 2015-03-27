package com.yun9.mobile.msg.action.impl;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Injection;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.Logger;
import com.yun9.mobile.msg.action.BpmExtraMenuHandler;
import com.yun9.mobile.msg.action.ExtraMenuFactory;
import com.yun9.mobile.msg.action.ExtraMenuHandler;
import com.yun9.mobile.msg.activity.MsgCardActivity;
import com.yun9.mobile.msg.model.MyMsgCard;

public class DefaultExtraMenuFactory implements ExtraMenuFactory, Bean, Injection {
	
	private static final Logger logger = Logger.getLogger(DefaultExtraMenuFactory.class);
	
	private List<ExtraMenuHandler> menuHanders;
	
	@Override
	public void addExtraMenu(MyMsgCard cardInfo, MsgCardActivity activity) {
		if (AssertValue.isNotNull(cardInfo.getMain())
				&& AssertValue.isNotNullAndNotEmpty(cardInfo.getMain().getSource())) {
			try {
				for (ExtraMenuHandler handler : menuHanders) {
					if (handler.getMenuType().getType().equals(cardInfo.getMain().getSource())) {
						handler.handle(cardInfo, activity);
					}
				}
			} catch (Exception e) {
				Toast.makeText(activity, "无法载入额外操作菜单，请稍候重试！", Toast.LENGTH_SHORT).show();
				logger.exception(e);
			}
		} else  {
			Toast.makeText(activity, "程序错误，请稍候重试！", Toast.LENGTH_SHORT).show();
			logger.e("无法获取消息卡片的内容或类型。");
		}
	}

	@Override
	public void injection(BeanContext beanContext) {
		// TODO 获取配置的actionHanders列表
		menuHanders = new ArrayList<ExtraMenuHandler>();
		menuHanders.add(new BpmExtraMenuHandler());
	}

	@Override
	public Class<?> getType() {
		return ExtraMenuFactory.class;
	}

}
