package com.woshidaniu.pwdmgr.api.provider.def;

import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;

import com.woshidaniu.basicutils.uid.Sequence;
import com.woshidaniu.format.utils.PatternFormatUtils;
import com.woshidaniu.ms.api.MessageBody;
import com.woshidaniu.ms.api.MessageKey;
import com.woshidaniu.ms.api.MessageType;
import com.woshidaniu.ms.api.provider.MessageOutputProvider;
import com.woshidaniu.pwdmgr.api.PwdMgrKey;
import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.provider.DataOutputProvider;
import com.woshidaniu.pwdmgr.api.provider.PwdPropertiesProvider;
import com.woshidaniu.pwdmgr.api.strategy.PwdStrategy;
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
public class SMSDataOutputProvider implements DataOutputProvider, InitializingBean {

	protected MessageOutputProvider outputProvider;

	protected PwdPropertiesProvider propsProvider = null;

	//高性能ID生成器
	protected Sequence sequence = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if( sequence == null){
			sequence = new Sequence();
		}
	}
	
	@Override
	public String name() {
		return this.getClass().getName();
	}
	
	@Override
	public boolean output(BindData data)  throws Exception {
		
		Properties props = getPropsProvider().props();
		
		//检查参数配置
		if(!props.containsKey(PwdMgrKey.PWD_SMS_CONTENT)){
			throw new IllegalArgumentException("密码找回参数: " + PwdMgrKey.PWD_SMS_CONTENT + " not found. ");
		}
		
		//构造消息体
		MessageBody message = new MessageBody(MessageType.SMS, String.valueOf(sequence.nextId()) );
		
		// 接收人的手机号码
		String sendTo = (String) data.getMap().get(PwdStrategy.PWD_RETAKE_BY_PHONE);
		message.getBody().put(MessageKey.SMS_SENDTO, sendTo);
		
		// 短信的内容体
        String content = PatternFormatUtils.format(props.getProperty(PwdMgrKey.PWD_SMS_CONTENT), data);
		message.getBody().put(MessageKey.SMS_CONTENT, content);
		
		return getOutputProvider().output(message);
		
	}

	public PwdPropertiesProvider getPropsProvider() {
		return propsProvider;
	}

	public void setPropsProvider(PwdPropertiesProvider propsProvider) {
		this.propsProvider = propsProvider;
	}

	public MessageOutputProvider getOutputProvider() {
		return outputProvider;
	}

	public void setOutputProvider(MessageOutputProvider outputProvider) {
		this.outputProvider = outputProvider;
	}
	
	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}
	
}
