package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** TODO 系统配置表，更新子表
 * ClassName: SystemConfigSubLine ()<br/>
 * date: 2014年11月25日  <br/>
 * 
 * 
 * @author wenlin
 * @version
 * 
 * @pdOid 261c3abc-651d-4336-82cd-6c686e9f6151 */
@DBTable(tableName = "hse_sys_record_ts_sub")
public class SystemConfigSubLine extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 1f2ca425-130a-4b68-a144-b262ef962f77 */
   private static final long serialVersionUID = -6093621043208860538L;
   /** 主键
    * 
    * @pdOid cdf79450-6599-4f3a-8781-f0b1f69e7e11 */
   @DBField(id=true)
   private Integer sys_subid;
   /** 表名
    * 
    * @pdOid 75c8067a-7b92-4600-ab8f-b356bc8f4fe2 */
   @DBField
   private String sys_tablename;
   /** 部门编码
    * 
    * @pdOid 03c40697-2afd-49b1-baa0-b28529cd2a5b */
   @DBField
   private String sys_dept;
   /**   时间戳
    * 
    * @pdOid 0cd9b196-f53e-454b-a760-6db14e0b1871 */
   @DBField
   private String sys_ts;
   /** 删除
    * 
    * @pdOid ee0b6db5-5542-49f8-9475-3a7c34928fcf */
   @DBField
   private String dr;
   /** 标记
    * 
    * @pdOid dec8611a-f05b-405b-97f1-437ac1c38a93 */
   @DBField
   private String tag;
   
   /** @pdOid 9cf32a1e-ef2b-4b8b-b824-f30635ff4df1 */
   public SystemConfigSubLine() {
   	// TODO Auto-generated constructor stub
   }
   
   /** @pdOid d218d767-c2d7-47d3-b7b9-2262159df7b5 */
   public Integer getSys_subid() {
   	return sys_subid;
   }
   
   /** @param sys_subid
    * @pdOid 48aa2b45-0d6e-4b57-9051-f0d8dbd1669e */
   public void setSys_subid(Integer sys_subid) {
   	this.sys_subid = sys_subid;
   }
   
   /** @pdOid 1716faee-e352-48d9-8127-b009d736ba45 */
   public String getSys_tablename() {
   	return sys_tablename;
   }
   
   /** @param sys_tablename
    * @pdOid 9f3b3782-5a42-4d6b-afea-cc2d92e595a6 */
   public void setSys_tablename(String sys_tablename) {
   	this.sys_tablename = sys_tablename;
   }
   
   /** @pdOid ea83d340-62b7-481b-94b4-91fe9b499c55 */
   public String getSys_dept() {
   	return sys_dept;
   }
   
   /** @param sys_dept
    * @pdOid 25b7963a-8449-4e66-ac63-75c908720817 */
   public void setSys_dept(String sys_dept) {
   	this.sys_dept = sys_dept;
   }
   
   /** @pdOid 75a4db9a-1d94-408b-9425-9822457923f6 */
   public String getSys_ts() {
   	return sys_ts;
   }
   
   /** @param sys_ts
    * @pdOid 9646ca3a-55a7-4887-b0e1-5d2bde6ce2a2 */
   public void setSys_ts(String sys_ts) {
   	this.sys_ts = sys_ts;
   }
   
   /** @pdOid baf56d96-5dd3-4f58-ade6-c70f97999dd3 */
   public String getDr() {
   	return dr;
   }
   
   /** @param dr
    * @pdOid c8b0b696-0f80-4e8a-a885-67e4a1f952cd */
   public void setDr(String dr) {
   	this.dr = dr;
   }
   
   /** @pdOid c7d76d9d-9eb9-4048-8e30-6d491d23f121 */
   public String getTag() {
   	return tag;
   }
   
   /** @param tag
    * @pdOid 040737a9-91e0-4c72-9e26-39dd8df8c7a1 */
   public void setTag(String tag) {
   	this.tag = tag;
   }

}