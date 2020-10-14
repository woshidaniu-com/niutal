package com.woshidaniu.globalweb.action;


import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.dao.entities.BmdmModel;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.IBmdmService;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.util.xml.BaseDataReader;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * 类名称：JsglAction 
 * 类描述： 角色管理控制 创建人：Administrator 
 * 创建时间：2012-4-1 下午03:46:07
 * 修改人：Administrator 
 * 修改时间：2012-4-1 下午03:46:07 
 * 修改备注：
 * 
 * @version
 * 
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JsglAction extends BaseAction implements ModelDriven<JsglModel> {

	/**
	 * @Fields serialVersionUID :
	 */

	private static final long serialVersionUID = 1L;
	private JsglModel model = new JsglModel();// 全局MODEL
	
	@Autowired
	private IJsglService jsglService;//角色管理SERVICE
	@Autowired
	private IBmdmService bmdmService;//部门管理service
	private BaseLog baseLog = LogEngineImpl.getInstance();

	public void setValueStack() throws Exception{
		ValueStack vs = getValueStack();
		List<HashMap<String, String>> sfList = BaseDataReader.getCachedOptionList("isNot");
		vs.set("sfList", sfList);
	}
	
	/**
	 * 功能模块信息列表
	 * @throws
	 */
	private void setGndmValueStack() throws Exception{
	}

	/**
	 * 
	 * 方法描述: 角色查询 
	 * 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String cxJsxx() {
		try {

			setValueStack();
			setGndmValueStack();
			String key = getRequest().getParameter("message");
			getValueStack().set("message", getText(key));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 *  
	 * 方法描述: 增加角色 
	 * 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	@RequiresPermissions("jsgl:zj")
	public String zjJsxx() {
		try {
			setGndmValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 方法描述: 保存增加角色 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 弃用：改用ajax保存
	 * @throws
	 */
	@Deprecated
	@RequiresPermissions("jsgl:zj")  
	public String zjBcJsxx() { 

		try {
			User user = getUser();
			boolean result = false;
			String key = null;
			//角色名称重复检测
			if(jsglService.getCount(model) == 0){
				// 保存数据操作
				result = jsglService.insert(model);
				key = result ? "I99001" : "I99002";
				getValueStack().set("result", getText(key));
			}else{
				key = "I99002M";
				getValueStack().set("result", getText(key, new String[]{"角色名称重复"}));
			}
			setGndmValueStack();
			if (result) {
				// 记操作日志
				baseLog.insert(user, getText("log.message.ywmc",
						new String[] { "角色管理", "XG_XTGL_JSXXB" }),
						"系统管理",
						getText("log.message.czms", new String[] { "新增角色",
								"角色名称", model.getJsmc() }));

			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return "zjJsxx";
	}

	/**
	 * 
	 * <p>方法说明：增加角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月24日上午11:49:00<p>
	 * @return java.lang.String
	 */
	@RequiresPermissions("jsgl:zj")
	public String saveJsxx() { 

		try {
			// 保存数据操作
			boolean result = jsglService.insert(model);
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			getValueStack().set(DATA, key.toString());
			
			if (result) {
				User user = getUser();
				// 记操作日志
				baseLog.insert(user, getText("log.message.ywmc",
						new String[] { "角色管理", "XG_XTGL_JSXXB" }),
						"系统管理",
						getText("log.message.czms", new String[] { "新增角色",
								"角色名称", model.getJsmc() }));

			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	/**
	 * 
	 * 方法描述: 修改角色 
	 * 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	@RequiresPermissions("jsgl:xg")
	public String xgJsxx() {
		try {
			JsglModel jsglModel = new JsglModel();
			jsglModel.setJsdm(getRequest().getParameter("jsdm"));

			// 查询单个角色信息
			jsglModel = jsglService.getModel(jsglModel);

			try {
				BeanUtils.copyProperties(model, jsglModel);
			} catch (Exception e) {
				logException(e);
			}

			setGndmValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 方法描述: 保存修改角色
	 * 参数 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * @return 
	 * @throws
	 */
	@Deprecated
	@RequiresPermissions("jsgl:xg")
	public String xgBcJsxx() {
		try {
			boolean result = false;
			String key = null;
			//角色名称重复检测
			if(jsglService.getCount(model) == 0){
				result = jsglService.update(model);
				key = result ? "I99001" : "I99002";
				getValueStack().set("result", getText(key));
				// 查询单个角色信息
				JsglModel jsglModel = jsglService.getModel(model);
				BeanUtils.copyProperties(model, jsglModel);
			}else{
				key = "I99002M";
				getValueStack().set("result", getText(key, new String[]{"角色名称重复"}));
			}
			if (result) {
				// 记操作日志
				baseLog.update(getUser(), getText("log.message.ywmc",
						new String[] { "角色管理", "XG_XTGL_JSXXB" }),
						"系统管理",
						getText("log.message.czms", new String[] { "修改角色",
								"角色名称", model.getJsmc() }));
			}
			setGndmValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return "xgJsxx";
	}

	/**
	 * 
	 * <p>方法说明：修改角色信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月24日下午2:36:22<p>
	 * @return
	 */
	@RequiresPermissions("jsgl:xg")
	public String modifyJsxx() {
		try {
			//角色名称重复检测
			boolean	result = jsglService.update(model);
			MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
			getValueStack().set(DATA, key.toString());
			if (result) {
				// 记操作日志
				baseLog.update(getUser(), getText("log.message.ywmc",
						new String[] { "角色管理", "XG_XTGL_JSXXB" }),
						"系统管理",
						getText("log.message.czms", new String[] { "修改角色",
								"角色名称", model.getJsmc() }));
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	/**
	 * 
	 * 方法描述: 删除角色信息 
	 * 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	@RequiresPermissions("jsgl:sc")
	public String scJsxx() throws Exception {
		
		try {
//			User user = getUser();
//			// 删除角色信息
//			String pks    = getRequest().getParameter("ids");
//			model.setPkValue(pks);
//			boolean result = jsglService.scJsdmxx(model);
//			String message = result ? MessageUtil.getText("I99005") : MessageUtil.getText("I99009");
//			getValueStack().set(DATA, message);
//				// 记操作日志
//				String opDesc = getText("log.message.czms", new String[] {
//						"批量删除角色", "角色代码", model.getPkValue() });
//
//				baseLog.delete(user, getText("log.message.ywmc",
//						new String[] { "角色管理", "XG_XTGL_JSXXB" }),
//						"系统管理", opDesc);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}

	

	/**
	 * 
	 * 方法描述: 查询角色代码功能权限
	 * 参数 @return
	 * 参数说明
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	@RequiresPermissions("jsgl:gnsq")
	public String cxJsdmGnqx() {
//		try {
//			getValueStack().set("data", jsglService.getJsgnqxStr(model));
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
		return "data";
	}

	/**
	 * 查询机构代码类型库
	 */
	private void setJgdmStack() throws Exception{
		ValueStack vs = getValueStack();
		//查询部门代码列表
		List<BmdmModel> jgdmsList = bmdmService.getModelList(new BmdmModel());
		vs.set("jgdmsList", jgdmsList);
	}
	
	/**
	 * 角色用户授权页面
	 * @return
	 */
	@RequiresPermissions("jsgl:fpyh")
	public String jsyhSq(){	
		ValueStack vs = getValueStack();
		try {
			// 查询单个角色信息
			JsglModel jsglModel = jsglService.getModel(model);
			YhglModel yhglModel = new YhglModel();
			BeanUtils.copyProperties(yhglModel, model);
			vs.set("model", jsglModel);// 角色信息
			//查询所有部门列表
			setJgdmStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 角色用户授权提交操作
	 * @return
	 */
	@RequiresPermissions("jsgl:fpyh")
	public String jsyhSqSubmit(){
		User user = getUser();
		//角色代码
//		String jsdm = model.getJsdm();
		// 职工号
//		String zghs = model.getIds();
		
//		boolean result = jsglService.scJsdmxx(model);
//		String message = result ? MessageUtil.getText("I99005") : MessageUtil.getText("I99009");
//		getValueStack().set(DATA, message);
//		
//		// 记操作日志
//		String opDesc = getText("log.message.czms", new String[] {
//				"批量删除角色", "角色代码", model.getPkValue() });
//
//		baseLog.delete(user, getText("log.message.ywmc",
//				new String[] { "角色管理", "XG_XTGL_JSXXB" }),
//				"系统管理", opDesc);
		
		return DATA;
	}
	
	/**
	 * 
	 * 方法描述: 角色分配用户
	 * 管理 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	@RequiresPermissions("jsgl:fpyh")
	public String fpyhJs() {
		ValueStack vs = getValueStack();
		try {
			// 查询单个角色信息
			JsglModel jsglModel = jsglService.getModel(model);
			YhglModel yhglModel = new YhglModel();
			BeanUtils.copyProperties(yhglModel, model);
			vs.set("model", jsglModel);// 角色信息
			
			//查询所有角色列表
			List<JsglModel> jsModelList = jsglService.getModelList(new JsglModel());
			vs.set("jsModelList", jsModelList);
			
			//查询所有部门列表
			setJgdmStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 
	 * 方法描述: 保存角色分配用户管理 
	 * 参数 @return 参数说明 
	 * 返回类型 String 
	 * 返回类型
	 * 
	 * @throws
	 */
	@RequiresPermissions("jsgl:fpyh")
	public String fpyhSaveJs() {
//		try {
//			String param = getRequest().getParameter("yhCbv");
//			String[] ids = null;
//			if(StringUtils.isNotBlank(param)){
//				ids = param.split(",");	
//			}
//			model.setYhCbv(ids);
//			// 保存角色分配用户信息
//			boolean isSuccess = jsglService.saveJsyhfpxx(model);
//			String key = isSuccess ? "I99001" : "I99002";
//			getValueStack().set(DATA, getText(key));
//			if (isSuccess) {
//				// 记操作日志
//				baseLog.update(getUser(), getText("log.message.ywmc",
//						new String[] { "角色管理", "XG_XTGL_JSXXB" }),
//						"系统管理", getText("log.message.czms",
//								new String[] { "角色分配用户", "角色代码",
//										model.getJsdm() }));
//			}
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
		return DATA;
	}

	/**
	 * 
	 * 方法描述: 取消角色分配用户 
	 * 参数 @return 参数说明 
	 * 返回类型 String 
	 * 返回类型
	 * 
	 * @throws
	 */
	@RequiresPermissions("jsgl:fpyh")
	public String fpyhCancelJs() {
//		try {
//			String param = getRequest().getParameter("yhCbv");
//			String[] ids = null;
//			if(StringUtils.isNotBlank(param)){
//				ids = param.split(",");	
//			}
//			model.setYhCbv(ids);
//			// 保存角色分配用户信息
//			boolean isSuccess = jsglService.cancelJsyhfpxx(model, null);
//			String key = isSuccess ? "I99001" : "I99002";
//			getValueStack().set(DATA, getText(key));
//			if (isSuccess) {
//				// 记操作日志
//				baseLog.update(getUser(), getText("log.message.ywmc",
//						new String[] { "角色管理", "XG_XTGL_JSXXB" }),
//						"系统管理", getText("log.message.czms",
//								new String[] { "角色分配用户", "角色代码",
//										model.getJsdm() }));
//			}
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
		return DATA;
	}
	
	/**
     * 
     * 方法描述: 角色分配用户，未分配用户列表
     * 
     * @throws
     */
	@RequiresPermissions("jsgl:fpyh")
	public String fpyhWfpYhxx() throws Exception {
		QueryModel queryModel = model.getQueryModel();
		try {
			queryModel.setItems(jsglService.getPagedWfpyhList(model));
		} catch (Exception e) {
			logException(e);
		}
		getValueStack().set(DATA, queryModel);
		return DATA;
	}
        
	/**
      * 
      * 方法描述: 角色分配用户，已分配用户列表
      * 
      * @throws
    */
	@RequiresPermissions("jsgl:fpyh")
    public String fpyhYfpYhxx() throws Exception{
        QueryModel queryModel = model.getQueryModel();
        queryModel.setItems(jsglService.getPagedYfpyhList(model));
        getValueStack().set(DATA, queryModel);
        return DATA;
    }
            
	/**
	 * 
	 * 方法描述: 功能授权
	 *  参数 @return 
	 *  参数说明 
	 *  返回类型 String 返回类型
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequiresPermissions("jsgl:gnsq")
	public String gnsqJs() {
//		ValueStack vs = getValueStack();
//
//		try {
//			model.setZgh(getUser().getYhm());
//			// 查询单个角色信息
//			JsglModel jsglModel = jsglService.getModel(model);
//			String btns = jsglService.getJsgnqxStr(model);
//			// 如果是admin角色则展示所有功能模块,不做过滤
//			List<String> jsxx=(List<String>)getRequest().getSession().getAttribute(getUser().getYhm());
//			model.setList(jsxx);
//			if(!CollectionUtils.isEmpty(jsxx)){
//				for(String jsdm:jsxx){
//					if("admin".equals(jsdm)){
//						model.setList(null);
//					}
//				}
//			}
//			List<JsglModel> list = jsglService.getAllGnmkList(model);
//			vs.set("allGnmkList", list);// 所有功能模块
//			// 界面高度
//			int height = (list.size() * 29 + 36) < 800 ? 800
//					: (list.size() * 29 + 36);
//			vs.set("height", height);
//			vs.set("czans", btns);
//
//			vs.set("model", jsglModel);// 角色信息
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
		return SUCCESS;
	}
		
	/**
	 * 
	 * 根据开课学院，查询组织机构树
	 *  参数 @return 
	 *  参数说明 
	 *  返回类型 String 返回类型
	 * 
	 */
	public String getZzjg() {
		Map<String, String> map = new HashMap<String, String>();
		String kkxy = getRequest().getParameter("kkxy");
		String id = getRequest().getParameter("id");
		try {
			if (StringUtils.isEmpty(id)) {
				map.put("kkxy", kkxy);
				List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
				getValueStack().set(DATA, items);
			}else{
				String[] ids = StringUtils.split(id, '&');
				List<Map<String,Object>> items = this.getChildren(ids[0], ids[1]);
				getValueStack().set(DATA, items);
			}
		} catch (Exception e) {
			logException(e);
		}

		return DATA;
	}
	
	/**
	 * 分配用户查询用户信息
	 * @return
	 * @author HuangXiaoping created on 2012-7-18 
	 * @since niutal 1.0
	 */
	@RequiresPermissions("jsgl:fpyh")
	public String fpyhCxYhxx()throws Exception{
//	    YhglModel yhglModel=yhglService.getModel(model.getZgh());
//	    getValueStack().set(DATA,yhglModel);
	    return DATA;
	}
	
	/**
	 * 获取子节点数据内容
	 * @param id 节点ID
	 * @param parm 节点类型(学院、专业、年级、班级)
	 * @return
	 */
	private List<Map<String,Object>> getChildren(String id,String parm){
		Map<String, String> map = null;List<Map<String, Object>> items = null;
		try {
			if(!StringUtils.isEmpty(parm)){
				if (parm.equals("XY")) {
					map = new HashMap<String, String>();
					map.put("bmdm_id", id);
					items = new ArrayList<Map<String, Object>>();
					return items;
				}
		 }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;
	}
	

	public JsglModel getModel() {
		return model;
	}

	
	/**
	 * 
	 * <p>方法说明：V5角色授权<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月18日下午7:04:55<p>
	 * @return
	 */
	public String cxJsyhfpIndex(){	
		ValueStack vs = getValueStack();
		try {
			// 查询单个角色信息
			JsglModel jsglModel = jsglService.getModel(model);
			YhglModel yhglModel = new YhglModel();
			BeanUtils.copyProperties(yhglModel, model);
			vs.set("model", jsglModel);// 角色信息
			//查询所有部门列表
			setJgdmStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
}
