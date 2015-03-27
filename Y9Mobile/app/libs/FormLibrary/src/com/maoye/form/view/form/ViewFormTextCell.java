package com.maoye.form.view.form;

import com.maoye.form.R;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.model.form.cell.ModelViewFormTextCell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewFormTextCell extends ViewFormCell {
	
	private ModelViewFormTextCell model;
	
	protected View form_cell;
	protected TextView form_cell_label;
	protected TextView form_cell_value;
	
	public ViewFormTextCell(Context context, ModelViewFormTextCell model) {
		super(context, model);
		init();
		initWithModel(model);
	}
	
	
	public void setViewLabel(String label) {
		form_cell_label.setText(label);
	}
	
	public void setViewValue(String value) {
		form_cell_value.setText(value);
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.form_cell_text, this);
		findView();
	}

	private void findView() {
		form_cell = findViewById(R.id.form_cell);
		form_cell_label = (TextView) findViewById(R.id.form_cell_label);
		form_cell_value = (TextView) findViewById(R.id.form_cell_value);
	}


	@Override
	public void initWithModel(ModelViewFormCell model) {
		this.model = (ModelViewFormTextCell) model;
		setViewLabel(this.model.getLabel());
		setViewValue(this.model.getValue());
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
