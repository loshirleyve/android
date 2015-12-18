package com.yun9.wservice.manager.support;

import android.app.Activity;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.conf.PropertiesManager;
import com.yun9.wservice.manager.WeChatManager;
import com.yun9.wservice.manager.support.xml2map.XmlUtils;

import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by huangbinglong on 15/12/14.
 */
public class DefaultWeChatManager implements WeChatManager, Bean {

    @Override
    public void pay(final Activity activity, final OrderInfo orderInfo, HttpResponseCallback callback) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(activity, WeChatManager.APP_ID);
        msgApi.registerApp(WeChatManager.APP_ID);
        PropertiesManager propertiesManager = JupiterApplication.getBeanManager().get(PropertiesManager.class);
        String notifyUrl = propertiesManager.getString("app.config.pay.wx.notify.url");
        final String entity = orderInfo.getOrderInfo(notifyUrl);
        getPrepayInfo(entity, callback);

    }

    public PayReq getReq(Activity activity, byte[] buf) {
        try {
            if (buf != null && buf.length > 0) {
                String content = new String(buf);
                Map map = XmlUtils.xmlToMap(new InputSource(new StringReader(content)));
                Map<String, String> props = (Map<String, String>) map.get("xml");
                if ("SUCCESS".equals(props.get("return_code"))) {
                    String onceStr = UUID.randomUUID().toString().replace("-", "");
                    String timeStamp = new Date().getTime() + "";
                    String stringSignTemp = "appid=" + WeChatManager.APP_ID + "&noncestr=" + onceStr +
                            "&package=Sign=WXPay" + "&partnerid=" + WeChatManager.MCH_ID +
                            "&prepayid=" + props.get("prepay_id") +
                            "&timestamp=" + timeStamp + "&key=" + WeChatManager.KEY;
                    String sign = MD5.getMessageDigest(stringSignTemp.getBytes());
                    PayReq req = new PayReq();
                    req.appId = WeChatManager.APP_ID;
                    req.partnerId = WeChatManager.MCH_ID;
                    req.prepayId = props.get("prepay_id");
                    req.nonceStr = onceStr;
                    req.timeStamp = timeStamp;
                    req.packageValue = "Sign=WXPay";
                    req.sign = sign;
                    return req;
                } else {
                    Toast.makeText(activity, "获取微信预付码错误：" + props.get("return_code"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, "获取微信预付码错误", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /**
     * 通过异步请求微信统一下单接口
     *
     * @param xml
     * @param callback
     */
    private void getPrepayInfo(final String xml, final HttpResponseCallback callback) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                byte[] buf = Util.httpPost(WeChatManager.PREPAY_URL, xml);
                if (buf.length > 0) {
                    callback.onSuccess(buf);
                } else {
                    callback.onFailure(buf, new Exception("无法获取统一下单信息"));
                }
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public Class<?> getType() {
        return WeChatManager.class;
    }
}
