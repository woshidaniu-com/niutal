/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler;

import java.util.List;
import java.util.Map;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;

/**
 * @author 982
 * 策略场景，决定使用的验证
 */
public class ValidatorContext {
	
	private IValidatorRuler ruler;

	public ValidatorContext(IValidatorRuler ruler) {
		this.ruler = ruler;
	}

	public ValidatorModel validator(Map<String, List<String>> dataSource,DrpzModel drpzModel, DrlpzModel drlpzModel, String param) {
		return ruler.validatorData(dataSource, drpzModel, drlpzModel, param);
	}

	public ValidatorModel validator(Map<String, List<String>> dataSource,DrpzModel drpzModel, DrlpzModel drlpzModel) {
		return ruler.validatorData(dataSource, drpzModel, drlpzModel);
	}
}
