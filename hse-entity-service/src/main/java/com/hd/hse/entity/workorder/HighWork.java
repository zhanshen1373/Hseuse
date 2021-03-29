/**
 * File:    UdZyxkGczy.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkGczy
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 129170de-23ac-4841-b3f6-ac5158e7b31d */
@DBTable(tableName = "ud_zyxk_gczy")
public class HighWork extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 489e5bb2-0b4e-46fe-bd88-3acd216826cd */
   private static final long serialVersionUID = 1848661831408425842L;
   /** 唯一标识
    * 
    * @pdOid cdaf4249-cbb0-4ace-96e6-00ebf7ed88f6 */
   @DBField(id=true)
   private Integer ud_zyxk_gczyid;
   /** 外键唯一标识
    * 
    * @pdOid 7289e6a7-f141-420c-8aa9-26b1ca0dce3d */
   @DBField(foreign=true)
   private Integer ud_zyxk_zysqid;
   /** 是否附救援预案
    * 
    * @pdOid 75a813f3-4130-454c-a26c-c5c22fc1754a */
   @DBField
   private Integer isfjyfa;
   /** 关闭监护人
    * 
    * @pdOid 49f8482c-f1a1-4e3b-869e-0a08e2f474f4 */
   @DBField
   private String gbjhperson;
   /** 日期
    * 
    * @pdOid 5b3ee87d-d4d6-447a-84ea-c6f97760d686 */
   @DBField
   private String gbdate;
   
   /** @return the ud_zyxk_gczyid
    * 
    * @pdOid 493332f7-901a-4da4-b7bb-f38cfcd8ce19 */
   public Integer getUd_zyxk_gczyid() {
   	return ud_zyxk_gczyid;
   }
   
   /** @param ud_zyxk_gczyid the ud_zyxk_gczyid to set
    * @pdOid d1f7d810-3e23-4c0f-80b7-b55ee9ff873e */
   public void setUd_zyxk_gczyid(Integer ud_zyxk_gczyid) {
   	this.ud_zyxk_gczyid = ud_zyxk_gczyid;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid 6aaa07e9-0a13-4751-bf29-e91108fa34d4 */
   public Integer getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid 5a443d34-d31f-49b2-964b-62cf4bdf7246 */
   public void setUd_zyxk_zysqid(Integer ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** @return the isfjyfa
    * 
    * @pdOid 3c64e139-80ef-4778-ac99-56067e3e5ccc */
   public Integer getIsfjyfa() {
   	return isfjyfa;
   }
   
   /** @param isfjyfa the isfjyfa to set
    * @pdOid e49dfb32-3db5-4e4a-836f-b4c3ca84c46b */
   public void setIsfjyfa(Integer isfjyfa) {
   	this.isfjyfa = isfjyfa;
   }
   
   /** @return the gbjhperson
    * 
    * @pdOid 57c71bf2-811d-4613-b948-aadd9ea48266 */
   public String getGbjhperson() {
   	return gbjhperson;
   }
   
   /** @param gbjhperson the gbjhperson to set
    * @pdOid cbfc0482-015c-4a24-98cb-fe47134923c0 */
   public void setGbjhperson(String gbjhperson) {
   	this.gbjhperson = gbjhperson;
   }
   
   /** @return the gbdate
    * 
    * @pdOid a31e31cf-1d6a-4c18-bbb0-92db0e1abfe0 */
   public String getGbdate() {
   	return gbdate;
   }
   
   /** @param gbdate the gbdate to set
    * @pdOid 865930cd-2a24-403d-aa1f-9d4cb21bdaae */
   public void setGbdate(String gbdate) {
   	this.gbdate = gbdate;
   }

}