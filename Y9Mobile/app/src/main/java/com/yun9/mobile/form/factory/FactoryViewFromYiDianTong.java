package com.yun9.mobile.form.factory;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.maoye.form.factory.facade.FactoryFacadeFormViewCellDefault;
import com.maoye.form.factory.view.form.FactoryViewFromDefault;
import com.maoye.form.interfaces.AdapterShowImage;
import com.maoye.form.interfaces.engine.EngineFileObtain;
import com.maoye.form.interfaces.facade.FacadeAttchCell;
import com.maoye.form.model.form.cell.ModelViewFormAttchmentCell;
import com.maoye.form.model.form.cell.ModelViewFormImageCell;
import com.maoye.form.view.form.ViewFormAttchmentCell;
import com.maoye.form.view.form.ViewFormImageCell;
import com.yun9.mobile.form.impl.ImplAdapterShowImage;
import com.yun9.mobile.form.view.ViewFormAttchmentCellYiDianTong;
import com.yun9.mobile.form.view.ViewFormImageCellYiDianTong;

public class FactoryViewFromYiDianTong extends FactoryViewFromDefault{


	@Override
	public ViewFormAttchmentCell creatViewFormAttchmentCell(Context context, ModelViewFormAttchmentCell model) {
		
		List<EngineFileObtain> listEngineFileObtain = getEngineFileObtains(context);
		AdapterShowImage adapterShowImage = getAdapterShowImage(context);
		FacadeAttchCell facade = new FactoryFacadeFormViewCellDefault().creatFacadeAttchCell(context, listEngineFileObtain);
		return new ViewFormAttchmentCellYiDianTong(context, model, facade, adapterShowImage);
	}
	
	@Override
	public ViewFormImageCell creatViewFormImageCell(Context context, ModelViewFormImageCell model) {

		List<EngineFileObtain> listEngineFileObtain = new ArrayList<EngineFileObtain>();
		EngineFileObtain EngineFileObtainAlbum = new FactoryEngineFileObtain2Album().creatEngineFileObtain(context, "相册");
		listEngineFileObtain.add(EngineFileObtainAlbum);
		
		AdapterShowImage adapterShowImage = getAdapterShowImage(context);
		FacadeAttchCell facade = new FactoryFacadeFormViewCellDefault().creatFacadeAttchCell(context, listEngineFileObtain);
	
		return new ViewFormImageCellYiDianTong(context, model, facade, adapterShowImage);
	}
	
	
	public AdapterShowImage getAdapterShowImage(Context context) {

		AdapterShowImage adapterShowImage = new ImplAdapterShowImage(context);
		
		return adapterShowImage;
	}
	

	public List<EngineFileObtain> getEngineFileObtains(Context context) {
		
		List<EngineFileObtain> listEngineFileObtain = new ArrayList<EngineFileObtain>();
		EngineFileObtain EngineFileObtainAlbum = new FactoryEngineFileObtain2Album().creatEngineFileObtain(context, "相册");
		EngineFileObtain EngineFileObtainAttchment = new FactoryEngineFileObtain2Attchment().creatEngineFileObtain(context, "附件");
		listEngineFileObtain.add(EngineFileObtainAlbum);
		listEngineFileObtain.add(EngineFileObtainAttchment);
		
		return listEngineFileObtain;
	}




	
}
