/**
 * Project Name:hse-common
 * File Name:PropDescriptor.java
 * Package Name:com.hd.hse.common.entity
 * Date:2014年8月9日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.entity;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

import com.hd.hse.common.logger.LogUtils;

/**
 * ClassName:PropDescriptor <br/>
 * Date: 2014年8月9日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class PropDescriptor {

	private static Logger logger = LogUtils.getLogger(PropDescriptor.class);

	private Class<?> entityType;

	private Class<?> propType;

	private String name;

	private String baseName;

	public PropDescriptor(Class<?> entityType, Class<?> propType,
			String propName) {
		if (entityType == null) {
			throw new IllegalArgumentException("entity Class can not be null!");
		}
		if (propName == null) {
			throw new IllegalArgumentException(
					"Entity Property name can not be null!");
		}
		this.propType = propType;
		this.entityType = entityType; // in which this property is declared.
		this.name = propName;
		this.baseName = capitalize(propName);
	}

	/**
	 * currEntity my override get and set.
	 */
	public synchronized Method getReadMethod(Class<?> currEntity) {
		Method readMethod;
		String readMethodName = null;
		if (propType == boolean.class || propType == null) {
			readMethodName = "is" + baseName;
		} else {
			readMethodName = "get" + baseName;
		}
		Class<?> classStart = currEntity;
		if (classStart == null) {
			classStart = entityType;
		}
		readMethod = findMemberMethod(classStart, readMethodName, 0, null);
		if (readMethod == null && readMethodName.startsWith("is")) {
			readMethodName = "get" + baseName;
			readMethod = findMemberMethod(classStart, readMethodName, 0, null);
		}
		if (readMethod != null) {
			int mf = readMethod.getModifiers();
			if (!Modifier.isPublic(mf)) {
				return null;
			}
			Class<?> retType = readMethod.getReturnType();
			if (!propType.isAssignableFrom(retType)) {
				logger.warn("return type unmatch for get Method and property! : "
						+ classStart.getName() + "." + name);
			}
		}
		return readMethod;
	}

	public synchronized Method getWriteMethod(Class<?> currEntity) {
		Method writeMethod;
		String writeMethodName = null;
		if (propType == null) {
			propType = findPropertyType(getReadMethod(currEntity), null);
		}
		if (writeMethodName == null) {
			writeMethodName = "set" + baseName;
		}
		Class<?> classStart = currEntity;
		if (classStart == null) {
			classStart = entityType;
		}
		writeMethod = findMemberMethod(classStart, writeMethodName, 1,
				(propType == null) ? null : new Class[] { propType });
		if (writeMethod != null) {
			int mf = writeMethod.getModifiers();
			if (!Modifier.isPublic(mf) || Modifier.isStatic(mf)) {
				writeMethod = null;
			}
		}
		return writeMethod;
	}

	private Class<?> findPropertyType(Method readMethod, Method writeMethod) {
		Class<?> propertyType = null;
		if (readMethod != null) {
			propertyType = readMethod.getReturnType();
		}
		if (propertyType == null && writeMethod != null) {
			Class<?> params[] = writeMethod.getParameterTypes();
			propertyType = params[0];
		}
		return propertyType;
	}

	private Method findMemberMethod(Class<?> entityClass, String mName,
			int num, Class<?>[] args) {
		Method foundM = null;
		Method[] methods = entityClass.getDeclaredMethods();
		if (methods.length < 0) {
			return null;
		}
		for (Method method : methods) {
			if (method.getName().equalsIgnoreCase(mName)) {
				Class<?>[] paramTypes = method.getParameterTypes();
				if (paramTypes.length == num) {
					boolean match = true;
					for (int i = 0; i < num; i++) {
						// parameter should be compatible with prop type
						if (!args[i].isAssignableFrom(paramTypes[i])) {
							match = false;
							break;
						}
					}
					if (match) {
						foundM = method;
						break;
					}
				}
			}
		}
		// recursively find super
		if (foundM == null) {
			if (entityClass.getSuperclass() != null) {
				foundM = findMemberMethod(entityClass.getSuperclass(), mName,
						num, args);
			}
		}
		return foundM;
	}

	public String getName() {
		return name;
	}

	public static String capitalize(String str) {
		return changeFirstCharacterCase(true, str);
	}

	private static String changeFirstCharacterCase(boolean capitalize,
			String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(strLen);
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}

}
