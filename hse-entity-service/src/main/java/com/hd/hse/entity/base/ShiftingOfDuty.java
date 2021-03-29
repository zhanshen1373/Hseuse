/**
 * File:    UdZyxkJiaob.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkJiaob
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 0c431eee-7213-4cbe-9e3f-9c6a175acc51 */
@DBTable(tableName = "ud_zyxk_jiaob")
public class ShiftingOfDuty extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 7c6cdc43-34d4-42dd-89c3-9052cc7a5f07 */
   private static final long serialVersionUID = -526928946215714340L;
   /** ID号
    * 
    * @pdOid cffa1f41-f88e-42f5-83bc-74211a92f46e */
   @DBField(id=true)
   private Integer ud_zyxk_jiaobid;
   /** 作业许可证外键ID
    * 
    * @pdOid 2807ebae-0527-499c-9e45-1a12a3be5367 */
   @DBField(foreign=true)
   private Integer ud_zyxk_zysqid;
   /** 交接班时间
    * 
    * @pdOid 8202f0b8-8dd2-4297-b580-124ae6360fc7 */
   @DBField
   private String jiaobdate;
   /** 原监护人
    * 
    * @pdOid dbe1c3d4-45ac-4780-b5fd-94d9e4ad5d90 */
   @DBField
   private String yjhperson;
   /** 接班监护人
    * 
    * @pdOid efc33261-f3f3-41ac-9580-f7a69714eb7f */
   @DBField
   private String jiaobjhperson;
   /** 备注
    * 
    * @pdOid ddf76841-2093-401f-9ef2-693a80eeb518 */
   @DBField
   private String note;
   /** a7
    * 
    * @pdOid b023ef9b-0639-4f13-817e-36e27105d4a4 */
   @DBField
   private Integer isupload;
   
   /** @return the ud_zyxk_jiaobid
    * 
    * @pdOid c9ecd8d4-23d8-4b54-ada3-b9c9b761b187 */
   public Integer getUd_zyxk_jiaobid() {
   	return ud_zyxk_jiaobid;
   }
   
   /** @param ud_zyxk_jiaobid the ud_zyxk_jiaobid to set
    * @pdOid a0d12e80-8e3b-41fb-bc60-ceca434a17b5 */
   public void setUd_zyxk_jiaobid(Integer ud_zyxk_jiaobid) {
   	this.ud_zyxk_jiaobid = ud_zyxk_jiaobid;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid f93c3d5f-58b2-4f76-82db-d7a322dbfd9c */
   public Integer getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid 63eb42a1-ec34-46c2-92ba-346b341feb91 */
   public void setUd_zyxk_zysqid(Integer ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** 设置 交接班时间
    * 该字段是：交接班时间
    * 
    * @param jiaobdate
    * @pdOid fdc0e94b-ad18-41c4-aea1-133c590d17bd */
   public void setJiaobdate(String jiaobdate) {
   	this.jiaobdate = jiaobdate;
   }
   
   /** 获取 交接班时间
    * 该字段是：交接班时间
    * 
    * 
    * @pdOid b598ec3c-2500-4111-bd55-ba0cef3ed29f */
   public String getJiaobdate() {
   	return jiaobdate;
   }
   
   /** 设置 原监护人
    * 该字段是：原监护人
    * 
    * @param yjhperson
    * @pdOid effcd75a-88f9-446c-b9a2-ec66f25d66a3 */
   public void setYjhperson(String yjhperson) {
   	this.yjhperson = yjhperson;
   }
   
   /** 获取 原监护人
    * 该字段是：原监护人
    * 
    * 
    * @pdOid da6275fe-a0d5-4e60-ad39-d2f10bfa6c4f */
   public String getYjhperson() {
   	return yjhperson;
   }
   
   /** 设置 接班监护人
    * 该字段是：接班监护人
    * 
    * @param jiaobjhperson
    * @pdOid b8a50d08-f26a-4454-8c4e-b4100925204c */
   public void setJiaobjhperson(String jiaobjhperson) {
   	this.jiaobjhperson = jiaobjhperson;
   }
   
   /** 获取 接班监护人
    * 该字段是：接班监护人
    * 
    * 
    * @pdOid f72f65af-a832-4121-b2fa-deb17e39405b */
   public String getJiaobjhperson() {
   	return jiaobjhperson;
   }
   
   /** 设置 备注
    * 该字段是：备注
    * 
    * @param note
    * @pdOid 1c932667-b798-46bb-839e-0ec4f939b57d */
   public void setNote(String note) {
   	this.note = note;
   }
   
   /** 获取 备注
    * 该字段是：备注
    * 
    * 
    * @pdOid 74146555-bc5b-45c8-830b-8169ae1a830d */
   public String getNote() {
   	return note;
   }
   
   /** 设置 a7
    * 该字段是：a7
    * 
    * @param isupload
    * @pdOid 49f88450-56cd-4e95-9ed2-2e3f75acdb55 */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** 获取 a7
    * 该字段是：a7
    * 
    * 
    * @pdOid 39d2866b-f24b-42bf-8dae-b739153bd381 */
   public Integer getIsupload() {
   	return isupload;
   }

}