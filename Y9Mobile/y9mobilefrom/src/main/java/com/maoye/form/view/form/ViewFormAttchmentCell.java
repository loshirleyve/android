package com.maoye.form.view.form;


import java.util.ArrayList;
import java.util.List;

import com.maoye.form.R;
import com.maoye.form.adapter.AdapterViewFormAttchmentFile;
import com.maoye.form.adapter.AdapterViewFormAttchmentFile.OnClickItemListener;
import com.maoye.form.adapter.AdapterViewFormAttchmentPic;
import com.maoye.form.dialog.DialogNormal;
import com.maoye.form.interfaces.AdapterShowImage;
import com.maoye.form.interfaces.facade.FacadeAttchCell;
import com.maoye.form.interfaces.facade.FacadeAttchCell.CallBack;
import com.maoye.form.model.ModelFile.FileType;
import com.maoye.form.model.ModelPic;
import com.maoye.form.model.form.ModelAttchmentFile;
import com.maoye.form.model.form.ModelAttchmentPic;
import com.maoye.form.model.form.cell.ModelViewFormAttchmentCell;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.utils.UtilCommon;

import android.content.Context;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class ViewFormAttchmentCell extends ViewFormCell{
	
	// 单元格
	protected TextView form_cell_label;
	protected EditText form_cell_value;
	
	protected GridView form_cell_attchment_pic;
	protected ListView form_cell_attchment_files;
	
	protected List<ModelPic> listModelAttchmentFile;
	protected AdapterViewFormAttchmentFile adapterFile;
	
	protected List<ModelPic> listModelAttchmentPic;
	protected AdapterViewFormAttchmentPic adapterPic;
	
	
	protected View form_cell_attchment_add;
	
	
	protected FacadeAttchCell facade;
	protected ModelViewFormAttchmentCell model;
	protected AdapterShowImage imageLoader;
	public ViewFormAttchmentCell(Context context, ModelViewFormAttchmentCell model, FacadeAttchCell facade, AdapterShowImage imageLoader) {
		super(context, model);
		this.facade = facade;
		this.imageLoader = imageLoader;
		this.model = model;
		
		findView();
		init();
		initWithModel(model);
	}
	

	protected void findView(){
		LayoutInflater.from(getContext()).inflate(R.layout.form_cell_attchment, this);
		form_cell_label = (TextView) findViewById(R.id.form_cell_label);
		form_cell_value = (EditText) findViewById(R.id.form_cell_value);
		
		form_cell_attchment_pic = (GridView) findViewById(R.id.form_cell_attchment_pic);
		form_cell_attchment_files = (ListView) findViewById(R.id.form_cell_attchment_files);
	
		form_cell_attchment_add = findViewById(R.id.form_cell_attchment_add);
	}
	
	protected void init(){
		
		form_cell_value.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				modelSetValue(s.toString());
			}
		});
		
		initImages();
		
		initFiles();
		
		initAttchAdd();
	}
	
	
	private void initImages() {
		listModelAttchmentPic = new ArrayList<ModelPic>();
		
		if(model != null && model.getPics() != null){
			for(ModelAttchmentPic pic : model.getPics()){
				ModelPic modelPic = new ModelPic();
				modelPic.setValue(pic.getValue());
				modelPic.setCreatedate(pic.getCreatedate());
				modelPic.setEmumType(FileType.PicNetWork);
				listModelAttchmentPic.add(modelPic);
			}
		}
		
		
		// GridView Adapter
		adapterPic = new AdapterViewFormAttchmentPic(listModelAttchmentPic , getContext(), imageLoader);
		form_cell_attchment_pic.setAdapter(adapterPic);
		form_cell_attchment_pic.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				removeAttchFile(listModelAttchmentPic, position, adapterPic);
			}
		});
		
	}
	
	private void initFiles() {
		listModelAttchmentFile = new ArrayList<ModelPic>();
		
		adapterFile = new AdapterViewFormAttchmentFile(listModelAttchmentFile, getContext());
	
		form_cell_attchment_files.setAdapter(adapterFile);
		
		adapterFile.setOnClickItemListener(new OnClickItemListener() {
			
			@Override
			public void onClickItem(View view, int position) {
				removeAttchFile(listModelAttchmentFile, position, adapterFile);				
			}
		});
	}
	
	private void initAttchAdd() {
		form_cell_attchment_add.setOnClickListener(onClickListenerAttchFileAdd);
	}
	
	protected OnClickListener onClickListenerAttchFileAdd = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			facade.obtainFile(listModelAttchmentFile, listModelAttchmentPic, new CallBack() {
				@Override
				public void onSuccess(List<ModelPic> file, List<ModelPic> pic) {
					
					listModelAttchmentPic = pic;
					adapterPic.setListModelAttchmentFile(listModelAttchmentPic);
					adapterPic.notifyDataSetChanged();
				}
				
				@Override
				public void onFailure() {
					
				}
			});
		}
	};


	
	/**
	 * 设置输入类型
	 * @param type
	 */
	public void setInputType(int type){
		this.form_cell_value.setInputType(type);
	}

	private void removeAttchFile(final List<ModelPic> list, final int position, final BaseAdapter adapter) {
		final DialogNormal dialogNormal = new DialogNormal(getContext());
		dialogNormal.setTitle("确认移除？");
		dialogNormal.setContent(list.get(position).getName());
		dialogNormal.setOnClickListenerOK(new OnClickListener() {
			@Override
			public void onClick(View v) {
				list.remove(position);
				adapter.notifyDataSetChanged();
				dialogNormal.dismiss();
			}
		});
		dialogNormal.show();
	}


	@Override
	public void initWithModel(ModelViewFormCell model) {
		this.model = (ModelViewFormAttchmentCell) model;
		
		setLabel(this.model.getLabel());
		setValue(this.model.getValue());
		
	}
	
	
	private void modelSetValue(String value){
		model.setValue(value);
		
	}

	@Override
	public void formCommit() {
		

	}
	
	
	
	public void setLabel(String label) {
		form_cell_label.setText(label);
	}
	

	protected void setValue(String value) {
		form_cell_value.setText(value);
	}


	@Override
	public String getCellTag() {
		return model.getTag();
	}
}
