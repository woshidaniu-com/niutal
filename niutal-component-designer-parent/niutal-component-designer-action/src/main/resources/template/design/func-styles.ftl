<#if parameters.funcModel?exists>
<style type="text/css">
<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
.design_btn_empty{
	border: 2px dashed  gray;
	border-radius: 6px;
	font-size: 18px;
	line-height: 1;
	padding: 6px 16px;
	text-align: center;
}
.design_empty{
	border: 2px dashed  #e6e6e6;
	border-radius: 6px;
	font-size: 18px;
	line-height: 1.33;
	padding: 20px 16px;
	text-align: center;
	margin-bottom: 10px;
}

.sl_add_btn  {
	margin-bottom: 15px;
}
.sl_add_btn button {
	margin-bottom: 0px;
}

.btn-group > .btn-design:not(:last-child):not(.dropdown-toggle) { 
	border-right: medium none; 
	margin-right: 1px;
}
.btn-design { 
	border:  2px dashed gray;
}
.btn-design:hover,
.btn-design:focus,
.btn-design:active,
.btn-design.active,
.open > .dropdown-toggle.btn-design {   
	border:  2px dashed gray;
}
.btn-design-sm{
	font-size: 16px !important;
    height: 26px !important;
    line-height: 16px !important;
    padding: 2px !important;
}
</#if>
<#if parameters.file_resource_list?exists &&  parameters.file_resource_list?size != 0>
<#list parameters.file_resource_list as resourceModel>
<#if resourceModel.resource_type?exists && resourceModel.resource_type == '2'>
/*${resourceModel.resource_name?default('')}CSS*/
${resourceModel.resource_text?default('')}
<#rt/>
</#if>
</#list>
</#if>
</style>
</#if>