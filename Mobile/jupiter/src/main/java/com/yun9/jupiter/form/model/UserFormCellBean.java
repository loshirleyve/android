package com.yun9.jupiter.form.model;

import com.yun9.jupiter.form.FormCell;

/**
 * Created by huangbinglong on 15/5/30.
 */
public class UserFormCellBean extends FormCellBean{

    private int mode;

    public class MODE {
       public static final int USER = 1; // 选择用户
       public static final int DEPT = 2; // 选择部门
       public static final int MIX = 3; // 混合
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void add(String userid,String name,String headurl){

    }
}
