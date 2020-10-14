package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.daointerface.IXwglDao;
import com.woshidaniu.entities.XwglModel;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IXwglService;

/**
 * 
* 
* 类名称：XwglServiceImpl 
* 类描述： 新闻管理实现
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
@After
@Logger(model="N0110",business="N011001")
@Service
public class XwglServiceImpl extends CommonBaseServiceImpl<XwglModel,IXwglDao> implements IXwglService {

	@Resource
	private IXwglDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
     	super.setDao(dao);
	}
	
	@Comment
	public boolean zjBcXw(XwglModel model) {
		if(model == null){
			return false;
		}
		String guid      = getQueryDao().getSysGuid();
		String groupId   =  model.getGroupId();
		String[] arrayId = groupId.split(",");
		if("1".equals(model.getMxdxlb())){
			for(int i=0;i<arrayId.length;i++){
				String id  =  arrayId[i];
				model.setXzlb(id.split("#")[0]);
				model.setXqh_id(id.split("#")[1].equals("n")?"":id.split("#")[1]);
				model.setJg_id(id.split("#")[2].equals("n")?"":id.split("#")[2]);
				model.setZyh_id(id.split("#")[3].equals("n")?"":id.split("#")[3]);
				model.setZyfx_id("");
				model.setNjdm_id(id.split("#")[4].equals("n")?"":id.split("#")[4]);
				model.setBh_id(id.split("#")[5].equals("n")?"":id.split("#")[5]);
				model.setXbm(id.split("#")[6].equals("n")?"":id.split("#")[6]);
				model.setXslbdm(id.split("#")[7].equals("n")?"":id.split("#")[7]);
				model.setPyccdm(id.split("#")[8].equals("n")?"":id.split("#")[8]);
				model.setXh_id(id.split("#")[9].equals("n")?"":id.split("#")[9]);
				model.setMxdxbh(guid);
				dao.insertXsdx(model);
			}
		}else if("2".equals(model.getMxdxlb())){
			for(int i=0;i<arrayId.length;i++){
				String id  =  arrayId[i];
				model.setXzlb(id.split("#")[0].equals("n")?"":id.split("#")[0]);
				model.setXnm(id.split("#")[1].equals("n")?"":id.split("#")[1]);
				model.setXqm(id.split("#")[2].equals("n")?"":id.split("#")[2]);
				model.setXqh_id(id.split("#")[3].equals("n")?"":id.split("#")[3]);
				model.setJg_id(id.split("#")[4].equals("n")?"":id.split("#")[4]);
				model.setKkbm_id(id.split("#")[5].equals("n")?"":id.split("#")[5]);
				model.setXbm(id.split("#")[6].equals("n")?"":id.split("#")[6]);
				model.setJgh_id(id.split("#")[7].equals("n")?"":id.split("#")[7]);
				model.setMxdxbh(guid);
				dao.insertJsdx(model);
			}
		}else if("3".equals(model.getMxdxlb())){
			for(int i=0;i<arrayId.length;i++){
				String id  =  arrayId[i];
				model.setXzlb(id.split("#")[0].equals("n")?"":id.split("#")[0]);
				model.setJsdm(id.split("#")[1].equals("n")?"":id.split("#")[1]);
				model.setJgh_id(id.split("#")[2].equals("n")?"":id.split("#")[2]);
				model.setMxdxbh(guid);
				dao.insertGwdx(model);
			}
		}else if("0".equals(model.getMxdxlb())){
			model.setMxdxbh("");
		}
		return dao.insert(model) > 0;
	}
	
	/**
	 * 修改通知公告
	 * @param model
	 * @return
	 */
	@Comment
	//@CacheExpire(key="Model")
	public boolean xgBcXw(XwglModel model) {
		if(model == null){
			return false;
		}
		String groupId   =  model.getGroupId();
		if(!"-1".equals(groupId)){
			dao.deleteMxdx(model.getXwbh());
			
			String guid      = getQueryDao().getSysGuid();
			String[] arrayId = groupId.split(",");
			if("1".equals(model.getMxdxlb())){
				for(int i=0;i<arrayId.length;i++){
					String id  =  arrayId[i];
					model.setXzlb(id.split("#")[0]);
					model.setXqh_id(id.split("#")[1].equals("n")?"":id.split("#")[1]);
					model.setJg_id(id.split("#")[2].equals("n")?"":id.split("#")[2]);
					model.setZyh_id(id.split("#")[3].equals("n")?"":id.split("#")[3]);
					model.setZyfx_id("");
					model.setNjdm_id(id.split("#")[4].equals("n")?"":id.split("#")[4]);
					model.setBh_id(id.split("#")[5].equals("n")?"":id.split("#")[5]);
					model.setXbm(id.split("#")[6].equals("n")?"":id.split("#")[6]);
					model.setXslbdm(id.split("#")[7].equals("n")?"":id.split("#")[7]);
					model.setPyccdm(id.split("#")[8].equals("n")?"":id.split("#")[8]);
					model.setXh_id(id.split("#")[9].equals("n")?"":id.split("#")[9]);
					model.setMxdxbh(guid);
					dao.insertXsdx(model);
				}
			}else if("2".equals(model.getMxdxlb())){
				for(int i=0;i<arrayId.length;i++){
					String id  =  arrayId[i];
					model.setXzlb(id.split("#")[0].equals("n")?"":id.split("#")[0]);
					model.setXnm(id.split("#")[1].equals("n")?"":id.split("#")[1]);
					model.setXqm(id.split("#")[2].equals("n")?"":id.split("#")[2]);
					model.setXqh_id(id.split("#")[3].equals("n")?"":id.split("#")[3]);
					model.setJg_id(id.split("#")[4].equals("n")?"":id.split("#")[4]);
					model.setKkbm_id(id.split("#")[5].equals("n")?"":id.split("#")[5]);
					model.setXbm(id.split("#")[6].equals("n")?"":id.split("#")[6]);
					model.setJgh_id(id.split("#")[7].equals("n")?"":id.split("#")[7]);
					model.setMxdxbh(guid);
					dao.insertJsdx(model);
				}
			}else if("3".equals(model.getMxdxlb())){
				for(int i=0;i<arrayId.length;i++){
					String id  =  arrayId[i];
					model.setXzlb(id.split("#")[0].equals("n")?"":id.split("#")[0]);
					model.setJsdm(id.split("#")[1].equals("n")?"":id.split("#")[1]);
					model.setJgh_id(id.split("#")[2].equals("n")?"":id.split("#")[2]);
					model.setMxdxbh(guid);
					dao.insertGwdx(model);
				}
			}else if("0".equals(model.getMxdxlb())){
				model.setMxdxbh("");
			}
		}
		
		return dao.update(model) > 0;
	}
	
	public boolean canDelete(String ids){
		return dao.canDelete(ids) <= 0;
	}
	
	@Comment
	public boolean scXw(XwglModel model) {
		if (!StringUtils.isEmpty(model.getXwbh())){
			String[] ids = model.getXwbh().split(",");
			
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			for (int i = 0; i < ids.length; i++) {
				dao.deleteMxdx(ids[i]);
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("xwbh", ids[i]);
				list.add(map);
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("list", list);
			param.put("xwbh", model.getXwbh());
			param.put("yhm", model.getYhm());
			int result = dao.batchDelete(param);
			return result > 0 ? true : false;
		}
		return false;
	}
	
	
	/**
	 * 发布新闻
	 */
	@Comment
	public boolean xgFbxw(XwglModel model) {
		
		if (!StringUtils.isEmpty(model.getXwbh())){
			String[] ids = model.getXwbh().split(",");
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			
			for (int i = 0; i < ids.length; i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("xwbh", ids[i]);
			    list.add(map);
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("list", list);
			param.put("sffb", 1);
			param.put("xwbh", model.getXwbh());
			param.put("yhm", model.getYhm());
			int result = dao.batchUpdate(param);
			return result > 0 ? true : false;
		}
		return false;
	}

	@Comment
	public boolean xgQxfbxw(XwglModel model)  {
		
		if (!StringUtils.isEmpty(model.getXwbh())){
			String[] ids = model.getXwbh().split(",");
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			
			for (int i = 0; i < ids.length; i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("xwbh", ids[i]);
			    list.add(map);
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("list", list);
			param.put("sffb", 0);
			param.put("xwbh", model.getXwbh());
			param.put("yhm", model.getYhm());
			int result = dao.batchUpdate(param);
			return result > 0 ? true : false;
		}
		
		return false;
	}

	@Comment
	public boolean xgZdxw(XwglModel model) {
		
		if (!StringUtils.isEmpty(model.getXwbh())){
			String[] ids = model.getXwbh().split(",");
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			
			for (int i = 0; i < ids.length; i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("xwbh", ids[i]);
			    list.add(map);
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("list", list);
			param.put("sfzd", 1);
			param.put("xwbh", model.getXwbh());
			param.put("yhm", model.getYhm());
			int result = dao.batchUpdate(param);
			return result > 0 ? true : false;
		}
		return false; 
	}
	
	@Comment
	public boolean xgQxzdXw(XwglModel model) {
		if (!StringUtils.isEmpty(model.getXwbh())){
			String[] ids = model.getXwbh().split(",");
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			for (int i = 0; i < ids.length; i++) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("xwbh", ids[i]);
			    list.add(map);
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("list", list);
			param.put("sfzd", 0);
			param.put("xwbh", model.getXwbh());
			param.put("yhm", model.getYhm());
			int result = dao.batchUpdate(param);
			return result > 0 ? true : false;
		}
		return false; 
	}

	
	/**
	 * 查询单条数据
	 * @param t
	 * @return
	 */
	public XwglModel getModel(XwglModel model){
		if(model == null){
			return null;
		}
		XwglModel xwgl = dao.getModel(model);
		if(xwgl != null && !StringUtils.isEmpty(xwgl.getFbdx())){
			xwgl.setFbdxs(xwgl.getFbdx().split(","));
		}
		return xwgl;
	}
	
	public int insertNewsLog(XwglModel model) {
		return dao.insertNewsLog(model);
	}
	
	@Override
	public List<XwglModel> getGrtzList(XwglModel model) {
		return dao.getGrtzList(model);
	}
	
	@Override
	public List<BaseMap> getPagedByScopeXsxx(XwglModel model) {
	 
		return dao.getPagedByScopeXsxx(model);
	}
	
	@Override
	public List<BaseMap> getPagedByScopeJsxx(XwglModel model) {
		return dao.getPagedByScopeJsxx(model);
	}
	
	@Override
	public List<BaseMap> getPagedByScopeGwxx(XwglModel model) {
		return dao.getPagedByScopeGwxx(model);
	}

	public List<XwglModel> cxXwList(XwglModel model) {
		return dao.getPagedByScopeXwList(model);
	}
	
	/**
	 * @see  {@link com.woshidaniu.comp.xtgl.xwgl.service.XwglService#fycxXw(XwglModel)}.
	 */
	/*public List<XwglModel> fycxXw(XwglModel model)  {
		
		return dao.getPagedList(model);
	}*/
}
