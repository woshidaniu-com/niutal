package com.woshidaniu.dao.entities;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：数据资源Model
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月22日上午8:56:59
 */
@Alias(value="sjzyModel")
public class SjzyModel implements Serializable{

	private static final long serialVersionUID = 2945496300399140062L;

	private String zyid;
	private String zymc;
	private List<SjzygzModel> zygzList;
	
	public String getZyid() {
		return zyid;
	}
	public void setZyid(String zyid) {
		this.zyid = zyid;
	}
	public String getZymc() {
		return zymc;
	}
	public void setZymc(String zymc) {
		this.zymc = zymc;
	}
	public List<SjzygzModel> getZygzList() {
		return zygzList;
	}
	public void setZygzList(List<SjzygzModel> zygzList) {
		this.zygzList = zygzList;
	}
	
	
}
