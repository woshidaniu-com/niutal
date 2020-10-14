<#-- 表单模式:1:单条记录模式:2:多条记录模式;3:功能自定义代码 -->
<#if func_flsz.bdms == '1'>
	<#if func_flsz.element_zddy_list?exists && func_flsz.element_zddy_list?size != 0> 
		<div class="row">
		<#list func_flsz.element_zddy_list as zddy_field> 
			<#-- 元素分组 -->
			<#if zddy_field.zdlx == '98' >
				<div class="col-md-${zddy_field.kdbl1?default('4')} col-sm-${zddy_field.kdbl2?default('6')}">
					<#if zddy_field.zddy_list?exists && zddy_field.zddy_list?size != 0> 
						<div class="row">
						<#list zddy_field.zddy_list as inner_field> 
							<div class="col-md-${inner_field.kdbl1?default('4')} col-sm-${inner_field.kdbl2?default('6')}">
								<div class="form-group" >
									<label class="col-sm-${inner_field.kdbl3?default('4')} control-label" for="" >
									<#--  功能类型 1:增加;2:修改;3:查看-->
									<#--  是否必填：是否必填： 1:必须填写,0:可不填写 -->
									<#if inner_field.sfkbj == '1' && (parameters.func_type == '1' || parameters.func_type == '2' ) && (inner_field.sfbt?exists && inner_field.sfbt == '1') >
									<span class='text-danger'>*</span>
									</#if>
									<#if parameters.func_type == '1' || parameters.func_type == '2'  >
										<#if inner_field.sfkg == '1' && inner_field.sfsh == '0'  >
										<span class='text-success'>${inner_field.bdmc?string}：</span>
										<#elseif inner_field.sfkg == '0' && inner_field.sfsh == '1'  >
										<span class='text-danger'>${inner_field.bdmc?string}：</span>
										<#else>
										${inner_field.bdmc?string}：
										</#if>
									<#else>
										${inner_field.bdmc?string}：
									</#if>
									</label>
									<div class="col-sm-${inner_field.kdbl4?default('8')}" id="col_${inner_field.zd?default('')}">
										 <#--  功能类型 1:增加;2:修改;3:查看--> 
									 	<#include "/${parameters.templateDir}/design/include/func-field-item.ftl" />
									</div>
								</div>
							</div>
						</#list>
						</div>
					</#if>
				</div>
			<#elseif zddy_field.zdlx == '99' >
				<div class="col-md-${zddy_field.kdbl1?default('4')} col-sm-${zddy_field.kdbl2?default('6')}">
					<div class="form-group" id="col_${zddy_field.zd?string}">
						<#-- 从其他区域拷贝 -->
						<script type="text/javascript">
							<#if zddy_field.sfkbj == '0' || ( zddy_field.sfkbj == '1' && zddy_field.sfkg == '0') >
							jQuery("#${zddy_field.szz?string}").find("*").attr("data-editable",false);
							<#else>
							jQuery("#${zddy_field.szz?string}").find("*").attr("data-editable",true);
							</#if>
					    	jQuery("#col_${zddy_field.zd?string}").append(jQuery("#${zddy_field.szz?string}").html());
							jQuery("#${zddy_field.szz?string}").remove();
					    </script>
					</div>
				</div>
			<#else>
				
				<#if zddy_field.zdlx == '1' >
					<#-- 隐藏域 -->
					<input type='hidden' name="${zddy_field.zd?string}" id="${zddy_field.zd?string}" value="${zddy_field.bdszz?default('')}"/>
				<#else>
					<div class="col-md-${zddy_field.kdbl1?default('4')} col-sm-${zddy_field.kdbl2?default('6')}">
						<div class="form-group" >
							<label class="col-sm-${zddy_field.kdbl3?default('4')} control-label" for="" >
								<#--  功能类型 1:增加;2:修改;3:查看-->
								<#--  是否必填：是否必填： 1:必须填写,0:可不填写 -->
								<#if zddy_field.sfkbj == '1' &&  (parameters.func_type == '1' || parameters.func_type == '2' ) && (zddy_field.sfbt?exists && zddy_field.sfbt == '1') >
								<span class='text-danger'>*</span>
								</#if>
								<#if parameters.func_type == '1' || parameters.func_type == '2'  >
									<#if zddy_field.sfkg == '1' && zddy_field.sfsh == '0'  >
									<span class='text-success'>${zddy_field.bdmc?string}：</span>
									<#elseif zddy_field.sfkg == '0' && zddy_field.sfsh == '1'  >
									<span class='text-danger'>${zddy_field.bdmc?string}：</span>
									<#else>
									${zddy_field.bdmc?string}：
									</#if>
								<#else>
									${zddy_field.bdmc?string}：
								</#if>
							</label>
							<div class="col-sm-${zddy_field.kdbl4?default('8')}" id="col_${zddy_field.zd?default('')}">
								 <#--  功能类型 1:增加;2:修改;3:查看-->
							 	<#include "/${parameters.templateDir}/design/include/func-field-item.ftl" />
							</div>
						</div>
					</div>
				</#if>
			</#if>
		</#list>
 		</div>
 	</#if>
 <#elseif func_flsz.bdms == '3'>
 	<#-- 从其他区域拷贝 -->
	<script type="text/javascript">
    	jQuery("#content_${func_flsz.flszid?string}").append(jQuery("#${func_flsz.bdszz?string}").html());
		jQuery("#${func_flsz.bdszz?string}").remove();
    </script>
</#if>

