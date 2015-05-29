package com.yun9.jupiter.form;

import com.yun9.jupiter.exception.JupiterRuntimeException;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.util.AssertValue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/5/25.
 */
public class Form implements java.io.Serializable {
    private FormBean formBean;

    private List<FormCell> cells;

    private Map<String,FormCell> cellMap;

    public static Form getInstance(FormBean formBean) {
        Form form = new Form(formBean);
        return form;
    }

    public Form(FormBean formBean) {
        this.formBean = formBean;
        buildWithFormBean();
    }

    private void buildWithFormBean() {
        cells = new ArrayList<>();
        cellMap = new HashMap<>();
        FormCellBean formCellBean;
        FormCell cell;
        for (int i = 0; i < formBean.getCellBeanList().size(); i++) {
            formCellBean = formBean.getCellBeanList().get(i);
            cell = createCell(formCellBean.getType(),formCellBean);
            cellMap.put(formCellBean.getKey(),cell);
            cells.add(cell);
        }
    }

    private  <T extends FormCell> T createCell(Class<T> type,FormCellBean bean) {

        try {
            FormCell formCell = type.getConstructor(FormCellBean.class).newInstance(bean);
            formCell.init();
            return (T) formCell;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new JupiterRuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new JupiterRuntimeException(e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new JupiterRuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new JupiterRuntimeException(e);
        }
    }

    public void putCell(FormCell formCell) {
        if (!AssertValue.isNotNull(cells)) {
            this.cells = new ArrayList<FormCell>();
        }
        this.cells.add(formCell);
    }

    public String getTitle() {
        return formBean.getTitle();
    }

    public String getKey() {
        return formBean.getKey();
    }


    public List<FormCell> getCells() {
        return cells;
    }

    public void setCells(List<FormCell> cells) {
        this.cells = cells;
    }

    /**
     * 获取Form绑定的数据model
     * 会触发generateFormValue方法，填充
     * FormBean里面的value属性
     * @return
     */
    public FormBean getFormBean() {
        formBean.setValue(generateFormValue());
        return formBean;
    }

    public Map<String,Object> generateFormValue() {
        Map<String,Object> map = new HashMap<>();
        for (FormCell cell : cells) {
            map.put(cell.getFormCellBean().getKey(),cell.getValue());
        }
        return map;
    }

    public Object getCellValue(String key) {
        return cellMap.get(key).getValue();
    }

    public void setFormBean(FormBean formBean) {
        this.formBean = formBean;
    }
}
