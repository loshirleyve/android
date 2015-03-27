package com.yun9.mobile.framework.dlg.prodlg;

import java.util.Random;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.dlg.nifty.Effectstype;
import com.yun9.mobile.framework.dlg.nifty.NiftyDialogBuilder;

public class ProDlgForce extends NiftyDialogBuilder{
	private Effectstype effect;
	private NiftyDialogBuilder dialogBuilder;
	private static int static_theme = R.style.dialog_untran;
	private int rand;
	/**
	 * @param context
	 */
	public ProDlgForce(Context context) {
		this(context, static_theme);
	}
	
	public ProDlgForce(Context context, int theme) {
		super(context, theme);
		init();
	}
	
	
	private void init(){
			//.withTitle(null)  no title
			withTitle("正在处理中...");
	        withTitleColor("#FFFFFF");                                 
	        withDividerColor("#11000000");                             
	        withMessage(null);                               
	        isCancelableOnTouchOutside(false); 
	        withDuration(700);
	        setCustomView(R.layout.dlg_proforce, getContext());
	}
	
	
	@Override
	public void show() {
		withEffect(randEffect());
		super.show();
	}
	
	public NiftyDialogBuilder getDlgBuilder(){
		return dialogBuilder;
	}
	
	private Effectstype randEffect(){
		Effectstype effect;
		int max=14;
		int min=1;
		Random random = new Random();
		rand = random.nextInt(max)%(max-min+1) + min;
		switch(rand){
			case 1:
				effect=Effectstype.Fadein;break;
	        case 2:
	        	effect=Effectstype.Slideright;break;
	        case 3:
	        	effect=Effectstype.Slideleft;break;
	        case 4:
	        	effect=Effectstype.Slidetop;break;
	        case 5:
	        	effect=Effectstype.SlideBottom;break;
	        case 6:
	        	effect=Effectstype.Newspager;break;
	        case 7:
	        	effect=Effectstype.Fall;break;
	        case 8:
	        	effect=Effectstype.Sidefill;break;
	        case 9:
	        	effect=Effectstype.Fliph;break;
	        case 10:
	        	effect=Effectstype.Flipv;break;
	        case 11:
	        	effect=Effectstype.RotateBottom;break;
	        case 12:
	        	effect=Effectstype.RotateLeft;break;
	        case 13:
	        	effect=Effectstype.Slit;break;
	        case 14:
	        	effect=Effectstype.Shake;break;
	        default:
	        	effect=Effectstype.Fadein;break;
		}
		return effect;
	}
}
