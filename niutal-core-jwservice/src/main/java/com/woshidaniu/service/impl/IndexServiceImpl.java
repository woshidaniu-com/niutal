package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.log.User;
import com.woshidaniu.daointerface.IIndexDao;
import com.woshidaniu.entities.AncdModel;
import com.woshidaniu.entities.IndexModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IIndexService;
import com.woshidaniu.util.local.LocaleUtils;
import com.woshidaniu.web.context.WebContext;


/**
 * 
* 
* 类名称：IndexServiceImpl 
* 类描述：首页业务层接口实现类
* 创建人：yjd 
* 创建时间：2012-05-09 上午10:53:39 
* 修改备注： 
* @version 
*
 */
@Service("indexService")
public class IndexServiceImpl extends CommonBaseServiceImpl<IndexModel, IIndexDao> implements IIndexService {
	
	//用户管理dao接口
	@Resource
	private IIndexDao dao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.afterPropertiesSet();
	    super.setDao(dao);   
	}
	
	/**
	 * 
	 * 方法描述: 登录成功获取当前用户的对应角色  即老师
	 * 参数 @param model 登录类
	 * @throws
	 */
	public List<String> cxJsxxLb(User user)  {
		List<String> list=null;
		if("student".equals(user.getYhlx())){
			//学生是没有角色的
			return null;
		}else{
			List<HashMap<String, String>> jsxxs=dao.cxJsxxLb(user);
			if(jsxxs.size() > 0){
				list=new ArrayList<String>();
				for (int i = 0; i < jsxxs.size(); i++) {
					list.add(jsxxs.get(i).get("JSDM"));
				}
			}
		}
		return list;
	}

	@Override
	public List<AncdModel> cxYhgnList(AncdModel model) {
		//通过功能代码判断几级菜单，不是很完美
		 if("7".equals(model.getGnmkdm())){
			 model.setCdjb("3");
		 }else{
			 model.setCdjb("2");
		 }
		model.setLocaleKey(WebContext.getLocale().toString());
		return dao.cxYhgnList(model);
	}

	/**
	 *@描述：查询访客功能list
	 *@创建人:"huangrz"
	 *@创建时间:2014-8-14上午11:04:10
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<AncdModel> cxFkgnList(){
		return dao.cxFkgnList(LocaleUtils.getLocaleKey());
	}
	/**
	 *@描述：根据jsdm查询角色类型代码
	 *@创建人:zou
	 *@创建时间:2016-4-8
	 *@param jsdm
	 *@return
	 */	
	public String getJslxdm(String jsdm) {
		return dao.getJslxdm(jsdm);
	}
	
	public Map<String,String> getYhxxIndex(Map<String,String> map){
		return dao.getYhxxIndex(map);
	}
	
	public boolean hasJzgzp(String jgh){
		return dao.getCountJzgzp(jgh)>0;
	}
	
	public boolean hasXsRxqzp(String xh){
		return dao.getCountXsRxqzp(xh)>0;
	}
	
	public boolean hasXsRxhzp(String xh){
		return dao.getCountXsRxhzp(xh)>0;
	}

 
	
}
