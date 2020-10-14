package com.woshidaniu.globalweb.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.entities.WdyyModel;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.IWdyyService;

/**
 * 
* 
* 类名称：WdyyAction
* 类描述： 我的应用action类
* 创建人：yijd
* 创建时间：2012-5-7 上午17:22:13 
* 修改备注： 
* @version 
*
 */
public class WdyyAction extends CommonBaseAction implements ModelDriven<WdyyModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private IWdyyService service;
	protected WdyyModel model=new WdyyModel();
	protected BaseLog baseLog = LogEngineImpl.getInstance();
    
    public WdyyModel getModel() {
    	model.setUser(getUser());
        return model;
    }
	/**
	 * 我的应用菜单
	 * @return
	 */
	public String cxWdyy(){
		ValueStack vs=getValueStack();
		User user=getUser();
		model.setYhdm(user.getYhm());
		//模型驱动获取不到 url后面传的值
		model.setFjgndm(getRequest().getParameter("fjgndm"));
		try {
			List<WdyyModel> wdyyList=null;
			if("teacher".equals(user.getYhlx())){
				wdyyList=getService().cxLsWdyy(model);//查询老师
			}else if("student".equals(user.getYhlx())){
				wdyyList=getService().cxXsWdyy(model);//查询学生
			}else{
				wdyyList=new ArrayList<WdyyModel>();//防止bug页面错误
			}
			vs.set(DATA, wdyyList);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	public String bcWdyy(){
		ValueStack vs = getValueStack();
		User user=getUser();
		model.setYhdm(user.getYhm());
		//模型驱动获取不到 url后面传的值
		model.setGnmkdm(getRequest().getParameter("gnmkdm"));
		model.setFjgndm(getRequest().getParameter("fjgndm"));
		boolean result;
		try {
			result = getService().insert(model);
			
			if (result) {
                baseLog.insert(user, getText("log.message.ywmc",
                 new String[] {"增加我的应用", "xg_xtgl_wdyygnmkczb"  }),
                "增加我的应用", getText("log.message.czms", new
                String[] { "增加我的应用", "职工号", model.getYhdm() }));
			}
			
			String key = result ? "I99001" : "I99002";
			vs.set(DATA, getText(key));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	public String scWdyy(){
		ValueStack vs = getValueStack();
		User user=getUser();
		model.setYhdm(user.getYhm());
		//模型驱动获取不到 url后面传的值
		model.setGnmkdm(getRequest().getParameter("gnmkdm"));
		model.setFjgndm(getRequest().getParameter("fjgndm"));
		boolean result;
		try {
			result = getService().scWdyy(model);
			// 记操作日志
			if (result) {
                baseLog.insert(user, getText("log.message.ywmc",
                 new String[] {"删除我的应用", "xg_xtgl_wdyygnmkczb"  }),
                "删除我的应用", getText("log.message.czms", new
                String[] { "删除我的应用", "职工号", model.getYhdm() }));
            }
			
			String key = result ? "I99005" : "I99006";
			vs.set(DATA, getText(key));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	public IWdyyService getService() {
		return service;
	}
	public void setService(IWdyyService service) {
		this.service = service;
	}
	public void setModel(WdyyModel model) {
		this.model = model;
	}

}