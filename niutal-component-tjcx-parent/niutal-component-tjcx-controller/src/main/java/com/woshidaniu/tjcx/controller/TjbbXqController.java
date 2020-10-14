package com.woshidaniu.tjcx.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woshidaniu.common.controller.BaseController;
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
@Controller
@RequestMapping("/niutal/tjcx/tjbbXq")
public class TjbbXqController extends BaseController {

	//KzszModel model
	
	@Autowired
	protected ITjbbXqService service;
	@Autowired
	protected ICxzdService cxzdService;
	@Autowired
	protected ITjcxCommon tjcxCommon;
	
	@RequestMapping("/cxTjbbXq.zf")
	public String cxTjbbXq() {
		return "/tjcx/cxTjbbXq";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/cxTjbbxqBt.zf")
	public String cxTjbbxqBt() {
		return "/tjcx/cxTjbbxqBt";
	}

	@RequestMapping(value="/cxTjbbxqBt.zf", params="doType=query")
	public Object cxTjbbxqBt(KzszModel model){
		try {
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
			return map;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/cxTjbbXqsj.zf")
	public String cxTjbbXqsj() {
		return "/tjcx/cxTjbbXqsj";

	}
	@RequestMapping(value="/cxTjbbXqsj.zf", params="doType=query")
	public Object cxTjbbXqsj(KzszModel model){
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
			queryModel.setItems(tjcxsjList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
		}
		return null;
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
	
	public ICxzdService getCxzdService() {
		return cxzdService;
	}

	public void setCxzdService(ICxzdService cxzdService) {
		this.cxzdService = cxzdService;
	}

}
