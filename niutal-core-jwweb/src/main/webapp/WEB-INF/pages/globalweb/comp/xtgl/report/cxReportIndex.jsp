<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<meta http-equiv="X-UA-Compatible" content="IE=9">  
	<s:if test="searchType==\"h\"">
		<%@ include file="/WEB-INF/pages/globalweb/head/searchbox.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
	</s:if>
</head>
<body style="height:100%">
	<%--高级查询--%>
	<s:if test="searchType==\"h\"">
	<div class="row">
		<div class="col-sm-12 col-md-12">
			<div id="searchBox"></div>
		</div>
	</div>
	</s:if>
	<s:else>
		<%--普通查询--%>
		<s:if test="searchType==\"n\"">
		<form id="reportSearchForm" class="form-horizontal sl_all_form" role="form"  action="" method="post" target="reportFrame" name="FRform">
			<div class="row">
				<s:iterator value="paras" var="p">
		        	<div class="col-md-4"><div class="form-group">
						<label for="" class="control-label col-sm-4"><s:property value="#p.text"/></label>
						<div class="col-sm-8">
						<s:if test="#p.shape == \"select\"">
							<s:select id="%{#p.index}" name="%{#p.name}" list="%{#p.list}" listKey="key" listValue="value" value="%{#p.value}"
							 headerKey="%{#p.headerKey}" headerValue="%{#p.headerValue}" cssClass="form-control chosen-select"/>
						</s:if>
						<s:elseif test="#p.shape == \"input\"">
							<input id="%{#p.id}" name="%{#p.name}" cssClass="form-control"/>
						</s:elseif>
						</div>
					</div></div>
		        </s:iterator>
			</div>
		    <script type="text/javascript">
		    $(".chosen-select").trigger("chosen");
		    </script>
		    <div class="row sl_aff_btn">
				<div class="col-sm-12">
					<button id="btn_tj" type="button" class="btn btn-primary btn-sm" ><span class="glyphicon glyphicon-search"/></span> 查询</button>
				</div>
			</div>
	    </form>
		</s:if>
		<s:else>
			<form id="reportSearchForm" class="form-horizontal sl_all_form" role="form"  action="" method="post" target="reportFrame" name="FRform"  style="display: none">
				<s:iterator value="requestMap">
					<input type="hidden" name="<s:property value="key" />" value="<s:property value="value" />"/>
				</s:iterator>			
			</form>
		</s:else>
	</s:else>			
	
	<div class="row" style="padding:10px 0px 15px 0px ;" id="page_bar">
		<div class="col-sm-12 col-md-12">
			<ul class="list-inline pull-right">
			<li><a id="page_first" class="btn btn-default btn-sm disabled" role="button"><span class='glyphicon glyphicon-fast-backward'/> 首页</a></li>
			<li><a id="page_prev" class="btn btn-default btn-sm disabled" role="button"><span class='glyphicon glyphicon-backward'/> 上一页</a></li>
			<li>第 <span id="page_cpage">1</span> 页/共 <span id="page_tpage">1</span> 页</li>
			<li><a id="page_next" class="btn btn-default btn-sm disabled" role="button"><span class='glyphicon glyphicon-forward'/> 下一页</a></li>
			<li><a id="page_last" class="btn btn-default btn-sm disabled" role="button"><span class='glyphicon glyphicon-fast-forward'/> 末页</a></li>
			<%=IndexAction.cxReportButtons(request.getParameter("gnmkdm"))%>
			</ul>
		</div>
	</div>
	<div id="frameContainer" style="min-height: 400px;" >
		<div id="frameContainer_tip" style="text-align:center;font-size: 30px;display: none;color: #999" >选择条件点击查询生成报表</div>
	</div>
	
</body>

