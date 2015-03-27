package com.maoye.form.utils;

import com.maoye.form.model.form.ModelViewForm;
import com.maoye.form.view.form.ViewForm;
import com.maoye.form.view.form.ViewForm.CallBackTranform2Model;
import com.maoye.form.view.form.ViewForm.TranType;

public class UtilModelAndView4Form {
	
	@Deprecated
	public static String view2Json(ViewForm form){
		try {
			ModelViewForm model = form.tranform2Model();
			return UtilModelAndJson4Form.model2Json(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void view2Json(ViewForm form, CallBackTranform2Model callBack){
		form.tranform2Model(callBack);
	}
	
	
	public static void view2DataJson(ViewForm form, CallBackTranform2Model callBack){
		form.tranform2Model(callBack, TranType.DATA);
	}
}
