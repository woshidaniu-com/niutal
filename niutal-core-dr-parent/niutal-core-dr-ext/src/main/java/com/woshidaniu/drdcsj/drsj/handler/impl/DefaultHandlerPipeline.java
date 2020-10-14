/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;
import com.woshidaniu.drdcsj.drsj.handler.Handler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.HandlerPipeline;
import com.woshidaniu.drdcsj.drsj.handler.IdObject;
import com.woshidaniu.drdcsj.drsj.handler.PipelineExecuteListener;

/**
 * @author 康康（1571）
 * 流水线
 * 1.用户代码和框架代码交替执行
 * 	框架代码做基础工作，用户代码做个性化操作，然后再使用框架代码进行资源回收
 * 2.限定扩展点和约束用户代码
 * 3.统一插件和框架handler的事务,事务由框架管理
 */
/**public**/ final class DefaultHandlerPipeline extends IdObject implements HandlerPipeline{
	
	private static final Logger log = LoggerFactory.getLogger(DefaultHandlerPipeline.class);
	
	private boolean executed = false;
	//操作用户
	private String userName;
	//导入模块代码
	private String drmkdm;
	//导入模块名称
	private String drmkmc;
	//导入表名称
	private String drbmc;
	//操作方式
	private String crfs;
	//选择的列
	private List<String> selectColumns;
	//handler列表
	private ArrayList<Handler> handlers = new ArrayList<Handler>(16);
	//TODO 只使用这一个集合类
	//handler映射
	private LinkedHashMap<String,Handler> handlerMapping = new LinkedHashMap<String,Handler>(16);
	//接受的行
	private List<ExcelRow> acceptRows = new LinkedList<ExcelRow>();
	//不接受的行
	private List<ExcelRow> unacceptRows = new LinkedList<ExcelRow>();
	//sqlSession
	private SqlSession sqlSession;
	
	private List<PipelineExecuteListener> listeners = new ArrayList<PipelineExecuteListener>(1);

	/**
	 * @description	: 创建pipeline，一般用于创建父pipeline
	 * @param sqlSession sqlSession
	 * @param userName 操作用户
	 * @param drmkdm 导入模块代码
	 * @param drmkmc 导入模块名称
	 * @param drbmc 导入表名称
	 * @param crfs 操作方式
	 * @param selectColumns 选择的列
	 * @param acceptRows 接受的行
	 */
	public DefaultHandlerPipeline(SqlSession sqlSession,String userName,String drmkdm,String drmkmc,String drbmc,String crfs,List<String> selectColumns,List<ExcelRow> acceptRows) {
		this.sqlSession = new DelegateSqlSession(sqlSession);
		this.userName = userName;
		this.drmkdm = drmkdm;
		this.drmkmc = drmkmc;
		this.drbmc = drbmc;
		this.crfs = crfs;
		this.selectColumns = selectColumns;
		this.acceptRows = acceptRows;
	}
	
	@Override
	public void addHandler(String name,Handler handler) {
		if(name == null) {
			throw new IllegalArgumentException("name can't be null");
		}
		if(handler == null) {
			throw new IllegalArgumentException("handler can't be null");
		}
		if(this.handlerMapping.containsKey(name)) {
			throw new IllegalArgumentException("one handler nameed "+ name +" already exist in pipeline[id="+ this.getId() +"]");			
		}
		this.handlerMapping.put(name, handler);
		this.handlers.add(handler);
		int index = this.handlers.indexOf(handler);
		log.debug("pipeline[id={}]添加handler[index={}][{}]",this.getId(),index,name);
	}
	
	@Override
	public Handler getHandler(String name) {
		return this.handlerMapping.get(name);
	}

	@Override
	public void execute() {
		
		if(this.executed) {
			throw new IllegalStateException("pipeline不可重复执行");
		}
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		for(PipelineExecuteListener listener : this.listeners) {
			listener.beforeExecute(this);
		}
		
		final DefaultHandlerContext context = new DefaultHandlerContext(this.sqlSession,this.userName,this.drmkdm,this.drmkmc,this.drbmc,this.crfs,this.selectColumns);

		for(int i = 0;i<this.handlers.size();i++) {
			
			Handler h = this.handlers.get(i);
			
			StopWatch watchHandler = new StopWatch();
			watchHandler.start();
			
			String handlerInstance = h.toString();
			try {
				
				log.debug("执行handler[index={}][{}]",i,handlerInstance);
				h.handle(context, acceptRows, unacceptRows);
				
			}catch (Exception e) {
				log.error("执行handler[index={}][{}]发生异常",i,h,e);
				throw new IllegalStateException(String.format("执行handler[index=%s,instance=%s]异常",i,handlerInstance));
			}finally {
				watchHandler.stop();
				log.debug("执行handler[index={}][{}]完成，耗时{}ms",i,handlerInstance,watchHandler.getTime());
			}
		}
		
		for(PipelineExecuteListener listener : this.listeners) {
			listener.afterExecute(this);
		}
		
		this.executed = true;
		stopWatch.stop();
		log.debug("执行pipeline[id={}]完成，耗时{}ms",this.getId(),stopWatch.getTime());
	}
	
	/**
	 * @author 康康（1571）
	 * 默认HandlerContext
	 */
	class DefaultHandlerContext implements HandlerContext{
		
		private SqlSession sqlSession;
		private String userName;
		private String drmkdm;
		private String drmkmc;
		private String drbmc;
		private String crfs;
		private List<String> selectColumns;
		private Map<String,Object> attrs = new HashMap<String,Object>();
		
		public DefaultHandlerContext(SqlSession sqlSession,String userName,String drmkdm,String drmkmc,String drbmc, String crfs,List<String> selectColumns) {
			this.sqlSession = sqlSession;
			this.userName = userName;
			this.drmkdm = drmkdm;
			this.drmkmc = drmkmc;
			this.drbmc = drbmc;
			this.crfs = crfs;
			this.selectColumns = selectColumns;
		}

		@Override
		public String getDrmkdm() {
			return this.drmkdm;
		}

		@Override
		public String getDrmkmc() {
			return this.drmkmc;
		}

		@Override
		public String getDrbmc() {
			return this.drbmc;
		}

		@Override
		public String getCrfs() {
			return this.crfs;
		}
		@Override
		public List<String> getSelectColumns() {
			return this.selectColumns;
		}

		@Override
		public Object removeAttr(String key) {
			return this.attrs.remove(key);
		}

		@Override
		public Object putAttr(String key, Object value) {
			return this.attrs.put(key, value);
		}

		@Override
		public Object getAttr(String key) {
			return this.attrs.get(key);
		}

		@Override
		public SqlSession getSqlSession() {
			return this.sqlSession;
		}

		@Override
		public String getUserName() {
			return this.userName;
		}
	}

	@Override
	public void addListener(PipelineExecuteListener listener) {
		this.listeners.add(listener);
	}
}