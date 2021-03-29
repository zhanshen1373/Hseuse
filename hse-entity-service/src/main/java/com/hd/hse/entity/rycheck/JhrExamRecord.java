package com.hd.hse.entity.rycheck;

/**
 * created by yangning on 2019/3/25 10:26.
 */
public class JhrExamRecord {
    /**
     * RESULT : 合格
     * SCORE : 81
     * JOBTYPE : 临电
     */

    private String RESULT;
    private String SCORE;
    private String JOBTYPE;

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getSCORE() {
        return SCORE;
    }

    public void setSCORE(String SCORE) {
        this.SCORE = SCORE;
    }

    public String getJOBTYPE() {
        return JOBTYPE;
    }

    public void setJOBTYPE(String JOBTYPE) {
        this.JOBTYPE = JOBTYPE;
    }
}
