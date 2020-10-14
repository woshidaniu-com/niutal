/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.svcinterface.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.handler.HandlerPipeline;
import com.woshidaniu.drdcsj.drsj.handler.HandlerPipelineFactory;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;
import com.woshidaniu.drdcsj.drsj.handler.impl.DefaultHandlerPipelineFactory;
import com.woshidaniu.drdcsj.drsj.handler.impl.ResultHandler;
import com.woshidaniu.drdcsj.drsj.svcinterface.IImportService;
import com.woshidaniu.util.base.MessageUtil;

/**
 * @author 康康（1571）
 * Excel服务实现类v2版本
 * 1.连续导入(保留正确数据,错误数据Excel给出下载,用户可继续修改错误数据Excel并进行上传)
 * 2.更加清晰的错误提示,错误单元格标红提示
 */
@Service(value = "drdcsjServiceV2")
public class ExcelImportServiceV2 implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	@Autowired
	@Qualifier("sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;
	/**
	 * 是否缓存formatter 
	 */
	private boolean useCacheFormatterFactory = true;
	/**
	 * 是否统计每种formatter的操作时间 
	 */
	private boolean useTimeFormatterFactory = true;
	
	private HandlerPipelineFactory handlerPipelineFactory = null;
	
	@PostConstruct
	public void init() {
		{
			String val = MessageUtil.getText("niutal.dr.v2.useCacheFormatterFactory");
			this.useCacheFormatterFactory = StringUtils.isNotEmpty(val) ? Boolean.parseBoolean(val) : this.useCacheFormatterFactory;
		}
		
		{
			String val = MessageUtil.getText("niutal.dr.v2.useTimeFormatterFactory");
			this.useTimeFormatterFactory = StringUtils.isNotEmpty(val) ? Boolean.parseBoolean(val) : this.useTimeFormatterFactory;
		}
		
		{
			this.handlerPipelineFactory = new DefaultHandlerPipelineFactory(applicationContext,useCacheFormatterFactory,useTimeFormatterFactory);
		}
	}

	/**
	 * @description	： 导入数据v2版本
	 * @param userName
	 * @param drmkdm
	 * @param crfs
	 * @param selectColumns
	 * @param rows
	 * @param workDir
	 * @return
	 */
	public Map<String,String> importData(String userName,String drmkdm,String crfs,List<String> selectColumns,List<ExcelRow> rows) {
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		IImportDao importDao = sqlSession.getMapper(IImportDao.class);
		List<DrpzModel> list = importDao.getDrPzxx(drmkdm);

		DrpzModel drpzModel = null;
		if(list.size() <= 0) {
			//意外情况,开发环境未正确配置导致,只抛运行时异常
			throw new IllegalStateException("没有配置drmkdm为"+ drmkdm +"的配置");
		}else if(list.size() == 1){
			drpzModel = list.get(0);
		}else {
			throw new IllegalStateException("存在多个配置drmkdm为"+ drmkdm +"的配置的配置");
		}
		String drmkmc = drpzModel.getDrmkmc();
		String drbmc = drpzModel.getDrbmc();
		
		HandlerPipeline pipeline = handlerPipelineFactory.newPipeline(sqlSession,userName,drmkdm,drmkmc,drbmc,crfs,selectColumns,rows);
		
		try {
			//执行
			pipeline.execute();
		}catch (IllegalStateException e) {
			throw e;
		}finally {
			//关闭sqlSession
			sqlSession.close();
		}
		
		ResultHandler resultHandler = (ResultHandler) pipeline.getHandler(ResultHandler.class.getName());
		
		//resultHandler提取结果
		Map<String,String> resultMap = new HashMap<String,String>(8);
		resultMap.put(IImportService.VersionKey, IImportService.Version_v2);
		resultMap.put(IImportService.ResultKey, "1");
		resultMap.put(IImportService.ResultFileIdKey, resultHandler.getResultFileId());
		resultMap.put(IImportService.TotalSizeKey,String.valueOf(resultHandler.getTotalSize()));
		resultMap.put(IImportService.SuccessInsertRowsSizeKey, String.valueOf(resultHandler.getSuccessInsertRowsSize()));
		resultMap.put(IImportService.SuccessUpdateRowsSizeKey, String.valueOf(resultHandler.getSuccessUpdateRowsSize()));
		resultMap.put(IImportService.TotalUnAcceptRowSizeKey, String.valueOf(resultHandler.getTotalUnAcceptRowSize()));
		return resultMap;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
