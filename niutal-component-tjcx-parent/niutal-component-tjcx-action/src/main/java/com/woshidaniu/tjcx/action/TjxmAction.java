package com.woshidaniu.tjcx.action;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.tjcx.dao.entites.TjxmModel;
import com.woshidaniu.tjcx.service.svcinterface.ITjxmService;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计项目action
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
public class TjxmAction extends BaseAction implements ModelDriven<TjxmModel> {

	private static final long serialVersionUID = 1L;
	private ITjxmService service;
	private TjxmModel model = new TjxmModel();
	private static String TJCX_GNMK_BS	= "TJCX_GNMK";

	/**
	 * 统计项目
	 * 
	 * @return
	 */
	public String cxxx() {
		try {
			String gnmk = getRequest().getParameter("gnmk");
			if(gnmk == null){
				gnmk = (String)getSession().getAttribute(TJCX_GNMK_BS);
			}
			model.setGnmk(gnmk);
			List<TjxmModel> list = service.getModelList(model);
			getValueStack().set(DATA, list);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	@Override
	public TjxmModel getModel() {
		return model;
	}

	public ITjxmService getService() {
		return service;
	}

	public void setService(ITjxmService service) {
		this.service = service;
	}

	public void setModel(TjxmModel model) {
		this.model = model;
	}

}
