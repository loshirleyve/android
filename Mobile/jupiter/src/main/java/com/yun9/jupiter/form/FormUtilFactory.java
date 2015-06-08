package com.yun9.jupiter.form;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.yun9.jupiter.form.cell.DetailFormCell;
import com.yun9.jupiter.form.cell.DocFormCell;
import com.yun9.jupiter.form.cell.ImageFormCell;
import com.yun9.jupiter.form.cell.MultiSelectFormCell;
import com.yun9.jupiter.form.cell.TextFormCell;
import com.yun9.jupiter.form.cell.UserFormCell;
import com.yun9.jupiter.util.AssertValue;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/3.
 */
public class FormUtilFactory {

    private static FormUtilFactory singleton;

    private Map<String,Class<? extends FormCell>> cellClassMap;

    private Map<String,LoadValueHandler> loadValueHandlerMap;

    private Map<String,BizExecutor> bizExecutorMap;

    public static FormUtilFactory getInstance(){
        synchronized (FormUtilFactory.class){
            if (singleton == null){
                singleton = new FormUtilFactory();
            }
        }
        return singleton;
    }

    private FormUtilFactory() {
        cellClassMap = new HashMap<>();
        loadValueHandlerMap = new HashMap<>();
        bizExecutorMap = new HashMap<>();
        initCellType();
    }

    private void initCellType() {
        registerCellType("TextFormCell", TextFormCell.class);
        registerCellType("DocFormCell", DocFormCell.class);
        registerCellType("ImageFormCell", ImageFormCell.class);
        registerCellType("MultiSelectFormCell", MultiSelectFormCell.class);
        registerCellType("UserFormCell", UserFormCell.class);
        registerCellType("DetailFormCell", DetailFormCell.class);
    }

    public void registerCellType(String name,Class<? extends FormCell> cellClass) {
        if (AssertValue.isNotNullAndNotEmpty(name)
                && cellClass != null) {
            cellClassMap.put(name,cellClass);
        }
    }

    /**
     * 根据类型标示获取cell的Class对象
     * @param type 类型标识，字符串
     * @return
     */
    public Class<? extends FormCell> getCellTypeClassByType(String type) {
        return cellClassMap.get(type);
    }

    /**
     * 注册取值器
     * @param type 取值器类型，请使用LoadValueHandler.Type_*
     * @param handler 自定义的取值处理器
     */
    public void registerLoadValueHandler(String type,LoadValueHandler handler) {
        if (AssertValue.isNotNullAndNotEmpty(type)
                && handler != null) {
            loadValueHandlerMap.put(type,handler);
        }
    }

    public LoadValueHandler getLoadValueHandler(String type) {
        return loadValueHandlerMap.get(type);
    }

    public interface LoadValueHandler{
        /*** 用户*/
        public static final String TYPE_USER = "user";
        /*** 部门*/
        public static final String TYPE_DEPT = "dept";

        public void load(String type,String id,LoadValueCompleted callback);
    }

    public interface LoadValueCompleted{
        public void callback(Map<String,Object> value);
    }

    public void registerBizExecutor(String type,BizExecutor executor) {
        if (AssertValue.isNotNullAndNotEmpty(type)
                && executor != null) {
            bizExecutorMap.put(type,executor);
        }
    }

    public BizExecutor getBizExcutor(String type) {
        return bizExecutorMap.get(type);
    }

    public interface BizExecutor{
        public static final String TYPE_SELECT_USER_OR_DEPT = "selectUserOrDept";
        public static final String TYPE_SELECT_FILE = "selectFile";
        /**
         * config中可选参数：position;
         * 必要参数：images,数组，图片ID
         * 不需要回调
         */
        public static final String TYPE_VIEW_IMAGE = "viewImage";
        /**
         * config中可选参数：ctrlCode，控制编码
         * 可选参数：selectedMap，已选项；
         * 可选参数：options，可选项；其中ctrlCode跟options必须有一个不能为空
         * 可选参数：isCacelable,默认false，是否可撤销，true会增加一个不使用的选项
         * callback需要返回值：selectedList<Entry<String,String>>,key为被选择项的code，value为被选择项的显示名称
         */
        public static final String TYPE_MULTI_SELECT = "multiSelect";

        /**
         * 具体业务处理
         * @param activity 从这个activity，可能需要启动另一个activity
         * @param requestCode 请求编码
         * @param config 具体业务数据
         */
        public void execute(Activity activity,int requestCode,Map<String,Object> config);
    }

}
