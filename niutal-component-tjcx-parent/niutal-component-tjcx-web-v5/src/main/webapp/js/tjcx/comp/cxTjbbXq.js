var exportConfigList;
var bblqzfw;
var parentPage;
jQuery(function() {
	parentPage = getParentDialogWin();
	
	onShow();
	
});

function onShow() {
//	createTjk();// 生成条件块代码
	createDqxx();
	setInit();
	setDcButton();

}

function setInit() {
	var sHtml = "";
	sHtml += "<table id='tabGrid'></table>";
	sHtml += "<div id='pager'></div>";
	jQuery(".formbox").html(sHtml);

	var xmdm = jQuery("#xmdm").val();
	var gltj = jQuery("#gltj").val();

	jQuery.ajax( {
		type : "post",
		// async: false,
		url : _path + "/zfxg/tjcx/tjbbXq/cxTjbbxqBt.zf?doType=query&timestamp="
				+ new Date().getTime(),
		data : {
			xmdm : xmdm
		},
		dataType : "json",
		success : function(data) {
			exportConfigList = data.exportConfigList;
			bblqzfw = data.bblqzfw;
			if (exportConfigList == null || exportConfigList.length == 0) {
				alert("字段未进行相关配置，请联系管理员！");
				return;
			}
			tjcxsj();
		}
	});

}

function tjcxsj() {
	var xmdm = jQuery("#xmdm").val();
	var gltj = parentPage.jQuery("#gltj").val();
	var curBbl = parentPage.jQuery("#curBbl").val();
	var url = _path + '/zfxg/tjcx/tjbbXq/cxTjbbXqsj.zf?doType=query';
	url += "&timestamp=" + new Date().getTime();

	jQuery("#gltj").val(gltj);
	jQuery("#curBbl").val(curBbl);
	
	var TjcxGrid = Class.create(BaseJqGrid, {
		caption : "信息列表",
		pager : "pager", // 分页工具栏
		url : url,
		postData : {
			xmdm : xmdm,
			gltj : gltj,
			curBbl : curBbl
		},
		autoencode : true,
		multiselect : false,
		shrinkToFit : false
	});

	var tjcxGrid = new TjcxGrid();
	tjcxGrid.colModel = createColModelJson();
	tjcxGrid.sortname = getSortname();

	loadJqGrid("#tabGrid", "#pager", tjcxGrid);
}

/*
 * 生成colModel的json格式
 */
function createColModelJson() {

	
	var str = new Array();
	for ( var i = 0; i < exportConfigList.length; i++) {
		var o = exportConfigList[i];
		var zd = o.zd;
		var zdss = zd;
		var zdmc = o.zdmc;
		// if(i > 5){
		// break;
		// }
		var o = {};
		o.label = zdmc;
		o.name = zd.toUpperCase();
		o.index = zd.toUpperCase();
		o.formatter = function(cellvalue, options, rowObject){
			var zdx= options.colModel.name.toLowerCase();
			var qzfw = bblqzfw[zdx];
			if(qzfw != null && qzfw != ""){
				cellvalue = getQzfwMc(zdx,cellvalue);
			}
			if(cellvalue == null){
				cellvalue = "";
			}
			return cellvalue;
		};
		str.push(o);
	}
//	str += "]";
	return str;
}

/*
 * 获取排序列
 */
function getSortname() {
	var str = "";
	for ( var i = 0; i < exportConfigList.length; i++) {
		var o = exportConfigList[i];
		var zd = o.zd;
		var zdmc = o.zdmc;
		if (i > 0) {
			str += ",";
		}
		str += zd;
		if (i > 2) {
			break;
		}
	}
	return str;
}

/*
 * 生成条件块代码
 */
function createTjk() {
	var gltjmc = parentPage.jQuery("#gltjmc").val();
	if (gltjmc == null || gltjmc == "") {
		return;
	}
	var sHtml = "";
	var gltjmcs = gltjmc.split("or");

	if (gltjmcs == null) {
		return;
	}
	var flag = false;
	for ( var i = 0; i < gltjmcs.length; i++) {
		var gltjmcTmp = gltjmcs[i];
		if (gltjmcTmp == null) {
			continue;
		}
		var gltjmcTmps = gltjmcTmp.split("and");
		if (gltjmcTmps == null) {
			continue;
		}
		if (flag) {
			sHtml += "<li>";
			sHtml += "<a href='javascript:;' >";
			sHtml += "<span><font color='#155fbe'>或者</font></span>&nbsp;";
			sHtml += "</a></li>";
		} else {
			flag = true;
		}
		for ( var j = 0; j < gltjmcTmps.length; j++) {
			var gltjk = jQuery.trim(gltjmcTmps[j]);
			sHtml += "<li>";
			sHtml += "<span style='white-space: normal;' class='title'>";
			sHtml += gltjk + "&nbsp;";
			sHtml += "</span>";
			sHtml += "</li>";
		}
	}

	jQuery("#yxtj").html(sHtml);

}

/*
 * 生成当前选项
 */
function createDqxx() {
	var sHtml = "";
	var gltjmcs = parentPage.jQuery("#curBblmc").val();
	if(gltjmcs == null || gltjmcs == ""){
		return ;
	}
	var gltjmcTmps = gltjmcs.split("and");
	for ( var j = 0; j < gltjmcTmps.length; j++) {
		var gltjk = jQuery.trim(gltjmcTmps[j]);
		sHtml += "<li>";
		sHtml += "<span style='white-space: normal;' class='title'>";
		sHtml += gltjk + "&nbsp;";
		sHtml += "</span>";
		sHtml += "</li>";
	}
	jQuery("#dqxx").html(sHtml);

}


/*
 * 得到取值范围中某一值名称
 */
function getQzfwMc(zdmc,qzfwDm) {
	var qzfwMc = "";
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


function setDcButton(){
	var xmdm = jQuery("#xmdm").val();
	var btn_dc=jQuery("#btn_dc");//导出
	if(btn_dc != null){
		btn_dc.click(function () {
		    var records = jQuery("#tabGrid").jqGrid('getGridParam','records');
			if(parseInt(records) > 10000){
				alert("导出数据过多，超出10000条，请增加查询条件后进行导出！");
				return;
			}
			var url = _path+'/zfxg/tjcx/tjcx/export.zf?timestamp=' + new Date().getTime();
			$.customExport(xmdm,url,function(){},{"formName":"form1"});
		});
	}
}

