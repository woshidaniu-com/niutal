var tbclass;
jQuery(function() {

	setTjx();// 设置统计项
	setTjzh();// 设置统计组合

	setInit();// 设置查询初值

	tjtbInit();//

	changeTjtb();

});

function setInit() {

	var tjx = jQuery("input[name='tjx']");// 统计项
	var tjzh = jQuery("input[name='tjzh']");// 统计组合

	var tblx = jQuery("input[name='tblx']");// 图表类型
	var tbbt = jQuery("input[name='tbbt']");// 图表标题

	tjx.eq(0).attr("checked", "checked");
	tjzh.eq(0).attr("checked", "checked");

	if (tjx != null) {
		tjx.change(function() {
			changeTjtb();
		});
	}

	if (tjzh != null) {		
		tjzh.change(function() {
			changeTjtb();
		});
	}

	if (tblx != null) {
		tblx.change(function() {
			changeTjtb();
		});
	}

	if (tbbt != null) {
		tbbt.change(function() {
			changeTjtb();
		});
	}
		
	var isPie = false;//是否包含饼图
	if (tjx != null) {
		tjx.each(function(index){
			if(jQuery(this).val() == "zs"){
				isPie = true;//包含总数项，保留饼图
				return false;
			}
		});
	}
	
	if(tjzh != null){
		if(isPie){
			//包含横向列，不包含纵向合计项
			var hxlHead = jQuery("#hxlHead").val();
			var zxlHead = jQuery("#zxlHead").val();
			if(hxlHead != null && hxlHead != "" 
				&& jQuery("input[name='tjzh'][value='zxlhj']").length == 0 
				&& zxlHead != null && zxlHead != ""  ){
				isPie = false;//去掉饼图
			}
		}
	}
	
	if(!isPie){//不包含饼图
		jQuery("#tblx_pie").remove();//去掉饼图
	}
}

/*
 * 统计图表
 */
function tjtbInit() {
	tbclass = {
		chart : {
			type : 'line'// column,pie,line
		},
		title : {
			text : ' '
		},
		xAxis : {
			categories : [],
			labels: { 
				rotation: 30,
				align: 'left'
			}
		},
		yAxis : {
			min : 0,
			title : {
				text : ''
			}
		},
//		exporting : {// 不允许打印导出
//			buttons : {
//				menuItems : ['downloadPNG'],
//				exportButton : {
//					enabled : false
//				},
//				printButton : {
//					enabled : true
//				}
//			}
//		},
		credits : {// 不显示logo
			enabled : false
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					formatter : function() {
						return '<b>' + this.point.name + '</b>: '
								+ xsgsh(this.percentage,2) + ' %';
					}
				}
			},
			series : {
				cursor : 'pointer',
				line : {
					dataLabels : {
						enabled : false
					},
					enableMouseTracking : false
				},
				point : {
					allowPointSelect : true,// 是否允许选中点
					animation : true,// 是否在显示图表的时候使用动画
					cursor : 'pointer',// 鼠标移到图表上时鼠标的样式
					dataLabels : {
						enabled : true,// 是否在点的旁边显示数据
						rotation : 0
					},
					enableMouseTracking : true,// 鼠标移到图表上时是否显示提示框
					events : {// 监听点的鼠标事件
						click : function(e) {
							
						}
					}
				}
			}
		},
		tooltip : {
			headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' + '<td style="padding:0"><b>{point.y} </b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		}
	}
}

/*
 * 界面表单元素更改后，重新绘制图表
 */
function changeTjtb() {
	var tjx = jQuery("input[name='tjx']:checked").val();
	var tblx = jQuery("input[name='tblx']:checked").val();// 图表类型
	var tjzh = jQuery("input[name='tjzh']:checked").val();// 统计组合,单选

	// 选中纵向合计，必须选择总数
	if (tjzh == "zxlhj") {
	//	jQuery("[name='tblx'][value='line']").attr("checked","checked");
	}
	
	if(tblx == "pie"){//饼图
		jQuery("[name='tjx'][value='zs']").attr("checked","checked");
		jQuery("[name='tjzh'][value='zxlhj']").attr("checked","checked");
	}
		
	
	createTjtb();
}

/*
 * 图表的绘制
 */
