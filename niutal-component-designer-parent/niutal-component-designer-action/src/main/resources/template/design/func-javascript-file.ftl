<#if parameters.funcModel?exists>
<#if parameters.funcReport?exists>
function iframe_onload (){
    
}
function iframe_readystatechange(){//IE works
   //alert(document.getElementById("f").readyState);//interactive [prompt download file] complete
}
/*加载iframe*/
function loadFrame(){
	if($("#reportFrame").size()<=0){
		//计算位置
		//var margin_top	=	($("#frameContainer_${parameters.funcModel.func_guid?string}").innerHeight() - 200)/2;
		//    margin_top	= 	(margin_top>0) ? margin_top : 0;
		var html = [];
			//html.push('<p id="loading_status" class="text-center header smaller lighter" style="margin-top:'+margin_top+'px;"><i class="icon-spinner icon-spin orange bigger-600"></i></br>  <span class="bigger-160">报表正在载入数据中.请等待....</span></p>');
			html.push('<iframe id="reportFrame" name="reportFrame" frameborder="0" width="100%" height="400px" onload ="iframe_onload()" onreadystatechange="iframe_readystatechange();" style="display: block;" ></iframe>');
		$("#frameContainer_${parameters.funcModel.func_guid?string}").empty().append(html.join(""));
	}
}
</#if>
jQuery(function($){
	
	//绑定AutoComplete组件
	if(jQuery.fn.autocomplete){
		jQuery('[data-toggle*="autocomplete"]').trigger("focus");
	}
	
	if($("#frameContainer_${parameters.funcModel.func_guid?string}").size() > 0 ){
		var top = parseInt(($("#frameContainer_${parameters.funcModel.func_guid?string}").height() - $("#frameContainer_tip_${parameters.funcModel.func_guid?string}").innerHeight())/2);
		$("#frameContainer_tip_${parameters.funcModel.func_guid?string}").css({"padding-top":top+"px"}).show();
	}
	
	<#if parameters.funcReport?exists>
	<#--  高级报表js脚本.  --><#rt/>
	<#include "/${parameters.templateDir}/design/include/func-javascript-file-report.ftl" /><#rt/>
	<#else>
	<#--  工具按钮区域js脚本.  --><#rt/>
	<#include "/${parameters.templateDir}/design/include/func-javascript-file-toolbar.ftl" /><#rt/>
	<#--  元素组件js脚本.  --><#rt/>
	<#include "/${parameters.templateDir}/design/include/func-javascript-file-element.ftl" /><#rt/>
	</#if>
});
</#if>