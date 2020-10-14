/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.tjcx.html;


import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author ：康康（1571）
 * 
 * 默认实现
 */
public class DefaultHtmlTableToXlsParser implements HtmlTableToXlsParser{

	private static final Logger log = LoggerFactory.getLogger(DefaultHtmlTableToXlsParser.class);
	
	private Document document;
	private Elements tableElements;
	private Iterator<Element> tableIterator;
	private WritableWorkbook workbook;
	private WritableSheet sheet;
	private File newXlsFile;
	
	public DefaultHtmlTableToXlsParser(File workDir,String html) {
		this.document = Jsoup.parse(html);
		this.tableElements = document.select("table");
		this.tableIterator = this.tableElements.iterator();

		String newXlsFileName = UUID.randomUUID().toString() + ".xls";
		this.newXlsFile = new File(workDir, newXlsFileName);
		try {
			this.newXlsFile.createNewFile();
			this.workbook = Workbook.createWorkbook(this.newXlsFile);
			this.sheet = workbook.createSheet("default", 0);
		} catch (IOException e) {
			log.error("创建Workbook异常", e);
		}
	}

	@Override
	public File parse() {

		Cursor cursor = new Cursor(this.sheet);

		Element table = this.tableIterator.next();
		Elements theadElements = table.select("thead");
		Element threadElement = theadElements.iterator().next();
		try {
			this.parseThead(threadElement, cursor);
		} catch (Exception e) {
			log.error("解析head标签异常",e);
		}

		Elements trs = table.select("tbody>tr");
		try {
			this.parseTr(trs,cursor);
		} catch (Exception e1) {
			log.error("解析tr标签异常",e1);
		}

		try {
			this.workbook.write();
			this.workbook.close();
		} catch (Exception e) {
			log.error("写xls文件异常",e);
		}
		
		return this.newXlsFile;
	}

	private void parseThead(Element threadElement, Cursor cursor) throws Exception {
		Elements trElements = threadElement.select("tr");
		Iterator<Element> it = trElements.iterator();
		
		while (it.hasNext()) {
			Element tr = it.next();
			Elements thElements = tr.select("th");

			for (int k = 0; k < thElements.size(); k++) {

				Element th = thElements.get(k);
				String rowspanStr = th.attr("rowspan");

				if (StringUtils.isBlank(rowspanStr)) {
					rowspanStr = "1";
				}
				String colspanStr = th.attr("colspan");
				if (StringUtils.isBlank(colspanStr)) {
					colspanStr = "1";
				}
				if(rowspanStr.equals("0")) {
					rowspanStr = "1";
				}
				if(colspanStr.equals("0")) {
					colspanStr = "1";
				}
				int rowspan = Integer.parseInt(rowspanStr);
				int colspan = Integer.parseInt(colspanStr);
				String text = th.text();

				// colspan和rowspan都是1的情况
				if (colspan == 1 && rowspan == 1) {
					String content = text;
					Label label = new Label(cursor.current.col, cursor.current.row, content,generateWritableCellFormat());
					sheet.addCell(label);
					sheet.setColumnView(cursor.current.col, 20);
					cursor.right();
				}

				// colspan大于1的情况,向右复制块
				if (colspan > 1) {
					int startCol = cursor.current.col;
					int startRow = cursor.current.row;
					for (int j = 0; j < colspan; j++) {
						String content = text;
						if (j > 0) {
							content = "";
						}
						Label label = new Label(cursor.current.col, cursor.current.row, content,generateWritableCellFormat());
						sheet.addCell(label);
						sheet.setColumnView(cursor.current.col, 20);
						cursor.right();
					}
					sheet.mergeCells(startCol,startRow,cursor.current.col-1, cursor.current.row);
				}

				if(rowspan > 1) {
					// rowspan大于1的情况，向下复制块
					Cursor colCursor = cursor.fork();
					for (int i = 0; i < rowspan; i++) {
						String content = text;
						if (i > 0) {
							content = "";
						}
						Label label = new Label(colCursor.current.col, colCursor.current.row, content,generateWritableCellFormat());
						sheet.addCell(label);
						sheet.setColumnView(cursor.current.col, 20);
						colCursor.down();
					}
					sheet.mergeCells(cursor.current.col, cursor.current.row, colCursor.current.col, colCursor.current.row-1);
					cursor.right();
				}
				
				if (k == thElements.size() - 1) {
					// 下一步
					cursor.next();
				}
			}
		}
	}

