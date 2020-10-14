package com.woshidaniu.jcsj.action;


import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IExportService;
import com.woshidaniu.jcsj.dao.entities.XydmModel;
import com.woshidaniu.jcsj.service.svcinterface.IXydmService;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.ISjbzService;

/**
 * 学院维护
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class XydmAction extends BaseAction implements ModelDriven<XydmModel>{

	private static final long serialVersionUID = 1L;

	private XydmModel model = new XydmModel();
	private ExportModel exportModel = new ExportModel();
	
	@Autowired
	private IXydmService xydmService;
	@Autowired
	@Qualifier("exportExcelPOI")
	private IExportService  exportService;
	@Autowired
	private ISjbzService sjbzService;
	
	private BaseLog baseLog = LogEngineImpl.getInstance();
	
	
	
	/**
	 * 学院代码查询
	 * @return
	 */
	public String cxXydm(){
		
		try {
			getValueStack().set("bmlxList", sjbzService.getYwsjList("bmlx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "cxXydm";
	}
	
	
	/**
	 * 学院信息查询 
	 * @return
	 */
	public String cxXydmList(){
		try {
			QueryModel queryModel = xydmService.getQueryResult(model);
//			queryModel.setItems(xydmService.getPagedList(model));
			getValueStack().set(DATA, queryModel);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	

	/**
	 * 
	 * 方法描述: 增加
	 * 参数 @return
	 * 参数说明
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String zjXydm() {
		try {
				getValueStack().set("bmlxList", sjbzService.getYwsjList("bmlx"));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "zjXydm";
	}
	
	/**
	 * 
	 * 方法描述: 保存
	 * 参数 @return 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String zjBcXydm() {
		try {
			User user = getUser();
			boolean result = xydmService.insert(model);
			String key = result ? "I99001" : "I99002";
			getValueStack().set(DATA, getText(key));
			if (result) {
				// 记操作日志
				baseLog.insert(user, getText("log.message.ywmc",
				 new String[] { "部门代码", "niutal_xtgl_bmdmb" }),
				"系统管理", getText("log.message.czms", new
				String[] { "新增学院", "学院代码", model.getBmdm_id()}));
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	/**
	 * 
	 * 方法描述: 验证 
	 * 参数 @return 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String valideXydm() throws Exception {
		XydmModel xydmModel = new XydmModel();
		xydmModel.setBmdm_id(getRequest().getParameter("pkValue"));
		// 查询单个信息
		xydmModel = xydmService.getModel(xydmModel);

		if (null != xydmModel) {
			getValueStack().set(DATA, "该学院代码已经存在!");
		}
		return DATA;
	}
	
	/**
	 * 
	 * 方法描述: 修改学院代码
	 * 参数 @return 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String xgXydm() throws Exception{
		try {
			ValueStack vs = getValueStack();
			XydmModel xydmModel = new XydmModel();

			// 查询单个信息
			xydmModel = xydmService.getModel(model);

			BeanUtils.copyProperties(model, xydmModel);
			vs.set("model", model);
			vs.set("bmlxList", sjbzService.getYwsjList("bmlx"));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "xgXydm";
	}
	
	/**
	 * 
	 * 方法描述: 修改保存学院代码
	 * 参数 @return 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String xgBcXydm() {
		try {

			User user = this.getUser();
			ValueStack vs = getValueStack();

			// 修改信息
			boolean result = xydmService.update(model);
			String key = result ? "I99003" : "I99004";
			getValueStack().set(DATA, getText(key));

			vs.set("model", model);
			if (result) {
				// 记操作日志
				baseLog.update(user, getText("log.message.ywmc",
				 new String[] { "学院代码", "niutal_xtgl_bmdmb" }),
				 "系统管理", getText("log.message.czms", new
				 String[] { "修改学院代码", "学院代码", model.getBmdm_id() }));
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return DATA;
	}
	
	/**
	 * 删除学院代码
	 * @return
	 */
	public String scXydm(){
		String ids = getRequest().getParameter("ids");
		String resultMessage = xydmService.scXydm(ids);
		getValueStack().set(DATA, resultMessage);
		return DATA;
	}
	
	public XydmModel getModel() {
		return model;
	}

	public void setModel(XydmModel model) {
		this.model = model;
	}


	public IXydmService getXydmService() {
		return xydmService;
	}


	public void setXydmService(IXydmService xydmService) {
		this.xydmService = xydmService;
	}

	
	public void setExportService(IExportService exportService) {
		this.exportService = exportService;
	}


	private static final String EXPORT_ID = "xydm";
	
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
            exportModel.setDataList(xydmService.getPagedList(model));
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


	public ExportModel getExportModel() {
		return exportModel;
	}


	public void setExportModel(ExportModel exportModel) {
		this.exportModel = exportModel;
	}
    
    
}
