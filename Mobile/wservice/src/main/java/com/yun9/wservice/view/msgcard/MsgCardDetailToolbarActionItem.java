package com.yun9.wservice.view.msgcard;

/**
 * Created by huangbinglong on 15/5/19.
 */
public class MsgCardDetailToolbarActionItem {

    /**
     * 标题，最好中文，供用户直观的看是什么按钮
     */
    private String title;
    /**
     * 动作按钮的资源图片ID
     */
    private int pic;
    /**
     * 动作按钮的类型，对应ActionItemType的静态属性：TYPE_*
     */
    private int type;

    /**
     * 定义动作的类型
     */
    public interface ActionItemType {
        /**
         * 掷骰子
         */
        public static int TYPE_TURNS =  0;
        /**
         * 保存表单
         */
        public static int TYPE_SAVE_FORM = 1;
        /**
         * 流程操作，同意
         */
        public static int TYPE_BPM_AGREE = 2;
        /**
         * 流程操作，驳回
         */
        public static int TYPE_BPM_REJECT = 3;
        /**
         * 流程操作，驳回到
         */
        public static int TYPE_BPM_REJECT_TO = 4;
        /**
         * 流程操作，撤销
         */
        public static int TYPE_BPM_UNDO = 5;
    }
}
