/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.entities;

import java.io.File;
import java.util.Map;

import com.woshidaniu.common.query.ModelBase;

/**
 *@类名称:EmailModel.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：Dec 24, 2015 4:14:29 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class EmailModel extends ModelBase {

	/**
	 * 邮件优先级(1:紧急 3:普通 5:低)
	 */
	private String priority;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件内容,普通文本或者html
	 */
	private String contentBody;
	/**
	 * 收件人名称
	 */
	private String userName;
	/**
	 * 收件人邮箱
	 */
	private String mailto;
	/**
	 * 抄送人邮箱
	 */
	private Map<String, String> mailcc;
	/**
	 * 密送人邮箱
	 */
	private Map<String, String> mailBcc;
	/**
	 * 嵌入图片
	 */
	private Map<String, File> inlineMap;
	/**
	 * 附件
	 */
	private Map<String, File> attached;
	
	/**
	 * 
	 */
	public EmailModel() {
	}

	/**
	 * @param priority	: 邮件优先级(1:紧急 3:普通 5:低)
	 * @param userName	: 收件人名称
	 * @param mailto		: 收件人邮箱
	 * @param subject 	: 邮件主题
	 * @param contentBody: 邮件内容,普通文本或者html
	 */
	public EmailModel(String priority, String subject, String contentBody,
			String userName, String mailto) {
		super();
		this.priority = priority;
		this.subject = subject;
		this.contentBody = contentBody;
		this.userName = userName;
		this.mailto = mailto;
	}

	/**
	 * @param priority	: 邮件优先级(1:紧急 3:普通 5:低)
	 * @param userName	: 收件人名称
	 * @param mailto	: 收件人邮箱
	 * @param mailcc	: 抄送人名称和邮箱
	 * @param subject 	: 邮件主题
	 * @param contentBody: 邮件内容,普通文本或者html
	 */
	public EmailModel(String priority, String subject, String contentBody,
			String userName, String mailto, Map<String, String> mailcc) {
		this.priority = priority;
		this.subject = subject;
		this.contentBody = contentBody;
		this.userName = userName;
		this.mailto = mailto;
		this.mailcc = mailcc;
	}
	
	/**
	 * @param priority	: 邮件优先级(1:紧急 3:普通 5:低)
	 * @param userName	: 收件人名称
	 * @param mailto	: 收件人邮箱
	 * @param mailcc	: 抄送人名称和邮箱
	 * @param mailBcc	: 秘送人名称和邮箱
	 * @param subject 	: 邮件主题
	 * @param contentBody: 邮件内容,普通文本或者html
	 */
	public EmailModel(String priority, String subject, String contentBody,
			String userName, String mailto, Map<String, String> mailcc,Map<String, String> mailBcc) {
		this.priority = priority;
		this.subject = subject;
		this.contentBody = contentBody;
		this.userName = userName;
		this.mailto = mailto;
		this.mailcc = mailcc;
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the contentBody
	 */
	public String getContentBody() {
		return contentBody;
	}

	/**
	 * @param contentBody
	 *            the contentBody to set
	 */
	public void setContentBody(String contentBody) {
		this.contentBody = contentBody;
	}

	/**
	 * @return the mailto
	 */
	public String getMailto() {
		return mailto;
	}

	/**
	 * @param mailto
	 *            the mailto to set
	 */
	public void setMailto(String mailto) {
		this.mailto = mailto;
	}
	
	/**
	 * @return the mailcc
	 */
	public Map<String, String> getMailcc() {
		return mailcc;
	}

	/**
	 * @param mailcc the mailcc to set
	 */
	public void setMailcc(Map<String, String> mailcc) {
		this.mailcc = mailcc;
	}

	/**
	 * @return the mailBcc
	 */
	public Map<String, String> getMailBcc() {
		return mailBcc;
	}

	/**
	 * @param mailBcc the mailBcc to set
	 */
	public void setMailBcc(Map<String, String> mailBcc) {
		this.mailBcc = mailBcc;
	}
	
	/**
	 * @return the inlineMap
	 */
	public Map<String, File> getInlineMap() {
		return inlineMap;
	}

	/**
	 * @param inlineMap the inlineMap to set
	 */
	public void setInlineMap(Map<String, File> inlineMap) {
		this.inlineMap = inlineMap;
	}

	/**
	 * @return the attached
	 */
	public Map<String, File> getAttached() {
		return attached;
	}

	/**
	 * @param attached
	 *            the attached to set
	 */
	public void setAttached(Map<String, File> attached) {
		this.attached = attached;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
