<#-- 字段类型 0:隐藏域1:仅显示 2:下拉框 3:单选框 4:复选框 10:文本域  11:文本框 13:日期文本框21:照片 22:省市县选择  99:功能自定义 -->
<#--
设置类型 当“字段类型”为：2:下拉框、3:单选框、4:复选框三种类型时，设置类型字段有效
1.固定值，格式为：1:男,2:女
2:数据库取值,“表名:代码,名称”,
3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个string类型；
4:基础数据获取，值为基础数据lxdm,根据lxdm获取相应类别的基础数据列表
5:直接在页面上制定list、key、value
-->
<#--
设置值 该字段与“字段类型”、“设置类型”配合使用
当“字段类型”为：2:下拉框、3:单选框、4:复选框三种类型时，设置值与设置类型的关系如下：
1.固定值，格式为：1:男,2:女
2:数据库取值,“表名:代码,名称”
3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个string类型
4:基础数据获取，值为基础数据lxdm,根据lxdm获取相应类别的基础数据列表
5:直接在页面上制定list、key、value

当“字段类型”非：2:下拉框、3:单选框、4:复选框三种类型时，设置值与字段类型的关系如下：
13:日期文本框，格式为：yyyymmdd等
99:功能自定义，格式为：定义代码，与界面中div的id关联

