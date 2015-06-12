package com.yun9.wservice.view.topic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.StringUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterImageEditLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.TopicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/12.
 */
public class TopicActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.topic_et)
    private JupiterImageEditLayout topicET;

    @ViewInject(id = R.id.topic_lv)
    private ListView topicLV;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private TopicAdapter topicAdapter;

    private List<TopicBean> topics = new ArrayList<>();

    private List<TopicBean> showTopics = new ArrayList<>();

    public static void start(Activity activity, TopicCommand command) {
        Intent intent = new Intent(activity, TopicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_topic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        this.topicET.getTextET().addTextChangedListener(textWatcher);
        this.topicLV.setOnItemClickListener(onItemClickListener);

        topicLV.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 100);
    }

    private void refresh() {
        String instid = sessionManager.getInst().getId();

        if (AssertValue.isNotNullAndNotEmpty(instid)) {
            final ProgressDialog locationDialog = ProgressDialog.show(TopicActivity.this, null, getResources().getString(R.string.app_wating), true);

            Resource resource = resourceFactory.create("QueryTopics");
            resource.param("instid", instid).param("type", "text");

            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    topics = (List<TopicBean>) response.getPayload();

                    if (AssertValue.isNotNullAndNotEmpty(topics)) {
                        showTopics.clear();

                        for (TopicBean topicBean : topics) {
                            showTopics.add(topicBean);
                        }
                        refreshListView();
                    }
                }

                @Override
                public void onFailure(Response response) {

                }

                @Override
                public void onFinally(Response response) {
                    locationDialog.dismiss();
                }
            });
        }
    }

    private void refreshListView() {
        if (!AssertValue.isNotNull(topicAdapter)) {
            topicAdapter = new TopicAdapter(TopicActivity.this, showTopics);
            topicLV.setAdapter(topicAdapter);
        } else {
            topicAdapter.notifyDataSetChanged();
        }
    }

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setResult(TopicCommand.RESULT_CODE_CANCEL);
            finish();
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TopicBean topicBean = (TopicBean) view.getTag();
            if (AssertValue.isNotNull(topicBean) && AssertValue.isNotNullAndNotEmpty(topicBean.getName())){
                Intent intent =new Intent();
                intent.putExtra(TopicCommand.PARAM_TOPIC,"#"+topicBean.getName()+"#");
                setResult(TopicCommand.RESULT_CODE_OK,intent);
                finish();
            }
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            showTopics.clear();

            if (!AssertValue.isNotNullAndNotEmpty(s.toString())) {
                for (TopicBean topicBean : topics) {
                    showTopics.add(topicBean);
                }
            } else {
                for (TopicBean topicBean : topics) {
                    if (StringUtil.contains(topicBean.getName(),s.toString(),true)){
                        showTopics.add(topicBean);
                    }
                }

                if (!AssertValue.isNotNullAndNotEmpty(showTopics)){
                    TopicBean topicBean = new TopicBean();
                    topicBean.setName(s.toString());
                    topicBean.setHotnum(0);
                    showTopics.add(topicBean);
                }
            }


            refreshListView();
        }
    };
}
