package com.woshidaniu.common.log;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class YhLog implements HttpSessionBindingListener {

	private String name;
	private YhLogList ul = YhLogList.getInstance();

	public YhLog() {
	}

	public YhLog(String name) {
		this.name = name;

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void valueBound(HttpSessionBindingEvent event) {
		ul.addUser(name);
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		ul.removeUser(name);
	}

}
