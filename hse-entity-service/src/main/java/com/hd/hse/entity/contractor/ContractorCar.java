/**
 * Project Name:hse-cbs-app
 * File Name:ContractorCar.java
 * Package Name:com.hd.hse.cbs.pub.car
 * Date:2014年9月12日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.entity.contractor;

import java.lang.annotation.*;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** ClassName:ContractorCar (承包商车辆).<br/>
 * Date: 2014年9月12日 <br/>
 * 
 * 
 * @author lg
 * @version
 * @see
 * 
 * @pdOid 51d97c9c-29f9-484c-b601-a1551aa1874e */
@DBTable(tableName = "ud_cbsgl_cl")
public class ContractorCar extends com.hd.hse.common.entity.SuperEntity {
   /** serialVersionUID:TODO().
    * 
    * 
    * @pdOid 4d897bb5-f3b3-4bbf-aa29-f1720c7029f9 */
   private static final long serialVersionUID = 6922073496647880451L;
   /**  主键
    * 
    * @pdOid e56b77c0-1dae-4463-8068-32133028a632 */
   @DBField
   private String ud_cbsgl_clid;
   /**  车牌号
    * 
    * @pdOid 12edda63-ac0c-4bf6-bb01-b1084744783e */
   @DBField(id = true)
   private String carnum;
   /**  档案编码
    * 
    * @pdOid 3ec45603-5ffa-4ded-ab71-9dbe34eb2e9b */
   @DBField
   private String docnum;
   /**  车辆名称
    * 
    * @pdOid ee93ab1e-0907-4a31-8342-d1aa50adc5a9 */
   @DBField
   private String carname;
   /**  校定载客
    * 
    * @pdOid dac1a9ca-6928-4e7c-b76a-5c7e05bc5218 */
   @DBField
   private String hdzkcount;
   /**  厂牌
    * 
    * @pdOid 19ec408c-06c6-4c19-9d1e-97de6ea3ff01 */
   @DBField
   private String cpnum;
   /**  危险车辆类型
    * 
    * @pdOid c650c1ca-3e13-4e30-9d7e-3f0a1af5af1e */
   @DBField
   private String dangertype;
   /**  车辆类型
    * 
    * @pdOid a128fba5-0ba3-47d8-97d5-cee1b247c1b5 */
   @DBField
   private String cartype;
   /**  主要用途
    * 
    * @pdOid e408100b-473f-4fad-b657-44e37ac8a5ee */
   @DBField
   private String mainuse;
   /**  型号
    * 
    * @pdOid 0233a86d-eeb7-4288-b385-f7b13b1ef37c */
   @DBField
   private String model;
   /**  年检到期日期
    * 
    * @pdOid f2de7349-76d3-4aa0-a432-d5fc46ac5453 */
   @DBField
   private String checkdate;
   /**  承包商
    * 
    * @pdOid e2c540ad-86ba-453b-877f-86d59d6cc771 */
   @DBField
   private String cbsdjnum;
   /**  时间戳
    * 
    * @pdOid dae51afe-cbec-4913-8e9c-44c6b043744d */
   @DBField
   private String changedate;
   /**  标记
    * 
    * @pdOid db434b2f-483b-447a-9cac-f48d27a211b5 */
   @DBField
   private Integer tag;
   /**  删除
    * 
    * @pdOid 59d090f2-e8cb-43b6-9345-bdcc9b5f2f18 */
   @DBField
   private Integer dr;
   /**  是否黑名单
    * 
    * @pdOid 1487773c-ede3-49b3-987c-6372b30ed276 */
   @DBField
   private Integer ishmd;
   /**  门禁卡号
    * 
    * @pdOid 08224b5e-9c74-4af8-ae83-51e4ab1a4172 */
   @DBField
   private String mjknum;
   /**  进厂事由
    * 
    * @pdOid 440cea08-91dd-4161-acc0-c30a96fe74ee */
   @DBField
   private String jcsy;
   /**  引入单位
    * 
    * @pdOid 3d2fcf71-f457-4e7f-a434-56b08c1d532f */
   @DBField
   private String zgdept_desc;
   /**  入厂证号
    * 
    * @pdOid dafb17f2-2fda-407d-a990-cdf9ca0961f2 */
   @DBField
   private String rcznum;
   
