var tjbbModel;// 统计报表数据
var tjsjList;// 统计数据
var bblList;// 报表列
var tsxList;// 特殊项
var cxtj;// 查询条件
var tjArr;// 统计项数组
var bblqzfw;// 报表列取值范围
var tsxJsZdList;// 特殊项计算字段

var bbhxl;// 报表横向列，查询设置
var bbzxl;// 报表纵向列，查询设置
var tsx; // 报表特殊列，查询设置

var bbhxlArr = "";//
var bbzxlArr = "";//
var depth = 0;// 纵向列深度
var hxlNum = 0;// 横向列列数

//缓存纵向总数统计列//
var inital_zs = {};
//总数百分比是否基于初始总数，默认不是//
var base_on_initial_zs= false;

jQuery(function() {
	csyz();
	setInitTjlbGltj();
	onShow();
});

function setInit(){
}

function onShow() {
	var xmdm = jQuery("#xmdm").val();
	var gltj = jQuery("#gltj").val();
	var oldGltj = jQuery("#oldGltj").val();
	var newGltj = jQuery("#newGltj").val();
	var bbhxl = jQuery("#bbhxl").val();
	var bbzxl = jQuery("#bbzxl").val();
	var tsx = jQuery("#tsx").val();

	if (xmdm == null || xmdm == "") {
		$.alert("请选择统计项目！");
		return;
	}
	
	if(newGltj != ""){
		newGltj = "(" + newGltj + ")";
	}
	
	if(oldGltj == "" && newGltj != ""){
		gltj = newGltj;
	}else if(oldGltj != "" && newGltj != ""){
		gltj = oldGltj + " and (" + newGltj + ")";
	}else{
		gltj = oldGltj;
	}
	
	jQuery("#gltj").val(gltj);
	jQuery.ajax( {
		type : "post",
		url : _path + "/niutal/tjcx/tjbb/tjsj.zf?timestamp=" + new Date().getTime(),
		data : {
			xmdm : xmdm,
			gltj : gltj,
			oldGltj : oldGltj,
			newGltj : newGltj,
			bbhxl : bbhxl,
			bbzxl : bbzxl,
			tsx : tsx
		},
		dataType : "json",
		beforeSend : function(){
			jQuery("#tip").show();
		},
		success : function(data) {
			if(data == null){
				$.alert("统计数据查询出错或者数据量过大！");
				jQuery("#tip").hide();
				return;
			}
			
			tjbbModel = data;
			if (tjbbModel == null) {
				$.alert('统计选项不合法，请重新设置统计项！');
				return false;
			}
			init();
			createTable();
			setTjx();// 根据参数，进行列的展示
			jQuery('#tjlbDiv table thead th[name^="thead$hxl$"]').each(function(i){
				var name = jQuery(this).attr('name');
				name = name.substring(name.lastIndexOf('$'));
				var len = jQuery('#tjlbDiv table tbody td[base-title="'+name+'"]').length;
				jQuery(this).html(jQuery(this).html() + '(共:'+len+'条)');
			});
		jQuery("#tjlbDiv").show();
		setButton();// 按钮设置
		jQuery("#tip").hide();
	}
	});
}

/*
 * 超时验证
 */
function csyz() {
	jQuery("#tip").show();
}

/*
 * 初始化数据
 */
function init() {
	tjsjList = tjbbModel['tjsjList'];
	bblList = tjbbModel['bblList'];
	tsxList = tjbbModel['tsxList'];
	cxtj = tjbbModel['cxtj'];
	bblqzfw = tjbbModel['bblqzfwMap'];
	tsxJsZdList = tjbbModel['tsxJsZdList'];

	bbhxl = cxtj['bbhxl'];// 报表横向列，查询设置
	bbzxl = cxtj['bbzxl'];// 报表纵向列，查询设置
	tsx = cxtj['tsx'];// 报表特殊列，查询设置
	if (bbhxl != null && bbhxl != undefined && jQuery.trim(bbhxl) != ""
			&& jQuery.trim(bbhxl) != "undefined") {
		bbhxlArr = bbhxl.split(",");
		hxlNum = bbhxlArr.length;
	}
	if (bbzxl != null && bbzxl != undefined && jQuery.trim(bbzxl) != ""
			&& jQuery.trim(bbzxl) != "undefined") {
		bbzxlArr = bbzxl.split(",");
		depth = bbzxlArr.length;
	}
	
}

/*
 * 根据参数，进行列的展示
 */
function setTjx() {
	setTjlZs();// 设置纵向列的值,总数列
	deleteWz();// 删除空的未知列

	if (tsxJsZdList.length > 0) {// 包含特殊项
		createTsx();// 生成特殊项
	}

	if (tsx.indexOf(HXLHJ) > -1 && hxlNum > 0) {// 横向列合计，必须包含横向列
		createHxlhj();// 生成横向列合计项
	}

	if (depth > 0) {// 包含纵向列
		if (tsx.indexOf(BFB) > -1) {// 百分比
			createZxlhj();// 生成纵向列合计项，计算百分比依赖于纵向列合计项
			createBfb();// 生成百分比
			if (tsx.indexOf(ZXLHJ) == -1) {// 不包含纵向列合计，解决含有百分比，不含有总数列情况
				deleteZxlhj();// 删除纵向列合计
			}
		} else {
			if (tsx.indexOf(ZS) > -1 && tsx.indexOf(ZXLHJ) > -1) {// 纵向列合计项(仅在包含总数列或百分比列时有效)
				createZxlhj();// 生成纵向列合计项
			}
		}
	}

	if (tsx.indexOf(ZS) === -1) {// 不包含总数项
		deleteZs();// 删除总数项
	}else{
		//包含总数列，在总数列数值上加超链接
		//setZsHref();
		//setZsHrefClick();
	}
	createThead();// 生成表头
	setRow();// 合并行

	setThead();// 对表头进行优化

	setStyle();// 设置样式
}

//包含总数列，在总数列数值上加超链接
function setZsHref(){
	
	jQuery(TBODY + " td[name^='"+ZS+"'],td[name^='"+HXLHJ + FGF_BS + ZS+"'],td[name^='"+ZXLHJ+"'][type='zs']").each(function(index){
		var zsValue = jQuery(this).text();
		if(zsValue != "0"){
			var hrefHtml = "<a href='javascript:;' >"+zsValue +"</a>" ;
			jQuery(this).html(hrefHtml);
		}
	});
}


