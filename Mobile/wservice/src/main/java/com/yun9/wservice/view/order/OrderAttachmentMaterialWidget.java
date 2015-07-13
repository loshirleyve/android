package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Attachment;

import java.util.List;

/**
 * Created by huangbinglong on 7/1/15.
 */
public class OrderAttachmentMaterialWidget extends JupiterRelativeLayout{

    private JupiterRowStyleTitleLayout titleLayout;

    private ListView listView;

    private List<Attachment> attachments;

    private JupiterAdapter adapter;

    public OrderAttachmentMaterialWidget(Context context) {
        super(context);
    }

    public OrderAttachmentMaterialWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderAttachmentMaterialWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_attachment_material;
    }

    public void buildWithData(List<Attachment> attachments) {
        this.attachments = attachments;
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        titleLayout = (JupiterRowStyleTitleLayout) this.findViewById(R.id.title_layout);
        listView = (ListView) this.findViewById(R.id.material_attach_lv);
        buildView();
    }

    private void buildView() {
        titleLayout.getHotNitoceTV().setText("未选择");

        adapter = new JupiterAdapter() {
            @Override
            public int getCount() {
                if (attachments != null){
                    return attachments.size();
                }
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                JupiterRowStyleTitleLayout temp;
                if (convertView == null){
                    temp = new JupiterRowStyleTitleLayout(OrderAttachmentMaterialWidget.this.mContext);
                    temp.getArrowRightIV().setVisibility(GONE);
                    temp.getMainIV().setVisibility(GONE);
                    temp.getTitleTV().setText(attachments.get(position).getAttachname());
                    temp.getContainer().setBackgroundColor(getResources().getColor(R.color.yellow_background));
                    convertView = temp;
                }
                return convertView;
            }
        };
        listView.setAdapter(adapter);
    }

    public JupiterRowStyleTitleLayout getTitleLayout() {
        return titleLayout;
    }

    public ListView getListView() {
        return listView;
    }

}
