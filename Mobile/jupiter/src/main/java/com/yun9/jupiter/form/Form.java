package com.yun9.jupiter.form;

import com.yun9.jupiter.exception.JupiterRuntimeException;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.util.AssertValue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/5/25.
 */
public class Form implements java.io.Serializable {
    private FormBean formBean;

    private List<FormCell> cells;

    public static Form getInstance(FormBean formBean) {
        Form form = new Form(formBean);
        return form;
    }

    /**
     * 通过JSON配置生成表单；
     * JSON的格式需符合实体：com.yun9.jupiter.form.model.FormBean
     * @param json
     * @return
     */
    public static Form getInstance(String json) {
        Form form = new Form(null);
        return form;
    }

    private Form(FormBean formBean) {
        this.formBean = formBean;
        buildWithFormBean();
    }

    private void buildWithFormBean() {
        cells = new ArrayList<>();
        FormCellBean formCellBean;
        FormCell cell;
        Class<? extends FormCell> type;
        for (int i = 0; i < formBean.getCellBeanList().size(); i++) {
            formCellBean = formBean.getCellBeanList().get(i);
            type = FormUtilFactory.getInstance().getCellTypeClassByType(formCellBean.getType());
            if (type != null){
                cell = createCell(type,formCellBean);
                cells.add(cell);
            }
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
        Iterator<FormCell> iterator = cells.iterator();
        FormCell cell;
        while (iterator.hasNext()){
            cell = iterator.next();
            map.put(cell.getFormCellBean().getKey(),cell.getValue());
        }
        return map;
    }

    public String validate() {
        String message = null;
        for (FormCell cell : cells){
           message = cell.validate();
            if (AssertValue.isNotNullAndNotEmpty(message)) {
                return message;
            }
        }
        return message;
    }

    public Object getCellValue(String key) {
        for (FormCell tmp : cells) {
            if (tmp.getFormCellBean().getKey().equals(key)) {
                return tmp;
            }
        }
        return null;
    }

    public void setFormBean(FormBean formBean) {
        this.formBean = formBean;
    }

    /**
     * 从json配置中，重新加载表单元素的值
     * @param json 表单值配置
     */
    public void loadDataFromJson(String json) {

    }

}
