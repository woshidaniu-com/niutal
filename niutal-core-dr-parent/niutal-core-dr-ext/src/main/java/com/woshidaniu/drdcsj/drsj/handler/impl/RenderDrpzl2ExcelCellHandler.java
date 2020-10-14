/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrFzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.excel.DrfzQuery;
import com.woshidaniu.drdcsj.drsj.handler.Handler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;
import com.woshidaniu.drdcsj.drsj.svcinterface.impl.SfbtColumnSwitchProcessor;

/**
 * @author 康康（1571）
 * 填充 导入列配置对象 到cell中
 */
/**public**/ final class RenderDrpzl2ExcelCellHandler implements Handler{
	
	private static final Logger log = LoggerFactory.getLogger(RenderDrpzl2ExcelCellHandler.class);
	
	public static final String FZ_DATA_KEY = RenderDrpzl2ExcelCellHandler.class.getName()+"_FZ_DATA_KEY";
	
	private Map<String, List<Map<String, String>>> fzData = new HashMap<String, List<Map<String, String>>>();
	
	@Override
	public void handle(final HandlerContext context,final List<ExcelRow> acceptRows,final List<ExcelRow> unacceptRows/*此时这个列表的size应该是0*/) {
		
		String drmkdm = context.getDrmkdm();
		
		IImportDao importDao = context.getSqlSession().getMapper(IImportDao.class);
		
		DrpzModel drpzModel = importDao.getDrPzxx(drmkdm).get(0);
		
		List<String> selectColumn = context.getSelectColumns();
		// 导入列配置
		List<DrlpzModel> drlpzList = importDao.getDrlpzModelList(selectColumn);
		
		for(DrlpzModel drlpz : drlpzList) {
			//提示文本
			String constraintMessage = getComments(drpzModel,drlpz,context.getCrfs());
			if(log.isTraceEnabled()) {
				log.trace("表头[{}]提示文本:[{}]",drlpz.getDrlmc(),constraintMessage);
			}
			drlpz.setConstraintMessage(constraintMessage);
		}
		
		//导入列名称map
		Map<String/**drlmc**/,DrlpzModel> drlmcMap = new HashMap<String,DrlpzModel>();
		//导入列map
		Map<String/**drl**/,DrlpzModel> drlMap = new HashMap<String,DrlpzModel>();
		
		//只处理选中的列
		Set<String> selectColumnSet = new HashSet<String>(selectColumn);
		
		for(DrlpzModel pz : drlpzList) {
			
			String drlpzid = pz.getDrlpzid();
			if(selectColumnSet.contains(drlpzid)) {
				drlmcMap.put(pz.getDrlmc(), pz);
				drlMap.put(pz.getDrl(), pz);				
			}
		}
		
		//辅助列
		List<DrFzModel> drfzModelList = importDao.getDrFzModelList(drmkdm);
		
		for(DrFzModel drfzModel : drfzModelList) {
			
			String drl = drfzModel.getDrl();
			DrlpzModel drlpzModel = drlMap.get(drl);

			// 名称
			String fzmc = drfzModel.getFzmc();
			// 数据抓取配置
			String pz = drfzModel.getPz();
			// 方式
			String[] array = StringUtils.split(pz, ":", 2);
			String type = array[0];
			// 目标
			String source = array[1];
			if(StringUtils.isBlank(drl)) {
				DrfzQuery fzQuery = new DrfzQuery(type, source);
				fzData.put(fzmc, fzQuery.queryFzData());
			}else{
				DrfzQuery fzQuery = new DrfzQuery(type, source);
				drlpzModel.setDropdownValues(fzQuery.queryFzDrlData());
			}
		}
		
		for(ExcelRow currentRow : acceptRows) {
			List<ExcelCell> cells = currentRow.getCells();
			for(ExcelCell cell : cells) {
				String h = cell.getExcelHeaderName();
				
				DrlpzModel drlpz = drlmcMap.get(h);
				cell.setDrlpzModel(drlpz);
			}
		}
		
		context.putAttr(FZ_DATA_KEY, fzData);
	}
	
	/**
	 * 给字段标题添加提示信息
	 * @param dm
	 * @return
	 */
	private static String getComments(DrpzModel drpzModel,DrlpzModel dm,final String crfs) {
		String sfzj = dm.getSfzj();
		String zdcd = dm.getZdcd();
		//TODO 不得已用这个AtomicInteger，方便递增
		final AtomicInteger index = new AtomicInteger(1);
		final StringBuffer comments = new StringBuffer();
		if (ImportConfig._SFZJ_YES.equals(sfzj)) {
			comments.append(index.getAndIncrement()).append(". ").append("不能重复; \n");
		}
		int sfbtColumnFlag = dm.getSfbtFlag();
		final String notNullComment = ". 不可为空; \n";
		// 验证当前字段是否必填
		SfbtColumnSwitchProcessor switchProcessor = new SfbtColumnSwitchProcessor() {
			
			private boolean appended = false; 
			@Override
			protected void ifBtOnInsert() {
				if(!appended) {
					comments.append(index.getAndIncrement()).append(notNullComment);					
					appended = true;
				}
			}
			@Override
			protected void ifBtOnUpdate() {
				if(!appended) {
					comments.append(index.getAndIncrement()).append(notNullComment);					
					appended = true;
				}
			}
			@Override
			protected void ifBtOnInsertAndUpdate() {
				if(!appended) {
					comments.append(index.getAndIncrement()).append(notNullComment);					
					appended = true;
				}
			}
		};
		switchProcessor.process(crfs, sfbtColumnFlag);
		
		if (StringUtils.isNotBlank(zdcd)) {
			comments.append(index.getAndIncrement()).append(". ").append("最大长度为" + zdcd + ";");
		}
		return comments.toString();
	}
}
