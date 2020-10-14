/*
 * 生成hidden隐藏域html
 */
function createHiddenHtml(id, szz) {
	var sHtml = "";
	sHtml += "<input type='hidden' name='" + id + "' id='" + id + "'";
	if (szz != null) {
		sHtml += " value='" + szz + "' ";
	}
	sHtml += ">";
	return sHtml;
}

/*
 * 生成hidden隐藏域html,obj
 */
function createHiddenHtmlByObj(obj) {
	var zd = obj.zd;
	var szz = obj.szz;
	var sHtml = "";
	sHtml += "<input type='hidden' name='" + zd + "' ";
	var includeId = obj['includeId'];
	if (includeId != null && includeId != false) {
		sHtml += " id='" + zd + "'";
	}
	if (szz != null) {
		sHtml += " value='" + szz + "' ";
	}
	sHtml += ">";
	return sHtml;
}


/*
 * 生成hidden隐藏域html，不包含id
 */
function createHiddenNoIdHtml(id, szz) {
	var sHtml = "";
	sHtml += "<input type='hidden' name='" + id + "'";
	if (szz != null) {
		sHtml += " value='" + szz + "' ";
	}
	sHtml += ">&nbsp;";
	return sHtml;
}

/*
 * 生成text文本框html
 */
function createTextHtml(id, szz, bdkd) {
	var sHtml = "";
	sHtml += "<input type='text' name='" + id + "'  value='' id='" + id
			+ "' class='text_nor'";
	if (bdkd != null && bdkd != "") {
		sHtml += " style='width: " + bdkd + "px'";
	}
	sHtml += ">";
	return sHtml;
}

/*
 * 生成text文本框html
 */
function createTextHtmlByObj(obj) {
	var zd = obj.zd;
	var bdkd = obj.bdkd;
	var szz = obj.szz;
	var sHtml = "";
	sHtml += "<input type='text' name='" + zd + "' class='text_nor'";
	if (bdkd != null && bdkd != "") {
		sHtml += " style='width: " + bdkd + "px'";
	}
	var includeId = obj['includeId'];
	if (includeId != null && includeId != false) {
		sHtml += " id='" + zd + "'";
	}
	var max = getMaxLength(szz);
	if (max != "") {
		sHtml += " maxlength='" + max + "'";
	}
	sHtml += ">";
	return sHtml;
}

function getMaxLength(szz) {
	var max = "";
	if (szz != null && szz != "") {// 最大长度限制
		var szzs = szz.split(",");
		if (szzs.length > 1) {
			max = szzs[1];
		}
	}
	return max;
}

/*
 * 生成text数字文本框html
 */
function createSzTextHtml(id, szz, bdkd) {
	var sHtml = "";
	sHtml += "<input type='text' name='" + id + "'  value='' id='" + id
			+ "' class='text_nor'";
	if (bdkd != null && bdkd != "") {
		sHtml += " style='width: " + bdkd + "px'";
	}
	sHtml += ">";
	return sHtml;
}


/*
 * 生成text数字文本框html ,obj
 */
function createSzTextHtmlByObj(obj) {
	var zd = obj.zd;
	var bdkd = obj.bdkd;
	var sHtml = "";
	sHtml += "<input type='text' ";
	var includeId = obj['includeId'];
	if (includeId != null && includeId != false) {
		sHtml += " id='" + zd + "'";
	}
	sHtml += " name='" + zd + "' class='text_nor'";
	if (bdkd != null && bdkd != "") {
		sHtml += " style='width: " + bdkd + "px'";
	}
	sHtml += ">";
	return sHtml;
}

/*
 * 生成textarea文本域html
 */
function createTextareaHtml(id, szz, bdkd) {
	var sHtml = "";
	if(bdkd == null || bdkd == ""){
		bdkd = 4;
	}
	sHtml += "<textarea  id='"
			+ id
			+ "' name='"
			+ id
			+ "'  rows='"+bdkd+"'   style='word-break:break-all;width:97%'></textarea>";
	return sHtml;
}

/*
 * 生成textarea文本域html,obj
 */
function createTextareaHtmlByObj(obj) {
	var zd = obj.zd;
	var szz = obj.szz;
	var szlx = obj.szlx;
	var bdkd = obj.bdkd;
	var sHtml = "";
	if(bdkd == null || bdkd == ""){
		bdkd = 4;
	}
	sHtml += "<textarea ";
	var includeId = obj['includeId'];
	if (includeId != null && includeId != false) {
		sHtml += " id='" + zd + "'";
	}
	sHtml += " name='"+ zd+ "'  rows='"+bdkd+"'  style='word-break:break-all;width:97%'></textarea>";
	return sHtml;
}

