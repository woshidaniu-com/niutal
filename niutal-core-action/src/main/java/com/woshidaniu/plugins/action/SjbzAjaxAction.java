package com.woshidaniu.plugins.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.service.svcinterface.ISjbzService;
import com.woshidaniu.util.base.MessageUtil;

/**
 * 标准数据加载
 * @author Penghui.Qu
 */
@Controller
@Scope("prototype")
public class SjbzAjaxAction extends BaseAction implements ModelDriven<SjbzModel>{
	private static final long serialVersionUID = 1L;
	
	private SjbzModel model = new SjbzModel();
	
	@Autowired
	private ISjbzService service;
	
	public SjbzModel getModel() {
		return model;
	}

	public void setService(ISjbzService service) {
		this.service = service;
	}

	
	/**
	 * 从基础数据维护中加载数据
	 * @return
	 */
	public String cxYwsj(){
		
		try {
			List<SjbzModel> dataList = service.getYwsjList(MessageUtil.getText(model.getLx()));
			getValueStack().set(DATA, dataList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
}
