/**
 * 
 */
package com.woshidaniu.licenseManagement.action;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.license.admin.LicenseAdminOps;
import com.woshidaniu.licenseManagement.dao.entities.LicenseModel;
import com.woshidaniu.licenseManagement.service.svcinterface.ILicenseService;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：license 管理action
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年6月17日上午9:08:42
 */
@Controller("licenseManagementAction")
@Scope("prototype")
public class LicenseManagementAction extends BaseAction implements ModelDriven<LicenseModel>{
	
	private static final long serialVersionUID = -7464680470744170160L;
	
	private LicenseModel model = new LicenseModel();
	
	private String testId;
	
	@Autowired
	@Qualifier("licenseManagementService")
	private ILicenseService licenseManagementService;
	
	private LicenseAdminOps licenseAdminOps = new LicenseAdminOps();
	
	/**
	 * 
	 * <p>方法说明：查询license<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月17日上午9:11:54<p>
	 */
	public String cxLicense(){
		ValueStack valueStack = getValueStack();
		if("query".equals(model.getDoType())){
			try{
				QueryModel queryModel = model.getQueryModel();
				List<LicenseModel> pagedList = licenseManagementService.getPagedList(model);
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
	 * 
	 * <p>方法说明：查询license日志<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月23日下午4:58:46<p>
	 */
	public String cxLogLicense(){
		return SUCCESS;
	}

	/**
	 * 
	 * <p>方法说明：增加license<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月17日上午9:12:15<p>
	 */
	public String zjLicense(){
		String authId = UniqID.getInstance().getUniqIDHash();
		String authDate = DateUtils.format(new Date());
		model.setAuthId(authId);
		model.setAuthDate(authDate);
		model.setAuthUser("我是大牛大学-试用版本");
		model.setAuthUserCode("******");
		model.setStartDate(authDate);
		model.setExpireDate(authDate);
		model.setUsage("100");
		model.setUsageCount("0");
		model.setAlert("7");
		model.setDevMode("0");
		return SUCCESS;
	}

	
	/**
	 * 
	 * <p>方法说明：增加license<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月24日上午9:14:08<p>
	 */
	public String zjBcLicense(){
		String message = "";
		try{
			boolean updateResult = Boolean.FALSE;
			updateResult = licenseManagementService.insert(model);
			message = getText(updateResult ? "I99001" : "I99002");
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	/**
	 * 
	 * <p>方法说明：修改license<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月24日上午9:27:51<p>
	 */
	public String xgLicense(){
		LicenseModel queryModel = licenseManagementService.getModel(model.getAuthId());
		if(queryModel != null){
			model.setAuthId(queryModel.getAuthId());
			model.setAuthUserCode(queryModel.getAuthUserCode());
			model.setAuthUser(queryModel.getAuthUser());
			model.setAuthDate(queryModel.getAuthDate());
			model.setStartDate(queryModel.getStartDate());
			model.setExpireDate(queryModel.getExpireDate());
			model.setUsage(queryModel.getUsage());
			model.setUsageCount(queryModel.getUsageCount());
			model.setAlert(queryModel.getAlert());
			model.setBzxx(queryModel.getBzxx());
			model.setDevMode(queryModel.getDevMode());
			model.setProductName(queryModel.getProductName());
		}
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * <p>方法说明：TODO<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月24日上午9:28:34<p>
	 */
	public String xgBcLicense(){
		String message = "";
		try{
			boolean updateResult = Boolean.FALSE;
			updateResult = licenseManagementService.update(model);
			message = getText(updateResult ? "I99003" : "I99004");
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	/**
	 * 
	 * <p>方法说明：delete<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月24日上午9:55:34<p>
	 */
	public String scBcLicense(){
		String message = "";
		try {
			if(model.getAuthId() != null){
				licenseManagementService.delete(model.getAuthId());
				message = getText("I99005");
			}
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	/**
	 * 
	 * <p>方法说明：查看license<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月24日上午9:29:55<p>
	 */
	public String ckLicense(){
		LicenseModel queryModel = licenseManagementService.getModel(model.getAuthId());
		if(queryModel != null){
			model.setAuthId(queryModel.getAuthId());
			model.setAuthUserCode(queryModel.getAuthUserCode());
			model.setAuthUser(queryModel.getAuthUser());
			model.setAuthDate(queryModel.getAuthDate());
			model.setStartDate(queryModel.getStartDate());
			model.setExpireDate(queryModel.getExpireDate());
			model.setUsage(queryModel.getUsage());
			model.setUsageCount(queryModel.getUsageCount());
			model.setAlert(queryModel.getAlert());
			model.setBzxx(queryModel.getBzxx());
			model.setDevMode(queryModel.getDevMode());
			model.setProductName(queryModel.getProductName());
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * <p>方法说明：下载license文件<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月17日上午9:13:27<p>
	 */
	public String dlLicense(){
		LicenseModel queryModel = licenseManagementService.getModel(model.getAuthId());
		if(queryModel != null){
			String generateLicense;
			OutputStream output = null;
			try {
				generateLicense = licenseAdminOps.genFile(queryModel.getAuthId(), 
						queryModel.getAuthUser(), 
						queryModel.getAuthUserCode(), 
						queryModel.getAuthDate(), 
						queryModel.getStartDate(), 
						queryModel.getExpireDate(), 
						Integer.parseInt(queryModel.getUsage()), 
						Integer.parseInt(queryModel.getUsageCount()), 
						Integer.parseInt(queryModel.getAlert()),
						queryModel.getDevMode(),
						queryModel.getProductName()
						);
				
				file = FileUtils.getFile(FileUtils.getTempDirectory(), System.currentTimeMillis()+".auth");
				log.debug("生成授权文件路径：{}", file.getAbsolutePath());
				if(!file.exists()){
					file.createNewFile();
				}
				output = new FileOutputStream(file);
				IOUtils.write(generateLicense, output, "utf-8");
			} catch (Exception e) {
				logException(e);
				e.printStackTrace();
			}finally{
				try {
					if(output != null){
						output.close();
					}
				} catch (IOException e) {
					output = null;
					e.printStackTrace();
				}
			}
		}
		return "dlLicense";
	}
	
	
	@Override
	public LicenseModel getModel() {
		return model;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

}
