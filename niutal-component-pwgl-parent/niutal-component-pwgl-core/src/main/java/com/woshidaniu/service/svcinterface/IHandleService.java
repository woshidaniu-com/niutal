package com.woshidaniu.service.svcinterface;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.PWModel;

/**
 * @className: IHandleService
 * @description: TODO(描述这个类的作用)
 * @author : Hanyc
 * @date: 2018-12-05 17:34:20
 * @version V1.0
 */
public interface IHandleService extends BaseService<PWModel>{

	/**
	 * @description: 获取文件-PDF
	 * @author : Hanyc
	 * @date : 2018-12-06 10:39:37
	 * @param model
	 * @param request
	 * @return
	 */
	List<File> getPdfFiles(PWModel model, HttpServletRequest request);

	/**
	 * @description: 获取文件-DOC
	 * @author : Hanyc
	 * @date : 2018-12-06 10:39:37
	 * @param model
	 * @param request
	 * @return
	 */
	List<File> getDocFiles(PWModel model, HttpServletRequest request);

	/**
	 * @description: 获取文件-DOCX
	 * @author : Hanyc
	 * @date : 2018-12-06 10:39:37
	 * @param model
	 * @param request
	 * @return
	 */
	List<File> getDocxFiles(PWModel model, HttpServletRequest request);

	/**
	 * @description: 获取文件路径
	 * @author : Hanyc
	 * @date : 2018-12-13 19:11:18
	 * @param model
	 * @param request
	 * @param flag 文件类型
	 * @return
	 */
	List<String> getFilesPathList(PWModel model, HttpServletRequest request, int flag);

	/**
	 * @description: 获取文件路径
	 * @author : Hanyc
	 * @date : 2018-12-13 19:11:18
	 * @param model
	 * @param request
	 * @param flag 文件类型
	 * @return
	 */
	String getMergeFilePath(PWModel model, HttpServletRequest request, int flag);

	/**
	 * @description: 下载单个pdf
	 * @author : Hanyc
	 * @date : 2019-05-09 19:31:59
	 * @param model
	 * @param request
	 * @param flag
	 * @return
	 */
	String getMergeFilePath4Dlop(PWModel model, HttpServletRequest request, int flag);

	/**
	 * @description: 获取文件HTML
	 * @author : Hanyc
	 * @date : 2019-06-26 10:16:09
	 * @param model
	 * @param request
	 * @param flag
	 * @return
	 */
	String getMergeFileHtml(PWModel model, HttpServletRequest request, int flag);
}