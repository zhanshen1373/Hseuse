/**
 * File:    UdZyxkRyk.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-25 11:58:43
 * Purpose: 定义数据类 UdZyxkRyk
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 3e8f898d-307a-4290-a3fe-1d85b9f4a6da */
@DBTable(tableName = "ud_zyxk_ryk")
public class PersonCardContractorPerson extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid b2ae7cbc-98ad-4d09-a210-5c9455cf74c6 */
   private static final long serialVersionUID = -3600096837632525058L;
   /** 主键
    * 
    * @pdOid 39f23e62-90a7-45ee-9b36-b9b22c5b165c */
   @DBField(id=true)
   private String ud_zyxk_rykid;
   /** 登陆账号
    * 
    * @pdOid 77028e5e-2720-4e40-bd53-9cbba2da30b8 */
   @DBField
   private String userid;
   /** 人员编码
    * 
    * @pdOid ec780bc0-cad8-4847-ab7f-b120f94da6a8 */
   @DBField(id=true)
   private String personid;
   /** 姓名（包含承包商）
    * 
    * @pdOid e0ad6598-05d1-4461-9fe3-9b3f68b1c100 */
   @DBField
   private String personid_desc;
   /** 人员卡
    * 
    * @pdOid 9bf5e265-ff43-4aed-816a-3d04ced06e78 */
   @DBField
   private String pcardnum;
   /** 部门编码
    * 
    * @pdOid 5701a174-45d4-4588-9cdd-ab2f3fe49c59 */
   @DBField
   private String department;
   /** 性别
    * 
    * @pdOid 1383706f-9722-4dbe-8f71-7cce2278082c */
   @DBField
   private String sex;
   /** 所属单位
    * 
    * @pdOid bead36b8-13ca-4824-bd88-34d5e1c2c809 */
   @DBField
   private String vdeptname;
   /** 身份证号
    * 
    * @pdOid 45e8be12-b77f-4722-a78c-ab504c261f7d */
   @DBField
   private String iccard;
   /** 培训结果
    * 
    * @pdOid 5907070f-4e39-463b-bb5a-d34f341e8e2f */
   @DBField
   private String trainresult;
   /** 入厂证编号
    * 
    * @pdOid 0afddc52-aa83-4b2f-b95e-ddb20a733ebf */
   @DBField
   private String innercard;
   /** 入厂时间
    * 
    * @pdOid 5ce01e86-1a1b-4f29-9e83-fb84e6b99a80 */
   @DBField
   private String starttime;
   /** 离厂时间
    * 
    * @pdOid 3ceedb41-f053-4eea-a9ab-e1e6911dc992 */
   @DBField
   private String endtime;
   /** 从事工种
    * 
    * @pdOid 1c53ebf1-ddd3-42c2-b55c-93bf145b0fb5 */
   @DBField
   private String worktype;
   /** 密码
    * 
    * @pdOid 67c952be-4765-4735-b840-ffc88e123942 */
   @DBField
   private String password;
   /** 是否登陆
    * 
    * @pdOid bbefba77-e2df-4730-9501-277290f820d6 */
   @DBField
   private Integer islogin;
   /** 是否有效
    * 
    * @pdOid 8d153439-0fa1-4146-99ce-04879e672796 */
   @DBField
   private Integer iscan;
   /** 是否承包商
    * 
    * @pdOid e643c827-bccf-4705-95c0-0fa918d19562 */
   @DBField
   private Integer iscbs;
   /** 下载权限
    * 
    * @pdOid fcee9843-a163-479e-b825-516ff0677d06 */
   @DBField
   private String czqx;
   /** 时间戳
    * 
    * @pdOid 7f185903-0282-4723-bb37-7ee8c718e7ae */
   @DBField
   private String changedate;
   /** 标记
    * 
    * @pdOid 22fc4328-5816-464b-b81c-7a063e65ce37 */
   @DBField
   private Integer tag;
   /** 删除
    * 
    * @pdOid e0a8e9b0-adcd-4a1b-8992-b7442314e965 */
   @DBField
   private Integer dr;
   /** 电子签名路径
    * 
    * @pdOid 4f0b642b-a3ce-458c-bacf-b2eae5c2d7ee */
   @DBField
   private String signpath;
   /** 指纹存储路径
    * 
    * @pdOid 81591594-396d-4dfe-8e94-7c3c3bb1c130 */
   @DBField
   private String zwsavepath;
   /** 是否黑名单
    * 
    * @pdOid 081c1316-312c-47b6-bba7-8ae4fa294d1b */
   @DBField
   private Integer ishmd;
   /** 部门描述
    * 
    * @pdOid 8fa1072e-e15a-41c0-bbd9-e20ddcd8cb68 */
   @DBField
   private String department_desc;
   /** 电话
    * 
    * @pdOid 01b86700-778f-4ad2-bfa2-d7525a53ce5f */
   @DBField
   private String primaryphone;
   
   /** @return the ud_zyxk_rykid
    * 
    * @pdOid 7622a529-0aa0-4903-b506-9ff3a1feb397 */
   public String getUd_zyxk_rykid() {
   	return ud_zyxk_rykid;
   }
   
   /** @param ud_zyxk_rykid the ud_zyxk_rykid to set
    * @pdOid 1f123f8c-0ff3-4980-9472-3ffae81b2ddd */
   public void setUd_zyxk_rykid(String ud_zyxk_rykid) {
   	this.ud_zyxk_rykid = ud_zyxk_rykid;
   }
   
   /** 设置 登陆账号
    * 该字段是：登陆账号
    * 
    * @param userid
    * @pdOid d6a3061e-9d5e-4c17-9fa6-59bfefaa52bc */
   public void setUserid(String userid) {
   	this.userid = userid;
   }
   
   /** 获取 登陆账号
    * 该字段是：登陆账号
    * 
    * 
    * @pdOid edb7d0d7-9773-4633-aaa5-b910c28150bc */
   public String getUserid() {
   	return userid;
   }
   
   /** 设置 人员编码
    * 该字段是：人员编码
    * 
    * @param personid
    * @pdOid 6bcece9d-f5b6-4a30-b3de-2a006c9021a6 */
   public void setPersonid(String personid) {
   	this.personid = personid;
   }
   
   /** 获取 人员编码
    * 该字段是：人员编码
    * 
    * 
    * @pdOid 9c4f1df7-1ed2-4341-acdd-83b35a210d92 */
   public String getPersonid() {
   	return personid;
   }
   
   /** @return the personid_desc
    * 
    * @pdOid 3141879e-6939-4070-b3c1-1321b59b0063 */
   public String getPersonid_desc() {
   	return personid_desc;
   }
   
   /** @param personid_desc the personid_desc to set
    * @pdOid 642ba06b-7162-4a55-8980-a8681ae50e58 */
   public void setPersonid_desc(String personid_desc) {
   	this.personid_desc = personid_desc;
   }
   
   /** 设置 人员卡
    * 该字段是：人员卡
    * 
    * @param pcardnum
    * @pdOid 5f68a310-f98e-4083-b28c-da3898bb776e */
   public void setPcardnum(String pcardnum) {
   	this.pcardnum = pcardnum;
   }
   
   /** 获取 人员卡
    * 该字段是：人员卡
    * 
    * 
    * @pdOid 41c64bfe-8e3e-4b02-a8f3-5e68239bd0e7 */
   public String getPcardnum() {
   	return pcardnum;
   }
   
   /** 设置 部门编码
    * 该字段是：部门编码
    * 
    * @param department
    * @pdOid 3f5812c4-3190-488a-92d4-01a2578deeb0 */
   public void setDepartment(String department) {
   	this.department = department;
   }
   
   /** 获取 部门编码
    * 该字段是：部门编码
    * 
    * 
    * @pdOid 3ea6a5d3-3850-42fe-826b-f8ca38e1bc00 */
   public String getDepartment() {
   	return department;
   }
   
   /** 设置 性别
    * 该字段是：性别
    * 
    * @param sex
    * @pdOid e7279c55-5570-4543-ac13-cbdd0ec118e2 */
   public void setSex(String sex) {
   	this.sex = sex;
   }
   
   /** 获取 性别
    * 该字段是：性别
    * 
    * 
    * @pdOid 06d1ac2c-da9e-456d-8cb4-51774a5d8782 */
   public String getSex() {
   	return sex;
   }
   
   /** 设置 所属单位
    * 该字段是：所属单位
    * 
    * @param vdeptname
    * @pdOid bd5ed7a7-d328-4f61-9417-bb9aa0d11eb9 */
   public void setVdeptname(String vdeptname) {
   	this.vdeptname = vdeptname;
   }
   
   /** 获取 所属单位
    * 该字段是：所属单位
    * 
    * 
    * @pdOid 28062930-ab25-4510-a833-f2fc41d4a1e0 */
   public String getVdeptname() {
   	return vdeptname;
   }
   
   /** 设置 身份证号
    * 该字段是：身份证号
    * 
    * @param iccard
    * @pdOid cc70c907-e5d2-448a-8670-0e4de6a08226 */
   public void setIccard(String iccard) {
   	this.iccard = iccard;
   }
   
   /** 获取 身份证号
    * 该字段是：身份证号
    * 
    * 
    * @pdOid b9322acc-c56e-4091-a780-4d8ca7630fdf */
   public String getIccard() {
   	return iccard;
   }
   
   /** 设置 培训结果
    * 该字段是：培训结果
    * 
    * @param trainresult
    * @pdOid ecd9c229-b57a-4079-a114-f6a2d0f82008 */
   public void setTrainresult(String trainresult) {
   	this.trainresult = trainresult;
   }
   
   /** 获取 培训结果
    * 该字段是：培训结果
    * 
    * 
    * @pdOid 8dfcaf40-4de1-49d6-a9ca-7d8482d876f6 */
   public String getTrainresult() {
   	return trainresult;
   }
   
   /** 设置 入厂证编号
    * 该字段是：入厂证编号
    * 
    * @param innercard
    * @pdOid 8e504c91-64e2-4481-933e-362315fcfb0c */
   public void setInnercard(String innercard) {
   	this.innercard = innercard;
   }
   
   /** 获取 入厂证编号
    * 该字段是：入厂证编号
    * 
    * 
    * @pdOid a75fc26a-d6a5-45aa-bbb8-ec162c12f18d */
   public String getInnercard() {
   	return innercard;
   }
   
   /** 设置 入厂时间
    * 该字段是：入厂时间
    * 
    * @param starttime
    * @pdOid 6fda49ec-621f-46b1-9ca7-db76cd7296ff */
   public void setStarttime(String starttime) {
   	this.starttime = starttime;
   }
   
   /** 获取 入厂时间
    * 该字段是：入厂时间
    * 
    * 
    * @pdOid 2cf9cc4e-a40c-4c53-b1d0-b65d1d388c68 */
   public String getStarttime() {
   	return starttime;
   }
   
   /** 设置 离厂时间
    * 该字段是：离厂时间
    * 
    * @param endtime
    * @pdOid d8652149-5b0b-4e22-8246-7427f8af0883 */
   public void setEndtime(String endtime) {
   	this.endtime = endtime;
   }
   
   /** 获取 离厂时间
    * 该字段是：离厂时间
    * 
    * 
    * @pdOid 33f6abe9-a2de-4ad9-aae4-223d22a9c962 */
   public String getEndtime() {
   	return endtime;
   }
   
   /** 设置 从事工种
    * 该字段是：从事工种
    * 
    * @param worktype
    * @pdOid c4cc1cb7-02c8-408f-b4cd-4edf8f1750d3 */
   public void setWorktype(String worktype) {
   	this.worktype = worktype;
   }
   
   /** 获取 从事工种
    * 该字段是：从事工种
    * 
    * 
    * @pdOid 838b4332-8e6a-414e-b36a-a2a2683a7e43 */
   public String getWorktype() {
   	return worktype;
   }
   
   /** 设置 密码
    * 该字段是：密码
    * 
    * @param password
    * @pdOid 2a4ab64a-6192-4fc8-8bdb-46a6cbdf3592 */
   public void setPassword(String password) {
   	this.password = password;
   }
   
   /** 获取 密码
    * 该字段是：密码
    * 
    * 
    * @pdOid 751d5836-811f-4c0a-860e-a932e715e474 */
   public String getPassword() {
   	return password;
   }
   
   /** 设置 是否登陆
    * 该字段是：是否登陆
    * 
    * @param islogin
    * @pdOid 11e684fc-50dc-438a-b1f3-56e177cac831 */
   public void setIslogin(Integer islogin) {
   	this.islogin = islogin;
   }
   
   /** 获取 是否登陆
    * 该字段是：是否登陆
    * 
    * 
    * @pdOid fa019bb4-0194-4c6c-9bd7-8721909048ce */
   public Integer getIslogin() {
   	return islogin;
   }
   
   /** 设置 是否有效
    * 该字段是：是否有效
    * 
    * @param iscan
    * @pdOid a2b733e9-fa27-4c55-8bfb-0d4c302e9292 */
   public void setIscan(Integer iscan) {
   	this.iscan = iscan;
   }
   
   /** 获取 是否有效
    * 该字段是：是否有效
    * 
    * 
    * @pdOid bc04130d-aea7-4959-83e5-3a945256929a */
   public Integer getIscan() {
   	return iscan;
   }
   
   /** 设置 是否承包商
    * 该字段是：是否承包商
    * 
    * @param iscbs
    * @pdOid 10c9d67a-7990-48a7-b457-391c683dfc96 */
   public void setIscbs(Integer iscbs) {
   	this.iscbs = iscbs;
   }
   
   /** 获取 是否承包商
    * 该字段是：是否承包商
    * 
    * 
    * @pdOid 43d8e19b-8981-4b15-ae9c-40c7d352999d */
   public Integer getIscbs() {
   	return iscbs;
   }
   
   /** 设置 下载权限
    * 该字段是：下载权限
    * 
    * @param czqx
    * @pdOid 9d158155-4560-408b-8dd7-a618a334a393 */
   public void setCzqx(String czqx) {
   	this.czqx = czqx;
   }
   
   /** 获取 下载权限
    * 该字段是：下载权限
    * 
    * 
    * @pdOid 8931a720-b177-4a47-9909-802b5775b6a5 */
   public String getCzqx() {
   	return czqx;
   }
   
   /** 设置 时间戳
    * 该字段是：时间戳
    * 
    * @param changedate
    * @pdOid 33486448-2fc7-466c-b8cc-22e21f0a6f57 */
   public void setChangedate(String changedate) {
   	this.changedate = changedate;
   }
   
   /** 获取 时间戳
    * 该字段是：时间戳
    * 
    * 
    * @pdOid e11992a4-7c41-4c27-b783-eb51aea61b64 */
   public String getChangedate() {
   	return changedate;
   }
   
   /** 设置 标记
    * 该字段是：标记
    * 
    * @param tag
    * @pdOid 783a299a-eaf4-4dbe-81ee-4ad8aa65a838 */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** 获取 标记
    * 该字段是：标记
    * 
    * 
    * @pdOid 4d401112-0029-42e5-acfa-dd1a92f62d61 */
   public Integer getTag() {
   	return tag;
   }
   
   /** 设置 删除
    * 该字段是：删除
    * 
    * @param dr
    * @pdOid 25507d19-296f-4788-931e-a18ac88b221d */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 删除
    * 该字段是：删除
    * 
    * 
    * @pdOid 3fbcee0e-c26f-4a16-b09a-61a3fc1c41ae */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 电子签名路径
    * 该字段是：电子签名路径
    * 
    * @param signpath
    * @pdOid 60c7b5f0-bb3e-403c-8d83-38687837bfa6 */
   public void setSignpath(String signpath) {
   	this.signpath = signpath;
   }
   
   /** 获取 电子签名路径
    * 该字段是：电子签名路径
    * 
    * 
    * @pdOid 9440acb4-7c95-4a2c-b6ef-6549c1348a21 */
   public String getSignpath() {
   	return signpath;
   }
   
   /** 设置 指纹存储路径
    * 该字段是：指纹存储路径
    * 
    * @param zwsavepath
    * @pdOid 31576932-999d-44b6-bdbb-20fddf324c4a */
   public void setZwsavepath(String zwsavepath) {
   	this.zwsavepath = zwsavepath;
   }
   
   /** 获取 指纹存储路径
    * 该字段是：指纹存储路径
    * 
    * 
    * @pdOid cbd18f97-37bf-44f5-992c-9901518a622a */
   public String getZwsavepath() {
   	return zwsavepath;
   }
   
   /** 设置 是否黑名单
    * 该字段是：是否黑名单
    * 
    * @param ishmd
    * @pdOid 248c4f61-5962-4a96-9155-159eaf0556d2 */
   public void setIshmd(Integer ishmd) {
   	this.ishmd = ishmd;
   }
   
   /** 获取 是否黑名单
    * 该字段是：是否黑名单
    * 
    * 
    * @pdOid 8c9497cf-7195-4f16-adbd-ac602a457279 */
   public Integer getIshmd() {
   	return ishmd;
   }
   
   /** @return the department_desc
    * 
    * @pdOid 69dca3bd-6c73-4391-8b04-e6ce4e046636 */
   public String getDepartment_desc() {
   	return department_desc;
   }
   
   /** @param department_desc the department_desc to set
    * @pdOid d0faa78f-2a1d-4048-b5a2-5e629ed2d563 */
   public void setDepartment_desc(String department_desc) {
   	this.department_desc = department_desc;
   }
   
   /** 设置 电话
    * 该字段是：电话
    * 
    * @param primaryphone
    * @pdOid c8e13f32-22dc-4a66-aeed-10adc2928e8f */
   public void setPrimaryphone(String primaryphone) {
   	this.primaryphone = primaryphone;
   }
   
   /** 获取 电话
    * 该字段是：电话
    * 
    * 
    * @pdOid 8e2de5f7-97de-4d71-b93e-01ae5c1b3323 */
   public String getPrimaryphone() {
   	return primaryphone;
   }

}