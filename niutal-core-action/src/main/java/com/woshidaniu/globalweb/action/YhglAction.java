package com.woshidaniu.globalweb.action;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.IBmdmService;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.service.svcinterface.IYhglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 
 * 
 * 类名称：YhglAction 类描述： 用户管理控制 创建人：Administrator 创建时间：2012-4-10 下午06:41:27
 * 修改人：Administrator 修改时间：2012-4-10 下午06:41:27 修改备注：
 * 
 * @version
 * 
 */
@Controller
@Scope("prototype")
public class YhglAction extends BaseAction implements ModelDriven<YhglModel> {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private YhglModel model = new YhglModel();


	private JsglModel jsglmodel = new JsglModel();
	
	@Autowired
	private IYhglService yhglService;

	@Autowired
	private IJsglService jsglService;
	
	
	@Autowired
	private IBmdmService bmdmService;
	
	private BaseLog baseLog = LogEngineImpl.getInstance();

//	/**
//	 *查询类型表
//	 * 
//	 * @throws Exception
//	 */
//	public void setValueStack() throws Exception {
//		ValueStack vs = getValueStack();
//
//		// 查询部门列表
//		//List<YhglModel> bmdmList = yhglService.getModelList(model);
//		//vs.set("bmdmList", bmdmList);
//
//		// 查询角色列表
////		model.setZgh(getUser().getYhm());
//		List<JsglModel> jsxxList = yhglService.cxJsdmList();
//		vs.set("jsxxList", jsxxList);
//
//		int size = jsxxList.size();
//		vs.set("col", size > 4 ? (size % 4 == 0 ? size / 4 : (size / 4 + 1)): 1);
//		// 岗位级别列表
//		//vs.set("gwjbList", jsglService.cxGwjbList());
//	}
//	
//	/**
//	 * 查询机构代码类型库
//	 */
//	public void setJgdmStack() throws Exception{
//		ValueStack vs = getValueStack();
//		//查询部门代码列表
//		List<BmdmModel> jgdmsList = bmdmService.getModelList(new BmdmModel());
//		vs.set("jgdmsList", jgdmsList);
//	}
//
//	/**
//	 * 
//	 * 方法描述: 用户信息查询 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:cx"})
//	public String cxYhxx() {
//		try {
//			if (QUERY.equals(model.getDoType())) {
//				QueryModel queryModel = model.getQueryModel();
//				List<YhglModel> pagedList = yhglService.getPagedList(model);
//				HtmlEncodeAnotationHandler.handle(pagedList);
//				queryModel.setItems(pagedList);
//				
//				getValueStack().set(DATA, queryModel);
//				return DATA;
//			}
//			setJgdmStack();
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//		return "cxYhxx";
//	}
//
//	/**
//	 * 
//	 * 方法描述: 增加用户信息 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:zjs"})
//	public String zjYhxx() {
//		try {
//			model.setSfqy("1");
//
//			setValueStack();
//			setJgdmStack();
//			List<HashMap<String, String>> sfList = BaseDataReader.getListOptions("isNot");
//			getValueStack().set("isNot", sfList);
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//
//		return "zjYhxx";
//	}
//
//	/**
//	 * 
//	 * 方法描述: 保存增加用户信息 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	@Deprecated
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:zj"})
//	public String zjBcYhxx() {
//		try {
//			User user = getUser();
//			// 密码加密
//			model.setMm(MD5Codec.encrypt(model.getMm()));
//			boolean result = yhglService.zjYhxx(model);
//			String key = result ? "I99001" : "I99002";
//			getValueStack().set("result", getText(key));
//			setValueStack();
//			if (result) {
//				// 记操作日志
//				baseLog.insert(user, getText("log.message.ywmc",
//						new String[] { "用户管理", "XG_XTGL_YHB" }),
//						"系统管理", getText("log.message.czms", new
//				 			String[] { "新增用户", "职工号", model.getZgh() }));
//			}
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//
//		return "zjYhxx";
//	}
//	
//	/**
//	 * 
//	 * <p>方法说明：用户增加<p>
//	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
//	 * <p>时间：2016年8月25日下午1:55:43<p>
//	 * @return
//	 */
//	public String saveYhxx() {
//		try {
//			User user = getUser();
//			// 密码加密
//			model.setMm(MD5Codec.encrypt(model.getMm()));
//			boolean result = yhglService.zjYhxx(model);
//			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
//			getValueStack().set(DATA,key.toString());
//			
//			if (result) {
//				// 记操作日志
//				baseLog.insert(user, getText("log.message.ywmc",
//						new String[] { "用户管理", "XG_XTGL_YHB" }),
//						"系统管理", getText("log.message.czms", new
//				 			String[] { "新增用户", "职工号", model.getZgh() }));
//			}
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//
//		return DATA;
//	}
//	
//
//	/**
//	 * 
//	 * 方法描述: 修改用户信息 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:xg"})
//	public String xgYhxx() {
//		try {
//			ValueStack vs = getValueStack();
//			// 查询单个用户信息
//			 YhglModel yhglModel = yhglService.getModel(model);
//
//			if (yhglModel != null){
//				BeanUtils.copyProperties(model, yhglModel);
//			}
//			
//			setValueStack();
//			setJgdmStack();
//			vs.set("model", model);
//
//			List<HashMap<String, String>> sfList = BaseDataReader.getListOptions("isNot");
//			getValueStack().set("isNot", sfList);
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//
//		return "xgYhxx";
//	}
//
//	/**
//	 * 
//	 * 方法描述: 保存修改用户信息 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	@Deprecated
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:xg"})
//	public String xgBcYhxx() {
//		try {
//
//			User user = this.getUser();
//			ValueStack vs = getValueStack();
//
//			// 修改用户信息
//			boolean result = yhglService.xgYhxx(model);
//			String key = result ? "I99001" : "I99002";
//			getValueStack().set("result", getText(key));
//
//			setValueStack();
//			vs.set("model", model);
//			if (result) {
//				// 记操作日志
//				baseLog.update(user, getText("log.message.ywmc",
//				 new String[] { "用户管理", "XG_XTGL_YHB" }),
//				 "系统管理", getText("log.message.czms", new
//				 String[] { "修改用户", "职工号", model.getZgh() }));
//			}
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//
//		return "xgYhxx";
//	}
//
//	
//	/**
//	 * 
//	 * <p>方法说明：修改用户信息<p>
//	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
//	 * <p>时间：2016年8月25日下午2:28:05<p>
//	 * @return
//	 */
//	public String modifyYhxx() {
//		try {
//			User user = getUser();
//
//			// 修改用户信息
//			boolean result = yhglService.xgYhxx(model);
//			MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
//			getValueStack().set(DATA,key.toString());
//
//			if (result) {
//				// 记操作日志
//				baseLog.update(user, getText("log.message.ywmc",
//				 new String[] { "用户管理", "XG_XTGL_YHB" }),
//				 "系统管理", getText("log.message.czms", new
//				 String[] { "修改用户", "职工号", model.getZgh() }));
//			}
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//		return DATA;
//	}
//	
//	
//	/**
//	 * 
//	 * 方法描述: 删除用户信息 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:sc"})
//	public String scYhxx() throws Exception {
//		User user = getUser();
//		String pks = getRequest().getParameter("ids");
//		model.setPkValue(pks);
//		boolean result = yhglService.scYhxx(model);
//		String key = result ? "I99005" : "I99006";
//		getValueStack().set(DATA, getText(key));
//		if (result) {
//			// 记操作日志
//			String opDesc = getText("log.message.czms", new String[] {
//					"批量删除用户", "职工号", pks });
//
//			
//			baseLog.delete(user, getText("log.message.ywmc", new
//			  String[] { "用户管理", "XG_XTGL_YHB" }), "系统管理",
//			  opDesc);
//			 
//		}
//		return DATA;
//	}
//
//	/**
//	 * 
//	 * 方法描述: 查看用户信息 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:ck"})
//	public String ckYhxx() {
//		try {
//			ValueStack vs = getValueStack();
//			// 查询单个用户信息
//			YhglModel yhglModel = yhglService.getModel(model);
//
//			if (yhglModel != null){
//				BeanUtils.copyProperties(model, yhglModel);
//			}
//			vs.set("model", model);
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//		return SUCCESS;
//	}
//
//	/**
//	 * 
//	 * 方法描述: 设置所属角色 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:szjs"})
//	public String szssjsYh() throws Exception {
//		//查询用户的已有角色信息
//		List<JsglModel> jsList= jsglService.getJsxxListByZgh(model.getZgh());
//		getValueStack().set("jsList", jsList);
//		return "szSsjs";
//	}
//
//	/**
//	 * 
//	 * @description: 查询角色信息和用户与角色相关的数据范围信息
//	 * @author : kangzhidong
//	 * @date : 2014-6-4
//	 * @time : 上午11:59:18 
//	 * @return
//	 * @throws Exception
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:cxjs"})
//	public String cxYhjs() throws Exception {
//		try {
//			
////			//查询目标用户所有角色的数据范围集合
////			Map<String,String> map = new HashMap<String,String>();
////			map.put("yh_id",model.getZgh());
////			List<YhjsfwModel> yhjsSjfw = yhjsfwService.cxSjfwYh(map);
////			
////			//查询属于当前登录用户的角色列表 
////			model.setZgh(getUser().getYhm());
////			List<JsglModel> jsxxList = yhglService.cxJsdmList(model);
////			
////			//循环取出角色数据范围,设置到数据范围字段中,供页面使用
////			StringBuilder builder = new StringBuilder();
////			for (JsglModel jsgl : jsxxList) {
////				builder.delete(0, builder.length());
////				for (YhjsfwModel model : yhjsSjfw) {
////					if (model.getJs_id().equals(jsgl.getJsdm())) {
////						builder.append(model.getSjfwzmc()).append(";");
////					}
////				}
////				if(builder.length()>0){
////					builder.deleteCharAt(builder.lastIndexOf(";"));
////				}
////				jsgl.setSjfwzmc(builder.toString());
////			}
////			
////			QueryModel queryModel = model.getQueryModel();
////			queryModel.setItems(jsxxList);
////			getValueStack().set(DATA, queryModel);
//
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//		return DATA;
//	}
//	
//	/**
//	 * 
//	 * 方法描述: 保存设置所属角色 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:szjs"})
//	public String szssjsSaveYh() throws Exception {
//		try {
//			// 修改用户所属角色
//			boolean result = yhglService.szSsjs(model);			
//			if (result) {
//				// 记操作日志
//				String opDesc = getText("log.message.czms", new String[] { "设置角色", "职工号", getRequest().getParameter("zgh") });
//				baseLog.update(getUser(), getText("log.message.ywmc", new  String[] { "用户管理", "XG_XTGL_YHB" }),  "系统管理", opDesc);
//			}
//			String key = result ? "I99001" : "I99002";
//			getValueStack().set(DATA, getText(key));
//			
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//		return DATA;
//	}
//
//	/**
//	 * 
//	 * 方法描述: 密码初始化 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:mmcsh"})
//	public String mmcsh() throws Exception {
//		// 对初始化的密码加密
//		User user = getUser();
//		String initialPassword = MessageUtil.getText(GlobalString.SYSTEM_INITIAL_PASSWORD_KEY);
//		if(StringUtils.isBlank(initialPassword)){
//			initialPassword = GlobalString.SYSTEM_INITIAL_PASSWORD;
//		}
//		model.setMm(MD5Codec.encrypt(initialPassword));
//		String cshlx = model.getCshlx();
//		boolean result = false;
//		if("all".equals(cshlx)){
//			result = yhglService.mmCshAll(model);
//		}else{
//			result = yhglService.mmCsh(model);
//		}
//		
//		String msg="";
//		if(result){
//			msg=MessageUtil.getText("I99010",new String[]{initialPassword});
//		}else{
//			msg=MessageUtil.getText("I99011");
//		}
//		
//		getValueStack().set(DATA, getText(msg));
//		if (result) {
//			// 记操作日志
//			baseLog.update(user, getText("log.message.ywmc", new
//			  String[] { "用户管理", "XG_XTGL_YHB" }), "系统管理",
//			  getText("log.message.czms", new String[] { "密码初始化", "职工号",
//			  model.getPkValue() }));
//		}
//		return DATA;
//	}
//
//	/**
//	 * 密码修改
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:mmcsh"})
//	public String xgMm() throws Exception {
//		User user = getUser();
//		model.setZgh(user.getYhm());
//		
//		if (OPER_SAVE.equalsIgnoreCase(getRequest().getParameter("doType"))) {
//			// 查询单个用户信息
//			YhglModel yhglModel = yhglService.getModel(model);
//
//			ValueStack vs = getValueStack();
//			if (!MD5Codec.encrypt(model.getYmm()).equals(yhglModel.getMm())) {
//				vs.set("message", "原密码错误，请重新输入！");
//				vs.set("ok", "false");
//				return SUCCESS;
//			}
//			boolean boo = yhglService.xgMm(model);
//			Map<String,String> result = new HashMap<String, String>();
//			if (boo) {
//				result.put("message", "修改成功，请重新登录！");
//				result.put("ok", "true");
//				// 记操作日志
//				baseLog.update(user, getText("log.message.ywmc", new
//				  String[] { "用户管理", "XG_XTGL_YHB" }),
//				  "系统管理", getText("log.message.czms", new
//				  String[] { "密码修改", "职工号", user.getYhm() }));
//				 
//			} else {
//				result.put("message", "修改失败！");
//				result.put("ok", "false");
//			}	
//			vs.set(DATA, result);
//			return DATA;
//		}
//		return SUCCESS;
//	}
//	
//	/**
//	 * 密码修改
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:mmcsh"})
//	public String xgMmQz() throws Exception {
//		User user = getUser();
//		model.setZgh(user.getYhm());
//		
//		if (OPER_SAVE.equalsIgnoreCase(getRequest().getParameter("doType"))) {
//			// 查询单个用户信息
//			YhglModel yhglModel = yhglService.getModel(model);
//
//			ValueStack vs = getValueStack();
//			if (!MD5Codec.encrypt(model.getYmm()).equals(yhglModel.getMm())) {
//				vs.set("message", "原密码错误，请重新输入！");
//				vs.set("ok", "false");
//				return SUCCESS;
//			}
//			boolean boo = yhglService.xgMm(model);
//			if (boo) {
//				vs.set("message", "修改成功，请重新登录！");
//				vs.set("ok", "true");
//				
//				// 记操作日志
//				baseLog.update(user, getText("log.message.ywmc", new
//				  String[] { "用户管理", "XG_XTGL_YHB" }),
//				  "系统管理", getText("log.message.czms", new
//				  String[] { "密码修改", "职工号", user.getYhm() }));
//				 
//			} else {
//				vs.set("message", "修改失败！");
//				vs.set("ok", "false");
//			}
//		}
//		return SUCCESS;
//	}
//	
//	/**
//	 * 
//	 * <p>方法说明：修改密码<p>
//	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
//	 * <p>时间：2016年8月29日下午5:11:42<p>
//	 * @return
//	 */
//	public String savePassword(){
//		try {
//				User user = getUser();
//				model.setZgh(user.getYhm());
//			
//				ValueStack vs = getValueStack();
//				boolean boo = yhglService.xgMm(model);
//				
//				MessageKey key = boo ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
//				vs.set(DATA, key.toString());
//				
//				if (boo) {
//					// 记操作日志
//					baseLog.update(user, getText("log.message.ywmc", new
//					  String[] { "用户管理", "XG_XTGL_YHB" }),
//					  "系统管理", getText("log.message.czms", new
//					  String[] { "密码修改", "职工号", user.getYhm() }));
//				} 
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//		
//		return DATA;
//	}
//	
//
//	/**
//	 * 
//	 * 方法描述: 根据角色查看所分配的用户 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	//@RequiresPermissions(logical=Logical.AND,value={"yhgl:cxjs"})
//	public String ckJsyh() {
//		try {
//			ValueStack vs = getValueStack();
//			JsglModel jsglModel = new JsglModel();
//
//			jsglModel.setJsdm(getRequest().getParameter("jsdm"));
//
//			// 根据角色代码得到角色名称
//			jsglModel = yhglService.cxJsmcByJsdm(jsglModel);
//
//			// 根据角色代码查询所属用户
//			List<YhglModel> yhList = yhglService.cxYhByJsdm(model);
//			vs.set("yhList", yhList);
//
//			int size = yhList.size();
//			vs.set("col",
//					size > 4 ? (size % 4 == 0 ? size / 4 : (size / 4 + 1)) : 1);
//
//			BeanUtils.copyProperties(model, jsglModel);
//			vs.set("model", model);
//
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//
//		return SUCCESS;
//	}
//	
//	/**
//	 * 切换角色
//	 * @return
//	 */
//	public String qhjs(){
//		try {
//			ValueStack vs = getValueStack();
//			String yhm = getUser().getYhm();
//			String curJsdm = getUser().getJsdm();
//			List<JsglModel> cxJsxxZgh = jsglService.getJsxxListByZgh(yhm);
//			JsglModel jsglModel = new JsglModel();
//			jsglModel.setJsdm(curJsdm);
//			JsglModel curJsglModel = jsglService.getModel(jsglModel);
//			vs.set("yhjsxxList", cxJsxxZgh);
//			vs.set("curJsglModel", curJsglModel);
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//		return "qhjs";
//	}
//	
//	/**
//	 * 切换角色保存
//	 * @return
//	 */
//	public String bcQhjs(){
//		try {
//			ValueStack vs = getValueStack();
//			String yhm = getUser().getYhm();
//			runSwitchRole(yhm);
//			List<JsglModel> cxJsxxZgh = jsglService.getJsxxListByZgh(yhm);
//			String curJsdm = getUser().getJsdm();
//			JsglModel jsglModel = new JsglModel();
//			jsglModel.setJsdm(curJsdm);
//			JsglModel curJsglModel = jsglService.getModel(jsglModel);
//			vs.set("yhjsxxList", cxJsxxZgh);
//			vs.set("curJsglModel", curJsglModel);
//			getValueStack().set("result", "SUCCESS");
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//		return "qhjs";
//	}
//
//	//执行角色切换
//	private String runSwitchRole(String yhm) {
//		try {
//			String jsdm = model.getJsdm();
//			if(StringUtils.isNotBlank(jsdm) && (!StringUtils.equals(jsdm, getUser().getJsdm()))){
//				//切换当前的角色信息
//				getUser().setJsdm(jsdm);
//				
//				//刷新shiro缓存
//				AccountRealm shiroRealm = ServiceFactory.getService(DefaultAccountRealm.class);
//				shiroRealm.clearAuthorizationCache();
//				//刷新shiro缓存
//				//删除用户数据范围标识
//				getSession().removeAttribute(GlobalString.SESSION_YHJSSJFWZ_LIST);
//				//删除一级菜单缓存
//				getSession().removeAttribute("topMenuList");
//				//删除二级和三级菜单缓存
//				Enumeration<String> attributeNames = getSession().getAttributeNames();
//				while(attributeNames.hasMoreElements()){
//					String nextElement = attributeNames.nextElement();
//					if(StringUtils.startsWith(nextElement, "menuList_")){
//						getSession().removeAttribute(nextElement);
//					}
//				}
//				//删除三级菜单按钮菜单缓存
//				getSession().removeAttribute(GlobalString.WEB_SESSION_ANCD);
//			}
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//		return yhm;
//	}
//	
//	/**
//	 * 
//	 * <p>方法说明：切换角色<p>
//	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
//	 * <p>时间：2016年8月29日下午2:22:30<p>
//	 * @return
//	 */
//	public String switchRole(){
//		try {
//			String yhm = getUser().getYhm();
//			runSwitchRole(yhm);
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//		return "switchRole";
//	}
//	
//	
//	
//	
//	/**
//	 * 方法描述: 批量数据授权 
//	 * 参数 @return 参数说明 
//	 * 返回类型 String 返回类型
//	 * @throws
//	 */
//	public String cxPlsjsq(){
//		try {
//			if (QUERY.equals(model.getDoType())) {
//				QueryModel queryModel = model.getQueryModel();
//				queryModel.setItems(yhglService.getPagedList(model));
//				getValueStack().set(DATA, queryModel);
//				return DATA;
//			}
//			//查询所有角色代码
//			setValueStack();
//			//查询所有机构
//			setJgdmStack();
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}
//		return "cxPlsjsq";
//	}
//	
//	/**
//	 * 
//	 * 方法描述: 验证职工号是否已经存在 参数 @return 参数说明 返回类型 String 返回类型
//	 * 
//	 * @throws
//	 */
//	public String valideZgh() throws Exception {
//		YhglModel yhglModel = new YhglModel();
//		yhglModel.setZgh(getRequest().getParameter("pkValue"));
//		// 查询单个用户信息
//		yhglModel = yhglService.getModel(yhglModel);
//
//		if (null != yhglModel) {
//			getValueStack().set("data", "该职工号已经存在!");
//		}
//		return "data";
//	}
//	
//	/**
//	 * 
//	 * 方法描述: 查询所有机构代码
//	 * @throws
//	 */
//	public String cxJgdms(){
//		try {
//			if (QUERY.equals(model.getDoType())) {
//				QueryModel queryModel = model.getQueryModel();
//				BmdmModel bmdmModel = new BmdmModel();
//				bmdmModel.setQueryModel(queryModel);
//				String bmmc = super.getRequest().getParameter("bmmc");
//				bmdmModel.setBmmc(bmmc);
//				queryModel.setItems(bmdmService.getPagedList(bmdmModel));
//				getValueStack().set(DATA, queryModel);
//				return DATA;
//			}
//		} catch (Exception e) {
//			logException(e);
//			return ERROR;
//		}		
//		
//		return "cxJgdms";
//	}
//	
//	/**
//	 * 批量停用启用用户
//	 * @return
//	 */
//	public String qyty(){
//		String pkValue = model.getPkValue();
//		if(StringUtils.isBlank(pkValue)){
//			getValueStack().set("yhs", 0);
//		}else{
//			getValueStack().set("yhs", pkValue.split(",").length);
//		}
//		return "qyty";
//	}
//	
//	public String bcQyty(){
//		Map<String,Object> update = new HashMap<String, Object>();
//		update.put("sfqy", model.getSfqy());
//		String pkValue = model.getPkValue();
//		update.put("zghs", pkValue.split(","));
//		boolean result = yhglService.batchUpdate(update);
//		String key = result ? "I99001" : "I99002";
//		getValueStack().set("result", getText(key));
//		return "qyty";
//	}
//	
//	/**
//	 * ajax验证用户密码
//	 * @return
//	 */
//	public String ajaxCheckYhMm(){
//		ValueStack vs=getValueStack();
//		boolean result = false;
//		try{
//			result = yhglService.checkYhMm(model.getZgh(), model.getMm());
//		} catch (Exception e) {
//			logException(e);
//		}finally{
//			vs.set(DATA, result);
//		}
//		return DATA;
//	}
	
	
	public YhglModel getModel() {
		return model;
	}

}