   /** @pdOid b30dd85c-4b1a-4a60-8290-b322323a775d */
   public String getUd_cbsgl_clid() {
   	return ud_cbsgl_clid;
   }
   
   /** @param ud_cbsgl_clid
    * @pdOid 5f2bffeb-e6ec-4237-b51b-59ae6689103e */
   public void setUd_cbsgl_clid(String ud_cbsgl_clid) {
   	this.ud_cbsgl_clid = ud_cbsgl_clid;
   }
   
   /** @pdOid 2fec052e-f736-4dc2-8493-d89c378b8b59 */
   public String getCarnum() {
   	return carnum;
   }
   
   /** @param carnum
    * @pdOid 646813e8-c9de-41f8-9826-2b307b0115e8 */
   public void setCarnum(String carnum) {
   	this.carnum = carnum;
   }
   
   /** @pdOid c18d090d-1a04-4c68-8ee2-a518a21c8df8 */
   public String getDocnum() {
   	return docnum;
   }
   
   /** @param docnum
    * @pdOid fbdbadc6-a9fd-48e3-8e55-6549eb3daea1 */
   public void setDocnum(String docnum) {
   	this.docnum = docnum;
   }
   
   /** @pdOid 4ad73fe8-b43b-4116-af41-432cbb3c95f6 */
   public String getCarname() {
   	return carname;
   }
   
   /** @param carname
    * @pdOid 8d63bebd-0378-4bec-a501-8d3c718a34fe */
   public void setCarname(String carname) {
   	this.carname = carname;
   }
   
   /** @pdOid db681c19-c848-4ed1-9ae9-219d84ea2634 */
   public String getHdzkcount() {
   	return hdzkcount;
   }
   
   /** @param hdzkcount
    * @pdOid c4289562-0ed6-4351-9f5f-2cec2d64df4d */
   public void setHdzkcount(String hdzkcount) {
   	this.hdzkcount = hdzkcount;
   }
   
   /** @pdOid 3499c267-b17f-4942-a171-bfd7838e3d29 */
   public String getCpnum() {
   	return cpnum;
   }
   
   /** @param cpnum
    * @pdOid 8cb438e1-74be-41cb-8052-d31a729f5884 */
   public void setCpnum(String cpnum) {
   	this.cpnum = cpnum;
   }
   
   /** @pdOid 6c85bfaf-b8ec-4012-87db-b900aa0374b6 */
   public String getDangertype() {
   	return dangertype;
   }
   
   /** @param dangertype
    * @pdOid 95962401-f498-48a1-b44a-81153d80cdce */
   public void setDangertype(String dangertype) {
   	this.dangertype = dangertype;
   }
   
   /** @pdOid f4313071-ef5f-484e-8c76-49bd675d976c */
   public String getCartype() {
   	return cartype;
   }
   
   /** @param cartype
    * @pdOid 062a3a80-9e5d-4da8-9b4a-96420f20fc57 */
   public void setCartype(String cartype) {
   	this.cartype = cartype;
   }
   
   /** @pdOid cd14390d-0029-493e-bc7b-b571f699c6d5 */
   public String getMainuse() {
   	return mainuse;
   }
   
   /** @param mainuse
    * @pdOid 26d351c9-3ba5-41e7-8108-5b6a5063cea2 */
   public void setMainuse(String mainuse) {
   	this.mainuse = mainuse;
   }
   
   /** @pdOid 841ef6b5-a9a9-469b-986f-1545670adf6e */
   public String getModel() {
   	return model;
   }
   
   /** @param model
    * @pdOid 5ed83dff-df04-40bd-af65-86a23eb4942a */
   public void setModel(String model) {
   	this.model = model;
   }
   
   /** @pdOid f55a11aa-a043-402a-841d-57e9fab01b93 */
   public String getCheckdate() {
   	return checkdate;
   }
   
