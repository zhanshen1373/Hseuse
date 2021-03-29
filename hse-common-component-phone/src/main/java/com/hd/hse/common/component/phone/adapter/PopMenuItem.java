package com.hd.hse.common.component.phone.adapter;

import com.hd.hse.common.entity.SuperEntity;

/**
 * ClassName: PopMenuItem (导航栏弹出菜单实体)<br/>
 * date: 2014年12月26日 <br/>
 * 
 * @author wenlin
 * @version
 */
public class PopMenuItem extends SuperEntity {
	// 图片描述
	private String description;
	// 图片
	private Integer drawable;

	private boolean isRemind;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDrawable() {
		return drawable;
	}

	public void setDrawable(Integer drawable) {
		this.drawable = drawable;
	}

	public boolean isRemind() {
		return isRemind;
	}

	public void setRemind(boolean isRemind) {
		this.isRemind = isRemind;
	}

}
