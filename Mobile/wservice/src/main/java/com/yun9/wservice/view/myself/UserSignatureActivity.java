package com.yun9.wservice.view.myself;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
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
    private String userid;
    private String instid;
    private UserSignatureCommand command;
    private UserInfoCommand userInfoCommand;

    @ViewInject(id = R.id.signature_title)
    private JupiterTitleBarLayout TitleBarLayout;

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

        TitleBarLayout.getTitleLeft().setOnClickListener(onBackOnclickListener);
        TitleBarLayout.getTitleRight().setOnClickListener(onSureOnclickListener);

        Intent intent = getIntent();
        if(intent != null) {
            command = (UserSignatureCommand)intent.getSerializableExtra(UserSignatureCommand.PARAM_COMMAND);
            if(!AssertValue.isNotNull(command) || !AssertValue.isNotNullAndNotEmpty(command.getUserid())){
                userid = command.getUserid();
                sessionManager.getUser().setId(userid);
            }
            else {
                userid = sessionManager.getUser().getId();
            }

            if(!AssertValue.isNotNull(command) || !AssertValue.isNotNullAndNotEmpty(command.getInstid())){
                instid = command.getInstid();
                sessionManager.getInst().setId(instid);
            }
            else {
                instid = sessionManager.getInst().getId();
            }

            signatureContent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fresh();
                }
            }, 100);
        }
    }

    private void fresh(){
        if(AssertValue.isNotNull(sessionManager.getUser()) && AssertValue.isNotNull(sessionManager.getInst())){
            final Resource resource = resourceFactory.create("QueryUserInfoByIdService");
            resource.param("userid", userid);
            resource.param("instid", instid);

            final ProgressDialog progressDialog = ProgressDialog.show(this, null, getString(R.string.app_wating), true);

            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    User user = (User) response.getPayload();
                    signatureContent.setText(user.getSignature());
                    CharSequence text = signatureContent.getText();
                    //Debug.asserts(text instanceof Spannable);
                    if (text instanceof Spannable) {
                        Spannable spanText = (Spannable) text;
                        Selection.setSelection(spanText, text.length());
                    }
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    progressDialog.dismiss();
                }
            });
        }
    }
    private void uploadSignature(){
        Intent intent = new Intent();
        intent.putExtra(UserSignatureCommand.PARAM_SIGNATURE_COMMAND, signatureContent.getText().toString());
        setResult(UserSignatureCommand.RESULT_CODE_OK, intent);
    }
    private View.OnClickListener onBackOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener onSureOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            uploadSignature();
            finish();
        }
    };
}
