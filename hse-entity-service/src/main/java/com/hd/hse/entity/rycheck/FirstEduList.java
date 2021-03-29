package com.hd.hse.entity.rycheck;

import com.hd.hse.common.entity.SuperEntity;

import java.util.List;

/**
 * created by yangning on 2018/2/5 10:33.
 */

public class FirstEduList extends SuperEntity {
    List<TrainingAchievement> FIRSTEDU;

    public List<TrainingAchievement> getFIRSTEDU() {
        return FIRSTEDU;
    }

    public void setFIRSTEDU(List<TrainingAchievement> FIRSTEDU) {
        this.FIRSTEDU = FIRSTEDU;
    }
}
