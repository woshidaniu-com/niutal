/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.woshidaniu.common.file.TempFileService;
import com.woshidaniu.drdcsj.drsj.formatter.FormatterFactory;
import com.woshidaniu.drdcsj.drsj.formatter.imp.CacheFormatterFactory;
import com.woshidaniu.drdcsj.drsj.formatter.imp.DefaultFormatterFactory;
import com.woshidaniu.drdcsj.drsj.formatter.imp.TimeFormatterFactory;
import com.woshidaniu.drdcsj.drsj.handler.FlushDatabaseListener;
import com.woshidaniu.drdcsj.drsj.handler.Handler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerPipeline;
import com.woshidaniu.drdcsj.drsj.handler.HandlerPipelineFactory;
import com.woshidaniu.drdcsj.drsj.handler.PipelineExecuteListener;
import com.woshidaniu.drdcsj.drsj.handler.PluginHandler;
import com.woshidaniu.drdcsj.drsj.handler.PluginListener;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @description	： DefaultPipelineFactory
 * @author 		：康康（1571）
 */
public final class DefaultHandlerPipelineFactory implements HandlerPipelineFactory{
	
	private static final Logger log = LoggerFactory.getLogger(DefaultHandlerPipelineFactory.class);
	
	private ApplicationContext applicationContext;
	
	private boolean useCacheFormatterFactory = true;
	private boolean useTimeFormatterFactory = true;
	
	public DefaultHandlerPipelineFactory(ApplicationContext applicationContext,boolean useCacheFormatterFactory,boolean useTimeFormatterFactory) {
		this.applicationContext = applicationContext;
		this.useCacheFormatterFactory = useCacheFormatterFactory;
		this.useTimeFormatterFactory = useTimeFormatterFactory;
	}

