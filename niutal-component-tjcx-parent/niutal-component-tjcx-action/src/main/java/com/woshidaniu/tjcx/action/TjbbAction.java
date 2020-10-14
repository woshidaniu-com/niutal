package com.woshidaniu.tjcx.action;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.TjbbModel;
import com.woshidaniu.tjcx.dao.entites.TjxmModel;
import com.woshidaniu.tjcx.service.svcinterface.IKzszService;
import com.woshidaniu.tjcx.service.svcinterface.ITjbbService;
import com.woshidaniu.tjcx.service.svcinterface.ITjxmService;
import com.woshidaniu.util.base.MessageUtil;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计报表action
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
public class TjbbAction extends BaseAction implements ModelDriven<KzszModel> {

	private static final long serialVersionUID = 1L;
	private ITjbbService service;
	private ITjxmService tjxmService;
	private IKzszService kzszService;
	private KzszModel model = new KzszModel();
	private String tableHtml = null;
	private String xmdm = null;
	private String gltj = null;
	private String bbhxl = null;
	private String bbzxl = null;
	private String tsx = null;
	private static String TJCX_GNMK_BS	= "TJCX_GNMK";

	public String getSjByXmdm() {
		try {
			//User user = SessionFactory.getUser();
			User user = getUser();
			model.setCzy(user.getYhm());
			TjbbModel tjbbModel = service.getSjByXmdm(model);
		    getValueStack().set(DATA,tjbbModel);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	public String tjbb() {
		String gnmk = getRequest().getParameter("gnmk");
		if(gnmk == null){
			gnmk = (String)getSession().getAttribute(TJCX_GNMK_BS);
		}
		model.setGnmk(gnmk);
		//User user = SessionFactory.getUser();
		User user = getUser();
		model.setCzy(user.getYhm());
		return SUCCESS;
	}
	
	public String tjlbxq() {
		return "tjlbxq";
	}

	/**
	 * 
	 * @描述:统计列表
	 * @作者：ligl
	 * @日期：2013-9-3 上午10:49:06
	 * @修改记录: 
	 * @return
	 * String 返回类型 
	 * @throws
	 */
	public String tjlb() {
		HttpServletRequest request = getRequest();
		if (model.getKzszid() != null) {
			model = kzszService.getModel(model);
			String xmdm = model.getXmdm();
			TjxmModel tjxmModel = new TjxmModel();
			tjxmModel.setXmdm(xmdm);
			TjxmModel tjxmModel2 = tjxmService.getModel(tjxmModel);
			request.setAttribute("xmdm", xmdm);
			request.setAttribute("gnmk", tjxmModel2.getGnmk());
			request.setAttribute("gltj", model.getGltj());
			request.setAttribute("bbhxl", model.getBbhxl());
			request.setAttribute("bbzxl", model.getBbzxl());
			request.setAttribute("tsx", model.getTsx());
			request.setAttribute("kzms", model.getKzms());
		}

		return SUCCESS;
	}
	
	/**
	 * 
	 * @描述:统计图表
	 * @作者：ligl
	 * @日期：2013-9-3 上午10:49:06
	 * @修改记录: 
	 * @return
	 * String 返回类型 
	 * @throws
	 */
	public String tjtb() {
		return SUCCESS;
	}


	public String export() {
		return SUCCESS;
	}

	public String tjlbBzsm(){
		return SUCCESS;
	}
	
	public String tjsj() {
		try {
//			kzszModel.setXmdm("xsxx");
//			kzszModel.setGltj(" (nj='2009' or nj='2010' or nj='2011' )  ");//    and (xydm='31110000' or xydm='501000' or xydm='503000'  or xydm='511113'  or xydm='514000' ) 
//			kzszModel.setBbhxl("xydm");
//			kzszModel.setBbzxl("");
//			kzszModel.setTsx("zs,xlhj,zxlhj");//zs,xh,bjdm,avg,max,hxlhj,zxlhj
//			kzszModel.setCzy("admin");
			
			if(gltj != null && !gltj.trim().equals("")){
//				String ss = gltj;
//				System.out.println("----------"+ss);
//				if(StringUtils.contains(gltj, "?")){
//					byte[] b = gltj.getBytes("ISO8859_1");
//					gltj = new String(b, "utf8");				
//				}

//				
//				try {
//					gltj = URLEncoder.encode(gltj,"UTF-8");//转码
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
			//	gltj = StringUtils.replace(gltj, DYH, "'");
			}
			//    and (xydm='31110000' or xydm='501000' or xydm='503000'  or xydm='511113'  or xydm='514000' ) 
			if(bbhxl != null && !bbhxl.trim().equals("")){
				model.setBbhxl(bbhxl);
			}
			if(bbzxl != null && !bbzxl.trim().equals("")){
				model.setBbzxl(bbzxl);
			}
			//zs,xh,bjdm,avg,max,hxlhj,zxlhj
			//User user = SessionFactory.getUser();
			User user = getUser();
			model.setCzy(user.getYhm());
			
			Integer limit = MessageUtil.getInt("tjcx_data_limit");
			model.setLimit(limit == null?-1:limit);
			TjbbModel tjbbModel = service.getTjbb(model);
		    getValueStack().set(DATA,tjbbModel);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	public ITjbbService getService() {
		return service;
	}

	public void setService(ITjbbService service) {
		this.service = service;
	}

	public ITjxmService getTjxmService() {
		return tjxmService;
	}

	public void setTjxmService(ITjxmService tjxmService) {
		this.tjxmService = tjxmService;
	}


	public KzszModel getModel() {
		return model;
	}

	public void setModel(KzszModel model) {
		this.model = model;
	}

	public String getTableHtml() {
		return tableHtml;
	}

	public void setTableHtml(String tableHtml) {
		this.tableHtml = tableHtml;
	}

	public String getXmdm() {
		return xmdm;
	}

	public void setXmdm(String xmdm) {
		this.xmdm = xmdm;
	}

	public String getGltj() {
		return gltj;
	}

	public void setGltj(String gltj) {
		this.gltj = gltj;
	}

	public String getBbhxl() {
		return bbhxl;
	}

	public void setBbhxl(String bbhxl) {
		this.bbhxl = bbhxl;
	}

	public String getBbzxl() {
		return bbzxl;
	}

	public void setBbzxl(String bbzxl) {
		this.bbzxl = bbzxl;
	}

	public String getTsx() {
		return tsx;
	}

	public void setTsx(String tsx) {
		this.tsx = tsx;
	}

	public IKzszService getKzszService() {
		return kzszService;
	}

	public void setKzszService(IKzszService kzszService) {
		this.kzszService = kzszService;
	}

}
