package com.woshidaniu.datarange.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.datarange.dao.daointerface.IYhjsSjfwDao;
import com.woshidaniu.datarange.dao.entities.DataRangeItemModel;
import com.woshidaniu.datarange.dao.entities.SjfwdxModel;
import com.woshidaniu.datarange.dao.entities.YhJssjfwModel;
import com.woshidaniu.datarange.service.svcinterface.IYhSjfwglService;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;

/**
 * 
 *@类名称		： YhjsSjfwServiceImpl.java
 *@类描述		：用户角色数据范围管理业务层接口实现
 *@创建人		：kangzhidong
 *@创建时间	：Aug 25, 2016 3:36:00 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@After
@Logger(model="N0102",business="N010205")
@Service
public class YhjsSjfwServiceImpl extends CommonBaseServiceImpl<YhJssjfwModel,IYhjsSjfwDao> implements IYhSjfwglService {
	
	@Resource
	private IYhjsSjfwDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);

	}
    
	@Comment
	public boolean updateYhSjfw(YhJssjfwModel model,List<SjfwdxModel> sjfwdxList) {
		try {
			String[] ids = StringUtils.splits(model.getYhm(), ",");
			//循环用户ID
			for (String yh_id : ids) {
				model.setYhm(yh_id);
				
				//1.删除用户所有指定控制对象的数据范围，数据范围组
				dao.deleteYhSjfwz(model);
				
				//全部学院即全校的数据范围
				if("1".equalsIgnoreCase(model.getSfqx())||"1".equalsIgnoreCase(model.getSfqxy())){
					//全校权限|全学院权限
					//2.保存用户全学院数据范围
					model.setSjfwz_id(getQueryDao().getSysGuid());
					if("1".equalsIgnoreCase(model.getSfqx())){
						//全校数据范围
						model.setSjfwzmc("全校");
						model.setSjfwztj("jg_id=-2");
					}else if("1".equalsIgnoreCase(model.getSfqxy())){
						//全学院数据范围
						model.setSjfwzmc("全学院");
						model.setSjfwztj("jg_id=-3");
					}
					model.setKzdx("xs");
					//2.保存用户新的数据范围，数据范围组
					dao.insertYhSjfw(model);
				}else{//具体的权限组合
					if(null!=model.getDataRanges()){
						StringBuilder builderID = new StringBuilder();
						StringBuilder builderMC = new StringBuilder();
						//获得每个数据范围组合
						for (DataRangeItemModel dataRange : model.getDataRanges()) {
							//清除内容
							builderID.delete(0, builderID.length());
							builderMC.delete(0, builderMC.length());
							//获取一行的数据范围信息
							Map<String,String> row = dataRange.getRow();
							
							//年级代码
							String njdm_id = row.get("njdm_id");
							//年级
							String[] njdm_id_list = new String[0];
							if(njdm_id!=null){
								njdm_id_list = StringUtils.splits(njdm_id,",");
							}
							//年级名称
							String njdm_mc = row.get("njdm_id_text");
							String[] njdm_mc_list = new String[0];
							if(njdm_id!=null){
								njdm_mc_list = StringUtils.splits(njdm_mc,",");
							}
							
							//循环数据范围对象，根据数范围对象取当前保存的数据范围结果
							for(SjfwdxModel sjfwdx : sjfwdxList){
								//字段代码key【部门ID：jg_id,专业号ID:zyh_id,班号:bh,学号ID：xh_id】
								String zddm = sjfwdx.getZddm();
								//字段代码key存在
								if(!BlankUtils.isBlank(zddm)){
									zddm = zddm.toLowerCase();
									//字段代码val
									String zddm_val = row.get(zddm);
									
									String zddm_text = row.get(zddm + "_text");
									
									if(!BlankUtils.isBlank(zddm_val)&&!BlankUtils.isBlank(zddm_text)){
										builderID.append(zddm).append("=").append(zddm_val);
										builderMC.append(zddm_text);
									}
								}
							}
							
							//存在年级的组合
							if(njdm_id_list!=null&&njdm_id_list.length>0){
								builderID.append(";").append("njdm_id=").append(StringUtils.join(njdm_id_list,","));
								builderMC.append("【").append(StringUtils.join(njdm_mc_list,",")).append("】");
							}else{
								if(builderMC.length()>0){
									if("xs".equals(model.getKzdx())){
										if(builderID.indexOf("xsxzm") > -1){
											builderMC.append( "【学生性质】" );
										}else{
											builderMC.append( "【学生数据】" );
										}
									}else if("kc".equals(model.getKzdx())){
										builderMC.append( "【课程数据】" );
									}else if("js".equals(model.getKzdx())){
										builderMC.append( "【教师数据】" );
									}else if("cd".equals(model.getKzdx())){
										builderMC.append( "【场地数据】" );
									}
								}
							}
							model.setSjfwz_id(getQueryDao().getSysGuid());
							model.setSjfwztj(builderID.toString());
							model.setSjfwzmc(builderMC.toString());
							//2.保存用户新的数据范围，数据范围组
							dao.insertYhSjfw(model);
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<YhJssjfwModel> getYhSjfwList(YhJssjfwModel model) {
		String[] ids = StringUtils.splits(model.getYhm(), ",");
		if(ids!=null&&ids.length>1){
			return new ArrayList<YhJssjfwModel>();
		}
		if(BlankUtils.isBlank(model.getQueryList())){
			model.setQueryList(CollectionUtils.arrayToList(ids));
		}
		return dao.getYhSjfwList(model);
	}
	
}
