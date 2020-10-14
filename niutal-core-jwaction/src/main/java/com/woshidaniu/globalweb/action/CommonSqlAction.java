package com.woshidaniu.globalweb.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanMap;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.BjdmModel;
import com.woshidaniu.entities.BmdmModel;
import com.woshidaniu.entities.CommonModel;
import com.woshidaniu.entities.JcsjModel;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.NjdmModel;
import com.woshidaniu.entities.ZydmModel;
import com.woshidaniu.entities.ZyfxdmModel;
import com.woshidaniu.service.common.ICommonTimeSettingService;
import com.woshidaniu.util.xml.BaseDataReader;

/**
 * 
* 
* 类名称：CommonSqlAction 
* 类描述： 公共Action
* 创建人：caozf 
* 创建时间：2012-7-17 上午08:41:27 
* 修改备注： 
* @version 
*
 */
public class CommonSqlAction extends CommonBaseAction implements ModelDriven<CommonModel>  {

	private static final long serialVersionUID = 1L;
	
	protected BjdmModel bjdmMode;
	protected CommonModel model = new CommonModel();
	@Resource
	private ICommonTimeSettingService timeSettingService;
	 /**
	  * 
	  *@描述：分页查询年级信息
	  *@创建人:kangzhidong
	  *@创建时间:2014-6-24上午09:23:01
	  *@修改人:
	  *@修改时间:
	  *@修改描述:
	  *@return
	  *@throws Exception
	  */
	public String cxNjPaged() throws Exception {
		try {
			//分页查询
			List<NjdmModel> modelList = getCommonSqlService().getPagedNjList(model);
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(modelList);
			getValueStack().set(DATA,queryModel);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,model.getQueryModel());
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：查询年级信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午04:37:52
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String cxNjList() throws Exception {
		try {
			Map<String,Object> map = getMap();
			if(model.getQueryModel()==null){
				map.put("order", "desc");//排序方式 asc , desc
				map.put("sort", "njmc");//排序字段 
			}
			List<NjdmModel> njlist =  getCommonSqlService().queryNjList(map);
			getValueStack().set(DATA,njlist);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<NjdmModel>());
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：分页查询角色代码
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-23下午07:52:35
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxJsdmPaged(){
		try {
			//分页查询
			List<JsglModel> modelList = getCommonSqlService().getPagedJsxxList(model);
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(modelList);
			getValueStack().set(DATA,queryModel);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,model.getQueryModel());
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：查询角色代码集合
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午04:37:52
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String cxJsdmList(){
		try {
			Map<String,Object> map = getMap();
			List<JsglModel> jsList = getCommonSqlService().getJsList(map);
			getValueStack().set(DATA,jsList);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<JsglModel>());
		}
		return DATA;
	}
	
	
	/**
	 * 
	 *@描述：分页查询开课部门
	 *@创建人:kangzhidong
	 *@创建时间:2014-9-16下午04:38:08
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxKkbmPaged(){
		try {
			List<BmdmModel> modelList = getCommonSqlService().getPagedKkbmList(model);
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(modelList);
			getValueStack().set(DATA,queryModel);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,model.getQueryModel());
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：查询开课部门代码
	 *@创建人:kangzhidong
	 *@创建时间:2014-9-16下午04:38:28
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxKkbmList(){
		try {
			getValueStack().set(DATA,getCommonSqlService().queryAllKkbm());
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<BmdmModel>());
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：分页查询学院代码
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-23下午07:58:39
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxXydmPaged(){
		try {
			//分页查询
			List<BmdmModel> modelList = getCommonSqlService().getPagedXyxxList(model);
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(modelList);
			getValueStack().set(DATA,queryModel);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,model.getQueryModel());
		}
		return DATA;
	}
	
	
	/**
	 * 
	 *@描述：查询学院代码集合
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午04:38:21
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxXydmList(){
		try {
			getValueStack().set(DATA,getCommonSqlService().queryXyxxList(model));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<BmdmModel>());
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：分页查询系信息
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午02:01:50
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxXxdmPaged(){
		try {
			//分页查询
			List<BmdmModel> modelList = getCommonSqlService().getPagedXxxList(model);
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(modelList);
			getValueStack().set(DATA,queryModel);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,model.getQueryModel());
		}
		return DATA;
	}
	
	
	/**
	 * 
	 *@描述：查询系信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午04:38:41
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxXxdmList(){
		try {
			getValueStack().set(DATA,getCommonSqlService().queryXxxList(model));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<BmdmModel>());
		}
		return DATA;
	}
	
	
	/**
	 * 
	 *@描述：分页查询机构代码
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-23下午07:58:39
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxJgdmPaged(){
		try {
			//分页查询
			List<BmdmModel> modelList = getCommonSqlService().getPagedJgxxList(model);
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(modelList);
			getValueStack().set(DATA,queryModel);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,model.getQueryModel());
		}
		return DATA;
	}

	
	/**
	 * 
	 *@描述：查询机构代码
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午04:39:01
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxJgdmList(){
		try {
			getValueStack().set(DATA,getCommonSqlService().queryAllJgxx());
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<BmdmModel>());
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：分页查询指定学院下的专业代码
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-24上午09:22:50
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxZydmPaged(){
		try {
			//分页查询
			List<ZydmModel> modelList = getCommonSqlService().getPagedZyxxList(model);
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(modelList);
			getValueStack().set(DATA,queryModel);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,model.getQueryModel());
		}
		return DATA;
	}
	
	
	/**
	 * 
	 *@描述：查询指定学院下的专业代码
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午03:16:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxZydmList(){
		ValueStack vs = getValueStack();
		String jg_id = getRequest().getParameter("jg_id");
		List<String> jg_id_list = null;
		if(!StringUtils.isEmpty(jg_id)){
			jg_id_list = new ArrayList<String>();
			if(jg_id.indexOf(",")>-1){
				for (String id : jg_id.split(",")) {
					jg_id_list.add(id);
				}
			}else{
				jg_id_list.add(jg_id);
			}
			model.setJg_id_list(jg_id_list);
		}
		try {
			vs.set(DATA,getCommonSqlService().queryZyxxList(model));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<ZydmModel>());
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：查询某专业下的专业方向
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午03:16:40
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxZyfxList(){
		ValueStack vs = getValueStack();
		List<String> zyh_id_list = null;
		List<String> jg_id_list = null;
		if(!StringUtils.isEmpty(model.getJg_id())){
			jg_id_list = new ArrayList<String>();
			if(model.getJg_id().indexOf(",")>-1){
				for (String id : model.getJg_id().split(",")) {
					jg_id_list.add(id);
				}
			}else{
				jg_id_list.add(model.getJg_id());
			}
			model.setJg_id_list(jg_id_list);
		}
		if(!StringUtils.isEmpty(model.getZyh_id())){
			zyh_id_list = new ArrayList<String>();
			if(model.getZyh_id().indexOf(",")>-1){
				for (String id : model.getZyh_id().split(",")) {
					zyh_id_list.add(id);
				}
			}else{
				zyh_id_list.add(model.getZyh_id());
			}
			model.setZyh_id_list(zyh_id_list);
		}
		try {
			vs.set(DATA,getCommonSqlService().queryZyfxList(model));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<ZyfxdmModel>());
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：分页查询班级代码
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-24上午09:22:42
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxBjdmPaged(){
		try {
			//分页查询
			List<BjdmModel> modelList = getCommonSqlService().getPagedBjxxList(model);
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(modelList);
			getValueStack().set(DATA,queryModel);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,model.getQueryModel());
		}
		return DATA;
	}
	
	
	
	/**
	 * 
	 *@描述：查询班级代码集合
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-24上午09:22:42
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxBjdmList(){
		try {
			//查询班级数据
			String njdm_id = getRequest().getParameter("njdm_id");
			String jg_id = getRequest().getParameter("jg_id");
			String zyh_id = getRequest().getParameter("zyh_id");
			List<String> njdm_id_list = null;
			List<String> jg_id_list = null;
			List<String> zyh_id_list = null;
			if(!StringUtils.isEmpty(njdm_id)){
				njdm_id_list = new ArrayList<String>();
				if(njdm_id.indexOf(",")>-1){
					for (String id : njdm_id.split(",")) {
						njdm_id_list.add(id);
					}
				}else{
					njdm_id_list.add(njdm_id);
				}
			}
			if(!StringUtils.isEmpty(jg_id)){
				jg_id_list = new ArrayList<String>();
				if(jg_id.indexOf(",")>-1){
					for (String id : jg_id.split(",")) {
						jg_id_list.add(id);
					}
				}else{
					jg_id_list.add(jg_id);
				}
			}
			if(!StringUtils.isEmpty(zyh_id)){
				 zyh_id_list = new ArrayList<String>();
				if(zyh_id.indexOf(",")>-1){
					for (String id : zyh_id.split(",")) {
						zyh_id_list.add(id);
					}
				}else{
					zyh_id_list.add(zyh_id);
				}
			}
			model.setNjdm_id_list(njdm_id_list);
			model.setJg_id_list(jg_id_list);
			model.setZyh_id_list(zyh_id_list);
			getValueStack().set(DATA,getCommonSqlService().queryBjxxList(model));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<BjdmModel>());
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：根据基础数据类型代码查询基础数据
	 *@创建人:kangzhidong
	 *@创建时间:2014-7-3上午10:47:24
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String cxJcsjList() throws Exception {
		try {
			List<JcsjModel> modelList = getCommonSqlService().queryJcsjList(model.getLxdm());
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(modelList);
			queryModel.setTotalResult(modelList.size());
			getValueStack().set(DATA,queryModel);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,model.getQueryModel());
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：查询baseData.xml中的数据
	 *@创建人:kangzhidong
	 *@创建时间:2014-7-5下午01:51:00
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxBaseDataList(){
		try {
			String data_id = getRequest().getParameter("data_id");
			List<HashMap<String, String>> dataList = BaseDataReader.getCachedOptionList(data_id);
			getValueStack().set(DATA,dataList);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<HashMap<String, String>>());
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述		：查询邮箱后缀
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 21, 201612:12:43 PM
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String cxEmailPostfixList(){
		try {
			getValueStack().set(DATA,getCommonQueryService().getEmailPostfixList());
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<HashMap<String, String>>());
		}
		return DATA;
	}
	
	
	
	/**
	 * 
	 *@描述：查询考试名称
	 *@创建人:jiangyy
	 *@创建时间:2018-5-17	下午03:16:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxKsmcList(){
		ValueStack vs = getValueStack();
		String xnm = getRequest().getParameter("xnm");
		String xqm = getRequest().getParameter("xqm");
		try {
			vs.set(DATA,getCommonSqlService().queryKsmcList(model));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA,new ArrayList<ZydmModel>());
		}
		return DATA;
	}
	
	
	
	protected Map<String,Object> getMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		BeanMap beanMap = new BeanMap(model);
		Iterator<Object> ite = beanMap.keySet().iterator();
		while (ite.hasNext()) {
			String name = (String) ite.next();
			map.put(name, beanMap.get(name));
		}
		
		map.put("minNum", model.getMinNum());
		map.put("maxNum", model.getMaxNum());
		QueryModel queryModel = model.getQueryModel();
		if(queryModel!=null){
			map.put("order", queryModel.getSortOrder());
			map.put("sort", queryModel.getSortName());
		}
		return map;
	}
	
	public ICommonTimeSettingService getTimeSettingService() {
		return timeSettingService;
	}

	public void setTimeSettingService(ICommonTimeSettingService timeSettingService) {
		this.timeSettingService = timeSettingService;
	}

	public BjdmModel getBjdmMode() {
		return bjdmMode;
	}

	public void setBjdmMode(BjdmModel bjdmMode) {
		this.bjdmMode = bjdmMode;
	}

	public CommonModel getModel() {
		return model;
	}

	public void setModel(CommonModel model) {
		this.model = model;
	}
	
	
}

