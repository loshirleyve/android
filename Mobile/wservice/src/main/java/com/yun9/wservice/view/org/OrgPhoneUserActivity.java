package com.yun9.wservice.view.org;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;
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
import com.yun9.jupiter.util.PinYinMaUtil;
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
import java.util.Map;

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

    private HashMap<String, PhoneUser> contactusers;

    private OrgPhoneUserAdapter phoneUserAdapter;

    private List<PhoneUser> phoneusers;

    private List<User> users;

    private List<PhoneUser> textwatchusers;

    private List<String> phonenumbers;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private String userid;

    private String instid;


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
        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getUserid())) {
            userid = command.getUserid();
        }

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getInstid())) {
            instid = command.getInstid();
        }
        if (!AssertValue.isNotNullAndNotEmpty(userid)) {
            userid = sessionManager.getUser().getId();
        }

        if (!AssertValue.isNotNullAndNotEmpty(instid)) {
            instid = sessionManager.getInst().getId();
        }
        jupiterSearchInputLayout = (JupiterSearchInputLayout) this.findViewById(R.id.searchRL);
        jupiterSearchInputLayout.getSearchET().addTextChangedListener(textWatcher);
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        initView();
    }

    public void initView() {
        mContext = getApplicationContext();
        phoneusers = new ArrayList<>();
        textwatchusers = new ArrayList<>();
        phonenumbers = new ArrayList<>();
        getPhoneContracts(mContext);//获取本机储存的电话号码
        getSimContracts(mContext);//获取sim卡储存的电话号码
        getRegisterUsers();
        if (AssertValue.isNotNullAndNotEmpty(contactusers)) {
            phoneusers.addAll(contactusers.values());
            textwatchusers.addAll(phoneusers);
        }
    }

    public void getRegisterUsers() {
        if (AssertValue.isNotNullAndNotEmpty(phonenumbers)) {
           final ProgressDialog registerDialog = ProgressDialog.show(OrgPhoneUserActivity.this, null, getResources().getString(R.string.app_wating), true);
            Resource resource = resourceFactory.create("QueryUsersByPhones");
            resource.param("instid", instid);
            resource.param("phones", phonenumbers);
            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    users = (List<User>) response.getPayload();
                    setIsRegister();
                }

                @Override
                public void onFailure(Response response) {

                }

                @Override
                public void onFinally(Response response) {
                    registerDialog.dismiss();
                }
            });
        }
    }


    public void setIsRegister() {
        if (AssertValue.isNotNullAndNotEmpty(contactusers)) {
            if (AssertValue.isNotNullAndNotEmpty(users)) {
                for (User user : users) {
                    PhoneUser pu = contactusers.get(user.getPhone());
                    if (AssertValue.isNotNull(pu))
                        pu.setIsregister(true);
                }
            }
            phoneUserAdapter = new OrgPhoneUserAdapter(this, resourceFactory, phoneusers, instid, userid, command.getOrgid());
            userListView.setAdapter(phoneUserAdapter);
        }
    }


    //取本机通讯录
    private HashMap<String, PhoneUser> getPhoneContracts(Context mContext) {
        contactusers = new HashMap<>();
        ContentResolver resolver = mContext.getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, null, null, null, null); //传入正确的uri
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                int nameIndex = phoneCursor.getColumnIndex(Phone.DISPLAY_NAME); //获取联系人name
                String name = phoneCursor.getString(nameIndex);
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.NUMBER)); //获取联系人number
                if (TextUtils.isEmpty(phoneNumber)) {
                    continue;
                }
                phoneNumber = phoneNumber.replace("-", "").replace(" ", "");
                if (phoneNumber.length() > 11)
                    phoneNumber = phoneNumber.substring(phoneNumber.length() - 11, phoneNumber.length());
                //以下是我自己的数据封装。
                if (phoneNumber.length() == 11) {
                    PhoneUser user = new PhoneUser();
                    user.setUsername(name);
                    user.setUsernumber(phoneNumber);
                    user.setIsregister(false);
                    phonenumbers.add(phoneNumber);
                    contactusers.put(phoneNumber, user);
                }
            }
            System.out.print(contactusers.size());
            phoneCursor.close();
        }
        return contactusers;
    }

    // 接下来看获取sim卡的方法，sim卡的uri有两种可能content://icc/adn与content://sim/adn （一般情况下是第一种）
    private HashMap<String, PhoneUser> getSimContracts(Context mContext) {
        //读取SIM卡手机号,有两种可能:content://icc/adn与content://sim/adn
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://icc/adn");
        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (manager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
            Cursor phoneCursor = resolver.query(uri, null, null, null, null);
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    String name = phoneCursor.getString(phoneCursor.getColumnIndex("name"));
                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex("number"));
                    if (TextUtils.isEmpty(phoneNumber)) {
                        continue;
                    }
                    phoneNumber = phoneNumber.replace("-", "").replace(" ", "");
                    if (phoneNumber.length() > 11)
                        phoneNumber = phoneNumber.substring(phoneNumber.length() - 11, phoneNumber.length());
                    //以下是我自己的数据封装。
                    if (phoneNumber.length() == 11) {
                        PhoneUser user = new PhoneUser();
                        user.setUsername(name);
                        user.setUsernumber(phoneNumber);
                        user.setIsregister(false);
                        phonenumbers.add(phoneNumber);
                        contactusers.put(phoneNumber, user);
                    }
                }
                System.out.print(contactusers.size());
                phoneCursor.close();
            }
        }
        return contactusers;
    }


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
            phoneusers.clear();
            if (AssertValue.isNotNullAndNotEmpty(s.toString())) {
                for (PhoneUser user : textwatchusers) {
                    if (StringUtil.contains(user.getUsername(), s.toString(), true)
                            || StringUtil.contains(PinYinMaUtil.stringToPinyin(user.getUsername(),true),
                                                        s.toString(), true)) {
                        phoneusers.add(user);
                    }
                }
            } else {
                phoneusers.addAll(textwatchusers);
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
}
