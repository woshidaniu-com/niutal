package com.woshidaniu.globalweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.BusinessLog;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.CsszModel;
import com.woshidaniu.service.svcinterface.ICsszService;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：参数设置
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年10月18日下午4:18:07
 */
@Controller
@RequestMapping("/xtgl/xtsz")
public class CsszController extends BaseController {

	@Autowired
	private ICsszService csszService;

	@RequiresPermissions("cssz:cx")
	@RequestMapping("/cxCssz")
	public String cxCssz(CsszModel model){
		return "/globalweb/comp/xtgl/xtsz/cxCssz";
	}
	
	/**
	 * 
	 * <p>方法说明：获取参数设置<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年10月19日下午2:39:17<p>
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("cssz:cx")
	@RequestMapping("/getCsszList")
	public Object getCsszList(CsszModel model){
		try {
			QueryModel queryModel = model.getQueryModel();
			List<CsszModel> itemList = csszService.getModelList(model);
			queryModel.setItems(itemList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	/**
	 * 
	 * <p>方法说明：保存参数设置<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年10月19日下午2:39:04<p>
	 * @param request
	 * @param model
	 * @return
	 */
	@BusinessLog(czmk = "系统管理", czms = "修改系统参数", ywmc = "参数设置", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequiresPermissions("cssz:bc")
	@RequestMapping(value = "/saveCssz")
	public Object saveCssz(HttpServletRequest request,CsszModel model) { 
		try {
//			List<CsszModel> itemList = csszService.getModelList(model);
//			for (CsszModel cssz : itemList){
//				
//				if (Integer.valueOf(cssz.getZdlx()) == 4){
//					//多选
//					String[] zdzArr = request.getParameterValues(cssz.getZdm());
//					CsszModel csszModel = new CsszModel();
//					csszModel.setZdm(cssz.getZdm());
//					String zdz = ArrayUtils.toString(zdzArr);
//					if (zdz.length() > 2){
//						csszModel.setZdz(zdz.substring(1, zdz.length()-1));
//						csszService.update(csszModel);
//					}
//				} else {
//					String zdz = request.getParameter(cssz.getZdm());
//					CsszModel csszModel = new CsszModel();
//					csszModel.setZdm(cssz.getZdm());
//					csszModel.setZdz(zdz);
//					csszService.update(csszModel);
//				}
//			}
			csszService.saveCssz(model, request.getParameterMap());
			return MessageKey.SAVE_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

}
