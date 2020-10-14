<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
<style type="text/css">
.has-error .ke-container {
	border-color: #a94442;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
}
</style>
</head>
<body style="background: #fff;">
	
	<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/xtgl/xtczms_xgBcXtczms.html" theme="simple">
		<s:hidden id="xtczms_id" name="xtczms_id" />
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group" style="margin-bottom: 5px !important;">
					<label for="" class="col-sm-2 col-md-2 control-label">
						功能模块
					</label>
					<div class="col-sm-9 col-md-9">
						<p class="form-control-static"><s:property value="gnmkmc"/></p>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
	        	<div class="form-group" style="margin-bottom: 5px !important;">
					<label for="" class="col-sm-2 col-md-2 control-label">
						操作名称
					</label>
					<div class="col-sm-9 col-md-9">
						<p class="form-control-static"><s:property value="czmc"/></p>
					</div>
				</div>
		    </div>
			<div class="col-md-12 col-sm-12">
	        	<div class="form-group">
					<label for="" class="col-sm-2 col-md-2 control-label">
						备注
					</label>
					<div class="col-sm-9 col-md-9">
						<p class="form-control-static"><s:property value="bz"/></p>
					</div>
				</div>
		    </div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 col-md-2 control-label">
						<span style="color:red;">*</span>操作描述
					</label>
					<div class="col-sm-9 col-md-9" id="ke_control">
						<s:textarea cssStyle="width:100%;height:400px" name="czms" id="czms" cssClass="form-control"></s:textarea>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 col-md-2 control-label">
						<span style="color:red;">*</span>启用状态
					</label>
					<div class="col-sm-9 col-md-9">
						<s:iterator value="sfsyList" status="stat">
						<label class="radio-inline">
						    <input validate="{required:true}" type="radio" id="sfsy_<s:property value="#stat.index"/>" name="sfsy" <s:if test="key == sfsy "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
					</div>
				</div>
			</div>
		</div>
	</s:form>
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/xtczms/xgXtczms.js?ver=<%=jsVersion%>"></script>
</html>
