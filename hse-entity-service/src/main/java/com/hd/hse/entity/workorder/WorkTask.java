/**
 * File:    UdZyxkWorktask.java
 * Author:  hd
 * Company:
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkWorktask
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * @pdOid 05cb26d4-c6ba-43e0-b82a-2990cb9d0f70
 */
@DBTable(tableName = "ud_zyxk_worktask")
public class WorkTask extends com.hd.hse.common.entity.SuperEntity {
    /**
     * @pdOid a0fde2e6-2053-4c36-9fce-b67e7720772c
     */
    private static final long serialVersionUID = -7292791600894372986L;
    /**
     * 作业名称
     *
     * @pdOid 3e961cbd-baf5-434e-a98e-b473d028ac7b
     */
    @DBField
    private String zyname;
    /**
     * 作业区域
     *
     * @pdOid 32dcff6e-eede-4689-87b0-869b425e6860
     */
    @DBField
    private String zylocation;
    /**
     * 作业区域描述
     *
     * @pdOid 38b6abc5-f887-468c-b2aa-1a602c344f85
     */
    @DBField
    private String zylocation_desc;
    /**
     * 作业位置
     *
     * @pdOid 2ab344cf-3637-41c2-adf3-76a419512ffc
     */
    @DBField
    private String zysite;
    /**
     * 作业内容
     *
     * @pdOid da7655b5-18b2-4e21-9264-7b9aeead60bd
     */
    @DBField
    private String zycontent;
    /**
     * 属地部门描述
     *
     * @pdOid 67494658-3e62-4d37-9a6b-d435c4218b1c
     */
    @DBField
    private String zyarea_desc;
    /**
     * 属地部门
     *
     * @pdOid e25ef764-c7b4-47bd-aa20-58eea97fed9f
     */
    @DBField
    private String zyarea;
    /**
     * 作业开始时间
     *
     * @pdOid 94151c30-e61e-461e-87e5-eb6a160bac84
     */
    @DBField
    private String zystarttime;
    /**
     * 作业结束时间
     *
     * @pdOid 0731e9d2-5237-47bc-b62b-665610c0b5c1
     */
    @DBField
    private String zyendtime;
    /**
     * 作业单位描述
     *
     * @pdOid 4385cf71-bf2b-4f50-8ce9-ee599fd1d897
     */
    @DBField
    private String zydept_desc;
    /**
     * 作业单位
     *
     * @pdOid 49bd9b06-6be4-45d1-9527-7ca41577259a
     */
    @DBField
    private String zydept;
    /**
     * 属地单位
     *
     * @pdOid 5b2347dd-289a-4075-8ffb-293a0d60539f
     */
    @DBField
    private String sddept;
    /**
     * 属地单位描述
     *
     * @pdOid 17e71611-a876-4bd3-b5fe-d583f3ae7583
     */
    @DBField
    private String sddept_desc;
    /**
     * 主键
     *
     * @pdOid c3b313a3-52ac-4dd1-9b00-4d7244da73b7
     */
    @DBField
    private String ud_zyxk_worktaskid;
    /**
     * 作业类别 Created by liuyang on 2016年10月28日
     */
    @DBField
    private String zyptype;
    /**
     * 作业类别 Created by liuyang on 2016年10月28日
     */
    @DBField
    private String zyptypedesc;

    //是和ud_zyxk_zysq联合查询的一个字段
    private int isxfdjh;
    //作业票按照属地单位过滤的一个字段
    private String gl;


    public String getGl() {
        return gl;
    }

    public void setGl(String gl) {
        this.gl = gl;
    }

    public int getIsxfdjh() {
        return isxfdjh;
    }

    public void setIsxfdjh(int isxfdjh) {
        this.isxfdjh = isxfdjh;
    }

    /**
     * 设置 作业名称 该字段是：作业名称
     *
     * @param zyname
     * @pdOid b31418f6-825c-4447-8829-270f1de28659
     */
    public void setZyname(String zyname) {
        this.zyname = zyname;
    }

    /**
     * 获取 作业名称 该字段是：作业名称
     *
     * @pdOid b4ef0577-b6e5-4266-b536-19a16d0b003f
     */
    public String getZyname() {
        return zyname;
    }

    /**
     * 设置 作业区域 该字段是：作业区域
     *
     * @param zylocation
     * @pdOid f6f6b9f5-8da5-4cd3-918d-88dea63d067a
     */
    public void setZylocation(String zylocation) {
        this.zylocation = zylocation;
    }

    /**
     * 获取 作业区域 该字段是：作业区域
     *
     * @pdOid 14bd7421-2410-4483-88a8-c55466fa0e23
     */
    public String getZylocation() {
        return zylocation;
    }

    /**
     * 设置 作业位置 该字段是：作业位置
     *
     * @param zysite
     * @pdOid c54b8cdf-df6f-4c01-9b10-93aef1745280
     */
    public void setZysite(String zysite) {
        this.zysite = zysite;
    }

    /**
     * 获取 作业位置 该字段是：作业位置
     *
     * @pdOid c0e078c8-ec8b-403c-9856-83ab15b778e9
     */
    public String getZysite() {
        return zysite;
    }

    /**
     * 设置 作业内容 该字段是：作业内容
     *
     * @param zycontent
     * @pdOid b08d469e-11ee-46d4-a1e5-8e2bd97081c7
     */
    public void setZycontent(String zycontent) {
        this.zycontent = zycontent;
    }

