package com.woshidaniu.pwdmgr.api.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BindTime implements Comparable<BindTime> {

	protected String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	protected String timestamp;
	protected SimpleDateFormat format;
	protected int effectTime;

	@Override
	public int compareTo(BindTime bind) {
		try {
			Date nowDate = getFormat().parse(this.getTimestamp());
			Date beforDate = getFormat().parse(bind.getTimestamp());
			if ((nowDate.getTime() - beforDate.getTime()) > bind.getEffectTime()) {
				return -1;
			} else {
				return 1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public BindTime(String timestamp, int effectTime) {
		this.timestamp = timestamp;
		this.format = new SimpleDateFormat(DEFAULT_FORMAT);
		this.effectTime = effectTime;
	}
	
	public BindTime(String timestamp, String format, int effectTime) {
		this.timestamp = timestamp;
		this.format = new SimpleDateFormat(format);
		this.effectTime = effectTime;
	}
	
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	public void setFormat(SimpleDateFormat format) {
		this.format = format;
	}

	public int getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(int effectTime) {
		this.effectTime = effectTime;
	}
	
}
