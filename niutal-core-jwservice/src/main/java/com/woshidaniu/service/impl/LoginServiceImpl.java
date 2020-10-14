package com.woshidaniu.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.daointerface.IJsglDao;
import com.woshidaniu.daointerface.IJsgnmkDao;
import com.woshidaniu.daointerface.ILoginDao;
import com.woshidaniu.daointerface.IYhglDao;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.LoginModel;
import com.woshidaniu.security.algorithm.MD5Codec;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.ILoginService;

/**
 * 
* 
* 类名称：LoginServiceImpl 
* 类描述： 登录业务层接口实现类
* 创建人：hhy 
* 创建时间：2011-12-20 上午10:53:39 
* 修改人：hhy 
* 修改时间：2011-12-20 上午10:53:39 
* 修改备注： 
* @version 
*
 */
@Service("loginService")
public class LoginServiceImpl extends CommonBaseServiceImpl<LoginModel, ILoginDao> implements ILoginService {
	
	//登录逻辑dao接口
	@Resource
	private ILoginDao loginDao;
	//角色管理dao接口
	@Resource
	private IJsglDao jsglDao;
	//用户管理dao接口
	@Resource
	private IYhglDao yhglDao;
	//角色功能模块dao接口
	@Resource
	private IJsgnmkDao jsgnmkDao;
	
	private User getUser(LoginModel loginModel){
		User user = null;
		if (loginModel!=null){
			user = new User();
			user.setYhm(loginModel.getYhm());
			user.setXm(loginModel.getXm());
			user.setYhlx(loginModel.getYhlx());
			user.setJg_id(loginModel.getJg_id());
			user.setJgdm(loginModel.getJgdm());
			user.setSfqy(loginModel.getSfqy());
			user.setYhlybid(loginModel.getYhlybid());
			user.setXyzyxx(loginModel.getXyzyxx());
			user.setSfxnyh(loginModel.getSfxnyh());
			
			List<String> jsdms = new ArrayList<String>();
			//角色对象列表
			List<Object> jsList = new ArrayList<Object>();
			//若多种角色则采用角色切换的方式,这里按角色级别取最大级别
			List<JsglModel> jsglModels = getJsglDao().cxJsxxListByYhm(loginModel.getYhm());
			for(JsglModel m:jsglModels){
				jsdms.add(m.getJsdm());
				jsList.add(m);
			}
			user.setJsdm(jsdms.get(0));
			user.setJsdms(jsdms);
			user.setJsList(jsList);
			
			//查询当前角色的角色功能代码集合
            user.setJsgnmkdmList(getJsgnmkDao().getJsGnmkdmList(user.getJsdm()));
            
		} 
		return user;
	}
	
	public User cxYhxx(LoginModel model) {
		//密码不用空加密
		if (!StringUtils.isEmpty(model.getMm())){
			model.setMm(MD5Codec.encrypt(model.getMm()));
		}
		//用户登录查询
		LoginModel loginModel = getLoginDao().cxYhxx(model.getYhm(),model.getMm());
		//返回用户对象
		return getUser(loginModel);
	}
	
	public User cxYhxxWithoutPwd(LoginModel model) {
		//用户登录查询
		LoginModel loginModel = getLoginDao().cxYhxxWithoutPwd(model.getYhm());
		//返回用户对象
		return getUser(loginModel);
	}
	
	public User cxYhxxWithoutPwd(String yhm) {
		//用户登录查询
		LoginModel loginModel = getLoginDao().cxYhxxWithoutPwd(yhm);
		//返回用户对象
		return getUser(loginModel);
	}

	@Override
	public Map<String, String> cxYhxxStatus(String yhm, String mm) {
		/*
		一次性查询用户判断相关数值
		select (select count('1') from niutal_xtgl_yhb a where a.yhm = #{yhm}) num_1,
		       (select count('1') from niutal_xtgl_yhb b where b.yhm = #{yhm} and b.kl = #{mm} ) num_2,
		       (select count('1') from niutal_xtgl_yhjsb c where c.yhm = #{yhm}) num_3,
		       nvl((select to_char(nvl(d.sfqy,'0')) from niutal_xtgl_yhb d where d.yhm = #{yhm}),'0') num_4
		from dual*/
		if (!StringUtils.isEmpty(mm)){
			mm = MD5Codec.encrypt(mm);
		}
		return getLoginDao().cxYhxxStatus(yhm , mm);
	}
	
	@Override
	public Set<String> getPermissionsInfo(String yhm,String jsdm) {
		return getLoginDao().getPermissionsInfo(yhm,jsdm);
	}
	 
	public IJsgnmkDao getJsgnmkDao() {
		return jsgnmkDao;
	}

	public void setJsgnmkDao(IJsgnmkDao jsgnmkDao) {
		this.jsgnmkDao = jsgnmkDao;
	}
	

	public ILoginDao getLoginDao() {
		return loginDao;
	}


	public void setLoginDao(ILoginDao loginDao) {
		this.loginDao = loginDao;
	}


	public IJsglDao getJsglDao() {
		return jsglDao;
	}


	public void setJsglDao(IJsglDao jsglDao) {
		this.jsglDao = jsglDao;
	}


	public IYhglDao getYhglDao() {
		return yhglDao;
	}


	public void setYhglDao(IYhglDao yhglDao) {
		this.yhglDao = yhglDao;
	}
	
}
