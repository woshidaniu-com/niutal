/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlplugin;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.datarange.DataRangeService;
import com.woshidaniu.common.datarange.QueryType;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.daointerface.IJsglDao;
import com.woshidaniu.dao.entities.SjzygzModel;
import com.woshidaniu.web.WebContext;

/**
 * 
 * @className	： DataRangeSqlBuilder
 * @description	： 数据范围sql条件构建器
 * @author 		：康康（1571）
 * @date		： 2018年9月11日 下午4:34:54
 * @version 	V1.0
 */
public class DataRangeSqlBuilder {
	
	protected static final Logger log = LoggerFactory.getLogger(DataRangeSqlBuilder.class);
	

	/**
	 * @description	： 构建数据范围添加语句
	 * 				形如  year in ('2017','2018','2019')
	 * @author 		： 康康（1571）
	 * @date 		：2018年9月12日 上午11:19:56
	 * @param gzzdMap 规则id和查询语句查询字段的映射map
	 * @return
	 */
	public static String build(Map<String/**gzid(规则id)**/,String/**zdm(查询字段名称)**/> gzzdMap) {
		return build(gzzdMap,QueryType.IN);
	}
	
	/**
	 * @description	： 构建数据范围添加语句
	 * 				形如  year in ('2017','2018','2019')
	 * @author 		： 康康（1571）
	 * @date 		：2018年9月12日 上午11:19:56
	 * @param gzzdMap 规则id和查询语句查询字段的映射map
	 * @param queryType 查询类型
	 * @return
	 */
	public static String build(Map<String/**gzid(规则id)**/,String/**zdm(查询字段名称)**/> gzzdMap,QueryType queryType) {
		
		if(gzzdMap == null) {
			throw new IllegalArgumentException("gzzdMap can't be null");
		}
		if(queryType == null) {
			throw new IllegalArgumentException("queryType can't be null");
		}
		return doBuilde(gzzdMap,queryType);
	}

	private static String doBuilde(Map<String/**gzid(规则id)**/,String/**zdm(查询字段名称)**/> gzzdMap, QueryType queryType) {

		Set<String> gzidSet = gzzdMap.keySet();
		String[] gzids = new String[gzzdMap.keySet().size()];
		gzids = gzidSet.toArray(gzids);
		
		//通过当前用户，取出角色数据范围
		User user = WebContext.getUser();
		String yhm = user.getYhm();
		String jsdm = user.getJsdm();
		IJsglDao jsglDao = ServiceFactory.getService(IJsglDao.class);
		List<SjzygzModel> sjzyList = jsglDao.getSjzyList(jsdm,gzids);//角色规则
		
		if(sjzyList == null || sjzyList.isEmpty()) {
			return "";
		}
		
		//数据范围SQL解析
		StringBuilder sql = new StringBuilder();
		for (SjzygzModel model : sjzyList){
			
			String gzid = model.getGzid();
			if (gzidSet.contains(gzid)){//拦截方法所支持的规则
				
				String zdm = gzzdMap.get(model.getGzid());//字段名
				DataRangeService service = (DataRangeService) ServiceFactory.getService(model.getGztgz());
				if(service == null) {
					log.error("无法找用户[{}]所属角色[{}]对应规则id[{}]的条件范围列表服务类[{}]",yhm,jsdm,gzid,model.getGztgz());
					continue;
				}
				List<String> sjfwList = service.getDataRangeList(user);
				if (sjfwList == null || sjfwList.isEmpty()) {
					continue;
				}
				
				if (sql.length() > 0){
					//如果多条规则同时起效
					sql.append(" or ");
				}
				StringBuilder format = new StringBuilder();
				
				if (QueryType.OR.equals(queryType)){//or 类型
					for (int i = 0 ,size = sjfwList.size() ; i < size; i++){
						format.append(zdm);
						format.append("='");
						format.append(sjfwList.get(i));
						format.append("'");
						if (i+1 != size){
							format.append(" or ");
						}
					}
				} else { //in 类型
					format.append(zdm);
					format.append(" in (");
					for (int i = 0 ,size = sjfwList.size() ; i < size; i++){
						format.append("'");
						format.append(sjfwList.get(i));
						format.append("'");
						if (i+1 != size){
							format.append(",");
						}
					}
					format.append(")");
				}
				sql.append(format);
			}
		}
		return sql.toString();
	}
}
