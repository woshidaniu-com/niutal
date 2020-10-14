package com.woshidaniu.jcsj.action;


import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.annotation.HtmlEncodeAnotationHandler;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IExportService;
import com.woshidaniu.jcsj.dao.entities.BjdmModel;
import com.woshidaniu.jcsj.dao.entities.XydmModel;
import com.woshidaniu.jcsj.dao.entities.ZydmModel;
import com.woshidaniu.jcsj.service.svcinterface.IBjdmService;
import com.woshidaniu.jcsj.service.svcinterface.IXydmService;
import com.woshidaniu.jcsj.service.svcinterface.IZydmService;

/**
 * 班级维护
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class BjdmAction extends BaseAction implements ModelDriven<BjdmModel>{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IBjdmService bjdmService;
	@Autowired
	private IXydmService xydmService;
	@Autowired
	private IZydmService zydmService;
	@Autowired
	@Qualifier("exportExcelPOI")
	private IExportService  exportService;
	
	private BjdmModel model = new BjdmModel();
	private ExportModel exportModel = new ExportModel();
	
	public BjdmModel getModel() {
		return model;
	}

	public void setBjdmService(IBjdmService bjdmService) {
		this.bjdmService = bjdmService;
	}

	public void setXydmService(IXydmService xydmService) {
		this.xydmService = xydmService;
	}

	public void setZydmService(IZydmService zydmService) {
		this.zydmService = zydmService;
	}
	

	public void setExportService(IExportService exportService) {
		this.exportService = exportService;
	}

	/**
	 * 查询班级代码（跳转）
	 * @return
	 */
	public String cxBjdm(){
		try{
			List<XydmModel> xydmList = xydmService.cxXydmList();
			List<ZydmModel> zydmList = zydmService.cxZydm();
			HtmlEncodeAnotationHandler.handle(xydmList);
			HtmlEncodeAnotationHandler.handle(zydmList);
			ValueStack vs = getValueStack();
			vs.set("xydmList", xydmList);
			vs.set("zydmList", zydmList);
		} catch(Exception ex){
			logException(ex);
		}
		return "cxBjdm";
	}
	
	
	/**
	 * 查询班级代码（供选择）
	 * @return
	 */
	public String cxBjdmForSelect(){
		try{
			List<XydmModel> xydmList = xydmService.cxXydmList();
			List<ZydmModel> zydmList = zydmService.cxZydm();
			HtmlEncodeAnotationHandler.handle(xydmList);
			HtmlEncodeAnotationHandler.handle(zydmList);
			ValueStack vs = getValueStack();
			vs.set("xydmList", xydmList);
			vs.set("zydmList", zydmList);
		} catch(Exception ex){
			logException(ex);
		}
		return "cxBjdmForSelect";
	}
	
	
	
	
	/**
	 * ajax查询班级代码
	 * @return
	 */
	public String cxBjdmList(){
		try{
			QueryModel queryModel = model.getQueryModel();
			List<BjdmModel> pagedList = bjdmService.getPagedList(model);
			HtmlEncodeAnotationHandler.handle(pagedList);
			queryModel.setItems(pagedList);
			getValueStack().set(DATA, queryModel);
		} catch(Exception ex){
			logException(ex);
		}
		return DATA;
	}
	
	
	public String cxBjdmByXyZydm(){
		try {
			List<BjdmModel> bjdmList = null;
			bjdmList=bjdmService.getModelList(model);
			getValueStack().set(DATA, bjdmList);
		} catch (Exception ex) {
			logException(ex);
		}
		
		return DATA;
	}
	
	/**
	 * 增加班级代码 
	 * @return
	 */
	public String zjBjdm(){
		try{
			List<XydmModel> xydmList = xydmService.cxXydmList();
			List<ZydmModel> zydmList = zydmService.cxZydm();
			HtmlEncodeAnotationHandler.handle(xydmList);
			HtmlEncodeAnotationHandler.handle(zydmList);
			ValueStack vs = getValueStack();
			vs.set("xydmList", xydmList);
			vs.set("zydmList", zydmList);
		} catch(Exception ex){
			logException(ex);
		}
		return "zjBjdm";
	}
	
	
	
	/**
	 * 增加班级代码（保存）
	 * @return
	 */
	public String zjBcBjdm(){
		String key = "S00001";
		boolean result = false;
		try{
			if(validateBjdm(model.getBjdm())){
				key = "I99002M";
				getValueStack().set(DATA, getText(key, new String[]{"班级代码已存在"}));
			}else{
				result = bjdmService.insert(model);
				key = result ? "I99001" : "I99002";
				getValueStack().set(DATA, getText(key));
			}
		} catch(Exception ex){
			logException(ex);
			getValueStack().set(DATA, getText(key));
		}
		return DATA;
	}
	
	
	/**
	 * 修改班级代码
	 * @return
	 */
	public String xgBjdm(){
		
		try{
			BjdmModel bjdmModel = bjdmService.getModel(model.getBjdm_id());
			BeanUtils.copyProperties(model, bjdmModel);
			
			List<XydmModel> xydmList = xydmService.cxXydmList();
			
			//需要根据选择的学院删选出专业信息
			List<ZydmModel> zydmList = zydmService.cxZydmList(bjdmModel.getBmdm_id());
			
			HtmlEncodeAnotationHandler.handle(xydmList);
			HtmlEncodeAnotationHandler.handle(zydmList);
			ValueStack vs = getValueStack();
			vs.set("xydmList", xydmList);
			vs.set("zydmList", zydmList);
		} catch(Exception ex){
			logException(ex);
		}
		return "xgBjdm";
	}
	
	
	
	/**
	 * 修改保存班级代码
	 * @return
	 */
	public String xgBcBjdm(){
		
		try{
			boolean result = bjdmService.update(model);
			String key = result ? "I99003" : "I99004";
			getValueStack().set(DATA, getText(key));
		} catch(Exception ex){
			logException(ex);
		}
		
		return DATA;
	}
	
	
	/**
	 * 删除班级
	 * @return
	 */
	public String scBjdm(){
		String ids = getRequest().getParameter("ids");
		String resultMessage = bjdmService.scBjdm(ids);
		getValueStack().set(DATA, resultMessage);
		return DATA;
	}
	
	
	private static final String EXPORT_ID = "bjdm";
	
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
            exportModel.setDataList(bjdmService.getPagedList(model));
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
     * 验证班级代码是否重复
     * @param bjdm
     * @return
     */
    private boolean validateBjdm(String bjdm){
    	BjdmModel m = new BjdmModel();
    	m.setBjdm(bjdm);
    	return bjdmService.getCount(m) >= 1;
    }
    
    
	public ExportModel getExportModel() {
		return exportModel;
	}

	public void setExportModel(ExportModel exportModel) {
		this.exportModel = exportModel;
	}
    
}
