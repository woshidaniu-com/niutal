package com.woshidaniu.datarange.aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;

import com.woshidaniu.daointerface.ICommonQueryDao;
import com.woshidaniu.daointerface.IYhglDao;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.YhglModel;
import com.woshidaniu.datarange.dao.daointerface.IYhjsSjfwDao;
import com.woshidaniu.datarange.dao.entities.YhJssjfwModel;
import com.woshidaniu.basicutils.BlankUtils;
/**
 * 
 *@类名称		： DatarangeAspectInterceptor.java
 *@类描述		：数据范围切面：对增加，修改，删除用户；角色分配用户操作进行拦截进行相应的数据范围操作
 *@创建人		：kangzhidong
 *@创建时间	：Aug 25, 2016 3:50:48 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class DatarangeAspectInterceptor {

	//公共查询dao接口
	@Resource
	private ICommonQueryDao queryDao;
	@Resource
	private IYhjsSjfwDao yhsjfwDao;
	@Resource
	private IYhglDao yhglDao;
	
	@SuppressWarnings("unchecked")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {  
		Object result = joinPoint.proceed();
		String mathodName = joinPoint.getSignature().getName();
		if("setSelf".equalsIgnoreCase(mathodName)){
    		return result;
    	}
 		Object[] args = joinPoint.getArgs();
 		Map<String,Object> params = new HashMap<String, Object>();
		if("zjYhxx".equalsIgnoreCase(mathodName)){
			YhglModel model = (YhglModel)args[0];
			List<YhJssjfwModel> yhjssjfwList = new ArrayList<YhJssjfwModel>();
			YhJssjfwModel sjfw = null;
			if(!BlankUtils.isBlank(model.getCbvjsxx())){
				//循环新提交的角色
				for (String jsdm : model.getCbvjsxx()) {
					// 用户数据范围对象
					//保存用户,角色数据范围
					//如果机构代码为-1则为超级管理员
					//如果机构类别为1则为教学院系，其它为全校。
					sjfw = new YhJssjfwModel();
					
					if(!"-1".equals(model.getJg_id())){
						if(!"1".equals(model.getJglb())){
							sjfw.setSjfwztj("jg_id=-3");//代码所有学院数据权限
						}else{
							sjfw.setSjfwztj("jg_id="+model.getJg_id());
						}
					}else{
						sjfw.setSjfwztj("jg_id=-1");
					}
					sjfw.setSjfwzmc(model.getJgmc());
					sjfw.setYhm(model.getYhm());
					sjfw.setJs_id(jsdm);
					sjfw.setKzdx("xs");
					yhjssjfwList.add(sjfw);
				}
			}
			
			params.put("yhjssjfwList", yhjssjfwList);
			getYhsjfwDao().zjYhsjfwxx(params);
		}else if("xgYhxx".equalsIgnoreCase(mathodName)){
			YhglModel model = (YhglModel)args[0];
			List<String> addList = (List<String>) model.getQueryList();
			//有新角色
			if(!BlankUtils.isBlank(addList)){
				List<YhJssjfwModel> yhjssjfwList = new ArrayList<YhJssjfwModel>();
				YhJssjfwModel sjfw = null;
				for (String jsdm : addList) {
					//保存用户,角色数据范围
					sjfw = new YhJssjfwModel();
					sjfw.setSjfwz_id(getQueryDao().getSysGuid());
					sjfw.setSjfwzmc(model.getJgmc()+"【学生数据】");
					sjfw.setYhm(model.getYhm());
					sjfw.setJs_id(jsdm);
					sjfw.setKzdx("xs");
					sjfw.setSjfwztj("jg_id="+model.getJg_id());
					yhjssjfwList.add(sjfw);
					sjfw = new YhJssjfwModel();
					sjfw.setSjfwz_id(getQueryDao().getSysGuid());
					sjfw.setSjfwzmc(model.getJgmc()+"【课程数据】");
					sjfw.setYhm(model.getYhm());
					sjfw.setJs_id(jsdm);
					sjfw.setKzdx("kc");
					sjfw.setSjfwztj("jg_id="+model.getJg_id());
					yhjssjfwList.add(sjfw);
				}
				
				params.put("yhm", model.getYhm());
				params.put("deleteList", model.getDeleteList());
				params.put("yhjssjfwList", yhjssjfwList);
				getYhsjfwDao().xgYhsjfwxx(params);
			}
		}else if("scYhxx".equalsIgnoreCase(mathodName)){
			YhglModel model = (YhglModel)args[0];
			params.put("deleteList", model.getDeleteList());
			getYhsjfwDao().scYhsjfwxx(params);
		}else if("zjJsyhfpxx".equalsIgnoreCase(mathodName)){
			
			JsglModel model = (JsglModel)args[0];
			
			// 有数据增加
			if (!BlankUtils.isBlank(model.getYhm_list())) {
				
				// 循环新分配的用户名
				YhJssjfwModel sjfw = null;
				List<YhJssjfwModel> jsyhsjfwList = new ArrayList<YhJssjfwModel>();
				for (String yhm : model.getYhm_list()) {
					// 保存用户、角色数据范围
					YhglModel yhgl = new YhglModel();
					
					yhgl.setYhm(yhm);
					
					yhgl = getYhglDao().getModel(yhgl);

					// 机构不为空且不是是给学生角色分配学生
					if (!BlankUtils.isBlank(yhgl.getJg_id()) && !"xs".equals(model.getJsdm())) {

						// 保存用户,角色数据范围
						sjfw = new YhJssjfwModel();
						sjfw.setSjfwz_id(getQueryDao().getSysGuid());
						sjfw.setSjfwzmc(model.getJgmc() + "【学生数据】");
						sjfw.setYhm(yhm);
						sjfw.setJs_id(model.getJsdm());
						sjfw.setKzdx("xs");
						sjfw.setSjfwztj("jg_id=" + model.getJg_id());
						jsyhsjfwList.add(sjfw);

						sjfw = new YhJssjfwModel();
						sjfw.setSjfwz_id(getQueryDao().getSysGuid());
						sjfw.setSjfwzmc(model.getJgmc() + "【课程数据】");
						sjfw.setYhm(yhm);
						sjfw.setJs_id(model.getJsdm());
						sjfw.setKzdx("kc");
						sjfw.setSjfwztj("jg_id=" + model.getJg_id());
						jsyhsjfwList.add(sjfw);

					}

				}
				params.put("jsdm",model.getJsdm());
				params.put("jsyhsjfwList",jsyhsjfwList  );
				getYhsjfwDao().zjJsyhfpSjfwxx(params);
			}
		}else if("scJsyhfpxx".equalsIgnoreCase(mathodName)){
			JsglModel model = (JsglModel)args[0];
			// 有数据删除
			if (!BlankUtils.isBlank(model.getYhm_list())) {
				params.put("yhm_list",model.getYhm_list());
				params.put("jsdm",model.getJsdm());
				getYhsjfwDao().scJsyhfpSjfwxx(params);
			}
		}
        return result;
	}

	
	public ICommonQueryDao getQueryDao() {
		return queryDao;
	}

	public void setQueryDao(ICommonQueryDao queryDao) {
		this.queryDao = queryDao;
	}

	public IYhjsSjfwDao getYhsjfwDao() {
		return yhsjfwDao;
	}

	public void setYhsjfwDao(IYhjsSjfwDao yhsjfwDao) {
		this.yhsjfwDao = yhsjfwDao;
	}

	public IYhglDao getYhglDao() {
		return yhglDao;
	}

	public void setYhglDao(IYhglDao yhglDao) {
		this.yhglDao = yhglDao;
	}
	
	

}
