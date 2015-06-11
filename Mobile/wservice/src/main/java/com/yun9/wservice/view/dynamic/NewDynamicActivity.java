package com.yun9.wservice.view.dynamic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/11.
 */
public class NewDynamicActivity extends JupiterFragmentActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_new_dynamic;
    }

    public static void start(Activity activity, NewDynamicCommand command) {
        Intent intent = new Intent(activity, NewDynamicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, NewDynamicCommand.REQUEST_CODE);
    }



}
