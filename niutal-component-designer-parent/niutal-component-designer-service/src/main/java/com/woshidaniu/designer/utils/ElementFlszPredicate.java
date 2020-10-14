/**
 * <p>Coyright (R) 2014 我是大牛<p>
 */
package com.woshidaniu.designer.utils;

import org.apache.commons.collections.Predicate;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.designer.dao.entities.DesignZdybdFlszModel;
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
public class ElementFlszPredicate implements Predicate{

	private DesignZdybdFlszModel flsz_element;
	 
	public boolean evaluate(Object object) {
		DesignZdybdZddyModel element_field =  (DesignZdybdZddyModel)object;
		//匹配元素关联字段信息
		if( BlankUtils.isBlank(element_field.getFjzddyid()) &&
			!BlankUtils.isBlank(element_field.getFlszid()) && element_field.getFlszid().equals(getFlsz_element().getFlszid())){
			//设置匹配的关联字段
			return true;
		}
		return false;
	}

	/**
	 * @return the flsz_element
	 */
	public DesignZdybdFlszModel getFlsz_element() {
		return flsz_element;
	}

	/**
	 * @param flszElement the flsz_element to set
	 */
	public void setFlsz_element(DesignZdybdFlszModel flszElement) {
		flsz_element = flszElement;
	}
}
