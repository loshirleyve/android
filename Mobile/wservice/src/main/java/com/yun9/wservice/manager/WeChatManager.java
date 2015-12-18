package com.yun9.wservice.manager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.text.TextUtils;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.yun9.wservice.manager.support.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by huangbinglong on 7/2/15.
 */
public interface WeChatManager {

    public static final String APP_ID = "wx38baa215cc9a46bb";

    public static final String MCH_ID = "1253257501";

    public static final String KEY = "6DB14B85EF21E7247DE90DA0DB12057A";

    public static final String TRADD_TYPE = "APP";

    public static final String PREPAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public void pay(Activity activity, OrderInfo orderInfo,HttpResponseCallback callback);

    public PayReq getReq(Activity activity, byte[] buf);

    /**
     * 支付请求
     */
    public static class OrderInfo {

        private String subject;
        private String body;
        private String callbackid;
        private String price;

        public OrderInfo(String subject, String body, String callbackid, String price) {
            this.subject = subject;
            this.body = body;
            this.callbackid = callbackid;
            this.price = price;
        }

        /**
         * create the order info. 创建订单信息
         */
        public String getOrderInfo(String notifyUrl) {
            String onceStr = UUID.randomUUID().toString().replace("-","");
            StringBuffer sb = new StringBuffer("<xml>");
            String totalFee =  (int)(Double.valueOf(this.price) * 100)+"";
            String body = this.body;
            String stringSignTemp = "appid=" + WeChatManager.APP_ID +
                    "&body=" + body + "&mch_id=" + WeChatManager.MCH_ID + "&nonce_str=" + onceStr +
                    "&notify_url=" + notifyUrl + "&out_trade_no=" + this.callbackid +
                    "&spbill_create_ip=120.24.84.201&total_fee=" + totalFee + "&trade_type=" + WeChatManager.TRADD_TYPE +
                    "&key=" + WeChatManager.KEY;
            String sign = MD5.getMessageDigest(stringSignTemp.getBytes()).toUpperCase();
            sb.append(this.createEle("appid", WeChatManager.APP_ID));
            sb.append(this.createEle("body", body));
            sb.append(this.createEle("mch_id", WeChatManager.MCH_ID));
            sb.append(this.createEle("nonce_str", onceStr));
            sb.append(this.createEle("notify_url", notifyUrl));
            sb.append(this.createEle("out_trade_no", this.callbackid));
            sb.append(this.createEle("spbill_create_ip", "120.24.84.201"));
            sb.append(this.createEle("total_fee",totalFee));
            sb.append(this.createEle("trade_type", WeChatManager.TRADD_TYPE));
            sb.append(this.createEle("sign", sign));
            sb.append("</xml>");
            try {
                return new String(sb.toString().getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        public String createEle(String ele, String value) {
            if (!TextUtils.isEmpty(ele) && !TextUtils.isEmpty(value)) {
                return "<" + ele + ">" + value + "</" + ele + ">";
            }
            return "";
        }

    }
    public static interface HttpResponseCallback {
        public void onSuccess(byte[] bytes);

        public void onFailure(byte[] bytes, Throwable throwable);
    }

}
