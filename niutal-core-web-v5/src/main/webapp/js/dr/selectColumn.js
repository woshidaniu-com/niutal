function selectAll() {
	$("#dr_select_column_Modal #drl").find("span[name='drlpz']").each(function() {
		if($(this).attr('valid') == '1' &&
				$(this).attr('locked') == '0' &&
				$(this).attr('select-status') == '0'){
			$(this).attr('select-status', '1');
			$(this).children(":checkbox").prop("checked",true);
		}
	});
}
function unselect() {
	$("#dr_select_column_Modal #drl").find("span[name='drlpz']").each(function() {
		if($(this).attr('valid') == '1' &&
				$(this).attr('locked') == '0' &&
				$(this).attr('select-status') == '1'){
			$(this).attr('select-status', '0');
			$(this).children(":checkbox").prop("checked",false);
		}
	});
	
}

function setCrfs(crfsModel){
	var cr="<label class='radio-inline'>";
		cr+="<input type='radio' name='crfs' value='1' onclick='loadSelectColumn();'/>";
		cr+="插入";
		cr+="</label>";
	var gx="<label class='radio-inline'>";
		gx+="<input type='radio' name='crfs' value='2' onclick='loadSelectColumn();'>";
		gx+="更新 ";
		gx+="</label>";
	var gxcr="<label class='radio-inline'>";
		gxcr+="<input type='radio' name='crfs' value='3' onclick='loadSelectColumn();'>";
		gxcr+="插入并更新 ";
		gxcr+="</label>";
	if(!crfsModel){
			//没有配置默认初始化“插入”
			$("#selectColumnForm").find("td[name='crfstd']").html(cr);
		}else{
		var crfs=crfsModel.crfs.split(",");
		// 1 仅插入、
		// 2 仅更新、
		// /3 插入并更新
		for(var i=0;i<crfs.length;i++){
			if(crfs[i]=="1"){
				$("#selectColumnForm").find("td[name='crfstd']").append($(cr));
			}else if(crfs[i]=="2"){
				$("#selectColumnForm").find("td[name='crfstd']").append($(gx));
			}else if(crfs[i]=="3"){
				$("#selectColumnForm").find("td[name='crfstd']").append($(gxcr));
			}
		}
	}
	return $("#selectColumnForm").find("[name='crfs']").first();
}

function addCloumnClick(){
	$(".select").bind("click",function(){
		if($(this).hasClass("cur")){
			$(this).attr("class","select");
		}else{
			$(this).attr("class","cur");
		}
	});
}
function loadSelectColumn() {
	$("#save").removeAttr("disabled");
	$("#save").css("color","#FFFFFF");
	var url=_path + '/niutal/dr/import/getImportColumn.zf';
	$.ajax( {
		type : "POST",
		url : url,
		data : {
			drmkdm : $("#drmkdm").val()
		},
		success : function(data) {
			//缓存数据
			$('#drmkdm').data('cache_date' , data);
			$("#drl").html("");
			var crfs=$("#selectColumnForm").find("input[name='crfs']:checked").val();
			var excelColumn=data[0];
			var importColumn=data[1];
			var firstCrfs = $("#selectColumnForm").find("input[name='crfs']").first();
			if($.trim($("td[name='crfstd']").html())==""){
				firstCrfs = setCrfs(data[2]);
				firstCrfs.attr("checked",true);
				crfs = firstCrfs.val();
			}
			if(crfs == '1'){
				$('#selectall').prop('checked',true);
			}
			var message="<table id='showerror' class='table table-condensed'><thead><tr><td>列名称</td><td>提示信息</td></tr></thead><tbody>";
			var ishaveError=false;
			// 验证用户导入的列是否可以导入
			$.each(excelColumn, function(i,n){
				var a = $("<span id='drlpz"+i+"' value='"+excelColumn[i]+"' name='drlpz' class='label label-info' style='margin:2px;display:inline-block;'></span>");
				var input = $("<input style='position:relative;tpp:4px;margin: 4px 5px 0 0;' type='checkbox' name='drlpzid' value='"+getImportDrlId(excelColumn[i],importColumn)+"' />");
				var map=getErrorMessage(excelColumn[i], importColumn,crfs);
				//如果该列不合法
				if(map.notselect){
					a.attr('valid' , '0');
					a.addClass('noselect');
					a.attr('title', '不合法列，将不进行导入');
					a.html(excelColumn[i]);
					var error="<tr style='font-weight:bold;'><td>"+map.key+"</td>"+"<td><font color='blue'>"+map.message+"</font></td></tr>";
					message+=error;
					ishaveError=true;
					a.removeClass("label-info").addClass("label-danger");
				}else if(map.iszj){
					a.attr('valid' , '1');
					a.attr('select-status', '1');
					a.attr('locked', '1');
					a.addClass('dis');
					a.attr('title', '必填列，不允许取消');
					a.html(excelColumn[i]);
					input.prop("disabled","disabled");
					input.prop("checked",true);
					a.addClass("dis");
					a.prepend(input);
				}else if(map.isbt){
					a.attr('valid' , '1');
					a.attr('select-status', '1');
					a.attr('locked', '1');
					a.addClass('dis');
					a.attr('title', '必填列，不允许取消');
					a.html(excelColumn[i]);
					input.prop("disabled","disabled");
					input.prop("checked",true);
					a.addClass("dis");
					a.prepend(input);
				}else{
					a.attr('select-status', crfs == '2' ? '0' : '1');
					a.attr('valid' , '1');
					a.attr('locked', '0');
					a.attr('title', '该列为选择性导入列,如无需导入，可点击取消');
					input.prop("checked", crfs == '2' ? false : true);
					a.html(excelColumn[i]);
					input.click(function(){
						if($(this).prop("checked") == true){
							$(this).parent().attr('select-status' , '1');
						}else{
							$(this).parent().attr('select-status' , '0');
						}
					});
					a.removeClass("label-info").addClass("label-success");
					a.prepend(input);
				}
				a.appendTo($("#drl"));
			});
			if(ishaveError){
				message+="</tbody></table>";
				$("#message").html(message);
			}
			if(!clbtl(excelColumn,importColumn,ishaveError)){
				$("#dr_select_column_Modal #btn_success").prop("disabled",true);
			}
		}
	}, "json");
}
function getImportDrlId(lmc,importColumn){
	for(var j in importColumn){
		if(importColumn[j].drlmc==lmc){
			return importColumn[j].drlpzid;
		}
	}
	return null;
}
function getImportDrl(lmc,importColumn){
	for(var j in importColumn){
		if(importColumn[j].drlmc==lmc){
			return importColumn[j];
		}
	}
	return null;
}
/**
 * 处理必填列
 * 
 * @param excelColumn
 * @param importColumn
 * @return
 */
