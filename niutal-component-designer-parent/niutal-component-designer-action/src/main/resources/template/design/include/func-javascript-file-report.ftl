<#if parameters.funcReport?exists>
	<#lt/>
	<#if parameters.funcModel.query_type == '2'>
		<#--  高级查询  -->
		
	<#elseif parameters.funcModel.query_type == '1'>
	<#--  普通查询  -->
	<#if parameters.func_element_list?exists && parameters.func_element_list?size != 0> 
	/*====================================元素组件js脚本开始====================================*/
	<#list parameters.func_element_list as func_element> 
	<#--  判断页面元素关联查询条件是否存在.  -->
	<#if func_element.element_query?exists> 
		<#include "/${parameters.templateDir}/design/include/func-javascript-file-report-query.ftl" />
	<#else>
		<#include "/${parameters.templateDir}/design/include/func-javascript-file-report-event.ftl" />
	</#if>
	<#break>
	</#list>
	/*====================================元素组件js脚本结束====================================*/
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
				"report_guid"	: '${parameters.funcReport.report_guid?default('')}',
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
	</#if>
	</#if>
	</#if>

	<#--  判断是否绑定了报表  -->
	<#if parameters.funcReport.report_alias?exists>
	<#--  高级查询  -->
	<#if parameters.funcModel.query_type == '2'>
		
	<#elseif parameters.funcModel.query_type == '1'>
	<#--  普通查询  -->
	$("#reportSearchForm_${parameters.funcModel.func_guid?string}").attr("action",_reportPath+ "/ReportServer?reportlet=${parameters.funcReport.report_alias}.cpt&__showtoolbar__=true&__cumulatepagenumber__=false");
	<#else>
	<#--  不需要查询  -->
	loadFrame();
	$("#reportSearchForm_${parameters.funcModel.func_guid?string}").attr("action",_reportPath+ "/ReportServer?reportlet=${parameters.funcReport.report_alias}.cpt&__showtoolbar__=true&__cumulatepagenumber__=false").submit();;
	</#if>

	var base_url = _reportPath + "/ReportServer?reportlet=${parameters.funcReport.report_alias}.cpt&__showtoolbar__=true&__cumulatepagenumber__=false";

	<#-- 
	//报表打印
	var btn_dy = $("#btn_report_dy_${parameters.funcModel.func_guid?string}");
	if($(btn_dy).size()>0){
		$(btn_dy).wrap("<div class='btn-group'></div>");
		var ulHtml = "<ul class='dropdown-menu' style='margin-top:2px;left:-62px' role='menu'>" +
			"<li><a href='#' id='report_pdf_print_${parameters.funcModel.func_guid?string}'><span class='glyphicon glyphicon-print'/> PDF打印  </a></li>" +
			"<li><a href='#' id='report_applet_print_${parameters.funcModel.func_guid?string}'><span class='glyphicon glyphicon-print'/> Applet打印 </a></li>" +
			"<li><a href='#' id='report_flash_print_${parameters.funcModel.func_guid?string}'><span class='glyphicon glyphicon-print'/> Flash打印 </a></li></ul>";
		$(btn_dy).removeClass("disabled").addClass("dropdown-toggle").attr("data-toggle", "dropdown").append(" <span class='caret'/>").parent().append(ulHtml);	

		$("#report_pdf_print_${parameters.funcModel.func_guid?string}").click(function(){
			$("#reportFrame")[0].src = base_url + "#pdfPrint_"+new Date().getTime(); 
		});	
		$("#report_applet_print_${parameters.funcModel.func_guid?string}").click(function(){
			$("#reportFrame")[0].src = base_url + "#appletPrint_"+new Date().getTime();  
		});	
		$("#report_flash_print_${parameters.funcModel.func_guid?string}").click(function(){
			$("#reportFrame")[0].src = base_url + "#flashPrint_"+new Date().getTime();  
		});	
	}	   
	
	//报表输出
	var btn_report = $("#btn_report_sc_${parameters.funcModel.func_guid?string}");
	if($(btn_report).size()>0){
		$(btn_report).wrap("<div class='btn-group'></div>");
		var ulHtml = "<ul class='dropdown-menu' style='margin-top:2px;left:-80px' role='menu'>" +
			"<li><a href='#' id='report_outPDF_${parameters.funcModel.func_guid?string}'><span class='glyphicon glyphicon-file'/> PDF</a></li>" +
			"<li><a href='#' id='excelfydc_${parameters.funcModel.func_guid?string}'><span class='glyphicon glyphicon-th-large'/> EXCEL分页</a></li>" +
			"<li><a href='#' id='excelyydc_${parameters.funcModel.func_guid?string}'><span class='glyphicon glyphicon-th-large'/> EXCEL不分页</a></li>" +
			"<li><a href='#' id='excelfsdc_${parameters.funcModel.func_guid?string}'><span class='glyphicon glyphicon-th-large'/> EXCEL分页分Sheet</a></li>" +
			"<li><a href='#' id='report_outWord_${parameters.funcModel.func_guid?string}'><span class='glyphicon glyphicon-pencil'/> WORD</a></li></ul>";
		$(btn_report).removeClass("disabled").addClass("dropdown-toggle").attr("data-toggle", "dropdown").append(" <span class='caret'/>").parent().append(ulHtml);	

		$("#report_outPDF_${parameters.funcModel.func_guid?string}").click(function(){
			$("#reportFrame")[0].src = base_url + "#outPDF_"+new Date().getTime(); 
		});	
		$("#excelfydc_${parameters.funcModel.func_guid?string}").click(function(){
			$("#reportFrame")[0].src = base_url + "#excelfydc_"+new Date().getTime(); 
		});	
		$("#excelyydc_${parameters.funcModel.func_guid?string}").click(function(){
			$("#reportFrame")[0].src = base_url + "#excelyydc_"+new Date().getTime(); 
		});	
		$("#excelfsdc").click(function(){
			$("#reportFrame")[0].src = base_url + "#excelfsdc_"+new Date().getTime(); 
		});	
		$("#report_outWord_${parameters.funcModel.func_guid?string}").click(function(){
			$("#reportFrame")[0].src = base_url + "#outWord_"+new Date().getTime();
		});		
	}	   
	
	/*分页*/
	$("#page_first").click(function(){
		var reportFrame = $("#reportFrame");
		reportFrame[0].src = _reportPath + "/ReportServer?reportlet=${parameters.funcReport.report_alias}.cpt&__showtoolbar__=true&__cumulatepagenumber__=false#first_"+new Date().getTime(); 
	});
	$("#page_prev").click(function(){
		var reportFrame = $("#reportFrame");
		reportFrame[0].src = _reportPath + "/ReportServer?reportlet=${parameters.funcReport.report_alias}.cpt&__showtoolbar__=true&__cumulatepagenumber__=false#prev_"+new Date().getTime(); 
	});
	$("#page_next").click(function(){
		var reportFrame = $("#reportFrame");
		reportFrame[0].src = _reportPath + "/ReportServer?reportlet=${parameters.funcReport.report_alias}.cpt&__showtoolbar__=true&__cumulatepagenumber__=false#next_"+new Date().getTime(); 
	});
	$("#page_last").click(function(){
		var reportFrame = $("#reportFrame");
		reportFrame[0].src = _reportPath + "/ReportServer?reportlet=${parameters.funcReport.report_alias}.cpt&__showtoolbar__=true&__cumulatepagenumber__=false#last_"+new Date().getTime(); 
	});

	$("#btn_dy2").click(function(){
		var reportFrame = $("#reportFrame");
		reportFrame[0].src = _reportPath + "/ReportServer?reportlet=${parameters.funcReport.report_alias}.cpt&__showtoolbar__=true&__cumulatepagenumber__=false#print_"+new Date().getTime(); 
	});
	-->
	</#if>
</#if>