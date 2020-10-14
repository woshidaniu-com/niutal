package com.woshidaniu.dao.entities;

import java.util.HashMap;
import java.util.List;

import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.search.core.SearchModel;

public class PWModel extends ModelBase {

	private static final long serialVersionUID = 1L;

	/** @fields : mkdm : 模块代码 */
	private String mkdm;
	/** @fields : mkmc : 模块名称 */
	private String mkmc;
	/** @fields : sjy : 数据源（表或视图） */
	private String sjy;
	/** @fields : id : 主键 */
	private String id;
	/** @fields : col : 字段 */
	private String col;
	/** @fields : com : 字段注释 */
	private String com;
	/** @fields : key : 主键标识 */
	private String key;
	/** @fields : zt : 启用状态 */
	private String zt;
	/** @fields : xssx : 显示顺序 */
	private String xssx;
	/** @fields : filename : 文件名标识 */
	private String filename;
	/** @fields : br : 换行标识 */
	private String br;
	/** @fields : drmbmc : 导入模板名称 */
	private String drmbmc;
	/** @fields : drpath : 导入模版路径 */
	private String drpath;
	/** @fields : expath : 原路径 */
	private String expath;
	/** @fields : model : PWModel */
	private PWModel model;
	/** @fields : modelList : 列表 */
	private List<PWModel> modelList;
	/** @fields : objMap : HashMap<String, Object> */
	private HashMap<String, Object> objMap;
	/** @fields : list : List<HashMap<String, String>> */
	private List<HashMap<String, String>> list;
	/** @fields : searchModel : 高级查询原型 */
	public SearchModel searchModel;

	public String getMkdm() {
		return mkdm;
	}
	public void setMkdm(String mkdm) {
		this.mkdm = mkdm;
	}
	public String getMkmc() {
		return mkmc;
	}
	public void setMkmc(String mkmc) {
		this.mkmc = mkmc;
	}
	public String getSjy() {
		return sjy;
	}
	public void setSjy(String sjy) {
		this.sjy = sjy;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getBr() {
		return br;
	}
	public void setBr(String br) {
		this.br = br;
	}
	public String getDrmbmc() {
		return drmbmc;
	}
	public void setDrmbmc(String drmbmc) {
		this.drmbmc = drmbmc;
	}
	public String getDrpath() {
		return drpath;
	}
	public void setDrpath(String drpath) {
		this.drpath = drpath;
	}
	public String getExpath() {
		return expath;
	}
	public void setExpath(String expath) {
		this.expath = expath;
	}
	public PWModel getModel() {
		return model;
	}
	public void setModel(PWModel model) {
		this.model = model;
	}
	public List<PWModel> getModelList() {
		return modelList;
	}
	public void setModelList(List<PWModel> modelList) {
		this.modelList = modelList;
	}
	public HashMap<String, Object> getObjMap() {
		return objMap;
	}
	public void setObjMap(HashMap<String, Object> objMap) {
		this.objMap = objMap;
	}
	public List<HashMap<String, String>> getList() {
		return list;
	}
	public void setList(List<HashMap<String, String>> list) {
		this.list = list;
	}
	public SearchModel getSearchModel() {
		return searchModel;
	}
	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}
}