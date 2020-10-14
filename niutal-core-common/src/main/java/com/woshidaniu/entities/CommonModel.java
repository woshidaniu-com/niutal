package com.woshidaniu.entities;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.basicutils.CollectionUtils;

/**
 * 
 * @package com.woshidaniu.dao.entities
 * @className: CommonModel
 * @description: 公共模型(教务在用)
 * @author : kangzhidong
 * @date : 2014-4-4
 * @time : 下午01:59:47
 */
public class CommonModel extends ModelBase {
	private static final long serialVersionUID = 1L;
	public String jbny; // 建班年月
	public String bzrgh_id; // 班主任工号
	public String bzxh_id; // 班长学号
	public String fdyh_id; // 辅导员号
	public String zxrs; // 在校人数
	public String bz; // 备注
	/**
	 * 基础数据类型代码
	 */
	public String lxdm;
	public String jgh_id;// 教工号ID
	public String jgh;// 教工号
	public String dwh; // 单位号
	public String xh_id;//学号ID
	public String xh;// 学号
	// 年级专业
	public String njzy;

	//场地类别id
	public String cdlb_id;
	//场地名称
	public String cdmc;
	
	// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
	public String jgpxzd = "1";
	
	// 查询基础select
	public String tableName;// 表名
	public String key;// 键
	public String value;// 值

	/** ===================组件参数接收对象；=========================== */

	public String minNum; // 最小记录数
	public String maxNum; // 最大记录数
	public String order; // 排序方式 asc , desc
	public String index; // 索引；如 njdm_id_list，jg_id_list
	public String sort; // 排序字段
	public String url; // 子页面将要请求的地址
	public String listnav = "false"; // 子页面是否显示a-z字母分页
	public String multiselect;
	public String pinyin; // 拼音
	public String letter; // 进行过滤的拼音首字母
	public String letter_text; // 进行汉字转拼音的字段名称，即组件中的mapper.text映射的字段
	
	/**
	 * JQGrid 映射的数据类型：是学院grid还是专业grid
	 */
	public String gridType;
	/**
	 * 已经选择的对象ID
	 */
	public String[] checked;
	/**
	 * 在grid页面做什么：hidden|select
	 */
	public String doWhat;

	/** ===================模糊过滤条件参数接收对象；=========================== */
	public String filterKey = null;
	public List<String> filter_list; // 年级ID集合

	/** ===================高级查询条件参数接收对象；=========================== */
	public List<String> njdm_id_list; // 年级ID集合
	public List<String> jg_id_list; // 机构ID集合
	public List<String> xy_id_list; // 学院ID集合
	public List<String> xx_id_list; // 系ID集合 ,学院ID集合
	public List<String> zyh_id_list; // 专业ID集合
	public List<String> bh_id_list; // 班级ID集合
	public List<String> xb_list; // 性别集合
	public List<String> sf_list; // 是否启用集合
	public List<String> js_id_list; // 角色ID集合
	public List<String> yhm_list; // 用户名集合
	public List<String> xm_list; // 姓名集合
	public List<String> mrz; //学生信息默认值
	
	
	public String kkbmqzfs;         //开课部门取值方式
	
	public String cjtjfxfs;//成绩统计分析方式
	public String cjtjfxsfklxntd;//成绩统计分析是否考虑校内替代
	public String cjtjfxsfklxwrd;//成绩统计分析是否考虑校外认定
	public String cjtjfxywlx;//成绩统计分析业务类型('tj':统计;'xs':'显示';)
	public String cjtjfxsjkcxs; //成绩统计分析实际课程显示(1:显示实际修读课程，如篮球,   0:计划课程显示，如篮球对应体育1，显示体育1)
	public String cjtjfxbkalsfj; //成绩统计分析补考按六十分记
	public String cjtjfxcxalsfj; //成绩统计分析重修按六十分记
	public String cjtjfxsftjzfcj; //成绩统计分析是否统计作废成绩
	public String cjzfkg; //成绩作废开关（1:按课程作废；2:按成绩作废）
	public String cjzdzjsfs; //成绩最大值计算方式 1：按百分制成绩，2：按成绩绩点
	
	public String ksmcdmb_id; //考试课程代码表id
	public String ksmc; //考试名称
	public List<String> zdmList; //字段名
	
	public List<String> getMrz() {
		return mrz;
	}

	public void setMrz(List<String> mrz) {
		this.mrz = mrz;
	}

