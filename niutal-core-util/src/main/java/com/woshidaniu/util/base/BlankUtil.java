package com.woshidaniu.util.base;


import java.util.Collection;
import java.util.Map;



/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：空对象、空字符串判断工具类
 * <p>
 * @deprecated
 * @see com.woshidaniu.basicutils.BlankUtils
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月7日下午2:31:56
 */
@Deprecated
public final class BlankUtil {

	private BlankUtil(){
		super();
	}
	
	/**
	 * 
	 * <p>方法说明：判断字符串是否为空<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月7日下午2:32:21<p>
	 * @param str String
	 * @return boolean
	 */
	public static boolean isBlank(final String str) {
		return str == null || str.trim().isEmpty();
	}

	
	

	/**
	 * 
	 * <p>方法说明：判断数组是否为空<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月7日下午2:35:00<p>
	 * @param arr Object[]
	 * @return boolean
	 */
	public static boolean isBlank(final Object... arr) {
		return arr == null || arr.length == 0;
	}

	
	/**
	 * 
	 * <p>方法说明：判断Collectionj是否为空<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月7日下午2:35:47<p>
	 * @param obj Collection<?>
	 * @return boolean
	 */
	public static boolean isBlank(final Collection<?> obj) {
		return obj == null || obj.isEmpty();
	}

	

	
	/**
	 * 
	 * <p>方法说明：判断Map是否为空<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月7日下午2:37:18<p>
	 * @param obj Map<?,?>
	 * @return boolean
	 */
	public static boolean isBlank(final Map<?,?> obj) {
		return obj == null || obj.isEmpty();
	}
	

	
	
}
