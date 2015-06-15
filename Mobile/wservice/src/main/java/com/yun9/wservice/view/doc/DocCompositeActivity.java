package com.yun9.wservice.view.doc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yun9.jupiter.model.ImageBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.List;

/**
 * Created by rxy on 15/6/1.
 * 文件列表界面
 */
public class DocCompositeActivity extends JupiterFragmentActivity {

    private static final Logger logger = Logger.getLogger(DocCompositeActivity.class);

    private DocCompositeCommand command;

    private LocalImageCommand localImageCommand;

    @ViewInject(id = R.id.buttonbar)
    private LinearLayout buttonbarLL;

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeButton;

    @ViewInject(id = R.id.sendmsgcard)
    private JupiterImageButtonLayout sendMsgCardButton;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.image_location)
    private JupiterRowStyleSutitleLayout localImageBtn;

    private List<ImageBean> onSelectLocalImages;


    public static void start(Activity activity, DocCompositeCommand command) {
        Intent intent = new Intent(activity, DocCompositeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_doc_composite;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取参数
        command = (DocCompositeCommand) this.getIntent().getSerializableExtra("command");

        localImageBtn.setOnClickListener(onLocalFileClickListener);
        this.titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);

    }

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener onLocalFileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AssertValue.isNotNull(localImageCommand)) {
                localImageCommand = new LocalImageCommand().setEdit(true).setCompleteType(LocalImageCommand.COMPLETE_TYPE_CALLBACK).setMaxSelectNum(6);
            }

            if (AssertValue.isNotNullAndNotEmpty(onSelectLocalImages)) {
                localImageCommand.setSelectImages(onSelectLocalImages);
            }

            LocalImageActivity.start(DocCompositeActivity.this, localImageCommand);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == localImageCommand.getRequestCode() && resultCode == LocalImageCommand.RESULT_CODE_OK) {
            onSelectLocalImages = (List<ImageBean>) data.getSerializableExtra(LocalImageCommand.PARAM_IMAGE);
        }
    }
}
