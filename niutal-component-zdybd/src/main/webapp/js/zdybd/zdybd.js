
var CONTENT_ID = "content";// 内容显示div
var NAVIGATION_ID = "navigation";// 导航栏div
var ZDYBD_NAVIGATION_ID = "zdybd_navigation";// 导航栏div

var PREFL_MKGS = 7;// 显示模块个数

var PREFL = "zdybdfl_";// 命名前缀，自定义表单分类
var PREFL_UL = "zdybdfl_ul_";// 命名前缀，自定义表单分类
var PREFL_LI = "zdybdfl_li_";// 命名前缀，自定义表单分类
var PREFL_HREF = "zdybdfl_href_";// 命名前缀，自定义表单分类
var PRECON_TABLE = "zdybdcon_table_";// 命名前缀，自定义表单内容
var PRECON_TH = "zdybdcon_th_";// 命名前缀，自定义表单内容
var PRECON_TD = "zdybdcon_td_";// 命名前缀，自定义表单内容
var PRECON_XGQ_TD = "zdybdconxgq_td_";// 命名前缀，自定义表单内容
var PRECON_XGQZ_ = "zdybdxgqz_";//修改前值
var PRECON_XGHZ_ = "zdybdxghz_";//修改后值
var PRECON_XGQH_TR = "zdybdconxgqh_tr";// 命名前缀，自定义表单内容
var PRECON_MORE_TR = "zdybdcon_more_tr_";// 命名前缀，自定义表单内容

var PRECON_XGQ_TIP = "zdybdxgqtip_";//修改前
var PRECON_XGH_TIP = "zdybdxghtip_";//修改后


var flszList;// 分类设置列表
var zddyList;// 字段定义列表
var valueJson;// 值
var valueXgqJson;// 值

var zdybdCurPzlx;//自定义表单当前配置类型

var moreUpdateTrJson = {};// 多条修改记录，增加一行
var moreUpdateZdJson = [];// 多条修改记录，字段

// //////////保存///////////////////////////////
var flszListObj = {};// 分类设置列表
var zddyListObj = {};// 字段定义列表
var valueJsonObj = {};// 值
var valueXgqJsonObj = {};// 值

/**
 * 初始化调用方法
 * 
 * @param gndm
 * @param param
 * @param otherParams
 * @return
 */
function zdybdInit(gndm, param, otherParams) {
	CONTENT_ID = "content" + "_" + gndm;
	
 jQuery("#" + CONTENT_ID).hide();
	valueJson = param;
	valueJsonObj[gndm] = valueJson;
	queryFl(gndm);

 jQuery("#" + CONTENT_ID).show();

}


/**
 * 初始化调用方法
 * 
 * @param gndm
 * @param param
 * @param otherParams
 * @return
 */
function zdybdXgqhInit(gndm, param, xghParam) {
	CONTENT_ID = "content" + "_" + gndm;
	
// jQuery("#" + CONTENT_ID).hide();
	valueXgqJson = param;
	valueXgqJsonObj[gndm] = valueXgqJson;
	valueJson = xghParam;
	valueJsonObj[gndm] = valueJson;

	queryFl(gndm);

// jQuery("#" + CONTENT_ID).show();

}



/**
 * 初始化调用方法
 * @return
 */
function zdybdInitByJson(jsonParam) {
	if(jsonParam == null){
		return;
	}
	var gndm = jsonParam["gndm"];
	var flszid = jsonParam["flszid"];
	var yhlx = jsonParam["yhlx"];
	var callback = jsonParam["callback"];

	valueXgqJson = jsonParam["valueXgqJson"];
	valueJson = jsonParam["valueJson"];
	
	zdybdCurPzlx = jsonParam["pzlx"];

	if(gndm == null || gndm == ""){
		return;
	}
	
	var GNDMBS = gndm;
	if(flszid != null && flszid != ""){
		GNDMBS += "_" + flszid;
	}
	CONTENT_ID = "content" + "_" + GNDMBS;
	valueXgqJsonObj[GNDMBS] = valueXgqJson;
	valueJsonObj[GNDMBS] = valueJson;
	queryFlByJson(jsonParam);
	
	if(callback != null){
		callback();
	}
}


/*
 * 分类下字段查询
 * 
 * @return
 */
function queryFlByJson(jsonParam) {
	
	var gndm = jsonParam["gndm"];
	var flszid = jsonParam["flszid"];
	var yhlx = jsonParam["yhlx"];
	var pzlx = jsonParam["pzlx"];

	// 字段定义
	var url =  _path + "/zfxg/zdybd/zddy_getZddyList.html";
	jQuery.ajax( {
		type : "post",
		async : false,
		url : url,
		data : {
			gndm : gndm,
			flszid : flszid,
			yhlx : yhlx,
			pzlx : pzlx
		},
		dataType : "json",
		success : function(data) {
			zddyList = data;
		}
	});

	// 分类
	var url =  _path + "/zfxg/zdybd/flsz_getFlszList.html";
	jQuery.ajax( {
		type : "post",
		async : false,
		url : url,
		data : {
			gndm : gndm,
			flszid : flszid,
			yhlx : yhlx,
			pzlx : pzlx
		},
		dataType : "json",
		success : function(data) {
			flszList = data;
			if (flszList != null && flszList.length > 0) {
				if (jQuery("#" + CONTENT_ID).length > 0) {
					createConNavFirst();// 生成内容导航
					createCon();// 生成内容
					setValue();// 设置值
					navAA();// js特效
				}
			}
		}
	});
	
	
	
	var GNDMBS = gndm;
	if(flszid != null && flszid != ""){
		GNDMBS += "_" + flszid;
	}

	zddyListObj[GNDMBS] = zddyList;
	flszListObj[GNDMBS] = flszList;
}


/*
 * 分类查询
 * 
 * @return
 */
function queryFl(gndm) {

	// 字段定义
	var url =  _path + "/zfxg/zdybd/zddy_getZddyList.html";
	jQuery.ajax( {
		type : "post",
		async : false,
		url : url,
		data : {
			gndm : gndm
		},
		dataType : "json",
		success : function(data) {
			zddyList = data;
		}
	});

	// 分类
	var url =  _path + "/zfxg/zdybd/flsz_getFlszList.html";
	jQuery.ajax( {
		type : "post",
		async : false,
		url : url,
		data : {
			gndm : gndm
		},
		dataType : "json",
		success : function(data) {
			flszList = data;
			if (flszList != null && flszList.length > 0) {
				if (jQuery("#" + NAVIGATION_ID).length > 0) {
					createNav();// 生成导航
	}
	if (jQuery("#" + CONTENT_ID).length > 0) {
		createConNavFirst();// 生成内容导航
		createCon();// 生成内容
		setValue();// 设置值
		navAA();// js特效
	}
}
}
	});

	zddyListObj[gndm] = zddyList;
	flszListObj[gndm] = flszList;
}

/*
 * 根据功能代码，刷新公用变量
 */
function refreshParams(gndm){
	var jsonParam = {gndm:gndm};
	refreshParamsByJson(jsonParam);
}

/*
 * 根据功能代码，刷新公用变量
 */
function refreshParamsByJson(jsonParam){
	var gndm = jsonParam["gndm"];
	var flszid = jsonParam["flszid"];	
	var GNDMBS = gndm;
	if(flszid != null && flszid != ""){
		GNDMBS += "_" + flszid;
	}

	CONTENT_ID = "content" + "_" + GNDMBS;	

	valueJson = valueJsonObj[GNDMBS];
	valueXgqJson = valueXgqJsonObj[GNDMBS];
	flszList = flszListObj[GNDMBS];
	zddyList = zddyListObj[GNDMBS];
}

/**
 * 生成导航栏
 * @return
 */
function createNavigation(listArr,zdybdDwpyl) {
	jQuery("#" + ZDYBD_NAVIGATION_ID + " div.position_xxxx").remove();
	if(listArr == null || listArr.length == 0){
		return;
	}
	var sHtml = "";
	sHtml += "<div class='position_xxxx after' >";
	sHtml += "<ul class='list_xxxx'>";
	for ( var i = 0; i < listArr.length; i++) {
		var o = listArr[i];
		var dm = o["dm"];
		var mc = o["mc"];
		sHtml += "<li>";
		sHtml += "<a href='#"+PREFL_HREF+dm + "' class='smooth'>"+mc+"</a>";
		sHtml += "</li>";		
	}	
	sHtml += "</ul>";
	sHtml += "</div>";
	jQuery("#" + ZDYBD_NAVIGATION_ID).append(sHtml);
	navAA(zdybdDwpyl);// js特效
}

/*
 * 生成导航
 * 
 * @return
 */
function createNav() {
	var sHtml = "";
	sHtml += "<div class='position_xxxx after' >";
	sHtml += "<ul class='list_xxxx' id='" + PREFL_UL + "' >";
	sHtml += "</ul>";
	sHtml += "</div>";
	jQuery("#" + NAVIGATION_ID).append(sHtml);
	createNavFirst();// 生成一级导航
}

/*
 * 生成一级导航
 */
function createNavFirst() {
	var mkNum = 0;
	var sHtml = "";
	var sHtmlMore = "";
	for ( var i = 0; i < flszList.length; i++) {
		var o = flszList[i];
		var flszid = o.flszid;// 分类设置id
		var flflszid = o.flflszid;// 父类id
		var flmc = o.flmc;
		if (flflszid == null) {// 为顶级类别
			if (mkNum < PREFL_MKGS) {
				sHtml = "<li id='" + PREFL_LI + flszid + "'>";
				sHtml += "<a href='#" + PREFL_HREF + flszid
						+ "' class='smooth'>" + flmc + "</a>";
				sHtml += "</li>";
				jQuery("#" + PREFL_UL).append(sHtml);
				createNavSecond(flszid);// 生成二级导航
			} else {// 更多单独处理
				if (mkNum == PREFL_MKGS) {
					sHtml = "<li id='" + PREFL_LI + "more'>";
					sHtml += "<a href='javascript:;' class='smooth'>更多</a>";
					sHtml += "</li>";
					jQuery("#" + PREFL_UL).append(sHtml);
				}
				sHtmlMore += "<dd  style='text-align: left; text-indent: 1em;'>";
				sHtmlMore += "<a href='#" + PREFL_HREF + flszid
						+ "' class='smooth'>" + flmc + "</a>";
				sHtmlMore += "</dd>";
			}
			mkNum++;
		}
	}

	// more单独处理
	sHtmlMore = "<dl>" + sHtmlMore + "</dl>";
	sHtmlMore = "<div class='list_xxxx_downmenu' style='display: none;'>"
			+ sHtmlMore + "</div>";
	jQuery("#" + PREFL_LI + "more").append(sHtmlMore);
}