<script type="text/javascript">
/*高级查询生成伪表单元素*/
function buildFormOption(options){
	var html = [];
	var map = {};
	$.each(options||{},function(key,val){
		var v = /(\[\d+\])$/ig;
		var keyName = key.replace(v, '');
		map[keyName] = map[keyName] || [];
		<s:iterator value="renderJs" var="js">
		<s:property value="#js"/>
		</s:iterator>
		map[keyName].push(val);
	});
	$.each(map, function(i,t){
		html.push("<input type='hidden' name='"+i+"' value='"+t.join(",")+"'>");
		
	});
	return html.join("");
}
function iframe_onload (){
    
}
function iframe_readystatechange(){//IE works
   //alert(document.getElementById("f").readyState);//interactive [prompt download file] complete
}
/*加载iframe*/
function loadFrame(){
	if($("#reportFrame").size()<=0){
		//计算位置
		//var margin_top	=	(jQuery("#frameContainer").innerHeight() - 200)/2;
		//	margin_top	= 	(margin_top>0) ? margin_top : 0;
		var html = [];
			//html.push('<p id="loading_status" class="text-center header smaller lighter" style="margin-top:'+margin_top+'px;"><i class="icon-spinner icon-spin orange bigger-600"></i></br>  <span class="bigger-160">报表正在载入数据中.请等待....</span></p>');
			html.push('<iframe id="reportFrame" name="reportFrame" frameborder="0" width="100%" height="400px" onload ="iframe_onload()" onreadystatechange="iframe_readystatechange();" style="display: block;" ></iframe>');
		$("#frameContainer").empty().append(html.join(""));
	}
}

