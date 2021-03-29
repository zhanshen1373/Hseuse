package com.hd.hse.common.module.phone.ui.activity;

import com.hd.hse.entity.workorder.CarXkz;

public class CarXkzActivity extends WorkOrderDetailActivity {

    @Override
    protected void UpdateTitleAUrl() {
        super.UpdateTitleAUrl();
        CarXkz carXkz = null;
        if (intent.getSerializableExtra(CAR_KXZ) != null) {
            carXkz = (CarXkz) intent.getSerializableExtra(CAR_KXZ);
        }
        if (carXkz != null) {
            String ud_zyxk_car_xkzid = carXkz.getUd_zyxk_car_xkzid();
            url = carXkz.getUrl() + "&carnumid=" + ud_zyxk_car_xkzid;
        }

    }
}
