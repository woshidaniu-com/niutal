package com.woshidaniu.ms.api.provider.def;

import com.woshidaniu.ms.api.MessageBody;
import com.woshidaniu.ms.api.MessageKey;
import com.woshidaniu.ms.api.provider.MessageOutputProvider;
import com.woshidaniu.sms.client.SmsClient;
import com.woshidaniu.sms.client.factory.SmsClientFactory;
/**
 * 
 *@类名称		： SMSDataOutputProvider.java
 *@类描述		：短信发送服务提供者
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月7日 下午3:59:29
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class SMSMessageOutputProvider implements MessageOutputProvider {

	protected SmsClient smsClient;
	protected SmsClientFactory smsClientFactory;
	
	@Override
	public String name() {
		return this.getClass().getName();
	}
	
	@Override
	public boolean output(MessageBody data)  throws Exception {
		
		//检查发送参数
		if(data.getBody().get(MessageKey.SMS_SENDTO) == null){
			throw new IllegalArgumentException("短信发送参数: " + MessageKey.SMS_SENDTO + " not found. ");
		}
		if(data.getBody().get(MessageKey.SMS_CONTENT) == null){
			throw new IllegalArgumentException("短信发送参数: " + MessageKey.SMS_CONTENT + " not found. ");
		}
		
		// 接收人的手机号码
		String sendTo = (String) data.getBody().get(MessageKey.SMS_SENDTO);
		// 短信的内容体
        String content = (String) data.getBody().get(MessageKey.SMS_CONTENT);
        
        if(null == smsClient){
        	//工厂对象获取客户端之后执行发送
        	return getSmsClientFactory().getClient().send(content, sendTo);
        }
        //客户端直接发送
		return smsClient.send(content, sendTo);
	}

	public SmsClient getSmsClient() {
		return smsClient;
	}

	public void setSmsClient(SmsClient smsClient) {
		this.smsClient = smsClient;
	}

	public SmsClientFactory getSmsClientFactory() {
		return smsClientFactory;
	}

	public void setSmsClientFactory(SmsClientFactory smsClientFactory) {
		this.smsClientFactory = smsClientFactory;
	}
	
}
