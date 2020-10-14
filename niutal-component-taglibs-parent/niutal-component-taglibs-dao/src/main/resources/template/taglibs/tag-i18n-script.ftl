<#--  国际化脚步模板.  -->
;(function($){

	/** Map holding bundle keys (if mode: 'map') */
	$.i18n.map = $.i18n.map || {};
	
	<#if parameters.pairList?exists && parameters.pairList?size != 0>  
	$.extend($.i18n.map,{
	<#list parameters.pairList as pairModel>
	<#lt/>"${pairModel.key?string}":"${pairModel.value?default('')}"<#rt/>
	<#lt/><#if pairModel_has_next>,</#if><#rt/>
	</#list>
	});
	</#if>
	 
}(jQuery));
