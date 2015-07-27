package com.yun9.wservice.view.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.cache.CtrlCodeCache;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterEditableView;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.CtrlCodeDefNo;
import com.yun9.wservice.model.Client;
import com.yun9.wservice.model.MdInstScale;
import com.yun9.wservice.model.MdInstScales;
import com.yun9.wservice.view.common.MultiSelectActivity;
import com.yun9.wservice.view.common.MultiSelectCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by li on 2015/7/20.
 */
public class ClientDetailActivity extends JupiterFragmentActivity {
    private List<SerialableEntry<String, String>> optionMap = new ArrayList<>();
    @BeanInject
    SessionManager sessionManager;
    @BeanInject
    ResourceFactory resourceFactory;
    @ViewInject(id = R.id.client_detail_title)
    private JupiterTitleBarLayout titleBarLayout;
    @ViewInject(id = R.id.clientNoEt)
    private EditText clientNoEt;
    @ViewInject(id = R.id.companyAbbrNameEt)
    private EditText companyAbbrNameEt;
    @ViewInject(id = R.id.companyFullNameEt)
    private EditText companyFullNameEt;
    @ViewInject(id = R.id.typeLayout)
    private JupiterRowStyleSutitleLayout typeLayout;
    @ViewInject(id = R.id.industryLayout)
    private JupiterRowStyleSutitleLayout industryLayout;
    @ViewInject(id = R.id.instScaleLayout)
    private JupiterRowStyleSutitleLayout instScaleLayout;
    @ViewInject(id = R.id.sourceLayout)
    private JupiterRowStyleSutitleLayout sourceLayout;
    @ViewInject(id = R.id.regionEt)
    private EditText regionEt;
    @ViewInject(id = R.id.detailInfoEt)
    private EditText detailInfoEt;
    @ViewInject(id = R.id.contactNameEt)
    private EditText contactNameEt;
    @ViewInject(id = R.id.contactPhoneEt)
    private EditText contactPhoneEt;
    @ViewInject(id = R.id.postLayout)
    private JupiterRowStyleSutitleLayout postLayout;
    @ViewInject(id = R.id.clientRankLayout)
    private JupiterRowStyleSutitleLayout clientRankLayout;
    @ViewInject(id = R.id.client_detail_sure)
    private TextView clientDetailSure;
    private EditClientCommand command;
    private String clientid;
    private MultiSelectCommand multiSelectCommand;
    private String type;
    private String industry;
    private String instScale;
    private String source;
    private String post;
    private String clientRank;
    private List<Client> clients = new ArrayList<Client>();
    private List<JupiterEditableView> itemList;

