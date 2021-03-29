/**
 * Project Name:hse-common
 * File Name:EntityHelper.java
 * Package Name:com.hd.hse.common.entity
 * Date:2014年8月9日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.entity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ClassName:EntityHelper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014年8月9日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class EntityHelper {
	/**
	 * ClassName: ReflectionInfo (缓存)<br/>
	 * date: 2014年8月9日 <br/>
	 * 
	 * @author lg
	 * @version EntityHelper
	 */
	static class ReflectionInfo {

		Map<String, Method> readMap = new HashMap<String, Method>();

		Map<String, Method> writeMap = new HashMap<String, Method>();

		Method getReadMethod(String prop) {
			return prop == null ? null : readMap.get(prop.toLowerCase());
		}

		Method getWriteMethod(String prop) {
			return prop == null ? null : writeMap.get(prop.toLowerCase());
		}
	}

	private static Map<String, ReflectionInfo> cache = new HashMap<String, ReflectionInfo>();

	private static EntityHelper etyHelper = new EntityHelper();

	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	protected static final Object[] NULL_ARGUMENTS = {};
	
	private EntityHelper(){
		
	}

	public static EntityHelper getInstance() {
		return etyHelper;
	}

	/**
	 * getAttribute:(取属性值). <br/>
	 * TODO().<br/>
	 * date: 2014年8月9日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @param attributeName
	 * @return
	 */
	public static Object getAttribute(SuperEntity entity, String attributeName) {
		try {
			Method method = getInstance().getMethod(entity, attributeName,
					false);
			if (attributeName != null && method == null) {
				return null;
			} else if (method == null) {
				return null;
			}
			return method.invoke(entity, NULL_ARGUMENTS);
		} catch (Exception e) {
			String errStr = "Failed to get property: " + attributeName;
			throw new RuntimeException(errStr, e);
		}
	}

	/**
	 * setAttribute:(设置属性值). <br/>
	 * TODO().<br/>
	 * date: 2014年8月9日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @param attributeName
	 * @param value
	 */
	public static void setAttribute(SuperEntity entity, String attributeName,
			Object value) {
		try {
			Method method = getInstance()
					.getMethod(entity, attributeName, true);
			if (method == null) {
				return;
			}
			Object[] arguments = { value };
			method.invoke(entity, arguments);
		} catch (Exception e) {
			String errStr = "Failed to set property: " + attributeName
					+ " on entity: " + entity.getClass().getName()
					+ " with value:" + value;
			throw new RuntimeException(errStr, e);
		}
	}

	/**
	 * getSetMethod:(获取set方法). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @param propertyName
	 * @return
	 */
	public static Method getSetMethod(Object entity, String propertyName) {
		return getInstance().getMethod(entity, propertyName, true);
	}

	private Method getMethod(Object entity, String propertyName,
			boolean isSetMethod) {
		Method method = null;
		rwl.readLock().lock();
		ReflectionInfo reflectionInfo = null;
		try {
			reflectionInfo = cachedReflectionInfo(entity.getClass());
			if (isSetMethod) {
				method = reflectionInfo.getWriteMethod(propertyName);
			} else {
				method = reflectionInfo.getReadMethod(propertyName);
			}
			return method;
		} finally {
			rwl.readLock().unlock();
		}
	}

	private ReflectionInfo cachedReflectionInfo(Class<?> entityCls) {
		return cacheReflectionInfo(entityCls, null);
	}

	/**
	 * cacheReflectionInfo:(缓存method). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param entityCls
	 * @param pdescriptor
	 * @return
	 */
	private ReflectionInfo cacheReflectionInfo(Class<?> entityCls,
			List<PropDescriptor> pdescriptor) {
		String key = entityCls.getName();
		ReflectionInfo reflectionInfo = cache.get(key);
		if (reflectionInfo == null) {
			rwl.readLock().unlock();
			rwl.writeLock().lock();
			rwl.readLock().lock();
			try {
				reflectionInfo = cache.get(key);
				if (reflectionInfo == null) {
					reflectionInfo = new ReflectionInfo();
					List<PropDescriptor> propDesc = new ArrayList<PropDescriptor>();
					if (pdescriptor != null) {
						propDesc.addAll(pdescriptor);
					} else {
						propDesc = getPropertyDescriptors(entityCls);
					}
					for (PropDescriptor pd : propDesc) {
						Method readMethod = pd.getReadMethod(entityCls);
						Method writeMethod = pd.getWriteMethod(entityCls);
						if (readMethod != null)
							reflectionInfo.readMap.put(pd.getName()
									.toLowerCase(), readMethod);
						if (writeMethod != null)
							reflectionInfo.writeMap.put(pd.getName()
									.toLowerCase(), writeMethod);
					}
					cache.put(key, reflectionInfo);
				}
			} finally {
				rwl.writeLock().unlock();
			}
		}
		return reflectionInfo;

	}

	private List<PropDescriptor> getPropertyDescriptors(Class<?> clazz) {
		List<PropDescriptor> descList = new ArrayList<PropDescriptor>();
		List<PropDescriptor> superDescList = new ArrayList<PropDescriptor>();
		List<String> propsList = new ArrayList<String>();
		Class<?> propType = null;
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().length() < 4) {
				continue;
			}
			if (method.getName().charAt(3) < 'A'
					|| method.getName().charAt(3) > 'Z') {
				continue;
			}
			if (method.getName().startsWith("set")) {
				if (method.getParameterTypes().length != 1) {
					continue;
				}
				if (method.getReturnType() != void.class) {
					continue;
				}
				propType = method.getParameterTypes()[0];
			} else if (method.getName().startsWith("get")) {
				if (method.getParameterTypes().length != 0) {
					continue;
				}
				propType = method.getReturnType();
			} else {
				continue;
			}
			String propname = method.getName().substring(3, 4).toLowerCase();
			if (method.getName().length() > 4) {
				propname = propname + method.getName().substring(4);
			}
			if (propname.equals("class")) {
				continue;
			}
			if (propsList.contains(propname)) {
				continue;
			} else {
				propsList.add(propname);
			}
			descList.add(new PropDescriptor(clazz, propType, propname));
		}

		// 处理父类
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			superDescList = getPropertyDescriptors(superClazz);
			descList.addAll(superDescList);
			if (!isEntityCached(superClazz)) {
				cacheReflectionInfo(superClazz, superDescList);
			}
		}
		return descList;
	}

	private boolean isEntityCached(Class<?> entity) {
		String key = entity.getName();
		ReflectionInfo cMethod = cache.get(key);
		if (cMethod == null) {
			rwl.readLock().lock();
			try {
				cMethod = cache.get(key);
				if (cMethod == null) {
					return false;
				}
			} finally {
				rwl.readLock().unlock();
			}
		}
		return true;
	}

	/**
	 * invokeMethod:(). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @param method
	 * @param value
	 */
	public static void invokeMethod(Object entity, Method method, Object value) {
		try {
			if (method == null)
				return;
			Object[] arguments = { value };
			method.invoke(entity, arguments);
		} catch (Exception e) {
			String errStr = "Failed to set property: " + method.getName();
			throw new RuntimeException(errStr, e);
		}
	}
}
