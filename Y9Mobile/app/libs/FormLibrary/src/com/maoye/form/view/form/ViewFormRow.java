package com.maoye.form.view.form;

import com.maoye.form.R;
import com.maoye.form.model.form.ModelViewFormRow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class ViewFormRow extends LinearLayout {
	
	protected LinearLayout form_row;
	protected int currentCellNum;
	protected int maxCellNum;
	
	private ModelViewFormRow model;
	
	public ViewFormRow(Context context, ModelViewFormRow model) {
		super(context);
		this.model = model;
		init();
		
		initWithModel(model);
	}
	
	
	protected void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.form_row, this);
		findView();
		
		this.maxCellNum = 4;
		this.currentCellNum = 0;
	}
	
	protected void findView(){
		form_row = (LinearLayout) findViewById(R.id.form_row);
	}
	
	
	public boolean addViewCell(View cell){
		if(!isAllowAdd()){
			return false;
		}
		
		if(this.currentCellNum > 0){
			// 竖线
			LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
			lineParams.weight = 0;
			View line = new View(getContext());
			line.setBackgroundColor(getResources().getColor(R.color.form_row_line));
			this.form_row.addView(line, lineParams);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
			params.weight = 1;
			this.form_row.addView(cell, params);
		}else{
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
			params.weight = 1;
			this.form_row.addView(cell, params);
		}

		this.currentCellNum += 1;
		
		return true;
	}
	
	private boolean isAllowAdd(){
		if(this.currentCellNum < this.maxCellNum)
			return true;
		else
			return false;
	}
	
	
	public void initWithModel(ModelViewFormRow model){
		this.model = model;
	}
}
