package com.woshidaniu.jcsj.action;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.annotation.HtmlEncodeAnotationHandler;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IExportService;
import com.woshidaniu.jcsj.dao.entities.XydmModel;
import com.woshidaniu.jcsj.dao.entities.ZydmModel;
import com.woshidaniu.jcsj.service.svcinterface.IXydmService;
import com.woshidaniu.jcsj.service.svcinterface.IZydmService;

/**
 * 专业代码维护
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class ZydmAction extends BaseAction implements ModelDriven<ZydmModel>{

	private static final long serialVersionUID = 1L;

	private ZydmModel model = new ZydmModel();
	private ExportModel exportModel = new ExportModel();
	
	@Autowired
	@Qualifier("exportExcelPOI")
	private IExportService  exportService;
	@Autowired
	private IZydmService zydmService;
	@Autowired
	private IXydmService xydmService;
	
	
	private void setValueStack() throws Exception {
		ValueStack vs = getValueStack();
		List<XydmModel> xyxxList = xydmService.cxXydmList();
		HtmlEncodeAnotationHandler.handle(xyxxList);
		vs.set("xyxxList", xyxxList);
	}
	
	
	/**
	 * 专业查询（跳转）
	 * @return
	 */
	public String cxZydm(){
		try {
			setValueStack();
		} catch (Exception ex) {
			logException(ex);
		}
		return "cxZydm"; 
	}
	
	
	/**
	 * 按学院查询专业代码
	 * @return
	 */
	public String cxZydmByXydm(){
		
		try {
			
			List<ZydmModel> zydmList = null;
			
			if (StringUtils.isEmpty(model.getBmdm_id_lsbm())){
				zydmList = zydmService.cxZydm();
			} else {
				zydmList = zydmService.cxZydmList(model.getBmdm_id_lsbm());
			}
			HtmlEncodeAnotationHandler.handle(zydmList);
			getValueStack().set(DATA, zydmList);
		} catch (Exception ex) {
			logException(ex);
		}
		
		return DATA;
	}
	
	
	/**
	 * ajax查询专业代码
	 * @return
	 */
	public String cxZydmList(){
		try {
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(zydmService.getPagedList(model));
			getValueStack().set(DATA, queryModel);
			return DATA;
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}
	
	
	/**
	 * 增加专业代码
	 * @return
	 */
	public String zjZydm() {
		try {
			setValueStack();
		} catch (Exception ex) {
			logException(ex);
		}
		return "zjZydm";
	}
	
	
	
	/**
	 * 增加专业代码
	 * @return
	 */
	public String zjBcZydm() {
		try {
			boolean result = false;
			String key = "S00001";
			if (validateZydm(model.getZydm())) {
				key = "I99002M";
				getValueStack().set(DATA, getText(key, new String[]{"专业代码已存在"}));
			}else{
				result = zydmService.zjZydm(model);
				key = result ? "I99001" : "I99002";
				getValueStack().set(DATA, getText(key));
			}
			
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText(key));
		}
		return DATA;
	}
	
	/**
	 * 修改专业代码
	 * @return
	 * @throws Exception
	 */
	public String xgZydm() throws Exception{
		try {
			ValueStack vs = getValueStack();
			ZydmModel zydmModel = zydmService.getModel(model);
			try {
				BeanUtils.copyProperties(model, zydmModel);
				setValueStack();
			} catch (Exception e) {
				e.printStackTrace();
			}
			vs.set("model", model);

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "xgZydm";
	}
	
	
	/**
	 * 修改保存专业代码
	 * @return
	 */
	public String xgBcZydm() {
		try {
			ValueStack vs = getValueStack();
			boolean result = zydmService.update(model);
			String key = result ? "I99003" : "I99004";
			getValueStack().set(DATA, getText(key));

			vs.set("model", model);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return DATA;
	}
	
	
	/**
	 * 删除专业代码
	 * @return
	 */
	public String scZydm(){
		String ids = getRequest().getParameter("ids");
		String resultMessage = zydmService.scZydm(ids);
		getValueStack().set(DATA, resultMessage);
		return DATA;
	}

	
	
	public ZydmModel getModel() {
		return model;
	}
	

	public void setZydmService(IZydmService zydmService) {
		this.zydmService = zydmService;
	}

	public void setXydmService(IXydmService xydmService) {
		this.xydmService = xydmService;
	}

	
	public void setExportService(IExportService exportService) {
		this.exportService = exportService;
	}


	private static final String EXPORT_ID = "zydm";
	
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
            exportModel.setDataList(zydmService.getPagedList(model));
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

    /**
     * 验证专业代码
     * @return
     */
    private boolean validateZydm(String zydm){
    	ZydmModel m = new ZydmModel();
    	m.setZydm(zydm);
    	return zydmService.getCount(m) >= 1;
    }
    
	public ExportModel getExportModel() {
		return exportModel;
	}

	public void setExportModel(ExportModel exportModel) {
		this.exportModel = exportModel;
	}
    
}
