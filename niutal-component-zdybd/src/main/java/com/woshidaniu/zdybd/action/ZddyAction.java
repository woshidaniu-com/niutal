package com.woshidaniu.zdybd.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.zdybd.dao.entities.GnszModel;
import com.woshidaniu.zdybd.dao.entities.ZddyModel;
import com.woshidaniu.zdybd.service.svcinterface.IZddyService;

/**
 * 
 * @系统名称: 新框架
 * @模块名称: 自定义表单
 * @类功能描述: 字段定义
 * @作者： ligl
 * @时间： 2014-2-18 上午09:11:28
 * @版本： V1.0
 * @修改记录:
 */
public class ZddyAction extends BaseAction implements ModelDriven<ZddyModel> {

	private static final long serialVersionUID = -1469450989982118696L;
	private IZddyService service;
	private ZddyModel model = new ZddyModel();

	/**
	 * 
	 * @描述:根据功能代码获取所有分类下的字段定义列表
	 * @作者：ligl
	 * @日期：2014-2-18 上午09:23:10
	 * @修改记录:
	 * @return String 返回类型
	 * @throws
	 */
	public String getZddyList() {
		try {
			HttpServletRequest request = getRequest();
			String gndm = request.getParameter("gndm");
			String flszid = request.getParameter("flszid");
			String pzlx = request.getParameter("pzlx");
			GnszModel gnszModel = new GnszModel();
			gnszModel.setGndm(gndm);
			gnszModel.setFlszid(flszid);
			gnszModel.setPzlx(pzlx);
			List<ZddyModel> cacheList = service.getListByGndmCache(gnszModel);
			List<ZddyModel> list = deepCopy(cacheList);
			service.getListByGndm(list);
			
			String bdms = null;
			for (ZddyModel zddyModel : list) {
				if (pzlx == null) {
					continue;
				}
				bdms = zddyModel.getBdms();
				if (pzlx.contains("shpz")) {
					zddyModel.setBdms("5");
				} else if (pzlx.contains("ckpz")) {
					if(bdms != null && bdms.equals("4")){
						zddyModel.setBdms("2");
					}
				}
			}

			getValueStack().set(DATA, list);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	public static <T> List<T> deepCopy(List<T> src) throws IOException,
			ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut
				.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		@SuppressWarnings("unchecked")
		List<T> dest = (List<T>) in.readObject();
		return dest;
	}

	@Override
	public ZddyModel getModel() {
		return model;
	}

	public IZddyService getService() {
		return service;
	}

	public void setService(IZddyService service) {
		this.service = service;
	}

	public void setModel(ZddyModel model) {
		this.model = model;
	}

}
