package com.woshidaniu.search.service.svcinterface;


import java.util.List;

import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchLinkageModel;
import com.woshidaniu.search.dao.entities.SearchModel;


/**
 * 高级查询
 * @author Penghui.Qu
 *
 */
public interface ISearchService {

	/**
	 * 构建选择型条件HTML
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public String buildSelectHtml(String sign) throws Exception;
	
	/**
	 * 构建模糊查询条件HTML
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public String buildBlurHtml(String sign) throws Exception;
	
	
	
	/**
	 * 高级查询SQL及参数封装
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	public SearchModel getSearchModel(Object o) throws Exception;
	
	
	
	/**
	 * 构建高级查询联动关系
	 * @param sign
	 * @return
	 */
	public String buildLinkageScript(String sign);
	
	
	
	/**
	 * 查找联动关系
	 * @param model
	 * @return
	 */
	public List<SearchLinkageModel> getLinkageList(SearchLinkageModel model);
	
	
	
	/**
	 * 构建联动内容
	 * @param config
	 * @param linkageList
	 * @return
	 * @throws Exception
	 */
	public String buildLinkageContent(SearchConfigModel config , List<SearchLinkageModel> linkageList,int pageNumber) throws Exception;




	/**
	 * 分页加载更多选项
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	public String getMoreOptions(SearchLinkageModel model) throws Exception;

}
