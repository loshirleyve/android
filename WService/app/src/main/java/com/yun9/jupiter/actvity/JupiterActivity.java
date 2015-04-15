package com.yun9.jupiter.actvity;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.Toast;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.actvity.annotation.EventListener;
import com.yun9.jupiter.actvity.annotation.Select;
import com.yun9.jupiter.actvity.annotation.ViewInject;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.annotation.BeanInject;
import com.yun9.jupiter.util.AssertValue;

public abstract class JupiterActivity extends Activity {

    private ProgressDialog progressDialog;

    private static boolean isShowToast = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (AssertValue.isNotNull(progressDialog)) {
            progressDialog.dismiss();
        }
    }

    public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initInjectedView(this);
	}


	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initInjectedView(this);
	}


	public void setContentView(View view) {
		super.setContentView(view);
		initInjectedView(this);
	}
	

	public static void initInjectedView(Activity activity){
		initInjectedView(activity,activity.getApplicationContext(), activity.getWindow().getDecorView());
	}

	public static void initInjectedView(Object injectedSource,Context context,View sourceView){
        JupiterApplication app =  (JupiterApplication)context.getApplicationContext();
        BeanManager beanManager = app.getBeanManager();

    	Field[] fields = injectedSource.getClass().getDeclaredFields();
		if(fields!=null && fields.length>0){
			for(Field field : fields){
				try {
					field.setAccessible(true);
					
					if(field.get(injectedSource)!= null )
						continue;
				
					ViewInject viewInject = field.getAnnotation(ViewInject.class);
					if(viewInject!=null){
						
						int viewId = viewInject.id();
					    field.set(injectedSource,sourceView.findViewById(viewId));
					
					    setListener(injectedSource,field,viewInject.click(),Method.Click);
						setListener(injectedSource,field,viewInject.longClick(),Method.LongClick);
						setListener(injectedSource,field,viewInject.itemClick(),Method.ItemClick);
						setListener(injectedSource,field,viewInject.itemLongClick(),Method.itemLongClick);
						
						Select select = viewInject.select();
						if(!TextUtils.isEmpty(select.selected())){
							setViewSelectListener(injectedSource,field,select.selected(),select.noSelected());
						}
						
					}

                    BeanInject beanInject = field.getAnnotation(BeanInject.class);

                    if (beanInject !=null){
                        Object beanObj = beanManager.get(field.getType());
                        field.set(injectedSource,beanObj);
                    }

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private static void setViewSelectListener(Object injectedSource,Field field,String select,String noSelect)throws Exception{
		Object obj = field.get(injectedSource);
		if(obj instanceof View){
			((AbsListView)obj).setOnItemSelectedListener(new EventListener(injectedSource).select(select).noSelect(noSelect));
		}
	}
	
	
	private static void setListener(Object injectedSource,Field field,String methodName,Method method)throws Exception{
		if(methodName == null || methodName.trim().length() == 0)
			return;
		
		Object obj = field.get(injectedSource);
		
		switch (method) {
			case Click:
				if(obj instanceof View){
					((View)obj).setOnClickListener(new EventListener(injectedSource).click(methodName));
				}
				break;
			case ItemClick:
				if(obj instanceof AbsListView){
					((AbsListView)obj).setOnItemClickListener(new EventListener(injectedSource).itemClick(methodName));
				}
				break;
			case LongClick:
				if(obj instanceof View){
					((View)obj).setOnLongClickListener(new EventListener(injectedSource).longClick(methodName));
				}
				break;
			case itemLongClick:
				if(obj instanceof AbsListView){
					((AbsListView)obj).setOnItemLongClickListener(new EventListener(injectedSource).itemLongClick(methodName));
				}
				break;
			default:
				break;
		}
	}
	
	public enum Method{
		Click,LongClick,ItemClick,itemLongClick
	}


    protected void openDialog() {
        this.openDialog(null);
    }

    protected void openDialog(String msg) {
        this.openDialog(msg, false);
    }

    protected void openDialog(String msg, boolean cancel) {
        if (!AssertValue.isNotNull(progressDialog)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(cancel);
        }

        progressDialog.show();
    }

    protected void hideDialog() {
        if (AssertValue.isNotNull(progressDialog)) {
            progressDialog.dismiss();
        }
    }

    protected void showToast(String msg) {
        if (isShowToast) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    protected void showToast(int msg) {
        if (isShowToast) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }


}
