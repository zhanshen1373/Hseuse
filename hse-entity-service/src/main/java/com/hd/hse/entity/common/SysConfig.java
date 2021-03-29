/**
 * Project Name:hse-cbs-app
 * File Name:SysConfig.java
 * Package Name:com.hd.hse.common.entity
 * Date:2014年9月5日
 * Copyright (c) 2014, zhulei@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** ClassName: SysConfig ()<br/>
 * date: 2014年9月5日  <br/>
 * 
 * 
 * @author HD
 * @version
 * 
 * @pdOid b160b6a3-1fae-40c0-ab2f-456689864a87 */
@DBTable(tableName = "hse_sys_config")
public class SysConfig extends com.hd.hse.common.entity.SuperEntity {
   /** serialVersionUID:TODO().
    * 
    * 
    * @pdOid add9d259-ad4c-4809-a49d-aeace5f19085 */
   private static final long serialVersionUID = -6144770712947025362L;
   /** 主键
    * 
    * @pdOid bccc96b1-6666-41bd-9e54-3b4e055f56b3 */
   @DBField(id=true)
   private String syscode;
   /** @pdOid e957bbb9-9f18-42fe-b5bd-39f9603b5f8a */
   @DBField
   private String sysdesc;
   /** @pdOid c30c9469-115b-4c70-9095-34af8e994edd */
   @DBField
   private String sysurl;
   /** @pdOid f1d988c9-66bf-48ed-a7bf-0feec007f3b6 */
   @DBField
   private Integer enable;
   
   /** @pdOid 910c69fe-3d99-44c8-8e96-0a7e7a598698 */
   public Integer getEnable() {
   	return enable;
   }
   
   /** @param enable
    * @pdOid a25c41ce-2ec0-450f-a5be-835aaa5a5254 */
   public void setEnable(Integer enable) {
   	this.enable = enable;
   }
   
   /** @pdOid eb848530-7e31-409b-9f52-8a4e11ff7d44 */
   public String getSyscode() {
   	return syscode;
   }
   
   /** @param syscode
    * @pdOid 4e07bda5-494d-4d22-b85e-9b6be48209ec */
   public void setSyscode(String syscode) {
   	this.syscode = syscode;
   }
   
   /** @pdOid e4c03bdc-f76c-4ae3-806f-2a510c5b6a8f */
   public String getSysdesc() {
   	return sysdesc;
   }
   
   /** @param sysdesc
    * @pdOid e62c7158-9729-4a20-9e85-c55319cb1bc3 */
   public void setSysdesc(String sysdesc) {
   	this.sysdesc = sysdesc;
   }
   
   /** @pdOid c7b94c54-ab44-4e0f-8a79-0ce8e3ac47e4 */
   public String getSysurl() {
   	return sysurl;
   }
   
   /** @param sysurl
    * @pdOid 4eedb22c-fec7-49e7-a985-551589aa43d1 */
   public void setSysurl(String sysurl) {
   	this.sysurl = sysurl;
   }

}