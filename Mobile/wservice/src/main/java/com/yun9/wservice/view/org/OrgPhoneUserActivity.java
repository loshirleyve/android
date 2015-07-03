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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.StringUtil;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Leon on 15/6/2.
 */
public class OrgPhoneUserActivity extends JupiterFragmentActivity {

    private Context mContext;

    private OrgPhoneUserCommand command;

    private JupiterSearchInputLayout jupiterSearchInputLayout;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.userlist)
    private ListView userListView;

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeButton;

    private HashMap<String, PhoneUser> contactusers =null;

    private OrgPhoneUserAdapter phoneUserAdapter;

    private List<PhoneUser> users;

    private List<PhoneUser> textwatchusers;

    public static void start(Activity activity, OrgPhoneUserCommand command) {
        Intent intent = new Intent(activity, OrgPhoneUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, OrgPhoneUserCommand.REQUEST_CODE);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_org_phoneuser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (OrgPhoneUserCommand) this.getIntent().getSerializableExtra("command");
        jupiterSearchInputLayout = (JupiterSearchInputLayout) this.findViewById(R.id.searchRL);
        jupiterSearchInputLayout.getSearchET().addTextChangedListener(textWatcher);
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        completeButton.setOnClickListener(onClickCompletionListener);
        initView();
    }

    public void initView()
    {
        mContext=getApplicationContext();
        users=new ArrayList<>();
        textwatchusers=new ArrayList<>();
        getPhoneContracts(mContext);//获取本机储存的电话号码
        getSimContracts(mContext);//获取sim卡储存的电话号码
        if(AssertValue.isNotNullAndNotEmpty(contactusers)) {
            users.addAll(contactusers.values());
            textwatchusers.addAll(users);
        }
        else//构造测试数据
        {
            users.addAll(build());
            textwatchusers.addAll(users);
        }
        phoneUserAdapter=new OrgPhoneUserAdapter(mContext,users);
        userListView.setAdapter(phoneUserAdapter);
    }

    //取本机通讯录
    public  HashMap<String, PhoneUser> getPhoneContracts(Context mContext){
        contactusers = new HashMap<>();
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
                contactusers.put(phoneNumber, user);
            }
            System.out.print(contactusers.size());
            phoneCursor.close();
        }
        return contactusers;
    }


   // 接下来看获取sim卡的方法，sim卡的uri有两种可能content://icc/adn与content://sim/adn （一般情况下是第一种）
    public  HashMap<String, PhoneUser> getSimContracts(Context mContext){
        //读取SIM卡手机号,有两种可能:content://icc/adn与content://sim/adn
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
                contactusers.put(phoneNumber, user);
            }
            System.out.print(contactusers.size());
            phoneCursor.close();
        }
        return contactusers;
    }

    private View.OnClickListener onClickCompletionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<PhoneUser> selectuser = new ArrayList<>();
            if (AssertValue.isNotNullAndNotEmpty(users)) {
                for (PhoneUser u : users) {
                    if (u.isSelected()) {
                        PhoneUser user = new PhoneUser();
                        user.setUsername(u.getUsername());
                        user.setUsernumber(u.getUsernumber());
                        selectuser.add(user);
                    }
                }
            }

            Intent intent = new Intent();
            intent.putExtra(OrgPhoneUserCommand.PARAM_COMMAND, selectuser);
            setResult(OrgListCommand.RESULT_CODE_OK, intent);
            finish();
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        jupiterSearchInputLayout.getEditLL().setVisibility(View.GONE);
        jupiterSearchInputLayout.getShowLL().setVisibility(View.VISIBLE);
        inputMethodManager.hideSoftInputFromWindow(jupiterSearchInputLayout.getSearchET().getWindowToken(), 0);
        return super.dispatchTouchEvent(ev);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            users.clear();
            if (AssertValue.isNotNullAndNotEmpty(s.toString())) {
                for ( PhoneUser user : textwatchusers) {
                    if (StringUtil.contains(user.getUsername(), s.toString(), true)) {
                        users.add(user);
                    }
                }
            }
            else
            {
                users.addAll(textwatchusers);
            }
            phoneUserAdapter.notifyDataSetChanged();
        }
    };
    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };




    public List<PhoneUser> build()
    {
        users=new ArrayList<>();
        PhoneUser user=new PhoneUser();
        user.setUsername("阮小玉");
        user.setUsernumber("13697110552");

        PhoneUser user1=new PhoneUser();
        user1.setUsername("权志龙");
        user1.setUsernumber("1369101459");

        PhoneUser user2=new PhoneUser();
        user2.setUsername("崔胜贤");
        user2.setUsernumber("136984939204");

        PhoneUser user3=new PhoneUser();
        user3.setUsername("李胜利");
        user3.setUsernumber("13593796895");

        PhoneUser user4=new PhoneUser();
        user4.setUsername("东永裴");
        user4.setUsernumber("1369998778");

        PhoneUser user5=new PhoneUser();
        user5.setUsername("姜大声");
        user5.setUsernumber("135577960");

        users.add(user);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        return users;

    }
}
