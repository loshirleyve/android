package com.yun9.jupiter.form;

import com.yun9.jupiter.exception.JupiterRuntimeException;
import com.yun9.jupiter.form.cell.TextFormCell;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/25.
 */
public class Form implements java.io.Serializable {
    private String title;

    private String key;

    private List<FormCell> cells;

    public static Form getInstance() {
        Form form = new Form();
        return form;
    }

    public <T extends FormCell> T createCell(Class<T> type) {

        try {
            FormCell formCell = type.newInstance();
            formCell.setType(type);
            formCell.init();
            return (T) formCell;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new JupiterRuntimeException(e);
        } catch (IllegalAccessException e) {
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
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<FormCell> getCells() {
        return cells;
    }

    public void setCells(List<FormCell> cells) {
        this.cells = cells;
    }
}
