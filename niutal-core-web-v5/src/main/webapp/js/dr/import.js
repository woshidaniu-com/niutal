var SUPPORT_TYPES = ['xls','xlsx','dbf','DBF'];

/**
 * 导入数据
 */
function importData(drmkdm) {
	if (drmkdm == null || drmkdm == "") {
		$.alert("导入模块代码不能为空。");
		return false;
	}
	var url = _path + '/niutal/dr/import/showImport.zf?drmkdm=' + drmkdm;
	$.showDialog(url, '导入', {
        width : "900px",
        modalName : "dr-component-main",
        buttons : {
          success : {
            label : "下一步",
            className : "btn-primary btn-next-step",
            callback: function () {
            	selectImport();
            	return false;
            }
          },
          cancel : {
            label : "关　闭",
            className : "btn-default"
          }
        }
      });
	return false;
}

/**
 * 导入数据
 */
function importDataByVersion(drmkdm,version) {
	if (drmkdm == null || drmkdm == "") {
		$.alert("导入模块代码不能为空。");
		return false;
	}
	var url = _path + '/niutal/dr/import/showImport.zf?drmkdm=' + drmkdm +"&version="+version;
	$.showDialog(url, '导入', {
        width : "900px",
        modalName : "dr-component-main",
        buttons : {
          success : {
            label : "下一步",
            className : "btn-primary btn-next-step",
            callback: function () {
            	selectImport();
            	return false;
            }
          },
          cancel : {
            label : "关　闭",
            className : "btn-default"
          }
        }
      });
	return false;
}

/**
 * 选择导入的列
 * @return
 */
function selectImport() {
	var importFile=$("#importFile").val();
	if(!importFile){
		return $.alert("请先选择要导入的文件！");
	}
	//检查是否是支持的文件类型
	var type = jQuery('#importFile').val().substring(jQuery('#importFile').val().lastIndexOf('.')+1, jQuery('#importFile').val().length);
	if(jQuery.inArray(type, SUPPORT_TYPES) == -1){
		return $.alert("导入文件格式不合法, 请确认！");
	}
	var drmkdm = $("#drmkdm").val();
	$.ajaxFileUpload( {
		url : _path + '/niutal/dr/import/uploadExcel.zf?filetype=' + type + '&drmkdm=' + drmkdm,
		secureuri : false,
		fileElementId : 'importFile', // 上传控件ID
		dataType : 'json',
		success : function(data, status) {
			  var url = _path + '/niutal/dr/import/selectColumn.zf?drmkdm=' + drmkdm;
	          $.showDialog(url,"导入设置",{
	          width : "900px",
	          modalName : "dr_select_column_Modal",
	          buttons : {
	            success: {
	              label: "确定导入",
	              className: "btn-primary",
	              callback:function(){
	            	  
	            	  //隐藏下一步按钮
	            	  $("button[data-loading-text='下一步']").hide();
	            	  
	            	  //保存选择
	            	  saveselect();
	              }
	            },
	            cancel : {
	              label : "关　闭",
	              className : "btn-default"
	            }
	          }
	        });
			return false;
		},
		error : function(data, status, e) {
			//隐藏下一步按钮
      	    $("button[data-loading-text='下一步']").hide();
      	  
			$.alert("系统异常，稍候重试!");
		}
	});
}
/**
 * 显示对应规则信息
 * @return
 */
