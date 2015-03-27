package com.yun9.mobile.framework.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.maoye.form.dialog.DialogProgressTip;
import com.maoye.form.factory.model.form.FactoryModelViewForm;
import com.maoye.form.factory.model.form.FactoryModelViewFormDefault;
import com.maoye.form.factory.view.form.FactoryViewForm;
import com.maoye.form.factory.view.form.FactoryViewFromDefault;
import com.maoye.form.interfaces.FormInputType;
import com.maoye.form.model.form.ModelMulSelected;
import com.maoye.form.model.form.ModelViewForm;
import com.maoye.form.model.form.ModelViewFormElement.FormStat;
import com.maoye.form.model.form.ModelViewFormRow;
import com.maoye.form.model.form.cell.ModelViewFormAttchmentCell;
import com.maoye.form.model.form.cell.ModelViewFormEditTextCell;
import com.maoye.form.model.form.cell.ModelViewFormImageCell;
import com.maoye.form.model.form.cell.ModelViewFormMulSelectedCell;
import com.maoye.form.model.form.cell.ModelViewFormRadioCell;
import com.maoye.form.model.form.cell.ModelViewFormTextCell;
import com.maoye.form.model.form.cell.ModelViewFormTimeCell;
import com.maoye.form.utils.UtilModelAndJson4Form;
import com.maoye.form.utils.UtilModelAndView4Form;
import com.maoye.form.view.form.ViewForm;
import com.maoye.form.view.form.ViewForm.CallBackTranform2Model;
import com.maoye.form.view.form.ViewFormShell;
import com.yun9.mobile.R;
import com.yun9.mobile.form.factory.FactoryViewFromYiDianTong;
import com.yun9.mobile.framework.activity.ScopeActivity;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.dlg.sweetAlert.DlgProTip;
import com.yun9.mobile.framework.file.FileFactory;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.CheckOnWorkAttend;
import com.yun9.mobile.framework.model.FileInfo;
import com.yun9.mobile.framework.model.FileUrlById;
import com.yun9.mobile.framework.model.ScheDulingWork;
import com.yun9.mobile.framework.model.Topic;
import com.yun9.mobile.framework.personelservice.CheckOnWorkAttendFactory;
import com.yun9.mobile.framework.personelservice.CheckOnWorkAttendFactory.CheckInInParm;
import com.yun9.mobile.framework.personelservice.CheckOnWorkAttendFactory.ScheDulingWorkInParm;
import com.yun9.mobile.framework.topic.TopicFactory;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.Logger;
import com.yun9.mobile.position.activity.DemoPositionActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2014/10/30.
 */
public class FormTestActivity extends Activity{
	
	ViewFormShell formShell1;
	ViewFormShell formShell2;
	
	ViewForm form1;
	ViewForm form2;
	
	String jsonHasCommit;
    protected static final String TAG = FormTestActivity.class.getSimpleName();
	private Logger logger = Logger.getLogger(FormTestActivity.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_form_demo);

        findView();
        init();
        setEvent();
    }

    private void init(){

    	// 创建表单
    	FactoryViewForm factory = new FactoryViewFromYiDianTong();
    	
    	form1 = factory.creatViewForm(this);
    	
    	form2 = factory.creatViewForm(this);
    	
    }
    
    private void findView(){
    	formShell1 = (ViewFormShell) findViewById(R.id.form1);
    	formShell2 = (ViewFormShell) findViewById(R.id.form2);
    }
    
    
    private void setEvent(){
    	
    	//1 （客户端1）显示服务端配置的表单视图并接收用户输入数据
    	findViewById(R.id.btn1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// 通过网络获取到服务端配置的表单视图（JSON）
				String json = getDescrbedJsonFromServe(FormStat.OnWrite);
				System.out.println("描述json :" + json);
				// 将JSON转成form视图需要的model并初始化
				ModelViewForm model = UtilModelAndJson4Form.json2Model(json);
//				ModelViewFormAttchmentCell cell41 = (ModelViewFormAttchmentCell) model.findCellByTag("cell41");
				
				
				form1.initWithModel(model);
				
				formShell1.loadForm(form1);
			}
		});
    	
    	
    	//2 （客户端1）表单获取到数据后提交给服务端
    	findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(form1.getModelViewForm() == null){
					return ;
				}
				
				
				final DlgProTip dlg = new DlgProTip(FormTestActivity.this);
				dlg.show();
				
				// 将表单转成JSON 传递给服务器
				UtilModelAndView4Form.view2DataJson(form1, new CallBackTranform2Model() {
					
					@Override
					public void onSuccess(String json) {
						jsonHasCommit = json;

						dlg.dismiss();
						System.out.println(jsonHasCommit);
						showToast("第2步 OK");
						
					}
					
					@Override
					public void onFail() {
						dlg.dismiss();
					}
				});
				
				
				