   /** @param checkdate
    * @pdOid 6965cb1d-59ec-4fa8-a0b6-a511a5e3c275 */
   public void setCheckdate(String checkdate) {
   	this.checkdate = checkdate;
   }
   
   /** @pdOid f4da5279-2e50-43da-ac20-2c9fb519ef7a */
   public String getCbsdjnum() {
   	return cbsdjnum;
   }
   
   /** @param cbsdjnum
    * @pdOid 2cd05098-b0f1-4da7-8500-2a6d5556271a */
   public void setCbsdjnum(String cbsdjnum) {
   	this.cbsdjnum = cbsdjnum;
   }
   
   /** @pdOid 0b591342-3871-459d-adcf-41cbec7548cf */
   public String getChangedate() {
   	return changedate;
   }
   
   /** @param changedate
    * @pdOid 9b5915e8-56f6-480d-b72b-906b87eb65f2 */
   public void setChangedate(String changedate) {
   	this.changedate = changedate;
   }
   
   /** @pdOid 71ab8e98-2d3d-4020-81aa-4b25dd082cc4 */
   public Integer getTag() {
   	return tag;
   }
   
   /** @param tag
    * @pdOid e929dbd5-f986-4e60-829e-5061066ba8b9 */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** @pdOid b549a0af-6ca9-4bb1-8cea-48fc28cf31f7 */
   public Integer getDr() {
   	return dr;
   }
   
   /** @param dr
    * @pdOid b7ef000d-7aea-417e-8c34-9e42b74dae11 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** @pdOid 3300e0c7-071b-4db3-8be1-d2463becc3d1 */
   public Integer getIshmd() {
   	return ishmd;
   }
   
   /** @param ishmd
    * @pdOid d76aa795-8a23-4364-a1ef-b37508ac0aac */
   public void setIshmd(Integer ishmd) {
   	this.ishmd = ishmd;
   }
   
   /** @pdOid 0fa3400e-5699-40bc-9186-b7ab4ed489af */
   public static long getSerialversionuid() {
   	return serialVersionUID;
   }
   
   /** TODO 驾驶员
    * 
    * @see com.hd.hse.common.entity.SuperEntity#getChildClasses()
    * 
    * @pdOid 6d61f5c5-6af1-49ca-8e57-178f11527c2e */
   @Override
   public String[] getChildClasses() {
   	// TODO Auto-generated method stub
   	return new String[]{ContractorCarDriver.class.getName()};
   }
   
   /** @pdOid 8e845d84-f5c2-4ea5-8ce8-01bed4be15bf */
   public String getMjknum() {
   	return mjknum;
   }
   
   /** @param mjknum
    * @pdOid 1bced58a-8770-459e-92ec-5ce9018947d7 */
   public void setMjknum(String mjknum) {
   	this.mjknum = mjknum;
   }
   
   /** @pdOid f50a25aa-7d4f-4cd4-9054-ab4f8cd4977f */
   public String getJcsy() {
   	return jcsy;
   }
   
   /** @param jcsy
    * @pdOid 6853b167-398b-4477-a7ca-7d361754a38b */
   public void setJcsy(String jcsy) {
   	this.jcsy = jcsy;
   }
   
   /** @pdOid 1df4325d-c5ae-44f3-ba93-9c0b0ac1d71e */
   public String getZgdept_desc() {
   	return zgdept_desc;
   }
   
   /** @param zgdept_desc
    * @pdOid 68506a83-0f71-4ca8-8e85-f3f12f33a687 */
   public void setZgdept_desc(String zgdept_desc) {
   	this.zgdept_desc = zgdept_desc;
   }
   
   /** @pdOid dcfd1c20-7603-4b07-bf1f-a955e6d7c366 */
   public String getRcznum() {
   	return rcznum;
   }
   
   /** @param rcznum
    * @pdOid ea52367f-f772-4db0-8076-41c96c89bbb1 */
   public void setRcznum(String rcznum) {
   	this.rcznum = rcznum;
   }

}