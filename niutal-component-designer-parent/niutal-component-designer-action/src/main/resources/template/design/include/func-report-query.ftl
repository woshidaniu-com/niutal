<#if func_element.element_query?exists>  
<!-- ${func_element.element_query.query_name?default("")}  start. -->
<form id="reportSearchForm_${parameters.funcModel.func_guid?string}" class="form-horizontal sl_all_form" role="form"  action="" method="post" target="reportFrame" name="FRform" style="border: 1px solid #e6e6e6;padding: 15px;"><#rt/>
	<div class="row " id="searchbox_${func_element.element_guid}">
		<#if func_element.element_query.query_field_list?exists && func_element.element_query.query_field_list?size != 0>
			<#list func_element.element_query.query_field_list as fieldModel> 
				<div class="col-md-${fieldModel.field_div_width} col-sm-${fieldModel.field_div_width}">
					<div class="form-group">
						<label for="" class="col-md-${fieldModel.field_name_width?default('4')} col-sm-${fieldModel.field_name_width?default('4')} control-label">
							<#if fieldModel.field_required?exists && fieldModel.field_required == '1'><span class="red">*</span> <#rt/></#if><#rt/>${fieldModel.field_text}
						</label>
						<div class="col-md-${fieldModel.field_element_width?default('8')} col-sm-${fieldModel.field_element_width?default('8')}">
							<#-- 字段展示形态;默认 1,可选值 ：1：select,2：input,3：textarea -->
							<#if fieldModel.field_shape == '1' >
							<select data-text="${fieldModel.field_text}" <#rt/>
								data-header-key="${fieldModel.field_headerKey?default('')}" <#rt/> 
								data-header-value="${fieldModel.field_headerValue?default('全部')}" <#rt/> 
								data-list-key="${fieldModel.field_listKey?default('')}" <#rt/> 
								data-list-value="${fieldModel.field_listValue?default('')}" <#rt/>
								<#if fieldModel.field_required?exists && fieldModel.field_required == '1'> validate="{required:true}" data-required="true"  <#rt/></#if><#rt/>
								<#if fieldModel.field_parent?exists> data-parent="${fieldModel.field_parent?string}" <#rt/></#if><#rt/>
								<#if fieldModel.field_mapper?exists> data-mapper="${fieldModel.field_mapper?string}" <#rt/></#if><#rt/>
								<#if fieldModel.field_update_url?exists>  data-updata-url="${fieldModel.field_update_url?default('')}" <#rt/></#if><#rt/>
								 id="${fieldModel.field_id?html}" name="${fieldModel.field_name?html}" class="form-control ${fieldModel.field_class?default('')}" title="${fieldModel.field_placeholder?default('')}" ${fieldModel.field_attr?default('')} >
								<#if fieldModel.field_headerValue?exists>
									<option value="${fieldModel.field_headerKey?default('')}">${fieldModel.field_headerValue?default('全部')}</option>
								</#if>
								<#--  判断字段绑定的数据存在.  -->
								<#if fieldModel.field_data_list?exists>
									<#list fieldModel.field_data_list as field_data> 
										<option value="${field_data.key?html}">${field_data.value?html}</option>
									</#list>
								</#if>
							</select>
							<#if fieldModel.field_chosen = '1'>
							<script type="text/javascript">
							jQuery(function($){
					    		if(jQuery.fn.chosen){
					    			jQuery('#${fieldModel.field_id?html}').trigger("chosen");
						    	}
					    	});
					    	</script>
							</#if>
							<#elseif fieldModel.field_shape == '2'><#rt/>
								<#-- 功能对应字段作为input元素时的类型;默认 1,可选值 ：1：text,2：radio,3：checkbox,4：hidden -->
								<#if fieldModel.field_type == '2' || fieldModel.field_type == '3' ><#rt/>
									<#--  单选框.  -->
									<#if fieldModel.field_type == '2' ><#rt/>
										<#--  判断字段绑定的数据存在.  -->
										<#if fieldModel.field_data_list?exists>
											<#list fieldModel.field_data_list as field_data> 
											<label class="radio-inline">
											    <input data-text="${fieldModel.field_text}" <#if fieldModel.field_required?exists && fieldModel.field_required == '1'> validate="{required:true}" data-required="true"  <#rt/></#if><#rt/> 
											    type="radio" id="${fieldModel.field_id + '_' + (field_data_index + 1)?html}"  name="${fieldModel.field_name?html}" <#if field_data_index == 0> checked="checked" </#if> value="${field_data.key?html}" />&nbsp;${field_data.value?html}
										  	</label>
											</#list>
										</#if>
									<#--  多选框.  -->
									<#elseif fieldModel.field_type == '3'>
										<#--  判断字段绑定的数据存在.  -->
										<#if fieldModel.field_data_list?exists>
											<#list fieldModel.field_data_list as field_data> 
											<label class="checkbox-inline">
											    <input data-text="${fieldModel.field_text}" <#if fieldModel.field_required?exists && fieldModel.field_required == '1'> validate="{required:true}" data-required="true"  <#rt/></#if><#rt/> 
											    type="checkbox" id="${fieldModel.field_id + '_' + (field_data_index + 1)?html}"  name="${fieldModel.field_name?html}" <#if field_data_index == 0> checked="checked" </#if> value="${field_data.key?html}" />&nbsp;${field_data.value?html}
										  	</label>
											</#list>
										</#if>
									</#if>
								<#elseif fieldModel.field_type == '1' || fieldModel.field_type == '4'>
									<#--  文本输入框.  -->
									<#if fieldModel.field_type == '1'>
										<input type="text"  data-text="${fieldModel.field_text}"
										id="${fieldModel.field_id?html}" 
										name="${fieldModel.field_name?html}" 
										<#if fieldModel.field_required?exists && fieldModel.field_required == '1'> validate="{required:true}" data-required="true"  <#rt/></#if><#rt/>
										<#if fieldModel.field_class?exists> class="form-control ${fieldModel.field_class?html}" <#rt/><#else> class="form-control" </#if>
										<#if fieldModel.field_placeholder?exists> placeholder="${fieldModel.field_placeholder?html}" <#rt/></#if>
										${fieldModel.field_attr?default("")}
										<#--  检查是否有配置自动完成组件参数.  -->
										<#if fieldModel.field_auto?exists>
											data-toggle="autocomplete"<#rt/>
											data-autocomplete-minLength="${fieldModel.field_auto.field_minLength?default('1')}"<#rt/>
											data-autocomplete-items="${fieldModel.field_auto.field_items?default('10')}"<#rt/>
											data-autocomplete-delay="${fieldModel.field_auto.field_delay?default('300')}"<#rt/>
											data-autocomplete-setvalue="${fieldModel.field_auto.field_setvalue}"<#rt/>
											data-autocomplete-realvalue="${fieldModel.field_auto.field_realvalue}"<#rt/>
											data-autocomplete-format="${fieldModel.field_auto.field_format}"<#rt/>
											data-autocomplete-action="${fieldModel.field_auto.field_action?default('value（key）')}"<#rt/>
											/>
										<#else>
										/>
										</#if><#rt/>
									<#--  隐藏框.  -->
									<#elseif fieldModel.field_type == '4'>
										<input type="hidden" id="${fieldModel.field_id?html}" name="${fieldModel.field_name?html}" class="form-control ${fieldModel.field_class?html}" placeholder="${fieldModel.field_placeholder?default('')}" ${fieldModel.field_attr?default("")} /><#rt/>
									</#if>
								</#if>
							<#elseif fieldModel.field_shape == '3'>
								<textarea rows="3" id="${fieldModel.field_id?html}" name="${fieldModel.field_name?html}" class="form-control ${fieldModel.field_class?html}" placeholder="${fieldModel.field_placeholder?default('')}" ${fieldModel.field_attr?default("")} ></textarea>
							</#if>
						</div>
					</div>
				</div>
			</#list>
		<#else>
		<div class="col-md-12 col-sm-12 align-center bigger-160" style="line-height: 30px;color: #999">无查询条件字段信息!</div>
		</#if>
	</div>
</form>
<!-- 查询按钮  start.  -->
<div class="row sl_aff_btn" id="searchResult" style="-moz-border-bottom-colors: none;<#rt/>
	    -moz-border-left-colors: none;<#rt/>
	    -moz-border-right-colors: none;<#rt/>
	    -moz-border-top-colors: none;<#rt/>
	    background: #f4f4f4 none repeat scroll 0 0;<#rt/>
	    border-color: -moz-use-text-color #e6e6e6 #e6e6e6;<#rt/>
	    border-image: none;<#rt/>
	    border-style: none solid solid;<#rt/>
	    border-width: 0 1px 1px;<#rt/>
	    margin: 0 0 15px;<#rt/>
	    padding: 5px 0;<#rt/>
	    text-align: right;">
	<div class="col-sm-10 col-md-10">
  		<p style="padding-right: 20px;color: #a94442;" class="form-control-static align-left " id="search_tip"></p>
	</div>
  	<div class="col-sm-2 col-md-2">
		<button type="button" class="btn btn-primary btn-sm" id="btn_search_${func_element.element_guid?string}">
			查 询
		</button>
	</div>
</div>
<!-- 查询按钮  end!  -->
<!-- ${func_element.element_query.query_name?default("")}  end. -->
</#if>