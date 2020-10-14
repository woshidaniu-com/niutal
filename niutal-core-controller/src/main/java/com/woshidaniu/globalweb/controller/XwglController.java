package com.woshidaniu.globalweb.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.BusinessLog;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.dao.entities.XwglModel;
import com.woshidaniu.search.core.SearchParser;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.IXwglService;
import com.woshidaniu.util.xml.BaseDataReader;


/**
 * 
* 
* 类名称：XwglAction
* 类描述：新闻管理
* 创建人：qph
* 创建时间：2012-4-20 
* 修改备注： 
*
*/
@Controller
@RequestMapping(value = "/xtgl/xwgl")
public class XwglController extends BaseController{
	
	private static final String TOKEN_KEY_NAME = "com.woshidaniu.globalweb.controller.XwglController.token";
	
    protected BaseLog baseLog = LogEngineImpl.getInstance();
	 
	@Autowired
	@Qualifier("xwglService")
	protected IXwglService service;

	
	/**
	 * 发布对象、是否列表
	 */
	public void setValueStack(HttpServletRequest request){
		List<HashMap<String, String>> fbdxList = BaseDataReader.getCachedOptionList("fbdx");
		request.setAttribute("fbdxList", fbdxList);

		List<HashMap<String, String>> sfList = BaseDataReader.getCachedOptionList("isNot");
		request.setAttribute("sfList", sfList);
		
		List<HashMap<String, String>> ydqxList = BaseDataReader.getCachedOptionList("ydqx");
		request.setAttribute("ydqxList", ydqxList);
	}
	
	
	/**
	 * 
	* 方法描述: 新闻查询
	* 参数 @return 参数说明
	* 返回类型 String 返回类型
	 */
	@RequiresPermissions("xwgl:cx")
	@RequestMapping(value = "/cxXw")
	public String cxXw(HttpServletRequest request, XwglModel model) {
		try {
			setValueStack(request);
			return "/globalweb/comp/xtgl/xwgl/cxXw";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}
	
	@ResponseBody
	@RequiresPermissions("xwgl:cx")
	@RequestMapping(value = "/getXwxxList")
	public Object getXwxxList(XwglModel model){
		try {
			SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
			List<XwglModel> pagedList = service.getPagedList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	

	/**
	 * 
	* 方法描述: 请求增加新闻页面
	* @return
	 */
	@RequiresPermissions("xwgl:zj")
	@RequestMapping(value = "/zjXw")
	public String zjXw(HttpServletRequest request, XwglModel model) {
		try {
			
			renderTokenAttr(request);
			
			// 生成初始化数据
			model.setSffb("1");
			model.setSfzd("0");
			model.setSfzy("0");
			model.setFbsj(DateUtils.format(DateUtils.DATE_FORMAT_TWO));
			setValueStack(request);
			request.setAttribute("model", model);
			return "/globalweb/comp/xtgl/xwgl/zjXw";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	
	
	/**
	 * 
	 * <p>方法说明：ajax保存新闻<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月25日下午3:20:16<p>
	 * @return
	 */
	@BusinessLog(czmk = "系统管理", czms = "保存通知公告-标题：${model.xwbt}", ywmc = "通知公告", czlx = BusinessType.INSERT)
	@ResponseBody
	@RequiresPermissions("xwgl:zj")
	@RequestMapping(value = "/saveXw")
	public Object saveXw(HttpServletRequest request, XwglModel model,String token) {
		try {
			String tokenInSession = (String)request.getSession().getAttribute(TOKEN_KEY_NAME);
			if(!tokenInSession.equals(token)){
				return MessageKey.SYSTEM_ERROR.getJson();
			}
			User user = getUser();
			model.setFbr(user.getYhm());
			boolean result = service.zjBcXw(model);
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			return key.getJson();
		}  catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	/**
	 * 修改新闻页面
	 * @return
	 */
	@RequestMapping(value = "/xgXw")
	@RequiresPermissions("xwgl:xg")
	public String xgXw(HttpServletRequest request, XwglModel model) {
		try {
			
			renderTokenAttr(request);
			
			XwglModel xwglModel = service.getModel(model);
			request.setAttribute("model", xwglModel);
			setValueStack(request);
			request.setAttribute("fList", Arrays.asList(xwglModel.getFbdxs()));
			return "/globalweb/comp/xtgl/xwgl/xgXw";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}
	
	
	
	
	/**
	 * 
	 * <p>方法说明：ajax修改保存新闻<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月25日下午3:23:00<p>
	 * @return
	 */
	@BusinessLog(czmk = "系统管理", czms = "修改通知公告-编号：${model.xwbh}", ywmc = "通知公告", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequiresPermissions("xwgl:xg")
	@RequestMapping(value = "/modifyXw")
	public Object modifyXw(HttpServletRequest request, XwglModel model,String token) {
		try {
			String tokenInSession = (String)request.getSession().getAttribute(TOKEN_KEY_NAME);
			if(!tokenInSession.equals(token)){
				return MessageKey.SYSTEM_ERROR.getJson();
			}
			boolean result = service.xgBcXw(model);
			MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	/**
	 * 查看新闻
	 * @return
	 */
	@RequiresPermissions("xwgl:ck")
	@RequestMapping(value = "/ckXw")
	public String ckXw(HttpServletRequest request, XwglModel model) {
		try {
			XwglModel xwglModel = service.getModel(model);
			request.setAttribute("model", xwglModel);
			return "/globalweb/comp/xtgl/xwgl/ckXw";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}	
	
	
	/**
	 * 删除新闻
	 * @return
	 */
	@BusinessLog(czmk = "系统管理", czms = "删除通知公告-编号：${ids}", ywmc = "通知公告", czlx = BusinessType.DELETE)
	@ResponseBody
	@RequestMapping(value = "/scXw")
	@RequiresPermissions("xwgl:sc")
	public Object scXw(@RequestParam(required=true)String ids, XwglModel model) {
		try {
			boolean result = service.scXw(ids);
			MessageKey key = result ? MessageKey.DELETE_SUCCESS : MessageKey.DELETE_FAIL;
			return key.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR;
		}
	}
	
	
	/**
	 * 发布新闻
	 * @return
	 */
	@BusinessLog(czmk = "系统管理", czms = "发布通知公告-编号：${ids}", ywmc = "通知公告", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping(value = "/fbXw")
	@RequiresPermissions("xwgl:fb")
	public Object fbXw(@RequestParam(required=true)String ids, XwglModel model) {
		try {
			boolean result = service.xgFbxw(ids);
			MessageKey key = result ? MessageKey.DO_SUCCESS : MessageKey.DO_FAIL;
			return key.getJson(new String[]{"发布"});
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	
	/**
	 * 取消发布新闻
	 * @return
	 */
	@BusinessLog(czmk = "系统管理", czms = "取消发布通知公告-编号：${ids}", ywmc = "通知公告", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping(value = "/qxfbXw")
	@RequiresPermissions("xwgl:qxfb")
	public Object qxfbXw(@RequestParam(required=true)String ids, XwglModel model) {
		try {
			boolean result = service.xgQxfbxw(ids);
	         MessageKey key = result ? MessageKey.DO_SUCCESS : MessageKey.DO_FAIL;
			 return key.getJson(new String[]{"取消发布"});
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	
	/**
	 * 新闻置顶
	 * @return
	 */
	@BusinessLog(czmk = "系统管理", czms = "置顶通知公告-编号：${ids}", ywmc = "通知公告", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping(value = "/zdXw")
	@RequiresPermissions("xwgl:zd")
	public Object zdXw(@RequestParam(required=true)String ids, XwglModel model) {
		try {
			boolean result = service.xgZdxw(ids);
			 MessageKey key = result ? MessageKey.DO_SUCCESS : MessageKey.DO_FAIL;
			 return key.getJson(new String[]{"置顶"});
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	
	
	/**
	 * 取消置顶新闻
	 * @return
	 */
	@BusinessLog(czmk = "系统管理", czms = "取消置顶通知公告-编号：${ids}", ywmc = "通知公告", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequestMapping(value = "/qxzdXw")
	@RequiresPermissions("xwgl:qxzd")
	public Object qxzdXw(@RequestParam(required=true)String ids, XwglModel model) {
		try {
			boolean result = service.xgQxzdXw(ids);
			MessageKey key = result ? MessageKey.DO_SUCCESS : MessageKey.DO_FAIL;
			return key.getJson(new String[] { "取消置顶" });
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	private void renderTokenAttr(HttpServletRequest request){
		String token = (String)request.getSession().getAttribute(TOKEN_KEY_NAME);
		if(token == null){
			token = UUID.randomUUID().toString();
			request.getSession().setAttribute(TOKEN_KEY_NAME,token);			
		}
		
		request.setAttribute("token", token);
	}
}
