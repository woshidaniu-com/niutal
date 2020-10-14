package com.woshidaniu.service.utils;


public class CommonUtils {

	/**
	 * 菜单权限处理
	 20140724 马俊注释，不用可以删除
	public static void cdqxcl() {
		HttpServletRequest request = ServletActionContext.getRequest();
		ValueStack vs = ServletActionContext.getValueStack(request);
		String path = ServletActionContext.getRequest().getServletPath();
		User user = SessionFactory.getUser();
		//获得当前参数中的角色代码
		String jsdm = request.getParameter("jsdm");
		//如果是空的去当前用户登录的角色代码
		if(StringUtil.isEmpty(jsdm)){
			jsdm = user.getJsdm();
		}
		// 菜单权限处理
		if (((IIndexService) ServiceFactory.getService("indexService")).yzQx(jsdm,path)) {
			// 查询session中是否已存在三级菜单权限，如果存在直接获取
			Object curPath = request.getSession().getAttribute(path);
			if (null != curPath) {
				vs.set("ancdModelList", curPath);
			} else {
				IAncdService service = (IAncdService) ServiceFactory
						.getService("ancdService");
				List<AncdModel> ancdModelList = service.cxAncd(user, path);
				vs.set("ancdModelList", ancdModelList);
				// 将三级操作功能权限放入到session中，注销后销毁，提升获取性能
				request.getSession().setAttribute(path, ancdModelList);
			}
		}
	}*/

}
