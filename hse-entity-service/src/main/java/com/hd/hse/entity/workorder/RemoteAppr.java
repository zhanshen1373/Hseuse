package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;

/**
 * 远程审批，上传审批环节点数据
 * 
 * @author yn
 * 
 */
public class RemoteAppr extends SuperEntity {
	/**
	 * zysqid : 65830,65831 approve_person_name : 黄文彬,高春雷,大管家 spnode_desc :
	 * 质量安全环保处 approve_personid : HUANGWENBIN, GAO1,MAXADMIN zyptype_desc :
	 * 作业大票,高处作业 zyptype : zylx99,zylx04 spot_final_person_name : 大管家
	 * spot_final_personid : MAXADMIN spnode : CHQ_ZLAQHBC
	 */

	private String zysqid;
	private String approve_person_name;
	private String spnode_desc;
	private String approve_personid;
	private String zyptype_desc;
	private String zyptype;
	private String spot_final_person_name;
	private String spot_final_personid;
	private String spnode;
	private String qxrole;
	private Integer bpermulcard;
	private Integer ismust;

	public Integer getIsmust() {
		return ismust;
	}

	public void setIsmust(Integer ismust) {
		this.ismust = ismust;
	}

	public String getZysqid() {
		return zysqid;
	}

	public void setZysqid(String zysqid) {
		this.zysqid = zysqid;
	}

	public String getApprove_person_name() {
		return approve_person_name;
	}

	public void setApprove_person_name(String approve_person_name) {
		this.approve_person_name = approve_person_name;
	}

	public String getSpnode_desc() {
		return spnode_desc;
	}

	public void setSpnode_desc(String spnode_desc) {
		this.spnode_desc = spnode_desc;
	}

	public String getApprove_personid() {
		return approve_personid;
	}

	public void setApprove_personid(String approve_personid) {
		this.approve_personid = approve_personid;
	}

	public String getZyptype_desc() {
		return zyptype_desc;
	}

	public void setZyptype_desc(String zyptype_desc) {
		this.zyptype_desc = zyptype_desc;
	}

	public String getZyptype() {
		return zyptype;
	}

	public void setZyptype(String zyptype) {
		this.zyptype = zyptype;
	}

	public String getSpot_final_person_name() {
		return spot_final_person_name;
	}

	public void setSpot_final_person_name(String spot_final_person_name) {
		this.spot_final_person_name = spot_final_person_name;
	}

	public String getSpot_final_personid() {
		return spot_final_personid;
	}

	public void setSpot_final_personid(String spot_final_personid) {
		this.spot_final_personid = spot_final_personid;
	}

	public String getSpnode() {
		return spnode;
	}

	public void setSpnode(String spnode) {
		this.spnode = spnode;
	}

	public String getQxrole() {
		return qxrole;
	}

	public void setQxrole(String qxrole) {
		this.qxrole = qxrole;
	}

	public Integer getBpermulcard() {
		return bpermulcard;
	}

	public void setBpermulcard(Integer bpermulcard) {
		this.bpermulcard = bpermulcard;
	}
}
