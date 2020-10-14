package com.woshidaniu.globalweb.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.woshidaniu.basicutils.RandomUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.datarange.DataItem;
import com.woshidaniu.common.datarange.DataItemService;
import com.woshidaniu.common.log.BusinessLog;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.password.UserPasswordBuilder;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.export.service.svcinterface.IExportService;
import com.woshidaniu.globalweb.common.AdminUserRoleUtils;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.security.algorithm.MD5Codec;
import com.woshidaniu.service.common.IPasswordStrengthCheckService;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.taglibs.factory.ServiceFactory;
import com.woshidaniu.util.base.MessageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：用户管理
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年9月2日下午2:57:10
 */
@Controller
@RequestMapping(value = "/xtgl/yhgl")
public class YhglController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(YhglController.class);
	
	@Autowired
	private UserPasswordBuilder userPasswordBuilder;

	@Autowired
	private IYhglService yhglService;
	
	@Autowired
	private IJsglService jsglService;
	
	@Autowired
	@Qualifier("exportExcel")
	//@Qualifier("exportExcelPOI")
	protected IExportService exportService;
	
	/**
	 * 元素是个Map，
	 * init方法调用之前
	 * {
	 * 	sjdm=xydm,
	 *  sjmc=学院, 
	 *  service=bmdmService,
	 * }
	 * 
	 * init方法调用之后
	 * {
	 * 	sjdm=xydm,
	 *  sjmc=学院, 
	 *  service=bmdmService,
	 *  pinyingList = new ArrayList<String>(),
	 * }
	 */
	@Autowired
	private List<?> sjfwList;
	
	private SjfwViewService sjfwViewService = new SjfwViewService();
	
	@Autowired
	private IPasswordStrengthCheckService passwordStrengthCheckService;
	
	private int yhsjfwPageSize = 40;
	
	private boolean yhsjfwInitFullscreen = false;
	
	/**
	 * @description	：初始化sjfwViewService
	 */
	@PostConstruct
	public void init() {
		this.sjfwViewService.init(sjfwList);
		
		//用户数据授权列表每页数据多少
		{
			String val = MessageUtil.getText("com.woshidaniu.globalweb.controller.YhglController.yhsjfw.pageSize");
			this.yhsjfwPageSize = StringUtils.isNotEmpty(val) ? Integer.parseInt(val) : this.yhsjfwPageSize;
		}
		
		//用户数据授权列页面是否初始时就全屏
		{
			String val = MessageUtil.getText("com.woshidaniu.globalweb.controller.YhglController.yhsjfw.initFullscreen");
			this.yhsjfwInitFullscreen = StringUtils.isNotEmpty(val) ? Boolean.parseBoolean(val) : this.yhsjfwInitFullscreen;
		}
	}
	
	/**
	 * 
	 * <p>方法说明：查询用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年9月2日下午3:07:23<p>
	 * @return String
	 */
	@RequiresPermissions("yhgl:cx")
	@RequestMapping(value = "/cxYhxx")
	public String cxYhxx() {
		return "/globalweb/comp/xtgl/yhgl/cxYhxx";
	}

	
	/**
	 * 
	 * <p>方法说明：ajax加载用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年9月2日下午3:07:23<p>
	 * @param model
	 * @return JSONObject
	 */
	@RequiresPermissions("yhgl:cx")
	@RequestMapping(value = "/getYhxxList")
	@ResponseBody
	public Object getYhxxList(YhglModel model,HttpSession httpSession){
		try {
			SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
			
			httpSession.setAttribute("getYhxxListQueryModel",model);
			
			List<YhglModel> pagedList = yhglService.getPagedList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	/**
	 * 
	 * <p>方法说明：访问增加用户<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日上午11:27:12<p>
	 * @return ModelAndView
	 */
	@RequiresPermissions("yhgl:zj")
	@RequestMapping(value = "/zjYhxx")
	public ModelAndView zjYhxx() {
		String password = RandomUtils.genMixRandomStr(6);
		List<JsglModel> jsxxList = jsglService.getModelList();
		ModelAndView view = new ModelAndView();
		view.addObject("password", password);
		view.addObject("jsxxList", jsxxList);
		view.setViewName("/globalweb/comp/xtgl/yhgl/zjYhxx");
		return  view;
	}

	
	
	/**
	 * 
	 * <p>方法说明：用户增加<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月25日下午1:55:43<p>
	 * @param model
	 * @param jsdms
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "新增用户-职工号：${model.zgh!}", ywmc = "用户管理", czlx = BusinessType.INSERT)
	@ResponseBody
	@RequestMapping(value = "/saveYhxx")
	@RequiresPermissions("yhgl:zj")
	public Object saveYhxx(YhglModel model,String jsdms) {
		try {
			boolean result = false;
			model.setMm(MD5Codec.encrypt(model.getMm()));// 密码加密
			if (!StringUtils.isEmpty(jsdms)){
				String [] jsdmArr = jsdms.split(",");
				result = yhglService.insert(model,jsdmArr);
			} else {
				result = yhglService.insert(model);
			}
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	

	/**
	 * 
	 * <p>方法说明：修改用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午2:38:36<p>
	 * @param model
	 * @return ModelAndView
	 */
	@BusinessLog(czmk = "系统管理", czms = "访问用户修改-职工号：${model.zgh!}", ywmc = "用户管理", czlx = BusinessType.SELECT)
	@RequiresPermissions("yhgl:xg")
	@RequestMapping(value = "/xgYhxx")
	public ModelAndView xgYhxx(YhglModel model) {
		ModelAndView view = new ModelAndView();
		try {
			//查询单个用户信息
			YhglModel yhglModel = yhglService.getModel(model);
			List<JsglModel> jsxxList = jsglService.getModelList();
			List<JsglModel> yhjsList = jsglService.getJsxxListByZgh(model.getZgh());
			view.addObject("model", yhglModel);
			view.addObject("jsxxList", jsxxList);
			view.addObject("yhjsList", yhjsList);
			view.setViewName("/globalweb/comp/xtgl/yhgl/xgYhxx");
		} catch (Exception e) {
			 logException(e);
			 view.setViewName(ERROR_VIEW);
		}
		return view;
	}


	
	/**
	 * 
	 * <p>方法说明：修改用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月25日下午2:28:05<p>
	 * @param model
	 * @param jsdms
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "修改用户-职工号：${model.zgh!}", ywmc = "用户管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping(value = "/modifyYhxx")
	@RequiresPermissions("yhgl:xg")
	public Object modifyYhxx(YhglModel model,String jsdms) {
		try {
			boolean result = false;
			String sfqy = model.getSfqy();
			
			String yhm = model.getYhm();
			//是管理员且设置禁用，拒绝这样的操作
			if(AdminUserRoleUtils.existAdminUser(new String[] {yhm}) && "0".equals(sfqy)) {
				return MessageKey.MODIFY_FAIL.getJson();
			}
			
			// 修改用户信息
			if (!StringUtils.isEmpty(jsdms)){
				String [] jsdmArr = jsdms.split(",");
				
				//是管理员 且又不存在管理员角色了，拒绝这样的操作
				if(AdminUserRoleUtils.existAdminUser(new String[] {yhm}) && !AdminUserRoleUtils.existAdminRole(jsdmArr)) {
					return MessageKey.MODIFY_FAIL.getJson();
				}

				result = yhglService.update(model,jsdmArr);
			} else {
				result = yhglService.update(model);
			}
			MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	/**
	 * 
	 * <p>方法说明：删除用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午2:44:11<p>
	 * @param ids 职工号
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "批量删除用户-职工号：${ids}", ywmc = "用户管理", czlx = BusinessType.DELETE)
	@ResponseBody
	@RequestMapping(value = "/scYhxx")
	@RequiresPermissions("yhgl:sc")
	public Object scYhxx(@RequestParam String ids) {
		try {
			//页面参数
			String[] idArray = ids.split(",");
			
			//存在管理员用户，拒绝这类操作
			if(AdminUserRoleUtils.existAdminUser(idArray)) {
				return MessageKey.DELETE_FAIL.getJson();
			}
			
			boolean result = yhglService.scYhxx(idArray);
			MessageKey key = result ? MessageKey.DELETE_SUCCESS : MessageKey.DELETE_FAIL;
			return key.getJson();
		}catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	
	
	
	/**
	 * 
	 * <p>方法说明：查看用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午2:53:35<p>
	 * @param model
	 * @return ModelAndView
	 */
	@BusinessLog(czmk = "系统管理", czms = "查看用户信息-职工号：${model.zgh!}", ywmc = "用户管理", czlx = BusinessType.SELECT)
	@RequestMapping(value = "/ckYhxx")
	@RequiresPermissions("yhgl:ck")
	public ModelAndView ckYhxx(YhglModel model) {
		ModelAndView view = new ModelAndView();
		try {
			// 查询单个用户信息
			YhglModel yhglModel = yhglService.getModel(model);
			view.addObject("model", yhglModel);
			view.setViewName("/globalweb/comp/xtgl/yhgl/ckYhxx");
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}




	/**
	 * 
	 * <p>方法说明：访问修改密码<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午2:55:55<p>
	 * @return String
	 */
	@RequestMapping(value = "/xgMm")
	public ModelAndView xgMm(){
		ModelAndView view = new ModelAndView();
		view.addObject("rules", passwordStrengthCheckService.info());
		view.setViewName("/globalweb/comp/xtgl/yhgl/xgMm");
		return view;
	}
	
	
	/**
	 * 
	 * <p>方法说明：密码初始化<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月29日上午9:39:19<p>
	 * @return ModelAndView
	 */
	@RequiresPermissions("yhgl:mmcsh")
	@RequestMapping("/mmcsh")
	public ModelAndView mmcsh(@RequestParam String ids){
		ModelAndView view = new ModelAndView();
		view.addObject("rules", passwordStrengthCheckService.info());
		view.addObject("ids", ids);
		view.setViewName("/globalweb/comp/xtgl/yhgl/mmcsh");
		return view;
	}

	/**
	 * 
	 * @description	： 密码批量初始化
	 * @author 		： 康康（1571）
	 * @date 		：2018年6月21日 下午3:58:45
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("yhgl:mmplcsh")
	@RequestMapping(value="/mmplcsh",method=RequestMethod.POST)
	public ModelAndView mmplcsh(){
		ModelAndView view = new ModelAndView();
		view.addObject("rules", passwordStrengthCheckService.info());
		view.setViewName("/globalweb/comp/xtgl/yhgl/mmplcsh");
		return view;
	}
	
	/**
	 * 
	 * <p>方法说明：保存密码初始化<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月29日上午9:51:38<p>
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("yhgl:mmcsh")
	@RequestMapping("/saveMmcsh")
	public Object saveMmcsh(//
			@RequestParam String ids ,//
			@RequestParam(required=false)String password//
			){//
		try {
			//校验密码强度	
			if(StringUtils.isNotEmpty(password)) {
				if(passwordStrengthCheckService.check(password)){
					String[] zghArr = ids.split(",");
					boolean result = yhglService.updateYhmm(zghArr, MD5Codec.encrypt(password));
					MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
					return key.getJson();
				}else{
					return MessageKey.PASSWORD_UPDATE_FAIL_M.status(new Object[]{});
				}
			}else {
				String[] zghArr = ids.split(",");
				String[] passwords = new String[zghArr.length];
				for(int i=0;i<zghArr.length;i++) {
					String zgh = zghArr[i];
					String pass = this.userPasswordBuilder.buildPassword(zgh);
					if(this.passwordStrengthCheckService.check(pass)) {//密码强度检测通过
						passwords[i] = MD5Codec.encrypt(pass);						
					}else {//密码强度检测未通过
						log.error("由{}根据职工号{}产生的密码不符合密码强度检测要求",this.userPasswordBuilder.getClass().getName(),zgh);
						log.error("密码强度要求:");
						String[] info = this.passwordStrengthCheckService.info();
						for(int k=0;k<info.length;k++) {
							log.error(info[k]);
						}
						return MessageKey.PASSWORD_UPDATE_FAIL_M.status(new Object[]{});
					}
					passwords[i] = MD5Codec.encrypt(pass);
				}
				boolean result = yhglService.updateYhmmBatch(zghArr, passwords);
				MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
				return key.getJson();
			}
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	/**
	 * 
	 * <p>方法说明：保存密码初始化<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月29日上午9:51:38<p>
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("yhgl:mmplcsh")
	@RequestMapping(value="/saveMmplcsh",method=RequestMethod.POST)
	public Object saveMmplcsh(//
			@RequestParam(name="password",required=false)String password,//
			HttpSession httpSession//
			){//
		try {
			
			Object modelObject = httpSession.getAttribute("getYhxxListQueryModel");
			List<YhglModel> list = null;
			if(modelObject != null) {
				httpSession.removeAttribute("getYhxxListQueryModel");
				YhglModel model = (YhglModel) modelObject;
				list = yhglService.getModelList(model);
			}else {
				MessageKey key = MessageKey.MODIFY_SUCCESS;
				return key.getJson();
			}
			
			if(StringUtils.isNotEmpty(password)) {//批量初始化密码为同一个密码
				//校验密码强度	
				if(passwordStrengthCheckService.check(password)){
					
					String[] zghArr = new String[list.size()];
					for(int i=0;i<list.size();i++) {
						YhglModel yhglModel = list.get(i);
						String zgh = yhglModel.getZgh();
						zghArr[i] = zgh;
					}
					boolean result = yhglService.updateYhmm(zghArr, MD5Codec.encrypt(password));
					MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
					return key.getJson();

				}else{
					return MessageKey.PASSWORD_UPDATE_FAIL_M.status(new Object[]{});
				}	
			}else {//批量初始化密码为不同的密码
				
				String[] zghArr = new String[list.size()];
				String[] passwords = new String[list.size()];
				
				for(int i=0;i<list.size();i++) {
					YhglModel yhglModel = list.get(i);
					String zgh = yhglModel.getZgh();
					zghArr[i] = zgh;
					String pass = this.userPasswordBuilder.buildPassword(zgh);
					if(this.passwordStrengthCheckService.check(pass)) {//密码强度检测通过
						passwords[i] = MD5Codec.encrypt(pass);						
					}else {//密码强度检测未通过
						log.error("由{}根据职工号{}产生的密码不符合密码强度检测要求",this.userPasswordBuilder.getClass().getName(),zgh);
						log.error("密码强度要求:");
						String[] info = this.passwordStrengthCheckService.info();
						for(int k=0;k<info.length;k++) {
							log.error(info[k]);
						}
						return MessageKey.PASSWORD_UPDATE_FAIL_M.status(new Object[]{});
					}
				}
				boolean result = yhglService.updateYhmmBatch(zghArr, passwords);
				MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
				return key.getJson();
			}
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	/**
	 * 
	 * <p>方法说明：修改密码<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月29日下午5:11:42<p>
	 * @param model
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "修改密码", ywmc = "用户管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping("/savePassword")
	public Object savePassword(@RequestParam String ymm, @RequestParam String mm){
		try {
			//校验密码强度	
			if(passwordStrengthCheckService.check(mm)){
				User user = getUser();
				YhglModel model = new YhglModel();
				model.setZgh(user.getYhm());
				model.setMm(MD5Codec.encrypt(ymm));
				YhglModel yModel = yhglService.getModel(model);
				if (yModel == null){
					return MessageKey.PASSWORD_UPDATE_FAIL.status(new Object[]{});
				}else{
					model.setMm(MD5Codec.encrypt(mm));
					boolean result = yhglService.update(model);
					return (result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL).status(new Object[]{});
				}
			}
			return MessageKey.PASSWORD_UPDATE_FAIL_M.status(new Object[]{});
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	

	
	
	/**
	 * 
	 * <p>方法说明：启用停用<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午2:57:27<p>
	 * @param ids
	 * @param model
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "用户启用停用：${ids!}", ywmc = "用户管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping("/qyty")
	@RequiresPermissions(logical=Logical.OR,value={"yhgl:qy","yhgl:ty"})
	public Object qyty(@RequestParam String ids,YhglModel model){
		try {
			String[] idArray = ids.split(",");
			
			//包含管理员账号且为停用，拒绝这种操作
			if(AdminUserRoleUtils.existAdminUser(idArray)) {
				String sfqy = model.getSfqy();
				if("0".equals(sfqy)) {
					MessageKey key = MessageKey.DO_FAIL;
					return key.getJson(new String[]{"操作"});
				}
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("sfqy", model.getSfqy());
			map.put("zghs", idArray);
			boolean result = yhglService.batchUpdate(map);
			MessageKey key = result ? MessageKey.DO_SUCCESS : MessageKey.DO_FAIL;
			return key.getJson(new String[]{"操作"});
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * @description	： ajax加载用户数据范围数据
	 * @param ids
	 * @param model
	 * @return
	 */
	@RequiresPermissions("yhgl:sjsq")
	@RequestMapping("/ajaxLoadYhsjfw")
	@ResponseBody
	public Object ajaxLoadYhsjfw(//
			@RequestParam String id,//用户id
			@RequestParam(required=true)String sjdm,//数据代码
			@RequestParam(defaultValue="")String selectType,// 0 是未选择， 1是已经选，空字符串，代表所有
			@RequestParam(defaultValue="")String pinyin,//拼音
			@RequestParam(defaultValue="")String name,//名称，模糊搜索条件
			@RequestParam(required=true,defaultValue="1")int pageNo,//
			@RequestParam(required=true)int pageSize,//
			HttpServletResponse response
			){//
		try {
			//用户数据范围，用户已经配置的
			List<String> yhsjfwList = yhglService.getYhsjfwList(id, sjdm);
			Set<String> yhsjfwSet = new HashSet<String>(yhsjfwList);
			//搜索过滤器
			List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
			if(StringUtils.isNotEmpty(pinyin)) {//拼音搜索
				searchFilters.add(new PinyinSearchFilter(pinyin));
			}
			if(StringUtils.isNotEmpty(name)) {//名称模糊搜索
				searchFilters.add(new NameSearchFilter(name));
			}
			if(StringUtils.isNotEmpty(selectType)) {//选择类型搜索
				searchFilters.add(new SelectTypeSearchFilter(selectType,yhsjfwSet));
			}
			//搜索获得分页结果
			Page page = this.sjfwViewService.search(searchFilters,sjdm,pageNo,pageSize);
			List<WrappeSelectDataItem> resultList = new ArrayList<WrappeSelectDataItem>(page.list.size());
			for(int i=0;i<page.list.size();i++) {
				DataItem dataItem = page.list.get(i);
				WrappeSelectDataItem wrappeSelectDataItem = new WrappeSelectDataItem(dataItem);
				if(yhsjfwSet.contains(dataItem.getKey())) {
					wrappeSelectDataItem.setSelected(true);
				}
				resultList.add(wrappeSelectDataItem);
			}
			JSONObject json = new JSONObject();
			json.put("list", resultList);
			json.put("pageNo", page.pageNo);
			json.put("pageSize", page.pageSize);
			json.put("totalPage", page.totalPage);
			return json;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * @description	： ajax保存用户数据范围数据
	 * @param ids
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("yhgl:sjsq")
	@RequestMapping("/ajaxSaveYhsjfw")
	public Object ajaxSaveYhsjfw(//
			){//
		try {
			return null;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * 
	 * <p>方法说明：用户数据范围<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年4月11日下午3:31:45<p>
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("yhgl:sjsq")
	@RequestMapping("/yhsjfw")
	public String yhsjfw(@RequestParam String ids,Model model){
		try {
			JSONArray jsonArray = JSONArray.fromObject(sjfwList);
			String jsonArrayStr = jsonArray.toString();
			model.addAttribute("sjfwList", sjfwList);
			model.addAttribute("sjfwListJsonArray",jsonArrayStr);
			model.addAttribute("ids", ids);
			model.addAttribute("yhsjfwPageSize", this.yhsjfwPageSize);
			model.addAttribute("yhsjfwInitFullscreen", this.yhsjfwInitFullscreen);
			
			return "/globalweb/comp/xtgl/yhgl/yhsjfw";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}
	
	@BusinessLog(czmk = "系统管理", czms = "设置用户数据范围：${ids!}", ywmc = "用户管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping("/submitCheckYhsjfw")
	@RequiresPermissions("yhgl:sjsq")
	public Object submitCheckYhsjfw(String id,String sjdm,String value,boolean check){
		try {
			boolean result = yhglService.updateCheckYhsjfw(id, sjdm,value,check);
			MessageKey messageKey = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return messageKey.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * 
	 * <p>方法说明：设置用户数据范围<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年4月13日上午9:17:39<p>
	 * @param ids 职工号
	 * @param sjfw 数据范围
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "设置用户数据范围：${ids!}", ywmc = "用户管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping("/saveYhsjfw")
	@RequiresPermissions("yhgl:sjsq")
	public Object saveYhsjfw(@RequestParam String ids , @RequestParam String sjfw){
		try {
			boolean result = yhglService.insertYhsjfw(ids, sjfw);
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * 导出操作
	 * @param model
	 * @param exportModel
	 * @return
	 */
	@RequestMapping(value="/doExport.zf")
	public ResponseEntity<byte[]> doExport(YhglModel model,ExportModel exportModel){
		try {
			User user = getUser();
			exportModel.setZgh(user.getYhm());

			//高级查询解析
			SearchParser.parseMybatisSQL(model);
			model.getQueryModel().setShowCount(Integer.MAX_VALUE);
			
			List<YhglModel> list = this.yhglService.getModelList(model);
			exportModel.setDataList(list);
			
			File file = exportService.getExportFile(exportModel);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", file.getName());
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		} catch (Exception e) {
			logException(e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	/**
	 * @description	： 数据范围搜索服务
	 */
	private class SjfwViewService{
		
		private List<SjfwView> sjfwViews = new ArrayList<SjfwView>();
		
		public void init(List<?> sjfwList) {
			
			log.info("初始化用户数据范围基础数据");
			
			for (Object o : sjfwList){
				
				Map<String,Object> map = (Map<String, Object>) o;
				
				String sjdm = (String)map.get("sjdm");
				String sjmc = (String)map.get("sjmc");
				String serviceName = (String)map.get("service");
				DataItemService service = (DataItemService) ServiceFactory.getService(serviceName);
				
				if(service == null) {
					
					log.warn("没有发现service[{}],对应sjdm:{},sjmc:{},无法加载数据范围数据",serviceName,sjdm,sjmc);
					
				}else {
					
					List<DataItem> list = service.getDataItemList();
					log.info("根据service[{}]初始化数据代码:[{}],得到数据{}条",service.getClass().getName(),sjdm,list.size());
					
					//拼音排序
					Set<String> pinyinSet = new HashSet<String>();
					Collections.sort(list, new DataItemComparator(pinyinSet));
					log.info("排序得到拼音列表[{}]",pinyinSet);
					
					//填充排序后的拼音列表
					List<String> pinyinList = new ArrayList<String>(pinyinSet);
					Collections.sort(pinyinList,new PinyingComparator());
					
					map.put("pinyingList", pinyinList);
					
					SjfwView sjfwView = new SjfwView();
					sjfwView.setSjdm(sjdm);
					sjfwView.setSjmc(sjmc);
					//包装DataItem
					
					sjfwView.setAll(list);
					sjfwView.setPinyinList(pinyinList);
					sjfwViews.add(sjfwView);
				}
			}
		}
		
		public Page search(List<SearchFilter> searchFilters,String sjdm,int pageNo, int pageSize) {
			for(int i=0;i<this.sjfwViews.size();i++) {
				SjfwView sjfwView = this.sjfwViews.get(i);
				String sjdm_ = sjfwView.getSjdm();
				if(sjdm_.equals(sjdm)) {
					List<DataItem> list = sjfwView.getAll();
					//搜索
					Searcher searcher = new Searcher(list);
					List<DataItem> searchResult = searcher.search(searchFilters);
					//分页
					Page pageResult = new Page(pageNo, pageSize);
					pageResult.doPage(searchResult);
					return pageResult;
				}
			}
			//无结果,返回空数据
			Page pageEmptyResult = new Page(pageNo, pageSize);
			pageEmptyResult.doPage(new ArrayList<DataItem>(1));
			return pageEmptyResult;
		}
	}
	
	/**
	 * @description	： 数据范围view,为了方便做内存搜索，内存分页，计算等
	 */
	class SjfwView{
		private String sjdm;//数据代码
		private String sjmc;//数据名称
		private List<DataItem> all;
		private List<String> pinyinList;
		private Map<String,List<DataItem>> pinyinDataItemMapper = new HashMap<String,List<DataItem>>();
		
		public String getSjdm() {
			return sjdm;
		}
		public void setSjdm(String sjdm) {
			this.sjdm = sjdm;
		}
		public String getSjmc() {
			return sjmc;
		}
		public void setSjmc(String sjmc) {
			this.sjmc = sjmc;
		}
		public List<DataItem> getAll() {
			return all;
		}
		public void setAll(List<DataItem> all) {
			this.all = all;
		}
		public Map<String, List<DataItem>> getPinyinDataItemMapper() {
			return pinyinDataItemMapper;
		}
		public void setPinyinDataItemMapper(Map<String, List<DataItem>> pinyinDataItemMapper) {
			this.pinyinDataItemMapper = pinyinDataItemMapper;
		}
		public List<String> getPinyinList() {
			return pinyinList;
		}
		public void setPinyinList(List<String> pinyinList) {
			this.pinyinList = pinyinList;
		}
		
	}
	
	/**
	 * @description	： 搜索过滤器
	 */
	private interface SearchFilter{
		public boolean accept(DataItem t);
	}
	/**
	 * 
	 * @description	： 拼音搜索过滤器
	 */
	class PinyinSearchFilter implements SearchFilter{
		private String pinyin;
		
		public PinyinSearchFilter(String pinyin) {
			super();
			this.pinyin = pinyin;
		}
		@Override
		public boolean accept(DataItem t) {
			String p = t.getPinyin();
			if(this.pinyin.equals(p)) {
				return true;
			}
			return false;
		}
		@Override
		public String toString() {
			return "PinyinSearchFilter [pinyin=" + pinyin + "]";
		}
	}
	/**
	 * @description	： 名称所示过滤器
	 */
	class NameSearchFilter implements SearchFilter{
		private String name;
		public NameSearchFilter(String name) {
			super();
			this.name = name;
		}
		@Override
		public boolean accept(DataItem t) {
			String n = t.getValue();
			if(n.contains(this.name)) {
				return true;
			}
			return false;
		}
		@Override
		public String toString() {
			return "NameSearchFilter [name=" + name + "]";
		}
	}

	class SelectTypeSearchFilter implements SearchFilter {

		private String selectType;
		private Set<String> yhsjfwSet;

		public SelectTypeSearchFilter(String selectType,Set<String> yhsjfwSet) {
			this.selectType = selectType;
			this.yhsjfwSet = yhsjfwSet;
		}

		@Override
		public boolean accept(DataItem t) {
			String key = t.getKey();
			if (this.selectType.equals("1")) {// 搜索已经选择的，包含
				if (this.yhsjfwSet.contains(key)) {
					return true;
				}else {
					return false;
				}
			} else if (this.selectType.equals("0")) {// 搜索没有选择的，不包含
				if (!this.yhsjfwSet.contains(key)) {
					return true;
				}else {
					return false;
				}
			} else {// 搜索全部
				return true;
			}
		}

		@Override
		public String toString() {
			return "SelectTypeSearchFilter [selectType=" + selectType + ", yhsjfwSet=" + yhsjfwSet + "]";
		}
	}
	/**
	 * @description ： 列表搜索
	 * @param <T>
	 */
	private class Searcher {

		private List<DataItem> list;

		public Searcher(List<DataItem> list) {
			super();
			if (list == null) {
				this.list = Collections.emptyList();
			} else {
				this.list = list;
			}
		}

		public List<DataItem> search(List<SearchFilter> filters) {
			if (filters == null || filters.isEmpty()) {
				return this.list;
			}
			List<DataItem> result = new ArrayList<DataItem>();
			for (int i = 0; i < this.list.size(); i++) {
				DataItem t = this.list.get(i);
				boolean accept = true;
				for (int k = 0; k < filters.size(); k++) {
					SearchFilter filter = filters.get(k);
					if (accept) {
						if (!filter.accept(t)) {
							accept = false;
						}
					}
				}
				if (accept) {
					result.add(t);
				}
			}
			if(log.isDebugEnabled()) {
				log.debug("search by filters:[{}],result size:[{}]",filters,result.size());
			}
			return result;
		}
	}
	/**
	 * @description ： 一页数据
	 */
	private class Page{
		
		private List<DataItem> list;
		private int pageNo;
		private int pageSize;
		private int totalPage;

		public Page(int pageNo, int pageSize) {
			this.pageNo = pageNo;
			this.pageSize = pageSize;
		}

		public void doPage(List<DataItem> list) {
			if (list == null) {
				list = Collections.emptyList();
			}
			if (pageSize <= 0) {
				this.pageSize = 20;
			}
			// 计算页数
			this.totalPage = list.size() / this.pageSize;
			int lastPage = list.size() % this.pageSize;
			if (lastPage > 0) {
				this.totalPage++;
			}
			if (pageNo <= 0) {
				this.pageNo = 1;
			}else if (pageNo > this.totalPage) {
				this.pageNo = this.totalPage;
				if(this.pageNo <= 0) {
					this.pageNo = 1;
				}
			}
			int start = (this.pageNo - 1) * this.pageSize;
			int end = start + this.pageSize;
			if (start > list.size()) {
				start = list.size();
			}
			if (end > list.size()) {
				end = list.size();
			}
			this.list = list.subList(start, end);
		}

		@Override
		public String toString() {
			return "Page [pageNo=" + pageNo + ", pageSize=" + pageSize + ", totalPage=" + totalPage + "]";
		}
	}
	
	public class WrappeSelectDataItem extends DataItem{
		
		private boolean selected = false;
		public WrappeSelectDataItem(DataItem dataItem) {
			this.setKey(dataItem.getKey());
			this.setValue(dataItem.getValue());
			this.setPinyin(dataItem.getPinyin());
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	}

	/**
	 * @description	： 拼音排序
	 */
	class PinyingComparator implements Comparator<String>{

		@Override
		public int compare(String p1,String p2) {
			
			if(p1 != null && p2 != null) {
				
				String p1_low = p1.toLowerCase();
				String p2_low = p2.toLowerCase();
				
				//存在a，A 这样的情况，那么a排在前面
				if(p1_low.equals(p2_low)) {
					return p1.compareTo(p2);
				}else {
					//其他情况
					return p1_low.compareTo(p2_low);					
				}
			}else {
				if(p1 == null && p2 != null) {
					return -1;
				}
				if(p1 != null && p2 == null) {
					return 1;
				}
				return 0;
			}
		}
	}

	/**
	 * @description	： 比较器 a A b B c C 这样的排序
	 */
	class DataItemComparator implements Comparator<DataItem>{
		
		private Set<String> pinyingSet;
		
		//委托这个排序器排序
		private PinyingComparator pinyingComparator = new PinyingComparator();

		public DataItemComparator(final Set<String> pinyingSet) {
			this.pinyingSet = pinyingSet;
		}

		@Override
		public int compare(DataItem o1, DataItem o2) {
			String p1 = o1.getPinyin();
			String p2 = o2.getPinyin();
			if(p1 != null) {
				pinyingSet.add(p1);
			}
			if(p2 != null) {
				pinyingSet.add(p2);
			}
			return this.pinyingComparator.compare(p1, p2);
		}
	}
}
