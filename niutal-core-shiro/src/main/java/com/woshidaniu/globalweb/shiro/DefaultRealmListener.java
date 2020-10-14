package com.woshidaniu.globalweb.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;

import com.woshidaniu.common.GlobalString;
import com.woshidaniu.common.log.User;
import com.woshidaniu.shiro.realm.RealmListener;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   <br>说明：TODO
 *	 <br>class：com.woshidaniu.globalweb.shiro.DefaultRealmListener.java
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class DefaultRealmListener implements RealmListener {

	@Override
	public void onAuthenticationSuccess(AuthenticationInfo info, Session session) {
		User user = (User) info.getPrincipals().getPrimaryPrincipal();
		List<String> jsdms = user.getJsdms();
		if (jsdms != null && !jsdms.isEmpty()){
			user.setJsdm(jsdms.get(0));
		}
		session.setAttribute(GlobalString.WEB_SESSION_USER_KEY, user);
        session.setAttribute(user.getYhm(), user.getJsdms());
	}

	@Override
	public void onAuthenticationFail(AuthenticationToken token, AuthenticationException ex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeAuthentication(AuthenticationToken token) {
		// TODO Auto-generated method stub
		
	}

}
