<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>

<form class="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="<s:property value="plxgURL"/>" theme="simple" style="min-height: 200px;">
	<s:iterator value="requestMap">
		<input type="hidden" name="<s:property value="key" />" value="<s:property value="value" />"/>
	</s:iterator>
	<div class="row" style="margin-top: 20px;">	
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label  class="col-md-3 col-sm-3 control-label">
					修改字段
				</label>
				<div class="col-md-7 col-sm-7">
					<select id="ajaxForm_zddm" name="zddm" class="form-control  chosen-select" validate="{required:true}">
						<s:iterator value="modelList">
							<option value="<s:property value="zddm"/>" data-zdcd="<s:property value="zdcd"/>"><s:property value="zdmc"/></option>
						</s:iterator>
					</select>
					<SCRIPT type="text/javascript">
			    		jQuery('#ajaxForm_zddm').trigger("chosen");
			    	</SCRIPT>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label  class="col-md-3 col-sm-3 control-label">
					缺省值
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield id="ajaxForm_zdz" name="zdz" maxlength="100"
	           			cssClass="form-control" validate="{required:true,stringMinLength:1}" ></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label  class="col-md-3 col-sm-3 control-label">
					&nbsp;
				</label>
				<div class="col-md-7 col-sm-7">
					<s:iterator value="xgfsList" status="sta">
						<s:if test="#sta.index == 0">
							<label class="radio-inline">
								<input type="radio" name="xgfs" checked="checked" validate="{required:true}" value="<s:property value="key"/>" />
								<s:property value="value" />
							</label>
						</s:if>
						<s:else>
							<label class="radio-inline">
								<input type="radio" name="xgfs" validate="{required:true}" value="<s:property value="key"/>" />
								<s:property value="value" />
							</label>
						</s:else>
					</s:iterator>
				</div>
			</div>
		</div>
	</div>
<form>
<script type="text/javascript">
jQuery(function($){
	
	$('#ajaxForm').validateForm();

	var ajaxForm_zdz = $("#ajaxForm_zdz");
	
	$("#ajaxForm_zddm").unbind().change(function(e){
		ajaxForm_zdz.rules("remove");
		ajaxForm_zdz.rules("add", {required:true,stringMinLength:1,stringMaxLength:$(this).data("zdcd")||100});
	});

	ajaxForm_zdz.rules("remove");
	ajaxForm_zdz.rules("add", {required:true,stringMinLength:1,stringMaxLength:$("#ajaxForm_zddm").find("option:selected").data("zdcd")||100});
	
});
</script>
</html>
