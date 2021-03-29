/**
 * Project Name:hse-entity-service
 * File Name:SysActionAgeConfig.java
 * Package Name:com.hd.hse.entity.common
 * Date:2014年11月5日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** ClassName: SysActionAgeConfig (时效配置表)<br/>
 * date: 2014年11月5日 <br/>
 * 
 * 
 * @author zhaofeng
 * @version
 * 
 * @pdOid d55efa61-bcea-4b0c-ab86-9f4c86f56842 */
@DBTable(tableName = "sys_action_sxpz")
public class SysActionAgeConfig extends com.hd.hse.common.entity.SuperEntity {
   /** serialVersionUID:TODO().
    * 
    * 
    * @pdOid b1d02705-5122-4848-84f4-74fd23975af7 */
   private static final long serialVersionUID = -5865526176269642172L;
   /** @pdOid 7cd893c7-5c0d-4155-b445-01a3423eb920 */
   @DBField(id = true)
   private Integer id;
   /** @pdOid d5195550-cc90-46bf-b029-d1ce7d55da8e */
   @DBField
   private String actiontype;
   /**  间隔（单位：分钟）
    * 
    * @pdOid 4cccdb83-40ed-421d-9335-debfa1b101f2 */
   @DBField
   private Float sx;
   /** @pdOid 2d51bdd7-71dc-4085-8669-cad6398ac668 */
   @DBField
   private String changedate;
   /** @pdOid 3b3d3fd6-397c-414b-b8c8-d442caf03f3f */
   @DBField
   private String description;
   /** @pdOid 7b515939-8b43-4bb4-997c-2e3604766dd0 */
   @DBField
   private Integer isactive;
   
   /** id.
    * 
    * 
    * @return the id
    * 
    * @pdOid e029b48c-1d3b-4c38-838d-f47ac0655972 */
   public Integer getId() {
   	return id;
   }
   
   /** id.
    * 
    * 
    * @param id the id to set
    * @pdOid d7e03ede-3a37-43fa-9cd7-3d93ec8feb28 */
   public void setId(Integer id) {
   	this.id = id;
   }
   
   /** actiontype.
    * 
    * 
    * @return the actiontype
    * 
    * @pdOid afa510a4-751f-4157-86f6-bfc9114f0b28 */
   public String getActiontype() {
   	return actiontype;
   }
   
   /** actiontype.
    * 
    * 
    * @param actiontype the actiontype to set
    * @pdOid 0882e4d3-a996-442b-a3ac-e3185020dc4e */
   public void setActiontype(String actiontype) {
   	this.actiontype = actiontype;
   }
   
   /** sx.
    * 
    * 
    * @return the sx
    * 
    * @pdOid 44df7736-be7c-4e8b-98a9-dac3eef548e5 */
   public Float getSx() {
   	return sx;
   }
   
   /** sx.
    * 
    * 
    * @param sx the sx to set
    * @pdOid 63e54670-14d6-44e9-a542-7bba04621b6f */
   public void setSx(Float sx) {
   	this.sx = sx;
   }
   
   /** changedate.
    * 
    * 
    * @return the changedate
    * 
    * @pdOid bdd07242-1dbf-4c35-a3e7-19a06930d49c */
   public String getChangedate() {
   	return changedate;
   }
   
   /** changedate.
    * 
    * 
    * @param changedate the changedate to set
    * @pdOid 2d4f1575-63b4-43e7-951d-1cbd3f4dfa21 */
   public void setChangedate(String changedate) {
   	this.changedate = changedate;
   }
   
   /** description.
    * 
    * 
    * @return the description
    * 
    * @pdOid 58217b06-0b04-499c-af23-966b1e2ee833 */
   public String getDescription() {
   	return description;
   }
   
   /** description.
    * 
    * 
    * @param description the description to set
    * @pdOid b4cdb08d-5849-4440-97f9-52b3627beb6d */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** isactive.
    * 
    * 
    * @return the isactive
    * 
    * @pdOid 5de2fad6-29d3-4bc8-ba31-0990a63283a3 */
   public Integer getIsactive() {
   	return isactive;
   }
   
   /** isactive.
    * 
    * 
    * @param isactive the isactive to set
    * @pdOid 8ca2829a-fac9-4e07-b54e-dfd1ee78bb64 */
   public void setIsactive(Integer isactive) {
   	this.isactive = isactive;
   }

}