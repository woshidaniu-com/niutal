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
	<div class="row" style="min-height: 300px;">
		<div class="col-md-12 col-sm-12">
			<div class="col-md-12 col-sm-12" style="padding: 0px;" id="field_container">
				<table id="tabGrid_fields"></table>
			</div>
			<div style="padding: 4px;text-align: left; " class="col-md-12 col-sm-12 clearfix ">
				<div style="float:right;" role="toolbar" class="btn-toolbar">
					<div class="btn-group">
						<button id="btn_add_row" href="javascript:void(0);" class="btn btn-default" type="button">
						<i class="bigger-100 glyphicon glyphicon-plus"></i>&nbsp;增加列信息</button>
					</div> 
				</div>
			</div>
		</div>
	</div>
</s:form>
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/design/design-func-element-field.js?ver=<%=jsVersion%>"></script>
</html>