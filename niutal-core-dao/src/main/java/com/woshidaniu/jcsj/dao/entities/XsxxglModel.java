package com.woshidaniu.jcsj.dao.entities;

import java.io.File;

import com.woshidaniu.common.query.ModelBase;

public class XsxxglModel extends ModelBase {

	private static final long serialVersionUID = -2077587228547634079L;
	private String xh; // 学号
	private String xm; // 姓名
	private String xbdm; // 性别
	private String mzdm; // 名族
	private String mzmc; // 名族名称
	private String zzmmdm; // 政治面貌
	private String sfzh; // 身份证号
	private String csrq; // 出生日期
	private String syszd; // 生源所在地
	private String syszdmc;//生源所在地名称
	private String hkszd; // 户口所在地
	private String hkszdmc;//户口所在地名称
	private String jg; // 籍贯
	private String jgmc;//籍贯名称
	private String bjdm_id; // 班级代码
	private String xlcc; // 学历层级
	private String xlmc; // 学历名称
	private String xjzt; // 学籍状态
	private String sfzxs; // 是否在校生
	private String xszt; // 学生状态，1为在校生，0为历史生
	private String sjhm; // 手机号码
	private String gddh; // 固定电话
	private String email; // 电子邮箱
	private String qqhm; // QQ号码
	private String jtdz; // 家庭电话
	private String jtyb; // 家庭邮编
	private String jtdh; // 家庭电话
	private String kzzd1; // 扩展字段1(健康状况代码)
	private String kzzd2; // 扩展字段2(婚姻状况代码)
	private String kzzd3; // 扩展字段3
	private String kzzd4; // 扩展字段4
	private String kzzd5; // 扩展字段5
	private String kzzd6; // 扩展字段6
	private String kzzd7; // 扩展字段7
	private String kzzd8; // 扩展字段8
	private String kzzd9; // 扩展字段9
	private String kzzd10; // 扩展字段10
	private String kzzd11; // 扩展字段11
	private String kzzd12; // 扩展字段12
	private String kzzd13; // 扩展字段13
	private String kzzd14; // 扩展字段14
	private String kzzd15; // 扩展字段15
	private String kzzd16; // 扩展字段16
	private String kzzd17; // 扩展字段17
	private String kzzd18; // 扩展字段18
	private String kzzd19; // 扩展字段19
	private String kzzd20; // 扩展字段20
	private String kzzd21; // 扩展字段21
	private String kzzd22; // 扩展字段22
	private String kzzd23; // 扩展字段23
	private String kzzd24; // 扩展字段24
	private String kzzd25; // 扩展字段25
	private String kzzd26; // 扩展字段26
	private String kzzd27; // 扩展字段27
	private String kzzd28; // 扩展字段28
	private String kzzd29; // 扩展字段29
	private String kzzd30; // 扩展字段30
	private String kzzd31; // 扩展字段31
	private String kzzd32; // 扩展字段32
	private String kzzd33; // 扩展字段33
	private String kzzd34; // 扩展字段34
	private String kzzd35; // 扩展字段35
	private String kzzd36; // 扩展字段36
	private String kzzd37; // 扩展字段37
	private String kzzd38; // 扩展字段38
	private String kzzd39; // 扩展字段39
	private String kzzd40; // 扩展字段40

	private byte[] xszp; // 学生照片
	private String path;//
	private String njdm_id; // 年级代码
	private String njmc; // 年级名称
	private String xbmc; // 性别名称
	private String zzmmmc; // 政治面貌名称
	private String xjztmc; // 学籍状态名称
	
	//学生公用属性
	private String bmdm_id;//部门代码
	private String bmmc;//部门名称
	private String zydm;//专业代码
	private String zymc;//专业名称
	private String bjdm;//班级代码
	private String bjmc;//班级名称
	
	//批量导入
	private String photoNameType;//选择类型
	private File file;
	

