package com.yun9.wservice.view.msgcard.model;

import android.view.View;

import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCardProcessAction;
import com.yun9.wservice.model.Product;

/**
 * Created by huangbinglong on 15/5/19.
 */
public class MsgCardPanelActionItem {
    /**
     * 定义动作的类型
     */
    public interface ActionType {
        /**
         * 掷骰子
         */
        public static final int TYPE_TURNS = 0;
        /**
         * 流程操作
         */
        public static final int TYPE_PROCESS = 1;
    }

    public interface ProcessActionType {
        /**
         * 同意
         */
        public static final String AGREE = "agree";
        /**
         * 驳回
         */
        public static final String REJECT = "reject";

        public static final String REJECT_TO = "reject_to";

        public static final String UNDO = "undo";
    }

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

    private MsgCardProcessAction msgCardProcessAction;

    public MsgCardPanelActionItem(String title, int pic, int type) {
        this.title = title;
        this.pic = pic;
        this.type = type;
    }

    public MsgCardPanelActionItem(MsgCardProcessAction msgCardProcessAction) {
        this.title = msgCardProcessAction.getName();
        this.type = ActionType.TYPE_PROCESS;
        this.msgCardProcessAction = msgCardProcessAction;

        if (ProcessActionType.AGREE.equals(msgCardProcessAction.getActionType())) {
            this.pic = R.drawable.agreed;
        } else if (ProcessActionType.REJECT.equals(msgCardProcessAction.getActionType())) {
            this.pic = R.drawable.rejected;
        } else if (ProcessActionType.REJECT_TO.equals(msgCardProcessAction.getActionType())) {
            this.pic = R.drawable.rejected1;
        } else if (ProcessActionType.UNDO.equals(msgCardProcessAction.getActionType())) {
            this.pic = R.drawable.undo;
        } else {
            this.pic = R.drawable.save_fill;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
