package com.woshidaniu.ms.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.ms.api.MessageBody;
import com.woshidaniu.ms.api.MessageKey;
import com.woshidaniu.ms.api.MessageType;
import com.woshidaniu.ms.api.event.EmailPushEvent;

@Controller
@RequestMapping(value = "/ms/mailclient/")
public class EmailClientTestController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(EmailClientTestController.class);
	
	@Autowired
	protected ApplicationContext applicationContext;
	
	//安全密码，当表单提交携带此随机生成的密码，则会返回具体异常错误信息，方便排查问题
	//如果在配置文件设置一个可配置项，则需要重启，这里不需要重启，只需要登录系统所在linux，看一下日志输出的skey
	//然后在页面表单的隐藏域设置此sky即可
	private final String skey = UUID.randomUUID().toString();

	@RequiresPermissions("ms-mailclient:test")
	@RequestMapping(value="mailClinetTestIndex")
	public String smsTestIndex(HttpServletRequest request){
		
		log.info("system generate skey:{}",this.skey);
		request.setAttribute("skey", System.currentTimeMillis());
		
		return "/ms/email_test";
	}
	
	@RequiresPermissions("ms-mailclient:test")
	@RequestMapping(value="mailClinetTestSend")
	@ResponseBody
	public Map<String,String> smsTestSend(//
			@RequestParam(name="sendTo",required=true)String sendTo,//
			@RequestParam(name="subject",required=true)String subject,//
			@RequestParam(name="content",required=true)String content,//
			@RequestParam(name="skey",required=false)String skey
		){//
		
		MessageBody data = new MessageBody(MessageType.EMAIL, UniqID.getInstance().getUniqIDHash());
		Map<String, String> messageBody = new HashMap<String, String>();
		messageBody.put(MessageKey.MAIL_SENDTO, sendTo);
		messageBody.put(MessageKey.MAIL_SUBJECT, subject);
		messageBody.put(MessageKey.MAIL_CONTENT, content);
		messageBody.put(MessageKey.MAIL_ENCODING, "UTF-8");
		data.setBody(messageBody);
		try {
			applicationContext.publishEvent(new EmailPushEvent(this, data));
			
			Map<String,String> result = new HashMap<String,String>();
			result.put("status", "success");
			result.put("message", "send success!!!");
			return result;
			
		} catch (Exception e) {
			
			if(!this.skey.equals(skey)){
				Map<String,String> result = new HashMap<String,String>();
				result.put("status", "fail");
				result.put("message", "send fial!!!");
				return result;
				
			}else{
				String msg = "send fial cause :" + e.getMessage();
				
				log.info("match right skey , render cause message:"+msg);
				Map<String,String> result = new HashMap<String,String>();
				result.put("status", "fail");
				result.put("message", msg);
				return result;
			}
		}
	}
}
