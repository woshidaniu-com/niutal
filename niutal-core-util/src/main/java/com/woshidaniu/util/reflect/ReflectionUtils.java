/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: ReflectionUtils.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package com.woshidaniu.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 反射工具类.
 * 提供访问私有变量,获取泛型类型Class, 提取集合中元素的属性, 转换字符串到对象等Util函数.
 * @author calvin
 */

@Deprecated
public class ReflectionUtils  extends com.woshidaniu.beanutils.reflection.ReflectionUtils{

	protected static Log logger = LogFactory.getLog(ReflectionUtils.class);

	private ReflectionUtils(){
		super();
	}
	
	
	
	/**
	 * 
	 * <p>方法说明：调用Getter方法.<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日下午3:14:09<p>
	 * @param obj 对象 
	 * @param propertyName 属性名
	 * @return 属性值 
	 */
	public static Object invokeGetterMethod(Object obj, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[] {});
	}

	
	
	/**
	 * 
	 * <p>方法说明：调用Setter方法.使用value的Class来查找Setter方法.<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日下午3:14:41<p>
	 * @param obj 对象
	 * @param propertyName 属性名
	 * @param value 属性值 
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
		invokeSetterMethod(obj, propertyName, value, null);
	}

	/**
	 * 
	 * <p>方法说明：调用Setter方法.<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日下午3:15:10<p>
	 * @param obj 对象
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @param propertyType 属性类型
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	
	/**
	 * 
	 * <p>方法说明：直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日下午3:15:48<p>
	 * @param obj 对象
	 * @param fieldName 属性名
	 * @return 属性值 
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error(e);
		}
		return result;
	}

	
	/**
	 * 
	 * <p>方法说明：直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日下午3:16:12<p>
	 * @param obj 对象
	 * @param fieldName 属性名
	 * @param value 属性值 
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error(e);
		}
	}

	
	/**
	 * 
	 * <p>方法说明： 循环向上转型, 获取对象的DeclaredField,并强制设置为可访问.
	 * 		如向上转型到Object仍无法找到, 返回null.
	 * <p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日下午3:16:49<p>
	 * @param obj 对象
	 * @param fieldName 属性名
	 * @return field
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		Assert.notNull(obj, "object不能为空");
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	
	/**
	 * 
	 * <p>方法说明：直接调用对象方法, 无视private/protected修饰符.<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日下午3:17:42<p>
	 * @param obj 对象
	 * @param methodName 方法名
	 * @param parameterTypes 参数类型
	 * @param args 参数值
	 * @return Object
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	
	/**
	 * 
	 * <p>方法说明：
	 * 	 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
	 * 	 如向上转型到Object仍无法找到, 返回null.
	 *  用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 * <p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日下午3:18:31<p>
	 * @param obj 对象
	 * @param methodName 属性名
	 * @param parameterTypes 参数类型
	 * @return Method
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		Assert.notNull(obj, "object不能为空");

		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

				method.setAccessible(true);

				return method;

			} catch (NoSuchMethodException e) {//NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * public UserDao extends HibernateDao<User>
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	public static <T> Class<?> getSuperClassGenricType(final Class<?> clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * 如public UserDao extends HibernateDao<User,Long>
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	public static Class<?> getSuperClassGenricType(final Class<?> clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class<?>) params[index];
	}

	
	//将反射时的checked exception转换为unchecked exception
	private static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
	
	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成List.
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @return list
	 
	public static List<Object> fetchElementPropertyToList(final Collection<?> collection, final String propertyName) {
		List<Object> list = new ArrayList<Object>();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}*/

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 * @return String
	
	public static String fetchElementPropertyToString(final Collection<?> collection, final String propertyName,
			final String separator) {
		List<?> list = fetchElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	} */
	
	/**
	 * 得到所有的Field
	 * @param clazz Class
	 * @return Field集合
	 */
	public static List<Field> getAllDeclaredField(Class<?> clazz){
		List<Field> list = new LinkedList<Field>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] declaredFields = superClass.getDeclaredFields();
			for(Field f : declaredFields)
				list.add(f);
		}
		return list;
	}
	
	/**
	 * 判断clazz类是否实现了某个接口
	 * @param clazz Class
	 * @param targetInterface 是否实现的接口
	 * @return boolean
	 */
	public static  boolean isInterface(Class<?> clazz, Class<?> targetInterface) {
		Class<?>[] face = clazz.getInterfaces();
		for (int i = 0, j = face.length; i < j; i++) {
			if (face[i].getName().equals(targetInterface.getName())) {
				return true;
			} else {
				Class<?>[] face1 = face[i].getInterfaces();
				for (int x = 0; x < face1.length; x++) {
					if (face1[x].getName().equals(targetInterface.getName())) {
						return true;
					} else if (isInterface(face1[x], targetInterface)) {
						return true;
					}
				}
			}
		}
		if (null != clazz.getSuperclass()) {
			return isInterface(clazz.getSuperclass(), targetInterface);
		}
		return false;
	}
	
	/**
	 * 
	 * <p>方法说明：判断对象是否拥有某属性<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年9月22日下午2:11:42<p>
	 * @param obj
	 * @param property
	 * @return
	 */
	public static boolean hasProperty(final Object obj,String property){
		try {
			return PropertyUtils.getProperty(obj, property)!= null;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return false;
	}
	 
	/**
	 * 获取obj对象fieldName的Field
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		return getField(obj, fieldName);
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if(field!=null){
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * 设置obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}
	
}