	public XsxxglModel() {
		super();
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXbdm() {
		return xbdm;
	}

	public void setXbdm(String xbdm) {
		this.xbdm = xbdm;
	}

	public String getMzdm() {
		return mzdm;
	}

	public void setMzdm(String mzdm) {
		this.mzdm = mzdm;
	}

	public String getZzmmdm() {
		return zzmmdm;
	}

	public void setZzmmdm(String zzmmdm) {
		this.zzmmdm = zzmmdm;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getCsrq() {
		return csrq;
	}

	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}

	public String getSyszd() {
		return syszd;
	}

	public void setSyszd(String syszd) {
		this.syszd = syszd;
	}

	public String getHkszd() {
		return hkszd;
	}

	public void setHkszd(String hkszd) {
		this.hkszd = hkszd;
	}

	public String getJg() {
		return jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

	public String getBjdm_id() {
		return bjdm_id;
	}

	public void setBjdm_id(String bjdmId) {
		bjdm_id = bjdmId;
	}

	public String getXlcc() {
		return xlcc;
	}

	public void setXlcc(String xlcc) {
		this.xlcc = xlcc;
	}

	public String getXjzt() {
		return xjzt;
	}

	public void setXjzt(String xjzt) {
		this.xjzt = xjzt;
	}

	public String getSfzxs() {
		return sfzxs;
	}

	public void setSfzxs(String sfzxs) {
		this.sfzxs = sfzxs;
	}

	public String getXszt() {
		return xszt;
	}

	public void setXszt(String xszt) {
		this.xszt = xszt;
	}

	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	public String getGddh() {
		return gddh;
	}

	public void setGddh(String gddh) {
		this.gddh = gddh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQqhm() {
		return qqhm;
	}

	public void setQqhm(String qqhm) {
		this.qqhm = qqhm;
	}

	public String getJtdz() {
		return jtdz;
	}

	public void setJtdz(String jtdz) {
		this.jtdz = jtdz;
	}

	public String getJtyb() {
		return jtyb;
	}

	public void setJtyb(String jtyb) {
		this.jtyb = jtyb;
	}

	public String getJtdh() {
		return jtdh;
	}

	public void setJtdh(String jtdh) {
		this.jtdh = jtdh;
	}

	public String getKzzd1() {
		return kzzd1;
	}

	public void setKzzd1(String kzzd1) {
		this.kzzd1 = kzzd1;
	}

	public String getKzzd2() {
		return kzzd2;
	}

	public void setKzzd2(String kzzd2) {
		this.kzzd2 = kzzd2;
	}

	public String getKzzd3() {
		return kzzd3;
	}

	public void setKzzd3(String kzzd3) {
		this.kzzd3 = kzzd3;
	}

	public String getKzzd4() {
		return kzzd4;
	}

	public void setKzzd4(String kzzd4) {
		this.kzzd4 = kzzd4;
	}

	public String getKzzd5() {
		return kzzd5;
	}

	public void setKzzd5(String kzzd5) {
		this.kzzd5 = kzzd5;
	}

	public String getKzzd6() {
		return kzzd6;
	}

	public void setKzzd6(String kzzd6) {
		this.kzzd6 = kzzd6;
	}

	public String getKzzd7() {
		return kzzd7;
	}

	public void setKzzd7(String kzzd7) {
		this.kzzd7 = kzzd7;
	}

	public String getKzzd8() {
		return kzzd8;
	}

	public void setKzzd8(String kzzd8) {
		this.kzzd8 = kzzd8;
	}

	public String getKzzd9() {
		return kzzd9;
	}

	public void setKzzd9(String kzzd9) {
		this.kzzd9 = kzzd9;
	}

	public String getKzzd10() {
		return kzzd10;
	}

	public void setKzzd10(String kzzd10) {
		this.kzzd10 = kzzd10;
	}

	public String getKzzd11() {
		return kzzd11;
	}

	public void setKzzd11(String kzzd11) {
		this.kzzd11 = kzzd11;
	}

	public String getKzzd12() {
		return kzzd12;
	}

	public void setKzzd12(String kzzd12) {
		this.kzzd12 = kzzd12;
	}

	public String getKzzd13() {
		return kzzd13;
	}

	public void setKzzd13(String kzzd13) {
		this.kzzd13 = kzzd13;
	}

	public String getKzzd14() {
		return kzzd14;
	}

	public void setKzzd14(String kzzd14) {
		this.kzzd14 = kzzd14;
	}

	public String getKzzd15() {
		return kzzd15;
	}

	public void setKzzd15(String kzzd15) {
		this.kzzd15 = kzzd15;
	}

	public String getKzzd16() {
		return kzzd16;
	}

	public void setKzzd16(String kzzd16) {
		this.kzzd16 = kzzd16;
	}

	public String getKzzd17() {
		return kzzd17;
	}

	public void setKzzd17(String kzzd17) {
		this.kzzd17 = kzzd17;
	}

	public String getKzzd18() {
		return kzzd18;
	}

	public void setKzzd18(String kzzd18) {
		this.kzzd18 = kzzd18;
	}

	public String getKzzd19() {
		return kzzd19;
	}

	public void setKzzd19(String kzzd19) {
		this.kzzd19 = kzzd19;
	}

	public String getKzzd20() {
		return kzzd20;
	}

	public void setKzzd20(String kzzd20) {
		this.kzzd20 = kzzd20;
	}

	public String getKzzd21() {
		return kzzd21;
	}

	public void setKzzd21(String kzzd21) {
		this.kzzd21 = kzzd21;
	}

	public String getKzzd22() {
		return kzzd22;
	}

	public void setKzzd22(String kzzd22) {
		this.kzzd22 = kzzd22;
	}

	public String getKzzd23() {
		return kzzd23;
	}

	public void setKzzd23(String kzzd23) {
		this.kzzd23 = kzzd23;
	}

	public String getKzzd24() {
		return kzzd24;
	}

	public void setKzzd24(String kzzd24) {
		this.kzzd24 = kzzd24;
	}

	public String getKzzd25() {
		return kzzd25;
	}

	public void setKzzd25(String kzzd25) {
		this.kzzd25 = kzzd25;
	}

	public String getKzzd26() {
		return kzzd26;
	}

	public void setKzzd26(String kzzd26) {
		this.kzzd26 = kzzd26;
	}

	public String getKzzd27() {
		return kzzd27;
	}

	public void setKzzd27(String kzzd27) {
		this.kzzd27 = kzzd27;
	}

	public String getKzzd28() {
		return kzzd28;
	}

	public void setKzzd28(String kzzd28) {
		this.kzzd28 = kzzd28;
	}

	public String getKzzd29() {
		return kzzd29;
	}

	public void setKzzd29(String kzzd29) {
		this.kzzd29 = kzzd29;
	}

	public String getKzzd30() {
		return kzzd30;
	}

	public void setKzzd30(String kzzd30) {
		this.kzzd30 = kzzd30;
	}

	public String getKzzd31() {
		return kzzd31;
	}

	public void setKzzd31(String kzzd31) {
		this.kzzd31 = kzzd31;
	}

	public String getKzzd32() {
		return kzzd32;
	}

	public void setKzzd32(String kzzd32) {
		this.kzzd32 = kzzd32;
	}

	public String getKzzd33() {
		return kzzd33;
	}

	public void setKzzd33(String kzzd33) {
		this.kzzd33 = kzzd33;
	}

	public String getKzzd34() {
		return kzzd34;
	}

	public void setKzzd34(String kzzd34) {
		this.kzzd34 = kzzd34;
	}

	public String getKzzd35() {
		return kzzd35;
	}

	public void setKzzd35(String kzzd35) {
		this.kzzd35 = kzzd35;
	}

	public String getKzzd36() {
		return kzzd36;
	}

	public void setKzzd36(String kzzd36) {
		this.kzzd36 = kzzd36;
	}

	public String getKzzd37() {
		return kzzd37;
	}

	public void setKzzd37(String kzzd37) {
		this.kzzd37 = kzzd37;
	}

	public String getKzzd38() {
		return kzzd38;
	}

	public void setKzzd38(String kzzd38) {
		this.kzzd38 = kzzd38;
	}

	public String getKzzd39() {
		return kzzd39;
	}

	public void setKzzd39(String kzzd39) {
		this.kzzd39 = kzzd39;
	}

	public String getKzzd40() {
		return kzzd40;
	}

	public void setKzzd40(String kzzd40) {
		this.kzzd40 = kzzd40;
	}

	public String getNjdm_id() {
		return njdm_id;
	}

	public void setNjdm_id(String njdmId) {
		njdm_id = njdmId;
	}

	public String getNjmc() {
		return njmc;
	}

	public void setNjmc(String njmc) {
		this.njmc = njmc;
	}

	public String getXbmc() {
		return xbmc;
	}

	public void setXbmc(String xbmc) {
		this.xbmc = xbmc;
	}

	public String getZzmmmc() {
		return zzmmmc;
	}

	public void setZzmmmc(String zzmmmc) {
		this.zzmmmc = zzmmmc;
	}

	public String getXjztmc() {
		return xjztmc;
	}

	public void setXjztmc(String xjztmc) {
		this.xjztmc = xjztmc;
	}


	public String getBmdm_id() {
		return bmdm_id;
	}

	public void setBmdm_id(String bmdmId) {
		bmdm_id = bmdmId;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getZydm() {
		return zydm;
	}

	public void setZydm(String zydm) {
		this.zydm = zydm;
	}

	public String getZymc() {
		return zymc;
	}

	public void setZymc(String zymc) {
		this.zymc = zymc;
	}

	public String getBjdm() {
		return bjdm;
	}

	public void setBjdm(String bjdm) {
		this.bjdm = bjdm;
	}

	public String getBjmc() {
		return bjmc;
	}

	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}

	public byte[] getXszp() {
		return xszp;
	}

	public void setXszp(byte[] xszp) {
		this.xszp = xszp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMzmc() {
		return mzmc;
	}

	public void setMzmc(String mzmc) {
		this.mzmc = mzmc;
	}

	public String getXlmc() {
		return xlmc;
	}

	public void setXlmc(String xlmc) {
		this.xlmc = xlmc;
	}

	public String getPhotoNameType() {
		return photoNameType;
	}

	public void setPhotoNameType(String photoNameType) {
		this.photoNameType = photoNameType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getSyszdmc() {
		return syszdmc;
	}

	public void setSyszdmc(String syszdmc) {
		this.syszdmc = syszdmc;
	}

	public String getHkszdmc() {
		return hkszdmc;
	}

	public void setHkszdmc(String hkszdmc) {
		this.hkszdmc = hkszdmc;
	}

	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}

}
