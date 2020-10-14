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
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/designFunc_zjysstFuncElementEntityData.html" theme="simple">
	<s:hidden  id="func_code" name="elementModel.func_code" ></s:hidden>
	<s:hidden  id="opt_code" name="elementModel.opt_code" ></s:hidden>
	<s:hidden  id="func_guid" name="elementModel.func_guid" ></s:hidden>
	<s:hidden  id="element_guid" name="elementModel.element_guid" ></s:hidden>
	<s:hidden  id="element_type" name="elementModel.element_type" ></s:hidden>
	<div class="row">
		<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="" class="col-md-4 col-sm-4 control-label">
					<span class="red">*</span>组件类型
				</label>
				<div class="col-md-5 col-sm-5">
					<select class="form-control chosen-select"  id="widget_guid"  name="widgetModel.widget_guid" validate="{required:true}">
						<s:iterator value="widgetList">
							<option <s:if test="widgetModel.widget_guid == widget_guid">selected="selected"</s:if> value="<s:property value="widget_guid"/>"><s:property value="widget_name"/></option>
						</s:iterator>
					</select>
					<SCRIPT type="text/javascript">
			    		jQuery('#widget_guid').trigger("chosen");
			    	</SCRIPT>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-6">
			<div class="form-group">
				<label for="" class="col-md-4 col-sm-4 control-label">
					<span class="red">*</span>数据是否分页
				</label>
				<div class="col-md-5 col-sm-5" >
					<s:iterator value="isPagination" >
						<label class="radio-inline">
						    <input type="radio" id="widget_pageable" name="widgetModel.widget_pageable" <s:if test="key == widgetModel.widget_pageable"> checked="checked" </s:if>  value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					<span class="red">*</span>功能组件名称
				</label>
				<div class="col-md-9 col-sm-9">
					<s:textfield cssClass="form-control" id="widget_title"  name="widgetModel.widget_title" placeholder="功能组件名称（如：jqgrid列表）" validate="{required:true,stringMaxLength:50}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					<span class="red">*</span>功能组件描述
				</label>
				<div class="col-md-9 col-sm-9" >
					<s:textfield cssClass="form-control" id="widget_desc"  name="widgetModel.widget_desc" placeholder="功能组件描述" validate="{required:true,stringMaxLength:100}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					<span class="red">*</span>数据查询URL
				</label>
				<div class="col-md-9 col-sm-9" >
					<s:textfield cssClass="form-control" id="widget_data_url"  name="widgetModel.widget_data_url" placeholder="请录入数据查询URL" validate="{widgetRequired:true,stringMaxLength:200}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12" >
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					<span class="red">*</span>数据查询SQL
				</label>
				<div class="col-md-9 col-sm-9" >
					<s:textarea cssClass="form-control" cssStyle="height: 150px;" id="widget_sql"  name="widgetModel.widget_sql" placeholder="请录入数据查询SQL" validate="{widgetRequired:true}"></s:textarea>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					<span class="red">*</span>是否立即加载数据
				</label>
				<div class="col-md-8 col-sm-8" >
					<s:iterator value="isNot" >
						<label class="radio-inline">
						    <input type="radio" id="widget_loadAtOnce" name="widgetModel.widget_loadAtOnce" <s:if test="key == widgetModel.widget_loadAtOnce"> checked="checked" </s:if>  value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
	</div>
</s:form>
</body>
<script type="text/javascript">
//你可以在这里继续使用$作为别名...
jQuery(function($) {
	
	$.validator.addMethod("widgetRequired", function(value, element, param) {
		return $("#widget_sql").founded() || $("#widget_data_url").founded();
	}, "组件数据[查询SQL]或[查询URL]必须有一个必填!");

	
	$('#ajaxForm').validateForm( {
		//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			return true;
		}
	});
	
});
</script>
</html>
