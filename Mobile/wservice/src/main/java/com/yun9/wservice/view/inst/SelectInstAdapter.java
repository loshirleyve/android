package com.yun9.wservice.view.inst;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yun9.jupiter.listener.OnSelectListener;
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
    private OnSelectListener onSelectListener;
    private Inst currInst;

    public SelectInstAdapter(Context context,List<Inst> insts){
        this.mInsts = insts;
        this.mContext = context;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public void setCurrInst(Inst currInst) {
        this.currInst = currInst;
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
    public View getView(int position, final View convertView, final ViewGroup parent) {
        JupiterRowStyleSutitleLayout rowStyleSutitleLayout = null;
        Inst inst = mInsts.get(position);

        if (AssertValue.isNotNull(convertView)){
            rowStyleSutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
        }else{
            rowStyleSutitleLayout = new JupiterRowStyleSutitleLayout(mContext);
            rowStyleSutitleLayout.setShowArrow(false);
            rowStyleSutitleLayout.setShowTime(false);
            rowStyleSutitleLayout.setShowSutitleText(false);
            rowStyleSutitleLayout.setShowMainImage(false);
            rowStyleSutitleLayout.setSelectMode(true);
            rowStyleSutitleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JupiterRowStyleSutitleLayout temp = (JupiterRowStyleSutitleLayout) v;
                     if (onSelectListener != null && !temp.isSelected()){
                        int count = SelectInstAdapter.this.getCount();
                        for (int i = 0; i < count; i++) {
                            JupiterRowStyleSutitleLayout t =
                                    (JupiterRowStyleSutitleLayout)
                                            parent.getChildAt(i);
                            t.select(false);
                        }
                        temp.select(true);
                        onSelectListener.onSelect(temp,true);
                    }
                }
            });
            if (AssertValue.isNotNull(onSelectListener)){
                rowStyleSutitleLayout.setOnSelectListener(onSelectListener);
            }

        }

        if (AssertValue.isNotNull(currInst) && currInst.getId().equals(inst.getId())){
            rowStyleSutitleLayout.select(true);
        }else{
            rowStyleSutitleLayout.select(false);
        }

        rowStyleSutitleLayout.setTag(inst);
        rowStyleSutitleLayout.getTitleTV().setText(inst.getName());

        return rowStyleSutitleLayout;
    }
}