	@Override
	public HandlerPipeline newPipeline(SqlSession sqlSession,String userName,String drmkdm,String drmkmc,String drbmc,String crfs,List<String> selectColumns,List<ExcelRow> acceptRows) {
		
		HandlerPipeline pipeline = new DefaultHandlerPipeline(sqlSession,userName,drmkdm,drmkmc,drbmc,crfs,selectColumns,acceptRows);
		
		//以下handler依次执行,可改变顺序来决定业务走向
		
		//trim操作,对Excel内的所有Cell进行trim处理
		//若为null则改成""
		pipeline.addHandler(PrepareHandler.class.getName(),new PrepareHandler());
		
		//render导入配置列
		pipeline.addHandler(RenderDrpzl2ExcelCellHandler.class.getName(),new RenderDrpzl2ExcelCellHandler());
		
		//收集主键列,始终接受
		pipeline.addHandler(PrimaryKeyExcelCollectorHandler.class.getName(),new PrimaryKeyExcelCollectorHandler());
		
		//验证excel内主键的空值,始终接受，但还是要设置cell的错误
		pipeline.addHandler(PrimaryKeyEmptyExcelValidatorHandler.class.getName(),new PrimaryKeyEmptyExcelValidatorHandler());
		
		//验证excel内主键的重复,始终接受，但还是要设置cell的错误
		pipeline.addHandler(PrimaryKeyDuplicateExcelValidatorHandler.class.getName(),new PrimaryKeyDuplicateExcelValidatorHandler());
		
		//验证主键列在数据库中的重复验证
		//设置插入或删除标志,需要根据主键查询数据库,设置每行是插入还是更新
		//如果是插入操作,但数据库存在,则直接拒绝
		//如果是更新操作,但数据库没有这一行数据,则直接拒绝
		//如果是插入或更新,则数据库存在这一行数据,就认定是更新操作,若数据库不存在这一行数据,则认定是插入操作
		pipeline.addHandler(PrimaryKeyDuplicateDatabaseValidatorHandler.class.getName(),new PrimaryKeyDuplicateDatabaseValidatorHandler());
		
		//采集唯一键,始终接受
		pipeline.addHandler(UniqueKeyExcelCollectorHandler.class.getName(),new UniqueKeyExcelCollectorHandler());
		
		//验证excel内唯一键的重复,始终接受，但还是要设置cell的错误
		pipeline.addHandler(UniqueKeyDuplicateExcelValidatorHandler.class.getName(),new UniqueKeyDuplicateExcelValidatorHandler());
		
		//验证数据库内唯一键的重复,始终接受，但还是要设置cell的错误
		//这里必须要放在PrimaryKeyDuplicateDatabaseValidatorHandler后面
		//因为只有决定了是更新还是插入才能判断是否重复
		pipeline.addHandler(UniqueKeyDuplicateDatabaseValidatorHandler.class.getName(),new UniqueKeyDuplicateDatabaseValidatorHandler());
		
		//必填列验证,始终接受，但还是要设置cell的错误
		pipeline.addHandler(MustInputExcelValidatorHandler.class.getName(),new MustInputExcelValidatorHandler());
		
		//最大长度验证,始终接受，但还是要设置cell的错误
		pipeline.addHandler(MaxLengthExcelValidatorHandler.class.getName(),new MaxLengthExcelValidatorHandler());

		//默认格式化器工厂
		FormatterFactory formatterFactory = new DefaultFormatterFactory(sqlSession);
		
		//进行缓存,减少创建对象的消耗,DefaultFormatterFactory创建了太多对象,需要减轻GC的压力
		if(this.useCacheFormatterFactory) {
			formatterFactory = new CacheFormatterFactory(formatterFactory);			
		}

		//对各种Formatter进行操作计时,按类名称统计操作时间
		if(this.useTimeFormatterFactory) {
			formatterFactory = new TimeFormatterFactory(formatterFactory);			
		}
		
		if(this.useTimeFormatterFactory && formatterFactory instanceof TimeFormatterFactory) {
			
			final FormatterFactory f = formatterFactory;
			
			pipeline.addListener(new PipelineExecuteListener() {
				
				@Override
				public void beforeExecute(final HandlerPipeline handlerPipeline) {
					
				}
				
				@Override
				public void afterExecute(final HandlerPipeline handlerPipeline) {
					TimeFormatterFactory timeFormatterFactory = (TimeFormatterFactory)f;
					timeFormatterFactory.tips();
				}
			});
		}
		
		//组合格式化验证,始终接受，但还是要设置cell的错误
		pipeline.addHandler(CompositieFormatorValidatorHandler.class.getName(),new CompositieFormatorValidatorHandler(formatterFactory));
		
		//插件handler执行,额外的用户代码插件
		List<Handler> pluginHandlers = this.getHandler(drmkdm);
		
		if(!pluginHandlers.isEmpty()) {
			int i = -1;
			for(Handler h : pluginHandlers) {
				String name = h.getClass().getName()+"_"+(++i);
				pipeline.addHandler(name,h);
			}			
		}
		
		//检查一遍是否有错误的Cell,如果有错误,就搬迁到unaccpetRows集合中,prefect！！！
		//这里才搬迁错误数据到unaccpetRows集合就是为了能够暴露出所有的错误到excel中
		pipeline.addHandler(AwaysCheckAllCellErrorHandler.class.getName(),new AwaysCheckAllCellErrorHandler());			
		
		//结果处理,数据库操作
		//监听器
		List<FlushDatabaseListener> listeners = this.getListener(drmkdm);
		//刷新数据到数据库,这里即使插入数据库发生数据库异常,也会填充简略异常信息到Excel
		pipeline.addHandler(SimpleFlushDatabaseHandler.class.getName(),new SimpleFlushDatabaseHandler(sqlSession,listeners));
		
		TempFileService tempFileService = this.applicationContext.getBean(TempFileService.class);
		
		//汇总采集,结果存放在ResultHandler中
		ResultHandler resultHandler = new ResultHandler(tempFileService);
		pipeline.addHandler(ResultHandler.class.getName(),resultHandler);
		
		return pipeline;
	}
	
