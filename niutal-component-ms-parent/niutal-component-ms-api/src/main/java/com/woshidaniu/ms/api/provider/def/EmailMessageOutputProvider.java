package com.woshidaniu.ms.api.provider.def;

import java.util.Properties;

import com.woshidaniu.javamail.JavaMailClient;
import com.woshidaniu.javamail.JavaMailKey;
import com.woshidaniu.javamail.conf.EmailBody;
import com.woshidaniu.ms.api.MessageBody;
import com.woshidaniu.ms.api.MessageKey;
import com.woshidaniu.ms.api.provider.MessageOutputProvider;
/**
 * 
 *@类名称		： EmailDataOutputProvider.java
 *@类描述		：邮件发送服务提供者
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月7日 下午4:00:12
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class EmailMessageOutputProvider implements MessageOutputProvider {

	protected JavaMailClient mailClient;
	
	@Override
	public String name() {
		return this.getClass().getName();
	}
	
	@Override
	public boolean output(MessageBody data) throws Exception {
		
		Properties props = mailClient.getPropsProvider().props();
		
		//检查参数配置
		if(props.get(JavaMailKey.MAIL_FROM_DESC) == null){
			throw new IllegalArgumentException("邮件客户端参数: " + JavaMailKey.MAIL_FROM_DESC + " not found. ");
		}
		if(props.get(JavaMailKey.MAIL_USER) == null){
			throw new IllegalArgumentException("邮件客户端参数: " + JavaMailKey.MAIL_USER + " not found. ");
		}
		
		//检查发送参数
		if(data.getBody().get(MessageKey.MAIL_SENDTO) == null){
			throw new IllegalArgumentException("邮件发送参数: " + MessageKey.MAIL_SENDTO + " not found. ");
		}
		if(data.getBody().get(MessageKey.MAIL_SUBJECT) == null){
			throw new IllegalArgumentException("邮件发送参数: " + MessageKey.MAIL_SUBJECT + " not found. ");
		}
		if(data.getBody().get(MessageKey.MAIL_CONTENT) == null){
			throw new IllegalArgumentException("邮件发送参数: " + MessageKey.MAIL_CONTENT + " not found. ");
		}
		
        //构建邮件对象
		EmailBody email = new EmailBody();
		
		email.setEncoding(props.getProperty(MessageKey.MAIL_ENCODING, "GB2312") );
		
		// 设置发件人信息
		email.setFrom(props.getProperty(JavaMailKey.MAIL_FROM_DESC), props.getProperty(JavaMailKey.MAIL_USER), false);
		// 设置收件人的邮箱
		String sendTo = (String) data.getBody().get(MessageKey.MAIL_SENDTO);
		email.setMailto(sendTo, sendTo);
		// 设置邮件标题
		email.setSubject((String) data.getBody().get(MessageKey.MAIL_SUBJECT));
        // 设置邮件的内容体
        String content = (String) data.getBody().get(MessageKey.MAIL_CONTENT);
        email.setContent(content);
		
        return mailClient.sendMime(email);
	}

	public JavaMailClient getMailClient() {
		return mailClient;
	}

	public void setMailClient(JavaMailClient mailClient) {
		this.mailClient = mailClient;
	}
	
}