sfkg;// 是否可修改1:可修改;0:不可修改
sfsh;// 是否要审核1:要审核;0:不审核
-->
<#if inner_field?exists >
	<#if inner_field.zdlx == '0' >
		<#-- 仅显示-->
		<p class="form-control-static">${inner_field.szz?string}</p>
	<#elseif inner_field.zdlx == '1' >
		<#-- 隐藏域 -->
		<input type='hidden' name="${inner_field.zd?string}" id="${inner_field.zd?string}" value="${inner_field.bdszz?default('')}"/>
	<#elseif inner_field.zdlx == '2' >
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if inner_field.sfkbj == '1' >
			<#-- 可修改-->
			<#-- 下拉框-->
			<select name="${inner_field.zd?string}" id="${inner_field.zd?string}" class="form-control chosen-select" ${inner_field.yzlx_temp?default('')}>
				<#if inner_field.szlx == '10' || inner_field.szlx == '20' || inner_field.szlx == '40' || inner_field.szlx == '50'  >
					<option value=''>--请选择--</option>
				</#if>
				<#--  判断字段绑定的数据存在.  -->
				<#if inner_field.data_list?exists && inner_field.data_list?size != 0> 
				<#list inner_field.data_list as field_data> 
					<#if inner_field.bdszz?exists && inner_field.bdszz == field_data.key  >
						<option selected="selected" value="${field_data.key?html}">${field_data.value?html}</option>
					<#else>
						<option value="${field_data.key?html}">${field_data.value?html}</option>
					</#if>
				</#list>
				</#if>
			</select>
			<script type="text/javascript">jQuery('#${inner_field.zd?string}').trigger('chosen');</script>
			
		<#else>
			<#--  判断字段绑定的数据存在.  -->
			<p class="form-control-static">
			<#if inner_field.data_list?exists && inner_field.data_list?size != 0> 
				<#list inner_field.data_list as field_data> 
					<#if inner_field.bdszz?exists && inner_field.bdszz == field_data.key  >
						${field_data.value?html}
						<#break>
					</#if>
				</#list>
			</#if>
			</p>
		</#if>
		
	<#elseif inner_field.zdlx == '3' >
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if inner_field.sfkbj == '1' >
			<#-- 可修改-->
			<#-- 单选框  -->
			<#--  判断字段绑定的数据存在.  -->
			<#if inner_field.data_list?exists && inner_field.data_list?size != 0> 
			<#list inner_field.data_list as field_data> 
				<label class="radio-inline" style="height: 34px;">
					<#if inner_field.bdszz?exists && inner_field.bdszz == field_data.key  >
						<input checked="checked" type="radio" name="${inner_field.zd?string}" id="${inner_field.zd + '_' + (field_data_index + 1)?html}" value="${field_data.key?html}" ${inner_field.yzlx_temp?default('')} >${field_data.value?html}
					<#else>
						<input type="radio" name="${inner_field.zd?string}" id="${inner_field.zd + '_' + (field_data_index + 1)?html}" value="${field_data.key?html}" ${inner_field.yzlx_temp?default('')} >${field_data.value?html}
					</#if>
				</label>
			</#list> 
			</#if>
		<#else>
			<#--  判断字段绑定的数据存在.  -->
			<p class="form-control-static">
			<#if inner_field.data_list?exists && inner_field.data_list?size != 0> 
				<#list inner_field.data_list as field_data> 
					<#if inner_field.bdszz?exists && inner_field.bdszz == field_data.key  >
						${field_data.value?html}
						<#break>
					</#if>
				</#list>
			</#if>
			</p>
		</#if>
		
	<#elseif inner_field.zdlx == '4' >
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if inner_field.sfkbj == '1' >
			<#-- 可修改-->
			<#-- 复选框-->
			<#--  判断字段绑定的数据存在.  --> 
			<#if inner_field.data_list?exists && inner_field.data_list?size != 0> 
			<#list inner_field.data_list as field_data>
				<label class="checkbox-inline" style="height: 34px;">
					<#if inner_field.bdszz?exists>
					    <#list inner_field.bdszz?split(",") as item>
					       <#if item == field_data.key>
						       <#assign checkedKey = field_data.key>
						       ${checkedKey}
					       </#if>
						</#list>
						<#if checkedKey?exists && checkedKey == field_data.key>
						     <input checked="checked" type="checkbox" name="${inner_field.zd?string}" id="${inner_field.zd + '_' + (field_data_index + 1)?html}" value="${field_data.key?html}" ${inner_field.yzlx_temp?default('')} >${field_data.value?html}
				       	<#else>
							<input type="checkbox" name="${inner_field.zd?string}" id="${inner_field.zd + '_' + (field_data_index + 1)?html}" value="${field_data.key?html}" ${inner_field.yzlx_temp?default('')} >${field_data.value?html}
						</#if>
					<#else>
						<input type="checkbox" name="${inner_field.zd?string}" id="${inner_field.zd + '_' + (field_data_index + 1)?html}" value="${field_data.key?html}" ${inner_field.yzlx_temp?default('')} >${field_data.value?html}
					</#if>
				</label>
			</#list>
			</#if>
		<#else>
			<#--  判断字段绑定的数据存在.  -->
			<p class="form-control-static">
			<#if inner_field.data_list?exists && inner_field.data_list?size != 0> 
				<#list inner_field.data_list as field_data> 
					<#if inner_field.bdszz?exists >
					  <#list inner_field.bdszz?split(",") as item>
					  	<#if item == field_data.key>
					  		<#assign checkedKey = field_data.key>
					  	</#if>
					  </#list>
					  <#if checkedKey?exists && checkedKey == field_data.key>
					  	${field_data.value?html}
					  </#if>
					</#if>
				</#list>
			</#if> 
			</p>
		</#if>
		
		
	<#elseif inner_field.zdlx == '10' >
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if inner_field.sfkbj == '1' >
			<#-- 可修改-->
			<#-- textarea文本域 -->
			<textarea name="${inner_field.zd?string}" id="${inner_field.zd?string}" class="form-control " ${inner_field.yzlx_temp?default('')}  >${inner_field.bdszz?default('')}</textarea>
		<#else>
			<#-- 不可修改-->
			<p class="form-control-static">${inner_field.bdszz?default('')}</p>
		</#if>
	<#elseif inner_field.zdlx == '11' >
	
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if inner_field.sfkbj == '1'>
			<#-- 可修改-->
			<#-- text文本框-->
			<input type='text' name="${inner_field.zd?string}" id="${inner_field.zd?string}" class="form-control " ${inner_field.yzlx_temp?default('')}  value="${inner_field.bdszz?default('')}"/>
		<#else>
			<#-- 不可修改-->
			<p class="form-control-static">${inner_field.bdszz?default('')}</p>
		</#if>
	<#elseif inner_field.zdlx == '13' >
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if inner_field.sfkbj == '1' >
			<#-- 可修改-->
			<#-- text日期文本框-->
			<input type='text' readonly="readonly"  name="${inner_field.zd?string}" id="${inner_field.zd?string}" class="form-control disabled Wdate" ${inner_field.yzlx_temp?default('')}
			 onfocus="WdatePicker({dateFmt:'${inner_field.szz?default('yyyyMMdd')}'})"  value="${inner_field.bdszz?default('')}"/>
		<#else>
			<#-- 不可修改-->
			<p class="form-control-static">${inner_field.bdszz?default('')}</p>
		</#if>
	<#elseif inner_field.zdlx == '99' >
		<#-- 从其他区域拷贝 -->
		<script type="text/javascript">
	    	jQuery("#col_${inner_field.zd?string}").append(jQuery("#${inner_field.szz?string}").html());
			jQuery("#${inner_field.szz?string}").remove();
	    </script>
	</#if>
