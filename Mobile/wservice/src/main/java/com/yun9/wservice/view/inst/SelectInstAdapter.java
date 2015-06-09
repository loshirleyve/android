package com.yun9.wservice.view.inst;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yun9.jupiter.model.Inst;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;

import java.util.List;

/**
 * Created by Leon on 15/6/9.
 */
public class SelectInstAdapter extends JupiterAdapter {

    private List<Inst> mInsts;
    private Context mContext;

    public SelectInstAdapter(Context context,List<Inst> insts){
        this.mInsts = insts;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mInsts.size();
    }

    @Override
    public Object getItem(int position) {
        return mInsts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JupiterRowStyleSutitleLayout rowStyleSutitleLayout = null;
        Inst inst = mInsts.get(position);

        if (AssertValue.isNotNull(convertView)){
            rowStyleSutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
        }else{
            rowStyleSutitleLayout = new JupiterRowStyleSutitleLayout(mContext);
            rowStyleSutitleLayout.setShowArrow(true);
            rowStyleSutitleLayout.setShowTime(false);
            rowStyleSutitleLayout.setShowSutitleText(false);
            rowStyleSutitleLayout.setShowMainImage(false);

        }

        rowStyleSutitleLayout.setTag(inst);
        rowStyleSutitleLayout.getTitleTV().setText(inst.getName());

        return rowStyleSutitleLayout;
    }
}
