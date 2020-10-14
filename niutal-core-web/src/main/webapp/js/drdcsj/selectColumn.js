function selectAll() {
	$("#drl").find("a").each(function() {
		if(jQuery(this).attr('valid') == '1' &&
				jQuery(this).attr('locked') == '0' &&
				jQuery(this).attr('select-status') == '0'){
			jQuery(this).attr('select-status', '1');
			jQuery(this).toggleClass('cur').toggleClass('select');
		}
	});
}
function unselect() {
	$("#drl").find("a").each(function() {
		if(jQuery(this).attr('valid') == '1' &&
				jQuery(this).attr('locked') == '0' &&
				jQuery(this).attr('select-status') == '1'){
			jQuery(this).attr('select-status', '0');
			jQuery(this).toggleClass('cur').toggleClass('select');
		}
	});
	
}
function setCrfs(crfsModel){
	var cr="<label style='cursor:pointer;'>";
		cr+="<input type='radio' name='crfs' value='1' onclick='loadSelectColumn();'/>";
		cr+="插入";
		cr+="</label>";
	var gx="<label style='cursor:pointer;'>";
		gx+="<input type='radio' name='crfs' value='2' onclick='loadSelectColumn();'>";
		gx+="更新 ";
		gx+="</label>";
	var gxcr="<label style='cursor:pointer;'>";
		gxcr+="<input type='radio' name='crfs' value='3' onclick='loadSelectColumn();'>";
		gxcr+="插入并更新 ";
		gxcr+="</label>";
	if(!crfsModel){
			//没有配置默认初始化“插入”
			$("td[name='crfstd']").html(cr);
		}else{
		var crfs=crfsModel.crfs.split(",");
		// 1 仅插入、
		// 2 仅更新、
		// /3 插入并更新
		for(var i=0;i<crfs.length;i++){
			if(crfs[i]=="1"){
				$("td[name='crfstd']").append($(cr));
			}else if(crfs[i]=="2"){
				$("td[name='crfstd']").append($(gx));
			}else if(crfs[i]=="3"){
				$("td[name='crfstd']").append($(gxcr));
			}
		}
	}
	return $("[name='crfs']").first();
	//$("[name='crfs']").first().click();
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
	var url=_path + '/niutal/dr/import_getImportColumn.html';
	$.ajax( {
		type : "POST",
		url : url,
		data : {
			drmkdm : $("#drmkdm").val()
		},
		success : function(data) {
			//缓存数据
			jQuery('#drmkdm').data('cache_date' , data);
			$("#drl").html("");
			var crfs=$("[name='crfs']:checked").val();
			var excelColumn=data[0];
			var importColumn=data[1];
			var firstCrfs = $("[name='crfs']").first();
			if($.trim($("td[name='crfstd']").html())==""){
				firstCrfs = setCrfs(data[2]);
				firstCrfs.click();
			}
			var message="<table id='showerror' width='100%' border='0' class='formlist'><thead><tr><td>列名称</td><td>提示信息</td></tr></thead><tbody>";
			var ishaveError=false;
			// 验证用户导入的列是否可以导入
			for(var i in excelColumn){
					var a = jQuery("<a href='javascript:void(0);' id='drlpz"+i+"' value='"+excelColumn[i]+"' name='drlpz'></a>");
					var input = jQuery("<input type='hidden' name='drlpzid' value='"+getImportDrlId(excelColumn[i],importColumn)+"' />");
					var map=getErrorMessage(excelColumn[i], importColumn);
					//如果该列不合法
					if(map.notselect){
						a.attr('valid' , '0');
						a.addClass('noselect');
						a.attr('title', '不合法列，将不进行导入');
						a.html("<font color='red'>"+excelColumn[i]+"</font>");
						var error="<tr style='font-weight:bold;'><td>"+map.key+"</td>"+"<td><font color='blue'>"+map.message+"</font></td></tr>";
						message+=error;
						ishaveError=true;
					}else if(map.iszj){
						a.attr('valid' , '1');
						a.attr('select-status', '1');
						a.attr('locked', '1');
						a.addClass('dis');
						a.attr('title', '必填列，不允许取消');
						a.html(excelColumn[i]);
					}else if(crfs != "2" && map.isbt){
						a.attr('valid' , '1');
						a.attr('select-status', '1');
						a.attr('locked', '1');
						a.addClass('dis');
						a.attr('title', '必填列，不允许取消');
						a.html(excelColumn[i]);
					}else{
						a.attr('select-status', crfs == '2' ? '0' : '1');
						a.attr('valid' , '1');
						a.attr('locked', '0');
						a.attr('title', '该列为选择性导入列,如无需导入，可点击取消');
						a.addClass(crfs == '2' ? 'select' : 'cur');
						a.html(excelColumn[i]);
						a.click(function(){
							jQuery(this).toggleClass('cur').toggleClass('select');
							var select_status = jQuery(this).attr('select-status');
							if(select_status == '0'){
								jQuery(this).attr('select-status' , '1');
							}else{
								jQuery(this).attr('select-status' , '0');
							}
						});
					}
					$("#drl").append(a).append(input);
			}
			if(ishaveError){
				message+="</tbody></table>";
				$("#message").html(message);
			}
			if(!clbtl(excelColumn,importColumn,ishaveError)){
				$("#save").attr("disabled","disabled");
				$("#save").css("color","#cccccc");
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
	var crfs=$("[name='crfs']:checked").val();
	// 验证用户导入的列是否合法（主键、必填列必须在模板中存在）
	var isok=true;
	var error="";
	var message="<table id='showerror' width='100%' border='0' class='formlist'><thead><tr><td>列名称</td><td>提示信息</td></tr></thead><tbody>";
	for(var j in importColumn){
		if(importColumn[j].sfzj=="1"&&!sfycl(importColumn[j].drlmc,excelColumn)){// 主键
			error+="<tr style='font-weight:bold;'><td>"+importColumn[j].drlmc+"</td>"+"<td><font color='red'>主键列，列必须存在导入模板中</font></td></tr>";
			isok=false;
		}else if(crfs!="2"&&importColumn[j].sfbt=="1"&&!sfycl(importColumn[j].drlmc,excelColumn)){
			error+="<tr style='font-weight:bold;'><td>"+importColumn[j].drlmc+"</td>"+"<td><font color='red'>必填列，列必须存在导入模板中</font></td></tr>";
			isok=false;
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
function getErrorMessage(cloumnName,importColumn){
	var map={};
	var ishave=false;
	for(var i =0;i<importColumn.length;i++){
		if(cloumnName==importColumn[i].drlmc){
			if(importColumn[i].sfzj=="1"){
				map.iszj=true;
			}else if(importColumn[i].sfbt=="1"){
				map.isbt=true;
			}
			ishave=true;
		}
	}
	if(!ishave){
		map.notselect=true;
		map.key=cloumnName;
		map.message="没有对应列，此列不导入";
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
	var parent = getParentDialogWin();
	var selectId = new Array();
	var selectCloumn = new Array();
	$("a[name='drlpz']").each(function() {
		if(jQuery(this).attr('valid') == '1' &&
				jQuery(this).attr('select-status') == '1'){
			selectCloumn.push(jQuery(this).text());
			selectId.push(jQuery(this).next().val());
		}
	});
	if (selectCloumn.length <= 0) {
		alert("请选择需要导入的列");
		return false;
	}
	parent.$("#drlpzs").val(selectCloumn.join(","));
	parent.$("#drlpzids").val(selectId.join(","));
	var crfs = $("input[name='crfs']:checked").val();
	parent.$("#crfs").val(crfs);
	parent.$("#import").css("color","").removeAttr("disabled");
	parent.$("#drzx").val("0");
	parent.$("#importResult").html("&nbsp;&nbsp;无");
	parent.saveImport();
	iFClose();
}