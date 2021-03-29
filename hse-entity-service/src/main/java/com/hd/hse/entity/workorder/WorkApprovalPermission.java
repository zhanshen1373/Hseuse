/**
 * File:    UdZyxkZyspqx.java
 * Author:  hd
 * Company:
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkZyspqx
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * @pdOid 5c7a6713-d45e-4e76-97b7-0d4d7e61789a
 */
@DBTable(tableName = "ud_zyxk_zyspqx")
public class WorkApprovalPermission extends
        com.hd.hse.common.entity.SuperEntity {
    /**
     * @pdOid 3259b246-1388-4b20-973a-d6fc03801402
     */
    private static final long serialVersionUID = -590384020357966220L;
    /**
     * 唯一标识
     *
     * @pdOid a95a5f9c-2793-4aa7-b158-86f53d109584
     */
    @DBField
    private String ud_zyxk_zyspqxid;
    /**
     * 作业类型
     *
     * @pdOid 32ae4f28-1af2-4241-9807-ab308902f4a5
     */
    @DBField
    private String wttype;
    /**
     * 作业级别
     *
     * @pdOid 84989816-9122-4ed3-8010-b497c09223f5
     */
    @DBField
    private String wtlevel;
    /**
     * 是否最终确认人
     *
     * @pdOid 82bf62fa-c325-4c5e-980d-6f4c22addc71
     */
    @DBField
    private Integer isend;
    /**
     * 作业级别1
     *
     * @pdOid b979b86c-d7d8-42bd-b23f-94c218f3ed33
     */
    @DBField
    private String description;
    /**
     * 部门编码
     *
     * @pdOid cedfae93-03f5-468c-809a-865813bbf2fb
     */
    @DBField
    private String deptnum;
    /**
     * 部门名称
     *
     * @pdOid ae76632c-de75-4cba-8dd1-d2bce1bca386
     */
    @DBField
    private String deptnum_desc;
    /**
     * 审批环节
     *
     * @pdOid d93e4d83-096c-407b-b973-ada94817d9fe
     */
    @DBField
    private String spfield;
    /**
     * 部门
     *
     * @pdOid 19224271-97a8-4358-98d4-051da7575a55
     */
    @DBField
    private String qxdept;
    /**
     * 角色
     *
     * @pdOid b49411ca-7b03-4888-9b8e-0012bda85ca9
     */
    @DBField
    private String qxrole;
    /**
     * 人员
     *
     * @pdOid 8388357a-2fd5-4006-81cf-e2ce3d588814
     */
    @DBField
    private String qxperson;
    /**
     * 作业地点
     *
     * @pdOid b94cab86-88f3-46a1-af09-01f09b1526af
     */
    @DBField
    private String zylocation;
    /**
     * 权限分类
     *
     * @pdOid 6a3ffb76-65bf-4a4b-900e-41d521782b82
     */
    @DBField
    private Integer qxtype;
    /**
     * Pda排序
     *
     * @pdOid 0636c470-b17a-4d02-96bc-50fb06d06261
     */
    @DBField
    private Integer pdapaixu;
    /**
     * 必须刷卡确认
     *
     * @pdOid 69fc6b88-4109-4478-b70c-63201357b7f6
     */
    @DBField
    private Integer ismust;
    /**
     * 是否允许多人刷卡
     *
     * @pdOid 54f3776f-dcff-4923-86a0-2c353f9cbed8
     */
    @DBField
    private Integer bpermulcard;
    /**
     * 修改日期
     *
     * @pdOid 0a20f06e-692d-43e9-801c-37626b3b4ecd
     */
    @DBField
    private String changedate;
    /**
     * 删除标识
     *
     * @pdOid 9d9241ed-034f-495f-9e74-939024f5e6a4
     */
    @DBField
    private Integer dr;
    /**
     * 是否承包商
     *
     * @pdOid 88ec9aa2-16d7-43b4-a9b5-ed2bc9c41e67
     */
    @DBField
    private Integer iscbs;
    /**
     * 是否手工输入
     *
     * @pdOid 7d443683-f4c3-4007-a5e8-2c63977436ed
     */
    @DBField
    private Integer isinput;
    /**
     * 权限字段名描述
     *
     * @pdOid 08f0cad3-dc95-4558-8c00-201c1bfdbdf4
     */
    @DBField
    private String spfield_desc;
    /**
     * 审批方案ID
     *
     * @pdOid 99e77376-6727-4595-bec2-12f435a973b0
     */
    @DBField
    private Integer ud_zyxk_spfaid;
    /**
     * 是否审核承包商资质
     *
     * @pdOid 8c3e2aaf-4b6f-461a-9488-eba94812bcbb
     */
    @DBField
    private Integer iscbszz;
    /**
     * 是否短信提醒
     *
     * @pdOid bc445726-2235-4547-9fa9-6c7de1a298df
     */
    @DBField
    private Integer bmsgremind;
    /**
     * a1
     *
     * @pdOid ee417378-7232-48ad-a4a7-b3de37d87e6a
     */
    @DBField
    private Integer iloadorder;
    /**
     * a2
     *
     * @pdOid 74ad51bd-9e5a-446a-9605-b44d9d317d1d
     */
    @DBField
    private String jhatype;
    /**
     * a3
     *
     * @pdOid ee8ea0e9-17a1-431d-9371-ef34abddaf7f
     */
    @DBField
    private Integer inputall;
    /**
     * a4
     *
     * @pdOid 11df0420-ec30-4add-af1f-b40b5c4e978e
     */
    @DBField
    private Integer confirmationlink;

    /**
     * jobnum:TODO(岗位).
     */
    @DBField
    private String jobnum;
    /**
     * job_desc:TODO(岗位描述).
     */
    @DBField
    private String job_desc;

    /**
     * 是否电子签名
     */
    @DBField
    private String isdzqm;
    /**
     * 相关作业类型 Created by liuyang on 2016年9月18日
     */
    @DBField
    private String relatedzy;
    /**
     * 是否启用
     */
    @DBField
    private int isqy;
    /**
     * 拍照配置
     */
    @DBField
    private int photoconfigur;
    /**
     * 是否是气体检测装置主任
     */
    @DBField
    private int isQtjcDirector;
    /**
     * 是否现场最终确认人
     */
    @DBField
    private int is_spot_final_person;
    /**
     * 是否远程审批
     */
    @DBField
    private int is_remote_approve;
    /**
     * index:TODO().
     * <p>
     * 控件索引
     *
     * @pdOid ce322ea6-b757-4dc4-8116-b02ea6cfbc98
     */
    private Integer index;
    /**
     * 环节点是否可用
     *
     * @pdOid b08f915a-eb75-41ab-84e1-7e7572e4d6b7
     */
    private int isexmaineable;
    /**
     * 刷卡人id
     *
     * @pdOid 642d8255-c45d-471b-b070-60750df19bc9
     */
    private String personid;
    /**
     * 刷卡人描述
     *
     * @pdOid 5fd6824a-61dd-4c41-9f80-5cf3a0f6fa27
     */
    private String persondesc;
    /**
     * 审批时间
     *
     * @pdOid 943f9318-2abb-45be-82ba-f39a5d204356
     */
    private String sptime;
    /**
     * 审批记录主键
     *
     * @pdOid 2ec2524b-f547-42e8-8918-6d33c8d69807
     */
    private String ud_zyxk_zyspryjlid;
    /**
     * 审批人员记录ID
     */
    private String strzyspryjlid;

    /**
     * 被修改的人员审批记录ID，用于合并审批时保存图片数据
     */
    private String modified_spryjlid;
    /**
     * 控件的默认值值描述
     *
     * @pdOid 8f4e3f67-3ac8-479b-bf30-4d4b73f7ba9a
     */
    private String defaultpersondesc;
    /**
     * 控件的默认值值编码
     *
     * @pdOid e4b938df-0137-4629-b904-728623181fb2
     */
    private String defaultpersonid;
    /**
     * 刷卡人部门
     *
     * @pdOid 4f95596f-f33f-4d3f-9ddc-58b9ee307565
     */
    private String departmentdesc;

    /**
     * currentgroupcolor:TODO(所在组的颜色标志位).
     */
    private Boolean currentgroupcolor;

    /**
     * groupheader:TODO(组头).
     */
    private Boolean groupheader;

    /**
     * status:TODO(作业票状态).
     */
    private String status;

    /**
     * ud_zyxk_zysqid:TODO(作业申请id).
     */
    private String ud_zyxk_zysqid;

    /**
     * strzysqid:TODO(作业申请id).
     */
    private String strzysqid;

    /**
     * strzyspqxid:TODO(作业审批权限id).
     */
    private String strzyspqxid;

    /**
     * strisend:TODO(是否最终刷卡人集合).
     */
    private String strisend;

    private String ud_onemeasure_person_ids;

    /**
     * 工种 Created by liuyang on 2016年11月11日
     */
    private String job;
    /**
     * 工种编号 Created by liuyang on 2016年11月11日
     */
    private String code;
    private String zcproject;
    //是否可以累加刷卡
    private Integer isCanLj;


    public Integer getIsCanLj() {
        return isCanLj;
    }

    public void setIsCanLj(Integer isCanLj) {
        this.isCanLj = isCanLj;
    }

    public String getIsdzqm() {
        return isdzqm;
    }

    public void setIsdzqm(String isdzqm) {
        this.isdzqm = isdzqm;
    }

    /**
     * index.
     *
     * @return the index
     * @pdOid 56043ae1-21a3-45b7-bfff-1db4eb7e3631
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * index.
     *
     * @param index the index to set
     * @pdOid e362fd79-774e-49d6-b26c-a52862627be5
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * departmentdesc.
     *
     * @return the departmentdesc
     * @pdOid 0dcd7137-a045-4e11-96da-ee5a6ac783a2
     */
    public String getDepartmentdesc() {
        return departmentdesc;
    }

    /**
     * departmentdesc.
     *
     * @param departmentdesc the departmentdesc to set
     * @pdOid 9c2cca96-f5c3-4a89-9faa-cf31789b5e53
     */
    public void setDepartmentdesc(String departmentdesc) {
        this.departmentdesc = departmentdesc;
    }

    /**
     * defaultpersondesc.
     *
     * @return the defaultpersondesc
     * @pdOid d385eb27-cc78-47aa-9311-f4df4f812d66
     */
    public String getDefaultpersondesc() {
        return defaultpersondesc;
    }

    /**
     * defaultpersondesc.
     *
     * @param defaultpersondesc the defaultpersondesc to set
     * @pdOid c8e59fa6-6ec0-4d59-8116-4cf344408fac
     */
    public void setDefaultpersondesc(String defaultpersondesc) {
        this.defaultpersondesc = defaultpersondesc;
    }

    /**
     * defaultpersonid.
     *
     * @return the defaultpersonid
     * @pdOid e3c6874d-45ed-43a5-9fb4-af47016e480c
     */
    public String getDefaultpersonid() {
        return defaultpersonid;
    }

    /**
     * defaultpersonid.
     *
     * @param defaultpersonid the defaultpersonid to set
     * @pdOid 8b53d13d-c360-417b-be65-9cdcaeb6c287
     */
    public void setDefaultpersonid(String defaultpersonid) {
        this.defaultpersonid = defaultpersonid;
    }

    /**
     * ud_zyxk_zyspryjlid.
     *
     * @return the ud_zyxk_zyspryjlid
     * @pdOid bc39020e-a77b-47d5-a5d2-08986bb903f2
     */
    public String getUd_zyxk_zyspryjlid() {
        return ud_zyxk_zyspryjlid;
    }

    /**
     * ud_zyxk_zyspryjlid.
     *
     * @param ud_zyxk_zyspryjlid the ud_zyxk_zyspryjlid to set
     * @pdOid c3b44b41-2f3e-41a5-a0b4-7ca61e12587c
     */
    public void setUd_zyxk_zyspryjlid(String ud_zyxk_zyspryjlid) {
        this.ud_zyxk_zyspryjlid = ud_zyxk_zyspryjlid;
    }

    /**
     * 设置 作业类型 该字段是：作业类型
     *
     * @param wttype
     * @pdOid 46f1e572-b763-43e0-84ec-b3b5a12dc2dd
     */
    public void setWttype(String wttype) {
        this.wttype = wttype;
    }

    /**
     * 获取 作业类型 该字段是：作业类型
     *
     * @pdOid 2d53f0ae-7f43-4d0b-a613-becb35005c81
     */
    public String getWttype() {
        return wttype;
    }

    /**
     * 设置 作业级别 该字段是：作业级别
     *
     * @param wtlevel
     * @pdOid 76b869ea-edc6-4f0d-ad99-b125dd645f25
     */
    public void setWtlevel(String wtlevel) {
        this.wtlevel = wtlevel;
    }

    /**
     * 获取 作业级别 该字段是：作业级别
     *
     * @pdOid 8abfe3c7-ad8e-4801-9bab-32bf8df449e8
     */
    public String getWtlevel() {
        return wtlevel;
    }

    /**
     * 设置 是否最终确认人 该字段是：是否最终确认人
     *
     * @param isend
     * @pdOid d24d996d-b384-40a5-b5bb-1e7b1b2ff9be
     */
    public void setIsend(Integer isend) {
        this.isend = isend;
    }

    /**
     * 获取 是否最终确认人 该字段是：是否最终确认人
     *
     * @pdOid 45b6a69b-b4d8-4c31-93a4-09bbafff336e
     */
    public Integer getIsend() {
        return isend;
    }

    /**
     * 设置 作业级别1 该字段是：作业级别1
     *
     * @param description
     * @pdOid 8eaf83bd-6ca4-4f86-83e7-4b2b69364b1d
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 作业级别1 该字段是：作业级别1
     *
     * @pdOid 50ec47cb-dded-4787-8845-513342ea9b69
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 部门编码 该字段是：部门编码
     *
     * @param deptnum
     * @pdOid d897c549-05ce-4785-b624-5592a9bf0e97
     */
    public void setDeptnum(String deptnum) {
        this.deptnum = deptnum;
    }

    /**
     * 获取 部门编码 该字段是：部门编码
     *
     * @pdOid 30088604-b44a-40e5-bf1d-28372c71a41c
     */
    public String getDeptnum() {
        return deptnum;
    }

    /**
     * @return the deptnum_desc
     * @pdOid ac0d7401-a0db-48f3-b44d-0cf73770f52d
     */
    public String getDeptnum_desc() {
        return deptnum_desc;
    }

    /**
     * @param deptnum_desc the deptnum_desc to set
     * @pdOid 62f3bf24-9d22-4889-b4b4-5382d46aac11
     */
    public void setDeptnum_desc(String deptnum_desc) {
        this.deptnum_desc = deptnum_desc;
    }

    /**
     * 设置 审批环节 该字段是：审批环节
     *
     * @param spfield
     * @pdOid 47c23abf-8565-4d5e-9f7a-297cef2ded49
     */
    public void setSpfield(String spfield) {
        this.spfield = spfield;
    }

    /**
     * 获取 审批环节 该字段是：审批环节
     *
     * @pdOid 70881b8d-75e9-4fb4-b54f-290be18e1da0
     */
    public String getSpfield() {
        return spfield;
    }

    /**
     * 设置 部门 该字段是：部门
     *
     * @param qxdept
     * @pdOid 9f90b4a7-e826-44cf-b1e0-cfb253b1ecd4
     */
    public void setQxdept(String qxdept) {
        this.qxdept = qxdept;
    }

    /**
     * 获取 部门 该字段是：部门
     *
     * @pdOid a9a1fbe0-7b94-4a21-96da-9aad28698213
     */
    public String getQxdept() {
        return qxdept;
    }

    /**
     * 设置 角色 该字段是：角色
     *
     * @param qxrole
     * @pdOid 8c2d9f77-263c-4d7a-96d0-83f611d9b09a
     */
    public void setQxrole(String qxrole) {
        this.qxrole = qxrole;
    }

    /**
     * 获取 角色 该字段是：角色
     *
     * @pdOid 70676f95-b769-4acb-a602-5e69338afcb6
     */
    public String getQxrole() {
        return qxrole;
    }

    /**
     * 设置 人员 该字段是：人员
     *
     * @param qxperson
     * @pdOid f43abbf4-7d99-41cb-8b53-4e43937c3447
     */
    public void setQxperson(String qxperson) {
        this.qxperson = qxperson;
    }

    /**
     * 获取 人员 该字段是：人员
     *
     * @pdOid bb93599a-0dd2-4bf9-b5f0-74c21e4fe4d0
     */
    public String getQxperson() {
        return qxperson;
    }

    /**
     * 设置 作业地点 该字段是：作业地点
     *
     * @param zylocation
     * @pdOid e9cb652f-a693-4990-902c-dd814aaf6044
     */
    public void setZylocation(String zylocation) {
        this.zylocation = zylocation;
    }

    /**
     * 获取 作业地点 该字段是：作业地点
     *
     * @pdOid 1103b43a-3353-4c94-b833-d569f60a1881
     */
    public String getZylocation() {
        return zylocation;
    }

    /**
     * 设置 权限分类 该字段是：权限分类
     *
     * @param qxtype
     * @pdOid 71d88ca1-411d-4824-9aa2-d3e7ada01cbd
     */
    public void setQxtype(Integer qxtype) {
        this.qxtype = qxtype;
    }

    /**
     * 获取 权限分类 该字段是：权限分类
     *
     * @pdOid 2d88c10e-e6d2-48d5-a687-988c6a73caab
     */
    public Integer getQxtype() {
        return qxtype;
    }

    /**
     * 设置 Pda排序 该字段是：Pda排序
     *
     * @param pdapaixu
     * @pdOid e177710b-f146-4eb7-9bb6-8bccbeea77c7
     */
    public void setPdapaixu(Integer pdapaixu) {
        this.pdapaixu = pdapaixu;
    }

    /**
     * 获取 Pda排序 该字段是：Pda排序
     *
     * @pdOid 8999a3be-3e5f-4dc8-8526-58ecf02f8dc9
     */
    public Integer getPdapaixu() {
        return pdapaixu;
    }

    /**
     * 设置 必须刷卡确认 该字段是：必须刷卡确认
     *
     * @param ismust
     * @pdOid 928ac214-109b-40ad-8fe3-74dc6b7b420b
     */
    public void setIsmust(Integer ismust) {
        this.ismust = ismust;
    }

    /**
     * 获取 必须刷卡确认 该字段是：必须刷卡确认
     *
     * @pdOid 3754c60d-6a8a-4f07-9741-6ac7258cc07c
     */
    public Integer getIsmust() {
        return ismust;
    }

    /**
     * 设置 是否允许多人刷卡 该字段是：是否允许多人刷卡
     *
     * @param bpermulcard
     * @pdOid ebe989e0-ee1f-47b5-9873-f17c45068cfc
     */
    public void setBpermulcard(Integer bpermulcard) {
        this.bpermulcard = bpermulcard;
    }

    /**
     * 获取 是否允许多人刷卡 该字段是：是否允许多人刷卡
     *
     * @pdOid 9125f188-b1b4-4337-bdc7-f2b4e5e66c12
     */
    public Integer getBpermulcard() {
        return bpermulcard;
    }

    /**
     * 设置 修改日期 该字段是：修改日期
     *
     * @param changedate
     * @pdOid c7e1f544-40d4-4a0b-b776-6c25e1090915
     */
    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    /**
     * 获取 修改日期 该字段是：修改日期
     *
     * @pdOid ecbee9f6-b09c-4358-a48b-9c54ecb752fd
     */
    public String getChangedate() {
        return changedate;
    }

    /**
     * 设置 删除标识 该字段是：删除标识
     *
     * @param dr
     * @pdOid 2271c977-5b7c-48fe-a1ec-578427a8012b
     */
    public void setDr(Integer dr) {
        this.dr = dr;
    }

    /**
     * 获取 删除标识 该字段是：删除标识
     *
     * @pdOid a20e8fcd-af28-40ba-a88d-d83d621f5d3d
     */
    public Integer getDr() {
        return dr;
    }

    /**
     * 设置 是否承包商 该字段是：是否承包商
     *
     * @param iscbs
     * @pdOid 079d3988-4e15-4a55-a5eb-0a2c743e050b
     */
    public void setIscbs(Integer iscbs) {
        this.iscbs = iscbs;
    }

    /**
     * 获取 是否承包商 该字段是：是否承包商
     *
     * @pdOid 74a1dd0d-44e5-4555-8e78-a337bd03f2ad
     */
    public Integer getIscbs() {
        return iscbs;
    }

    /**
     * 设置 是否手工输入 该字段是：是否手工输入
     *
     * @param isinput
     * @pdOid c7e933a0-bf39-4e79-865f-f5211f0d50cd
     */
    public void setIsinput(Integer isinput) {
        this.isinput = isinput;
    }

    /**
     * 获取 是否手工输入 该字段是：是否手工输入
     *
     * @pdOid 5e46858f-9844-4bc7-83a2-0a4797c1bae2
     */
    public Integer getIsinput() {
        return isinput;
    }

    /**
     * @return the ud_zyxk_zyspqxid
     * @pdOid 444f6045-2534-4938-ae2e-a2c1abef60b9
     */
    public String getUd_zyxk_zyspqxid() {
        return ud_zyxk_zyspqxid;
    }

    /**
     * @param ud_zyxk_zyspqxid the ud_zyxk_zyspqxid to set
     * @pdOid 05f36a0d-f8b2-4b63-a57d-e219b5496bb6
     */
    public void setUd_zyxk_zyspqxid(String ud_zyxk_zyspqxid) {
        this.ud_zyxk_zyspqxid = ud_zyxk_zyspqxid;
    }

    /**
     * @return the spfield_desc
     * @pdOid 66038246-c10e-4618-a97b-4b711dffde49
     */
    public String getSpfield_desc() {
        return spfield_desc;
    }

    /**
     * @param spfield_desc the spfield_desc to set
     * @pdOid 0abfb0b1-2f4f-4450-8b1a-41ae15289d9d
     */
    public void setSpfield_desc(String spfield_desc) {
        this.spfield_desc = spfield_desc;
    }

    /**
     * @return the ud_zyxk_spfaid
     * @pdOid cf4161a2-a4d7-4329-83b9-08aab6562bd8
     */
    public Integer getUd_zyxk_spfaid() {
        return ud_zyxk_spfaid;
    }

    /**
     * @param ud_zyxk_spfaid the ud_zyxk_spfaid to set
     * @pdOid 35ada8bf-6fb3-498d-a33f-a0f0ad041c0a
     */
    public void setUd_zyxk_spfaid(Integer ud_zyxk_spfaid) {
        this.ud_zyxk_spfaid = ud_zyxk_spfaid;
    }

    /**
     * 设置 是否审核承包商资质 该字段是：是否审核承包商资质
     *
     * @param iscbszz
     * @pdOid 8584b887-f865-405b-8d14-afa2f20c860a
     */
    public void setIscbszz(Integer iscbszz) {
        this.iscbszz = iscbszz;
    }

    /**
     * 获取 是否审核承包商资质 该字段是：是否审核承包商资质
     *
     * @pdOid be000274-75ca-4f4d-839e-ab1b5511210e
     */
    public Integer getIscbszz() {
        return iscbszz;
    }

    /**
     * 设置 是否短信提醒 该字段是：是否短信提醒
     *
     * @param bmsgremind
     * @pdOid 61cce80a-195e-4f2b-844a-bcd77d4486f6
     */
    public void setBmsgremind(Integer bmsgremind) {
        this.bmsgremind = bmsgremind;
    }

    /**
     * 获取 是否短信提醒 该字段是：是否短信提醒
     *
     * @pdOid b412c25c-6d23-4b34-a798-4dfdef27ee6f
     */
    public Integer getBmsgremind() {
        return bmsgremind;
    }

    /**
     * 设置 a1 该字段是：a1
     *
     * @param iloadorder
     * @pdOid bd63323c-7b43-4312-a285-4ded664c657f
     */
    public void setIloadorder(Integer iloadorder) {
        this.iloadorder = iloadorder;
    }

    /**
     * 获取 a1 该字段是：a1
     *
     * @pdOid 00f29c3b-ff85-4f30-91d5-4e54edbdb940
     */
    public Integer getIloadorder() {
        return iloadorder;
    }

    /**
     * 设置 a2 该字段是：a2
     *
     * @param jhatype
     * @pdOid 0afe22c2-5a0e-4d82-af17-ff79ac19be35
     */
    public void setJhatype(String jhatype) {
        this.jhatype = jhatype;
    }

    /**
     * 获取 a2 该字段是：a2
     *
     * @pdOid a1ccb921-b4b9-4708-be67-f3c398c431fc
     */
    public String getJhatype() {
        return jhatype;
    }

    /**
     * 设置 a3 该字段是：a3
     *
     * @param inputall
     * @pdOid 4ae9918b-2157-4580-93ef-bf70ccf19636
     */
    public void setInputall(Integer inputall) {
        this.inputall = inputall;
    }

    /**
     * 获取 a3 该字段是：a3
     *
     * @pdOid 35f0cb3c-1648-4fac-b055-3fc451ddfaf0
     */
    public Integer getInputall() {
        return inputall;
    }

    /**
     * 设置 a4 该字段是：a4
     *
     * @param confirmationlink
     * @pdOid cca921ce-c40e-48f3-8c8d-09a8a20ac13f
     */
    public void setConfirmationlink(Integer confirmationlink) {
        this.confirmationlink = confirmationlink;
    }

    /**
     * 获取 a4 该字段是：a4
     *
     * @pdOid 5072358f-e1d9-4392-8e08-9276d0676219
     */
    public Integer getConfirmationlink() {
        return confirmationlink;
    }

    /**
     * @pdOid 14904a3a-df4f-4978-8314-aaa6bb00302f
     */
    public int getIsexmaineable() {
        return isexmaineable;
    }

    /**
     * @param isexmaineable
     * @pdOid 09da48a9-8d5b-4b86-a4cd-e4f0f196917f
     */
    public void setIsexmaineable(int isexmaineable) {
        this.isexmaineable = isexmaineable;
    }

    /**
     * @pdOid f4be584d-db7f-43d5-a292-afbaf4d13c62
     */
    public String getPersonid() {
        return personid;
    }

    /**
     * @param personid
     * @pdOid d303b127-28e0-40a6-b72b-18476d59be25
     */
    public void setPersonid(String personid) {
        this.personid = personid;
    }

    /**
     * @pdOid b6016ee8-369e-4722-af3c-b18c6ba898c1
     */
    public String getPersondesc() {
        return persondesc;
    }

    /**
     * @param persondesc
     * @pdOid 5db42765-e68b-4e60-9641-035b9a2c6a1e
     */
    public void setPersondesc(String persondesc) {
        this.persondesc = persondesc;
    }

    /**
     * @pdOid ebfe3aef-48c5-42c1-ba44-aa54e8e4488b
     */
    public String getSptime() {
        return sptime;
    }

    /**
     * @param sptime
     * @pdOid f9768bec-e01e-43ad-885b-482ec86ab9bf
     */
    public void setSptime(String sptime) {
        this.sptime = sptime;
    }

    public Boolean getCurrentgroupcolor() {
        return currentgroupcolor;
    }

    public void setCurrentgroupcolor(Boolean currentgroupcolor) {
        this.currentgroupcolor = currentgroupcolor;
    }

    public Boolean getGroupheader() {
        return groupheader;
    }

    public void setGroupheader(Boolean groupheader) {
        this.groupheader = groupheader;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getJobnum:(岗位). <br/>
     * date: 2015年8月26日 <br/>
     *
     * @return
     * @author lxf
     */
    public String getJobnum() {
        return jobnum;
    }

    /**
     * setJobnum:(岗位). <br/>
     * date: 2015年8月26日 <br/>
     *
     * @param jobnum
     * @author lxf
     */
    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    /**
     * getJob_desc:(岗位描述). <br/>
     * date: 2015年8月26日 <br/>
     *
     * @return
     * @author lxf
     */
    public String getJob_desc() {
        return job_desc;
    }

    /**
     * setJob_desc:(岗位描述). <br/>
     * date: 2015年8月26日 <br/>
     *
     * @param job_desc
     * @author lxf
     */
    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    /**
     * getStrzysqid:(环节点关联的作业票ID). <br/>
     * date: 2015年9月6日 <br/>
     *
     * @return
     * @author lxf
     */
    public String getStrzysqid() {
        return strzysqid;
    }

    /**
     * setStrzysqid:(环节点关联的作业票ID). <br/>
     * date: 2015年9月6日 <br/>
     *
     * @param strzysqid
     * @author lxf
     */
    public void setStrzysqid(String strzysqid) {
        this.strzysqid = strzysqid;
    }

    /**
     * getStrzyspqxid:(环节点关联的审批权限id). <br/>
     * date: 2015年9月6日 <br/>
     *
     * @return
     * @author lxf
     */
    public String getStrzyspqxid() {
        return strzyspqxid;
    }

    /**
     * setStrzyspqxid:(环节点关联的审批权限id). <br/>
     * date: 2015年9月6日 <br/>
     *
     * @param strzyspqxid
     * @author lxf
     */
    public void setStrzyspqxid(String strzyspqxid) {
        this.strzyspqxid = strzyspqxid;
    }

    /**
     * getUd_zyxk_zysqid:(作业申请id). <br/>
     * date: 2015年9月6日 <br/>
     *
     * @return
     * @author lxf
     */
    public String getUd_zyxk_zysqid() {
        return ud_zyxk_zysqid;
    }

    /**
     * setUd_zyxk_zysqid:(作业申请id). <br/>
     * date: 2015年9月6日 <br/>
     *
     * @param ud_zyxk_zysqid
     * @author lxf
     */
    public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
        this.ud_zyxk_zysqid = ud_zyxk_zysqid;
    }

    /**
     * getStrisend:(是否最终刷卡人集合). <br/>
     * date: 2015年9月7日 <br/>
     *
     * @return
     * @author lxf
     */
    public String getStrisend() {
        return strisend;
    }

    /**
     * setStrisend:(是否最终刷卡人集合). <br/>
     * date: 2015年9月7日 <br/>
     *
     * @param strisend
     * @author lxf
     */
    public void setStrisend(String strisend) {
        this.strisend = strisend;
    }

    public String getStrzyspryjlid() {
        return strzyspryjlid;
    }

    public void setStrzyspryjlid(String strzyspryjlid) {
        this.strzyspryjlid = strzyspryjlid;
    }

    public String getModified_spryjlid() {
        return modified_spryjlid;
    }

    public void setModified_spryjlid(String modified_spryjlid) {
        this.modified_spryjlid = modified_spryjlid;
    }

    public String getUd_onemeasure_person_ids() {
        return ud_onemeasure_person_ids;
    }

    public void setUd_onemeasure_person_ids(String ud_onemeasure_person_ids) {
        this.ud_onemeasure_person_ids = ud_onemeasure_person_ids;
    }

    // lxf 新增作业审批权限编码字符串
    private String strzyspqxfiled;

    /**
     * getStrzyspqxfiled:(合并审批环节点编码，拼接). <br/>
     * date: 2015年11月26日 <br/>
     *
     * @return
     * @author lxf
     */
    public String getStrzyspqxfiled() {
        return strzyspqxfiled;
    }

    /**
     * setStrzyspqxfiled:(合并审批环节点编码，拼接). <br/>
     * date: 2015年11月26日 <br/>
     *
     * @param strzyspqxfiled
     * @author lxf
     */
    public void setStrzyspqxfiled(String strzyspqxfiled) {
        this.strzyspqxfiled = strzyspqxfiled;
    }

    public String getRelatedzy() {
        return relatedzy;
    }

    public void setRelatedzy(String relatedzy) {
        this.relatedzy = relatedzy;
    }

    public int getIsqy() {
        return isqy;
    }

    public void setIsqy(int isqy) {
        this.isqy = isqy;
    }

    public int getPhotoconfigur() {
        return photoconfigur;
    }

    public void setPhotoconfigur(int photoconfigur) {
        this.photoconfigur = photoconfigur;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getZcproject() {
        return zcproject;
    }

    public void setZcproject(String zcproject) {
        this.zcproject = zcproject;
    }

    public int getIsQtjcDirector() {
        return isQtjcDirector;
    }

    public void setIsQtjcDirector(int isQtjcDirector) {
        this.isQtjcDirector = isQtjcDirector;
    }


    public int getIs_remote_approve() {
        return is_remote_approve;
    }

    public void setIs_remote_approve(int is_remote_approve) {
        this.is_remote_approve = is_remote_approve;
    }

    public int getIs_spot_final_person() {
        return is_spot_final_person;
    }

    public void setIs_spot_final_person(int is_spot_final_person) {
        this.is_spot_final_person = is_spot_final_person;
    }

}