function showRule() {
	var url = _path + '/niutal/dr/import/getRulers.zf';
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
 * @param data
 * @return
 */
function showRuleMessage(data) {
	$.each(data,function(i,n){
		var model =n;
		var gshxx = "无";
		if (model.gshxx) {
			gshxx = model.gshxx;
		}
		var tr = "" 
			+"<tr>"
				+ "<td style='width:15%'>" + model.drlmc + "</td>"
				+ "<td style='width:10%' align='center'>" + fomartZj(model) + "</td>"
				+ "<td style='width:10%' align='center'>" + fomartWy(model) + "</td>"
				+ "<td style='width:10%' align='center'>" + fomartNull( model) + "</td>"
				+ "<td style='width:10%' align='center'>" + model.zdcd + "</td>"
				+ "<td style='width:45%'>" + gshxx + "</td>";
			+ "</tr>";
		$("#rule_table>tbody:last").append($(tr));
	});
	}

function showMore(){
	var drmkdm=$("#drmkdm").val();
	url=_path + '/niutal/dr/import/showRulers.zf?drmkdm='+drmkdm;;
	showWindow('导入设置', 600, 420, url);
}

function fomartZj(model){
	if(model.sfzj=="1"){
		return '<i class="glyphicon glyphicon-ok text-green"></i>'
	}else{
		return "";
	}
}
function fomartWy(model){
	if(model.sfwy=="1"){
		return '<i class="glyphicon glyphicon-ok text-green"></i>'
	}else{
		return "";
	}
}
function fomartNull(model){
	var html = '<i class="glyphicon glyphicon-ok text-green"></i>';
	var showHtml = false;
	if(model.sfzj=="1"){
		showHtml = true;
	}else{
		var sfbtFlag = model.sfbtFlag;
		var sfbtFlagNumber = parseInt(sfbtFlag);
		var flag = sfbtFlagNumber & 1;//插入必填
		if(flag != 0){
			showHtml = true;
		}
	}
	if(showHtml){
		return html;
	}else{
		return "";
	}
}
/**
 * 下载模板
 * @return
 */
function downloadTemplate() {
	var jqFrom = $("#importForm");
	var url = _path + "/niutal/dr/import/dowloadTemplate.zf";
	jqFrom.attr("action", url);
	jqFrom.submit();
}
function downloadErrorData(resultFileId) {
	$("#resultFileId").val(resultFileId);
	var cwts=$("#cwts").val();
	if(cwts <= 0){
		$.alert("不存在错误信息！");
		return ;
	}
	var jqFrom = $("#importForm");
	var url = _path + "/niutal/dr/import/dowloadError.zf";
	jqFrom.attr("action", url);
	jqFrom.submit();
}
function saveImport() {
	var url = _path + '/niutal/dr/import/importData.zf';
	var drmkdm = $("#drmkdm").val();
	var drlpzids = $("#drlpzids").val();
	var crfs = $("#crfs").val();
	var version = $("#version").val();
	$.ajax( {
		type : "POST",
		url : url,
		data : {
			"drmkdm" : drmkdm,
			"drlpzids" : drlpzids,
			"crfs" : crfs,
			"version":version
		},
		beforeSend : function(){
			setDrWaiting(true);
		},
		success : function(data) {
			
			if(data.version == "v1"){//v1版本
				
				var message = "";
				
				if(data.result == -1){
					
					return $.alert("导入错误！请重新导入。");
					
				}else if(data.result == 0){//导入失败
					
					var totalSize=parseInt(data.totalSize);
					var totalUnAcceptRowSize = data.totalUnAcceptRowSize;
					var successInsertRowsSize = parseInt(data.successInsertRowsSize);
					var successUpdateRowsSize = parseInt(data.successUpdateRowsSize);
					
					$("#drzx").val(1);
					$("#totalUnAcceptRowSize").val(totalUnAcceptRowSize);
					
					message = "&nbsp;&nbsp;<font color='red'>导入失败，存在错误数据，请重新导入。共["+totalSize+"]条，其中错误["+data.totalUnAcceptRowSize+"]条。</font>";
					if(data.totalUnAcceptRowSize > 0){
						var a = jQuery('<a href="javascript:void(0);" class="name" onclick="downloadErrorData(\''+ data.resultFileId +'\');">点击下载异常数据</a>');
						$('#cwsj').empty().append('&nbsp;&nbsp;').append(a);
					}else{
						$('#cwsj').empty().html('&nbsp;&nbsp;无异常数据');
					}
				}else{//导入成功
					
					$("#drzx").val(2);
					$("#totalUnAcceptRowSize").val(data.totalUnAcceptRowSize);
					
					var totalSize = parseInt(data.totalSize);
					var successInsertRowsSize = parseInt(data.successInsertRowsSize);
					var successUpdateRowsSize = parseInt(data.successUpdateRowsSize);
					
					message = "&nbsp;&nbsp;<font color='red'>导入成功。</font>共[<font color='text-green' style='font-weight: bold;'>"+totalSize+"</font>]条，" +
					"插入[<font color='text-green' style='font-weight: bold;'>"+data.successInsertRowsSize+"</font>]条，修改[<font color='text-green' style='font-weight: bold;'>"+data.successUpdateRowsSize+"</font>]条。";
					
					$('#cwsj').empty().html('&nbsp;&nbsp;无异常数据');
				}
				closeDrWaiting();
				$('#dr_result').show();
				$("#importResult").html(message);
			}else if(data.version == "v2"){//v2版本
				
				var message = "";
				
				if(data.result == -1){
					return $.alert("导入错误！请重新导入。");
				}else if(data.result > 0 ){//导入成功
					
					var totalSize = parseInt(data.totalSize);
					var totalUnAcceptRowSize = data.totalUnAcceptRowSize;
					var successInsertRowsSize = parseInt(data.successInsertRowsSize);
					var successUpdateRowsSize = parseInt(data.successUpdateRowsSize);
					
					$("#drzx").val(1);
					$("#totalUnAcceptRowSize").val(totalUnAcceptRowSize);
					
					message = "&nbsp;&nbsp;<font color='red'>导入处理完成。</font>" +
							"共[<font color='text-green' style='font-weight: bold;'>"+totalSize+"</font>]条，" +
							"插入[<font color='text-green' style='font-weight: bold;'>"+data.successInsertRowsSize+"</font>]条，" +
							"修改[<font color='text-green' style='font-weight: bold;'>"+data.successUpdateRowsSize+"</font>]条，" +
							"错误[<font color='red' style='font-weight: bold;'>"+data.totalUnAcceptRowSize+"</font>]条";
					
					if(totalUnAcceptRowSize > 0){
						var a = jQuery('<a href="javascript:void(0);" class="name" onclick="downloadErrorData(\''+ data.resultFileId +'\');"><font color="red" style="font-weight: bold;">点击下载异常数据继续编辑导入</font></a>');
						$('#cwsj').empty().append('&nbsp;&nbsp;').append(a);
					}else{
						$('#cwsj').empty().html('&nbsp;&nbsp;无异常数据');
					}
				}else{//导入失败 v2版本不会出现这种情况
					
				}
				closeDrWaiting();
				$('#dr_result').show();
				$("#importResult").html(message);
			}else{
				console.log("error version:"+data.version);
			}
		}
	}, "json");
}

function setDrWaiting(){
	if($("#dr-waiting").length > 0){
		$("#dr-waiting").show();
	}else{
		$("#dr_result").before('<div id="dr-waiting" class="text-center"><i class="fa fa-spin fa-spinner fa-2x fa-fw"></i> 数据处理中...</div>');
	}
}

function closeDrWaiting(){
	$("#dr-waiting").hide();
}