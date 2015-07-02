package com.yun9.wservice.view.org;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
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
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterSearchInputLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.PhoneUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Leon on 15/6/2.
 */
public class OrgPhoneUserActivity extends JupiterFragmentActivity {

    private Context mContext;

    private OrgPhoneUserCommand command;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;


    private JupiterSearchInputLayout jupiterSearchInputLayout;

    @ViewInject(id = R.id.userlist)
    private ListView userListView;

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeButton;



    private List<PhoneUser> users;


    public static void start(Activity activity, OrgPhoneUserCommand command) {
        Intent intent = new Intent(activity, OrgPhoneUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, OrgChooseAddUserCommand.REQUEST_CODE);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_org_phoneuser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (OrgPhoneUserCommand) this.getIntent().getSerializableExtra("command");
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        initView();
    }

    public void initView()
    {
        mContext=getApplicationContext();
        getPhoneContracts(mContext);
        getSimContracts(mContext);
    }


    List<String> mContactsName=new ArrayList<>();
    List<String> mContactsNumber=new ArrayList<>();

//    private void getPhoneContacts() {
//        ContentResolver resolver = this.getContentResolver();
//
//        // 获取手机联系人
//        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                new String[] { Phone.CONTACT_ID, Phone.DISPLAY_NAME,
//                        Phone.NUMBER },
//                Phone.DISPLAY_NAME + "=?" + " AND " + Phone.TYPE + "='"
//                        + Phone.TYPE_MOBILE + "'", new String[] { name }, null);
//        if (phoneCursor != null) {
//            while (phoneCursor.moveToNext()) {
//                String number = phoneCursor.getString(2);
//                // 当手机号码为空的或者为空字段 跳过当前循环
//                if (TextUtils.isEmpty(number))
//                    continue;
//                // 得到联系人名称
//                String username = phoneCursor.getString(1);
//                mContactsName.add(number);
//                mContactsNumber.add(continue);
//
//            }
//            phoneCursor.close();
//        }
//    }



    //取本机通讯录
    public  HashMap<String, PhoneUser> getPhoneContracts(Context mContext){
        HashMap<String, PhoneUser> map = new HashMap<String, PhoneUser>();
        ContentResolver resolver = mContext.getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,null, null, null, null); //传入正确的uri
        if(phoneCursor!=null){
            while(phoneCursor.moveToNext()){
                int nameIndex = phoneCursor.getColumnIndex(Phone.DISPLAY_NAME); //获取联系人name
                String name = phoneCursor.getString(nameIndex);
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.NUMBER)); //获取联系人number
                if(TextUtils.isEmpty(phoneNumber)){
                    continue;
                }
                //以下是我自己的数据封装。
                PhoneUser user = new PhoneUser();
                user.setUsername(name);
                user.setUsernumber(phoneNumber);
                map.put(phoneNumber, user);
            }
            System.out.print(map.size());
            phoneCursor.close();
        }
        return map;
    }


   // 接下来看获取sim卡的方法，sim卡的uri有两种可能content://icc/adn与content://sim/adn （一般情况下是第一种）
    public  HashMap<String, PhoneUser> getSimContracts(Context mContext){
   //读取SIM卡手机号,有两种可能:content://icc/adn与content://sim/adn
        HashMap<String, PhoneUser> map = new HashMap<String, PhoneUser>();

        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri,null, null, null, null);
        if(phoneCursor!=null){
            while(phoneCursor.moveToNext()){
                String name = phoneCursor.getString(phoneCursor.getColumnIndex("name"));
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex("number"));
                if(TextUtils.isEmpty(phoneNumber)){
                    continue;
                }
                //以下是我自己的数据封装。
                PhoneUser user = new PhoneUser();
                user.setUsername(name);
                user.setUsernumber(phoneNumber);
                map.put(phoneNumber, user);
            }
            System.out.print(map.size());
            phoneCursor.close();
        }
        return map;
    }


    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}
