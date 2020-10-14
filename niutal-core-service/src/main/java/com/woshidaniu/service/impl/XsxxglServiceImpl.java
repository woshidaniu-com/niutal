package com.woshidaniu.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.compress.ZipUtils;
import com.woshidaniu.io.utils.FileUtils;
import com.woshidaniu.jcsj.dao.daointerface.IXsxxglDao;
import com.woshidaniu.jcsj.dao.entities.XsxxglModel;
import com.woshidaniu.jcsj.service.svcinterface.IXsxxglService;
import com.woshidaniu.web.WebContext;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service("xsxxglService")
public class XsxxglServiceImpl extends BaseServiceImpl<XsxxglModel, IXsxxglDao>
		implements IXsxxglService {
	protected static String default_xszp = "/images/default_xszp.gif";
	protected static int MAX_SIZE = 150;// 单个文件最大k

	@Resource(name="xsxxglDao")
	private IXsxxglDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	@Override
	public boolean scXsxx(String xhs) {
		boolean result = false;
		if (!StringUtils.isEmpty(xhs)) {
			String[] xh = xhs.split(",");
			List<XsxxglModel> list = new ArrayList<XsxxglModel>();
			XsxxglModel model = null;
			for (int i = 0; i < xh.length; i++) {
				model = new XsxxglModel();
				model.setXh(xh[i]);
				list.add(model);
			}
			int row = dao.batchDelete(list);
			if (row > 0) {
				result = true;
			}
		}
		return result;
	}

	public XsxxglModel getXszp(String xh) {
		XsxxglModel model = dao.getXszp(xh);
		byte[] bytes = null;
		if (model == null || null == model.getXszp()) {
			String filePath = WebContext.getServletContext().getRealPath(default_xszp);
			try {
				bytes = FileUtils.toByteArray(filePath);
				model = new XsxxglModel();
				model.setXh(xh);
				model.setXszp(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	public boolean xgXsxx(XsxxglModel model) {
		setXszp(model);
		return dao.update(model) > 0;
	}

	private void setXszp(XsxxglModel model) {
		if (model != null && model.getXh() != null) {

			// 程序目录
			String appPath = ((HttpServletRequest)WebContext.getRequest()).getContextPath();
			// 上传文件的绝对路径
			String rootPath = WebContext.getServletContext().getRealPath(model.getPath().replace(appPath, ""));

			try {
				model
						.setXszp(FileCopyUtils.copyToByteArray(new File(
								rootPath)));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public boolean zjXsxx(XsxxglModel model) {
		setXszp(model);
		dao.insert(model);
		return true;
	}


	public String unZip(String tempFilePath) {
		String tempDir = WebContext.getServletContext().getRealPath("/");
		tempDir += "temp/pldrzp/zip";
		// 文件进行解压
		String savePath = tempDir;
		try {
			ZipUtils.decompress(new File(tempFilePath), new File(savePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savePath;
	}

	/**
	 * 上传照片，并将未导入的照片名抛出
	 * 
	 * @param photoPath
	 *            照片存放路径
	 * @return
	 */
	public List<HashMap<String, String>> savePhoto(String photoPath,
			String photoNameType) {
		File file = new File(photoPath);
		String[] files = file.list();
		FileInputStream is = null;

		List<HashMap<String, String>> zpscjgList = new ArrayList<HashMap<String, String>>();
		List<HashMap<String, String>> errorList = new ArrayList<HashMap<String, String>>();

		String xszdmc = "";
		XsxxglModel xsxxglModel = null;

		for (int i = 0; i < files.length; i++) {
			xsxxglModel = new XsxxglModel();
			HashMap<String, String> hashMap = new HashMap<String, String>();
			String fileTemp = files[i];
			if (!checkFile(errorList, fileTemp, photoPath)) {// 验证文件格式及大小
				continue;
			}
			String fileName = fileTemp.substring(0, fileTemp.indexOf("."));
			if ("xh".equalsIgnoreCase(photoNameType)) {
				xszdmc = "学号";
				xsxxglModel.setXh(fileName);
			} else if ("sfzh".equalsIgnoreCase(photoNameType)) {
				xszdmc = "身份证号";
				xsxxglModel.setSfzh(fileName);
			}
			try {
				is = new FileInputStream(photoPath + "/" + fileTemp);
				xsxxglModel = dao.getModelSimple(xsxxglModel);
				if (xsxxglModel == null || xsxxglModel.getXh() == null) {
					hashMap.put("xh", fileName);
					hashMap.put("czjg", " 导入失败：系统中不存在该"+xszdmc+"的学生信息");
					errorList.add(hashMap);
				} else {
					xsxxglModel.setXszp(FileCopyUtils.copyToByteArray(is));
					dao.update(xsxxglModel);// 更新照片
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
		}
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("czjg", "导入" + files.length + "张照片,成功" + (files.length - errorList.size()) + "张,失败"
				+ errorList.size() + "张");
		zpscjgList.add(hashMap);
		if (errorList.size() == 0) {
			// 无失败信息
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("xh", "");
			temp.put("czjg", "无失败信息");
			errorList.add(temp);
		}
		zpscjgList.addAll(errorList);
		return zpscjgList;
	}

	// 输出导出结果
	public File printZpdr(String photoNameType,
			List<HashMap<String, String>> zpscjgList) throws Exception {
		File file = null;
		WritableWorkbook wwb = null;
		FileOutputStream stream = null;
		try {
			String tempDir = WebContext.getServletContext().getRealPath("/");
			long currentTimeMillis = System.currentTimeMillis();
			String modelPath = tempDir + "temp/pldrzp/print/";
			File fileDir = new File(modelPath);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			modelPath = modelPath + currentTimeMillis + ".xls";
			file = new File(modelPath);
			file.setWritable(true);
			stream = new FileOutputStream(file);

			// 创建excel工作表
			wwb = Workbook.createWorkbook(stream);
			WritableSheet ws = wwb.createSheet("照片导入结果列表", 0);
			// 创建xls中SHEET对象
			WritableCellFormat wcfTytle = new WritableCellFormat();
			// 设置对齐方式
			Alignment alignMent = Alignment.CENTRE;
			VerticalAlignment vag = VerticalAlignment.CENTRE;

			wcfTytle.setVerticalAlignment(vag);
			wcfTytle.setAlignment(alignMent);

			WritableFont wfTytle = new WritableFont(WritableFont.ARIAL);
			wfTytle.setBoldStyle(WritableFont.BOLD);
			wfTytle.setPointSize(12);
			wcfTytle.setFont(wfTytle);
			ws.mergeCells(0, 0, 4, 0);
			ws.addCell(new Label(0, 0, "照片导入(" + zpscjgList.get(0).get("czjg")
					+ ")", wcfTytle));

			wcfTytle = new WritableCellFormat();
			alignMent = Alignment.LEFT;
			vag = VerticalAlignment.CENTRE;

			wcfTytle.setVerticalAlignment(vag);
			wcfTytle.setAlignment(alignMent);
			wcfTytle.setBorder(Border.ALL, BorderLineStyle.THIN);

			wfTytle = new WritableFont(WritableFont.ARIAL);
			wfTytle.setBoldStyle(WritableFont.BOLD);
			wfTytle.setPointSize(12);
			wcfTytle.setFont(wfTytle);

			wcfTytle = new WritableCellFormat();
			alignMent = Alignment.CENTRE;
			vag = VerticalAlignment.CENTRE;

			wcfTytle.setVerticalAlignment(vag);
			wcfTytle.setAlignment(alignMent);
			wcfTytle.setWrap(true);
			// 设置表格边框
			wcfTytle.setBorder(Border.ALL, BorderLineStyle.THIN);

			wfTytle = new WritableFont(WritableFont.ARIAL);
			wfTytle.setPointSize(10);
			wcfTytle.setFont(wfTytle);

			String xszdmc = "";
			if ("xh".equalsIgnoreCase(photoNameType)) {
				xszdmc = "学号";
			} else if ("sfzh".equalsIgnoreCase(photoNameType)) {
				xszdmc = "身份证号";
			}
			ws.addCell(new Label(0, 1, xszdmc, wcfTytle));
			ws.mergeCells(1, 1, 4, 1);
			ws.addCell(new Label(1, 1, "操作结果", wcfTytle));
			for (int i = 1; i < zpscjgList.size(); i++) {
				HashMap<String, String> zpscjgMap = zpscjgList.get(i);
				ws.mergeCells(1, i + 1, 4, i + 1);
				ws.addCell(new Label(0, i + 1, zpscjgMap.get("xh"), wcfTytle));
				ws
						.addCell(new Label(1, i + 1, zpscjgMap.get("czjg"),
								wcfTytle));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 向客户端输出
			 wwb.write();
			 wwb.close();
			if (stream != null) {
				stream.close();
			}
		}
		return file;
	}

	/*
	 * 验证文件格式及大小
	 */
	private boolean checkFile(List<HashMap<String, String>> errorList,
			String fileTemp, String photoPath) {
		boolean result = true;
		String fileType = "jpg,gif,png,bmp";
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String[] fileTemps = fileTemp.split("[.]");
		String postfix = fileTemps[fileTemps.length - 1].toLowerCase();
		File fileOne = new File(photoPath + "/" + fileTemp);

		if (fileType.indexOf(postfix) == -1) {
			hashMap.put("xh", fileTemp.substring(0, fileTemp.indexOf(".")));
			hashMap.put("czjg", " 文件格式不正确");
			errorList.add(hashMap);
			result = false;
		} else if (fileOne.length() >= MAX_SIZE * 1024) {
			hashMap.put("xh", fileTemp.substring(0, fileTemp.indexOf(".")));
			hashMap.put("czjg", " 导入失败：文件过大(大于" + MAX_SIZE + "K)");
			errorList.add(hashMap);
			result = false;
		}
		return result;
	}

	public boolean chkZyischg(XsxxglModel model) {
		XsxxglModel xsModel=dao.getModel(model);
		String zydm=dao.getZydmByBjdm(model.getBjdm_id());
		return zydm.equalsIgnoreCase(xsModel.getZydm());
	}

	/**
	 * 收费删除学生处理
	 */
	public int scXsxxForSf(String xhs) {
		if (!StringUtils.isEmpty(xhs)) {
			String[] xh = xhs.split(",");
			List<XsxxglModel> list = new ArrayList<XsxxglModel>();
			XsxxglModel model = null;
			for (int i = 0; i < xh.length; i++) {
				model = new XsxxglModel();
				model.setXh(xh[i]);
				list.add(model);
			}
			return dao.batchDeleteForSf(list);
		}else{return 0;}
	}

}
