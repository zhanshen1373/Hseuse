package com.hd.hse.entity.sitesupervision;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * 现场监督实体类 Created by liuyang on 2016年9月27日
 */
@DBTable(tableName = "ud_cbsgl_jcjl")
public class SupervisionEntity extends SuperEntity {
	/**
	 * 
	 * Created by liuyang on 2016年9月27日
	 */
	private static final long serialVersionUID = 7649528592580307630L;
	/**
	 * ID Created by liuyang on 2016年9月27日
	 */
	@DBField(id = true)
	private String ud_cbsgl_jcjlid;
	/**
	 * 检查部门 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String jcdept;
	/**
	 * 监察部门描述 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String jcdept_desc;
	/**
	 * 检查人 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String jcperson;
	/**
	 * 检查人描述 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String jcperson_desc;
	/**
	 * 检查时间 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String changedate;
	/**
	 * 作业申请ID Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String ud_zyxk_sysqid;
	/**
	 * 属地部门 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String jclocation;
	/**
	 * 属地部门描述 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String jclocation_desc;
	/**
	 * 属地单位 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String sddept;
	/**
	 * 属地单位描述 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String sddept_desc;
	/**
	 * 问题分类 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String problem_type;
	/**
	 * 问题描述 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String problem_desc;
	/**
	 * 负责人 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String fzperson;
	/**
	 * 负责人描述 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String fzperson_desc;
	/**
	 * 违章人员 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String wzperson;
	/**
	 * 违章人员描述 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String wzperson_desc;
	/**
	 * 责任单位 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String zrdept;
	/**
	 * 责任单位描述 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String zrdept_desc;
	/**
	 * 罚款金额 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private String wyj;
	/**
	 * 是否整改 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private Integer iszg;
	/**
	 * 是否转入黑名单 Created by liuyang on 2016年9月27日
	 */
	@DBField
	private Integer ishmd;
	/**
	 * 
	 * Created by liuyang on 2016年9月27日
	 */
	@DBField
	private Integer isupload;
	/**
	 * 
	 * Created by liuyang on 2016年9月27日
	 */
	@DBField
	private Integer dr;
	/**
	 * 
	 * Created by liuyang on 2016年9月27日
	 */
	@DBField
	private Integer tag;

	public String getUd_cbsgl_jcjlid() {
		return ud_cbsgl_jcjlid;
	}

	public void setUd_cbsgl_jcjlid(String ud_cbsgl_jcjlid) {
		this.ud_cbsgl_jcjlid = ud_cbsgl_jcjlid;
	}

	public String getJcdept() {
		return jcdept;
	}

	public void setJcdept(String jcdept) {
		this.jcdept = jcdept;
	}

	public String getJcdept_desc() {
		return jcdept_desc;
	}

	public void setJcdept_desc(String jcdept_desc) {
		this.jcdept_desc = jcdept_desc;
	}

	public String getJcperson() {
		return jcperson;
	}

	public void setJcperson(String jcperson) {
		this.jcperson = jcperson;
	}

	public String getJcperson_desc() {
		return jcperson_desc;
	}

	public void setJcperson_desc(String jcperson_desc) {
		this.jcperson_desc = jcperson_desc;
	}

	public String getChangedate() {
		return changedate;
	}

	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}

	public String getUd_zyxk_sysqid() {
		return ud_zyxk_sysqid;
	}

	public void setUd_zyxk_sysqid(String ud_zyxk_sysqid) {
		this.ud_zyxk_sysqid = ud_zyxk_sysqid;
	}

	public String getJclocation() {
		return jclocation;
	}

	public void setJclocation(String jclocation) {
		this.jclocation = jclocation;
	}

	public String getJclocation_desc() {
		return jclocation_desc;
	}

	public void setJclocation_desc(String jclocation_desc) {
		this.jclocation_desc = jclocation_desc;
	}

	public String getSddept() {
		return sddept;
	}

	public void setSddept(String sddept) {
		this.sddept = sddept;
	}

	public String getSddept_desc() {
		return sddept_desc;
	}

	public void setSddept_desc(String sddept_desc) {
		this.sddept_desc = sddept_desc;
	}

	public String getProblem_type() {
		return problem_type;
	}

	public void setProblem_type(String problem_type) {
		this.problem_type = problem_type;
	}

	public String getProblem_desc() {
		return problem_desc;
	}

	public void setProblem_desc(String problem_desc) {
		this.problem_desc = problem_desc;
	}

	public String getFzperson() {
		return fzperson;
	}

	public void setFzperson(String fzperson) {
		this.fzperson = fzperson;
	}

	public String getFzperson_desc() {
		return fzperson_desc;
	}

	public void setFzperson_desc(String fzperson_desc) {
		this.fzperson_desc = fzperson_desc;
	}

	public String getWzperson() {
		return wzperson;
	}

	public void setWzperson(String wzperson) {
		this.wzperson = wzperson;
	}

	public String getWzperson_desc() {
		return wzperson_desc;
	}

	public void setWzperson_desc(String wzperson_desc) {
		this.wzperson_desc = wzperson_desc;
	}

	public String getZrdept() {
		return zrdept;
	}

	public void setZrdept(String zrdept) {
		this.zrdept = zrdept;
	}

	public String getZrdept_desc() {
		return zrdept_desc;
	}

	public void setZrdept_desc(String zrdept_desc) {
		this.zrdept_desc = zrdept_desc;
	}

	public String getWyj() {
		return wyj;
	}

	public void setWyj(String wyj) {
		this.wyj = wyj;
	}

	public Integer getIszg() {
		return iszg;
	}

	public void setIszg(Integer iszg) {
		this.iszg = iszg;
	}

	public Integer getIshmd() {
		return ishmd;
	}

	public void setIshmd(Integer ishmd) {
		this.ishmd = ishmd;
	}

	public Integer getIsupload() {
		return isupload;
	}

	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}
}
