/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Predicate;

import com.woshidaniu.entities.JsgnmkModel;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;

/**
 *@类名称:JsgnmkSQLUtils.java
 *@类描述：功能模块代码SQL构建工具类
 *@创建人：kangzhidong
 *@创建时间：2014-10-20 上午11:26:34
 *@版本号:v1.0
 */

public class JsgnmkUtils {
	
	/**
	 * 
	 *@描述		：组织学生或教师功能菜单
	 *@创建人		: kangzhidong
	 *@创建时间	: Nov 4, 201610:36:03 AM
	 *@param list
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static List<List<Map<String,String>>> getStudentOrTeacherGnmkdmList(List<HashMap<String, String>> list){
		
		List<List<Map<String,String>>> rslist 	 = new ArrayList<List<Map<String,String>>>();
		List<Map<String, String>> list1  = new ArrayList<Map<String, String>>();//一级菜单
		List<Map<String, String>> list2  = new ArrayList<Map<String, String>>();//二级菜单
		List<Map<String, String>> list3  = new ArrayList<Map<String, String>>();//功能按钮
		String _topgndm  = "";
		String _ejmkdm   = "";
		for(int i=0;i<list.size();i++){
			Map<String,String>  _map  = (Map<String,String>)list.get(i);
			String topgndm  = String.valueOf(_map.get("JSTOPMKMCB_ID"));
			String ejgndm   = String.valueOf(_map.get("GNMKDM"));
			//模块名称
			if(i==0||!_topgndm.equals(topgndm)){
				_topgndm = topgndm;
				Map<String,String> topMap  =  new HashMap<String, String>();
				topMap.put("yjgnmkdm", String.valueOf(_map.get("JSTOPMKMCB_ID")));
				topMap.put("yjgnmkmc", String.valueOf(_map.get("MKMC")));
				list1.add(topMap);
			}
			//功能名称
			if(i==0||!_ejmkdm.equals(ejgndm)){
				_ejmkdm =  ejgndm;
				Map<String,String> ejMap  = new HashMap<String, String>();
				ejMap.put("yjgnmkdm", String.valueOf(_map.get("JSTOPMKMCB_ID")));
				ejMap.put("yjgnmkmc", String.valueOf(_map.get("MKMC")));
				ejMap.put("ejgndm", String.valueOf(_map.get("GNMKDM")));
				ejMap.put("ejgnmc", String.valueOf(_map.get("GNMKMC")));
				ejMap.put("dyym", String.valueOf(_map.get("DYYM")));
				list2.add(ejMap);
			}
			//按钮名称
			Map<String,String> anMap  = new HashMap<String, String>();
			anMap.put("yjgnmkdm", String.valueOf(_map.get("JSTOPMKMCB_ID")));
			anMap.put("yjgnmkmc", String.valueOf(_map.get("MKMC")));
			anMap.put("ejgndm", String.valueOf(_map.get("GNMKDM")));
			anMap.put("ejgnmc", String.valueOf(_map.get("GNMKMC")));
			anMap.put("dyym", String.valueOf(_map.get("DYYM")));
			
			anMap.put("czdm", String.valueOf(_map.get("CZDM")));
			anMap.put("czmc", String.valueOf(_map.get("CZMC")));
			anMap.put("sfxz", String.valueOf(_map.get("SFXZ")));
			list3.add(anMap);
		}
		rslist.add(list1);
		rslist.add(list2);
		rslist.add(list3);
		return rslist;
	}
	
	public static void initJsgnmkList(final JsgnmkModel parent_jsgnmkModel,List<JsgnmkModel> gnmkdmList,List<JsgnmkModel> gnmkczList) {
		//筛选当前父功能模块节点的子功能模块节点数据
		List<JsgnmkModel> childGnmkList = CollectionUtils.findAll(gnmkdmList, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				JsgnmkModel jsgnmkModel = (JsgnmkModel)object;
				//去除无用属性
				jsgnmkModel.setQueryList(null);
				jsgnmkModel.setQueryModel(null);
				jsgnmkModel.setListnav(null);
				jsgnmkModel.setTotalResult(null);
				jsgnmkModel.setDyym(null);
				if(jsgnmkModel.getFjgndm() != null && jsgnmkModel.getFjgndm().equalsIgnoreCase(parent_jsgnmkModel.getGnmkdm())){
					return true;
				}
				return false;
			}
		});
		//如果有子菜单,则进行子菜单的处理
		if(childGnmkList != null && childGnmkList.size() > 0){
			//循环子功能模块
			for (JsgnmkModel jsgnmkModel : childGnmkList) {
				//从一级功能菜单开始初始化各级别功能模块
				initJsgnmkList(jsgnmkModel,gnmkdmList,gnmkczList);
			}
			parent_jsgnmkModel.setChildList(childGnmkList);
		}else{
			//筛选当前前父功能模的功能操作数据
			List<JsgnmkModel> childGnmkczList = CollectionUtils.findAll(gnmkczList, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					JsgnmkModel child = (JsgnmkModel)object;
					//去除无用属性
					child.setQueryList(null);
					child.setQueryModel(null);
					child.setListnav(null);
					child.setTotalResult(null);
					child.setDyym(null);
					if(child.getGnmkdm().equalsIgnoreCase(parent_jsgnmkModel.getGnmkdm())){
						return true;
					}
					return false;
				}
			});
			//操作按钮
			parent_jsgnmkModel.setBtnList(childGnmkczList);
		}
		
	}
	
	protected static String getNvl(List<JsgnmkModel> gnmkdmJbList,int startIndex) {
		StringBuilder nvl = new StringBuilder();
		//倒着循环
		for (int index = gnmkdmJbList.size() - 1 ; index > startIndex; index--) {
			nvl.append("nvl(");
		}
		for (int index = gnmkdmJbList.size() - 1  ; index >= startIndex; index--) {
			JsgnmkModel jsgnmkModel = gnmkdmJbList.get(index);
			if( index == gnmkdmJbList.size() - 1){
				//t4.gnmkdm
				nvl.append("t").append(jsgnmkModel.getCdjb()).append(".gnmkdm");
			}else{
				//, t3.gnmkdm)
				nvl.append(",t").append(jsgnmkModel.getCdjb()).append(".gnmkdm)");
			}
		}
		return nvl.toString();
	}
	
	
	/**
	 * 
	 *@描述：构建多层级功能模块菜单的map结果：如下3层SQl
	 *<pre>
	 *
	 *	select t1.gnmkmc gnmkmc1,
	 *		   t1.gnmkdm gnmkdm1,
	 *	       t2.gnmkmc gnmkmc2,
	 *		   t2.gnmkdm gnmkdm2,
	 *		   t3.gnmkmc gnmkmc3,
	 *	       t3.gnmkdm gnmkdm3,
	 *		   t1.dyym || t2.dyym || t3.dyym dyym
	 *	  from niutal_xtgl_jsgnmkdmb t1,
	 *	       niutal_xtgl_jsgnmkdmb t2,
	 *	       niutal_xtgl_jsgnmkdmb t3
	 *	 where t1.fjgndm = 'N'
	 *	   and t1.gnmkdm = t2.fjgndm(+)
	 *	   and t2.gnmkdm = t3.fjgndm(+)
	 *	   and exists (select 'x'
	 *	          from niutal_xtgl_jsgnmkczb m, niutal_xtgl_yhjsb n
	 *	         where m.jsdm = n.jsdm
	 *	           and n.yhm = 'admin'
	 *	           and m.jsdm = 'admin'
	 *	           and m.gnmkdm = nvl(t3.gnmkdm, t2.gnmkdm)
	 *	       )
	 *	       
	 *	 order by t1.gnmkdm, to_number(t1.xssx),
	 *	          t2.gnmkdm,  to_number(t2.xssx),
	 *	          t3.gnmkdm, to_number(t3.xssx)
	 *
	 *</pre>
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20上午11:33:33
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param gnmkdmJbList
	 *@param yhm
	 *@param jsdm
	 *@return
	 */
	public static String getJsgnmkMapSQL(List<JsgnmkModel> gnmkdmJbList,String jsdm,String sqrJsdm,int startIndex){
		if(!BlankUtils.isBlank(gnmkdmJbList)){
			StringBuilder builder = new StringBuilder();
			builder.append("select ");
			for (int index = startIndex; index < gnmkdmJbList.size() ; index++) {
				String cdjb = gnmkdmJbList.get(index).getCdjb();
				builder.append(" t").append(cdjb).append(".gnmkmc as gnmkmc").append(cdjb).append(",");
				builder.append(" t").append(cdjb).append(".gnmkdm as gnmkdm").append(cdjb).append(",");
				builder.append(" t").append(cdjb).append(".fjgndm as fjgndm").append(cdjb).append(",");
			}
			for (int index = startIndex; index < gnmkdmJbList.size() ; index++) {
				builder.append(" t").append(gnmkdmJbList.get(index).getCdjb()).append(".dyym ||");
			}
			builder.delete(builder.lastIndexOf("||"), builder.length());
			builder.append( " as dyym ");
			
			builder.append(" from ");
			for (int index = startIndex; index < gnmkdmJbList.size() ; index++) {
				builder.append(" niutal_xtgl_jsgnmkdmb t").append(gnmkdmJbList.get(index).getCdjb()).append(",");
			}
			builder.deleteCharAt(builder.length() -1);
			builder.append(" where 1=1 ");
			
			for (int index = startIndex; index < gnmkdmJbList.size() - 1; index++) {
				JsgnmkModel jsgnmkModel = gnmkdmJbList.get(index);
				JsgnmkModel nextJsgnmkModel = gnmkdmJbList.get(index + 1);
				builder.append(" and t").append(jsgnmkModel.getCdjb()).append(".gnmkdm = t").append(nextJsgnmkModel.getCdjb()).append(".fjgndm(+) ");
			}
			
			builder.append(" and exists (select 'x' ");
			builder.append("  from niutal_xtgl_jsgnmkczb m ");
			builder.append(" where m.jsdm = '").append(sqrJsdm).append("' ");
			builder.append("   and m.gnmkdm = ").append(JsgnmkUtils.getNvl(gnmkdmJbList,startIndex));
			builder.append(")");
			builder.append("order by ");
			
			for (int index = startIndex; index < gnmkdmJbList.size() ; index++) {
				String cdjb = gnmkdmJbList.get(index).getCdjb();
				builder.append(" t").append(cdjb).append(".gnmkdm , to_number(t").append(cdjb).append(".xssx),");
			}
			builder.deleteCharAt(builder.length() -1);
			//返回结果
			return builder.toString();
		}else{
			return "";
		}
	}
	
