package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * 优先级line表
 * 
 * Created by liuyang on 2016年9月18日
 */
@DBTable(tableName = "ud_zyxk_prtyline")
public class PriorityLine extends SuperEntity {
	/**
	 * 
	 * Created by liuyang on 2016年9月18日
	 */
	private static final long serialVersionUID = 6328079565990175663L;
	/**
	 * 优先级line表ID
	 */
	@DBField
	private int ud_zyxk_prtylineid;
	/**
	 * 描述
	 */
	@DBField
	private String description;
	/**
	 * 作业类型
	 */
	@DBField
	private String zypclass;
	/**
	 * 优先级
	 */
	@DBField
	private int priority;
	/**
	 * 作业级别
	 */
	@DBField
	private String zyplevel;

	public int getUd_zyxk_prtylineid() {
		return ud_zyxk_prtylineid;
	}

	public void setUd_zyxk_prtylineid(int ud_zyxk_prtylineid) {
		this.ud_zyxk_prtylineid = ud_zyxk_prtylineid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getZypclass() {
		return zypclass;
	}

	public void setZypclass(String zypclass) {
		this.zypclass = zypclass;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getZyplevel() {
		return zyplevel;
	}

	public void setZyplevel(String zyplevel) {
		this.zyplevel = zyplevel;
	}

}
