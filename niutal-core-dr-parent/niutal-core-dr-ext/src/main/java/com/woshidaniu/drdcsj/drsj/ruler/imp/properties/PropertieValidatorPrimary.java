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

import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.comm.ImportErrorMessage;
import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.LyzgzdzModel;
import com.woshidaniu.drdcsj.drsj.formatter.FormatResult;
import com.woshidaniu.drdcsj.drsj.formatter.FormatterFactory;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatter;
import com.woshidaniu.drdcsj.drsj.formatter.imp.DefaultFormatterFactory;
import com.woshidaniu.drdcsj.drsj.ruler.IValidatorRuler;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorDb;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;

/**
 * @author 982
 * 验证主键（不包含联合主键）
 * @param <T>验证子类所需参数类型
 */
public class PropertieValidatorPrimary<T> extends ValidatorDb implements IValidatorRuler {
	
	private static final Logger log = LoggerFactory.getLogger(PropertieValidatorPrimary.class);
	
	private ValidatorModel vm = null;
	private final String _ERROR_PRIMARY_KEY_INSERT = "不能重复，但系统已经存在此值。";
	private final String _ERROR_PRIMARY_KEY_UPDTAE = "更新时必须存在，但系统不存在此值。";
	private ImportErrorMessage errorMessage = new ImportErrorMessage();
	// 保存数据库当前主键结合，考虑太多导入数据，每列验证查询数据库较慢，这里一次查询出，程序中进行验证。
	private List<String> databasePrimary = null;
	
