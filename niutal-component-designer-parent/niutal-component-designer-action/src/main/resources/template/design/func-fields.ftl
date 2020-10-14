<style type="text/css">
.form-control-static{ height: 34px;padding-top: 7px;}
</style>
<#if parameters.func_flsz_list?exists && parameters.func_flsz_list?size != 0> 
<div id="${parameters.id?default('func_fields')}" <#rt/>
<#if parameters.name?exists> name="${parameters.name?html}"<#rt/></#if>
<#if parameters.cssClass?exists>class="${parameters.cssClass?html}"<#rt/></#if>
<#if parameters.cssStyle?exists> style="${parameters.cssStyle?html}"<#rt/></#if>
<#if parameters.title?exists> title="${parameters.title?html}"<#rt/></#if>
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" /><#rt/> 
<#include "/${parameters.templateDir}/simple/dynamic-attributes.ftl" /><#rt/> >
<div class="row ">
	<#--  判断显示模式：1：横排页签 ，2：竖排伸缩 -->
	<#if parameters.display_mode == '1'>  
		<!--Tab 导航栏 -->
		<div class="col-md-12 col-sm-12">
			<ul class='nav nav-tabs' role='tablist' id='tab_${parameters.func_code}' style="margin-bottom: 10px;padding-left:15px;">
			<#list parameters.func_flsz_list as func_flsz> 
			<#if func_flsz.xsxx == '1'>
			<li class="active" >
			<#else>
			<li>
			</#if>
				<a href="#content_${func_flsz.flszid?string}" data-flszid="${func_flsz.flszid?string}" role='tab' data-toggle='tab'> ${func_flsz.flmc?string} </a>
			</li>
			</#list>
			</ul>
			<!-- Tab 内容面板 -->
			<div class="tab-content ">
				<#list parameters.func_flsz_list as func_flsz> 
				<div id="content_${func_flsz.flszid?string}" <#if func_flsz.xsxx != '1'> class="tab-pane fade"<#else> class="tab-pane fade in active"  </#if><#rt/>>
					<#include "/${parameters.templateDir}/design/include/func-field-normal.ftl" />
				</div>
				</#list>
			</div>
		</div>
	<#else>
		<#list parameters.func_flsz_list as func_flsz> 
		<div class="col-md-12 col-sm-12">
			<!--Tab 导航栏 -->
			<ul class='nav nav-tabs nav-flsz' role='tablist' id='tab_${parameters.func_code}_${func_flsz_index}' style="margin-bottom: 10px;padding-left:15px;">
				<#if func_flsz.xsxx == '1'>
				<li class="active" >
				<#else>
				<li>
				</#if>
				<a href="#content_${func_flsz.flszid?string}" onclick="onclickXXFL.call(this,'${func_flsz.flszid?string}')" data-flszid="${func_flsz.flszid?string}" role='tab' data-toggle='tab' > ${func_flsz.flmc?string} </a></li>
			</ul>
			<!-- Tab 内容面板 -->
			<div class="tab-content tab-content-flsz" id="tab_content_${func_flsz.flszid?string}">
				<div id="content_${func_flsz.flszid?string}" <#if func_flsz.xsxx != '1'> class="tab-pane"<#else> class="tab-pane active"  </#if><#rt/>>
					<#include "/${parameters.templateDir}/design/include/func-field-normal.ftl" />
				</div>
			</div>
		</div>
		</#list>
		<script type="text/javascript">
		 
		function onclickXXFL(flszid){
			jQuery(this).trigger("show.bs.tab");	
						
			jQuery("div.tab-content-flsz").hide();
			jQuery("#tab_content_"+flszid).fadeIn();	
			
			jQuery(".nav-flsz").find("li").removeClass("active");
			jQuery("#content_"+flszid).addClass("active");	
			
			jQuery(this).trigger("shown.bs.tab");
		}
		
		</script>
	</#if>
</div>
</#if>