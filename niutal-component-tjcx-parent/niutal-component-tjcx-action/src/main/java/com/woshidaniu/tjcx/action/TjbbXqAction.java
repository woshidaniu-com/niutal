package com.woshidaniu.tjcx.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.tjcx.dao.entites.CxzdModel;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.service.svcinterface.ICxzdService;
import com.woshidaniu.tjcx.service.svcinterface.ITjbbXqService;
import com.woshidaniu.tjcx.service.svcinterface.ITjcxCommon;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计报表action
 * @类功能描述:
 * @作者： ligl
 * @时间： 2014-05-20 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
public class TjbbXqAction extends BaseAction implements ModelDriven<KzszModel> {

	private static final long serialVersionUID = 8498805156665457520L;
	private ITjbbXqService service;
	private ICxzdService cxzdService;
	private ITjcxCommon tjcxCommon;
	private KzszModel model = new KzszModel();
	public String cxTjbbXq() {
		return SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String cxTjbbxqBt() {

		if (QUERY.equals(model.getDoType())) {
			try {
				//User user = SessionFactory.getUser();
				User user = getUser();
				model.setCzy(user.getYhm());
				List<ExportConfigModel> exportConfigList = tjcxCommon
						.getExportConfigList(model);
				HashMap map = new HashMap();
				HashMap cxzdBtMap = new HashMap();		
				if (exportConfigList == null || exportConfigList.size() == 0) {
					exportConfigList = new ArrayList<ExportConfigModel>();
					List<CxzdModel> cxzdList = cxzdService.getModelList(new CxzdModel(model.getXmdm()));
					ExportConfigModel exportConfigModel = null;
					for (CxzdModel cxzdModel : cxzdList) {
						exportConfigModel = new ExportConfigModel();
						exportConfigModel.setZd(cxzdModel.getZdmc());
						exportConfigModel.setZdmc(cxzdModel.getZdsm());
						exportConfigList.add(exportConfigModel);
					}
					List<CxzdModel> cxzdBtList = service.getCxzdBt(model);
					if(cxzdBtList != null && cxzdBtList.size() > 0){
						for (CxzdModel cxzdModel : cxzdBtList) {
							cxzdBtMap.put(cxzdModel.getZdmc(), cxzdModel.getQzfw());
						}
					}
				}

				map.put("exportConfigList", exportConfigList);
				map.put("bblqzfw", cxzdBtMap);
				
				getValueStack().set(DATA, map);
			} catch (Exception e) {
				logException(e);
			}
			return DATA;
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String cxTjbbXqsj() {
		if (QUERY.equals(model.getDoType())) {
			try {
				QueryModel queryModel = model.getQueryModel();
				//User user = SessionFactory.getUser();
				User user = getUser();
				model.setCzy(user.getYhm());
				
				String curBbl = model.getCurBbl();
				String gltj = model.getGltj();
				if(gltj == null || gltj.trim().equals("")){
					gltj = curBbl;
				}else{
					gltj = "(" + gltj +  ")";
					if(curBbl != null && !curBbl.trim().equals("")){
						gltj += " and " + curBbl;
					}
				}
				model.setGltj(gltj);
				
				List<HashMap> tjcxsjList = service.getTjbbXq(queryModel,model);
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
	
	@Override
	public KzszModel getModel() {
		return model;
	}

	public ITjbbXqService getService() {
		return service;
	}

	public void setService(ITjbbXqService service) {
		this.service = service;
	}

	public ITjcxCommon getTjcxCommon() {
		return tjcxCommon;
	}

	public void setTjcxCommon(ITjcxCommon tjcxCommon) {
		this.tjcxCommon = tjcxCommon;
	}

	public void setModel(KzszModel model) {
		this.model = model;
	}

	public ICxzdService getCxzdService() {
		return cxzdService;
	}

	public void setCxzdService(ICxzdService cxzdService) {
		this.cxzdService = cxzdService;
	}

}
