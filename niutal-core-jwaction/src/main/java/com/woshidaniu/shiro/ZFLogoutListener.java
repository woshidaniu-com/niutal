/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.struts2.util.TextProviderHelper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.shiro.filter.impl.DefaultLogoutListener;

@SuppressWarnings("unchecked")
public class ZFLogoutListener extends DefaultLogoutListener {

	@Override
	public void beforeLogout(Subject subject, ServletRequest request, ServletResponse response) {
		
		if(!subject.isAuthenticated()){
			return;
		}
		
		User loginUser = (User) subject.getPrincipals().getPrimaryPrincipal();
		
		ValueStack stack = ActionContext.getContext().getValueStack();
		
		String ywmc = TextProviderHelper.getText("log.message.ywmc", null, CollectionUtils.arrayToList(new Object[]{"登出系统", "xg_xtgl_yhb"}), stack);
		
		String czms = TextProviderHelper.getText("log.message.czms", null, CollectionUtils.arrayToList(new Object[]{"登出系统", "用户名", loginUser.getYhm()}), stack);
		
		 //记录退出日志
		LogEngineImpl.getInstance().logout(loginUser, ywmc, "系统管理", czms);
		
		
	}
	
}
