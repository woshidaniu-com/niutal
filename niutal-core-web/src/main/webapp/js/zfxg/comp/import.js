/**
 * 跳转至导入页面
 * @param drmkdm
 * @return
 */
function toImportData(drmkdm){
	if(drmkdm == null || drmkdm == ""){
		alert("导入模块代码不能为空。");
		return false;
	}
	var url=_path+'/niutal/drdc/import_toImportData.html'; 
	url+='?drmkdm='+drmkdm;
	
	showWindow('导入',600,420,url);
	return false;
}

//下载模板
function downloadTemplate(drwjgs){
	if(notNullImportTable()){
		if(drwjgs != null && drwjgs != ""){
			jQuery("#drwjgs").val(drwjgs);
		}else{
			jQuery("#drwjgs").val("xls");
		}
		var jqFrom=jQuery("#importForm");
		var url = _path+"/niutal/drdc/import_downloadTemplate.html";
		jqFrom.attr("action",url);
		jqFrom.submit();
	}
}


//下载错误数据
function downloadErrorData(){
	jQuery("#drwjgs").val("xls");

	var jqIsImport=jQuery("#isImport");
	var jqErrorDataNum=jQuery("#errorDataNum");
	if(jqIsImport.val() == "0"){
		alert("您还未导入数据或已导入失败！");
		return false;
	}
	if(jqErrorDataNum.val() == "0"){
		alert("本次导入没有错误信息！");
		return false;
	}
	var jqFrom=jQuery("#importForm");
	var url=_path+"/niutal/drdc/import_downloadErrorData.html";
	if(notNullImportTable()){
		jqFrom.attr("action",url);
		jqFrom.submit();
	}
	
}

//设置导入结果
//导入模式drms:1为update，0为insert
function setImportRsulte(data,drms){
	var isshow=false;
	jQuery("#drwjgs").val("xls");
	var reslutMsg="";
	var jqImportResult=jQuery("#importResult");
	var jqIsImport=jQuery("#isImport");
	var jqErrorDataNum=jQuery("#errorDataNum");
	if(data != null && data != ""){
		var drzs=data["drzs"];
		var cgs=data["cgs"];
		var cws=data["cws"];
		//设置是否导入成功
		jqIsImport.val("1");
		//设置错误数据
		jqErrorDataNum.val(cws);
		if(drms == "0"){
			reslutMsg="导入数据总数["+drzs+"]条，导入成功["+cgs+"]条，导入失败["+cws+"]条。";
			if(cws > 0){
				setTimeout(function(){
					alertConfirm("是否将失败数据中重复数据更新至系统？",function(){
						jQuery("#drms").val("1");
						jQuery("#drzs").val(drzs);
						jQuery("#cgs").val(cgs);
						jQuery("#cws").val(cws);
						importData();
						return;
					});
				},100);
			}else{
				isshow=true;
			}
		}else{
			isshow=true;
			var oldDrzs = jQuery("#drzs").val();
			var oldCgs = parseInt(jQuery("#cgs").val());
			var newCgs = cgs;
			var newCws = cws;
			if(oldCgs > 0){
				newCgs = oldCgs + parseInt(cgs);
			}
			reslutMsg="导入数据总数["+oldDrzs+"]条，导入成功["+newCgs+"]条，导入失败["+newCws+"]条。";

		}
	}else{
		isshow=true;
		reslutMsg="数据为空或不合法，导入失败，请参考模板重新进行导入！";
		//设置是否导入成功
		jqIsImport.val("0");
		//设置错误数据
		jqErrorDataNum.val("0");
	}
	jQuery("#drms").val("0");
	jqImportResult.html(reslutMsg);
	if(isshow){
		setTimeout(function(){
			alert("导入执行完成");
		},100);
	}
}

//验证表明不为空
function notNullImportTable(){
	var drmkdm=jQuery("#drmkdm").val();
	var drbm=jQuery("#drbm").val();
	if(drmkdm == null || drmkdm == ""){
		alert("当前页面出错,请重新进入!");
		return false;
	}
	if(drbm == null || drbm == ""){
		alert("请选择模板!");
		return false;
	}
	return true;
}

//验证导入文件不能为空
function notNullImportFile(){
	var importFile=jQuery("#importFile").val();
	if(importFile == null || importFile == ""){
		alert("请选择导入文件!");
		return false;
	}
	var fileSuffix=importFile.substring(importFile.lastIndexOf(".")+1,importFile.length);
	fileSuffix = fileSuffix.toLowerCase();
	if(fileSuffix != "xls"  && fileSuffix != "dbf" ){
		alert("请选择xls或dbf格式的文件进行导入!")
		return false;
	}
	jQuery("#drwjgs").val(fileSuffix);
	return true;
}

