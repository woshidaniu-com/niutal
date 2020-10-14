/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler.imp;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;

/**
 * @author 		：康康（1571）
 * 验证器抽象
 */
public abstract class ValidatorAbstract implements Validator{

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	protected DrpzModel drpzModel;
	protected List<DrlpzModel> drlpzList;
	protected ValidatorModel validatorModel;
	
	public ValidatorAbstract(DrpzModel drpzModel, List<DrlpzModel> drlpzList, ValidatorModel validatorModel) {
		this.drpzModel = drpzModel;
		this.drlpzList = drlpzList;
		this.validatorModel = validatorModel;
	}

	@Override
	public void validate() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		this.doValidate();
		stopWatch.stop();
		log.debug("验证器:{},验证花费时间:{}ms",this,stopWatch.getTime());
	}

	protected abstract void doValidate();
}
