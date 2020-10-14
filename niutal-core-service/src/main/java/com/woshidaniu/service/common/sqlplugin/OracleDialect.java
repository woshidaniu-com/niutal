package com.woshidaniu.service.common.sqlplugin;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basemodel.QueryModel.Sort;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.query.QueryModel;

public class OracleDialect extends Dialect {
	
	private static final Logger log = LoggerFactory.getLogger(OracleDialect.class);
	
	public String generatePageSql(String sql, QueryModel page) {
		if (page != null) {
			StringBuffer pageSql = new StringBuffer();
			pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
			pageSql.append(sql);

			
			//支持多列排序功能
			List<Sort> multiSort = page.getSorts();
			if(multiSort != null && multiSort.size() > 0){
				StringBuffer sortsBuf = new StringBuffer();
				Iterator<Sort> sortsIt = multiSort.iterator();
				while(sortsIt.hasNext()){
					if(sortsBuf.length() > 0){
						sortsBuf.append(", ");
					}
					Sort sort = sortsIt.next();
					
					String sortName = sort.getSortName();
					String sortOrder = StringUtils.isEmpty(sort.getSortOrder()) ? "asc" : sort.getSortOrder();
					
					if(checkSqlInjectionForSortName(sortName)){
						sortsBuf.append(sortName).append(" ").append(sortOrder);
					}
				}
				sortsBuf.insert(0, " order by ");
				pageSql.append(sortsBuf.toString());
			}else{
				if (StringUtils.isNotEmpty(page.getSortName())) {
					pageSql.append(" order by ");
					pageSql.append(page.getSortName());
					pageSql.append(" ");
					pageSql.append(StringUtils.isEmpty(page.getSortOrder()) ? "asc"
							: page.getSortOrder());
					// 对于仅包含一个排序字段的情况下，通过配置，增加第二个排序列
					if (page.getSortName().split(",").length == 1) {
						String sortNamePro = page.getSortNamePro();
						if (sortNamePro != null && !sortNamePro.trim().equals("")
								&& !sortNamePro.trim().equals(page.getSortName())) {
							pageSql.append(",");
							pageSql.append(sortNamePro);
						}
					}
				}
			}
			

			pageSql.append(") tmp_tb where ROWNUM<=");
			pageSql.append(page.getCurrentResult() + page.getShowCount());
			pageSql.append(") where row_id>");
			pageSql.append(page.getCurrentResult());
			sql = pageSql.toString();
		}
		return sql;
	}

	/**
	 * 返回true通过,false是不通过
	 * @param sortName
	 * @return
	 */
	private boolean checkSqlInjectionForSortName(String sortName) {
		String upperCaseSortName = sortName.toUpperCase();
		
		if(upperCaseSortName.contains("SELECT")){
			return false;
		}
		if(upperCaseSortName.contains("|")){
			return false;
		}
		if(upperCaseSortName.contains(" FROM ")){
			return false;
		}
		if(upperCaseSortName.contains(" DUAL ")){
			return false;
		}
		if(upperCaseSortName.contains(" WHERE ")){
			return false;
		}
		return true;
	}
}