	public String getJbny() {
		return jbny;
	}

	public void setJbny(String jbny) {
		this.jbny = jbny;
	}

	public String getBzrgh_id() {
		return bzrgh_id;
	}

	public void setBzrgh_id(String bzrghId) {
		bzrgh_id = bzrghId;
	}

	public String getBzxh_id() {
		return bzxh_id;
	}

	public void setBzxh_id(String bzxhId) {
		bzxh_id = bzxhId;
	}

	public String getFdyh_id() {
		return fdyh_id;
	}

	public void setFdyh_id(String fdyhId) {
		fdyh_id = fdyhId;
	}

	public String getZxrs() {
		return zxrs;
	}

	public void setZxrs(String zxrs) {
		this.zxrs = zxrs;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getLxdm() {
		return lxdm;
	}

	public void setLxdm(String lxdm) {
		this.lxdm = lxdm;
	}

	public String getJgh_id() {
		return jgh_id;
	}

	public void setJgh_id(String jghId) {
		jgh_id = jghId;
	}

	public String getJgh() {
//		try {
//			jgh = jgh==null?null:java.net.URLDecoder.decode(jgh,"utf-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return jgh;
	}

	public void setJgh(String jgh) {
		this.jgh = jgh;
	}

	public String getDwh() {
		return dwh;
	}

	public void setDwh(String dwh) {
		this.dwh = dwh;
	}


	public String getXh_id() {
		return xh_id;
	}

	public void setXh_id(String xhId) {
		xh_id = xhId;
	}
	
	public void setXh(String xh) {
		this.xh = xh;
	}
	
	public String getXh() {
		/*try {
			xh = xh==null?null:java.net.URLDecoder.decode(xh,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return xh;
	}

	public String getNjzy() {
		try {
			njzy = njzy==null?null:java.net.URLDecoder.decode(njzy,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return njzy;
	}

	public void setNjzy(String njzy) {
		this.njzy = njzy;
	}

	public String getMinNum() {
		return minNum;
	}

	public void setMinNum(String minNum) {
		this.minNum = minNum;
	}

	public String getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getListnav() {
		return listnav;
	}

	public void setListnav(String listnav) {
		this.listnav = listnav;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getLetter_text() {
		return letter_text;
	}

	public void setLetter_text(String letterText) {
		letter_text = letterText;
	}

	public String getGridType() {
		return gridType;
	}

	public void setGridType(String gridType) {
		this.gridType = gridType;
	}

	public String[] getChecked() {
		return checked;
	}

	public void setChecked(String[] checked) {
		this.checked = checked;
	}

	public String getDoWhat() {
		return doWhat;
	}

	public void setDoWhat(String doWhat) {
		this.doWhat = doWhat;
	}

	public String getFilterKey() {
		return filterKey;
	}

	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}

	public List<String> getFilter_list() {
		return CollectionUtils.killNull(filter_list) ;
	}

	public void setFilter_list(List<String> filterList) {
		filter_list = filterList;
	}

	public List<String> getNjdm_id_list() {
		return CollectionUtils.killNull(njdm_id_list);
	}

	public void setNjdm_id_list(List<String> njdmIdList) {
		njdm_id_list = njdmIdList;
	}

	public List<String> getJg_id_list() {
		return CollectionUtils.killNull(jg_id_list);
	}

	public void setJg_id_list(List<String> jgIdList) {
		jg_id_list = jgIdList;
	}

	public List<String> getXy_id_list() {
		return CollectionUtils.killNull(xy_id_list);
	}

	public void setXy_id_list(List<String> xyIdList) {
		xy_id_list = xyIdList;
	}

	public List<String> getXx_id_list() {
		return CollectionUtils.killNull(xx_id_list);
	}

	public void setXx_id_list(List<String> xxIdList) {
		xx_id_list = xxIdList;
	}

	public List<String> getZyh_id_list() {
		return CollectionUtils.killNull(zyh_id_list);
	}

	public void setZyh_id_list(List<String> zyhIdList) {
		zyh_id_list = zyhIdList;
	}

	public List<String> getBh_id_list() {
		return CollectionUtils.killNull(bh_id_list);
	}

	public void setBh_id_list(List<String> bhIdList) {
		bh_id_list = bhIdList;
	}

	public List<String> getXb_list() {
		return CollectionUtils.killNull(xb_list);
	}

	public void setXb_list(List<String> xbList) {
		xb_list = xbList;
	}

	public List<String> getSf_list() {
		return CollectionUtils.killNull(sf_list);
	}

	public void setSf_list(List<String> sfList) {
		sf_list = sfList;
	}

	public List<String> getJs_id_list() {
		return CollectionUtils.killNull(js_id_list);
	}

	public void setJs_id_list(List<String> jsIdList) {
		js_id_list = jsIdList;
	}

	public List<String> getYhm_list() {
		return CollectionUtils.killNull(yhm_list);
	}

	public void setYhm_list(List<String> yhmList) {
		yhm_list = yhmList;
	}

	public List<String> getXm_list() {
		return CollectionUtils.killNull(xm_list);
	}

	public void setXm_list(List<String> xmList) {
		xm_list = xmList;
	}

	public String getMultiselect() {
		return multiselect;
	}

	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}

	public String getCdlb_id() {
		return cdlb_id;
	}

	public void setCdlb_id(String cdlbId) {
		cdlb_id = cdlbId;
	}

	public String getCdmc() {
		return cdmc;
	}

	public void setCdmc(String cdmc) {
		this.cdmc = cdmc;
	}

	public String getJgpxzd() {
		return jgpxzd;
	}

	public void setJgpxzd(String jgpxzd) {
		this.jgpxzd = jgpxzd;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKkbmqzfs() {
		return kkbmqzfs;
	}

	public void setKkbmqzfs(String kkbmqzfs) {
		this.kkbmqzfs = kkbmqzfs;
	}

	public String getCjtjfxfs() {
		return cjtjfxfs;
	}

	public void setCjtjfxfs(String cjtjfxfs) {
		this.cjtjfxfs = cjtjfxfs;
	}

	public String getCjtjfxsfklxntd() {
		return cjtjfxsfklxntd;
	}

	public void setCjtjfxsfklxntd(String cjtjfxsfklxntd) {
		this.cjtjfxsfklxntd = cjtjfxsfklxntd;
	}

	public String getCjtjfxsfklxwrd() {
		return cjtjfxsfklxwrd;
	}

	public void setCjtjfxsfklxwrd(String cjtjfxsfklxwrd) {
		this.cjtjfxsfklxwrd = cjtjfxsfklxwrd;
	}

	public String getCjtjfxywlx() {
		return cjtjfxywlx;
	}

	public void setCjtjfxywlx(String cjtjfxywlx) {
		this.cjtjfxywlx = cjtjfxywlx;
	}

	public String getCjtjfxsjkcxs() {
		return cjtjfxsjkcxs;
	}

	public void setCjtjfxsjkcxs(String cjtjfxsjkcxs) {
		this.cjtjfxsjkcxs = cjtjfxsjkcxs;
	}

	public String getCjtjfxbkalsfj() {
		return cjtjfxbkalsfj;
	}

	public void setCjtjfxbkalsfj(String cjtjfxbkalsfj) {
		this.cjtjfxbkalsfj = cjtjfxbkalsfj;
	}

	public String getCjtjfxcxalsfj() {
		return cjtjfxcxalsfj;
	}

	public void setCjtjfxcxalsfj(String cjtjfxcxalsfj) {
		this.cjtjfxcxalsfj = cjtjfxcxalsfj;
	}

	public String getCjtjfxsftjzfcj() {
		return cjtjfxsftjzfcj;
	}

	public void setCjtjfxsftjzfcj(String cjtjfxsftjzfcj) {
		this.cjtjfxsftjzfcj = cjtjfxsftjzfcj;
	}

	public String getCjzfkg() {
		return cjzfkg;
	}

	public void setCjzfkg(String cjzfkg) {
		this.cjzfkg = cjzfkg;
	}

	public String getCjzdzjsfs() {
		return cjzdzjsfs;
	}

	public void setCjzdzjsfs(String cjzdzjsfs) {
		this.cjzdzjsfs = cjzdzjsfs;
	}

	public String getKsmcdmb_id() {
		return ksmcdmb_id;
	}

	public void setKsmcdmb_id(String ksmcdmb_id) {
		this.ksmcdmb_id = ksmcdmb_id;
	}

	public String getKsmc() {
		return ksmc;
	}

	public void setKsmc(String ksmc) {
		this.ksmc = ksmc;
	}

	public List<String> getZdmList() {
		return zdmList;
	}

	public void setZdmList(List<String> zdmList) {
		this.zdmList = zdmList;
	}
	
	
	
}
