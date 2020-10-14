package com.woshidaniu.plugins.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.progress.BarSource;
import com.woshidaniu.common.progress.ProgressBar;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.dao.entities.ImportModel;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.IImportService;

/**
 * 导入数据
 * @author Jiangdong.Yi
 *
 */
public class ImportAction extends BaseAction implements ModelDriven<ImportModel>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ImportModel model = new ImportModel();
	private IImportService importService;
	private String barkey;
	private BaseLog baseLog = LogEngineImpl.getInstance();
	public void setImportService(IImportService importService) {
		this.importService = importService;
	}
	public ImportModel getModel() {
		return model;
	}
	/**
	 * 进入导入数据页面
	 * @return
	 */
	public String toImportData(){
		try {
			ValueStack vs=getValueStack();
			//设置导入表列表
			List<ImportModel> impTableList=importService.getImportTableListByDrmkdm(model.getDrmkdm());
			vs.set("impTableList", impTableList);
		} catch (Exception e) {
			logException(e);
		}
		return "toImportData";
	}
	
	/**
	 * 导入数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importData(){
		ValueStack vs=getValueStack();
		User user=getUser();
		ProgressBar pb = BarSource.initAutoProgressBar(barkey);
		try {
			if(model.getDrms() != null && model.getDrms().equals("1")){
				model.setDrwjgs("xls");
				// 获取错误临时目录
				String path = ServletActionContext.getServletContext().getRealPath(
						IImportService.IMPORT_TEMP_DIRPATH);

				StringBuffer filePath = new StringBuffer(path);
				filePath.append("\\");
				filePath.append(IImportService.IMPORT_ERROR);
				filePath.append("_");
				filePath.append(model.getDrmkdm());
				// 导入错误数据文件名称加入用户
				if (user.getYhm() != null) {
					filePath.append("_");
					filePath.append(user.getYhm());
				}
				// 暂时值支持xls文件导入
				filePath.append(".");
				filePath.append(IImportService.IMPORT_SUFFLX);
				// 导出文件存放 的临时目
				file = new File(filePath.toString());
				model.setUpdateFile(file);
			}

			Map<String, Object> importReslult=importService.importData(model,pb);
			
			//导入成功数据
			if(importReslult != null && importReslult.get("succeedList") != null){
				baseLog.update(user, getText("log.message.ywmc", new String[] {
						"通用导入", model.getDrbm().toUpperCase() }), "通用导入",
						getText("log.message.czms", new String[] { "通用导入",
								"导入模块代码", model.getDrmkdm() }));
			}
			//生成错误文件
			if(importReslult != null && importReslult.get("errorList") != null){
				importService.getImportErrortData(model.getDrmkdm(), model.getDrbm(), 
						(List<String[]>)importReslult.get("errorList"),user);
			}
			pb.autoFinish();
			pb.returnData(barkey, importReslult.get("importModel"));
			//vs.set(DATA, importReslult.get("importModel"));
		} catch (Exception e) {
			pb.autoFinish();
			pb.setMessage("导入失败！");
			logException(e);
		}
		return "downloadErrorData";
	}
	
	/**
	 * 下载模板
	 * @return
	 */
	public String downloadTemplate(){
		try {
			file = importService.getImportTemplate(model);
		} catch (Exception e) {
			logException(e);
		}
		return "exportExcel";
	}
	
	/**
	 * 下载错误数据
	 * @return
	 */
	public String downloadErrorData(){
		try {
			//获取错误临时目录
			String path=ServletActionContext.getServletContext().getRealPath(IImportService.IMPORT_TEMP_DIRPATH);
			//获取当前操作用户
			User user=getUser();
			
			StringBuffer filePath=new StringBuffer(path);
			filePath.append("\\");
			filePath.append(IImportService.IMPORT_ERROR);
			filePath.append("_");
			filePath.append(model.getDrmkdm());
			//导入错误数据文件名称加入用户名
			if(!StringUtils.isEmpty(user.getYhm())){
				filePath.append("_");
				filePath.append(user.getYhm());
			}
			//暂时值支持xls文件导入
			filePath.append(".");
			filePath.append(IImportService.IMPORT_SUFFLX);
			//导出文件存放 的临时目录
			file = new File(filePath.toString());
		} catch (Exception e) {
			logException(e);
		}
		return "exportExcel";
	}
	public void setModel(ImportModel model) {
		this.model = model;
	}
	public String getBarkey() {
		return barkey;
	}
	public void setBarkey(String barkey) {
		this.barkey = barkey;
	}

}
