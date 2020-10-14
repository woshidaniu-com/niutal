package com.woshidaniu.globalweb.action;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.StringUtils;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * KindEdirot 编辑器文件上传与选择
 * @author Penghui.Qu
 *
 */
@Controller
@Scope("prototype")
public class EditorAction extends FileUploadAction {

	private static final long serialVersionUID = 1L;
	private String path;//路径
	private String order;//排序方式
    private File imgFile;
    private String imgFileFileName;
    private String imgFileContentType;
	
    
	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public String getImgFileContentType() {
		return imgFileContentType;
	}

	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	
	/**
	 * 编辑器文件上传-调用父类公用文件上传
	 */
	@Override
	public String fileUpload(){
		super.setDir(this.getDir());
    	super.setFile(this.getImgFile());
    	super.setFileFileName(this.getImgFileFileName());
    	super.setFileContentType(this.getImgFileContentType());
    	
    	return super.fileUpload();
    }
	

	/**
	 * 通过编辑器浏览访问抛出的错误
	 * @param message
	 * @return
	 */
	private String getManageEorror(String message){
		ValueStack vs = getValueStack();
		vs.set(DATA, message);
		return DATA;
	}
	
	
	
	/**
	 * 服务器上文件浏览与选择
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String fileManage() {
		
		if (dir == null || !Arrays.<String> asList(dirType).contains(dir)) {
			return getManageEorror("非法访问！");
		}
		
		path = path == null ? "" : path;
		// 不允许使用..移动到上一级目录 ,最后一个字符不是/
		if (path.indexOf("..") >= 0 || (!StringUtils.isEmpty(path) && !path.endsWith("/"))) {
			return getManageEorror("非法访问！");
		}
		
		//程序目录
		String appPath = ServletActionContext.getServletContext().getContextPath();
		// 上传文件的访问路径
		String rootUrl = appPath+ROOT_PATH+dir+"/";
		//上传文件的绝对路径
		String rootPath = ServletActionContext.getServletContext().getRealPath(ROOT_PATH+dir+"/");
		//当前绝对目录 
		String currentPath = rootPath +"/"+ path;
		//当前访问
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		
		try {
			// 如果目录不存在、创建它
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}

			if (!StringUtils.isEmpty(path)) {
				String str = currentDirPath.substring(0, currentDirPath.length() - 1);
				moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
			}

			// 排序形式，name or size or type
			order = order == null ? "order" : order.toLowerCase();
			// 目录不存在或不是目录
			File currentPathFile = new File(currentPath);
			if (!currentPathFile.isDirectory()) {
				return getManageEorror("访问目录不存在！");
			}

			// 遍历目录取的文件信息
			List<HashMap<String, Object>> fileList = new ArrayList<HashMap<String, Object>>();
			
			if (currentPathFile.listFiles() != null) {
				for (File file : currentPathFile.listFiles()) {
					HashMap<String, Object> hash = new HashMap<String, Object>();
					String fileName = file.getName();
					if (file.isDirectory()) {
						hash.put("is_dir", true);
						hash.put("has_file", (file.listFiles() != null));
						hash.put("filesize", 0L);
						hash.put("is_photo", false);
						hash.put("filetype", "");
					} else if (file.isFile()) {
						String fileExt = fileName.substring(
								fileName.lastIndexOf(".") + 1).toLowerCase();
						hash.put("is_dir", false);
						hash.put("has_file", false);
						hash.put("filesize", file.length());
						hash.put("is_photo", Arrays.<String> asList(imageTypes)
								.contains(fileExt));
						hash.put("filetype", fileExt);
					}
					hash.put("filename", fileName);
					hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
					fileList.add(hash);
				}
			}

			// 根据不同的方式排序
			if ("size".equals(order)) {
				Collections.sort(fileList, new SizeComparator());
			} else if ("type".equals(order)) {
				Collections.sort(fileList, new TypeComparator());
			} else {
				Collections.sort(fileList, new NameComparator());
			}

			JSONObject result = new JSONObject();
			result.put("moveup_dir_path", moveupDirPath);
			result.put("current_dir_path", currentDirPath);
			result.put("current_url", currentUrl);
			result.put("total_count", fileList.size());
			result.put("file_list", fileList);

			getValueStack().set(DATA, result);
		} catch (Exception e) {
			logException(e);
			return getManageEorror("非法访问！");
		}
		return DATA;
	}

	
	/**
	 * 编辑器插件HTML文件跳转
	 * @return
	 */
	public String plugins(){
		return SUCCESS;
	}
}

/**
 * 按名称排序
 */
@SuppressWarnings("rawtypes")
class NameComparator implements Comparator {
	public int compare(Object a, Object b) {
		HashMap oldMap = (HashMap) a;
		HashMap newMap = (HashMap) b;
		if (((Boolean) oldMap.get("is_dir"))
				&& !((Boolean) newMap.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) oldMap.get("is_dir"))
				&& ((Boolean) newMap.get("is_dir"))) {
			return 1;
		} else {
			return ((String) oldMap.get("filename")).compareTo((String) newMap
					.get("filename"));
		}
	}
}

/**
 * 按文件大小排序
 */
@SuppressWarnings("rawtypes")
class SizeComparator implements Comparator {
	public int compare(Object a, Object b) {
		HashMap oldMap = (HashMap) a;
		HashMap newMap = (HashMap) b;
		if (((Boolean) oldMap.get("is_dir"))
				&& !((Boolean) newMap.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) oldMap.get("is_dir"))
				&& ((Boolean) newMap.get("is_dir"))) {
			return 1;
		} else {
			if (((Long) oldMap.get("filesize")) > ((Long) newMap
					.get("filesize"))) {
				return 1;
			} else if (((Long) oldMap.get("filesize")) < ((Long) newMap
					.get("filesize"))) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}

/**
 * 按文件类型排序
 */
@SuppressWarnings("rawtypes")
class TypeComparator implements Comparator {
	public int compare(Object a, Object b) {
		HashMap oldMap = (HashMap) a;
		HashMap newMap = (HashMap) b;
		if (((Boolean) oldMap.get("is_dir"))
				&& !((Boolean) newMap.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) oldMap.get("is_dir"))
				&& ((Boolean) newMap.get("is_dir"))) {
			return 1;
		} else {
			return ((String) oldMap.get("filetype")).compareTo((String) newMap
					.get("filetype"));
		}
	}
}
