package com.woshidaniu.datarange.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.datarange.dao.entities.SjfwdxModel;

/**
 * 
 * 类名称： ISjfwdxService
 * 类描述：数据范围对象Service
 * 创建人：caozf
 * 创建时间：2012-7-12
 */
public interface ISjfwdxService extends BaseService<SjfwdxModel>{
	/**
	 * 查询数据范围对象列表
	 * @param List<SjfwdxModel>
	 * @return
	 * @throws Exception
	 */
	public List<SjfwdxModel> cxSjfwdx(SjfwdxModel model);
	
	/**
	 * 根据数据范围对象，查询数据范围内容 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public List<SjfwdxModel> cxSjfwnr(SjfwdxModel model);
	 
}
