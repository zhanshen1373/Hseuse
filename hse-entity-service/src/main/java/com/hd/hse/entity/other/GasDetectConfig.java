/**
 * File:    UdZyxkQtjcpz.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkQtjcpz
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 53bca543-99b7-4293-885c-64bb20608410 */
@DBTable(tableName = "ud_zyxk_qtjcpz")
public class GasDetectConfig extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid d613ac45-cad1-4613-b28a-e8b1712dba64 */
   private static final long serialVersionUID = -1093372241144829666L;
   /** 主键
    * 
    * @pdOid 11a63497-081e-474a-b0b9-11ecce515078 */
   @DBField(id=true)
   private Integer ud_zyxk_qtjcpzid;
   /** 气体名称
    * 
    * @pdOid 5ec1ff03-2b44-4b8c-83f0-4530cea2ef07 */
   @DBField
   private String qtname;
   /** 气体单位
    * 
    * @pdOid 75fcdd60-ce6a-4b51-97ed-e0500469bf7a */
   @DBField
   private String qtdw;
   /** 上限值
    * 
    * @pdOid 27441eda-052e-4342-befa-6f50ad89d555 */
   @DBField
   private Double maxvalue;
   /** 下限值
    * 
    * @pdOid 0f77508e-2154-4e4e-820b-ee47908d86d0 */
   @DBField
   private Double minvalue;
   /** 是否包含边界
    * 
    * @pdOid 9a6dfa4f-cdad-452f-a7bc-c9f6f427f7d5 */
   @DBField
   private Integer isbj;
   /** 修改时间
    * 
    * @pdOid 79cf36fe-6958-41b7-9baf-c7db3a6883be */
   @DBField
   private String changedate;
   /** 是否伪删除
    * 
    * @pdOid b856f9ba-0736-4814-9b8a-d74fdeef5e3c */
   @DBField
   private Integer dr;
   /** 气体类型
    * 
    * @pdOid b18f777a-f797-4bf5-9dcd-b7759390384c */
   @DBField
   private String qtlx;
   
   /** @return the ud_zyxk_qtjcpzid
    * 
    * @pdOid 5f98b312-a228-43a6-b647-59d5ce6cd706 */
   public Integer getUd_zyxk_qtjcpzid() {
   	return ud_zyxk_qtjcpzid;
   }
   
   /** @param ud_zyxk_qtjcpzid the ud_zyxk_qtjcpzid to set
    * @pdOid 1685b5d7-b52b-4d1d-87d2-276fc5137071 */
   public void setUd_zyxk_qtjcpzid(Integer ud_zyxk_qtjcpzid) {
   	this.ud_zyxk_qtjcpzid = ud_zyxk_qtjcpzid;
   }
   
   /** 设置 气体名称
    * 该字段是：气体名称
    * 
    * @param qtname
    * @pdOid 8e086b9e-e1f2-4bfb-b92e-ba98d32f11bb */
   public void setQtname(String qtname) {
   	this.qtname = qtname;
   }
   
   /** 获取 气体名称
    * 该字段是：气体名称
    * 
    * 
    * @pdOid d79eab5d-a5d1-4977-ac37-654e8427f8c8 */
   public String getQtname() {
   	return qtname;
   }
   
   /** 设置 气体单位
    * 该字段是：气体单位
    * 
    * @param qtdw
    * @pdOid bd2c8a3b-10e3-4540-bac9-404df629f888 */
   public void setQtdw(String qtdw) {
   	this.qtdw = qtdw;
   }
   
   /** 获取 气体单位
    * 该字段是：气体单位
    * 
    * 
    * @pdOid 5b755f3b-2749-4f5b-9061-ca3d4aa216e1 */
   public String getQtdw() {
   	return qtdw;
   }
   
   /** 设置 上限值
    * 该字段是：上限值
    * 
    * @param maxvalue
    * @pdOid c4ca284c-45ea-43ae-8b84-3b4a1a7920d2 */
   public void setMaxvalue(Double maxvalue) {
   	this.maxvalue = maxvalue;
   }
   
   /** 获取 上限值
    * 该字段是：上限值
    * 
    * 
    * @pdOid 5d947e8a-2d1a-4d7c-924d-ccfbbb9699d5 */
   public Double getMaxvalue() {
   	return maxvalue;
   }
   
   /** 设置 下限值
    * 该字段是：下限值
    * 
    * @param minvalue
    * @pdOid d3b72a48-0adc-4ddd-b2f1-ab371be53582 */
   public void setMinvalue(Double minvalue) {
   	this.minvalue = minvalue;
   }
   
   /** 获取 下限值
    * 该字段是：下限值
    * 
    * 
    * @pdOid 05c91ba4-eb8f-485a-9c3f-0eeda81ad490 */
   public Double getMinvalue() {
   	return minvalue;
   }
   
   /** 设置 是否包含边界
    * 该字段是：是否包含边界
    * 
    * @param isbj
    * @pdOid de5c9896-dd3f-4af2-81a9-536a3e799d0c */
   public void setIsbj(Integer isbj) {
   	this.isbj = isbj;
   }
   
   /** 获取 是否包含边界
    * 该字段是：是否包含边界
    * 
    * 
    * @pdOid e4a9f667-a08a-4693-a010-87d7ed353ade */
   public Integer getIsbj() {
   	return isbj;
   }
   
   /** 设置 修改时间
    * 该字段是：修改时间
    * 
    * @param changedate
    * @pdOid 0b324461-d45b-400d-a5bd-391a6f9f3c3b */
   public void setChangedate(String changedate) {
   	this.changedate = changedate;
   }
   
   /** 获取 修改时间
    * 该字段是：修改时间
    * 
    * 
    * @pdOid 96e650fe-ab70-4966-9f17-12c77cd0df9c */
   public String getChangedate() {
   	return changedate;
   }
   
   /** 设置 是否伪删除
    * 该字段是：是否伪删除
    * 
    * @param dr
    * @pdOid 2b2674ed-d4ec-47d6-841e-f20a0ed2433c */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 是否伪删除
    * 该字段是：是否伪删除
    * 
    * 
    * @pdOid a7f89d2c-fe58-4ad5-9d88-b6f386623860 */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 气体类型
    * 该字段是：气体类型
    * 
    * @param qtlx
    * @pdOid 32c36477-1014-41a6-8581-6632281eb3e9 */
   public void setQtlx(String qtlx) {
   	this.qtlx = qtlx;
   }
   
   /** 获取 气体类型
    * 该字段是：气体类型
    * 
    * 
    * @pdOid f3ec8295-dcab-4f32-9fe8-1c362e10a0b3 */
   public String getQtlx() {
   	return qtlx;
   }

}