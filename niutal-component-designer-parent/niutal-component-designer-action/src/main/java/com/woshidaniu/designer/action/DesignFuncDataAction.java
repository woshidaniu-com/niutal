package com.woshidaniu.designer.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.constant.BaseConstant;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.designer.dao.entities.DesignFuncDataModel;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncDataService;
import com.woshidaniu.designer.utils.FuncDataUtils;
import com.woshidaniu.export.service.svcinterface.IDocExportService;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.io.utils.IOUtils;
import com.woshidaniu.util.file.ZipHelper;
import com.woshidaniu.util.request.WebRequestUtils;

/**
 * 
 *@类名称: DesignFuncDataAction.java
 *@类描述：功能数据查询action
 *@创建人：kangzhidong
 *@创建时间：2015-5-4 下午03:44:47
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncDataAction extends BaseAction implements ModelDriven<DesignFuncDataModel>  { 

	protected DesignFuncDataModel model = new DesignFuncDataModel();
	@Resource(name="designFuncDataService")
	protected IDesignFuncDataService service;
	/**导出model*/
	protected ExportModel exportModel = new ExportModel();
	/**导出service*/
	@Resource
	protected IDocExportService exportService;
	
	protected void initRequestMap() {
		Map<String, Object> requestMap = WebRequestUtils.getObjectParameters(getRequest());
		Map<String, Object> newRequestMap = new HashMap<String, Object>();
		Object value = null;
		for (String key : requestMap.keySet()) {
			value = requestMap.get(key);
			if ((value instanceof String) && !BlankUtils.isBlank(value.toString())) {
				newRequestMap.put(key, value);
			}else if(value.getClass().isArray() && !BlankUtils.isBlank((String[])value) ){
				newRequestMap.put(key, value);
			}
		}
		//收集参数
		if(BlankUtils.isBlank(model.getParamMap())){
			model.setParamMap(newRequestMap);
		}else{
			model.getParamMap().putAll(newRequestMap);
		}
		model.getParamMap().put("user", getUser());
	}
	
	/**
	 * 
	 *@描述：查询功能数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-4下午04:10:21
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxFuncDataList(){
		QueryModel queryModel = model.getQueryModel();
		try {
			//处理参数
			initRequestMap();
			queryModel.setItems(getService().getPagedFuncDataList(model));
			getValueStack().set(DATA, queryModel);
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, queryModel);
		}
		return DATA;
	}

	
	/**
	 * 
	 *@描述：导出数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-25上午11:11:58
	 *@return
	 *@throws Exception
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String dcFuncDataList() throws Exception{
		//处理参数
		initRequestMap();
		//查询数据
		List<BaseMap> data =  getService().getFuncDataList(model);
		exportModel.setZgh(getUser().getYhlybid());
		exportModel.setDcclbh(getRequest().getParameter("dcclbh"));
		if(data == null ){
			data = new ArrayList<BaseMap>();
		}
		// //////////////插入dbf处理////////////////////////////////////
		String wjgs = exportModel.getExportWjgs();
		if (wjgs != null && wjgs.equals("dbf")) {
			exportModel.setDataList(data);
			this.file = exportService.getExportFile(exportModel);
			this.fileName = new String(this.file.getName().getBytes("utf-8"), "ISO8859-1");
		}else{
			if(data.size() <= 10000){
				exportModel.setDataList(data);
				this.file = exportService.getExportFile(exportModel);
				this.fileName = new String(this.file.getName().getBytes("utf-8"), "ISO8859-1");
			}else{
				int fileCount = FuncDataUtils.getFileCount(data.size(), 10000);
				List<File> fileList = new ArrayList<File>();
				for (int i = 1; i <= fileCount; i++) {
					exportModel.setDataList(data.subList((i-1) * 10000, Math.min(i * 10000 - 1, data.size())));
					fileList.add(exportService.getExportFile(exportModel));
				}
				ByteArrayOutputStream outzip = null;
				ArchiveOutputStream archOuts = null;
				try {
					outzip = new ByteArrayOutputStream();
					archOuts = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, outzip);
					
					File tempDir = new File(ServletActionContext.getServletContext().getRealPath(BaseConstant.TEMP_PATH));
					ZipHelper.zipFiles(fileList, archOuts,tempDir+File.separator);
					this.fileName = new String((StringUtils.getSafeStr(getFileName(), "压缩包") + ".zip").getBytes("utf-8"), "ISO8859-1");
					this.inputStream = new ByteArrayInputStream(outzip.toByteArray());
					
					for(int i=0;i<fileList.size();i++){
						if(fileList.get(i).exists()){
							fileList.get(i).delete();
						}
					}
				} catch (Exception e) {
					logException(e);
					return ERROR;
				} finally {
					IOUtils.closeQuietly(archOuts);
					IOUtils.closeQuietly(outzip);
				}
				return Result.ZIP;
			}
		}
		return "exportExcel";
	}
	 
	@Override
	public DesignFuncDataModel getModel() {
		model.setUser(getUser());
		return model;
	}

	public IDesignFuncDataService getService() {
		return service;
	}

	public void setService(IDesignFuncDataService service) {
		this.service = service;
	}

	public ExportModel getExportModel() {
		return exportModel;
	}

	public void setExportModel(ExportModel exportModel) {
		this.exportModel = exportModel;
	}

	public IDocExportService getExportService() {
		return exportService;
	}

	public void setExportService(IDocExportService exportService) {
		this.exportService = exportService;
	}

	public void setModel(DesignFuncDataModel model) {
		this.model = model;
	}
	
	
}

