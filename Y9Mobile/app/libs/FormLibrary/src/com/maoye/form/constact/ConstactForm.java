package com.maoye.form.constact;

import com.maoye.form.model.form.cell.ModelViewFormAttchmentCell;
import com.maoye.form.model.form.cell.ModelViewFormEditTextCell;
import com.maoye.form.model.form.cell.ModelViewFormImageCell;
import com.maoye.form.model.form.cell.ModelViewFormMulSelectedCell;
import com.maoye.form.model.form.cell.ModelViewFormRadioCell;
import com.maoye.form.model.form.cell.ModelViewFormTextCell;
import com.maoye.form.model.form.cell.ModelViewFormTimeCell;

public class ConstactForm {
	
	public static String SUFFIX_TXT = "txt";
	public static String SUFFIX_PDF = "pdf";
	public static String SUFFIX_DOC = "doc";
	public static String SUFFIX_DOCX = "docx";
	public static String SUFFIX_PPT = "ppt";
	public static String SUFFIX_PPTX = "pptx";
	public static String SUFFIX_XLS = "xls";
	public static String SUFFIX_XLSX = "xlsx";
	public static String SUFFIX_WPS = "wps";
	
	
	public static final String KEY_ROWS = "rows";
	public static final String KEY_CELLS = "cells";
	public static final String KEY_TYPE = "type";
	
	public static final Class MODEL_VIEWFORM_TEXTCELL = ModelViewFormTextCell.class;
	public static final Class MODEL_VIEWFORM_RADIOCELL = ModelViewFormRadioCell.class;
	public static final Class MODEL_VIEWFORM_TIMECELL = ModelViewFormTimeCell.class;
	public static final Class MODEL_VIEWFORM_EDITTEXTCELL = ModelViewFormEditTextCell.class;
	public static final Class MODEL_VIEWFORM_ATTCHMENTCELL = ModelViewFormAttchmentCell.class;
	public static final Class MODEL_VIEWFORM_MULSELECTEDCELL = ModelViewFormMulSelectedCell.class;
	public static final Class MODEL_VIEWFORM_IMAGECELL = ModelViewFormImageCell.class;
	
	
	
	public static final Class MODEL_VIEWFORM_CLASS[] = new Class[]
	{
		MODEL_VIEWFORM_TEXTCELL,
		MODEL_VIEWFORM_RADIOCELL,
		MODEL_VIEWFORM_TIMECELL,
		MODEL_VIEWFORM_EDITTEXTCELL,
		MODEL_VIEWFORM_ATTCHMENTCELL,
		MODEL_VIEWFORM_MULSELECTEDCELL,
		MODEL_VIEWFORM_IMAGECELL
	};
	

}
