/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.designer.utils;

import org.apache.commons.collections.Predicate;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.designer.dao.entities.DesignZdybdZddyModel;

/**
 * 
 *@类名称:FirstPredicate.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：Nov 3, 2015 3:20:56 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public class ElementZddyPredicate implements Predicate{

	private DesignZdybdZddyModel zddy_element;
	 
	public boolean evaluate(Object object) {
		DesignZdybdZddyModel element_field =  (DesignZdybdZddyModel)object;
		//匹配元素关联字段信息
		if(!BlankUtils.isBlank(element_field.getFjzddyid()) && element_field.getFjzddyid().equals(getZddy_element().getZddyid())){
			//设置匹配的关联字段
			return true;
		}
		return false;
	}

	/**
	 * @return the zddy_element
	 */
	public DesignZdybdZddyModel getZddy_element() {
		return zddy_element;
	}

	/**
	 * @param zddyElement the zddy_element to set
	 */
	public void setZddy_element(DesignZdybdZddyModel zddyElement) {
		zddy_element = zddyElement;
	}

	
}