	/**
	 * @description	： 获得监听器
	 * @param context
	 * @return
	 */
	protected List<FlushDatabaseListener> getListener(String drmkdm) {
		final String[] names = this.applicationContext.getBeanNamesForAnnotation(PluginListener.class);
		
		if(names == null || names.length <= 0) {
			return Collections.emptyList();
		}
		
		//针对匹配的Listener,最多也就两个
		final List<FlushDatabaseListener> hs = new ArrayList<FlushDatabaseListener>(2);
		final Map<FlushDatabaseListener,PluginListener> mapper = new HashMap<FlushDatabaseListener,PluginListener>(2);
		
		for(String name:names) {
			FlushDatabaseListener h = this.applicationContext.getBean(name, FlushDatabaseListener.class);
			PluginListener ph = h.getClass().getAnnotation(PluginListener.class);
			
			if(ph != null) {
				boolean enable = ph.enable();
				String[] drmkdm_in_handlers = ph.drmkdm();
				Set<String> drmkdmSet = new HashSet<String>(Arrays.asList(drmkdm_in_handlers));
				if(drmkdmSet.contains(drmkdm) && enable) {
					hs.add(h);
					mapper.put(h, ph);
				}
			}
		}
		if(!hs.isEmpty()) {
			Collections.sort(hs, new Comparator<FlushDatabaseListener>() {

				@Override
				public int compare(FlushDatabaseListener h1, FlushDatabaseListener h2) {
					PluginListener p1 = mapper.get(h1);
					PluginListener p2 = mapper.get(h2);
					
					Integer order1 = p1.order();
					Integer order2 = p2.order();
					
					return order1.compareTo(order2);
				}
			});
			
			if(log.isDebugEnabled()) {
				for(FlushDatabaseListener h : hs) {
					PluginListener ph = mapper.get(h);
					log.debug("根据drmkdm[{}]从SpringContext得到FlushDatabaseListener:[{}] -->order:[{}],enable:[{}]",drmkdm,h,ph.order(),ph.enable());
				}
			}
		}
		return hs;
	}
	
	/**
	 * @description	： 获得插件handler
	 * @param drmkdm
	 * @return
	 */
	protected List<Handler> getHandler(String drmkdm) {
		final String[] names = this.applicationContext.getBeanNamesForAnnotation(PluginHandler.class);
		
		if(names == null || names.length <= 0) {
			return Collections.emptyList();
		}
		
		//针对匹配的PluginHandler,最多也就两个
		final List<Handler> hs = new ArrayList<Handler>(2);
		final Map<Handler,PluginHandler> mapper = new HashMap<Handler,PluginHandler>(2);
		
		for(String name:names) {
			Handler h = this.applicationContext.getBean(name, Handler.class);
			PluginHandler ph = h.getClass().getAnnotation(PluginHandler.class);
			
			if(ph != null) {
				boolean enable = ph.enable();
				String[] drmkdm_in_handlers = ph.drmkdm();
				Set<String> drmkdmSet = new HashSet<String>(Arrays.asList(drmkdm_in_handlers));
				if(drmkdmSet.contains(drmkdm) && enable) {
					hs.add(h);
					mapper.put(h, ph);
				}
			}
		}
		if(!hs.isEmpty()) {
			Collections.sort(hs, new Comparator<Handler>() {

				@Override
				public int compare(Handler h1, Handler h2) {
					PluginHandler p1 = mapper.get(h1);
					PluginHandler p2 = mapper.get(h2);
					
					Integer order1 = p1.order();
					Integer order2 = p2.order();
					
					return order1.compareTo(order2);
				}
			});
			
			if(log.isDebugEnabled()) {
				for(Handler h : hs) {
					PluginHandler ph = mapper.get(h);
					log.debug("根据drmkdm[{}]从SpringContext得到handler:[{}] -->order:[{}],enable:[{}]",drmkdm,h,ph.order(),ph.enable());
				}
			}
		}
		return hs;
	}
}
