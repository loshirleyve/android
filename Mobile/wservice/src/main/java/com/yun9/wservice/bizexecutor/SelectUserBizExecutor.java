package com.yun9.wservice.bizexecutor;

import android.content.Intent;

import com.yun9.jupiter.form.FormActivity;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.FormUtilFactory.BizExecutor;
import com.yun9.jupiter.form.cell.UserFormCell;
import com.yun9.jupiter.form.model.UserFormCellBean;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.view.CustomCallbackActivity;
import com.yun9.wservice.view.org.OrgCompositeActivity;
import com.yun9.wservice.view.org.OrgCompositeCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/9.
 */
public class SelectUserBizExecutor implements BizExecutor{

    @Override
    public void execute(CustomCallbackActivity activity, FormCell cell) {
        final UserFormCell formCell = (UserFormCell) cell;
        final UserFormCellBean cellBean = (UserFormCellBean) cell.getFormCellBean();
        activity.addActivityCallback(OrgCompositeCommand.REQUEST_CODE,
                new FormActivity.IActivityCallback() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        if (resultCode == OrgCompositeCommand.RESULT_CODE_OK) {
                            ArrayList<Org> onSelectOrgs =
                                    (ArrayList<Org>) data
                                            .getSerializableExtra(OrgCompositeCommand.PARAM_ORG);
                            ArrayList<User> onSelectUsers =
                                    (ArrayList<User>) data
                                            .getSerializableExtra(OrgCompositeCommand.PARAM_USER);
                            List<Map<String,String>> uodMaps = new ArrayList<Map<String, String>>();
                            Map<String,String> map;
                            for (int i = 0; i < onSelectOrgs.size(); i++) {
                                map = new HashMap<String, String>();
                                map.put(UserFormCell.PARAM_KEY_TYPE,OrgCompositeCommand.PARAM_ORG);
                                map.put(UserFormCell.PARAM_KEY_VALUE, onSelectOrgs.get(i).getId());
                                uodMaps.add(map);
                            }
                            for (int i = 0; i < onSelectUsers.size(); i++) {
                                map = new HashMap<String, String>();
                                map.put(UserFormCell.PARAM_KEY_TYPE,OrgCompositeCommand.PARAM_USER);
                                map.put(UserFormCell.PARAM_KEY_VALUE,onSelectUsers.get(i).getId());
                                uodMaps.add(map);
                            }
                            formCell.reload(uodMaps);
                        }
                    }
                });
        OrgCompositeCommand command = new OrgCompositeCommand();
        command.setEdit(true);
        command.setCompleteType(OrgCompositeCommand.COMPLETE_TYPE_CALLBACK);
        List<Map<String,String>> uodMaps = (List<Map<String, String>>) cell.getValue();
        Map<String,String> uodMap;
        for (int i = 0; i < uodMaps.size();i++) {
            uodMap = uodMaps.get(i);
            if (uodMap.get(UserFormCell.PARAM_KEY_TYPE).equals(OrgCompositeCommand.PARAM_ORG)){
                command.putSelectOrgs(uodMap.get(UserFormCell.PARAM_KEY_VALUE));
            }else if (uodMap.get(UserFormCell.PARAM_KEY_TYPE).equals(OrgCompositeCommand.PARAM_USER)){
                command.putSelectUser(uodMap.get(UserFormCell.PARAM_KEY_VALUE));
            }
        }
        OrgCompositeActivity.start(activity,command);
    }
}