/*
 * 生成text日期文本框html
 */
function createDateTextHtml(id, szz, bdkd) {
	var sHtml = "";
	if (szz == null || szz == "") {
		szz = "yyyyMMdd";
	}
	sHtml += "<input type='text' id='" + id + "' name='" + id
			+ "'  onfocus='WdatePicker({dateFmt:\"" + szz
			+ "\"})' class='text_nor'";
	if (bdkd != null && bdkd != "") {
		sHtml += " style='width: " + bdkd + "px'";
	}
	sHtml += ">";
	return sHtml;
}


/*
 * 生成text日期文本框html
 */
function createDateTextHtmlByObj(obj) {
	var zd = obj.zd;
	var szz = obj.szz;
	var szlx = obj.szlx;
	var bdkd = obj.bdkd;
	var sHtml = "";
	if (szz == null || szz == "") {
		szz = "yyyyMMdd";
	}
	sHtml += "<input type='text' ";
	var includeId = obj['includeId'];
	if (includeId != null && includeId != false) {
		sHtml += " id='" + zd + "'";
	}
	sHtml += " name='" + zd
			+ "'  onfocus='WdatePicker({dateFmt:\"" + szz
			+ "\"})' class='text_nor'";
	if (bdkd != null && bdkd != "") {
		sHtml += " style='width: " + bdkd + "px'";
	}
	sHtml += ">";
	return sHtml;
}


/*
 * 生成select下拉框html
 */
function createSelectHtml(id, szz, szlx, bdkd) {
	var sHtml = "";
	if (bdkd == null || bdkd == "") {
		bdkd = 150;
	}
	sHtml += "<select  id='" + id + "' name='" + id + "' style='width:" + bdkd
			+ "px'>";
	if (szlx == "10" || szlx == "20" || szlx == "30") {
		sHtml += getSelectDefaultOpt();
	}
	if (szz != null) {
		var szzList = null;
		try{
			szzList = eval(szz);
		}catch(e){}
		if (szzList != null) {
			for ( var j = 0; j < szzList.length; j++) {
				var szzObj = szzList[j];
				var dm = szzObj.dm;
				var mc = szzObj.mc;
				sHtml += "<option value='" + dm + "'>" + mc + "</option>";
			}
		}
	}
	sHtml += "</select>";
	return sHtml;
}

function createSelectHtmlByObj(obj) {
	var zd = obj.zd;
	var szz = obj.szz;
	var szlx = obj.szlx;
	var bdkd = obj.bdkd;
	var sHtml = "";
	if (bdkd == null || bdkd == "") {
		bdkd = 150;
	}
	sHtml += "<select  name='" + zd + "' style='width:" + bdkd + "px'";
	var includeId = obj['includeId'];
	if (includeId != null && includeId != false) {
		sHtml += " id='" + zd + "'";
	}
	sHtml += ">";
	if (szlx == "10" || szlx == "20" || szlx == "30") {
		sHtml += getSelectDefaultOpt();
	}
	if (szz != null) {
		var szzList = null;
		try{
			szzList = eval(szz);
		}catch(e){}
		if (szzList != null) {
			for ( var j = 0; j < szzList.length; j++) {
				var szzObj = szzList[j];
				var dm = szzObj.dm;
				var mc = szzObj.mc;
				sHtml += "<option value='" + dm + "'>" + mc + "</option>";
			}
		}
	}
	sHtml += "</select>";
	return sHtml;
}

/*
 * 生成radio单选框html
 */
function createRadioHtml(id, szz) {
	var sHtml = "";
	if (szz != null) {
		var szzList = null;
		try{
			szzList = eval(szz);
		}catch(e){}
		if (szzList != null) {
			for ( var j = 0; j < szzList.length; j++) {
				var szzObj = szzList[j];
				var dm = szzObj.dm;
				var mc = szzObj.mc;				
				sHtml += "<span style='display:inline-block'>";
				sHtml += "<label>";
				sHtml += "<input type='radio' name='" + id + "' value='" + dm
						+ "'>";
				sHtml += mc;
				sHtml += "</label>";
				sHtml += "</span>";
			}
		}
	}
	sHtml += "</select>";
	return sHtml;
}

/*
 * 生成checkbox复选框html
 */
function createCheckboxHtml(id, szz) {
	var sHtml = "";
	if (szz != null) {
		var szzList = null;
		try{
			szzList = eval(szz);
		}catch(e){}
		if (szzList != null) {
			for ( var j = 0; j < szzList.length; j++) {
				var szzObj = szzList[j];
				var dm = szzObj.dm;
				var mc = szzObj.mc;
				sHtml += "<span style='display:inline-block'><label>";
				sHtml += "<input type='checkbox'  id='" + id + "' name='" + id
						+ "' value='" + dm + "'>";
				sHtml += mc;
				sHtml += "</label></span>";
			}
		}
	}
	return sHtml;
}


