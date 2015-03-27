package com.maoye.form.view.form;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.maoye.form.R;
import com.maoye.form.adapter.AdapterViewFormAttchmentPic;
import com.maoye.form.adapter.AdapterViewFormImagePic;
import com.maoye.form.dialog.DialogNormal;
import com.maoye.form.interfaces.AdapterShowImage;
import com.maoye.form.interfaces.facade.FacadeAttchCell;
import com.maoye.form.interfaces.facade.FacadeAttchCell.CallBack;
import com.maoye.form.model.ModelPic;
import com.maoye.form.model.ModelFile.FileType;
import com.maoye.form.model.form.ModelAttchmentPic;
import com.maoye.form.model.form.cell.ModelViewFormCell;
import com.maoye.form.model.form.cell.ModelViewFormImageCell;
import com.maoye.form.view.GalleryAlignLeft;
import com.maoye.form.view.GalleryAlignLeft.IOnItemClickListener;

public class ViewFormImageCell extends ViewFormCell {

	protected ImageButton form_cell_image_pic;
	protected TextView form_cell_label;
	protected GalleryAlignLeft form_cell_image_gallery;
	
	protected FacadeAttchCell facade;
	protected AdapterShowImage imageLoader;
	protected ModelViewFormImageCell model;
	
	protected List<ModelPic> listModelAttchmentPic;
	protected AdapterViewFormImagePic adapter;
	
	public ViewFormImageCell(Context context, ModelViewFormImageCell model, FacadeAttchCell facade,AdapterShowImage imageLoader) {
		super(context, model);
		this.model = model;
		this.imageLoader = imageLoader;
		this.facade = facade;
		findView();
		init();
		initWithModel(model);
		setEvent();
		
	}

	
	private void setEvent() {
		
		form_cell_image_pic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				facade.obtainFile(null, listModelAttchmentPic, new CallBack() {
					@Override
					public void onSuccess(List<ModelPic> file, List<ModelPic> pic) {
						listModelAttchmentPic = pic;
						adapter.setPics(listModelAttchmentPic);
						adapter.notifyDataSetChanged();
					}
					
					@Override
					public void onFailure() {
						
					}
				});
			}
		});
		
		
		form_cell_image_gallery.setOnItemClickListener(new IOnItemClickListener() {
			@Override
			public void onItemClick(int position) {
				removeAttchFile(listModelAttchmentPic, position, adapter);
			}
		});
	}


	private void init() {
		listModelAttchmentPic = new ArrayList<ModelPic>();
		
		
		initImages();
	}


	private void findView(){
		LayoutInflater.from(getContext()).inflate(R.layout.form_cell_image, this);
		form_cell_label = (TextView) findViewById(R.id.form_cell_label);
		form_cell_image_pic = (ImageButton) findViewById(R.id.form_cell_image_pic);
		form_cell_image_gallery = (GalleryAlignLeft) findViewById(R.id.form_cell_image_gallery);
	}
	
	private void initImages() {
		
//		MarginLayoutParams mlp = (MarginLayoutParams) form_cell_image_gallery.getLayoutParams();
//		mlp.setMargins(0, mlp.topMargin, mlp.rightMargin, mlp.bottomMargin);
		
		if(model != null && model.getPics() != null){
			for(ModelAttchmentPic pic : model.getPics()){
				ModelPic modelPic = new ModelPic();
				modelPic.setValue(pic.getValue());
				modelPic.setCreatedate(pic.getCreatedate());
				modelPic.setEmumType(FileType.PicNetWork);
				listModelAttchmentPic.add(modelPic);
			}
		}
		
		adapter = new AdapterViewFormImagePic(listModelAttchmentPic, getContext(), imageLoader);
		form_cell_image_gallery.setAdapter(adapter);
		
	}
	
	
	@Override
	public void formCommit() {
	}


	@Override
	public void initWithModel(ModelViewFormCell model) {
		setLabel(model.getLabel());
		
	}
	public void setLabel(String label) {
		form_cell_label.setText(label);
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
	public String getCellTag() {
		return model.getTag();
	}
}
