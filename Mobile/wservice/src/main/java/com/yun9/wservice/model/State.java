package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/6/25.
 */
public final class State implements Serializable{

    public class Order {
        public static final String WAITING_PAY = "buy";
        public static final String IN_SERVICE = "inservice";
        public static final String COMPLETE = "complete";
    }

    public class OrderPayState {
        public static final String WAITING_PAY = "waitingpay";
        public static final String IN_SERVICE = "inservice";
        public static final String COMPLETE = "complete";
    }

    public class MsgCard {
        public static final String UN_READ = "unread";
    }

    public class WorkOrder {
        public static final String COMPLETE = "complete";
    }
}
