package com.yun9.mobile.framework.util;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogutil extends ProgressDialog
{
    private ProgressDialog pd;

    public ProgressDialogutil(Context context)
    {
        super(context);
    }

    public void initProgressDialog(Context context,String msg)
    {
        if(pd == null)
        {
            pd = new ProgressDialog(context);
        }
        pd.setMessage(msg);
        pd.setCancelable(false);
    }

    public void showProgressDialog()
    {
        if(pd != null)
        {
            if(!pd.isShowing())
            {
                pd.show();
            }
        }
    }

    public void closeProgressDialog()
    {
        if(pd != null)
        {
            if(pd.isShowing())
            {
                pd.dismiss();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        pd.dismiss();
    }
}
