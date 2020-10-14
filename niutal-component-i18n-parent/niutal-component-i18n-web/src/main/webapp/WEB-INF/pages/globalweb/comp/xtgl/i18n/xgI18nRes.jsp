<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<s:form cssClass="form-horizontal sl_all_form sl_all_bg" role="form"
		name="form" id="ajaxForm" method="post"
		action="/i18n/i18n_xgBcI18nRes.html" theme="simple"
		onsubmit="return false;">
		<s:hidden name="res_oid" id="res_oid"></s:hidden>
		<div class="row">
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label"><span
						style="color: red;">*</span>文件代码</label>
					<div class="col-sm-8">
						<s:textfield id="res_code" name="res_code" cssClass="form-control"
							validate="{required:true,stringMaxLength:50,nowhitespace:true,multiUnique:['V0201',['#res_code', '#res_type'],['%{res_code}', '%{res_type}'],'违反唯一性约束，已存在相同的资源文件配置!']}"
							data-toggle="autocomplete"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label"><span
						style="color: red;">*</span>文件类型</label>
					<div class="col-sm-8">
						<s:select list="#{'js':'javasript', 'properties': 'properties' }"
							id="res_type" name="res_type" listKey="key" listValue="value"
							headerKey="" headerValue="---请选择---"
							cssClass="form-control chosen-select"
							validate="{required:true,multiUnique:['V0201',['#res_code', '#res_type'],['%{res_code}', '%{res_type}'],'违反唯一性约束，已存在相同的资源文件配置!']}"></s:select>
						<SCRIPT type="text/javascript">
							jQuery('#res_type').trigger("chosen");
						</SCRIPT>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12" style="display:none;">
				<div class="form-group">
					<label for="" class="col-sm-2 control-label">文件名称</label>
					<div class="col-sm-10">
						<s:textfield id="res_name" name="res_name" cssClass="form-control"
							validate="{stringMaxLength:100}" readonly="true"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-2 control-label">文件路径</label>
					<div class="col-sm-10">
						<s:textfield id="res_path" name="res_path" cssClass="form-control"
							validate="{stringMaxLength:100}" readonly="true"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-2 control-label">文件备注</label>
					<div class="col-sm-10">
						<s:textarea id="res_bz" name="res_bz" cssClass="form-control"
							rows="2" cols="2" style="height:auto" validate="{stringMaxLength:200}"></s:textarea>
					</div>
				</div>
			</div>
		</div>
		<div id="I18nDiv" style="display:none;"></div>
	</s:form>
	<div id="gridI18n" style="margin: 10px 10px;">
		<blockquote class="blockquote1" style="padding: 10px 0px 35px 0px;">
			<p style="float: left;">资源内容信息</p>
			<div style="float: right;" class="btn-toolbar">
				<button type="button" class="btn btn-sm btn-primary" href="javascript:void(0);" id="addResI18n"> 增 加</button>
				<button type="button" class="btn btn-sm btn-primary" href="javascript:void(0);" id="delResI18n"> 删 除</button>
			</div>
		</blockquote>
		<div class="ui-grid-handle" id="gridI18nHandle">&nbsp;</div>
		<table id="tabGridI18n"></table>	
	</div>
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/i18n/xgI18nRes.js?ver=<%=jsVersion%>"></script>
</html>