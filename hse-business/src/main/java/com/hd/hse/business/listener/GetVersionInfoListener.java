package com.hd.hse.business.listener;


import java.util.HashMap;
import java.util.Map;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.padinterface.PadInterfaceResponse;

public abstract class GetVersionInfoListener extends AbstractWebListener {

	@SuppressWarnings("unchecked")
	@Override
	public Object action(String action, Object... args) throws HDException {
		try {
			super.action(action, args);
			HashMap<String, Object> hasmap = null;
			Object objparam = initParam();
			if (HashMap.class.isInstance(objparam)) {
				hasmap = (HashMap<String, Object>) objparam;
			} else {
				throw new HDException("initParam()必须设置为HashMap对象;");
			}
			Object obj = client.getVersionInfo(hasmap);
			if (obj instanceof Map) {
				this.SendMessage(IMessageWhat.END, 100, 100, "", obj);
			} else {
				if (obj instanceof PadInterfaceResponse) {
					PadInterfaceResponse pad = (PadInterfaceResponse) obj;
					setWritelog("下载版本信息报错:" + pad.getExceptionmsg());
				}
				this.SendMessage(IMessageWhat.ERROR, 9, 9, "下载版本信息报错");
			}
		} catch (Exception e) {
			setWritelog("下载版本信息报错", e);
			this.SendMessage(IMessageWhat.ERROR, 9, 9,
					e.getMessage().indexOf("超时") > 0 ? e.getMessage()
							: "下载版本信息报错");
		}
		return null;
	}

}
