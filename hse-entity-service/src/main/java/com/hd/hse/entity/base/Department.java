/**
 * File:    UdSyDept.java
 * Author:  hd
 * Company:
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdSyDept
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.base;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid e3459251-6be5-4356-9267-2af9cffa6902 */

/**
 *
 * Created by liuyang on 2016年5月12日
 */
@DBTable(tableName = "ud_sy_dept")
public class Department extends com.hd.hse.common.entity.SuperEntity {
    /** @pdOid 0daa7e79-dd4b-4818-8fda-1a98c3695ee8 */
    private static final long serialVersionUID = 5187065377130849839L;
    /**
     * 部门
     *
     * @pdOid ca3d141d-5cfe-4b6d-8a9d-45034711446b
     */
    @DBField
    private String deptnum;
    /**
     * 描述
     *
     * @pdOid 7527fe3c-93bb-4b84-b414-05a397084e55
     */
    @DBField
    private String description;
    /**
     * a3
     *
     * @pdOid ec65d139-5317-4796-8785-bc9ee8c6c334
     */
    @DBField
    private String parent;
    /**
     * a4
     *
     * @pdOid 6eab8dbf-ea8c-49a6-9191-b70e3219d642
     */
    @DBField
    private String vreportdeptnum;
    /**
     * 是否有效
     *
     * @pdOid afdff03d-a8fa-461a-ab38-033e73c606c0
     */
    @DBField
    private Integer isuseful;
    /**
     * 删除标识
     *
     * @pdOid f40a0101-8bce-4379-8be4-3560504b62c9
     */
    @DBField
    private Integer dr;
    /**
     * 修改时间
     *
     * @pdOid 489ebfda-110c-412a-b14c-ac42d134772c
     */
    @DBField
    private String changedate;

    /**
     * 是否查询该部门（用于统计分析展示部门列表）
     *
     * Created by liuyang on 2016年5月12日
     */
    @DBField
    private int isquery;

    /**
     * 增加的一个过滤条件
     */
    @DBField
    private int isfilter;

    /**
     * 区分厂级还是车间级
     */
    @DBField
    private int layer;

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getIsfilter() {
        return isfilter;
    }

    public void setIsfilter(int isfilter) {
        this.isfilter = isfilter;
    }

    /**
     * 设置 部门 该字段是：部门
     *
     * @param deptnum
     * @pdOid a7d1964d-51d4-4ec7-8ba9-0e8ec427d902
     */
    public void setDeptnum(String deptnum) {
        this.deptnum = deptnum;
    }

    /**
     * 获取 部门 该字段是：部门
     *
     *
     * @pdOid ad2e90ea-60e1-479d-8005-49f149353cb5
     */
    public String getDeptnum() {
        return deptnum;
    }

    /**
     * 设置 描述 该字段是：描述
     *
     * @param description
     * @pdOid 02ce6cb6-32f8-4a7b-a2f1-b71a14fd7207
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 描述 该字段是：描述
     *
     *
     * @pdOid 0e5f8b13-4bab-4a32-9244-3c4466c7ff45
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 a3 该字段是：a3
     *
     * @param parent
     * @pdOid 1d9666d3-4abe-4f9f-904c-f915a1bdf360
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * 获取 a3 该字段是：a3
     *
     *
     * @pdOid 0377a796-b76c-4dbe-be70-33eaed6e4530
     */
    public String getParent() {
        return parent;
    }

    /**
     * 设置 a4 该字段是：a4
     *
     * @param vreportdeptnum
     * @pdOid 626f67ba-31df-44eb-b1d2-38ae3f0d4342
     */
    public void setVreportdeptnum(String vreportdeptnum) {
        this.vreportdeptnum = vreportdeptnum;
    }

    /**
     * 获取 a4 该字段是：a4
     *
     *
     * @pdOid 9bc46cfd-4a46-4a51-ac6b-0dcd7a271877
     */
    public String getVreportdeptnum() {
        return vreportdeptnum;
    }

    /**
     * 设置 是否有效 该字段是：是否有效
     *
     * @param isuseful
     * @pdOid 57aece45-7d98-4346-84c2-fc6c67d56967
     */
    public void setIsuseful(Integer isuseful) {
        this.isuseful = isuseful;
    }

    /**
     * 获取 是否有效 该字段是：是否有效
     *
     *
     * @pdOid 4bb25e29-d87f-46bc-88be-593f85e6ca22
     */
    public Integer getIsuseful() {
        return isuseful;
    }

    /**
     * 设置 删除标识 该字段是：删除标识
     *
     * @param dr
     * @pdOid cbb0450f-d994-4f1e-b159-fb6b54b8c214
     */
    public void setDr(Integer dr) {
        this.dr = dr;
    }

    /**
     * 获取 删除标识 该字段是：删除标识
     *
     *
     * @pdOid 1a4667cb-65b6-489e-be1d-230c7b85a68a
     */
    public Integer getDr() {
        return dr;
    }

    /**
     * 设置 修改时间 该字段是：修改时间
     *
     * @param changedate
     * @pdOid 806e84ea-6b30-4cda-af8f-0f7bf642614e
     */
    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    /**
     * 获取 修改时间 该字段是：修改时间
     *
     *
     * @pdOid 2903cb21-101d-438e-a0a6-7c2553fd30b7
     */
    public String getChangedate() {
        return changedate;
    }

    public int getIsquery() {
        return isquery;
    }

    public void setIsquery(int isquery) {
        this.isquery = isquery;
    }

}