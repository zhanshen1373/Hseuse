package com.hd.hse.dc.business.common.webservice;

import java.util.HashMap;

import com.hd.hse.common.exception.HDException;

public interface IWebActionListener {

	/**
	 * validate:(验证). <br/>
	 * date: 2015年3月16日 <br/>
	 *
	 * @author lxf
	 * @param map
	 * @return
	 * @throws HDException
	 */
	public Object validate(HashMap<String, Object> map) throws HDException;
	
	/**
	 * upData:(上传). <br/>
	 * date: 2015年3月16日 <br/>
	 *
	 * @author lxf
	 * @param map
	 * @return
	 * @throws HDException
	 */
	public Object upData(HashMap<String, Object> map) throws HDException;
	/**
	 * downData:(下载). <br/>
	 * date: 2015年3月16日 <br/>
	 *
	 * @author lxf
	 * @param map
	 * @return
	 * @throws HDException
	 */
	public Object downData(HashMap<String, Object> map) throws HDException;


	/**
	 * upFile:(上传文件). <br/>
	 * date: 2015年3月16日 <br/>
	 *
	 * @author lxf
	 * @param map
	 * @return
	 * @throws HDException
	 */
	public Object upFile(String actionType, String path, HashMap<String, Object> map) throws HDException;
	/**
	 * downFile:(下载文件). <br/>
	 * date: 2015年3月16日 <br/>
	 *
	 * @author lxf
	 * @param map
	 * @return
	 * @throws HDException
	 */
	public Object downFile(HashMap<String, Object> map) throws HDException;
	
	
}
