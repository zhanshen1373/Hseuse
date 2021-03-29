package com.hd.hse.entity.common;

/** ClassName: UpdataInfo (版本升级)<br/>
 * date: 2014年9月9日  <br/>
 * 
 * 
 * @author lxf
 * @version
 * 
 * @pdOid a5087290-de92-4dde-911d-db16c9673e6b */
public class UpdataInfo {
   /** @pdOid 0927e7f5-9174-43a0-99ba-4a439d07e057 */
   private String version;
   /** @pdOid 5e31feb8-6d8b-4147-98ab-800d23c337ad */
   private String url;
   /** @pdOid e29720d4-df8e-4870-bb35-c05486bb52bd */
   private String description;
   
   /** @pdOid f4c609b9-e5c7-4459-81e8-f0d534e7614d */
   public String getVersion() {
   	return version;
   }
   
   /** @param version
    * @pdOid 26e132c6-820f-4a88-8680-a28d5192fd7d */
   public void setVersion(String version) {
   	this.version = version;
   }
   
   /** @pdOid 461873a2-aa3b-4beb-8c17-ab9fa23458cc */
   public String getUrl() {
   	return url;
   }
   
   /** @param url
    * @pdOid 236eecfb-6aaf-418f-95d7-6df6b3e6aa08 */
   public void setUrl(String url) {
   	this.url = url;
   }
   
   /** @pdOid a5d7da04-ad7e-4fb6-b299-d31a007e3973 */
   public String getDescription() {
   	return description;
   }
   
   /** @param description
    * @pdOid 7f0b10db-6927-466c-b0fc-a7c6295c995d */
   public void setDescription(String description) {
   	this.description = description;
   }

}