/**
 * File:    UdZyxkWzk.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-25 11:58:43
 * Purpose: 定义数据类 UdZyxkWzk
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 215a30c4-f4bb-4cb9-8347-a89886f4fb96 */
@DBTable(tableName = "ud_zyxk_wzk")
public class PositionCard extends com.hd.hse.common.entity.SuperEntity {
	/**
	 * serialVersionUID:TODO().
	 * 
	 * 
	 * @pdOid 3070af5f-fbc3-4184-8dfb-dd8227181688
	 */
	private static final long serialVersionUID = 6821814478786952167L;
	/**
	 * 主键
	 * 
	 * @pdOid 2aa8ec63-74a7-416c-80b5-8af8f6296857
	 */
	@DBField(id = true)
	private String ud_zyxk_wzkid;
	/**
	 * 位置编码
	 * 
	 * @pdOid 79aa32e6-d76e-4942-953c-0c4a633614d1
	 */
	@DBField
	private String location;
	/**
	 * 作业位置描述
	 * 
	 * @pdOid 543e695f-12c2-4ba6-b14b-987666b85187
	 */
	@DBField
	private String description;
	/**
	 * 位置卡号
	 * 
	 * @pdOid 1ef62a21-6bc5-4cd0-80af-3c7c9d318651
	 */
	@DBField
	private String locationcardid;
	/**
	 * 位置描述
	 * 
	 * @pdOid 9649800a-0b19-4327-881d-e74b9c12d0bb
	 */
	@DBField
	private String location_desc;
	/**
	 * 时间戳
	 * 
	 * @pdOid 82e0a508-1818-4d61-9003-501242bf796e
	 */
	@DBField
	private String changedate;
	/**
	 * 标记
	 * 
	 * @pdOid d01f8071-49fa-453d-8a8b-353e820c02f2
	 */
	@DBField
	private Integer tag;
	/**
	 * 删除
	 * 
	 * @pdOid e7fd62f4-c82a-42f8-bcb5-fa873d9a4e9f
	 */
	@DBField
	private Integer dr;
	/**
	 * 时间提醒
	 * 
	 * @pdOid 527ccf4d-1cdb-45ea-bd01-2f3b70b47943
	 */
	@DBField
	private String txtime;
	/**
	 * longitude:TODO(虚拟位置卡经度).
	 */
	@DBField
	private String longitude;
	/**
	 * isvirtualcard:TODO(是否是虚拟位置卡).
	 */
	@DBField
	private Integer isvirtualcard;
	/**
	 * latitude:TODO(虚拟位置卡纬度).
	 */
	@DBField
	private String latitude;
	/**
	 * radiu:TODO(位置卡区域半径).
	 */
	@DBField
	private Integer radiu;
	/**
	 * 刷卡时间
	 */
	private long skTime;

	public long getSkTime() {
		return skTime;
	}

	public void setSkTime(long skTime) {
		this.skTime = skTime;
	}

	/**
	 * @return the ud_zyxk_wzkid
	 * 
	 * @pdOid b3a6fe69-ba2e-4423-851b-c743e5a15dec
	 */
	public String getUd_zyxk_wzkid() {
		return ud_zyxk_wzkid;
	}

	/**
	 * @param ud_zyxk_wzkid
	 *            the ud_zyxk_wzkid to set
	 * @pdOid 14b198ed-b546-4df4-af26-daab203e1735
	 */
	public void setUd_zyxk_wzkid(String ud_zyxk_wzkid) {
		this.ud_zyxk_wzkid = ud_zyxk_wzkid;
	}

	/**
	 * 设置 位置编码 该字段是：位置编码
	 * 
	 * @param location
	 * @pdOid 3fe0fb82-4b7d-43f1-9f29-35a77dbda526
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * 获取 位置编码 该字段是：位置编码
	 * 
	 * 
	 * @pdOid 1bdf0366-37bd-4a21-b76f-9c81ddaa08b3
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * 设置 作业位置描述 该字段是：作业位置描述
	 * 
	 * @param description
	 * @pdOid 41bdcf5f-6f71-42fa-bfb2-90bca98f341a
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取 作业位置描述 该字段是：作业位置描述
	 * 
	 * 
	 * @pdOid 8acfd25e-bf12-425e-a3f7-92c2a8d8687a
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置 位置卡号 该字段是：位置卡号
	 * 
	 * @param locationcardid
	 * @pdOid 9f453e85-3438-4d2b-9f07-d0173f1db9d3
	 */
	public void setLocationcardid(String locationcardid) {
		this.locationcardid = locationcardid;
	}

	/**
	 * 获取 位置卡号 该字段是：位置卡号
	 * 
	 * 
	 * @pdOid 324da5ac-8d4d-4c40-8c9d-c7c0a6ebd105
	 */
	public String getLocationcardid() {
		return locationcardid;
	}

	/**
	 * @return the location_desc
	 * 
	 * @pdOid dee4452b-a556-4a5d-8ed2-8ad0fc822256
	 */
	public String getLocation_desc() {
		return location_desc;
	}

	/**
	 * @param location_desc
	 *            the location_desc to set
	 * @pdOid 31a61f29-24c9-40fe-a1b6-ef3f020a5322
	 */
	public void setLocation_desc(String location_desc) {
		this.location_desc = location_desc;
	}

	/**
	 * 设置 时间戳 该字段是：时间戳
	 * 
	 * @param changedate
	 * @pdOid 1de10e92-f550-4731-ac14-3e68de214afb
	 */
	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}

	/**
	 * 获取 时间戳 该字段是：时间戳
	 * 
	 * 
	 * @pdOid 6bf7e7ec-de1a-4ff4-8392-f0f5635d021e
	 */
	public String getChangedate() {
		return changedate;
	}

	/**
	 * 设置 标记 该字段是：标记
	 * 
	 * @param tag
	 * @pdOid 3c66a4fc-d46f-4587-8441-fccfbc166678
	 */
	public void setTag(Integer tag) {
		this.tag = tag;
	}

	/**
	 * 获取 标记 该字段是：标记
	 * 
	 * 
	 * @pdOid d0f6e8e2-c8d3-454d-bb95-561ba54e7889
	 */
	public Integer getTag() {
		return tag;
	}

	/**
	 * 设置 删除 该字段是：删除
	 * 
	 * @param dr
	 * @pdOid 6bf30541-539a-4c8f-b558-f94e65dd9947
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}

	/**
	 * 获取 删除 该字段是：删除
	 * 
	 * 
	 * @pdOid ded0f583-974d-45cb-b9de-e8ad633aaeae
	 */
	public Integer getDr() {
		return dr;
	}

	/**
	 * 设置 时间提醒 该字段是：时间提醒
	 * 
	 * @param txtime
	 * @pdOid b1f5497b-f9de-4af0-bb5f-11cb4cd27436
	 */
	public void setTxtime(String txtime) {
		this.txtime = txtime;
	}

	/**
	 * 获取 时间提醒 该字段是：时间提醒
	 * 
	 * 
	 * @pdOid 5f438eaf-0b0d-486b-ba07-9a732eeec9c6
	 */
	public String getTxtime() {
		return txtime;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Integer getIsvirtualcard() {
		return isvirtualcard;
	}

	public void setIsvirtualcard(Integer isvirtualcard) {
		this.isvirtualcard = isvirtualcard;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getRadiu() {
		return radiu;
	}

	public void setRadiu(Integer radiu) {
		this.radiu = radiu;
	}

}