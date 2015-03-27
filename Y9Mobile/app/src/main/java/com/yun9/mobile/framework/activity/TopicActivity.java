package com.yun9.mobile.framework.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.adapter.TopicAdapter;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.topic.SelectTopic;
import com.yun9.mobile.framework.interfaces.topic.SelectTopicCallback;
import com.yun9.mobile.framework.model.Topic;
import com.yun9.mobile.framework.presenters.TopicPresenter;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.topic.TopicFactory;
import com.yun9.mobile.framework.uiface.UifaceTopic;
import com.yun9.mobile.framework.util.AssertValue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TopicActivity extends Activity implements UifaceTopic
{

    private EditText et_topic;
    private Button btn_topic;
    private ListView lv_topic;
    private TopicAdapter mAdapter;

    private List<Topic> topics;
   
    private TopicPresenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

       
        findId();
        init();
        setEvent();
    }

    private void setEvent()
    {
        btn_topic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				presenter.btnTopoiconClick(et_topic);
			}
		});
        
        lv_topic.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
			  	
				presenter.onItemClick(topics.get(position));
				
			}
		});
        
        et_topic.addTextChangedListener(EditTextListener);
    }

    private void init()
    {
    	presenter = new TopicPresenter(TopicActivity.this);
    	
    	topics = new ArrayList<Topic>();
        mAdapter = new TopicAdapter(topics,TopicActivity.this);
        lv_topic.setAdapter(mAdapter);
        
        presenter.updataListView(et_topic.getText().toString());
        
    }

    private void findId()
    {
        et_topic = (EditText) findViewById(R.id.et_topic);
        btn_topic = (Button) findViewById(R.id.btn_topic_state);
        lv_topic = (ListView) findViewById(R.id.lv_topic);
    }

    TextWatcher EditTextListener = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {
        	
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {
        	
          
        }

        @Override
        public void afterTextChanged(Editable editable)
        {
            if(!et_topic.getText().toString().equals(""))
            {
                presenter.updataListView(et_topic.getText().toString());
                changeBtnTopicStat(getString(R.string.complete));
            	btn_topic.setTextColor(getResources().getColor(R.color.red));
                
            } else
            {
                changeBtnTopicStat(getString(R.string.app_cancel));
                btn_topic.setTextColor(getResources().getColor(R.color.gray));
            }
        }
    };

	@Override
	public void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void notifyDataSetChanged(List<Topic> listTopic) {
		
		String etText = et_topic.getText().toString().trim();
		
		// 后台获取到话题数据
		if(AssertValue.isNotNullAndNotEmpty(listTopic)){
			topics.clear();
			topics.addAll(listTopic);
		}
		// 后台没有获取到话题数据 && 文本不为空
		else if(!AssertValue.isNotNullAndNotEmpty(listTopic) && (!etText.equals(""))){
			Topic topic = new Topic();
			topic.setName(etText);
			topic.setType("text");
			topics.clear();
			topics.add(topic);
		}
		mAdapter.notifyDataSetChanged();
	}
	
	private void changeBtnTopicStat(String stat){
		btn_topic.setText(stat);
	}
}
