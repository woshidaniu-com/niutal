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
									//$("#topButton").click();
									window.location.reload(); 
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
	
	<#if func_element.element_type?exists>
	$("#btn_element_bind_${func_element.element_guid?string}").off("click").killFocus().on("click",function(event){
	 	event.stopImmediatePropagation(); 
	 	<#--1:'查询条件',2:'脚本控件',3:'普通字段-->
	 	//弹窗
	 	<#if func_element.element_type == '1'>
	 	$.showDialog(_path+"/design/designFunc_zjysstFuncElementEntityQuery.html" ,"绑定/编辑查询条件",$.extend(true,{},modifyConfig,{
			"width"		: "600px",//$("#yhgnPage").innerWidth()
	 	<#elseif func_element.element_type == '2'>
	 	$.showDialog(_path+"/design/designFunc_zjysstFuncElementEntityWidget.html" ,"绑定/编辑功能组件",$.extend(true,{},modifyConfig,{
			"width"		: "900px",//$("#yhgnPage").innerWidth()
	 	<#elseif func_element.element_type == '3'>
	 	$.showDialog(_path+"/design/designFunc_zjysstFuncElementEntityFields.html" ,"绑定/编辑元素字段",$.extend(true,{},modifyConfig,{
			"width"		: "1024px",//$("#yhgnPage").innerWidth()
	 	</#if>
			"modalName"	: "modifyElement",
			"offAtOnce"	: true,
			"data"		: {
				"elementModel.func_code"	: '${parameters.func_code?string}',
				"elementModel.opt_code"		: '${parameters.opt_code?string}',
				"elementModel.func_guid"	: '${parameters.funcModel.func_guid?string}',
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
									//$("#topButton").click();
									window.location.reload(); 
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
	 </#if>
	 
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
						"elementModel.element_guid"	: '${func_element.element_guid?string}'
					},
					success	: function(responseText){
						if(responseText.indexOf("成功") > -1){
							$.success(responseText,function() {
								window.location.reload(); 
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
	 
	 $("#btn_bind_resource").off("click").killFocus().on("click",function(event){
	 	event.stopImmediatePropagation(); 
		//弹窗
		$.showDialog(_path+"/design/designFunc_xgzdyysFuncElement.html","编辑元素",$.extend(true,{},modifyConfig,{
			"width"		: "800px",
			"modalName"	: "modifyElement",
			"offAtOnce"	: true,
			"data"		: {
				"elementModel.func_code"	: '${parameters.func_code?string}',
				"elementModel.opt_code"		: '${parameters.opt_code?string}',
				"elementModel.func_guid"	: '${parameters.funcModel.func_guid?string}',
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
									//$("#topButton").click();
									window.location.reload(); 
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
	 
	 $("#btn_edit_resource").off("click").killFocus().on("click",function(event){
	 	event.stopImmediatePropagation(); 
		//弹窗
		$.showDialog(_path+"/design/designFunc_xgzdyysFuncElement.html","编辑元素",$.extend(true,{},modifyConfig,{
			"width"		: "800px",
			"modalName"	: "modifyElement",
			"offAtOnce"	: true,
			"data"		: {
				"elementModel.func_code"	: '${parameters.func_code?string}',
				"elementModel.opt_code"		: '${parameters.opt_code?string}',
				"elementModel.func_guid"	: '${parameters.funcModel.func_guid?string}',
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
									//$("#topButton").click();
									window.location.reload(); 
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
	 
	</#if>
</#if>