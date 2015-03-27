package com.maoye.form.view.form;


import com.maoye.form.R;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.model.form.cell.ModelViewFormEditTextCell;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ViewFormEditTextCell extends ViewFormCell{
	
	private ModelViewFormEditTextCell model;
	
	// 单元格
	protected TextView form_cell_label;
	protected EditText form_cell_value;
	
	
	public ViewFormEditTextCell(Context context, ModelViewFormEditTextCell model) {
		super(context, model);
		init();
		
		initWithModel(model);
	}
	
	
	protected void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.form_cell_edittext, this);
		findView();
		form_cell_value.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				modelSetValue(s.toString());
			}
		});
		
	}
	
	
	protected void findView(){
		form_cell_label = (TextView) findViewById(R.id.form_cell_label);
		form_cell_value = (EditText) findViewById(R.id.form_cell_value);
	}


	public void setLabel(String label) {
		form_cell_label.setText(label);
	}
	

	public void setValue(String value) {
		form_cell_value.setText(value);
	}
	
	/**
	 * 设置输入类型
	 * @param type
	 */
	public void setInputType(int type){
		
		form_cell_value.setInputType(type);
//		this.form_cell_content.setKeyListener(new NumberKeyListener() {
//			@Override
//			public int getInputType() {
//				return (InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_DECIMAL) ;
//			}
//			
//			@Override
//			protected char[] getAcceptedChars() {
//				return new char[] { '.', '1', '2', '3', '4', '5', '6', '7', '8','9', '0' };
//			}
//		});
	}


	@Override
	public void initWithModel(ModelViewFormCell model) {
		this.model = (ModelViewFormEditTextCell) model;
		setLabel(this.model.getLabel());
		setValue(this.model.getValue());
		setInputType(this.model.getInputType());
		
	}
	
	private void modelSetValue(String value){
		this.model.setValue(value);
	}

	@Override
	public void formCommit() {
		notifyFormCommitResult(true);
	}


	@Override
	public String getCellTag() {
		return model.getTag();
	}
	
}
