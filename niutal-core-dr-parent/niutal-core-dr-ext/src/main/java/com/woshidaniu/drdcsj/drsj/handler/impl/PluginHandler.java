/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.PluginModel;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.Handler;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;
import com.woshidaniu.drdcsj.drsj.ruler.plugin.AbstractCombinationValidateRule;
import com.woshidaniu.web.context.WebContext;

/**
 * @author 康康（1571）
 * 行转列，恢复之前非常蹩脚的处理思路，完全是为了适配以前的方式，废弃的类,留在这里为了以后备用
 */
@Deprecated
class PluginHandler implements Handler{
	
	private static final Logger log = LoggerFactory.getLogger(PluginHandler.class);

	private SqlSession SqlSession;
	private boolean webModel = false;
	
	public PluginHandler(SqlSession sqlSession) {
		this.SqlSession = sqlSession;
	}
	public PluginHandler(SqlSession sqlSession,boolean webModel) {
		this.SqlSession = sqlSession;
		this.webModel = webModel;
	}

	@Override
	public void handle(final HandlerContext context,final List<ExcelRow> acceptRows,final List<ExcelRow> unacceptRows){

		IImportDao importDao = this.SqlSession.getMapper(IImportDao.class);
		List<DrlpzModel> drlpzList = importDao.getDrlpzModelList(context.getSelectColumns());
		List<PluginModel> pluginModels = importDao.getPluginModelList(context.getDrmkdm());
		
		if(!pluginModels.isEmpty()) {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			
			Map<String,List<String>> allColumnDatas = this.transformToColumnsData(acceptRows);
			
			stopWatch.stop();
			log.debug("行转列,耗时:{}ms",stopWatch.getTime());
			
			this.processPlugins(acceptRows,unacceptRows,importDao,allColumnDatas,drlpzList,pluginModels,context.getDrmkdm());
		}
		
	}
	
	private void processPlugins(final List<ExcelRow> acceptRows,final List<ExcelRow> unacceptRows,IImportDao importDao,Map<String,List<String>> allColumnDatas,List<DrlpzModel> drlpzList, List<PluginModel> pluginModels,String drmkdm) {
		
		//需要移除的row
		Map<Integer,String> needRemovedRowIndexs = new LinkedHashMap<Integer,String>();
		
		for (PluginModel pluginModel : pluginModels) {
			
			pluginModel.doConfig(drlpzList);
			
			String[] drlmcs = pluginModel.getDrlmcs();
			
			if(drlmcs == null || drlmcs.length == 0){
				log.warn("导入模块代码[{}]的插入配置中的入列列名称列表是空",drmkdm);
				return;
			}
			
			AbstractCombinationValidateRule rule = pluginModel.getRule();
			if(webModel) {
				rule.setRequest((HttpServletRequest) WebContext.getRequest());				
			}
			rule.setDrlpzModelList(drlpzList);
			rule.init();
			
			Map<String,List<String>> drlVals = new HashMap<String, List<String>>();
			for(String drlmc : drlmcs) {
				
				List<String> data = allColumnDatas.get(drlmc);
				drlVals.put(drlmc, data);
			}
			
			boolean[] rs = rule.validate(drlmcs, drlVals);
			String error = this.formatError(drlmcs, pluginModel.getTsxx());
			
			for (int i = 0; i < rs.length; i++) {
				if(!rs[i]){
					needRemovedRowIndexs.put(Integer.valueOf(i), error);
				}
			}
			rule.destroy();
		}
		
		// 若真的存在需要移除的
		if (!needRemovedRowIndexs.isEmpty()) {
			log.debug("需要移除个数:{}",needRemovedRowIndexs.size());
			
			Iterator<ExcelRow> it_row = acceptRows.iterator();
			int i = -1;
			while (it_row.hasNext()) {
				ExcelRow remove_row = it_row.next();
				i++;
				String error = needRemovedRowIndexs.get(i); 
				if (error != null) {

					it_row.remove();
					
					remove_row.getCells().get(0).setError(error);
					
					unacceptRows.add(remove_row);
				}else {
					
				}
			}
			

		}
	}

	private Map<String,List<String>> transformToColumnsData(List<ExcelRow> acceptRows) {
		
		Map<String,List<String>> allColumnDatas = new HashMap<String,List<String>>();
		
		List<String> headers = new ArrayList<String>();
		
		//采集所有的excel头名称
		ExcelRow firstRow = acceptRows.get(0);
		List<ExcelCell> cells = firstRow.getCells();
		for(ExcelCell cell : cells) {
			String excelHeaderName = cell.getExcelHeaderName();
			headers.add(excelHeaderName);
		}
		//采集所有的数据
		for(String header: headers) {
			
			List<String> oneColumnDatas = new ArrayList<String>();
			allColumnDatas.put(header, oneColumnDatas);
			
			for(ExcelRow row: acceptRows) {
				List<ExcelCell> cs = row.getCells();
				for(ExcelCell c : cs) {
					if(header.equals(c.getExcelHeaderName())) {
						oneColumnDatas.add(c.getExcelCellValue());
					}
				}
			}
		}
		return allColumnDatas;
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
		return Arrays.toString(drlmcs) + message;
	}
}
