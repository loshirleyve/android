package com.yun9.wservice.view.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.payment.PaymentOrderCommand;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class InputTextActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.edit_text)
    private EditText editText;

    @ViewInject(id=R.id.confirm_ll)
    private LinearLayout confirmLl;

    private InputTextCommand command;

    public static void start(Activity activity,InputTextCommand command) {
        Intent intent =  new Intent(activity,InputTextActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND,command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (InputTextCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        buildView();
    }

    private void buildView() {
        if (AssertValue.isNotNullAndNotEmpty(command.getTitle())) {
            titleBarLayout.getTitleTv().setText(command.getTitle());
        }
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputTextActivity.this.finish();
            }
        });

        if (AssertValue.isNotNullAndNotEmpty(command.getValue())){
            editText.setText(command.getValue());
        }


        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeInput();
            }
        });
    }

    private void completeInput() {
        String value = editText.getText().toString();
        if (command.getRegularMap() != null){
           Iterator<String> keyset =  command.getRegularMap().keySet().iterator();
            String key;
            while (keyset.hasNext()) {
                key = keyset.next();
                if (!value.matches(key)){
                    showToast(command.getRegularMap().get(key));
                    return;
                }
            }
        }
        if (command.getMinValue() != null
                && Double.valueOf(value) < command.getMinValue()){
            showToast("数值不能小于："+command.getMinValue());
            return;
        }
        if (command.getMinValue() != null
                && Double.valueOf(value) > command.getMaxValue()){
            showToast("数值不能大于："+command.getMaxValue());
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(JupiterCommand.RESULT_PARAM,value);
        setResult(JupiterCommand.RESULT_CODE_OK,intent);
        this.finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_input_text;
    }
}
