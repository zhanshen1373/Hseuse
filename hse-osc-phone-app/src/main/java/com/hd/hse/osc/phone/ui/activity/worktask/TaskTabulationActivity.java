package com.hd.hse.osc.phone.ui.activity.worktask;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.module.phone.ui.module.activity.BaseListBusActivity;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.osc.business.workorder.WorkTaskSrv;
import com.hd.hse.osc.phone.ui.activity.workorder.WorkOrderActivity;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.WorkTaskDBSrv;

public class TaskTabulationActivity extends BaseListBusActivity {

    @Override
    public WorkTaskDBSrv getWorkTaskObject() {
        // TODO Auto-generated method stub
        return new WorkTaskSrv();
    }

    @Override
    public String getTitileName() {
        // TODO Auto-generated method stub
        return "现场核查";
    }

    @Override
    public String[] setActionBarItems() {
        return new String[]{IActionBar.IV_LMORE, IActionBar.IBTN_SEARCH, IActionBar.TV_TITLE, IActionBar.TV_MORE};
    }

    @Override
    public String[] getActionBarMenus() {
        QueryRelativeConfig config = new QueryRelativeConfig();
        RelationTableName tableName = new RelationTableName();
        tableName.setSys_type(IRelativeEncoding.HBZYP);
        if (config.isHadRelative(tableName)) {
            return new String[]{IActionBar.TV_JOIN, IActionBar.TV_POWER_UPDATE};
        } else {
            return new String[]{IActionBar.TV_POWER_UPDATE};
        }
    }

    @Override
    public String getNavCurrentKey() {
        // TODO Auto-generated method stub
        return "hse-osc-phone-app";
    }

    @Override
    public Class<?> getToActivityClass() {
        // TODO Auto-generated method stub
        return WorkOrderActivity.class;
    }

    @Override
    public String getFunctionCode() {
        // TODO Auto-generated method stub
        return IConfigEncoding.SP;
    }
}
