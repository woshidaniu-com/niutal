/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.imp;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.FormatterFactory;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatter;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatterContants;
import com.woshidaniu.drdcsj.drsj.formatter.field.FIELDTYPE;
import com.woshidaniu.drdcsj.drsj.formatter.field.IField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.DateField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.DatetimeField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.EmailField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.FloatField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.IDCardField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.IntField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.IntegerFloatField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.MobileField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.NumberField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.PatternField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.TelField;
import com.woshidaniu.drdcsj.drsj.formatter.field.imp.ZipField;

/**
 * @description	： 默认格式化器工厂
 * @author xiaokang
 * @author 康康（1571）
 */
public class DefaultFormatterFactory implements FormatterFactory{
	
	private static final Logger log = LoggerFactory.getLogger(DefaultFormatterFactory.class);
	
	private static final AcceptAllPatternField ACCEPT_ALL_PATTERN_FIELD = new AcceptAllPatternField();

	private SqlSession sqlSession;
	
	public DefaultFormatterFactory(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public ImportFormatter getFormatter(String lsjgsh) {
		
		if(StringUtils.isBlank(lsjgsh)) {
			return new ReturnOriginValueFormatter();
		}
		String lsjgsh_lowerCase = lsjgsh.toLowerCase();
		
		if (lsjgsh_lowerCase.startsWith(ImportFormatterContants._JSON)) {//json格式化文本格式化器
			
			return new JsonFormatter();
			
		} else if (lsjgsh_lowerCase.startsWith(ImportFormatterContants._C_SQL)) {//csql前缀格式化器
			
			String sql = StringUtils.substringAfter(lsjgsh, "c_sql:").trim();
			return new CSqlFormatter(this.sqlSession,sql);
			
		}else if (lsjgsh_lowerCase.startsWith(ImportFormatterContants._SQL)) {//sql前缀格式化器
			
			String sql = StringUtils.substringAfter(lsjgsh, "sql:").trim();
			return new SqlFormatter(this.sqlSession,sql);
			
		} else if (lsjgsh_lowerCase.startsWith(ImportFormatterContants._FIELD)) {//字段格式化器
			
			return this.getFieldWrappFormatter(lsjgsh);
			
		} else {
			//开发阶段需要处理的问题,我们只能抛出运行时异常
			throw new IllegalArgumentException("从文本["+lsjgsh+"]无法构建预定义的格式化器.请仔细检查配置的字符串是否正确!!!");
		}
	}
	
	private ImportFormatter getFieldWrappFormatter(String lsjgsh) {
		
		String fieldVal = StringUtils.substringAfter(lsjgsh, ":");
		String args = null;
		
		//如果带有参数
		if(StringUtils.containsAny(fieldVal,'(',')')){
			args = StringUtils.substring(fieldVal, 
						StringUtils.indexOf(fieldVal, "(") + 1, 
						StringUtils.lastIndexOf(fieldVal, ")")
					);
			
			fieldVal = StringUtils.substringBefore(fieldVal, "(").toUpperCase();	
		}
		
		FIELDTYPE fielType = FIELDTYPE.fromString(fieldVal.toUpperCase());
		IField filed = null;
		switch (fielType) {
		case NUMBER:
			filed = new NumberField();
			break;
		case INTEGERFLOAT:
			filed = new IntegerFloatField();
			break;	
		case EMAIL:
			filed = new EmailField();
			break;
		case FLOAT:
			filed = new FloatField();
			break;
		case INT:
			filed = new IntField();
			break;
		case DATE:
			if(args != null){
				filed = new DateField(args);
			}else{
				filed = new DateField();
			}
			break;
		case DATETIME:
			if(args != null){
				filed = new DatetimeField(args);
			}else{
				filed = new DatetimeField();
			}
			break;
		case IDCARD:
			filed = new IDCardField();
			break;
		case TEL:
			filed = new TelField();
			break;
		case ZIP:
			filed = new ZipField();
			break;
		case MOBILE:
			filed = new MobileField();
			break;
		case PATTERN:
			if(args != null){
				filed = new PatternField(args);
			}else{
				filed = ACCEPT_ALL_PATTERN_FIELD;
			}
			break;
		default:
			break;
		}
		if(filed == null) {
			//开发阶段需要处理的问题,我们只能抛出运行时异常
			throw new IllegalArgumentException("从文本["+lsjgsh+"]无法构建预定义的格式化器.请仔细检查配置的字符串是否正确!!!");
		}else {
			return new FieldFormatter(filed);
		}
	}

	/**
	 * @description	： 允许所有的正则表达式字段
	 */
	private static class AcceptAllPatternField implements IField{

		@Override
		public boolean check(DrlpzModel drlpzModel, String value) {
			return true;
		}

		@Override
		public String getErrorMessage(DrlpzModel drlpzModel, String value) {
			return null;
		}

		@Override
		public String toString() {
			return "AcceptAllPatternField []";
		}
	}
}
