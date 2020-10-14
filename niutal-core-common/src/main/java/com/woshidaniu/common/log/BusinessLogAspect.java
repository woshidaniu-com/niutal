/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.log;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.common.GlobalString;
import com.woshidaniu.freemarker.utils.FormatUtils;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.web.utils.WebRequestUtils;

/**
 * @author Penghui.Qu[445]
 * 业务日志切面
 */
public class BusinessLogAspect{
	
	private static final Logger log = LoggerFactory.getLogger(BusinessLogAspect.class);
	@Autowired
	private  BusinessLogService logService;
	
	private BusinessLogModel getLogModel(User user, BusinessLog businessLog,Map<String,Object> data){
		
		BusinessLogModel model = new BusinessLogModel();
		HttpServletRequest request = (HttpServletRequest) WebContext.getRequest();
		String czlx = businessLog.czlx().getKey();
		String czmk = businessLog.czmk();
		String czms = businessLog.czms();
		String ywmc = businessLog.ywmc();
		model.setCzlx(czlx);
		model.setCzmk(czmk);
		model.setCzrq(DateUtils.format(DateUtils.DATE_FORMAT_TWO));
		String finalCzms = "";
		try {
			finalCzms = FormatUtils.toTextStatic(data, czms);
		} catch (Exception e) {
			String yhm = (user == null ? "null" : user.getYhm());
			String requestUri = request.getRequestURI();
			log.error("格式化日志文本[{}]异常,yhm:[{}],czmk:[{}],czlx:[{}],ywmc:[{}],requestUri:[{}]",czms,yhm,czmk,czlx,ywmc,requestUri,e);
		}
		model.setCzms(finalCzms);
		model.setYwmc(ywmc);
		model.setCzr(String.format("%s【用户名：%s】", user.getXm(),user.getYhm()));
		model.setIpdz(WebRequestUtils.getRemoteAddr(request));
		model.setZjm(request.getRemoteHost());
		return model;
	}
	

	/**
	 * 代理对象正常调用返回后advice
	 * @param jp
	 * @param businessLog
	 */
	public void afterReturing(JoinPoint jp, BusinessLog businessLog){
		
		Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		
		String[] argNames = methodSignature.getParameterNames();
		Object[] args = jp.getArgs();
		Map<String,Object> data = new HashMap<String,Object>();
		
		for (int i = 0 , j = argNames.length ; i < j ; i++){
			data.put(argNames[i], args[i]);
		}
		
		Subject subject = SecurityUtils.getSubject();
		HttpServletRequest request = (HttpServletRequest) WebContext.getRequest();
		HttpSession session = request.getSession();
		
		User user = null;
		
		if (subject != null && subject.isAuthenticated()){
			user = (User) subject.getPrincipal();
		} else {
			user = (User) session.getAttribute(GlobalString.WEB_SESSION_USER_KEY);
		}
		
		if(user != null){
			BusinessLogModel logModel = getLogModel(user, businessLog,data);
			logService.log(logModel);
		}
	}
}