/*
 * 生成checkbox复选框html,obj
 */
function createCheckboxHtmlByObj(obj) {
	var zd = obj.zd;
	var szz = obj.szz;
	var sHtml = "";
	if (szz != null) {
		var szzList = null;
		try{
			szzList = eval(szz);
		}catch(e){}
		if (szzList != null) {
			for ( var j = 0; j < szzList.length; j++) {
				var szzObj = szzList[j];
				var dm = szzObj.dm;
				var mc = szzObj.mc;
				sHtml += "<span style='display:inline-block'><label>";
				sHtml += "<input type='checkbox' name='" + zd
						+ "' value='" + dm + "'>";
				sHtml += mc;
				sHtml += "</label></span>";
			}
		}
	}
	return sHtml;
}


/*
 * 生成22:省市县选择 html
 */
function createSsxHtml(id, szz) {
	var sHtml = "";
//	sHtml += "<select id='" + id + "_province' name='" + id
//			+ "_province' style='width:120px'>";
//	sHtml += "</select>";
//	sHtml += "&nbsp;&nbsp;&nbsp;&nbsp;";
//
//	sHtml += "<select id='" + id + "_city' name='" + id
//			+ "_city' style='width:120px'>";
//	sHtml += "</select>";
//	sHtml += "&nbsp;&nbsp;&nbsp;&nbsp;";
//
//	sHtml += "<select id='" + id + "_locale' name='" + id
//			+ "_locale' style='width:120px'>";
//	sHtml += "</select>";
//	sHtml += "&nbsp;&nbsp;&nbsp;&nbsp;";
	sHtml += "<input type='text' name='_" + id + "'  id='_" + id + "' />";
	sHtml += "<input type='hidden' name='" + id + "'  id='" + id + "' />";
	return sHtml;
}

/*
 * 得到select默认option,
 */
function getSelectDefaultOpt() {
	return "<option value=''>--请选择--</option>";
}

/*
 * 设置省市县联动
 */
function setSsxLiandong(id, szz) {
	jQuery("#" + id + "_province").append(getSelectDefaultOpt());
	jQuery("#" + id + "_city").append(getSelectDefaultOpt());
	jQuery("#" + id + "_locale").append(getSelectDefaultOpt());

	var sHtml = "";
	if (szz == null) {
		return;
	}
	for ( var i = 0; i < szz.length; i++) {
		var shObj = szz[i];
		var shdm = shObj.value;
		var shmc = shObj.treeNode;
		var shChild = shObj.childNode;
		sHtml += "<option value='" + shdm + "'>" + shmc + "</option>";

		if (shChild == null) {
			continue;
		}

		for ( var j = 0; j < shChild.length; j++) {
			var shiObj = shChild[j];
			var shidm = shiObj.value;
			var shimc = shiObj.treeNode;
			var shiChild = shiObj.childNode;
		}
	}

	jQuery("#" + id + "_province").append(sHtml);

	jQuery("#" + id + "_province").change(
			function() {
				var sShiHtml = "";
				var curSh = jQuery(this).val();
				for ( var i = 0; i < szz.length; i++) {
					var shObj = szz[i];
					var shdm = shObj.value;
					var shmc = shObj.treeNode;
					var shChild = shObj.childNode;
					if (curSh == shdm) {
						if (shChild == null) {
							break;
						}
						for ( var j = 0; j < shChild.length; j++) {
							var shiObj = shChild[j];
							var shidm = shiObj.value;
							var shimc = shiObj.treeNode;
							var shiChild = shiObj.childNode;
							sShiHtml += "<option value='" + shidm + "'>"
									+ shimc + "</option>";
						}
					}
				}
				jQuery("#" + id + "_locale").empty().append(
						getSelectDefaultOpt());

				jQuery("#" + id + "_city").empty()
						.append(getSelectDefaultOpt()).append(sShiHtml);
			});

	jQuery("#" + id + "_city").change(function() {
		var sXiHtml = "";
		var curSh = jQuery("#" + id + "_province").val();// 当前省
			var curShi = jQuery(this).val();// 当前市
			for ( var i = 0; i < szz.length; i++) {
				var shObj = szz[i];
				var shdm = shObj.value;
				var shmc = shObj.treeNode;
				var shChild = shObj.childNode;
				if (curSh != shdm) {
					continue;
				}
				if (shChild == null) {
					break;
				}
				for ( var j = 0; j < shChild.length; j++) {
					var shiObj = shChild[j];
					var shidm = shiObj.value;
					var shimc = shiObj.treeNode;
					var shiChild = shiObj.childNode;

					if (shidm != curShi) {
						continue;
					}
					if (shiChild == null) {
						break;
					}

					for ( var k = 0; k < shiChild.length; k++) {
						var xiObj = shiChild[k];
						var xidm = xiObj.value;
						var ximc = xiObj.treeNode;

						sXiHtml += "<option value='" + xidm + "'>" + ximc
								+ "</option>";
					}

				}
			}

			jQuery("#" + id + "_locale").empty().append(getSelectDefaultOpt())
					.append(sXiHtml);
		});

	var zddm = "";
	jQuery("#" + id + "_province").change(function() {// 将省值付给隐藏域
				jQuery("#" + id).val(jQuery(this).val());
			});

	jQuery("#" + id + "_city").change(function() {// 将市值付给隐藏域
				zddm = jQuery(this).val();
				if(zddm == ""){
					zddm = jQuery("#" + id + "_province").val();
				}
				jQuery("#" + id).val(zddm);
			});
	jQuery("#" + id + "_locale").change(function() {// 将县值付给隐藏域
				zddm = jQuery(this).val();
				if(zddm == ""){
					zddm = jQuery("#" + id + "_city").val();
				}
				jQuery("#" + id).val(zddm);
			});
}