	/**
	 * @description ： 解析tr标签集合
	 * @author ： 康康（1571）
	 * @date ：2018年7月4日 上午9:02:22
	 * @param trs
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private void parseTr(Elements trs,Cursor cursor) throws Exception {

		//复位到本行开始
		cursor.resetColumn();
		
		for(int i=0;i<trs.size();i++) {
			
			Element tr = trs.get(i);
			Elements tds = tr.select("td");
			
			//构成一行数据
			for(int j=0;j<tds.size();j++) {
				
				Element td = tds.get(j);
				//处理rowspan有可能的数据，占据几行就向下几次空单元格
				String rowspan = td.attr("rowspan");
				Cursor below = null;
				if(StringUtils.isNotBlank(rowspan)) {
					int rowspanCount = Integer.valueOf(rowspan);
					int belowCount = rowspanCount  - 1;
					for(int k=0;k<belowCount;k++) {
						if(below == null) {
							//首次向下分裂
							below = cursor.fork();							
						}else {
							//已经向下分裂
							below = below.fork();							
						}
						//向下
						below.down();
						
						Label label = new Label(below.current.col, below.current.row,"",generateWritableCellFormat());
						sheet.addCell(label);
						sheet.setColumnView(below.current.col, 20);
						
					}
				}
				String text = td.text();
				Label label = new Label(cursor.current.col, cursor.current.row, text,generateWritableCellFormat());
				sheet.addCell(label);
				sheet.setColumnView(cursor.current.col, 20);
				
				//合并向下的
				if(StringUtils.isNotBlank(rowspan)) {
					sheet.mergeCells(cursor.current.col, cursor.current.row, below.current.col, below.current.row);
				}
				
				//处理colspan有可能的数据，占据几列就向右几次空单元格
				String colspan = td.attr("colspan");
				Cursor right = null;
				if(StringUtils.isNotBlank(colspan)) {
					int colspanCount = Integer.valueOf(colspan);
					int rightCount = colspanCount  - 1;
					for(int k=0;k<rightCount;k++) {
						if(right == null) {
							//首次向右分裂
							right = cursor.fork();					
						}else {
							//已经向右分裂
							right = right.fork();							
						}
						//向右
						right.right();
						
						Label rightLabel = new Label(right.current.col, right.current.row,"",generateWritableCellFormat());
						sheet.addCell(rightLabel);
						sheet.setColumnView(right.current.col, 20);
					}
				}
				//合并向右的
				if(StringUtils.isNotBlank(colspan)) {
					sheet.mergeCells(cursor.current.col, cursor.current.row, right.current.col, right.current.row);
					cursor.right();
				}
				
				//FIXME 既有colspan，也有rowspan怎么办？
				
				cursor.right();
				
				if(j == tds.size() - 1) {
					// 下一步
					cursor.next();
				}
			}
		}
	}

	/**
	 * 
	 * @className ： Cursor
	 * @description ： xls的cell的游标，具有向前和向下的操作，具有记忆功能，走过的路径绝不再走第二遍
	 * @author ：康康（1571）
	 * @date ： 2018年7月4日 下午12:38:42
	 * @version V1.0
	 */
	private class Cursor {

		private WritableSheet sheet;
		private Position current;

		private Set<Position> history = new HashSet<Position>();

		public Cursor(WritableSheet sheet) {
			super();
			this.sheet = sheet;
			this.current = new Position(0, 0);
			this.history.add(current);
		}

		/**
		 * @description ： 分裂cursor
		 * @author ： 康康（1571）
		 * @date ：2018年7月4日 下午2:35:59
		 * @return
		 */
		public Cursor fork() {
			Cursor fork = new Cursor(this.sheet);
			fork.current = this.current;
			fork.history = history;
			return fork;
		}

		/**
		 * @description	： 找个没有使用的块，从下一行的第一列开始递归寻找
		 * @author 		： 康康（1571）
		 * @date 		：2018年7月4日 下午3:59:35
		 */
		public void next() {
			int newCol = 0;
			int newRow = this.current.row + 1;
			doNext(newCol, newRow);
		}
		
		/**
		 * @description	： 复位到本列的开始
		 * @author 		： 康康（1571）
		 * @date 		：2018年7月4日 下午4:21:21
		 */
		public void resetRow() {
			Position newP = new Position(this.current.col, 0);
			this.current = newP;
			this.history.add(this.current);
		}
		
		/**
		 * @description	： 复位到本行的开始
		 * @author 		： 康康（1571）
		 * @date 		：2018年7月4日 下午4:19:38
		 */
		public void resetColumn() {
			Position newP = new Position(0, this.current.row);
			this.current = newP;
			this.history.add(this.current);
		}

		/**
		 * @description	： 递归寻找下一个没有走过的块
		 * @author 		： 康康（1571）
		 * @date 		：2018年7月4日 下午4:06:25
		 * @param newCol
		 * @param newRow
		 */
		private void doNext(int newCol,int newRow) {
			Position newP = new Position(newCol, newRow);
			if (this.history.contains(newP)) {
				doNext(newCol+1,newRow);
			}else {
				this.current = newP;
				this.history.add(current);
			}
		}

		/**
		 * @description ： 向下一步
		 * @author ： 康康（1571）
		 * @date ：2018年7月4日 下午12:40:25
		 */
		public void down() {
			int newRow = this.current.row + 1;
			Position newPosition = new Position(this.current.col, newRow);
			this.current = newPosition;
			this.history.add(this.current);
		}

		/**
		 * @description ： 向前一步
		 * @author ： 康康（1571）
		 * @date ：2018年7月4日 下午12:41:50
		 */
		public void right() {
			int newCol = this.current.col + 1;
			Position newPosition = new Position(newCol, this.current.row);
			this.current = newPosition;
			this.history.add(this.current);
		}

		@Override
		public String toString() {
			return "Cursor [current=" + current + "]";
		}

	}

	private static class Position {
		//列号
		final int col;
		//行号
		final int row;

		public Position(int col, int row) {
			super();
			this.col = col;
			this.row = row;
		}

		@Override
		public String toString() {
			return "Position [col=" + col + ", row=" + row + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + col;
			result = prime * result + row;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (col != other.col)
				return false;
			if (row != other.row)
				return false;
			return true;
		}
	}

	private WritableCellFormat generateWritableCellFormat(){
		
		// 定义格式 字体 下划线 斜体 粗体 颜色
		WritableFont WF_TITLE = new WritableFont(WritableFont.ARIAL, 11,WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); 
		
		// 单元格定义
		WritableCellFormat WCF_TITLE = new WritableCellFormat(WF_TITLE);
		try {
			//水平方向对齐
			WCF_TITLE.setAlignment(jxl.format.Alignment.CENTRE);
			//垂直方向对齐
			WCF_TITLE.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			// 边框
			WCF_TITLE.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK); 
		} catch (WriteException e) {
			log.error("",e);
		}
		return WCF_TITLE;
	}
}
