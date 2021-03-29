package com.hd.hse.entity.common;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * File: UdZyxkRyk.java Author: hd Company: Created: 2014-09-03 13:40:28
 * Purpose: 定义数据类 UdZyxkRyk NOTE: 该文件为自动生成，请勿手工改动！
 *
 * @pdOid c150515d-9b70-481f-9920-0431e2c777c9
 */
@DBTable(tableName = "ud_zyxk_ryk")
public class PersonCard extends com.hd.hse.common.entity.SuperEntity {
    /**
     * @pdOid d3b8a1f0-734d-4311-a28f-beec72107692
     */
    private static final long serialVersionUID = 8346674940803355549L;
    /**
     * 主键
     *
     * @pdOid aa2ae44c-00e7-44a5-a7f4-e917e425027c
     */
    @DBField(id = true)
    private String ud_zyxk_rykid;
    /**
     * 登陆账号
     *
     * @pdOid ced2cfd8-ae50-4fa6-95b6-00f2b35b8000
     */
    @DBField
    private String userid;
    /**
     * 人员编码
     *
     * @pdOid 9183f127-210c-47e9-8c32-b09712a0e343
     */
    @DBField(id = true)
    private String personid;
    /**
     * 姓名（包含承包商）
     *
     * @pdOid 5b8091a8-e6ed-4872-8991-33de009e8ae5
     */
    @DBField
    private String personid_desc;
    /**
     * 人员卡
     *
     * @pdOid 854eeffe-b0fd-41ef-8ee3-1d2e3745eb2e
     */
    @DBField
    private String pcardnum;
    /**
     * 部门编码
     *
     * @pdOid 0782539c-d35e-4532-a6a4-40c1109fdd7a
     */
    @DBField
    private String department;
    /**
     * 性别
     *
     * @pdOid 40ec9931-f381-41f5-a88e-2ecaf4f6abbc
     */
    @DBField
    private String sex;
    /**
     * 所属单位
     *
     * @pdOid e2fa89c7-2943-41e8-a7b2-332db2c41fd5
     */
    @DBField
    private String vdeptname;
    /**
     * 身份证号
     *
     * @pdOid 0e39f72c-19ca-48ae-a172-13c2906bd0b6
     */
    @DBField
    private String iccard;
    /**
     * 培训结果
     *
     * @pdOid 29f77e09-5ec2-4e21-a4ca-d53fc6d6fd74
     */
    @DBField
    private String trainresult;
    /**
     * 入厂证编号
     *
     * @pdOid b51682e9-be67-4292-a05e-527e4c31b037
     */
    @DBField
    private String innercard;
    /**
     * 入厂时间
     *
     * @pdOid 27d89d42-470f-4f4c-9000-6e023c553bb3
     */
    @DBField
    private String starttime;
    /**
     * 离厂时间
     *
     * @pdOid 99b78d37-5ade-4de5-b501-4d98ef79bd5b
     */
    @DBField
    private String endtime;
    /**
     * 从事工种
     *
     * @pdOid 1f4f3976-2ae2-4a07-8f9e-a1cc48bf72f1
     */
    @DBField
    private String worktype;
    /**
     * 密码
     *
     * @pdOid cf5e8579-1b8c-4700-afae-062031510b14
     */
    @DBField
    private String password;
    /**
     * 是否登陆
     *
     * @pdOid f6652fde-4f6e-442d-adab-acc8fed790d6
     */
    @DBField
    private Integer islogin;
    /**
     * 是否有效
     *
     * @pdOid 883d5d35-87a4-4a27-a26c-eae9bce36e26
     */
    @DBField
    private Integer iscan;
    /**
     * 是否承包商
     *
     * @pdOid 48d2cf94-79d5-446a-8b77-3591473c0eff
     */
    @DBField
    private Integer iscbs;
    /**
     * 下载权限
     *
     * @pdOid 35033553-bb60-48c0-b5a4-3dcfd3878431
     */
    @DBField
    private String czqx;
    /**
     * 时间戳
     *
     * @pdOid c8a7328f-3fe8-45dd-9daa-c45fbce4e249
     */
    @DBField
    private String changedate;
    /**
     * 标记
     *
     * @pdOid a2d8f1c4-5c5d-421a-8e6c-89b9acc444b0
     */
    @DBField
    private Integer tag;
    /**
     * 删除
     *
     * @pdOid ea5dc660-814f-4dfd-b564-ef3944aeaf14
     */
    @DBField
    private Integer dr;
    /**
     * 电子签名路径
     *
     * @pdOid e04f2bec-ea5f-40c9-934b-f93767b5a6e0
     */
    @DBField
    private String signpath;
    /**
     * 指纹存储路径
     *
     * @pdOid d55a0e27-a15f-43a0-a3c2-a4f160232b85
     */
    @DBField
    private String zwsavepath;
    /**
     * 部门描述
     *
     * @pdOid cbdb1047-32b3-4c83-8c9a-2e83a1ea2eed
     */
    @DBField
    private String department_desc;
    /**
     * 是否黑名单
     *
     * @pdOid 3cd2b645-3c43-4038-8066-3f2bc5d0fe5e
     */
    @DBField
    private Integer ishmd;

