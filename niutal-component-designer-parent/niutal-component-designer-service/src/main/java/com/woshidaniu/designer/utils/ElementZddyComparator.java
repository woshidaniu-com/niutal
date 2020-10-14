/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.designer.utils;

import java.util.Comparator;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.designer.dao.entities.DesignZdybdZddyModel;

/**
 * 
 *@类名称:ElementZddyComparator.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：Nov 3, 2015 3:31:37 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public class ElementZddyComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		DesignZdybdZddyModel a1 =  (DesignZdybdZddyModel)o1;
		DesignZdybdZddyModel a2 =  (DesignZdybdZddyModel)o2;
		return StringUtils.getSafeInt(a1.getZbwz(), "0") - StringUtils.getSafeInt(a2.getZbwz(), "0");
	}

	  
	
}
