var cjgly = "zf01,admin";// 多个用逗号分割。设置默认查询快照弹层显示，超级管理员可进行共享
var FGF_BS = "$";// 分割符标识,若用"#"，json代码不合法，
var JQUERY_ID_FGF_BS1 = "\\$";// jquery中id需加\\进行转义
var JQUERY_ID_FGF_BS = "\\$";// jquery中id需加\\进行转义
var ZS = "zs";// 总数
var BFB = "bfb";// 百分比

var ZS_CN = "总数";// 总数
var BFB_CN = "比例";// 百分比
var QJK_PRE = "tjcxqjk_";//区间块字段前缀

var AVG = "avg";
var MAX = "max";
var MIN = "min";
var SUM = "sum";
var PTG = "ptg";

var AVG_CN = "平均值";
var MAX_CN = "最大值";
var MIN_CN = "最小值";
var SUM_CN = "总和";
var PTG_CN = "比例";

var WZ_EN = "WZ";// 未知
var WZ_CN = "未知";// 未知
var HXLHJ = "hxlhj";// 横向列合计
var ZXLHJ = "zxlhj";// 纵向列合计
var DTJLH = "dtjlh";// 单条记录行

var HXLHJ_CN = "合计";// 横向列合计
var ZXLHJ_CN = "总数合计";// 纵向列合计

var TBODY = "#tbody";// jquery语法，tbody
var THEAD = "#thead";// jquery语法，thead

/**
 * 保留小数点后位数，若无，则不显示小数
 * 
 * @return
 */
function xsgsh(num, ws) {
	if (num == null) {
		return "";
	}
	try {
		var value = 1;
		for ( var i = 0; i < ws; i++) {
			value *= 10;
		}
		num = Math.round(num * value) / value;
	} catch (e) {

	}
	return num;
}

/**
 * 保留小数点后位数，若无，则用0补全
 * 
 * @return
 */
function xsgsh2(num, ws) {
	if (num == null) {
		return "";
	}
	try {
		ws = ws > 0 && ws <= 20 ? ws : 2;
		num = parseFloat((num + "").replace(/[^\d\.-]/g, "")).toFixed(ws) + "";
		var l = num.split(".")[0].split("").reverse(), r = num.split(".")[1];
		t = "";
		for (i = 0; i < l.length; i++) {
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "" : "");
		}
		num = t.split("").reverse().join("") + "." + r;
	} catch (e) {

	}
	return num;
}

/**
 * 判断是否为纯数字
 * @param value
 * @return
 */
function onlyInt(value) {
	value = jQuery.trim(value);
	var flag = false;
	var reg = /^\d+$/;
	if (reg.test(value) == true) {
		flag = true;
	}
	return flag;
}

/**
 * 只允许输入数字
 * 
 * @param obj
 * @return
 */
function limitSz(obj) {
	jQuery(obj).val(jQuery(obj).val().replace(/[^\d]/g, ''));
}

/**
 * 字符格式限制
 * 
 * @param obj
 * @return
 */
function limitZf(obj) {
	var val = jQuery(obj).val();
	var reg = new RegExp("'|\%|\"", "g");
	val = val.replace(reg, "");//
	jQuery(obj).val(val);
}

/**
 * 将java类型时间格式，转化为oracle类型
 */
function changeSjgs(sjgs) {
	if (sjgs == null || sjgs == "") {
		return "";
	}
	sjgs = sjgs.replace("HH", "HH24");
	sjgs = sjgs.replace("hh", "HH");
	sjgs = sjgs.replace("mm", "MI");
	return sjgs;
}

/**
 * jQuery取id，特殊字符处理
 * @param value
 * @return
 */
function jQueryChangeTszf(value){
	if(value == null || value == ""){
		return value;
	}
	var ret;
	var first = value.substring(0,1);
	if(first == "#"){
		if(value.length > 1){
			var other = value.substring(1);
			ret = "#" + other.replace(/\#/g,"\\#");
		}else{
			ret = "#";
		}
	}else{
		ret = value.replace(/\#/g,"\\#");
	}
	ret = ret.replace(/:/g,"\\:");
	ret = ret.replace(/\./g,"\\.");
    ret = ret.replace(/\//g,"\\/");
 //   ret = ret.replace(/\$/g,"\\$");
    ret = ret.replace(/\[/g,"\\[");
    ret = ret.replace(/\]/g,"\\]");
    ret = ret.replace(/\(/g,"\\(");
    ret = ret.replace(/\)/g,"\\)");
    return ret;
}

/**
 * 区间块模式解析，格式为:字段+最小值+to+最大值，如nl20to30
 * 必须包含最小值
 * 若无最大值，格式为:nl20to
 * 返回json格式，包含zdmc,min,max,success
 * @param value
 * @return
 */
function parseQjk(value) {
	var result = {};
	result.result = false;
	var	reg=/([A-Za-z]+)(\d+)to(\d*)/g; //格式为:字段+最小值+to+最大值，如nl20to30
	if(reg.test(value)){ 		
		result.zdmc = RegExp.$1;
		result.min = RegExp.$2;
		result.max = RegExp.$3;
		result.success = true;
	}
	return result;
} 


/*
 * 区间块模式sql语句生成
 */
function qjkSql(curBbl){
	var result = parseQjk(curBbl);
	var zszdmc = result.zdmc;
	var min = result.min;
	var max = result.max;
	var params = "to_number(" + zszdmc + ")>=";
	params += min;
	if(max != null && max != ""){
		params += " and to_number(" + zszdmc + ")<";
		params += max;	
	}
	return params;
}

