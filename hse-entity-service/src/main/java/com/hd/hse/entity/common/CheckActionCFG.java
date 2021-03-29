package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** ClassName: CheckActionCFG ()</br>
 * date: 2014年10月22日 </br>
 * 
 * 
 * @author wenlin
 * @version
 * 
 * @pdOid 12c5539b-c259-40a8-8810-3e4548bec123 */
@DBTable(tableName = "checkactioncfg")
public class CheckActionCFG extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 0f466bee-84b6-4965-b13f-22fdc39b352e */
   private static final long serialVersionUID = -393548000386425556L;
   /** checkactioncfgid:TODO(主键).
    * 
    * 
    * @pdOid 1ec23afd-8927-4e7f-bed7-b05da9e299db */
   @DBField(id=true)
   private String checkactioncfgid;
   /** checkactioncfgid:TODO(功能点).
    * 
    * 
    * @pdOid a0fe2ac9-ce63-4a86-ae7d-e39cce0e022b */
   @DBField
   private String actiontype;
   /** checkactioncfgid:TODO(功能点描述).
    * 
    * 
    * @pdOid 7adde6f3-c005-46b7-80dd-de347b58d0de */
   @DBField
   private String actiontypedesc;
   /** checkactioncfgid:TODO(注册的类名).
    * 
    * 
    * @pdOid 89d33c68-5475-485d-852a-89298c78fbb6 */
   @DBField
   private String classname;
   /** checkactioncfgid:TODO(注册的类名描述).
    * 
    * 
    * @pdOid 7d8b653c-9d56-484a-8e6a-3f7717edecd0 */
   @DBField
   private String classnamedesc;
   /** checkactioncfgid:TODO(排序).
    * 
    * 
    * @pdOid d2619d7e-4656-4e3f-9bf8-a07139338fb2 */
   @DBField
   private String px;
   
   /** @pdOid 837efacd-8c07-46b1-b419-8b3950155894 */
   public CheckActionCFG() {
   	// TODO Auto-generated constructor stub
   }
   
   /** @pdOid 75af3415-7f4c-493d-a7fb-7eb9e3612a07 */
   public String getCheckactioncfgid() {
   	return checkactioncfgid;
   }
   
   /** @param checkactioncfgid
    * @pdOid 9fbb8655-7536-4f66-9d43-bef39c345472 */
   public void setCheckactioncfgid(String checkactioncfgid) {
   	this.checkactioncfgid = checkactioncfgid;
   }
   
   /** @pdOid efa136b4-872a-4b11-ac88-1769ac4ca63f */
   public String getActiontype() {
   	return actiontype;
   }
   
   /** @param actiontype
    * @pdOid 1e19208f-070f-41c5-90e4-1ffa910dc9c1 */
   public void setActiontype(String actiontype) {
   	this.actiontype = actiontype;
   }
   
   /** @pdOid 8061fca2-330e-43ed-8185-3d71dc46143a */
   public String getActiontypedesc() {
   	return actiontypedesc;
   }
   
   /** @param actiontypedesc
    * @pdOid 408d3a35-fb15-4ae7-a590-88ab1946652d */
   public void setActiontypedesc(String actiontypedesc) {
   	this.actiontypedesc = actiontypedesc;
   }
   
   /** @pdOid 4c7af958-54cc-45a2-9100-bc769bb80740 */
   public String getClassname() {
   	return classname;
   }
   
   /** @param classname
    * @pdOid b9a67652-ded0-437a-99e6-439031f4128e */
   public void setClassname(String classname) {
   	this.classname = classname;
   }
   
   /** @pdOid 49ae956a-ffa8-4a96-8525-1897ac4e5dff */
   public String getClassnamedesc() {
   	return classnamedesc;
   }
   
   /** @param classnamedesc
    * @pdOid a878b413-5b0f-4df9-bc44-be5ff8860430 */
   public void setClassnamedesc(String classnamedesc) {
   	this.classnamedesc = classnamedesc;
   }
   
   /** @pdOid f6b96866-62f0-4a76-999c-fc1a02da76f1 */
   public String getPx() {
   	return px;
   }
   
   /** @param px
    * @pdOid 4af9cc10-361b-4899-8de4-cf9e883e85e8 */
   public void setPx(String px) {
   	this.px = px;
   }

}