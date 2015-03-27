package com.maoye.form.view.form;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.maoye.form.R;
import com.maoye.form.constact.ConstactForm;
import com.maoye.form.engine.EngineFormCommitResult;
import com.maoye.form.engine.EngineFormCommitResult.OberverFormCommitResult;
import com.maoye.form.factory.view.form.FactoryViewForm;
import com.maoye.form.factory.view.form.FactoryViewFromDefault;
import com.maoye.form.interfaces.oberver.OberverViewForm;
import com.maoye.form.model.form.ModelViewForm;
import com.maoye.form.model.form.ModelViewFormElement.FormStat;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.model.form.ModelViewFormRow;
import com.maoye.form.utils.UtilModelAndJson4Form;
import com.maoye.form.view.ReboundScrollView;
import com.maoye.form.view.form.ViewForm.CallBackTranform2Model;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ViewForm extends ReboundScrollView {
	private TextView formTitle;
	protected LinearLayout formBody;
	private ModelViewForm modelViewForm;
	
	private FactoryViewForm factoryViewForm;
	
	private List<OberverViewForm> obervers;
	
	
	private EngineFormCommit engineFormCommit;
	
	
	private TranType tranType = TranType.NORMAL;
	
	public ViewForm(Context context) {
		super(context);
		init();
	}
	public ViewForm(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public ViewForm(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	protected void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.form_form, this);
		findView();
		engineFormCommit = new EngineFormCommit();
	}
	
	protected void findView(){
		formBody = (LinearLayout) findViewById(R.id.form_body);
		formTitle = (TextView) findViewById(R.id.form_title);
	}
	
	public void addRow(ViewFormRow row){
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
				);
		formBody.addView(row, params);
		
	}
	
	private void clearForm(){
		formBody.removeAllViews();
		modelViewForm = null;
	}
	
	
	public void setFormTitle(String text){
		formTitle.setText(text);
	}
	public String getFormTitl(){
		return formTitle.getText().toString();
	}
	
	public void initWithModel(ModelViewForm modelViewForm){
		
		if(modelViewForm == null){
			return ;
		}
		if(this.modelViewForm != null){
			clearForm();
		}
		this.modelViewForm = modelViewForm;
		setFormTitle(modelViewForm.getName());
		
		List<ModelViewFormRow> modelRows = modelViewForm.getRows();
		if(modelRows != null && modelRows.size() > 0){
			int cellNumber = 0;
			for(ModelViewFormRow modelRow : modelRows){
				List<ModelViewFormCell> cells = modelRow.getCells();
				
				ViewFormRow viewRow = factoryViewForm.creatViewFormRow(getContext(), modelRow);
				
				if(cells != null && cells.size() > 0){
				
					for(ModelViewFormCell modelCell : cells){
						ViewFormCell viewCell = factoryViewForm.creatViewFormCell(getContext(), modelCell.getType(), modelCell);
						viewRow.addViewCell(viewCell);
						
						addOberver(viewCell);
						cellNumber++;
						EngineFormCommitResult engineFormCommitResult = new EngineFormCommitResult(cellNumber, engineFormCommit);
						viewCell.setEngineFormCommitResult(engineFormCommitResult);
					}
				}
				addRow(viewRow);
			}
			engineFormCommit.setWaitCellNum(cellNumber);
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		if(isReadOnly()){
			return true;
		}
		
		return super.onInterceptTouchEvent(ev);
	}

	private boolean isReadOnly(){
		
		if(modelViewForm != null){
			if(modelViewForm.getFormStat() == FormStat.OnRead){
				return true;
			}
		}
		return false;
		
	}
	public ModelViewForm getModelViewForm() {
		return modelViewForm;
	}
	
	
	private void addOberver(OberverViewForm oberver){
		if(obervers == null){
			obervers = new LinkedList<OberverViewForm>();
		}
		
		obervers.add(oberver);
	}
	
	public void setFactoryViewForm(FactoryViewForm factoryViewForm) {
		this.factoryViewForm = factoryViewForm;
	}
	
	
	public void formCommit() {
		modelViewForm.setFormStat(FormStat.OnRead);
	}
	
	public ModelViewForm tranform2Model(){
		setTranType(TranType.NORMAL);
		formCommit();
		
		if(obervers != null){
			for(OberverViewForm oberver : obervers){
				oberver.formCommit();
			}
		}
		return modelViewForm;
	}
	
	private CallBackTranform2Model callBack;
	public void tranform2Model(CallBackTranform2Model callBack){
		tranform2Model(callBack, TranType.NORMAL);
	}
	public void tranform2Model(CallBackTranform2Model callBack, TranType tranType){
		this.callBack = callBack;
		this.tranType = tranType;
		formCommit();
		if(obervers != null){
			ExecutorService pool = Executors.newFixedThreadPool(3);
			for(final OberverViewForm oberver : obervers){
				pool.execute(new Runnable() {
					@Override
					public void run() {
						oberver.formCommit();
					}
				});
			}
		}
	}
	
	
	public class EngineFormCommit implements OberverFormCommitResult{
		private int waitCellNum;
		
		@Override
		public  void notifyFormCommitResult(int who, boolean isOK) {
			synchronized(this){
			
				System.out.println("waitCellNum = " + waitCellNum);
				waitCellNum--;
				if(waitCellNum <= 0){
					final String json = model2Json(modelViewForm);
					
					if(callBack != null){
						Activity activity = (Activity) getContext();
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								callBack.onSuccess(json);
							}
						});
					}
				}
			}
		}

		public void setWaitCellNum(int waitCellNum) {
			this.waitCellNum = waitCellNum;
		}
	}
	
	public interface CallBackTranform2Model{
		public void onSuccess(String json);
		
		public void onFail();
	}
	
	public enum TranType{
		
		NORMAL, DATA;
	}

	public void setTranType(TranType tranType) {
		this.tranType = tranType;
	}
	
	
	
	private String model2Json(ModelViewForm modelViewForm){
		if(tranType == TranType.NORMAL){
			return UtilModelAndJson4Form.model2Json(modelViewForm);
		}else if(tranType == TranType.DATA){
			return UtilModelAndJson4Form.model2ValueJson(modelViewForm);
		}
		return UtilModelAndJson4Form.model2Json(modelViewForm);
	}
	
	

	
}
