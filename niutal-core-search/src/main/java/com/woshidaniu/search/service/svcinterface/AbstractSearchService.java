/**
 * 
 */
package com.woshidaniu.search.service.svcinterface;

import java.util.List;

import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchLinkageModel;
import com.woshidaniu.search.dao.entities.SearchModel;

/**
 * 高级查询服务接口抽象类
 * 
 * @author kzd 1036
 *
 */
public abstract class AbstractSearchService implements ISearchService {

	/* (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.ISearchService#buildBlurHtml(java.lang.String)
	 */
	@Override
	public String buildBlurHtml(String sign) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.ISearchService#buildLinkageContent(com.woshidaniu.search.dao.entities.SearchConfigModel, java.util.List, int)
	 */
	@Override
	public String buildLinkageContent(SearchConfigModel config,
			List<SearchLinkageModel> linkageList, int pageNumber)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.ISearchService#buildLinkageScript(java.lang.String)
	 */
	@Override
	public String buildLinkageScript(String sign) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.ISearchService#buildSelectHtml(java.lang.String)
	 */
	@Override
	public String buildSelectHtml(String sign) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.ISearchService#getLinkageList(com.woshidaniu.search.dao.entities.SearchLinkageModel)
	 */
	@Override
	public List<SearchLinkageModel> getLinkageList(SearchLinkageModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.ISearchService#getMoreOptions(com.woshidaniu.search.dao.entities.SearchLinkageModel)
	 */
	@Override
	public String getMoreOptions(SearchLinkageModel model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.ISearchService#getSearchModel(java.lang.Object)
	 */
	@Override
	public SearchModel getSearchModel(Object o) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
