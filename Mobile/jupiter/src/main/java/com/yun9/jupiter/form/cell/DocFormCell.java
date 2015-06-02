package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.model.DocFormCellBean;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.ImageFormCellBean;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterBadgeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Leon on 15/5/25.
 */
public class DocFormCell extends FormCell {

    public static final int DEFAULT_DOC_NUM_LIMIT = 3;

    private Context context;

    private LinearLayout docContainer;

    private ImageButton addDocButton;

    private TextView titleDesc;

    private List<LinearLayout> docViews;

    private List<JupiterBadgeView> badgeViews;

    private List<String> docIds;

    private DocFormCellBean cellBean;

    public DocFormCell(FormCellBean cellBean) {
        super(cellBean);
        this.cellBean = (DocFormCellBean) cellBean;
    }


    @Override
    public View getCellView(Context context) {
        this.context = context;
        docIds = new ArrayList<>(DEFAULT_DOC_NUM_LIMIT);
        docViews = new ArrayList<>(DEFAULT_DOC_NUM_LIMIT);
        badgeViews = new ArrayList<>(DEFAULT_DOC_NUM_LIMIT);
        View rootView = LayoutInflater.from(context).inflate(R.layout.form_cell_doc,null);
        addDocButton = (ImageButton) rootView.findViewById(R.id.addDoc_ib);
        docContainer = (LinearLayout) rootView.findViewById(R.id.doc_container_ll);
        titleDesc = (TextView) rootView.findViewById(R.id.title_desc);
        titleDesc.setText(cellBean.getLabel());
        addDocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUploadDoc();
            }
        });
        restore();
        return rootView;
    }

    private void restore() {
        if (cellBean.getValue() == null) {
            return;
        }
        String[] ids = ((String)cellBean.getValue()).split(",");
        rebuildContainer(ids);
    }

    private void rebuildContainer(String[] ids) {
        docIds.clear();
        docViews.clear();
        docContainer.removeAllViews();
        LinearLayout docView = null;
        for (int i = 0; i < ids.length; i++) {
            docIds.add(ids[i]);
            docView = createCloseableDocView(i);
            docContainer.addView(docView);
            docViews.add(docView);
        }
        if (docIds.size() >= DEFAULT_DOC_NUM_LIMIT) {
            addDocButton.setVisibility(View.GONE);
        } else {
            addDocButton.setVisibility(View.VISIBLE);
        }
        appendBadges();
    }

    private void appendBadges() {
        badgeViews.clear();
        for (int i = 0; i < docViews.size(); i++) {
            JupiterBadgeView badgeView = new JupiterBadgeView(context, docViews.get(i));
            badgeView.setBadgePosition(JupiterBadgeView.POSITION_BOTTOM_RIGHT);
            badgeView.setBackgroundResource(R.drawable.icn_delete);
            badgeView.setBadgeSize(20,20);
            final int finalI = i;
            badgeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeDocView(finalI);
                }
            });
            badgeView.show();
            badgeViews.add(badgeView);
        }
    }

    private void toggleBadges(boolean isShow) {
        for (int i = 0; i < badgeViews.size(); i++) {
            if (isShow) {
                badgeViews.get(i).show();
            } else {
                badgeViews.get(i).hide();
            }
        }
    }

    private LinearLayout createCloseableDocView(final int position) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        ImageView imageView = new ImageView(this.context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(PublicHelp.dip2px(context, 40), PublicHelp.dip2px(context, 40)));
        imageView.setImageResource(R.drawable.fileicon);
        linearLayout.addView(imageView);
        TextView textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(16);
        textView.setText("顶聚科技工商注册材料"+position+".doc");
        linearLayout.addView(textView);
        return linearLayout;
    }

    private void removeDocView(int position) {
        docIds.remove(position);
        rebuildContainer(docIds.toArray(new String[0]));
    }

    private void startUploadDoc() {
        // TODO 调用相册功能，添加图片
        docIds.add("xx");
        rebuildContainer(docIds.toArray(new String[0]));
    }

    @Override
    public void edit(boolean edit) {
        if (edit) {
            addDocButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startUploadDoc();
                }
            });
        } else {
            addDocButton.setOnClickListener(null);
        }
        toggleBadges(edit);
    }

    @Override
    public Object getValue() {
        StringBuffer sb = new StringBuffer();
        for (String id : docIds) {
            sb.append(id).append(",");
        }
        if (sb.length() > 0) {
            return sb.substring(0,sb.length() - 1);
        }
        return sb;
    }

    @Override
    public FormCellBean getFormCellBean() {
        return cellBean;
    }
}
