package com.woshidaniu.zdybd.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.zdybd.dao.entities.FlszModel;
import com.woshidaniu.zdybd.dao.entities.GnszModel;
import com.woshidaniu.zdybd.service.svcinterface.IFlszService;

/**
 * 
 * @系统名称: 新框架
 * @模块名称: 自定义表单
 * @类功能描述: 分类设置
 * @作者： ligl
 * @时间： 2014-2-18 上午09:11:28
 * @版本： V1.0
 * @修改记录:
 */
public class FlszAction extends BaseAction implements ModelDriven<FlszModel> {

	private static final long serialVersionUID = -1469450923982118696L;
	private IFlszService service;
	private FlszModel model = new FlszModel();

	/**
	 * 
	 * @描述:根据功能代码获取所有分类下的字段定义列表
	 * @作者：ligl
	 * @日期：2014-2-18 上午09:23:10
	 * @修改记录:
	 * @return String 返回类型
	 * @throws
	 */
	public String getFlszList() {
		try {
			HttpServletRequest request = getRequest();
			String gndm = request.getParameter("gndm");
			String flszid = request.getParameter("flszid");
			String pzlx = request.getParameter("pzlx");
			GnszModel gnszModel = new GnszModel();
			gnszModel.setGndm(gndm);
			gnszModel.setFlszid(flszid);
			gnszModel.setPzlx(pzlx);
			List<FlszModel> list = service.getListByGndm(gnszModel);
			String bdms = null;
			for (FlszModel flszModel : list) {
				if (pzlx == null) {
					continue;
				}
				bdms = flszModel.getBdms();
				flszModel.setGnlx("2");
				if (pzlx.contains("shpz")) {
					flszModel.setBdms("5");
				} else if (pzlx.contains("ckpz")) {
					if(bdms != null && bdms.equals("4")){
						flszModel.setBdms("2");
					}
				}
			}
			getValueStack().set(DATA, list);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	public IFlszService getService() {
		return service;
	}

	public void setService(IFlszService service) {
		this.service = service;
	}

	public FlszModel getModel() {
		return model;
	}

	public void setModel(FlszModel model) {
		this.model = model;
	}

}