//包含总数列，在总数列数值上加超链接，事件处理
function setZsHrefClick(){
	
	jQuery(TBODY + " td[name^='"+ZS+"'] a").click(function(index){
		var params = "";
		var curBblmc = "";
		
		var hxlValueStr = jQuery(this).parent().parent("tr").attr("id");
		var zxlValueStr = jQuery(this).parent().attr("name");
		var hxlValueArr = hxlValueStr.split(FGF_BS);
		var zxlValueArr = zxlValueStr.split(FGF_BS);
		
		for ( var i = 0; i < bbhxlArr.length; i++) {
			if(params != ""){
				params += " and ";
				curBblmc += " and ";
			}
			var bbhxl = bbhxlArr[i];
			var curBbl = "";
			if(hxlValueArr.length > i){
				curBbl = hxlValueArr[i];
			}			
			curBblmc += "<span class='bold'>"+getZdsmFromZdmc(bbhxl)+"</span>";

			
			if(curBbl == WZ_EN){//未知
				if(bbhxl.indexOf(QJK_PRE) > -1){
					params += "(" + bbhxl.replace(QJK_PRE,"") + " is null "; 
					params += ")";
				}else{
					var qzfwObj = getQzfwObj(bbhxl);
					params += "(" + bbhxl + " not in(";
					params += qzfwObj["dmdhs"];
					params += ")";
					params += " or " + bbhxl + " is null "; 
					params += ")";
				}
				curBblmc += " " + WZ_CN;
			}else if(parseQjk(curBbl).success){//区间块类型特殊处理
				params += "(";
				params += qjkSql(curBbl);
				params += ")";
				curBblmc += "<span> "+ getQzfwMc(bbhxl,curBbl) +"</span>";
			}else{
				params += bbhxl + "='" + hxlValueArr[i] + "'";
				curBblmc += "<span> "+ getQzfwMc(bbhxl,curBbl) +"</span>";
			}
		}
		
		for ( var i = 0; i < bbzxlArr.length; i++) {
			if(params != ""){
				params += " and ";
				curBblmc += " and ";
			}
			var bbzxl = bbzxlArr[i];
			var curBbl = "";
			if(zxlValueArr.length > i + 1){
				curBbl = zxlValueArr[i + 1];
			}	
			curBblmc += "<span class='bold'>"+getZdsmFromZdmc(bbzxl)+"</span>";
			if(curBbl == WZ_EN){//未知
				if(bbzxl.indexOf(QJK_PRE) > -1){
					params += "(" + bbzxl.replace(QJK_PRE,"") + " is null "; 
					params += ")";
				}else{
					var qzfwObj = getQzfwObj(bbzxl);
					params += "(" + bbzxl + " not in(";
					params += qzfwObj["dmdhs"];
					params += ")";
					params += " or " + bbzxl + " is null "; 
					params += ")";
				}
				curBblmc += " " + WZ_CN;
			}else if(parseQjk(curBbl).success){//区间块类型特殊处理
				params += "(";
				params += qjkSql(curBbl);
				params += ")";
				curBblmc += "<span> "+ getQzfwMc(bbzxl,curBbl) +"</span>";
			}else{
				params += bbzxl + "='" + curBbl + "'";
				curBblmc += "<span> "+ getQzfwMc(bbzxl,curBbl) +"</span>";
			}
		}
		jQuery("#curBbl").val(params);
		jQuery("#curBblmc").val(curBblmc);
		tjbbXqWin();
	});
	
	//横向合计
	jQuery(TBODY + " td[name^='"+HXLHJ + FGF_BS + ZS+"']").click(function(index){
		var params = "";
		var curBblmc = "";

		var zxlValueStr = jQuery(this).attr("name");
		var zxlValueArr = zxlValueStr.split(FGF_BS);
		
		for ( var i = 0; i < bbzxlArr.length; i++) {
			if(params != ""){
				params += " and ";
				curBblmc += " and ";
			}
			var bbzxl = bbzxlArr[i];
			var curBbl = "";
			if(zxlValueArr.length > i + 2){
				curBbl = zxlValueArr[i + 2];
			}
			
			curBblmc += "<span class='bold'>"+getZdsmFromZdmc(bbzxl)+"</span>";
			
			if(curBbl == WZ_EN){//未知
				if(bbzxl.indexOf(QJK_PRE) > -1){
					params += "(" + bbzxl.replace(QJK_PRE,"") + " is null "; 
					params += ")";
				}else{
					var qzfwObj = getQzfwObj(bbzxl);
					params += "(" + bbzxl + " not in(";
					params += qzfwObj["dmdhs"];
					params += ")";
					params += " or " + bbzxl + " is null "; 
					params += ")";
					curBblmc += " " + WZ_CN;
				}
			}else if(parseQjk(curBbl).success){//区间块类型特殊处理
				params += "(";
				params += qjkSql(curBbl);
				params += ")";
				curBblmc += "<span> "+ getQzfwMc(bbzxl,curBbl) +"</span>";
			}else{
				params += bbzxl + "='" + curBbl + "'";
				curBblmc += "<span> "+ getQzfwMc(bbzxl,curBbl) +"</span>";
			}
		}
		jQuery("#curBbl").val(params);
		jQuery("#curBblmc").val(curBblmc);
		tjbbXqWin();
	});
	
	//纵向合计
	jQuery(TBODY + " td[name^='"+ZXLHJ +"']").click(function(index){
		var params = "";
		var curBblmc = "";

		var hxlValueStr = jQuery(this).attr("name");
		var hxlValueArr = hxlValueStr.split(FGF_BS);
		if(hxlValueStr == ZXLHJ + FGF_BS + HXLHJ){
			
		}else{
			for ( var i = 0; i < bbhxlArr.length; i++) {
				if(params != ""){
					params += " and ";
					curBblmc += " and ";
				}
				var bbhxl = bbhxlArr[i];
				var curBbl = "";
				if(hxlValueArr.length > i + 1){
					curBbl = hxlValueArr[i + 1];
				}
				
				curBblmc += "<span class='bold'>"+getZdsmFromZdmc(bbhxl)+"</span>";
				
				if(curBbl == WZ_EN){//未知
					if(bbhxl.indexOf(QJK_PRE) > -1){
						params += "(" + bbhxl.replace(QJK_PRE,"") + " is null "; 
						params += ")";
					}else{
						var qzfwObj = getQzfwObj(bbhxl);
						params += "(" + bbhxl + " not in(";
						params += qzfwObj["dmdhs"];
						params += ")";
						params += " or " + bbhxl + " is null "; 
						params += ")";
						curBblmc += " " + WZ_CN;
					}
				}else if(parseQjk(curBbl).success){//区间块类型特殊处理
					params += "(";
					params += qjkSql(curBbl);
					params += ")";
					curBblmc += "<span> "+ getQzfwMc(bbhxl,curBbl) +"</span>";
				}else{
					params += bbhxl + "='" + curBbl + "'";
					curBblmc += "<span> "+ getQzfwMc(bbhxl,curBbl) +"</span>";
				}
			}
		}
	
//		alert(params);
		jQuery("#curBbl").val(params);
		jQuery("#curBblmc").val(curBblmc);
		tjbbXqWin();
	});
}


/*
 * 统计报表详情页弹层显示
 */
function tjbbXqWin(){
	var title = "统计报表详情";

	var url = _path + "/niutal/tjcx/tjbbXq/cxTjbbXq.zf?timestamp=" + new Date().getTime();
	url += "&gnmk=" + jQuery("#gnmk").val();
	url += "&xmdm="+ jQuery("#xmdm").val();
	showWindow(title, 803, 500,url);
}

/*
 * 设置样式
 */
