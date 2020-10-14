//支持的导入文件类型
var SUPPORT_TYPES = ['xls','xlsx','dbf','DBF'];
function importData(drmkdm) {
	if (drmkdm == null || drmkdm == "") {
		alert("导入模块代码不能为空。");
		return false;
	}
	var url = _path + '/niutal/dr/import_showImport.html';
	url += '?drmkdm=' + drmkdm;

	showWindow('导入', 720, 560, url);
	return false;
}
/**
 * 选择导入的列
 * 
 * @return
 */
function selectImport() {
	var importFile=$("#importFile").val();
	if(!importFile){
		return alert("请先选择要导入的文件！");
	}
	//检查是否是支持的文件类型
	var type = jQuery('#importFile').val().substring(jQuery('#importFile').val().lastIndexOf('.')+1, jQuery('#importFile').val().length);
	if(jQuery.inArray(type, SUPPORT_TYPES) == -1){
		return alert("导入文件格式不合法, 请确认！");
	}
	var drmkdm = $("#drmkdm").val();
	$.ajaxFileUpload( {
		url : _path + '/niutal/dr/import_uploadExcel.html?filetype=' + type + '&drmkdm=' + drmkdm,
		secureuri : false,
		fileElementId : 'importFile', // 上传控件ID
		dataType : 'json',
		success : function(data, status) {
			var url = _path + '/niutal/dr/import_selectColumn.html';
			url += '?drmkdm=' + drmkdm;
			showWindow('导入设置', 800,500, url);
			return false;
		},
		error : function(data, status, e) {
			alert("系统异常，稍候重试!");
		}
	});
}
/**
 * 显示对应规则信息
 * 
 * @return
 */
function showRule() {
	var url = _path + '/niutal/dr/import_getRulers.html';
	$.ajax( {
		type : "POST",
		url : url,
		data : {
			drmkdm : $("#drmkdm").val(),
			drlpzids : $("#drlpzids").val()
		},
		success : function(data) {
			showRuleMessage(data);
		}
	}, "json");
}
/**
 * 显示规则提示信息
 * 
 * @param data
 * @return
 */
function showRuleMessage(data) {
	var width_1 = jQuery('#rule_header_1').width() + 1;
	var width_2 = jQuery('#rule_header_2').width() + 1;
	var width_3 = jQuery('#rule_header_3').width() + 1;
	var width_4 = jQuery('#rule_header_4').width() + 1;
	var width_5 = jQuery('#rule_header_5').width() + 1;
	var width_6 = jQuery('#rule_header_6').width() + 1;
	
	for ( var i in data) {
		var model = data[i];
		var gshxx = "无";
		if (model.gshxx) {
			gshxx = model.gshxx;
		}
		var tr = "<tr><th style='width:"+width_1+"px'>" + model.drlmc + "</th>";
			tr += "<td  align='center' style='width:"+width_2+"px'>" 
			+ fomartZj(model) + "</td><td  align='center' style='width:"+width_3+"px'>" 
			+ fomartWy(model) + "</td><td  align='center' style='width:"+width_4+"px'>" 
			+ fomartNull( model) + "</td><td  align='center' style='width:"+width_5+"px'>"
			+ model.zdcd + "</td><td  align='center' style='text-align:left;width:"+width_6+"px'>" + gshxx + "</td></tr>";
		$("#rule_div>table:last").append($(tr));
	}
	//$("#drts").append("<div style='border: 1px solid #B0CBE0;padding: 6px 8px 6px 6px;text-align:right;background-color: #eee;'><a style='color: blue;cursor: pointer;' href='javascript:void(0);' onclick='showMore();'>其它导入规则</a></div>");
	$("#drts").append("<div style='border-bottom: 1px solid #B0CBE0;background-color: #eee;'></div>");
	if(jQuery('#rule_div').height() >220){
		jQuery('#rule_div').css({"height":"220px","overflow-x":"hidden","overflow-y":"scroll"});
	}
}
function showMore(){
	var drmkdm=$("#drmkdm").val();
	url=_path + '/niutal/dr/import_showRulers.html?drmkdm='+drmkdm;;
	showWindow('导入设置', 600, 420, url);
}

