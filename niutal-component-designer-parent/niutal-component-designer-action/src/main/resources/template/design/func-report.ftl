<#if parameters.funcModel?exists>
<div id="${parameters.id?default('func_report')}" <#rt/>
<#if parameters.name?exists> name="${parameters.name?html}"<#rt/></#if>
<#if parameters.cssClass?exists>class="${parameters.cssClass?html}"<#rt/></#if>
<#if parameters.cssStyle?exists> style="${parameters.cssStyle?html}"<#rt/></#if>
<#if parameters.title?exists> title="${parameters.title?html}"<#rt/></#if>
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" /><#rt/>
<#include "/${parameters.templateDir}/simple/dynamic-attributes.ftl" /> <#rt/>>
<#--  如果一个报表页面不是查询功能，则表示是弹窗内的报表，这里需要一个标记以便在报表的回调处理中显示合适高度  -->
<#if parameters.funcModel.query_type == '2'>
	<#--  高级查询  -->
	<div class="row">
		<div class="col-sm-12 col-md-12">
			<div id="searchBox"></div>
		</div>
	</div>
<#elseif parameters.funcModel.query_type == '1'>
	<#--  普通查询  -->
	<#if parameters.func_element_list?exists && parameters.func_element_list?size != 0> 
	<div class="row ">
		<#list parameters.func_element_list as func_element> 
			<div class="col-md-${func_element.element_width} col-sm-${func_element.element_width}" id="element_${func_element.element_id}">
				<#--  判断页面元素关联查询条件是否存在.  -->
				<#if func_element.element_query?exists && (parameters.funcModel.query_type == '1' || parameters.funcModel.query_type == '2' )>
					<#include "/${parameters.templateDir}/design/include/func-report-query.ftl" />
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
			<#break>
		</#list>
	</div>
	<#else>
	<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
	<div class="design_empty" style="margin-top: 15px;">
		<div class="btn-group width-40">
			<button class="btn btn-default btn-lg btn-design width-100" type="button" id="btn_element_new_${parameters.funcModel.func_guid?string}">
				添加新元素
			</button>
		</div>
	</div>
	</#if>
	</#if>
<#else>
	<#-- 不需要查询  -->
	<form id="reportSearchForm_${parameters.funcModel.func_guid?string}" class="form-horizontal " role="form"  action="" method="post" target="reportFrame" name="FRform"  style="display: none">
		<#if parameters.requestMap?exists && parameters.requestMap?size != 0> 
		<#list parameters.requestMap as requestRow> 
			<input type="hidden" name="${requestRow.key}" value="${requestRow.value}"/>
		</#list>	
		</#if>		
	</form>
</#if>
<#if parameters.funcReport?exists>  
<#-- 
<div class="row" style="padding:10px 0px 5px 0px ;" id="page_bar">
	<div class="col-sm-12 col-md-12">
		<ul class="list-inline pull-right">
		<li><a id="page_first" class="btn btn-default btn-sm disabled" role="button"><span class='glyphicon glyphicon-fast-backward'/> 首页</a></li>
		<li><a id="page_prev" class="btn btn-default btn-sm disabled" role="button"><span class='glyphicon glyphicon-backward'/> 上一页</a></li>
		<li>第 <span id="page_cpage">1</span> 页/共 <span id="page_tpage">1</span> 页</li>
		<li><a id="page_next" class="btn btn-default btn-sm disabled" role="button"><span class='glyphicon glyphicon-forward'/> 下一页</a></li>
		<li><a id="page_last" class="btn btn-default btn-sm disabled" role="button"><span class='glyphicon glyphicon-fast-forward'/> 末页</a></li>
		<#if parameters.report_button_list?exists &&  parameters.report_button_list?size != 0>
		<#list parameters.report_button_list as buttonModel> 
		<li><a id="${buttonModel.button_id?string}_${parameters.funcModel.func_guid?string}" name="${buttonModel.button_name?string}" class="btn btn-default btn-sm disabled" role="button"><span class="${buttonModel.button_icon_class?default("")}"/> ${buttonModel.button_text?default("")} </a></li>
		</#list>
		</#if>
		</ul>
	</div>
</div>
 -->
<div id="frameContainer_${parameters.funcModel.func_guid?string}" style="min-height: 350px">
	<div id="frameContainer_tip_${parameters.funcModel.func_guid?string}" style="text-align:center;font-size: 30px;display: none;color: #999" >选择条件点击查询生成报表</div>
</div>
<#else>
<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
<div class="row ">
	<div class="col-sm-12 col-md-12">
		<div class="design_empty" style="margin-top: 15px;">
			<div class="btn-group width-40">
				<button class="btn btn-default btn-lg btn-design width-35" type="button" id="btn_element_edit">
					编辑元素
				</button>
				<button class="btn btn-default btn-lg btn-design width-35" type="button" id="btn_element_bind">
					绑定/编辑实体
				</button>
				<button class="btn btn-default btn-lg btn-design width-30" type="button" id="btn_element_remove">
					删除元素
				</button>
			</div>
		</div>
	</div>
</div>
</#if>
</#if>
</div>

</#if>