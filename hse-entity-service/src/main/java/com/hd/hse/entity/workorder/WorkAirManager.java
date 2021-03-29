package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** TODO 作业气体管理
 * ClassName: WorkAirManager ()</br>
 * date: 2014年10月18日 </br>
 * 
 * 
 * @author wenlin
 * @version
 * 
 * @pdOid bf022ea0-b729-4359-a9b9-db05cd8b2bab */
@DBTable(tableName = "ud_zyxk_zyqtgl")
public class WorkAirManager extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid f8c688dd-fab8-43a1-89da-f12ca0308425 */
   private static final long serialVersionUID = 7235324390586389355L;
   /** 主键
    * 
    * @pdOid 35dd7840-87da-42be-b71a-47e8dcf6d453 */
   @DBField(id=true)
   private String ud_zyxk_zyqtglid;
   /**  作业类型
    * 
    * @pdOid 33109d61-d7e1-4dd0-b5d0-d71d47119729 */
   @DBField
   private String zypclass;
   /**  作业等级
    * 
    * @pdOid 2182f742-84c7-4040-bd64-ed667a6bee45 */
   @DBField
   private String zylevel;
   /**  气体类型
    * 
    * @pdOid 1825583a-9bf8-4889-b96c-e646ba5297c4 */
   @DBField
   private String qtlx;
   /**  修改日期
    * 
    * @pdOid 2efa118a-eb4e-43a9-a94f-9459f7ec34d9 */
   @DBField
   private String changedate;
   /** @pdOid b2440af5-013a-40b6-9a25-113d98888d8a */
   @DBField
   private int dr;
   /** @pdOid d4b94f8c-6f44-417f-bb3d-8f78d1100721 */
   @DBField
   private int isupload;
   /** @pdOid b31345a1-b984-477c-b2dc-db0c69739a63 */
   @DBField
   private int tag;
   
   /** @pdOid b9f75378-aee3-4ea5-93b2-bc4682d1e7fc */
   public WorkAirManager() {
   	// TODO Auto-generated constructor stub
   }
   
   /** @pdOid aa647970-0ab8-4a4f-bf78-27ef75d114f6 */
   public String getUd_zyxk_zyqtglid() {
   	return ud_zyxk_zyqtglid;
   }
   
   /** @param ud_zyxk_zyqtglid
    * @pdOid 9dd62a40-2c99-4bc8-a5f6-71b52880a458 */
   public void setUd_zyxk_zyqtglid(String ud_zyxk_zyqtglid) {
   	this.ud_zyxk_zyqtglid = ud_zyxk_zyqtglid;
   }
   
   /** @pdOid 6a57ff9d-67b3-495d-95ac-c34affa0ab5b */
   public String getZypclass() {
   	return zypclass;
   }
   
   /** @param zypclass
    * @pdOid 3b0a21de-38bf-485b-8d59-31d72adc73fd */
   public void setZypclass(String zypclass) {
   	this.zypclass = zypclass;
   }
   
   /** @pdOid a72a902f-4fec-4ba5-8558-fb512af2d6f1 */
   public String getZylevel() {
   	return zylevel;
   }
   
   /** @param zylevel
    * @pdOid f9214496-8e87-4b8c-9aea-81669e2213f6 */
   public void setZylevel(String zylevel) {
   	this.zylevel = zylevel;
   }
   
   /** @pdOid 72e9b1d8-4d63-4d60-9588-177322f95c9c */
   public String getQtlx() {
   	return qtlx;
   }
   
   /** @param qtlx
    * @pdOid 0941e923-821d-495e-b4d7-db6c82dd70ec */
   public void setQtlx(String qtlx) {
   	this.qtlx = qtlx;
   }
   
   /** @pdOid a5a99259-9a32-46da-952b-0646093e3ea6 */
   public String getChangedate() {
   	return changedate;
   }
   
   /** @param changedate
    * @pdOid 490f52f0-23a8-4f17-82d0-723d213279b6 */
   public void setChangedate(String changedate) {
   	this.changedate = changedate;
   }
   
   /** @pdOid 05b7c5f9-1ccd-4227-8928-6e0182a36044 */
   public int getDr() {
   	return dr;
   }
   
   /** @param dr
    * @pdOid f6e234cb-a110-4225-87d7-9d2ec11cc1a3 */
   public void setDr(int dr) {
   	this.dr = dr;
   }
   
   /** @pdOid 6c440401-4505-486a-bd21-2746d23a9cee */
   public int getIsupload() {
   	return isupload;
   }
   
   /** @param isupload
    * @pdOid a0b25d52-6f05-4df0-9cdd-878f4dd38a93 */
   public void setIsupload(int isupload) {
   	this.isupload = isupload;
   }
   
   /** @pdOid 3802a641-7b77-4bd2-9daf-af539a99288a */
   public int getTag() {
   	return tag;
   }
   
   /** @param tag
    * @pdOid 88ec94b4-8ba6-4cc9-b957-6f3c01399ab5 */
   public void setTag(int tag) {
   	this.tag = tag;
   }

}