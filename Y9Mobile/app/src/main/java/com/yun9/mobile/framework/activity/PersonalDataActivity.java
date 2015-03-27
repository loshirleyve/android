package com.yun9.mobile.framework.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.fragment.PersonalDataFragment;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.interfaces.SysUserQueryInfo;
import com.yun9.mobile.framework.model.UserQuery;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.support.DefaultUserQuery;

public class PersonalDataActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        final FrameLayout fl_forFragment = (FrameLayout) findViewById(R.id.layoutForFragment);
        final boolean isUser = getIntent().getBooleanExtra("isUser", false);

        SessionManager manager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
        String userId = manager.getAuthInfo().getUserinfo().getId();
        SysUserQueryInfo userQueryInfo = new DefaultUserQuery();
        userQueryInfo.getUserQueryInfo(userId,new AsyncHttpResponseCallback()
        {
            @Override
            public void onSuccess(Response response)
            {
                List<UserQuery> users = (List<UserQuery>) response.getPayload();
                UserQuery user = null;
                if (users != null && users.size() > 0)
                {
                    user = users.get(0);
                }
                if (user != null)
                {
                    PersonalDataFragment fragment = new PersonalDataFragment(user, isUser);
                    getFragmentManager().beginTransaction().replace(R.id.layoutForFragment, fragment).commit();
                } else
                {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    Button button = new Button(PersonalDataActivity.this);
                    button.setLayoutParams(params);
                    button.setText("获取用户错误，点击返回");
                    button.setTextSize(18);
                    button.setBackgroundColor(getResources().getColor(R.color.lightblue));
                    button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            PersonalDataActivity.this.finish();
                        }
                    });
                    fl_forFragment.addView(button);
                }
            }

            @Override
            public void onFailure(Response response)
            {

            }
        });
    }
    
}
