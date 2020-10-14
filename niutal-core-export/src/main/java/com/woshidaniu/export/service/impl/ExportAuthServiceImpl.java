/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.impl;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.dao.daointerface.IExportAuthDao;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportConfigVoModel;
import com.woshidaniu.dao.daointerface.IExportAuthDao;
import com.woshidaniu.export.service.svcinterface.IExportAuthService;
import com.woshidaniu.export.service.svcinterface.IExportAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @description	： 导出授权服务实现类
 * @author 		：康康（1571）
 */
@Service("exportAuthService")
public class ExportAuthServiceImpl implements IExportAuthService {
	
	@Autowired
	protected IExportAuthDao exportAuthDao;
	
	@Override
	public List<ExportConfigVoModel> getPagedExportConfigVoModelList(ExportConfigModel model){
		List<ExportConfigVoModel> list = this.exportAuthDao.getPagedExportConfigVoModelList(model);
		for(ExportConfigVoModel exportConfigVoModel : list) {
			this.populateAuthProperty(exportConfigVoModel);
		}
		return list;
	}
	
	private void populateAuthProperty(ExportConfigVoModel exportConfigVoModel) {
		String dcclbh = exportConfigVoModel.getDcclbh();
		String zd = exportConfigVoModel.getZd();
		Set<String> sqzs = this.exportAuthDao.getExportAuthSqzList(dcclbh, zd);
		if(!sqzs.isEmpty()) {
			
			exportConfigVoModel.setSqzList(new ArrayList<String>(sqzs));
			
			String szqStrList = StringUtils.join(sqzs, ",");
			exportConfigVoModel.setSqzStrList(szqStrList);
		}
	}
	
	@Override
	public int insertExportConfigModel(ExportConfigModel exportConfigModel) {
		return this.exportAuthDao.insertExportConfigModel(exportConfigModel);
	}

	@Override
	public int deleteExportConfigModel(String dcclbh, String zd) {
		return this.exportAuthDao.deleteExportConfigModel(dcclbh, zd);
	}

	@Override
	public int updateExportConfigModel(ExportConfigModel exportConfigModel) {
		return this.exportAuthDao.updateExportConfigModel(exportConfigModel);
	}
	
	@Override
	public ExportConfigVoModel getExportConfigVoModel(String dcclbh, String zd) {
		ExportConfigVoModel result = this.exportAuthDao.getExportConfigVoModel(dcclbh,zd);
		this.populateAuthProperty(result);
		return result;
	}

	@Override
	public int updateExportAuth(String dcclbh,String zd,String[] sqzs) {
		int deleteCount = this.exportAuthDao.deleteExportAuth(dcclbh, zd);
		if(sqzs == null || sqzs.length <= 0) {//是删除所有角色,直接返回删除个数,代表成功
			return deleteCount;
		}else {
			int c = 0;
			for(String sqz : sqzs) {
				if(this.exportAuthDao.insertExportAuth(dcclbh, zd, sqz) > 0) {
					c++;
				}
			}
			return c;
		}
	}
}
