/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler.imp.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;

import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;
import com.woshidaniu.drdcsj.drsj.ruler.imp.ValidatorDbValidator;
import com.woshidaniu.drdcsj.drsj.svcinterface.impl.SfbtColumnSwitchProcessor;

/**
 * 属性验证
 * @author 982 张昌路
 */
public class CommonValidator extends ValidatorDbValidator{

	public CommonValidator(DrpzModel drpzModel, List<DrlpzModel> drlpzList, ValidatorModel validatorModel,SqlSession sqlSession) {
		super(drpzModel, drlpzList, validatorModel, sqlSession);
	}
	// 提示信息
	private static final Map<String, String> errorMessages = new HashMap<String, String>();
	// 初始化
	static {
		errorMessages.put("PRIMARY", "EXCEL数据中有重复");
		errorMessages.put("PRIMARY_NOTNULL", "不能为空");
		errorMessages.put("NOTNULL", "不能为空");
		errorMessages.put("LENGTH", "超过最大长度");
		errorMessages.put("PRIMARY_INSERT", "数据库中有重复");
		errorMessages.put("PRIMARY_UPDATE", "更新时数据库中必须存在");
	}

	@Override
	public void doValidate() {
		Map<String, List<String>> dataSource = validatorModel.getDataSource();
		for (DrlpzModel drlpzModel : drlpzList) {
			//当前列的数据
			List<String> dqllist = dataSource.get(drlpzModel.getDrlmc());
			this.validateDrl(drpzModel, drlpzModel, dqllist,validatorModel);
		}
		
		if (validatorModel.getErrorMessage().size() > 0) {
			return;
		}
		
		if (ImportConfig._CRFS_INSERT_UPDATE.equals(drpzModel.getCrfs())) {
			Map<String, List<String>> dataForInsert = validatorModel.getDataForInsert();
			Map<String, List<String>> dataForUpdate = validatorModel.getDataForUpdate();
			drpzModel.setCrfs(ImportConfig._CRFS_INSERT);
			for (DrlpzModel drlpzModel : drlpzList) {
				List<String> dqllist = dataForInsert.get(drlpzModel.getDrlmc());
				this.validateDrl(drpzModel, drlpzModel, dqllist,validatorModel);
			}
			drpzModel.setCrfs(ImportConfig._CRFS_UPDATE);
			for (DrlpzModel drlpzModel : drlpzList) {
				List<String> dqllist = dataForUpdate.get(drlpzModel.getDrlmc());
				this.validateDrl(drpzModel, drlpzModel, dqllist,validatorModel);
			}
			drpzModel.setCrfs(ImportConfig._CRFS_INSERT_UPDATE);
		}
	}

