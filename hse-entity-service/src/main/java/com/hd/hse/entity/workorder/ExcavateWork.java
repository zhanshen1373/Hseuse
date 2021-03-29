/**
 * File:    UdZyxkWjzy.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkWjzy
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 06ce318b-3feb-4c36-a8be-dcec71c453b6 */
@DBTable(tableName = "ud_zyxk_wjzy")
public class ExcavateWork extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 1314fd8e-f0fc-4109-aa41-2997011c7e92 */
   private static final long serialVersionUID = 2264214244469054034L;
   /** 主键
    * 
    * @pdOid 4701f316-53f3-4211-a1dd-490e7fc04b41 */
   @DBField(id=true)
   private Integer ud_zyxk_wjzyid;
   /** 外键
    * 
    * @pdOid 6ab2e1da-8149-4556-90d4-639d64fbb756 */
   @DBField(foreign=true)
   private Integer ud_zyxk_zysqid;
   /** 地下隐蔽措施
    * 
    * @pdOid af059e2e-8bfe-4d1b-9e71-0a0d710ce5af */
   @DBField
   private String dxpbcs;
   /** 地上架空线路
    * 
    * @pdOid 4c9785a0-6d54-45e2-991e-6da97c0bc344 */
   @DBField
   private String dsjkxl;
   /** 其他设施
    * 
    * @pdOid 94b7c64b-dac7-4116-8a1c-e1f08f5c6a29 */
   @DBField
   private String qtcs;
   /** 不确定因素
    * 
    * @pdOid 50ebe637-3dc7-4975-b0c9-e8320c7959cd */
   @DBField
   private String bqdys;
   /** 类型(总图/工艺等)
    * 
    * @pdOid 994a6a97-e2ab-4f4c-8ceb-8b04666df9f3 */
   @DBField
   private String type;
   /** 基本要求
    * 
    * @pdOid ce46ef05-710a-4e18-87cc-f7455aeba00e */
   @DBField
   private String jbyq;
   /** 保护系统
    * 
    * @pdOid ef07830c-bb5d-43cf-85dd-d87cd1d90634 */
   @DBField
   private String bhsystem;
   /** 进出口
    * 
    * @pdOid 46e0e4f6-5fad-495e-94bd-a1d7ef9231aa */
   @DBField
   private String jck;
   /** 危险性气体
    * 
    * @pdOid faa0589e-22b6-45d5-9c75-4b33e1b1a4da */
   @DBField
   private String wxqt;
   /** 个体防护
    * 
    * @pdOid 20a77efc-230f-43c3-87a1-32c780bff381 */
   @DBField
   private String gtfh;
   /** 警示标识
    * 
    * @pdOid 08100e67-a595-4305-b604-c1a85fb7c33c */
   @DBField
   private String warnbz;
   /** 作业单位监护人
    * 
    * @pdOid 0d3e229f-9ded-4ab3-8184-ec8ceb17e2c5 */
   @DBField
   private String zydwjhperson;
   /** 属地单位监护人
    * 
    * @pdOid 1e237daa-a1d5-4253-836e-7263cf0fc309 */
   @DBField
   private String sddwjhperson;
   /** 项目管理负责人
    * 
    * @pdOid b6531f4a-2a91-4a85-93bf-a235d80f1678 */
   @DBField
   private String xmglfzperson;
   
   /** @return the ud_zyxk_wjzyid
    * 
    * @pdOid bfafeb48-72a5-4030-9bf6-9d014915594d */
   public Integer getUd_zyxk_wjzyid() {
   	return ud_zyxk_wjzyid;
   }
   
   /** @param ud_zyxk_wjzyid the ud_zyxk_wjzyid to set
    * @pdOid ff22e20f-80a9-4cb7-872f-3f1d4fb3bf9f */
   public void setUd_zyxk_wjzyid(Integer ud_zyxk_wjzyid) {
   	this.ud_zyxk_wjzyid = ud_zyxk_wjzyid;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid b4a615b6-c3f1-4301-b73f-70239e9b0491 */
   public Integer getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid 8cbb77b0-f623-44e6-abdc-7287ccf99d97 */
   public void setUd_zyxk_zysqid(Integer ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** 设置 地下隐蔽措施
    * 该字段是：地下隐蔽措施
    * 
    * @param dxpbcs
    * @pdOid ac95cee3-f3ec-4c0c-97f9-c1078411d949 */
   public void setDxpbcs(String dxpbcs) {
   	this.dxpbcs = dxpbcs;
   }
   
   /** 获取 地下隐蔽措施
    * 该字段是：地下隐蔽措施
    * 
    * 
    * @pdOid 897f5a28-b8d2-48bf-8693-80c52bc7dd2b */
   public String getDxpbcs() {
   	return dxpbcs;
   }
   
   /** 设置 地上架空线路
    * 该字段是：地上架空线路
    * 
    * @param dsjkxl
    * @pdOid b4d99601-1345-42a5-8cfe-207746fe8860 */
   public void setDsjkxl(String dsjkxl) {
   	this.dsjkxl = dsjkxl;
   }
   
   /** 获取 地上架空线路
    * 该字段是：地上架空线路
    * 
    * 
    * @pdOid 1bb7680b-23b2-4960-bf6a-e54fe083f607 */
   public String getDsjkxl() {
   	return dsjkxl;
   }
   
   /** 设置 其他设施
    * 该字段是：其他设施
    * 
    * @param qtcs
    * @pdOid 9b87fc16-f833-47bd-ae4a-deaef4722344 */
   public void setQtcs(String qtcs) {
   	this.qtcs = qtcs;
   }
   
   /** 获取 其他设施
    * 该字段是：其他设施
    * 
    * 
    * @pdOid 03dd2277-113a-4134-88c2-d8df4cea6611 */
   public String getQtcs() {
   	return qtcs;
   }
   
   /** 设置 不确定因素
    * 该字段是：不确定因素
    * 
    * @param bqdys
    * @pdOid 02f269ff-28a0-4f78-9f6c-4816ab57ebe7 */
   public void setBqdys(String bqdys) {
   	this.bqdys = bqdys;
   }
   
   /** 获取 不确定因素
    * 该字段是：不确定因素
    * 
    * 
    * @pdOid fb5656f7-f23f-4e2b-b642-2015b14b75d0 */
   public String getBqdys() {
   	return bqdys;
   }
   
   /** 设置 类型(总图/工艺等)
    * 该字段是：类型(总图/工艺等)
    * 
    * @param type
    * @pdOid 018c837b-bfb9-4987-8b1c-5d165dd77585 */
   public void setType(String type) {
   	this.type = type;
   }
   
   /** 获取 类型(总图/工艺等)
    * 该字段是：类型(总图/工艺等)
    * 
    * 
    * @pdOid 615cadd7-a8ad-4eac-a894-407958bbc467 */
   public String getType() {
   	return type;
   }
   
   /** 设置 基本要求
    * 该字段是：基本要求
    * 
    * @param jbyq
    * @pdOid 08ecab7d-dd07-481c-8681-3b3673104a3e */
   public void setJbyq(String jbyq) {
   	this.jbyq = jbyq;
   }
   
   /** 获取 基本要求
    * 该字段是：基本要求
    * 
    * 
    * @pdOid d851fd9b-4f03-4239-b778-3edd16367d20 */
   public String getJbyq() {
   	return jbyq;
   }
   
   /** 设置 保护系统
    * 该字段是：保护系统
    * 
    * @param bhsystem
    * @pdOid 26cb7a14-58d4-4746-a40e-a9b45f3191ec */
   public void setBhsystem(String bhsystem) {
   	this.bhsystem = bhsystem;
   }
   
   /** 获取 保护系统
    * 该字段是：保护系统
    * 
    * 
    * @pdOid 1393699f-e2de-4ce3-88c4-d2dc3ae16729 */
   public String getBhsystem() {
   	return bhsystem;
   }
   
   /** 设置 进出口
    * 该字段是：进出口
    * 
    * @param jck
    * @pdOid 6b4f126d-4874-495f-967d-a4d4653048eb */
   public void setJck(String jck) {
   	this.jck = jck;
   }
   
   /** 获取 进出口
    * 该字段是：进出口
    * 
    * 
    * @pdOid b20b377c-1c51-4e5a-83d8-4c97b950fad6 */
   public String getJck() {
   	return jck;
   }
   
   /** 设置 危险性气体
    * 该字段是：危险性气体
    * 
    * @param wxqt
    * @pdOid e3c7e4b3-444f-4e4e-bb49-c8e762b48497 */
   public void setWxqt(String wxqt) {
   	this.wxqt = wxqt;
   }
   
   /** 获取 危险性气体
    * 该字段是：危险性气体
    * 
    * 
    * @pdOid 0cacc8a8-f62a-4499-9c22-ce49245ba1b3 */
   public String getWxqt() {
   	return wxqt;
   }
   
   /** 设置 个体防护
    * 该字段是：个体防护
    * 
    * @param gtfh
    * @pdOid d617e0ce-50d3-4720-886a-58a5c536f9a6 */
   public void setGtfh(String gtfh) {
   	this.gtfh = gtfh;
   }
   
   /** 获取 个体防护
    * 该字段是：个体防护
    * 
    * 
    * @pdOid d2dd7715-2fd9-4bdb-ab88-43e097591d4e */
   public String getGtfh() {
   	return gtfh;
   }
   
   /** 设置 警示标识
    * 该字段是：警示标识
    * 
    * @param warnbz
    * @pdOid 93d84930-24ad-465d-9256-6e29a7b4bcc4 */
   public void setWarnbz(String warnbz) {
   	this.warnbz = warnbz;
   }
   
   /** 获取 警示标识
    * 该字段是：警示标识
    * 
    * 
    * @pdOid 367a3a77-4217-4b9f-afeb-5ad4ea3ee323 */
   public String getWarnbz() {
   	return warnbz;
   }
   
   /** 设置 作业单位监护人
    * 该字段是：作业单位监护人
    * 
    * @param zydwjhperson
    * @pdOid cc69d475-61b8-4cb2-8f00-e71f81f68878 */
   public void setZydwjhperson(String zydwjhperson) {
   	this.zydwjhperson = zydwjhperson;
   }
   
   /** 获取 作业单位监护人
    * 该字段是：作业单位监护人
    * 
    * 
    * @pdOid 0bc66f6e-bff6-47b6-981c-b9d0f1c5a088 */
   public String getZydwjhperson() {
   	return zydwjhperson;
   }
   
   /** 设置 属地单位监护人
    * 该字段是：属地单位监护人
    * 
    * @param sddwjhperson
    * @pdOid 2e869a94-a757-4774-a595-c92389313ccf */
   public void setSddwjhperson(String sddwjhperson) {
   	this.sddwjhperson = sddwjhperson;
   }
   
   /** 获取 属地单位监护人
    * 该字段是：属地单位监护人
    * 
    * 
    * @pdOid d9e13d72-2c8a-46da-9cac-fc31bfd0e983 */
   public String getSddwjhperson() {
   	return sddwjhperson;
   }
   
   /** 设置 项目管理负责人
    * 该字段是：项目管理负责人
    * 
    * @param xmglfzperson
    * @pdOid 8354ac06-ce5d-459c-ae29-bfaaf5207348 */
   public void setXmglfzperson(String xmglfzperson) {
   	this.xmglfzperson = xmglfzperson;
   }
   
   /** 获取 项目管理负责人
    * 该字段是：项目管理负责人
    * 
    * 
    * @pdOid 7f0b5a59-e9a8-4de7-b711-d3b9248491c1 */
   public String getXmglfzperson() {
   	return xmglfzperson;
   }

}