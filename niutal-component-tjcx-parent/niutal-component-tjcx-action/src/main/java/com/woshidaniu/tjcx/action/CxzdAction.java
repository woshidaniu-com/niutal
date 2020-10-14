package com.woshidaniu.tjcx.action;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.tjcx.dao.entites.CxzdModel;
import com.woshidaniu.tjcx.dao.entites.YsfModel;
import com.woshidaniu.tjcx.service.svcinterface.ICxzdService;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 查询字段action
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
public class CxzdAction extends BaseAction implements ModelDriven<CxzdModel> {

	private static final long serialVersionUID = 1L;
	private ICxzdService service;
	private CxzdModel model = new CxzdModel();

	public String cxzd() {
		try {
			CxzdModel model2 = service.getModel(model);
		    getValueStack().set(DATA,model2);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	/**
	 * 
	 * @描述:取所有运算符
	 * @作者：ligl
	 * @日期：2013-8-20 下午04:27:40
	 * @修改记录: 
	 * @return
	 * String 返回类型 
	 * @throws
	 */
	public String getAllYsfList() {
		try {
			List<YsfModel> allYsfList = service.getAllYsfList();
		    getValueStack().set(DATA,allYsfList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 
	 * @描述:获取所有子类
	 * @作者：ligl
	 * @日期：2013-9-24 下午02:49:07
	 * @修改记录: 
	 * @return
	 * String 返回类型 
	 * @throws
	 */
	public String getChildrenList() {
		try {
			List<CxzdModel> list = service.getChildrenList(model);
		    getValueStack().set(DATA,list);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}


	public ICxzdService getService() {
		return service;
	}

	public void setService(ICxzdService service) {
		this.service = service;
	}

	public CxzdModel getModel() {
		return model;
	}

	public void setModel(CxzdModel model) {
		this.model = model;
	}

}
