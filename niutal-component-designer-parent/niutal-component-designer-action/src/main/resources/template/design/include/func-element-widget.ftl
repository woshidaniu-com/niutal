<#if func_element.element_widget?exists>  
<#assign widget_name='${func_element.element_widget.widget_name?lower_case}'/>
<#if (widget_name?index_of('jqgrid') > -1) >  
	<!-- JQGrid-start  -->
	<#if parameters.opt_code == 'cx'>
	<input type="hidden" value="JW_${parameters.func_code?upper_case}_CX" name="dcclbh" id="export_dcclbh">
	<table id="tabGrid_${parameters.funcModel.func_guid?default("cx")}"  <#rt/>
		data-related_guid="${parameters.funcModel.func_guid}" <#rt/>
		data-widget_guid="${func_element.element_widget.widget_guid?string}" <#rt/>
		data-func_widget_guid="${func_element.element_widget.func_widget_guid?string}"></table>
	<div id="pager_${parameters.funcModel.func_guid?default("cx")}"></div>
	<#else>
	<input type="hidden" value="JW_${parameters.func_code?upper_case}_${parameters.opt_code?upper_case}" name="dcclbh" id="export_dcclbh">
	<table id="tabGrid_${func_element.element_widget.widget_guid?default("cx")}" <#rt/>
		data-related_guid="${func_element.element_related_guid?default('')}" <#rt/>
		data-widget_guid="${func_element.element_widget.widget_guid?string}" <#rt/>
		data-func_widget_guid="${func_element.element_widget.func_widget_guid?string}"></table>
	<div id="pager_${func_element.element_widget.widget_guid?string}"></div>
	</#if>
	<!-- JQGrid-end  -->
</#if> 
</#if>