function setStyle() {
	jQuery("#hxlhj td").addClass("tjlbtotal");
	jQuery("tbody tr td[name^='zxlhj" + FGF_BS + "']").addClass("tjlbtotal");
}

/*
 * 对表头进行优化
 */
function setThead() {
	// 若最底行全为总数行，则去掉总数行标识
	var flag = false;
	jQuery("#thead0 th").each(function(index) {
		var text = jQuery(this).text();
		if (text != ZS_CN) {
			flag = true;
		}
	});
	if (!flag) {
		var flag1 = false;
		jQuery(
				"#thead" + JQUERY_ID_FGF_BS + "hxl th[name^='thead" + FGF_BS
						+ "hxl']").each(function(index) {
			var rowspan = jQuery(this).attr("rowspan");
			try {
				rowspan = parseInt(rowspan);
				jQuery(this).attr("rowspan", rowspan - 1);
				if (index == 0) {// 纵向合计列只执行一次
					rowspan = jQuery(
							"#thead th[name='thead" + FGF_BS + "zxlhj']").attr(
							"rowspan");// 纵向合计列
					jQuery("#thead th[name='thead" + FGF_BS + "zxlhj']").attr(
							"rowspan", rowspan - 1);
				}
			} catch (ex) {
				flag1 = true;
			}
		});
		if (!flag1) {
			jQuery("#thead0").remove();
		}
	}

}

function createTable() {
	var sHtml = "";
	sHtml += "<table  class='table table-bordered table-striped table-condensed' align='center' border='1'>";
	sHtml += "<thead id='thead'>";
	sHtml += "</thead>";
	sHtml += createTbody();
	sHtml += "</table>";
	jQuery("#tjlbDiv").html(sHtml);
}

/*
 * 生成表头
 */
function createThead() {
	var sHtml = "";
	sHtml += "<tr id='thead" + FGF_BS + "hxl'>";
	if (hxlNum > 0) {
		for ( var i = 0; i < bbhxlArr.length; i++) {
			var hxlRowSpan;
			if (depth > 0) {
				hxlRowSpan = depth + 2;
			} else {
				hxlRowSpan = 1;
			}
			var zdsm = getZdsmFromZdmc(bbhxlArr[i]);
			sHtml += "<th rowSpan='" + hxlRowSpan + "' name='thead" + FGF_BS
					+ "hxl" + FGF_BS + bbhxlArr[i] + "'>" + zdsm +"</th>";
		}
	}
	if (depth > 0) {
		var zxlColSpan1 = jQuery(TBODY + " tr:first td").length - hxlNum;
		sHtml += "<th colSpan='" + zxlColSpan1 + "'>"
				+ getZdsmFromZdmc(bbzxlArr[0]) + "</th>";
	}
	sHtml += "</tr>";

	// 最末行
	var thead0 = "";
	var thead1 = "";
	var thead2 = "";
	var thead3 = "";
	jQuery(TBODY + " tr:first td:not([name^='" + ZXLHJ + FGF_BS + "'])").each(
			function(index) {
				if (index >= hxlNum) {
					var xsName = "";
					var code1 = "";
					var code2 = "";
					var code3 = "";
					var tdName = jQuery(this).attr("name");
					var nLength = tdName.length;
					tdNames = tdName.split(FGF_BS);
					if (tdNames[0] === ZS || tdNames[0] === BFB) {
						if (tdNames[0] === ZS) {
							xsName = ZS_CN;
							code1 = theadName(tdName, 1);
							code2 = theadName(tdName, 1, 1);
							code3 = theadName(tdName, 1, 2);
						} else if (tdNames[0] === BFB) {
							xsName = BFB_CN;
							code1 = theadName(tdName, 2);
							code2 = theadName(tdName, 2, 1);
							if (code2 != "") {
								code3 = theadName(tdName, 2, 2);
							}
						}
					} else if (tdNames[0] === MAX || tdNames[0] === MIN
							|| tdNames[0] === AVG || tdNames[0] === SUM || tdNames[0] === PTG) {
						var name0 = getTsxZdsmFromZdmc(tdNames[1]);
						if (tdNames[0] === MAX) {
							xsName = name0 + "(" + MAX_CN + ")";
						} else if (tdNames[0] === MIN) {
							xsName = name0 + "(" + MIN_CN + ")";
						} else if (tdNames[0] === AVG) {
							xsName = name0 + "(" + AVG_CN + ")";
						} else if (tdNames[0] === SUM) {
							xsName = name0 + "(" + SUM_CN + ")";
						} else if (tdNames[0] === PTG) {
							xsName = name0 + "(" + PTG_CN + ")";
						}
						code1 = theadName(tdName, 3);
						code2 = theadName(tdName, 3, 1);
						if (code2 != "") {
							code3 = theadName(tdName, 3, 2);
						}
					}

					thead0 += "<th name='thead0" + FGF_BS + tdName
							+ "'  colSpan='1'>" + xsName + "</th>";
					if (code1 != "") {
						thead1 += "<th name='thead1" + FGF_BS + code1
								+ "' colSpan='1'>" + getTheadName(code1, 1)
								+ "</th>";
					}
					if (code2 != "") {
						thead2 += "<th name='thead2" + FGF_BS + code2
								+ "' colSpan='1'>" + getTheadName(code2, 2)
								+ "</th>";
					}
					if (code3 != "") {
						thead3 += "<th name='thead3" + FGF_BS + code3
								+ "' colSpan='1'>" + getTheadName(code3, 3)
								+ "</th>";
					}
				}
			});

	if (thead3 != "") {
		sHtml += "<tr id='thead3'>";
		sHtml += thead3;
		sHtml += "</tr>";
	}

	if (thead2 != "") {
		sHtml += "<tr id='thead2'>";
		sHtml += thead2;
		sHtml += "</tr>";
	}

	if (thead1 != "") {
		sHtml += "<tr id='thead1'>";
		sHtml += thead1;
		sHtml += "</tr>";
	}

	sHtml += "<tr id='thead0'>";
	sHtml += thead0;
	sHtml += "</tr>";

	jQuery(THEAD).html(sHtml);

	// 合并表头
	for ( var i = 1; i < depth + 1; i++) {
		var tdObjOld;
		jQuery("#thead" + i + ">th").each(
				function(index) {
					var tdObj = jQuery(this);
					if (tdObjOld != null
							&& tdObjOld.attr("name") === tdObj.attr("name")) {
						tdObj.remove();
						var colSpan = tdObjOld.attr("colSpan");
						if (colSpan == undefined) {
							colSpan = 1;
						}
						tdObjOld.attr("colSpan", parseInt(colSpan) + 1);
					} else {
						tdObjOld = tdObj;
					}
				});
	}

	// 增加纵向合计表头
	if (depth > 0) {
		if (jQuery("td[name^='" + ZXLHJ + FGF_BS + "'][type='zs']").length > 0) {
			var theadZxlhj = "<th name='thead" + FGF_BS + ZXLHJ + "' rowSpan='"
					+ (depth + 1) + "'>" + ZXLHJ_CN + "</th>";
			jQuery(THEAD + " tr:nth-child(2)").append(theadZxlhj);
		}
		
		if(tsxList != null && tsxList.length > 0){
			jQuery.each(tsxList,function(i,n){
				var _tsxmc = n['tsxmc'];
				var _tsxsm = n['tsxsm'];
				if (jQuery("td[name^='" + ZXLHJ + FGF_BS + MIN + FGF_BS + _tsxmc + FGF_BS + "']").length > 0) {
					var theadZxlhj = "<th name='thead" + FGF_BS + ZXLHJ + FGF_BS + MIN + FGF_BS + _tsxmc +"' rowSpan='"
						+ (depth + 1) + "'>" + _tsxsm +"("+MIN_CN + ")</th>";
					jQuery(THEAD + " tr:nth-child(2)").append(theadZxlhj);
				}
				if (jQuery("td[name^='" + ZXLHJ + FGF_BS + MAX + FGF_BS + _tsxmc + FGF_BS + "']").length > 0) {
					var theadZxlhj = "<th name='thead" + FGF_BS + ZXLHJ + FGF_BS + MAX + FGF_BS + _tsxmc +"' rowSpan='"
						+ (depth + 1) + "'>" + _tsxsm +"("+MAX_CN + ")</th>";
					jQuery(THEAD + " tr:nth-child(2)").append(theadZxlhj);
				}
				if (jQuery("td[name^='" + ZXLHJ + FGF_BS + AVG + FGF_BS + _tsxmc + FGF_BS + "']").length > 0) {
					var theadZxlhj = "<th name='thead" + FGF_BS + ZXLHJ + FGF_BS + AVG + FGF_BS + _tsxmc +"' rowSpan='"
						+ (depth + 1) + "'>" + _tsxsm +"("+AVG_CN + ")</th>";
					jQuery(THEAD + " tr:nth-child(2)").append(theadZxlhj);
				}
				if (jQuery("td[name^='" + ZXLHJ + FGF_BS + SUM + FGF_BS + _tsxmc + FGF_BS + "']").length > 0) {
					var theadZxlhj = "<th name='thead" + FGF_BS + ZXLHJ + FGF_BS + SUM + FGF_BS + _tsxmc +"' rowSpan='"
						+ (depth + 1) + "'>" + _tsxsm +"("+SUM_CN + ")</th>";
					jQuery(THEAD + " tr:nth-child(2)").append(theadZxlhj);
				}
				if (jQuery("td[name^='" + ZXLHJ + FGF_BS + PTG + FGF_BS + _tsxmc + FGF_BS + "']").length > 0) {
					var theadZxlhj = "<th name='thead" + FGF_BS + ZXLHJ + FGF_BS + PTG + FGF_BS + _tsxmc +"' rowSpan='"
						+ (depth + 1) + "'>" + _tsxsm +"("+PTG_CN + ")</th>";
					jQuery(THEAD + " tr:nth-child(2)").append(theadZxlhj);
				}
			});
		}
	}

	// 当纵向列无级别时，合并表头
	if (depth === 0) {
		jQuery("#thead" + JQUERY_ID_FGF_BS + "hxl").append(
				jQuery("#thead0").html());
		jQuery("#thead0").remove();
	}
}

