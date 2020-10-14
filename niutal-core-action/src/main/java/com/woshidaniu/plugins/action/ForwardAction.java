package com.woshidaniu.plugins.action;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.security.algorithm.DesBase64Codec;

/**
 * 与其它系统集成跳转
 * @author Penghui.Qu
 *
 */
@Controller
@Scope("prototype")
public class ForwardAction extends BaseAction{
	
	private static final transient Logger log = LoggerFactory.getLogger(ForwardAction.class);
	
	private static final long serialVersionUID = 1L;

	/**
	 * 与我是大牛SMP结合，跳转到SMP
	 * @return
	 */
	public String toSmp(){
		User user = getUser();
		DesBase64Codec encrypt = new DesBase64Codec();
		
		String gotoUrl = getRequest().getParameter("goto");
		gotoUrl+="&clientTicket="+ URLEncoder.encode(encrypt.decrypt(user.getYhm()));
		log.debug("单点SMP："+gotoUrl);
		getValueStack().set("url", gotoUrl);
		
		return "toSmp";
	}
}
