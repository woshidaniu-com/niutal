package com.woshidaniu.util.bean;

/**
 * 
 *@类名称	: BeanUtils.java
 *@类描述	：Java Bean 应用类 扩展Apache Commons BeanUtils, 提供一些反射方面缺失功能的封装.
 *@创建人	：kangzhidong
 *@创建时间	：Mar 10, 2016 2:24:00 PM
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 *@see com.woshidaniu.beanutils.BeanUtils
 */
@Deprecated
public abstract class BeanUtils extends com.woshidaniu.beanutils.BeanUtils {

    /**
     * 
     * 判断字符串为空或者为NULL return true 
     * @param str
     * @return
     */
	@Deprecated
    public static boolean isEmpty(String str){
    	return ((str == null) || (str.length() == 0));
    }
	
    /**
     * 
     * 判断对象为空或者为NULL return true 
     * @param str
     * @return
     */
	@Deprecated
    public static boolean isEmpty(Object obj){
    	return ((obj == null));
    }
	
    /**
     * 判断字符串是否为空或者为NULL 
     * 不为空并且不为NULL return true 
     * @param str
     * @return
     */
	@Deprecated
    public static boolean isNotEmpty(String str){
    	 return (!(isEmpty(str)));
    }
	
    /**
     * 判断对象是否为空或者为NULL 
     * 不为空并且不为NULL return true 
     * @param str
     * @return
     */
	@Deprecated
    public static boolean isNotEmpty(Object obj){
    	 return (!(isEmpty(obj)));
    }
}