/*
 * 生成二级导航
 */
function createNavSecond(idFlag) {
	var sHtml = "";
	for ( var i = 0; i < flszList.length; i++) {
		var o = flszList[i];
		var flszid = o.flszid;// 分类设置id
		var flflszid = o.flflszid;// 父类id
		var flmc = o.flmc;// 分类名称
		if (flmc == null || flmc == "") {
			continue;
		}
		if (flflszid == idFlag) {// 为顶级类别
			sHtml += "<dd  style='text-align: left; text-indent: 1em;'>";
			sHtml += "<a href='#" + PREFL_HREF + flszid + "' class='smooth'>"
					+ flmc + "</a>";
			sHtml += "</dd>";
		}
	}
	if (sHtml != "") {
		sHtml = "<dl>" + sHtml + "</dl>";
		sHtml = "<div class='list_xxxx_downmenu' style='display: none;'>"
				+ sHtml + "</div>";
		jQuery("#" + PREFL_LI + idFlag).append(sHtml);
	}
}

/*
 * 生成内容导航
 * 
 * @return
 */
function createConNav() {
	var sHtml = "";
	sHtml += "<div class='position_xxxx after' >";
	sHtml += "<ul class='list_xxxx' id='nav_ul' >";
	sHtml += "</ul>";
	sHtml += "</div>";

	jQuery("#" + CONTENT_ID).append(sHtml);
	createConNavFirst();// 生成一级导航
}

/*
 * 生成一级内容导航
 */
function createConNavFirst() {
	var mkNum = 0;
	var sHtml = "";
	for ( var i = 0; i < flszList.length; i++) {
		var o = flszList[i];
		var flszid = o.flszid;// 分类设置id
		var flflszid = o.flflszid;// 父类id
		var flmc = o.flmc;// 分类名称
		var bdms = o.bdms;// 表单模式
		var bhmk = o.bhmk;// 包含模块
		var bdszz = o.bdszz;
		if (flflszid == null) {// 为顶级类别
			sHtml = "<div id='" + PREFL + flszid + "'>";
			if(bdms == "4"){
				var bdszzObj = eval("[{" + bdszz + "}]")[0];
				var src = bdszzObj["src"];			
				sHtml += "<input type='hidden' id='" + src + "' name='" + src
						+ "'>";
			}
			if (bhmk == "1") {// 模块级
				sHtml += "<h3 class='college_title' style='margin-bottom:5px;'>";
				sHtml += "<span class='title_name'>" + flmc + "</span>";
				sHtml += "</h3>";
			} else {// 非模块
				sHtml += "<table width='100%' border='0' style='margin-bottom: 5px;";
				if (flmc == null || flmc == "") {
					sHtml += "display:none;";
				}
				sHtml += "' class='formlist'>";
				sHtml += "<thead>";
				sHtml += "<tr onclick='' style='cursor: hand;'>";
				sHtml += "<th colspan='5'>";
				sHtml += "<span>" + flmc;
				if (bdms == "4") {// 多条修改模式
					sHtml += "&nbsp;&nbsp;<a class='name' href='javascript:;' onclick='addConMoreUpdateTr(\""
							+ flszid + "\");'>增加一行</a>";
				}
				sHtml += "</span>";
				sHtml += "</th>";
				sHtml += "</tr>";
				sHtml += "</thead>";
				sHtml += "</table>";
			}
			sHtml += "<a name='" + PREFL_HREF + flszid + "'></a>";
			sHtml += "</div>";
			jQuery("#" + CONTENT_ID).append(sHtml);
			createConNavSecond(flszid);// 生成二级导航
		}
	}
}

/*
 * 生成二级内容导航
 */
function createConNavSecond(idFlag) {
	var sHtml = "";
	for ( var i = 0; i < flszList.length; i++) {
		var o = flszList[i];
		var flszid = o.flszid;// 分类设置id
		var flflszid = o.flflszid;// 父类id
		var flmc = o.flmc;// 分类名称
		var bdms = o.bdms;// 表单模式
		var bdszz = o.bdszz;
		if (flflszid == idFlag) {// 为顶级类别
			sHtml = "<div id='" + PREFL + flszid + "'>";
			if(bdms == "4"){
				var bdszzObj = eval("[{" + bdszz + "}]")[0];
				var src = bdszzObj["src"];
				sHtml += "<input type='hidden' id='" + src + "' name='" + src
						+ "'>";
			}
			if (flmc != null && flmc != "") {
				sHtml += "<table width='100%' border='0' style='margin-bottom: 5px' class='formlist'>";
				sHtml += "<thead>";
				sHtml += "<tr onclick='' style='cursor: hand;'>";
				sHtml += "<th colspan='5'>";
				sHtml += "<span>" + flmc;
				if (bdms == "4") {// 多条修改模式
					sHtml += "&nbsp;&nbsp;<a class='name' href='javascript:;' onclick='addConMoreUpdateTr(\""
							+ flszid + "\");'>增加一行</a>";
				}
				sHtml += "</span>";
				sHtml += "</th>";
				sHtml += "</tr>";
				sHtml += "</thead>";
				sHtml += "</table>";
			}
			sHtml += "<a name='" + PREFL_HREF + flszid + "'></a>";
			sHtml += "</div>";
			jQuery("#" + PREFL + idFlag).append(sHtml);
		}
	}
}

/*
 * 生成内容
 * 
 * @return
 */
function createCon() {
	var sHtml = "";
	for ( var i = 0; i < flszList.length; i++) {
		var o = flszList[i];
		var flszid = o.flszid;// 分类设置id
		var flflszid = o.flflszid;// 父类id
		var flmc = o.flmc;// 分类名称
		var bdms = o.bdms;// 表单模式 1:单条记录模式:2:多条记录模式;3:功能自定义代码
		var bdls = o.bdls;// 表单列数 为表单整体字段列数设置，不包含照片列(由程序自动计算)
		var bdszz = o.bdszz;// 表单设置值
		var gnlx = o.gnlx;// 功能类型 1:增加,2:修改,3:查看
		if (bdms == "1" && bdls != null) {
			createConDtjl(o);
		} else if (bdms == "2") {
			createConMore(o);
		} else if (bdms == "3") {
			jQuery("#" + PREFL + flszid).append(jQuery("#" + bdszz).html());
		} else if (bdms == "4") {
			createConMoreUpdate(o);
		} else if (bdms == "5") {
			createConDtjlXgqh(o);
		}
	}
	createConDtjlZp();
	createConDtjlXshbh();// 显示合并行
}

/*
 * 单条记录模式
 */
function createConDtjl(flszObj) {
	var flszid = flszObj.flszid;// 分类设置id
	var sHtml = "";
	sHtml += "<table width='100%' border='0' style='margin-bottom: 5px' class='formlist' id='"
			+ PRECON_TABLE + flszid + "' >";
	sHtml += "<tbody >";
	sHtml += "</tbody>";
	sHtml += "</table>";
	jQuery("#" + PREFL + flszid).append(sHtml);
	createConDtjlTr(flszObj);// 生成单条记录模式tr
}

// 生成单条记录模式tr
function createConDtjlTr(flszObj) {
	var flszid = flszObj.flszid;// 分类设置id
	for ( var i = 0; i < zddyList.length; i++) {
		var o = zddyList[i];
		var curFlszid = o.flszid;// 分类设置id
		var bdmc = o.bdmc;// 表单名称
		var zd = o.zd;// 
		var zbwz = o.zbwz;
		var szls = o.szls;// 所占列数
		var zdlx = o.zdlx;// 字段类型
		var yxwk = o.yxwk;// 允许为空
		var yxxg = o.yxxg;// 允许修改
		if (zdlx == "1" || zdlx == "21" || (zdlx >= "50" && zdlx < "60")) {// 照片类型单独处理
			continue;
		}
		
		if (flszid != curFlszid || zbwz == null) {// 
			continue;
		}		
		var zbwzs = zbwz.split(",");
		var zbwzh = parseInt(zbwzs[0]);

		var colspanLen = 1;// 合并列数
		if (szls == null) {
			szls = 1;
		}
		szls = parseInt(szls);

		if (szls != null) {
			colspanLen = parseInt(szls) * 2  - 1;
		}
		
		if (yxwk == "0" && yxxg == "1" && zdlx != null && zdlx != "0") {
			bdmc = "<span class='red'>*</span>" + bdmc;
		}
		
		if(zbwzs.length > 1){
			var zbwzl = zbwzs[1];
			var tableLength = jQuery("#" + PRECON_TABLE + flszid + " tr").length;
			if (tableLength < zbwzh) {
				for ( var j = 0; j < zbwzh - tableLength; j++) {
					jQuery("#" + PRECON_TABLE + flszid + " tbody").append(
							createConDtjlTrHtml(flszObj));
				}
			}
			var _conTr = jQuery("#" + PRECON_TABLE + flszid + " tr:eq("
					+ (zbwzh - 1) + ")");// 当前行tr
			var _conTh = _conTr.find("th:eq(" + (zbwzl - 1) + ")");
			_conTh.attr("name", PRECON_TH + zd);
			_conTh.html(bdmc);
			var _conTd = _conTr.find("td:eq(" + (zbwzl - 1) + ")");
			_conTd.attr("name", PRECON_TD + zd);
			if (colspanLen > 1) {
				var oldColspanLen = _conTd.attr("colSpan");
				var newColspanLen = colspanLen;
				if (oldColspanLen != null && parseInt(oldColspanLen) > 1) {
					newColspanLen += parseInt(oldColspanLen);
				}
				_conTd.attr("colSpan", newColspanLen);
				for ( var k = 0; k < colspanLen - 1; k++) {
					_conTd.next().remove();
				}
			}
		}else{
			var _tbody = jQuery("#" + PRECON_TABLE + flszid + " tbody");
			
			var _lastTh = _tbody.find("tr:last th:not([name^='"+PRECON_TH+"'])");
			var lastThLength =  _lastTh.length;
			if(lastThLength == 0 || szls > lastThLength){
				if(szls > lastThLength){
					var _prevTd = _lastTh.first().prev();
					
					var prevTdCol = _prevTd.attr("colSpan");
					if (prevTdCol == null) {
						prevTdCol = 1;
					}
					prevTdCol += lastThLength * 2;
					
					_prevTd.attr("colSpan", prevTdCol);
					_lastTh.next().remove();
					_lastTh.remove();
					
				}
				_tbody.append(createConDtjlTrHtml(flszObj));
				_lastTh = _tbody.find("tr:last th:not([name^='"+PRECON_TH+"'])");
			}

			var _conTh = _lastTh.first();
			_conTh.attr("name", PRECON_TH + zd);
			_conTh.html(bdmc);
			var _conTd = _conTh.next();
			_conTd.attr("name", PRECON_TD + zd);
			
			if(szls > 1){				
				_conTd.attr("colSpan", colspanLen);
				for ( var k = 0; k < colspanLen - 1; k++) {
					_conTd.next().remove();
				}
			}
		}
	}
}

