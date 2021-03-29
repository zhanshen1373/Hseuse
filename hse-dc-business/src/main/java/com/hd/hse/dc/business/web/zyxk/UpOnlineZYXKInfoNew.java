package com.hd.hse.dc.business.web.zyxk;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Context;

import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.entity.workorder.WorkOrder;

/**
 * 上传作业符合数据（相当于上传在线数据）
 * 
 * @author yn
 * 
 */
public class UpOnlineZYXKInfoNew extends UpZYXKInfoNew1 {
	private static Logger logger = LogUtils
			.getLogger(UpOnlineZYXKInfoNew.class);
	private static List<TableDesc> listTableRelation = null;
	private List<WorkOrder> workOrderList;

	public UpOnlineZYXKInfoNew(Context context, List<WorkOrder> workOrderList) {
		super(context);
		this.workOrderList = workOrderList;
	}

	@Override
	public List<TableDesc> getRelation() {
		if (null == listTableRelation) {
			listTableRelation = new ArrayList<TableDesc>();
			TableDesc tb = null;
			// 作业申请表
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_zysq");
			tb.setPrimarykey("ud_zyxk_zysqid");
			listTableRelation.add(tb);
		}
		return listTableRelation;
	}

	@Override
	public void beforUpDataInfo(Object... args) throws HDException {
		listmapRelation = getRelation();
		if (null == listmapRelation || listmapRelation.size() == 0) {
			setWritelog("请实现getRelation重写，并赋予相应值;");
		}
		dataAdapter = this.getAnalyzeDataAdapter();
		if (null == dataAdapter) {
			setWritelog("实例化数据适配器对象为null,请检查配置参数;");
		}
		Object objparam = initParam();
		if (HashMap.class.isInstance(objparam)) {
			hasmap = (HashMap<String, Object>) objparam;
		} else {
			throw new HDException("initParam()必须设置为HashMap对象;");
		}
		listinfo = getListInfo(workOrderList);
	}

	/*
	 * @Override public Object initParam() throws HDException { HashMap<String,
	 * Object> hashmap ; hashmap= (HashMap<String, Object>) super.initParam();
	 * hashmap.put(PadInterfaceRequest.KEYMAC, SystemProperty
	 * .getSystemProperty().getPadmac()); return hashmap;
	 * 
	 * }
	 */
	private HashMap<String, List<Map<String, Object>>> getListInfo(
			List<WorkOrder> workOrderList) throws HDException {
		HashMap<String, List<Map<String, Object>>> update = new HashMap<String, List<Map<String, Object>>>();
		String tablename = "ud_zyxk_zysq";
		ArrayList<Map<String, Object>> mapList = getMapList(workOrderList);
		update.put(tablename, mapList);
		return update;
	}

	private ArrayList<Map<String, Object>> getMapList(
			List<WorkOrder> workOrderList) throws HDException {
		ArrayList<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (WorkOrder workOrder : workOrderList) {
			//Log.e("ccc", workOrder.getUd_zyxk_zysqid());
			mapList.add(toMap(workOrder));
		}
		return mapList;
	}

	/**
	 * 把一个Bean对象转换成Map对象</br>
	 * 
	 * @param clazz
	 * @param ignores
	 * @return
	 * @throws HDException
	 * @throws IllegalAccessException
	 */
	public Map<String, Object> toMap(Object object) throws HDException {
		Class clazz = object.getClass();
		Class superClass = clazz.getSuperclass();
		Field[] fields = clazz.getDeclaredFields();
		Field[] superFields = superClass.getDeclaredFields();
		if (fields == null || fields.length == 0) {
			return Collections.EMPTY_MAP;
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.get(object) != null) {
					ParamsName paramsName = field
							.getAnnotation(ParamsName.class);
					String key;
					if (paramsName == null) {
						key = field.getName();
					} else {
						key = paramsName.value();
					}
					if (field.get(object) != null) {
						params.put(key, String.valueOf(field.get(object)));
					} else {
						params.put(key, "null");
					}
				}
			}
			if (params.containsKey("ud_zyxk_worktaskid")) {
				params.put("ud_zyxk_worktaskid",
						params.get("ud_zyxk_worktaskid") + "-ll");
			} else {
				throw new HDException("没有找到ud_zyxk_worktaskid字段");
			}
			params.put("v_action", "update");
			/*
			 * for (Field superField : superFields) { if (superField.get(object)
			 * != null) { superField.setAccessible(true); ParamsName
			 * superParamsName = superField .getAnnotation(ParamsName.class);
			 * String superKey; if (superParamsName == null) { superKey =
			 * superField.getName(); } else { superKey =
			 * superParamsName.value(); } if (superKey.equals("add")) { break; }
			 * if (superField.get(object) != null) { params.put(superKey,
			 * String.valueOf(superField.get(object))); } else {
			 * params.put(superKey, "null"); } } }
			 */
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error(e.toString(), null);
			throw new HDException(e.toString());
		}
		return params;
	}
}