	public PropertieValidatorPrimary(SqlSession sqlSession) {
		super(sqlSession);
	}
	@Override
	public ValidatorModel validatorData(Map<String, List<String>> dataSource,DrpzModel drpzModel, DrlpzModel drlpzModel) {
		
		// 为了缓存的数据为原数据不被更改，这里新生成了副本来进行保存格式化后的数据，
		// 效率比较低（用了map的putall），后期有好的实现可以更改掉
		Map<String, List<String>> newDataSource = new HashMap<String, List<String>>();
		newDataSource.putAll(dataSource);
		vm = new ValidatorModel();
		try {
			List<String> fomartList = new ArrayList<String>();
			List<String> dqllist = dataSource.get(drlpzModel.getDrlmc());
			String error = null;
			
			FormatterFactory formatterFactory = new DefaultFormatterFactory(sqlSession);
			
			// 获取格式化处理方式
			String lsjgsh = drlpzModel.getLsjgsh(); 
			ImportFormatter formatter = formatterFactory.getFormatter(lsjgsh);
			
			//如果导入数据不多，小于Thresholds阀值,则采用数据库对比方式验证，否则采用缓存方式
			if(dqllist != null && dqllist.size() < ImportConfig.Thresholds){
				String sql = getSql(drpzModel, drlpzModel);
				List<String[]> paramsList = getSqlPramas(dqllist);
				int[] validateResult = batchQuery(sql, paramsList);
				fomartList.add(dqllist.get(0));
				for (int i = 0; i < validateResult.length; i++) {
					int r = validateResult[i];
					error = check(r, drpzModel, drlpzModel);
					error = addError(i + 1, error);
					// 格式化配置处理
					if (null != formatter) {
						FormatResult  formatResult = formatter.format(drlpzModel, dqllist.get(i + 1));
						error = formatResult.getError();
						// 保存格式化后的值
						fomartList.add(formatResult.getResult());
					} else {
						fomartList.add(dqllist.get(i + 1));
					}
					error = addError(i + 1, error);
				}
			}else{
				int rowIndex = 0;
				// 循环当前列,验证主键，格式化数据
				for (String str : dqllist) {
					// 第一行为列头，不做处理。
					if (rowIndex == 0) {
						fomartList.add(str);
						rowIndex++;
						continue;
					}
					error = check(str, drpzModel, drlpzModel);
					error = addError(rowIndex, error);
					// 格式化配置处理
					if (null != formatter) {
						FormatResult  formatResult = formatter.format(drlpzModel, str);
						error = formatResult.getError();
						// 保存格式化后的值
						fomartList.add(formatResult.getResult());
					} else {
						fomartList.add(str);
					}
					error = addError(rowIndex, error);
					rowIndex++;
				}
			}
			vm.setErrorMessage(errorMessage);
			// 更新数据，格式化后的值替换原值
			newDataSource.put(drlpzModel.getDrlmc(), fomartList);
			vm.setDataSource(newDataSource);
		} catch (Exception e) {
			log.error("",e);
		} finally {
			sqlSession.close();
		}
		return vm;
	}
	/**
	 * 检测主键
	 * @param rowIndex 当前行
	 * @param sql 检查sql
	 * @param str 当前列值
	 * @param drpzModel
	 * @param drlpzModel
	 * @return
	 * @throws SQLException
	 */
	private  String check(String str, DrpzModel drpzModel,DrlpzModel drlpzModel){
		String error = null;
		if (ImportConfig._SFZJ_YES.equals(drlpzModel.getSfzj()) && null == databasePrimary) {
			//获取数据库表主键的集合
			databasePrimary = getPrimary(drpzModel, drlpzModel);
		}
		// 如果是插入模式，且是主键
		if (ImportConfig._CRFS_INSERT.equals(drpzModel.getCrfs()) && ImportConfig._SFZJ_YES.equals(drlpzModel.getSfzj())) {

			if (databasePrimary.contains(str)) {
				error = fomartError(drlpzModel, _ERROR_PRIMARY_KEY_INSERT);
			}
		} else if (ImportConfig._CRFS_UPDATE.equals(drpzModel.getCrfs()) && ImportConfig._SFZJ_YES.equals(drlpzModel.getSfzj())) {// 如果是更新模式，且是主键
			if (!databasePrimary.contains(str)) {
				error = fomartError(drlpzModel, _ERROR_PRIMARY_KEY_UPDTAE);
			}
		}
		return error;
	}
	/**
	 * 检测主键
	 * @param rowIndex 当前行
	 * @param sql 检查sql
	 * @param str 当前列值
	 * @param drpzModel
	 * @param drlpzModel
	 * @return
	 * @throws SQLException
	 */
	private  String check(int count, DrpzModel drpzModel,DrlpzModel drlpzModel){
		String error = null;
		// 如果是插入模式，且是主键
		if (ImportConfig._CRFS_INSERT.equals(drpzModel.getCrfs()) && ImportConfig._SFZJ_YES.equals(drlpzModel.getSfzj())) {
			if (count > 0) {
				error = fomartError(drlpzModel, _ERROR_PRIMARY_KEY_INSERT);
			}
		} else if (ImportConfig._CRFS_UPDATE.equals(drpzModel.getCrfs()) && ImportConfig._SFZJ_YES.equals(drlpzModel.getSfzj())) {// 如果是更新模式，且是主键
			if (count == 0) {
				error = fomartError(drlpzModel, _ERROR_PRIMARY_KEY_UPDTAE);
			}
		}
		return error;
	}
	
	private String addError(int rowIndex, String error) {
		if (StringUtils.isNotEmpty(error)) {
			errorMessage.update(rowIndex, error);
		}
		return null;
	}
	/**
	 * 获取主键集合
	 * @param drpzModel
	 * @param drlpzModel
	 * @return
	 */
	private List<String> getPrimary(DrpzModel drpzModel, DrlpzModel drlpzModel) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("primary", drlpzModel.getDrl());
			map.put("tableName", drpzModel.getDrbmc());
			IImportDao dao = ServiceFactory.getService(IImportDao.class);
			return dao.getPrimary(map);
		} catch (Exception e) {
			log.error("",e);
		}
		return new ArrayList<String>();
	}

	@Override
	public ValidatorModel validatorData(Map<String, List<String>> dataSource,DrpzModel drpzModel, DrlpzModel drlpzModel, String param) {
		return validatorData(dataSource, drpzModel, drlpzModel);
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

	@Override
	public void setLyzgzdzModel(LyzgzdzModel lyzgzdzModel) {

	}
}
