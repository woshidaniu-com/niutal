/**
 *公用自定义导出功能相关JS 
 */
//保存配置
function saveConfig(){
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
		alert("请选择您要导出的列！");
		return false;
	}
	

	//showWindow('增加',400,300,_path+"/zfxg/xydm/zjXydm.html");
	
	//未修改过设置直接导出
	//if (!isModify){
	//	doExport();
	//	return;
	//}
	
	////弹出对话框选择是新增设置还是更新设置
	alertPrompt('输入设置名称', function(){
		var api = frameElement.api, W = api.opener;
		var data = W.jQuery('#alertPrompt_text').val();
		if(data != undefined || jQuery.trim(data) != ''){
			jQuery.post(
					_path+"/niutal/drdc/export_saveCustomConfig.html",
					{
						"dcclbh":jQuery("#dcclbh").val(),
					 	"selectZd":selectCol.toString(),
					 	"unselectZd":unselectCol.toString(),
					 	"exportPHID":defvalID,
					 	"exportPHMC":data
					 },
					function(data){
						if (Boolean(data)){
							//jQuery("#exportButton").click();
							window.location.reload();
						} else {
							alert("导出设置保存失败！");
						}
					}
			);
		}
	},'提示',defval==undefined?"默认导出配置名称":defval,{});
	
	
}

//直接导出
function doExport(){
	jQuery("#selectUl").find(":input").attr("name","exportModel.selectCol");
	if (jQuery("#selectUl").find(":input").length == 0){
		alert("请选择您要导出的列！");
		return false;
	}
	var pWin = window.parent;
	var pDoc;
	if(window.parent.document.getElementById('ifm') != undefined){
		//IE<8 存在兼容性问题
		pDoc = pWin.document.getElementById('ifm').contentDocument;
		if(pDoc == undefined){
			pDoc = pWin.document.getElementById('ifm').contentWindow.document;
		}
	}else if(getParentDialogWin() != undefined){
		pDoc = getParentDialogWin().document;
	}else{
		pDoc = pWin.document
	}
	var pCol = jQuery("input[name$=selectCol]",pDoc);
	if (pCol.length > 0){
		pCol.remove();
	}
	var pForm = jQuery(pDoc).find("form:not([id='teaPageForm'])").eq(0);
	pForm.append(jQuery("#selectUl").find(":input").clone());
	
	pForm.find("input[name$='exportWjgs']").remove();	
	var exportWjgs = jQuery(":radio[name='exportWjgs']:checked").val();
	var wjgsHtml = "<input type='hidden' name='exportModel.exportWjgs' value='"+exportWjgs+"'>";
	pForm.append(wjgsHtml);	
	return true;
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

//删除偏好设置
function deletePh(id,mc,$this){
	alertConfirm("确认要删除 ‘<b>"+mc+"</b>’ 配置信息？",function(){
		jQuery.post(_path+"/niutal/drdc/export_deleteCustomConfig.html?_t="+new Date().getTime(),{"exportPHID":id},function(data){
			if (Boolean(data)){
				window.location.reload();
			} else {
				alert("操作失败！");
			}
		},'json');
	},function(){},"提示");
}

//选择偏好设置
function selectPh(id,$this){
	defval = jQuery($this).text();
	defvalID = id;
	jQuery('.kzlist ul li a').removeClass('cur');
	jQuery($this).parent('a').addClass('cur');
	jQuery.post(_path+"/niutal/drdc/export_cxConfigPhZdList.html",
			{"id": id},
			function(data){
				jQuery('#selectUl li span').click();
				//遍历偏好设置
				jQuery.each(data,function(i,n){
					var zd = n['zd'];
					var zdmc = n['zdmc'];
					var el = jQuery('#unselectUl').find('input[name="unselectCol"]'+'[value="' + zd + '@' + zdmc + '"]');
					if(el.size() > 0){
						el.parent().next('.choose_wx:first').click();
					}
				});
			},
			'json');
}