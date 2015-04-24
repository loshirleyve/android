package com.yun9.wservice.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.jupiter.widget.TitleBarLayout;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.model.MsgCardMain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class DynamicSessionFragment extends JupiterFragment {

    private static final Logger logger = Logger.getLogger(DynamicSessionFragment.class);

    //动态页面需要传递参数的Key
    public static final String ARG_PARAM1 = "param1";

    private String mParam1 ;

    @BeanInject
    private SessionManager sessionManager;

    @ViewInject(id=R.id.dynamic_title_tb)
    private TitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.dynamic_sessions_lv)
    private ListView dynamicSessionList;


    /**
     * 使用工厂方法创建一个新的动态实例，
     * 这个动态的使用必须使用此方法创建实例
     *
     */
    public static DynamicSessionFragment newInstance(Bundle args) {
        DynamicSessionFragment fragment = new DynamicSessionFragment();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);

        this.titleBarLayout.getTitleLeft().setVisibility(View.VISIBLE);


        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.d("点击了返回");
                logger.d("参数："+mParam1);
            }
        });

        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.d("点击了右边按钮");
            }
        });

        titleBarLayout.getTitleCenter().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.d("点击了标题。");
            }
        });

        this.dynamicSessionList.setAdapter(new DynamicSessionAdapter(this.mContext,this.initMsgCard()));
        this.dynamicSessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MsgCard msgCard = (MsgCard) view.getTag();

                logger.d("动态会话表格点击。"+msgCard.getMain().getFrom());
            }
        });

        return view;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dynamic;
    }

    private List<MsgCard> initMsgCard(){
        List<MsgCard> msgCards = new ArrayList<MsgCard>();

        for(int i=0;i<10;i++){
            msgCards.add(this.createMsgCard(i));
        }

        return msgCards;
    }

    private MsgCard createMsgCard(int i){
        MsgCard msgCard = new MsgCard();

        MsgCardMain msgCardMain = new MsgCardMain();

        msgCardMain.setId(i+"");
        msgCardMain.setContent("测试内容"+i);
        msgCardMain.setFrom("Leon"+i);

        msgCard.setMain(msgCardMain);

        return msgCard;
    }
}
