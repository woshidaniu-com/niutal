package com.woshidaniu.taglibs.views.components;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.util.TextProviderHelper;
import org.apache.struts2.views.annotations.StrutsTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.entities.AncdModel;
import com.woshidaniu.service.svcinterface.IAncdService;
import com.woshidaniu.web.WebContext;

/** strutsTag注解指明了该UIBean的名字和Tag类的类名 */
@StrutsTag(name = "toolbar", tldTagClass = "com.woshidaniu.taglibs.views.tags.ToolbarTag", description = "生成页面功能工具栏")
public class ToolbarComponent extends AbstractTaglibsStrutsUIBean {
	
	protected static Logger LOG = LoggerFactory.getLogger(ToolbarComponent.class);
	// 数据集查询Service接口
	protected IAncdService ancdService;
		
	public ToolbarComponent(ValueStack stack,
			HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
		//从spring容器获取service
		ancdService = (IAncdService) (ancdService == null ? ServiceFactory.getService("ancdService") : ancdService);
	}

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "tag-toolbar";
	}

	
	@Override
	public boolean start(Writer writer) {
		//调用父级方法
		super.start(writer);
		if(!BlankUtils.isBlank(getFunc_code())){
			
			User user  			   = WebContext.getUser();
			List<AncdModel>  btn_list  = ancdService.cxButtonsList(user, getFunc_code());
			if(!BlankUtils.isBlank(btn_list)){
				this.addParameter("btn_list", btn_list);
			}else{
				this.addParameter("unauthorized", TextProviderHelper.getText("i18n.unauthorized", "", getStack()));
			}
			/*StringBuffer buttons   = new StringBuffer();
			buttons.append("<div class=\"btn-toolbar\" role=\"toolbar\" style=\"float:right;\">");
			buttons.append("<div class=\"btn-group\" id=\"but_ancd\">");
			if(!BlankUtils.isBlank(list)){
				String path =  null;
				List<String> czdmList  = new ArrayList<String>();
				for(int i=0;i<list.size();i++){
					AncdModel _model  = list.get(i);
					if("1".equals(_model.getSfxs())){
						if(!BlankUtils.isBlank(czdms)){
							for (String czdm : czdms) {
								if( _model.getCzdm().equals(czdm)){
									buttons.append(_model.getButton());
									break;
								}
							}
						}else{
							buttons.append(_model.getButton());
						}
					}
					czdmList.add(_model.getCzdm());
					if(i==0){
						path = _model.getPath();
					}
				}
				
				session.setAttribute(WebRequestUtils.getFuncSessionKey(path, user.getJsdm()), czdmList);
			}else{
				ValueStack stack = ActionContext.getContext().getValueStack();
				buttons.append(TextProviderHelper.getText("i18n.unauthorized", "", stack));
			}
			buttons.append(" </div> </div>");
			 */
		}
		return false;
	}

}
