package com.woshidaniu.datarange.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.datarange.dao.entities.SjfwdxModel;
import com.woshidaniu.datarange.dao.entities.YhJssjfwModel;

/**
 * 
 * @package com.woshidaniu.service.svcinterface
 * @className: IYhSjfwglService
 * @description: 用户角色数据范围管理业务处理接口
 * @author : kangzhidong
 * @date : 2014-6-3
 * @time : 上午09:15:54
 */
public interface IYhSjfwglService extends BaseService<YhJssjfwModel>{

	/**
	 * 
	 * @description: 修改某角色下的某用户数据范围信息
	 * @author : kangzhidong
	 * @date : 2014-4-9
	 * @time : 下午04:50:28 
	 * @param sjfwModel
	 * @return
	 */
	public boolean updateYhSjfw(YhJssjfwModel model,List<SjfwdxModel> sjfwdxList);
	
	/**
	 * 
	 * @description: 根据角色代码和用户id查询某角色下的某用户数据范围信息
	 * @author : kangzhidong
	 * @date : 2014-4-9
	 * @time : 下午04:58:50 
	 * @param model
	 * @return
	 */
	public List<YhJssjfwModel> getYhSjfwList(YhJssjfwModel model);

    
}
