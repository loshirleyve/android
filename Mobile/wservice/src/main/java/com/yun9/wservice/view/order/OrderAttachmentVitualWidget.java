package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yun9.jupiter.exception.JupiterRuntimeException;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.form.cell.DetailFormCell;
import com.yun9.jupiter.form.cell.DocFormCell;
import com.yun9.jupiter.form.cell.ImageFormCell;
import com.yun9.jupiter.form.cell.MultiSelectFormCell;
import com.yun9.jupiter.form.cell.TextFormCell;
import com.yun9.jupiter.form.cell.UserFormCell;
import com.yun9.jupiter.form.model.DetailFormCellBean;
import com.yun9.jupiter.form.model.DocFormCellBean;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.ImageFormCellBean;
import com.yun9.jupiter.form.model.MultiSelectFormCellBean;
import com.yun9.jupiter.form.model.TextFormCellBean;
import com.yun9.jupiter.form.model.UserFormCellBean;
import com.yun9.jupiter.model.SerialableEntry;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.AttachmentInputType;
import com.yun9.wservice.model.AttachTransferWay;
import com.yun9.wservice.model.Attachment;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by huangbinglong on 15/7/1.
 */
public class OrderAttachmentVitualWidget extends JupiterRelativeLayout{

    private LinearLayout formPage;

    private List<FormCell> cells;

    public OrderAttachmentVitualWidget(Context context) {
        super(context);
    }

    public OrderAttachmentVitualWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderAttachmentVitualWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_attachment_vitual;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        formPage = (LinearLayout) this.findViewById(R.id.form_page);
        cells = new ArrayList<>();
        buildView();
    }

    public void buildWithData(List<Attachment> attachments) {
        List<FormCellBean> cellBeans = toFormCellBeans(attachments);
        FormCellBean formCellBean;
        FormCell cell;
        Class<? extends FormCell> type;
        for (int i = 0; i < cellBeans.size(); i++) {
            formCellBean = cellBeans.get(i);
            type = FormUtilFactory.getInstance().getCellTypeClassByType(formCellBean.getType());
            if (type != null){
                cell = FormUtilFactory.createCell(type, formCellBean);
                cells.add(cell);
                View view = cell.getCellView(this.mContext);
                if (AssertValue.isNotNull(view)) {
                    formPage.addView(view);
                    cell.edit(true);
                }
            }
        }
    }

    private List<FormCellBean> toFormCellBeans(List<Attachment> attachments) {
        List<FormCellBean> cellBeans = new ArrayList<>();
        // 目前只有这两种，先这样处理吧
        for (Attachment attachment : attachments) {
            if (AttachmentInputType.FILE.equals(attachment.getInputtype())){
                DocFormCellBean cellBean = new DocFormCellBean();
                cellBean.setType(DocFormCell.class.getSimpleName());
                cellBean.setKey(attachment.getAttachkey());
                cellBean.setId(attachment.getId());
                cellBean.setLabel(attachment.getAttachname());
                cellBean.setMaxNum(3);
                cellBeans.add(cellBean);
            } else if (AttachmentInputType.FILE.equals(attachment.getInputtype())){
                TextFormCellBean cellBean = new TextFormCellBean();
                cellBean.setType(TextFormCell.class.getSimpleName());
                cellBean.setKey(attachment.getAttachkey());
                cellBean.setId(attachment.getId());
                cellBean.setLabel(attachment.getAttachname());
                cellBean.setDefaultValue(attachment.getInputvalue());
                cellBeans.add(cellBean);
            }
        }
        return cellBeans;
    }

    private void buildView() {

    }

    public List<Attachement> getValue() {
        List<Attachement> attachements = new ArrayList<>();
        String validate;
        for (FormCell formCell : cells){
            validate = formCell.validate();
            if (AssertValue.isNotNullAndNotEmpty(validate)){
                Toast.makeText(this.mContext,validate,Toast.LENGTH_SHORT).show();
                return null;
            }
            attachements.add(new Attachement(formCell.getFormCellBean().getId(),
                    formCell.getStringValue()));
        }
        return attachements;
    }

    public class Attachement{
        private String id;
        private String inputvalue;

        public Attachement(String id, String inputvalue) {
            this.id = id;
            this.inputvalue = inputvalue;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInputvalue() {
            return inputvalue;
        }

        public void setInputvalue(String inputvalue) {
            this.inputvalue = inputvalue;
        }
    }
}
