
/**
 * 统计图表生成
 * 
 * @return
 */
function createTb() {
	if(depth > 2){
		alert("图表绘制最多支持两级纵向列，请重设查询条件后再进行操作！");
		return false;
	}	
	
	createTbHxl();//

	createTbZxl();//纵向列表头

	
	createTbTjx();// 统计项
	createTbZxlHead();// 纵向列表头

	createTbContentHead2();// head2
	createTbZxlhj();// 纵向列合计
	createTbHxlhj();// 横向列合计

	jQuery("#form2").submit();
}

// 统计项
function createTbTjx() {
	var splitFlag = FGF_BS + ZS;
	var theadName = "thead0";
	var codes = "";
	var tjxJson = "";
	var thead0ThName = "#" + theadName + " th";

	if (depth > 0 && jQuery(thead0ThName).length == 0) {// 有纵向列，仅包含“总数”
		tjxJson += "{";
		tjxJson += "code:'" + ZS + "'";
		tjxJson += ",name:'" + ZS_CN + "'";
		tjxJson += "}";
	} else {
		if (jQuery(thead0ThName).length == 0) {// 无纵向列
			thead0ThName = "[id='thead"+FGF_BS+"hxl'] th[name^='" + theadName + "']";
		}

		jQuery(thead0ThName).each(function(index) {
			var thName = jQuery(this).attr("name");
			var name = jQuery(this).text();// 表头字段名称
				var head0Code = thName.substr(theadName.length + 1);

				var code = head0Code.split(splitFlag)[0];
				if (code == null || code == "" || code.indexOf(ZS) > -1) {
					code = ZS;
				} else {
					code += FGF_BS + ZS;
				}
				if (codes.indexOf(code) > -1) {
					return false;
				} else {
					codes += code + ";";
				}

				if (index > 0) {
					tjxJson += ",";
				}
				tjxJson += "{";
				tjxJson += "code:'" + code + "'";
				tjxJson += ",name:'" + name + "'";
				tjxJson += "}";
			});

	}

	tjxJson = "[" + tjxJson + "]";

	jQuery("#tjxJson").val(tjxJson);
}

/*
 * 图表横向列值设置
 */
function createTbHxl() {
	var hxlHead = "";
	var hxlContent = "";
	var hxlContentCode = "";
	// 报表横向列，取最末一列
	if (bbhxlArr != null && bbhxlArr.length > 0) {
		var lastHxl = bbhxlArr[bbhxlArr.length - 1];
		hxlHead = jQuery("[id='thead" + FGF_BS + "hxl'] th[name='thead"+FGF_BS+"hxl" + FGF_BS + lastHxl + "']")
				.text();
		hxlContent = jQuery(TBODY + " tr td[name^='" + lastHxl + FGF_BS + "']").map(
				function() {
					return "'" + jQuery(this).text() + "'";
				}).get().join(",");

		hxlContentCode = jQuery(TBODY + " tr td[name^='" + lastHxl + FGF_BS + "']").map(
				function() {
					var code = jQuery(this).attr("name");
					return "'" + code + "'";
				}).get().join(",");
	}

	if (hxlContent != "") {
		hxlContent = "[" + hxlContent + "]";
		hxlContentCode = "[" + hxlContentCode + "]";
	}

	jQuery("#hxlHead").val(hxlHead);
	jQuery("#hxlContent").val(hxlContent);
	jQuery("#hxlContentCode").val(hxlContentCode);
}


function createTbZxl(){
	var zxlContent = "";
	var zxlContentCode = "";
	jQuery("#thead1 th[name^='thead1']").each(function(index) {
		var bblname = "'" + jQuery(this).text() + "'";
		if (zxlContent.indexOf(bblname) > -1) {
			return true;
		}
		if (index > 0) {
			zxlContent += ",";
			zxlContentCode += ",";
		}
		zxlContent += bblname;
		zxlContentCode += "'" + jQuery(this).attr("name") + "'";
	});
	
	if (zxlContent != "") {
		zxlContent = "[" + zxlContent + "]";
		zxlContentCode = "[" + zxlContentCode + "]";
	}
	jQuery("#zxlContent").val(zxlContent);
	jQuery("#zxlContentCode").val(zxlContentCode);
}

