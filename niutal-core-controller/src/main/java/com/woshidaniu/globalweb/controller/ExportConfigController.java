/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.globalweb.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportConfigVoModel;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.export.service.svcinterface.IExportAuthService;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.service.svcinterface.IJsglService;

/**
 * @description	： 自定义导出配置Controller
 * @author 		：康康（1571）
 */
@Controller
@RequestMapping(value = "/xtgl/dcpz")
public class ExportConfigController extends BaseController{
	
	//使用前缀，避免业务方部署多台机器，导致虚拟ID一样
	private long startTime = System.currentTimeMillis();
	
	private AtomicLong index = new AtomicLong(0);
	
	private String nextId() {
		return String.format("%s%s", startTime,index.incrementAndGet());
	}
	
	@Autowired
	private IJsglService jsglService;
	
	@Autowired
	@Qualifier("exportAuthService")
	private IExportAuthService exportAuthService;
	
	@RequiresPermissions("dcpz:cx")
	@RequestMapping(value = "/cx")
	public String cxDcpz() {
		return "/globalweb/comp/xtgl/dcpz/cxDcpz";
	}
	
	@RequiresPermissions("dcpz:cx")
	@RequestMapping(value = "/getDcpzList")
	@ResponseBody
	public Object getDcpzList(ExportConfigModel model,HttpSession httpSession){
		try {
			SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
			
			List<ExportConfigVoModel> pagedList = exportAuthService.getPagedExportConfigVoModelList(model);
			
			List<JsglModel> jsglModelList = this.jsglService.getModelList(new JsglModel());
			
			Map<String,String> jsdmToJsmcMap = new HashMap<String,String>();
			for(JsglModel jsglModel : jsglModelList) {
				String jsdm = jsglModel.getJsdm();
				String jsmc = jsglModel.getJsmc();
				jsdmToJsmcMap.put(jsdm, jsmc);
			}
			
			for(ExportConfigVoModel voModel : pagedList) {
				voModel.setId(nextId());
				
				List<String> jsdmList = voModel.getSqzList();
				List<String> jsmcList = new ArrayList<String>();
				
				for(String jsdm : jsdmList) {
					String jsmc = jsdmToJsmcMap.get(jsdm);
					jsmcList.add(jsmc);
				}
				
				Collections.sort(jsmcList);
				
				String sqzMcList = StringUtils.join(jsmcList, ",");
				
				voModel.setSqzMcStrList(sqzMcList);
			}
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	@RequiresPermissions("dcpz:xg")
	@RequestMapping(value = "/xgDcpz")
	public ModelAndView xgDcpz(@RequestParam(name="dcclbh",required=true)String dcclbh,@RequestParam(name="zd",required=true)String zd) {
		ModelAndView view = new ModelAndView();
		try {
			
			ExportConfigVoModel model = exportAuthService.getExportConfigVoModel(dcclbh, zd);
			view.addObject("model", model);
			
			List<JsglModel> jsxxList = jsglService.getModelList();
			view.addObject("jsxxList", jsxxList);
			
			view.setViewName("/globalweb/comp/xtgl/dcpz/xgDcpz");
		} catch (Exception e) {
			 logException(e);
			 view.setViewName(ERROR_VIEW);
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/modifyDcpz")
	@RequiresPermissions("dcpz:xg")
	public Object modifyDcpz(@RequestParam(name="dcclbh",required=true)String dcclbh,@RequestParam(name="zd",required=true)String zd,@RequestParam(name="jsdms",required=true)String[] jsdms) {
		try {
			int c = this.exportAuthService.updateExportAuth(dcclbh, zd, jsdms);
			MessageKey key = c > 0 ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	//TODO 后续增加以下功能
	/**
	@RequestMapping(value = "/ckDcpz")
	@RequiresPermissions("dcpz:ck")
	public ModelAndView ckDcpz(@RequestParam(name="dcclbh",required=true)String dcclbh,@RequestParam(name="zd",required=true)String zd) {
		ModelAndView view = new ModelAndView();
		try {
			ExportConfigVoModel model = exportAuthService.getExportConfigVoModel(dcclbh, zd);
			view.addObject("model", model);
			view.setViewName("/globalweb/comp/xtgl/dcpz/ckDcpz");
		} catch (Exception e) {
			logException(e);
			view.setViewName(ERROR_VIEW);
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/scDcpz")
	@RequiresPermissions("dcpz:sc")
	public Object scDcpz(@RequestParam(name="dcclbh",required=true)String dcclbh,@RequestParam(name="zd",required=true)String zd) {
		try {
			int c = exportAuthService.deleteExportConfigModel(dcclbh, zd);
			MessageKey key = c > 0 ? MessageKey.DELETE_SUCCESS : MessageKey.DELETE_FAIL;
			return key.getJson();
		}catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	**/
}
