package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.EmailFromModel;
import com.woshidaniu.entities.EmailModel;
import com.woshidaniu.entities.EmailSettingModel;

/**
 * 
 *@类名称:IEmailSenderService.java
 *@类描述：基于Spring mail 的 Email发送服务接口
 *@创建人：kangzhidong
 *@创建时间：Dec 24, 2015 4:09:06 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IEmailSenderService extends BaseService<EmailModel> {

	/**
	 * 
	 *@描述：获取系统内置邮件账户信息和邮件服务器配置；niutal_xtgl_yjfszhxxb 和 niutal_xtgl_yjfwszb
	 *@创建人:kangzhidong
	 *@创建时间:Dec 28, 20155:20:58 PM
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<EmailFromModel> getEmailFromList();
	
	/**
	 * 
	 *@描述：获取系统内置邮件服务器配置；niutal_xtgl_yjfszhxxb 和 niutal_xtgl_yjfwszb
	 *@创建人:kangzhidong
	 *@创建时间:Dec 28, 20155:20:58 PM
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<EmailSettingModel> getEmailSettingList();
	
	/**
	 * 
	 *@描述：根据发送人信息和收件人信息,发送邮件
	 *@创建人:kangzhidong
	 *@创建时间:Dec 28, 20155:21:39 PM
	 *@param from
	 *@param email
	 *@return
	 *@throws Exception
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public boolean sendPlainMail(EmailFromModel from, EmailModel email) throws Exception;

	public boolean sendPlainMail(EmailModel email) throws Exception;

	public boolean sendMimeMail(EmailFromModel from, EmailModel email) throws Exception;

	public boolean sendMimeMail(EmailModel email) throws Exception;
	
}
