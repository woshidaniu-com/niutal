package com.woshidaniu.workflow.action;

import java.util.List;

import com.woshidaniu.workflow.model.SpBusiness;
import com.woshidaniu.workflow.service.ISpBusinessService;

/**
 * 
 * 类描述：流程业务维护
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:33:23
 */
public class SpBusinessAction extends WorkFlowBaseAction {
	private static final long serialVersionUID = 6535431275595307300L;

	private ISpBusinessService spBusinessService;
	private SpBusiness spBusiness;
	private String[] classIds;
	private String[] classPrivilege;
	private String op = "add";

	/**
	 * 获取流程业务列表
	 * 
	 * @return
	 */
	public String list() {
		List<SpBusiness> spBusinesss = spBusinessService
				.findSpBusiness(new SpBusiness());
		this.getValueStack().set("businessList", spBusinesss);
		return LIST_PAGE;
	}

	/**
	 * 请求修改业务
	 * 
	 * @return
	 */
	public String modifyBusiness() {
		op = "modify";
		spBusiness = spBusinessService.findSpBusinessByIdAndBType(
				spBusiness.getBid(), null);
		return EDIT_PAGE;
	}

	/**
	 * 保存修改业务
	 * 
	 * @return
	 */
	public String saveBusiness() {
		String pid = spBusiness.getPid();
		String billId = spBusiness.getBillId();
		spBusiness = spBusinessService.findSpBusinessByIdAndBType(
				spBusiness.getBid(), null);
		spBusiness.setPid(pid);
		spBusiness.setBillId(billId);
		spBusinessService.update(spBusiness, classIds, classPrivilege);
		this.setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public SpBusiness getSpBusiness() {
		return spBusiness;
	}

	public void setSpBusiness(SpBusiness spBusiness) {
		this.spBusiness = spBusiness;
	}

	public String getOp() {
		return op;
	}

	/**
	 * @return classIds : return the property classIds.
	 */

	public String[] getClassIds() {
		return classIds;
	}

	/**
	 * @param classIds
	 *            : set the property classIds.
	 */

	public void setClassIds(String[] classIds) {
		this.classIds = classIds;
	}

	/**
	 * @return classPrivilege : return the property classPrivilege.
	 */

	public String[] getClassPrivilege() {
		return classPrivilege;
	}

	/**
	 * @param classPrivilege
	 *            : set the property classPrivilege.
	 */

	public void setClassPrivilege(String[] classPrivilege) {
		this.classPrivilege = classPrivilege;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public void setSpBusinessService(ISpBusinessService spBusinessService) {
		this.spBusinessService = spBusinessService;
	}
}