function createTjtb() {
	var tjx = jQuery("input[name='tjx']:checked").val();// 统计项
	var tbbt = jQuery("#tbbt").val();// 图表标题
	var tblx = jQuery("input[name='tblx']:checked").val();// 图表类型

	var hxlHead = jQuery("#hxlHead").val();
	var zxlHead = jQuery("#zxlHead").val();
	var hxlContent = jQuery("#hxlContent").val();
	var zxlContent = jQuery("#zxlContent").val();
	var contentJson = jQuery("#contentJson").val();
	var contentJsonObj = eval(contentJson);

	var hxlhjContentJson = jQuery("#hxlhjContentJson").val();
	var hxlhjContentJsonObj = eval(hxlhjContentJson);

	var zxlhjContentJson = jQuery("#zxlhjContentJson").val();
	var zxlhjContentJsonObj = eval(zxlhjContentJson);

	var tjzh = jQuery("input[name='tjzh']:checked").val();// 统计组合,单选
	if(tbbt == ""){//标题若为空，以空串显示，避免样式问题
		tbbt = " ";
	}
	tbclass.title.text = tbbt;// 图表标题

	// /////饼图绘制/////////////////
	if (tblx == "pie") {// 饼图，特殊处理，仅总数显示时支持
		tbclass.series = [ {
			type : 'pie',
			name : '总数',
			data : getPieData()
		}]
		jQuery('#container').highcharts(tbclass);
		return;
	}
	// /////////////////////////

	// 统计项名称，纵轴显示
	var tjxname = "";
	var tjxJsonObj = eval(jQuery("#tjxJson").val());
	for ( var i = 0; i < tjxJsonObj.length; i++) {
		var o = tjxJsonObj[i];
		var code = o.code;
		if (code == tjx) {
			tjxname = o.name;
			break;
		}
	}
	tbclass.yAxis.title.text = tjxname;// 纵轴标题

	tbclass.chart.type = tblx;// 图表类型

	var xContentObj = "";// X轴显示内容
	if (hxlContent != null && hxlContent != "") {
		xContentObj = eval(hxlContent);//
	} else {
		xContentObj = eval(zxlContent);//
	}

	tbclass.xAxis.categories = xContentObj;

	var seriesObj = "";

	if (hxlHead == null || hxlHead == "") {// 不包含横向列
		for ( var i = 0; i < hxlhjContentJsonObj.length; i++) {
			var o = hxlhjContentJsonObj[i];
			if (o.bblname == tjzh) {
				seriesObj = hxlhjContentJsonObj[i][tjx];
				break;
			}
		}
	} else if (tjzh == "zxlhj") {// 纵向列合计项
		seriesObj = zxlhjContentJsonObj;
	} else if (jQuery("#tjzh_hxlhj").attr("checked") == "checked") {// 横向列合计项
		// xContentObj = eval(zxlContent);//
		// tbclass.xAxis.categories = xContentObj;
		//
		// for ( var i = 0; i < hxlhjContentJsonObj.length; i++) {
		// var o = hxlhjContentJsonObj[i];
		// if(o.bblname == tjzh){
		// seriesObj = hxlhjContentJsonObj[i][tjx];
		// break;
		// }
		// }
	} else {
		if (zxlHead == "") {// 无纵向列
			seriesObj = contentJsonObj[0][tjx];
		} else {
			for ( var i = 0; i < contentJsonObj.length; i++) {
				var o = contentJsonObj[i];
				if (o.bblname == tjzh) {
					seriesObj = contentJsonObj[i][tjx];
					break;
				}
			}
		}
	}
	
	//仅对总数统计进行详情查看
	//if(tjx == ZS){
	//	tbclass.plotOptions.series.point.events.click = function(e) {
	//		tjbbXqWin(this.x);
	//	};
	//}

	tbclass.series = seriesObj;
	jQuery('#container').highcharts(tbclass);
}

/*
 * 得到饼图的数据
 */