/*
 * 生成单条记录模式tr html
 */
function createConDtjlTrHtml(flszObj) {
	var key = "thtdkd";
	var bdls = flszObj.bdls;// 表单列数
	var bdszz = flszObj.bdszz;
	var thtdkdStrs = getJsonParam(bdszz,key);
	var thtdkdArr = null;
	if(thtdkdStrs != null && thtdkdStrs != ""){
		thtdkdArr = thtdkdStrs.split(",");
	}
	var thWidth = "";
	var tdWidth = "";
	if(thtdkdArr == null || thtdkdArr.length == 0){
		thWidth = "15%";
		tdWidth = "35%";
	}else if(thtdkdArr.length == 1){
		thWidth = thtdkdArr[0];
	}else if(thtdkdArr.length  >= 1 ){
		thWidth = thtdkdArr[0];
		tdWidth = thtdkdArr[1];
	}

	
	var sHtml = "";
	sHtml += "<tr>";
	for ( var i = 0; i < parseInt(bdls); i++) {
		sHtml += "<th";
		if(thWidth != ""){
			sHtml += " width='"+thWidth+"' ";
		}else if(thtdkdArr.length > i*2){
			sHtml += " width='"+thtdkdArr[i*2]+"' ";
		}
		sHtml += ">";
		sHtml += "&nbsp;";
		sHtml += "</th>";
		sHtml += "<td ";
		if(tdWidth != ""){
			sHtml += " width='"+tdWidth+"' ";
		}else if(thtdkdArr.length > i*2 + 1){
			sHtml += " width='"+thtdkdArr[i*2 + 1]+"' ";
		}
		sHtml += ">";
		sHtml += "&nbsp;";
		sHtml += "</td>";
	}
	sHtml += "</tr>";
	return sHtml;
}

/*
 * 多条记录模式
 */
function createConMore(flszObj) {
	var flszid = flszObj.flszid;// 分类设置id
	var bdszz = flszObj.bdszz;// 表单设置值
	if (bdszz == null || bdszz == "") {
		return;
	}
	var bdszzObj = eval("[{" + bdszz + "}]")[0];
	var src = bdszzObj["src"];
	var srcList = valueJson[src];
	var zdStr = bdszzObj["zd"];// 字段列表
	var zdmcStr = bdszzObj["zdmc"];// 字段名称列表
	var bdkdStr = bdszzObj["bdkd"];// 表单宽度
	var zdArr = zdStr.split(",");
	var zdmcArr = zdmcStr.split(",");
	var bdkdArr = "";
	if(bdkdStr != null){
		bdkdArr = bdkdStr.split(",");
	}
	var sHtml = "";
	sHtml += "<table width='100%' border='0' style='margin-bottom:5px' class='formlist'>";
	sHtml += "<tbody >";
	sHtml += "<tr>";
	for ( var i = 0; i < zdmcArr.length; i++) {
		sHtml += "<th";
		if(bdkdArr != null && bdkdArr.length > i && bdkdArr[i] != ""){
			sHtml += " width='"+bdkdArr[i]+"px' ";
		}
		sHtml += "><div align='center'>" + zdmcArr[i] + "</div>";
		sHtml += "</th>";
	}
	sHtml += "</tr>";

	if (srcList == null || srcList.length == 0) {
		sHtml += "<tr>";
		sHtml += "<td colSpan='" + zdmcArr.length + "'>";
		sHtml += "<div align='center'>暂无数据！</div>";
		sHtml += "</td>";
		sHtml += "</tr>";
	} else {
		for ( var i = 0; i < srcList.length; i++) {
			var o = srcList[i];
			sHtml += "<tr>";
			for ( var j = 0; j < zdArr.length; j++) {
				var value = o[zdArr[j]];
				if (value == null) {
					value = "&nbsp;";
				}
				sHtml += "<td align='center'>" + value + "</td>";
			}
			sHtml += "</tr>";
		}
	}
	sHtml += "</tbody>";
	sHtml += "</table>";
	jQuery("#" + PREFL + flszid).append(sHtml);
}

/*
 * 多条记录模式，修改模式
 */
function createConMoreUpdate(flszObj) {
	var flszid = flszObj.flszid;// 分类设置id
	var sHtml = "";
	sHtml += "<table width='100%' border='0' style='margin-bottom: 5px' class='formlist' id='"
			+ PRECON_TABLE + flszid + "' >";
	sHtml += "<tbody >";
	sHtml += "</tbody>";
	sHtml += "</table>";
	jQuery("#" + PREFL + flszid).append(sHtml);
	createConMoreUpdateTr(flszObj);// 生成多条记录修改模式tr
}

/*
 * 多条记录模式，增加一行
 */
function addConMoreUpdateTr(flszid) {
	var trHtml = moreUpdateTrJson[flszid];
	jQuery("#" + PRECON_TABLE + flszid + " tbody").append(trHtml);
}

// 生成多条记录修改模式tr
function createConMoreUpdateTr(flszObj) {
	var flszid = flszObj.flszid;// 分类设置id
	var bdszz = flszObj.bdszz;// 表单设置值

	var sHtml = "";
	var titleHtml = "";

	var flszZdJson = {};
	var zds = [];
	
	if (bdszz != null && bdszz != "") {
		var bdszzObj = eval("[{" + bdszz + "}]")[0];
		var src = bdszzObj["src"];
		var bzzd = bdszzObj["bzzd"];// 标志字段
		flszZdJson.src = src;
		flszZdJson.bzzd = bzzd;
		flszZdJson.flszid = flszid;
		flszZdJson.flmc = flszObj.flmc;
		flszZdJson.zds = zds;
		moreUpdateZdJson.push(flszZdJson);
	}

	for ( var i = 0; i < zddyList.length; i++) {
		var o = zddyList[i];
		var curFlszid = o.flszid;// 分类设置id
		var bdmc = o.bdmc;// 表单名称
		var zd = o.zd;// 
		var szz = o.szz;
		var szlx = o.szlx;
		var zbwz = o.zbwz;
		var bdkd = o.bdkd;
		var zdlx = o.zdlx;// 字段类型
		var yxwk = o.yxwk;// 允许为空
		var yxxg = o.yxxg;
		if (zdlx == "21") {// 照片类型单独处理
			continue;
		}
		var tdHtml = "";
		if (flszid == curFlszid) {// 			
			zds.push(o);
			if (zdlx == "1") {
				titleHtml += "<th style='display:none;'><div></div></th>";
			} else {
				titleHtml += "<th><div align='center'>" + bdmc + "</div></th>";
			}
			if(yxxg == "0"){
				tdHtml += createHiddenNoIdHtml(zd, "");
			}else{
				if (zdlx == "11") {
					o.includeId = false;
					tdHtml += createTextHtmlByObj(o);
				} else if (zdlx == "13") {
					tdHtml += createDateTextHtml(zd, szz, bdkd);
				} else if (zdlx == "1") {
					tdHtml += createHiddenNoIdHtml(zd, "");
				} else if (zdlx == "2") {
					o.includeId = false;
					tdHtml += createSelectHtmlByObj(o);
				} else if (zdlx == "3") {
					tdHtml += createRadioHtml(zd, szz);
				} else if (zdlx == "4") {
					tdHtml += createCheckboxHtml(zd, szz);
				}				
			}
			if (zdlx == "1") {
				sHtml += "<td style='display:none;'>" + tdHtml + "</td>";
			} else {
				sHtml += "<td align='center'>" + tdHtml + "</td>";
			}
		}
	}

	if (titleHtml != "") {
//		if (xs != true) {
//			titleHtml += "<th><div align='center'>操作</div></th>";
//		}
		titleHtml = "<tr>" + titleHtml + "</tr>";
		jQuery("#" + PRECON_TABLE + flszid + " tbody").append(titleHtml);
	}

	if (sHtml != "") {
//		if (xs != true) {
//			sHtml += "<td align='center'><a class='name' href='javascript:;' onclick='deleteTr(this)'>删除</a></td>";
//		}
		sHtml = "<tr  name='" + PRECON_MORE_TR + "'>" + sHtml + "</tr>";
		moreUpdateTrJson[flszid] = sHtml;
	}
}


/*
 * 单条记录模式，修改前后对比
 */
