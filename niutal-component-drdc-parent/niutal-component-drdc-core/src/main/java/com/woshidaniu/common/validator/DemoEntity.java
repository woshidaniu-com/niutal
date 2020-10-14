package com.woshidaniu.common.validator;

import java.util.Collection;
import java.util.Date;

/**
 * ʾ��ʵ����
 * 
 * @author taofucheng
 * 
 */
public class DemoEntity {
	private String str;
	private Collection<?> col;
	private Number num;
	private Date date;
	private Object obj;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public Collection<?> getCol() {
		return col;
	}

	public void setCol(Collection<?> col) {
		this.col = col;
	}

	public Number getNum() {
		return num;
	}

	public void setNum(Number num) {
		this.num = num;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
