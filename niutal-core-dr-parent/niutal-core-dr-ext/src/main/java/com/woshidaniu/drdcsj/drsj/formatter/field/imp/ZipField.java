/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.field.imp;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.field.IField;
import com.woshidaniu.drdcsj.drsj.utils.PatternFactory;

/**
 * @author 		：康康（1571）
 * @description	： 带缓存的邮政编码字段，因为邮政编码范围有限，且重复率比较高
 */
public class ZipField implements IField {
	
	private static final Logger log = LoggerFactory.getLogger(ZipField.class);
	
	private static final Set<String> CACHE = new HashSet<String>();
	
	private static Pattern pattern =  PatternFactory.getZipPattern();

	@Override
	public boolean check(DrlpzModel drlpzModel, String value) {
		synchronized (CACHE) {
			//isZipCode
			if(CACHE.contains(value)) {
				log.debug("命中缓存");
				return true;
			}else {
				Matcher m = pattern.matcher(value);
				if (m.matches()) {
					CACHE.add(value);
					return true;
				}
				return false;			
			}
		}
	}

	@Override
	public String getErrorMessage(DrlpzModel drlpzModel, String value) {
		return "[" + drlpzModel.getDrlmc() + "]的值不合法";
	}

	@Override
	public String toString() {
		return "ZipField@"+this.hashCode();
	}
}