function createConDtjlXgqh(flszObj) {
	var flszid = flszObj.flszid;// 分类设置id
	var bdszz = flszObj.bdszz;
	
	var key = "thtdkd";
	var thtdkdStrs = getJsonParam(bdszz,key);
	var thtdkdArr = null;
	if(thtdkdStrs != null && thtdkdStrs != ""){
		thtdkdArr = thtdkdStrs.split(",");
	}
	var thWidth = "";
	var tdWidth = "";
	if(thtdkdArr != null && thtdkdArr.length >= 1){
		thWidth = thtdkdArr[0];
	}
	if(thtdkdArr != null && thtdkdArr.length >= 2){
		tdWidth = thtdkdArr[1];
	}
		
	key = "xgqhbs";
	var xgqhbsStrs = getJsonParam(bdszz,key);
	var xgqhbsArr = null;
	if(xgqhbsStrs != null && xgqhbsStrs != ""){
		xgqhbsArr = xgqhbsStrs.split(",");
	}
	var xgqbs = "修改前值";
	var xghbs = "修改后值";
	if(xgqhbsArr != null && xgqhbsArr.length >= 1){
		xgqbs = xgqhbsArr[0];
	}
	if(xgqhbsArr != null && xgqhbsArr.length >= 2){
		xghbs = xgqhbsArr[1];
	}
	
	var sHtml = "";
	sHtml += "<table width='100%' border='0' style='margin-bottom: 5px' class='formlist' id='"
			+ PRECON_TABLE + flszid + "' >";
	sHtml += "<thead>";
	sHtml += "<tr style='font-weight: bold'>";
	sHtml += "<td";
	if(thWidth != ""){
		sHtml += " width='"+thWidth+"' ";
	}
	sHtml += ">修改项</td>";
	sHtml += "<td";
	if(tdWidth != ""){
		sHtml += " width='"+tdWidth+"' ";
	}
	sHtml += ">"+xgqbs+"</td>";
	sHtml += "<td>"+xghbs+"</td>";
	sHtml += "</tr>";
	sHtml += "</thead>";
	sHtml += "</table>";
	jQuery("#" + PREFL + flszid).append(sHtml);
	
	var _table = jQuery("#" + PRECON_TABLE + flszid);

	for ( var i = 0; i < zddyList.length; i++) {
		var o = zddyList[i];
		var curFlszid = o.flszid;// 分类设置id
		var bdmc = o.bdmc;// 表单名称
		var zd = o.zd;// 
		var zbwz = o.zbwz;
		var zdlx = o.zdlx;// 字段类型
		var yxwk = o.yxwk;// 允许为空
		var yxxg = o.yxxg;// 允许修改
		if (flszid != curFlszid) {// 
			continue;
		}
		if(bdmc == null || bdmc == ""){
			continue;
		}
		if (yxwk == "0" && yxxg == "1" && zdlx != null && zdlx != "0") {
			bdmc = "<span class='red'>*</span>" + bdmc;
		}
		if(!_table.children().last().is("tbody")){
			sHtml = "<tbody></tbody>";
			_table.append(sHtml);
		}
		
		if(zdlx == "54"){
			sHtml = "<tr><td colspan='3'><span style='font-weight: bold'>"+bdmc+"</span></td></tr>";
			_table.find("tbody").last().append(sHtml);
		}else{
			sHtml = "<tr name='"+PRECON_XGQH_TR+"'>";
			sHtml += "<th name='" + PRECON_TH + zd + "'>"+bdmc+"</th>";
			sHtml += "<td name='" + PRECON_XGQ_TD + zd + "'>&nbsp;";
			sHtml += "<span name='"+PRECON_XGQ_TIP+zd + "' class='floatright' style='display:none;'><a href='javascript:;' title='点击可更正数据' onclick='zdybdGzsj(this);'><img src='" + _path+"/images/gz.png'/></a></span>";
			sHtml += "</td>";
			sHtml += "<td name='" + PRECON_TD + zd + "'>";
			sHtml += createConDtjlXgqhBd(o);
			sHtml += "<span name='"+PRECON_XGH_TIP+zd + "' style='display:none;'><a href='javascript:;' title='点击可撤销操作' onclick='zdybdCxsj(this);'>";
			sHtml += "<img src='" + _path+"/images/cx.png'/>";
			sHtml += "</a></span>";
			sHtml += "</td>";
			sHtml += "</tr>";
			_table.find("tbody").last().append(sHtml);
		}
	}
}

/*
 * 字段更正
 */
function zdybdGzsj(obj){
	var _xgqhTr = jQuery(obj).parents("tr[name^='"+PRECON_XGQH_TR+"']");
	var _obj = _xgqhTr.find("input[name^='"+PRECON_XGQZ_+"']");
	var xgqz = _obj.val();
	var zd = _obj.attr("name").substr(PRECON_XGQZ_.length);
	
	
	var flag = true;
	var obj = {};
	obj.zd = zd;
	obj.value = xgqz;
	try{
		flag = beforeXgqhDeal(obj);
	}catch(ex){}
	if(flag){
		resetZdybdZd(zd,xgqz);	
	}
	try{
		afterXgqhDeal(obj);
	}catch(ex){}	
}

/*
 * 字段撤销
 */
function zdybdCxsj(obj){
	var _xgqhTr = jQuery(obj).parents("tr[name^='"+PRECON_XGQH_TR+"']");
	var _obj = _xgqhTr.find("input[name^='"+PRECON_XGHZ_+"']");
	var xghz = _obj.val();
	var zd = _obj.attr("name").substr(PRECON_XGHZ_.length);
	resetZdybdZd(zd,xghz);
}

/*
 * 重置字段值
 */
function resetZdybdZd(curZd,value){
	if(curZd == null){
		return;
	}
	
	for ( var i = 0; i < zddyList.length; i++) {
		var o = zddyList[i];
		var zd = o.zd;// 
		/*
		 * 字段类型 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 13:日期文本框 21:照片
		 * 22:省市县选择 23:链接 24;学院专业班级 99:功能自定义
		 */
		var zdlx = o.zdlx;
		var szlx = o.szlx;
		var szz = o.szz;
		var flszid = o.flszid;
		var yxxg = o.yxxg;
		var bdms = o.bdms;
		if(curZd != zd){
			continue;
		}
		
		var zdValue = value;
		if (zdValue == null) {
			zdValue = "";
		}
		var _zdTd = jQuery("#"+PRECON_TABLE+flszid + " td[name='" + PRECON_TD + zd + "']");

		if (zdlx == "3") {
			jQuery(
					"#"+PRECON_TABLE+flszid + " [name='" + zd + "'][value='"
							+ zdValue + "']")
					.attr("checked", "checked");
		} else if (zdlx == "4") {
			if(zdValue != null){
				var arr = zdValue.split(",");
				jQuery.each(arr,function(index,value){
					value = jQuery.trim(value);
					jQuery(
							"#"+PRECON_TABLE+flszid + " [name='" + zd + "'][value='"
									+ value + "']")
							.attr("checked", "checked");
				});
			}
		} else if (zdlx == "22") {

		} else if (zdlx == "1" || zdlx == "2" || zdlx == "5"
				|| zdlx == "11" || zdlx == "13") {
			jQuery("#"+PRECON_TABLE+flszid + " td[name='" + PRECON_TD + zd + "'] [name='" + zd + "']").val(zdValue);
		} else {
		}

		break;
	}	
	setZdybdZdTdYs(curZd);
}


/*
 * 设置td样式
 */
function setZdybdZdTdYs(curZd){
	if(curZd == null || curZd == ""){
		return;
	}
	for ( var i = 0; i < zddyList.length; i++) {
		var o = zddyList[i];
		var zd = o.zd;// 
		/*
		 * 字段类型 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 13:日期文本框 21:照片
		 * 22:省市县选择 23:链接 24;学院专业班级 99:功能自定义
		 */
		var zdlx = o.zdlx;
		var szlx = o.szlx;
		var szz = o.szz;
		var flszid = o.flszid;
		var yxxg = o.yxxg;
		var bdms = o.bdms;
		if(curZd != zd){
			continue;
		}
		var _zdTd = jQuery("#"+PRECON_TABLE+flszid + " td[name='" + PRECON_TD + curZd + "']");

		var xgqz = jQuery("#"+PRECON_TABLE+flszid + " [name='" + PRECON_XGQZ_ + curZd + "']").val();

		var xghz = "";
		if (zdlx == "3") {
			xghz = _zdTd.find("[name='" + zd + "']:checked").val();
		} else if (zdlx == "4") {
			xghz = _zdTd.find("[name='" + zd + "']:checked").map(function() {
						  return jQuery(this).val();
					}).get().join(',');
		} else if (zdlx == "22") {

		}else {
			xghz = _zdTd.find("[name='" + zd + "']").val();
		}

		var _xgqTd = jQuery("#"+PRECON_TABLE+flszid + " td[name='" + PRECON_XGQ_TD + curZd + "']");

		var _topTd = _zdTd.parent().prev().find("td[name^='" + PRECON_TD + "']");
		
		if(xgqz != xghz){
			_zdTd.css("background-color","rgb(253, 216, 216)");
//			_xgqTd.css("border-right-color","red");
//			_topTd.css("border-bottom-color","red");
		}else{
			_zdTd.css("background-color","");
//			_xgqTd.css("border-right-color","#B0CBE0");
//			_topTd.css("border-bottom-color","#B0CBE0");
		}
		
		var oldZdValue = valueJson[zd];
		if(oldZdValue == null){
			oldZdValue = "";
		}
		if(oldZdValue == xghz){
			_zdTd.find("span[name^='" + PRECON_XGH_TIP + "']").hide();
		}else{
			_zdTd.find("span[name^='" + PRECON_XGH_TIP + "']").show();
		}
		
		break;
	}
}

/*
 * 生成修改前后字段表单
 */
function createConDtjlXgqhBd(o){
	var zd = o.zd;// 
	var zdlx = o.zdlx;// 字段类型
	var bdmc = o.bdmc;// 表单名称
	var szz = o.szz;// 设置值
	var szls = o.szls;// 所占列数
	var bdkd = o.bdkd;// 表单宽度
	var szlx = o.szlx;// 设置类型
	var flszid = o.flszid;// 分类设置id
	/*
	 * 字段类型 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 12:数字文本框 13:日期文本框 21:照片
	 * 22:省市县选择 23:链接 99:功能自定义
	 */
	var zdlx = o.zdlx;// 
	var yxwk = o.yxwk;// 允许为空
	var yxxg = o.yxxg;// 允许修改
	var sHtml = "";
	if(zdlx == "0" && szz != null){
		sHtml += szz;
	}else if (yxxg == "0") {
		sHtml += createHiddenHtmlByObj(o);
	} else {
		if (zdlx == "99" || zdlx == "21") {
			sHtml += jQuery("#" + szz).html();
			jQuery("#" + szz).remove();
		} else if (zdlx == "11") {
			sHtml += createTextHtmlByObj(o);
		} else if (zdlx == "12") {
			sHtml += createSzTextHtmlByObj(o);
		} else if (zdlx == "13") {
			sHtml += createDateTextHtmlByObj(o);
		} else if (zdlx == "1") {
			sHtml += createHiddenHtmlByObj(o);
		} else if (zdlx == "2") {
			sHtml += createSelectHtmlByObj(o);
		} else if (zdlx == "3") {
			sHtml += createRadioHtml(zd, szz);
		} else if (zdlx == "4") {
			sHtml += createCheckboxHtmlByObj(o);
		} else if (zdlx == "5") {
			sHtml += createTextareaHtmlByObj(o);
		} else if (zdlx == "6") {

		} else if (zdlx == "22") {// 22:省市县选择
		// sHtml += createSsxHtml(zd, szz);
		} else if (zdlx == "23") {

		}
	}
	return sHtml;
}


/*
 * 删除一行
 */
function deleteTr(obj) {
	jQuery(obj).parents("tr[name='" + PRECON_MORE_TR + "']").remove();
}

/*
 * 设置值
 * 
 * @return
 */
