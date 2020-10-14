/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.imp;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.FormatResult;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatter;

/**
 * @author 		：康康（1571）
 * @description	：返回原值的formatter，空操作对象，避免太多null判断
 * 	原先设计的fotmatter能够返回null是一种不良的设计
 */
/**public**/ class ReturnOriginValueFormatter implements ImportFormatter{

	@Override
	public FormatResult format(DrlpzModel drlpzModel, String value) {
		return new DefaultFormatResult(value);
	}
	
	@Override
	public String toString() {
		return "ReturnOriginValueFormatter@"+this.hashCode();
	}
}
