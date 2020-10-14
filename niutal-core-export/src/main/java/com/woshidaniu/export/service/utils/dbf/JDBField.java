/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.utils.dbf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDBField {

	private String name;
	private char type;
	private int length;
	private int decimalCount;

	public JDBField(String name, char type, int length, int decimalCount)throws JDBFException {

		if (type != 'C' && type != 'N' && type != 'L' && type != 'D' && type != 'F') {
			type = 'C';
		}
		if (length < 1) {
			length = 1;
		}

		if (type == 'C' && length > 255) {
			length = 255;
		}
		if (type == 'N' && length > 20) {
			length = 20;
		}
		if (type == 'L' && length != 1) {
			length = 1;
		}

		if (type == 'D' && length != 8) {
			length = 8;
		}

		if (type == 'F' && length > 20) {
			length = 20;
		}
		if (decimalCount < 0) {
			decimalCount = 0;
		}
		if ((type == 'C' || type == 'L' || type == 'D') && decimalCount != 0) {
			decimalCount = 0;
		}
		if (decimalCount > length - 1) {
			decimalCount = length - 1;
		}
		if (getStrLength(name) > 10) {
			name = name.substring(0, 5);
		}
		this.name = name;
		this.type = type;
		this.length = length;
		this.decimalCount = decimalCount;
	}

	public String format(Object obj) throws JDBFException {
		if (type == 'C' || type == 'N') {
			if (obj == null) {
				obj = "";
			}
			if (obj instanceof String) {
				//
			} else {
				obj = obj.toString();
			}
			String s = (String) obj;
			if (getLength() < s.length()) {
				s = s.substring(0, getLength());
			}
			StringBuffer stringbuffer = new StringBuffer(getLength()- s.length());
			for (int i = 0; i < getLength() - s.length(); i++) {
				stringbuffer.append(' ');
			}
			return s + stringbuffer;
		}
		if (type == 'L') {
			if (obj == null) {
				obj = new Boolean(false);				
			}
			if (obj instanceof Boolean) {
				Boolean boolean1 = (Boolean) obj;
				return boolean1.booleanValue() ? "Y" : "N";
			} else {
				throw new JDBFException("Expected a Boolean, got "+ obj.getClass() + ".");
			}
		}
		if (type == 'D') {
			if (obj == null) {
				obj = new Date();				
			}
			if (obj instanceof Date) {
				Date date = (Date) obj;
				SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
				return simpledateformat.format(date);
			} else {
				throw new JDBFException("Expected a Date, got "+obj.getClass() + ".");
			}
		} else {
			throw new JDBFException("Unrecognized JDBFField type: " + type);
		}
	}

	public Object parse(String s) throws JDBFException {
		s = s.trim();
		if (type == 'N' || type == 'F') {
			if (s.equals("")) {
				s = "0";				
			}
			try {
				if (getDecimalCount() == 0) {
					return new Long(s);
				} else {
					return new Double(s);
				}
			} catch (NumberFormatException numberformatexception) {
				throw new JDBFException(numberformatexception);
			}
		}
		if (type == 'C') {
			return s.trim();
		}
		if (type == 'L') {
			if (s.equals("Y") || s.equals("y") || s.equals("T")	|| s.equals("t")) {
				return new Boolean(true);
			}
			if (s.equals("N") || s.equals("n") || s.equals("F") || s.equals("f")) {
				return new Boolean(false);
			} else {
				throw new JDBFException("Unrecognized value for logical field: " + s);
			}
		}
		if (type == 'D') {
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
			try {
				if ("".equals(s)) {
					return null;					
				}else {
					return simpledateformat.parse(s);					
				}
			} catch (ParseException parseexception) {
				throw new JDBFException(parseexception);
			}
		} else {
			throw new JDBFException("Unrecognized JDBFField type: " + type);
		}
	}

	/*
	 * ȡ�ַ��ȣ������������ַ�
	 */
	private int getStrLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* ��ȡ�ֶ�ֵ�ĳ��ȣ���������ַ���ÿ�������ַ��Ϊ2������Ϊ1 */
		if (value != null) {
			for (int i = 0; i < value.length(); i++) {
				/* ��ȡһ���ַ� */
				String temp = value.substring(i, i + 1);
				/* �ж��Ƿ�Ϊ�����ַ� */
				if (temp.matches(chinese)) {
					/* �����ַ��Ϊ2 */
					valueLength += 2;
				} else {
					/* �����ַ��Ϊ1 */
					valueLength += 1;
				}
			}
		}
		return valueLength;
	}

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public char getType() {
		return type;
	}

	public int getLength() {
		return length;
	}

	public int getDecimalCount() {
		return decimalCount;
	}
}