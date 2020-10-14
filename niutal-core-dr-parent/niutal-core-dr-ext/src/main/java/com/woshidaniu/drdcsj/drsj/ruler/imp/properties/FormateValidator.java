/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler.imp.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.woshidaniu.drdcsj.drsj.comm.ImportErrorMessage;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.FormatResult;
import com.woshidaniu.drdcsj.drsj.formatter.FormatterFactory;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatter;
import com.woshidaniu.drdcsj.drsj.formatter.imp.CSqlFormatter;
import com.woshidaniu.drdcsj.drsj.formatter.imp.DefaultFormatterFactory;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;
import com.woshidaniu.drdcsj.drsj.ruler.imp.ValidatorDbValidator;

/**
 * @author 982
 * 格式化验证
 * 	验证子类所需参数类型
 * @author zhidong(1571)
 */
public class FormateValidator extends ValidatorDbValidator{
	
	private FormatterFactory formatter;
	
	public FormateValidator(DrpzModel drpzModel, List<DrlpzModel> drlpzList, ValidatorModel validatorModel,SqlSession sqlSession) {
		super(drpzModel, drlpzList, validatorModel, sqlSession);
		this.formatter = new DefaultFormatterFactory(sqlSession);
	}
	
	@Override
	public void doValidate(){
		
		Map<String, List<String>> dataSource = validatorModel.getDataSource();
		ImportErrorMessage errorMessage = validatorModel.getErrorMessage();
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		stopWatch.stop();
		
		for (DrlpzModel drlpzModel : drlpzList) {
			stopWatch.reset();
			stopWatch.start();
			
			String drlmc = drlpzModel.getDrlmc();
			int sfbtFlag = drlpzModel.getSfbtFlag();
			
			//当前列的数据
			List<String> dqllist = dataSource.get(drlmc);
			List<String> fomartList = new ArrayList<String>();
			fomartList.add(dqllist.get(0));
			
			//获取格式化实例
			String lsjgsh = drlpzModel.getLsjgsh();
			//formatter只能用一次
			ImportFormatter formatter = this.formatter.getFormatter(lsjgsh);
			
			for (int i = 1; i < dqllist.size(); i++) {

				//导入列值
				String dpl = dqllist.get(i);

				//不是必填,且是空值的列，则不验证 <------冯乐
				if(sfbtFlag <= 0 && StringUtils.isEmpty(dpl)) {
					fomartList.add(dpl);
					continue;
				}
				
				//错误信息
				String error = null;
				FormatResult formatResult = null;
				
				if(formatter instanceof CSqlFormatter){//如果是CSqlFormatter,需要传入当前行数据
					Map<String,String> currentRowData = new HashMap<String,String>();
					for (DrlpzModel dm : drlpzList) {
						String drl = dm.getDrl().toLowerCase();
						String data = dataSource.get(dm.getDrlmc()).get(i);
						currentRowData.put(drl,data);
					}
					
					CSqlFormatter ciif = (CSqlFormatter)formatter;
					formatResult = ciif.format(drlpzModel, currentRowData, dpl);
					if(formatResult.hasError()) {
						error = formatResult.getError();
					}
					if(StringUtils.isNotBlank(error)) {
						log.debug("导入行:{},导入列值:{},存格式化发生错误:{},格式化器:{}",i,dpl,error,formatter);
						log.debug("导入行:{},当前行数据:{}",i,currentRowData);
					}
				}else{//普通formatter
					formatResult = formatter.format(drlpzModel, dpl);
				}
				if(formatResult.hasError()) {
					errorMessage.update(i, formatResult.getError());
					fomartList.add(formatResult.getResult());
				}else {
					fomartList.add(formatResult.getResult());					
				}
			}
			dataSource.put(drlpzModel.getDrlmc(), fomartList);
			
			stopWatch.stop();
			log.debug("导入数据格式化格式化列值,名称:{}, 格式化器:{}",drlmc,formatter);
			log.debug("处理次数:{},花费时间:{}ms",dqllist.size(),stopWatch.getTime());
		}
	}
}