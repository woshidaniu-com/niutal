package com.woshidaniu.service.svcinterface;

import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.LoginModel;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：用户登录
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月17日下午2:36:39
 */
public interface ILoginService extends BaseService<LoginModel>{

	
	
	/**
	 * 
	 * <p>方法说明：查询用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月17日下午2:36:50<p>
	 * @param model
	 * @return
	 */
	public User cxYhxx(LoginModel model) ;
	
	
	
}
