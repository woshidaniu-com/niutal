package com.woshidaniu.common.template.directive;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.entities.AncdModel;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.service.svcinterface.ISystemConfigService;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：Freemarker工具--加载功能按钮
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年9月2日下午4:43:02
 */
@Component("buttonsDirective")
public class ButtonsDirective implements TemplateDirectiveModel {
	
	private static final Logger log = LoggerFactory.getLogger(ButtonsDirective.class);

	@Autowired
	private IJsglService jsglService;
	
	@Autowired
	private ISystemConfigService systemConfigService;

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		
		TemplateModel paramValue = (TemplateModel) params.get("gnmkdm");
		String gnmkdm = ((SimpleScalar)paramValue).getAsString();
		if(StringUtils.hasText(gnmkdm)){
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<AncdModel> ancdList = this.getAncdModelList(user, gnmkdm);
			
			TemplateModel template = new BeansWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).build().wrap(ancdList);
			env.setVariable("ancdList",template);
		} else{
			TemplateModel template = new BeansWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).build().wrap(null);
			env.setVariable("ancdList",template);
		}
		
		if (body != null) {  
            body.render(env.getOut());  
        } 
	}
	
	private List<AncdModel> getAncdModelList(User user,String gnmkdm){
		
		List<AncdModel> ancdList = jsglService.getButtonList(user.getJsdm(), user.getYhm(), gnmkdm);
		
		//多角色上下文环境下，追加其他角色的功能
		if(this.systemConfigService.isEnableMutileRoleContext()){
			
			Set<String> roleSet = new HashSet<String>(user.getJsdms());
			roleSet.remove(user.getJsdm());
			
			int appendTimes = 0;
			
			for(String role : roleSet){
				List<AncdModel> otherRoleAncdList = this.jsglService.getButtonList(role, user.getYhm(), gnmkdm);
				if(!otherRoleAncdList.isEmpty()){
					appendTimes ++;
					ancdList.addAll(otherRoleAncdList);
				}
			}
			if(appendTimes > 0){
				process(ancdList);
			}
		}
		return ancdList;
	}

	private void process(List<AncdModel> list){
		
		processDistinct(list);
		
		processSort(list);
	}
	
	private void processDistinct(List<AncdModel> list){
		
		Set<String> set = new HashSet<String>();
		
		Iterator<AncdModel> it = list.iterator();
		while(it.hasNext()){
			AncdModel ancdModel = it.next();
			String czdm = ancdModel.getCzdm();
			if(!set.contains(czdm)){
				set.add(czdm);
			}else{
				it.remove();
			}
		}
	}
	
	private void processSort(List<AncdModel> list){
		
		@SuppressWarnings("unchecked")
		List<Comparator<AncdModel>> comparatorList = Arrays.asList(//
				new XssxComparator(),//
				new CzmcComparator()//
		);//
		Comparator<AncdModel> comparator = new ComponistedComparator(comparatorList);

		list.sort(comparator);
	}

	/**
	 * @author 1571
	 */
	private static class CzmcComparator implements Comparator<AncdModel>{
		
		@Override
		public int compare(AncdModel o1, AncdModel o2) {
			String k1 = o1.getCzmc();
			String k2 = o2.getCzmc();
			if(StringUtils.isEmpty(k1) && StringUtils.isEmpty(k1)){
				return 0;
			}else if(StringUtils.isNotEmpty(k1) && StringUtils.isEmpty(k2)){
				return 1;
			}else if(StringUtils.isEmpty(k1) && StringUtils.isNotEmpty(k2)){
				return -1;
			}else{
				return k1.compareTo(k2);
			}
		}
	}
	
	/**
	 * @author 1571
	 */
	private static class XssxComparator implements Comparator<AncdModel>{

		@Override
		public int compare(AncdModel o1, AncdModel o2) {
			String k1 = o1.getXssx();
			String k2 = o2.getXssx();
			if(StringUtils.isEmpty(k1) && StringUtils.isEmpty(k1)){
				return 0;
			}else if(StringUtils.isNotEmpty(k1) && StringUtils.isEmpty(k2)){
				return 1;
			}else if(StringUtils.isEmpty(k1) && StringUtils.isNotEmpty(k2)){
				return -1;
			}else{
				int n1 = Integer.MAX_VALUE;
				try{
					n1 = Integer.parseInt(k1);				
				}catch (Exception e) {
					log.warn("can't parse string[{}] to number",k1,e);
				}
				int n2 = Integer.MAX_VALUE;
				try{
					n2 = Integer.parseInt(k2);
				}catch (Exception e) {
					log.warn("can't parse string[{}] to number",k2,e);
				}
				return n1 - n2;
			}
		}
	}
	
	/**
	 * 优先级组合排序
	 * @author 1571
	 *
	 */
	private static class ComponistedComparator implements Comparator<AncdModel>{
		
		private List<Comparator<AncdModel>> list = Collections.emptyList();
		
		public ComponistedComparator(List<Comparator<AncdModel>> list) {
			super();
			this.list = list;
		}

		@Override
		public int compare(AncdModel o1, AncdModel o2) {
			for(Comparator<AncdModel> comp : this.list){
				int c = comp.compare(o1, o2);
				if(c != 0){
					return c;
				}else{
					continue;
				}
			}
			return 0;
		}
	}
}
