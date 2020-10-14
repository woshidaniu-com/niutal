/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.service.svcinterface;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.zxzx.dao.entities.ZxwtModel;

/**
 * @类名 IZxwtService.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月24日 下午7:20:01
 * @功能描述 在线咨询-咨询问题
 * 
 */
public interface IZxwtService extends BaseService<ZxwtModel> {
	
	/**
	 * 点击事件记录
	 * @param model
	 * @return
	 */
	public boolean clickEventRecord(ZxwtModel model);
	
	/**
	 * 设置为常见问题
	 * @param model
	 * @return
	 */
	public boolean szCjwt(ZxwtModel model);
	
	/**
	 * 提交回复信息
	 * @param model
	 * @return
	 */
	public boolean submitHfxx(ZxwtModel model);
	
	/**
	 * 用于导出
	 * @param model
	 * @return
	 */
	public List<Map<String,String>> getExportData(ZxwtModel model);
	
	/**
	 * 批量回复数据导出
	 * @param bkdm
	 * @param zgh
	 * @return
	 */
	public List<ZxwtModel> getBatchDataList(String[] bkdms);
	
	/**
	 * web端查询
	 * @param model
	 * @return
	 */
	public List<ZxwtModel> getPagedListWeb(ZxwtModel model);
	
	/**
	 * 用于web端我的咨询查询
	 * @param model
	 * @return
	 */ 
	public List<ZxwtModel> getPagedListMytopicWeb(ZxwtModel model);
	
	
	/**
	 * 获取热门咨询
	 * @param num
	 * @return
	 */
	public List<ZxwtModel> getTopWeb(String bkdm, int fs, int sjd, int ts);
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	public int updateDjl(ZxwtModel model);
	
	/**
	 * 用户删除数据web
	 * @param list
	 * @return
	 */
	public boolean batchDeleteWeb(List list,String yhm);
	
	public boolean updateWeb(ZxwtModel model);
	
	
	/**
	 * 生成批量回复excel文件
	 * @param model
	 * @return
	 */
	public File getBatchZxwtFile(String[] bkdms, String zgh);
	
	/**
	 * 处理用户批量回复文件
	 * @param fileSteam
	 * @param zgh
	 * @return
	 */
	public void handleBatchZxwtFile(InputStream fileSteam, String zgh); 
}
