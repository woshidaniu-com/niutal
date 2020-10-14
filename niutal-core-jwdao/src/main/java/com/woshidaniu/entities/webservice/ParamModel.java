package com.woshidaniu.entities.webservice;

/**
 *@类名称:ParamModel.java
 *@类描述：WebService专用Model
 *@创建人："huangrz"
 *@创建时间：2014-8-28 上午10:11:42
 *@版本号:v1.0
 */
public class ParamModel {
	private String yhm;     //用户名
	private int num;        //获取记录条数
	private String yhlx;    //tea:老师 stu:学生
	private String yhid;    //用户id
	
	public void setYhm(String yhm) {
		this.yhm = yhm;
	}
	public String getYhm() {
		return yhm;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getNum() {
		return num;
	}
	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
	}
	public String getYhlx() {
		return yhlx;
	}
	public void setYhid(String yhid) {
		this.yhid = yhid;
	}
	public String getYhid() {
		return yhid;
	}
}
