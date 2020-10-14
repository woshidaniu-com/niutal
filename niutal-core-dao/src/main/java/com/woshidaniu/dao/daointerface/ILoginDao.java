package com.woshidaniu.dao.daointerface;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.entities.LoginModel;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：用户登录
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月17日下午2:35:25
 */
@Repository("loginDao")
public interface ILoginDao extends BaseDao<LoginModel>{

	
	
	
	/**
	 * 
	 * <p>方法说明：根据用户名和密码查询用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月17日下午2:35:50<p>
	 * @param model
	 * @return
	 */
	public User getUserInfo(LoginModel model);
	
	
	/**
	 * 
	 * <p>方法说明：学生用户登录查询<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月17日下午2:35:58<p>
	 * @param model
	 * @return
	 */
	public User getStudentInfo(LoginModel model);
	
	/**
	 * 停用账户
	 * @param account
	 */
	@Transactional
	public void disableAccount(@Param("account")String account);
	
	
	
}