/**
 * 通过代码，获取省市县
 * 
 * @param xi
 * @return
 */
function getSsx(szz, dm) {
	var result = {};
	var sHtml = "";
	if (szz == null || dm == null || dm == "") {
		return result;
	}
	if(dm.substring(2,6) == "0000"){//省
		result = getSsxBySh(szz, dm);
	}else if(dm.substring(4,6) == "00"){//市
		result = getSsxByShi(szz, dm);
	}else{//县
		result = getSsxByXi(szz, dm);
	}
	return result;
}

/**
 * 通过省代码，获取省市县
 * 
 * @param xi
 * @return
 */
function getSsxBySh(szz, sh) {
	var result = {};
	var sHtml = "";
	for ( var i = 0; i < szz.length; i++) {
		var shObj = szz[i];
		var shdm = shObj.value;
		var shmc = shObj.treeNode;
		var shChild = shObj.childNode;
		if (shChild == null) {
			continue;
		}
		if (shdm == sh) {
			result.shdm = shdm;
			result.shidm = "";
			result.xidm = "";
			result.shmc = shmc;
			result.shimc = "";
			result.ximc = "";
			break;
		}
	}
	return result;
}


/**
 * 通过市代码，获取省市县
 * 
 * @param xi
 * @return
 */
function getSsxByShi(szz, shi) {
	var result = {};
	var sHtml = "";
	for ( var i = 0; i < szz.length; i++) {
		var shObj = szz[i];
		var shdm = shObj.value;
		var shmc = shObj.treeNode;
		var shChild = shObj.childNode;
		if (shChild == null) {
			continue;
		}
		for ( var j = 0; j < shChild.length; j++) {
			var shiObj = shChild[j];
			var shidm = shiObj.value;
			var shimc = shiObj.treeNode;
			var shiChild = shiObj.childNode;
			if (shidm == shi) {
				result.shdm = shdm;
				result.shidm = shidm;
				result.xidm = "";
				result.shmc = shmc;
				result.shimc = shimc;
				result.ximc = "";
				break;
			}
		}
	}
	return result;
}


/**
 * 通过县代码，获取省市县
 * 
 * @param xi
 * @return
 */
function getSsxByXi(szz, xi) {
	var result = {};
	var sHtml = "";
	for ( var i = 0; i < szz.length; i++) {
		var shObj = szz[i];
		var shdm = shObj.value;
		var shmc = shObj.treeNode;
		var shChild = shObj.childNode;
		if (shChild == null) {
			continue;
		}
		for ( var j = 0; j < shChild.length; j++) {
			var shiObj = shChild[j];
			var shidm = shiObj.value;
			var shimc = shiObj.treeNode;
			var shiChild = shiObj.childNode;
			if (shiChild == null) {
				continue;
			}

			for ( var k = 0; k < shiChild.length; k++) {
				var xiObj = shiChild[k];
				var xidm = xiObj.value;
				var ximc = xiObj.treeNode;

				if (xidm == xi) {
					result.shdm = shdm;
					result.shidm = shidm;
					result.xidm = xidm;
					result.shmc = shmc;
					result.shimc = shimc;
					result.ximc = ximc;
					break;
				}
			}
		}
	}
	return result;
}

/*
 * 自定义表单验证-非空
 */
