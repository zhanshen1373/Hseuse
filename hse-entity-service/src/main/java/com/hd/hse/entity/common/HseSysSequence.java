/**
 * File:    HseSysSequence.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-25 11:58:43
 * Purpose: 定义数据类 HseSysSequence
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 926562e6-ec90-46ad-b07f-e4b3084c687a */
@DBTable(tableName = "hse_sys_sequence")
public class HseSysSequence extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid ba6b95d3-3a73-4936-8d7e-fb0a33aac58f */
   private static final long serialVersionUID = -1196008290112281895L;
   /** seed
    * 
    * @pdOid ac70296a-f331-4f68-b306-59d2f0023091 */
   @DBField
   private String seed;
   
   /** 设置 seed
    * 该字段是：seed
    * 
    * @param seed
    * @pdOid eae13269-537d-4714-87b0-c8ad9ca93e1f */
   public void setSeed(String seed) {
   	this.seed = seed;
   }
   
   /** 获取 seed
    * 该字段是：seed
    * 
    * 
    * @pdOid a7a3b24d-1aa4-4fc7-8b81-751585ab0d37 */
   public String getSeed() {
   	return seed;
   }

}