package com.woshidaniu.globalweb.action;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.annotation.HtmlEncodeAnotationHandler;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.dao.entities.XwglModel;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.IXwglService;
import com.woshidaniu.util.xml.BaseDataReader;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


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
@Scope("prototype")
public class XwglAction extends BaseAction implements ModelDriven<XwglModel>{
	
	protected static final long serialVersionUID = 1L;
    protected BaseLog baseLog = LogEngineImpl.getInstance();
	 
	protected XwglModel model = new XwglModel();
	
	@Autowired
	@Qualifier("xwglService")
	protected IXwglService service;
	

	public void setService(IXwglService service) {
		this.service = service;
	}

	public XwglModel getModel() {
		return model;
	}

	
	
	/**
	 * 发布对象、是否列表
	 */
	public void setValueStack(){
		ValueStack vs = getValueStack();
		List<HashMap<String, String>> fbdxList = BaseDataReader.getCachedOptionList("fbdx");
		vs.set("fbdxList", fbdxList);

		List<HashMap<String, String>> sfList = BaseDataReader.getCachedOptionList("isNot");
		vs.set("sfList", sfList);
	}
	
	
	/**
	 * 
	* 方法描述: 新闻查询
	* 参数 @return 参数说明
	* 返回类型 String 返回类型
	 */
	public String cxXw() {
		try {
			if (QUERY.equals(model.getDoType())){
				QueryModel queryModel = model.getQueryModel();
				List<XwglModel> pagedList = service.getPagedList(model);
				HtmlEncodeAnotationHandler.handle(pagedList);
				queryModel.setItems(pagedList);
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			setValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "cxXw";
	}
	

	/**
	 * 
	* 方法描述: 请求增加新闻页面
	* @return
	 */
	public String zjXw() {
		// 生成初始化数据
		model.setSffb("1");
		model.setSfzd("0");
		model.setSfzy("0");
		model.setFbsj(DateUtils.format(DateUtils.DATE_FORMAT_TWO));
		
		setValueStack();
		return "zjXw";
	}

	/**
	 * 保存新增加闻
	 * @return
	 */
	@Deprecated
	public String zjBcXw() {
		try {
			User user = getUser();
			model.setFbr(user.getYhm());
			boolean result = service.zjBcXw(model);

			if (result) {
                baseLog.insert(user, getText("log.message.ywmc",
                 new String[] {"新闻发布", "xg_xtgl_xwfbb"  }),
                "新闻发布", getText("log.message.czms", new
                String[] { "新闻发布", "职工号", model.getFbr() }));
            }
			
			String key = result ? "I99001" : "I99002";
			getValueStack().set("message", getText(key));
		}  catch (Exception e) {
			logException(e);
			return ERROR;
		}
		setValueStack();
		return "zjXw";
	}
	
	
	/**
	 * 
	 * <p>方法说明：ajax保存新闻<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月25日下午3:20:16<p>
	 * @return
	 */
	public String saveXw() {
		try {
			User user = getUser();
			model.setFbr(user.getYhm());
			boolean result = service.zjBcXw(model);

			if (result) {
                baseLog.insert(user, getText("log.message.ywmc",
                 new String[] {"新闻发布", "xg_xtgl_xwfbb"  }),
                "新闻发布", getText("log.message.czms", new
                String[] { "新闻发布", "职工号", model.getFbr() }));
            }
			MessageKey key = result ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL;
			getValueStack().set(DATA,key.toString());
		}  catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	/**
	 * 修改新闻页面
	 * @return
	 */
	public String xgXw() {
		try {
			XwglModel xwglModel = service.getModel(model);
			if (xwglModel != null){
				BeanUtils.copyProperties(model, xwglModel);
			}
			setValueStack();
			getValueStack().set("fList", Arrays.asList(model.getFbdxs()));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "xgXw";
	}
	
	
	/**
	 * 保存修改新闻
	 * @return
	 */
	@Deprecated
	public String xgBcXw() {
		try {
			User user = getUser();

			XwglModel model = new XwglModel();
			this.model.setFbnr(this.model.getXwnr());
			BeanUtils.copyProperties(model, this.model);
			boolean result = service.xgBcXw(model);

			if (result) {
                baseLog.insert(user, getText("log.message.ywmc",
                 new String[] {"新闻发布", "xg_xtgl_xwfbb"  }),
                "新闻发布", getText("log.message.czms", new
                String[] { "修改新闻", "新闻编号", model.getXwbh() }));
            }
			String key = result ? "I99001" : "I99002";
			getValueStack().set(DATA, getText(key));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		setValueStack();
		return DATA;
	}
	
	
	/**
	 * 
	 * <p>方法说明：ajax修改保存新闻<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月25日下午3:23:00<p>
	 * @return
	 */
	public String modifyXw() {
		try {
			User user = getUser();

			XwglModel model = new XwglModel();
			BeanUtils.copyProperties(model, this.model);
			boolean result = service.xgBcXw(model);

			if (result) {
                baseLog.insert(user, getText("log.message.ywmc",
                 new String[] {"新闻发布", "xg_xtgl_xwfbb"  }),
                "新闻发布", getText("log.message.czms", new
                String[] { "修改新闻", "新闻编号", model.getXwbh() }));
            }
			MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
			getValueStack().set(DATA, key.toString());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	/**
	 * 查看新闻
	 * @return
	 */
	public String ckXw() {
		try {

			XwglModel model = service.getModel(this.model);

			try {
				BeanUtils.copyProperties(this.model, model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "ckXw";
	}	
	
	
	/**
	 * 删除新闻
	 * @return
	 */
	public String scXw() {
		try {
			User user = getUser();
			String ids = getRequest().getParameter("ids");
			boolean result = service.scXw(ids);
			
			if (result) {
                baseLog.insert(user, getText("log.message.ywmc",
                 new String[] {"新闻发布", "xg_xtgl_xwfbb"  }),
                "新闻发布", getText("log.message.czms", new
                String[] {"批量删除新闻","新闻编号",ids }));
            }
			
			getValueStack().set(DATA, result ? getText("I99005") : getText("I99006"));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	/**
	 * 发布新闻
	 * @return
	 */
	public String fbXw() {
		try {
			User user = getUser();
			String ids = getRequest().getParameter("ids");
			boolean result = service.xgFbxw(ids);
			if (result) {
                baseLog.insert(user, getText("log.message.ywmc",
                 new String[] {"新闻发布", "xg_xtgl_xwfbb"  }),
                "新闻发布", getText("log.message.czms", new
                String[] {"批量发布新闻", "新闻编号", ids }));
            }
			
			String key = result ? "I99007" : "I99008";
			getValueStack().set(DATA, getText(key,new String[]{"发布"}));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	
	/**
	 * 取消发布新闻
	 * @return
	 */
	public String qxfbXw() {
		try {
			User user = getUser();
			String ids = getRequest().getParameter("ids");

			boolean result = service.xgQxfbxw(ids);
			
	         if (result) {
	                baseLog.insert(user, getText("log.message.ywmc",
	                 new String[] {"新闻发布", "xg_xtgl_xwfbb"  }),
	                "新闻发布", getText("log.message.czms", new
	                String[] {"取消发布新闻","新闻编号",ids }));
	            }
			String key = result ? "I99007" : "I99008";
			getValueStack().set(DATA, getText(key,new String[]{"取消发布"}));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	
	/**
	 * 新闻置顶
	 * @return
	 */
	public String zdXw() {
		try {
			User user = getUser();
			String ids = getRequest().getParameter("ids");
			boolean result = service.xgZdxw(ids);
			// 记操作日志
			 if (result) {
                 baseLog.insert(user, getText("log.message.ywmc",
                  new String[] {"新闻发布", "xg_xtgl_xwfbb"  }),
                 "新闻发布", getText("log.message.czms", new
                 String[] {"批量置顶新闻","新闻编号",ids}));
             }
			 
			String key = result ? "I99007" : "I99008";
			getValueStack().set(DATA, getText(key, new String[]{"置顶"}));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}

	
	
	/**
	 * 取消置顶新闻
	 * @return
	 */
	public String qxzdXw() {
		try {
			User user = getUser();
			String ids = getRequest().getParameter("ids");
			boolean result = service.xgQxzdXw(ids);

			  if (result) {
	                 baseLog.insert(user, getText("log.message.ywmc",
	                  new String[] {"新闻发布", "xg_xtgl_xwfbb"  }),
	                 "新闻发布", getText("log.message.czms", new
	                 String[] {"取消置顶新闻","新闻编号",ids}));
	             }
			String key = result ? "I99007" : "I99008";
			getValueStack().set(DATA, getText(key,new String[]{"取消置顶"}));
		}catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
}
