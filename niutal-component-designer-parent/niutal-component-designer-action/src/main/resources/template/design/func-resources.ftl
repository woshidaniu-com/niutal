<#if parameters.funcModel?exists> 
<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
<div class="row ">
	<div class="col-md-12 col-sm-12">
		<div class="design_empty">
			<div class="btn-group width-40">
				<button class="btn btn-default btn-lg btn-design width-100" type="button" id="btn_edit_resource_${parameters.funcModel.func_guid?string}">
					绑定、编辑（js/css）资源
				</button>
			</div>
		</div>
	</div>
</div>
<!-- js/css resource start. -->

<!--jQuery.jqGrid -->
<link rel="stylesheet" type="text/css" href="${parameters.stylePath?html}/assets/plugins/jqGrid/css/jquery.jqgrid.css?ver=${parameters.cssVersion?default('')}" />
<script type="text/javascript" src="${parameters.stylePath?html}/assets/plugins/jqGrid/jquery.jqgrid.src-min.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
<script type="text/javascript" src="${parameters.stylePath?html}/assets/plugins/jqGrid/jquery.jqgrid.contact-min.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
<script type="text/javascript" src="${parameters.stylePath?html}/assets/plugins/jqGrid/lang/zh_CN.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
<script type="text/javascript" src="${parameters.systemPath?html}/js/plugins/jqGrid4.6/jquery.jqgrid.settings.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
<script type="text/javascript" src="${parameters.systemPath?html}/js/plugins/jqGrid4.6/jquery.jqGrid-min.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>

<!--jQuery.tooltip -->
<link rel="stylesheet" type="text/css" href="${parameters.stylePath?html}/assets/plugins/tooltips/css/tooltips-min.css?ver=${parameters.cssVersion?default('')}" />
<script type="text/javascript" src="${parameters.stylePath?html}/assets/plugins/tooltips/jquery.tooltips-min.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
<!--jQuery.adapt -->
<link rel="stylesheet" type="text/css" href="${parameters.stylePath?html}/assets/plugins/adapt/css/jquery.adapt-min.css?ver=${parameters.cssVersion?default('')}" />
<script type="text/javascript" src="${parameters.stylePath?html}/assets/plugins/adapt/jquery.adapt-min.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
<!--文件上传美化 -->
<script type="text/javascript" src="${parameters.stylePath?html}/assets/js/niutal/jquery.filestyle.contact-min.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
<!--jQuery.validation -->
<link rel="stylesheet" type="text/css" href="${parameters.stylePath?html}/assets/plugins/validation/css/jquery.validate-min.css?ver=${parameters.cssVersion?default('')}" />
<script type="text/javascript" src="${parameters.stylePath?html}/assets/plugins/validation/js/jquery.validate-min.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
<script type="text/javascript" src="${parameters.stylePath?html}/assets/plugins/validation/js/jquery.validate.contact-min.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
<script type="text/javascript" src="${parameters.stylePath?html}/assets/plugins/validation/js/jquery.validate.methods.contact-min.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
<script type="text/javascript" src="${parameters.stylePath?html}/assets/plugins/validation/lang/zh_CN-min.js?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
<script type="text/javascript">
	jQuery(function($){
		$('[data-toggle*="validation"]').trigger("validation");
	});
</script>
<#else>
<#if parameters.widget_resource_list?exists &&  parameters.widget_resource_list?size != 0>
<#list parameters.widget_resource_list as resourceModel> 
<#if resourceModel.resource_href?exists>
	<#if resourceModel.resource_from == '1'  ><#rt/>
		<#if (resourceModel.resource_href?ends_with("css")) >
		<link rel="stylesheet" type="text/css" href="${parameters.stylePath?html}${resourceModel.resource_href?html}?ver=${parameters.cssVersion?default('')}" />
		<#elseif (resourceModel.resource_href?ends_with("js")) >
		<script type="text/javascript" src="${parameters.stylePath?html}${resourceModel.resource_href?html}?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
		</#if>
	<#else><#rt/>
		<#if (resourceModel.resource_href?ends_with("css")) >
		<link rel="stylesheet" type="text/css" href="${parameters.systemPath?html}${resourceModel.resource_href?html}?ver=${parameters.cssVersion?default('')}" />
		<#elseif (resourceModel.resource_href?ends_with("js")) >
		<script type="text/javascript" src="${parameters.systemPath?html}${resourceModel.resource_href?html}?ver=${parameters.jsVersion?default('')}" charset="utf-8"></script>
		</#if>
	</#if><#lt/>
</#if>
</#list>
<!-- js/css resource end. -->
</#if>
<#if parameters.file_resource_list?exists &&  parameters.file_resource_list?size != 0>
<#list parameters.file_resource_list as resourceModel>
<#if resourceModel.resource_type?exists && resourceModel.resource_type == '1'>
<!-- ${resourceModel.resource_name?default('')} js. -->
﻿<script type="text/javascript">
${resourceModel.resource_text?default('')}
<#rt/>
</script>
</#if>
</#list>
</#if>
</#if>
</#if>