function getPieData() {
	var tjx = jQuery("input[name='tjx']:checked").val();// 统计项
	var tbbt = jQuery("#tbbt").val();// 图表标题
	var tblx = jQuery("input[name='tblx']:checked").val();// 图表类型

	var hxlHead = jQuery("#hxlHead").val();
	var zxlHead = jQuery("#zxlHead").val();
	var hxlContent = jQuery("#hxlContent").val();
	var zxlContent = jQuery("#zxlContent").val();
	var contentJson = jQuery("#contentJson").val();
	var contentJsonObj = eval(contentJson);

	var hxlhjContentJson = jQuery("#hxlhjContentJson").val();
	var hxlhjContentJsonObj = eval(hxlhjContentJson);

	var zxlhjContentJson = jQuery("#zxlhjContentJson").val();
	var zxlhjContentJsonObj = eval(zxlhjContentJson);

	var tjzh = jQuery("input[name='tjzh']:checked").val();// 统计组合,单选

	var dataArr = "";
	var xArr = "";
	// 包含横向列，仅支持含有纵向合计列或仅有总数列
	if (hxlHead != null && hxlHead != "") {
		xArr = eval(hxlContent);
		if (tjzh == "zxlhj") {// 纵向列合计情况
			dataArr = eval(zxlhjContentJsonObj[0].data);
		}else if (tjzh == undefined){//无纵向列级别
			dataArr = eval(contentJsonObj[0].zs[0].data);
		}
	} else {
		xArr = eval(zxlContent);

		if(zxlHead.split(",").length < 2){
			dataArr = eval(hxlhjContentJsonObj[0].zs[0].data);
		}else{
			for ( var i = 0; i < hxlhjContentJsonObj.length; i++) {
				var o = hxlhjContentJsonObj[i];
				if (o.bblname == tjzh) {
					dataArr = hxlhjContentJsonObj[i].zs[0].data;
					break;
				}
			}	
		}
	}

	var pieJsonObj = changePieArr(xArr, dataArr);

	// 不包含横向列情况

	return pieJsonObj;
}

/*
 * 饼图，转换数组
 */
function changePieArr(xArr, dataArr) {
	if (xArr == null || dataArr == null) {
		return;
	}
	var pieJson = "";
	for ( var i = 0; i < xArr.length; i++) {
		var jsonTemp = "['" + xArr[i] + "',";
		if (dataArr.length > i) {
			jsonTemp += dataArr[i];
		} else {
			jsonTemp += 0;
		}
		jsonTemp += "]";

		if (i > 0) {
			pieJson += ","
		}
		pieJson += jsonTemp;
	}
	pieJson = "[" + pieJson + "]";
	return eval(pieJson);
}

// 统计项
function setTjx() {
	var sHtml = "";
	var tjxJsonObj = eval(jQuery("#tjxJson").val());
	for ( var i = 0; i < tjxJsonObj.length; i++) {
		var o = tjxJsonObj[i];
		var code = o.code;
		var name = o.name;
		sHtml += "<label>";
		sHtml += "<input type='radio' name='tjx'  value='" + code + "'/>"
				+ name;
		sHtml += "</label>&nbsp;";
	}
	jQuery("#tjxTd").html(sHtml);
}

// 统计组合
function setTjzh() {
	var sHtml = "";
	var hxlHead = jQuery("#hxlHead").val();
	var zxlHead = jQuery("#zxlHead").val();
	var hxlhjContentJson = jQuery("#hxlhjContentJson").val();
	var zxlhjContentJson = jQuery("#zxlhjContentJson").val();
	var contentJson = jQuery("#contentJson").val();
	var contentJsonObj = eval(contentJson);

	var hxlhjContentJson = jQuery("#hxlhjContentJson").val();

	if (zxlHead != null && zxlHead != "") {
		var zxlHeads = zxlHead.split(",");
		if (zxlHeads.length > 0) {
			for ( var i = 0; i < contentJsonObj.length; i++) {
				var o = contentJsonObj[i];
				var bblname = o.bblname;
				sHtml += "<label>";
				sHtml += "<input type='radio' name='tjzh' value='" + bblname
						+ "'/>" + bblname;
				sHtml += "</label>&nbsp;";
			}
		}
	}

	if (hxlHead != "" && zxlhjContentJson != null && zxlhjContentJson != "") {
		sHtml += "<label>";
		sHtml += "<input type='radio' name='tjzh' value='zxlhj' />" + "纵向合计";
		sHtml += "</label>&nbsp;";
	}
	//
	// if(hxlHead != "" && hxlhjContentJson != ""){
	// sHtml += "<label>";
	// sHtml += "<input type='checkbox' id='tjzh_hxlhj' name='tjzh_hxlhj' />" +
	// "横向合计";
	// sHtml += "</label>&nbsp;";
	// }

	jQuery("#tjzhTd").html(sHtml);
}

