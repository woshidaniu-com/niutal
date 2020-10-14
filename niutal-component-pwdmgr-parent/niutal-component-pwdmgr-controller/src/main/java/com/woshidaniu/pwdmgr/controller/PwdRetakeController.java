/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.pwdmgr.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.format.utils.PatternFormatUtils;
import com.woshidaniu.pwdmgr.api.PwdMgrKey;
import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.model.ResultData;
import com.woshidaniu.pwdmgr.api.provider.PwdUpdateProvider;
import com.woshidaniu.pwdmgr.api.provider.def.EmailDataOutputProvider;
import com.woshidaniu.pwdmgr.api.provider.def.SMSDataOutputProvider;
import com.woshidaniu.pwdmgr.api.strategy.PwdStrategy;
import com.woshidaniu.pwdmgr.api.strategy.PwdStrategyManager;
import com.woshidaniu.pwdmgr.dao.entities.StrategyModel;
import com.woshidaniu.pwdmgr.dao.entities.VerifiModel;
import com.woshidaniu.pwdmgr.service.svcinterface.StrategyService;
import com.woshidaniu.pwdmgr.service.svcinterface.VerifiService;
import com.woshidaniu.service.common.IPasswordStrengthCheckService;
import com.woshidaniu.util.ResultUtils;
import com.woshidaniu.web.context.WebContext;

/**
 *@autor kangzhidong
 * 找回密码控制器
 *
 *@autor zhidong
 * 修改逻辑防止攻击者枚举系统用户名
 */
@Controller
@RequestMapping(value = "/pwdmgr/retake/")
public class PwdRetakeController extends BaseController{
	//在session中存放用户名的key
	private static final String USER_NAME_SESSION_KEY = PwdRetakeController.class.getName()+"_USER_NAME_SESSION_KEY";
	
	@Resource(name = "pwdStrategyManager")
	protected PwdStrategyManager strategyManager;
	@Resource
	protected PwdUpdateProvider updateProvider;
	@Resource
	protected StrategyService strategyService;
	@Resource
	protected VerifiService verifiService;
	@Resource
	private IPasswordStrengthCheckService passwordStrengthCheckService;
	
	@Value("#{pwdmgrProps['pwd.relogin.url']}")
	protected String loginURL;
	
	@RequestMapping(value="index")
	public String index(HttpServletRequest request){
		
		//查询所有的找回策略
		StrategyModel strategyModel = new StrategyModel();
		List<StrategyModel> strategyList = strategyService.getModelList(strategyModel);
		if(strategyList != null){
			for (StrategyModel strategy : strategyList) {
				if(!strategyManager.retakeStrategys().contains(strategy.getName())){
					strategy.setStatus("0");
				}
			}
		}
		//查询启用的身份校验字段
		VerifiModel verifiModel = new VerifiModel();
		verifiModel.setStatus("1");
		List<VerifiModel> verifiList = verifiService.getModelList(verifiModel);
		
		request.setAttribute("verifiList", verifiList);
		request.setAttribute("strategyList", strategyList);
		request.setAttribute("nextURL", "valdiateYhmCard");
		request.setAttribute("step", "1");
		request.setAttribute("rules", passwordStrengthCheckService.info());
		return "/pwdmgr/find_password";
	}
	
