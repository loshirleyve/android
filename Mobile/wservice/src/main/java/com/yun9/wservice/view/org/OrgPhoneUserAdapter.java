package com.yun9.wservice.view.org;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.PhoneUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/6/1.
 */
public class OrgPhoneUserAdapter extends JupiterAdapter {

    private Context mContext;

    private List<PhoneUser> users;

    private ResourceFactory resourceFactory;

    private String userid;

    private String instid;

    private String orgid;


    public OrgPhoneUserAdapter(Context context, ResourceFactory resourceFactory, List<PhoneUser> users, String instid, String userid, String orgid) {
        this.mContext = context;
        this.resourceFactory = resourceFactory;
        this.users = users;
        this.instid = instid;
        this.userid = userid;
        this.orgid = orgid;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(users)) {
            return users.size();
        } else {
            return 0;
        }
    }


    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhoneUser user = users.get(position);
        JupiterRowStyleSutitleLayout item = null;
        if (!AssertValue.isNotNull(convertView)) {
            item = new JupiterRowStyleSutitleLayout(mContext);
            item.setShowTime(false);
            item.setShowArrow(false);
            item.setMainContentGravity(Gravity.CENTER);
            item.getArrowRightButton().setText(R.string.invitation_reg);
            item.getArrowRightButton().setTextColor(mContext.getResources().getColor(R.color.whites));
            item.getArrowRightButton().setBackgroundResource(R.drawable.button_background);
            item.getArrowRightButton().setOnClickListener(onAddUserOrgClickListener);
        } else {
            item = (JupiterRowStyleSutitleLayout) convertView;
        }

        if (!user.isregister()) {
            item.setShowArrowButton(true);
        } else {
            item.setShowArrowButton(false);
        }
        item.getTitleTV().setText(user.getUsername());
        item.getSutitleTv().setText(user.getUsernumber());
        item.select(user.isregister());
        item.getArrowRightButton().setTag(user);
        return item;
    }


    private View.OnClickListener onAddUserOrgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            inviteUser((PhoneUser) v.getTag());
        }
    };

    private void inviteUser(final PhoneUser user) {
        if (AssertValue.isNotNull(user)) {
            final ProgressDialog progressDialog = ProgressDialog.show(mContext, null, mContext.getResources().getString(R.string.app_wating), true);
            Resource resource = resourceFactory.create("InviteUser");
            resource.param("instid", instid);
            resource.param("userid", userid);
            resource.param("phoneNum", user.getUsernumber());
            resource.param("deptid", orgid);
            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    Map<String, String> msginfo = (Map<String, String>) response.getPayload();
                    if (AssertValue.isNotNullAndNotEmpty(msginfo)) {
                        alertDialog(user.getUsername(),user.getUsernumber(), (String) msginfo.get("message"));
                    }
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(mContext, response.getCause(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFinally(Response response) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void alertDialog(String userName, final String userNumber, final String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(message);
        builder.setTitle("发送信息给 "+userName+"("+userNumber+")：");
        builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                sendMsgInfo(userNumber,message);
            }
        });
        builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    protected void sendMsgInfo(String phonenumber, String msginfo) {
        SmsManager smsManager = SmsManager.getDefault();//得到短信管理器
        ArrayList<String> texts = smsManager.divideMessage(msginfo);//拆分短信,短信字数太多了的时候要分几次发
        smsManager.sendMultipartTextMessage(phonenumber, null, texts, null, null);//发送短信,mobile是对方手机号
        Toast.makeText(mContext, R.string.orguserphone_success_tip, Toast.LENGTH_LONG).show();//提示成功
        ((Activity)mContext).setResult(JupiterCommand.RESULT_CODE_OK);// 邀请了用户后，设置操作OK，用户返回刷新
    }


}
