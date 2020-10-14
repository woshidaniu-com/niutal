/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler;

import com.woshidaniu.drdcsj.drsj.comm.ImportUtil;
import com.woshidaniu.drdcsj.drsj.dao.entities.LyzgzdzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.YzgzModel;

/**
 * @author 982
 * 验证工厂 获取对应的验证规则
 */
public class ValidatorFactory {
	private static final String _LHYZ_YZL = "1";

	private static final String _NONE_LHYZ = "0";
	
	/**
	 * 获取配置验证规则对象
	 * @param lyzgzdzModel
	 * @return
	 */
	public static <T> IValidatorRuler getRuler(LyzgzdzModel lyzgzdzModel) {
		
		if (_LHYZ_YZL.equals(lyzgzdzModel.getLhyz()) || _NONE_LHYZ.equals(lyzgzdzModel.getLhyz())) {
			YzgzModel yzgzModel = lyzgzdzModel.getYzgzModel();
			String llj = yzgzModel.getLlj();
			String param[] = lyzgzdzModel.getParam();
			IValidatorRuler validatorRuler = (IValidatorRuler) ImportUtil.getValidatorRuler(llj, param);
			validatorRuler.setLyzgzdzModel(lyzgzdzModel);
			return validatorRuler;
		}
		return null;
	}

}
