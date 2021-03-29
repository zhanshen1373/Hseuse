package com.hd.hse.entity.calendar;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * Created by dubojian on 2018/3/26.
 */

@DBTable(tableName = "calendar")
public class DutyTime extends SuperEntity {

    @DBField
    private String startdate;

    @DBField
    private String enddate;

    @DBField
    private Integer isweekday;


    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Integer getIsweekday() {
        return isweekday;
    }

    public void setIsweekday(Integer isweekday) {
        this.isweekday = isweekday;
    }
}
