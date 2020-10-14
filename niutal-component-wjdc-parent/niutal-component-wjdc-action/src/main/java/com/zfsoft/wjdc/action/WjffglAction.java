package com.woshidaniu.wjdc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.wjdc.dao.entites.WjffglModel;
import com.woshidaniu.wjdc.dao.entites.WjpzModel;
import com.woshidaniu.wjdc.dao.entites.WjpzSjylxModel;
import com.woshidaniu.wjdc.service.svcinterface.IWjBaseService;
import com.woshidaniu.wjdc.service.svcinterface.IWjffglService;

/**
 * 问卷发放管理ACTION
 * @author Administrator
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WjffglAction extends BaseAction implements ModelDriven<WjffglModel>  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WjffglModel model = new WjffglModel();
	@Autowired
	private IWjffglService iWjffglService;
	@Autowired
	private IWjBaseService iWjBaseService;
	private BaseLog baseLog = LogEngineImpl.getInstance();
	private static String _DELETE = "del";
	private static String _FF = "ff";//是否发布：分发
	private static String _QXFF = "qxff";//是否发布：取消分发
	private static String _ALL = "all";
	/**
	 * 查询问卷发放信息
	 * @return
	 */
	public String cxWjffxx() {
		ValueStack vs = getValueStack();
		
		//查询数据
		if (QUERY.equals(model.getDoType())) {
			QueryModel queryModel = model.getQueryModel();
			try {
				queryModel.setItems(iWjffglService.getPagedList(model));
			} catch (Exception e) {
				logException(e);
			}
			vs.set(DATA, queryModel);
			return DATA;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询分发问卷列表
	 * @return
	 */
	public String cxFfwjxx() {
		ValueStack vs = getValueStack();
		User user = getUser();
		try {
			List<WjpzSjylxModel> lxbtList = iWjBaseService.getWjpzSjylxList();
			String lxid = StringUtils.isEmpty(model.getLxid()) ? (lxbtList != null && lxbtList.size() > 0 ? 
					lxbtList.get(0).getLxid() : "") : model.getLxid();
			model.setLxid(lxid);
			
			//删除问卷对象
			plscYffwjdxxx(vs, user);
			
			//发布问卷对象标志
			xgWjffdxbz(vs, user);
			
			//取消发布问卷对象标志
			xgWjffdxbzQxff(vs,user);
			
			//获取查询条件，结果
			WjpzModel wjpzModel = new WjpzModel();
			wjpzModel.setLxid(lxid);
			wjpzModel.setGnlb(IWjBaseService.GNLB_WJFF);
			List<WjpzModel> tjList = iWjBaseService.getGnlbCxtjPzList(wjpzModel);	
			List<WjpzModel> jgList = iWjBaseService.getGnlbCxjgPzList(wjpzModel);
			WjpzSjylxModel sjyModel = iWjBaseService.getWjpzSjylxModel(lxid);
			
			Map<String, String> cxtjMap = iWjBaseService.formatMap(getRequest().getParameterMap());
			
			//查询结果展现
			List<HashMap<String, String>> titList = iWjBaseService.getWjdxcxbtList(jgList);
			List<HashMap<String, String>> rsList = iWjBaseService.getYffWjdxCxjgList(model, cxtjMap, jgList, sjyModel);
			setTjjg(jgList, titList);//特殊处理查询条件及结果
			List<String[]> rs = iWjBaseService.getWjdxCxjgList(rsList, jgList, sjyModel.getZj());//格式化输出结果
			vs.set("titList", titList);
			vs.set("rsList", rs);
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
			vs.set("valueStr", iWjBaseService.formatMaptoStr(cxtjMap));
			vs.set("lxbtList", lxbtList);//表头类型列表
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	private void setTjjg(List<WjpzModel> jgList,
			List<HashMap<String, String>> titList) {
		//单独增加对是否发放，显示控制的字段
		WjpzModel record = new WjpzModel();
		record.setZd("disabled");
		jgList.add(0, record);
		record = new WjpzModel();
		record.setZd("djzt");
		jgList.add(record);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dm", "djzt");
		map.put("mc", "答卷状态");
		titList.add(map);
		//end
	}

	/**
	 * 修改问卷发放对象标志
	 * @param vs
	 * @param user
	 * @throws Exception
	 */
	private void xgWjffdxbz(ValueStack vs, User user) throws Exception {
		if (_FF.equalsIgnoreCase(model.getDoType())) {
			boolean result = iWjffglService.xgWjffdxbz(model);
			if (result) {
				baseLog.update(
						user,
						getText("log.message.ywmc",
								new String[] { "问卷调查管理","zfxg_wjdc_djrffb" }),
						"问卷分发",
						getText("log.message.czms", new String[] { "发布问卷分发对象",
								"问卷ID+类型ID", model.getWjid()+model.getLxid() }));
			}
			String key = result ? "I99001" : "I99002";
			vs.set("result", getText(key));
		}
	}
	
	/**
	 * 修改问卷发放对象标志  取消分发
	 * @param vs
	 * @param user
	 * @throws Exception
	 */
	private void xgWjffdxbzQxff(ValueStack vs, User user) throws Exception {
		if (_QXFF.equalsIgnoreCase(model.getDoType())) {
			boolean result = iWjffglService.xgWjffdxbzWff(model);
			if (result) {
				baseLog.update(
						user,
						getText("log.message.ywmc",
								new String[] { "问卷调查管理","zfxg_wjdc_djrffb" }),
						"问卷分发",
						getText("log.message.czms", new String[] { "取消发布问卷分发对象",
								"问卷ID+类型ID", model.getWjid()+model.getLxid() }));
			}
			String key = result ? "I99001" : "I99002";
			vs.set("result", getText(key));
		}
	}

	/**
	 * 批量删除已发放问卷对象信息
	 * @param vs
	 * @param user
	 * @throws Exception
	 */
	private void plscYffwjdxxx(ValueStack vs, User user) throws Exception {
		if (_DELETE.equalsIgnoreCase(model.getDoType())) {
			boolean result = iWjffglService.plscYffwjffdx(model);
			if (result) {
				baseLog.insert(
						user,
						getText("log.message.ywmc",
								new String[] { "问卷调查管理","zfxg_wjdc_djrffb" }),
						"问卷分发",
						getText("log.message.czms", new String[] { "删除问卷分发对象",
								"问卷ID+类型ID", model.getWjid()+model.getLxid() }));
			}
			String key = result ? "I99005" : "I99006";
			vs.set("result", getText(key));
		}
	}
	
	/**
	 * 增加问卷发放信息
	 * @return
	 */
	public String zjWjffxx() {
		ValueStack vs = getValueStack();
		User user = getUser();
		try {
			String lxid = model.getLxid();
			
			//获取查询条件，结果
			WjpzModel wjpzModel = new WjpzModel();
			wjpzModel.setLxid(lxid);
			wjpzModel.setGnlb(IWjBaseService.GNLB_WJFF);
			List<WjpzModel> tjList = iWjBaseService.getGnlbCxtjPzList(wjpzModel);	
			List<WjpzModel> jgList = iWjBaseService.getGnlbCxjgPzList(wjpzModel);
			WjpzSjylxModel sjyModel = iWjBaseService.getWjpzSjylxModel(lxid);
			Map<String, String> cxtjMap = iWjBaseService.formatMap(getRequest().getParameterMap());
			//保存问卷对象数据
			bcWjffxx(vs, user, model, cxtjMap,sjyModel);
			//查询结果展现
			List<HashMap<String, String>> titList = iWjBaseService.getWjdxcxbtList(jgList);
			List<HashMap<String, String>> rsList = iWjBaseService.getWjdxCxjgList(model,cxtjMap, jgList, sjyModel);
			List<String[]> list=iWjBaseService.getWjdxCxjgList(rsList, jgList, sjyModel.getZj());
			vs.set("titList", titList);
			vs.set("rsList", list);
			vs.set("sjyModel", sjyModel);
			vs.set("tjList", tjList);		
			vs.set("lxbt",lxid);
			vs.set("wjid", model.getWjid());
			//将MAP转换为str查询条件值
			vs.set("valueStr", iWjBaseService.formatMaptoStr(cxtjMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return SUCCESS;
	}

	/**
	 * 保存问卷分发对象
	 * @param vs
	 * @param user
	 * @throws Exception
	 */
	private void bcWjffxx(ValueStack vs, User user, WjffglModel model, Map<String, String> map, WjpzSjylxModel sjyModel) throws Exception {
		if (OPER_SAVE.equalsIgnoreCase(model.getDoType())) {
			boolean result = false;
			//根据查询条件进行分发
			if (_ALL.equalsIgnoreCase(getRequest().getParameter("dx"))) {
				result = iWjffglService.bcWjdxBytj(sjyModel, map, model);
			} else {//根据选择的记录进行分发
				result = iWjffglService.bcWjffdx(model);
			}
			if (result) {
				baseLog.insert(
						user,
						getText("log.message.ywmc",
								new String[] { "问卷调查管理","zfxg_wjdc_djrffb" }),
						"问卷分发",
						getText("log.message.czms", new String[] { "保存问卷分发对象",
								"问卷ID+类型ID", model.getWjid()+model.getLxid() }));
			}
			String key = result ? "I99001" : "I99002";
			vs.set("result", getText(key));
		}
	}
	
	public WjffglModel getModel() {
		return model;
	}

	public IWjffglService getiWjffglService() {
		return iWjffglService;
	}

	public void setiWjffglService(IWjffglService iWjffglService) {
		this.iWjffglService = iWjffglService;
	}

	public IWjBaseService getiWjBaseService() {
		return iWjBaseService;
	}

	public void setiWjBaseService(IWjBaseService iWjBaseService) {
		this.iWjBaseService = iWjBaseService;
	}


	
}