    public String getPrimaryphone() {
        return primaryphone;
    }

    public void setPrimaryphone(String primaryphone) {
        this.primaryphone = primaryphone;
    }

    /**
     * 手机号
     */
    @DBField
    private String primaryphone;
    /**
     * 培训分数
     *
     * @pdOid 97f2a08a-30c7-449c-8fc8-ebec48c47c30
     */
    @DBField
    private Integer pxmark;
    /**
     * 是否黑名单虚拟字段
     *
     * @pdOid c2f921ed-7f1b-4559-81e5-58ec8c8446dd
     */
    private String ishmdvirtual;

    /**
     * zyarea:TODO(属地部门-记录人员入场的用工单位).
     */
    @DBField
    private String zyarea;

    /**
     * uuid:TODO(UUID 推送消息ID).
     */
    @DBField
    private String uuid;
    /**
     * 年龄
     * Created by liuyang on 2016年10月14日
     */
    @DBField
    private String age;
    /**
     * 入网时间
     * Created by liuyang on 2016年10月14日
     */
    @DBField
    private String swtime;
    /**
     * 审查时间
     * Created by liuyang on 2016年10月14日
     */
    @DBField
    private String scdeadline;
    /**
     * 保险有效期
     * Created by liuyang on 2016年10月14日
     */
    @DBField
    private String bxdeadline;
    /**
     * 劳动合同有效期
     * Created by liuyang on 2016年10月14日
     */
    @DBField
    private String htqdtime;
    /**
     * 状态
     * Created by liuyang on 2016年10月14日
     */
    @DBField
    private String processstatus;
    /**
     * 承包商有效期
     * Created by liuyang on 2016年10月14日
     */
    @DBField
    private String yxtime;
    /**
     * 特殊资质有效期
     * Created by liuyang on 2016年10月14日
     */
    @DBField
    private String zzdeadline;
    /**
     * 允许进入的部门编码
     * Created by liuyang on 2016年10月18日
     */
    @DBField
    private String jrdept;
    /**
     * 允许进入的部门名称
     * Created by liuyang on 2016年10月18日
     */
    @DBField
    private String jrdept_desc;
    /**
     * 如需进入的门岗
     * Created by liuyang on 2016年10月18日
     */
    @DBField
    private String jrdoor;
    /**
     * 是否记住密码
     */
    private boolean isRememberPWD;
    /**
     * 是否自动登录
     * <p>
     * Created by wdf on 2016年4月14日
     *
     * @return
     */
    private boolean autologin;

    //记录的是移动大厅的
    private String encryptParams;


    public String getEncryptParams() {
        return encryptParams;
    }

    public void setEncryptParams(String encryptParams) {
        this.encryptParams = encryptParams;
    }

    @DBField
    private String JOBTABOO;//健康体检建议

    public boolean isAutologin() {
        return autologin;
    }