/*
 * 取标头名称 code:2011_1 jb级别,1为纵向列最底列，2为倒数第二层。。。。
 */
function getTheadName(name, jb) {
	var bblName = name;
	if (depth >= jb) {
		var bbzxl = bbzxlArr[depth - jb];
		names = name.split(FGF_BS);
		var code = names[names.length - 1];
		bblName = getBblName(bbzxl, code);
		if (bblName === WZ_EN) {
			bblName = WZ_CN;
		}
	}
	return bblName;
}

/*
 * 从td名称中截取,a_b_c_d 0,1返回a_b_c 1,1返回b_c 1返回b_c_d
 */
function theadName(tdName, start, end) {
	var sName = "";
	var startNum = 0;
	var endNum = 0;
	var nLength = tdName.length;
	var tdNames = tdName.split(FGF_BS);
	var flag = false;
	if (tdNames != null) {
		if (tdNames.length >= start) {
			for ( var i = 0; i < start; i++) {
				startNum += tdNames[i].length + 1;
			}
		} else {
			flag = true;
		}
		if (end != null && tdNames.length >= end) {
			for ( var i = 0; i < end; i++) {
				endNum += tdNames[tdNames.length - i - 1].length + 1;
			}
			if (tdNames.length < end) {
				flag = true;
			}
		}
	} else {
		flag = true;
	}
	if (flag || startNum >= nLength - endNum) {
		sName = "";
	} else {
		sName = tdName.substring(startNum, nLength - endNum);
	}
	return sName;
}

/*
 * 生成内容行
 */
function createTbody() {
	var sHtml = "";
	sHtml += "<tbody id='tbody'>";
	var hxlPjOld = "";
	if (hxlNum === 0) {// 无横向列
		sHtml += "<tr id='" + getTrId() + "'>";
		sHtml += createTjlTd();
		sHtml += "</tr>";
	} else {
		// 循环统计数据
		for ( var i = 0; i < tjsjList.length; i++) {
			var o = tjsjList[i];
			var hxlPj = "";
			var sTd = "";
			for ( var j = 0; j < bbhxlArr.length; j++) {// 显示查询条件中设置的横向列字段
				var bbhxlKey = bbhxlArr[j];
				var bbhxlCode = o[bbhxlKey] || o[bbhxlKey.toLowerCase()]
						|| o[bbhxlKey.toUpperCase()];
				if(bbhxlCode === undefined){
					bbhxlCode = WZ_EN;
				}
				sTd += "<td name='" + bbhxlKey + FGF_BS + bbhxlCode
						+ "'  class='tjlbleft' base-title='" + FGF_BS + bbhxlKey + "'>";
				sTd += getBblName(bbhxlKey, bbhxlCode);
				// sTd += bbhxlCode;
				sTd += "</td>";
				hxlPj += bbhxlCode + FGF_BS;
			}
			if (hxlPj != hxlPjOld) {
				sHtml += "<tr id='" + getTrId(o) + "'>";
				sHtml += sTd;
				hxlPjOld = hxlPj;
				sHtml += createTjlTd();
			}
		}
		sHtml += "</tr>";
	}
	sHtml += "</tbody>";
	return sHtml;
}

/*
 * 生成横向列合计项
 */
function createHxlhj() {
	var hjTr = "<tr id='" + HXLHJ + "'>";
	hjTr += "<td name='' colSpan='" + hxlNum + "' class='tjlbleft'>";
	hjTr += HXLHJ_CN;
	hjTr += "</td>";
	jQuery(TBODY + " tr:first td").each(function(index) {
		if (index >= hxlNum) {
			var valueTmp = 0;
			var tdName = jQuery(this).attr("name");
			var hjZs = 0;
			if (tdName.indexOf(ZXLHJ) > -1) {// 纵向列进行汇总
				tdName = ZXLHJ;
				jQuery(TBODY + " tr td[name^='" + tdName + "']").each(
						function() {
							var value = jQuery(this).text();
							if (value != null && value != "") {
								hjZs += parseInt(value);// 总数汇总，全为整数
							}
						});
			} else {// 普通列汇总
				jQuery(TBODY + " tr td[name='" + tdName + "']").each(
						function() {
							var value = jQuery(this).text();
							if (value != null && value != "") {
								valueTmp = value;
								hjZs += parseFloat(value);
							}
						});
			}
			var str = valueTmp + "";// 计算小数位数，合计项与普通数据一致
			var strs = str.split(".");
			if (strs.length > 1) {
				hjZs = xsgsh2(hjZs, strs[1].length);
			}
			hjTr += "<td name='" + HXLHJ + FGF_BS + tdName + "'>" + hjZs
					+ "</td>";
		}
	});
	hjTr += "</tr>";
	jQuery(TBODY).append(hjTr);
}