jQuery(function($){

	if($("#frameContainer").size() > 0 ){
		var top = parseInt(($("#frameContainer").height() - $("#frameContainer_tip").innerHeight())/2);
		$("#frameContainer_tip").css({"padding-top":top+"px"}).show();
	}
	
	var base_url = _reportPath + "/ReportServer?reportlet=${reportID}&__showtoolbar__=false";
	
	//报表打印
	if($("#btn_report_dy").size()>0){
		$("#btn_report_dy").wrap("<div class='btn-group'></div>");
		var ulHtml = "<ul class='dropdown-menu' style='margin-top:2px;left:-62px' role='menu'>" +
			"<li><a href='#' id='report_pdf_print'><span class='glyphicon glyphicon-print'/> PDF打印  </a></li>" +
			"<li><a href='#' id='report_applet_print'><span class='glyphicon glyphicon-print'/> Applet打印 </a></li>" +
			"<li><a href='#' id='report_flash_print'><span class='glyphicon glyphicon-print'/> Flash打印 </a></li></ul>";
		$("#btn_report_dy").addClass("dropdown-toggle").attr("data-toggle", "dropdown").append(" <span class='caret'/>").parent().append(ulHtml);	

		$("#report_pdf_print").click(function(){
			$("#reportFrame")[0].src = base_url + "#pdfPrint_"+new Date().getTime(); 
		});	
		$("#report_applet_print").click(function(){
			$("#reportFrame")[0].src = base_url + "#appletPrint_"+new Date().getTime();  
		});	
		$("#report_flash_print").click(function(){
			$("#reportFrame")[0].src = base_url + "#flashPrint_"+new Date().getTime();  
		});	
	}	   
	
	//报表输出
	if($("#btn_report_sc").size()>0){
		$("#btn_report_sc").wrap("<div class='btn-group'></div>");
		var ulHtml = "<ul class='dropdown-menu' style='margin-top:2px;left:-80px' role='menu'>" +
			"<li><a href='#' id='report_outPDF'><span class='glyphicon glyphicon-file'/> PDF</a></li>" +
			"<li><a href='#' id='excelfydc'><span class='glyphicon glyphicon-th-large'/> EXCEL分页</a></li>" +
			"<li><a href='#' id='excelyydc'><span class='glyphicon glyphicon-th-large'/> EXCEL不分页</a></li>" +
			"<li><a href='#' id='excelfsdc'><span class='glyphicon glyphicon-th-large'/> EXCEL分页分Sheet</a></li>" +
			"<li><a href='#' id='report_outWord'><span class='glyphicon glyphicon-pencil'/> WORD</a></li></ul>";
		$("#btn_report_sc").addClass("dropdown-toggle").attr("data-toggle", "dropdown").append(" <span class='caret'/>").parent().append(ulHtml);	

		$("#report_outPDF").click(function(){
			$("#reportFrame")[0].src = base_url + "#outPDF_"+new Date().getTime(); 
		});	
		$("#excelfydc").click(function(){
			$("#reportFrame")[0].src = base_url + "#excelfydc_"+new Date().getTime(); 
		});	
		$("#excelyydc").click(function(){
			$("#reportFrame")[0].src = base_url + "#excelyydc_"+new Date().getTime(); 
		});	
		$("#excelfsdc").click(function(){
			$("#reportFrame")[0].src = base_url + "#excelfsdc_"+new Date().getTime(); 
		});	
		$("#report_outWord").click(function(){
			$("#reportFrame")[0].src = base_url + "#outWord_"+new Date().getTime();
		});		
	}	   
	
	if('${searchType}'=='h'){ //高级查询
		var modelList = jQuery.parseJSON('${paras}');
		
		$.each(modelList, function(index, item){
			if($.defined(item.url)){
				item.url = _path+item.url;
			}
			if($.defined(item.leftURL)){
				item.leftURL = _path+item.leftURL;
			}
			if($.defined(item.rightURL)){
				item.rightURL = _path+item.rightURL;
			}
		});
		$("#searchBox").searchBox({
			showSize:8,
			searchFilter:false,
			autoSearch:false,
			onSearchClick:function(paramMap){
				loadFrame();
				if($("#hreportSearchForm").size()<=0){
					$("#searchBox").after("<form id='hreportSearchForm' method='post' target='reportFrame' action='"+ _reportPath +"/ReportServer?reportlet=${reportID}&__showtoolbar__=false'>");
				}
				$("#hreportSearchForm").empty().append(buildFormOption(paramMap)).submit();
			},
			model: modelList
		});
	}else if('${searchType}'=='n'){ //普通查询
		$("#btn_tj").click(function(){
			loadFrame();
    		$("#reportSearchForm").submit();
    	});
    	document.getElementById("reportSearchForm").action= _reportPath+ "/ReportServer?reportlet=${reportID}&__showtoolbar__=false";
	}else{ 
		//不需要查询
		loadFrame();
		document.getElementById("reportSearchForm").action= _reportPath+ "/ReportServer?reportlet=${reportID}&__showtoolbar__=false";
		$("#reportSearchForm").submit();
	}

	/*分页*/
	$("#page_first").click(function(){
		var reportFrame = $("#reportFrame");
		reportFrame[0].src = _reportPath + "/ReportServer?reportlet=${reportID}&__showtoolbar__=false#first_"+new Date().getTime(); 
	});
	$("#page_prev").click(function(){
		var reportFrame = $("#reportFrame");
		reportFrame[0].src = _reportPath + "/ReportServer?reportlet=${reportID}&__showtoolbar__=false#prev_"+new Date().getTime(); 
	});
	$("#page_next").click(function(){
		var reportFrame = $("#reportFrame");
		reportFrame[0].src = _reportPath + "/ReportServer?reportlet=${reportID}&__showtoolbar__=false#next_"+new Date().getTime(); 
	});
	$("#page_last").click(function(){
		var reportFrame = $("#reportFrame");
		reportFrame[0].src = _reportPath + "/ReportServer?reportlet=${reportID}&__showtoolbar__=false#last_"+new Date().getTime(); 
	});

	$("#btn_dy2").click(function(){
		var reportFrame = $("#reportFrame");
		reportFrame[0].src = _reportPath + "/ReportServer?reportlet=${reportID}&__showtoolbar__=false#print_"+new Date().getTime(); 
	});
});
</script>
</html>
