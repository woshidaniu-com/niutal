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
 *@类名称		： EmailDataOutputProvider.java
 *@类描述		：邮件发送服务提供者
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月7日 下午4:00:12
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class EmailDataOutputProvider implements DataOutputProvider, InitializingBean  {

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
	public boolean output(BindData data) throws Exception {
		
		Properties props = getPropsProvider().props();
	 
		//检查参数配置
		if(!props.containsKey(PwdMgrKey.PWD_MAIL_SUBJECT)){
			throw new IllegalArgumentException("密码找回参数: " + PwdMgrKey.PWD_MAIL_SUBJECT + " not found. ");
		}
		if(!props.containsKey(PwdMgrKey.PWD_MAIL_CONTENT)){
			throw new IllegalArgumentException("密码找回参数: " + PwdMgrKey.PWD_MAIL_CONTENT + " not found. ");
		}
		
		//构造消息体
		MessageBody message = new MessageBody(MessageType.EMAIL, String.valueOf(sequence.nextId()) );
		
		// 设置收件人的邮箱
		String sendTo = (String) data.getMap().get(PwdStrategy.PWD_RETAKE_BY_EMAIL);
		message.getBody().put(MessageKey.MAIL_SENDTO, sendTo);
		
		// 设置邮件标题
		message.getBody().put(MessageKey.MAIL_SUBJECT, props.getProperty(PwdMgrKey.PWD_MAIL_SUBJECT));
		
		// 设置邮件的内容体
        String content = PatternFormatUtils.format(props.getProperty(PwdMgrKey.PWD_MAIL_CONTENT), data);
		message.getBody().put(MessageKey.MAIL_CONTENT, content);
		
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
