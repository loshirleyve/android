package com.yun9.mobile.framework.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.activity.ScopeActivity;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.file.FileFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.CheckOnWorkAttend;
import com.yun9.mobile.framework.model.FileInfo;
import com.yun9.mobile.framework.model.FileUrlById;
import com.yun9.mobile.framework.model.ScheDulingWork;
import com.yun9.mobile.framework.model.Topic;
import com.yun9.mobile.framework.personelservice.CheckOnWorkAttendFactory;
import com.yun9.mobile.framework.personelservice.CheckOnWorkAttendFactory.CheckInInParm;
import com.yun9.mobile.framework.personelservice.CheckOnWorkAttendFactory.ScheDulingWorkInParm;
import com.yun9.mobile.framework.topic.TopicFactory;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.Logger;
import com.yun9.mobile.position.activity.DemoPositionActivity;

import java.io.File;
import java.util.List;

/**
 * Created by User on 2014/10/30.
 */
public class FunTestActivity extends Activity{
    protected static final String TAG = FunTestActivity.class.getSimpleName();
	private Logger logger = Logger.getLogger(FunTestActivity.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fun);

        setEvent();
    }

    private void setEvent(){
       
    	findViewById(R.id.btnForm).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FunTestActivity.this, FormTestActivity.class);
				startActivity(intent);
			}
		});
    }
    

    private void showToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
