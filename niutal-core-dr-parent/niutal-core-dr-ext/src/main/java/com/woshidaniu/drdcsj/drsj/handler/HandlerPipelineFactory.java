/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @description	：PipelineFactory 
 * @author 		：康康（1571）
 */
public interface HandlerPipelineFactory {

	/**
	 * @description	： 创建新pipeline
	 * @param sqlSession
	 * @param userName
	 * @param drmkdm
	 * @param drmkmc
	 * @param drbmc
	 * @param crfs
	 * @param selectColumns
	 * @param acceptRows
	 * @return
	 */
	HandlerPipeline newPipeline(SqlSession sqlSession,String userName,String drmkdm,String drmkmc,String drbmc,String crfs,List<String> selectColumns,List<ExcelRow> acceptRows);
}
