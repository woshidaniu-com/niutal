/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.BusinessLog;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.wjdc.dao.entites.DjrffModel;
import com.woshidaniu.wjdc.dao.entites.StglModel;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.dao.entites.WjhdModel;
import com.woshidaniu.wjdc.dao.entites.XxglModel;
import com.woshidaniu.wjdc.dao.entites.YwgnModel;
import com.woshidaniu.wjdc.enums.QuestionType;
import com.woshidaniu.wjdc.enums.QuestionnaireStatus;
import com.woshidaniu.wjdc.filter.QuestionnaireFilter;
import com.woshidaniu.wjdc.service.svcinterface.IDjrffService;
import com.woshidaniu.wjdc.service.svcinterface.IStglService;
import com.woshidaniu.wjdc.service.svcinterface.IWjdcExportService;
import com.woshidaniu.wjdc.service.svcinterface.IWjglService;
import com.woshidaniu.wjdc.service.svcinterface.IYwgnService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Penghui.Qu(445)
 * 功能实现
 * 
 * @author ：康康（1571）
 * 整理，优化
 * 
 * */
@Controller
@RequestMapping(value = "/wjdc/wjgl")
public class WjglController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(WjglController.class);
	//默认用户类型，如果当用户类型为null时
	private static final String DEFAULT_USER_TYPE_IF_USER_YHLX_IS_NULL = "student";
	//是否异步提交配置项（用户回答一题提交一题）配置key
	private static final String USE_ASYNC_SUMIT_KEY = "niutal.wjdc.useAsyncSubmit";
	//是否使用开启试卷优先级配置key
	private static final String ENABLE_SJYXJ_KEY = "niutal.wjdc.enableSjyxj";
	
	private static final String BLANK_TYPE="0";
	//是否采用异步提交每个问题的回答(用户回答一题就提交一题)
	protected boolean useAsync = true;
	//是否使用开启试卷优先级
	protected boolean enableSjyxj = false;
	
	protected boolean onlyUpdateFinishFlagOnSubmit = false;
	
	@Autowired
	private IWjglService wjglService;
	@Autowired
	private IStglService stglService;
	@Autowired
	private IYwgnService ywgnService;
	@Autowired
	private IWjdcExportService wjdcExportService;
	@Autowired
	private IDjrffService djrffService;
	
	//private boolean checkWjIfBelongToMe = false;
	//是否用户答卷自适应效果
	private boolean yhdjZsy = false;
	
	@PostConstruct
	public void init() {
		
		//是否异步提交表单
		String useAsyncSubmitValue = MessageUtil.getText(USE_ASYNC_SUMIT_KEY);
		if("true".equals(useAsyncSubmitValue)) {
			this.useAsync = true;
		}else {
			this.useAsync = false;
		}
		log.info("问卷调查提交方式,是否使用异步提交方式(回答一题就提交一题),配置文件key:[{}],配置文件value:[{}],系统采用value:[{}]",USE_ASYNC_SUMIT_KEY,useAsyncSubmitValue,this.useAsync);
		
		//是否使用试卷优先级
		String enableSjyxjValue = MessageUtil.getText(ENABLE_SJYXJ_KEY);
		if("true".equals(enableSjyxjValue)) {
			this.enableSjyxj = true;
		}else {
			this.enableSjyxj = false;
		}
		log.info("问卷调查,是否启用试卷优先级,配置文件key:[{}],配置文件value:[{}],系统采用value:[{}]",ENABLE_SJYXJ_KEY,enableSjyxjValue,this.enableSjyxj);
		
		{
			//是否用户答卷自适应效果
			String val =  MessageUtil.getText("niutal.wjdc.yhdjZsy");
			this.yhdjZsy = val != null ? Boolean.parseBoolean(val) : this.yhdjZsy;
			log.info("问卷调查,用户答卷自适应:{}",this.yhdjZsy);
		}
		
		{
			String val =  MessageUtil.getText("niutal.wjdc.onlyUpdateFinishFlagOnSubmit");
			this.onlyUpdateFinishFlagOnSubmit = val != null ? Boolean.parseBoolean(val) : this.onlyUpdateFinishFlagOnSubmit;
			log.info("问卷调查,异步提交问卷时是否仅仅更新答卷完成状态:{}",this.onlyUpdateFinishFlagOnSubmit);
		}
		/**
		{
			String val =  MessageUtil.getText("niutal.wjdc.checkWjIfBelongToMe");
			this.checkWjIfBelongToMe = val != null ? Boolean.parseBoolean(val) : this.checkWjIfBelongToMe;
			log.info("问卷调查,checkWjIfBelongToMe:{}",this.checkWjIfBelongToMe);
		}
		**/
	}
	
	/**
	 * @description	： 查询问卷信息
	 * @return
	 */
	@RequiresPermissions("wjgl:cx")
	@RequestMapping(value = "/cxWjxx")
	public String cxWjxx() {
		return "/wjdc/wjgl/cxWjxx";
	}
	
	/**
	 * 查看问卷基本信息
	 * @return
	 */
	@RequiresPermissions("wjgl:ck")
	@RequestMapping(value = "/ckWjBaseXx")
	public ModelAndView ckBaseWjxx(String wjid){
		
		ModelAndView mav = new ModelAndView("/wjdc/wjgl/ckWjBaseXx");
		WjglModel wjglModel = this.wjglService.getModel(wjid);
		mav.addObject("model", wjglModel);
		return mav;
	}
	
	/**
	 * @description	： ajax加载问卷信息
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wjgl:cx")
	@RequestMapping(value = "/getWjxxList")
	@ResponseBody
	public Object getWjxxList(WjglModel model) {
		try {
			SearchParser.parseMybatisSQL(model);
			QueryModel queryModel = model.getQueryModel();
			if(!getUser().isAdmin()){
				model.setCjr(getUser().getYhm());
			}
			queryModel.setItems(wjglService.getPagedList(model));
			return queryModel;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * @description	： 增加问卷信息
	 * @param map
	 * @return
	 */
	@RequiresPermissions("wjgl:zj")
	@RequestMapping(value = "/zjWjxx")
	public String zjWjxx(Model model) {
		try {
			model.addAttribute("enableSjyxj", this.enableSjyxj);
			String nextWjid = this.wjglService.getNextWjid();
			model.addAttribute("wjid", nextWjid);
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
		return "/wjdc/wjgl/zjWjxx";
	}
	/**
	 * @description	： 增加保存问卷信息
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("wjgl:zj")
	@RequestMapping(value = "/zjBcWjxx")
	@BusinessLog(czmk = "问卷调查", czms = "新增问卷：${model.wjmc}", ywmc = "问卷管理", czlx = BusinessType.INSERT)
	public Object saveWjxx(WjglModel model) {
		try {
			User user = getUser();
			model.setWjzt(QuestionnaireStatus.DRAFT);
			model.setCjr(user.getYhm());
			boolean result = wjglService.insert(model);
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * @description	： 复制文件页面
	 * @param model
	 * @param wjid
	 * @return
	 */
	@RequiresPermissions("wjgl:fz")
	@RequestMapping(value = "/copyWjxx")
	public String copyWjxx(Model model,String wjid) {
		try {
			WjglModel wjglModel = wjglService.getModel(wjid);
			//修改问卷名称
			String wjmc = wjglModel.getWjmc();
			String newWjmc = wjmc + "_copy";
			wjglModel.setWjmc(newWjmc);
			model.addAttribute("enableSjyxj", this.enableSjyxj);
			model.addAttribute("model", wjglModel);
			return "/wjdc/wjgl/copyWjxx";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}
	/**
	 * @description	： 保存copy问卷信息
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("wjgl:fz")
	@RequestMapping(value = "/saveCopyWjxx")
	@BusinessLog(czmk = "问卷调查", czms = "保存复制问卷：${model.wjmc}", ywmc = "问卷管理", czlx = BusinessType.INSERT)
	public Object saveCopyWjxx(WjglModel model) {
		try {
			User user = getUser();
			model.setWjzt(QuestionnaireStatus.DRAFT);
			model.setCjr(user.getYhm());
			boolean result = wjglService.insertCopyWj(model);
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * @description	：修改问卷信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wjgl:xg")
	@RequestMapping(value = "/xgWjxx")
	public String xgWjxx(Model model,String wjid) {
		try {
			WjglModel wjglModel = wjglService.getModel(wjid);
			
			model.addAttribute("enableSjyxj", this.enableSjyxj);
			model.addAttribute("model", wjglModel);
			return "/wjdc/wjgl/xgWjxx";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}
	/**
	 * @description	： 修改保存问卷信息
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("wjgl:xg")
	@RequestMapping(value = "/xgBcWjxx")
	@BusinessLog(czmk = "问卷调查", czms = "修改问卷：${model.wjid}", ywmc = "问卷管理", czlx = BusinessType.UPDATE)
	public Object modifyWjxx(WjglModel model) {
		try {
			boolean result = wjglService.update(model);
			MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * @description	：删除问卷信息
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("wjgl:sc")
	@RequestMapping(value = "/scWjxx")
	@BusinessLog(czmk = "问卷调查", czms = "删除问卷：${ids}", ywmc = "问卷管理", czlx = BusinessType.UPDATE)
	public Object deleteWjxx(@RequestParam String ids) {
		try {
			boolean result = wjglService.scWjxx(ids);
			MessageKey key = result ? MessageKey.DELETE_SUCCESS : MessageKey.DELETE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * @description	： 修改问卷状态
	 * @param model
	 * @param request
	 * @return
	 */
	@RequiresPermissions("wjgl:ztxg")
	@RequestMapping(value = "/updateWjzt")
	public String updateWjzt(WjglModel model, HttpServletRequest request) {
		try {
			WjglModel wjglModel = wjglService.getModel(model);
			request.setAttribute("model", wjglModel);
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
		return "/wjdc/wjgl/xgWjzt";
	}
	/**
	 * @description	： 修改问卷状态
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("wjgl:ztxg")
	@RequestMapping(value = "/modifyWjzt")
	@BusinessLog(czmk = "问卷调查", czms = "修改问卷状态：${model.wjid}-${model.wjzt}", ywmc = "问卷管理", czlx = BusinessType.UPDATE)
	public Object modifyWjzt(WjglModel model) {
		try {
			boolean result = wjglService.update(model);
			MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * @description	： 获得问卷分发列表
	 * @param wjid
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("wjgl:wjff")
	@RequestMapping(value = "/getWjffList")
	public Object getWjffList(@RequestParam(name="wjid",required=true) String wjid) {
		try {
			List<Map<String, String>> result = wjglService.getWjffList(wjid);
			return result;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	/**
	 * @description	：问卷分发
	 * @param wjid
	 * @return
	 */
	@RequiresPermissions("wjgl:wjff")
	@RequestMapping(value = "/wjff")
	public synchronized ModelAndView wjff(@RequestParam String wjid) {
		ModelAndView view = new ModelAndView();
		try {

			//文件分发条件
			view.addObject("wjffList", this.wjglService.getInitedFfdxModels());
			
			WjglModel model = wjglService.getModel(wjid);
			List<Map<String, String>> ffmxList = wjglService.getWjffList(wjid);
			List<String> wjgnList = wjglService.getWjgnList(wjid);
			List<YwgnModel> ywgnList = ywgnService.getYwgnList();

			view.addObject("ffmxList", ffmxList);
			view.addObject("wjgnList", wjgnList);
			view.addObject("ywgnList", ywgnList);
			view.addObject("model", model);
			view.setViewName("/wjdc/wjgl/wjff");
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}

	
	/**
	 * @description	： 加载发放人数
	 * @param ffdx
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("wjgl:wjff")
	@GetMapping("/getFfrs")
	public int getFfrs(@RequestParam String ffdx) {
		return wjglService.getFfrs(ffdx);
	}
	/**
	 * @description	： 保存问卷分发
	 * @param wjid
	 * @param ffdx
	 * @param gnid
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("wjgl:wjff")
	@RequestMapping(value = "/saveWjff")
	public Object saveWjff(@RequestParam String wjid, @RequestParam String ffdx, @RequestParam String gnid) {
		try {
			boolean result = wjglService.saveWjff(wjid, ffdx, gnid);
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * @description	： 编辑试题
	 * @param wjid
	 * @param type
	 * @return
	 */
	@RequiresPermissions("wjgl:wjsj")
	@RequestMapping("/editWjst/{wjid}")
	public ModelAndView editWjst(@PathVariable String wjid,String type) {
		ModelAndView view = new ModelAndView();
		try {
			WjglModel wjModel = wjglService.getModel(wjid);
			List<StglModel> stxxList = stglService.getStxxAndStdlXxList(wjModel);
			List<XxglModel> xxxxList = stglService.getStXxxxList(wjModel);
			//将跳转试题id转换为索引
			for(int i=0;i<xxxxList.size();i++) {
				XxglModel xxglModel = xxxxList.get(i);
				String tzstid = xxglModel.getTzstid();
				if(StringUtils.isNotEmpty(tzstid)) {
					String tzstIndex = tzstid.replace("stid_", "");
					xxglModel.setTzstid(tzstIndex);
				}
			}
			view.addObject("model", wjModel);
			view.addObject("stxxList", stxxList);
			view.addObject("xxxxList", xxxxList);
			
			if (BLANK_TYPE.equals(type)){
				view.setViewName("/wjdc/wjgl/editWjst");
			} else {
				view.setViewName("/wjdc/wjgl/importWjst");
			}
			
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}
	/**
	 * @description	： 问卷预览
	 * @param wjid
	 * @param isShowHeader
	 * @return
	 */
	@RequiresPermissions("wjgl:wjyl")
	@RequestMapping("/ckWjxx")
	public ModelAndView ckWjxx(//
			HttpServletRequest request,//
			@RequestParam(name="wjid",required=true)String wjid,//
			@RequestParam(name="isShowHeader",defaultValue="true",required=false)Boolean isShowHeader//
			) {
		//模拟答卷，但无法提交
		ModelAndView view = this.yhdj(request, wjid, isShowHeader, true);
		return view;
	}
	/**
	 * @description	： 用户答卷
	 * @param request
	 * @param wjid
	 * @param isShowHeader
	 * @return
	 */
	@RequestMapping("/yhdj")
	public ModelAndView yhdj(//
			HttpServletRequest request,//
			@RequestParam(name="wjid",required=true)String wjid,//
			@RequestParam(name="isShowHeader",defaultValue="true",required=false)Boolean isShowHeader,
			@RequestParam(name="isyl",defaultValue="false",required=false)boolean isyl//是否是预览
			) {
		ModelAndView view = new ModelAndView();
		try {
			WjglModel wjModel = wjglService.getModel(wjid);
			
			if(wjModel == null){
				view.setViewName("/exception/404");
				return view;
			}
			
			User user = this.getUser();
			wjModel.setDjrid(user.getYhm());
			//用户类型
			String yhlx = user.getYhlx();
			if(StringUtils.isEmpty(yhlx)) {
				user.setYhlx(DEFAULT_USER_TYPE_IF_USER_YHLX_IS_NULL);
			}

			//若当前用户不是此问卷创建人，则需判断是否分发这个问卷给当前用户
			if(!isyl && !user.getYhm().equals(wjModel.getCjr())){
				SafetyCheckResult checkResult = this.checkWjExist(wjid,user.getYhm());
				if(!checkResult.accept){
					view.setViewName("/exception/404");
					return view;
				}
			}
			
			List<String> wjids = this.wjglService.getFinishedWjidList(user.getYhm());
			boolean finish = wjids.contains(wjid);
			
			List<StglModel> stxxList = stglService.getYhdjStxxAndStdlXxList(wjModel);
			List<XxglModel> xxxxList = stglService.getYhdjStXxxxList(wjModel);
			
			//过滤最后一个选项，设置状态,并设置最后一个选项的input的内容
			this.processStLastStxx(wjid,xxxxList,user.getYhm(),isyl);
			
			boolean isRun = QuestionnaireStatus.RUN.equals(wjModel.getWjzt());
			
			if(isyl || finish || !isRun) {
				view.addObject("acceptSubmit","false");//不显示提交按钮
			}else {
				view.addObject("acceptSubmit","true");//显示提交按钮
				
				//若是'未开始'就更新成'答卷中'
				this.djrffService.updateDoingZt(user.getYhm(),wjid,yhlx);
			}
			
			if(isyl || finish) {//预览不会异步提交
				//处理跳转试题的隐藏
				this.processTzstDisplayNone(stxxList,xxxxList);
				view.addObject("useAsyncSubmit", "false");
			}else {//真正的答卷会异步提交
				if(useAsync) {
					//处理跳转试题的隐藏
					this.processTzstDisplayNone(stxxList,xxxxList);
					view.addObject("useAsyncSubmit", "true");
				}else {
					view.addObject("useAsyncSubmit", "false");
				}
			}
			view.addObject("isShowHeader",isShowHeader);
			view.addObject("model", wjModel);
			view.addObject("pageTitle", "问卷调查答卷");
			view.addObject("stxxList", stxxList);
			view.addObject("xxxxList", xxxxList);
			
			String contextPath = request.getContextPath();
			view.addObject("contextPath",contextPath);
			
			if(this.yhdjZsy){
				view.setViewName("/wjdc/wjgl/yhdj_zsy");				
			}else{
				view.setViewName("/wjdc/wjgl/yhdj");
			}
			
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}
	/**
	 * @description	： 处理用户已经回答的试题下面的跳转试题是否隐藏，标记这样的试题隐藏
	 * @param stxxList
	 * @param xxxxList
	 */
	private void processTzstDisplayNone(List<StglModel> stxxList, List<XxglModel> xxxxList) {
		//聚合所有选择到具体试题
		//key是试题id,vlaue是这个试题的所有选项
		Map<String,List<XxglModel>> map = new HashMap<String,List<XxglModel>>(stxxList.size());
		for(XxglModel xxglModel:xxxxList) {
			String stid = xxglModel.getStid();
			List<XxglModel> xxglModelList = map.get(stid);
			if(xxglModelList == null) {
				xxglModelList = new ArrayList<XxglModel>();
				map.put(stid, xxglModelList);
			}
			xxglModelList.add(xxglModel);
		}
		
		//遍历试题，找出需要隐藏的试题
		boolean hideSwitch = false;
		String tzstid = null;
		for(int i=0;i<stxxList.size();i++) {
			StglModel stglModel = stxxList.get(i);
			String stlx = stglModel.getStlx();
			String stid = stglModel.getStid();
			if(hideSwitch && !stid.equals(tzstid)) {
				stglModel.setDisplay("none");				
				continue;
			}
			if(stid.equals(tzstid)) {
				hideSwitch = false;
				tzstid = null;
			}
			//单项选择题和单项组合题
			if("1".equals(stlx) || "2".equals(stlx)) {
				List<XxglModel> xxglModelList = map.get(stid);
				XxglModel checkedXxglModel = this.findCheckedAndhasTzstIdStxx(xxglModelList);
				if(checkedXxglModel != null) {
					//在此之间的所有试题应该都是隐藏的
					hideSwitch = true;
					String tzstidParam = checkedXxglModel.getTzstid();
					if(map.containsKey(tzstidParam)) {
						tzstid = tzstidParam;
					}
				}
			}
		}
	}
	/**
	 * @description	： 找到已经选中的选项
	 * @param xxglModelList
	 * @return
	 */
	private XxglModel findCheckedAndhasTzstIdStxx(List<XxglModel> xxglModelList){
		for(XxglModel xxglModel : xxglModelList) {
			String checked = xxglModel.getChecked();
			String tzstid = xxglModel.getTzstid();
			if("1".equals(checked) && StringUtils.isNotEmpty(tzstid)) {
				return xxglModel;
			}
		}
		return null;
	}
	/**
	 * @description	： 过滤标记最后一个选项
	 * @param xxxxList
	 * @param isyl 
	 */
	private void processStLastStxx(String wjid,List<XxglModel> xxxxList,String djrid,boolean isyl) {
		
		Map<String,Map<String,String>> stTxnrMap = this.stglService.getStLastOptionValues(wjid,djrid);
		 
		Map<String,XxglModel> map = new HashMap<String,XxglModel>();
		
		for(XxglModel xxglModel:xxxxList) {
			String stid = xxglModel.getStid();
			map.put(stid, xxglModel);
		}
		Iterator<Entry<String, XxglModel>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, XxglModel> e = it.next();
			XxglModel xx = e.getValue();
			xx.setIsLastxx("true");
			
			//最后一个选项的填写内容
			String stid = xx.getStid();
			Map<String,String> keyValueMap = stTxnrMap.get(stid);
			if(keyValueMap != null) {
				String lastXxTxnr = keyValueMap.get("txnr");
				if(!isyl && StringUtils.isNotEmpty(lastXxTxnr)) {
					xx.setLastxxOption(lastXxTxnr);	
				}
			}
		}
	}
	
	/**
	 * @description	： ajax保存用户答卷的试题选项
	 * @param wjModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxSaveYhdjStxx")
	@BusinessLog(czmk = "问卷调查", czms = "ajax保存用户试题选项：${wjModel.wjid!}", ywmc = "问卷管理", czlx = BusinessType.INSERT)
	public Object ajaxSaveYhdjStxx(WjglModel wjModel, HttpServletRequest request) {
		User user = this.getUser();
		String wjid = wjModel.getWjid();
		String yhlx = user.getYhlx();
		if(StringUtils.isEmpty(yhlx)) {
			user.setYhlx(DEFAULT_USER_TYPE_IF_USER_YHLX_IS_NULL);
		}
		
		{
			SafetyCheckResult checkResult = this.checkWjExist(wjid, this.getUser().getYhm());
			if(!checkResult.accept){
				Map<String, String> map = new HashMap<String, String>();
				map.put("message", checkResult.msg);
				map.put("status", "fail");
				return map;
			}
		}
		
		{
			SafetyCheckResult checkResult = this.checkWjRunStatus(wjid, this.getUser().getYhm());
			if(!checkResult.accept){
				Map<String, String> map = new HashMap<String, String>();
				map.put("message", checkResult.msg);
				map.put("status", "fail");
				return map;
			}
		}
		
		try {
			boolean result = wjglService.saveSubmitYhdj(user, wjModel, request, true);
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		}catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * @description	： ajax保存用户答卷的多选择题的排序
	 * @param wjModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxSubmitSort")
	@BusinessLog(czmk = "问卷调查", czms = "ajax保存用户试题排序,问卷id：${wjid},试题id:${stid},选项列表:${xxids}", ywmc = "问卷管理", czlx = BusinessType.INSERT)
	public Object ajaxSubmitSort(String wjid,String stid,String xxids) {
		User user = this.getUser();
		String yhlx = user.getYhlx();
		if(StringUtils.isEmpty(yhlx)) {
			user.setYhlx(DEFAULT_USER_TYPE_IF_USER_YHLX_IS_NULL);
		}
		
		{
			SafetyCheckResult checkResult = this.checkWjExist(wjid, this.getUser().getYhm());
			if(!checkResult.accept){
				Map<String, String> map = new HashMap<String, String>();
				map.put("message", checkResult.msg);
				map.put("status", "fail");
				return map;
			}
		}
		
		{
			SafetyCheckResult checkResult = this.checkWjRunStatus(wjid, this.getUser().getYhm());
			if(!checkResult.accept){
				Map<String, String> map = new HashMap<String, String>();
				map.put("message", checkResult.msg);
				map.put("status", "fail");
				return map;
			}
		}
		
		try {
			boolean result = wjglService.saveAjaxSubmitSort(user,wjid,stid,xxids);
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		}catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * @description	： ajax查询多选题的排序
	 * @param wjModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDxSort")
	@BusinessLog(czmk = "问卷调查", czms = "ajax保存用户试题排序,问卷id：${wjid},试题id:${stid}", ywmc = "问卷管理", czlx = BusinessType.INSERT)
	public Object queryDxSort(String wjid,String stid) {
		User user = this.getUser();
		String yhlx = user.getYhlx();
		if(StringUtils.isEmpty(yhlx)) {
			user.setYhlx(DEFAULT_USER_TYPE_IF_USER_YHLX_IS_NULL);
		}
		
		{
			SafetyCheckResult checkResult = this.checkWjExist(wjid, this.getUser().getYhm());
			if(!checkResult.accept){
				Map<String, String> map = new HashMap<String, String>();
				map.put("message", checkResult.msg);
				map.put("status", "fail");
				return map;
			}
		}
		
		{
			SafetyCheckResult checkResult = this.checkWjRunStatus(wjid, this.getUser().getYhm());
			if(!checkResult.accept){
				Map<String, String> map = new HashMap<String, String>();
				map.put("message", checkResult.msg);
				map.put("status", "fail");
				return map;
			}
		}
		
		try {
			List<WjhdModel> wjhdList = wjglService.queryDxSort(user,wjid,stid);
			return wjhdList;
		}catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * @description	： ajax保存用户答卷的试题选项
	 * @param wjModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxDeleteYhdjStxx")
	@BusinessLog(czmk = "问卷调查", czms = "ajax删除用户试题选项：${stid!}", ywmc = "问卷管理", czlx = BusinessType.INSERT)
	public Object ajaxDeleteYhdjStxx(String wjid,String stid,HttpServletRequest request) {
		try {
			User user = this.getUser();
			
			{
				SafetyCheckResult checkResult = this.checkWjExist(wjid, this.getUser().getYhm());
				if(!checkResult.accept){
					Map<String, String> map = new HashMap<String, String>();
					map.put("message", checkResult.msg);
					map.put("status", "fail");
					return map;
				}
			}
			
			{
				SafetyCheckResult checkResult = this.checkWjRunStatus(wjid, this.getUser().getYhm());
				if(!checkResult.accept){
					Map<String, String> map = new HashMap<String, String>();
					map.put("message", checkResult.msg);
					map.put("status", "fail");
					return map;
				}
			}
			
			String currentUserId = user.getYhm();
			Set<String> stids = new HashSet<String>(1);
			stids.add(stid);
			wjglService.deleteBatchYhdjStxx(currentUserId, wjid, stids);
			MessageKey key = MessageKey.SAVE_SUCCESS;
			return key.getJson();
		}catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * @description	： 提交用户答卷
	 * @param wjModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submitYhdj")
	@BusinessLog(czmk = "问卷调查", czms = "提交用户答卷：${wjModel.wjid!}", ywmc = "问卷管理", czlx = BusinessType.INSERT)
	public Object submitYhdj(WjglModel wjModel, HttpServletRequest request) {
		try {
			String wjid = wjModel.getWjid();
			User user = this.getUser();
			String yhlx = user.getYhlx();
			if(StringUtils.isEmpty(yhlx)) {
				user.setYhlx(DEFAULT_USER_TYPE_IF_USER_YHLX_IS_NULL);
			}
			
			{
				SafetyCheckResult checkResult = this.checkWjExist(wjid, this.getUser().getYhm());
				if(!checkResult.accept){
					Map<String, String> map = new HashMap<String, String>();
					map.put("message", checkResult.msg);
					map.put("status", "fail");
					return map;
				}
			}
			
			{
				SafetyCheckResult checkResult = this.checkWjRunStatus(wjid, this.getUser().getYhm());
				if(!checkResult.accept){
					Map<String, String> map = new HashMap<String, String>();
					map.put("message", checkResult.msg);
					map.put("status", "fail");
					return map;
				}
			}
			
			//异步模式下，仅仅修改问卷答卷状态为完成
			if(useAsync && this.onlyUpdateFinishFlagOnSubmit){
				this.wjglService.updateSubmitFinish(user, wjid);
			}else{
				this.wjglService.saveSubmitYhdj(user,wjModel,request,false);
			}

			HttpSession httpSession = request.getSession();
			httpSession.removeAttribute(QuestionnaireFilter.UN_FINISH_WJLIST_SESSION_KEY);
			
			MessageKey key = MessageKey.SAVE_SUCCESS;
			return key.getJson();
		}catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * @description	： 保存问卷试题信息
	 * @param wjid
	 * @param wjst
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("wjgl:wjsj")
	@RequestMapping(value = "/saveWjst")
	@BusinessLog(czmk = "问卷调查", czms = "设计问卷：${wjid}", ywmc = "问卷管理", czlx = BusinessType.UPDATE)
	public Object saveWjst(@RequestParam String wjid, @RequestParam String wjst) {
		JSONArray json = JSONArray.fromObject(wjst);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<Map<String, String>> stflList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> stxxList = new ArrayList<Map<String, String>>();
		
		//跳转试题信息对
		Set<TzstInfoPair> tzstInfoPairs = new HashSet<TzstInfoPair>();
		
		try {
			String flid = null;
			for (int i = 0; i < json.size(); i++) {
				JSONObject o = (JSONObject) json.get(i);
				Map<String, String> map = new HashMap<String, String>();
				map.put("wjid", wjid);
				String id = String.format("stid_%d", i);
				
				int type = o.getInt("type");
				
				if (QuestionType.DESCRIBE.getKey() == type) {// 描述说明
					
					flid = id;
					String flmc = o.getString("mssm");
					map.put("stdlid", flid);
					map.put("stdlmc", flmc);
					map.put("xssx", o.getString("xssx"));
					map.put("dqfs", o.getString("dqfs"));
					stflList.add(map);
				} else if (QuestionType.TEXT.getKey() == type) {// 文本题
					
					map.put("stid", id);
					map.put("stdlid", flid);
					map.put("stmc", o.getString("title"));
					map.put("stlx", o.getString("type"));
					map.put("sfbd", o.getString("sfbt"));
					map.put("xssx", o.getString("xssx"));
					map.put("zdzs", o.getString("zdzs"));
					map.put("wbgd", o.getString("wbgd"));
					map.put("wblx", o.getString("wblx"));
					map.put("ts", o.getString("ts"));
					list.add(map);
				} else if(
						QuestionType.RADIO.getKey() == type || //单选
						QuestionType.RADIO_GROUP.getKey() == type ||// 单选组合
						QuestionType.MULTI.getKey() == type ||//多选
						QuestionType.MULTI_GROUP.getKey() == type//多选组合
						){
					
					map.put("stid", id);
					map.put("stdlid", flid);
					map.put("stmc", o.getString("title"));
					map.put("stlx", o.getString("type"));
					map.put("sfbd", o.getString("sfbt"));
					map.put("xssx", o.getString("xssx"));
					map.put("xxkzdxzs", o.getString("kxgs"));
					map.put("ts", o.getString("ts"));
					map.put("mhxxgs", o.getString("mhxxgs"));
					if(QuestionType.MULTI.getKey() == type) {
						map.put("sfyxpx", o.getString("sfyxpx"));//多选题，是否允许排序
					}
					list.add(map);
					
					//解析试题选项
					JSONArray stxxJsonObjectArray = o.getJSONArray("stxx");
					for (int x = 0; x < stxxJsonObjectArray.size(); x++) {
						Map<String, String> m = new HashMap<String, String>();
						String xxid = String.format("%s_xxid_%d", id, x);
						JSONObject jsonObject = stxxJsonObjectArray.getJSONObject(x);
						
						m.put("wjid", wjid);
						m.put("stid", id);
						m.put("xxid", xxid);
						
						String xxmc = jsonObject.getString("xxmc");
						m.put("xxmc", xxmc);
						
						m.put("xssx", String.valueOf(x));

						//选项分值
						if(jsonObject.containsKey("xxfz")) {
							String xxfz = jsonObject.getString("xxfz");
							if(StringUtils.isEmpty(xxfz)) {
								xxfz = "0";
							}
							m.put("xxfz", xxfz);								
						}
						
						if(jsonObject.containsKey("tzstid")) {
							String tzstid = jsonObject.getString("tzstid");
							if(StringUtils.isNotBlank(tzstid)) {
								TzstInfoPair pair = new TzstInfoPair(id, xxid, tzstid);
								tzstInfoPairs.add(pair);
							}
						}
						stxxList.add(m);
					}
				}else {// end of if
					log.warn("系统未定义的试题类型:{}",type);
				}
			}//end of for
			
			//构建tzstid字段
			if(!tzstInfoPairs.isEmpty()) {
				Iterator<Map<String, String>> it = stxxList.iterator();
				while(it.hasNext()) {
					Map<String,String> stxxMap = it.next();
					String stid = stxxMap.get("stid");
					String xxid = stxxMap.get("xxid");
					Iterator<TzstInfoPair> it_pair = tzstInfoPairs.iterator();
					while(it_pair.hasNext()) {
						TzstInfoPair pair = it_pair.next();
						String stid_in_pair = pair.stid;
						String xxid_in_pair = pair.xxid;
						String tzstid_in_pair = pair.tzstid;
						
						//若存在填写tzstid
						if(stid.equals(stid_in_pair) && xxid.equals(xxid_in_pair)) {
							stxxMap.put("tzstid", "stid_"+tzstid_in_pair);
						}
					}
				}
			}
			boolean result = wjglService.saveWjst(wjid, stflList, list, stxxList);
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			log.error("保存问卷试题异常，问卷id:{},异常源头文本:{}",wjid,wjst,e);
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * @description	： 导出答题详情
	 * @param wjid
	 * @return
	 */
	@RequiresPermissions("wjgl:djxq")
	@RequestMapping(value = "/exportDtxq", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> exportDtxq(@RequestParam String wjid) {
		try {
			File file = wjdcExportService.getDtxqById(wjid);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", new String("答题详情.xls".getBytes(), "ISO8859-1"));
			byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
			headers.setContentLength(bytes.length);
			return new ResponseEntity<byte[]>(bytes , headers , HttpStatus.OK);
		} catch (Exception e) {
			logException(e);
			return null;
		}
	}
	/**
	 * @description	： 导出定制化答题详情
	 * @param wjid
	 * @return
	 */
	@RequiresPermissions("wjgl:djxq")
	@RequestMapping(value = "/exportDtxqCustom", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> exportDtxqCustom(@RequestParam String wjid) {
		try {
			File file = wjdcExportService.exportDtxqById_10698(wjid);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", new String("定制化答题详情.xls".getBytes(), "ISO8859-1"));
			byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
			headers.setContentLength(bytes.length);
			return new ResponseEntity<byte[]>(bytes , headers , HttpStatus.OK);
		} catch (Exception e) {
			logException(e);
			return null;
		}
	}
	/**
	 * 问卷选项统计
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wjgl:djtj")
	@RequestMapping(value = "/xxtj")
	public synchronized ModelAndView wjtj(WjglModel model){
		ModelAndView view = new ModelAndView();
		try {
			List<Map<String,Object>> wjtjList = wjglService.getWjtjList(model.getWjid());
			List<StglModel> stglList = stglService.getStxxAndStdlXxList(model);
			view.addObject("wjtjList", wjtjList);
			view.addObject("stglList", stglList);
			view.addObject("wjffList", this.wjglService.getInitedFfdxModels());
			view.setViewName("/wjdc/wjtj/xxtj");
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}
	/**
	 * @description	： 导出统计
	 * @param model
	 */
	@RequiresPermissions("wjgl:djtj")
	@RequestMapping(value = "/exportXxtj")
	@ResponseBody
	public ResponseEntity<byte[]> exportWjtj(String wjid) {
		try {
			File newXlsFile = this.wjdcExportService.exportWjtj(wjid);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", new String("统计.xls".getBytes(), "ISO8859-1"));
			byte[] bytes = IOUtils.toByteArray(new FileInputStream(newXlsFile));
			headers.setContentLength(bytes.length);
			return new ResponseEntity<byte[]>(bytes , headers , HttpStatus.OK);
		} catch (Exception e) {
			logException(e);
			return null;
		}
	}
	
	private static class SafetyCheckResult {
		
		private static final SafetyCheckResult SUCESS = new SafetyCheckResult(true);
		
		public SafetyCheckResult(boolean accept) {
			super();
			this.accept = accept;
		}

		private boolean accept;
		
		private String msg = "";
	}
	
	/**
	 * 检查某个用户是否分发给了某一个问卷
	 * @param wjid
	 * @param yhm
	 * @return
	 */
	private SafetyCheckResult checkWjExist(String wjid,String yhm){
		
		if(StringUtils.isEmpty(wjid)){
			SafetyCheckResult safetyCheckResult = new SafetyCheckResult(false);
			safetyCheckResult.msg = "问卷ID参数不能为空!";
			return safetyCheckResult;
		}
		
		//此处检查此用户是否被分发此问卷，若无，返回错误
		List<DjrffModel> djrffModelList = this.djrffService.queryByWjidAndZjz(wjid,yhm);
		if(djrffModelList.isEmpty()){
			SafetyCheckResult safetyCheckResult = new SafetyCheckResult(false);
			safetyCheckResult.msg = "此问卷未分发给当前用户!";
			return safetyCheckResult;
		}
		
		return SafetyCheckResult.SUCESS;
	}
	
	private SafetyCheckResult checkWjRunStatus(String wjid,String yhm){
		
		//检查问卷是否处于运行状态,若非运行状态，则不允许提交
		WjglModel wjModelInDatabase = wjglService.getModel(wjid);
		QuestionnaireStatus status = wjModelInDatabase.getWjzt();
		if(status == QuestionnaireStatus.DRAFT || status == QuestionnaireStatus.STOP){
			
			SafetyCheckResult safetyCheckResult = new SafetyCheckResult(false);
			safetyCheckResult.msg = "当前问卷不是运行状态!";
			return safetyCheckResult;
		}
		
		return SafetyCheckResult.SUCESS;
	}
	
	/**
	 * @description	： 跳转试题信息对
	 */
	private static class TzstInfoPair{
		
		//试题id
		final String stid;
		//选项id
		final String xxid;
		//跳转试题id
		final String tzstid;
		
		public TzstInfoPair(String stid, String xxid, String tzstid) {
			super();
			this.stid = stid;
			this.xxid = xxid;
			this.tzstid = tzstid;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((stid == null) ? 0 : stid.hashCode());
			result = prime * result + ((tzstid == null) ? 0 : tzstid.hashCode());
			result = prime * result + ((xxid == null) ? 0 : xxid.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TzstInfoPair other = (TzstInfoPair) obj;
			if (stid == null) {
				if (other.stid != null)
					return false;
			} else if (!stid.equals(other.stid))
				return false;
			if (tzstid == null) {
				if (other.tzstid != null)
					return false;
			} else if (!tzstid.equals(other.tzstid))
				return false;
			if (xxid == null) {
				if (other.xxid != null)
					return false;
			} else if (!xxid.equals(other.xxid))
				return false;
			return true;
		}
	}

}
