package com.hd.hse.business.listener;



import java.util.HashMap;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.padinterface.PadInterfaceResponse;

public abstract class CheckUserListener extends AbstractWebListener {

	@Override
	public Object action(String action, Object... args) throws HDException {
		
		Object obj=null;
		try {
			super.action(action, args);
			HashMap<String, Object> hasmap = null;
			Object objparam=initParam();
			if (HashMap.class.isInstance(objparam)) {
				hasmap = (HashMap<String, Object>)objparam;
			} else {
				throw new HDException("initParam()必须设置为HashMap对象;");
			}
			 obj = client.checkUser(hasmap);
			PadInterfaceResponse response = null;
			if (PadInterfaceResponse.class.isInstance(obj)) {
				response = (PadInterfaceResponse) obj;
				setWritelog(response.getExceptionmsg());
				throw new HDException("pc端验证失败,请联系管理员");
			}
			//this.SendMessage(IMessageWhat.END, 98, 100, "成功");
		} catch (HDException e) {
			setWritelog("验证用户失败：" + e.getMessage());
			throw  e;
			/*this.SendMessage(IMessageWhat.ERROR, 9, 9,
					e.getMessage().indexOf("超时") > 0 ? e.getMessage()
							: "验证用户失败!");*/
		}
		return obj;
	}

}