<#elseif zddy_field?exists>
	<#if zddy_field.zdlx == '0' >
		<#-- 仅显示-->
		<p class="form-control-static">${zddy_field.bdszz?default('')}</p>
	<#elseif zddy_field.zdlx == '1' >
		<#-- 隐藏域 -->
		<input type='hidden' name="${zddy_field.zd?string}" id="${zddy_field.zd?string}" value="${zddy_field.bdszz?default('')}"/>
	<#elseif zddy_field.zdlx == '2' >
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if zddy_field.sfkbj == '1' >
			<#-- 可修改-->
			
			<#-- 下拉框-->
			<select name="${zddy_field.zd?string}" id="${zddy_field.zd?string}" class="form-control chosen-select" ${zddy_field.yzlx_temp?default('')}>
				<#if zddy_field.szlx == '10' || zddy_field.szlx == '20' || zddy_field.szlx == '40' || zddy_field.szlx == '50'  >
					<option value=''>--请选择--</option>
				</#if>
				<#--  判断字段绑定的数据存在.  -->
				<#if zddy_field.data_list?exists && zddy_field.data_list?size != 0> 
				<#list zddy_field.data_list as field_data> 
					<#if zddy_field.bdszz?exists && zddy_field.bdszz == field_data.key  >
						<option selected="selected" value="${field_data.key?html}">${field_data.value?html}</option>
					<#else>
						<option value="${field_data.key?html}">${field_data.value?html}</option>
					</#if>
				</#list>
				</#if>
			</select>
			<script type="text/javascript">jQuery('#${zddy_field.zd?string}').trigger('chosen');</script>
			
		<#else>
			<#--  判断字段绑定的数据存在.  -->
			<p class="form-control-static">
			<#if zddy_field.data_list?exists && zddy_field.data_list?size != 0> 
				<#list zddy_field.data_list as field_data> 
					<#if zddy_field.bdszz?exists && zddy_field.bdszz == field_data.key  >
						${field_data.value?html}
						<#break>
					</#if>
				</#list>
			</#if>
			</p>
		</#if>
	<#elseif zddy_field.zdlx == '3' >
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if zddy_field.sfkbj == '1' >
			<#-- 可修改-->
			<#-- 单选框  -->
			<#--  判断字段绑定的数据存在.  -->
			<#if zddy_field.data_list?exists && zddy_field.data_list?size != 0> 
			<#list zddy_field.data_list as field_data> 
				<label class="radio-inline" style="height: 34px;">
					<#if zddy_field.bdszz?exists && zddy_field.bdszz == field_data.key  >
						<input checked="checked" type="radio" name="${zddy_field.zd?string}" id="${zddy_field.zd + '_' + (field_data_index + 1)?html}" value="${field_data.key?html}" ${zddy_field.yzlx_temp?default('')} >${field_data.value?html}
					<#else>
						<input type="radio" name="${zddy_field.zd?string}" id="${zddy_field.zd + '_' + (field_data_index + 1)?html}" value="${field_data.key?html}" ${zddy_field.yzlx_temp?default('')} >${field_data.value?html}
					</#if>
				</label>
			</#list>
			</#if>
		<#else>
			<#--  判断字段绑定的数据存在.  -->
			<p class="form-control-static">
			<#if zddy_field.data_list?exists && zddy_field.data_list?size != 0> 
				<#list zddy_field.data_list as field_data> 
					<#if zddy_field.bdszz?exists && zddy_field.bdszz == field_data.key  >
						${field_data.value?html}
						<#break>
					</#if>
				</#list>
			</#if>
			</p>
		</#if>
	<#elseif zddy_field.zdlx == '4' >
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if zddy_field.sfkbj == '1' >
			<#-- 可修改-->
			<#-- 复选框-->
			<#--  判断字段绑定的数据存在.  -->
			<#if zddy_field.data_list?exists && zddy_field.data_list?size != 0> 
			<#list zddy_field.data_list as field_data> 
				<label class="checkbox-inline" style="height: 34px;">
					<#if zddy_field.bdszz?exists>
					    <#list zddy_field.bdszz?split(",") as item>
					       <#if item == field_data.key>
						       <#assign checkedKey = field_data.key>
					       </#if>
						</#list>
						<#if checkedKey?exists && checkedKey == field_data.key>
							<input checked="checked" type="checkbox" name="${zddy_field.zd?string}" id="${zddy_field.zd + '_' + (field_data_index + 1)?html}" value="${field_data.key?html}" ${zddy_field.yzlx_temp?default('')} >${field_data.value?html}
				       	<#else>
							<input type="checkbox" name="${zddy_field.zd?string}" id="${zddy_field.zd + '_' + (field_data_index + 1)?html}" value="${field_data.key?html}" ${zddy_field.yzlx_temp?default('')} >${field_data.value?html}
						</#if>
					<#else>
						<input type="checkbox" name="${zddy_field.zd?string}" id="${zddy_field.zd + '_' + (field_data_index + 1)?html}" value="${field_data.key?html}" ${zddy_field.yzlx_temp?default('')} >${field_data.value?html}
					</#if>
				</label>
			</#list>
			</#if>
		<#else>
			<#--  判断字段绑定的数据存在.  -->
			<p class="form-control-static">
			<#if zddy_field.data_list?exists && zddy_field.data_list?size != 0> 
				<#list zddy_field.data_list as field_data> 
					<#if zddy_field.bdszz?exists>
						<#list zddy_field.bdszz?split(",") as item>
							<#if item == field_data.key>
							  <#assign checkedKey = field_data.key>
							</#if>
						</#list>
						<#if checkedKey?exists && checkedKey == field_data.key>
						${field_data.value?html}
						</#if>
					</#if>
				</#list>
			</#if>
			</p>
		</#if>
	<#elseif zddy_field.zdlx == '10' >
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if zddy_field.sfkbj == '1' >
			<#-- 可修改-->
			<#-- textarea文本域 -->
			<textarea name="${zddy_field.zd?string}" id="${zddy_field.zd?string}" class="form-control " ${zddy_field.yzlx_temp?default('')}  >${zddy_field.bdszz?default('')}</textarea>
		<#else>
			<#-- 不可修改-->
			<p class="form-control-static">${zddy_field.bdszz?default('')}</p>
		</#if>
	<#elseif zddy_field.zdlx == '11' >
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if zddy_field.sfkbj == '1' >
			<#-- 可修改-->
			
			<#-- text文本框-->
			<input type='text' name="${zddy_field.zd?string}" id="${zddy_field.zd?string}" class="form-control " ${zddy_field.yzlx_temp?default('')}  value="${zddy_field.bdszz?default('')}"/>
		
		<#else>
			<#-- 不可修改-->
			<p class="form-control-static">${zddy_field.bdszz?default('')}</p>
		</#if>
	<#elseif zddy_field.zdlx == '13' >
		<#--  功能类型 1:增加;2:修改;3:查看-->
		<#if zddy_field.sfkbj == '1' >
			<#-- 可修改-->
			<#-- text日期文本框-->
			<input readonly="readonly" type='text' name="${zddy_field.zd?string}" id="${zddy_field.zd?string}" class="form-control disabled Wdate" ${zddy_field.yzlx_temp?default('')} 
			onfocus="WdatePicker({dateFmt:'${zddy_field.szz?default('yyyyMMdd')}'})" value="${zddy_field.bdszz?default('')}"/>
		<#else>
			<#-- 不可修改-->
			<p class="form-control-static">${zddy_field.bdszz?default('')}</p>
		</#if>
	<#elseif zddy_field.zdlx == '99' >
		<#-- 从其他区域拷贝 -->
		<script type="text/javascript">
	    	jQuery("#col_${zddy_field.zd?string}").append(jQuery("#${zddy_field.szz?string}").html());
			jQuery("#${zddy_field.szz?string}").remove();
	    </script>
	</#if>
</#if> 