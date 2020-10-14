<#if parameters.func_list?exists &&  (parameters.func_list?size > 1) && (parameters.xslx == '1') >
<!-- navtabs-start  --> 
<div class="container sl_all_bg" style="padding-bottom: 0px;">
	<ul class="nav nav-tabs" id="navbar-tabs" role="tablist" style="padding-left: 15px">
		<#list parameters.func_list as func> 
			<#-- 第一个元素 -->
			<#if (func_index > 0) >
				<li><a href="#" data-dyym="${func.dyym?default('')}" data-gnmkdm="${func.gnmkdm?default('')}" role="tab"
					onclick="onClickMenu('${func.dyym?default('')}','${func.gnmkdm?default('')}')"
					data-toggle="tab" style="margin-top: 0px !important;margin-bottom: 0px !important;line-height: 1.1"> ${func.gnmkmc?default('')}
				</a></li>
			</#if>
		</#list>
	</ul>
</div>
<!-- navtabs-end  -->
</#if>