function checkZdybdYxwk(o, value) {
	var result = {};
	result.success = true;
	result.yzlx = "fk";
	var zd = o.zd;
	var bdmc = o.bdmc;
	var szlx = o.szlx;
	var szz = o.szz;
	var zdlx = o.zdlx;
	var yxwk = o.yxwk;
	var flszid = o.flszid;
	result.zd = zd;
	result.bdmc = bdmc;
	result.flszid = flszid; 
	if (yxwk != "0") {
		return result;
	}
	if (zdlx == "3" || zdlx == "4") {// 单选框，复选框
		var length = jQuery("#" + PRECON_TABLE+ flszid + " [name='" + zd + "']:checked").length;
		if (length == 0) {
			result.success = false;
			result.message = "[" + bdmc + "]不允许为空！";
		}
	} else if (zdlx == "2" || zdlx == "5" || zdlx == "6" || zdlx == "11"
			|| zdlx == "13" || zdlx == "22" || zdlx == "24") {
		if (value == null) {
//			var value = jQuery.trim(jQuery("#" + zd).val());
			var value = jQuery.trim(jQuery("#"+PRECON_TABLE+ flszid+" [name='" + zd + "']").val());
		}
		if (value == "") {
			result.success = false;
			result.message = "[" + bdmc + "]不允许为空！";
		}
	}
	return result;
}

/*
 * 自定义表单验证
 */
function checkZdybd(o, value) {
	var result = {};
	result.success = true;
	result.yzlx = "gs";
	var zd = o.zd;
	var bdmc = o.bdmc;
	var zdlx = o.zdlx;
	var szlx = o.szlx;
	var szz = o.szz;
	var yxwk = o.yxwk;
	var flszid = o.flszid;
	result.zd = zd;
	result.bdmc = bdmc;
	result.flszid= flszid;
	/*
	 * 11:数字、字母、下划线 12:仅字母 13:仅数字 14:小数 21:邮箱 22:电话号码 23:手机号码 24:身份证号 25:邮编
	 * 98:正则表达式 99:自定义方法，当前字段值做为参数
	 */
	if(value == null){
		value = jQuery.trim(jQuery("#"+PRECON_TABLE+ flszid+" [name='" + zd + "']").val());
		if(value == null ){
			value = "";
		}
	}
	
	if (szlx == "11") {
		result = checkZdybdSzx(zd, bdmc, value);
	} else if (szlx == "12") {
		result = checkZdybdZm(zd, bdmc, value);
	} else if (szlx == "13") {
		result = checkZdybdSz(zd, bdmc, value);
	} else if (szlx == "14") {

	} else if (szlx == "21") {//电子邮箱
		result = checkZdybdEmail(zd, bdmc, value);
	} else if (szlx == "22") {//电话号码
		result = checkZdybdDhhm(zd, bdmc, value);
	} else if (szlx == "23") {//手机号码
		result = checkZdybdDhhm(zd, bdmc, value);
	} else if (szlx == "24") {//身份证号码
		result = checkZdybdSfzh(zd, bdmc, value);
	} else if (szlx == "25") {//邮编
		result = checkZdybdYb(zd, bdmc, value);
	} else if (szlx == "99") {
		result = checkZdybdZzbds(zd, bdmc, szz, value);
	}
	return result;
}

/*
 * 自定义表单验证-数字字母下划线
 */
function checkZdybdSzx(zd, bdmc, value) {
	var result = {};
	result.success = true;
	var reg = "";
	var message = "[" + bdmc + "]格式不符";
	if (value == null) {
		var value = jQuery.trim(jQuery("#" + zd).val());
	}
	reg = /^(\w)*$/;
	message += "，只允许输入数字、字母、下划线！";
	if (reg != "" && !reg.exec(value)) {
		result.success = false;
		result.message = message;
	}
	return result;
}

/*
 * 自定义表单验证-仅字母
 */
function checkZdybdZm(zd, bdmc, value) {
	var result = {};
	result.success = true;
	var reg = "";
	var message = "[" + bdmc + "]格式不符";
	if (value == null) {
		var value = jQuery.trim(jQuery("#" + zd).val());
	}
	reg = /^[A-Za-z]*$/;
	message += "，只允许输入字母！";
	if (reg != "" && !reg.exec(value)) {
		result.success = false;
		result.message = message;
	}
	return result;
}

/*
 * 自定义表单验证-仅数字
 */
function checkZdybdSz(zd, bdmc, value) {
	var result = {};
	result.success = true;
	var reg = "";
	var message = "[" + bdmc + "]格式不符";
	if (value == null) {
		var value = jQuery.trim(jQuery("#" + zd).val());
	}
	reg = /^[0-9]*$/;
	message += "，只允许输入数字！";
	if (reg != "" && !reg.exec(value)) {
		result.success = false;
		result.message = message;
	}
	return result;
}