    public void setAutoLogin(boolean autologin) {
        this.autologin = autologin;
    }

    public boolean isRememberPWD() {
        return isRememberPWD;
    }

    public void setRememberPWD(boolean isRememberPWD) {
        this.isRememberPWD = isRememberPWD;
    }

    public String getZyarea() {
        return zyarea;
    }

    public void setZyarea(String zyarea) {
        this.zyarea = zyarea;
    }

    /**
     * @pdOid 6c14253a-0592-4f0c-b0a7-3744d4dee1fe
     */
    public String getUd_zyxk_rykid() {
        return ud_zyxk_rykid;
    }

    /**
     * @param ud_zyxk_rykid
     * @pdOid 275a5eb7-f32c-431d-bc14-26a94c6a43cc
     */
    public void setUd_zyxk_rykid(String ud_zyxk_rykid) {
        this.ud_zyxk_rykid = ud_zyxk_rykid;
    }

    /**
     * @pdOid 48b0d5b7-3cdb-4e83-a61c-6a23458d48b6
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     * @pdOid b676d2c0-9b7c-4c24-8259-e0d4c50d651f
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @pdOid ef799ada-c34a-4a31-9b94-803c6b74818e
     */
    public String getPersonid() {
        return personid;
    }

    /**
     * @param personid
     * @pdOid f586d3b9-3510-47aa-acdc-4b010579e27e
     */
    public void setPersonid(String personid) {
        this.personid = personid;
    }

    /**
     * @pdOid adda85c4-9060-4348-b45f-8a3693f3c20f
     */
    public String getPersonid_desc() {
        return personid_desc;
    }

    /**
     * @param personid_desc
     * @pdOid ef1ffbf2-4df2-4f6e-9f6e-828642f2d857
     */
    public void setPersonid_desc(String personid_desc) {
        this.personid_desc = personid_desc;
    }

    /**
     * @pdOid 72700aa3-955d-49e4-a6d1-5b40a5699bca
     */
    public String getPcardnum() {
        return pcardnum;
    }

    /**
     * @param pcardnum
     * @pdOid 0d914925-4d9f-4105-bc83-55efa8823bd6
     */
    public void setPcardnum(String pcardnum) {
        this.pcardnum = pcardnum;
    }

    /**
     * @pdOid a730797c-0e2e-425a-bbf2-10659e4045a3
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department
     * @pdOid ae524704-7a91-4a1e-b219-2b174e9a4932
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @pdOid 2a3b4fb4-0c74-402c-8172-6924f434e2dd
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     * @pdOid 95f84a4c-60d0-405d-9752-bb7adf4bda69
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @pdOid d689fe22-bc7b-4288-825e-64cd24add2c9
     */
    public String getVdeptname() {
        return vdeptname;
    }

    /**
     * @param vdeptname
     * @pdOid 9a1fa060-871d-4ebe-b0a6-9e83692c55b9
     */
    public void setVdeptname(String vdeptname) {
        this.vdeptname = vdeptname;
    }

    /**
     * @pdOid d7d8a926-a06a-4f17-94e2-ef652c2daa47
     */
    public String getIccard() {
        return iccard;
    }

    /**
     * @param iccard
     * @pdOid dc8f587b-f03e-4ea1-b817-5a3e5861e1de
     */
    public void setIccard(String iccard) {
        this.iccard = iccard;
    }

    /**
     * @pdOid 0b238cf8-be33-462a-85fd-c24d3b8174a6
     */
    public String getTrainresult() {
        return trainresult;
    }

    /**
     * @param trainresult
     * @pdOid 2d177b4b-1a26-4385-a263-1ace2ca2d477
     */
    public void setTrainresult(String trainresult) {
        this.trainresult = trainresult;
    }

    /**
     * @pdOid c9918644-a089-4cc4-8030-0fe1ed81e5bc
     */
    public String getInnercard() {
        return innercard;
    }

    /**
     * @param innercard
     * @pdOid 95deba57-4c66-4c05-8a31-93df71f82055
     */
    public void setInnercard(String innercard) {
        this.innercard = innercard;
    }