//				UtilModelAndView4Form.view2Json(form1, new CallBackTranform2Model() {
//					
//					@Override
//					public void onSuccess(String json) {
//						jsonHasCommit = json;
//
//						
//						dlg.dismiss();
//						
//						System.out.println("第2步 OK");
//						showToast("第2步 OK");
//						
//					}
//					
//					@Override
//					public void onFail() {
//						dlg.dismiss();
//					}
//				});
				
				
			}
		});
    	
    	//3 （客户端2,3,...）获取已经采集到数据的表单并显示
    	findViewById(R.id.btn3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			
				// 通过网络获取到服务端配置的表单视图（JSON）
				
				// 获取表单的描述JSON
				String json = getDescrbedJsonFromServe(FormStat.OnRead);
				if(json == null){
					return ;
				}
				
				// 获取表单的数据JSON
				String dataJson = getJsonHasCommit();
				if(dataJson == null){
					return ;
				}
				
				
				// 将描述JSON 转成model
				ModelViewForm model = UtilModelAndJson4Form.json2Model(json);
				
				// 给model填充数据
				boolean result = model.loadData(dataJson);
				if(!result){
					// 数据JSON和描述JSON 不匹配
					showToast("数据JSON和描述JSON 不匹配");
					return ;
				}
			
	
				form2.initWithModel(model);
				formShell2.loadForm(form2);
			}
		});
    }
    

    protected String getDescrbedJsonFromServe(FormStat state){
    	// 服务端配置表单
		FactoryModelViewForm factory = new FactoryModelViewFormDefault();
	
		ModelViewForm model = factory.creatModelViewForm("form");
		
		// 如果不希望改变表单的数据
		model.setFormStat(state);
		
		
		model.setName("请假条");
		
		ModelViewFormRow row1 = factory.creatModelViewFormRow("row1");
		ModelViewFormRow row2 = factory.creatModelViewFormRow("row2");
		ModelViewFormRow row3 = factory.creatModelViewFormRow("row3");
		ModelViewFormRow row4 = factory.creatModelViewFormRow("row4");
		ModelViewFormRow row5 = factory.creatModelViewFormRow("row5");
		
		ModelViewFormTextCell cell11 = factory.creatModelViewFormTextCell("cell11");
		cell11.setLabel("提示");
		cell11.setValue("提示的内容");
		
		
		List<ModelMulSelected> options = new ArrayList<ModelMulSelected>();
		
		options.add(new ModelMulSelected("苹果"));
		options.add(new ModelMulSelected("哈密瓜"));
		options.add(new ModelMulSelected("荔枝"));
		options.add(new ModelMulSelected("葡萄"));
		
		options.add(new ModelMulSelected("苹果"));
		options.add(new ModelMulSelected("哈密瓜"));
		options.add(new ModelMulSelected("荔枝"));
		options.add(new ModelMulSelected("葡萄"));
		
		options.add(new ModelMulSelected("苹果"));
		options.add(new ModelMulSelected("哈密瓜"));
		options.add(new ModelMulSelected("荔枝"));
		options.add(new ModelMulSelected("葡萄"));
		
		ModelViewFormMulSelectedCell cell12 = factory.creatModelViewFormMulSelectedCell("cell12", options);
		cell12.setLabel("多选");
		
		row1.addCell(cell11);
		row1.addCell(cell12);
		
		ModelViewFormRadioCell cell21 = factory.creatModelViewFormRadioCell(getRadios(),"cell21");
		cell21.setLabel("类型");
		
		
		ModelViewFormEditTextCell cell22 = factory.creatModelViewFormEditTextCell("cell22");
		cell22.setLabel("天数");
		cell22.setInputType(FormInputType.TYPE_CLASS_NUMBER | FormInputType.TYPE_NUMBER_FLAG_DECIMAL);
		
		row2.addCell(cell21);
		row2.addCell(cell22);
		
		
		ModelViewFormTimeCell cell31 = factory.creatModelViewFormTimeCell("cell31");
		cell31.setLabel("开始时间");
		
		
		ModelViewFormTimeCell cell32= factory.creatModelViewFormTimeCell("cell32");
		cell32.setLabel("结束时间");
		
		row3.addCell(cell31);
		row3.addCell(cell32);
		
		
		ModelViewFormAttchmentCell cell41 = factory.creatModelViewFormAttchmentCell("cell41");
		cell41.setLabel("说明:");
		
		row4.addCell(cell41);
		
		
	  	
		ModelViewFormImageCell cell51 = factory.creatModelViewFormImageCell("cell51");
		cell51.setLabel("头像");
		row5.addCell(cell51);
		
		model.addRow(row1);
		model.addRow(row2);
		model.addRow(row3);
		model.addRow(row4);
		model.addRow(row5);
  
		
		
		
		String serJson = UtilModelAndJson4Form.model2Json(model);
		
    	return serJson;
    }
    

    
    public List<String> getRadios(){
		List<String> radios;
		radios = new ArrayList<String>();
		radios.add("事假");
		radios.add("病假");
		radios.add("丧假");
		radios.add("婚假");
		radios.add("年休假");
		radios.add("调休假");
		radios.add("产假");
		
		
		return radios;
	}
    
	private void showToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }


	public String getJsonHasCommit() {
		return jsonHasCommit;
	}
	
	
}
