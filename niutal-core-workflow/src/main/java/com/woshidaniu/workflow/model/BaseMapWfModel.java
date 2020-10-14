package com.woshidaniu.workflow.model;

import java.io.Serializable;
import java.util.HashMap;
import com.woshidaniu.common.query.QueryModel;

/**
 * Create on 2013-7-12 下午01:57:13 All CopyRight By http://www.woshidaniu.com/ AT 2013 Version 1.0
 * 
 * @author : HJL [718]
 */
public class BaseMapWfModel extends HashMap<String, Object> implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 7511777436380564538L;

    public QueryModel         queryModel       = new QueryModel();

    public QueryModel getQueryModel() {
        return queryModel;
    }

    public void setQueryModel(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

}
