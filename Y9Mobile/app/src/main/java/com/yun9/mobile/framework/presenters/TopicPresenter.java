package com.yun9.mobile.framework.presenters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.yun9.mobile.framework.activity.TopicActivity;
import com.yun9.mobile.framework.adapter.TopicAdapter;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.topic.SelectTopicCallback;
import com.yun9.mobile.framework.model.Topic;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.topic.TopicFactory;
import com.yun9.mobile.framework.uiface.UifaceTopic;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

public class TopicPresenter {
	
    private SelectTopicCallback mCallback;
    public static SelectTopicCallback callBack;
	
	private String instId;
	private String userId;
	private Activity activity;
	private TopicFactory factory;
	private UifaceTopic uiface;
	
	/**
	 * @param activity
	 */
	public TopicPresenter(Activity activity) {
		super();
		this.activity = activity;
		this.uiface = (UifaceTopic)activity;
		
		init();
	}
	
	
	private  void init(){
		mCallback = callBack;
		factory = BeanConfig.getInstance().getBeanContext().get(TopicFactory.class);
		SessionManager manager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
		instId = manager.getAuthInfo().getInstinfo().getId();
		userId = manager.getAuthInfo().getUserinfo().getId();
		
	}

	public void btnTopoiconClick(EditText etTopic) {
		try {
			if (etTopic.getText().toString().equals("")) {
				mCallback.onFailure();
			} else {
				Topic topic = new Topic();
				topic.setName(etTopic.getText().toString());
				mCallback.onSuccess(topic);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		activity.finish();	
	}


	public void updataListView(String name) {
		
		 factory.getTopics(instId,userId,name,new AsyncHttpResponseCallback()
         {
             @Override
             public void onSuccess(Response response)
             {
            	 List<Topic> topics = (List<Topic>) response.getPayload();
                 uiface.notifyDataSetChanged(topics);
             }

             @Override
             public void onFailure(Response response)
             {
            	 List<Topic> topics = new ArrayList<Topic>();
            	 uiface.notifyDataSetChanged(topics);
             }
         });
	}


	public void onItemClick(Topic topic) {
		mCallback.onSuccess(topic);
        activity.finish();
	}
}
