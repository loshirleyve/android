package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.view.JupiterFragmentMenuActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.pulltorefresh.PullToRefreshListView;
import com.yun9.wservice.R;
import com.yun9.wservice.cache.FileIdCache;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.model.MsgCardAttachment;
import com.yun9.wservice.model.MsgCardMain;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import java.util.ArrayList;
import java.util.List;

public class MsgCardListActivity extends JupiterFragmentActivity {

    public static final String ARG_TYPE = "type";

    public static final String ARG_VALUE = "value";

    private String mType;

    private String mValue;

    private Logger logger = Logger.getLogger(MsgCardListActivity.class);

    @ViewInject(id=R.id.msg_card_list_title)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id=R.id.msg_card_lv)
    private PullToRefreshListView msgCardList;

    public static void start(Context context,Bundle bundle){
        Intent intent = new Intent(context,MsgCardListActivity.class);
        if (AssertValue.isNotNull(bundle)){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取传递的参数
        if (AssertValue.isNotNull(this.getIntent().getExtras())) {
            mType = this.getIntent().getExtras().getString(ARG_TYPE);
            mValue = this.getIntent().getExtras().getString(ARG_VALUE);
        }

        titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgCardListActivity.this.finish();
            }
        });

//        titleBar.getTitleRight().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MsgCardListActivity.this.mMenuDrawer.toggleMenu();
//            }
//        });

        MsgCardListAdapter msgCardListAdapter = new MsgCardListAdapter(this,this.initMsgCard());
        msgCardList.setAdapter(msgCardListAdapter);
        msgCardList.setOnItemClickListener(msgCardOnItemClickListener);

   }


    private AdapterView.OnItemClickListener msgCardOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MsgCard msgCard = (MsgCard) view.getTag();

            logger.d("消息卡片点击！"+msgCard.getMain().getFrom());

            Bundle bundle = new Bundle();
            bundle.putSerializable(MsgCardDetailActivity.ARG_MSG_CARD,msgCard);
            MsgCardDetailActivity.start(MsgCardListActivity.this,bundle);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_msg_card_list;
    }

    private List<MsgCard> initMsgCard(){
        List<MsgCard> msgCards = new ArrayList<MsgCard>();
        // 预先缓冲一些测试图片
        FileIdCache.getInstance().put("0","http://tabletpcssource.com/wp-content/uploads/2011/05/android-logo.png");
        FileIdCache.getInstance().put("2","http://radiotray.sourceforge.net/radio.png");
        FileIdCache.getInstance().put("3","http://wrong.site.com/corruptedLink");
        FileIdCache.getInstance().put("4","http://bit.ly/soBiXr");
        FileIdCache.getInstance().put("5","http://img001.us.expono.com/100001/100001-1bc30-2d736f_m.jpg");
        FileIdCache.getInstance().put("7","");
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

        // 伪造一些图片
        List<MsgCardAttachment> attachments = new ArrayList<>();
        for (int j = 0;j < 7;j++) {
            attachments.add(new MsgCardAttachment(i+"",i+"",j+"",i+""));
        }
        msgCard.setAttachments(attachments);

        return msgCard;
    }
}
