package com.yun9.wservice.manager.support;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.conf.PropertiesManager;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.manager.AlipayManager;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by huangbinglong on 7/2/15.
 */
public class DefaultAlipayManager implements AlipayManager,Bean {

    @Override
    public void pay(final Activity activity,OrderInfo orderInfo, final Handler handler) {
        PropertiesManager propertiesManager = JupiterApplication.getBeanManager().get(PropertiesManager.class);
        String notifyUrl = propertiesManager.getString("app.config.pay.notify.url");
        if (orderInfo == null){
            Toast.makeText(activity,"订单信息为空",Toast.LENGTH_SHORT).show();
            return;
        } else if (!AssertValue.isNotNullAndNotEmpty(notifyUrl)){
            Toast.makeText(activity,"无法获取通知回调接口地址",Toast.LENGTH_SHORT).show();
            return;
        }

        // 订单
        String order = orderInfo.getOrderInfo(notifyUrl);

        // 对订单做RSA 签名
        String sign = sign(order);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = order + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                System.out.println("支付宝结果："+result);
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    @Override
    public Class<?> getType() {
        return AlipayManager.class;
    }
}
