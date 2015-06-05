package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.ImageFormCellBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterBadgeView;
import com.yun9.jupiter.widget.BasicJupiterEditIcoAdapter;
import com.yun9.jupiter.widget.JupiterEditIco;
import com.yun9.jupiter.widget.JupiterEditIcoAdapter;
import com.yun9.jupiter.widget.JupiterTextIco;
import com.yun9.jupiter.widget.JupiterTextIcoWithoutCorner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/25.
 */
public class ImageFormCell extends FormCell {

    private Context context;

    private JupiterEditIco jupiterEditIco;

    private JupiterEditIcoAdapter adapter;

    private ImageFormCellBean cellBean;

    private boolean edit;

    private List<JupiterTextIco> itemList;

    public ImageFormCell(FormCellBean cellBean) {
        super(cellBean);
        this.cellBean = (ImageFormCellBean) cellBean;
    }


    @Override
    public View getCellView(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
        View rootView = LayoutInflater.from(context).inflate(R.layout.form_cell_image, null);
        jupiterEditIco = (JupiterEditIco) rootView.findViewById(R.id.edit_ico);
        this.build();
        setupEditIco();
        return rootView;
    }

    private void build() {
        jupiterEditIco.getRowStyleSutitleLayout().getTitleTV().setText(cellBean.getLabel());
        StringBuffer sb = new StringBuffer();
        if (cellBean.getMinNum() > 0) {
            sb.append("至少 " + cellBean.getMinNum() + "个");
        }
        if (cellBean.getMaxNum() > 0) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append("最多选择 " + cellBean.getMaxNum() + "个");
        }
        if (sb.length() > 0) {
            jupiterEditIco.getRowStyleSutitleLayout().getHotNitoceTV().setVisibility(View.VISIBLE);
            jupiterEditIco.getRowStyleSutitleLayout()
                            .getHotNitoceTV()
                                .setBackgroundColor(this.context.getResources().getColor(R.color.whites));
            jupiterEditIco.getRowStyleSutitleLayout()
                    .getHotNitoceTV().setText(sb);
        }
    }

    private void setupEditIco() {
        appendAddButton();
        adapter = new BasicJupiterEditIcoAdapter(itemList);
        jupiterEditIco.setAdapter(adapter);
        this.restore();
    }

    private void appendAddButton() {
        JupiterTextIco item = new JupiterTextIcoWithoutCorner(this.context);
        item.setTitle(null);
        item.setImage("drawable://" + R.drawable.upload_icon);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUploadImage();
            }
        });
        itemList.add(item);
    }

    private void restore() {
        if (cellBean.getValue() == null) {
            return;
        }
        String[] ids = ((String) cellBean.getValue()).split(",");
        for (String id : ids) {
            createItem(id);
        }
        adapter.notifyDataSetChanged();
    }

    private void startUploadImage() {
        // TODO 调用相册功能，添加图片
        createItem("drawable://" + R.drawable.add_user);
        reCaculateLimit();
        adapter.notifyDataSetChanged();
    }

    private JupiterTextIco createItem(String id) {
        final JupiterTextIco item = new JupiterTextIco(this.context);
        item.setTitle(null);
        item.setImage(id);
        item.hideCorner();
        item.setTag(id);
        item.setCornerImage(R.drawable.icn_delete);
        item.getBadgeView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItm(item);
            }
        });
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示大图
            }
        });
        itemList.add(0, item);
        return item;
    }

    private void deleteItm(JupiterTextIco item) {
        itemList.remove(item);
        reCaculateLimit();
        adapter.notifyDataSetChanged();
    }

    private void reCaculateLimit() {
        if (itemList.size() > cellBean.getMaxNum()) {
            itemList = itemList.subList(0, cellBean.getMaxNum());
        } else if (itemList.size() < cellBean.getMaxNum()) {
            JupiterTextIco item = itemList.get(itemList.size() - 1);
            // 只有添加按钮的tag为null
            if (item.getTag() != null) {
                appendAddButton();
            }
        }
    }

    @Override
    public void edit(boolean edit) {
        this.edit = edit;
        adapter.edit(edit);
    }

    @Override
    public Object getValue() {
        StringBuffer sb = new StringBuffer();
        for (JupiterTextIco ico : itemList) {
            if (ico.getTag() != null) {
                sb.append(ico.getTag()).append(",");
            }
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return sb;
    }

    @Override
    public FormCellBean getFormCellBean() {
        return cellBean;
    }

}
