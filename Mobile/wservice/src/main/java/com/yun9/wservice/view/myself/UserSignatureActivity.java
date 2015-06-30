package com.yun9.wservice.view.myself;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
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
public class UserSignatureActivity extends JupiterFragmentActivity {

    private UserSignatureCommand command;

    @ViewInject(id = R.id.signature_title)
    private JupiterTitleBarLayout jupiterTitleBarLayout;

    @ViewInject(id = R.id.signature_content)
    private EditText signatureContent;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    public static void start(Activity activity, UserSignatureCommand command) {
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

        jupiterTitleBarLayout.getTitleLeft().setOnClickListener(onBackOnclickListener);
        jupiterTitleBarLayout.getTitleRight().setOnClickListener(onOkClickListener);

        Intent intent = getIntent();
        if (intent != null) {
            command = (UserSignatureCommand) intent.getSerializableExtra(UserSignatureCommand.PARAM_COMMAND);

            if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSignature())) {
                signatureContent.setText(command.getSignature());
                CharSequence text = signatureContent.getText();
                //Debug.asserts(text instanceof Spannable);
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
            }
        }
    }

    private void upadteSignature() {
        Intent intent = new Intent();
        intent.putExtra(UserSignatureCommand.PARAM_SIGNATURE, signatureContent.getText().toString());
        setResult(UserSignatureCommand.RESULT_CODE_OK, intent);
    }

    private View.OnClickListener onBackOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }

    };

    private View.OnClickListener onOkClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            upadteSignature();
            finish();
        }
    };
}
