package com.woshidaniu.taglibs.views.components;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.constant.BaseConstant;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.entities.AncdModel;
import com.woshidaniu.service.svcinterface.IIndexService;
import com.woshidaniu.web.WebContext;

public abstract class AbstractTaglibsStrutsUIBean extends AbstractCacheableStrutsUIBean  {

	protected Logger LOG = LoggerFactory.getLogger(AbstractTaglibsStrutsUIBean.class);
	protected IIndexService indexService;
	
	public AbstractTaglibsStrutsUIBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
		//从spring容器获取service
		indexService = (IIndexService) (indexService == null ? ServiceFactory.getRealService("indexService", BaseConstant.XXDM) : indexService);
	}

	/**
	 * 
	 *@描述		：
	 *@创建人		: kangzhidong
	 *@创建时间	: Jul 27, 20166:13:49 PM
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	@SuppressWarnings("unchecked")
	public List<AncdModel> getFuncList(){
		String stackKey1 = getStackKey("funcItem");
		String stackKey = getStackKey("funcList");
		Object tmp1 = getStack().findValue(stackKey1);
		Object tmp = getStack().findValue(stackKey);
		AncdModel ancdModel = tmp1 != null ? (AncdModel) tmp1 : null;
		List<AncdModel> funcList = tmp != null ? (List<AncdModel>) tmp: null;
		User user  = WebContext.getUser();
		
		//'gnmkdm':gnmkdm,'dyym':dyym,'gnmkmc':encodeURIComponent(gnmkmc),'sfgnym':sfgnym
		func_code = BlankUtils.isBlank(func_code) ? getRequest().getParameter("gnmkdm") : func_code;
		
		// 设置默认显示类型：0：菜单样式，1：页签样式
		this.addParameter("xslx", "0");
		
		//判断逻辑: 功能代码不为空，Service已定义
		if( !BlankUtils.isBlank(func_code) && !BlankUtils.isBlank(indexService) ){
			ancdModel = new AncdModel();
			ancdModel.setGnmkdm(func_code);
			ancdModel.setYhm(user.getYhm());
			ancdModel.setJsdm(user.getJsdm());
			//为空则表示未初始化
			if(BlankUtils.isBlank(funcList) ){
				funcList  =  indexService.cxYhgnList(ancdModel);
			}
			if(! BlankUtils.isBlank(funcList) ) {
				// 显示类型：0：菜单样式，1：页签样式
				this.addParameter("xslx", StringUtils.getSafeStr(funcList.get(0).getXslx(), "0"));
				if(funcList.size() == 1 || "1".equals(funcList.get(0).getXslx())){
					try {
						AncdModel model =  (AncdModel) BeanUtils.cloneBean(funcList.get(0));
						this.addParameter("ancdModel", model);
					} catch (Exception e) {
						// ignore
						this.addParameter("ancdModel", ancdModel);
					}
				} else if (funcList.size() > 1){
					try {
						AncdModel model =  (AncdModel) BeanUtils.cloneBean(funcList.get(1));
						this.addParameter("ancdModel", model);
					} catch (Exception e) {
						// ignore
						this.addParameter("ancdModel", ancdModel);
					}
				} else {
					try {
						AncdModel model =  (AncdModel) BeanUtils.cloneBean(funcList.get(0));
						this.addParameter("ancdModel", model);
					} catch (Exception e) {
						// ignore
						this.addParameter("ancdModel", ancdModel);
					}
				}
			} else {
				this.addParameter("ancdModel", ancdModel);
			}
		}
		getStack().set(stackKey, funcList);
		if(!BlankUtils.isBlank(funcList)){
		}
		return funcList;
	}
	
}
