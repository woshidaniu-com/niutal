<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
<style type="text/css">
#input-group .chosen-single{
	border-radius: 4px 0 0 4px;
	border-right: 0px;
}
</style>
</head>
<body>
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/baseField_xgBaseQueryFieldData.html" theme="simple">
	<s:hidden id="field_guid" name="field_guid" />
	<div class="row">
		<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="" class="col-md-4 col-sm-4 control-label">
					<span class="red">*</span>字段标题
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control" id="field_text"  name="field_text" placeholder="页面元素前的文字标题" validate="{required:true,stringMaxLength:50}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>字段名称
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control" id="field_name"  name="field_name" placeholder="页面元素name属性值" validate="{required:true,fieldType:true,stringMaxLength:50}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="" class="col-md-4 col-sm-4 control-label">
					<span class="red">*</span>字段ID
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control" id="field_id"  name="field_id" placeholder="页面元素id属性值" validate="{required:true,fieldType:true,stringMaxLength:50}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>字段数据取值索引
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control disabled" id="field_list" readonly="true" name="field_list" validate="{required:true,fieldType:true,stringMaxLength:50}" placeholder="struts的 s:select list='' 标签list对应的值"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="" class="col-md-4 col-sm-4 control-label">
					<span class="red">*</span>字段key取值索引
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control" id="field_listKey"  name="field_listKey" validate="{required:true,fieldType:true,stringMaxLength:50}" placeholder="struts的 s:select listKey='' 标签listKey对应的值"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>字段value取值索引
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control" id="field_listValue"  name="field_listValue" validate="{required:true,fieldType:true,stringMaxLength:50}" placeholder="struts的 s:select listValue='' 标签listValue对应的值"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="" class="col-md-4 col-sm-4 control-label">
					下拉框默认值
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control" id="field_headerKey"  name="field_headerKey" validate="{stringMaxLength:50}" value=""></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>下拉框默认文字
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control" id="field_headerValue"  name="field_headerValue" validate="{required:true,stringMaxLength:50}" value="全部"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					<span class="red">*</span>字段使用范围
				</label>
				<div class="col-md-4 col-sm-4">
					<s:select cssClass="form-control chosen-select" name="field_scope" list="fieldScopeList" validate="{required:true}" listKey="key" listValue="value" id="field_scope"></s:select>
					<SCRIPT type="text/javascript">
			    		jQuery('#field_scope').trigger("chosen");
			    	</SCRIPT>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group" id="input-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					<span class="red">*</span>字段值来源
				</label>
				<div class="col-md-7 col-sm-7">
					<div class="input-group">
						<div class="input-group-btn">
							<s:select cssClass="form-control chosen-select" cssStyle="width: 180px;"  id="field_source_type"  name="field_source_type" validate="{required:true}" list="sourceTypeList" listKey="key" listValue="value" value="APP" ></s:select>
							<SCRIPT type="text/javascript">
					    		jQuery('#field_source_type').trigger("chosen"); 
					    	</SCRIPT>
						</div>
						<span class="input-group-addon" id="field_source_txt" style="padding: 6px 2px  6px 12px ;">APP:</span>
						<input type="text" class="form-control" id="field_source" name="field_source" style="border-left-width: 0px;"/>
					</div>
					<p style="padding-top: 2px;padding-bottom: 2px;" class="form-control-static smaller-80 red" id="field_source_tip">此时如果field_list值不为空且field_shape=2，需要开发者在进入页面前指定field_list对应的对象结果!</p>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					扩展样式名称
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="field_class"  name="field_class" validate="{stringMaxLength:100}" placeholder="struts的 s:select headerValue='' 标签headerValue对应的值"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					字段描述信息
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="field_desc"  name="field_desc" validate="{stringMaxLength:200}" placeholder="struts的 s:select headerValue='' 标签headerValue对应的值"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					字段占位描述
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="field_placeholder"  name="field_placeholder" validate="{stringMaxLength:100}" placeholder="struts的 s:select headerValue='' 标签headerValue对应的值"></s:textfield>
				</div>
			</div>
		</div>
	</div>
</s:form>
</body>
<script language="javascript">
//你可以在这里继续使用$作为别名...
jQuery(function($) {

	
	$('#ajaxForm').validateForm( {
		//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			return true;
		}
	});

	var tips = {
		"APP"	:'此时如果field_list值不为空且field_shape=2，需要开发者在进入页面前指定field_list对应的对象结果',
		"SQL"	:'格式如 SQL:查询SQL 例如 SQL:select id as key,name as value from table_name ',
		"XML"	:'格式如 XML:(baseData.xml)文件中的id 例如 XML:isValid ',
		"Spring":'格式如 Spring:文件中的id 例如 Spring:field_list',
		"Fixed"	:'格式如 Fixed:固定值1,固定值2,...(多个用,隔开) 例如 Fixed:aaa,bbb,ccc',
		"Base"	:'格式如 Base:基础数据类型变化  例如 Base:0009',
	};
	$("#field_source_type").unbind().change(function(e){
		$("#field_source_txt").text($(this).founded()? $(this).val() + ":":"");
		$("#field_source_tip").html(tips[$(this).val()]);
	});
	
	$("#field_shape").unbind().change(function(e){
		if($(this).val() == '2'){
			$("#field_type").removeClass("disabled").prop("readonly",false);
		}else{
			$("#field_type").addClass("disabled").prop("readonly",true);
		}
	});

	$("#field_name").unbind().keyup(function(e){
		
		$("#field_id").val($(this).val()).focus().blur();
		$("#field_listKey").val($(this).val()).focus().blur();
		$("#field_list").val($(this).founded() ?  $(this).val()+"_list" : "" ).focus().blur();
		$(this).focus();
	});
	
});
</script>
</html>
