package com.woshidaniu.datarange.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.datarange.dao.entities.SjfwdxModel;
import com.woshidaniu.datarange.service.svcinterface.ISjfwdxService;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.format.pinyin4j.PingYinUtils;

/**
 * 
 *@类名称		： SjfwdxAction.java
 *@类描述		：数据范围对象
 *@创建人		：kangzhidong
 *@创建时间	：Aug 22, 2016 4:03:56 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class SjfwdxAction extends BaseAction implements ModelDriven<SjfwdxModel> {
	
	protected SjfwdxModel model = new SjfwdxModel();
	
	@Resource
	private ISjfwdxService sjfwdxService;
	
	public String cxSjfwdxInfo(){
		//数据范围对象列表yhJssjfwModel.setKzdx(model.getKzdx());
		SjfwdxModel sjfwdxModel = new SjfwdxModel();
					sjfwdxModel.setKzdx(model.getKzdx());
		List<SjfwdxModel> list = getSjfwdxService().cxSjfwdx(sjfwdxModel);
		//迭代页面元素关联查询信息
		boolean  isAdmin = getUser().isAdmin();
		for (Iterator<SjfwdxModel> iterator = list.iterator(); iterator.hasNext();) {
			SjfwdxModel element_query =  iterator.next();
			//匹配元素关联字段信息is
			if( !isAdmin && element_query.getBm().equalsIgnoreCase("SCHOOL")){
				//设置匹配的关联字段
				list.remove(element_query);
				break;
			}
		}
		getValueStack().set(DATA, list);
		return DATA;
	}
	
	//查询数据范围对象
	public String cxSjfwdxList(){
		try{
			
			//查询出当前数据范围对象的信息
			SjfwdxModel sjfwdxModel = getSjfwdxService().getModel(model);
			sjfwdxModel.setLs_bmdm(model.getLs_bmdm());
			sjfwdxModel.setLs_zydm(model.getLs_zydm());
			sjfwdxModel.setLs_bjdm(model.getLs_bjdm());
			sjfwdxModel.setLs_njdm(model.getLs_njdm());
			sjfwdxModel.setLs_xh(model.getLs_xh());
			sjfwdxModel.setLs_xqdm(model.getLs_xqdm());
			sjfwdxModel.setLssjjgid(model.getLssjjgid());
			//根据数据范围对象查询该对象可使用的数据范围明细
			List<SjfwdxModel> sjfwdxList = getSjfwdxService().getModelListByScope(sjfwdxModel);
			if(BlankUtils.isBlank(sjfwdxList)){
				sjfwdxList = new ArrayList<SjfwdxModel>();
			}
			
			/*减少响应的JSON数据量*/
			List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
			Map<String,Object> mapRow = null;
			Map<String,String> attrs = null;
			List<PairModel> parents = null;
			String table = model.getBm();
			for (SjfwdxModel model : sjfwdxList) {
				
				mapRow = new HashMap<String, Object>();
				mapRow.put("zddm", model.getZddm());
			    mapRow.put("zdmc", model.getZdmc());
			   try {
				   //PingYinUtils.converterToFUllSpell(model.getZdmc())
					mapRow.put("zwpy", PingYinUtils.hanziToPinyin(model.getZdmc()));
				} catch (Exception e) {
					mapRow.put("zwpy", "");
				}
			    
			    attrs = new HashMap<String, String>();
			    
			    attrs.put("lssjjgid", model.getLssjjgid());
			    attrs.put("jg_id", model.getLs_bmdm());
			    attrs.put("zyh_id", model.getLs_zydm());
			    attrs.put("njdm_id", model.getLs_njdm());
			  
			    
			    mapRow.put("attrs", attrs);
			    
			    parents = new ArrayList<PairModel>();
			    if("niutal_xtgl_jgdmb".equalsIgnoreCase(table)){
			    	//do nothing
			    }else if("niutal_xtgl_zydmb".equalsIgnoreCase(table)){
			    	parents.add(new PairModel("jg_id", "niutal_xtgl_jgdmb") );
			    }else if("niutal_xtgl_bjdmb".equalsIgnoreCase(table)){
			    	/*parents.add(new PairModel("jg_id", "niutal_xtgl_jgdmb") );
			    	parents.add(new PairModel("zyh_id", "niutal_xtgl_zydmb") );*/
			    }
			    mapRow.put("parents", parents);
			    
			    
				mapList.add(mapRow);
			}
			getValueStack().set(DATA,mapList);
		}catch(Exception e){
			logException(e);
		}
		return DATA;
	}
	
	@Override
	public SjfwdxModel getModel() {
		model.setUser(getUser());
		return model;
	}

	public ISjfwdxService getSjfwdxService() {
		return sjfwdxService;
	}

	public void setSjfwdxService(ISjfwdxService sjfwdxService) {
		this.sjfwdxService = sjfwdxService;
	}
}