function fomartZj(model){
	if(model.sfzj=="1"){
		return "<img src='"+_path+"/images/right.png' />"
	}else{
		return "";
	}
}

function fomartWy(model){
	if(model.sfwy=="1"){
		return "<img src='"+_path+"/images/right.png' />"
	}else{
		return "";
	}
}

function fomartNull(model){
	if(model.sfzj=="1"||model.sfbt=="1"){
		return "<img src='"+_path+"/images/right.png' />"
	}else{
		return "";
	}
}
/**
 * 下载模板
 * 
 * @return
 */
function downloadTemplate() {
	var jqFrom = jQuery("#importForm");
	var url = _path + "/niutal/dr/import_dowloadTemplate.html";
	jqFrom.attr("action", url);
	jqFrom.submit();
}
function downloadErrorData() {
	var cwts=$("#cwts").val();
	if(cwts<=0){
		alert("不存在错误信息！");
		return ;
	}
	var jqFrom = jQuery("#importForm");
	var url = _path + "/niutal/dr/import_dowloadError.html";
	jqFrom.attr("action", url);
	jqFrom.submit();
}
function saveImport() {
	var tips;
	//setTimeout(function(){
	//	tips = alertTips(null,null,"数据执行中...");
	//},100);
	var url = _path + '/niutal/dr/import_importData.html';
	$.ajax( {
		type : "POST",
		url : url,
		data : {
			drmkdm : $("#drmkdm").val(),
			drlpzids : $("#drlpzids").val(),
			crfs : $("#crfs").val()
		},
		beforeSend : function(){
			tips = alertTips(null,null,"数据执行中...");
		},
		success : function(data) {
			tips.close();
			var mes="";
			var all="";
			if(data.result==-1){
				return alert("导入错误！请重新导入。");
			}else if(data.result==0){
				$("#drzx").val(1);
				$("#cwts").val(data.cwts);
				all=parseInt(data.all)-1;
				var cgts=all-parseInt(data.cwts);
				mes="&nbsp;&nbsp;<font color='blue'>总计["+all+"]条</font>，<font color='green'>可导入["+cgts+"]条</font>，<font color='red'>错误["+data.cwts+"]条</font>。";
				if(data.cwts > 0){
					var a = jQuery('<a href="javascript:void(0);" class="name" onclick="downloadErrorData();">点击下载异常数据</a>');
					jQuery('#cwsj').empty().append(a);
				}else{
					jQuery('#cwsj').empty().html('&nbsp;&nbsp;无异常数据');
				}
			}else{
				$("#cwts").val(data.cwts);
				$("#drzx").val(2);
				all=parseInt(data.all)-1;
				var cgts=parseInt(data.insert)+parseInt(data.update);
				mes="&nbsp;&nbsp;总计["+all+"]条，成功导入[<font color='green' style='font-weight: bold;'>"+cgts+"</font>]条，" +
						"插入[<font color='green' style='font-weight: bold;'>"+data.insert+"</font>]条，修改[<font color='green' style='font-weight: bold;'>"+data.update+"</font>]条。";
				jQuery('#cwsj').empty().html('&nbsp;&nbsp;无异常数据');
			}
			jQuery('#dr_result').show();
			$("#importResult").html(mes);
			
			//新增回调函数
			var api = frameElement.api,W = api.opener;
			if(W && W.importCallback && typeof W.importCallback == 'function'){
				/**
				 * data.result : 导入结果，-1代表失败，0代表成功
				 * 
				 * data.cwts ： 错误条数，如果大于0代表有错误数据，实际没有处理导入数据，否则标识数据成功处理
				 */
				W.importCallback(data.result, data.cwts, $("#drmkdm").val());
			}
			//新增回调函数
			
			
		}
	}, "json");
}

function setDrWaiting(ms){
	var tips;
	if(ms == true){
		$("#importResult").html("<font color='red'>数据导入中，请耐心等待...</font>");
		setTimeout(function(){
			tips = alertTips(null,null,"数据执行中...");
		},100);
	}else{
		$("#importResult").html("&nbsp;&nbsp;无");
		tips.close();
	}
}