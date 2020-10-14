package com.woshidaniu.api.action;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.api.utils.TicketTokenUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.constant.BaseConstant;
import com.woshidaniu.common.utils.ResultUtils;
import com.woshidaniu.entities.TicketTokenModel;
import com.woshidaniu.service.common.ICommonQueryService;

/**
 * 
 *@类名称		： TicketTokenAction.java
 *@类描述		： 提供票据登录的Token获取，校验，刷新接口
 *<pre>
 *	仿微信实现系统间的认证接口对接规范
 *	以下方法是为了提供其他系统的安全无密码登录或不需要登录即可访问数据接口
 *	
 *	1、为每个应用分配appid和secret值
 *	
 *	appid是唯一的值
 *	secret值是RSA的私钥，验证方法会通过对应的公钥进行验证
 *	
 *	
 *	2、访问获取access_token的路径通过获取授权access_token值
 *	
 *  #获取授权access_token
 *	access_token_get_url = http://niutal.edu.cn/api/ticket_access_token?appid={0}
 *	#检验授权凭证（access_token）是否有效
 *	access_token_valid_url =  http://niutal.edu.cn/api/ticket_valid_token?openid={0}&token={1}
 *
 *</pre>
 *
 *@创建人		：kangzhidong
 *@创建时间	：Sep 7, 2016 1:58:34 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 */
@SuppressWarnings("serial")
public class TicketTokenAction extends BaseAction implements ModelDriven<TicketTokenModel> {

    protected TicketTokenModel model = new TicketTokenModel();
     
    @Resource
    protected ICommonQueryService queryService;

    	
   /**
    * 
    *@描述		：获取授权access_token值
    *@创建人		: kangzhidong
    *@创建时间	: Sep 7, 20162:06:10 PM
    *@return
    *@修改人		: 
    *@修改时间	: 
    *@修改描述	:
    */
   public String access_token() {
		try {
			// 学校代码
			String xxdm = BaseConstant.XXDM;
			// 业务系统ID
			String appid = StringUtils.getSafeStr(model.getAppid(), getRequest().getParameter("appid"));
			// 验证appid
			if (!TicketTokenUtils.validAppid(xxdm, appid)) {
				// 无效的appid值
				getValueStack().set(DATA,ResultUtils.tokenMap("0", getText("login.tickit.appid")));
				return DATA;
			}
			// RSA的私钥，验证方法会通过对应的公钥进行验证
			String secret = StringUtils.getSafeStr(model.getSecret(), getRequest().getParameter("secret"));
			// 数据库时间
			String dbTime = getQueryService().getDatabaseTime();
			//返回token
			getValueStack().set(DATA, ResultUtils.tokenMap("1", TicketTokenUtils.genToken(appid , secret ,BaseConstant.XXDM , dbTime )));
		} catch (Exception e) {
			getValueStack().set(DATA, ResultUtils.tokenMap("0", "token值生成失败."));
		}
    	return DATA;
   }

   /**
    * 
    *@描述		：校验token有效期
    *@创建人		: kangzhidong
    *@创建时间	: Sep 7, 20164:19:37 PM
    *@return
    *@修改人		: 
    *@修改时间	: 
    *@修改描述	:
    */
   public String valid_token() {
		// 学校代码
		String xxdm = BaseConstant.XXDM;
		// 系统双方约定的秘钥:基于Base64加密的值
		String token = StringUtils.getSafeStr(model.getToken(), getRequest().getParameter("token"));
		// 数据库时间
		String dbTime = getQueryService().getDatabaseTime();
	   // 验证token
		if (!TicketTokenUtils.validToken(xxdm, token, dbTime)) {
			// 无效的token值
			getValueStack().set(DATA, ResultUtils.tokenMap("0", getText("login.tickit.token")));
		}else{
			getValueStack().set(DATA, ResultUtils.tokenMap("1", "success"));
		}
		return DATA;
   }

	public ICommonQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(ICommonQueryService queryService) {
		this.queryService = queryService;
	}

	@Override
	public TicketTokenModel getModel() {
		return model;
	}
	
}