/*
 * 生成纵向列合计项
 * 
 * generate tsx tj
 * 
 */
function createZxlhj() {
	jQuery(TBODY + ">tr").each(
		function(index) {
			var trId = jQuery(this).attr("id");//横向列代码
			var hjZs = 0;
			jQuery(this).find(
					"td[name^='" + ZS + FGF_BS + "'],[name^='" + HXLHJ
							+ FGF_BS + ZS + FGF_BS + "']").each(
					function(tdIndex) {
						var value = jQuery(this).text();
						if (value != null && value != "") {
							hjZs += parseInt(value);
						}
					});
			var hjTd = "";
			//缓存横向列总数合计////////////////////////
			if(!inital_zs[ZXLHJ + FGF_BS + trId]){
				inital_zs[ZXLHJ + FGF_BS + trId] = hjZs;
			}
			//如果总数被锁定，则总数列取缓存中的数据
			if(base_on_initial_zs && inital_zs[ZXLHJ + FGF_BS + trId]){
				hjZs = inital_zs[ZXLHJ + FGF_BS + trId];
			}
			hjTd += "<td name='" + ZXLHJ + FGF_BS + trId + "' type='zs'>" + hjZs + "</td>";
			jQuery(this).append(hjTd);
	});
	
	if(tsxList != null && tsxList.length > 0){
		jQuery.each(tsxList,function(i,n){
			var _tsxmc = n['tsxmc'];
			var _tsxsm = n['tsxsm'];
			var _xsws  = jQuery.trim(n['xsws']);
			jQuery(TBODY + ">tr").each(
				function(index) {
					var _ptg_context = jQuery(this);
					var trId = jQuery(this).attr("id");
					var hjZs = 0;
					var hjTs = 0;
					var hjZs_max = 0;
					var hjZs_min = 0;
					var hjZs_avg = 0;
					var hjZs_sum = 0;
					var hjZs_max_td_id = '';
					var hjZs_min_td_id = '';
					var hjZs_avg_td_id = '';
					var hjZs_sum_td_id = '';
					var hjZs_max_f =  false;
					var hjZs_min_f = false;
					var hjZs_avg_f = false;
					var hjZs_sum_f = false;
					jQuery(this).find("td[name^='" + MAX + FGF_BS + _tsxmc + FGF_BS +"'],[name^='" + HXLHJ + FGF_BS + MAX + FGF_BS + _tsxmc + FGF_BS +"']," +
									  "td[name^='" + MIN + FGF_BS + _tsxmc + FGF_BS +"'],[name^='" + HXLHJ + FGF_BS + MIN + FGF_BS + _tsxmc + FGF_BS +"']," +
									  "td[name^='" + AVG + FGF_BS + _tsxmc + FGF_BS +"'],[name^='" + HXLHJ + FGF_BS + AVG + FGF_BS + _tsxmc + FGF_BS +"']," +
									  "td[name^='" + SUM + FGF_BS + _tsxmc + FGF_BS +"'],[name^='" + HXLHJ + FGF_BS + SUM + FGF_BS + _tsxmc + FGF_BS +"']").each(
							function(tdIndex) {
								var value = jQuery(this).text();
								var name = jQuery(this).attr('name');
								if (value != null && value != "") {
									hjZs = parseFloat(value);
								}else{
									hjZs=0;
								}
								if(name.indexOf(MIN + FGF_BS + _tsxmc + FGF_BS)>=0){
									hjZs_min_td_id = ZXLHJ + FGF_BS + MIN + FGF_BS + _tsxmc + FGF_BS + ZS + FGF_BS + trId;
									hjZs_min_f = true;
									if(hjZs < hjZs_min){
										hjZs_min = hjZs;
									}
								}
								if(name.indexOf(MAX + FGF_BS + _tsxmc + FGF_BS)>=0){
									hjZs_max_td_id = ZXLHJ + FGF_BS + MAX + FGF_BS + _tsxmc + FGF_BS + ZS + FGF_BS + trId;
									hjZs_max_f = true;
									if(hjZs > hjZs_max){
										hjZs_max = hjZs;
									}
								}
								if(name.indexOf(AVG + FGF_BS + _tsxmc + FGF_BS)>=0){
									hjZs_avg_td_id = ZXLHJ + FGF_BS + AVG + FGF_BS + _tsxmc + FGF_BS + ZS + FGF_BS + trId;
									hjZs_avg+=hjZs;
									hjZs_avg_f = true;
									hjTs++;
								}
								if(name.indexOf(SUM + FGF_BS + _tsxmc + FGF_BS)>=0){
									hjZs_sum_td_id = ZXLHJ + FGF_BS + SUM + FGF_BS + _tsxmc + FGF_BS + ZS + FGF_BS+ trId;
									hjZs_sum_f = true;
									hjZs_sum+=hjZs;
								}
							});
					
					//如果设置了特殊项百分比，则在这里计算//
					jQuery(this).find("td[name^='" + PTG + FGF_BS + _tsxmc + FGF_BS +"']").each(
							function(i){
								var _sum_ = SUM + jQuery(this).attr("name").substring(PTG.length);
								var _sum_val = jQuery("td[name='" +_sum_+ "']" , _ptg_context).text();
								if(_sum_val != null && _sum_val !=""){
									var _ptg_jg = _sum_val/hjZs_sum *100;
									if (_xsws == null || _xsws == "") {
										_ptg_jg = xsgsh(_ptg_jg, 2);// 默认方式
									} else {
										_ptg_jg = xsgsh(_ptg_jg, getTsxXswsOfBs(_xsws, PTG) == 0 ? 2: getTsxXswsOfBs(_xsws, PTG));//
									}
									jQuery(this).text(_ptg_jg + "%");
								}else{
									jQuery(this).text("0");
								}
							}
					);
					jQuery(this).find("td[name^='" + HXLHJ + FGF_BS + PTG + FGF_BS + _tsxmc + FGF_BS +"']").each(
							function(i){
								var _sum_ = HXLHJ + FGF_BS + SUM + jQuery(this).attr("name").substring((HXLHJ + FGF_BS + PTG).length);
								var _sum_val = jQuery("td[name='" +_sum_+ "']" , _ptg_context).text();
								if(_sum_val != null && _sum_val !=""){
									var _ptg_jg = _sum_val/hjZs_sum *100;
									if (_xsws == null || _xsws == "") {
										_ptg_jg = xsgsh(_ptg_jg, 2);// 默认方式
									} else {
										_ptg_jg = xsgsh(_ptg_jg, getTsxXswsOfBs(_xsws, PTG) == 0 ? 2: getTsxXswsOfBs(_xsws, PTG));//
									}
									jQuery(this).text(_ptg_jg + "%");
								}else{
									jQuery(this).text("0");
								}
							}	
					);
					//////////////////////////////////////
					if(hjTs != 0){
						hjZs_avg = Math.round(hjZs_avg/hjTs *100)/100;
					}
					var hjTd_max = "<td name='" + hjZs_max_td_id + "' data='"+_tsxmc+"' type='max'>" + hjZs_max + "</td>";
					var hjTd_min = "<td name='" + hjZs_min_td_id + "' data='"+_tsxmc+"' type='min'>" + hjZs_min + "</td>";
					var hjTd_avg = "<td name='" + hjZs_avg_td_id + "' data='"+_tsxmc+"' type='avg'>" + xsgsh2(hjZs_avg,_xsws) + "</td>";
					var hjTd_sum = "<td name='" + hjZs_sum_td_id + "' data='"+_tsxmc+"' type='sum'>" + xsgsh2(hjZs_sum,_xsws) + "</td>";
					if(hjZs_min_f){
						jQuery(this).append(hjTd_min);
					}
					if(hjZs_max_f){
						jQuery(this).append(hjTd_max);
					}
					if(hjZs_avg_f){
						jQuery(this).append(hjTd_avg);
					}
					if(hjZs_sum_f){
						jQuery(this).append(hjTd_sum);
					}
				});
		});
	}
}