	public static String getJsgnmkMapSQL(List<JsgnmkModel> gnmkdmJbList,String jsdm,String sqrJsdm){
		return JsgnmkUtils.getJsgnmkMapSQL(gnmkdmJbList, jsdm, sqrJsdm,0);
	}
	
	/**
	 * 
	 *@描述：构建多层级功能模块菜单的map结果：如下3层SQl
	 *<pre>
	 *
	 *	select t1.gnmkdm yjmkdm,
	 *		   t2.gnmkdm ejmkdm,
	 *	       t3.gnmkdm sjmkdm
	 *	  from niutal_xtgl_jsgnmkdmb t1,
	 *	       niutal_xtgl_jsgnmkdmb t2,
	 *	       niutal_xtgl_jsgnmkdmb t3
	 *	 where t1.fjgndm = 'N'
	 *	   and t1.gnmkdm = t2.fjgndm(+)
	 *	   and t2.gnmkdm = t3.fjgndm(+)
	 *	   and exists (select 'x'
	 *	          from niutal_xtgl_jsgnmkczb m, niutal_xtgl_yhjsb n
	 *	         where m.jsdm = n.jsdm
	 *	           and n.yhm = 'admin'
	 *	           and m.jsdm = 'admin'
	 *	           and m.gnmkdm = nvl(t3.gnmkdm, t2.gnmkdm)
	 *	       )
	 *	       
	 *	 order by t1.gnmkdm, to_number(t1.xssx),
	 *	          t2.gnmkdm,  to_number(t2.xssx),
	 *	          t3.gnmkdm, to_number(t3.xssx)
	 *
	 *</pre>
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20上午11:33:33
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param gnmkdmJbList
	 *@param yhm
	 *@param jsdm
	 *@return
	 */
	public static String getJsgnmkdmMapSQL(List<JsgnmkModel> gnmkdmJbList,String sqrJsdm,int startIndex){
		if(!BlankUtils.isBlank(gnmkdmJbList)){
			StringBuilder builder = new StringBuilder();
			builder.append("select ");
			for (int index = startIndex; index < gnmkdmJbList.size() ; index++) {
				String cdjb = gnmkdmJbList.get(index).getCdjb();
				builder.append(" t").append(cdjb).append(".gnmkdm as gnmkdm").append(cdjb).append(",");
				builder.append(" t").append(cdjb).append(".fjgndm as fjgndm").append(cdjb).append(",");
			}
			builder.deleteCharAt(builder.length() -1);
			builder.append(" from ");
			for (int index = startIndex; index < gnmkdmJbList.size() ; index++) {
				builder.append(" niutal_xtgl_jsgnmkdmb t").append(gnmkdmJbList.get(index).getCdjb()).append(",");
			}
			builder.deleteCharAt(builder.length() -1);
			builder.append(" where 1=1 ");
			
			for (int index = startIndex; index < gnmkdmJbList.size() - 1; index++) {
				JsgnmkModel jsgnmkModel = gnmkdmJbList.get(index);
				JsgnmkModel nextJsgnmkModel = gnmkdmJbList.get(index + 1);
				builder.append(" and t").append(jsgnmkModel.getCdjb()).append(".gnmkdm = t").append(nextJsgnmkModel.getCdjb()).append(".fjgndm(+) ");
			}
			
			builder.append(" and exists (select 'x' ");
			builder.append("	from niutal_xtgl_jsgnmkczb m ");
			builder.append(" where m.jsdm = '").append(sqrJsdm).append("' ");
			builder.append("   and m.gnmkdm = ").append(JsgnmkUtils.getNvl(gnmkdmJbList,startIndex));
			
			builder.append(")");
			builder.append("order by ");
			for (int index = startIndex; index < gnmkdmJbList.size()  ; index++) {
				String cdjb = gnmkdmJbList.get(index).getCdjb();
				builder.append(" t").append(cdjb).append(".gnmkdm , to_number(t").append(cdjb).append(".xssx),");
			}
			builder.deleteCharAt(builder.length() -1);
			//返回结果
			return builder.toString();
		}else{
			return "";
		}
	}
	