    /**
     * @pdOid afad7c25-1d99-4b53-a487-e526b6de6e6f
     */
    public String getStarttime() {
        return starttime;
    }

    /**
     * @param starttime
     * @pdOid b35bd72c-a282-4d57-836c-f3dc5c54a10d
     */
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    /**
     * @pdOid ee2a144c-2528-453b-b04b-9eba48ae6174
     */
    public String getEndtime() {
        return endtime;
    }

    /**
     * @param endtime
     * @pdOid 5d2158e9-a333-47c0-b9ca-d2705b60e113
     */
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    /**
     * @pdOid 10d912d8-6531-40a9-9910-8da9995cefc0
     */
    public String getWorktype() {
        return worktype;
    }

    /**
     * @param worktype
     * @pdOid 8a722d96-27c2-40e2-ab55-87dd36f3fa54
     */
    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    /**
     * @pdOid 78905250-225b-4ba3-bd66-11bace9c55de
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     * @pdOid bc600342-b88b-4d45-8e67-41b160e8cb99
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @pdOid efb85abd-2cfb-4186-aecf-bb37d68adb41
     */
    public Integer getIslogin() {
        return islogin;
    }

    /**
     * @param islogin
     * @pdOid 77355b8d-48bf-4d24-9a2a-385497217adc
     */
    public void setIslogin(Integer islogin) {
        this.islogin = islogin;
    }

    /**
     * @pdOid 2750b451-07d6-4711-abc8-bd43243940c3
     */
    public Integer getIscan() {
        return iscan;
    }

    /**
     * @param iscan
     * @pdOid 8ffdf97d-0ff7-4f1c-b2e2-dbf643ec1762
     */
    public void setIscan(Integer iscan) {
        this.iscan = iscan;
    }

    /**
     * @pdOid ceab471c-b091-4191-9438-46f6368b73df
     */
    public Integer getIscbs() {
        return iscbs;
    }

    /**
     * @param iscbs
     * @pdOid 65396cb6-7418-4f3a-90e9-5976b05313cf
     */
    public void setIscbs(Integer iscbs) {
        this.iscbs = iscbs;
    }

    /**
     * @pdOid bfe28e47-ea9d-41be-a7ea-1ab8aa835125
     */
    public String getCzqx() {
        return czqx;
    }

    /**
     * @param czqx
     * @pdOid 0370f0ec-967b-486d-9972-60cba1644d9c
     */
    public void setCzqx(String czqx) {
        this.czqx = czqx;
    }

    /**
     * @pdOid b6660397-249e-4674-94e2-90d23c3d21f5
     */
    public String getChangedate() {
        return changedate;
    }

    /**
     * @param changedate
     * @pdOid a18ab0e7-91b5-4ed1-8a15-dc7721a807d0
     */
    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    /**
     * @pdOid f12f32bf-5e4d-4224-92c7-e63dc19364fa
     */
    public Integer getTag() {
        return tag;
    }

    /**
     * @param tag
     * @pdOid 8f84ad18-a418-4535-bf45-16c6ca4048c3
     */
    public void setTag(Integer tag) {
        this.tag = tag;
    }

    /**
     * @pdOid d87e680a-8d31-4e5b-8f4f-13c016a6ee9a
     */
    public Integer getDr() {
        return dr;
    }

    /**
     * @param dr
     * @pdOid 56b94884-1b78-4450-b40c-cf04bb37df05
     */
    public void setDr(Integer dr) {
        this.dr = dr;
    }

    /**
     * @pdOid 9e17648f-cbff-4a88-8369-13aaa7baa779
     */
    public String getSignpath() {
        return signpath;
    }

    /**
     * @param signpath
     * @pdOid afe9f484-b460-4908-9cd8-b589e82ab24a
     */
    public void setSignpath(String signpath) {
        this.signpath = signpath;
    }

    /**
     * @pdOid 875c18dc-e9ea-4a20-b186-8e685ed5290c
     */
    public String getZwsavepath() {
        return zwsavepath;
    }

