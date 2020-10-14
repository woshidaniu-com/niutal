package com.woshidaniu.datarange.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.BmdmModel;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.NjdmModel;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.entities.YhglModel;
import com.woshidaniu.datarange.dao.entities.SjfwdxModel;
import com.woshidaniu.datarange.dao.entities.YhJssjfwModel;
import com.woshidaniu.datarange.service.svcinterface.ISjfwdxService;
import com.woshidaniu.datarange.service.svcinterface.ISjfwzService;
import com.woshidaniu.datarange.service.svcinterface.IYhSjfwglService;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.format.utils.JSONFormatUtils;
import com.woshidaniu.service.common.ICommonSqlService;
import com.woshidaniu.service.svcinterface.IBmdmService;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.util.xml.BaseDataReader;

/**
 * 
 *@类名称		：YhJssjfwAction.java
 *@类描述		：用户角色数据权限范围管理action
 *@创建人		：kangzhidong
 *@创建时间	：Aug 25, 2016 9:43:05 AM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@SuppressWarnings({ "unchecked", "serial" })
public class YhjsSjfwAction extends BaseAction implements ModelDriven<YhJssjfwModel> {
	
	@Resource
	private IYhSjfwglService service;//角色管理SERVICE
	@Resource
	private IBmdmService bmdmService;
	@Resource
	private IYhglService yhglService;
	@Resource
	private ISjfwzService sjfwzService;
	@Resource
	private ISjfwdxService sjfwdxService;
	@Resource
	private ICommonSqlService commonSqlService;
	//角色管理SERVICE
	@Resource
	private IJsglService jsglService;
	
	protected YhJssjfwModel model = new YhJssjfwModel();   //数据范围模型

	protected Map<String,String> simple = new HashMap<String, String>();
	
	protected Map<String,PairModel> complex = new HashMap<String, PairModel>();
	
	public YhjsSjfwAction() {
		//学院
		complex.put("jg_id", new PairModel("njdm_id", "niutal_xtgl_jgdmb"));
		//专业
		complex.put("zyh_id", new PairModel("njdm_id", "niutal_xtgl_zydmb"));
		//开课部门
		simple.put("kkbm_id", "view_xtgl_kkbm");
		//班级
		simple.put("xh_id", "view_xjgl_xsjbxxb");
		//学生
		simple.put("bh_id", "niutal_xtgl_bjdmb");
		simple.put("bh", "niutal_xtgl_bjdmb");
		//学生性质
		simple.put("xsxzm", "jw_xjgl_xsxzdmb");
		//教师部门
		simple.put("jsbm_id", "view_xtgl_jsbm");
		//场地托管部门
		simple.put("cdbm_id", "view_xtgl_cdbm");
	}
	
	protected void setValueStack() {
		
		getValueStack().set("sfejsqFlag",1);
		//查询部门代码列表
		List<BmdmModel> jgdmsList = getBmdmService().getModelList(new BmdmModel());
		getValueStack().set("jgdmsList", jgdmsList);
		
		// 查询角色列表
		YhglModel model = new YhglModel();
		model.setYhm(getUser().getYhm());
		model.setJsgybj("1");
		List<JsglModel> jsxxList = getYhglService().cxJsdmList(model);
		getValueStack().set("jsxxList", jsxxList);

		int size = jsxxList.size();
		getValueStack().set("col", size > 4 ? (size % 4 == 0 ? size / 4 : (size / 4 + 1)): 1);
		
	}
	
	/**
	 *  
	 * @description: 进入数据权限设置页面
	 * @author : kangzhidong
	 * @date : 2014-5-15
	 * @time : 上午11:22:32 
	 * @return
	 * @throws Exception
	 */
	public String cxSjfwszIndex() throws Exception {
		setValueStack();
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：跳转至课程数据范围主页
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-23下午04:32:30
	 *@return
	 *@throws Exception
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxKcsjfwszIndex() throws Exception {
		
		setValueStack();
		
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：跳转至教师部门数据范围主页
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-23下午04:33:15
	 *@return
	 *@throws Exception
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxJsbmSjfwszIndex() throws Exception {
		
		setValueStack();
		
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：跳转至场地托管部门数据范围主页
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-23下午04:33:15
	 *@return
	 *@throws Exception
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxCdtgbmSjfwszIndex() throws Exception {
		
		setValueStack();
		
		return SUCCESS;
	}
	
	public String cxSjfwsz() throws Exception {
		//数据范围对象列表
		try {
			QueryModel queryModel = model.getQueryModel();
			model.setJgh(getUser().getYhm());
			List<YhJssjfwModel> list = getService().getPagedList(model);
			//循环取出角色数据范围,设置到数据范围字段中,供页面使用
			List<Map<String,Object>> sjfwList = null;
			Map<String,Object> sjfwMap = null;
			List<String> sjfwmcList = null;
			//进行数据范围与用户角色的组织
			if(!BlankUtils.isBlank(list)){
				
				//获得不重复用户名集合
				Set<String> yhmList = new HashSet<String>();
				for (YhJssjfwModel yhJssjfwModel : list) {
					yhmList.add(yhJssjfwModel.getYhm());
				}
				//查询该集合中用户的所有数据范围
				YhJssjfwModel yhModel = new YhJssjfwModel();
				yhModel.setKzdx(model.getKzdx());
				yhModel.setJsdm(model.getJsdm());
				yhModel.setJs_id(model.getJsdm());
				yhModel.setQueryList(BlankUtils.isBlank(yhmList)?new ArrayList():CollectionUtils.setToList(yhmList));
				List<YhJssjfwModel> fwlist = getService().getYhSjfwList(yhModel);
				
				//循环用户名
				for (YhJssjfwModel yhmModel : list) {
					//用户多个角色的数据范围集合
					sjfwList = new  ArrayList<Map<String,Object>>();
					//分割角色代码
					String[] jsdms = yhmModel.getJsmc().split(",");
					for (int i = 0; i < jsdms.length; i++) {
						//当前角色的数据范围map对象
						sjfwMap = new HashMap<String, Object>();
						sjfwmcList = new ArrayList<String>();
						//当前角色代码，角色名称
						String[] split = jsdms[i].split("#");
						String jsdm = split[0];
						String jsmc = split[1];
						//查询当前循环的用户的此角色的数据范围集合
						yhmModel.setJs_id(jsdm);
						//循环集合，拼装数据范围内容
						for (YhJssjfwModel yhJssjfwModel : fwlist) {
							//比对用户
							if(yhJssjfwModel.getJsdm().equals(jsdm)&& yhmModel.getYhm().equals(yhJssjfwModel.getYhm())){
								sjfwmcList.add(yhJssjfwModel.getSjfwzmc());
							}
						}
						//设置结果
						sjfwMap.put("jsdm", jsdm);
						sjfwMap.put("jsmc", jsmc);
						sjfwMap.put("sjfwmcList",sjfwmcList);
						sjfwList.add(sjfwMap);
					}
					yhmModel.setSjfwList(sjfwList);
				}
			}else{
				//循环用户名
				for (YhJssjfwModel yhmModel : list) {
					//用户多个角色的数据范围集合
					yhmModel.setSjfwList(new  ArrayList<Map<String,Object>>());
				}
			}
			
			queryModel.setItems(list);
			getValueStack().set(DATA, queryModel);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, model.getQueryModel());
		}
		return DATA;
	}	
	
	private void setSfejsq() {
		// 查询当前登录用户正激活角色信息
		boolean sfersq = !getUser().isAdmin() ? getJsglService().getYhEjsq(getUser().getJsdm()) : true;
		//判断是否允许二级授权
		if(sfersq){
			getValueStack().set("sfejsqFlag", 1);
		}else{
			getValueStack().set("sfejsqFlag", 0);
		}
	}
	
	/**
	 * 方法描述: 批量数据授权 
	 * 参数 @return 参数说明 
	 * 返回类型 String 返回类型
	 * @throws
	 */
	public String cxSjfwszSetting(){
		try {
			//取当前用户登录的角色
			// 查询当前登录用户正激活角色信息
			boolean sfersq = !getUser().isAdmin() ? getJsglService().getYhEjsq(getUser().getJsdm()) : true;
			
			List<NjdmModel> njdmList = getCommonSqlService().queryAllNj();
			getValueStack().set("njdmsJson", JSONFormatUtils.toJSONString(njdmList, "njdm_id", "njmc") );
			
			//判断是否允许二级授权
			if(sfersq){
				getValueStack().set("sfejsqFlag", 1);
				//数据范围对象列表
				getValueStack().set("njdms", getCommonSqlService().queryAllNj()); 
				getValueStack().set("bmdms", getCommonSqlService().queryAllXy()); 
				getValueStack().set("jxbmdms", getCommonSqlService().queryAllJgxx());
				getValueStack().set("bmlxs", BaseDataReader.getCachedBaseDataList("bmlxList")); 
				getValueStack().set("xqdms", getCommonSqlService().queryAllXq()); 
			}else{
				getValueStack().set("sfejsqFlag", 0);
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String cxKcsjfwszSetting(){
		setSfejsq();
		return SUCCESS;
	}
	
	public String cxJsbmSjfwszSetting(){
		setSfejsq();
		return SUCCESS;
	}
	
	public String cxCdtgbmSjfwszSetting(){
		setSfejsq();
		return SUCCESS;
	}
	
	/**
	 * 
	 * @description: 保存或修改数据范围
	 * @author : kangzhidong
	 * @date : 2014-4-9
	 * @time : 下午05:04:48 
	 * @return
	 * @throws Exception
	 */
	public String sjsqXgSjfwsz() throws Exception{
		try {
			//添加,更新角色数据范围信息
			SjfwdxModel sjfwdxModel = new SjfwdxModel();
			sjfwdxModel.setKzdx(model.getKzdx());
			service.updateYhSjfw(model,getSjfwdxService().cxSjfwdx(sjfwdxModel));
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}
	
	/**
	 * 
	 * @description: 获得按表名分类的数据范围集合
	 * @author : kangzhidong
	 * @date : 2014-5-30
	 * @time : 下午03:15:34 
	 * @param dataranges
	 * @param table
	 * @return
	 */
	protected List<Map<String,Object>> getDataRanges(Map<String,List<Map<String,Object>>> dataranges,String table){
		List<Map<String,Object>> datarange = dataranges.get(table);
		if(null==datarange){
			datarange = new ArrayList<Map<String,Object>>();
			dataranges.put(table, datarange);
		}
		return datarange;
	}
	
	/**
	 * 
	 * @description: 查询用户数据范围JSON数据，为了同步页面用户数据范围已选范围
	 * @author : kangzhidong
	 * @date : 2014-6-3
	 * @time : 上午09:09:30 
	 * @return
	 * @throws Exception
	 */
	public String cxSjfwszList() throws Exception{
        try {
			// 查询用户角色对应数据范围
			List<YhJssjfwModel> yhjsModels = getService().getYhSjfwList(model);
			Map<String,List<Map<String,Object>>> datarangeList = new HashMap<String, List<Map<String,Object>>>();
			//有数据范围的情况
			if(yhjsModels!=null&&yhjsModels.size()>0){
				//循环用户数据范围明细，组织数据范围JSON
				for (YhJssjfwModel dataRange : yhjsModels) {
					//获得当前记录行数据范围结果
					String sjfwz_id = dataRange.getSjfwz_id();
					String sjfwztj = dataRange.getSjfwztj();
					String[] sjfw_arr = StringUtils.splits(sjfwztj, ";");
					if(!BlankUtils.isBlank(sjfwztj)){
						//按全校
						if(sjfwztj.indexOf("jg_id=-2")>-1){
							Map<String,Object> map = new HashMap<String, Object>();
							map.put("sfqx","1");
							map.put("jg_id","-2");
							map.put("sjfwz_id",sjfwz_id);
							getDataRanges(datarangeList, "SCHOOL").add(map);
						}
						//按学院-全学院
						else if(sjfwztj.indexOf("jg_id=-3")>-1){
							Map<String,Object> map = new HashMap<String, Object>();
							map.put("sfqxy","1");
							map.put("jg_id","-3");
							map.put("sjfwz_id",sjfwz_id);
							//allList 参数关系着页面，年级属性在只保存了学院或者专业ID的情况下，是否选中
							map.put("allList",true);
							map.put("list",new ArrayList<Object>());
							getDataRanges(datarangeList, "niutal_xtgl_jgdmb").add(map);
						}else{
							boolean isComplex = false;
							//判断是否复杂组合
							for (String key_id : complex.keySet()) {
								if(sjfwztj.indexOf(key_id)>-1){
									isComplex = true;
									break;
								}
							}
							if(isComplex){
								//按学院-学院组合:不包括全校，全学院：如：jg_id=0322,njdm_id=2004,2005,2006,2007
								//按专业  如：zyh_id=0322,njdm_id=2004,2005,2006,2007
								for (String key_id : complex.keySet()) {
									
									PairModel pairModel = complex.get(key_id);
									
									//获得按表名分类的数据范围集合
									List<Map<String,Object>> dataranges = getDataRanges(datarangeList, pairModel.getValue());
									//获得当前行数据范围应归属的数据范围Map
									Map<String, Object> map = new HashMap<String, Object>();
									List<String> njList =  new ArrayList<String>();
									for (String sjfw : sjfw_arr) {
										String[] key_val = StringUtils.splits(sjfw, "=");
										if(key_val[0].equalsIgnoreCase(key_id)){
											//学院:jg_id=0322
											map.put(key_id, key_val[1]);
											map.put("sjfwz_id",sjfwz_id);
										}else if(key_val[0].equalsIgnoreCase(pairModel.getKey())){
											//年级：njdm_id=2004,2005,2006,2007
											njList.addAll(CollectionUtils.arrayToList(StringUtils.splits(key_val[1], "年,")));
										}
									}
									map.put("allList",njList.size() > 0 ? false : true);
									map.put("list", njList);
									dataranges.add(map);
								}
								
							}else{
								
								boolean isSimple = false;
								//判断是否简单组合
								for (String key_id : simple.keySet()) {
									if(sjfwztj.indexOf(key_id)>-1){
										isSimple = true;
										break;
									}
								}
								if(isSimple){
									for (String key_id : simple.keySet()) {
										String table_name = simple.get(key_id);
										//获得按表名分类的数据范围集合
										List<Map<String,Object>> dataranges = getDataRanges(datarangeList, table_name);
										//获得当前行数据范围应归属的数据范围Map
										Map<String, Object> map = new HashMap<String, Object>();
										for (String sjfw : sjfw_arr) {
											String[] key_val = StringUtils.splits(sjfw, "=");
											if(key_val[0].equalsIgnoreCase(key_id)){
												map.put(key_id, key_val[1]);
												map.put("sjfwz_id",sjfwz_id);
											}
										}
										dataranges.add(map);
									}
								}
							}
						}
					}
				}
			}else{
				for (String key_id : simple.keySet()) {
					String table_name = simple.get(key_id);
					getDataRanges(datarangeList, table_name);
				}
				for (String key_id : complex.keySet()) {
					PairModel pairModel = complex.get(key_id);
					getDataRanges(datarangeList, pairModel.getValue());
				}
			}
			getValueStack().set(DATA,datarangeList);
		} catch (Exception e) {
			logStackException(e);
		}
		return DATA;
    }

	@Override
	public YhJssjfwModel getModel() {
		model.setUser(getUser());
		return model;
	}

	public IYhSjfwglService getService() {
		return service;
	}

	public void setService(IYhSjfwglService service) {
		this.service = service;
	}

	public void setModel(YhJssjfwModel model) {
		this.model = model;
	}

	public ISjfwdxService getSjfwdxService() {
		return sjfwdxService;
	}

	public void setSjfwdxService(ISjfwdxService sjfwdxService) {
		this.sjfwdxService = sjfwdxService;
	}

	public IBmdmService getBmdmService() {
		return bmdmService;
	}

	public void setBmdmService(IBmdmService bmdmService) {
		this.bmdmService = bmdmService;
	}

	public IYhglService getYhglService() {
		return yhglService;
	}

	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}
	
	public ISjfwzService getSjfwzService() {
		return sjfwzService;
	}
	public void setSjfwzService(ISjfwzService sjfwzService) {
		this.sjfwzService = sjfwzService;
	}

	public IJsglService getJsglService() {
		return jsglService;
	}

	public void setJsglService(IJsglService jsglService) {
		this.jsglService = jsglService;
	}

	public ICommonSqlService getCommonSqlService() {
		return commonSqlService;
	}

	public void setCommonSqlService(ICommonSqlService commonSqlService) {
		this.commonSqlService = commonSqlService;
	}
	
	

}
