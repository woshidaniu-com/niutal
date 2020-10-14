<#if parameters.funcModel?exists>
	<#lt/>
	<#if parameters.func_element_list?exists && parameters.func_element_list?size != 0>
	/*====================================元素组件js脚本开始====================================*/
	<#list parameters.func_element_list as func_element> 
	<#--  判断页面元素关联查询条件是否存在.  -->
	<#if func_element.element_query?exists> 
		<#include "/${parameters.templateDir}/design/include/func-javascript-file-element-query.ftl" />
	<#--  判断页面元素关联js组件是否存在.  -->
	<#elseif func_element.element_widget?exists>
		<#include "/${parameters.templateDir}/design/include/func-javascript-file-element-widget.ftl" />
	<#-- 判断页面元素关联字段是否存在.  -->
	<#elseif func_element.element_field?exists>
		<#include "/${parameters.templateDir}/design/include/func-javascript-file-element-normal.ftl" />
	<#else>
		<#include "/${parameters.templateDir}/design/include/func-javascript-file-element-event.ftl" />
	</#if>
	</#list>
	<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
	 $("#btn_element_new_${parameters.funcModel.func_guid?string}").off("click").killFocus().on("click",function(event){
 		//弹窗
		$.showDialog(_path+"/design/designFunc_zjzdyysFuncElement.html","添加新元素",$.extend(true,{},modifyConfig,{
			"width"		: "800px",
			"modalName"	: "addElement",
			"offAtOnce"	: true,
			"data"		: {
				"func_code"		: '${parameters.func_code?string}',
				"opt_code"		: '${parameters.opt_code?string}',
				"func_guid"		: '${parameters.funcModel.func_guid?string}',
				"max_ordinal"	: '${parameters.func_element_list?size + 1}'
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
									$.closeModal("addElement");
									$("#topButton").click();
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
	<#else>
	<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
	 $("#btn_element_new_${parameters.funcModel.func_guid?string}").off("click").killFocus().on("click",function(event){
 		//弹窗
		$.showDialog(_path+"/design/designFunc_zjzdyysFuncElement.html","添加新元素",$.extend(true,{},modifyConfig,{
			"width"		: "800px",
			"modalName"	: "addElement",
			"offAtOnce"	: true,
			"data"		: {
				"func_code"		: '${parameters.func_code?string}',
				"opt_code"		: '${parameters.opt_code?string}',
				"func_guid"		: '${parameters.funcModel.func_guid?string}',
				"max_ordinal"	: '1'
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
									$.closeModal("addElement");
									$("#topButton").click();
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
	/*====================================元素组件js脚本结束====================================*/
	</#if>
	<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
 	
	$("#btn_edit_resource_${parameters.funcModel.func_guid?string}").off("click").killFocus().on("click",function(event){
	 		//弹窗
			$.showDialog(_path+"/design/designResource_cxFuncResourceIndex.html","绑定、编辑（js/css）资源",$.extend(true,{},modifyConfig,{
				"width"		: "800px",
				"modalName"	: "resourceModal",
				"offAtOnce"	: true,
				"data"		: {
					"func_code"		: '${parameters.func_code?string}',
					"opt_code"		: '${parameters.opt_code?string}',
					"func_guid"		: '${parameters.funcModel.func_guid?string}'
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
										$.closeModal("resourceModal");
										$("#topButton").click();
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