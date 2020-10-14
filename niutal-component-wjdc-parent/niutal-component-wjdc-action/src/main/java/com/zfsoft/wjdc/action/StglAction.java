package com.woshidaniu.wjdc.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.exception.BusinessException;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.wjdc.dao.entites.StglModel;
import com.woshidaniu.wjdc.dao.entites.WjffglModel;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.dao.entites.XxglModel;
import com.woshidaniu.wjdc.service.svcinterface.IStglService;
import com.woshidaniu.wjdc.service.svcinterface.IWjffglService;
import com.woshidaniu.wjdc.service.svcinterface.IWjglService;

/**
 * 试题管理
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StglAction extends BaseAction implements ModelDriven<StglModel>{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IStglService service;
	@Autowired
	private IWjglService wjglService;
	@Autowired
	private IWjffglService wjffglService;
	private StglModel model;
	private WjglModel wjModel=new WjglModel();
	private BaseLog baseLog = LogEngineImpl.getInstance();
	
	/**
	 * 编辑试题
	 * @return
	 */
	public String editStxx(){
		try {
			WjglModel wjm=wjglService.getModel(wjModel.getWjid());
			//wjm.setDjrid(wjModel.getDjrid());//设置答卷人id
			BeanUtils.copyProperties(wjModel, wjm);
		} catch (Exception e) {
			logException(e);
		}
		return "editStxx";
	}
	
	/**
	 * 保存编辑的试题
	 * @return
	 */
	public String saveEditStxx(){
		HttpServletRequest request = getRequest();
		try {
			boolean result = service.saveEditStxx(request, wjModel);
			String key = result ? "I99001" : "I99002";
			getValueStack().set("result", getText(key));
			if (result) {
				baseLog.update(
						getUser(),
						getText("log.message.ywmc", new String[] { "问卷管理",
								"ZFXG_WJDC_WJSTB" }),
						"问卷信息管理",
						getText("log.message.czms", new String[] { "编辑试题",
								"问卷id", wjModel.getWjid()}));
			}
		} catch (Exception e) {
			logException(e);
		}
		return editStxx();
	}
	
	/**
	 * 获取问卷试题相关信息
	 * @return
	 */
	public String getWjStxxList(){
		try {
			List<StglModel> stList=service.getStxxAndStdlXxList(wjModel);
			List<XxglModel> xxList=service.getStXxxxList(wjModel);
			HashMap<String,Object> rs=new HashMap<String, Object>();
			rs.put("tmxxs", stList);
			rs.put("xxxxs", xxList);
			getValueStack().set(DATA, rs);
			return DATA;
		} catch (Exception e) {
			logException(e);
		}
		return "";
	}
	
	/**
	 * 保存问卷答案
	 * @return
	 */
	public String saveWjDa(){
		HttpServletRequest request = getRequest();
		try {
			wjModel.setDjrid(getUser().getYhm());
			WjglModel wjglModel=service.getYhdjxx(wjModel);
			//如果是游客那么就自动生成一个djrid
			if(wjglModel!=null&&"已答卷".equals(wjglModel.getDjzt())){
				getValueStack().set("result", "该问卷已作答，不可重复提交！");
			}else{
				String result = service.saveWjDa(request, wjModel);
				if("I99001".equals(result) || "I99002".equals(result)){
					if("I99001".equals(result)){
						baseLog.insert(
								getUser(),
								getText("log.message.ywmc", new String[] { "问卷管理",
										"ZFXG_WJDC_WJHDB" }),
								"我的问卷",
								getText("log.message.czms", new String[] { "回答问卷",
										"问卷id", wjModel.getWjid() }));
					}
					result = getText(result);
				}
				getValueStack().set("result",result );
			}
		} catch (Exception e) {
			logException(e);
		}
		return yhdj();
	}
	
	/**
	 * 获取问卷答案列表
	 * @return
	 */
	public String getWjDaList(){
		try {
			List<XxglModel> wjdaList=service.getWjDaList(wjModel);
			getValueStack().set(DATA, wjdaList);
			return DATA;
		} catch (Exception e) {
			logException(e);
		}
		return "";
	}
	
	/**
	 * 显示试题_预览
	 * @return
	 */
	public String showStxx(){
		try {
			WjglModel wjm=wjglService.getModel(wjModel.getWjid());
//			if(wjModel.getDjrid()==null||"".equals(wjModel.getDjrid())){
//				wjm.setDjrid(getUser().getYhm());//设置答卷人id
//			}
			BeanUtils.copyProperties(wjModel, wjm);
		} catch (Exception e) {
			logException(e);
		}
		return "showStxx";
	}
	
	/**
	 * 用户答卷
	 * @return
	 */
	public String yhdj(){
		HttpServletRequest request = getRequest();
		try {
			String source = request.getParameter("source");
			if(StringUtils.isNotBlank(source)){
				getValueStack().set("source", source);
			}
			WjglModel wjm=wjglService.getModel(wjModel.getWjid());
			wjm.setDjrid(wjModel.getDjrid());
			if(wjm.getDjrid()==null||"".equals(wjm.getDjrid())){
				wjm.setDjrid(getUser().getYhm());//设置答卷人id
			}
			
			wjModel.setDjrid(wjm.getDjrid());
			WjglModel yhdjxx=service.getYhdjxx(wjModel);
			wjm.setDjzt(yhdjxx.getDjzt());

			
			//判断该问卷是否过期//
			String gqsj = wjm.getGqsj();
			if(StringUtils.isNotBlank(gqsj)){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String currentDate = df.format(new Date());
				if(log.isInfoEnabled()){
					log.info("服务器时间：{"+currentDate+"}，问卷过期时间：{"+gqsj+"}");
				}
				if(currentDate.compareTo(gqsj) > 0){
					wjm.setSfgq("1");
				}else{
					wjm.setSfgq("0");
				}
			}else{
				wjm.setSfgq("0");
			}
			
			//判断该问卷是否过期//
			
			BeanUtils.copyProperties(wjModel, wjm);
		} catch (Exception e) {
			logException(e);
		}
		return "showStxx";
	}

	
	/*****************************匿名问卷实现**************************************/
	/**
	 * 获取匿名答卷
	 * @return
	 * @throws Throwable 
	 */
	public String nmdj() throws Throwable{
		//是否允许访问，该方法只限访问匿名问卷并且状态为'运行'
		boolean auth = false;
		try {
			WjglModel wjm=wjglService.getModel(wjModel.getWjid());
			if(wjm == null){
				exception = new BusinessException("查询的问卷不存在！");
				throw exception;
			}
			String fblx = wjm.getFblx();
			String wjzt = wjm.getWjzt();
			auth = (StringUtils.equals(fblx, "1") && StringUtils.equals("运行", wjzt)) ? true : false;
			BeanUtils.copyProperties(wjModel, wjm);
		} catch (Exception e) {
			logException(e);
		}
		//如果问卷不是运行状态
		if(!auth){
			exception = new BusinessException("该问卷未开放，暂无法查看！");
			throw exception;
		}
		
		return"showStxx";
	}
	
	/**
	 * 获取匿名问卷试题信息
	 * @return
	 */
	public String getNmWjStxxList(){
		try {
			List<StglModel> stList=service.getStxxAndStdlXxList(wjModel);
			List<XxglModel> xxList=service.getStXxxxList(wjModel);
			HashMap<String,Object> rs=new HashMap<String, Object>();
			rs.put("tmxxs", stList);
			rs.put("xxxxs", xxList);
			getValueStack().set(DATA, rs);
			return DATA;
		} catch (Exception e) {
			logException(e);
		}
		return FAILED;
	}
	
	/**
	 * 保存匿名答卷答题信息
	 * @return
	 */
	public String saveNmWjDa(){
		HttpServletRequest request = getRequest();
		try {
			//给匿名答卷人生成一个编号，编号规则:'nmdjr-' 加一个guid。
			String djrId = "nmdjr-" + UniqID.getInstance().getUniqIDHash().toLowerCase();
			wjModel.setDjrid(djrId);
			//给问卷分发表添加一条匿名数据
			WjffglModel wjffglModel = new WjffglModel();
			wjffglModel.setWjid(wjModel.getWjid());
			wjffglModel.setLxid("anonymous");
			wjffglModel.setZjz(djrId);
			wjffglModel.setDjzt("已答卷");
			String result  = service.zjNmwjDjxx(request, wjffglModel, wjModel);
			//boolean insertResult = wjffglService.insert(wjffglModel);
			//给问卷分发表添加一条匿名数据
				//String result = service.saveWjDa(request, wjModel);
			if("I99001".equals(result) || "I99002".equals(result)){
				if("I99001".equals(result)){
					baseLog.insert(
							getUser(),
							getText("log.message.ywmc", new String[] { "问卷管理",
									"ZFXG_WJDC_WJHDB" }),
							"我的问卷",
							getText("log.message.czms", new String[] { "回答问卷",
									"问卷id", wjModel.getWjid() }));
				}
				result = getText(result);
			}
			getValueStack().set("data",result );
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/******************************匿名问卷实现*************************************/
	
	public IStglService getService() {
		return service;
	}

	public void setService(IStglService service) {
		this.service = service;
	}

	@Override
	public StglModel getModel() {
		return model;
	}

	public WjglModel getWjModel() {
		return wjModel;
	}

	public void setWjModel(WjglModel wjModel) {
		this.wjModel = wjModel;
	}

	public IWjglService getWjglService() {
		return wjglService;
	}

	public void setWjglService(IWjglService wjglService) {
		this.wjglService = wjglService;
	}

	public IWjffglService getWjffglService() {
		return wjffglService;
	}

	public void setWjffglService(IWjffglService wjffglService) {
		this.wjffglService = wjffglService;
	}

	
}
