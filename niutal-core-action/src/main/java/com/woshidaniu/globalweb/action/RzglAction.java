package com.woshidaniu.globalweb.action;


import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.dao.entities.RzglModel;
import com.woshidaniu.export.service.svcinterface.IExportService;
import com.woshidaniu.service.svcinterface.IRzglService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

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
@Scope("prototype")
public class RzglAction extends BaseAction implements ModelDriven<RzglModel> {

	private static final long serialVersionUID = 1L;

	private static final String EXPORT_ID = "xtrz";
	
	@Autowired
	private IRzglService service;
	
	@Autowired
	@Qualifier("exportExcelPOI")
	private IExportService  exportService;
	
	private RzglModel model = new RzglModel();
	
	private ExportModel exportModel = new ExportModel();
	
	public RzglModel getModel() {
		return model;
	}
	
	/**
	 * 操作日志查询
	 * @return
	 */
	public String cxRz() {

		try {
			if (QUERY.equals(model.getDoType())){
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(service.getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "cxRz";
	}

	
	/**
	 * 查看日志详细信息
	 * @return
	 */
	public String ckRzxx(){
		try {
			RzglModel model = service.getModel(this.model.getCzbh());
			BeanUtils.copyProperties(this.model, model);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		} 
		return "ckRzxx";
	}

	
	/**
	 * 导出
	 * @return
	 */
	public String export(){
        try{
            User user = getUser();
            exportModel.setZgh(user.getYhm());
            exportModel.setDcclbh(EXPORT_ID);
            model.getQueryModel().setShowCount(Integer.MAX_VALUE);
            exportModel.setDataList(service.getPagedList(model));
            file = exportService.getExportFile(exportModel);
        } catch(Exception e){
            logException(e);
            return ERROR;
        }
        return "exportExcel";
    }
	
	/**
	 * 自定义导出
	 * @return
	 */
    public String customExport(){
        ValueStack stack = getValueStack();
        stack.set("dcclbh", EXPORT_ID);
        return "toExportConfig";
    }
	
	public IRzglService getService() {
		return service;
	}

	public void setService(IRzglService service) {
		this.service = service;
	}
	
}
