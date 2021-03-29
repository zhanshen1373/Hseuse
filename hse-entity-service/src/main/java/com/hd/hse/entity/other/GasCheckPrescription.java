package com.hd.hse.entity.other;

import android.R.integer;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** 气体检测时效
 * ClassName: GasCheckPrescription ()<br/>
 * date: 2014年11月5日  <br/>
 * 
 * 
 * @author wenlin
 * @version
 * 
 * @pdOid 159efab5-f8cb-420f-ad81-82e78d755029 */
@DBTable(tableName="ud_zyxk_qtjcsx")
public class GasCheckPrescription extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 6716d9af-29e2-457d-b374-b6b1e3895fca */
   private static final long serialVersionUID = -8635179581583451122L;
   /** 主键
    * 
    * @pdOid 7f3c4cf5-c655-4fdc-b080-008ae266285d */
   @DBField(id=true)
   private String ud_zyxk_qtjcsxid;
   /** 票类型
    * 
    * @pdOid c4dab13a-f14a-4563-bfea-95cb38b10350 */
   @DBField
   private String wttype;
   /** 票类型描述
    * 
    * @pdOid 1b729113-193b-4739-a83a-45778bca14fa */
   @DBField
   private String zypclassdesc;
   /** 票等级
    * 
    * @pdOid 314da399-56b4-4901-ae80-5cf4b2eeffcf */
   @DBField
   private String wtlevel;
   /** 时效
    * 
    * @pdOid b9ad2911-e452-4a24-999d-70568b353ffb */
   @DBField
   private integer sx;
   /** 时间戳
    * 
    * @pdOid 8b91413f-cddd-498c-99e0-140c9334a909 */
   @DBField
   private String changedate;
   /** 时间戳
    * 
    * @pdOid fefcb88d-5414-487e-a56b-ad3ebe308c61 */
   @DBField
   private integer dr;
   /** 时间戳
    * 
    * @pdOid d3bb0834-a75a-44ed-9cd4-55ef5cb532ae */
   @DBField
   private integer tag;
   
   /** @pdOid 4d2b57f8-6240-421e-9370-8bdaaadbf3ac */
   public String getUd_zyxk_qtjcsxid() {
   	return ud_zyxk_qtjcsxid;
   }
   
   /** @param ud_zyxk_qtjcsxid
    * @pdOid 68c9383e-6fbf-4b19-a6f2-d5241f4acc65 */
   public void setUd_zyxk_qtjcsxid(String ud_zyxk_qtjcsxid) {
   	this.ud_zyxk_qtjcsxid = ud_zyxk_qtjcsxid;
   }
   
   /** @pdOid 1ca78645-d025-4bbb-815a-e95a0b4122c9 */
   public String getWttype() {
   	return wttype;
   }
   
   /** @param wttype
    * @pdOid 63fc7313-de82-4464-b398-a214c14a3581 */
   public void setWttype(String wttype) {
   	this.wttype = wttype;
   }
   
   /** @pdOid b1bee259-6cb3-415a-9b0b-ff7a621ab4d6 */
   public String getZypclassdesc() {
   	return zypclassdesc;
   }
   
   /** @param zypclassdesc
    * @pdOid 8e321de5-69d1-4fba-8126-1d3430240cfd */
   public void setZypclassdesc(String zypclassdesc) {
   	this.zypclassdesc = zypclassdesc;
   }
   
   /** @pdOid 73c17f32-7014-4648-8e2d-55bee87ca3e7 */
   public String getWtlevel() {
   	return wtlevel;
   }
   
   /** @param wtlevel
    * @pdOid 6f371b83-840a-4226-8bf0-984a392f7ef7 */
   public void setWtlevel(String wtlevel) {
   	this.wtlevel = wtlevel;
   }
   
   /** @pdOid 9c0b39ba-fa65-41c6-afdb-75acf69a48f3 */
   public integer getSx() {
   	return sx;
   }
   
   /** @param sx
    * @pdOid 0bd27678-81fb-4e27-ba3f-4a7a731b6cff */
   public void setSx(integer sx) {
   	this.sx = sx;
   }
   
   /** @pdOid 7a639f70-4a08-4e5f-b3be-382f8cc97e89 */
   public String getChangedate() {
   	return changedate;
   }
   
   /** @param changedate
    * @pdOid 11fe3305-4122-408e-9631-52e4e7fe69d0 */
   public void setChangedate(String changedate) {
   	this.changedate = changedate;
   }
   
   /** @pdOid 2ddb335f-ac39-40cb-ba2b-2846ddac3c3c */
   public integer getDr() {
   	return dr;
   }
   
   /** @param dr
    * @pdOid 923b9bb0-f08e-4b92-a3af-c727b9385997 */
   public void setDr(integer dr) {
   	this.dr = dr;
   }
   
   /** @pdOid eff50e12-a1c0-4ef2-bc84-3beb94a94a8d */
   public integer getTag() {
   	return tag;
   }
   
   /** @param tag
    * @pdOid a988525e-f0f6-42ce-a34d-b4f8e753f8f7 */
   public void setTag(integer tag) {
   	this.tag = tag;
   }

}