/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler;

import java.util.List;
import java.util.Map;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.LyzgzdzModel;

public interface IValidatorRuler {
	
	void setLyzgzdzModel(LyzgzdzModel lyzgzdzModel);
	
	/**
	 * 验证数据
	 * @param dataSource 数据源
	 * @param drlpzModel 配置信息
	 * @return 错误信息
	 */
	ValidatorModel validatorData(Map<String, List<String>> dataSource,DrpzModel drpzModel, DrlpzModel drlpzModel);
	/**
	 * 验证数据
	 * @param dataSource 数据源
	 * @param drlpzModel 配置信息
	 * @param T 验证配置参数
	 * @return 错误信息
	 */
	ValidatorModel validatorData(Map<String, List<String>> dataSource,DrpzModel drpzModel, DrlpzModel drlpzModel, String param);

}
