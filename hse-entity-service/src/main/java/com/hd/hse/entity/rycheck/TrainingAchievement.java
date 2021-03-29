package com.hd.hse.entity.rycheck;

import com.hd.hse.common.entity.SuperEntity;

import java.util.List;

/**
 * created by yangning on 2018/1/8 15:14.
 * 培训成绩
 */

public class TrainingAchievement extends SuperEntity{
    /**
     * FACTORYNAME : JH02
     * SCORE : 100
     * DATE : 2018-01-03
     * RESULT : 合格(厂)
     * SECONDEDU : [{"WORKSHOPNAME":"JH0201","SCORE":"100","RESULT":"合格(车间)"}]
     */

    private String FACTORYNAME;
    private String SCORE;
    private String DATE;
    private String RESULT;
    private List<TwoLevelAchievement> SECONDEDU;

    public String getFACTORYNAME() {
        return FACTORYNAME;
    }

    public void setFACTORYNAME(String FACTORYNAME) {
        this.FACTORYNAME = FACTORYNAME;
    }

    public String getSCORE() {
        return SCORE;
    }

    public void setSCORE(String SCORE) {
        this.SCORE = SCORE;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public List<TwoLevelAchievement> getSECONDEDU() {
        return SECONDEDU;
    }

    public void setSECONDEDU(List<TwoLevelAchievement> SECONDEDU) {
        this.SECONDEDU = SECONDEDU;
    }




}
