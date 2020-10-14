package com.woshidaniu.globalweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.woshidaniu.basemodel.QueryModel;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.BusinessLog;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.dao.entities.PWModel;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.globalweb.PWUtils;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.service.svcinterface.ISjyglService;

/**
 * @className: SjyglController
 * @description: 数据源管理控制层
 * @author : Hanyc
 * @date: 2018-12-03 09:28:04
 * @version V1.0
 */
@Controller
@RequestMapping("/pwgl/sjygl")
public class SjyglController extends BaseController {

	@Autowired
	protected ISjyglService service;

	/** @fields : PAGE_ROOT_DIR : 页面文件目录 */
	private static final String PAGE_ROOT_DIR = "/globalweb/comp/pwgl/sjygl";

	/**
	 * @description: 查询
	 * @author : Hanyc
	 * @date : 2018-12-03 09:23:17
	 * @param request
	 * @return
	 */
	@RequiresPermissions("PWsjy:cx")
	@RequestMapping("/cx")
	public String cx(HttpServletRequest request){
		try{
			List<SjbzModel> sjyList = service.getSjyList();
			request.setAttribute("sjyList", sjyList);
			return PAGE_ROOT_DIR + "/cx";
		}catch(Exception e){
			super.logException(e);
			return ERROR_VIEW;
		}
	}

	/**
	 * @description: 异步获取列表数据
	 * @author : Hanyc
	 * @date : 2018-12-03 09:23:13
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("PWsjy:cx")
	@RequestMapping("/getPagedListAjax")
	public Object getPagedListAjax(PWModel model, HttpServletRequest request){
		try{
			SearchParser.parseMybatisSQL(model);
			QueryModel queryModel = model.getQueryModel();
			List<PWModel> pagedList = service.getPagedList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		}catch(Exception e){
			super.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * @description: 异步获取对应模块的字段
	 * @author : Hanyc
	 * @date : 2018-12-04 14:32:47
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCols")
	public Object getCols(PWModel model){
		try{
			String sjy = service.getValue(model.getMkdm());
			model.setSjy(sjy);
			List<PWModel> cols = service.getRestColsList(model);
			return cols;
		}catch(Exception e){
			super.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * @description: 增加
	 * @author : Hanyc
	 * @date : 2018-12-04 11:41:52
	 * @param model
	 * @param view
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("PWsjy:zj")
	@RequestMapping("/zj")
	public ModelAndView zj(PWModel model, ModelAndView view){
		try{
			List<SjbzModel> sjyList = service.getSjyList();
			List<SjbzModel> sfList = PWUtils.getSfList();

			view.addObject("sjyList", sjyList);
			view.addObject("sfList", sfList);
			view.setViewName(PAGE_ROOT_DIR + "/zj");
		}catch(Exception e){
			super.logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}

	/**
	 * @description: 增加保存
	 * @author : Hanyc
	 * @date : 2018-12-04 11:48:13
	 * @param model
	 * @param view
	 * @return
	 */
	@BusinessLog(czmk = "PDF/WORD数据源管理", czms = "新增数据：“模块：${model.mkdm!}，字段：${model.col!}，字段显示：${model.com!}，主键标识：${model.key}”。", ywmc = "增加", czlx = BusinessType.INSERT)
	@ResponseBody
	@RequiresPermissions("PWsjy:zj")
	@RequestMapping("/zjbc")
	public Object zjbc(PWModel model){
		try{
			return service.insert(model) ? MessageKey.DO_SUCCESS.status(new Object[]{"增加"})
					: MessageKey.DO_SUCCESS.status(new Object[]{"增加"});
		}catch(Exception e){
			super.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * @description: 修改
	 * @author : Hanyc
	 * @date : 2018-12-04 11:41:52
	 * @param model
	 * @param view
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("PWsjy:xg")
	@RequestMapping("/xg")
	public ModelAndView xg(PWModel model, ModelAndView view){
		try{
			List<SjbzModel> sfList = PWUtils.getSfList();
			List<SjbzModel> sjyList = service.getSjyList();

			view.addObject("sjyList", sjyList);
			view.addObject("sfList", sfList);
			view.addObject("model", service.getModel(model));
			view.setViewName(PAGE_ROOT_DIR + "/xg");
		}catch(Exception e){
			super.logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}

	/**
	 * @description: 修改保存
	 * @author : Hanyc
	 * @date : 2018-12-04 11:48:13
	 * @param model
	 * @param view
	 * @return
	 */
	@BusinessLog(czmk = "PDF/WORD数据源管理", czms = "修改数据：“模块：${model.mkdm!}，字段：${model.col!}，字段显示：${model.com!}，主键标识：${model.key}”。", ywmc = "修改", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequiresPermissions("PWsjy:xg")
	@RequestMapping("/xgbc")
	public Object xgbc(PWModel model){
		try{
			return service.update(model) ? MessageKey.DO_SUCCESS.status(new Object[]{"修改"})
					: MessageKey.DO_SUCCESS.status(new Object[]{"修改"});
		}catch(Exception e){
			super.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/**
	 * @description: 删除
	 * @author : Hanyc
	 * @date : 2018-12-04 11:48:13
	 * @param model
	 * @return
	 */
	@BusinessLog(czmk = "PDF/WORD数据源管理", czms = "删除数据", ywmc = "删除", czlx = BusinessType.DELETE)
	@ResponseBody
	@RequiresPermissions("PWsjy:sc")
	@RequestMapping("/sc")
	public Object sc(PWModel model){
		try{
			return service.delete(model) ? MessageKey.DO_SUCCESS.status(new Object[]{"删除"})
					: MessageKey.DO_SUCCESS.status(new Object[]{"删除"});
		}catch(Exception e){
			super.logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

}