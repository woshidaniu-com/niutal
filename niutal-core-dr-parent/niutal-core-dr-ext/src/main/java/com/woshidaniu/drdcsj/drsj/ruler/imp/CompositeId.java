/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorDb;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;

public class CompositeId extends ValidatorDb {
	
	private static final Logger log = LoggerFactory.getLogger(CompositeId.class);

	private static final String LJ = "-";
	//提示信息
	private static final Map<String, String> errorMessages = new HashMap<String, String>();
	//初始化
	static{
		errorMessages.put("PRIMARY", "EXCEL数据中有重复");
		errorMessages.put("PRIMARY_INSERT", "数据库中有重复");
		errorMessages.put("PRIMARY_UPDATE", "更新时数据库中必须存在");
	}	
	
	private  ValidatorModel validationModel;
	
	public CompositeId(SqlSession sqlSession) {
		super(sqlSession);
	}
	
	public void validate(DrpzModel drpzModel, List<DrlpzModel> drlpzList, ValidatorModel vm, String[] compositeIds){
		this.validationModel = vm;
		Map<String, List<String>> dataSource = validationModel.getDataSource();
		//联合主键字段值
		List<String> compositeIdValList = getCompositeIdValueList(drlpzList, compositeIds, dataSource);
		//导入列名称
		String[] drlmc = getDrlmc(drlpzList, compositeIds);
		
		for (int i = 1; i < compositeIdValList.size(); i++) {
			String zdValue = compositeIdValList.get(i);
			//验证导入数据唯一性
			int cardinality = CollectionUtils.cardinality(zdValue,compositeIdValList.subList(1, compositeIdValList.size()));
			if(cardinality > 1){
				validationModel.getErrorMessage().update(i, fomartError(StringUtils.join(drlmc, ","), errorMessages.get("PRIMARY")));
			}
		}
		
		//联合主键验证
		//这里分组执行,提高速度
		List<String[]> paramsList = new ArrayList<String[]>();
		String sql = getSql(drpzModel, compositeIds);
		int[] batchQuery = null;
		int ii= 1, jj= 0;
		while (ii < compositeIdValList.size()) {
			String[] item = new String[drlmc.length];
			for (int k = 0; k < drlmc.length; k++) {
				List<String> list = dataSource.get(drlmc[k]);
				item[k] = list.get(ii);
			}
			paramsList.add(item);
			
			if(ii == (compositeIdValList.size()-1) || ii == (jj * 100 + 99)){
				int[]  queryResult = this.batchQuery(sql, paramsList);
				batchQuery = ArrayUtils.addAll(batchQuery, queryResult);
				paramsList.clear();
				jj++;
			}
			
			ii++;
		}
		for (int i = 0; i < batchQuery.length; i++) {
			// 如果是插入模式
			if (ImportConfig._CRFS_INSERT.equals(drpzModel.getCrfs())) {
				if (batchQuery[i] > 0) {
					validationModel.getErrorMessage().update(i + 1, fomartError(StringUtils.join(drlmc, ","), errorMessages.get("PRIMARY_INSERT")));
				}
			} else if (ImportConfig._CRFS_UPDATE.equals(drpzModel.getCrfs())) {
				// 如果是更新模式
				if (batchQuery[i] == 0) {
					validationModel.getErrorMessage().update(i + 1, fomartError(StringUtils.join(drlmc, ","), errorMessages.get("PRIMARY_UPDATE")));
				}
			}else{
				if(batchQuery[i] > 0){
					validationModel.transferData(i+1, "U");
				}else{
					validationModel.transferData(i+1, "I");
				}
			}
		}
	}
	/**
	 * @param drpzModel
	 * @param drlpzModel
	 * @return
	 */
	private String getSql(DrpzModel drpzModel, String[] compositeIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) countR from ");
		sql.append(drpzModel.getDrbmc());
		sql.append(" where ");
		String[] condition = new String[compositeIds.length];
		for (int i = 0; i < compositeIds.length; i++) {
			condition[i] = compositeIds[i] + " = ? ";
		}
		sql.append(StringUtils.join(condition, " and "));
		return sql.toString();
	}
	/**
	 * @param compositeIds2
	 * @param dataSource
	 * @return
	 */
	private List<String> getCompositeIdValueList(List<DrlpzModel> drlpzList,String[] compositeIds2,Map<String, List<String>> dataSource) {
		List<String> compositeIdValList = new ArrayList<String>();
		String[] drlmc = getDrlmc(drlpzList, compositeIds2);
		boolean init = true;
		for (String drl : drlmc) {
			List<String> list = dataSource.get(drl);
			for (int i = 0; i < list.size(); i++) {
				if(init){
					compositeIdValList.add(list.get(i));
				}else{
					compositeIdValList.set(i, compositeIdValList.get(i) + LJ + list.get(i));
				}
			}
			init = false;
		}
		return compositeIdValList;
	}

	private String[] getDrlmc(List<DrlpzModel> drlpzList,String[] compositeIds2){
		String[] drlmc = new String[compositeIds2.length];
		for (int i = 0; i < compositeIds2.length; i++) {
			for (DrlpzModel drlpzModel : drlpzList) {
				String drlmc2 = drlpzModel.getDrlmc();
				String drl = drlpzModel.getDrl();
				if(StringUtils.equals(drl, compositeIds2[i])){
					drlmc[i] = drlmc2;
					break;
				}
			}
		}
		return drlmc;
	}

	private String fomartError(String tslm, String gzts) {
		return "[" + tslm + "]" + gzts;
	}
}