/*
 * 删除空的未知列
 */
function deleteWz() {
	var tdName = "";
	for ( var m = 0; m < getQzfws(bbzxlArr[0]).length; m++) {// 
		if (getQzfws(bbzxlArr[0]) != null) {
			var code1 = getQzfws(bbzxlArr[0])[m].split(":")[0];
			if (depth > 1) {
				for ( var n = 0; n < getQzfws(bbzxlArr[1]).length; n++) {// 
					var code2 = getQzfws(bbzxlArr[1])[n].split(":")[0];
					if (depth > 2) {
						tdName = ZS + FGF_BS + code1 + FGF_BS + code2 + FGF_BS
								+ WZ_EN;
						deleteWzDeal(tdName);
					}
				}
				tdName = ZS + FGF_BS + code1 + FGF_BS + WZ_EN;
				deleteWzDeal(tdName);
			}
		}
	}
	tdName = ZS + FGF_BS + WZ_EN;
	deleteWzDeal(tdName);
}

/*
 * 删除空的未知列，具体实现
 */
function deleteWzDeal(tdName) {
	var flag = false;
	jQuery(TBODY + ">tr>td[name^='" + tdName + "']").each(
			function(index) {
				var text = jQuery(this).text();
				if (text != null && text != "null" && text != "" && text != "0"
						&& text != undefined) {
					flag = true;
					return;
				}
			});
	if (!flag) {
		jQuery(TBODY + ">tr>td[name^='" + tdName + "']").remove();
	}
}

/*
 * 删除总数项
 */
function deleteZs() {
	jQuery(
			TBODY + ">tr>td[name^='" + ZS + "'],[name^='" + HXLHJ + FGF_BS + ZS
					+ "']").remove();
}

/*
 * 删除纵向列合计
 */
function deleteZxlhj() {
	jQuery(TBODY + ">tr>td[name^='" + ZXLHJ + "']").remove();
}

/*
 * 生成百分比
 */
function createBfb() {
	jQuery(TBODY + ">tr")
			.each(
					function(index) {
						var hxllb = hxlNum;
						//横向列代码
						var trId = jQuery(this).attr("id");
						//纵向列合计总数（最后一列的值）
						var zxlhj = jQuery(this).find(
								"td[name='" + ZXLHJ + FGF_BS + trId + "']")
								.text();
						if (trId === HXLHJ) {// 底部合计项处理
							hxllb = 1;
							zxlhj = jQuery(this)
									.find(
											"td[name='" + ZXLHJ + FGF_BS
													+ HXLHJ + "']").text();
						} else if (trId === DTJLH) {// 单条记录行
							hxllb = 0;
						}
						jQuery(this).find(
								"td[name^='" + ZS + FGF_BS + "'],[name='" + ZS + "'],[name^='" + HXLHJ
										+ FGF_BS + ZS + "']").each(
								function(tdIndex) {
									var tdName = jQuery(this).attr("name");
									var value = jQuery(this).text();
									var bfb = 0;
									if (value != null && value != "") {
										if (parseInt(zxlhj) === 0) {
											bfb = 0;
										} else {
											bfb = parseInt(value) * 100
													/ parseInt(zxlhj);
											bfb = xsgsh(bfb, 2);
										}
									}
									if (bfb != null && bfb != "") {
										bfb += "%";
									}
									var hjTd = "<td name='" + BFB + FGF_BS
											+ tdName + "'>" + bfb + "</td>";
									jQuery(this).after(hjTd);
								});
					});
}

/*
 * 生成特殊项
 */
function createTsx() {
	jQuery(TBODY + ">tr")
			.each(
					function(index) {
						var trName = jQuery(this).attr("name");
						var zxlhj = jQuery(this).find(
								"td[name='" + ZXLHJ + FGF_BS + trName + "']")
								.text();
						if (trName === HXLHJ) {// 底部合计项处理
							zxlhj = jQuery(this)
									.find(
											"td[name='" + HXLHJ + FGF_BS
													+ ZXLHJ + "']").text();
						}
						jQuery(this)
								.find(
										"td[name^='" + ZS + "'],[name^='"
												+ HXLHJ + FGF_BS + ZS + "']")
								.not("td[name$='" + WZ_EN + "']")
								.each(
										function(tdIndex) {
											var tdName = jQuery(this).attr(
													"name");
											var hjTd = "";
											for ( var i = 0; i < tsxJsZdList.length; i++) {
												hjTd += "<td name='"
														+ tsxJsZdList[i]
														+ FGF_BS + tdName
														+ "'>" + "0" + "</td>";
											}
											jQuery(this).after(hjTd);
										});
					});

	// 循环统计数据
	for ( var i = 0; i < tjsjList.length; i++) {
		var o = tjsjList[i];
		var trId = getTrId(o);
		var tdName = getTdName(o);
		for ( var j = 0; j < tsxJsZdList.length; j++) {
			var tsxJsZd = tsxJsZdList[j];// 特殊项计算字段,格式max$cj
			var td = "#" + changeJqueryIdGs(trId) + "" + ">td[name='" + tsxJsZd
					+ FGF_BS + tdName + "']";
			var value = o[tsxJsZd] || o[tsxJsZd.toLowerCase()]
					|| o[tsxJsZd.toUpperCase()];
			var jg = "";
			var tsxJsZds = tsxJsZd.split(FGF_BS);
			var xsws = jQuery.trim(getTsxXsws(tsxJsZds[1]));
			if (xsws == null || xsws == "") {
				jg = xsgsh(value, 2);// 默认方式
			} else if (onlyInt(xsws)) {// 全为相同的小数位数
				jg = xsgsh2(value, xsws);//
			} else {//
				jg = xsgsh2(value, getTsxXswsOfBs(xsws, tsxJsZds[0]));//
			}

			jQuery(td).html(jg);
		}
	}
}

