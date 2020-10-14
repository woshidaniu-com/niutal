package com.woshidaniu.tjcx.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IExportService;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.TjxmModel;
import com.woshidaniu.tjcx.service.svcinterface.IKzszService;
import com.woshidaniu.tjcx.service.svcinterface.ITjcxCommon;
import com.woshidaniu.tjcx.service.svcinterface.ITjcxService;
import com.woshidaniu.tjcx.service.svcinterface.ITjxmService;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计查询action
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
public class TjcxAction extends BaseAction implements ModelDriven<KzszModel> {

	private static final long serialVersionUID = 1L;
	private ITjcxService service;
	private ITjcxCommon tjcxCommon;
	private ITjxmService tjxmService;
	private IKzszService kzszService;
	private KzszModel model = new KzszModel();
	private ExportModel exportModel = new ExportModel();
	private IExportService exportService;
	private IExportService exportServicePOI;
	private static String TJCX_GNMK_BS	= "TJCX_GNMK";
	private static String DCQZ	= "tjcx_";//导出前缀
	private static int DEFAULT_EXPORT_MAX = 5000;//单个导出文件数据最大条数
	public String tjcx() {
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

	/**
	 * 
	 * @描述:统计查询列表
	 * @作者：ligl
	 * @日期：2013-8-26 下午01:59:23
	 * @修改记录:
	 * @return String 返回类型
	 * @throws
	 */
	public String tjcxlb() {
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
			request.setAttribute("tjcxlbxq", "1");
		}
		
		if (QUERY.equals(model.getDoType())) {
			try {
				//User user = SessionFactory.getUser();
				User user = getUser();
				model.setCzy(user.getYhm());
				List<ExportConfigModel> exportConfigList = tjcxCommon
						.getExportConfigList(model);
				getValueStack().set(DATA, exportConfigList);
			} catch (Exception e) {
				logException(e);
			}
			return DATA;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @描述:统计查询列表
	 * @作者：ligl
	 * @日期：2013-8-26 下午01:59:23
	 * @修改记录:
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public String tjcxsj() {
		if (QUERY.equals(model.getDoType())) {
			try {
				QueryModel queryModel = model.getQueryModel();
				//User user = SessionFactory.getUser();
				User user = getUser();
				model.setCzy(user.getYhm());
				List<HashMap> tjcxsjList = service.getTjcxsj(queryModel,model);
//				 List<HashMap<String, String>> list = new
//				 ArrayList<HashMap<String,String>>();
//				 HashMap<String, String> map = new HashMap<String, String>();
//				 map.put("xy", "理学院");
//				 map.put("zymc", "应用数学");
//				 list.add(map);
				queryModel.setItems(tjcxsjList);
				getValueStack().set(DATA, queryModel);
			} catch (Exception e) {
				logException(e);
			}
			return DATA;
		}
		return SUCCESS;

	}
	
	/**
	 * 导出
	 * @return
	 */
	public String export(){
		try{
			User user = getUser();
			model.setCzy(user.getYhm());
			
			String curBbl = model.getCurBbl();
			String newGltj = model.getNewGltj();
			String gltj = model.getGltj();
			if(newGltj != null && !newGltj.trim().equals("")){
				if(gltj == null || gltj.trim().equals("")){
					gltj = newGltj;
				}else{
					gltj += " and " + newGltj;
				}
			}
			
			//需要加上当前的过滤条件
			if(curBbl != null && !curBbl.trim().equals("")){
				if(gltj != null && !gltj.trim().equals("")){
					gltj += " and " + curBbl;
				}else{
					gltj = curBbl;
				}
			}
			
			model.setGltj(gltj);
			
			if(log.isDebugEnabled()){
				log.debug("统计查询列表导出过滤条件：" + model.getGltj());
			}
			
			exportModel.setZgh(user.getYhm());
			exportModel.setDcclbh(DCQZ + model.getXmdm());
			
			//先查询有几条需要导出的数据
			
			//1.如果数据量超过5000条，直接下载
			
			//2.如果数据量超过5000条，zip打包下载
			
			int tjcxsjCountNoPage = service.getTjcxsjCountNoPage(model);
			
			if(DEFAULT_EXPORT_MAX >= tjcxsjCountNoPage){
				exportModel.setDataList(service.getTjcxsjNoPage(model));
				file = exportService.getExportFile(exportModel);
			}else{
				exportModel.setDataList(service.getTjcxsjNoPage(model));
				file = exportServicePOI.getExportFile(exportModel);
			}
			
		} catch(Exception e){
			logException(e);
			return ERROR;
		}
		
		return "exportExcel";
	}
	

	/**
	 * 自定义导出
	 * @return
	 */
	public String customExport(){
		ValueStack stack = getValueStack();
		stack.set("dcclbh", DCQZ + model.getXmdm());
		return "toExportConfig";
	}

	public ITjcxService getService() {
		return service;
	}

	public void setService(ITjcxService service) {
		this.service = service;
	}

	public KzszModel getModel() {
		return model;
	}

	public void setModel(KzszModel model) {
		this.model = model;
	}

	public ExportModel getExportModel() {
		return exportModel;
	}

	public void setExportModel(ExportModel exportModel) {
		this.exportModel = exportModel;
	}

	public IExportService getExportService() {
		return exportService;
	}

	public void setExportService(IExportService exportService) {
		this.exportService = exportService;
	}

	public ITjcxCommon getTjcxCommon() {
		return tjcxCommon;
	}

	public void setTjcxCommon(ITjcxCommon tjcxCommon) {
		this.tjcxCommon = tjcxCommon;
	}

	public ITjxmService getTjxmService() {
		return tjxmService;
	}

	public void setTjxmService(ITjxmService tjxmService) {
		this.tjxmService = tjxmService;
	}

	public IKzszService getKzszService() {
		return kzszService;
	}

	public void setKzszService(IKzszService kzszService) {
		this.kzszService = kzszService;
	}

	public IExportService getExportServicePOI() {
		return exportServicePOI;
	}

	public void setExportServicePOI(IExportService exportServicePOI) {
		this.exportServicePOI = exportServicePOI;
	}

}
