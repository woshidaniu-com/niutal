package com.woshidaniu.service.svcinterface;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.PWModel;

/**
 * @className: IMbglService
 * @description: 模板管理接口
 * @author : Hanyc
 * @date: 2018-12-03 09:29:19
 * @version V1.0
 */
public interface IMbglService extends BaseService<PWModel>{

	/**
	 * @description: 新增
	 * @author : Hanyc
	 * @date : 2018-12-04 16:33:49
	 * @param model
	 * @param request
	 * @return
	 */
	boolean insert(PWModel model, MultipartHttpServletRequest request);

	/**
	 * @description: 更新
	 * @author : Hanyc
	 * @date : 2018-12-04 16:33:49
	 * @param model
	 * @param request
	 * @return
	 */
	boolean update(PWModel model, HttpServletRequest request);

	/**
	 * @description: TODO
	 * @author : Hanyc
	 * @date : 2018-12-13 09:33:03
	 * @param model
	 * @return
	 */
	File getModelFile(PWModel model);
}