	/**
	 *@description：  账号核实验证
	 *@param request
	 *@param step
	 *@param type 验证类型 如email,phone
	 *@param value 验证的值
	 *@return
	 */
	@ResponseBody
	@RequestMapping(value="verifi")
	public Object verifi(HttpServletRequest request, String step,BindData data){
		if ("1".equals(step)) {
			ResultData result = strategyManager.getPwdVerifiStrategy(PwdStrategy.DEFAULT_STRATEGY).verifi(data);
			
			if (MESSAGE_STATUS_SUCCESS.equals(result.getStatus())) {// 如果验证通过
				
				//将用户名放入session
				String username = (String) result.getMap().get("username");
				request.getSession().setAttribute(USER_NAME_SESSION_KEY, username);
				
				result.setI18nArgs(new String[] {});
				String message = getMessageSource().getMessage(result.getI18nKey(), result.getI18nArgs(), WebContext.getLocale());
				Map<String, Object> rtMap = ResultUtils.statusMap(MESSAGE_STATUS_SUCCESS, message);

				rtMap.put("step", "2");
				return rtMap;
			}
			// 如果进入这里说明验证信息发送失败，需要进行异常处理
			String message = getMessageSource().getMessage(result.getI18nKey(), result.getI18nArgs(), WebContext.getLocale());
			return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, message);
		} else {
			//无效的访问
			String message = getMessageSource().getMessage(PwdMgrKey.PWD_MGR_INVALID_ACCESS, EMPTY_ARGS , WebContext.getLocale());
			return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, message);
		}
	}
	
	/**
	 *@description：执行秘密找回逻辑主要实现验证码发送和验证
	 *@param request
	 *@param data
	 *@param retakeType
	 *@return
	 */
	@ResponseBody
	@RequestMapping(value="advice")
	public Object advice(HttpServletRequest request,BindData data,String step, String retakeType){
		if ("2".equals(step)) {
			
			//填入放入session的用户名
			String username = (String) request.getSession().getAttribute(USER_NAME_SESSION_KEY);
			if(StringUtils.isEmpty(username)) {
				String message = messageSource.getMessage(PwdMgrKey.FAIL, new Object[] {}, WebContext.getLocale());
				return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, message);
			}else {
				data.setUsername(username);				
			}
			
			ResultData result = strategyManager.getPwdRetakeStrategy(retakeType).advice(data);
			
			if(MESSAGE_STATUS_SUCCESS.equals(result.getStatus())){//如果找回密码操作成功，表示验证信息已经发送成功，等待输入验证码
				
				Map<String, Object> rtMap = ResultUtils.statusMap(MESSAGE_STATUS_SUCCESS, "");
				rtMap.put("retakeType", retakeType);
				//验证码的唯一ID;用于检测验证码的安全性
				rtMap.put("uuid", result.getMap().get("uuid"));
				
				if (EmailDataOutputProvider.class.getName().equals(result.getI18nArgs()[0])) {//邮件发送成功
					String template = messageSource.getMessage(PwdMgrKey.PWD_RETAKE_CAPTCHA_MAIL_OUTPUT_SUCCESS, null,WebContext.getLocale());
					rtMap.put("message", PatternFormatUtils.format(template, result.getMap()));
					
				}else if (SMSDataOutputProvider.class.getName().equals(result.getI18nArgs()[0])) {//短信发送成功
					String template = messageSource.getMessage(PwdMgrKey.PWD_RETAKE_CAPTCHA_SMS_OUTPUT_SUCCESS,null, WebContext.getLocale());
					rtMap.put("message", PatternFormatUtils.format(template, result.getMap()));
					
				}else{
					String message = messageSource.getMessage(result.getI18nKey(), result.getI18nArgs(), WebContext.getLocale());
					rtMap.put("message", message );
				}
				rtMap.put("step", "3");
				return rtMap;
			}else {//如果进入这里说明验证信息发送失败，需要进行异常处理
				
				String message = messageSource.getMessage(result.getI18nKey(), result.getI18nArgs(), WebContext.getLocale());
				return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, message);
			}
		} else {
			//无效的访问
			String message = getMessageSource().getMessage(PwdMgrKey.PWD_MGR_INVALID_ACCESS, EMPTY_ARGS , WebContext.getLocale());
			return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, message);
		}
	}
	
	/**
	 * @description	：获得验证码
	 * @param request
	 * @param data
	 * @param step
	 * @param retakeType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="retake")
	public Object retake(HttpServletRequest request,BindData data,String step, String retakeType){
		if ("3".equals(step)) {
			String username = (String) request.getSession().getAttribute(USER_NAME_SESSION_KEY);
			if(StringUtils.isEmpty(username)) {//防止人为直接访问此链接修改密码
				String message = messageSource.getMessage(PwdMgrKey.FAIL, new Object[] {}, WebContext.getLocale());
				return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, message);
			}else {
				data.setUsername(username);
			}
			ResultData result = strategyManager.getPwdRetakeStrategy(retakeType).retake(data);
			
			if(MESSAGE_STATUS_SUCCESS.equals(result.getStatus())){//如果找回密码操作成功，表示验证信息已经发送成功，等待输入验证码
				
				Map<String, Object> rtMap = ResultUtils.statusMap(MESSAGE_STATUS_SUCCESS, "");
				rtMap.put("retakeType", retakeType);
				rtMap.put("step", "4");
				//验证码的唯一ID;用于检测验证码的安全性
				rtMap.put("uuid", result.getMap().get("uuid"));
				return rtMap;
				
			}else {//如果进入这里说明验证信息发送失败，需要进行异常处理
				
				String message = messageSource.getMessage(result.getI18nKey(), result.getI18nArgs(), WebContext.getLocale());
				return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, message);
			}
		} else {
			//无效的访问
			String message = getMessageSource().getMessage(PwdMgrKey.PWD_MGR_INVALID_ACCESS, EMPTY_ARGS , WebContext.getLocale());
			return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, message);
		}
	}
	
	/**
	 * @description	： 修改密码表单
	 * @param request
	 * @param data
	 * @param step
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Object update(HttpServletRequest request,BindData data,String step) {
		if ("4".equals(step)) {
			
			String username = (String) request.getSession().getAttribute(USER_NAME_SESSION_KEY);
			
			if(StringUtils.isEmpty(username)) {//防止人为直接访问此链接修改密码
				String message = messageSource.getMessage(PwdMgrKey.FAIL, new Object[] {}, WebContext.getLocale());
				return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, message);
			}else {
				data.setUsername(username);
			}
			//更新密码
			ResultData result = updateProvider.update(data);
			
			if(MESSAGE_STATUS_SUCCESS.equals(result.getStatus())){//密码更新操作成功
				
				request.getSession().removeAttribute(USER_NAME_SESSION_KEY);
				
				String message = messageSource.getMessage(result.getI18nKey(), result.getI18nArgs(), WebContext.getLocale());
				Map<String, Object> rtMap = ResultUtils.statusMap( MESSAGE_STATUS_SUCCESS, message );
				rtMap.put("toURL", this.loginURL);
				return rtMap;
			}else {//如果进入这里说明验证信息发送失败，需要进行异常处理
				
				String message = messageSource.getMessage(result.getI18nKey(), result.getI18nArgs(), WebContext.getLocale());
				return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, message);
			}
		} else {
			//无效的访问
			String message = getMessageSource().getMessage(PwdMgrKey.PWD_MGR_INVALID_ACCESS, EMPTY_ARGS , WebContext.getLocale());
			return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, message);
		}
	}
}