function setValue() {
	var flszFirstObj = flszList[0];
	var gnlx = flszFirstObj.gnlx;// 功能类型型 1:增加 2:修改 3:查看
	if (gnlx == "3") {
		if (valueJson != null) {
			for ( var i = 0; i < zddyList.length; i++) {
				var o = zddyList[i];
				var zd = o.zd;// 
				var zdlx = o.zdlx;
				var szz = o.szz;
				var zdValue = valueJson[zd];
				if (zdValue == null) {
					zdValue = "";
				}
				var _zdTd = jQuery("td[name='" + PRECON_TD + zd + "']");

				if (zdlx == "0") {
					if (_zdTd.length > 0 && szz != null) {
						_zdTd.html(szz + "&nbsp;");
					}
				}else if (zdlx == "22") {
//					if (zdValue == "") {
//						continue;
//					} else {
//						var ssxDms = getSsx(szz, zdValue);
//						if (ssxDms != null) {
//							zdValue = ssxDms['shmc'] + ssxDms['shimc']
//									+ ssxDms['ximc'];
//						}	
//						
//					}
					_zdTd.html(valueJson[zd+"mc"]);
				} else if (zdlx == "5") {
					if (_zdTd.length > 0) {
						zdValue = htmlJsEncode(zdValue);
						_zdTd.html(zdValue + "&nbsp;");
					}
				} else {
					if (_zdTd.length > 0) {
						_zdTd.html(zdValue + "&nbsp;");
					}
				}
			}
		}
	} else {
		setValueUpdateZd();// 字段设置
		setValueUpdateCheck();// 字段验证
		setValueUpdateTip();// 增加提示信息
		if (gnlx == "2") {
			setValueUpdate();// 字段赋值
			setValueUpdateMore();// 字段赋值,多条记录模式
			setValueXgqhUpdate();//字段赋值，修改前后
		}
	}
}

/*
 * 修改功能，字段赋值
 */
function setValueUpdate() {
	if (valueJson != null) {
		for ( var i = 0; i < zddyList.length; i++) {
			var o = zddyList[i];
			var zd = o.zd;// 
			/*
			 * 字段类型 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 13:日期文本框 21:照片
			 * 22:省市县选择 23:链接 24;学院专业班级 99:功能自定义
			 */
			var zdlx = o.zdlx;
			var szlx = o.szlx;
			var szz = o.szz;
			var yxxg = o.yxxg;
			var bdms = o.bdms;
			if (bdms != "1") {
				continue;
			}
			if (zdlx == "99") {
				continue;
			}
			
			var zdValue = valueJson[zd];
			if (zdValue == null) {
				zdValue = "";
			}
			if (yxxg == "0") {
				jQuery("#" + zd).val(zdValue);
				var _zdTd = jQuery("td[name='" + PRECON_TD + zd + "']");
				if (_zdTd.length > 0) {
					if (zdlx == "2" || zdlx == "3" || zdlx == "4") {
						_zdTd.prepend(getNameByDm(zdValue, szz));
					} else if (zdlx == "22") {
						if (zdValue != "") {
//							var ssxDms = getSsx(szz, zdValue);
//							if (ssxDms != null && ssxDms['shmc'] != null) {
//								_zdTd.prepend(ssxDms['shmc'] + ssxDms['shimc']
//										+ ssxDms['ximc']);
//							}
							
							_zdTd.append(valueJson[zd+'mc']);
						}

					} else {
						_zdTd.prepend("&nbsp;<span class='sjxs'>"+zdValue+"</span>");
					}
				}
			} else {
				if (zdlx == "3") {
					jQuery(
							"#" + CONTENT_ID + " [name='" + zd + "'][value='"
									+ zdValue + "']")
							.attr("checked", "checked");
				} else if (zdlx == "4") {
					if(zdValue != null){
						var arr = zdValue.split(",");
						jQuery.each(arr,function(index,value){
							value = jQuery.trim(value);
							jQuery(
									"#" + CONTENT_ID + " [name='" + zd + "'][value='"
											+ value + "']")
									.attr("checked", "checked");
						});
					}
				} else if (zdlx == "22") {
					if (zdValue != "") {
//						var ssxDms = getSsx(szz, zdValue);
//						if (ssxDms != null) {
//							var shdm = ssxDms['shdm'];
//							var shidm = ssxDms['shidm'];
//							var xidm = ssxDms['xidm'];
//							if(shdm != ""){
//								jQuery("#" + zd + "_province").val(shdm);
//								jQuery("#" + zd + "_province").change();
//							}
//							if(shidm != ""){
//								jQuery("#" + zd + "_city").val(shidm);
//								jQuery("#" + zd + "_city").change();
//							}
//							if(xidm != ""){
//								jQuery("#" + zd + "_locale").val(xidm);
//								jQuery("#" + zd + "_locale").change();
//							}
//						}
						jQuery("#" + zd).val(zdValue);
						jQuery("#_" + zd).val(valueJson[zd+'mc']);
					}
				} else if (zdlx == "1" || zdlx == "2" || zdlx == "5"
						|| zdlx == "11" || zdlx == "13") {
					jQuery("#" + zd).val(zdValue);
				} else {
					var _zdTd = jQuery("td[name='" + PRECON_TD + zd + "']");
					_zdTd.append("&nbsp;<span class='sjxs'>"+zdValue + "</span>"+ "&nbsp;");
				}
			}
		}
	}
}


/*
 * 修改前后，字段赋值
 */
function setValueXgqhUpdate() {
	if (valueJson == null) {
		return;
	}
	for ( var i = 0; i < zddyList.length; i++) {
		var o = zddyList[i];
		var zd = o.zd;// 
		/*
		 * 字段类型 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 13:日期文本框 21:照片
		 * 22:省市县选择 23:链接 24;学院专业班级 99:功能自定义
		 */
		var zdlx = o.zdlx;
		var szlx = o.szlx;
		var szz = o.szz;
		var flszid = o.flszid;
		var yxxg = o.yxxg;
		var bdms = o.bdms;
		if (bdms != "5") {
			continue;
		}
		if (zdlx == "99") {
			continue;
		}
		
		var zdValue = valueJson[zd];
		if (zdValue == null) {
			zdValue = "";
		}
		var _zdTd = jQuery("#"+PRECON_TABLE+flszid + " td[name='" + PRECON_TD + zd + "']");

		if (yxxg == "0") {
			jQuery("#"+PRECON_TABLE+flszid + " [name='" + zd + "']").val(zdValue);
			if (_zdTd.length > 0) {
				if (zdlx == "2" || zdlx == "3" || zdlx == "4") {
					_zdTd.prepend(getNameByDm(zdValue, szz));
				} else if (zdlx == "22") {						
				} else {
					_zdTd.prepend("&nbsp;<span class='sjxs'>"+zdValue+"</span>");
				}
			}
		} else {
			if (zdlx == "3") {
				jQuery(
						"#"+PRECON_TABLE+flszid + " [name='" + zd + "'][value='"
								+ zdValue + "']")
						.attr("checked", "checked");
			} else if (zdlx == "4") {
				if(zdValue != null){
					var arr = zdValue.split(",");
					jQuery.each(arr,function(index,value){
						value = jQuery.trim(value);
						jQuery(
								"#"+PRECON_TABLE+flszid + " [name='" + zd + "'][value='"
										+ value + "']")
								.attr("checked", "checked");
					});
				}
			} else if (zdlx == "22") {
				
			} else if (zdlx == "1" || zdlx == "2" || zdlx == "5"
					|| zdlx == "11" || zdlx == "13") {
				jQuery("#"+PRECON_TABLE+flszid + " td[name='" + PRECON_TD + zd + "'] [name='" + zd + "']").val(zdValue);
			} else {
				_zdTd.append("&nbsp;<span class='sjxs'>"+zdValue+"</span>"+ "&nbsp;");
			}
		}
		
		_zdTd.append("<input type='hidden' name='"+PRECON_XGHZ_ + zd +"' value='"+zdValue+"'>");

		
		//修改前值设置
		var zdXgqValue = null;
		if(valueXgqJson != null){
			zdXgqValue = valueXgqJson[zd];
		}
		if (zdXgqValue == null) {
			zdXgqValue = "";
		}
		var _xgqZdTd = jQuery("#"+PRECON_TABLE+flszid + " td[name='" + PRECON_XGQ_TD + zd + "']");
		var zdValueView = zdXgqValue;			
		if (zdlx == "2" || zdlx == "3" || zdlx == "4") {
			zdValueView = getNameByDmIncludeNull(zdXgqValue, szz);
			if(zdValueView == ""){
				var _gzA = jQuery("span[name='"+PRECON_XGQ_TIP+zd + "'] a");
				_gzA.attr("title","该值在右侧列表中不存在");
				_gzA.attr("onclick","");
				_gzA.find("img").attr("src",_path+"/images/gz_gray.png");
				zdValueView = zdXgqValue;
				
				//删除修改后的撤销按钮
				jQuery("#"+PRECON_TABLE+flszid + " span[name='"+PRECON_XGH_TIP+zd + "'] ").remove();
			}
		}
		var sHtml = "";
		sHtml += "&nbsp;<span class='sjxs'>"+zdValueView+"</span>";
		sHtml += "";
		sHtml += "<input type='hidden' name='"+PRECON_XGQZ_ + zd +"' value='"+zdXgqValue+"'>";
		_xgqZdTd.prepend(sHtml);
		
		if (yxxg != "0") {
			//显示提示
			var _table = jQuery("#"+PRECON_TABLE+flszid);
			if(zdValue != zdXgqValue){
				_table.find("span[name='" + PRECON_XGQ_TIP + zd + "']").show();
				_table.find("span[name='" + PRECON_XGH_TIP + zd + "']").show();
			}else{
				_table.find("span[name='" + PRECON_XGH_TIP + zd + "']").show();
			}
			
			//设置事件修改
			_zdTd.find("[name='"+zd+"']").change(function(){
				setZdybdZdTdYs(jQuery(this).attr("name"));
			});
			_zdTd.find("[name='"+zd+"']").change();
			_zdTd.find("[name='"+zd+"']").blur(function(){
				setZdybdZdTdYs(jQuery(this).attr("name"));
			});
		}
		
	}
}

/*
 * 修改功能，字段赋值，多条记录模式
 */