/*
 * head2的内容,纵向列二级情况 // contentJson = "[{bblname:'2011',zs : [{ name :
 * '男',data:[0,3,5,1,1341,1382,1466,1379]},{ name :
 * '女',data:[1,0,4,1,2991,3076,3207,3406]},{ name :
 * '未知',data:[0,0,0,0,0,0,1,59]}]}]";
 */
function createTbContentHead2() {
	var contentJson = "";
	var theadName = "thead2";
	jQuery("#" + theadName + " th[name^='" + theadName + FGF_BS + "']").each(
			function(index) {
				var thName = jQuery(this).attr("name");
				var name = jQuery(this).text();// 表头字段名称
				var head2Code = thName.substr(theadName.length + 1);
				if (index > 0) {
					contentJson += ",";
				}
				contentJson += createTbContent(name, head2Code);

			});

	if (contentJson == "") {// 仅一级表头
		var zxlHead = jQuery("#zxlHead").val();
		var zxlHeads = zxlHead.split(",");
		var zxlHead0 = "";
		if (zxlHeads != null) {
			zxlHead0 = zxlHeads[0];//
		}
		contentJson = createTbContent(zxlHead0, "");//
	}
	contentJson = "[" + contentJson + "]";

	jQuery("#contentJson").val(contentJson);

}

/*
 * 图表内容值设置，单列设置，返回{}json格式
 */
function createTbContent(bblname, head2Code) {
	var contentJson = "";

	if (head2Code == null) {
		head2Code = "";
	}
	if (bblname == null) {
		bblname = "";
	}
	var theadName = "thead1";
	contentJson += "bblname:'" + bblname + "'";

	var tjxJson = jQuery("#tjxJson").val();
	var tjxJsonObj = eval(tjxJson);
	if (tjxJsonObj != null) {
		for ( var i = 0; i < tjxJsonObj.length; i++) {
			var zxlJson = "";
			var o = tjxJsonObj[i];
			var tjxCode = o.code;
			var tjxName = o.name;
			jQuery(
					"#" + theadName + " th[name^='" + theadName + FGF_BS
							+ head2Code + "']").each(function(index) {
				var thName = jQuery(this).attr("name");
				var name = jQuery(this).text();// 表头字段名称
					var contentName = thName.substr(theadName.length);
					if (zxlJson != "") {
						zxlJson += ",";
					}
					zxlJson += getContentJson(name, tjxCode + contentName);
				});

			if (zxlJson == "") {// 不含一级纵向列
				zxlJson += getContentJson(tjxName, tjxCode);
			}

			contentJson += "," + tjxCode + ":[" + zxlJson + "]";
		}
	}
	contentJson = "{" + contentJson + "}";

	return contentJson;
}

/*
 * 用逗号拼接的内容显示列，json格式
 */
function getContentJson(name, contentName) {
	var contentJson = "";
	var content = jQuery(TBODY + " tr td[name^='" + contentName + "']").map(
			function() {
				var text = jQuery(this).text();
				return formatText(text);
			}).get().join(",");
	if (content != null && content != "") {
		contentJson = "{ name : '" + name + "',data:[" + content + "]}";
	}
	return contentJson;
}

/*
 * 格式化数值
 */
function formatText(text) {
	if (text == null || text == "null" || text == "") {
		text = "0";
	}
	text = text.replace("%", "");
	return text;
}

/*
 * 纵向列合计
 */