/*
 * 自定义表单验证-电话号码
 */
function checkZdybdDhhm(zd, bdmc, value) {
	var result = {};
	result.success = true;
	var message = "[" + bdmc + "]格式不符";
	if (value == null) {
		var value = jQuery.trim(jQuery("#" + zd).val());
	}
	message += "！";
	if (!isTel(value)) {
		result.success = false;
		result.message = message;
	}
	return result;
}


/*
 * 自定义表单验证-电子邮箱
 */
function checkZdybdEmail(zd, bdmc, value) {
	var result = {};
	result.success = true;
	var reg = "";
	var message = "[" + bdmc + "]格式不符";
	if (value == null) {
		var value = jQuery.trim(jQuery("#" + zd).val());
	}	
	message += "！";
	if (!isEmail(value)) {
		result.success = false;
		result.message = message;
	}
	return result;
}

/*
 * 自定义表单验证-邮编
 */
function checkZdybdYb(zd, bdmc, value) {
	var result = {};
	result.success = true;
	var message = "[" + bdmc + "]格式不符";
	if (value == null) {
		var value = jQuery.trim(jQuery("#" + zd).val());
	}
	message += "！";
	if (!isPostalCode(value)) {
		result.success = false;
		result.message = message;
	}
	return result;
}

/*
 * 自定义表单验证-身份证号
 */
function checkZdybdSfzh(zd, bdmc, value) {
	var result = {};
	result.success = true;
	var message = "[" + bdmc + "]格式不符";
	if (value == null) {
		var value = jQuery.trim(jQuery("#" + zd).val());
	}	
	var reg = /^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
	message += "！";
	if (reg != "" && !reg.exec(value)) {
		result.success = false;
		result.message = message;
	}
	return result;
}


/*
 * 自定义表单验证-正则表达式
 */
function checkZdybdZzbds(zd, bdmc, szz, value) {
	var result = {};
	result.success = true;
	var reg = "";
	var message = "[" + bdmc + "]格式不符";
	if (value == null) {
		var value = jQuery.trim(jQuery("#" + zd).val());
	}

	try{
		reg = eval(szz);
	}catch(e){}
	
	message += "！";
	if (reg != "" && !reg.exec(value)) {
		result.success = false;
		result.message = message;
	}
	return result;
}

/**
 * 自定义表单验证-数字字母下划线-focus当前对象
 */
function checkSzx(obj, bdmc) {
	var zd = jQuery(obj).attr("id");
	var result = checkZdybdSzx(zd, bdmc);
	if (result != null && result.success == false) {
		showAlertDivLayer(result.message, {}, {
			"clkFun" : function() {
				jQuery("#" + zd).focus();
			}
		});
	}
}

/**
 * 自定义表单验证-仅字母-focus当前对象
 */
function checkZm(obj, bdmc) {
	var result = {};
	result.success = true;
	var reg = "";
	var message = "[" + bdmc + "]格式不符";
	var value = jQuery.trim(jQuery("#" + zd).val());
	reg = /^[A-Za-z]*$/;
	message += "，只允许输入字母！";
	if (reg != "" && !reg.exec(value)) {
		result.success = false;
		result.message = message;
	}
	return result;
}

/**
 * 自定义表单验证-仅数字-focus当前对象
 */
function checkSz(obj, bdmc) {
	var zd = jQuery(obj).attr("id");
	var result = checkZdybdSz(zd, bdmc);
	if (result != null && result.success == false) {
		showAlertDivLayer(result.message, {}, {
			"clkFun" : function() {
				jQuery("#" + zd).focus();
			}
		});
	}
}

/**
 * 自定义表单验证-电话号码-focus当前对象
 */
function checkDhhm(obj, bdmc) {
	var zd = jQuery(obj).attr("id");
	var result = checkZdybdDhhm(zd, bdmc);
	if (result != null && result.success == false) {
		showAlertDivLayer(result.message, {}, {
			"clkFun" : function() {
				jQuery("#" + zd).focus();
			}
		});
	}
}

/**
 * 自定义表单验证-身份证号-focus当前对象
 */
function checkSfzh(obj, bdmc) {
	var zd = jQuery(obj).attr("id");
	var result = checkZdybdSfzh(zd, bdmc);
	if (result != null && result.success == false) {
		showAlertDivLayer(result.message, {}, {
			"clkFun" : function() {
				jQuery("#" + zd).focus();
			}
		});
	}
}

/**
 * 自定义表单验证-正则表达式-focus当前对象
 */
