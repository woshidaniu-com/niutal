/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.field;

import java.util.HashMap;
import java.util.Map;

public enum FIELDTYPE {
	
	STRING, NUMBER, EMAIL, FLOAT, DATE, DATETIME, IDCARD, TEL, ZIP, MOBILE, INT,PATTERN, INTEGERFLOAT;
	
	private static final Map<String, FIELDTYPE> stringToEnum = new HashMap<String, FIELDTYPE>();
	
	static {
		for (FIELDTYPE blah : values()) {
			stringToEnum.put(blah.toString(), blah);
		}
	}

	public static FIELDTYPE fromString(String symbol) {
		return stringToEnum.get(symbol);
	}
}
