package com.hd.hse.business.listener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.business.util.AbstractDataAdapter;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;

/**
 * ClassName:DownListener (下载业务数据监听类).<br/>
 * Date: 2014年8月25日 <br/>
 *
 * @author lxf
 * @version
 * @see
 */
public abstract class DownListener extends AbstractWebListener {

	AbstractDataAdapter dataAdapter = null;

	@SuppressWarnings("unchecked")
	@Override
	public Object action(String action,Object... args) throws HDException {
		// 表示票的下载//1.求情下载的票//2.解析返回请求的的票(在别的方法中靠子类调用)
		// 表示票的下载//3.选择下载//4.判断票是否在PDA中存在//5.给用户反馈//5.解析票成SQL语句//6.插入数据//7.成功
		// 记录成功和失败的票的主键 eg: id:1或0
		HashMap<String, Object> hasReturn = new HashMap<String, Object>();
//		try
//		{
		super.action(action,args);
		HashMap<String, TableDesc> hashRelation = getTableRelation();
		if (null == hashRelation && hashRelation.size() == 0) {
			throw new HDException("请实现getTableRelation()方法，设置业务关联表;");
		}
		String businessType=getBusinessType();
		if(StringUtils.isEmpty(businessType))
		{
			throw new HDException("请实现getBusinessType()方法，设置下载的业务类型;");
		}
		String returnAction =getReturnPCAction();
		if (StringUtils.isEmpty(returnAction)) {
			throw new HDException("请实现getReturnPCAction()方法，设置回传动作类型;");
		}
		HashMap<String, Object> hasmap = null;
		Object objparam=initParam();
		if (HashMap.class.isInstance(objparam)) {
			hasmap = (HashMap<String, Object>)objparam;
		} else {
			throw new HDException("initParam()必须设置为HashMap对象;");
		}
		HashMap<String, Object> returnhasmap = getReturnPCMapInfo();
		if(null==returnhasmap)
		{
			throw new HDException("getReturnPCMapInfo()必须设置为HashMap对象;");
		}

		List<String> retStr =null;
		Object obj= client.DownLoadBusiness(hasmap, businessType);
		if (obj instanceof PadInterfaceResponse) {
			PadInterfaceResponse response = (PadInterfaceResponse) obj;
			setWritelog("pc错误："+response.getExceptionmsg());
			throw new HDException("下载失败,请联系管理员");
		}
		else
		{
			retStr =new ArrayList<String>();
			setWriteDublog(obj.toString());
			retStr.add(obj.toString());//(List<String>) obj;
			//retStr=(List<String>)obj;
		}
		
		StringBuilder sbstr=new StringBuilder();
		for (String str : retStr) {
			HashMap<String, List<String>> hasmapsql = resDowninfo(
					hashRelation, str);

			// key是主键
			Iterator<Entry<String, List<String>>> iter = hasmapsql
					.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, List<String>> entry = (Entry<String, List<String>>) iter
						.next();
				// 表示一套票一套票执行
				try {
					this.execute(entry.getValue());
					setWriteDublog(entry.getValue().toString());
					String strid=entry.getKey();
					if(strid.contains("@"))
					{
						strid=strid.split("@")[1];
						sbstr.append(strid);
					}
					else
					{
						sbstr.append("'").append(strid).append("',");
					}
					
					hasReturn.put(entry.getKey(), "1");
				} catch (Exception e) {
					// 如果失败 得到key
					hasReturn.put(entry.getKey(), "0");
				}
			}
		}
		//表示不是气体检测
		if(sbstr.length()>0 && !businessType.equalsIgnoreCase(PadInterfaceContainers.METHOD_ZYP_DOWNQTJC))
		{
			sbstr.delete(sbstr.length()-1, sbstr.length());
			returnhasmap.put(PadInterfaceRequest.KEYZYPSTRID, sbstr.toString());
			// 回调PC端(注意下载不接受回执的返回信息)
			client.ReturnPCInfo(returnAction, returnhasmap);
		}
		
//		} catch (HDException e) {
//			setWritelog("获取PC端数据报错", e);
//			throw new HDException(e.getMessage());
//		}
		return hasReturn;
	}
	/**
	 * 上传业务类型
	 * */
	public abstract String getBusinessType();
	/**
	 * 获取表的关系
	 * */
	public abstract HashMap<String, TableDesc> getTableRelation();
	/**
	 * getReturnPCAction:(设置返回成功的动作). <br/>
	 * date: 2014年10月9日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public abstract String getReturnPCAction();
	
	/**
	 * getReturnPCMapInfo:(下载成功，返回PC端数据). <br/>
	 * date: 2014年10月9日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public abstract HashMap<String,Object> getReturnPCMapInfo();
}
