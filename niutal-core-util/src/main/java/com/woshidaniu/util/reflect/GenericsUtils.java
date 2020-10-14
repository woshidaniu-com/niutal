package com.woshidaniu.util.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：Generics的util类.
 * 	  来自www.springside.org.cn
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月16日下午3:10:14
 */
@Deprecated
public class GenericsUtils {
    
    private static final Log LOGGER = LogFactory.getLog(GenericsUtils.class);

    private GenericsUtils() {
    	super();
    }

    
    
    
    /**
     * 
     * <p>方法说明：通过反射,获得定义Class时声明的父类的范型参数的类型.<p>
     * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
     * <p>时间：2016年6月16日下午3:11:08<p>
     * @param clazz The class to introspect
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class<?> getSuperClassGenricType(Class<?> clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    
    /**
     * 
     * <p>方法说明：通过反射,获得定义Class时声明的父类的范型参数的类型<p>
     * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
     * <p>时间：2016年6月16日下午3:11:34<p>
     * @param clazz clazz clazz The class to introspect
     * @param index index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class<?> getSuperClassGenricType(Class<?> clazz, int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            LOGGER.warn(clazz.getSimpleName()
                + "'s superclass not ParameterizedType");

            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType)
            .getActualTypeArguments();

        if ((index >= params.length) || (index < 0)) {
            LOGGER.warn("Index: " + index + ", Size of "
                + clazz.getSimpleName() + "'s Parameterized Type: "
                + params.length);

            return Object.class;
        }

        if (!(params[index] instanceof Class)) {
            LOGGER.warn(clazz.getSimpleName()
                + " not set the actual class on superclass generic parameter");

            return Object.class;
        }

        return (Class<?>) params[index];
    }
}
