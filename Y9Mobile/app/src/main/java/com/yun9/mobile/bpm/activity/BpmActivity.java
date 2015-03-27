package com.yun9.mobile.bpm.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ProcessDefInfo.ProcessDefFormInfo;
import ProcessDefInfo.ProcessDefFormInfo.Device;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.maoye.form.factory.view.form.FactoryViewForm;
import com.maoye.form.model.form.ModelAttchmentFile;
import com.maoye.form.model.form.ModelAttchmentPic;
import com.maoye.form.model.form.ModelViewForm;
import com.maoye.form.model.form.ModelViewFormElement.FormStat;
import com.maoye.form.model.form.ModelViewFormRow;
import com.maoye.form.model.form.cell.ModelViewFormAttchmentCell;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.utils.UtilModelAndJson4Form;
import com.maoye.form.utils.UtilModelAndView4Form;
import com.maoye.form.view.form.ViewForm;
import com.maoye.form.view.form.ViewForm.CallBackTranform2Model;
import com.maoye.form.view.form.ViewFormShell;
import com.yun9.mobile.R;
import com.yun9.mobile.bpm.fragment.BpmNodeFragment;
import com.yun9.mobile.bpm.model.ProcessDefInfo;
import com.yun9.mobile.bpm.model.ProcessDefNodeBoInfo;
import com.yun9.mobile.bpm.model.ProcessDefNodeBoModelAttrInfo;
import com.yun9.mobile.bpm.model.ProcessDefNodeBoModelInfo;
import com.yun9.mobile.bpm.model.ProcessDefNodeInfo;
import com.yun9.mobile.bpm.model.ProcessDefNodeRefInfo;
import com.yun9.mobile.bpm.model.SysInstProcess;
import com.yun9.mobile.form.factory.FactoryViewFromYiDianTong;
import com.yun9.mobile.framework.base.activity.BaseFragmentActivity;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.JsonUtil;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.StringUtils;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.framework.view.TextTitleBarView;

public class BpmActivity extends BaseFragmentActivity {
	
	/**
	 * 附件BO名称
	 */
	private static final String BOMODEL_ATTACHMENT_KEY = "attachments";

	private SysInstProcess sysInstProcess;
	private ProcessDefInfo processDefInfo;

	private TextTitleBarView titleBarView;
	private BpmNodeFragment bpmNodeFragment; // 节点处理Fragment
	private ViewFormShell viewFormShell;
	private ViewForm viewForm;
	private ModelViewForm modelViewForm;
	private ProgressDialog progressDialog; // 请等待弹出框

