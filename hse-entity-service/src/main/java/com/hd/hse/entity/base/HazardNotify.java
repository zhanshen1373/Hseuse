/**
 * File:    UdZyxkZysq2hazard.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkZysq2hazard
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;
/**
 * 危害识别
 */
/** @pdOid e361643d-0cb1-44c0-aad2-42f574d48b87 */
@DBTable(tableName = "ud_zyxk_zysq2hazard")
public class HazardNotify extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 3104a960-bd10-4bb6-a903-d3a8c7d26ad4 */
   private static final long serialVersionUID = 5689377693471548627L;
   /** @pdOid 790b63d7-8492-4226-94be-0132d17eb69b */
   @DBField(id=true)
   private String ud_zyxk_zysq2hazardid;
   /** 作业申请ID
    * 
    * @pdOid dae89522-daaa-4d8e-9f47-93dadcda5622 */
   @DBField
   private String ud_zyxk_zysqid;
   /** 主键
    * 
    * @pdOid 2c297ac1-9ae1-46a2-b64a-b931b73a8098 */
   @DBField
   private String hazardid;
   /** 描述
    * 
    * @pdOid c6bd0908-6ad3-4ccc-8ef9-6b288dc2ab4d */
   @DBField
   private String description;
   /** 是否选中
    * 
    * @pdOid b075f572-04fc-4e5c-8607-abcd1dab2446 */
   @DBField
   private Integer isselect;
   /** a1
    * 
    * @pdOid f90e6aeb-c2d6-4c51-8c7b-6e445eb24767 */
   @DBField
   private Integer paixu;
   /** 危害类型
    * 
    * @pdOid d5ba63fb-d86b-4194-a3ea-917d3f08f2e5 */
   @DBField
   private Integer whtype;
   /** 是否伪删除
    * 
    * @pdOid 00a9fd65-a9fd-4852-942f-f549c0170766 */
   @DBField
   private Integer dr;
   /** 标记
    * 
    * @pdOid 10fa6dec-3f59-4ee0-b7dd-aadf821643f4 */
   @DBField
   private Integer tag;
   /** 是否上传
    * 
    * @pdOid 765f4690-d950-4725-bd87-7045a1e1f898 */
   @DBField
   private Integer isupload;
   /** 是否pad选中
    * 
    * @pdOid 2ca5a489-df0c-4cfb-86cc-3ca5f0eb9527 */
   @DBField
   private Integer ispadselect;
   
   /** ud_zyxk_zysq2hazardid.
    * 
    * 
    * @return  the ud_zyxk_zysq2hazardid
    * 
    * @pdOid aaf0fca7-ff88-4bed-be3c-2a8c429040d6 */
   public String getUd_zyxk_zysq2hazardid() {
   	return ud_zyxk_zysq2hazardid;
   }
   
   /** ud_zyxk_zysq2hazardid.
    * 
    * 
    * @param ud_zyxk_zysq2hazardid the ud_zyxk_zysq2hazardid to set
    * @pdOid 6e80f6f9-ba20-4f9c-9a64-a8dff3655675 */
   public void setUd_zyxk_zysq2hazardid(String ud_zyxk_zysq2hazardid) {
   	this.ud_zyxk_zysq2hazardid = ud_zyxk_zysq2hazardid;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid e32ab2f5-032e-4737-bc5f-3a15eb5c8275 */
   public String getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid 8246eeac-f461-4a4a-87d2-5bac68999d9c */
   public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** 设置 主键
    * 该字段是：主键
    * 
    * @param hazardid
    * @pdOid de54a6cd-8177-43a9-b4e7-91c4e875df8d */
   public void setHazardid(String hazardid) {
   	this.hazardid = hazardid;
   }
   
   /** 获取 主键
    * 该字段是：主键
    * 
    * 
    * @pdOid f7e20c9a-d6c4-496c-a172-e3da153eb984 */
   public String getHazardid() {
   	return hazardid;
   }
   
   /** 设置 描述
    * 该字段是：描述
    * 
    * @param description
    * @pdOid 2bc38af4-d95f-45db-9a59-52c0b18e01ea */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 描述
    * 该字段是：描述
    * 
    * 
    * @pdOid 5945862b-a42e-483a-b456-385c2b3ca924 */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 是否选中
    * 该字段是：是否选中
    * 
    * @param isselect
    * @pdOid e67eb91a-587f-4867-8289-0a61d0503548 */
   public void setIsselect(Integer isselect) {
   	this.isselect = isselect;
   }
   
   /** 获取 是否选中
    * 该字段是：是否选中
    * 
    * 
    * @pdOid bcdec227-2a58-4363-bbbc-8ce63dbdec1c */
   public Integer getIsselect() {
   	return isselect;
   }
   
   /** 设置 a1
    * 该字段是：a1
    * 
    * @param paixu
    * @pdOid 92936ddb-53c6-4aee-9995-7f462c07fab1 */
   public void setPaixu(Integer paixu) {
   	this.paixu = paixu;
   }
   
   /** 获取 a1
    * 该字段是：a1
    * 
    * 
    * @pdOid cee7f4d5-6624-4e56-8351-a25f19c860d0 */
   public Integer getPaixu() {
   	return paixu;
   }
   
   /** 设置 危害类型
    * 该字段是：危害类型
    * 
    * @param whtype
    * @pdOid b95bbf14-990b-4791-93ea-49c7b10be78f */
   public void setWhtype(Integer whtype) {
   	this.whtype = whtype;
   }
   
   /** 获取 危害类型
    * 该字段是：危害类型
    * 
    * 
    * @pdOid 8907d224-50f5-4b16-815a-0569012c19d4 */
   public Integer getWhtype() {
   	return whtype;
   }
   
   /** 设置 是否伪删除
    * 该字段是：是否伪删除
    * 
    * @param dr
    * @pdOid bfc9c3cf-5637-4eea-ab30-ecb98ae58e61 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 是否伪删除
    * 该字段是：是否伪删除
    * 
    * 
    * @pdOid c28e7cf7-50d2-46a8-bb2c-7a7743292fe1 */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 标记
    * 该字段是：标记
    * 
    * @param tag
    * @pdOid 5b36e687-e9f3-4d68-a6bf-b10c3d344925 */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** 获取 标记
    * 该字段是：标记
    * 
    * 
    * @pdOid 2886d864-9736-4b95-a1ce-6f1e535a294b */
   public Integer getTag() {
   	return tag;
   }
   
   /** 设置 是否上传
    * 该字段是：是否上传
    * 
    * @param isupload
    * @pdOid c0f75523-1c92-4830-87ac-dda59c620dc9 */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** 获取 是否上传
    * 该字段是：是否上传
    * 
    * 
    * @pdOid 4a803641-bedd-4a91-a053-9205dd2ef492 */
   public Integer getIsupload() {
   	return isupload;
   }
   
   /** @pdOid 50b47ed1-c6e5-4ab1-9f2f-01c1783b27f0 */
   public Integer getIspadselect() {
   	return ispadselect;
   }
   
   /** @param ispadselect
    * @pdOid 236e3d81-2c87-4237-8774-8fb62e0146cf */
   public void setIspadselect(Integer ispadselect) {
   	this.ispadselect = ispadselect;
   }

}