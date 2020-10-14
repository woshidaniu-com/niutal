package com.woshidaniu.util.base;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：Apache BeanUtils 扩展
 * <p>
 * @deprecated 
 * @see com.woshidaniu.beanutils.BeanUtils
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月7日下午2:21:26
 */
@Deprecated
public final class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

	private static  Log logger = LogFactory.getLog(BeanUtils.class);

	private  BeanUtils() {
		super();
	}

	
	/**
	 * 
	 * <p>方法说明：获取类声明属性<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月7日下午2:07:14<p>
	 * @param clazz Class类型
	 * @return String[]
	 */
	public static String[] getDeclaredPropertys(final Class<?> clazz) {
		
		Set<String> props = new HashSet<String>();
		
		final Field[] fields = clazz.getDeclaredFields();
		
		for (final Field field : fields){
			props.add(field.getName());
		}
		return props.toArray(new String[]{});
	}

	
	/**
	 * 
	 * <p>方法说明：检测类是否包含属性<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月7日下午2:06:57<p>
	 * 
	 * @param clazz Class类型
	 * @param propertyName 属性名
	 * @return boolean
	 */
	public static boolean hasProperty(final Class<?> clazz, final String propertyName) {
		
		Field field = null;
		
		try {
			field = clazz.getDeclaredField(propertyName);
		} catch (SecurityException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchFieldException e) {
			logger.error(e.getMessage(), e);
		}
		return field != null;
	}


}
