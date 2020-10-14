package com.woshidaniu.globalweb.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

public class ZFSessionListener extends SessionListenerAdapter {

	@Override
	public void onStart(Session session) {
		super.onStart(session);
		System.out.println("----SHIRO-----" + session.getId()+"-----");
	}


}
