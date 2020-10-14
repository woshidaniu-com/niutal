package com.woshidaniu.monitor.service.provider;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.woshidaniu.api.conf.PropertiesProvider;
import com.woshidaniu.basicutils.TimeUtils;
import com.woshidaniu.basicutils.uid.Sequence;
import com.woshidaniu.freemarker.utils.FormatUtils;
import com.woshidaniu.monitor.api.provider.NoticeProvider;
import com.woshidaniu.ms.api.MessageBody;
import com.woshidaniu.ms.api.MessageKey;
import com.woshidaniu.ms.api.MessageType;
import com.woshidaniu.ms.api.event.EmailPushEvent;
import com.woshidaniu.ms.api.event.SmsPushEvent;
import com.woshidaniu.ms.api.provider.MessageOutputProvider;
import com.woshidaniu.web.context.WebContext;

import freemarker.template.TemplateException;

public class MemoryInfoNoticeProvider implements ApplicationContextAware, InitializingBean, NoticeProvider{

	protected ApplicationContext applicationContext = null;
	// 本地IP地址
	protected String hostIP = "127.0.0.1";
	// 本地主机名
	protected String hostName = "localhost";
	protected Map<String, Object> infoMap = new HashMap<String, Object>();
	protected String template = "系统预警：[IP地址:${info['host.ip']}、主机名:${info['host.name']}、应用:${info['host.app']}];您预设的CPU使用率预警阈值是${info['watch.cup.threshold']}%,当前使用率是${info['os.cpu.usage']}%;您预设的物理内存使用率预警阈值是${info['watch.ram.threshold']}%, 当前使用率是${info['os.ram.usage']}%;您预设的JVM使用率预警阈值是${info['watch.jvm.threshold']}%, 当前使用率是${info['jvm.memory.usage']}%;请及时处理系统预警。";
	
	@Resource(name = "wacthPropsProvider")
	protected PropertiesProvider propsProvider = null;
	
	@Resource(name = "emailMessageOutputProvider")
	protected MessageOutputProvider emailOutputProvider;
	
	@Resource(name = "smsMessageOutputProvider")
	protected MessageOutputProvider smsOutputProvider;
	
	//高性能ID生成器
	@Resource(name = "sequence")
	protected Sequence sequence = null;
	
	//上次通知时间：初始值为对象初始化时间
	protected long preNotice = System.currentTimeMillis();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if( sequence == null){
			sequence = new Sequence();
		}
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
		}
		if (null != addr) {
			try {
				hostIP = addr.getHostAddress();
			} catch (Exception e) {
			}
			try {
				hostName = addr.getHostName();
			} catch (Exception e) {
			}
		}
		infoMap.put("host.ip", hostIP);// 本地ip地址
		infoMap.put("host.name", hostName);// 本地主机名
		infoMap.put("host.app", WebContext.CONTEXT_PATH);// 
	}
	
	@Override
	public void notice(Map<String, Double> usage) {
		//通知时间
		preNotice = System.currentTimeMillis();
		// 获取相关参数
		Properties props = getPropsProvider().props();
		long peroid = TimeUtils.getTimeMillis(props.getProperty(NoticeProvider.NOTICE_PERIOD, "10m")).longValue();
		if("off".equalsIgnoreCase(props.getProperty(NoticeProvider.NOTICE_STATUS))
				|| ((System.currentTimeMillis() - preNotice) < peroid) ){
			return;
		};
		
		String cup_threshold = props.getProperty(NoticeProvider.CUP_THRESHOLD);
		String ram_threshold = props.getProperty(NoticeProvider.RAM_THRESHOLD);
		String jvm_threshold = props.getProperty(NoticeProvider.JVM_THRESHOLD);
		if((usage.get("os.cpu.usage") > Double.parseDouble(cup_threshold) / 100) 
				|| (usage.get("os.ram.usage") > Double.parseDouble(ram_threshold) / 100)
				|| (usage.get("jvm.memory.usage") > Double.parseDouble(jvm_threshold) / 100) ){
			
			try {
			
				//通知时间
				preNotice = System.currentTimeMillis();
				
				infoMap.putAll(usage);
				infoMap.put(NoticeProvider.CUP_THRESHOLD, cup_threshold);
				infoMap.put(NoticeProvider.RAM_THRESHOLD, ram_threshold);
				infoMap.put(NoticeProvider.JVM_THRESHOLD, jvm_threshold);
				
				Map<String, Object> rootMap = new HashMap<String, Object>();
				
				rootMap.put("info", infoMap);
				
				String notice_type = props.getProperty(NoticeProvider.NOTICE_TYPE, "email");
				if(notice_type.contains("email")){
					String mail_to = props.getProperty(NoticeProvider.MAIL_TO);
					this.outputEmail(mail_to, rootMap);
				} 
				if (notice_type.contains("sms")) {
					String sms_to = props.getProperty(NoticeProvider.SMS_TO);
					this.outputSms(sms_to, rootMap);
				} 
				if (notice_type.contains("app")) {
					
				}
				
				
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	protected void outputEmail(String mailto,Map<String, Object> rootMap)  throws TemplateException, IOException {
		//构造消息体
		MessageBody message = new MessageBody(MessageType.EMAIL, String.valueOf(sequence.nextId()) );
		// 设置收件人的邮箱
		message.getBody().put(MessageKey.MAIL_SENDTO, mailto);
		// 设置邮件标题
		message.getBody().put(MessageKey.MAIL_SUBJECT, "系统监控预警" );
		// 设置邮件的内容体
		//使用Freemarker模板生成Email的html内容
		String contentBody = FormatUtils.toStatic(rootMap, this.getClass(), "noticeEmail.ftl");
		message.getBody().put(MessageKey.MAIL_CONTENT, contentBody);
		
		//异步发送邮件
		getApplicationContext().publishEvent(new EmailPushEvent(this, message));
	}
	
	protected void outputSms(String sendTo,Map<String, Object> rootMap) throws TemplateException, IOException{
		
		//构造消息体
		MessageBody message = new MessageBody(MessageType.SMS, String.valueOf(sequence.nextId()) );
		
		// 接收人的手机号码
		message.getBody().put(MessageKey.SMS_SENDTO, sendTo);
		// 短信的内容体
		String content = FormatUtils.toTextStatic(rootMap, "monitor-notice-sms", template);
		message.getBody().put(MessageKey.SMS_CONTENT, content);
				
		//异步发送短信
		getApplicationContext().publishEvent(new SmsPushEvent(this, message));
		
	}
	
	
	public PropertiesProvider getPropsProvider() {
		return propsProvider;
	}

	public void setPropsProvider(PropertiesProvider propsProvider) {
		this.propsProvider = propsProvider;
	}


	public MessageOutputProvider getEmailOutputProvider() {
		return emailOutputProvider;
	}

	public void setEmailOutputProvider(MessageOutputProvider emailOutputProvider) {
		this.emailOutputProvider = emailOutputProvider;
	}


	public MessageOutputProvider getSmsOutputProvider() {
		return smsOutputProvider;
	}

	public void setSmsOutputProvider(MessageOutputProvider smsOutputProvider) {
		this.smsOutputProvider = smsOutputProvider;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