/*
 * 根据标识取小数位数，如：max
 */
function getTsxXswsOfBs(xsws, bs) {
	var num = 0;
	var xswss = xsws.split(",");
	for ( var i = 0; i < xswss.length; i++) {
		if (xswss[i].split(":")[0] == bs) {
			num = xswss[i].split(":")[1];
		}
	}
	return num;
}

/*
 * 得到特殊项小数位数
 */
function getTsxXsws(zdmc) {
	var xsws = "";
	for ( var i = 0; i < tsxList.length; i++) {
		var o = tsxList[i];
		if (o.tsxmc == zdmc) {
			xsws = o.xsws;// 小数位数
			break;
		}
	}
	return xsws;
}

/*
 * 合并行
 */
function setRow() {
	var num = 1;// 记数
	var tdObjOld;
	for ( var j = 0; j < bbhxlArr.length - 1; j++) {// 
		if(j == 1){
			break;
		}
		var bbhxlKey = bbhxlArr[j];
		jQuery(TBODY + ">tr>td[name^='" + bbhxlKey + FGF_BS + "']").each(
				function(index) {
					var tdObj = jQuery(this);
					if (tdObjOld != null
							&& tdObjOld.attr('name') === tdObj.attr('name')) {
						tdObj.remove();
						num++;
						tdObjOld.attr("rowSpan", num);
					} else {
						num = 1;
						nameOld = name;
						tdObjOld = tdObj;
					}
				});
	}
}

/*
 * 设置纵向列的值
 */
function setTjlZs() {
	// 循环统计数据
	for ( var i = 0; i < tjsjList.length; i++) {
		var o = tjsjList[i];
		var trId = getTrId(o);
		var tdName = getTdName(o);
		//var td = "#" + jQueryChangeTszf(changeJqueryIdGs(trId)) + ">td[name='" + tdName + "']";
		var tdObj = jQuery(document.getElementById(trId)).find("td[name='" + tdName + "']");
		var zs = o[ZS] || o[ZS.toLowerCase()] || o[ZS.toUpperCase()];
		var value = tdObj.text();
		if (value == null || value == "" || value == undefined) {
			value = 0;
		}
		//TODO 如果是未知的数据，需要设置未知的值在元素上，查询未知数据详情的时候需要使用
		//if(o[WZ_VALUE]){
		//	if(tdObj.data(WZ_VALUE_CXTJ)){
		//		tdObj.data(WZ_VALUE_CXTJ, tdObj.data(WZ_VALUE_CXTJ) + "," + o[WZ_VALUE]);
		//	}else{
		//		tdObj.data(WZ_VALUE_CXTJ, o[WZ_VALUE]);
		//	}
		//}
		tdObj.html(parseInt(zs) + parseInt(value));
	}
}

/*
 * 字符串替换，针对jquery id包含特殊字符情况
 */
function changeJqueryIdGs(str) {
	var reg = new RegExp(JQUERY_ID_FGF_BS1, "g");
	str = str.replace(reg, JQUERY_ID_FGF_BS);
	return str;
}

/*
 * 生成统计列的TD，内容为空
 */
function createTjlTd() {
	var sTd = "";
	if (depth === 0) {
		sTd += "<td name='" + ZS + "'>";
		sTd += "0";
		sTd += "</td>";
	} else {
		for ( var m = 0; m < getQzfws(bbzxlArr[0]).length; m++) {// 
			if (getQzfws(bbzxlArr[0]) != null) {
				var code1 = getQzfws(bbzxlArr[0])[m].split(":")[0];
				if (depth > 1) {
					for ( var n = 0; n < getQzfws(bbzxlArr[1]).length; n++) {// 
						var code2 = getQzfws(bbzxlArr[1])[n].split(":")[0];
						if (depth > 2) {
							for ( var k = 0; k < getQzfws(bbzxlArr[2]).length; k++) {// 
								var code3 = getQzfws(bbzxlArr[2])[k].split(":")[0];
								sTd += "<td name='" + ZS + FGF_BS + code1
										+ FGF_BS + code2 + FGF_BS + code3
										+ "'>";
								sTd += "0";
								sTd += "</td>";
							}
						} else {
							sTd += "<td name='" + ZS + FGF_BS + code1 + FGF_BS
									+ code2 + "'>";
							sTd += "0";
							sTd += "</td>";
						}
					}
				} else {
					sTd += "<td name='" + ZS + FGF_BS + code1 + "'>";
					sTd += "0";
					sTd += "</td>";
				}
			}
		}
	}
	return sTd;
}

function tjlTdDg() {

}

/*
 * 得到报表列名称
 */
function getBblName(key, code) {
	var name = "";
	var qzfws = getQzfws(key);
	for ( var i = 0; i < qzfws.length; i++) {
		var qzfwCode = qzfws[i].split(":")[0];
		if (qzfwCode == code) {
			name = qzfws[i].split(":")[1];
			break;
		}
	}
	if (name == null || name == "null" || name == "") {
		name = code;
	}
	if(name == WZ_EN){
		name = WZ_CN;
	}
	return name;
}

/*
 * 得到取值范围
 */
function getQzfw(zdmc) {
	var qzfw = "";
	qzfw = bblqzfw[zdmc];
	if (qzfw != "") {
		qzfw += "," + WZ_EN + ":" + WZ_CN;// 默认增加一未知项
	}
	return qzfw;
}

/*
 * 得到取值范围数组
 */
function getQzfws(zdmc) {
	var qzfw = getQzfw(zdmc);
	var qzfws = "";
	if (qzfw != null) {
		qzfws = qzfw.split(",");
	}
	return qzfws;
}


/*
 * 得到取值范围中某一值名称
 */
function getQzfwMc(zdmc,qzfwDm) {
	var qzfwMc = qzfwDm;
	if(qzfwMc == WZ_EN){
		qzfwMc = WZ_CN;
	}
	var qzfwStrs = bblqzfw[zdmc];
	if(qzfwStrs == null || qzfwStrs == ""){
		return qzfwMc;
	}
	var qzfwStrArr = qzfwStrs.split(",");
	if(qzfwStrArr == null || qzfwStrArr == ""){
		return qzfwMc;
	}
	for ( var i = 0; i < qzfwStrArr.length; i++) {
		var o = qzfwStrArr[i].split(":");
		var curQzfwDm = o[0];
		var curQzfwMc = curQzfwDm;
		if(o.length > 1){
			curQzfwMc = o[1];
		}
		if(curQzfwDm == qzfwDm){
			qzfwMc = curQzfwMc;
			break;
		}
	}
	return qzfwMc;
}


/*
 * 得到取值范围中某一项的代码集合
 */
