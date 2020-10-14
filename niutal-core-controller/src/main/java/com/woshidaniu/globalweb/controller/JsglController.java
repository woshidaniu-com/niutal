package com.woshidaniu.globalweb.controller;



import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.BusinessLog;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.AncdModel;
import com.woshidaniu.dao.entities.EjqxModel;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.dao.entities.Menu;
import com.woshidaniu.dao.entities.SjzyModel;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.globalweb.common.AdminUserRoleUtils;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.service.svcinterface.ISjzyService;
import com.woshidaniu.service.svcinterface.IYhglService;

import net.sf.json.JSONObject;



/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：角色管理Controller
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月17日上午10:10:26
 */
@Controller
@RequestMapping(value = "/xtgl/jsgl")
public class JsglController extends BaseController {

	@Autowired
	private IJsglService jsglService;//角色管理SERVICE
	
	@Autowired
	private IYhglService yhglService;//用户管理SERVICE
	
	@Autowired
	private ISjzyService sjzyService;//

	

	/**
	 * 
	 * <p>方法说明：查询角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月15日下午7:37:54<p>
	 * @return String
	 */
	@RequiresPermissions("jsgl:cx")
	@RequestMapping(value = "/cxJsxx")
	public String cxJsxx() {
		return "/globalweb/comp/xtgl/jsgl/cxJsxx";
	}