	/**
	 * 验证列
	 * @param drpzModel
	 * @param drlpzModel
	 * @param drlValue
	 */
	private void validateDrl(DrpzModel drpzModel,final DrlpzModel drlpzModel, List<String> drlValue,final ValidatorModel vm) {
		//如果导入列为空，则跳过验证
		if (drlValue == null || drlValue.size() <= 1) {
			return;
		}
		//插入方式，操作模式
		final String crfs = drpzModel.getCrfs();
		// 当前列是否必填
		final int sfbtColumnFlag = drlpzModel.getSfbtFlag();
		// 当前列是否需要联合主键验证
		boolean isCompositeId = drpzModel.isCompositeId();
		// 当前列是否是主键
		String sfzj = drlpzModel.getSfzj();
		// 当前列是否唯一
		String sfwy = drlpzModel.getSfwy();
		// 当前列字段最大长度
		int zdcd = NumberUtils.toInt(drlpzModel.getZdcd());

		for (int i = 1; i < drlValue.size(); i++) {
			final int index = i;
			final String zdValue = drlValue.get(i);
			// 验证当前字段主键非空
			if (StringUtils.equals(sfzj, ImportConfig._SFZJ_YES) && StringUtils.isBlank(zdValue)) {
				vm.getErrorMessage().update(i,formatError(drlpzModel, errorMessages.get("PRIMARY_NOTNULL")));
			}
			// 验证当前字段是否必填
			SfbtColumnSwitchProcessor switchProcessor = new SfbtColumnSwitchProcessor() {
				private boolean appended = false;
				@Override
				protected void ifBtOnInsert() {
					if (StringUtils.isBlank(zdValue)) {
						if(!appended) {
							vm.getErrorMessage().update(index, formatError(drlpzModel, errorMessages.get("NOTNULL")));
							appended = true;
						}
					}
				}
				@Override
				protected void ifBtOnUpdate() {
					if(StringUtils.isBlank(zdValue)) {
						if(!appended) {
							vm.getErrorMessage().update(index, formatError(drlpzModel, errorMessages.get("NOTNULL")));
							appended = true;
						}
					}
				}
				@Override
				protected void ifBtOnInsertAndUpdate() {
					
					if(StringUtils.isBlank(zdValue)) {
						if(!appended) {
							vm.getErrorMessage().update(index, formatError(drlpzModel, errorMessages.get("NOTNULL")));
							appended = true;
						}
					}
				}
			};
			switchProcessor.process(crfs, sfbtColumnFlag);
			// 验证字段最大长度
			if (StringUtils.length(zdValue) > zdcd) {
				vm.getErrorMessage().update(i,formatError(drlpzModel, errorMessages.get("LENGTH") + zdcd));
			}

			// 验证唯一性
			if (StringUtils.equals(sfwy, ImportConfig._SFWY_YES)||(!isCompositeId && StringUtils.equals(sfzj, ImportConfig._SFZJ_YES))) {
				// 验证导入数据唯一性
				if (StringUtils.isNotBlank(zdValue)) {
					int cardinality = CollectionUtils.cardinality(zdValue, drlValue.subList(1, drlValue.size()));
					if (cardinality > 1) {
						vm.getErrorMessage().update(i,formatError(drlpzModel, errorMessages.get("PRIMARY")));
					}
				}
			}
		}

		// 如果是单一主键或唯一，验证数据库数据唯一性
		if ((!isCompositeId && StringUtils.equals(sfzj, ImportConfig._SFZJ_YES)) || StringUtils.equals(sfwy, ImportConfig._SFWY_YES)) {
			// 这里分组执行,提高速度
			List<String> subList = drlValue.subList(1, drlValue.size());
			List<String[]> paramsList = new ArrayList<String[]>();
			String sql = getSql(drpzModel, drlpzModel);
			int[] batchQuery = null;
			int ii = 0, jj = 0;
			while (ii < subList.size()) {
				paramsList.add(new String[] { subList.get(ii) });
				if (ii == (subList.size() - 1) || ii == (jj * 100 + 99)) {
					int[] queryResult = this.batchQuery(sql, paramsList);
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
						vm.getErrorMessage().update(i + 1,formatError(drlpzModel, errorMessages.get("PRIMARY_INSERT")));
					}
				} else if (ImportConfig._CRFS_UPDATE.equals(drpzModel.getCrfs()) && ImportConfig._SFZJ_YES.equals(drlpzModel.getSfzj())) {
					// 如果是更新模式并且主键
					if (batchQuery[i] == 0 && ImportConfig._SFZJ_YES.equals(drlpzModel.getSfzj())) {
						vm.getErrorMessage().update(i + 1,formatError(drlpzModel, errorMessages.get("PRIMARY_UPDATE")));
					}
				} else if(ImportConfig._CRFS_INSERT_UPDATE.equals(drpzModel.getCrfs())){
					// 如果是主键，可以判断用什么方式处理数据，更新或则直接插入
					// 如果不是主键，不能判定如何处理数据，需要根据主键的数据处理逻辑，来判断该条数据是否有错误。
					if (StringUtils.equals(sfzj, ImportConfig._SFZJ_YES)) {
						if (batchQuery[i] > 0) {
							vm.transferData(i + 1, "U");
						} else {
							vm.transferData(i + 1, "I");
						}
					}
				}else {
					//can't happen
				}
			}
		}
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
	 * @param drlpzModel
	 * @param message
	 * @return
	 */
	private String formatError(DrlpzModel drlpzModel, String message) {
		if (StringUtils.isBlank(message)) {
			return null;
		}
		return "[" + drlpzModel.getDrlmc() + "]" + message;
	}
}
