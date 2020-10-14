/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.imp;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.FormatResult;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatter;
import com.woshidaniu.drdcsj.drsj.utils.PatternFactory;
/**
 * @author xiaokang[1036]
 * 复杂sql格式化，示例    
 * c_sql:select dm from t_table where zd1 = #{drlmc1} and zd2 = #{drlmc2}
 */
public class CSqlFormatter implements ImportFormatter{

	private static final Logger log = LoggerFactory.getLogger(CSqlFormatter.class);
	
	private static final Pattern PATTERN = PatternFactory.compile("(#\\{(\\w+)\\})", Pattern.CASE_INSENSITIVE);
	
	private SqlSession sqlSession;
	//数据库配置的查询SQL
	private String configSql;
	
	public CSqlFormatter(SqlSession sqlSession,String configSql) {
		this.sqlSession = sqlSession;
		this.configSql = configSql;
	}
	
	/**
	 * @description	： 格式化数据，新增一个当前行数据参数
	 * @param drlpzModel
	 * @param currentRowValue
	 * @param value
	 */
	public FormatResult format(DrlpzModel drlpzModel, Map<String,String> currentRowValue, String value){
		// 空不做处理
		if (StringUtils.isBlank(value)) {
			return new DefaultFormatResult(value);
		}
		StringBuffer executableSql = new StringBuffer();
		StringBuffer sql = new StringBuffer(this.configSql);
		Matcher matcher = PATTERN.matcher(sql);
		while(matcher.find()){
			//String g1 = matcher.group(1);
			String g2 = matcher.group(2);
			String g2_trim = g2.trim();
			//转换成小写
			String g2_lowerCase = g2_trim.toLowerCase();
			
			String g2_value = currentRowValue.get(g2_lowerCase);
			if(StringUtils.isEmpty(g2_value)) {
				//转换成大写，再尝试一下
				String g2_upperCase = g2_trim.toUpperCase();
				g2_value = currentRowValue.get(g2_upperCase);
			}
			matcher.appendReplacement(executableSql, "'" + g2_value + "'");
		}
		matcher.appendTail(executableSql);
		log.debug("导出数据格式化，数据库查询语句：{}", executableSql.toString());
		
		IImportDao importDao = this.sqlSession.getMapper(IImportDao.class);
		List<Map<String, String>> fomartData = importDao.getFomartData(executableSql.toString());
		
		String drlmc = drlpzModel.getDrlmc();
		String gshxx = drlpzModel.getGshxx();
		
		if(fomartData == null || fomartData.isEmpty()){
			
			return new DefaultFormatResult(value,"["+drlmc+"]"+gshxx);//错误也设置结果,但还是原始value
			
		}else if(fomartData.size() == 1) {
			
			Map<String, String> map = fomartData.get(0);
			String result = map.get("DM");
			
			return new DefaultFormatResult(result);
			
		}else if(fomartData.size() > 1){
			
			return new DefaultFormatResult(value,"["+drlmc+"]"+gshxx);//错误也设置结果,但还是原始value
			
		}else {
			//can't happen
			return new DefaultFormatResult(value);
		}
	}
	
	@Override
	public FormatResult format(DrlpzModel drlpzModel, String value) {
		throw new UnsupportedOperationException("不支持的方法调用");
	}

	@Override
	public String toString() {
		return "CSqlFormatter [configSQL=" + configSql + "]@"+this.hashCode();
	}
}