    public static void start(Activity activity, EditClientCommand command) {
        Intent intent = new Intent(activity, ClientDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_client_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        command = (EditClientCommand) getIntent().getSerializableExtra("command");
        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getClientId())) {
            clientid = command.getClientId();
        }
        if (AssertValue.isNotNullAndNotEmpty(clientid)) {
            queryInstClientById(clientid);
        }
        titleBarLayout.getTitleLeftIV().setOnClickListener(onBackClickListener);
        clientDetailSure.setOnClickListener(onSureClickListener);
        typeLayout.setOnClickListener(onTypeClickListener);
        industryLayout.setOnClickListener(onIndustrylickListener);
        instScaleLayout.setOnClickListener(onInstScaleClickListener);
        sourceLayout.setOnClickListener(onSourceClickListener);
        postLayout.setOnClickListener(onPostClickListener);
        clientRankLayout.setOnClickListener(onClientRankClickListener);
        if(!AssertValue.isNotNullAndNotEmpty(clientid)){
            clientNoEt.setOnFocusChangeListener(new OnFocusChangeListener());
            companyAbbrNameEt.setOnFocusChangeListener(new OnFocusChangeListener());
            contactPhoneEt.setOnFocusChangeListener(new OnFocusChangeListener());
        }
    }

    private View.OnClickListener onSureClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AssertValue.isNotNullAndNotEmpty(clientNoEt.getText().toString())) {
                Toast.makeText(mContext, getString(R.string.client_no_input_please), Toast.LENGTH_SHORT).show();
            } else if (!isClientNo(clientNoEt.getText().toString())) {
                Toast.makeText(mContext, getString(R.string.client_no_correct_not), Toast.LENGTH_SHORT).show();
            } else if (!AssertValue.isNotNullAndNotEmpty(companyAbbrNameEt.getText().toString())) {
                Toast.makeText(mContext, getString(R.string.company_abbrev_name_input), Toast.LENGTH_SHORT).show();
            } else if (!AssertValue.isNotNullAndNotEmpty(companyFullNameEt.getText().toString())) {
                Toast.makeText(mContext, getString(R.string.company_full_name_input), Toast.LENGTH_SHORT).show();
            } else if (typeLayout.getSutitleTv().getVisibility() == View.GONE) {
                Toast.makeText(mContext, getString(R.string.type_chose), Toast.LENGTH_SHORT).show();
            } else if (industryLayout.getSutitleTv().getVisibility() == View.GONE) {
                Toast.makeText(mContext, getString(R.string.industry_chose), Toast.LENGTH_SHORT).show();
            } else if (instScaleLayout.getSutitleTv().getVisibility() == View.GONE) {
                Toast.makeText(mContext, getString(R.string.inst_scale_chose), Toast.LENGTH_SHORT).show();
            } else if (sourceLayout.getSutitleTv().getVisibility() == View.GONE) {
                Toast.makeText(mContext, getString(R.string.source_chose), Toast.LENGTH_SHORT).show();
            } else if (!AssertValue.isNotNullAndNotEmpty(regionEt.getText().toString())) {
                Toast.makeText(mContext, getString(R.string.region_input), Toast.LENGTH_SHORT).show();
            } else if (!AssertValue.isNotNullAndNotEmpty(detailInfoEt.getText().toString())) {
                Toast.makeText(mContext, getString(R.string.detail_info_input), Toast.LENGTH_SHORT).show();
            } else if (!AssertValue.isNotNullAndNotEmpty(contactNameEt.getText().toString())) {
                Toast.makeText(mContext, getString(R.string.contact_name_input), Toast.LENGTH_SHORT).show();
            } else if (!AssertValue.isNotNullAndNotEmpty(contactPhoneEt.getText().toString())) {
                Toast.makeText(mContext, getString(R.string.contact_phone_input), Toast.LENGTH_SHORT).show();
            } else if (postLayout.getSutitleTv().getVisibility() == View.GONE) {
                Toast.makeText(mContext, getString(R.string.post_chose), Toast.LENGTH_SHORT).show();
            } else if (clientRankLayout.getSutitleTv().getVisibility() == View.GONE) {
                Toast.makeText(mContext, getString(R.string.client_rank_input), Toast.LENGTH_SHORT).show();
            } else if (clientNoEt.getText().toString().equals(companyAbbrNameEt.getText().toString())
                    || clientNoEt.getText().toString().equals(companyFullNameEt.getText().toString())) {
                Toast.makeText(mContext, getString(R.string.client_no_name_same_not), Toast.LENGTH_SHORT).show();
            } else if (!isPhone(contactPhoneEt.getText().toString())) {
                Toast.makeText(mContext, getString(R.string.correct_phone_no_not), Toast.LENGTH_SHORT).show();
            } else {
                doSave();
            }
        }
    };

    private void doSave() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("AddOrUpdateInstClientsService");
        resource.param("id", clientid);
        resource.param("sn", clientNoEt.getText().toString());
        resource.param("instid", sessionManager.getInst().getId());
        resource.param("name", companyAbbrNameEt.getText().toString());
        resource.param("fullname", companyFullNameEt.getText().toString());
        resource.param("type", type);
        resource.param("level", clientRank);
        resource.param("contactman", contactNameEt.getText().toString());
        resource.param("contactphone", contactPhoneEt.getText().toString());
        resource.param("region", regionEt.getText().toString());
        resource.param("address", detailInfoEt.getText().toString());
        resource.param("source", source);
        resource.param("industry", industry);
        resource.param("scaleid", instScale);
        resource.param("contactposition", post);
        resource.param("createby", sessionManager.getUser().getId());

        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {

            @Override
            public void onSuccess(Response response) {
                showToast(getString(R.string.save_client_success_not));
                doSuccess();
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                registerDialog.dismiss();
            }
        });
    }

    private void doSuccess() {
        setResult(JupiterCommand.RESULT_CODE_OK);
        this.finish();
    }

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener onTypeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (multiSelectCommand == null) {
                multiSelectCommand = new MultiSelectCommand();
            }
            MultiSelectCommand.FLAG = "type";
            getOptions(v, "clienttype", null);
        }
    };
    private View.OnClickListener onIndustrylickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (multiSelectCommand == null) {
                multiSelectCommand = new MultiSelectCommand();
            }
            MultiSelectCommand.FLAG = "industry";
            getOptions(v, "clientindustry", null);
        }
    };
    private View.OnClickListener onInstScaleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (multiSelectCommand == null) {
                multiSelectCommand = new MultiSelectCommand();
            }
            MultiSelectCommand.FLAG = "instScale";
            getOptionMap(v);
        }
    };
    private View.OnClickListener onSourceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (multiSelectCommand == null) {
                multiSelectCommand = new MultiSelectCommand();
            }
            MultiSelectCommand.FLAG = "source";
            getOptions(v, "clientsource", null);
        }
    };
    private View.OnClickListener onPostClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (multiSelectCommand == null) {
                multiSelectCommand = new MultiSelectCommand();
            }
            MultiSelectCommand.FLAG = "post";
            getOptions(v, "contactposition", null);
        }
    };
    private View.OnClickListener onClientRankClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (multiSelectCommand == null) {
                multiSelectCommand = new MultiSelectCommand();
            }
            MultiSelectCommand.FLAG = "clientRank";
            getOptions(v, "clientlevel", null);
        }
    };

    private void getOptions(View v, String ctrlCode, List<SerialableEntry<String, String>> optionMap) {
        if (AssertValue.isNotNull(ctrlCode) && !AssertValue.isNotNull(optionMap)) {
            multiSelectCommand.setCtrlCode(ctrlCode);
            multiSelectCommand.setOptions(null);
        } else {
            multiSelectCommand.setOptions(optionMap);
            multiSelectCommand.setCtrlCode(null);
        }
        multiSelectCommand.setMaxNum(1);
        multiSelectCommand.setIsCancelable(true);
        switch (v.getId()){
            case R.id.typeLayout:
                if(AssertValue.isNotNullAndNotEmpty(typeLayout.getSutitleTv().getText().toString()) && AssertValue.isNotNullAndNotEmpty(type)) {
                    SerialableEntry<String, String> object = new SerialableEntry<String, String>(type, typeLayout.getSutitleTv().getText().toString());
                    List<SerialableEntry<String, String>> list = new ArrayList<>();
                    list.add(object);
                    multiSelectCommand.setSelectedList(list);
                }
                break;
            case R.id.industryLayout:
                if(AssertValue.isNotNullAndNotEmpty(industryLayout.getSutitleTv().getText().toString()) && AssertValue.isNotNullAndNotEmpty(industry)) {
                    SerialableEntry<String, String> object = new SerialableEntry<String, String>(industry, industryLayout.getSutitleTv().getText().toString());
                    List<SerialableEntry<String, String>> list = new ArrayList<>();
                    list.add(object);
                    multiSelectCommand.setSelectedList(list);
                }
                break;
            case R.id.instScaleLayout:
                if(AssertValue.isNotNullAndNotEmpty(instScaleLayout.getSutitleTv().getText().toString()) && AssertValue.isNotNullAndNotEmpty(instScale)) {
                    SerialableEntry<String, String> object = new SerialableEntry<String, String>(instScale, instScaleLayout.getSutitleTv().getText().toString());
                    List<SerialableEntry<String, String>> list = new ArrayList<>();
                    list.add(object);
                    multiSelectCommand.setSelectedList(list);
                }
                break;
            case R.id.sourceLayout:
                if(AssertValue.isNotNullAndNotEmpty(sourceLayout.getSutitleTv().getText().toString()) && AssertValue.isNotNullAndNotEmpty(source)) {
                    SerialableEntry<String, String> object = new SerialableEntry<String, String>(source, sourceLayout.getSutitleTv().getText().toString());
                    List<SerialableEntry<String, String>> list = new ArrayList<>();
                    list.add(object);
                    multiSelectCommand.setSelectedList(list);
                }
                break;
            case R.id.postLayout:
                if(AssertValue.isNotNullAndNotEmpty(postLayout.getSutitleTv().getText().toString()) && AssertValue.isNotNullAndNotEmpty(post)) {
                    SerialableEntry<String, String> object = new SerialableEntry<String, String>(post, postLayout.getSutitleTv().getText().toString());
                    List<SerialableEntry<String, String>> list = new ArrayList<>();
                    list.add(object);
                    multiSelectCommand.setSelectedList(list);
                }
                break;
            case R.id.clientRankLayout:
                if(AssertValue.isNotNullAndNotEmpty(clientRankLayout.getSutitleTv().getText().toString()) && AssertValue.isNotNullAndNotEmpty(clientRank)) {
                    SerialableEntry<String, String> object = new SerialableEntry<String, String>(clientRank, clientRankLayout.getSutitleTv().getText().toString());
                    List<SerialableEntry<String, String>> list = new ArrayList<>();
                    list.add(object);
                    multiSelectCommand.setSelectedList(list);
                }
                break;
        }
        MultiSelectActivity.start(ClientDetailActivity.this, multiSelectCommand);
    }
    private void getCurrentScaleId(final String scaleid) {
        Resource resource = resourceFactory.create("QueryMdInstScale");
        resource.param("userid", sessionManager.getUser().getId());
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, getString(R.string.app_wating));
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                MdInstScales mdInstScales = (MdInstScales) response.getPayload();
                if (AssertValue.isNotNull(mdInstScales) && AssertValue.isNotNullAndNotEmpty(mdInstScales.getBizMdInstScales())) {
                    for (int i = 0; i < mdInstScales.getBizMdInstScales().size(); i++) {
                        if (mdInstScales.getBizMdInstScales().get(i).getType().equals(scaleid)) {
                            instScaleLayout.getSutitleTv().setText(mdInstScales.getBizMdInstScales().get(i).getName());
                        }
                    }
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
    private void getOptionMap(final View v) {
        optionMap.clear();
        Resource resource = resourceFactory.create("QueryMdInstScale");
        resource.param("userid", sessionManager.getUser().getId());
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, getString(R.string.app_wating));
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                MdInstScales mdInstScales = (MdInstScales) response.getPayload();
                if (AssertValue.isNotNull(mdInstScales) && AssertValue.isNotNullAndNotEmpty(mdInstScales.getBizMdInstScales())) {
                    for (int i = 0; i < mdInstScales.getBizMdInstScales().size(); i++) {
                        optionMap.add(i, new SerialableEntry<String, String>(mdInstScales.getBizMdInstScales().get(i).getType()
                                , mdInstScales.getBizMdInstScales().get(i).getName()));
                    }
                }
                getOptions(v, null, optionMap);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == multiSelectCommand.getRequestCode() && resultCode == JupiterCommand.RESULT_CODE_OK) {
            List<SerialableEntry<String, String>> selectedList =
                    (List<SerialableEntry<String, String>>) data.getSerializableExtra("selectedList");
            switch (MultiSelectCommand.FLAG) {
                case "type":
                    if (AssertValue.isNotNull(selectedList) && selectedList.size() != 0 && (AssertValue.isNotNullAndNotEmpty(selectedList.get(0).getValue()))) {
                        typeLayout.getSutitleTv().setVisibility(View.VISIBLE);
                        typeLayout.getSutitleTv().setText(selectedList.get(0).getValue());
                        type = selectedList.get(0).getKey();
                    } else if (AssertValue.isNotNull(selectedList) && selectedList.size() == 0) {
                        typeLayout.getSutitleTv().setVisibility(View.GONE);
                    }
                    break;
                case "industry":
                    if (AssertValue.isNotNull(selectedList) && selectedList.size() != 0 && (AssertValue.isNotNullAndNotEmpty(selectedList.get(0).getValue()))) {
                        industryLayout.getSutitleTv().setVisibility(View.VISIBLE);
                        industryLayout.getSutitleTv().setText(selectedList.get(0).getValue());
                        industry = selectedList.get(0).getKey();
                    } else {
                        industryLayout.getSutitleTv().setVisibility(View.GONE);
                    }
                    break;
                case "instScale":
                    if (AssertValue.isNotNull(selectedList) && selectedList.size() != 0 && (AssertValue.isNotNullAndNotEmpty(selectedList.get(0).getValue()))) {
                        instScaleLayout.getSutitleTv().setVisibility(View.VISIBLE);
                        instScaleLayout.getSutitleTv().setText(selectedList.get(0).getValue());
                        instScale = selectedList.get(0).getKey();
                    } else {
                        instScaleLayout.getSutitleTv().setVisibility(View.GONE);
                    }
                    break;
                case "source":
                    if (AssertValue.isNotNull(selectedList) && selectedList.size() != 0 && (AssertValue.isNotNullAndNotEmpty(selectedList.get(0).getValue()))) {
                        sourceLayout.getSutitleTv().setVisibility(View.VISIBLE);
                        sourceLayout.getSutitleTv().setText(selectedList.get(0).getValue());
                        source = selectedList.get(0).getKey();
                    } else {
                        sourceLayout.getSutitleTv().setVisibility(View.GONE);
                    }
                    break;
                case "post":
                    if (AssertValue.isNotNull(selectedList) && selectedList.size() != 0 && (AssertValue.isNotNullAndNotEmpty(selectedList.get(0).getValue()))) {
                        postLayout.getSutitleTv().setVisibility(View.VISIBLE);
                        postLayout.getSutitleTv().setText(selectedList.get(0).getValue());
                        post = selectedList.get(0).getKey();
                    } else {
                        postLayout.getSutitleTv().setVisibility(View.GONE);
                    }
                    break;
                case "clientRank":
                    if (AssertValue.isNotNull(selectedList) && selectedList.size() != 0 && (AssertValue.isNotNullAndNotEmpty(selectedList.get(0).getValue()))) {
                        clientRankLayout.getSutitleTv().setVisibility(View.VISIBLE);
                        clientRankLayout.getSutitleTv().setText(selectedList.get(0).getValue());
                        clientRank = selectedList.get(0).getKey();
                    } else {
                        clientRankLayout.getSutitleTv().setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }

    public boolean isPhone(String phone) {
        String str = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public boolean isClientNo(String clientNo) {
        String str = "^([a-zA-Z0-9]{6,8})$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(clientNo);
        return m.matches();
    }

    private void queryInstClientById(String clientid) {
        Resource resource = resourceFactory.create("QueryInstClientById");
        resource.param("instClient", clientid);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Client client = (Client) response.getPayload();
                displayClientData(client);
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }

    private void displayClientData(Client client) {
        clientNoEt.setText(client.getSn());
        clientNoEt.setEnabled(false);
        companyAbbrNameEt.setText(client.getName());
        companyFullNameEt.setText(client.getFullname());
        companyFullNameEt.setEnabled(false);
        typeLayout.getSutitleTv().setText(CtrlCodeCache.getInstance().getCtrlcodeName(CtrlCodeDefNo.CLIENT_TYPE_STATE, client.getType()));
        typeLayout.setShowSutitleText(true);
        type = client.getType();

        industryLayout.getSutitleTv().setText(CtrlCodeCache.getInstance().getCtrlcodeName(CtrlCodeDefNo.CLIENT_INDUSTRY_STATE, client.getIndustry()));
        industryLayout.setShowSutitleText(true);
        industry = client.getIndustry();

        getCurrentScaleId(client.getScaleid());
        instScaleLayout.setShowSutitleText(true);
        instScale = client.getScaleid();

        sourceLayout.getSutitleTv().setText(CtrlCodeCache.getInstance().getCtrlcodeName(CtrlCodeDefNo.CLIENT_SOURCE_STATE, client.getSource()));
        sourceLayout.setShowSutitleText(true);
        source = client.getSource();

        regionEt.setText(client.getRegion());
        detailInfoEt.setText(client.getAddress());
        contactNameEt.setText(client.getContactman());
        contactPhoneEt.setText(client.getContactphone());

        postLayout.getSutitleTv().setText(CtrlCodeCache.getInstance().getCtrlcodeName(CtrlCodeDefNo.CLIENT_POSITION_STATE, client.getContactposition()));
        postLayout.setShowSutitleText(true);
        post = client.getContactposition();

        clientRankLayout.getSutitleTv().setText(CtrlCodeCache.getInstance().getCtrlcodeName(CtrlCodeDefNo.CLIENT_LEVEL_STATE, client.getLevel()));
        clientRankLayout.setShowSutitleText(true);
        clientRank = client.getLevel();
    }

    private class OnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(final View v, boolean hasFocus) {
            if (!v.hasFocus()) {
                Resource resource = resourceFactory.create("QueryInstClients");
                if (v == clientNoEt) {
                    resource.param("sn", clientNoEt.getText().toString());
                } else if (v == companyAbbrNameEt) {
                    resource.param("name", companyAbbrNameEt.getText().toString());
                } else if (v == contactPhoneEt) {
                    resource.param("contactphone", contactPhoneEt.getText().toString());
                }
                resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                    @Override
                    public void onSuccess(Response response) {
                        clients = (List<Client>) response.getPayload();
                        String notice = null;
                        if (clients.size() > 0) {
                            if(v == clientNoEt) {
                                notice = getResources().getString(R.string.duplicate_client_not, getString(R.string.duplicate_client_no));
                            }else if(v == companyAbbrNameEt){
                                notice = getResources().getString(R.string.duplicate_client_not, getString(R.string.duplicate_client_name));
                            }else if(v == contactPhoneEt){
                                notice = getResources().getString(R.string.duplicate_client_not, getString(R.string.duplicate_client_phone));
                            }
                            Toast.makeText(mContext, notice, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Response response) {
                        Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinally(Response response) {
                    }
                });
            }
        }
    }
}
