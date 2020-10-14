package com.woshidaniu.globalweb.action;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.GlobalString;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.dao.entities.XtszModel;
import com.woshidaniu.globalweb.interceptor.POST;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.IJcsjService;
import com.woshidaniu.service.svcinterface.IXtszService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 
 * 
 * 类名称：XtszAction 类描述：系统设置 创建人：qph 创建时间：2012-4-20 修改备注：
 * 
 */
@Controller
@Scope("prototype")
public class XtszAction extends BaseAction implements ModelDriven<XtszModel> {

	private static final long serialVersionUID = 1L;

	private XtszModel model = new XtszModel();
	@Autowired
	private IJcsjService jcsjService;
	@Autowired
	private IXtszService xtszService;
	private BaseLog baseLog = LogEngineImpl.getInstance();

	/**
	 * 界面请求：获得系统设置信息
	 * 
	 * @return
	 */
	public String xtsz() {
		try {
			XtszModel xtszModel = xtszService.getModel("");
			
			if (xtszModel != null){
				BeanUtils.copyProperties(model, xtszModel);
			}
			
			setList();
			return SUCCESS;
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}

	/**
	 * 加载页面数据列表
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void setList() throws Exception {
		ValueStack rs = this.getValueStack();
		List lxRows = xtszService.cxXnlb(null);

		rs.set("curStudyYear", lxRows);

		List xqRows = jcsjService.getJcsjList(GlobalString.JCSJ_XUEQDM);
		rs.set("curStudyScope", xqRows);

		List ndRows = xtszService.cxNdlb(null);

		rs.set("curYear", ndRows);
	}

	/**
	 * 操作请求：更新系统设置
	 * 
	 * @POST 表示该方法只有 post 防止提交的请求才能调用，否则将要被拦截
	 * 
	 * @return
	 */
	@POST
	public String xtszXg() {
		try {
			User user = (User) getSession().getAttribute("user");

			boolean result = xtszService.update(model);

			// 记操作日志
			baseLog.update(user, getText("log.message.ywmc",
					new String[] { "系统设置", "xg_xtgl_xtszb" }),
					"系统管理", "修改系统设置");
			String key = result ? "I99001" : "I99002";
			getValueStack().set("message", getText(key));

			//XtszModel model = xtszService.getModel("");
			//BeanUtils.copyProperties(this.model, model);
			//setList();

			return "xtszXg";

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}

	
	/**
	 * 
	 * <p>方法说明：保存系统设置<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月24日下午3:01:36<p>
	 * @return
	 */
	@POST
	public String saveXtsz() {
		try {
			User user = getUser();

			boolean result = xtszService.update(model);
			MessageKey messageKey = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			getValueStack().set(DATA, messageKey.toString());
			
			// 记操作日志
			baseLog.update(user, getText("log.message.ywmc",
					new String[] { "系统设置", "xg_xtgl_xtszb" }),
					"系统管理", "修改系统设置");
			return DATA;
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}
	
	
	
	public XtszModel getModel() {
		return model;
	}

	public IJcsjService getJcsjService() {
		return jcsjService;
	}

	public void setJcsjService(IJcsjService jcsjService) {
		this.jcsjService = jcsjService;
	}

	public IXtszService getXtszService() {
		return xtszService;
	}

	public void setXtszService(IXtszService xtszService) {
		this.xtszService = xtszService;
	}

}
