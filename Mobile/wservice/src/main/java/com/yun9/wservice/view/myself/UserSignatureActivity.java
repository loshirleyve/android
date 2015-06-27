package com.yun9.wservice.view.myself;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by li on 2015/6/25.
 */
public class UserSignatureActivity extends JupiterFragmentActivity{
    @ViewInject(id = R.id.signature_title)
    private JupiterTitleBarLayout jupiterTitleBarLayout;

    @ViewInject(id = R.id.signature_content)
    private EditText signatureContent;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    public static void start(Activity activity, UserSignatureCommand command){
        Intent intent = new Intent(activity, UserSignatureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(UserSignatureCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_user_signature;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jupiterTitleBarLayout.getTitleLeft().setOnClickListener(BackOnclickListener);

        Intent intent = getIntent();
        if(intent != null) {
            UserSignatureCommand userSignatureCommand = (UserSignatureCommand)intent.getSerializableExtra(UserSignatureCommand.PARAM_COMMAND);
            signatureContent.setText(userSignatureCommand.getSignature());
        }
    }

    private void upadteSignature(){
        if(AssertValue.isNotNull(sessionManager.getInst()) && AssertValue.isNotNull(sessionManager.getUser())){
            Resource resource = resourceFactory.create("UpdateUserBySignature");
            resource.param("userid", sessionManager.getUser().getId());
            resource.param("instid", sessionManager.getInst().getId());
            resource.param("signature", signatureContent.getText());
        }
    }

    private View.OnClickListener BackOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            upadteSignature();
            finish();
        }

    };
}
