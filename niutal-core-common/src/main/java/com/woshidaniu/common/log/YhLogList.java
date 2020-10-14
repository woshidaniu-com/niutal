package com.woshidaniu.common.log;

import java.util.Enumeration;
import java.util.Vector;

public class YhLogList {
	private static final YhLogList userList = new YhLogList();
	private Vector<String> v;

	private YhLogList() {
		v = new Vector<String>();

	}

	public static YhLogList getInstance() {
		return userList;
	}

	public void addUser(String name) {
		if (name != null)
			v.addElement(name);
	}

	public void removeUser(String name) {
		if (name != null)
			v.remove(name);
	}

	public Enumeration<String> getUserList() {
		return v.elements();
	}

	public int getUserCount() {
		return v.size();
	}
}
