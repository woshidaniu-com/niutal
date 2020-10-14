/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler.imp.properties;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.comm.ImportErrorMessage;
import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorDb;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;

/**
 * @author kzd 1036
 * 数据库唯一性验证
 */
public class UniqueValidator extends ValidatorDb{

	protected static final Logger log = LoggerFactory.getLogger(UniqueValidator.class);
	
	private final String _ERROR_UNIQUE_KEY_INSERT = "该项不能重复，系统数据库中已存在。";
	
	private final String _ERROR_UNIQUE_KEY_UPDATE = "更新时必须存在，但系统不存在此值。";

	public UniqueValidator(SqlSession sqlSession) {
		super(sqlSession);
	}
	
	/**
	 * 验证数据的唯一性
	 * @param dataSource
	 * @param drpzModel
	 * @param drlpzModel
	 * @return
	 * @throws ImportException 
	 */
	public ValidatorModel validate(Map<String, List<String>> dataSource,DrpzModel drpzModel,DrlpzModel drlpzModel) {
		ValidatorModel vm = new ValidatorModel();
		ImportErrorMessage errorMessage = new ImportErrorMessage();
		List<String> dqllist = dataSource.get(drlpzModel.getDrlmc());
		String error = null;
		if(dqllist != null && dqllist.size() < ImportConfig.Thresholds){
			String sql = this.getSql(drpzModel, drlpzModel);
			List<String[]> paramsList = this.getSqlPramas(dqllist);
			int[] validateResult = this.batchQuery(sql, paramsList);
			for (int i = 0; i < validateResult.length; i++) {
				int r = validateResult[i];
				error = check(r, drpzModel, drlpzModel);
				if (StringUtils.isNotEmpty(error)) {
					errorMessage.update(i + 1 , error);
				}
			}
		}else{
			List<String> databaseUnique = getValues(drpzModel, drlpzModel);
			int rowIndex = 0;
			// 循环当前列,验证主键，格式化数据
			for (String str : dqllist) {
				// 第一行为列头，不做处理。
				if (rowIndex == 0) {
					rowIndex++;
					continue;
				}
				error = check(str, drpzModel, drlpzModel, databaseUnique);
				if (StringUtils.isNotEmpty(error)) {
					errorMessage.update(rowIndex , error);
				}
				rowIndex++;
			}
		}
		vm.setErrorMessage(errorMessage);
		vm.setDataSource(dataSource);
		return vm;
	}

	/**
	 * 检测唯一性
	 * @param rowIndex 当前行
	 * @param sql 检查sql
	 * @param str 当前列值
	 * @param drpzModel
	 * @param drlpzModel
	 * @return
	 * @throws SQLException
	 */
	private  String check(int count, DrpzModel drpzModel,DrlpzModel drlpzModel) {
		String error = null;
		// 如果是插入模式，且是主键
		if (ImportConfig._CRFS_INSERT.equals(drpzModel.getCrfs())) {
			if (count > 0) {
				error = fomartError(drlpzModel, _ERROR_UNIQUE_KEY_INSERT);
			}
		} else if (ImportConfig._CRFS_UPDATE.equals(drpzModel.getCrfs()) && ImportConfig._SFZJ_YES.equals(drlpzModel.getSfzj())) {
			// 如果是更新模式，且是主键
			if (count == 0) {
				error = fomartError(drlpzModel, _ERROR_UNIQUE_KEY_UPDATE);
			}
		}
		return error;
	}
		
	/**
	 * 检测唯一性
	 * @param rowIndex 当前行
	 * @param sql 检查sql
	 * @param str 当前列值
	 * @param drpzModel
	 * @param drlpzModel
	 * @return
	 * @throws SQLException
	 */
	private  String check(String str, DrpzModel drpzModel,DrlpzModel drlpzModel, List<String> databaseUnique) {
		String error = null;
		// 如果是插入模式
		if (ImportConfig._CRFS_INSERT.equals(drpzModel.getCrfs())) {
			if (databaseUnique.contains(str)) {
				error = fomartError(drlpzModel, _ERROR_UNIQUE_KEY_INSERT);
			}
		} 
		
		// 如果是更新模式，且是主键
		if (ImportConfig._CRFS_UPDATE.equals(drpzModel.getCrfs()) && ImportConfig._SFZJ_YES.equals(drlpzModel.getSfzj())) {
		
			if (!databaseUnique.contains(str)) {
				error = fomartError(drlpzModel, _ERROR_UNIQUE_KEY_UPDATE);
			}
		}
		return error;
	}
		
	/**
	 * 获取集合
	 * @param drpzModel
	 * @param drlpzModel
	 * @return
	 */
	private List<String> getValues(DrpzModel drpzModel, DrlpzModel drlpzModel) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("zd", drlpzModel.getDrl());
			map.put("tableName", drpzModel.getDrbmc());
			IImportDao dao = this.sqlSession.getMapper(IImportDao.class);
			return dao.getValues(map);
		} catch (Exception e) {
			log.error("查询值异常",e);
		}
		return new ArrayList<String>();
	}
		
	/**
	 * @param drpzModel
	 * @param drlpzModel
	 * @return
	 */
	private String getSql(DrpzModel drpzModel, DrlpzModel drlpzModel) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(");
		sql.append(drlpzModel.getDrl());
		sql.append(") countR from ");
		sql.append(drpzModel.getDrbmc());
		sql.append(" where ");
		sql.append(drlpzModel.getDrl());
		sql.append("=?");
		return sql.toString();
	}
	/**
	 * @return
	 */
	private List<String[]> getSqlPramas(List<String> source){
		List<String[]> paramsList = new ArrayList<String[]>();
		for (int i = 1; i < source.size(); i++) {
			paramsList.add(new String[]{source.get(i)});
		}
		return paramsList;
	}
		
	/**
	 * @param drlpzModel
	 * @param message
	 * @return
	 */
	private String fomartError(DrlpzModel drlpzModel, String message) {
		if (StringUtils.isBlank(message)) {
			return null;
		}
		return "[" + drlpzModel.getDrlmc() + "]" + message;
	}
}
