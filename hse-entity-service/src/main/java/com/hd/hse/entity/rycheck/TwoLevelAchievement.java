package com.hd.hse.entity.rycheck;

import com.hd.hse.common.entity.SuperEntity;

/**
 * created by yangning on 2018/1/8 15:19.
 */

public class TwoLevelAchievement extends SuperEntity {
    private String WORKSHOPNAME;
    private String SCORE;
    private String RESULT;
    private String DATE;

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getWORKSHOPNAME() {
        return WORKSHOPNAME;
    }

    public void setWORKSHOPNAME(String WORKSHOPNAME) {
        this.WORKSHOPNAME = WORKSHOPNAME;
    }

    public String getSCORE() {
        return SCORE;
    }

    public void setSCORE(String SCORE) {
        this.SCORE = SCORE;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }
}