function createTbZxlhj() {
	contentJson = "";
	content = jQuery(
			TBODY + " tr td[name^='zxlhj"+FGF_BS+"']:not(td[name^='zxlhj"+FGF_BS+"hxlhj'])").map(
			function() {
				return jQuery(this).text();
			}).get().join(",");
	if (content != null && content != "") {
		contentJson = "[{ name : '" + ZXLHJ_CN + "',data:[" + content + "]}]";
	}
	jQuery("#zxlhjContentJson").val(contentJson);

}

/*
 * 横向列合计，或者 不包含横向列情况
 * 
 * 
 */
function createTbHxlContent(bblname, head2Code) {
	var contentJson = "";

	if (head2Code == null) {
		head2Code = "";
	}
	if (bblname == null) {
		bblname = "";
	}
	contentJson += "bblname:'" + bblname + "'";

	var tjxJson = jQuery("#tjxJson").val();//统计项,zs:总数,bfb_zs:百分比
	var tjxJsonObj = eval(tjxJson);
	if (tjxJsonObj != null) {
		var flag = false;
		for ( var i = 0; i < tjxJsonObj.length; i++) {
			var o = tjxJsonObj[i];
			var tjxCode = o.code;//zs
			var tjxName = o.name;//总数
			var data = jQuery("#dtjlh td[name^='" + tjxCode + FGF_BS+head2Code+"']").map(
					function() {// 不包含横向列，单行数据
						var text = jQuery(this).text();
						return formatText(text);
					}).get().join(",");
			if (data == null || data == "") {
				var hxlhjCode = "";
				if(tjxCode == "zs"){
					hxlhjCode = "hxlhj"+FGF_BS+"zs";
				}else if(tjxCode == "bfb"+FGF_BS+"zs"){
					hxlhjCode = "bfb"+FGF_BS+"hxlhj"+FGF_BS+"zs";
				}
				data = jQuery("#hxlhj td[name^='" + hxlhjCode + "']").map(
						function() {// 横向列合计
							var text = jQuery(this).text();
							return formatText(text);
						}).get().join(",");
			}

			if (data != null && data != "") {
				contentJson += "," + tjxCode + ":[{ name : '" + tjxName
						+ "',data:[" + data + "]}]";
			}
		}
	}
	if (contentJson != "") {
		contentJson = "{" + contentJson + "}";
	}
	return contentJson;

}



function createTbHxlhj() {
	var contentJson = "";
	var theadName = "thead2";
	jQuery("#" + theadName + " th[name^='" + theadName + FGF_BS+"']").each(
			function(index) {
				var thName = jQuery(this).attr("name");
				var name = jQuery(this).text();// 表头字段名称,2011
				var head2Code = thName.substr(theadName.length + 1);//2011
				if (index > 0) {
					contentJson += ",";
				}
				contentJson += createTbHxlContent(name, head2Code);

			});

	if (contentJson == "") {// 仅一级表头
		var zxlHead = jQuery("#zxlHead").val();
		var zxlHeads = zxlHead.split(",");
		var zxlHead0 = "";
		if (zxlHeads != null) {
			zxlHead0 = zxlHeads[0];//
		}
		contentJson = createTbHxlContent(zxlHead0, "");//
	}
	if(contentJson != ""){
		contentJson = "[" + contentJson + "]";
	}

	jQuery("#hxlhjContentJson").val(contentJson);

}

/*
 * 纵向列头
 */
function createTbZxlHead() {
	var zxlHead = "";
	var zxlHeadCode = "";
	// 报表纵向列getZdsmFromZdmc(zdmc)
	if (bbzxlArr != null && bbzxlArr.length > 0) {
		for ( var i = 0; i < bbzxlArr.length; i++) {
			if (i > 0) {
				zxlHead += ",";
			}
			zxlHeadCode = bbzxlArr[i];
			zxlHead += getZdsmFromZdmc(bbzxlArr[i]);
		}
	}

	jQuery("#zxlHead").val(zxlHead);
	jQuery("#zxlHeadCode").val(zxlHeadCode);

}
