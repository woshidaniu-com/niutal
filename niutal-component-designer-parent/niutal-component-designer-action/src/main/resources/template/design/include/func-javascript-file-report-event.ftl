<#if func_element?exists>  
	<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
	 $("#btn_element_edit_${func_element.element_guid?string}").off("click").killFocus().on("click",function(event){
 		//弹窗
		$.showDialog(_path+"/design/designFunc_xgzdyysFuncElement.html","编辑元素",$.extend(true,{},modifyConfig,{
			"width"		: "800px",
			"modalName"	: "modifyElement",
			"offAtOnce"	: true,
			"data"		: {
				"elementModel.func_code"	: '${parameters.func_code?string}',
				"elementModel.opt_code"		: '${parameters.opt_code?string}',
				"elementModel.func_guid"	: '${parameters.funcModel.func_guid?string}',
				"elementModel.report_guid"	: '${parameters.funcReport.report_guid?default('')}',
				"elementModel.element_guid"	: '${func_element.element_guid?string}'
			},
			buttons:{
				success : {
					label : "确 定",
					className : "btn-primary",
					callback : function() {
						var $this = this;
						submitForm("ajaxForm",function(responseText,statusText){
							// responseText 可能是 xmlDoc, jsonObj, html, text, 等等...
							// statusText 	描述状态的字符串（可能值："No Transport"、"timeout"、"notmodified"---304 "、"parsererror"、"success"、"error"
							if(responseText.indexOf("成功") > -1){
								$.success(responseText,function() {
									$.closeModal("modifyElement");
									if($("#ReportModal").size() > 0){
										$("#ReportModal").reloadDialog();
									}else{
										$("#topButton").click();
									}
								});
							}else if(responseText.indexOf("失败") > -1){
								$.error(responseText,function() {
								});
							} else{
								$.alert(responseText,function() {
								});
							}
						});
						return false;
					}
				},
				cancel : {
					label : "关 闭",
					className : "btn-default"
				}
			}
		}));
	});
	
	$("#btn_element_bind_${func_element.element_guid?string}").off("click").killFocus().on("click",function(event){
	 	event.stopImmediatePropagation(); 
	 	<#--1:'查询条件',2:'脚本控件',3:'普通字段-->
	 	//弹窗
	 	$.showDialog(_path+"/design/designFunc_zjysstFuncElementEntityQuery.html" ,"绑定/编辑查询条件",$.extend(true,{},modifyConfig,{
			"width"		: "600px",//$("#yhgnPage").innerWidth()
			"modalName"	: "modifyElement",
			"offAtOnce"	: true,
			"data"		: {
				"elementModel.func_code"	: '${parameters.func_code?string}',
				"elementModel.opt_code"		: '${parameters.opt_code?string}',
				"elementModel.func_guid"	: '${parameters.funcModel.func_guid?string}',
				"elementModel.report_guid"	: '${parameters.funcReport.report_guid?default('')}',
				"elementModel.element_guid"	: '${func_element.element_guid?string}'
			},
			buttons:{
				success : {
					label : "确 定",
					className : "btn-primary",
					callback : function() {
						var $this = this;
						submitForm("ajaxForm",function(responseText,statusText){
							// responseText 可能是 xmlDoc, jsonObj, html, text, 等等...
							// statusText 	描述状态的字符串（可能值："No Transport"、"timeout"、"notmodified"---304 "、"parsererror"、"success"、"error"
							if(responseText.indexOf("成功") > -1){
								$.success(responseText,function() {
									$.closeModal("modifyElement");
									if($("#ReportModal").size() > 0){
										$("#ReportModal").reloadDialog();
									}else{
										$("#topButton").click();
									}
								});
							}else if(responseText.indexOf("失败") > -1){
								$.error(responseText,function() {
								});
							} else{
								$.alert(responseText,function() {
								});
							}
						});
						return false;
					}
				},
				cancel : {
					label : "关 闭",
					className : "btn-default"
				}
			}
		}));
	 });
	 
	 $("#btn_element_remove_${func_element.element_guid?string}").off("click").killFocus().on("click",function(event){
	 	event.stopImmediatePropagation(); 
		$.confirm('继续将删除元素数据！是否继续？',function(result) {
			if(result){
				$.ajax({
					type	: "POST",
					url		: _path+"/design/designFunc_sczdyysFuncElementData.html",
					async	: false ,
					data	: {
						"elementModel.func_code"	: '${parameters.func_code?string}',
						"elementModel.opt_code"		: '${parameters.opt_code?string}',
						"elementModel.func_guid"	: '${parameters.funcModel.func_guid?string}',
						"elementModel.report_guid"	: '${parameters.funcReport.report_guid?default('')}',
						"elementModel.element_guid"	: '${func_element.element_guid?string}'
					},
					success	: function(responseText){
						if(responseText.indexOf("成功") > -1){
							$.success(responseText,function() {
								if($("#ReportModal").size() > 0){
									$("#ReportModal").reloadDialog();
								}else{
									$("#topButton").click();
								}
							});
						}else if(responseText.indexOf("失败") > -1){
							$.error(responseText,function() {
								//$.closeModal("addModal");
							});
						} else{
							$.alert(responseText,function() {
								//$.closeModal("addModal");
							});
						}
					}
				});
			}
		});
	 });
	</#if>
</#if>