package com.woshidaniu.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.dao.entities.AncdModel;

@Repository("funcMenuDao")
public interface IFuncMenuDao extends BaseDao<AncdModel> {

	public List<BaseMap> getTopNavMenuList(@Param(value = "yhm") String yhm, @Param(value = "jsdm") String jsdm,
			@Param(value = "localeKey") String localeKey);

	public List<BaseMap> getChildNavMenuList(@Param(value = "yhm") String yhm, @Param(value = "jsdm") String jsdm,
			@Param(value = "gnmkdm") String parentGnmkdm, @Param(value = "localeKey") String localeKey);
	
	public List<BaseMap> getNavMenuTreeList(@Param(value = "yhm") String yhm, @Param(value = "jsdm") String jsdm,
			@Param(value = "gnmkdm") String parentGnmkdm, @Param(value = "localeKey") String localeKey);

}
