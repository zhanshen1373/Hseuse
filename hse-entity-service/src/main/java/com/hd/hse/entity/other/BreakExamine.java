/**
 * File:    UdZyxkWzjc.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkWzjc
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid fe9ed34a-b1d0-46cf-b771-e762422f1784 */
@DBTable(tableName = "ud_zyxk_wzjc")
public class BreakExamine extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 8427b4bb-1716-46eb-9709-67bb921fb44f */
   private static final long serialVersionUID = -2187396735795265226L;
   /** 主键
    * 
    * @pdOid 75a0024a-782a-42d5-b7e0-4e5823cb58a2 */
   @DBField(id=true)
   private Integer ud_zyxk_wzjcid;
   /** 作业许可证编号
    * 
    * @pdOid 67fa94e8-9aa4-469e-ac55-20e05ec7a284 */
   @DBField
   private String zysqnum;
   /** 外键
    * 
    * @pdOid 95b17623-89d8-4761-b28d-8378f1899e6a */
   @DBField(foreign=true)
   private Integer ud_zyxk_zysqid;
   /** 作业许可证名称
    * 
    * @pdOid adccb56b-f814-441d-bba1-20db850ffa7a */
   @DBField
   private String zysqname;
   /** 检查单位描述
    * 
    * @pdOid b0969d92-eb3b-48fa-8782-978efb87d357 */
   @DBField
   private String jcdept_desc;
   /** 检查人
    * 
    * @pdOid 7123e95a-811c-4b85-bd11-1c32f5ae3591 */
   @DBField
   private String jcperson;
   /** 被检查单位
    * 
    * @pdOid b50210d7-7508-4718-9d43-3191f856caec */
   @DBField
   private String bjcdept;
   /** 合同编码
    * 
    * @pdOid 92f6baed-3a19-4e16-ad98-103492b7a53c */
   @DBField
   private String htnum;
   /** 合同名称
    * 
    * @pdOid 9c894181-f74c-4375-920f-bd414798b19b */
   @DBField
   private String htname;
   /** 甲方代表
    * 
    * @pdOid c98a8a19-5c1f-4818-8c9b-c8f63e4afa5e */
   @DBField
   private String jfdb;
   /** 是否分包商
    * 
    * @pdOid b1735058-c620-434c-a87e-302d1ef35a42 */
   @DBField
   private Integer isfbs;
   /** 是否有问题
    * 
    * @pdOid 032d5fb5-a2e3-4ba4-9668-8ebba2e36a84 */
   @DBField
   private Integer iswt;
   /** 检查事实描述
    * 
    * @pdOid 7bfe768a-8870-46e3-a655-e2b02be9b639 */
   @DBField
   private String jcssms;
   /** 违约时间
    * 
    * @pdOid 76f0b24f-6fc9-4d25-8252-2d051ef6d33f */
   @DBField
   private String wytime;
   /** 违约地点
    * 
    * @pdOid ab1e165d-d4d4-4002-b0e6-a939ed67bbd5 */
   @DBField
   private String wysite;
   /** 违规内容
    * 
    * @pdOid c649b87f-efc6-4371-aeda-51dd70e3d59b */
   @DBField
   private String wgnr;
   /** 问题责任人
    * 
    * @pdOid c728cf9f-e6cf-4a1c-955e-038b77a4d5b7 */
   @DBField
   private String wtzrperson;
   /** 发现人次
    * 
    * @pdOid fdaf25c4-6dc6-4c82-bb64-666eaa3706a6 */
   @DBField
   private Integer fxrc;
   /** 问题总扣分数
    * 
    * @pdOid ef083106-e8c3-4b98-b71e-967182bfd9a6 */
   @DBField
   private Double wtzkfs;
   /** 问题总扣分数1
    * 
    * @pdOid b7dc37cd-125c-4380-9306-52a071022496 */
   @DBField
   private Double wtzkks;
   /** 是否现场整改
    * 
    * @pdOid b3896d24-9d04-4f83-b7fb-b1497c3b7bad */
   @DBField
   private Integer isxczg;
   /** 整改状态
    * 
    * @pdOid a553118d-c31f-4b70-912c-ddd7dcce61a3 */
   @DBField
   private String zgstatus;
   /** 验收人
    * 
    * @pdOid 4b623f9c-e27d-4678-ada3-1869db4dcdca */
   @DBField
   private String ysperson;
   /** 验收时间
    * 
    * @pdOid a86b06be-e606-478b-93f0-956d2cb5721a */
   @DBField
   private String yspersontime;
   /** a1
    * 
    * @pdOid bae60729-8fba-49b8-8d29-af97c3047692 */
   @DBField
   private Integer isupload;
   /** 检查类型
    * 
    * @pdOid 72f8a2cc-ae87-4e3e-9288-67276fc9a0ef */
   @DBField
   private String jctype;
   
   /** @return the ud_zyxk_wzjcid
    * 
    * @pdOid 70c27c64-e5f1-4626-ae9d-0f481171c6df */
   public Integer getUd_zyxk_wzjcid() {
   	return ud_zyxk_wzjcid;
   }
   
   /** @param ud_zyxk_wzjcid the ud_zyxk_wzjcid to set
    * @pdOid dadbc64e-5716-4a09-97c0-7e841701b431 */
   public void setUd_zyxk_wzjcid(Integer ud_zyxk_wzjcid) {
   	this.ud_zyxk_wzjcid = ud_zyxk_wzjcid;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid 56f561d0-6974-432a-ac43-e481182ff9b8 */
   public Integer getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid a9000b56-4acd-4b51-a4ea-70178427b393 */
   public void setUd_zyxk_zysqid(Integer ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** 设置 作业许可证编号
    * 该字段是：作业许可证编号
    * 
    * @param zysqnum
    * @pdOid df7e27c5-91a8-46e4-84a4-429f4e13c780 */
   public void setZysqnum(String zysqnum) {
   	this.zysqnum = zysqnum;
   }
   
   /** 获取 作业许可证编号
    * 该字段是：作业许可证编号
    * 
    * 
    * @pdOid fae9deae-e7eb-4291-9c7b-60e2a1430922 */
   public String getZysqnum() {
   	return zysqnum;
   }
   
   /** 设置 作业许可证名称
    * 该字段是：作业许可证名称
    * 
    * @param zysqname
    * @pdOid 21de50e6-5ec3-4c93-a869-6f7d4d1afd61 */
   public void setZysqname(String zysqname) {
   	this.zysqname = zysqname;
   }
   
   /** 获取 作业许可证名称
    * 该字段是：作业许可证名称
    * 
    * 
    * @pdOid 7432d4f4-0e29-4d76-ad97-e7983a5407b7 */
   public String getZysqname() {
   	return zysqname;
   }
   
   /** @return the jcdept_desc
    * 
    * @pdOid fdff23e2-b0bd-4550-b3b9-b183f9d43223 */
   public String getJcdept_desc() {
   	return jcdept_desc;
   }
   
   /** @param jcdept_desc the jcdept_desc to set
    * @pdOid c613a950-2e28-41f0-8255-112c88dc257f */
   public void setJcdept_desc(String jcdept_desc) {
   	this.jcdept_desc = jcdept_desc;
   }
   
   /** 设置 检查人
    * 该字段是：检查人
    * 
    * @param jcperson
    * @pdOid db3eca3f-5209-4dfe-94e6-a9d38bc30815 */
   public void setJcperson(String jcperson) {
   	this.jcperson = jcperson;
   }
   
   /** 获取 检查人
    * 该字段是：检查人
    * 
    * 
    * @pdOid f7c3f708-cca6-4169-9bba-8def18b5be04 */
   public String getJcperson() {
   	return jcperson;
   }
   
   /** 设置 被检查单位
    * 该字段是：被检查单位
    * 
    * @param bjcdept
    * @pdOid 805ae5dc-7bb2-421f-b101-c85968eb5a0a */
   public void setBjcdept(String bjcdept) {
   	this.bjcdept = bjcdept;
   }
   
   /** 获取 被检查单位
    * 该字段是：被检查单位
    * 
    * 
    * @pdOid 7ee6b95c-b005-4359-9639-a75f51f6716f */
   public String getBjcdept() {
   	return bjcdept;
   }
   
   /** 设置 合同编码
    * 该字段是：合同编码
    * 
    * @param htnum
    * @pdOid 4f503120-d11b-4a8b-9253-678791a7aa34 */
   public void setHtnum(String htnum) {
   	this.htnum = htnum;
   }
   
   /** 获取 合同编码
    * 该字段是：合同编码
    * 
    * 
    * @pdOid 310bd620-c7e2-4e59-bb81-794b42505f59 */
   public String getHtnum() {
   	return htnum;
   }
   
   /** 设置 合同名称
    * 该字段是：合同名称
    * 
    * @param htname
    * @pdOid 7447e7b8-c1a5-40f9-87df-f7e095f6bb5f */
   public void setHtname(String htname) {
   	this.htname = htname;
   }
   
   /** 获取 合同名称
    * 该字段是：合同名称
    * 
    * 
    * @pdOid c67791bb-d6ae-41f2-89a9-ef926ea7c258 */
   public String getHtname() {
   	return htname;
   }
   
   /** 设置 甲方代表
    * 该字段是：甲方代表
    * 
    * @param jfdb
    * @pdOid ed6e4378-28b0-4188-a1fe-f66cfcccda48 */
   public void setJfdb(String jfdb) {
   	this.jfdb = jfdb;
   }
   
   /** 获取 甲方代表
    * 该字段是：甲方代表
    * 
    * 
    * @pdOid cb91ac25-03d9-444f-b19b-3c06f95564c3 */
   public String getJfdb() {
   	return jfdb;
   }
   
   /** 设置 是否分包商
    * 该字段是：是否分包商
    * 
    * @param isfbs
    * @pdOid 1a5baf90-d691-4f8f-b813-472de815a6cd */
   public void setIsfbs(Integer isfbs) {
   	this.isfbs = isfbs;
   }
   
   /** 获取 是否分包商
    * 该字段是：是否分包商
    * 
    * 
    * @pdOid 83a44c62-e56e-4105-9e71-89f9a7c66607 */
   public Integer getIsfbs() {
   	return isfbs;
   }
   
   /** 设置 是否有问题
    * 该字段是：是否有问题
    * 
    * @param iswt
    * @pdOid fd67bb1d-a6f0-4e2d-a2e2-e64699c2383b */
   public void setIswt(Integer iswt) {
   	this.iswt = iswt;
   }
   
   /** 获取 是否有问题
    * 该字段是：是否有问题
    * 
    * 
    * @pdOid fe302aea-ef33-4657-b415-a513340bf837 */
   public Integer getIswt() {
   	return iswt;
   }
   
   /** 设置 检查事实描述
    * 该字段是：检查事实描述
    * 
    * @param jcssms
    * @pdOid 93d2b35c-6af8-4402-a5e7-2c448bc20997 */
   public void setJcssms(String jcssms) {
   	this.jcssms = jcssms;
   }
   
   /** 获取 检查事实描述
    * 该字段是：检查事实描述
    * 
    * 
    * @pdOid 08afa1dc-b672-4701-875d-83c67c528b06 */
   public String getJcssms() {
   	return jcssms;
   }
   
   /** 设置 违约时间
    * 该字段是：违约时间
    * 
    * @param wytime
    * @pdOid c7435eb5-0880-4ab2-b8d2-142348e618f5 */
   public void setWytime(String wytime) {
   	this.wytime = wytime;
   }
   
   /** 获取 违约时间
    * 该字段是：违约时间
    * 
    * 
    * @pdOid 409e5fbd-6324-488c-8fca-ac829de787bd */
   public String getWytime() {
   	return wytime;
   }
   
   /** 设置 违约地点
    * 该字段是：违约地点
    * 
    * @param wysite
    * @pdOid 38638e42-17b9-4a22-ba28-130f08f6caab */
   public void setWysite(String wysite) {
   	this.wysite = wysite;
   }
   
   /** 获取 违约地点
    * 该字段是：违约地点
    * 
    * 
    * @pdOid d56164de-f66f-439f-a3e9-c5971f51a03f */
   public String getWysite() {
   	return wysite;
   }
   
   /** 设置 违规内容
    * 该字段是：违规内容
    * 
    * @param wgnr
    * @pdOid 256864ac-0847-474b-8278-63ff749919f6 */
   public void setWgnr(String wgnr) {
   	this.wgnr = wgnr;
   }
   
   /** 获取 违规内容
    * 该字段是：违规内容
    * 
    * 
    * @pdOid 4f5e5bd3-752a-405e-9d58-1e27decb6f71 */
   public String getWgnr() {
   	return wgnr;
   }
   
   /** 设置 问题责任人
    * 该字段是：问题责任人
    * 
    * @param wtzrperson
    * @pdOid 816d167f-d603-4a35-b573-a815cc4bb48a */
   public void setWtzrperson(String wtzrperson) {
   	this.wtzrperson = wtzrperson;
   }
   
   /** 获取 问题责任人
    * 该字段是：问题责任人
    * 
    * 
    * @pdOid c4e08b72-7efd-4ba2-b707-0ec707286357 */
   public String getWtzrperson() {
   	return wtzrperson;
   }
   
   /** 设置 发现人次
    * 该字段是：发现人次
    * 
    * @param fxrc
    * @pdOid c2c85106-78d1-41c9-a5ef-42777d7d5056 */
   public void setFxrc(Integer fxrc) {
   	this.fxrc = fxrc;
   }
   
   /** 获取 发现人次
    * 该字段是：发现人次
    * 
    * 
    * @pdOid a66a59c3-b714-47d5-ab8d-075af3eb3f76 */
   public Integer getFxrc() {
   	return fxrc;
   }
   
   /** 设置 问题总扣分数
    * 该字段是：问题总扣分数
    * 
    * @param wtzkfs
    * @pdOid 1a650ed4-5dd3-41a4-a3eb-7b0e23dac01a */
   public void setWtzkfs(Double wtzkfs) {
   	this.wtzkfs = wtzkfs;
   }
   
   /** 获取 问题总扣分数
    * 该字段是：问题总扣分数
    * 
    * 
    * @pdOid d1eb61de-4309-430c-ac1e-c296170696f7 */
   public Double getWtzkfs() {
   	return wtzkfs;
   }
   
   /** 设置 问题总扣分数1
    * 该字段是：问题总扣分数1
    * 
    * @param wtzkks
    * @pdOid 78c6d22b-a53e-4b0a-92ea-6946b78a8fa9 */
   public void setWtzkks(Double wtzkks) {
   	this.wtzkks = wtzkks;
   }
   
   /** 获取 问题总扣分数1
    * 该字段是：问题总扣分数1
    * 
    * 
    * @pdOid cd8ca41f-ded5-42ab-9207-98def86ac10c */
   public Double getWtzkks() {
   	return wtzkks;
   }
   
   /** 设置 是否现场整改
    * 该字段是：是否现场整改
    * 
    * @param isxczg
    * @pdOid 8c09d387-8a79-43db-95c7-788af895127c */
   public void setIsxczg(Integer isxczg) {
   	this.isxczg = isxczg;
   }
   
   /** 获取 是否现场整改
    * 该字段是：是否现场整改
    * 
    * 
    * @pdOid a93ba24a-59ef-4908-a8c0-18e39e268255 */
   public Integer getIsxczg() {
   	return isxczg;
   }
   
   /** 设置 整改状态
    * 该字段是：整改状态
    * 
    * @param zgstatus
    * @pdOid 7af36988-cb7b-4631-b8be-63812c7094e7 */
   public void setZgstatus(String zgstatus) {
   	this.zgstatus = zgstatus;
   }
   
   /** 获取 整改状态
    * 该字段是：整改状态
    * 
    * 
    * @pdOid 428b3f8d-a116-47eb-829d-991b4606dc05 */
   public String getZgstatus() {
   	return zgstatus;
   }
   
   /** 设置 验收人
    * 该字段是：验收人
    * 
    * @param ysperson
    * @pdOid dd32ddbc-b9ec-4bcd-9ff1-f7acd1fe5d50 */
   public void setYsperson(String ysperson) {
   	this.ysperson = ysperson;
   }
   
   /** 获取 验收人
    * 该字段是：验收人
    * 
    * 
    * @pdOid fa0f5d2e-1d89-4477-90cf-e919baa59097 */
   public String getYsperson() {
   	return ysperson;
   }
   
   /** 设置 验收时间
    * 该字段是：验收时间
    * 
    * @param yspersontime
    * @pdOid 5b28b4fe-9323-4cdb-b72f-32efea889da9 */
   public void setYspersontime(String yspersontime) {
   	this.yspersontime = yspersontime;
   }
   
   /** 获取 验收时间
    * 该字段是：验收时间
    * 
    * 
    * @pdOid ded25e26-65cb-4715-aa79-38e015e60d13 */
   public String getYspersontime() {
   	return yspersontime;
   }
   
   /** 设置 a1
    * 该字段是：a1
    * 
    * @param isupload
    * @pdOid c9d0d597-54d4-47b2-9081-f4c0510890a2 */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** 获取 a1
    * 该字段是：a1
    * 
    * 
    * @pdOid a51a353d-f7c3-4042-ab33-cd2110ce5464 */
   public Integer getIsupload() {
   	return isupload;
   }
   
   /** 设置 检查类型
    * 该字段是：检查类型
    * 
    * @param jctype
    * @pdOid e29d142d-4b9c-48f2-bc3f-e1db3ad551d2 */
   public void setJctype(String jctype) {
   	this.jctype = jctype;
   }
   
   /** 获取 检查类型
    * 该字段是：检查类型
    * 
    * 
    * @pdOid 5fc22cb3-9ca5-46d3-9ad9-0105b7ccd525 */
   public String getJctype() {
   	return jctype;
   }

}