    /**
     * 获取 作业内容 该字段是：作业内容
     *
     * @pdOid 6297806a-e1ef-40f5-8705-8640ef46a01a
     */
    public String getZycontent() {
        return zycontent;
    }

    /**
     * 设置 属地部门 该字段是：属地部门
     *
     * @param zyarea
     * @pdOid bfb3c229-9e34-466c-911c-53cc47ae9834
     */
    public void setZyarea(String zyarea) {
        this.zyarea = zyarea;
    }

    /**
     * 获取 属地部门 该字段是：属地部门
     *
     * @pdOid 4b5ed1dd-0bf2-4ada-a125-766bd26f4340
     */
    public String getZyarea() {
        return zyarea;
    }

    /**
     * 设置 作业开始时间 该字段是：作业开始时间
     *
     * @param zystarttime
     * @pdOid 5d4ba0b2-86d8-4389-8244-241cc5d1f60c
     */
    public void setZystarttime(String zystarttime) {
        this.zystarttime = zystarttime;
    }

    /**
     * 获取 作业开始时间 该字段是：作业开始时间
     *
     * @pdOid aad56fe2-3431-449c-a41e-8cf1b03d9ae3
     */
    public String getZystarttime() {
        return zystarttime;
    }

    /**
     * 设置 作业结束时间 该字段是：作业结束时间
     *
     * @param zyendtime
     * @pdOid f14224a7-ac73-43e2-b39e-e5b99dbc330c
     */
    public void setZyendtime(String zyendtime) {
        this.zyendtime = zyendtime;
    }

    /**
     * 获取 作业结束时间 该字段是：作业结束时间
     *
     * @pdOid 13321107-9e7f-482e-bdca-3f841ee39acb
     */
    public String getZyendtime() {
        return zyendtime;
    }

    /**
     * 设置 作业单位 该字段是：作业单位
     *
     * @param zydept
     * @pdOid d204b486-f29d-4f2a-91c8-2a21e91a9ff9
     */
    public void setZydept(String zydept) {
        this.zydept = zydept;
    }

    /**
     * 获取 作业单位 该字段是：作业单位
     *
     * @pdOid 2662578e-5c90-4c5d-9438-bfe06dd39021
     */
    public String getZydept() {
        return zydept;
    }

    /**
     * 设置 属地单位 该字段是：属地单位
     *
     * @param sddept
     * @pdOid f91c4240-c783-4f51-9d24-e1a5b110ba20
     */
    public void setSddept(String sddept) {
        this.sddept = sddept;
    }

    /**
     * 获取 属地单位 该字段是：属地单位
     *
     * @pdOid 47670880-c2db-49b6-af7a-0b04fb4da390
     */
    public String getSddept() {
        return sddept;
    }

    /**
     * @pdOid e1c2ab44-9569-4191-a515-e381fc8d14af
     */
    public String getZylocation_desc() {
        return zylocation_desc;
    }

    /**
     * @param zylocation_desc
     * @pdOid b3114afc-fd81-4da8-99a5-19f95b3837c7
     */
    public void setZylocation_desc(String zylocation_desc) {
        this.zylocation_desc = zylocation_desc;
    }

    /**
     * @pdOid 955f3b86-3972-4c57-ab8b-050bfc2cac80
     */
    public String getZyarea_desc() {
        return zyarea_desc;
    }

    /**
     * @param zyarea_desc
     * @pdOid d46afb6d-8800-4cb4-ae77-92eb95b3df73
     */
    public void setZyarea_desc(String zyarea_desc) {
        this.zyarea_desc = zyarea_desc;
    }

    /**
     * @pdOid 306b9974-dbeb-4ba5-a72d-47633455f49f
     */
    public String getZydept_desc() {
        return zydept_desc;
    }

    /**
     * @param zydept_desc
     * @pdOid 668ed005-ecae-4038-ab27-dd1ac3975b1f
     */
    public void setZydept_desc(String zydept_desc) {
        this.zydept_desc = zydept_desc;
    }

    /**
     * @pdOid 1bc57985-fe77-496c-a788-467d70040fc7
     */
    public String getSddept_desc() {
        return sddept_desc;
    }

    /**
     * @param sddept_desc
     * @pdOid ae20f2ef-1f12-4234-a1c7-548ce2f516cc
     */
    public void setSddept_desc(String sddept_desc) {
        this.sddept_desc = sddept_desc;
    }

    /**
     * @pdOid 4ff0df57-2b17-4e3d-88c7-c1fdb20b115d
     */
    public String getUd_zyxk_worktaskid() {
        return ud_zyxk_worktaskid;
    }

    /**
     * @param ud_zyxk_worktaskid
     * @pdOid 82f3338e-4789-4385-9bf5-1c554432dca4
     */
    public void setUd_zyxk_worktaskid(String ud_zyxk_worktaskid) {
        this.ud_zyxk_worktaskid = ud_zyxk_worktaskid;
    }

    /**
     * @pdOid 10188b1f-f2e6-4b0e-834b-de56bc5bc3f0
     */
    @Override
    public String[] getChildClasses() {
        return new String[]{WorkOrder.class.getName()};
    }

    public String getZyptype() {
        return zyptype;
    }

    public void setZyptype(String zyptype) {
        this.zyptype = zyptype;
    }

    public String getZyptypedesc() {
        return zyptypedesc;
    }

    public void setZyptypedesc(String zyptypedesc) {
        this.zyptypedesc = zyptypedesc;
    }

}