    /**
     * @param zwsavepath
     * @pdOid 0949f60a-dd2f-451c-99f8-db891f203d0f
     */
    public void setZwsavepath(String zwsavepath) {
        this.zwsavepath = zwsavepath;
    }

    /**
     * TODO 人员特种作业证
     *
     * @pdOid 12e063b0-5356-42c9-a6a1-63c1cb7e3f75
     * @see com.hd.hse.common.entity.SuperEntity#getChildClasses() lxflxflxf注释
     */
    @Override
    public String[] getChildClasses() {
        // TODO Auto-generated method stub
        // return new String[]{ContractorPersonCert.class.getName()};
        return null;
    }

    /**
     * @pdOid de5a6cf1-96a2-4505-8248-8f23deed54f7
     */
    public String getDepartment_desc() {
        return department_desc;
    }

    /**
     * @param department_desc
     * @pdOid 0889349a-c94f-4346-9318-7632d41db288
     */
    public void setDepartment_desc(String department_desc) {
        this.department_desc = department_desc;
    }

    /**
     * @pdOid c5c08d0a-2872-4191-877c-e3b81f256ea6
     */
    public Integer getIshmd() {
        return ishmd;
    }

    /**
     * @param ishmd
     * @pdOid 8e81e65d-4faa-4996-b09a-b6cd0cd04503
     */
    public void setIshmd(Integer ishmd) {
        this.ishmd = ishmd;
    }

    /**
     * @pdOid 6e902a02-217e-423a-a0d8-9fbfe7683b97
     */
    public String getIshmdVirtual() {
        return ishmdvirtual;
    }

    /**
     * @param ishmdvirtual
     * @pdOid 01bb6bb4-ff7f-4b52-afab-bbe1a0833e01
     */
    public void setIshmdVirtual(String ishmdvirtual) {
        this.ishmdvirtual = ishmdvirtual;
    }

    /**
     * @pdOid fac2c891-465e-4366-bd84-440c303f1d8c
     */
    public Integer getPxmark() {
        return pxmark;
    }

    /**
     * @param pxmark
     * @pdOid 6aa03955-fffb-44c3-b14e-00bd2c957aaf
     */
    public void setPxmark(Integer pxmark) {
        this.pxmark = pxmark;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSwtime() {
        return swtime;
    }

    public void setSwtime(String swtime) {
        this.swtime = swtime;
    }

    public String getScdeadline() {
        return scdeadline;
    }

    public void setScdeadline(String scdeadline) {
        this.scdeadline = scdeadline;
    }

    public String getBxdeadline() {
        return bxdeadline;
    }

    public void setBxdeadline(String bxdeadline) {
        this.bxdeadline = bxdeadline;
    }

    public String getHtqdtime() {
        return htqdtime;
    }

    public void setHtqdtime(String htqdtime) {
        this.htqdtime = htqdtime;
    }

    public String getProcessstatus() {
        return processstatus;
    }

    public void setProcessstatus(String processstatus) {
        this.processstatus = processstatus;
    }

    public String getYxtime() {
        return yxtime;
    }

    public void setYxtime(String yxtime) {
        this.yxtime = yxtime;
    }

    public String getZzdeadline() {
        return zzdeadline;
    }

    public void setZzdeadline(String zzdeadline) {
        this.zzdeadline = zzdeadline;
    }

    public String getJrdept() {
        return jrdept;
    }

    public void setJrdept(String jrdept) {
        this.jrdept = jrdept;
    }

    public String getJrdept_desc() {
        return jrdept_desc;
    }

    public void setJrdept_desc(String jrdept_desc) {
        this.jrdept_desc = jrdept_desc;
    }

    public String getJrdoor() {
        return jrdoor;
    }

    public void setJrdoor(String jrdoor) {
        this.jrdoor = jrdoor;
    }

    public String getJOBTABOO() {
        return JOBTABOO;
    }

    public void setJOBTABOO(String jOBTABOO) {
        JOBTABOO = jOBTABOO;
    }


}