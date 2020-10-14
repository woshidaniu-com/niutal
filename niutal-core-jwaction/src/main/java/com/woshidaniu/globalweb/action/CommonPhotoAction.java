package com.woshidaniu.globalweb.action;
 
import java.io.File;
import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.entities.CommonModel;
import com.woshidaniu.io.utils.FileUtils;
/**
 * 
 *@类名称		： CommonPhotoAction.java
 *@类描述		：处理照片Ation
 *@创建人		：kangzhidong
 *@创建时间	：2016年5月5日 上午11:24:49
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class CommonPhotoAction extends CommonBaseAction implements ModelDriven<CommonModel> {
	
	protected static final Logger LOG = LoggerFactory.getLogger(CommonPhotoAction.class);
	private static final long serialVersionUID = 1L;
	protected CommonModel model = new CommonModel();
	
	/**
	 * 
	 *@描述		：显示学生照片信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016年5月5日上午11:27:25
	 *@return
	 *@throws IOException
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String cxXszp() throws IOException {

		setProperty("contentType", "image/jpeg") ;
		
		try {
			if(!BlankUtils.isBlank(model.getXh_id())){
				 //do something
			}
			//取默认照片
			if(bytes == null){
				bytes = getDefaultZp();
			}
			
		} catch (Exception e) {
			logException(e);
			bytes = getDefaultZp();
		}
		return Result.BYTE;
	} 
	
	/**
	 * 
	 *@描述		：显示教职工照片信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016年5月5日上午11:27:33
	 *@return
	 *@throws IOException
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String cxJzgzp() throws IOException {
		
		setProperty("contentType", "image/jpeg") ;
		
		try {
			if(!BlankUtils.isBlank(model.getJgh_id()) || !BlankUtils.isBlank(model.getJgh())){
				 //do something
			}
			//取默认照片
			if(bytes == null){
				bytes = getDefaultZp();
			}
			
		} catch (Exception e) {
			logException(e);
			bytes = getDefaultZp();
		}
		return Result.BYTE;
	} 
	
	@Override
	public CommonModel getModel() {
		return model;
	}
	
	/**
	 * 
	 *@描述		： 取默认照片
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016年5月5日上午11:26:58
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	protected byte[] getDefaultZp(){
		byte[] result = null;
		String path = ServletActionContext.getServletContext().getRealPath(File.separator);
		File file = new File(path + "/images/default_xszp.gif");
		try {
			result = FileUtils.toByteArray(file);
		} catch (IOException e) {
			logStackException(e);
			LOG.error("默认学生照片不存在");
		}
		return result;
	}

}
