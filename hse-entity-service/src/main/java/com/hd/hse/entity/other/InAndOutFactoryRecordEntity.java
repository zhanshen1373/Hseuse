package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * ClassName: UD_CBSGL_RYCRJLEntity (出入厂人员记录)<br/>
 * date: 2014年11月10日  <br/>
 *
 * @author lxf
 * @version 
 */
@DBTable(tableName = "ud_cbsgl_rycrjl")
public class InAndOutFactoryRecordEntity extends SuperEntity {

	/**
	 * serialVersionUID:TODO().
	 */
	private static final long serialVersionUID = 1650609100273338859L;
	@DBField(id=true)
	private String ud_cbsgl_rycrjlid; //主键
	@DBField
	private String description;//描述
	@DBField
	private String name; //姓名
	@DBField
	private String innercard; //入厂证号
	@DBField
	private String location;//入厂地点
	@DBField
	private String crdatetime; // 入厂时间
	
	public String getUd_cbsgl_rycrjlid() {
		return ud_cbsgl_rycrjlid;
	}
	public void setUd_cbsgl_rycrjlid(String ud_cbsgl_rycrjlid) {
		this.ud_cbsgl_rycrjlid = ud_cbsgl_rycrjlid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInnercard() {
		return innercard;
	}
	public void setInnercard(String innercard) {
		this.innercard = innercard;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCrdatetime() {
		return crdatetime;
	}
	public void setCrdatetime(String crdatetime) {
		this.crdatetime = crdatetime;
	}
	



}
