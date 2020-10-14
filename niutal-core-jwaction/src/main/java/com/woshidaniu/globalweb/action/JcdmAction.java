package com.woshidaniu.globalweb.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.ColModel;
import com.woshidaniu.entities.JcdmModel;
import com.woshidaniu.entities.NjdmModel;
import com.woshidaniu.entities.YjfszhxxModel;
import com.woshidaniu.format.fastjson.FastJSONObject;
import com.woshidaniu.format.utils.PatternFormatUtils;
import com.woshidaniu.service.svcinterface.IJcdmService;
import com.woshidaniu.util.xml.BaseData;
import com.woshidaniu.util.xml.BaseDataReader;
/***
 * @功能  自定义基础代码维护Action
 * @说明 目前已经支持文本输入框、单选项、支持日期
 * @author majun
 * @version 1.0.1
 *
 */
@SuppressWarnings("unchecked")
public class JcdmAction extends CommonBaseAction implements ModelDriven<JcdmModel>{
	protected static Log logger = LogFactory.getLog(JcdmAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String EXP_SQL = "";
	private JcdmModel model = new JcdmModel();
	private List<?> en_col_List;
	private List<?> cn_col_List;
	private Map<String,Map<?,?>> table_items;  //表元素映射
	private Map<String,List<?>> table_fields;//表字段映射
	
	@Resource
	private IJcdmService jcdmService;
	
	//年级代码model
	protected NjdmModel njdmModel = new NjdmModel();
	
	//邮件发送账号信息Model
	protected YjfszhxxModel yjfszhxxModel = new YjfszhxxModel();
	
	/***
	 * 获取XML配置文件中表元素
	 * 获取基础表配置项
	 * @param table
	 * @return
	 */
	private Map<?,?> getTable_items(String table){
		Map<?,?> map = (Map)ServiceFactory.getService(table+"_config");
		return map;
	}
	/**
	 * 获取XML配置文件中List显示列表
	 * @param table
	 * @return
	 */
	private List<?> getTable_fields(String table){
		List<?> list = (List)ServiceFactory.getService(table+"_list");
		return list;
	}
	
	/***
	 * @描述：查询基础代码维护
	 * @创建人：majun
	 * @创建时间：2014-06-11 16：08
	 * @修改人：
	 * @修改时间：
	 * @修改描述：
	 * @return
	 */
	public String cxJcdmIndex(){
		getValueStack().set("gnmkdm", getRequest().getParameter("gnmkdm"));
		try {
			getItems(model.getTable());
			getColModel(model.getTable());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	/***
	 * @描述：显示增加、修改基础数据页面
	 * @return
	 */
	public String zjJcdm(){
		BaseMap obj = null;
		getValueStack().set("doType", "insert");
		try {
			if(StringUtils.isNotEmpty(model.getPrimary_key())){
				model.setTable_fields(getFields());
				obj = getJcdmService().getObject(model);
				getValueStack().set("doType", "update");
			}
			List<ColModel> colList = getPageFields(model.getTable(),obj);
			getValueStack().set("colList", colList);
			getValueStack().set("notNullableField", getNotNullableField(colList));
			
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * @Description:保存基础代码
	 * @return String
	 * @throws
	 */
	public String zjBcjcdm() {
		boolean result = false;
		try {
			getParmValus(model,"add");
			result = getJcdmService().insert(model);
			String key = result ? "I99001" : "I99002";
			getValueStack().set(DATA, getText(key));
			this.clearCache(model.getTable());
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99002"));

		}
		return DATA;
	}
	/**
	 * 
	 *@描述：修改基础代码 显示界面
	 *@创建人:majun
	 *@创建时间:2014-7-14下午07:47:38
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgJcdm(){
		BaseMap obj = null;
		getValueStack().set("doType", "insert");
		try {
			if(StringUtils.isNotEmpty(model.getPrimary_key())){
				model.setTable_fields(getFields());
				model.setPrimary_value(URLDecoder.decode(model.getPrimary_value(), "utf-8"));
				obj = getJcdmService().getObject(model);
				getValueStack().set("doType", "update");
			}
			List<ColModel> colList = getPageFields(model.getTable(),obj);
			getValueStack().set("colList", colList);
			getValueStack().set("notNullableField", getNotNullableField(colList));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 
	 *@描述：修改保存基础代码
	 *@创建人:majun
	 *@创建时间:2014-7-14下午07:47:50
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgBcJcdm() {
		try {
			getParmValus(model,"modify");
			getJcdmService().update(model);
			this.clearCache(model.getTable());
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99002"));

		}
		return DATA;
	}
	/**
	 * @Description:分页查询
	 * @return String
	 * @throws
	 */
	public String cxJcdmList() {
		try {
			QueryModel queryModel = model.getQueryModel();
			model.setTable_fields(getFields());
			if(getTable_items(model.getTable()).get("queryWhere")!=null){
				String queryWhere = getTable_items(model.getTable()).get("queryWhere").toString();
				//queryWhere =StringPatternFormat.getInstance().format(queryWhere, njdmModel);
				model.setQueryWhere(queryWhere);
			}
			if(model.getTable().equals("niutal_xtgl_njdmb")){
				List<String> querys = new ArrayList<String>();
				if(!BlankUtils.isBlank(njdmModel.getSfsy())) {
					querys.add("sfsy = '" + njdmModel.getSfsy() + "'" );
				}
				if(!BlankUtils.isBlank(njdmModel.getNjmc())) {
					querys.add("(njmc like '%" + njdmModel.getNjmc() + "%' or njdm like '%" + njdmModel.getNjmc() + "%')");
				}
				
				model.setQueryWhere(StringUtils.join(querys, " and "));
			} else if (model.getTable().equals("niutal_xtgl_yjfszhxxb")) {
				List<String> querys = new ArrayList<String>();
				if(!BlankUtils.isBlank(yjfszhxxModel.getFslx())) {
					querys.add("fslx = '" + yjfszhxxModel.getFslx() + "'" );
				}
				if(!BlankUtils.isBlank(yjfszhxxModel.getYjzh())) {
					querys.add("(yjzh like '%" + yjfszhxxModel.getYjzh() + "%')");
				}
				
				model.setQueryWhere(StringUtils.join(querys, " and "));
			}
			
			List lists =  getJcdmService().getPagedByScope(model);
			queryModel.setItems(lists);
			String exp_sql = queryModel.getExpSql();
			if(StringUtils.isNotEmpty(exp_sql)){
				getSession().setAttribute(EXP_SQL, exp_sql);
			}
			getValueStack().set(DATA, queryModel);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	/**
	 * @Description:删除基础代码
	 * @return String
	 * @throws
	 */
	public String scJcdm() {
		try {
			String ids = getRequest().getParameter("ids");
			if (StringUtils.isNotBlank(ids)) {
				String[] pks = ids.split(",");
				model.setPks(pks);
				getJcdmService().delete(model);
				getValueStack().set(DATA, getText("I99005"));
				this.clearCache(model.getTable());
			}
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99006"));
		}
		return DATA;
	}
	/***
	 * 
	 *@描述：清空缓存
	 *@创建人:majun
	 *@创建时间:2014-8-27下午07:58:24
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	private void clearCache(String table){
		try {
			Map itemsMap =  getTable_items(table);
			if(itemsMap.get("cacheKey")!=null){
				String cacheKey = String.valueOf(itemsMap.get("cacheKey"));
				//缓存服务器开启
				if(cacheSupport){
					//通过反射获取类中静态变量
					getCache().evict(cacheKey);
				}
			}
		} catch (Exception e) {
			logStackException(e);
		} 
		 
	}
	
	/**
	 * 操作验证
	 * @return
	 */
	public String cxValidate(){
		boolean is  = true;
		Map<String,String> map = (Map<String,String>)getTable_items(model.getTable());
		String delete_validate_sql = map.get("delete_validate_sql");
		String update_validate_sql = map.get("update_validate_sql");
		if("delete".equals(model.getDoType())&&delete_validate_sql!=null){
			JcdmModel jcxxModel = new JcdmModel();
			String ids = getRequest().getParameter("ids");
			for(int i=0;i<ids.split(",").length;i++){
				String sql = PatternFormatUtils.format(delete_validate_sql, new String[]{ids.split(",")[i]});
				jcxxModel.setValidateSql(sql);
				List<Map<String,String>> list  = getJcdmService().getValidateList(jcxxModel);
				if(list.size()>0){
					is = false;
					break;
				}
			}
		}else if("update".equals(model.getDoType())&&update_validate_sql!=null){
			JcdmModel jcxxModel = new JcdmModel();
			String ids = getRequest().getParameter("ids");
			for(int i=0;i<ids.split(",").length;i++){
				String sql = PatternFormatUtils.format(update_validate_sql, new String[]{ids.split(",")[i]});
				jcxxModel.setValidateSql(sql);
				List<Map<String,String>> list  = getJcdmService().getValidateList(jcxxModel);
				if(list.size()>0){
					is = false;
					break;
				}
			}
		}
		getValueStack().set(DATA, is);
		return DATA;
	}
	
	/**
	 * @Description: 获取XML配置文件中表元素
	 * @param table
	 * @return String
	 * @throws
	 */
	private void getItems(String table){
		ValueStack vs = getValueStack();
		try {
			Map<String,String> map = (Map<String,String>)getTable_items(table);
			String table_name      = map.get("table_name");
			String table_sortname  = map.get("table_sortname");
			String table_sortorder = map.get("table_sortorder");
			String table_multiselect = map.get("table_multiselect");
			String width 		   = map.get("width");//弹出层宽
			String hight 		   = map.get("height");//弹出层高
			vs.set("caption", table_name);
			vs.set("sortname", table_sortname);
			vs.set("sortorder", table_sortorder);
			vs.set("multiselect", StringUtils.getSafeBoolean(table_multiselect, "true"));
			vs.set("width", width);
			vs.set("height", hight);
			
		} catch (Exception ex) {
			logger.error(ex, ex);
		}
	}
	/**
	 * @Description: 获取XML配置文件中List显示列表
	 * @param table
	 * @return String
	 * @throws
	 */
	private void getColModel(String table){
		List<Map> filedList = (List<Map>) getTable_fields(table);
		ValueStack vs = getValueStack();
		List<ColModel> list = new ArrayList<ColModel>();
		ColModel colModel = null;
		String json = "";String primary_key="";String hidden="";
		try {
			for (Map<String, String> filed : filedList) {
				colModel = new ColModel();
				colModel.setLabel(filed.get("comments"));
				colModel.setName(filed.get("column_name"));
				colModel.setIndex(filed.get("column_name"));
				if("true".equalsIgnoreCase(filed.get("primary_key"))){
					colModel.setKey(true);
					primary_key = filed.get("column_name");
				}
				hidden = filed.get("hidden");
				colModel.setHidden("true".equalsIgnoreCase(hidden) ? true : false);
				colModel.setAlign(filed.get("align"));
				colModel.setWidth(filed.get("width"));
				list.add(colModel);
			}
			json = FastJSONObject.toCleanJSONString(list);
			json = json.replace("\"", "\'");
		} catch (Exception ex) {
			logger.error(ex, ex);
		}
		vs.set("primary_key", primary_key);
		vs.set("colModel",json);
	}
	/**
	 * @Description: 根据参数名称和值,包装字符串
	 * @return String
	 * @throws
	 */
	private JcdmModel getParmValus(JcdmModel model,String doType){
		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();
		List<Map> filedList = (List<Map>) getTable_fields(model.getTable());
		//获取XML配置的主键字段
		String filed_  = "";
		String hidden_ = "";
		for (Map<String, String> filed : filedList) {
			if("true".equalsIgnoreCase(filed.get("primary_key"))){
				filed_  = filed.get("column_name");
				hidden_ = filed.get("hidden");
				break;
			}
		}
		Iterator paramFiled = filedList.iterator();
		if("add".equals(doType)){
			while (paramFiled.hasNext()) {
				Map<String, String> map = (Map)paramFiled.next();
				String paramName = map.get("column_name").toString();
				
				if (BlankUtils.isBlank(map.get("ignore"))) {
					
					//去serialize后的table字段
					if(filed_.equals(paramName)&&!"true".equals(hidden_)){
						String[] paramValues = getRequest().getParameterValues(paramName);
						if (paramValues.length == 1) {
							String paramValue = paramValues[0];
							if (paramValue.length() != 0) {
								fields.append(paramName).append(",");
								values.append("'").append(paramValue).append("'").append(",");
							}else{
								fields.append(paramName+",");
								values.append("'").append(paramValue).append("'").append(",");
							}
						}
					}else{
						if(!"table".equals(paramName) && !filed_.equals(paramName)){
							if("zjr".equalsIgnoreCase(paramName)){
								fields.append(paramName).append(",");
								values.append("'").append(getUser().getYhm()).append("'").append(",");
							}else if("zjsj".equalsIgnoreCase(paramName)){
								fields.append(paramName).append(",");
								values.append("'").append(getJcdmService().getNowDate("yyyy-MM-dd HH24:mi:ss")).append("'").append(",");
							}else{
								String[] paramValues = getRequest().getParameterValues(paramName);
								if (paramValues.length == 1) {
									String paramValue = paramValues[0];
									if (paramValue.length() != 0) {
										fields.append(paramName).append(",");
										values.append("'").append(paramValue).append("'").append(",");
									}else{
										fields.append(paramName+",");
										values.append("'").append(paramValue).append("'").append(",");
									}
								}
							}
						}
					}
					
					if(fields.length()>0){
						model.setTable_fields(StringUtils.removeLast(fields.toString()));
						model.setValues(StringUtils.removeLast(values.toString()));
					}
				}
			}
		}
		else if("modify".equals(doType)){
			while (paramFiled.hasNext()) {
				Map<String, String> map = (Map)paramFiled.next();
				String paramName = map.get("column_name").toString();
				
				if (BlankUtils.isBlank(map.get("ignore"))) {
					//去serialize后的table字段
					if(!"table".equals(paramName)){
						String[] paramValues = getRequest().getParameterValues(paramName);
						if(filed_.equals(paramName)){
							model.setPrimary_key(paramName);
							model.setPrimary_value(paramValues[0]);
						}else{
							if("xgr".equalsIgnoreCase(paramName)){
								fields.append(paramName).append("=").append("'").append(getUser().getYhm()).append("'").append(",");
							}else if("xgsj".equalsIgnoreCase(paramName)){
								fields.append(paramName).append("=").append("'").append(getJcdmService().getNowDate("yyyy-MM-dd HH24:mi:ss")).append("'").append(",");
							}else if("zjr".equalsIgnoreCase(paramName)||"zjsj".equalsIgnoreCase(paramName)){
								
							}else{
								if (paramValues.length == 1) {
									String paramValue = paramValues[0];
									fields.append(paramName).append("=").append("'").append(paramValue).append("'").append(",");
								}
							}						
						}
					}
					if(fields.length()>0){
						model.setTable_fields(StringUtils.removeLast(fields.toString()));
					}
				}
			}			
		}
		return model;
	}
	/**
	 * @Description:获取XML配置文件中的表字段
	 * @return String
	 * @throws
	 */
	private String getFields() {
		List<Map<String, String>> filedList = (List<Map<String, String>>) getTable_fields(model.getTable());
		Map<String, String> table_items     = (Map<String, String>)getTable_items(model.getTable());
		String querySql  	  =  table_items.get("querySql");
		String result 		  = "";
		StringBuilder builder = new StringBuilder();
		try {
			if(!BlankUtils.isBlank(querySql)){
				result =  querySql;
			}else{
				//拼装SQL语句
				for (Map<String, String> filed : filedList) {
					String transform = filed.get("transform");
					if (StringUtils.isNotEmpty(transform)) {
						StringBuilder temp = new StringBuilder();
						temp.append(filed.get("column_name")).append(",");
						List<HashMap<String,String>> listA = BaseDataReader.getCachedOptionList(transform);
						for(int i=0;i<listA.size();i++){
							Map mapA =  listA.get(i);
							temp.append("'").append(mapA.get("key")).append("'").append(",");
							temp.append("'").append(mapA.get("value")).append("'").append(",");
						}
						String tempStr = temp.toString();
						builder.append("decode").append("(").append(tempStr.substring(0, tempStr.length()-1)).append(") ").append(filed.get("column_name")).append(",");
					}else if("select".equals(filed.get("type"))){
						builder.append(" (select "+filed.get("listValue")+" from "+filed.get("listTable")+" where "+filed.get("listKey")+"=")
						.append(model.getTable()+"."+filed.get("column_name")+")"+filed.get("column_name")+",");
					} else {
						builder.append(filed.get("column_name")).append(",");
					}

				}
				result = StringUtils.removeLast(builder.toString());
			}
		} catch (Exception ex) {
			logger.error(ex, ex);
		}
		return result;
	}
	
	/**
	 * @Description:获取XML配置文件中的页面显示字段
	 * @return List<ColModel>
	 * @throws
	 */
	private List<ColModel> getPageFields(String table,BaseMap obj){
		//如果参数obj为空则代表增加操作,否则为修改操作
		List<Map> filedList = (List<Map>) getTable_fields(table);
		List<ColModel> list = new ArrayList<ColModel>();
		ColModel colModel = null;String type="";
		try {
			for (Map<String, Object> filed : filedList) {
				type = String.valueOf(filed.get("type"));
				if(StringUtils.isNotEmpty(type)){
					colModel = new ColModel();
					colModel.setType(type);
					colModel.setLabel(String.valueOf(filed.get("comments")));
					colModel.setName(String.valueOf(filed.get("column_name")));
					colModel.setDateFmt(String.valueOf(filed.get("dateFmt")));
					colModel.setNullable("false".equals(String.valueOf(filed.get("Nullable")))?false:true);
					colModel.setDesc(StringUtils.getSafeObj(filed.get("desc")));
					//增加默认字段
					colModel.setValue(filed.get("default") == null ? null : String.valueOf(filed.get("default")));
					//colModel.setTargetID(StringUtils.getSafeStr("targetID"));
					//组装验证规则
					if(filed.get("validateMap")!=null){
						Map<String, String>   validateMap =  (Map<String, String>) filed.get("validateMap");
						List<String> conditions = new ArrayList<String>();
						for(String key:validateMap.keySet()){
							conditions.add(StringUtils.quote(key) + ":" + validateMap.get(key));
						}
						colModel.setValidateMap("{"+StringUtils.join(conditions, ",")+"}");
					}
					
					
					//TODO 从baseData.xml中取相应标签的值,
					if("radio".equals(type)){
						List<BaseData> listA = BaseDataReader.getCachedBaseDataList(String.valueOf(filed.get("transform")));
						colModel.setBoxList(listA);
					}else if("select".equals(type)){
						//根据配置文件中QuerySql字段配置的SQl查询。
						List qlist =   getJcdmService().getList(String.valueOf(filed.get("QuerySql")));
						colModel.setSelectList(qlist);
						colModel.setListKey(String.valueOf(filed.get("listKey")));
						colModel.setListValue(String.valueOf(filed.get("listValue")));
					}
					if(!CollectionUtils.isEmpty(obj)){
						String targetColumn = StringUtils.getSafeStr(filed.get("targetColumn"));
						String v_ = null;
						if(BlankUtils.isBlank(targetColumn)){
							v_ = String.valueOf(obj.get(filed.get("column_name")));
						}else{
							v_ = String.valueOf(obj.get(targetColumn));
						}
						colModel.setValue("null".equals(v_)?"":v_);
						colModel.setReadonly("readonly".equals(String.valueOf(filed.get("readonly")))?"readonly":"");
					}
				}
				list.add(colModel);
			}
		} catch (Exception ex) {
			logger.error(ex, ex);
		}
		return list;
	}
	/***
	 * 返回必填字段如多个字段显示格式:A!!B!!C
	 * @return
	 */
	public String getNotNullableField(List<ColModel> list){
		StringBuffer field = new StringBuffer();
		for(int i=0;i<list.size();i++){
			ColModel model =  list.get(i);
			if(model.isNullable()==false){
				if(field.length()>0){
					field.append("!!"+model.getName());
				}else{
					field.append(model.getName());
				}
				
			}
			
		}
		return field.toString();
	}
	/**
	 * 验证字段唯一
	 * @return
	 */
	public String cxUniqueValidate(){
		String doType =  getRequest().getParameter("doType");
		JcdmModel jcdmModel    = new JcdmModel();
		Enumeration paramNames = getRequest().getParameterNames();
	    Map<String,Object> map = new HashMap<String, Object>();
		while (paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				String[] paramValues = getRequest().getParameterValues(paramName);
				map.put(paramName, paramValues.length>0?paramValues[0]:"");
		}
		
		List<Map> filedList = (List<Map>) getTable_fields(model.getTable());
		//获取XML配置的主键字段
		String filed_ = "";
		for (Map<String, String> filed : filedList) {
			if("true".equalsIgnoreCase(filed.get("primary_key"))){
				filed_ = filed.get("column_name");
				break;
			}
		}
		
		//循环字段需要验证的字段
		String info = "-1";
		for(Map<String, String> filed : filedList) {
			String unique   = filed.get("unique");
			String comments = filed.get("comments");
			if("true".equals(unique)){
				String  validateSql  =  PatternFormatUtils.format(filed.get("filedValidate"), map);
				jcdmModel.setValidateSql(validateSql);
				List list  = getJcdmService().getValidateList(jcdmModel);
				if("insert".equals(doType)){
					if(list.size()>0){
						info  = comments+"不能重复";
						break;
					}
				}else{
					if(list.size()>1){
						info  = comments+"不能重复";
						break;
					}else if(list.size()==1){
						Map map1 =  (Map) list.get(0);
						if(!map1.get(filed_).equals(map.get(filed_))){
							info  = comments+"不能重复";
							break;
						}
					}
				}
			}
		}
		getValueStack().set(DATA,info);
		return DATA;
	}
	
	/**
	 * 
	 *@描述：邮箱维护 查看密码 显示界面
	 *@创建人:zfankai
	 *@创建时间:2016-1-7下午14:47:38
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String ckmmJcdm(){
		BaseMap obj = null;
		getValueStack().set("doType", "insert");
		try {
			if(StringUtils.isNotEmpty(model.getPrimary_key())){
				model.setTable_fields(getFields());
				model.setPrimary_value(URLDecoder.decode(model.getPrimary_value(), "utf-8"));
				obj = getJcdmService().getObject(model);
				getValueStack().set("doType", "update");
			}
			List<ColModel> colList = getPageFields(model.getTable(),obj);
			getValueStack().set("colList", colList);
			getValueStack().set("notNullableField", getNotNullableField(colList));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public IJcdmService getJcdmService() {
		return jcdmService;
	}
	public void setJcdmService(IJcdmService jcdmService) {
		this.jcdmService = jcdmService;
	}
	
	public List<?> getEn_col_List() {
		return en_col_List;
	}



	public void setEn_col_List(List<?> enColList) {
		en_col_List = enColList;
	}



	public List<?> getCn_col_List() {
		return cn_col_List;
	}



	public void setCn_col_List(List<?> cnColList) {
		cn_col_List = cnColList;
	}
	public Map<String, Map<?, ?>> getTable_items() {
		return table_items;
	}


	public void setTable_items(Map<String, Map<?, ?>> tableItems) {
		table_items = tableItems;
	}


	public Map<String, List<?>> getTable_fields() {
		return table_fields;
	}



	public void setTable_fields(Map<String, List<?>> tableFields) {
		table_fields = tableFields;
	}



	public void setModel(JcdmModel model) {
		this.model = model;
	}



	public static String getExpSql() {
		return EXP_SQL;
	}


	@Override
	public JcdmModel getModel() {
		model.setUser(getUser());
		return model;
	}
	public NjdmModel getNjdmModel() {
		return njdmModel;
	}
	public void setNjdmModel(NjdmModel njdmModel) {
		this.njdmModel = njdmModel;
	}
	public YjfszhxxModel getYjfszhxxModel() {
		return yjfszhxxModel;
	}
	public void setYjfszhxxModel(YjfszhxxModel yjfszhxxModel) {
		this.yjfszhxxModel = yjfszhxxModel;
	}
	
}
