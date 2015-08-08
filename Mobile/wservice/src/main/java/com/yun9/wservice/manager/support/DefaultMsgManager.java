package com.yun9.wservice.manager.support;

import android.app.Activity;
import android.view.View;

import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.view.CustomCallbackActivity;
import com.yun9.wservice.manager.MsgManager;
import com.yun9.wservice.model.Msg;
import com.yun9.wservice.msghandler.MsgHandler;
import com.yun9.wservice.msghandler.support.MsgCardHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangbinglong on 8/7/15.
 */
public class DefaultMsgManager implements MsgManager,Bean,Initialization{

    private Map<String,MsgHandler> handlerMap;

    @Override
    public View getView(CustomCallbackActivity activity, View convertView, Msg msg) {
        if (msg == null || activity == null){
            return null;
        }
        MsgHandler handler = handlerMap.get(msg.getType());
        if (handler != null){
            if (convertView == null){
                convertView = handler.getView(activity,msg);
            } else {
                handler.loadData(activity,convertView,msg);
            }
        }
        return convertView;
    }

    @Override
    public Class<?> getType() {
        return MsgManager.class;
    }

    @Override
    public void init(BeanManager beanManager) {
        if (handlerMap ==null){
            handlerMap = new HashMap<>();
        }
        addHandler(new MsgCardHandler());
    }

    private void addHandler(MsgHandler msgHandler) {
        handlerMap.put(msgHandler.getType(),msgHandler);
    }
}
