package com.woshidaniu.globalweb.action;


import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.RzglModel;
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
public class RzglAction extends CommonBaseAction implements ModelDriven<RzglModel> {

	private static final long serialVersionUID = 1L;

	@Resource
	private IRzglService service;
	
	protected RzglModel model = new RzglModel();
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
				queryModel.setItems(getService().getPagedList(model));
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
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:Oct 19, 201510:16:14 AM
	 *@return
	 *@throws Exception
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxRzList() throws Exception {
		model.setPageable(false);//设置成不分页
		return DATA;
	}
	
	/**
	 * 查看日志详细信息
	 * @return
	 */
	public String ckRzxx(){
		try {
			RzglModel model = getService().getModel(this.model.getCzbh());
			BeanUtils.copyProperties(this.model, model);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		} 
		return "ckRzxx";
	}

	public IRzglService getService() {
		return service;
	}

	public void setService(IRzglService service) {
		this.service = service;
	}
	
}
