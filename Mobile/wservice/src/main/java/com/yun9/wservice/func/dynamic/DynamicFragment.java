package com.yun9.wservice.func.dynamic;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.model.MsgCardMain;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class DynamicFragment extends JupiterFragment {

    private static final Logger logger = Logger.getLogger(DynamicFragment.class);

    //动态页面需要传递参数的Key
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    @BeanInject
    private SessionManager sessionManager;

    @ViewInject(id = R.id.dynamic_sessions_lv)
    private ListView dynamicSessionList;

    /**
     * 使用工厂方法创建一个新的动态实例，
     * 这个动态的使用必须使用此方法创建实例
     */
    public static DynamicFragment newInstance(Bundle args) {
        DynamicFragment fragment = new DynamicFragment();
        if (AssertValue.isNotNull(args)) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传递的参数
        if (AssertValue.isNotNull(this.getArguments())) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initViews(View view) {

    }

    private List<MsgCard> initMsgCard() {
        List<MsgCard> msgCards = new ArrayList<MsgCard>();

        for (int i = 0; i < 10; i++) {
            msgCards.add(this.createMsgCard(i));
        }

        return msgCards;
    }

    private MsgCard createMsgCard(int i) {
        MsgCard msgCard = new MsgCard();

        MsgCardMain msgCardMain = new MsgCardMain();

        msgCardMain.setId(i + "");
        msgCardMain.setContent("测试内容" + i);
        msgCardMain.setFrom("Leon" + i);

        msgCard.setMain(msgCardMain);

        return msgCard;
    }
}
