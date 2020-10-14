package com.woshidaniu.globalweb.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.JcsjModel;
import com.woshidaniu.service.svcinterface.IJcsjService;

/**
 * 
 *@类名称:JcsjAction.java
 *@类描述： 基础数据控制 
 *@创建人：kangzhidong
 *@创建时间：2015-1-20 下午03:18:10
 *@版本号:v1.0
 */
public class JcsjAction extends CommonBaseAction implements ModelDriven<JcsjModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JcsjModel model = new JcsjModel();

	@Resource(name="jcsjService")
	private IJcsjService jcsjService;

	/**
	 *查询类型表
	 * 
	 * @throws Exception
	 */
	public void setValueStack() throws Exception {
		ValueStack vs = getValueStack();
		model.setXtjb(null);
		List<JcsjModel> lxdmList = getJcsjService().getModelList(model);
		vs.set("lxdmList", lxdmList);
	}

	/**
	 * 
	 *@描述：基础数据列表 
	 *@创建人:
	 *@创建时间:2015-1-20下午03:17:54
	 *@修改人:kangzhidong
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxJcsj() {
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getJcsjService().getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			setValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 
	 *@描述：增加基础数据
	 *@创建人:
	 *@创建时间:2015-1-20下午03:17:40
	 *@修改人:kangzhidong
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String zjJcsj() {
		try {
		    ValueStack vs = getValueStack();
	        model.setXtjb("yw");
	        List<JcsjModel> lxdmList = getJcsjService().getModelList(model);
	        vs.set("lxdmList", lxdmList);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：保存增加基础数据 
	 *@创建人:
	 *@创建时间:2015-1-20下午03:17:30
	 *@修改人:kangzhidong
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String zjBcJcsj() {
		try {
			getJcsjService().insert(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：修改基础数据
	 *@创建人:
	 *@创建时间:2015-1-20下午03:17:20
	 *@修改人:kangzhidong
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgJcsj() {
		try {
			JcsjModel jcsjModel = new JcsjModel();
			jcsjModel.setPkValue((getRequest().getParameter("pkValue")));
			// 查询单个信息
			jcsjModel = getJcsjService().getModel(jcsjModel);
			BeanUtils.copyProperties(model, jcsjModel);
			setValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：保存修改基础数据
	 *@创建人:
	 *@创建时间:2015-1-20下午03:17:09
	 *@修改人:kangzhidong
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgBcJcsj() {
		try {
			JcsjModel jcsjModel = new JcsjModel();
			jcsjModel.setPkValue((getRequest().getParameter("pkValue")));
			// 查询单个信息
			jcsjModel = getJcsjService().getModel(jcsjModel);
			// 修改基础数据
			getJcsjService().update(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：删除基础数据
	 *@创建人:
	 *@创建时间:2015-1-20下午03:16:55
	 *@修改人:kangzhidong
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String scJcsj() throws Exception {
		try{
			model.setPkValue(getRequest().getParameter("ids"));
			getJcsjService().scJcsj(model);
			getValueStack().set(DATA, getText("I99005"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99006"));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：验证代码是否已经存在
	 *@创建人:
	 *@创建时间:2015-1-20下午03:25:26
	 *@修改人:kangzhidong
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String cxValideDm() throws Exception {
		// 查询单条数据
		JcsjModel jcsjModel = getJcsjService().getModel(model);
		if (null != jcsjModel) {
			getValueStack().set(DATA, "该代码已经存在!");
		}
		return DATA;
	}


	public JcsjModel getModel() {
		model.setUser(getUser());
		return model;
	}

	public void setModel(JcsjModel model) {
		this.model = model;
	}
  
	public IJcsjService getJcsjService() {
		return jcsjService;
	}

	public void setJcsjService(IJcsjService jcsjService) {
		this.jcsjService = jcsjService;
	}

}