/*
 * 统计报表详情页弹层显示
 * hzbbh:横坐标编号
 */
//function tjbbXqWin(hzbbh){
//	var title = "统计报表详情";
//	
//	setCurBbl(hzbbh);
//
//	var url = _path + "/niutal/tjcx/tjbbXq/cxTjbbXq.zf?timestamp=" + new Date().getTime();
//	url += "&gnmk=" + jQuery("#gnmk").val();
//	url += "&xmdm="+ jQuery("#xmdm").val();
//	url += "&gltj="+ jQuery("#gltj").val();
//	
//	$.showDialog(url,title ,$.mergeObj(modifyConfig,{"width":"803px", "height":"500px"}));
//
//}

function setCurBbl(hzbbh){
	setCurBblHxl(hzbbh);
	setCurBblZxl(hzbbh);
}


function setCurBblHxl(hzbbh){
	var hxlHead = jQuery("#hxlHead").val();
	var hxlContent = jQuery("#hxlContent").val();
	var hxlContentCode = jQuery("#hxlContentCode").val();
	var xContentObj = "";// X轴显示内容
	if (hxlContent == null || hxlContent == "") {
		return;
	}

	xContentObj = eval(hxlContent);//
	var xContentCodeObj = "";// X轴显示内容
	xContentCodeObj = eval(hxlContentCode);//
	
	var params = "";
	var curBbl = xContentCodeObj[hzbbh];
	var bbhxlName = xContentObj[hzbbh];
	
	var bbhxls = curBbl.split(FGF_BS);
	
	var bbhxl = bbhxls[0];
	var bbhxlValue = bbhxls[1];
	
	var curBblmc = "<span class='bold'>"+hxlHead+"</span>";
	curBblmc += "<span> "+ bbhxlName +"</span>";

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
	}else if(parseQjk(curBbl).success){//区间块类型特殊处理
		params += "(";
		params += qjkSql(curBbl);
		params += ")";
	}else{
		params += bbhxl + "='" + bbhxlValue + "'";
	}
	
	jQuery("#curBbl").val(params);
	jQuery("#curBblmc").val(curBblmc);
	
}


function setCurBblZxl(hzbbh){
	var zxlHead = jQuery("#zxlHead").val();
	var zxlHeadCode = jQuery("#zxlHeadCode").val();
	var hxlContent = jQuery("#hxlContent").val();
	var zxlContent = jQuery("#zxlContent").val();
	var zxlContentCode = jQuery("#zxlContentCode").val();

	var xContentObj = "";// X轴显示内容
	
	if ((hxlContent != null && hxlContent != "") || zxlContent == null || zxlContent == "") {
		return;
	}
	if(zxlHead != null && zxlHead != ""){
		var zxlHeads = zxlHead.split(",");
		zxlHead = zxlHeads[zxlHeads.length - 1];
	}

	xContentObj = eval(zxlContent);//

	var xContentCodeObj = "";// X轴显示内容
	xContentCodeObj = eval(zxlContentCode);//
	
	var params = "";
	var curBbl = xContentCodeObj[hzbbh];
	var bbhxlName = xContentObj[hzbbh];
	
	var bbhxls = curBbl.split(FGF_BS);
	
	var bbhxl = zxlHeadCode;
	var bbhxlValue = bbhxls[bbhxls.length - 1];
	
	var curBblmc = "<span class='bold'>"+zxlHead+"</span>";
	curBblmc += "<span> "+ bbhxlName +"</span>";

	if(curBbl.indexOf(FGF_BS + WZ_EN) > -1){//未知
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
	}else if(parseQjk(curBbl).success){//区间块类型特殊处理
		params += "(";
		params += qjkSql(curBbl);
		params += ")";
	}else{
		params += bbhxl + "='" + bbhxlValue + "'";
	}
	
	jQuery("#curBbl").val(params);
	jQuery("#curBblmc").val(curBblmc);

	
}
