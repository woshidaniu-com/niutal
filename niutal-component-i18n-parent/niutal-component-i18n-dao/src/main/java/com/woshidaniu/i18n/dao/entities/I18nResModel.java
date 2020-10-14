package com.woshidaniu.i18n.dao.entities;

import java.util.List;

import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.query.ModelBase;

/**
 * 
 * @类名称:ZjdxI18nModel.java
 * @类描述：国际化资源文件代码
 * @创建人：WuXinfeng
 * @创建时间：2017年1月11日 下午4:52:04
 * @版本号:v1.0
 */
@SuppressWarnings("serial")
public class I18nResModel extends ModelBase {

	private String res_oid; // 资源信息OID
	private String res_key; // 资源内容主键
	private String zh_cn;// 中文描述
	private String en_us;// 英文描述
	private String locale;// 中英文

	private String res_code;// 资源文件代码
	private String res_name;// 资源文件名称
	private String res_desc;// 资源文件描述
	private String res_xxdm;// 学校代码
	private String res_type;// 资源文件类型：js, properties
	private String res_path;// 资源文件路径
	private String res_zjsj;// 增加时间
	private String res_zjyh;// 增加用户
	private String res_fbbj;// 发布标记
	private String res_fbsj;// 发布时间
	private String res_fbyh;// 发布用户
	private String res_bz;// 备注

	private List<BaseMap> i18nMaps;

	public String getRes_oid() {
		return res_oid;
	}

	public void setRes_oid(String res_oid) {
		this.res_oid = res_oid;
	}

	public String getRes_key() {
		return res_key;
	}

	public void setRes_key(String res_key) {
		this.res_key = res_key;
	}

	public String getZh_cn() {
		return zh_cn;
	}

	public void setZh_cn(String zh_cn) {
		this.zh_cn = zh_cn;
	}

	public String getEn_us() {
		return en_us;
	}

	public void setEn_us(String en_us) {
		this.en_us = en_us;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getRes_name() {
		return res_name;
	}

	public void setRes_name(String res_name) {
		this.res_name = res_name;
	}

	public String getRes_desc() {
		return res_desc;
	}

	public void setRes_desc(String res_desc) {
		this.res_desc = res_desc;
	}

	public String getRes_xxdm() {
		return res_xxdm;
	}

	public void setRes_xxdm(String res_xxdm) {
		this.res_xxdm = res_xxdm;
	}

	public String getRes_type() {
		return res_type;
	}

	public void setRes_type(String res_type) {
		this.res_type = res_type;
	}

	public String getRes_path() {
		return res_path;
	}

	public void setRes_path(String res_path) {
		this.res_path = res_path;
	}

	public String getRes_zjsj() {
		return res_zjsj;
	}

	public void setRes_zjsj(String res_zjsj) {
		this.res_zjsj = res_zjsj;
	}

	public String getRes_zjyh() {
		return res_zjyh;
	}

	public void setRes_zjyh(String res_zjyh) {
		this.res_zjyh = res_zjyh;
	}

	public String getRes_fbbj() {
		return res_fbbj;
	}

	public void setRes_fbbj(String res_fbbj) {
		this.res_fbbj = res_fbbj;
	}

	public String getRes_fbsj() {
		return res_fbsj;
	}

	public void setRes_fbsj(String res_fbsj) {
		this.res_fbsj = res_fbsj;
	}

	public String getRes_fbyh() {
		return res_fbyh;
	}

	public void setRes_fbyh(String res_fbyh) {
		this.res_fbyh = res_fbyh;
	}

	public List<BaseMap> getI18nMaps() {
		return i18nMaps;
	}

	public void setI18nMaps(List<BaseMap> i18nMaps) {
		this.i18nMaps = i18nMaps;
	}

	public String getRes_bz() {
		return res_bz;
	}

	public void setRes_bz(String res_bz) {
		this.res_bz = res_bz;
	}
}
