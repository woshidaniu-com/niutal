<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<s:form id="childFixWidth" theme="simple" cssClass="form-horizontal sl_all_form">
		<s:hidden id="cxAll" name="cxAll" />
		<div class="row">
			<div class="col-sm-5">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">业务大类</label>
					<div class="col-sm-8">
						<s:select name="ywdl" id="ywdlSelect" cssClass="form-control chosen-select" headerValue="全部" headerKey=""
								list="ywdlList" listKey="YWDL" listValue="YWDLMC"></s:select>
						<SCRIPT type="text/javascript">
							jQuery('#ywdlSelect').trigger("chosen");
						</SCRIPT>
					</div>
				</div>
			</div>
		 
			<div class="col-sm-5">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">业务名称</label>
					<div class="col-sm-8">
						<s:textfield type="text" id="businessNameSelect" name="businessName" cssClass="form-control"/>
					</div>
				</div>
			</div>
			
			<div class="col-sm-2">
				<div style="text-align: right">
					<button type="button" id="search_go" onclick="searchChild()" class="btn btn-primary">查询</button>
				</div>
			</div>
		</div>
	</s:form>
	
	<table id="childGrid"></table>
	<div id="childPager"></div>
</body>
<script type="text/javascript" src="<%=systemPath %>/js/sp/business_select.js?ver=<%=jsVersion%>"></script>
</html>