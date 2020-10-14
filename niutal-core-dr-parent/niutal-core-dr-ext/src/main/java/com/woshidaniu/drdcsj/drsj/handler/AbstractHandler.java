/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @author 康康（1571）
 * 抽象handler,按行处理
 * 
 * 注意点
 *  1.为了数据库操作性能，必须使用context.getSqlSession获得Mapper来操作数据库，以此让mybatis缓存查询结果，减少数据库实际交互，减少验证时间
 */
public abstract class AbstractHandler implements Handler{
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void handle(final HandlerContext context,final List<ExcelRow> acceptRows,final List<ExcelRow> unacceptRows) {

		this.doHandleBefore(context,acceptRows,unacceptRows);
		
		this.doHandle(context,acceptRows,unacceptRows);
		
		this.doHandleAfter(context,acceptRows,unacceptRows);
	}

	/**
	 * @description	： doHandleBefore
	 * @param context
	 * @param acceptRows
	 * @param unacceptRows
	 */
	protected void doHandleBefore(HandlerContext context, List<ExcelRow> acceptRows, List<ExcelRow> unacceptRows) {
		
	}

	/**
	 * @description	： doHandle
	 * @param context
	 * @param acceptRows
	 * @param unacceptRows
	 */
	private void doHandle(HandlerContext context, List<ExcelRow> acceptRows, List<ExcelRow> unacceptRows) {
		
		Iterator<ExcelRow> it = acceptRows.iterator();
		while(it.hasNext()) {
			ExcelRow currentRow = it.next();
			boolean accept = this.accept(context,currentRow,acceptRows);
			
			if(accept) {//如果接受
				this.doAccept(context,acceptRows,new OnlySupportRemoveIterator(it),currentRow,unacceptRows);
			}else {//如果不接受
				this.doUnAccept(context,acceptRows,new OnlySupportRemoveIterator(it),currentRow,unacceptRows);
			}
		}
	}

	/**
	 * @description	： doHandleAfter
	 * @param context
	 * @param acceptRows
	 * @param unacceptRows
	 */
	protected void doHandleAfter(HandlerContext context, List<ExcelRow> acceptRows, List<ExcelRow> unacceptRows) {
		
	}
	
	/**
	 * @description	： 处理接受此行的操作
	 * @param context
	 * @param acceptRows
	 * @param it
	 * @param currentRow 这一行数据
	 * @param unacceptRows
	 */
	protected void doAccept(final HandlerContext context,final List<ExcelRow> acceptRows,final Iterator<ExcelRow> it,final ExcelRow currentRow,final List<ExcelRow> unacceptRows) {
		
	}

	/**
	 * @description	： 处理不接受此行的操作
	 * @param context
	 * @param acceptRows
	 * @param it
	 * @param currentRow 这一行数据
	 * @param unacceptRows
	 */
	protected void doUnAccept(final HandlerContext context,final List<ExcelRow> acceptRows,final Iterator<ExcelRow> it,final ExcelRow currentRow,final List<ExcelRow> unacceptRows) {
		it.remove();
		unacceptRows.add(currentRow);
	}

	/**
	 * @description	： 是否接受一行的数据
	 * @param context 上下文
	 * @param currentRow 当前的行数据
	 * @param allRows 所有的行数据
	 * @return 返回true表示接受,返回false表示不接受
	 */
	protected abstract boolean accept(final HandlerContext context,final ExcelRow currentRow,final List<ExcelRow> allRows);
	
	/**
	 * @description	： 只支持Remove操作的迭代器,且最多只能remove一次
	 * @author 		：康康（1571）
	 */
	private static final class OnlySupportRemoveIterator implements Iterator<ExcelRow>{
		
		private AtomicBoolean removed = new AtomicBoolean(false);
		
		private Iterator<ExcelRow> originalIterator;
		
		public OnlySupportRemoveIterator(Iterator<ExcelRow> originalIterator) {
			super();
			this.originalIterator = originalIterator;
		}
		
		@Override
		public boolean hasNext() {
			throw new UnsupportedOperationException("不支持的操作");
		}

		@Override
		public ExcelRow next() {
			throw new UnsupportedOperationException("不支持的操作");
		}

		@Override
		public void remove() {
			if(this.removed.compareAndSet(false, true)) {
				this.originalIterator.remove();				
			}else {
				throw new IllegalStateException("只支持remove一次");
			}
		}
	}
}
