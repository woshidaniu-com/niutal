package com.woshidaniu.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IHandleDao;
import com.woshidaniu.dao.daointerface.IMbglDao;
import com.woshidaniu.dao.daointerface.ISjyglDao;
import com.woshidaniu.dao.entities.PWModel;
import com.woshidaniu.globalweb.PWUtils;
import com.woshidaniu.globalweb.RSAUtils;
import com.woshidaniu.globalweb.pdf.PdfReplacer;
import com.woshidaniu.globalweb.word.DocUtils;
import com.woshidaniu.globalweb.word.DocxUtils;
import com.woshidaniu.globalweb.word2pdf.DocxToPDFConverter;
import com.woshidaniu.service.svcinterface.IHandleService;

/**
 * @className: HandleServiceImpl
 * @description: TODO(描述这个类的作用)
 * @author : Hanyc
 * @date: 2018-12-05 17:36:56
 * @version V1.0
 */
@Service
public class HandleServiceImpl extends BaseServiceImpl<PWModel, BaseDao<PWModel>> implements IHandleService {

	@Resource
	protected IHandleDao dao;

	@Resource
	protected ISjyglDao sjyglDao;

	@Resource
	protected IMbglDao mbglDao;

	private static KeyPair keyPair;

	static{
		try {
			keyPair = RSAUtils.getKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}

	/**
	 * @description: 获取要输出的数据列表
	 * @author : Hanyc
	 * @date : 2018-12-06 14:36:32
	 * @param model
	 * @param request
	 * @return
	 */
	public List<HashMap<String, Object>> getDataList(PWModel model, HttpServletRequest request){
		PWModel sModel = sjyglDao.getModelByMkdm(model);
		if(null != sModel && null != sModel.getModelList() && sModel.getModelList().size() != 0){
			//
			model.setModelList(sModel.getModelList());
			List<String> keys = new ArrayList<String>(0);
			List<HashMap<String, String>> cols = new ArrayList<HashMap<String, String>>(0);
			List<String[]> list = new ArrayList<String[]>(0);
			List<String> fileName = new ArrayList<String>(0);
			for(PWModel m : sModel.getModelList()){
				HashMap<String, String> map = new HashMap<String, String>(0);
				if(PWUtils.MAGIC_1.equals(m.getKey())){
					keys.add(m.getCol());
					String v = request.getParameter(m.getCol());
					if(null == v){
						return null;
					}else{
						list.add(v.split(","));
					}
				}
				map.put(PWUtils.COM, '【' + m.getCom() + '】');
				map.put(PWUtils.COL, m.getCol());
				map.put(PWUtils.BR, m.getBr());
				if(PWUtils.MAGIC_1.equals(m.getFilename())){
					fileName.add(m.getCol());
				}
				cols.add(map);
				//cols.add('【' + m.getCom() + '】');
			}
			if(cols.size() == 0){
				return null;
			}
			//可被替换的元素
			model.setList(cols);
			HashMap<String, Object> m = new HashMap<String, Object>(0);
			if(list.size() > 0){
				int i = 0;
				for(String[] arr :list){
					i = arr.length > i ? arr.length : i;
				}
				List<String[]> list2 = new ArrayList<String[]>(0);
				for(int j = 0; j < i; j++){
					String[] arr2 = new String[list.size()];
					for(int k = 0; k < list.size(); k++){
						String[] arr = list.get(k);
						if(arr.length == 1){
							arr2[k] = arr[0];
						}else{
							arr2[k] = arr[j];
						}
					}
					list2.add(arr2);
				}
				m.put("val", list2);
			}
			if(keys.size() > 0){
				m.put("key", keys);
			}
			if(fileName.size() > 0){
				m.put("fileName", fileName);
			}
			model.setObjMap(m);
			return dao.getDataList(model);
		}
		return null;
	}

	/**
	 * @description: 获取文件-PDF
	 * @author : Hanyc
	 * @date : 2018-12-06 10:39:37
	 * @param model
	 * @param request
	 * @return
	 */
	@Override
	public List<File> getPdfFiles(PWModel model, HttpServletRequest request){
		List<File> files = new ArrayList<File>(0);
		List<HashMap<String, Object>> dataList = this.getDataList(model, request);
		if(null != dataList && dataList.size() > 0){
			try {
				PdfReplacer soruceFile = new PdfReplacer(PWUtils.getRealPath(model.getDrpath()), 2);
				files = soruceFile.toPdfBatch(model.getList(), dataList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return files;
	}

	/**
	 * @description: 获取文件-DOC
	 * @author : Hanyc
	 * @date : 2018-12-06 10:39:37
	 * @param model
	 * @param request
	 * @return
	 */
	@Override
	public List<File> getDocFiles(PWModel model, HttpServletRequest request){
		List<File> files = new ArrayList<File>(0);
		List<HashMap<String, Object>> dataList = this.getDataList(model, request);
		if(null != dataList && dataList.size() > 0){
			try {
				FileInputStream inputStream = new FileInputStream(PWUtils.getRealPath(model.getDrpath()));
				files = DocUtils.toDocBatch(inputStream, model.getList(), dataList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return files;
	}

	/**
	 * @description: 获取文件-DOCX
	 * @author : Hanyc
	 * @date : 2018-12-06 10:39:37
	 * @param model
	 * @param request
	 * @return
	 */
	@Override
	public List<File> getDocxFiles(PWModel model, HttpServletRequest request){
		List<File> files = new ArrayList<File>(0);
		List<HashMap<String, Object>> dataList = this.getDataList(model, request);
		if(null != dataList && dataList.size() > 0){
			try {
				files = DocxUtils.toDocxBatch(new File(PWUtils.getRealPath(model.getDrpath())), model.getList(), dataList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return files;
	}

	/**
	 * @description: 获取文件路径
	 * @author : Hanyc
	 * @date : 2018-12-13 19:11:18
	 * @param model
	 * @param request
	 * @param flag 文件类型
	 * @return
	 */
	@Override
	public List<String> getFilesPathList(PWModel model, HttpServletRequest request, int flag){
		List<String> list = new ArrayList<String>(0);
		List<File> files = null;
		switch(flag){
		case PWUtils.IS_PDF:{
			files = this.getPdfFiles(model, request);
			break;
		}
		case PWUtils.IS_DOC:{
			files = this.getDocFiles(model, request);
			break;
		}
		case PWUtils.IS_DOCX:{
			files = this.getDocxFiles(model, request);
			break;
		}
		default:{
			break;
		}
		}
		if(null != files && files.size() > 0){
			for(File f : files){
				list.add(this.encodeRSA(f.toString()));
			}
		}
		return list;
	}

	@Override
	public String getMergeFilePath(PWModel model, HttpServletRequest request, int flag){
		List<String> list = new ArrayList<String>(0);
		List<File> files = null;
		String key = null;
		switch(flag){
		case PWUtils.IS_PDF:{
			files = this.getPdfFiles(model, request);
			break;
		}
		case PWUtils.IS_DOC:{
			files = this.getDocFiles(model, request);
			break;
		}
		case PWUtils.IS_DOCX:{
			files = this.getDocxFiles(model, request);
			break;
		}
		default:{
			break;
		}
		}
		if(null != files && files.size() > 0){
			String file = System.getProperty("java.io.tmpdir") + File.separator + System.currentTimeMillis() + ".pdf";
			switch(flag){
			case PWUtils.IS_PDF:{
				for(File f : files){
					list.add(f.toString());
				}
				String[] arr = list.toArray(new String[list.size()]);
				PWUtils.mergePdfFiles(arr, file);
				key = this.encodeRSA(file);
				break;
			}
			case PWUtils.IS_DOC:{
				break;
			}
			case PWUtils.IS_DOCX:{
				try {
					for(File f : files){
						String str = f.toString().replace(".DOCX", ".PDF");
						DocxToPDFConverter d = new DocxToPDFConverter(new FileInputStream(f), new FileOutputStream(new File(str)), false, true);
						d.convert();
						list.add(str);
					}
					String[] arr = list.toArray(new String[list.size()]);
					PWUtils.mergePdfFiles(arr, file);
					key = this.encodeRSA(file);
					break;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				key = this.encodeRSA(file);
				break;
			}
			default:{
				break;
			}
			}
		}
		return key;
	}

	@Override
	public String getMergeFilePath4Dlop(PWModel model, HttpServletRequest request, int flag){
		List<String> list = new ArrayList<String>(0);
		List<File> files = null;
		String file = null;
		switch(flag){
		case PWUtils.IS_PDF:{
			files = this.getPdfFiles(model, request);
			break;
		}
		case PWUtils.IS_DOC:{
			files = this.getDocFiles(model, request);
			break;
		}
		case PWUtils.IS_DOCX:{
			files = this.getDocxFiles(model, request);
			break;
		}
		default:{
			break;
		}
		}
		if(null != files && files.size() > 0){
			file = System.getProperty("java.io.tmpdir") + File.separator + System.currentTimeMillis() + ".pdf";
			switch(flag){
			case PWUtils.IS_PDF:{
				for(File f : files){
					//list.add(this.encodeRSA(f.toString()));
					list.add(f.toString());
				}
				String[] arr = list.toArray(new String[list.size()]);
				PWUtils.mergePdfFiles(arr, file);
				break;
			}
			case PWUtils.IS_DOC:{
				break;
			}
			case PWUtils.IS_DOCX:{
				try {
					for(File f : files){
						String str = f.toString().replace(".DOCX", ".PDF");
						DocxToPDFConverter d = new DocxToPDFConverter(new FileInputStream(f), new FileOutputStream(new File(str)), false, true);
						d.convert();
						list.add(str);
					}
					String[] arr = list.toArray(new String[list.size()]);
					PWUtils.mergePdfFiles(arr, file);
					break;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			default:{
				break;
			}
			}
		}
		return file;
	}

	@Override
	public String getValue(String key){
		return this.decodeRSA(key);
	}

	@Override
	public String getMergeFileHtml(PWModel model, HttpServletRequest request, int flag){
		List<File> files = null;
		String content = null;
		switch(flag){
		case PWUtils.IS_PDF:{
			return "模板有误，请上传word模板！";
		}
		case PWUtils.IS_DOC:{
			files = this.getDocFiles(model, request);
			break;
		}
		case PWUtils.IS_DOCX:{
			files = this.getDocxFiles(model, request);
			break;
		}
		default:{
			break;
		}
		}
		if(null != files && files.size() > 0){
			try {
				switch(flag){
				case PWUtils.IS_PDF:{
					return "模板有误，请上传word模板！";
				}
				case PWUtils.IS_DOC:{
					String file = System.getProperty("java.io.tmpdir") + File.separator + System.currentTimeMillis() + ".doc";
					DocUtils.mergeDoc(files, file);
					content = DocUtils.toHtml(file);
					break;
				}
				case PWUtils.IS_DOCX:{
					String file = System.getProperty("java.io.tmpdir") + File.separator + System.currentTimeMillis() + ".docx";
					DocxUtils.mergeDocx(files, file);
					content = DocxUtils.toHtml(files.get(0).getAbsolutePath());
					break;
				}
				default:{
					break;
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * @description: 加密
	 * @author : Hanyc
	 * @date : 2018-12-13 19:32:09
	 * @param str
	 * @return
	 */
	public String encodeRSA(String str){
		try {
			//===============生成公钥和私钥，公钥传给客户端，私钥服务端保留==================
			//生成RSA公钥和私钥，并Base64编码
			if(null == keyPair){
				keyPair = RSAUtils.getKeyPair();
			}
			String publicKeyStr = RSAUtils.getPublicKey(keyPair);

			//=================客户端=================
			//将Base64编码后的公钥转换成PublicKey对象
			PublicKey publicKey = RSAUtils.string2PublicKey(publicKeyStr);
			//用公钥加密
			byte[] publicEncrypt = RSAUtils.publicEncrypt(str.getBytes(), publicKey);
			//加密后的内容Base64编码
			String byte2Base64 = RSAUtils.byte2Base64(publicEncrypt);
			return byte2Base64;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @description: 解密
	 * @author : Hanyc
	 * @date : 2018-12-13 19:32:16
	 * @param byte2Base64
	 * @return
	 */
	public String decodeRSA(String byte2Base64){
		try {
			//===============生成公钥和私钥，公钥传给客户端，私钥服务端保留==================
			//生成RSA公钥和私钥，并Base64编码
			if(null == keyPair){
				keyPair = RSAUtils.getKeyPair();
			}
			String privateKeyStr = RSAUtils.getPrivateKey(keyPair);

			//##############	网络上传输的内容有Base64编码后的公钥 和 Base64编码后的公钥加密的内容	#################

			//将Base64编码后的私钥转换成PrivateKey对象
			PrivateKey privateKey = RSAUtils.string2PrivateKey(privateKeyStr);
			//加密后的内容Base64解码
			byte[] base642Byte = RSAUtils.base642Byte(byte2Base64);
			//用私钥解密
			byte[] privateDecrypt = RSAUtils.privateDecrypt(base642Byte, privateKey);
			//解密后的明文
			return new String(privateDecrypt);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}