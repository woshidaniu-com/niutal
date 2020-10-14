package com.woshidaniu.common.query;


@SuppressWarnings("serial")
public class QueryModel extends com.woshidaniu.basemodel.QueryModel {

	// 每页显示记录数
	private int showCount = 10; 
	// 总记录数
	private int totalResult; 
	// 当前页
	private int currentPage; 
	// 当前记录起始索引
	private int currentResult; 
	// true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	private boolean entityOrField;
	//最终页面显示的底部翻页导航，详细见：getPageStr();
	// private String pageStr;
	private String sortNamePro;
	
	// 导出SQL语句
	private String expSql; 
	// 记录数查询SQL语句ID
	private String countSqlId; 

	public String getExpSql() {
		return expSql;
	}

	public void setExpSql(String expSql) {
		this.expSql = expSql;
	}

	public int getCurrentPage() {
		if (currentPage <= 0) {
			currentPage = 1;
		}
		if (currentPage >= getTotalPage() && getTotalPage() != 0) {
			currentPage = getTotalPage();
		}
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	@Override
	public int getTotalPage() {
		if (totalResult % showCount == 0) {
			totalPage = totalResult / showCount;
		} else {
			totalPage = totalResult / showCount + 1;
		}
		return totalPage;
	}

	@Override
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getCurrentResult() {
		currentResult = (getCurrentPage() - 1) * getShowCount();
		if (currentResult < 0) {
			currentResult = 0;
		}
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}
	

	public String getCountSqlId() {
		return countSqlId;
	}

	public void setCountSqlId(String countSqlId) {
		this.countSqlId = countSqlId;
	}
	
	/*
	 * public String getSortName() { return sortName; } public void
	 * setSortName(String sortName) { this.sortName = sortName; } public String
	 * getSortOrder() { return sortOrder; } public void setSortOrder(String
	 * sortOrder) { this.sortOrder = sortOrder; }
	 */
	public String getSortNamePro() {
		return sortNamePro;
	}

	public void setSortNamePro(String sortNamePro) {
		this.sortNamePro = sortNamePro;
	}

}
