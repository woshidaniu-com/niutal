package com.woshidaniu.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.constant.BaseConstant;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.utils.RoleUtils;
import com.woshidaniu.daointerface.IAncdDao;
import com.woshidaniu.daointerface.IJsgnmkDao;
import com.woshidaniu.entities.AncdModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IAncdService;
import com.woshidaniu.service.utils.NavMenuUtils;
import com.woshidaniu.util.local.LocaleUtils;


/**
 * 
* 
* 类名称：JcsjServiceImpl 
* 类描述： 按钮菜单业务处理实现类
* 创建人：yijd
* 创建时间：2012-4-25 上午10:22:13 
* 修改人：yijd
* 修改时间：2012-4-25 上午10:22:13 
* 修改备注： 
* @version 
*
 */
@Service("ancdService")
public class AncdServiceImpl extends CommonBaseServiceImpl<AncdModel, IAncdDao> implements IAncdService {
	
	@Resource
	private IAncdDao dao;
	@Resource
	private IJsgnmkDao jsgnmkDao;
	
	public IJsgnmkDao getJsgnmkDao() {
		return jsgnmkDao;
	}

	public void setJsgnmkDao(IJsgnmkDao jsgnmkDao) {
		this.jsgnmkDao = jsgnmkDao;
	}

	public List<AncdModel> cxAncd(AncdModel ancdModel,User user){
		List<AncdModel> list=null;
		if(user!=null && "student".equals(user.getYhlx())){
			list= dao.cxAncdXs(ancdModel);
		}else{
			list= dao.cxAncdLs(ancdModel);
		}
		return list;
	}

	public List<AncdModel> cxAncd(User user, String path) {
		AncdModel ancdModel= new AncdModel();
		if(user!=null){
			ancdModel.setDyym(path);
			ancdModel.setYhm(user.getYhm());
			ancdModel.setJsdm(user.getJsdm());
		}
		List<AncdModel> list=null;
		if(user!=null && "student".equals(user.getYhlx())){
			list= dao.cxAncdXs(ancdModel);
		}else{
			list=dao.cxAncdLs(ancdModel);
		}
		return list;
	}
	
	@Override
	public List<AncdModel> cxButtonsList(User user, String gnmkdm) {
		AncdModel model =  new AncdModel();
		model.setYhlx(user.getYhlx());//用户类型
		model.setGnmkdm(gnmkdm);// 功能代码
		model.setYhm(user.getYhm());//用户名
		model.setJsdm(user.getJsdm());//角色代码
		List<AncdModel> buttonsList = dao.cxUserRoleButtonsList(model);
		if(BlankUtils.isBlank(buttonsList)){
			buttonsList = dao.cxButtonsList(model);
		}
		return buttonsList;
	}
	
	@Override
	public List<AncdModel> cxButtonGroupList(User user, String gnmkdm) {
		AncdModel model =  new AncdModel();
		model.setYhlx(user.getYhlx());//用户类型
		model.setGnmkdm(gnmkdm);// 功能代码
		model.setYhm(user.getYhm());//用户名
		model.setJsdm(user.getJsdm());//角色代码
		return dao.cxButtonGroupList(model);
	}
	
	public String getMenu(String yhm, String jsdm) {
		String meun = "";
		//教师角色，学生角色的功能菜单比较特殊;单独处理
		if( RoleUtils.isStudent(jsdm) || RoleUtils.isTeacher(jsdm) ){
			//查询学生或教师功能菜单
			List<HashMap<String, String>> list =  getJsgnmkDao().getJsgnList(jsdm, "cxcd" ,LocaleUtils.getLocaleKey());
			meun  =  NavMenuUtils.getStudentOrTeacherNavMenuList(list);
		}else{
			//系统功能模块代码，该值决定当前运行系统会显示的菜单
			String xtgnmkdm = StringUtils.getSafeStr(BaseConstant.SYSTEM_GNMKDM, "jwglxt");
			List<String> topNavMenuList = dao.getTopNavMenuList(xtgnmkdm);
			//(1)先取用户角色个人的菜单
			List<BaseMap> userNavMenuTreeList = dao.getUserRoleNavMenuTreeList(yhm, jsdm, LocaleUtils.getLocaleKey());
			//(2)先取用户角色个人的菜单为空，则取角色的菜单，这样权限细化到个人
			if(BlankUtils.isBlank(userNavMenuTreeList)){   
				userNavMenuTreeList = dao.getUserNavMenuTreeList(yhm, jsdm, LocaleUtils.getLocaleKey());
			}
			StringBuilder html  = NavMenuUtils.getRoleMenuList(topNavMenuList , userNavMenuTreeList);
			meun  =  html.toString();
		}
		return meun;
	}

	@Override
	public List<AncdModel> cxLinkList(String[] gnmkdms) {
		return dao.cxLinkList(gnmkdms, LocaleUtils.getLocaleKey());
	}
	
}