function getQzfwObj(zdmc) {
	var qzfwJson = {};
	var qzfwDms = "";
	var qzfwDmDhs = "";
	var qzfwMcs = "";
	var qzfwStrs = bblqzfw[zdmc];
	if(qzfwStrs == null || qzfwStrs == ""){
		return qzfwJson;
	}
	var qzfwStrArr = qzfwStrs.split(",");
	if(qzfwStrArr == null || qzfwStrArr == ""){
		return qzfwJson;
	}
	for ( var j = 0; j < qzfwStrArr.length; j++) {
		var qzfwOneArr = qzfwStrArr[j].split(":");
		if(j > 0){
			qzfwDms += ",";
			qzfwDmDhs += ",";
			qzfwMcs += ",";
		}
		qzfwDms += qzfwOneArr[0];
		qzfwDmDhs += "'" + qzfwOneArr[0] + "'";
		qzfwMcs += qzfwOneArr[1];
	}
	qzfwJson.dms = qzfwDms;
	qzfwJson.mcs = qzfwMcs;
	qzfwJson.dmdhs = qzfwDmDhs;
	return qzfwJson;
}



/*
 * 得到取值范围条数
 */
function getQzfwTs(zdmc) {
	var qzfws = getQzfws(zdmc);
	return qzfws.length;
}

function getBblqzfw(){
	return bblqzfw;
}

/*
 * TD的命名
 */
function getTdName(o, prefex) {
	if (prefex == null) {
		prefex = ZS;
	}
	if (depth == 0) {
		return prefex;
	}
	var sName = prefex + FGF_BS;
	var flag = false;
	for ( var j = 0; j < bbzxlArr.length; j++) {// 显示查询条件中设置的纵向列字段
		var bbzxlKey = bbzxlArr[j];
		var bbzxlCode = o[bbzxlKey] || o[bbzxlKey.toLowerCase()]
				|| o[bbzxlKey.toUpperCase()];
		if (flag) {
			sName += FGF_BS;
		} else {
			flag = true;
		}
		sName += bbzxlCode;
	}
	return sName;
}

/*
 * TR的命名
 */
function getTrId(o) {
	var sName = "";
	if (hxlNum == 0) {// 横向列为空
		sName = DTJLH;
	} else {
		var flag = false;
		for ( var j = 0; j < bbhxlArr.length; j++) {// 显示查询条件中设置的横向列字段
			var bbhxlKey = bbhxlArr[j];
			var bbhxlCode = o[bbhxlKey] || o[bbhxlKey.toLowerCase()]
					|| o[bbhxlKey.toUpperCase()];
			if(bbhxlCode === undefined){
				bbhxlCode = WZ_EN;
			}
			if (flag) {
				sName += FGF_BS;
			} else {
				flag = true;
			}
			sName += bbhxlCode;
		}
	}

	return sName;
}

/*
 * 报表列，根据字段名称获取字段说明
 */
function getZdsmFromZdmc(zdmc) {
	var zdsm = "";
	for ( var i = 0; i < bblList.length; i++) {// 
		var o = bblList[i];
		if (o['zdmc'] == zdmc) {
			zdsm = o['zdsm'];
			break;
		}
	}
	if (zdsm == "") {
		zdsm = zdmc;
	}
	return zdsm;
}

/*
 * 特殊项，根据字段名称获取字段说明
 */
function getTsxZdsmFromZdmc(tsxmc) {
	var tsxsm = "";
	for ( var i = 0; i < tsxList.length; i++) {// 
		var o = tsxList[i];
		if (o['tsxmc'] == tsxmc) {
			tsxsm = o['tsxsm'];
			break;
		}
	}
	if (tsxsm == "") {
		tsxsm = tsxmc;
	}
	return tsxsm;
}

/**
 * 设置按钮
 * 
 * @return
 */
function setButton() {
	var btn_dc = jQuery("#btn_dc");// 导出
	var btn_dy = jQuery("#btn_dy");// 打印
	var btn_tj = jQuery("#btn_tj");// 生成图表
	//var btn_cx = jQuery("#btn_cx");// 使用说明
	
	if (btn_dc != null) {
		btn_dc.click(function() {
			jQuery(TBODY + " td[name^='"+ZS+"'],td[name^='"+ZXLHJ +"'],td[name^='"+HXLHJ + FGF_BS + ZS+"']").each(function(index){
				var value = jQuery(this).text();
				jQuery(this).html(value);
			});
			jQuery("#tableHtml").val(jQuery("#tjlbDiv").html());
			jQuery("#form1").submit();
			//包含总数列，在总数列数值上加超链接
			//setZsHref();
			//setZsHrefClick();
		});
	}

	if (btn_dy != null) {
		btn_dy.click(function() {
			if (beforePrint()) {
				//浏览器判断
				if (window.ActiveXObject || "ActiveXObject" in window){
					if(wb){
						try{
							wb.execwb(7,1);
						}catch(ex){
							window.print();
							if(window.console){
								window.console.debug("当前版本IE浏览器不支持WebBrowser控件!")
							}
						}
					}else{
						window.print();
					}
				}else{
					window.print();
				}
				afterPrint();
			}
		});
	}

	if (btn_tj != null) {
		btn_tj.click(function() {
			createTb();
		});
	}

	
//	if(btn_cx != null){
//		btn_cx.click(function() {
//			var title = "使用说明";
//			var url = _path + "/niutal/tjcx/tjbb/tjlbBzsm.zf";
//			$.showDialog(url,title ,$.mergeObj(modifyConfig,{"width":"1024px", "height":"580px"}));
//		});
//	}
	
	jQuery("#buttonbox").show();
}

// 打印之前隐藏不想打印出来的信息
function beforePrint() {
	var flag = false;
	try {
		pageSetupNull();
		flag = true;
	} catch (e) {
		var errorMsg = e.message + "\r" + "请设置:IE选项->安全->Internet->"
				+ "ActiveX控件和插件" + "\r"
				+ "对未标记为可安全执行脚本的ActiveX的控件初始化并执行脚本->允许/提示";
		$.alert(errorMsg);
	}
	if (flag) {
		jQuery("#tjlbDiv").css("overflow-x","initial");
		jQuery("#tjlbGnszq").hide();
	}
	return flag;
}
// 打印之后将隐藏掉的信息再显示出来
function afterPrint() {
	jQuery("#tjlbDiv").css("overflow-x","scroll");
	jQuery("#tjlbGnszq").show();
}

// 设置网页打印的页眉页脚为空，针对IE
function pageSetupNull() {
	if (typeof (ActiveXObject) == "undefined") {
		return;
	}
	var HKEY_Root, HKEY_Path, HKEY_Key;
	HKEY_Root = "HKEY_CURRENT_USER";
	HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";

	var Wsh = new ActiveXObject("WScript.Shell");
	HKEY_Key = "header";
	Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
	HKEY_Key = "footer";
	Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
	HKEY_Key = "margin_left"
	Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0"); // 键值设定--左边边界

	HKEY_Key = "margin_top"
	Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0"); // 键值设定--上边边界

	HKEY_Key = "margin_right"
	Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0"); // 键值设定--右边边界

	HKEY_Key = "margin_bottom"
	Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0"); // 键值设定--下边边界
}
