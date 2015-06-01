package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.org.OrgCompositeCommand;

/**
 * Created by rxy on 15/6/1.
 * 文件列表界面
 */
public class DocCompositeActivity extends JupiterFragmentActivity {

    private static final Logger logger = Logger.getLogger(DocCompositeActivity.class);


    @ViewInject(id = R.id.orglist)
    private ListView userListView;

    @ViewInject(id = R.id.buttonbar)
    private LinearLayout buttonbarLL;

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeButton;

    @ViewInject(id = R.id.sendmsgcard)
    private JupiterImageButtonLayout sendMsgCardButton;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, DocCompositeActivity.class);
       // Bundle bundle = new Bundle();
      //  bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_doc_composite;
    }


}