	public static Map<String, User> USER_CACHE;
	public static Map<String, Org> ORG_CACHE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_bpm);
		sysInstProcess = (SysInstProcess) this.getIntent()
				.getSerializableExtra("sysinstprocess");
		super.onCreate(savedInstanceState);// 执行initWidget(),bindEvent()
		// 初始化用户及机构缓存
		if (USER_CACHE == null) {
			USER_CACHE = new HashMap<String, User>();
		}
		if (ORG_CACHE == null) {
			ORG_CACHE = new HashMap<String, Org>();
		}
		// 初始化ViewForm
		initViewForm();
		// 初始化流程配置
		bindData(initDataCallback);
	}

	private void initViewForm() {
		// 创建表单
		FactoryViewForm factory = new FactoryViewFromYiDianTong();
		viewForm = factory.creatViewForm(this);
	}

	@Override
	protected void initWidget() {
		titleBarView = (TextTitleBarView) findViewById(R.id.title_bar);
		viewFormShell = (ViewFormShell) findViewById(R.id.viewForm);

		// NODE,Fragment替换
		if (!AssertValue.isNotNull(bpmNodeFragment)) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			bpmNodeFragment = new BpmNodeFragment(this);
			ft.replace(R.id.fr_bpm_node, bpmNodeFragment,
					BpmActivity.class.getName());
			ft.commit();
		}
	}

	@Override
	protected void bindEvent() {
		titleBarView.getBtnReturn().setVisibility(View.VISIBLE);
		titleBarView.getTvTitle().setVisibility(View.VISIBLE);
		titleBarView.getTvTitle().setText(sysInstProcess.getName());
		titleBarView.getBtnFuncNav().setVisibility(View.VISIBLE);
		titleBarView.getBtnFuncNav().setText(R.string.bpm_start_node);
		titleBarView.getBtnReturn().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		titleBarView.getBtnFuncNav().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tranform2Model(new CallBackTranform2Model(){

					@Override
					public void onSuccess(String json) {
						viewForm.getModelViewForm().setFormStat(FormStat.OnWrite);
						//检查是否包含节点
						if (processDefInfo.getNodes().size() == 0) {
							TipsUtil.showToast("请添加审批节点。", BpmActivity.this);
							return;
						} else {
							for (ProcessDefNodeInfo node : processDefInfo.getNodes()) {
								node.setRefs(Collections.singletonList(new ProcessDefNodeRefInfo(processDefInfo.getName(),false)));
							}
						}
						buildData(json);
						submitProcess();
					}

					@Override
					public void onFail() {
						viewForm.getModelViewForm().setFormStat(FormStat.OnWrite);
					}});
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void submitProcess() {
		progressDialog = TipsUtil.openDialog(null, false,
				BpmActivity.this);
		Resource resource = ResourceUtil.get("BpmStartService");
		if (sysInstProcess.isDynamicNode()) {
			Map<String, Object> params = JsonUtil.jsonToBean(
					JsonUtil.beanToJson(processDefInfo), Map.class);
			params.put("title", getProcTitle());
			resource.setParams(params);
		} else {
			Map<String, Object> processDefInfoParam = JsonUtil
					.jsonToBean(JsonUtil.beanToJson(processDefInfo),
							Map.class);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dataObjects",
					processDefInfoParam.get("dataObjects"));
			params.put("procDefId", sysInstProcess.getProcessdefid());
			resource.setParams(params);
		}
		resource.invok(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				progressDialog.dismiss();
				TipsUtil.showToast("流程保存成功！", context);
				saveTemplate();
			}

			@Override
			public void onFailure(Response response) {
				progressDialog.dismiss();
				TipsUtil.showToast("保存流程失败，请稍候重试！", context);
			}
		});
	}

	private void bindData(InitDataCallback callback) {
		if (SysInstProcess.ProcessType.DYNAMIC.equals(sysInstProcess
				.getProcesstype())) {
			processDefInfo = JsonUtil.jsonToBean(sysInstProcess.getParams(),
					ProcessDefInfo.class);
			callback.onSucess();
		} else if (SysInstProcess.ProcessType.STATIC.equals(sysInstProcess
				.getProcesstype())) {
			loadProcessConfig(sysInstProcess.getProcessdefid(), callback);
		} else {
			TipsUtil.showToast("无效的流程类型。", context);
			callback.onFailure();
		}
	}

	private void loadProcessConfig(String processdefid,
			final InitDataCallback callback) {
		progressDialog = TipsUtil.openDialog(null, false, BpmActivity.this);
		Resource resource = ResourceUtil.get("BpmDefQuery4AppService");
		resource.param("defid", processdefid);
		resource.invok(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				progressDialog.dismiss();
				processDefInfo = (ProcessDefInfo) response.getPayload();
				if (!AssertValue.isNotNull(processDefInfo)) {
					TipsUtil.showToast("静态流程的配置信息为空。", context);
					callback.onFailure();
				} else {
					callback.onSucess();
				}
			}

			@Override
			public void onFailure(Response response) {
				progressDialog.dismiss();
				TipsUtil.showToast("无法获取静态流程的配置信息。", context);
				callback.onFailure();
			}
		});
	}

	/**
	 * 初始化Fragment 初始化表单跟节点的Fragment
	 */
	private void initFragment() {
		if (!AssertValue.isNotNull(processDefInfo.getBos())) {
			processDefInfo.setBos(new ArrayList<ProcessDefNodeBoInfo>());
		}
		if (!AssertValue.isNotNull(processDefInfo.getDataObjects())) {
			processDefInfo
					.setDataObjects(new HashMap<String, Map<String, Object>>());
		}
		if (!AssertValue.isNotNull(processDefInfo.getNodes())) {
			processDefInfo.setNodes(new ArrayList<ProcessDefNodeInfo>());
		}
		if (!AssertValue.isNotNull(processDefInfo.getForms())) {
			processDefInfo.setForms(new ArrayList<ProcessDefFormInfo>());
		}
		bpmNodeFragment.load(sysInstProcess, processDefInfo);

		// 初始化表单
		if (processDefInfo.getForms().size() > 0
				&& ProcessDefFormInfo.Device.APP.equals(processDefInfo
						.getForms().get(0).getDevice())) {
			// 将JSON转成form视图需要的model并初始化
			modelViewForm = UtilModelAndJson4Form
					.json2Model(processDefInfo.getForms().get(0)
							.getValueString());

			viewForm.initWithModel(modelViewForm);
			viewFormShell.loadForm(viewForm);
		}

	}

	private void saveTemplate() {
		Resource resource = ResourceUtil
				.get("SysInstProcessUserdefSaveService");
		resource.param("instprocessid", sysInstProcess.getId());
		resource.param("params", JsonUtil.beanToJson(processDefInfo));
		resource.invok(new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				finish();
			}

			@Override
			public void onFailure(Response response) {
				TipsUtil.showToast("保存流程模板失败。", context);
				finish();
			}
		});
	}

	private interface InitDataCallback {
		public void onSucess();

		public void onFailure();
	}

	private InitDataCallback initDataCallback = new InitDataCallback() {

		@Override
		public void onSucess() {
			initFragment();
		}

		@Override
		public void onFailure() {
			finish();
		}
	};
	
	private void tranform2Model(final CallBackTranform2Model callBackTranform2Model) {
		if (viewForm.getModelViewForm() == null) {
			return;
		}
		modelViewForm = viewForm.getModelViewForm();
		// 将表单转成JSON 传递给服务器
		UtilModelAndView4Form.view2Json(viewForm, new CallBackTranform2Model() {
			
			boolean fixMeBool = true; // FIXME CallBackTranform2Model会被执行多次
			@Override
			public void onSuccess(String json) {
				if (fixMeBool) {
					fixMeBool = false;
					callBackTranform2Model.onSuccess(json);
				}
				
			}
			
			@Override
			public void onFail() {
				if (fixMeBool) {
					fixMeBool = false;
					callBackTranform2Model.onFail();
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void buildData(String json) {
		
		// 将JSON转成form视图需要的model并初始化
		ModelViewForm model = UtilModelAndJson4Form.json2Model(json);
		ProcessDefNodeBoModelInfo boModel = new ProcessDefNodeBoModelInfo();
		// 附件mobel
		ProcessDefNodeBoModelInfo boModelAttach = createAttachModel();
		Map<String, Object> attacheDataObject = null;
		Map<String, ProcessDefNodeBoModelAttrInfo> attrMap = new HashMap<String, ProcessDefNodeBoModelAttrInfo>();
		Map<String, Object> dataObject = new HashMap<String, Object>();
		List<ModelViewFormRow> rows = model.getRows();
		for (ModelViewFormRow row : rows) {
			List<ModelViewFormCell> cells = row.getCells();
			for (ModelViewFormCell cell : cells) {
				ProcessDefNodeBoModelAttrInfo attr = new ProcessDefNodeBoModelAttrInfo();
				attr.setName(cell.getTag());
				attr.setDesc(cell.getLabel());
				attr.setType(ProcessDefNodeBoModelAttrInfo.Type.STRING);
				attrMap.put(attr.getName(), attr);
				dataObject.put(attr.getName(), cell.getValue());
				if (cell instanceof ModelViewFormAttchmentCell) {
					attacheDataObject = getAttachDataObject((ModelViewFormAttchmentCell)cell);
				}
			}

		}
		boModel.setBoAttributes(attrMap);
		boModel.setName(processDefInfo.getName());
		Map<String, Object> d = processDefInfo.getDataObjects().get(processDefInfo.getName());
		if (d != null) {
			d.clear();
			d.putAll(dataObject);
		} else {
			processDefInfo.getDataObjects().put(processDefInfo.getName(),
					dataObject); // 设置表单值
		}
		ProcessDefNodeBoInfo bo = null;
		for (ProcessDefNodeBoInfo b : processDefInfo.getBos()) {
			if (b.getName().equals(boModel.getName())) {
				bo = b;
				break;
			}
		}
		if (bo != null) {
			if (bo.getBoModels() == null) {
				bo.setBoModels(new HashMap<String, ProcessDefNodeBoModelInfo>());
			}
			bo.getBoModels().put(boModel.getName(), boModel);
		} else {
			bo = new ProcessDefNodeBoInfo();
			bo.setRoot(boModel.getName());
			bo.setName(boModel.getName());
			bo.setDesc(sysInstProcess.getName());
			// bo.setJpaService("noticeRegistService");
			bo.setType("json");
			bo.setBoModels(new HashMap<String, ProcessDefNodeBoModelInfo>());
			bo.getBoModels().put(boModel.getName(), boModel);
			processDefInfo.getBos().add(bo); // 设置BO
		}
		if (attacheDataObject != null) {
			if (boModel.getHasmanys() == null) {
				boModel.setHasmanys(new HashMap<String, String>());
			}
			bo.getBoModels().put(BOMODEL_ATTACHMENT_KEY, boModelAttach);
			boModel.getHasmanys().put(BOMODEL_ATTACHMENT_KEY,BOMODEL_ATTACHMENT_KEY);
			processDefInfo.getDataObjects().get(processDefInfo.getName()).putAll(attacheDataObject);
			
		}
		ProcessDefFormInfo form = new ProcessDefFormInfo();
		form.setDevice(Device.APP);
		form.setId(processDefInfo.getName());
		form.setName(processDefInfo.getTitle());
		Map<String, Object> formValue = JsonUtil.jsonToBean(json, Map.class);
		form.setValue(formValue);
		processDefInfo.setForms(Collections.singletonList(form));

	}

	private Map<String, Object> getAttachDataObject(
			ModelViewFormAttchmentCell cell) {
		Map<String, Object> attacheDataObject = new HashMap<String, Object>();
		List<Attachment> attachments = new ArrayList<BpmActivity.Attachment>();
		List<ModelAttchmentFile> attachs = cell.getAttchments();
		List<ModelAttchmentPic> pics = cell.getPics();
		if (attachs != null) {
			for (ModelAttchmentFile file : attachs) {
				Attachment a = new Attachment();
				a.setId(file.getValue());
				a.setType(Attachment.Type.TYPE_ATTACHMENT);
				attachments.add(a);
			}
		}
		if (pics != null) {
			for (ModelAttchmentPic pic : pics) {
				Attachment a = new Attachment();
				a.setId(pic.getValue());
				a.setType(Attachment.Type.TYPE_PIC);
				attachments.add(a);
			}
		}
		if (attachments.size() > 0) {
			attacheDataObject.put("attachments", attachments);
		}
		
		return attacheDataObject;
	}

	private ProcessDefNodeBoModelInfo createAttachModel() {
		ProcessDefNodeBoModelInfo boModel = new ProcessDefNodeBoModelInfo();
		Map<String, ProcessDefNodeBoModelAttrInfo> attrMap = new HashMap<String, ProcessDefNodeBoModelAttrInfo>();
		boModel.setName(BOMODEL_ATTACHMENT_KEY);
		boModel.setDesc("附件");
		ProcessDefNodeBoModelAttrInfo idAttr = new ProcessDefNodeBoModelAttrInfo();
		idAttr.setName("id");
		idAttr.setDesc("附件ID");
		idAttr.setType(ProcessDefNodeBoModelAttrInfo.Type.STRING);
		ProcessDefNodeBoModelAttrInfo typeAttr = new ProcessDefNodeBoModelAttrInfo();
		typeAttr.setName("type");
		typeAttr.setDesc("附件类型");
		typeAttr.setType(ProcessDefNodeBoModelAttrInfo.Type.STRING);
		attrMap.put(idAttr.getName(), idAttr);
		attrMap.put(typeAttr.getName(), typeAttr);
		boModel.setBoAttributes(attrMap);
		return boModel;
	}

	private String getProcTitle() {
		if (viewForm.getModelViewForm() == null) {
			return "";
		}
		List<ModelViewFormRow> rows = viewForm.getModelViewForm().getRows();
		StringBuffer sb = new StringBuffer(processDefInfo.getTitle())
				.append("\r\n");
		for (ModelViewFormRow row : rows) {
			List<ModelViewFormCell> cells = row.getCells();
			for (ModelViewFormCell cell : cells) {
				sb.append(cell.getLabel()).append("：")
						.append(StringUtils.escapeNull(cell.getValue()))
						.append("\r\n");
			}

		}
		return sb.toString();
	}
	
	class Attachment {
		class Type {
			public static final String TYPE_ATTACHMENT = "attachment";
			public static final String TYPE_PIC = "pic";
		}
		private String id;
		private String type;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}

}
