package com.hd.hse.business.readcard;

/**
 * ClassName: AbstractReadCardListener (抽象读卡类)<br/>
 * date: 2014年9月4日  <br/>
 *
 * @author lxf
 * @version 
 */
public abstract class AbstractReadCardListener {

	/**
	 * getCardNum:(获取卡号). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public abstract String getCardNum();
	
	
	/**
	 * setClose:(关闭读卡). <br/>
	 * date: 2014年9月11日 <br/>
	 *
	 * @author Administrator
	 * @return
	 */
	public abstract Object setClose();
	
}
