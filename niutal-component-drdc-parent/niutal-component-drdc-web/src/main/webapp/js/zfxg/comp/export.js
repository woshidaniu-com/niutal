/**
 *公用自定义导出功能相关JS 
 */
//保存配置
function saveConfig(form,callback){
	if(jQuery("#selectUl").size() > 0 ){
		var selectZd = jQuery("#selectUl").find(":input");
		var unselectZd = jQuery("#unselectUl").find(":input");
		var selectCol = new Array();
		var unselectCol = new Array();
		
		for (var i = 0 ; i < selectZd.length ; i++){
			selectCol[i]=selectZd.eq(i).val();
		}
		
		for (var i = 0 ; i < unselectZd.length ; i++){
			unselectCol[i]=unselectZd.eq(i).val();
		}
		
		
		if (selectCol.length == 0){
			$.alert("请选择您要导出的列！");
			return false;
		}else{
			//保存设置后导出
			$.ajaxSetup({async:false});
			$.post(_path+"/niutal/drdc/export_saveCustomConfig.html", {
				"dcclbh":$("#dcclbh").val(),
			 	"selectZd": encodeURIComponent(selectCol.toString()) ,
			 	"unselectZd":encodeURIComponent(unselectCol.toString())
			 }, function(data){
				if (Boolean(data)){
					//导出
					doExport(form,callback);
				} else {
					$.alert("导出失败！");
				}
			});
			$.ajaxSetup({async:true});
			return true;
		}	
	}else{
		return true;
	}
}

//直接导出
function doExport(pForm,callback){
	$("#selectUl").find(":input").attr("name","exportModel.selectCol");
	if ($("#selectUl").find(":input").length == 0){
		//$.alert("请选择您要导出的列！");
		return false;
	}else{
		var pCol = jQuery("input[name=selectCol]",window.parent.document);
		if (pCol.length > 0){
			pCol.remove();
		}
		pForm.append(jQuery("#selectUl").find(":input").clone());
		
		pForm.find("input[name$='exportWjgs']").remove();	
		var exportWjgs = jQuery(":radio[name='exportWjgs']:checked").val();
		var wjgsHtml = "<input type='hidden' name='exportModel.exportWjgs' value='"+exportWjgs+"'>";
		pForm.append(wjgsHtml);
		pForm.append("<input type='hidden' name='fileName' value='"+$("#exportFileName").val()+"'>");
		
		//提交
		if($.founded(callback)&&$.isFunction(callback)){
			return callback.call(this,pForm);
		}else{
			$(pForm).submit();
			return true;
		}
	}	
}

//点击加号
function select(obj){
	var li = jQuery(obj).parent();
	jQuery(obj).parent().appendTo(jQuery("#selectUl"));
	jQuery(obj).remove();
	li.append("<span class='choose_yx' onclick='unselect(this)'></span>");
	saveOrder();
}

//点击减号
function unselect(obj){
	var li = jQuery(obj).parent();
	jQuery(obj).parent().appendTo(jQuery("#unselectUl"));
	jQuery(obj).remove();
	li.append("<span class='choose_wx' onclick='select(this)'></span>");
	saveOrder();
}