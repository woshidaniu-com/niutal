<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<style type="text/css">
	#moreSettingForm .tooltip{
		z-index: 888888;
	}
	</style>
</head>
<body>
	<s:form cssClass="form-horizontal sl_all_form" role="form" id="moreSettingForm" theme="simple">
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-3 col-sm-3 control-label">
						Chosen组件美化
					</label>
					<div class="col-md-8 col-sm-8">
						<s:iterator value="#{1:'使用',0:'不使用'}" var="stat">
							<label class="radio-inline">
							    <input type="radio" id="field_chosen_<s:property value="#stat.index"/>" name="field_chosen" validate="{required:true}"  <s:if test="queryFieldModel.field_chosen == key"> checked="checked" </s:if>  value="<s:property value="key"/>" /><s:property value="value"/>
						  	</label>
						</s:iterator>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-3 col-sm-3 control-label">
						字段要求
					</label>
					<div class="col-md-8 col-sm-8">
						<s:iterator value="#{1:'必填',0:'非必填'}" var="stat">
							<label class="radio-inline">
							    <input type="radio" id="field_required_<s:property value="#stat.index"/>" name="field_required" validate="{required:true}"  <s:if test="queryFieldModel.field_required == key"> checked="checked" </s:if>  value="<s:property value="key"/>"/><s:property value="value"/>
						  	</label>
						</s:iterator>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-3 col-sm-3 control-label">
						AutoComplete提示
					</label>
					<div class="col-md-8 col-sm-8">
						<s:iterator value="#{1:'使用',0:'不使用'}" var="stat">
							<label class="radio-inline">
							    <input type="radio" id="field_autocomplete_<s:property value="#stat.index"/>" name="field_autocomplete" validate="{required:true}"  <s:if test="queryFieldModel.field_autocomplete == key"> checked="checked" </s:if>  value="<s:property value="key"/>"/><s:property value="value"/>
						  	</label>
						</s:iterator>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-3 col-sm-3 control-label">
						扩展样式名称
					</label>
					<div class="col-md-8 col-sm-8">
						<s:textfield cssClass="form-control input-sm"
							data-toggle="tooltip" data-placement="top" title="字段元素的自定义css样式名称;"
							id="field_class" name="queryFieldModel.field_class" validate="{stringMaxLength:100}"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-3 col-sm-3 control-label">
						自定义属性
					</label>
					<div class="col-md-8 col-sm-8">
						<s:textfield cssClass="form-control input-sm"
							data-toggle="tooltip" data-placement="top" title="字段元素的自定义属性;"
							id="field_attr" name="queryFieldModel.field_attr" validate="{stringMaxLength:100}"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-3 col-sm-3 control-label">
						默认值
					</label>
					<div class="col-md-8 col-sm-8">
						<s:textfield cssClass="form-control input-sm"
							data-toggle="tooltip" data-placement="top" title="功能对应默认值:可以是固定的值，也可以是OGNL表达式，方便从struts上下文获取"
							id="field_value" name="queryFieldModel.field_value" validate="{stringMaxLength:50}"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-3 col-sm-3 control-label">
						字段映射名称
					</label>
					<div class="col-md-8 col-sm-8">
						<s:textfield cssClass="form-control input-sm"  data-toggle="tooltip" data-placement="top" title="功能对应字段名称的映射名称:在作为其他字段父级条件，【name属性值】与级联字段父级条件【参数名称】不统一时作为参数名称使用"
							id="field_mapper" name="queryFieldModel.field_mapper" validate="{stringMaxLength:100}"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-3 col-sm-3 control-label">
						提示信息
					</label>
					<div class="col-md-8 col-sm-8">
						<s:textfield cssClass="form-control input-sm" data-toggle="tooltip" data-placement="top" title="字段元素内的占位描述信息：即input输入前的提示"
							id="field_placeholder" name="queryFieldModel.field_placeholder" 
							validate="{stringMaxLength:100}"></s:textfield>
					</div>
				</div>
			</div>

		</div>
	</s:form>
</body>
<script language="javascript">
//你可以在这里继续使用$作为别名...
jQuery(function($) {

	$('#moreSettingForm').validateForm( {
	//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			return false;
		}
	});

	//触发tooltip组件
	$('#moreSettingForm').find('[data-toggle="tooltip"]').tooltip('destroy').tooltip();
});
</script>
</html>
