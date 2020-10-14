/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler.imp.properties;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;

import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.PluginModel;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;
import com.woshidaniu.drdcsj.drsj.ruler.imp.ValidatorAbstract;
import com.woshidaniu.drdcsj.drsj.ruler.plugin.AbstractCombinationValidateRule;
import com.woshidaniu.web.context.WebContext;

/**
 * 插件验证
 */
public class PluginValidator extends ValidatorAbstract{
	
	private List<PluginModel> pluginModelList;

	public PluginValidator(DrpzModel drpzModel, List<DrlpzModel> drlpzList, ValidatorModel validatorModel,List<PluginModel> pluginModelList) {
		super(drpzModel, drlpzList, validatorModel);
		this.pluginModelList = pluginModelList;
	}

	@Override
	public void doValidate(){
		
		Map<String, List<String>> dataSource = validatorModel.getDataSource();
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		stopWatch.stop();
		
		for (PluginModel pluginModel : pluginModelList) {
			
			stopWatch.reset();
			stopWatch.start();
			
			String[] drlmcs = pluginModel.getDrlmcs();
			
			if(drlmcs == null || drlmcs.length == 0){
				return;
			}
			
			int dataSize = dataSource.get(drlmcs[0]).size();
			
			AbstractCombinationValidateRule rule = pluginModel.getRule();
			rule.setRequest((HttpServletRequest) WebContext.getRequest());
			rule.setDrlpzModelList(drlpzList);
			rule.init();
			Map<String,List<String>> drlVals = new HashMap<String, List<String>>();
			for (int current = 1; current < dataSize;) {
				drlVals.clear();
				for (String drlmc : drlmcs) {
					List<String> list = null;
					if((current + ImportConfig._Thresholds) >= dataSize){
						list = dataSource.get(drlmc).subList(current, dataSource.get(drlmc).size());
					}else{
						list = dataSource.get(drlmc).subList(current, current + ImportConfig._Thresholds);
					}
					drlVals.put(drlmc, list);
				}
				boolean[] rs = rule.validate(drlmcs, drlVals);
				for (int i = 0; i < rs.length; i++) {
					if(!rs[i]){
						validatorModel.getErrorMessage().update((i+current), formatError(drlmcs, pluginModel.getTsxx()));
					}
				}
				current += ImportConfig._Thresholds;
			}
			rule.destroy();
			
			stopWatch.stop();
			log.info("插件{}验证执行",pluginModel);
			log.info("耗时:{}ms",stopWatch.getTime());
		}
	}
	
	/**
	 * @param drlmcs
	 * @param message
	 * @return
	 */
	private String formatError(String[] drlmcs, String message) {
		if (StringUtils.isBlank(message)) {
			message = "错误提示未定义！";
		}
		return  Arrays.toString(drlmcs) + message;
	}
}
