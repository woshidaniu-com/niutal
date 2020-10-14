package com.woshidaniu.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IDymbglDao;
import com.woshidaniu.dao.entities.DymbglModel;
import com.woshidaniu.dao.entities.DymblxModel;
import com.woshidaniu.dao.entities.DysjxModel;
import com.woshidaniu.dao.entities.DysjyModel;
import com.woshidaniu.service.svcinterface.IDymbglService;

/**
 * 打印模板管理Service实现
 * @author guoqb[1127]
 * @date 2017-2-27 16:13:15
 */
@Service("dymbglService")
public class DymbglServiceImpl extends BaseServiceImpl<DymbglModel, IDymbglDao> implements IDymbglService {

	@Resource
	private IDymbglDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.setDao(dao);
	}

	/**
	 * 查询所有数据源列表
	 * @author guoqb[1127]
	 * @date 2017年3月1日 16:43:43
	 * @return List
	 */
	@Override
	public List<DysjyModel> getDysjyList() {
		return dao.getDysjyList();
	}

	/**
	 * 根据打印模板类型代码查询所有数据项列表
	 * @author guoqb[1127]
	 * @date 2017-3-1 16:59:27
	 * @param dm
	 * @return List
	 */
	@Override
	public List<DysjxModel> getDysjxListByDymblxdm(String dm) {
		return dao.getDysjxListByDymblxdm(dm);
	}

	/**
	 * 模板类型列表
	 * @author guoqb[1127]
	 * @date 2017-3-17 14:40:41
	 * @return List
	 */
	@Override
	public List<DymblxModel> getDymblxList() {
		return dao.getDymblxList();
	}

	/**
	 * 修改打印模板启用状态
	 * @author guoqb[1127]
	 * @date 2017-3-17 14:40:41
	 * @param model
	 * @return boolean
	 */
	@Override
	public boolean updateDymbQyzt(DymbglModel model) {
		return dao.updateDymbQyzt(model);
	}

	/**
	 * 删除打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-17 16:29:35
	 * @param ids
	 * @return boolean
	 */
	@Override
	public boolean scDymbgl(String ids) {
		boolean result = false;
		if(StringUtils.isEmpty(ids)){
			return result;
		}
		String[] id = ids.split(",");
		List<String> list=new ArrayList<String>();
		for (int i = 0; i < id.length; i++) {
			list.add(id[i]);
		}
		//删除打印信息
		int row = dao.batchDelete(list);
		if(row > 0){
			result = true;
		}else{
			result = false;
		}
		return result;
	}

	/**
	 * 根据打印模板类型获取打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-17 17:22:14
	 * @param mblxdm
	 * @return boolean
	 */
	@Override
	public boolean getDymbByDymblx(DymbglModel model) {
		model.setQyzt(IDymbglService.DYMB_QYZT_QY);
		int i = dao.getDymbByDymblx(model);
		return i > 0;
	}
	
	/**
	 * 根据打印模板类型获取打印模板个数
	 * @author zhidong[1571]
	 * @date 2020-7-29
	 * @param mblxdm
	 * @return int
	 */
	@Override
	public int getDymbByDymblxCount(DymbglModel model) {
		model.setQyzt(IDymbglService.DYMB_QYZT_QY);
		int i = dao.getDymbByDymblx(model);
		return i;
	}

	/**
	 * 新增打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-20 16:02:49
	 * @param model
	 * @return Map
	 */
	@Override
	public Map<String, Object> insertDymb(DymbglModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		DymbglModel dymbglModel = dao.getModel(model);
		if(dymbglModel != null){
			map.put("res", "fail");
			map.put("mes", "打印模板名称已存在");
		}else{
			String guid = UniqID.getInstance().getUniqIDHash();
			model.setId(guid);
			int i = dao.insert(model);
			if(i > 0){
				map.put("res", "success");
				map.put("guid", guid);
			}else{
				map.put("res", "fail");
				map.put("mes", "新增打印模板失败");
			}
		}
		return map;
	}

	/**
	 * 修改打印模板名称
	 * @author guoqb[1127]
	 * @date 2017-3-20 16:47:49
	 * @param model
	 * @return Map
	 */
	@Override
	public Map<String, Object> updateDymbMc(DymbglModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		int i = dao.getModelByMc(model);
		if(i > 0){
			map.put("res", "fail");
			map.put("mes", "打印模板名称已存在");
		}else{
			i = dao.updateDymbMc(model);
			if(i > 0){
				map.put("res", "success");
			}else{
				map.put("res", "fail");
				map.put("mes", "修改打印模板失败");
			}
		}
		return map;
	}

	/**
	 * 修改打印模板类型
	 * @author guoqb[1127]
	 * @date 2017-3-20 17:52:53
	 * @param model
	 * @return Map
	 */
	@Override
	public Map<String, Object> changeDymblx(String op,DymbglModel model) {
		
		if(CHANGE_DYMBLX_OP_TYPE_ZJ.equals(op)){//增加
			//for 刘冲
			//增加的时候验证一下有没有已经启用的模板，如果有就不启用，如果没有就启用
			
			String mblxdm = model.getMblxdm();
			//是否启用当前要插入的model
			boolean isEnableForThisModel = false;
			
			//检查是否是否已经存在模板,不存在，则启用这个
			if(!isEnableForThisModel){
				DymbglModel queryModel = new DymbglModel();
				queryModel.setMblxdm(mblxdm);
				List<DymbglModel> list = this.dao.getModelList(queryModel);
				if(list.isEmpty()){
					//启用当前要插入的模板
					isEnableForThisModel = true;						
				}
			}
			
			//检查是否有已经启用的模板，若没有启用的模板，则启用这个新模板
			if(!isEnableForThisModel){
				DymbglModel queryModel = new DymbglModel();
				queryModel.setMblxdm(mblxdm);
				queryModel.setQyzt(IDymbglService.DYMB_QYZT_QY);
				List<DymbglModel> list = this.dao.getModelList(queryModel);
				if(list.isEmpty()){
					//启用当前要插入的模板
					isEnableForThisModel = true;						
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if(isEnableForThisModel){
				model.setQyzt(IDymbglService.DYMB_QYZT_QY);
			}else{
				model.setQyzt(IDymbglService.DYMB_QYZT_TY);
			}
			int i = dao.update(model);
			if(i > 0){
				map.put("res", "success");
				List<DysjxModel> dysjxList= dao.getDysjxListByDymblxdm(model.getMblxdm());
				map.put("dysjxList", dysjxList);
			}else{
				map.put("res", "fail");
				map.put("mes", "修改打印模板类型失败");
			}
			return map;
			
		}else if(CHANGE_DYMBLX_OP_TYPE_XG.equals(op)){//修改
			
			Map<String, Object> map = new HashMap<String, Object>();
			int i = dao.update(model);
			if(i > 0){
				map.put("res", "success");
				List<DysjxModel> dysjxList= dao.getDysjxListByDymblxdm(model.getMblxdm());
				map.put("dysjxList", dysjxList);
			}else{
				map.put("res", "fail");
				map.put("mes", "修改打印模板类型失败");
			}
			return map;
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("res", "fail");
			map.put("mes", "未定义的操作类型["+ op +"]");
			return map;
		}
	}

	/**
	 * 根据打印模版类型与职工号查找打印模板内容
	 * @author guoqb[1127]
	 * @date 2017-3-22 17:21:41
	 * @param map
	 * @return DymbglModel
	 */
	@Override
	public DymbglModel getDymbByDymblxAndZgh(Map<String, String> map) {
		DymbglModel dymbglModel = dao.getDymbByDymblxAndZgh(map);
		if(null == dymbglModel){
			map.put("zgh", "mr");
			dymbglModel = dao.getDymbByDymblxAndZgh(map);
		}
		return dymbglModel;
	}

	/**
	 * 验证模板类型是否存在
	 * @author guoqb[1127]
	 * @date 2017-3-27 11:21:02
	 * @param mblxdm
	 * @return boolean
	 */
	@Override
	public boolean yzmblxSfcz(String mblxdm) {
		if(StringUtils.isNull(mblxdm)){
			return true;
		}
		DymbglModel dymbglModel = new DymbglModel();
		dymbglModel.setMblxdm(mblxdm);
		dymbglModel.setQyzt(DYMB_QYZT_QY);
		return dao.getModel(dymbglModel) == null;
	}

	@Override
	public boolean updateBg(MultipartHttpServletRequest req) {
		MultipartFile file = req.getFile("bg");
		String id = req.getParameter("id");
		boolean flag = false;
		try {
			flag = dao.updateBg(id, file.getBytes()) == 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Object getBg(String id) {
		// TODO Auto-generated method stub
		return dao.getBg(id);
	}
}