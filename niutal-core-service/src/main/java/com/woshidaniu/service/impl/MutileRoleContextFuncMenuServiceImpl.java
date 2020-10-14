package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.service.svcinterface.IFuncMenuService;
import com.woshidaniu.service.svcinterface.IMutileRoleContextFuncMenuService;

/**
 * @author 1571
 */
@Service("mutileRoleContextFuncMenuService")
public class MutileRoleContextFuncMenuServiceImpl implements IMutileRoleContextFuncMenuService{
	
	private static final Logger log = LoggerFactory.getLogger(MutileRoleContextFuncMenuServiceImpl.class);
	
	@Resource(name  = "funcMenuService")
	private IFuncMenuService funcMenuService;
	
	@Override
	public List<BaseMap> getTopNavMenuList(String yhm, List<String> jsdms, String localeKey) {
		
		List<BaseMap> result = new ArrayList<BaseMap>();
		
		for(String jsdm : jsdms){
			List<BaseMap> list = this.funcMenuService.getTopNavMenuList(yhm, jsdm, localeKey);
			result.addAll(list);
		}
		
		this.process(result);
		
		return result;
	}

	@Override
	public List<BaseMap> getChildNavMenuList(String yhm, List<String> jsdms, String parentGnmkdm, String localeKey) {
		List<BaseMap> resultList = new ArrayList<BaseMap>();
		
		for(String jsdm : jsdms){
			List<BaseMap> list = this.funcMenuService.getChildNavMenuList(yhm, jsdm,parentGnmkdm,localeKey);
			resultList.addAll(list);
		}
		
		this.process(resultList);
		
		return resultList;
	}

	@Override
	public List<BaseMap> getChildNavMenuTreeList(String yhm, List<String> jsdms, String parentGnmkdm,String localeKey) {
		
		List<BaseMap> resultList = new ArrayList<BaseMap>();
		
		for(String jsdm : jsdms){
			List<BaseMap> list = this.funcMenuService.getChildNavMenuTreeList(yhm, jsdm,parentGnmkdm,localeKey);
			resultList.addAll(list);
		}
		
		this.process(resultList);
		
		return resultList;
	}

	@Override
	public List<BaseMap> getNavMenuTreeList(String yhm, List<String> jsdms, String localeKey) {
		List<BaseMap> resultList = new ArrayList<BaseMap>();
		
		for(String jsdm : jsdms){
			List<BaseMap> list = this.funcMenuService.getNavMenuTreeList(yhm, jsdm,localeKey);
			resultList.addAll(list);
		}
		
		this.process(resultList);
		
		return resultList;
	}
	
	private void process(List<BaseMap> list){
		
		processDistinct(list);
		
		processSort(list);
	}
	
	private void processDistinct(List<BaseMap> list){
		
		Set<String> gnmkdmSet = new HashSet<String>();
		
		Iterator<BaseMap> it = list.iterator();
		while(it.hasNext()){
			BaseMap baseMap = it.next();
			String gnmkdm = (String) baseMap.get("gnmkdm");
			if(!gnmkdmSet.contains(gnmkdm)){
				gnmkdmSet.add(gnmkdm);
			}else{
				it.remove();
			}
		}
	}
	
	private void processSort(List<BaseMap> list){
		
		@SuppressWarnings("unchecked")
		List<Comparator<BaseMap>> comparatorList = Arrays.asList(//
				new BaseMapNumberKeyComparator("xssx"),//
				new BaseMapStringKeyComparator("gnmkdm")//
		);//
		Comparator<BaseMap> comparator = new ComponistedComparator(comparatorList);
				
		list.sort(comparator);
	}

	
	/**
	 * 字符串的key的排序
	 * @author 1571
	 *
	 */
	private static class BaseMapStringKeyComparator implements Comparator<BaseMap>{
		
		private final String keyName;
		
		public BaseMapStringKeyComparator(String keyName) {
			super();
			this.keyName = keyName;
		}

		@Override
		public int compare(BaseMap o1, BaseMap o2) {
			String k1 = (String) o1.get(this.keyName);
			String k2 = (String) o2.get(this.keyName);
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
	 * 数字的key的排序
	 * @author 1571
	 *
	 */
	private static class BaseMapNumberKeyComparator implements Comparator<BaseMap>{
		
		private final String keyName;
		
		public BaseMapNumberKeyComparator(String keyName) {
			super();
			this.keyName = keyName;
		}

		@Override
		public int compare(BaseMap o1, BaseMap o2) {
			String k1 = (String) o1.get(this.keyName);
			String k2 = (String) o2.get(this.keyName);
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
	private static class ComponistedComparator implements Comparator<BaseMap>{
		
		private List<Comparator<BaseMap>> list = Collections.emptyList();
		
		public ComponistedComparator(List<Comparator<BaseMap>> list) {
			super();
			this.list = list;
		}

		@Override
		public int compare(BaseMap o1, BaseMap o2) {
			for(Comparator<BaseMap> comp : this.list){
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
