package com.yun9.wservice.view.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.form.cell.MultiSelectFormCell;
import com.yun9.jupiter.form.cell.TextFormCell;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.form.model.TextFormCellBean;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.PatternPoll;
import com.yun9.jupiter.view.CustomCallbackActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Client;
import com.yun9.wservice.model.MdInstScale;
import com.yun9.wservice.model.MdInstScales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 7/7/15.
 */
public class EditClientActivity extends CustomCallbackActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.confirm_ll)
    private LinearLayout confirmLl;

    @ViewInject(id=R.id.form_page)
    private LinearLayout formPage;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private EditClientCommand command;

    private List<FormCell> cells;

    private Map<Integer, IActivityCallback> activityCallbackMap;

    private List<SerialableEntry<String,String>> optionsList = new ArrayList<>();

    private int baseRequestCode = 10000;

    public static void start(Activity activity,EditClientCommand command) {
        Intent intent = new Intent(activity,EditClientActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND,command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (EditClientCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        activityCallbackMap = new HashMap<>();
        cells = new ArrayList<>();
        loadInstScale();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_client;
    }

    private void loadInstScale() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryMdInstScale");
        resource.param("userid", sessionManager.getUser().getId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                MdInstScales scales = (MdInstScales) response.getPayload();
                if (scales != null
                        && scales.getBizMdInstScales() != null
                        && scales.getBizMdInstScales().size()>0) {
                    for (MdInstScale scale : scales.getBizMdInstScales()) {
                        optionsList.add(new SerialableEntry<String, String>(
                                scale.getType(),scale.getName()
                        ));
                    }
                }
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                buildView();
                registerDialog.dismiss();
            }
        });
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditClientActivity.this.finish();
            }
        });
        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
        formPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                formPage.requestFocus();
                return false;
            }
        });

        buildForm();
    }

    /**
     * 提交修改
     */
    private void confirm() {
        for (FormCell cell : cells){
            String validate = cell.validate();
            if (AssertValue.isNotNullAndNotEmpty(validate)){
                showToast(validate);
                return;
            }
        }
        Client client = gatherClient();
        doSave(client);
    }

    private Client gatherClient() {
        Client client = new Client();
        Map<String,String> cellValueMap = gatherCellValue();
        client.setName(cellValueMap.get("name"));
        client.setFullname(cellValueMap.get("fullname"));
        client.setType(cellValueMap.get("type"));
        client.setLevel(cellValueMap.get("level"));
        client.setContactman(cellValueMap.get("contactman"));
        client.setContactphone(cellValueMap.get("contactphone"));
        client.setRegion(cellValueMap.get("region"));
        client.setSource(cellValueMap.get("source"));
        client.setIndustry(cellValueMap.get("industry"));
        client.setScaleid(cellValueMap.get("scaleid"));
        client.setContactposition(cellValueMap.get("contactposition"));
        return client;
    }

    private Map<String, String> gatherCellValue() {
        Map<String, String> value = new HashMap<>();
        for (FormCell cell : cells){
            value.put(cell.getFormCellBean().getKey(),cell.getStringValue());
        }
        return value;
    }

    private void doSave(Client client) {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("AddOrUpdateInstClientsService");
        resource.param("id",command.getClientId());
        resource.param("instid", sessionManager.getInst().getId());
        resource.param("name", client.getName());
        resource.param("fullname", client.getFullname());
        resource.param("type", client.getType());
        resource.param("level", client.getLevel());
        resource.param("contactman", client.getContactman());
        resource.param("contactphone", client.getContactphone());
        resource.param("region", client.getRegion());
        resource.param("source", client.getSource());
        resource.param("industry", client.getIndustry());
        resource.param("industry", client.getIndustry());
        resource.param("scaleid", client.getScaleid());
        resource.param("contactposition", client.getContactposition());
        resource.param("createby", sessionManager.getUser().getId());

        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {

            @Override
            public void onSuccess(Response response) {
                showToast("保存客户成功！");
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


    private void buildForm() {
        cells.clear();
        formPage.removeAllViews();
        List<FormCellBean> cellBeans = getCellBeans();
        FormCellBean formCellBean;
        FormCell cell;
        Class<? extends FormCell> type;
        for (int i = 0; i < cellBeans.size(); i++) {
            formCellBean = cellBeans.get(i);
            type = FormUtilFactory.getInstance().getCellTypeClassByType(formCellBean.getType());
            if (type != null){
                cell = FormUtilFactory.createCell(type, formCellBean);
                cells.add(cell);
                View view = cell.getCellView(this);
                if (AssertValue.isNotNull(view)) {
                    formPage.addView(view);
                    cell.edit(true);
                }
            }
        }
    }

    public List<FormCellBean> getCellBeans() {
        List<FormCellBean> cellBeans = new ArrayList<>();

        TextFormCellBean snTFC = new TextFormCellBean();
        snTFC.setType(TextFormCell.class.getSimpleName());
        snTFC.setKey("sn");
        snTFC.setLabel("客户编号-建议使用公司名称拼音");
        snTFC.setRequired(true);
        snTFC.setRegular(PatternPoll.NUMBER_OR_CAPTION);
        snTFC.setMinNum(4);
        snTFC.setMaxNum(8);
        snTFC.setErrorMessage("客户编号：只能由数字或字母组成");
        cellBeans.add(snTFC);

        TextFormCellBean nameTFC = new TextFormCellBean();
        nameTFC.setType(TextFormCell.class.getSimpleName());
        nameTFC.setKey("name");
        nameTFC.setLabel("公司简称");
        nameTFC.setRequired(true);
        cellBeans.add(nameTFC);

        TextFormCellBean fullnameTFC = new TextFormCellBean();
        fullnameTFC.setType(TextFormCell.class.getSimpleName());
        fullnameTFC.setKey("fullname");
        fullnameTFC.setLabel("公司全称");
        fullnameTFC.setRequired(true);
        cellBeans.add(fullnameTFC);

        MultiSelectFormCellBean typeMSFC = new MultiSelectFormCellBean();
        typeMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        typeMSFC.setCtrlCode("clienttype");
        typeMSFC.setKey("type");
        typeMSFC.setLabel("类型");
        typeMSFC.setMaxNum(1);
        typeMSFC.setRequired(true);
        cellBeans.add(typeMSFC);

        MultiSelectFormCellBean industryMSFC = new MultiSelectFormCellBean();
        industryMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        industryMSFC.setCtrlCode("clientindustry");
        industryMSFC.setKey("industry");
        industryMSFC.setLabel("行业");
        industryMSFC.setMaxNum(1);
        cellBeans.add(industryMSFC);


        MultiSelectFormCellBean scaleMSFC = new MultiSelectFormCellBean();
        scaleMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        scaleMSFC.setOptionMap(optionsList);
        scaleMSFC.setKey("scaleid");
        scaleMSFC.setLabel("机构规模");
        scaleMSFC.setMaxNum(1);
        scaleMSFC.setRequired(true);
        cellBeans.add(scaleMSFC);


        MultiSelectFormCellBean sourceMSFC = new MultiSelectFormCellBean();
        sourceMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        sourceMSFC.setCtrlCode("clientsource");
        sourceMSFC.setKey("source");
        sourceMSFC.setMaxNum(1);
        sourceMSFC.setLabel("来源");
        cellBeans.add(sourceMSFC);


        TextFormCellBean regionTFC = new TextFormCellBean();
        regionTFC.setType(TextFormCell.class.getSimpleName());
        regionTFC.setKey("region");
        regionTFC.setLabel("区域");
        regionTFC.setRequired(true);
        cellBeans.add(regionTFC);

        TextFormCellBean addressTFC = new TextFormCellBean();
        addressTFC.setType(TextFormCell.class.getSimpleName());
        addressTFC.setKey("address");
        addressTFC.setLabel("详细地址");
        cellBeans.add(addressTFC);

        TextFormCellBean contactmanTFC = new TextFormCellBean();
        contactmanTFC.setType(TextFormCell.class.getSimpleName());
        contactmanTFC.setKey("contactman");
        contactmanTFC.setLabel("联系人名");
        contactmanTFC.setRequired(true);
        cellBeans.add(contactmanTFC);

        TextFormCellBean contactphoneTFC = new TextFormCellBean();
        contactphoneTFC.setType(TextFormCell.class.getSimpleName());
        contactphoneTFC.setKey("contactphone");
        contactphoneTFC.setLabel("联系电话");
        contactphoneTFC.setRegular(PatternPoll.PHONE);
        contactphoneTFC.setErrorMessage("联系电话：请输入正确的电话号码");
        contactphoneTFC.setRequired(true);
        cellBeans.add(contactphoneTFC);

        MultiSelectFormCellBean contactpositionMSFC = new MultiSelectFormCellBean();
        contactpositionMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        contactpositionMSFC.setCtrlCode("contactposition");
        contactpositionMSFC.setKey("contactposition");
        contactpositionMSFC.setLabel("职位");
        contactpositionMSFC.setRequired(true);
        contactpositionMSFC.setMaxNum(1);
        cellBeans.add(contactpositionMSFC);

        MultiSelectFormCellBean levelMSFC = new MultiSelectFormCellBean();
        levelMSFC.setType(MultiSelectFormCell.class.getSimpleName());
        levelMSFC.setCtrlCode("clientlevel");
        levelMSFC.setKey("level");
        levelMSFC.setLabel("客户等级");
        levelMSFC.setMaxNum(1);
        levelMSFC.setRequired(true);
        cellBeans.add(levelMSFC);
        return cellBeans;
    }

    private int generateRequestCode() {
        return ++baseRequestCode;
    }

    public int addActivityCallback(IActivityCallback callback) {
        if (callback == null) {
            callback = EMPTY_CALL_BACK;
        }
        int requestCode = generateRequestCode();
        activityCallbackMap.put(requestCode, callback);
        return requestCode;
    }

    public void addActivityCallback(int requestCode,IActivityCallback callback) {
        if (callback == null) {
            callback = EMPTY_CALL_BACK;
        }
        activityCallbackMap.put(requestCode, callback);
    }

    private static final IActivityCallback EMPTY_CALL_BACK = new IActivityCallback() {
        @Override
        public void onActivityResult(int resultCode, Intent data) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IActivityCallback callback = activityCallbackMap.get(requestCode);
        if (callback != null) {
            callback.onActivityResult(resultCode, data);
            activityCallbackMap.remove(requestCode);
        }
    }
}