function setValueUpdateMore() {
	if (valueJson != null) {
		for ( var i = 0; i < flszList.length; i++) {
			var o = flszList[i];
			var flszid = o.flszid;// 分类设置id
			var flmc = o.flmc;// 分类名称
			var bdms = o.bdms;// 表单模式
			var bdszz = o.bdszz;//
			if (bdms != "4") {
				continue;
			}
			var bdszzObj = eval("[{" + bdszz + "}]")[0];
			var src = bdszzObj["src"];
			var srcList = valueJson[src];
			if (srcList == null || srcList == "" || srcList == "null") {
				continue;
			}
			if( typeof srcList =='string' && srcList.constructor==String){
				srcList = eval(srcList);
			}
			for ( var j = 0; j < srcList.length; j++) {
				var srcObj = srcList[j];
				setValueUpdateMoreTr(flszid, srcObj);
			}
		}
	}
}

/*
 * 多条记录模式，一行赋值
 */
function setValueUpdateMoreTr(flszid, srcObj) {
	addConMoreUpdateTr(flszid);// 增加一行
	for ( var i = 0; i < moreUpdateZdJson.length; i++) {
		var o = moreUpdateZdJson[i];
		if (flszid != o.flszid) {
			continue;
		}
		var zds = o.zds;
		for ( var j = 0; j < zds.length; j++) {
			var zdObj = zds[j];
			var zd = zdObj.zd;
			var zdlx = zdObj.zdlx;
			var szz = zdObj.szz;
			var _zd = jQuery("#" + PRECON_TABLE + flszid + " tbody tr:last").find(
					"[name='" + zd + "']");
			var dm = srcObj[zd];
			if(_zd.attr("type") == "hidden"){
				var mc = "";
				if(zdlx == "2"){
					mc = getNameByDm(dm, szz);
				}else{
					mc = dm;
				}
				_zd.after(mc);
			}
			_zd.val(dm);
		}
	}
}

/*
 * //字段设置
 */
function setValueUpdateZd() {
	for ( var i = 0; i < zddyList.length; i++) {
		var o = zddyList[i];
		var zd = o.zd;// 
		var zdlx = o.zdlx;// 字段类型
		var bdmc = o.bdmc;// 表单名称
		var szz = o.szz;// 设置值
		var szls = o.szls;// 所占列数
		var bdkd = o.bdkd;// 表单宽度
		var szlx = o.szlx;// 设置类型
		var flszid = o.flszid;// 分类设置id
		/*
		 * 字段类型 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 12:数字文本框 13:日期文本框
		 * 21:照片 22:省市县选择 23:链接 99:功能自定义
		 */
		var zdlx = o.zdlx;// 
		var yxwk = o.yxwk;// 允许为空
		var yxxg = o.yxxg;// 允许修改
		var bdms = o.bdms;
		if(bdms != "1"){
			continue;
		}
		
		if(zdlx == "1"){// 隐藏域
			var hidHtml = createHiddenHtml(zd);
			
			jQuery("#" + PREFL + flszid).prepend(hidHtml);
		}
		var _zdTd = jQuery("td[name='" + PRECON_TD + zd + "']");
		if (_zdTd.length > 0) {
			var sHtml = "";
			if(zdlx == "0" && szz != null){
				sHtml += szz;
			}else if (yxxg == "0") {
				if(zdybdCurPzlx == null || zdybdCurPzlx.indexOf("ckpz") == -1){
					sHtml += createHiddenHtml(zd, szz);
				}
			} else {
				if (zdlx == "99" || zdlx == "21") {
					sHtml += jQuery("#" + szz).html();
					jQuery("#" + szz).remove();
				} else if (zdlx == "11") {
					var obj = o;
					obj["includeId"] = true;
					sHtml += createTextHtmlByObj(obj);
				} else if (zdlx == "12") {
					sHtml += createSzTextHtml(zd, szz);
				} else if (zdlx == "13") {
					sHtml += createDateTextHtml(zd, szz);
				} else if (zdlx == "1") {
					sHtml += createHiddenHtml(zd, szz);
				} else if (zdlx == "2") {
					sHtml += createSelectHtml(zd, szz, szlx,bdkd);
				} else if (zdlx == "3") {
					sHtml += createRadioHtml(zd, szz);
				} else if (zdlx == "4") {
					sHtml += createCheckboxHtml(zd, szz);
				} else if (zdlx == "5") {
					sHtml += createTextareaHtml(zd, szz,bdkd);
				} else if (zdlx == "6") {

				} else if (zdlx == "22") {// 22:省市县选择
					sHtml += createSsxHtml(zd, szz);
				} else if (zdlx == "23") {

				}
			}
			_zdTd.html(sHtml);
			if (zdlx == "22") {// 22:省市县选择
				//setSsxLiandong(zd, szz);
				var type = "xian";
				if(szz != null){
					type = szz;
				}
				initBigCombo({targetid:"_"+zd, setText:"_"+zd,setValue:zd,tipText:"",selectType:"xzqType",xzqStyle:type});
			}
		}
	}
}

/*
 * 保存，增加字段的验证信息
 */
function setValueUpdateCheck() {
	for ( var i = 0; i < zddyList.length; i++) {
		var o = zddyList[i];
		var zd = o.zd;
		var bdmc = o.bdmc;
		var szlx = o.szlx;
		var szz = o.szz;
		var yxwk = o.yxwk;
		var flszid = o.flszid;
		/*
		 * 字段类型 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 12:数字文本框 13:日期文本框
		 * 21:照片 22:省市县选择 23:链接 99:功能自定义
		 */
		var zdlx = o.zdlx;// 
		/*
		 * 设置类型 当“字段类型”为：2:下拉框、3:单选框、4:复选框三种类型时，来源类型字段有效 1.固定值，格式为：1:男,2:女
		 * 2:数据库取值,“表名:代码,名称”, 3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型；
		 * 字段类型为输入框时，做为验证类型配置： 11:数字、字母、下划线 12:仅字母 13:仅数字 14:小数 21:邮箱 22:电话号码
		 * 23:手机号码 24:身份证号 25:邮编 99:正则表达式
		 * 
		 */
		if (zdlx == "11" || zdlx == "5") {
			var tsxs = bdmc;
			var _zd = jQuery("#"+PRECON_TABLE+ flszid+" [name='" + zd + "']");
			if (szlx == "13") {
				_zd.blur(function() {
					limitSz(this);
				});
				_zd.keyup(function() {
					limitSz(this);
				});
			} else if (szlx == "22") {
				_zd.blur(function() {
					// checkDhhm(this, tsxs);
					});
			}
			var max = getMaxLength(szz);
			if (max > 0) {
				if (zdlx == "5") {
					var sHtml = "<br/><font color='red'>(限" + max
							+ "字以内)</font>";
				//	if (xs != true) {
						jQuery("#"+PRECON_TABLE+ flszid+" [name='" + PRECON_TH + zd + "']").append(sHtml);
				//	}
				} else {
					_zd.attr("maxlength", max);
				}
			}
		}
	}
}

/*
 * 增加提示信息
 */
function setValueUpdateTip() {
	for ( var i = 0; i < zddyList.length; i++) {
		var o = zddyList[i];
		var zd = o.zd;
		var zdsm = o.zdsm;
		/*
		 * 字段类型 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 12:数字文本框 13:日期文本框
		 * 21:照片 22:省市县选择 23:链接 99:功能自定义
		 */
		var zdlx = o.zdlx;// 
		var tslx = o["tslx"];
		if(zdsm == null || jQuery.trim(zdsm) == ""){
			continue;
		}
		
		var sHtml = "";
		
		if(tslx == "1"){
			sHtml += "<span style='color: #4a74a1;'>";
			sHtml += zdsm;
			sHtml += "</span>";
		}else if(tslx == "2"){
			sHtml += "<div style='color: #4a74a1;'>";
			sHtml += zdsm;
			sHtml += "</div>";
		}else{
			sHtml += "<div class='pop' >";
			sHtml += "<div class='pop_list' >";
			sHtml += "<div class='pop_list1'>";
			sHtml += "<p>"+zdsm+"</p>";
			sHtml += "</div>";
			sHtml += "<div class='pop_but'></div>";
			sHtml += "</div>";
			sHtml += "</div>";
		}
		if (zdlx == "2" || zdlx == "5" || zdlx == "11" || zdlx == "12" || zdlx == "13") {
			var _zd = jQuery("#" + zd).parents("td[name='"+PRECON_TD+zd+"']");
			_zd.append(sHtml);
		}
		
		if (zdlx == "3" || zdlx == "4") {
			var _zd = jQuery("#" + CONTENT_ID + " [name='" + zd + "']").parents("td[name='"+PRECON_TD+zd+"']");
			_zd.append(sHtml);
		}
	}
	
	jQuery(".pop").css({
	    "display": "inline-block",
	    "*display": "inline",
	    "*zoom": "1",
	    "width": "16px",
	    "height": "16px",
	    "background": "url("+_stylePath+"/images/blue/ico_79.gif) no-repeat",
	    "margin-left": "1px",
	    "cursor": "pointer",
	    "position": "relative"
	});

	
	jQuery(".pop .pop_list").css({
	    "position": "absolute",
	    "z-index": "3",
	    "top": "15px",
	    "right": "-5px",
	    "width": "390px",
	    "height": "100px",
	    "background":"url("+_stylePath+"/images/ico_payment_top.png) left top no-repeat",
	    "text-align":"left",
	    "display": "none"
	});
	
	jQuery(".pop .pop_list .pop_list1").css({
	    "margin-top": "19px",
	    "width": "370px",
	    "padding": "0px 10px",
	    "background": "url("+_stylePath+"/images/ico_paymentc_center.png) left top repeat-y"
	});
		
	
	jQuery(".pop .pop_list .pop_but").css({
	    "width": "390px",
	    "height": "8px",
	    "background": "url("+_stylePath+"/images/ico_payment_bot.png) left top no-repeat"
	});
	
	jQuery(".pop").hover(function() {
		jQuery(this).children(".pop_list").show();
		jQuery(this).css("z-index","11");
    },function(){
    	jQuery(this).children(".pop_list").hide();
    	jQuery(this).css("z-index","1");
	});
}


/*
 * 增加照片列
 */
