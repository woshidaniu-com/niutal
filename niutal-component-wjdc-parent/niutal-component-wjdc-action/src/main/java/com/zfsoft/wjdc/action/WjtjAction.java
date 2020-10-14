package com.woshidaniu.wjdc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.wjdc.dao.entites.StglModel;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.dao.entites.WjpzModel;
import com.woshidaniu.wjdc.dao.entites.WjpzSjylxModel;
import com.woshidaniu.wjdc.dao.entites.WjtjModel;
import com.woshidaniu.wjdc.dao.entites.XxglModel;
import com.woshidaniu.wjdc.excel.ExcelUtils;
import com.woshidaniu.wjdc.service.svcinterface.IStglService;
import com.woshidaniu.wjdc.service.svcinterface.IWjBaseService;
import com.woshidaniu.wjdc.service.svcinterface.IWjglService;
import com.woshidaniu.wjdc.service.svcinterface.IWjtjService;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WjtjAction extends BaseAction implements ModelDriven<WjtjModel> {
	
	private static final long serialVersionUID = 1L;
	private WjtjModel model=new WjtjModel();
	@Autowired
	private IWjtjService service;
	@Autowired
	private IWjBaseService wjBaseService;
	@Autowired
	private IStglService stglService;
	@Autowired
	private IWjglService wjglService;
	/**
	 * 查询问卷统计
	 * @return
	 */
	public String cxWjtj(){
		ValueStack vs = getValueStack();
		//查询数据
		if (QUERY.equals(model.getDoType())) {
			QueryModel queryModel = model.getQueryModel();
			try {
				queryModel.setItems(service.getPagedList(model));
			} catch (Exception e) {
				logException(e);
			}
			vs.set(DATA, queryModel);
			return DATA;
		}
		return "cxWjtj";
	}
	
	/**答卷统计*/
	public String djtj(){
		ValueStack vs = getValueStack();
		try {
			//问卷已分发数据源列表
			List<WjpzSjylxModel> lxbtList = service.getWjyffSjylxList(model);
			String lxid = StringUtils.isEmpty(model.getLxid()) ? (lxbtList != null && lxbtList.size() > 0 ? 
					lxbtList.get(0).getLxid() : "") : model.getLxid();
			model.setLxid(lxid);
			
			//获取查询条件，结果
			WjpzModel wjpzModel = new WjpzModel();
			wjpzModel.setLxid(lxid);
			wjpzModel.setGnlb(wjBaseService.GNLB_WJDTQKTJ);
			List<WjpzModel> tjList = wjBaseService.getGnlbCxtjPzList(wjpzModel);	
			List<WjpzModel> jgList = wjBaseService.getGnlbCxjgPzList(wjpzModel);
			WjpzSjylxModel sjyModel = wjBaseService.getWjpzSjylxModel(lxid);
			
			Map<String, String> cxtjMap = wjBaseService.formatMap(getRequest().getParameterMap());
			
			//查询结果展现
			wjpzModel.setFields(model.getGroupFields());
			List<WjpzModel> titList = service.getFieldPzListByFields(wjpzModel);
			
			String whereSql=wjBaseService.getWhereSql(cxtjMap);
			List<HashMap<String, Object>> rsList = service.getDjtjList(model, sjyModel,whereSql);
			vs.set("titList", titList);
			getRequest().setAttribute("groupFields", model.getGroupFields());
			getRequest().setAttribute("rsList", rsList);
			vs.set("sjyModel", sjyModel);
			vs.set("tjList", tjList);	
			vs.set("jgList", jgList);	
			
			vs.set("lxbt",lxid);
			vs.set("wjid", model.getWjid());
			//将MAP转换为str查询条件值
			vs.set("valueStr", wjBaseService.formatMaptoStr(cxtjMap));
			vs.set("lxbtList", lxbtList);//表头类型列表
		} catch (Exception e) {
			logException(e);
		}
		return "djtj";
	}
	
	/**答卷统计(匿名问卷)*/
	public String djtjNmwj(){
		ValueStack vs = getValueStack();
		try {
			//问卷已分发数据源列表
			WjpzSjylxModel asyn = new WjpzSjylxModel();
			asyn.setLxid("anonymous");
			asyn.setLxmc("匿名");
			List<WjpzSjylxModel> lxbtList = new ArrayList<WjpzSjylxModel>();
			lxbtList.add(asyn);
			String lxid = StringUtils.isEmpty(model.getLxid()) ? (lxbtList != null && lxbtList.size() > 0 ? 
					lxbtList.get(0).getLxid() : "") : model.getLxid();
			model.setLxid(lxid);
			
			List<HashMap<String, Object>> rsList = service.getDjtjNmwjList(model);
			getRequest().setAttribute("rsList", rsList);
			
			vs.set("lxbt",lxid);
			vs.set("wjid", model.getWjid());
			vs.set("lxbtList", lxbtList);//表头类型列表
		} catch (Exception e) {
			logException(e);
		}
		return "djtjNmwj";
	}
	
	/**试题统计*/
	public String sttj(){
		ValueStack vs = getValueStack();
		try {
			//问卷已分发数据源列表
			//model.setDjzt("已答卷");
			List<WjpzSjylxModel> lxbtList = service.getWjyffSjylxList(model);
			String lxid = StringUtils.isEmpty(model.getLxid()) ? (lxbtList != null && lxbtList.size() > 0 ? 
					lxbtList.get(0).getLxid() : "") : model.getLxid();
			model.setLxid(lxid);
			
			//获取查询条件，结果
			WjpzModel wjpzModel = new WjpzModel();
			wjpzModel.setLxid(lxid);
			wjpzModel.setGnlb(wjBaseService.GNLB_WJSTTJ);
			List<WjpzModel> tjList = wjBaseService.getGnlbCxtjPzList(wjpzModel);	
			WjpzSjylxModel sjyModel = wjBaseService.getWjpzSjylxModel(lxid);
			
			Map<String, String> cxtjMap = wjBaseService.formatMap(getRequest().getParameterMap());
			
			//查询结果展现
			wjpzModel.setFields(model.getGroupFields());
			
			String whereSql=wjBaseService.getWhereSql(cxtjMap);
			List<HashMap<String,Object>> rsList=service.getWjxzstTjxx(model,sjyModel,whereSql);
			
			//获取问卷相关信息
			WjglModel wjglModel=new WjglModel();
			wjglModel.setWjid(model.getWjid());
			wjglModel=wjBaseService.getWjglModel(wjglModel);
			vs.set("wjglModel", wjglModel);
			
			vs.set("rsList", rsList);
			vs.set("sjyModel", sjyModel);
			vs.set("tjList", tjList);	
			
			vs.set("lxbt",lxid);
			vs.set("wjid", model.getWjid());
			//将MAP转换为str查询条件值
			vs.set("valueStr", wjBaseService.formatMaptoStr(cxtjMap));
			vs.set("lxbtList", lxbtList);//表头类型列表
		} catch (Exception e) {
			logException(e);
		}
		return "sttj";
	}
	
	/**
	 * 试题统计导出
	 * @return
	 */
	public String sttjExport(){
		return SUCCESS;
	}
	
	/**交叉统计*/
	public String jctj(){
		ValueStack vs = getValueStack();
		try {
			//问卷已分发数据源列表
			//model.setDjzt("已答卷");
			List<WjpzSjylxModel> lxbtList = service.getWjyffSjylxList(model);
			String lxid = StringUtils.isEmpty(model.getLxid()) ? (lxbtList != null && lxbtList.size() > 0 ? 
					lxbtList.get(0).getLxid() : "") : model.getLxid();
			model.setLxid(lxid);
			
			//获取查询条件，结果
			WjpzModel wjpzModel = new WjpzModel();
			wjpzModel.setLxid(lxid);
			wjpzModel.setGnlb(wjBaseService.GNLB_WJSTJCTJ);
			List<WjpzModel> tjList = wjBaseService.getGnlbCxtjPzList(wjpzModel);	
			List<WjpzModel> jgList = wjBaseService.getGnlbCxjgPzList(wjpzModel);
			WjpzSjylxModel sjyModel = wjBaseService.getWjpzSjylxModel(lxid);
			
			Map<String, String> cxtjMap = wjBaseService.formatMap(getRequest().getParameterMap());
			
			//查询结果展现
			wjpzModel.setFields(model.getGroupFields());
			
			//获取统计试题选项列表
			StglModel stglModel=new StglModel();
			stglModel.setWjid(model.getWjid());
			stglModel.setStid(model.getStidy());
			List<XxglModel> xxList=wjBaseService.getWjOneStXxList(stglModel);
			//获取数据源过滤条件
			String whereSql=wjBaseService.getWhereSql(cxtjMap);
			//获取最后的结果集
			List<HashMap<String,Object>> rsList=service.getWjxzstJctjxx(model,sjyModel,whereSql,xxList,getRequest());
			
			//vs.set("rsList", rsList);
			vs.set("sjyModel", sjyModel);
			vs.set("tjList", tjList);
			vs.set("jgList", jgList);
			
			vs.set("lxbt",lxid);
			vs.set("wjid", model.getWjid());
			//将MAP转换为str查询条件值
			vs.set("valueStr", wjBaseService.formatMaptoStr(cxtjMap));
			vs.set("lxbtList", lxbtList);//表头类型列表

			WjglModel wjglModel=new WjglModel();
			wjglModel.setWjid(model.getWjid());
			vs.set("wjstList", service.getWjxxstxx(wjglModel));
			
			vs.set("xxList", xxList);
			getRequest().setAttribute("xxList", xxList);
			getRequest().setAttribute("rsList", rsList);
		} catch (Exception e) {
			logException(e);
		}
		return "jctj";
	}
	
	/**交叉统计（匿名问卷）*/
	public String jctjNmwj(){
		ValueStack vs = getValueStack();
		try {
			//问卷已分发数据源列表
			//model.setDjzt("已答卷");
			WjpzSjylxModel asyn = new WjpzSjylxModel();
			asyn.setLxid("anonymous");
			asyn.setLxmc("匿名");
			List<WjpzSjylxModel> lxbtList = new ArrayList<WjpzSjylxModel>();
			lxbtList.add(asyn);
			String lxid = StringUtils.isEmpty(model.getLxid()) ? (lxbtList != null && lxbtList.size() > 0 ? 
					lxbtList.get(0).getLxid() : "") : model.getLxid();
			model.setLxid(lxid);
			
			//获取查询条件，结果
			WjpzSjylxModel sjyModel = wjBaseService.getWjpzSjylxModel(lxid);
			
			//获取统计试题选项列表
			StglModel stglModel=new StglModel();
			stglModel.setWjid(model.getWjid());
			stglModel.setStid(model.getStidy());
			List<XxglModel> xxList=wjBaseService.getWjOneStXxList(stglModel);
			
			//获取最后的结果集
			List<HashMap<String,Object>> rsList=service.getWjxzstJctjxxNmwj(model,xxList,getRequest());

			vs.set("sjyModel", sjyModel);
			vs.set("lxbt",lxid);
			vs.set("wjid", model.getWjid());
			vs.set("lxbtList", lxbtList);//表头类型列表

			WjglModel wjglModel=new WjglModel();
			wjglModel.setWjid(model.getWjid());
			vs.set("wjstList", service.getWjxxstxx(wjglModel));
			
			vs.set("xxList", xxList);
			getRequest().setAttribute("xxList", xxList);
			getRequest().setAttribute("rsList", rsList);
		} catch (Exception e) {
			logException(e);
		}
		return "jctjnNmwj";
	}
	
	/**交叉统计参数配置*/
	public String jctjcspz(){
		ValueStack vs = getValueStack();
		try {
			if(model.getTjtype()!=null&&!"".equals(model.getTjtype())){
				model.setGroupFields(service.getJctjcspzOne(model));
			}
			//问卷已分发数据源列表
			//model.setDjzt("已答卷");
			List<WjpzSjylxModel> lxbtList = service.getWjyffSjylxList(model);
			String lxid = StringUtils.isEmpty(model.getLxid()) ? (lxbtList != null && lxbtList.size() > 0 ? 
					lxbtList.get(0).getLxid() : "") : model.getLxid();
			model.setLxid(lxid);
			
			//获取查询条件，结果
			WjpzModel wjpzModel = new WjpzModel();
			wjpzModel.setLxid(lxid);
			wjpzModel.setGnlb(wjBaseService.GNLB_WJSTJCTJ);
			List<WjpzModel> tjList = wjBaseService.getGnlbCxtjPzList(wjpzModel);	
//			List<WjpzModel> jgList = wjBaseService.getGnlbCxjgPzList(wjpzModel);
			WjpzSjylxModel sjyModel = wjBaseService.getWjpzSjylxModel(lxid);
			
			Map<String, String> cxtjMap = wjBaseService.formatMap(getRequest().getParameterMap());
			
			//查询结果展现
			wjpzModel.setFields(model.getGroupFields());
			
			//获取统计试题选项列表
			StglModel stglModel=new StglModel();
			stglModel.setWjid(model.getWjid());
			stglModel.setStid(model.getStidy());
			List<XxglModel> xxList=wjBaseService.getWjOneStXxList(stglModel);
			//获取数据源过滤条件
			String whereSql=wjBaseService.getWhereSql(cxtjMap);
			//获取最后的结果集
			List<HashMap<String,Object>> rsList=service.getWjxzstJctjxx(model,sjyModel,whereSql,xxList,getRequest());
			
			//vs.set("rsList", rsList);
			vs.set("sjyModel", sjyModel);
			vs.set("tjList", tjList);
//			vs.set("jgList", jgList);
			vs.set("jgList", service.getJctjcspzList(model));
			
			vs.set("lxbt",lxid);
			vs.set("wjid", model.getWjid());
			//将MAP转换为str查询条件值
			vs.set("valueStr", wjBaseService.formatMaptoStr(cxtjMap));
			vs.set("lxbtList", lxbtList);//表头类型列表

			WjglModel wjglModel=new WjglModel();
			wjglModel.setWjid(model.getWjid());
			vs.set("wjstList", service.getWjxxstxx(wjglModel));
			
			vs.set("xxList", xxList);
			getRequest().setAttribute("xxList", xxList);
			getRequest().setAttribute("rsList", rsList);
		} catch (Exception e) {
			logException(e);
		}
		return "jctjcspz";
	}
	
	public String djxq(){
		ValueStack vs = getValueStack();
		try {
			List<WjpzSjylxModel> lxbtList = wjBaseService.getWjpzSjylxList();
			String lxid = StringUtils.isEmpty(model.getLxid()) ? (lxbtList != null && lxbtList.size() > 0 ? 
					lxbtList.get(0).getLxid() : "") : model.getLxid();
			model.setLxid(lxid);
			
			//删除问卷对象
//			plscYffwjdxxx(vs, user);
			
			//发布问卷对象标志
//			xgWjffdxbz(vs, user);
			
			//获取查询条件，结果
			WjpzModel wjpzModel = new WjpzModel();
			wjpzModel.setLxid(lxid);
			wjpzModel.setGnlb(wjBaseService.GNLB_WJFF);
			List<WjpzModel> tjList = wjBaseService.getGnlbCxtjPzList(wjpzModel);	
			List<WjpzModel> jgList = wjBaseService.getGnlbCxjgPzList(wjpzModel);
			WjpzSjylxModel sjyModel = wjBaseService.getWjpzSjylxModel(lxid);
			
			Map<String, String> cxtjMap = wjBaseService.formatMap(getRequest().getParameterMap());
			//获取数据源过滤条件
			String whereSql=wjBaseService.getWhereSql(cxtjMap);
			//查询结果展现
			List<HashMap<String, String>> titList = wjBaseService.getWjdxcxbtList(jgList);
			setTjjg(jgList, titList);//特殊处理查询条件及结果
			List<String[]> rsList = service.getDjxqList(whereSql, jgList, sjyModel,model);
//			List<String[]> rs = service.getWjdxCxjgList(rsList, jgList, sjyModel.getZj());//格式化输出结果
			vs.set("titList", titList);
			vs.set("rsList", rsList);
			vs.set("sjyModel", sjyModel);
			WjpzModel pzModel = new WjpzModel();
			pzModel.setZd("djzt");
			pzModel.setZdmc("答卷状态");
			pzModel.setBqlx("select");
			tjList.add(pzModel);
			vs.set("tjList", tjList);		
			vs.set("lxbt",lxid);
			vs.set("wjid", model.getWjid());
			//将MAP转换为str查询条件值
			vs.set("valueStr", wjBaseService.formatMaptoStr(cxtjMap));
			vs.set("lxbtList", lxbtList);//表头类型列表
		} catch (Exception e) {
			logException(e);
		}
		return "djxq";
	}
	
	//答卷详情 匿名问卷单独调用
	public String djxqNmwj(){
		ValueStack vs = getValueStack();
		try {
			WjpzSjylxModel asyn = new WjpzSjylxModel();
			asyn.setLxid("anonymous");
			asyn.setLxmc("匿名");
			List<WjpzSjylxModel> lxbtList = new ArrayList<WjpzSjylxModel>();
			lxbtList.add(asyn);
			String lxid = StringUtils.isEmpty(model.getLxid()) ? (lxbtList != null && lxbtList.size() > 0 ? 
					lxbtList.get(0).getLxid() : "") : model.getLxid();
			model.setLxid(lxid);
			List<HashMap<String, Object>> rsList = service.getDjxqNmwjList(model);
			vs.set("rsList", rsList);
			vs.set("lxbt",lxid);
			vs.set("wjid", model.getWjid());
			vs.set("lxbtList", lxbtList);//表头类型列表
		} catch (Exception e) {
			logException(e);
		}
		return "djxqNmwj";
	}
	
	
	private void setTjjg(List<WjpzModel> jgList,
			List<HashMap<String, String>> titList) {
		//单独增加对是否发放，显示控制的字段
		WjpzModel record = new WjpzModel();
//		record.setZd("djzt");
//		jgList.add(jgList.size()-1, record);
		record = new WjpzModel();
		record.setZd("djzt");
		jgList.add(record);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dm", "djzt");
		map.put("mc", "答卷状态");
		titList.add(map);
		//end
	}
	
	/**问卷答卷详情导出*/
	public String wjdjxqdc(){
		if(StringUtils.isBlank(model.getWjid())){
			return null;
		}
		try {				
			long currentTimeMillis = System.currentTimeMillis();
			
			//导出时需要传的数据Map
			List<Map<String,Object>> dcData = new ArrayList<Map<String,Object>>();
			WjglModel requetWjModel = new WjglModel();
			//1.	查询该问卷的基本信息 //
			WjtjModel requestWjtjModel = service.getModel(model);
			requetWjModel.setWjid(model.getWjid());
			//2.	查询试题列表 //
			List<StglModel> requestStxxList = stglService.getStxxList(requetWjModel);
			
			//3.	查询所有已经答题的用户ID（包括学生，老师，企业用户）//
			List<WjpzSjylxModel> lxbtList = service.getWjyffSjylxList(model);
			for (WjpzSjylxModel wjpzSjylxModel : lxbtList) {
				Map<String , Object> data = new HashMap<String, Object>();
				//类型ID
				String lxid = wjpzSjylxModel.getLxid();
				//类型名称
				String lxmc = wjpzSjylxModel.getLxmc();
				data.put("LXID", lxid);
				data.put("LXMC", lxmc);
				WjpzModel wjpzModelParam = new WjpzModel();
				wjpzModelParam.setLxid(lxid);
				wjpzModelParam.setGnlb(wjBaseService.GNLB_WJDJQKDC);
				List<WjpzModel> gnlbCxjgPzList = null;
				/**
				 * 匿名问卷特殊处理
				 */
				if(StringUtils.equals("anonymous", lxid)){
					gnlbCxjgPzList = new ArrayList<WjpzModel>();
					WjpzModel nmzd = new WjpzModel();
					nmzd.setZd("XM");
					nmzd.setZdmc("姓名");
					gnlbCxjgPzList.add(nmzd);
				}else{
					gnlbCxjgPzList = wjBaseService.getGnlbCxjgPzList(wjpzModelParam);
				}
				
				List<Map<String, String>>[] wjdjqkListAndDrjxxList = service.getWjdjqkList(wjpzSjylxModel, gnlbCxjgPzList, requetWjModel, requestStxxList);
				data.put("ZDS", gnlbCxjgPzList);
				//data.put("DJRXX", wjdjqkListAndDrjxxList[1]);
				data.put("DJXX", wjdjqkListAndDrjxxList[0]);
				dcData.add(data);
			}
			String tempPath = ServletActionContext.getServletContext().getRealPath(ExcelUtils.SYS_TEMP_PATH);
			file = ExcelUtils.createExcel(tempPath, requestWjtjModel, requestStxxList, dcData);
		} catch (Exception e) {
			logException(e);
		}
		return "exportExcel";
	}
	
	@Override
	public WjtjModel getModel() {
		return model;
	}
	public IWjtjService getService() {
		return service;
	}
	public void setService(IWjtjService service) {
		this.service = service;
	}
	public void setModel(WjtjModel model) {
		this.model = model;
	}

	public IWjBaseService getWjBaseService() {
		return wjBaseService;
	}

	public void setWjBaseService(IWjBaseService wjBaseService) {
		this.wjBaseService = wjBaseService;
	}

	public IStglService getStglService() {
		return stglService;
	}

	public void setStglService(IStglService stglService) {
		this.stglService = stglService;
	}

	public IWjglService getWjglService() {
		return wjglService;
	}

	public void setWjglService(IWjglService wjglService) {
		this.wjglService = wjglService;
	}
	
}