	public static String getJsgnmkdmMapSQL(List<JsgnmkModel> gnmkdmJbList,String sqrJsdm){
		return JsgnmkUtils.getJsgnmkdmMapSQL(gnmkdmJbList, sqrJsdm , 0);
	}
	
	/**
	 * 
	 *@描述：构建多层级功能模块菜单的map结果：如下3层SQl
	 *<pre>
	 *select distinct t.gnmkdm1 
	 *from (
	 *	select t1.gnmkdm gnmkdm1,
	 *		   t2.gnmkdm gnmkdm2,
	 *	       t3.gnmkdm gnmkdm3
	 *	  from niutal_xtgl_jsgnmkdmb t1,
	 *	       niutal_xtgl_jsgnmkdmb t2,
	 *	       niutal_xtgl_jsgnmkdmb t3
	 *	 where t1.fjgndm = 'N'
	 *	   and t1.gnmkdm = t2.fjgndm(+)
	 *	   and t2.gnmkdm = t3.fjgndm(+)
	 *	   and exists (select 'x'
	 *	          from niutal_xtgl_jsgnmkczb m, niutal_xtgl_yhjsb n
	 *	         where m.jsdm = n.jsdm
	 *	           and n.yhm = 'admin'
	 *	           and m.jsdm = 'admin'
	 *	           and m.gnmkdm = nvl(t3.gnmkdm, t2.gnmkdm)
	 *	       )
	 *	       
	 *	 order by t1.gnmkdm, to_number(t1.xssx),
	 *	          t2.gnmkdm,  to_number(t2.xssx),
	 *	          t3.gnmkdm, to_number(t3.xssx)
	 *) t
	 *order by t.gnmkdm1
	 *</pre>
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20上午11:33:33
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param gnmkdmJbList
	 *@param yhm
	 *@param jsdm	
	 *@param cdjb
	 *@return
	 */
	public static String getJsgnmkdmListSQL(List<JsgnmkModel> gnmkdmJbList,String sqrJsdm,String cdjb){
		if(!BlankUtils.isBlank(gnmkdmJbList)){
			StringBuilder builder = new StringBuilder();
			builder.append("select distinct t.gnmkdm").append(cdjb).append(" from (");
			builder.append(JsgnmkUtils.getJsgnmkdmMapSQL(gnmkdmJbList, sqrJsdm));
			builder.append(") t ");
			//返回结果
			return builder.toString();
		}else{
			return "";
		}
	}
	
	public static void main(String[] args) {
		
		//功能模块代码级别集合
		List<JsgnmkModel> gnmkdmJbList = new ArrayList<JsgnmkModel>();
		
		gnmkdmJbList.add(new JsgnmkModel("1"));
		gnmkdmJbList.add(new JsgnmkModel("2"));
		gnmkdmJbList.add(new JsgnmkModel("3"));
		System.out.println(JsgnmkUtils.getJsgnmkMapSQL(gnmkdmJbList, "01939EFA399D62AFE050007F010034A6", "admin",1));
		System.out.println(JsgnmkUtils.getJsgnmkdmMapSQL(gnmkdmJbList, "admin"));
		System.out.println(JsgnmkUtils.getJsgnmkdmListSQL(gnmkdmJbList, "admin", "1"));
	}

}