function clbtl(excelColumn,importColumn,ishaveError){
	var crfs = $("[name='crfs']:checked").val();
	// 验证用户导入的列是否合法（主键、必填列必须在模板中存在）
	
	var isok = true;
	var message= "<table id='showerror' class='table table-bordered table-condensed'><thead><tr><th>列名称</th><th>提示信息</th></tr></thead><tbody>";
	
	var error = "";
	for(var i = 0;i< importColumn.length;i++){
		var n = importColumn[i];
		if(n.sfzj == "1" && !sfycl(n.drlmc,excelColumn)){// 主键
			error+="<tr style='font-weight:bold;'><td>"+n.drlmc+"</td>"+"<td><font color='red'>主键列，列必须存在导入模板中</font></td></tr>";
			isok=false;
		}else{
			var isbt = false;
			var sfbtFlagNumber = parseInt(n.sfbtFlag);
			if(crfs == "1"){//当操作是插入
				var flag = sfbtFlagNumber & 1;
				if(flag != 0){
					isbt = true;
				}
			}else if(crfs == "2"){//当操作是更新
				var flag = sfbtFlagNumber & 2;
				if(flag != 0){
					isbt = true;
					break;
				}
			}else if(crfs == "3"){//当操作是插入和更新
				var flag = sfbtFlagNumber & 4;
				if(flag != 0){
					isbt = true;
					break;
				}
			}
			if(isbt && !sfycl(n.drlmc,excelColumn)){
				error+="<tr style='font-weight:bold;'><td>"+n.drlmc+"</td>"+"<td><font color='red'>必填列，列必须存在导入模板中</font></td></tr>";
				isok=false;
			}
		}
	}
	// 是否已经有其他错误
	if(ishaveError){
		$("#showerror>tbody").append($(error));
	}else{
		message+=error;
		message+="</tbody></table>";
		$("#message").html(message);
	}
	return isok;
}
/**
 * 获取错误信息
 * 
 * @param cloumnName
 * @param importColumn
 * @return
 */
function getErrorMessage(cloumnName,importColumn,crfs){
	
	var map = {};
	var ishave = false;
	
	for(var i =0;i<importColumn.length;i++){
		
		if(cloumnName == importColumn[i].drlmc){
			ishave = true;
			if(importColumn[i].sfzj=="1"){//主键一定必填
				map.iszj = true;
				break;
			}else{//非主键
				var sfbtFlag = importColumn[i].sfbtFlag;
				var sfbtFlagNumber = parseInt(sfbtFlag);
				if(crfs == "1"){//当操作是插入
					var flag = sfbtFlagNumber & 1;
					if(flag != 0){
						map.isbt = true;
						break;
					}
				}
				if(crfs == "2"){//当操作是更新
					var flag = sfbtFlagNumber & 2;
					if(flag != 0){
						map.isbt = true;
						break;
					}
				}
				if(crfs == "3"){//当操作是插入和更新
					var flag = sfbtFlagNumber & 4;
					if(flag != 0){
						map.isbt = true;
						break;
					}
				}
				map.isbt = false;
				break;
			}
		}
	}
	if(!ishave){
		map.notselect = true;
		map.key = cloumnName;
		map.message = "没有对应列，此列不导入";
	}
	return map;
}
/**
 * 是否有此列
 * 
 * @param drlmc
 * @param excelColumn
 * @return
 */
function sfycl(drlmc,excelColumn){
	var isHave=false;
	for(var i in excelColumn){
		if(drlmc==excelColumn[i]){
			isHave=true;
		}
	}
	return isHave;
}
function saveselect() {
	var selectId = new Array();
	var selectCloumn = new Array();
	$("span[name='drlpz']").each(function() {
		if($(this).attr('valid') == '1' &&
				jQuery(this).attr('select-status') == '1'){
			selectCloumn.push($(this).text());
			selectId.push($(this).children().val());
		}
	});
	if (selectCloumn.length <= 0) {
		$.alert("请选择需要导入的列");
		return false;
	}
	$("#dr-component-main #drlpzs").val(selectCloumn.join(","));
	$("#dr-component-main #drlpzids").val(selectId.join(","));
	var crfs = $("#dr_select_column_Modal input[name='crfs']:checked").val();
	$("#dr-component-main #crfs").val(crfs);
	$("#dr-component-main #drzx").val("0");
	$("#dr-component-main #importResult").html("&nbsp;&nbsp;无");
	$('#dr_result').hide();
	saveImport();
}