function createConDtjlZp() {
	// zpzdObjArr
	var key = "rtkd";
	for ( var i = 0; i < zddyList.length; i++) {
		var sHtml = "";
		var o = zddyList[i];
		var zdlx = o.zdlx;
		var szlx = o.szlx;
		var szz = o.szz;
		var flszid = o.flszid;
		var szls = o.szls;
		var bdszz = o.bdszz;
		var bdms = o.bdms;
		if(bdms != "1"){
			continue;
		}
		if (szls == null || szls == "") {
			szls = 1;
		}

		if (zdlx == "21" || zdlx == "53") {
			sHtml += "<td rowspan='" + szls + "'";
			var tdLength = getJsonParam(bdszz,key);
			if(tdLength != null && tdLength != ""){
				sHtml += " width='" + tdLength + "'";
			}
			sHtml += ">";
			
			if(jQuery("#" + szz).length > 0){
				sHtml += jQuery("#" + szz).html();
			}
			jQuery("#" + szz).remove();
			sHtml += "</td>";
			jQuery("#" + PRECON_TABLE + flszid + ">tbody>tr:first").append(
					sHtml);

			jQuery(
					"#" + PRECON_TABLE + flszid + ">tbody>tr:gt("
							+ eval(parseInt(szls) - 1) + ")").find("td:last")
					.each(
							function(index) {
								var oldColspan = jQuery(this).attr("colSpan");
								if (oldColspan == null || oldColspan == "") {
									oldColspan = 1;
								}
								jQuery(this).attr("colSpan",
										eval(parseInt(oldColspan) + 1));
							});
		}
	}
}

/*
 * 显示合并行
 */
function createConDtjlXshbh() {
	var key = "lkd";
	for ( var i = 0; i < zddyList.length; i++) {
		var sHtml = "";
		var o = zddyList[i];
		var zdlx = o.zdlx;
		var szlx = o.szlx;
		var bdmc = o.bdmc;
		var flszid = o.flszid;
		var szls = o.szls;
		var zbwz = o.zbwz;
		var bdszz = o.bdszz;
		var bdms = o.bdms;
		if(bdms != "1"){
			continue;
		}
		if (szls == null || szls == "") {
			szls = 1;
		}
		var curWz = 0;
		if(zbwz != null){
			curWz = parseInt(zbwz) - 1;
		}
		sHtml += "<td rowspan='" + szls + "'";
		var tdLength = getJsonParam(bdszz,key);
		if(tdLength != null && tdLength != ""){
			sHtml += " width='" + tdLength + "'";
		}
		sHtml += ">";
		sHtml += bdmc;
		sHtml += "</td>";
		if (zdlx == "50") {
			jQuery("#" + PRECON_TABLE + flszid + ">tbody>tr:eq("+curWz+")").prepend(
					sHtml);
		}
	}
}

/*
 * 获取配置中的json格式值
 */
function getJsonParam(jsonStr,key){
	var value = "";
	if (jsonStr == null || jsonStr == "") {
		return value;
	}
	if(key == null){
		return jsonStr;
	}
	var jsonObj = eval("[{" + jsonStr + "}]")[0];
	value = jsonObj[key];
	return value;
}


/**
 * 自定义表单验证
 * 
 * @return
 */
function zdybdCheckByJson(jsonParam) {
	if(jsonParam == null){
		return;
	}
	var gndm = jsonParam["gndm"];
	var flszid = jsonParam["flszid"];
	
	var checkResult = zdybdCheckAllByJson(jsonParam);
	
	var errorTotal = checkResult.errorTotal;
	var errorData = checkResult.errorData;
	if(errorTotal > 0){// 包含错误字段
		// 若用alert弹出提示，直接弹出第一个错误即可
		alertInfo(errorData[0].message);
		return false;
	}
	return true;
}

/**
 * 自定义表单验证
 * 
 * @return
 */
function zdybdCheckAlert(gndm) {
	var checkResult = zdybdCheck(gndm);
	
	var errorTotal = checkResult.errorTotal;
	var errorData = checkResult.errorData;
	if(errorTotal > 0){// 包含错误字段
		// 若用alert弹出提示，直接弹出第一个错误即可
		alertInfo(errorData[0].message);
		return false;
	}
	return true;
}


/**
 * 自定义表单验证
 * 
 * @return
 */
function zdybdCheck(gndm) {	
	var jsonParam = {gndm:gndm};
	return zdybdCheckAllByJson(jsonParam);
}

/**
 * 自定义表单验证
 * 
 * @return
 */
function zdybdCheckAllByJson(jsonParam) {
	if(jsonParam == null){
		return;
	}
	refreshParamsByJson(jsonParam);
	
	var checkResult = {};
	checkResult.errorTotal = 0;
	checkResult.errorData = [];

	if(zddyList == null || zddyList.length == 0){
		return checkResult;
	}
	for ( var i = 0; i < zddyList.length; i++) {
		var o = zddyList[i];
		var zd = o.zd;
		var bdmc = o.bdmc;
		var zdlx = o.zdlx;
		var szlx = o.szlx;
		var szz = o.szz;
		var bdms = o.bdms;
		var yxxg = o.yxxg;
		var flszid = o.flszid;
		if(bdms == "4"){// 多条记录修改模式，单独处理
			continue;
		}
		if(zdlx == null || zdlx == "" || zdlx == "1" || zdlx == "0" || (zdlx >= "50" && zdlx < "60")){
			continue;
		}
		if(yxxg == "0"){
			continue;
		}
		var result = {};
		result.success = true;
		result = checkZdybdYxwk(o);// 非空验证
		if (!result.success) {
			checkResult.errorTotal++;
			checkResult.errorData.push(result);
		}
	//	var value = jQuery.trim(jQuery("#" + zd).val());
		var value = jQuery.trim(jQuery("#"+PRECON_TABLE+ flszid+" [name='" + zd + "']").val());

		if (zdlx != "5" && zdlx != "11" || value == "") {// 文本框，文本域
			continue;
		}

		result = checkZdybd(o);// 格式验证
		if (!result.success) {
			checkResult.errorTotal++;
			checkResult.errorData.push(result);
		}

		result = checkZdybdLength(o);// 长度验证
		if (!result.success) {
			checkResult.errorTotal++;
			checkResult.errorData.push(result);
		}
	}

	// 多条修改模式记录值处理
	dealConMoreValue(checkResult);
	
	// 自定义函数调用执行
	var flszFirstObj = flszList[0];
	var yzsz = flszFirstObj.yzsz;
	if (yzsz != null) {
		try {
			eval(yzsz + "()");
		} catch (ex) {
		}
	}
	return checkResult;
}

/*
 * /多条修改模式记录值处理
 */
function dealConMoreValue(checkResult) {
	var sHtml = "";

	for ( var i = 0; i < moreUpdateZdJson.length; i++) {
		var o = moreUpdateZdJson[i];
		var flszid = o.flszid;
		var flmc = o.flmc;
		var zds = o.zds;
		var src = o.src;
		var zdsArr = [];
		var dataJson = {};
		dataJson.data = zdsArr;
		jQuery("#" + PRECON_TABLE + flszid + " tbody tr:gt(0)").each(
				function(index) {
					var pretip = flmc + "：第" +(index + 1) + "行";

					var zdJson = {};
					var flag = false;
					for ( var j = 0; j < zds.length; j++) {
						var zdObj = zds[j];
						var zd = zdObj.zd;
						var zdlx = zdObj.zdlx;
						var bdmc = zdObj.bdmc;
						var value = jQuery.trim(jQuery(this).find("td:visible [name='" + zd + "']")
								.val());						
						// 格式验证///////////////////////////
						if(value == null){
							value = "";
						}
						var result = {};
						result.success = true;
						result.zd = zd;
						result = checkZdybdYxwk(zdObj,value);// 非空验证
						if (!result.success) {
							result.message= pretip + result.message;
							result.zd = zd;
							result.bdmc = bdmc;
							checkResult.errorTotal++;
							checkResult.errorData.push(result);
						}
						if ((zdlx == "5" || zdlx == "11") && value != "") {// 文本框，文本域
							result = checkZdybd(zdObj,value);// 格式验证
							if (!result.success) {
								result.message= pretip + result.message;
								result.zd = zd;
								result.bdmc = bdmc;
								checkResult.errorTotal++;
								checkResult.errorData.push(result);
							}
	
							result = checkZdybdLength(zdObj,value);// 长度验证
							if (!result.success) {
								result.message= pretip + result.message;
								result.zd = zd;
								result.bdmc = bdmc;
								checkResult.errorTotal++;
								checkResult.errorData.push(result);
							}
						}
						
						// ////////////////////////////////////////
						if(value != null && value != ""){
							flag = true;
						}
						zdJson[zd] = value;
					}
					if(flag){// 若一行值均为空，则不保存
						zdsArr.push(zdJson);
					}
				});
		jQuery("#" + src).val(JSON.stringify(dataJson));// 数据保存
	}
}

/**
 * 用户申请修改字段的显示
 * 
 * @param data
 * @return
 */
function zdybdXgzd(data) {
	if (data == null || data == "") {
		return;
	}
	zdybdXgzdDt(data);// 单条记录单个
	zdybdXgzdMore(data);// 多条记录修改模式
}

function zdybdXgzdDt(data) {
	for ( var i = 0; i < data.length; i++) {
		var o = data[i];
		var zd = o.zd;
		var zdz = o.zdz;
		var xgqz = o.xgqz;
		var _zdTd = jQuery("td[name='" + PRECON_TD + zd + "']");
		var oldValue = xgqz;
		if (oldValue == null) {
			oldValue = "";
		}
		var newValue = zdz;
		if (newValue == null) {
			newValue == "";
		}
		/*
		 * 字段类型 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 13:日期文本框 21:照片
		 * 22:省市县选择 23:链接 24;学院专业班级 99:功能自定义
		 */
		for ( var j = 0; j < zddyList.length; j++) {
			var zddyObj = zddyList[j];
			var zddyZd = zddyObj.zd;//
			var zddySzz = zddyObj.szz;
			var zddyZdlx = zddyObj.zdlx;//
			if (zddyZd == zd) {
				if (zddyZdlx == "2" || zddyZdlx == "3" || zddyZdlx == "4") {
					newValue = getNameByDm(zdz, zddySzz);
					oldValue = getNameByDm(oldValue, zddySzz);
				} else if (zddyZdlx == "22") {
					var ssxDms;
					if (newValue != null && newValue != "") {
						ssxDms = getSsx(zddySzz, newValue);
						if (ssxDms != null && ssxDms['shmc'] != null) {
							newValue = ssxDms['shmc'] + ssxDms['shimc']
									+ ssxDms['ximc'];
						}
					}
					if (oldValue != "") {
						ssxDms = getSsx(zddySzz, oldValue);
						if (ssxDms != null && ssxDms['shmc'] != null) {
							oldValue = ssxDms['shmc'] + ssxDms['shimc']
									+ ssxDms['ximc'];
						}
					}
				}
				break;
			}
		}
		if (oldValue == null) {
			oldValue = "";
		}
		if (newValue == null) {
			newValue = "";
		}
		if (oldValue != newValue) {
			if (oldValue == "") {
				oldValue = "空";
			}
			if (newValue == "") {
				newValue = "空";
			}
			var title = "修改前信息为：" + oldValue;
			var newTip = "<a href='javascript:void(0);' >";
			newTip += "<font color='red'  title='" + title + "' alt='" + title
					+ "' >" + newValue + "</font></a>";
			_zdTd.html(newTip);
		}
	}
}

