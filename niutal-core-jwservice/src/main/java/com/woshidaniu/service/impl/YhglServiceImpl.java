package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.daointerface.ICommonSqlDao;
import com.woshidaniu.daointerface.IJsglDao;
import com.woshidaniu.daointerface.IYhglDao;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.YhglModel;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.security.algorithm.MD5Codec;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.service.utils.YhglUtils;
/**
 * 
 *@类名称:YhglServiceImpl.java
 *@类描述：用户管理业务处理实现类
 *@创建人：kangzhidong
 *@创建时间：2015-1-9 下午06:34:02
 *@版本号:v1.0
 */
@After
@Logger(model="N0102",business="N010202")
@Service("yhglService")
public class YhglServiceImpl extends CommonBaseServiceImpl<YhglModel, IYhglDao> implements IYhglService{
	
	//角色管理dao接口
	@Resource
	private IJsglDao jsglDao;
	//公共查询dao接口
	@Resource
	private ICommonSqlDao commonSqlDao;
	@Resource
	private IYhglDao dao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
    	super.setDao(dao);
	}
	
	@Comment
	public YhglModel getModel(YhglModel t) {
		YhglModel model =  getDao().getModel(t);
		/*查询用户角色信息*/
		List<JsglModel> yhJs = getDao().cxJsdmListByYhm(model);
		List<String> jsList = new ArrayList<String>();
		for (JsglModel jsglModel : yhJs) {
			jsList.add(jsglModel.getJsdm());
		}
		model.setJs_id_list(jsList);
		return model;
	}
	
	// 查询角色列表
	public List<JsglModel> cxJsdmList(YhglModel model) {
		return getDao().cxJsdmListByYhm(model);
	}

	// 查询角色列表
	public List<JsglModel> getTreeGridJsdmList(YhglModel model) {
		model.setJsgybj("1");
		return getDao().getTreeGridJsdmList(model);
	}
	
	/* 增加用户信息：步骤1：增加用户信息
	// 增加用户信息：步骤2：增加用户角色信息
	// 增加用户信息：步骤3：增加用户数据范围组:在切面中完成
	*/
	@Comment(recordSQL=true)
	public void zjYhxx(YhglModel model)  {
		model.setJgdm(model.getJg_id());
		//如果有学生角色
		if(ArrayUtils.contains(model.getCbvjsxx(), "xs")){
			model.setSjly("JW_XJGL_XSJBXXB");
			//学生
			model.setYhlx("student");
		}else{
			model.setSjly("jw_jg_jzgxxb");
			//老师
			model.setYhlx("teacher");
		}
		//执行更新
		getDao().zjYhxx(model);
		
	}
	
	/*// 修改用户信息：步骤1：修改用户信息
	// 修改用户信息：步骤2：删除取消勾选的用户所属角色信息
	// 修改用户信息：步骤3：增加新勾选的用户所属角色信息
	// 修改用户信息：步骤4：删除用户取消的所属角色对应的数据范围组:在切面中完成
	// 修改用户信息：步骤5：增加用户新勾选的所属角色对应的数据范围组:在切面中完成
	*/
	@Comment(recordSQL=true)
	public void xgYhxx(YhglModel model)  {
		
		model.setJgdm(model.getJg_id());
		//根据操作人ID,用户代码查询获得当前操作人的角色集合与被操作人的角色集合的交集
		List<JsglModel> jsList = getJsglDao().cxKczjsxxList(model);
		List<String> deleteList = YhglUtils.getDeleteList(model.getCbvjsxx(), jsList);
		List<String> addList = YhglUtils.getNewList(model.getCbvjsxx(), jsList);
		if(BlankUtils.isBlank(model.getCbvjsxx())){
			//如果页面没勾选任何角色，但是操作的用户却有着当前操作人可操作的角色，表示页面取消了角色；
			if(!BlankUtils.isBlank(jsList)){
				for (JsglModel jsModel : jsList) {
					deleteList.add(jsModel.getJsdm());
				}
			}
		}
		model.setDeleteList(deleteList);
		model.setQueryList(addList);
		//执行更新
		getDao().xgYhxx(model);
	}

	/**
	 *	删除用户信息：步骤1：批量删除用户信息
	 *	删除用户信息：步骤2：批量删除用户角色信息
	 */
	@Comment(recordSQL=true)
	public void scYhxx(YhglModel model)  {
		String pkValue = model.getPkValue();
		if(!BlankUtils.isBlank(pkValue)){
			model.setDeleteList(CollectionUtils.arrayToList(pkValue.split(",")));
			// 删除用户信息;用户角色信息
			getDao().scYhxx(model);
		}
	}
	
	/**
	 * 设置用户所属角色 ：步骤1：删除取消勾选的用户所属角色信息
	 * 					步骤2：增加新勾选的用户所属角色信息
	 */
	@Comment(recordSQL=true)
	public synchronized void szSsjs(YhglModel model)  {
		/**
		 * 因为这里可能涉及多个管理员对同一个人进行授权；需要注意以下情形：
		 * 
		 * 用户c有角色：1，3，4，5
		 * 用户a有角色：1，3，6，8
		 * 用户b有角色：4，5，7，9
		 * 
		 * 1、如果a给c授权：打开页面会看到 1,3选中：
		 * 
		 * 此时如果a选择角色：1，6去除 3；则c的角色为：1，6，4，5
		 * 
		 * 2、如果b给c授权：打开页面会看到 4,5选中：
		 * 
		 * 此时如果b选择角色：7，9 去除 4,5；则c的角色为：1，6，7，9
		 * 
		 * 鉴于以上情形；这里必须是对同一用户进行角色变更需要加锁
		 * 
		 */
		//根据操作人ID,用户代码查询获得当前操作人的角色集合与被操作人的角色集合的交集
		List<JsglModel> jsList = jsglDao.cxKczjsxxList(model);
		List<String> deleteList = YhglUtils.getDeleteList(model.getCbvjsxx(), jsList);
		List<String> addList = YhglUtils.getNewList(model.getCbvjsxx(), jsList);
		if(BlankUtils.isBlank(model.getCbvjsxx())){
			//如果页面没勾选任何角色，但是操作的用户却有着当前操作人可操作的角色，表示页面取消了角色；
			if(!BlankUtils.isBlank(jsList)){
				for (JsglModel jsModel : jsList) {
					deleteList.add(jsModel.getJsdm());
				}
			}
		}
		
		//有更新
		if(!BlankUtils.isBlank(addList) || !BlankUtils.isBlank(deleteList) ){
			model.setDeleteList(deleteList);
			model.setQueryList(addList);
			// 更新用户角色信息
			getDao().szSsjs(model);
		}
	}
	
	// 密码初始化
	@Comment(recordSQL=true)
	public void mmCsh(YhglModel model)  {
		
		String pkValue = model.getPkValue();
		if (!BlankUtils.isBlank(pkValue)) {
			//用戶信息
			model.setQueryList(Arrays.asList(pkValue.split(",")));
			
			getDao().mmCsh(model);
		}
		
	}
	
	/***
	 * majun 20140723修改，此方法首页修改密码需要用到
	 */
	 @Comment
	 public void xgMm(YhglModel model)  {
		model.setKl(MD5Codec.encrypt(model.getMm()));// 加密
		getDao().xgMm(model);
	 }

	// 根据角色代码查询用户
	public List<YhglModel> cxYhByJsdm(YhglModel model)  {
		return getDao().cxYhByJsdm(model);
	}
	
	public List<YhglModel> cxYhByJgdm(YhglModel model){
        return getDao().cxYhByJgdm(model);
    }
 
	/**
	 * 获取用户ID 根据职工号
	 */
	public YhglModel getModel(String zgh){
		if(StringUtils.isEmpty(zgh)){
			return null;
		}
		YhglModel yhglModel=new YhglModel();
		yhglModel.setYhm(zgh);
		return getDao().getModel(yhglModel);
	}
	
	/**
	 * 验证用户名称
	 * @param zgh
	 * @param mm
	 * @return
	 */
	public boolean checkYhMm(String zgh, String mm) {
		if(StringUtils.isEmpty(zgh) || StringUtils.isEmpty(mm)){
			return false;
		}
		YhglModel yhglModel = new YhglModel();
		yhglModel.setYhm(zgh);
		// 加密后验证
		yhglModel.setKl(MD5Codec.encrypt(mm));
		return getDao().getCount(yhglModel) > 0;
	}
	
	
	/**
	 * 根据用户名及功能模块代码查询是否有权限
	 * @param zgh
	 * @param mm
	 * @return
	 */
	public boolean checkPrivilege(String yhm, String gnmkdm) {
		if(StringUtils.isEmpty(yhm) || StringUtils.isEmpty(gnmkdm)){
			return false;
		}
		YhglModel yhglModel = new YhglModel();
		yhglModel.setYhm(yhm);
		yhglModel.setGnmkdm(gnmkdm);
		return getDao().checkPrivilege(yhglModel) > 0;
	}

	public ICommonSqlDao getCommonSqlDao() {
		return commonSqlDao;
	}

	public void setCommonSqlDao(ICommonSqlDao commonSqlDao) {
		this.commonSqlDao = commonSqlDao;
	}

	public IJsglDao getJsglDao() {
		return jsglDao;
	}

	public void setJsglDao(IJsglDao jsglDao) {
		this.jsglDao = jsglDao;
	}


}
