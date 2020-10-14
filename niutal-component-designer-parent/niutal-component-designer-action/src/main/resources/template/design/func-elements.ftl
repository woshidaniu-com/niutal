<#if parameters.funcModel?exists>
<#if parameters.func_element_list?exists>  
<div id="${parameters.id?default('func_elements')}" <#rt/>
<#if parameters.name?exists> name="${parameters.name?html}"<#rt/></#if>
<#if parameters.cssClass?exists>class="${parameters.cssClass?html}"<#rt/></#if>
<#if parameters.cssStyle?exists> style="${parameters.cssStyle?html}"<#rt/></#if>
<#if parameters.title?exists> title="${parameters.title?html}"<#rt/></#if>
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
<#include "/${parameters.templateDir}/simple/dynamic-attributes.ftl" /> >
	<div class="row ">
		<#if parameters.func_element_list?size != 0>
			<#list parameters.func_element_list as func_element> 
				<div class="col-md-${func_element.element_width} col-sm-${func_element.element_width}" id="element_${func_element.element_id}">
					<#--  判断页面元素关联查询条件是否存在.  -->
					<#if func_element.element_query?exists && (parameters.funcModel.query_type == '1' || parameters.funcModel.query_type == '2' )>  
						<#include "/${parameters.templateDir}/design/include/func-element-query.ftl" />
					<#--  判断页面元素关联js组件是否存在.  -->
					<#elseif func_element.element_widget?exists>  
						<#include "/${parameters.templateDir}/design/include/func-element-widget.ftl" />
					<#-- 判断页面元素关联字段是否存在.  -->
					<#elseif func_element.element_field_list?exists>
						<#include "/${parameters.templateDir}/design/include/func-element-normal.ftl" />
					<#else>
					<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
					<div class="design_empty" style="margin-top: 15px;">
						<div class="btn-group width-40">
							<button class="btn btn-default btn-lg btn-design width-35" type="button" id="btn_element_edit_${func_element.element_guid?string}">
								编辑元素
							</button>
							<button class="btn btn-default btn-lg btn-design width-35" type="button" id="btn_element_bind_${func_element.element_guid?string}">
								绑定/编辑实体
							</button>
							<button class="btn btn-default btn-lg btn-design width-30" type="button" id="btn_element_remove_${func_element.element_guid?string}">
								删除元素
							</button>
						</div>
					</div>
					</#if>
					</#if>
				</div>
			</#list>
		</#if>
		<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
		<div class="col-md-12 col-sm-12" style="margin-top: 15px;">
			<div class="design_empty">
				<button class="btn btn-default btn-lg btn-design width-40" type="button" id="btn_element_new_${parameters.funcModel.func_guid?string}">
					添加新元素
				</button>
			</div>
		</div>
		</#if>
	</div>
</div>
</#if>
</#if>