	/**
	 * 
	 * <p>方法说明：Ajax分页查询角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月15日下午7:36:55<p>
	 * @param model
	 * @return Object
	 */
	@ResponseBody
	@RequiresPermissions("jsgl:cx")
	@RequestMapping(value = "/getJsxxList")
	public Object getJsxxList(JsglModel model){
		try {
			SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
			List<JsglModel> pagedList = jsglService.getPagedList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	/**
	 * 
	 * <p>方法说明：增加角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月15日下午7:36:22<p>
	 * @return ModelAndView
	 */
	@RequiresPermissions("jsgl:zj")
	@RequestMapping(value = "/zjJsxx")
	public ModelAndView zjJsxx() {
		ModelAndView view = new ModelAndView();
		view.addObject("jsdm", UniqID.getInstance().getUniqIDHash());
		view.setViewName("/globalweb/comp/xtgl/jsgl/zjJsxx");
		return view;
	}


	/**
	 * 
	 * <p>方法说明：增加角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月24日上午11:49:00<p>
	 * @param model
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "新增角色-名称：${model.jsmc!}", ywmc = "角色管理", czlx = BusinessType.INSERT)
	@ResponseBody
	@RequiresPermissions("jsgl:zj")
	@RequestMapping(value = "/saveJsxx",method=RequestMethod.POST)
	public Object saveJsxx(JsglModel model) { 
		try {
			// 保存数据操作
			boolean result = jsglService.insert(model);
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	/**
	 * 
	 * <p>方法说明：修改角色 <p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月15日下午7:38:51<p>
	 * @param model
	 * @return ModelAndView
	 */
	@BusinessLog(czmk = "系统管理", czms = "访问修改角色功能，角色代码：${model.jsdm!}", ywmc = "角色管理", czlx = BusinessType.SELECT)
	@RequiresPermissions("jsgl:xg")
	@RequestMapping(value = "/xgJsxx")
	public ModelAndView xgJsxx(JsglModel model) {
		ModelAndView view = new ModelAndView();
		try {
			// 查询单个角色信息
			JsglModel jsglModel = jsglService.getModel(model);
			view.addObject("model", jsglModel);
			view.setViewName("/globalweb/comp/xtgl/jsgl/xgJsxx");
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}
	
	
	/**
	 * 
	 * <p>方法说明：查看角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月29日上午9:08:11<p>
	 * @param model
	 * @return
	 */
	@RequiresPermissions("jsgl:ck")
	@RequestMapping(value = "/ckJsxx")
	public ModelAndView ckJsxx(JsglModel model) {
		ModelAndView view = new ModelAndView();
		try {
			// 查询单个角色信息
			JsglModel jsglModel = jsglService.getModel(model);
			view.addObject("model", jsglModel);
			view.setViewName("/globalweb/comp/xtgl/jsgl/ckJsxx");
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}


	/**
	 * 
	 * <p>方法说明：修改角色信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月24日下午2:36:22<p>
	 * @param model
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "修改角色-名称：${model.jsmc!}", ywmc = "角色管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequiresPermissions("jsgl:xg")
	@RequestMapping(value = "/modifyJsxx",method=RequestMethod.POST)
	public JSONObject modifyJsxx(JsglModel model) {
		try {
			//是系统管理员角色且停用，拒绝这种操作
			String jsdm = model.getJsdm();
			if(AdminUserRoleUtils.existAdminRole(new String[] {jsdm})) {
				String qyzt = model.getQyzt();
				if("0".equals(qyzt)) {
					MessageKey key = MessageKey.MODIFY_FAIL;
					return key.getJson();
				}
			}
			boolean	result = jsglService.update(model);
			MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	
	/**
	 * 
	 * <p>方法说明：删除角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日上午9:33:29<p>
	 * @param id 角色代码
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "删除角色-角色代码：${id!}", ywmc = "角色管理", czlx = BusinessType.DELETE)
	@ResponseBody
	@RequiresPermissions("jsgl:sc")
	@RequestMapping(value = "/scJsxx")
	public Object scJsxx(@RequestParam String id)  {
		try {
			//删除系统管理员角色，拒绝这种操作
			if(AdminUserRoleUtils.existAdminRole(new String[] {id})) {
				MessageKey key = MessageKey.DELETE_FAIL;
				return key.getJson();
			}
			//	删除角色信息
			boolean result = jsglService.deleteJsxxById(id);
			MessageKey key = result ? MessageKey.DELETE_SUCCESS : MessageKey.DELETE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	
	/**
	 * 
	 * <p>方法说明：未分配用户列表<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午4:46:05<p>
	 * @param model
	 * @return JSONObject
	 */
	@ResponseBody
	@RequiresPermissions("jsgl:fpyh")
	@RequestMapping(value = "/getWfpyhList")
	public Object getWfpyhList(JsglModel model)  {
		QueryModel queryModel = model.getQueryModel();
		try {
			SearchParser.parseMybatisSQL(model.getSearchModel());
			queryModel.setItems(jsglService.getPagedWfpyhList(model));
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
		return queryModel;
	}
        
	
	/**
	 * 
	 * <p>方法说明：已分配用户列表<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午4:46:29<p>
	 * @param model
	 * @return JSONObject
	 */
	@ResponseBody
	@RequiresPermissions("jsgl:fpyh")
	@RequestMapping(value = "/getYfpyhList")
    public Object getYfpyhList(JsglModel model){
        try {
        	SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
	        queryModel.setItems(jsglService.getPagedYfpyhList(model));
	        return queryModel;
        } catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
    }
	
	
	/**
	 * 
	 * <p>方法说明：分配用户角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午5:05:35<p>
	 * @param model
	 * @return ModelAndView
	 */
	@RequiresPermissions("jsgl:fpyh")
	@RequestMapping(value = "/fpyh")
	public ModelAndView fpyh(JsglModel model) {
		ModelAndView view = new ModelAndView();
		try {
			// 查询单个角色信息
			JsglModel jsglModel = jsglService.getModel(model);
			view.addObject("model",jsglModel);
			view.setViewName("/globalweb/comp/xtgl/jsgl/fpyh");
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}
	
	
	/**
	 * 
	 * <p>方法说明：角色分配用户<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月17日上午10:09:48<p>
	 * @param jsdm 角色代码
	 * @param ids 职工号
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "角色【${jsdm}】分配用户【${ids}】", ywmc = "角色管理", czlx = BusinessType.INSERT)
	@ResponseBody
	@RequiresPermissions("jsgl:fpyh")
	@RequestMapping(value = "/inertYhfp")
	public Object inertYhfp(@RequestParam String jsdm ,@RequestParam String ids) {
		try {
			String[] zghs = ids.split(",");
			boolean isSuccess = jsglService.insertYhfp(jsdm, zghs);
			MessageKey key = isSuccess ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	
	/**
	 * 
	 * <p>方法说明：取消分配用户<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月17日上午10:35:47<p>
	 * @param jsdm 角色代码
	 * @param ids 职工号
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "角色【${jsdm}】取消分配用户【${ids}】", ywmc = "角色管理", czlx = BusinessType.DELETE)
	@ResponseBody
	@RequiresPermissions("jsgl:fpyh")
	@RequestMapping(value = "/deleteYhfp")
	public Object deleteYhfp(@RequestParam String jsdm ,@RequestParam String ids) {
		try {
			String[] zghs = ids.split(",");
			boolean isSuccess = jsglService.deleteYhfp(jsdm, zghs);
			MessageKey key = isSuccess ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	
	
	
	/**
	 * 
	 * <p>方法说明：功能授权<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月18日下午7:04:55<p>
	 * @param jsdm 角色代码
	 * @return ModelAndView
	 */
	@RequiresPermissions("jsgl:gnsq")
	@RequestMapping(value = "/gnsq")
	public ModelAndView gnsq(@RequestParam String jsdm){	
		ModelAndView view = new ModelAndView();
		try {
			JsglModel jsglModel = jsglService.getModel(jsdm);
			List<Menu> gnqxList = jsglService.getAllGnqxList();
			List<AncdModel> buttonList = jsglService.getButtonList(jsdm);
			view.addObject("model", jsglModel);
			view.addObject("gnqxList", gnqxList);
			view.addObject("buttonList", buttonList);
			view.setViewName("/globalweb/comp/xtgl/jsgl/gnsq");
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}
	
	
	
	/**
	 * 
	 * <p>方法说明：角色功能授权<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月24日下午3:23:07<p>
	 * @param jsdm 角色代码
	 * @param gnczid 操作ID
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "角色【${jsdm}】授权", ywmc = "角色管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequiresPermissions("jsgl:gnsq")
	@RequestMapping(value = "/saveGnqx")
	public Object saveGnqx(@RequestParam String jsdm, String[] gnczid){	
		try {
			//是系统管理员角色，拒绝修改功能授权
			if(AdminUserRoleUtils.existAdminRole(new String[] {jsdm})) {
				MessageKey key = MessageKey.SAVE_FAIL;
				return key.getJson();
			}
			boolean isSuccess = jsglService.insertGnqx(jsdm, gnczid);
			MessageKey key = isSuccess ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	
	/**
	 * 
	 * <p>方法说明：查询二级授权<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月27日上午9:22:57<p>
	 * @return java.lang.String
	 */
	@RequiresPermissions("ejsq:cx")
	@RequestMapping(value = "/cxEjsq")
	public String cxEjsq(){	
		return "/globalweb/comp/xtgl/jsgl/cxEjsq";
	}
	
	
	/**
	 * 
	 * <p>方法说明：ajax查询二级授权用户列表<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月27日上午10:05:07<p>
	 * @param model 角色
	 * @return JSONObject
	 */
	@ResponseBody
	@RequiresPermissions("ejsq:cx")
	@RequestMapping(value = "/getEjsqList")
	public Object getEjsqList(EjqxModel model){
		try {
			User user = getUser();
			model.setJsdm(user.getJsdm());
			model.setGxz(user.getYhm());
			SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
			List<YhglModel> pagedList = jsglService.getPagedEjsqList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	
	/**
	 * 
	 * <p>方法说明：二级授权<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月27日上午9:21:37<p>
	 * @return ModelAndView
	 */
	@RequiresPermissions("ejsq:cx")
	@RequestMapping(value = "/ejsq")
	public ModelAndView ejsq(@RequestParam String zgh){	
		ModelAndView view = new ModelAndView();
		try {
			User user = getUser();
			List<Menu> gnqxList =  jsglService.getGnqxByJsdm(user.getYhm(),user.getJsdm());
			JsglModel jsglModel = jsglService.getModel(user.getJsdm());
			
			EjqxModel ejqxModel = new EjqxModel();
			ejqxModel.setGxz(user.getYhm());
			ejqxModel.setSxz(zgh);
			ejqxModel.setJsdm(user.getJsdm());
			List<AncdModel> buttonList = jsglService.getEjqxList(ejqxModel);
			
			YhglModel yhgl = new YhglModel();
			yhgl.setZgh(zgh);
			YhglModel yhglModel = yhglService.getModel(yhgl);
			
			view.addObject("gnqxList", gnqxList);
			view.addObject("model", jsglModel);
			view.addObject("yhglModel", yhglModel);
			view.addObject("buttonList", buttonList);
			view.setViewName("/globalweb/comp/xtgl/jsgl/ejsq");
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}
	
	
	/**
	 * 
	 * <p>方法说明：保存二级授权<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月27日下午1:37:49<p>
	 * @param gnczid 功能操作ID
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "二级授权,用户：${zgh!}", ywmc = "角色管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequiresPermissions("ejsq:cx")
	@RequestMapping(value = "/saveEjsq")
	public Object saveEjsq(@RequestParam String zgh, String[] gnczid){	
		try {
			User user = getUser();
			EjqxModel model = new EjqxModel();
			model.setGxz(user.getYhm());
			model.setSxz(zgh);
			model.setJsdm(user.getJsdm());
			model.setGnczids(gnczid);
			boolean isSuccess = jsglService.insertEjsq(model);
			MessageKey key = isSuccess ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	/**
	 * 
	 * <p>方法说明：数据授权<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月17日下午2:57:06<p>
	 * @param jsdm 角色代码
	 * @return ModelAndView
	 */
	@RequiresPermissions("jsgl:sjsq")
	@RequestMapping(value = "/sjsq")
	public ModelAndView sjsq(@RequestParam String jsdm){	
		ModelAndView view = new ModelAndView();
		try {
			JsglModel jsglModel = jsglService.getModel(jsdm);
			List<SjzyModel> sjzyList = sjzyService.getModelList();
			List<String> jsgzList = jsglService.getSjqxByJsdm(jsdm);
			
			view.addObject("model", jsglModel);
			view.addObject("sjzyList", sjzyList);
			view.addObject("jsgzList", jsgzList);
			view.setViewName("/globalweb/comp/xtgl/jsgl/sjsq");
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}
	
	
	
	/**
	 * 
	 * <p>方法说明：角色数据授权<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月28日下午3:13:09<p>
	 * @param jsdm 角色代码
	 * @param gzids 规则ID
	 * @return JSONObject
	 */
	@BusinessLog(czmk = "系统管理", czms = "角色【${jsdm!}】数据授权", ywmc = "角色管理", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequiresPermissions("jsgl:sjsq")
	@RequestMapping(value = "/saveSjqx")
	public Object saveSjqx(@RequestParam String jsdm, @RequestParam String gzids){	
		try {
			String[] gzidArr = StringUtils.isNull(gzids) ?  null : gzids.split(",");
			boolean isSuccess = jsglService.insertSjqx(jsdm, gzidArr);
			MessageKey key = isSuccess ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}	

}
