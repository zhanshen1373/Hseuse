/**
 * File:    UdZyxkJsa.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkJsa
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 4ca111e3-940f-474f-8352-f1832620f2d4 */
@DBTable(tableName = "ud_zyxk_jsa")
public class JSATable extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid cfd23657-7c1d-400d-950f-48e02bc4d097 */
   private static final long serialVersionUID = -1200255519261662288L;
   /** a1
    * 
    * @pdOid adfd070a-7315-4e68-9f6f-2df047771ef2 */
   @DBField
   private String ud_zyxk_jsaid;
   /** a2
    * 
    * @pdOid e1e8efee-9d14-453b-9c88-a82a9e752eb3 */
   @DBField
   private String proj_num;
   /** a3
    * 
    * @pdOid 73288711-3fd5-4b06-894f-f01d764c0f33 */
   @DBField
   private String status;
   /** a4
    * 
    * @pdOid e53b9b50-3af1-46d8-9719-456229ec67f0 */
   @DBField
   private String wtjs;
   /** a5
    * 
    * @pdOid ee3bb546-f916-4c83-9450-b25410fb375b */
   @DBField
   private String jobsite;
   /** 单位
    * 
    * @pdOid 89d1eada-ad45-47d0-8a91-5f8567d3560d */
   @DBField
   private String bzdept;
   /** 编制单位描述
    * 
    * @pdOid 4915a66f-749e-4ca6-8ca0-5597c4a01876 */
   @DBField
   private String bzdept_desc;
   /** a8
    * 
    * @pdOid 609998d9-36cd-4a2c-815e-24c4b93b647b */
   @DBField
   private String asset_name;
   /** a9
    * 
    * @pdOid 4bd2d42b-7166-49c6-83b8-f77ddb595154 */
   @DBField
   private String eqzbh;
   /** a10
    * 
    * @pdOid ccde78fd-fc3a-4a5b-8e14-c372af0aec19 */
   @DBField
   private String zyarea;
   /** a11
    * 
    * @pdOid cb49aae7-ded7-4a6e-a207-a47d71aac76c */
   @DBField
   private String fxlevel;
   /** a12
    * 
    * @pdOid 7466123c-132f-4d93-901c-87f541553140 */
   @DBField
   private String jxlx;
   /** a13
    * 
    * @pdOid 190dfa03-c20d-4e8e-be98-d19e220155a0 */
   @DBField
   private String jhaname;
   /** 分析日期
    * 
    * @pdOid 4d05640f-4852-4fa6-b44c-396e5a09c6f0 */
   @DBField
   private String fxdata;
   /** a15
    * 
    * @pdOid 3c564596-6ddb-4082-8371-9ec715bc2812 */
   @DBField
   private String jhatype;
   /** 改进建议
    * 
    * @pdOid bba0f6af-e356-4f7e-bbfc-fa811b5cd509 */
   @DBField
   private String gjjy;
   
   /** @return the ud_zyxk_jsaid
    * 
    * @pdOid f7cf988e-d09b-4cd5-948d-f29878228c2f */
   public String getUd_zyxk_jsaid() {
   	return ud_zyxk_jsaid;
   }
   
   /** @param ud_zyxk_jsaid the ud_zyxk_jsaid to set
    * @pdOid 17a26ac3-1e92-4633-a17c-21b72ddb3426 */
   public void setUd_zyxk_jsaid(String ud_zyxk_jsaid) {
   	this.ud_zyxk_jsaid = ud_zyxk_jsaid;
   }
   
   /** @return the proj_num
    * 
    * @pdOid 60ddb57f-f960-4fc2-9d05-216ce7dcf746 */
   public String getProj_num() {
   	return proj_num;
   }
   
   /** @param proj_num the proj_num to set
    * @pdOid bfb15268-d2cf-47bd-955e-4ceaf1623040 */
   public void setProj_num(String proj_num) {
   	this.proj_num = proj_num;
   }
   
   /** @return the status
    * 
    * @pdOid 0e0a1796-4693-4baa-a89a-2b4812eda827 */
   public String getStatus() {
   	return status;
   }
   
   /** @param status the status to set
    * @pdOid 0c9add48-a38a-4f5e-87aa-6bfa700693f6 */
   public void setStatus(String status) {
   	this.status = status;
   }
   
   /** @return the wtjs
    * 
    * @pdOid cbdb8701-28ab-4b6d-902c-b45cd0776184 */
   public String getWtjs() {
   	return wtjs;
   }
   
   /** @param wtjs the wtjs to set
    * @pdOid 95f3c4f0-dbe1-4b41-aa38-ee8a70f7a2e0 */
   public void setWtjs(String wtjs) {
   	this.wtjs = wtjs;
   }
   
   /** @return the jobsite
    * 
    * @pdOid cce61c2d-495c-48b4-b40c-3c946395ef08 */
   public String getJobsite() {
   	return jobsite;
   }
   
   /** @param jobsite the jobsite to set
    * @pdOid 1a0b1e7c-7a32-4e8b-8667-3c24aed0010a */
   public void setJobsite(String jobsite) {
   	this.jobsite = jobsite;
   }
   
   /** @return the bzdept
    * 
    * @pdOid ce5c651b-fb8f-4915-b27a-1b732cdb5d1b */
   public String getBzdept() {
   	return bzdept;
   }
   
   /** @param bzdept the bzdept to set
    * @pdOid f50f2043-ccec-4d3e-aad9-d936a093ea48 */
   public void setBzdept(String bzdept) {
   	this.bzdept = bzdept;
   }
   
   /** @return the bzdept_desc
    * 
    * @pdOid 5c32a39d-6f1b-40fc-91bb-ffc6a56c4fd3 */
   public String getBzdept_desc() {
   	return bzdept_desc;
   }
   
   /** @param bzdept_desc the bzdept_desc to set
    * @pdOid 73d15977-b0ee-4425-bd69-46a00dd3956a */
   public void setBzdept_desc(String bzdept_desc) {
   	this.bzdept_desc = bzdept_desc;
   }
   
   /** @return the asset_name
    * 
    * @pdOid 1f887a09-0d91-4316-938d-33fe5a23626f */
   public String getAsset_name() {
   	return asset_name;
   }
   
   /** @param asset_name the asset_name to set
    * @pdOid 1299790f-242f-4201-95f6-d11a294c35a7 */
   public void setAsset_name(String asset_name) {
   	this.asset_name = asset_name;
   }
   
   /** @return the eqzbh
    * 
    * @pdOid 22edf001-44ba-4f24-b865-69764e5eb7cf */
   public String getEqzbh() {
   	return eqzbh;
   }
   
   /** @param eqzbh the eqzbh to set
    * @pdOid df10ac27-a4d6-435b-8365-8a7ec22085ed */
   public void setEqzbh(String eqzbh) {
   	this.eqzbh = eqzbh;
   }
   
   /** @return the zyarea
    * 
    * @pdOid cbb5cc46-a508-48f6-8bdf-16923d90fa39 */
   public String getZyarea() {
   	return zyarea;
   }
   
   /** @param zyarea the zyarea to set
    * @pdOid 324245ae-3f26-4517-a2e3-0d40e4e970e3 */
   public void setZyarea(String zyarea) {
   	this.zyarea = zyarea;
   }
   
   /** @return the fxlevel
    * 
    * @pdOid 82bc1212-bf05-4d85-8e63-e6fa210f793b */
   public String getFxlevel() {
   	return fxlevel;
   }
   
   /** @param fxlevel the fxlevel to set
    * @pdOid ea5c9b8f-9e17-43ad-b37d-f720686aca97 */
   public void setFxlevel(String fxlevel) {
   	this.fxlevel = fxlevel;
   }
   
   /** @return the jxlx
    * 
    * @pdOid b6d5318c-d639-4635-a471-83022dfc2076 */
   public String getJxlx() {
   	return jxlx;
   }
   
   /** @param jxlx the jxlx to set
    * @pdOid ff419172-2098-41be-843f-fbb647f75dfa */
   public void setJxlx(String jxlx) {
   	this.jxlx = jxlx;
   }
   
   /** @return the jhaname
    * 
    * @pdOid d5602fac-c57a-4e87-beca-f78c7420dcea */
   public String getJhaname() {
   	return jhaname;
   }
   
   /** @param jhaname the jhaname to set
    * @pdOid 5c6a6be6-bf25-44e1-8848-c9e1494953a1 */
   public void setJhaname(String jhaname) {
   	this.jhaname = jhaname;
   }
   
   /** @return the fxdata
    * 
    * @pdOid 8c88ae93-4152-407f-ae7c-261da8a0652b */
   public String getFxdata() {
   	return fxdata;
   }
   
   /** @param fxdata the fxdata to set
    * @pdOid 410ace8f-9a18-4afa-a435-68a3731c3097 */
   public void setFxdata(String fxdata) {
   	this.fxdata = fxdata;
   }
   
   /** @return the jhatype
    * 
    * @pdOid 53e13727-f1b9-40f2-ae91-c74555dddce0 */
   public String getJhatype() {
   	return jhatype;
   }
   
   /** @param jhatype the jhatype to set
    * @pdOid 0797020c-03df-45ab-ad12-1073888a7a0d */
   public void setJhatype(String jhatype) {
   	this.jhatype = jhatype;
   }
   
   /** @return the gjjy
    * 
    * @pdOid 29673c91-f9ca-439b-805d-874734756e2a */
   public String getGjjy() {
   	return gjjy;
   }
   
   /** @param gjjy the gjjy to set
    * @pdOid 0144ac2d-5ceb-4a21-abd3-d84ab501b501 */
   public void setGjjy(String gjjy) {
   	this.gjjy = gjjy;
   }

}