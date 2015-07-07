package com.yun9.wservice.view.common;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.SerialableEntry;

import java.util.List;

/**
 * Created by huangbinglong on 15/6/18.
 */
public class MultiSelectCommand extends JupiterCommand{

    private boolean isCancelable;

    private List<SerialableEntry<String, String>> selectedList;

    private List<SerialableEntry<String, String>> options;

    private String ctrlCode;

    private int minNum;

    private int maxNum;

    public boolean isCancelable() {
        return isCancelable;
    }

    public void setIsCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    public List<SerialableEntry<String, String>> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<SerialableEntry<String, String>> selectedList) {
        this.selectedList = selectedList;
    }

    public List<SerialableEntry<String, String>> getOptions() {
        return options;
    }

    public void setOptions(List<SerialableEntry<String, String>> options) {
        this.options = options;
    }

    public String getCtrlCode() {
        return ctrlCode;
    }

    public void setCtrlCode(String ctrlCode) {
        this.ctrlCode = ctrlCode;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }
}