/*
 * 多条记录修改模式
 */
function zdybdXgzdMore(data) {
	for ( var i = 0; i < moreUpdateZdJson.length; i++) {
		var o = moreUpdateZdJson[i];
		var src = o.src;
		for ( var j = 0; j < data.length; j++) {
			var xgzdObj = data[j];
			var xgzd = xgzdObj.zd;
			if(src != xgzd){
				continue;
			}
			zdybdXgzdMoreSz(o,xgzdObj);//
		}
	}
}

/*
 * 多条记录修改模式值设置
 */
function zdybdXgzdMoreSz(moreTrObj,xgzdObj){
	var flszid = moreTrObj.flszid;
	var src = moreTrObj.src;
	var bzzd = moreTrObj.bzzd;
	var zds = moreTrObj.zds;
	
	var xgzd = xgzdObj.zd;
	var xgzdZdz = xgzdObj.zdz;
	var xgzdXgqz = xgzdObj.xgqz;
	
	jQuery("#" + PRECON_TABLE + flszid + " tbody tr:gt(0)").remove();
	for ( var i = 0; i < xgzdXgqz.length; i++) {
		var srcObj = xgzdXgqz[i];
		setValueUpdateMoreTr(flszid, srcObj);
	}
	
	var tdLength = jQuery("#" + PRECON_TABLE + flszid + " tbody tr:first").find(
	"th:visible").length;
	var tipTr = "<tr><td colspan='"+tdLength+"'><font color='red'>以下为修改后的记录</font></td></tr>";
	jQuery("#" + PRECON_TABLE + flszid ).append(tipTr);
	for ( var i = 0; i < xgzdZdz.length; i++) {// 修改后记录
		var srcObj = xgzdZdz[i];
		setValueUpdateMoreTr(flszid, srcObj);
	}	
}


/**
 * 用提供的记录，替换表单值
 * 
 * @param data
 * @return
 */
function zdybdReplaceZd(data) {
	if (data == null || data == "") {
		return;
	}
	zdybdReplaceZdDt(data);
	zdybdReplaceZdMore(data);
}

/*
 * 用提供的记录，替换表单值，单条记录
 */
function zdybdReplaceZdDt(data) {

	for ( var i = 0; i < data.length; i++) {
		var o = data[i];
		var zd = o.zd;
		var zdz = o.zdz;
		var xgqz = o.xgqz;
		var newValue = zdz;
		if (newValue == null) {
			newValue == "";
		}
		/*
		 * 字段类型 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 13:日期文本框 21:照片
		 * 22:省市县选择 23:链接 24;学院专业班级 99:功能自定义
		 */
		for ( var j = 0; j < zddyList.length; j++) {
			var zddyObj = zddyList[j];
			var zddyZd = zddyObj.zd;//
			var zddySzz = zddyObj.szz;
			var zddyZdlx = zddyObj.zdlx;//
			var zddyBdms = zddyObj.bdms;
			if(zddyBdms == "4"){
				continue;
			}
			if (zddyZd == zd) {
				if (zddyZdlx == "2" || zddyZdlx == "5" || zddyZdlx == "11"
						|| zddyZdlx == "13") {
					jQuery("#" + zd).val(newValue);
				} else if (zddyZdlx == "3") {
					jQuery(
							"input:radio[name='" + zd + "'][value='" + newValue
									+ "']").attr("checked", "checked");
				} else if (zddyZdlx == "22") {
					if (newValue != "") {
						var ssxDms = getSsx(zddySzz, newValue);
						if (ssxDms != null) {
							jQuery("#" + zd + "_province").val(ssxDms['shdm']);
							jQuery("#" + zd + "_province").change();
							jQuery("#" + zd + "_city").val(ssxDms['shidm']);
							jQuery("#" + zd + "_city").change();
							jQuery("#" + zd + "_locale").val(ssxDms['xidm']);
							jQuery("#" + zd + "_locale").change();
						}
					}
				}
				break;
			}
		}
	}
}

/*
 * 用提供的记录，替换表单值，多条记录
 */
function zdybdReplaceZdMore(data) {
	for ( var i = 0; i < moreUpdateZdJson.length; i++) {
		var o = moreUpdateZdJson[i];
		var flszid = o.flszid;
		var src = o.src;
		var bzzd = o.bzzd;
		var zds = o.zds;
		
		for ( var i = 0; i < data.length; i++) {
			var xgzdObj = data[i];
			var xgzd = xgzdObj.zd;
			if(src != xgzd){
				continue;
			}
			var xgzdZdz = xgzdObj.zdz;
			jQuery("#" + PRECON_TABLE + flszid + " tbody tr:gt(0)").remove();
			for ( var i = 0; i < xgzdZdz.length; i++) {// 修改后记录
				var srcObj = xgzdZdz[i];
				setValueUpdateMoreTr(flszid, srcObj);
			}
		}
	}	
}


/**
 * 得到修改字段及值json
 * 
 * @return
 */
function getXgzdJson() {
	var xgzdArr = [];
	var xgzdJson = {};
	xgzdJson.data = xgzdArr;
	
	var newValue = "";
	var flag = false;
	for ( var i = 0; i < zddyList.length; i++) {
		var xgzdDt = {};
		var o = zddyList[i];
		var zd = o.zd;//
		var szz = o.szz;
		var xgzdDtFlag = false;
		/*
		 * 字段类型 0:仅显示 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 13:日期文本框 21:照片
		 * 22:省市县选择 23:链接 24;学院专业班级 99:功能自定义
		 */
		var zdlx = o.zdlx;
		var yxxg = o.yxxg;
		var bdms = o.bdms;
		if(bdms == "4"){
			continue;
		}
		if(zdlx == "0"){
			continue;
		}
		if (yxxg != "0") {// 允许修改
			if (!flag) {
				flag = true;
			} else {
			}
			if (zdlx == "3") {
				var oldDm = valueJson[zd];
				var newValue = "";
				if (zdlx == "2") {
					newValue = jQuery("#" + zd).val();
				} else {
					newValue = jQuery("[name='" + zd + "']:checked").val();
				}
				if (oldDm == null) {
					oldDm = "";
				}
				if (newValue == null) {
					newValue = "";
				}
				if (oldDm != newValue) {
					xgzdDt.zd = zd;
					xgzdDt.zdz = newValue;
					xgzdDt.xgqz = oldDm;	
					xgzdDtFlag = true;
				}
			} else if (zdlx == "22") {
				var oldDm = valueJson[zd];
				var newValue = jQuery("#" + zd + "_locale").val();
				if (oldDm == null) {
					oldDm = "";
				}
				if (newValue == null) {
					newValue = "";
				}
				if (oldDm != newValue) {
					xgzdDt.zd = zd;
					xgzdDt.zdz = newValue;
					xgzdDt.xgqz = oldDm;	
					xgzdDtFlag = true;

				}
			} else if (zdlx == "5" || zdlx == "11" || zdlx == "13"
					|| zdlx == "2") {
				var oldDm = valueJson[zd];

				newValue = jQuery.trim(jQuery("#" + zd).val());
				if (oldDm == null) {
					oldDm = "";
				}
				if (newValue == null) {
					newValue = "";
				}
				if (oldDm != newValue) {
					xgzdDt.zd = zd;
					xgzdDt.zdz = newValue;
					xgzdDt.xgqz = oldDm;	
					xgzdDtFlag = true;
				}
			}
		}

		if (xgzdDtFlag) {
			xgzdArr.push(xgzdDt);
		}
	}
	
	// 多条记录模式
	for ( var i = 0; i < moreUpdateZdJson.length; i++) {
		var o = moreUpdateZdJson[i];
		var flszid = o.flszid;
		var zds = o.zds;
		var src = o.src;
		var xgzdDt = {};
		xgzdDt.zd = src;
		var xgqzSrcList = valueJson[src];
		xgzdDt.xgqz = JSON.stringify(xgqzSrcList);
		var newValue = jQuery("#" + src).val();
		var newValueArr = eval("[" + newValue + "]");
		var zdzSrcList = "";
		if(newValueArr.length > 0){
			zdzSrcList = newValueArr[0]["data"];
			xgzdDt.zdz = JSON.stringify(zdzSrcList);
		}

	 if(!xgqzIsZdz(zds,xgqzSrcList,zdzSrcList)){
			xgzdArr.push(xgzdDt);
	 }
	}
	if(xgzdJson.data == null || xgzdJson.data.length == 0){
		return false;
	}
	jQuery("#xgzdJson").val(JSON.stringify(xgzdJson));
	return true;
}

/*
 * 判断修改前值是否与修改后值相同
 */
function xgqzIsZdz(zds,xgqzSrcList,zdzSrcList){
	var flag = true;
	var xgqzStr = "";
	var zdzStr = "";
	for ( var i = 0; i < zds.length; i++) {
		var zdObj = zds[i];
		var zd = zdObj.zd;
		var zdlx = zdObj.zdlx;
		if(zdlx == "1"){
			continue;
		}
		if(xgqzSrcList != null && xgqzSrcList.length > 0 ){
			for ( var j = 0; j < xgqzSrcList.length; j++) {
				var xgqzObj = xgqzSrcList[j];
				var xgqzValue = xgqzObj[zd];
				if(xgqzValue == null){
					xgqzValue = "";
				}
				xgqzStr += "zd:" +  zd + ",value:" + xgqzValue + ",";		
			}	
		}

		if(zdzSrcList != null && zdzSrcList.length > 0 ){
			for ( var j = 0; j < zdzSrcList.length; j++) {
				var zdzObj = zdzSrcList[j];
				var zdzValue = zdzObj[zd];
				if(zdzValue == null){
					zdzValue = "";
				}
				zdzStr += "zd:" + zd + ",value:" + zdzValue + ",";		
			}		
		}
	}	
	if(xgqzStr != zdzStr){
		flag = false;
	}	
	
	return flag;
}