function checkZzbds(obj, bdmc, szz) {
	var zd = jQuery(obj).attr("id");
	var result = checkZdybdZzbds(zd, bdmc, szz);
	if (result != null && result.success == false) {
		showAlertDivLayer(result.message, {}, {
			"clkFun" : function() {
				jQuery("#" + zd).focus();
			}
		});
	}
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

/*
 * 长度验证
 */
function checkZdybdLength(o) {
	var result = {};
	result.success = true;
	result.yzlx = "cd";
	var zd = o.zd;
	var bdmc = o.bdmc;
	var szlx = o.szlx;
	var szz = o.szz;
	var zdlx = o.zdlx;
	var flszid = o.flszid;
	var value = jQuery.trim(jQuery("#"+PRECON_TABLE+ flszid+" [name='" + zd + "']").val());
	result.zd = zd;
	result.bdmc = bdmc;
	result.flszid = flszid;
	if (szz != null) {
		var szzs = szz.split(",");
		var min = 0;
		var max = 0;
		var length = value.length;
		if (szzs.length > 0) {
			min = szzs[0];
		}
		if (szzs.length > 1) {
			max = szzs[1];
		}

		if (min != 0 && max == 0 && length < min) {
			result.success = false;
			result.message = "[" + bdmc + "]格式不符，至少为" + min + "字符！";
		} else if (min == 0 && max != 0 && length > max) {
			result.success = false;
			result.message = "[" + bdmc + "]格式不符，至多为" + max + "字符！";
		} else if (min != 0 && max != 0 && (length < min || length > max)) {
			result.success = false;
			result.message = "[" + bdmc + "]格式不符，为" + min + "-" + max + "字符！";
		}
	}
	return result;
}

/**
 * 通过代码获取名称
 * 
 * @param dm
 * @param szz
 * @return
 */
function getNameByDm(dm, szz) {
	var mc = getNameByDmIncludeNull(dm, szz);
	if(mc == ""){
		mc = dm;
	}
	return mc;
}


/**
 * 通过代码获取名称，当获取不到值时，返回空字符串
 * 
 * @param dm
 * @param szz
 * @return
 */
function getNameByDmIncludeNull(dm, szz) {
	var mc = "";
	if (dm == null || szz == null || szz == "") {
		return "";
	}

	var szzList = null;
	try{
		szzList = eval(szz);
	}catch(e){}
	if (szzList != null) {
		for ( var j = 0; j < szzList.length; j++) {
			var szzObj = szzList[j];
			var dmTemp = szzObj.dm;
			var mcTemp = szzObj.mc;
			if (dmTemp == dm) {
				mc = mcTemp;
			}
		}
	}
	return mc;
}


/*
 * js特效
 */
function navAA(zdybdDwpyl) {
	jQuery(".college_title").toggle(function() {
		// jQuery(this).siblings().hide();
			// jQuery(this).children(".up").attr("class", "down").text("展开");
			return false;
		}, function() {
			// jQuery(this).siblings().show();
			// jQuery(this).children(".down").attr("class", "up").text("收起");
		})
	jQuery(".college_title a:eq()").toggle(function() {
		jQuery(this).siblings().hide();
		// jQuery(".btn").attr("class", "down");
			// jQuery(".btn").text("展开");
			return false;
		}, function() {
			jQuery(".demo_college .con").show();
			// jQuery(".btn").attr("class", "up");
			// jQuery(".btn").text("收起");
		})
	jQuery(".demo_college li .college_li").hover(function() {
		jQuery(this).next().show();
		jQuery(this).parent().css("position", "relative");
	}, function() {
		jQuery(this).next().hide();
		jQuery(this).parent().css("position", "");
	})
	jQuery(".list_xxxx li").hover(function() {
		jQuery(this).children(".list_xxxx_downmenu").show();
		jQuery(this).css("position", "relative");
	}, function() {
		jQuery(this).children(".list_xxxx_downmenu").hide();
		jQuery(this).css("position", "");
	})
	jQuery(".list_xxxx_downmenu").hover(function() {
		jQuery(this).show();
		jQuery(this).prev().attr("class", "hover");
	}, function() {
		jQuery(this).hide();
		jQuery(this).prev().removeClass("hover");
	});

	jQuery('#position-fixed').find(".list_xxxx li").hover(function() {

		jQuery(this).children(".list_xxxx_downmenu").show();
		jQuery(this).css("position", "relative")
	}, function() {
		jQuery(this).children(".list_xxxx_downmenu").hide();
		jQuery(this).css("position", "")
	});
	jQuery('#position-fixed').find(".list_xxxx_downmenu").hover(function() {
		jQuery(this).show();
		jQuery(this).prev().attr("class", "hover")
	}, function() {
		jQuery(this).hide();
		jQuery(this).prev().removeClass("hover")
	});
	jQuery(".list_xxxx_downmenu dd").live('click', function() {
		jQuery(this).parent().parent().hide();
	});

	var sfxsgban = jQuery.trim(jQuery("#sfxsgban").val());
	if (sfxsgban != null && sfxsgban != "") {
		// 各种IE页面当中的定位问题
		jQuery("a").each(function() {
			var link = jQuery(this);
			var href = link.attr("href");
			if (href && href[0] == "#") {
				var name = href.substring(1);
				jQuery(this).click(function() {
					var nameElement = jQuery("[name='" + name + "']");
					var idElement = jQuery("#" + name);
					var element = null;
					if (nameElement.length > 0) {
						element = nameElement;
					} else if (idElement.length > 0) {
						element = idElement;
					}

					if (element) {
						var offset = element.offset();
						window.parent.scrollTo(offset.left, offset.top + 125);
					}
					return false;
				});
			}
		});
	}
	
	if(zdybdDwpyl == null || zdybdDwpyl == ""){
		zdybdDwpyl = 110;
	}

	jQuery(".smooth").click(function() {
		var href = jQuery(this).attr("href");
		var _href = jQuery("[name='" + href.substr(1) + "']");
		if (_href.length > 0) {
			var pos = jQuery("[name='" + href.substr(1) + "']").position().top;
			jQuery("html,body").animate( {
				scrollTop : pos - zdybdDwpyl
			}, 400);
		}
		return false;
	});
}

/**
 * json处理
 */
var JSON = function() {
	var m = {
		'\b' : '\\b',
		'\t' : '\\t',
		'\n' : '\\n',
		'\f' : '\\f',
		'\r' : '\\r',
		'"' : '\\"',
		'\\' : '\\\\'
	}, s = {
		'boolean' : function(x) {
			return String(x);
		},
		number : function(x) {
			return isFinite(x) ? String(x) : 'null';
		},
		string : function(x) {
			if (/["\\\x00-\x1f]/.test(x)) {
				x = x.replace(/([\x00-\x1f\\"])/g, function(a, b) {
					var c = m[b];
					if (c) {
						return c;
					}
					c = b.charCodeAt();
					return '\\u00' + Math.floor(c / 16).toString(16)
							+ (c % 16).toString(16);
				});
			}
			return '"' + x + '"';
		},
		object : function(x) {
			if (x) {
				var a = [], b, f, i, l, v;
				if (x instanceof Array) {
					a[0] = '[';
					l = x.length;
					for (i = 0; i < l; i += 1) {
						v = x[i];
						f = s[typeof v];
						if (f) {
							v = f(v);
							if (typeof v == 'string') {
								if (b) {
									a[a.length] = ',';
								}
								a[a.length] = v;
								b = true;
							}
						}
					}
					a[a.length] = ']';
				} else if (x instanceof Object) {
					a[0] = '{';
					for (i in x) {
						v = x[i];
						f = s[typeof v];
						if (f) {
							v = f(v);
							if (typeof v == 'string') {
								if (b) {
									a[a.length] = ',';
								}
								a.push(s.string(i), ':', v);
								b = true;
							}
						}
					}
					a[a.length] = '}';
				} else {
					return;
				}
				return a.join('');
			}
			return 'null';
		}
	};
	return {
		copyright : '',
		license : '',
		stringify : function(v) {
			var f = s[typeof v];
			if (f) {
				v = f(v);
				if (typeof v == 'string') {
					return v;
				}
			}
			return null;
		},
		parse : function(text) {
			try {
				return !(/[^,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]/.test(text
						.replace(/"(\\.|[^"\\])*"/g, '')))
						&& eval('(' + text + ')');
			} catch (e) {
				return false;
			}
		}
	};
}();

function htmlJsEncode(str){
	var s = "";
	if (str == null || str.length == 0){
		return "";
	}
	s = str.replace(/&/g, "&gt;");
	s = s.replace(/</g, "&lt;");
	s = s.replace(/>/g, "&gt;");
	s = s.replace(/ /g, "&nbsp;");
	s = s.replace(/\'/g, "&#39;");
	s = s.replace(/\"/g, "&quot;");
	s = s.replace(/\n/g, "<br/>");
	s = s.replace(/\t/g, "<br/>");
	return s;
}

function htmlJsDecode(str){
	var s = "";
	if (str == null || str.length == 0){
		return "";
	}
	s = str.replace(/&gt;/g, "&");
	s = s.replace(/&lt;/g, "<");
	s = s.replace(/&gt;/g, ">");
	s = s.replace(/&nbsp;/g, " ");
	s = s.replace(/&#39;/g, "\'");
	s = s.replace(/&quot;/g, "\"");
	s = s.replace(/<br>/g, "\n");
	return s;
}
