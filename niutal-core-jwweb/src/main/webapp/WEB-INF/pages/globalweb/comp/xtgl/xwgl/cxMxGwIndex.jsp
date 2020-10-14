<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>  
</head>
<body>
	<s:form id="ajaxForm" action="" theme="simple" cssClass="form-horizontal sl_all_form">
		<div class="row">
			<div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		<font color="red">*</font>限制类别
		          </label>
		          <div class="col-sm-7">
		            	<s:select list="#{'mx':'面向','xz':'限制'}" listKey="key" listValue="value"
		            	 cssClass="form-control chosen-select" id="xzlb" name="xzlb"></s:select>
		            	 <SCRIPT type="text/javascript">
				    		jQuery('#xzlb').trigger("chosen");
				    	  </SCRIPT>
		          </div>
		        </div>
		      </div>
		       <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		<font color="red">*</font>角色
		          </label>
		          <div class="col-sm-7">
		              <s:select name="jsdm" list="jsList" listKey="jsdm" cssClass="form-control chosen-select"
		          	listValue="jsmc" id="jsdm" headerKey="" headerValue="---请选择---"  onchange="queryResult()"></s:select>
		          	  <SCRIPT type="text/javascript">
			    		jQuery('#jsdm').trigger("chosen");
			    	  </SCRIPT>
		          </div>
		        </div>
		      </div>
		      
		      <div class="col-sm-12">
			      	 <div  class="panel panel-default">
						<div class="panel-heading" id="title-h3">
							<h3 class="panel-title">
								<input id="szdjs" onclick="queryResult()" type="checkbox">&nbsp;&nbsp;设置到教师
							</h3>
						</div>
						<div class="panel-body" style="padding:0px;">
							 <table id="tabGrid1"></table>
							 <div id="pager1"></div>
						</div>
					</div>
		      </div>
		</div>
	</s:form>
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/xwgl/cxMxGwIndex.js?ver=<%=jsVersion%>"></script>
</html>
