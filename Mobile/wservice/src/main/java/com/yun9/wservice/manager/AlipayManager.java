package com.yun9.wservice.manager;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by huangbinglong on 7/2/15.
 */
public interface AlipayManager {
    //商户PID
    public static final String PARTNER = "2088911050457424";
    //商户收款账号
    public static final String SELLER = "2088911050457424";
    //商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALlOqB3MOlDvGMAK\n" +
            "LMRnM4DGhxgi/nO2QTVdHaFuytUHRXs/QKD6asmsFCdZSzqN4KX16VAGmst/1Ltf\n" +
            "oUHwmcgbCd96VGYOEthGia2XYDnMVXdaMkw8jbUvIxhH0NlEf2rqucjOjPAIXfmd\n" +
            "9HyXAqo0ZzBGmfknuY0Ru2cQjxZ9AgMBAAECgYBn7xwQCnoy5sgE/i/5wMB8W91t\n" +
            "xkLdd72/RojWAKQ3M6re461GHO5ESwXKxCSTpPv5WaIQxoOP3BTfO1Xg/Cfjxgh7\n" +
            "xHCvP5jnFjC1nFG/qd6t7W1O2Doa136Kuv2CYy3mCo0bIe1RxYZ30zWQBOmb3nVQ\n" +
            "TXENoZb68D1R/hc0AQJBAOBIVNpviCeiP8inR9BH1B9BT0q77scCvjZoghUEM5Np\n" +
            "10CUWKZ+Efp48RJWuzck/CNsNWEiDaJkujgNi7YOPIECQQDTg07yUoSAk5EEqYbv\n" +
            "c7AEev7Jx/ydTafxC1vsShh5r9hjC4EvM1JQMdsjp6Z5cSsFu0OH0JH7KnnZjU2l\n" +
            "A8v9AkBibvxmvgp4spnh26EGBXBQo15Eg4TBbS/EO0vUI3rrWMly+2iI8c28KzZK\n" +
            "gSw7gIz0kvo+fi6TtjeQgElwBoGBAkBoX5+RjI6+NBOXzWA8wUXWsCsSv5E0vqFl\n" +
            "HUnbLCUvx0psdbzl4dl4oWGWEqDfxKyKQ4JrfszKuIcwh74M8/axAkEAmyt+uO2D\n" +
            "s4Xog+X3ZK9rQ7Ad1UrYVl5OlRgWIFHt7631pSq/WEId4z2uRsJZaeLrcoyy4VdM\n" +
            "s3oVqnSB9Ng/ug==";
    //支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    public static final int SDK_PAY_FLAG = 1;

    /**
     * 支付订单，回调的handler中，
     * 会接收到的Message：what为SDK_PAY_FLAG，obj为PayResult
     * @param activity 上下文，用户显示提示信息等
     * @param orderInfo 订单信息
     * @param handler 回调
     */
    public void pay(Activity activity,OrderInfo orderInfo,Handler handler);

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
         *
         */
        public String getOrderInfo(String notifyUrl) {
            // 签约合作者身份ID
            String orderInfo = "partner=" + "\"" + PARTNER + "\"";

            // 签约卖家支付宝账号
            orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

            // 商户网站唯一订单号
            orderInfo += "&out_trade_no=" + "\"" + callbackid + "\"";

            // 商品名称
            orderInfo += "&subject=" + "\"" + subject + "\"";

            // 商品详情
            orderInfo += "&body=" + "\"" + body + "\"";

            // 商品金额
            orderInfo += "&total_fee=" + "\"" + price + "\"";

            // 服务器异步通知页面路径
            orderInfo += "&notify_url=" + "\"" + notifyUrl
                    + "\"";

            // 服务接口名称， 固定值
            orderInfo += "&service=\"mobile.securitypay.pay\"";

            // 支付类型， 固定值
            orderInfo += "&payment_type=\"1\"";

            // 参数编码， 固定值
            orderInfo += "&_input_charset=\"utf-8\"";

            // 设置未付款交易的超时时间
            // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
            // 取值范围：1m～15d。
            // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
            // 该参数数值不接受小数点，如1.5h，可转换为90m。
            orderInfo += "&it_b_pay=\"30m\"";

            // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
            // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

            // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
            orderInfo += "&return_url=\"m.alipay.com\"";

            // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
            // orderInfo += "&paymethod=\"expressGateway\"";

            return orderInfo;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getCallbackid() {
            return callbackid;
        }

        public void setCallbackid(String callbackid) {
            this.callbackid = callbackid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    /**
     * 支付结果
     */
    public static class PayResult {
        private String resultStatus;
        private String result;
        private String memo;

        public PayResult(String rawResult) {

            if (TextUtils.isEmpty(rawResult))
                return;

            String[] resultParams = rawResult.split(";");
            for (String resultParam : resultParams) {
                if (resultParam.startsWith("resultStatus")) {
                    resultStatus = gatValue(resultParam, "resultStatus");
                }
                if (resultParam.startsWith("result")) {
                    result = gatValue(resultParam, "result");
                }
                if (resultParam.startsWith("memo")) {
                    memo = gatValue(resultParam, "memo");
                }
            }
        }

        @Override
        public String toString() {
            return "resultStatus={" + resultStatus + "};memo={" + memo
                    + "};result={" + result + "}";
        }

        private String gatValue(String content, String key) {
            String prefix = key + "={";
            return content.substring(content.indexOf(prefix) + prefix.length(),
                    content.lastIndexOf("}"));
        }

        /**
         * @return the resultStatus
         */
        public String getResultStatus() {
            return resultStatus;
        }

        /**
         * @return the memo
         */
        public String getMemo() {
            return memo;
        }

        /**
         * @return the result
         */
        public String getResult() {
            return result;
        }
    }
}
