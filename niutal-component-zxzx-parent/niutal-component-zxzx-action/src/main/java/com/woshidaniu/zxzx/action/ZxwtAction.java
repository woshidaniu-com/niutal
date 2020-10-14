/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IExportService;
import com.woshidaniu.search.dao.entities.SearchModel;
import com.woshidaniu.search.service.svcinterface.ISearchService;
import com.woshidaniu.zxzx.dao.entities.ZxhfModel;
import com.woshidaniu.zxzx.dao.entities.ZxwtModel;
import com.woshidaniu.zxzx.service.svcinterface.ICjwtService;
import com.woshidaniu.zxzx.service.svcinterface.IYhbkService;
import com.woshidaniu.zxzx.service.svcinterface.IZxwtService;

/**
 * @类名 ZxwtAction.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月24日 下午7:23:13
 * @功能描述 在线咨询-咨询问题
 * 
 */
@Controller("zxzxZxwtAction")
@Scope("prototype")
public class ZxwtAction extends BaseAction implements ModelDriven<ZxwtModel>{

	private static final long serialVersionUID = 8468011470448649189L;

	private ZxwtModel model = new ZxwtModel();
	
	@Autowired
	@Qualifier("exportExcelPOI")
	private IExportService  exportService;

	private ExportModel exportModel = new ExportModel();
	
	@Autowired
	@Qualifier("zxzxZxwtService")
	private IZxwtService service;
	
	@Autowired
	@Qualifier("zxzxCjwtService")
	private ICjwtService cjwtService;
	
	@Autowired
	@Qualifier("zxzxYhbkService")
	private IYhbkService yhbkService;
	
	@Autowired
	protected ISearchService searchService;
	
	public static final String EXPORT_ID = "zxzx_zxwt";
	
	/**
	 * 查询咨询问题
	 * @return
	 */
	public String cxZxwt(){
		User user = getUser();
		ValueStack valueStack = getValueStack();
		if("query".equals(model.getDoType())){
			try{
				//List<String> yhbkdmList = yhbkService.getYhbkdmList(user.getYhm());
				//model.setLoginUserBkdmList(yhbkdmList);
				model.setLoginUser(user.getYhm());
				SearchModel searchModel = searchService.getSearchModel(model);
				model.setSearchModel(searchModel);
				QueryModel queryModel = model.getQueryModel();
				List<ZxwtModel> pagedList = service.getPagedList(model);
				queryModel.setItems(pagedList);
				valueStack.set(DATA, queryModel);
			} catch(Exception ex){
				logException(ex);
			}
			return DATA;
		}
		return SUCCESS;
	}
	
	/**
	 * 回复咨询问题
	 * @return
	 */
	public String hfZxwt(){
		ZxwtModel queryModel = service.getModel(model.getZxid());
		if(queryModel != null){
			model.setZxsj(queryModel.getZxsj());
			model.setZxr(queryModel.getZxr());
			model.setkzdt(queryModel.getkzdt());
			model.setZxid(queryModel.getZxid());
			model.setZxnr(queryModel.getZxnr());
			model.setDjl(queryModel.getDjl());
			model.setHfzt(queryModel.getHfzt());
			model.setSfgk(queryModel.getSfgk());
			model.setkzdkModel(queryModel.getkzdkModel());
			model.setZxrModel(queryModel.getZxrModel());
			model.setZxhfModel(queryModel.getZxhfModel());
		}
		return SUCCESS;
	}
	
	/**
	 * 回复保存咨询问题
	 */
	public String hfBcZxwt(){
		String message = "";
		try{
			ZxwtModel updateModel = new ZxwtModel();
			ZxhfModel zxhfModel = model.getZxhfModel();
			zxhfModel.setHfid(UniqID.getInstance().getUniqIDHash());
			zxhfModel.setHfr(getUser().getYhm());
			zxhfModel.setHfsj(DateUtils.format(new Date()));
			zxhfModel.setZxid(model.getZxid());
			updateModel.setZxhfModel(zxhfModel);
			updateModel.setSfgk(model.getSfgk());
			updateModel.setZxid(model.getZxid());
			boolean submitResult = service.submitHfxx(updateModel);
			message = getText(submitResult ? "ZXZX_I001" : "ZXZX_I002");
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	/**
	 * 查看咨询问题
	 * @return
	 */
	public String ckZxwt(){
		ZxwtModel queryModel = service.getModel(model.getZxid());
		if(queryModel != null){
			model.setZxsj(queryModel.getZxsj());
			model.setZxr(queryModel.getZxr());
			model.setkzdt(queryModel.getkzdt());
			model.setZxid(queryModel.getZxid());
			model.setZxnr(queryModel.getZxnr());
			model.setDjl(queryModel.getDjl());
			model.setHfzt(queryModel.getHfzt());
			model.setSfgk(queryModel.getSfgk());
			model.setkzdkModel(queryModel.getkzdkModel());
			model.setZxrModel(queryModel.getZxrModel());
			model.setZxhfModel(queryModel.getZxhfModel());
		}
		return SUCCESS;
	}
	
	/**
	 * 设置保存为常见问题
	 * 修改当前cjwt状态为“1”
	 * 复制一条数据到cjwt表并带上zxid字段
	 * @return
	 */
	public String zsBcCjwt(){
		String message = "";
		ZxwtModel queryModel = service.getModel(model.getZxid());
		if(queryModel != null){
			boolean result = service.szCjwt(queryModel);
			message = getText(result ? "ZXZX_I004" : "ZXZX_I005");
		}else{
			message = getText("ZXZX_I003");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	/**
	 * 删除保存咨询问题
	 * @return
	 */
	public String scBcZxwt(){
		String message = "";
		try {
			if(model.getZxid() != null){
				String[] zxids = model.getZxid().split(",");
				String delCjwt = model.getDelCjwt();
				List<String> zxidList = new ArrayList<String>();
				for (String zxid : zxids) {
					zxidList.add(zxid);
				}
				boolean batchDelete = service.batchDelete(zxidList);
				
				//删除常见问题信息表中的数据
				if("1".equals(delCjwt)){
					batchDelete = cjwtService.batchDeleteByZxid(zxidList);
				}
				
				message = getText(batchDelete ? "I99005" : "I99006");
			}
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	/**
	 * 导出
	 * @return
	 */
	public String export(){
        try{
            SearchModel searchModel = searchService.getSearchModel(model);
			model.setSearchModel(searchModel);
			exportModel.setZgh(getUser().getYhm());
			exportModel.setDcclbh(EXPORT_ID);
			exportModel.setDataList(service.getExportData(model));
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
	
    
	@Override
	public ZxwtModel getModel() {
		return model;
	}

	public IExportService getExportService() {
		return exportService;
	}

	public void setExportService(IExportService exportService) {
		this.exportService = exportService;
	}

	public ExportModel getExportModel() {
		return exportModel;
	}

	public void setExportModel(ExportModel exportModel) {
		this.exportModel = exportModel;
	}

	public IZxwtService getService() {
		return service;
	}

	public void setService(IZxwtService service) {
		this.service = service;
	}

	public ICjwtService getCjwtService() {
		return cjwtService;
	}

	public void setCjwtService(ICjwtService cjwtService) {
		this.cjwtService = cjwtService;
	}

	public ISearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(ISearchService searchService) {
		this.searchService = searchService;
	}

	public void setModel(ZxwtModel model) {
		this.model = model;
	}

}
