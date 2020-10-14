package com.woshidaniu.globalweb.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.RzglModel;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.service.svcinterface.IRzglService;

/**
 * 
* 类名称：RzglAction
* 类描述：日志管理
* 创建人：qph
* 创建时间：2012-4-20 
* 修改备注： 
*
*/
@Controller
@RequestMapping("/xtgl/rzgl")
public class RzglController extends BaseController {

	@Autowired
	private IRzglService service;
	
//	@Autowired
//	@Qualifier("exportExcelPOI")
//	private IExportService  exportService;
	
	
//	private ExportModel exportModel = new ExportModel();
	
	
	/**
	 * 操作日志查询
	 * @return
	 */
	@RequestMapping("/cxRz")
	public String cxRz() {
		return "/globalweb/comp/xtgl/rzgl/cxRz";
	}
	
	@ResponseBody
	@RequiresPermissions("rzgl:cx")
	@RequestMapping("/getLogsList")
	public Object getLogsList(RzglModel model){
		try {
			SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(service.getPagedList(model));
			return queryModel;
		}catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	
	/**
	 * 查看日志详细信息
	 * @return
	 */
	public String ckRzxx(HttpServletRequest request ,RzglModel model){
		try {
			RzglModel rzglModel = service.getModel(model.getCzbh());
			request.setAttribute("model", rzglModel);
			return "/globalweb/comp/xtgl/rzgl/ckRzxx";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		} 
	}

	
	/**
	 * 导出
	 * @return
	 */
	public String export(){
        try{
//            User user = getUser();
//            exportModel.setZgh(user.getYhm());
//            exportModel.setDcclbh(EXPORT_ID);
//            model.getQueryModel().setShowCount(Integer.MAX_VALUE);
//            exportModel.setDataList(service.getPagedList(model));
//            file = exportService.getExportFile(exportModel);
        } catch(Exception e){
//            logException(e);
//            return ERROR;
        }
        return "null";
    }
	
//	/**
//	 * 自定义导出
//	 * @return
//	 */
//    public String customExport(){
//        ValueStack stack = getValueStack();
//        stack.set("dcclbh", EXPORT_ID);
//        return "toExportConfig";
//    }
	
	public IRzglService getService() {
		return service;
	}

	public void setService(IRzglService service) {
		this.service = service;
	}
	
}
