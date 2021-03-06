package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.yun9.jupiter.R;
import com.yun9.jupiter.view.JupiterGridView;

/**
 * Created by Leon on 15/6/2.
 */
public class JupiterEditIco extends JupiterRelativeLayout {

    private JupiterRowStyleTitleLayout rowStyleSutitleLayout;

    private JupiterGridView gridView;

    public JupiterEditIco(Context context) {
        super(context);
    }

    public JupiterEditIco(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterEditIco(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_edit_ico;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        rowStyleSutitleLayout = (JupiterRowStyleTitleLayout) this.findViewById(R.id.title_ll);
        gridView = (JupiterGridView) this.findViewById(R.id.item_grid_view);
    }

    public void setAdapter(JupiterEditAdapter adapter) {
        gridView.setAdapter(adapter);
    }

    public void edit(boolean edit) {
        this.getRowStyleSutitleLayout().setEnabled(edit);
        JupiterEditAdapter adapter = (JupiterEditAdapter) this.gridView.getAdapter();
        adapter.edit(edit);
    }

    public JupiterRowStyleTitleLayout getRowStyleSutitleLayout() {
        return rowStyleSutitleLayout;
    }

    public void setTitle(String title) {
        this.getRowStyleSutitleLayout().getTitleTV().setText(title);
    }

}
